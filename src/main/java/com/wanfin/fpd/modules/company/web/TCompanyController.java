/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.company.web;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.sun.mail.imap.protocol.Status;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.company.entity.Customer;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 企业客户Controller
 * @author lx
 * @version 2016-03-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/company/tCompany")
public class TCompanyController extends BaseController {

	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired 
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TProductService tProductService;
	
	@ModelAttribute
	public TCompany get(@RequestParam(required=false) String id) {
		TCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCompanyService.get(id);
		}
		if (entity == null){
			entity = new TCompany();
		}
		return entity;
	}
	
	/**
	 * 目标客户列表
	 * @param tCompany
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCompany tCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCompany.setStatus(Cons.CustomerStatus.UNNORMAL);
		Page<TCompany> page = tCompanyService.findPage(new Page<TCompany>(request, response), tCompany); 
		model.addAttribute("page", page);
		model.addAttribute("tCompany",tCompany);
		model.addAttribute("goal_customer","goal_customer");
		return "modules/company/tCompanyList";
	}
	
	/**
	 * | 客户列表
	 * @param tCompany
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "listP")
	public String listP(TCompany tCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCompany.setStatus(Cons.CustomerStatus.NORMAL);
		Page<TCompany> page = tCompanyService.findCompanyPage(new Page<TCompany>(request, response), tCompany); 
		model.addAttribute("page", page);
		model.addAttribute("tCompany",tCompany);
		model.addAttribute("goal_customer","customer");
		return "modules/company/tCompanyList";
	}

	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "listAll")
	public String listAll(TCompany tCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCompany.setOrganId(UserUtils.getUser().getCompany().getId());
		Page<TCompany> page = tCompanyService.findPage(new Page<TCompany>(request, response), tCompany); 
		model.addAttribute("page", page);
		model.addAttribute("tCompany",tCompany);
		return "modules/company/tCompanyListAll";
	}

	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "form")
	public String form(TCompany tCompany, Model model) {
		if (StringUtils.isBlank(tCompany.getId())){
			//新增是设置一个临时id, 以"new_"开头表示
			tCompany.setId("new_"+IdGen.uuid());
		}
		FModel fm = FModel.M_CUSTOMER_COMPANY;
		/** 
		 * Start By Chenh 2016-08-24 Start 
		 * Bug #2777 企业客户模板异常
		 **/
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TCOMPANY_FORM_ID);

		
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tCompany);
		String url = "modules/company/tCompanyForm";

		return url;
		
		
	}

	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "view")
	public String view(TCompany tCompany, Model model) {
		tCompany = tCompanyService.get(tCompany);
		model.addAttribute("tCompany",tCompany);
		return "modules/wind/creditchecking/toViewCompany";
	}

	@RequiresPermissions("company:tCompany:edit")
	@RequestMapping(value = "save")
	public String save(TCompany tCompany, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		tCompany.setOrganId(currentUser.getOffice().getParent().getId()); 
		if (!beanValidator(model, tCompany)){  
			return form(tCompany, model);
		}
		//判断同一个租户该用户是否已经存在
		if(StringUtils.isNotBlank(tCompany.getName()) && StringUtils.isNotBlank(tCompany.getCardNum())){
			TCompany tmp = new TCompany();
			tmp.setId(tCompany.getId());
			//tmp.setName(tCompany.getName());
			tmp.setCardNum(tCompany.getCardNum());
			tmp.setOrganId(UserUtils.getUser().getCompany().getId());
			List<TCompany> list = tCompanyService.getByCondition(tmp);
			if(list.size()>0){// && (StringUtils.isBlank(tCompany.getId()) || tCompany.getId().startsWith("new_"))
				model.addAttribute("message", "该企业已经存在，请确认是否已经是公司客户或填写错误！");
				return form(tCompany, model);
			}
			//else if(list.size()>1){
			//	model.addAttribute("message", "该企业证件号码已经存在,请确保是否填写正确！");
			//	return form(tCompany, model);
			//}
		}else{
			model.addAttribute("message", "企业姓名、企业证件号码不能为空！");
			return form(tCompany, model);
		}
		tCompany.setScanFlag("0");
		tCompanyService.save(tCompany);
		addMessage(redirectAttributes, "保存企业客户成功");
		return "redirect:"+Global.getAdminPath()+"/company/tCompany/?repage";
	}
	
	@RequiresPermissions("company:tCompany:edit")
	@RequestMapping(value = "delete")
	public String delete(TCompany tCompany, RedirectAttributes redirectAttributes) {
		tCompany.setScanFlag("0");
		tCompanyService.delete(tCompany);
		addMessage(redirectAttributes, "删除企业客户成功");
		return "redirect:"+Global.getAdminPath()+"/company/tCompany/?repage";
	}
	
	
	
	//session客户类型，给下个方法treeData使用
	@ResponseBody
	@RequiresPermissions("product:tProduct:edit")
	@RequestMapping(value = "sCustomerType")
	public void sCustomerType(String id, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		request.getSession().setAttribute("customer_type", id);
	}
	
	
	
	
	/**
	 * 系统中需要选择客户时使用
	 * @param extId
	 * @param response
	 * @param request
	 * @return
	 */
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response,HttpServletRequest request) {
		
		if(StringUtils.isBlank(extId)){
			//extId  1企业    2个人
			String productid = (String)UserUtils.getCache(UserUtils.CACHE_SYSCODE);
			if(productid!=null&&!"".equals(productid)){
				TProduct tproduct = tProductService.get(productid);
				extId = tproduct.getReleasesObje();
				//System.out.println(extId);
			}
			if(extId==null||"".equals(extId)){
				extId = "1,2";
			}			
		}
		
		
		List<Map<String, Object>> mapList = Lists.newArrayList();//客户列表内容
		TCompany company = new TCompany();
		company.setStatus(Cons.CustomerStatus.NORMAL);
		TEmployee employee = new TEmployee();
		employee.setStatus(Cons.CustomerStatus.NORMAL);
		List<TCompany> list = tCompanyService.findList(company);//企业客户
		List<TEmployee> list1 = tEmployeeService.findList(employee);//个人客户
		if(extId.contains(Cons.CustomerType.CUST_COMPANY) &&
				list != null && list.size() > 0 ){
			Map<String, Object> pMap = Maps.newHashMap();
			pMap.put("id", Cons.CustomerType.CUST_COMPANY);//企业客户
			pMap.put("pId", null);
			pMap.put("name", "企业");
			mapList.add(pMap);
			for (int i=0; i<list.size(); i++){
				TCompany e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId",Cons.CustomerType.CUST_COMPANY);//企业用户
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		if(extId.contains(Cons.CustomerType.CUST_EMPLOYEE) &&
				list1 != null && list1.size() > 0  ){
			Map<String, Object> pMap = Maps.newHashMap();
			pMap.put("id",Cons.CustomerType.CUST_EMPLOYEE);//个人客户
			pMap.put("pId", null);
			pMap.put("name", "个人");
			mapList.add(pMap);
			for (int i=0; i<list1.size(); i++){
				TEmployee e = list1.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", Cons.CustomerType.CUST_EMPLOYEE);//个人用户
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataB")
	public List<Map<String, Object>> treeDataBlack(@RequestParam(required=false) String extId, HttpServletResponse response,HttpServletRequest request) {
		
		//extId  1企业    2个人
		String productid = (String)UserUtils.getCache(UserUtils.CACHE_SYSCODE);
		if(productid!=null&&!"".equals(productid)){
			TProduct tproduct = tProductService.get(productid);
			extId = tproduct.getReleasesObje();
			System.out.println(extId);
		}
		if(extId==null||"".equals(extId)){
			extId = "1,2";
		}
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		
		/**
		 * 正式企业客户
		 */
		TCompany company = new TCompany();
		company.setStatus(Cons.CustomerStatus.NORMAL);
		List<TCompany> list = tCompanyService.findList(company);
		if(list != null && list.size() > 0 ){
			Map<String, Object> pMap = Maps.newHashMap();
			pMap.put("id", 1);//企业客户
			pMap.put("pId", null);
			pMap.put("name", "正式企业客户");
			mapList.add(pMap);
			for (int i=0; i<list.size(); i++){
				TCompany e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId",1);//企业用户
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		
		/**
		 * 潜在企业客户
		 */
		TCompany company2 = new TCompany();
		company2.setStatus(Cons.CustomerStatus.UNNORMAL);
		List<TCompany> list2 = tCompanyService.findList(company2);
		if(list2 != null && list2.size() > 0 ){
			Map<String, Object> pMap2 = Maps.newHashMap();
			pMap2.put("id", 11);//企业客户
			pMap2.put("pId", null);
			pMap2.put("name", "潜在企业客户");
			mapList.add(pMap2);
			for (int i=0; i<list2.size(); i++){
				TCompany e = list2.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map2 = Maps.newHashMap();
					map2.put("id", e.getId());
					map2.put("pId",11);//企业用户
					map2.put("name", e.getName());
					mapList.add(map2);
				}
			}
		}
		/**
		 * 正式个人客户
		 */
		TEmployee employee = new TEmployee();
		employee.setStatus(Cons.CustomerStatus.NORMAL);
		List<TEmployee> list1 = tEmployeeService.findList(employee);
		if(list1 != null && list1.size() > 0  ){
			Map<String, Object> pMap3 = Maps.newHashMap();
			pMap3.put("id",2);//个人客户
			pMap3.put("pId", null);
			pMap3.put("name", "正式个人客户");
			mapList.add(pMap3);
			for (int i=0; i<list1.size(); i++){
				TEmployee e = list1.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map3 = Maps.newHashMap();
					map3.put("id", e.getId());
					map3.put("pId",2);//个人用户
					map3.put("name", e.getName());
					mapList.add(map3);
				}
			}
		}
		/**
		 * 潜在企业客户
		 */
		TEmployee employee2 = new TEmployee();
		employee2.setStatus(Cons.CustomerStatus.UNNORMAL);
		List<TEmployee> list4 = tEmployeeService.findList(employee2);
		if(list4 != null && list4.size() > 0  ){
			Map<String, Object> pMap4 = Maps.newHashMap();
			pMap4.put("id",22);//个人客户
			pMap4.put("pId", null);
			pMap4.put("name", "潜在个人客户");
			mapList.add(pMap4);
			for (int i=0; i<list4.size(); i++){
				TEmployee e = list4.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.contains(e.getId()))){
					Map<String, Object> map4 = Maps.newHashMap();
					map4.put("id", e.getId());
					map4.put("pId", 22);//个人用户
					map4.put("name", e.getName());
					mapList.add(map4);
				}
			}
		}
		return mapList;
	}
	
	/**
	 * 目标客户添加至客户时使用
	 * @param extId
	 * @param response
	 * @param request
	 * @return
	 */
	
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataP")
	public List<Map<String, Object>> treeDataP(@RequestParam(required=false) String extId, HttpServletResponse response,HttpServletRequest request) {
	
		List<Map<String, Object>> mapList = Lists.newArrayList();
		TCompany company = new TCompany();
		company.setStatus(Cons.CustomerStatus.UNNORMAL);
		TEmployee employee = new TEmployee();
		employee.setStatus(Cons.CustomerStatus.UNNORMAL);
		List<TCompany> list = tCompanyService.findList(company);
		List<TEmployee> list1 = tEmployeeService.findList(employee);
		if(!StringUtils.equals(extId, Cons.CustomerType.CUST_COMPANY) &&
				list != null && list.size() > 0 ){
			Map<String, Object> pMap = Maps.newHashMap();
			pMap.put("id", Cons.CustomerType.CUST_COMPANY);//企业客户
			pMap.put("pId", null);
			pMap.put("name", "企业");
			mapList.add(pMap);
			for (int i=0; i<list.size(); i++){
				TCompany e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId",Cons.CustomerType.CUST_COMPANY);//企业用户
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		if(!StringUtils.equals(extId, Cons.CustomerType.CUST_EMPLOYEE) &&
				list1 != null && list1.size() > 0  ){
			Map<String, Object> pMap = Maps.newHashMap();
			pMap.put("id",Cons.CustomerType.CUST_EMPLOYEE);//个人客户
			pMap.put("pId", null);
			pMap.put("name", "个人");
			mapList.add(pMap);
			for (int i=0; i<list1.size(); i++){
				TEmployee e = list1.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", Cons.CustomerType.CUST_EMPLOYEE);//个人用户
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
	
	
	
	/**
	 * @Description 加入黑名单的审核表单
	 * @param id
	 * @param businessTable
	 * @author zzm
	 * @date 2016-4-14 下午5:51:49  
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "auditForm")
	public String auditForm(TCompany tCompany, Model model, RedirectAttributes redirectAttributes) {
		TEmployee tEmployee = tEmployeeService.get(tCompany.getId());
		
		//企业客户表单
		if(tEmployee == null){
			if(StringUtils.isBlank(tCompany.getProcInsId()) &&
					(Cons.CustomerStatus.BLACK.equals(tCompany.getStatus())
					||Cons.CustomerStatus.BLACK_AUDIT.equals(tCompany.getStatus()))){
				addMessage(redirectAttributes, "已是黑名单用户或正黑名单审核中！");
				return "redirect:"+Global.getAdminPath()+"/company/tCompany/?repage";
			}
			FModel fm = FModel.M_CUSTOMER_COMPANY;
			/** 
			 * Start By Chenh 2016-09-20 Start 
			 * Bug #3218 企业客户模板异常
			 **/
//			DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			/** Old*******************************/
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TCOMPANY_FORM_ID);
			/** Update By Chenh 2016-09-20 End **/
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("tCompany", tCompany);
			return "modules/company/auditForm";
		} 
		//个人客户表单
		else {
			tEmployee.setAct(tCompany.getAct());
			if(StringUtils.isBlank(tEmployee.getProcInsId()) && 
					(Cons.CustomerStatus.BLACK.equals(tEmployee.getStatus())
					||Cons.CustomerStatus.BLACK_AUDIT.equals(tEmployee.getStatus()))){
				addMessage(redirectAttributes, "已是黑名单用户或正黑名单审核中！");
				return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/?repage";
			}
			FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
			/** 
			 * Start By Chenh 2016-09-20 Start 
			 * Bug #3218 企业客户模板异常
			 **/
//			DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			/** Old*******************************/
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TEMPLOYEE_FORM_ID);
			/** Update By Chenh 2016-09-20 End **/
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("tEmployee", tEmployee);
			return "modules/employee/auditForm";
		}

	}	
	
	/**
	 * @Description 解除黑名单的审核表单
	 * @param id
	 * @param businessTable
	 * @author zzm
	 * @date 2016-4-14 下午5:51:49  
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "removeBlackForm")
	public String removeBlackForm(TCompany tCompany, Model model, RedirectAttributes redirectAttributes) {
		TEmployee tEmployee = tEmployeeService.get(tCompany.getId());
		
		//企业客户表单
		if(tEmployee == null){
			if(StringUtils.isBlank(tCompany.getProcInsId()) &&
					(Cons.CustomerStatus.NORMAL.equals(tCompany.getStatus()) 
					  || Cons.CustomerStatus.REMOVE_BLACK_AUDIT.equals(tCompany.getStatus()))){
				addMessage(redirectAttributes, "非黑名用户不用解除黑名，或审核中！");
				return "redirect:"+Global.getAdminPath()+"/company/tCompany/blackList?repage";
			}
			FModel fm = FModel.M_CUSTOMER_COMPANY;
			//DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TCOMPANY_FORM_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("tCompany", tCompany);
			return "modules/company/auditForm";
		} 
		//个人客户表单
		else {
			tEmployee.setAct(tCompany.getAct());
			if(StringUtils.isBlank(tEmployee.getProcInsId()) && 
					(Cons.CustomerStatus.NORMAL.equals(tCompany.getStatus()) 
							  || Cons.CustomerStatus.REMOVE_BLACK_AUDIT.equals(tCompany.getStatus()))){
				addMessage(redirectAttributes, "非黑名用户不用解除黑名！或审核中");
				return "redirect:"+Global.getAdminPath()+"/company/tCompany/blackList?repage";
			}
			FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
			//DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TEMPLOYEE_FORM_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("tEmployee", tEmployee);
			return "modules/employee/auditForm";
		}

	}	
	
	/**
	 * @Description 加入/解除黑名单申请审批
	 * @param id
	 * @param businessTable
	 * @return
	 * @author zzm 
	 * @date 2016-4-15 上午9:23:14  
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(TCompany tCompany, Model model, RedirectAttributes redirectAttributes) {
		TEmployee tEmployee = tEmployeeService.get(tCompany.getId());
		if (!beanValidator(model, tCompany)){
			return auditForm(tCompany, model, redirectAttributes);
		}
		tCompanyService.saveAudit(tCompany);
		addMessage(redirectAttributes, "处理成功");
		
		if(Cons.ProcDefKey.BLACK_REMOVE.equals(tCompany.getAct().getProcDefKey())){
			return "redirect:"+Global.getAdminPath()+"/company/tCompany/blackList?repage";
		}else if(tEmployee != null){
			return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/company/tCompany/?repage";
		}
	}

	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "blackList")
	public String blackList(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		customer.setStatus(Cons.CustomerStatus.BLACK+","+Cons.CustomerStatus.REMOVE_BLACK_AUDIT);//查询黑名状态、解除黑名审核中的的客户
		customer.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		Page<Customer> page = tCompanyService.findCustomByPage(new Page<Customer>(request, response), customer); 
		model.addAttribute("page", page);
		model.addAttribute("customer",customer);
		return "modules/company/blackList";
	}
	
	
	
	/**
	 * 客户类型统计分析
	 * @param customer
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("company:tCompany:view")
	@RequestMapping(value = "customerstatistic")
	public String customerstatistic(String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		TCompany  tCompany = new TCompany();
		TEmployee tEmployee = new TEmployee();
		
		List<TCompany> list  = tCompanyService.findAllList(tCompany);//企业客户集合
		List<TEmployee> liste = tEmployeeService.findAllList(tEmployee);
		
		String str_url = null;
		
		if(type.equals("area")){
			str_url = "modules/custumerAnalysis/areastatistic";
		}else if(type.equals("type")){
			Calendar c = Calendar.getInstance();
			
			int now_year = c.get(Calendar.YEAR);
						
			int cJanuary  = 0,cFebruary  = 0,cMarch  = 0,cApril  = 0,cMay  = 0,cJune  = 0,cJuly  = 0,cAugust  = 0,cSeptember  = 0,cOctober  = 0,cNovember  = 0,cDecember = 0;
			int eJanuary  = 0,eFebruary  = 0,eMarch  = 0,eApril  = 0,eMay  = 0,eJune  = 0,eJuly  = 0,eAugust  = 0,eSeptember  = 0,eOctober  = 0,eNovember  = 0,eDecember = 0;
			
			for (int i = 0; i <list.size(); i++) {
				tCompany = list.get(i);
				
				c.setTime(tCompany.getCreateDate());
				
				 int year = c.get(Calendar.YEAR);
				 int month = c.get(Calendar.MONTH)+1;
				 
				 if(year!=now_year){//默认统计本年客户信息
					 continue;
				 }
				if(month==1){
					cJanuary +=1;
				}else if(month==2){
					cFebruary+=1;
				}else if(month==3){
					cMarch+=1;
				}else if(month==4){
					cApril+=1;
				}else if(month==5){
					cMay+=1;
				}else if(month==6){
					cJune+=1;
				}else if(month==7){
					cJuly+=1;
				}else if(month==8){
					cAugust+=1;
				}else if(month==9){
					cSeptember+=1;
				}else if(month==10){
					cOctober+=1;
				}else if(month==11){
					cNovember+=1;
				}else if(month==12){
					cDecember+=1;
				}
			}
			
			for (int i = 0; i <liste.size(); i++) {
				tEmployee = liste.get(i);
				c.setTime(tEmployee.getCreateDate());
				int month = c.get(Calendar.MONTH)+1;
				 int year = c.get(Calendar.YEAR);
				 
				 if(year!=now_year){//默认统计本年客户信息
					 continue;
				 }
				
				if(month==1){
					eJanuary +=1;
				}else if(month==2){
					eFebruary+=1;
				}else if(month==3){
					eMarch+=1;
				}else if(month==4){
					eApril+=1;
				}else if(month==5){
					eMay+=1;
				}else if(month==6){
					eJune+=1;
				}else if(month==7){
					eJuly+=1;
				}else if(month==8){
					eAugust+=1;
				}else if(month==9){
					eSeptember+=1;
				}else if(month==10){
					eOctober+=1;
				}else if(month==11){
					eNovember+=1;
				}else if(month==12){
					eDecember+=1;
				}
			}
		    request.setAttribute("cJanuary",cJanuary);
		    request.setAttribute("cFebruary",cFebruary);
		    request.setAttribute("cMarch",cMarch);
		    request.setAttribute("cApril",cApril);
		    request.setAttribute("cMay",cMay);
		    request.setAttribute("cJune",cJune);
		    request.setAttribute("cJuly",cJuly);
		    request.setAttribute("cAugust",cAugust);
		    request.setAttribute("cSeptember",cSeptember);
		    request.setAttribute("cOctober",cOctober);
		    request.setAttribute("cNovember",cNovember);
		    request.setAttribute("cDecember",cDecember);
		    
		    request.setAttribute("eJanuary",eJanuary);
		    request.setAttribute("eFebruary",eFebruary);
		    request.setAttribute("eMarch",eMarch);
		    request.setAttribute("eApril",eApril);
		    request.setAttribute("eMay",eMay);
		    request.setAttribute("eJune",eJune);
		    request.setAttribute("eJuly",eJuly);
		    request.setAttribute("eAugust",eAugust);
		    request.setAttribute("eSeptember",eSeptember);
		    request.setAttribute("eOctober",eOctober);
		    request.setAttribute("eNovember",eNovember);
		    request.setAttribute("eDecember",eDecember);

		    request.setAttribute("now_year",now_year);
		    
		    
			str_url = "modules/custumerAnalysis/typestatistic";
		}else if(type.equals("sex")){
			
		    //企业男女数量查找
		    int CManAmount = 0 ;//tCompanyService.findCustomerSexAmount("t_company","surety_sex",1,1);//表名，字段名，1企业   2个人 ，   1男   2女
		    int CWomanAmount = 0;// tCompanyService.findCustomerSexAmount("t_company","surety_sex",1,2);//表名，字段名，1企业   2个人 ，   1男   2女
		    //个人客户男女数量查找
		    int EManAmount =  0;//tCompanyService.findCustomerSexAmount("t_employee","sex",2,1);////表名，字段名，1企业   2个人 ，   1男   2女
		    int EWomanAmount = 0;//tCompanyService.findCustomerSexAmount("t_employee","sex",2,2);////表名，字段名，1企业   2个人 ，   1男   2女
			
		    for (int i = 0; i < list.size(); i++) {
		    	tCompany = list.get(i);
		    	if("1".equals(tCompany.getSuretySex())){
		    		CManAmount +=1;
		    	}else if("2".equals(tCompany.getSuretySex())){
		    		CWomanAmount +=1;
		    	}
			}
		    for (int i = 0; i < liste.size(); i++) {
		    	tEmployee = liste.get(i);
		    	if("1".equals(tEmployee.getSex())){
		    		EManAmount +=1;
		    	}else if("2".equals(tEmployee.getSex())){
		    		EWomanAmount +=1;
		    	}
			}
		    request.setAttribute("CManAmount",CManAmount);
			request.setAttribute("CWomanAmount",CWomanAmount);
			request.setAttribute("EManAmount",EManAmount);
			request.setAttribute("EWomanAmount",EWomanAmount);
			str_url = "modules/custumerAnalysis/sexstatistic";
		}else if(type.equals("turnover")){
			  
		    //企业月营业额 //10万以下  //10-50万                //50-100万               //100-500万              //500-1000万         //1000万以上
			int oneoption = 0,twooption = 0,threeoption = 0,fouroption = 0,fiveoption = 0,sixoption = 0;
			int oneoption1 = 0,twooption1 = 0,threeoption1 = 0,fouroption1 = 0,fiveoption1 = 0,sixoption1 = 0;

			//个人月收入//1万以下            //1-2万                           //2-5万                            //5-10万                             //10-50万                    //50万以上
			int eoneoption = 0,etwooption = 0,ethreeoption = 0,efouroption = 0,efiveoption = 0,esixoption = 0;
			int eoneoption1 = 0,etwooption1 = 0,ethreeoption1 = 0,efouroption1 = 0,efiveoption1 = 0,esixoption1 = 0;

			
			for (int i = 0; i < list.size(); i++) {
				tCompany = list.get(i);
				if(Integer.parseInt(tCompany.getTurnover())<100000){
					oneoption += Integer.parseInt(tCompany.getTurnover());
					oneoption1 +=1;
				}else if(100000<=Integer.parseInt(tCompany.getTurnover())&&Integer.parseInt(tCompany.getTurnover())<500000){
					twooption += Integer.parseInt(tCompany.getTurnover());
					twooption1+=1;
				}else if(500000<=Integer.parseInt(tCompany.getTurnover())&&Integer.parseInt(tCompany.getTurnover())<1000000){
					threeoption += Integer.parseInt(tCompany.getTurnover());
					threeoption1+=1;
				}else if(1000000<=Integer.parseInt(tCompany.getTurnover())&&Integer.parseInt(tCompany.getTurnover())<5000000){
					fouroption += Integer.parseInt(tCompany.getTurnover());
					fouroption1+=1;
				}else if(5000000<=Integer.parseInt(tCompany.getTurnover())&&Integer.parseInt(tCompany.getTurnover())<10000000){
					fiveoption += Integer.parseInt(tCompany.getTurnover());
					fiveoption1+=1;
				}else if(10000000<=Integer.parseInt(tCompany.getTurnover())){
					sixoption += Integer.parseInt(tCompany.getTurnover());
					sixoption1+=1;
				}
			}
			
			for (int i = 0; i < liste.size(); i++) {
				tEmployee = liste.get(i);
				if(tEmployee.getMonthIncome().intValue()<10000){
					eoneoption += tEmployee.getMonthIncome().intValue();
					eoneoption1+=1;
				}else if(10000<=tEmployee.getMonthIncome().intValue()&&tEmployee.getMonthIncome().intValue()<20000){
					etwooption += tEmployee.getMonthIncome().intValue();
					etwooption1+=1;
				}else if(20000<=tEmployee.getMonthIncome().intValue()&&tEmployee.getMonthIncome().intValue()<50000){
					ethreeoption += tEmployee.getMonthIncome().intValue();
					ethreeoption1+=1;
				}else if(50000<=tEmployee.getMonthIncome().intValue()&&tEmployee.getMonthIncome().intValue()<100000){
					efouroption += tEmployee.getMonthIncome().intValue();
					efouroption1+=1;
				}else if(100000<=tEmployee.getMonthIncome().intValue()&&tEmployee.getMonthIncome().intValue()<500000){
					efiveoption += tEmployee.getMonthIncome().intValue();
					efiveoption1+=1;
				}else if(500000<=tEmployee.getMonthIncome().intValue()){
					esixoption += tEmployee.getMonthIncome().intValue();
					esixoption1+=1;
				}
			}
			//阶段金额总数
			request.setAttribute("oneoption",oneoption);
			request.setAttribute("twooption",twooption);
			request.setAttribute("threeoption",threeoption);
			request.setAttribute("fouroption",fouroption);
			request.setAttribute("fiveoption",fiveoption);
			request.setAttribute("sixoption",sixoption);
			//阶段金额数量
			request.setAttribute("oneoption1",oneoption1);
			request.setAttribute("twooption1",twooption1);
			request.setAttribute("threeoption1",threeoption1);
			request.setAttribute("fouroption1",fouroption1);
			request.setAttribute("fiveoption1",fiveoption1);
			request.setAttribute("sixoption1",sixoption1);
			
			request.setAttribute("eoneoption",eoneoption);
			request.setAttribute("etwooption",etwooption);
			request.setAttribute("ethreeoption",ethreeoption);
			request.setAttribute("efouroption",efouroption);
			request.setAttribute("efiveoption",efiveoption);
			request.setAttribute("eixoption",esixoption);
			
			request.setAttribute("eoneoption1",eoneoption1);
			request.setAttribute("etwooption1",etwooption1);
			request.setAttribute("ethreeoption1",ethreeoption1);
			request.setAttribute("efouroption1",efouroption1);
			request.setAttribute("efiveoption1",efiveoption1);
			request.setAttribute("eixoption1",esixoption1);
			
			str_url = "modules/custumerAnalysis/turnoverstatistic";
		}else if(type.equals("age")){
			
			
			    //18岁以下                      //18-25岁                 //26-35岁                         //36-45岁                //46以上
			int oneoption = 0,twooption = 0,threeoption = 0,fouroption = 0,fiveoption = 0;
			int eoneoption = 0,etwooption = 0,ethreeoption = 0,efouroption = 0,efiveoption = 0;
			
			for (int i = 0; i < list.size(); i++) {
				tCompany = list.get(i);
				if(Integer.parseInt(tCompany.getSuretyAge())<18){
					oneoption+=1;
				}else if(Integer.parseInt(tCompany.getSuretyAge())>=18&Integer.parseInt(tCompany.getSuretyAge())<=25){
					twooption+=1;
				}else if(Integer.parseInt(tCompany.getSuretyAge())>25&Integer.parseInt(tCompany.getSuretyAge())<=35){
					threeoption+=1;
				}else if(Integer.parseInt(tCompany.getSuretyAge())>35&Integer.parseInt(tCompany.getSuretyAge())<=45){
					fouroption+=1;
				}else if(Integer.parseInt(tCompany.getSuretyAge())>=46){
					fiveoption+=1;
				}
			}
			
			for (int i = 0; i < liste.size(); i++) {
				tEmployee = liste.get(i);
				if(Integer.parseInt(tEmployee.getAge())<18){
					eoneoption+=1;
				}else if(Integer.parseInt(tEmployee.getAge())>=18&Integer.parseInt(tEmployee.getAge())<=25){
					etwooption+=1;
				}else if(Integer.parseInt(tEmployee.getAge())>25&Integer.parseInt(tEmployee.getAge())<=35){
					ethreeoption+=1;
				}else if(Integer.parseInt(tEmployee.getAge())>35&Integer.parseInt(tEmployee.getAge())<=45){
					efouroption+=1;
				}else if(Integer.parseInt(tEmployee.getAge())>=46){
					efiveoption+=1;
				}
			}
			
			
			request.setAttribute("oneoption",oneoption);
			request.setAttribute("twooption",twooption);
			request.setAttribute("threeoption",threeoption);
			request.setAttribute("fouroption",fouroption);
			request.setAttribute("fiveoption",fiveoption);
			
			request.setAttribute("eoneoption",eoneoption);
			request.setAttribute("etwooption",etwooption);
			request.setAttribute("ethreeoption",ethreeoption);
			request.setAttribute("efouroption",efouroption);
			request.setAttribute("efiveoption",efiveoption);
			
			str_url = "modules/custumerAnalysis/agestatistic";
		}
		return str_url;
	}
	
	/**
	 * @Description 获取客户信息（企业/个人 ）
	 * @param id
	 * @return Customer
	 * @author zzm 
	 * @date 2016-6-3 下午2:43:16  
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomer")
	public Customer getCustomer(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		customer = tCompanyService.getCustomer(customer.getId()); 
		return customer;
	}
	
	


	/**
	 * @Description 
	 * @param 
	 * @param ids	客户ids（“,”）
	 * @return
	 * @author zzm
	 * @date 2016-6-7 下午2:25:06  
	 */
	@RequestMapping(value = "addP")
	public String addP(String customerType, String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			tCompanyService.updateStatus(idArray[i],customerType,Cons.CustomerStatus.NORMAL);
			System.out.println(idArray[i]);
		}
		addMessage(redirectAttributes, "添加成功！");
		if(StringUtils.equals(Cons.CustomerType.CUST_COMPANY,customerType)){
			return "redirect:"+Global.getAdminPath()+"/company/tCompany/listP?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/listP?repage";
		}
	}
	
	/**
	 * @Description 
	 * @param 
	 * @param ids	客户ids（“,”）
	 * @return
	 * @author zzm
	 * @date 2016-6-7 下午2:25:06  
	 */
	@RequestMapping(value = "addB")
	public String addB(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String customerType = null;
			String status = null;
			TCompany tCompany = tCompanyService.get(idArray[i]);
			TEmployee tEmployee = tEmployeeService.get(idArray[i]);
			if(tCompany !=null){
				customerType ="1";
				status="black"+"_"+tCompany.getStatus();
			}
			if(tEmployee !=null){
				customerType ="2";
				status="black"+"_"+tEmployee.getStatus();
			}
			tCompanyService.updateStatus(idArray[i],customerType,status);
			System.out.println(idArray[i]);
		}
		addMessage(redirectAttributes, "添加成功！");
		return "redirect:"+Global.getAdminPath()+"/company/tCompany/blackList?repage";
	}
	
	/**
	 * 移除黑名单
	 * @param customerType
	 * @param id
	 * @param redirectAttributes   提示信息
	 * @return
	 */
	@RequestMapping(value = "removeB")
	public String removeB(String id, RedirectAttributes redirectAttributes) {
		String customerType = null;
		String status = null;
		TCompany tCompany = tCompanyService.get(id);
		TEmployee tEmployee = tEmployeeService.get(id);
		if(tCompany !=null){
			customerType ="1";
			status=tCompany.getStatus().substring(6);
		}
		if(tEmployee !=null){
			customerType ="2";
			status=tEmployee.getStatus().substring(6);
		}
		tCompanyService.updateStatus(id,customerType,status);
		addMessage(redirectAttributes, "移除成功！");
		if(StringUtils.equals(Cons.CustomerType.CUST_COMPANY,customerType)){
			//表示个人客户
			return "redirect:"+Global.getAdminPath()+"/company/tCompany/listP?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/listP?repage";
		}
	}
	
	@RequestMapping(value = "removeP")
	public String removeP(String customerType,String id, RedirectAttributes redirectAttributes) {
		tCompanyService.updateStatus(id,customerType,Cons.CustomerStatus.UNNORMAL);
		addMessage(redirectAttributes, "移除成功！");
		if(StringUtils.equals(Cons.CustomerType.CUST_COMPANY,customerType)){
			//表示个人客户
			return "redirect:"+Global.getAdminPath()+"/company/tCompany/listP?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/listP?repage";
		}
	}
	
	
	
	/**
	 **当要移除客户时，先查看该客户是否已经申请了业务，有业务不能删除
	 */
	@ResponseBody
	@RequestMapping(value = "checkCustomerContract")
	public String checkCustomerContract(String id,String type, Model model, RedirectAttributes redirectAttributes) {
		
		TLoanContract tLoanContract = new TLoanContract();
		tLoanContract.setCustomerId(id);
		tLoanContract.setCustomerType(type);
		List<TLoanContract> contlist = tLoanContractService.findList(tLoanContract);
		if(contlist.size()==0){//Y 表示没有业务记录，可以删除
			return "Y";
		}else{
			return "N";
		}
	}
}