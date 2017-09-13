/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;
import com.wanfin.fpd.modules.common.service.tpl.TplPublicService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.vo.CsPersonVo;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.entity.CreditCoborrower;
import com.wanfin.fpd.modules.credit.entity.report.ReportCompany;
import com.wanfin.fpd.modules.credit.entity.report.ReportCulture;
import com.wanfin.fpd.modules.credit.entity.report.ReportEmployee;
import com.wanfin.fpd.modules.credit.entity.report.ReportPlanted;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.credit.service.CreditCoborrowerService;
import com.wanfin.fpd.modules.credit.service.report.ReportCompanyService;
import com.wanfin.fpd.modules.credit.service.report.ReportCultureService;
import com.wanfin.fpd.modules.credit.service.report.ReportEmployeeService;
import com.wanfin.fpd.modules.credit.service.report.ReportPlantedService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 授信申请Controller
 * @author zzm
 * @version 2016-07-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/creditApply")
public class CreditApplyController extends BaseController {
	@Autowired
    FormService formService;//获取流程表单内容
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CreditCoborrowerService creditCoborrowerService;//共同借款人
	//@Autowired
	//private SystemService systemService;
	@Autowired
	private CreditApplyService creditApplyService;
	@Autowired
	private ProductBizCfgService productBizCfgService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired 
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TplPublicService tplPublicService;//公共模板
//	@Autowired
//	private ReportCultureService reportCultureService;//贷款项目调查报告养殖贷
	@Autowired
	private ReportPlantedService reportPlantedService;//贷款项目调查报告种殖贷
//	@Autowired
//	private ReportEmployeeService reportEmployeeService;//贷款项目调查报告职工信用贷
//	@Autowired
//	private ReportCompanyService reportCompanyService;//贷款项目调查报告企业贷
	//@Autowired 
	//private ActTaskService actTaskService;
	
	@ModelAttribute
	public CreditApply get(@RequestParam(required=false) String id) {
		CreditApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditApplyService.get(id);
		}
		if (entity == null){
			entity = new CreditApply();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditApply:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditApply creditApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditApply> page = creditApplyService.findPage(new Page<CreditApply>(request, response), creditApply); 
		model.addAttribute("page", page);
		model.addAttribute("creditApply", creditApply);
		
//		//获取用户在流程中使用的名称
//		System.out.println("=============================");
//		System.out.println("获取用户在流程中使用的名称");
//		List<String> ennameList = systemService.findDealLoginName("hsps");
//		for(String enname:ennameList){
//			System.out.println("enname="+enname);
//		}
//		System.out.println("=============================");
		
		return "modules/credit/creditApplyList";
	}
	/**
	 * @Description		
	 * @param creditApply    申请授信
	 * @param bizCode		客户授信的对应编号
	 * @param request
	 * @param model			
	 * @return
	 * @author 
	 */
	@RequiresPermissions("credit:creditApply:view")
	@RequestMapping(value = "form")
	public String form(CreditApply creditApply,String bizCode, HttpServletRequest request, Model model) {
		TEmployee employee = tEmployeeService.get(creditApply.getCustomerId());
		
		String tab = request.getParameter("tab");
		
		String ifEdit = "view";
		//为了关联先生成临时ID,保存的时候需要处理
		if(StringUtils.isBlank(creditApply.getId())){
			creditApply.setId("temp_"+IdGen.uuid());
			ifEdit = "edit";
		}else if(creditApply.getId().startsWith("temp_")){
			ifEdit = "edit";
		}
		
		if(StringUtils.isBlank(creditApply.getProductId())){//产品ID
			creditApply.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		}
		if(StringUtils.isBlank(creditApply.getProductname())){//产品名称
			creditApply.setProductname((String)UserUtils.getCache(UserUtils.CACHE_SYSNAME));
		}
		
		//个人授信     个人客户改为固定模板  (前端显示模板)
		if(employee != null){
			creditApply.setCustomerName(employee.getName());
			model.addAttribute("customer", employee);
			FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
			/** Update By Chenh 2016-09-12 start **/
			/** 个人客户模板改为固定模板 **/
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TEMPLOYEE_FORM_ID);
			/** old **/
//			DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			/** Update By Chenh 2016-09-12 end **/
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			
		}else{//公司授信
			TCompany company = tCompanyService.get(creditApply.getCustomerId());
			creditApply.setCustomerName(company.getName());
			model.addAttribute("customer", company);
			FModel fm = FModel.M_CUSTOMER_COMPANY;
			/** Update By Chenh 2016-09-12 start **/
			/** 个人客户模板改为固定模板 **/
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TCOMPANY_FORM_ID);
			/** old **/
//			DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			/** Update By Chenh 2016-09-12 end **/
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			
		}
		//if(StringUtils.isNotBlank(bizCode) && StringUtils.isBlank(creditApply.getAct().getProcDefId())){
		//工作流id
		if(StringUtils.isBlank(creditApply.getAct().getProcDefId())){
			if(StringUtils.isBlank(bizCode)){
				//客户授信的对应编号
				bizCode = FModel.M_CUSTOMER_CREDIT.getKey();
			}
			//根据bizCode和产品id获取业务节点配置
			ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(bizCode);
			creditApply.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		//User user = UserUtils.getUser();
		//System.out.println(user.getOffice().getName());
		//通过产品来确定调查报告
		if(StringUtils.isNotBlank(creditApply.getProductId())){
			/*if("6320307f3c724c86a61d5f73512af954".equals(creditApply.getProductId())){*///种植贷
				//读取已有记录
				creditApply.setReportPlanted(reportPlantedService.getByCreditApply(creditApply.getId()));
				//没有记录则赋初始值
				//if(creditApply.getReportPlanted() == null || StringUtils.isBlank(creditApply.getReportPlanted().getId()) || StringUtils.isBlank(creditApply.getReportPlanted().getFtlContent())){
				if(creditApply.getReportPlanted() == null || StringUtils.isBlank(creditApply.getReportPlanted().getId()) ){
					
					ReportPlanted reportPlanted = new ReportPlanted();
					reportPlanted.setCreditApplyId(creditApply.getId());//设置临时ID
					reportPlanted.setName(creditApply.getCustomerName());
					reportPlanted.setSalesman(UserUtils.getUser().getName());
					reportPlanted.setDepartment(UserUtils.getUser().getOffice().getName());
					reportPlanted.setTplCode("nk_zhongzhidai");
					
					TplPublic tplPublic = tplPublicService.getByCode("nk_zhongzhidai");
					if(tplPublic != null && StringUtils.isNotBlank(tplPublic.getFtlContent())){
						reportPlanted.setFtlContent(tplPublic.getFtlContent());//初始化模板
					}
					creditApply.setReportPlanted(reportPlanted);
				}
				model.addAttribute("reportName", "nk_zhongzhidai");
			
		}
		//TODO
		
		//判断是否为最后节点
		//if(actTaskService.checkIsEnd(creditApply.getAct()))
		//{
		//	creditApply.getAct().setIsEnd("yes");
		//}
		//工作流   ---任务编号
		String taskId = creditApply.getAct().getTaskId();
		//System.out.println("任务ID="+taskId);
		if(StringUtils.isNotBlank(taskId)){
			
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			if(taskFormData != null){
				for (FormProperty p : taskFormData.getFormProperties()) {
					String getFormPropertyId = p.getId();
					String getFormPropertyname= p.getName();
					creditApply.setSubType(getFormPropertyname);
					//String getFormPropertyvalue= p.getValue();
					//System.out.println("FormProperty Data:id="+getFormPropertyId+";name="+getFormPropertyname+";value="+getFormPropertyvalue);
					model.addAttribute("subType", getFormPropertyname);
					if("approved".equals(getFormPropertyId)){
						if("start".equals(getFormPropertyname)){//只能提交，可以修改金额和时间
							//model.addAttribute("subType", "start");
							ifEdit = "edit";
						//}else if("four".equals(getFormPropertyname)){//同意、不同意、驳回、简化流程
							//model.addAttribute("subType", "four");
						//}else if("three".equals(getFormPropertyname)){//同意、不同意、驳回
							//model.addAttribute("subType", "three");
						//}else if("two".equals(getFormPropertyname)){//会审用：同意，不同意(流程不结束)
							//model.addAttribute("subType", "two");
						}else if("submit".equals(getFormPropertyname)){//只能提交，风控专员
							//model.addAttribute("subType", "submit");
							creditApply.setApprTotal((Integer)taskService.getVariable(taskId, "approvedTotal"));
							creditApply.setApprAgree((Integer)taskService.getVariable(taskId, "approvedAgree"));
							creditApply.setApprDisAgree((Integer)taskService.getVariable(taskId, "approvedDisAgree"));
						}
					}
				}
			}
			
		}
		
		if(StringUtils.isBlank(tab)){
		}else if("bd".equals(tab)){  //客户信息
		}else if("edit".equals(ifEdit) && "report".equals(tab)){//显示调查报告内容
			List<TplPublic> listTpl = tplPublicService.findList(new TplPublic());
			model.addAttribute("tplPublicList", listTpl);
			
		}else if("lc".equals(tab)){//审批
		}
		//获取共同借款人
		CreditCoborrower creditCoborrower = new CreditCoborrower();
		creditCoborrower.setCreditApplyId(creditApply.getId());
		creditApply.setCoborrowerList(creditCoborrowerService.findAllList(creditCoborrower));
		
		model.addAttribute("ifEdit", ifEdit);//调查报告是否可以编辑
		model.addAttribute("creditApply", creditApply);
				return "modules/credit/creditApplyFormPlanted";
	}
	
	@RequiresPermissions("credit:creditApply:view")
	@RequestMapping(value = "toSign")
	public String toSign(CreditApply creditApply, Model model) {
		TEmployee employee = tEmployeeService.get(creditApply.getCustomerId());
		if(employee != null){
			model.addAttribute("customer", employee);
			FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
//			DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
			DfFormTpl dfFormTpl =  dfFormTplService.get(Cons.FormTplId.B_TEMPLOYEE_FORM_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
		}else{
			TCompany company = tCompanyService.get(creditApply.getCustomerId());
			model.addAttribute("customer", company);
			FModel fm = FModel.M_CUSTOMER_COMPANY;
//			DfFormTpl dfFormTpl =  dfFormTplService.getByBizCode(fm.getKey());
			DfFormTpl dfFormTpl =  dfFormTplService.get(Cons.FormTplId.B_TCOMPANY_FORM_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
		}
		model.addAttribute("creditApply", creditApply);
		return "modules/credit/creditApplySign";
	}

	@RequiresPermissions("credit:creditApply:edit")
	@RequestMapping(value = "save")
	public String save(CreditApply creditApply, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditApply)){
			return form(creditApply, "", request, model);
		}
		
		try {
			creditApplyService.saveAudit(creditApply);
			addMessage(redirectAttributes, "保存授信申请成功");
			return "redirect:"+Global.getAdminPath()+"/credit/creditApply/?repage";
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, e.getMessage());
			return form(creditApply, "", request, model);
		}
	}
	
	//保存调查报告内容 TODO
	@RequiresPermissions("credit:creditApply:edit")
	@RequestMapping(value = "saveReport")
	public String saveReport(CreditApply creditApply, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditApply)){
			return form(creditApply, "", request, model);
		}
		try {
			//creditApplyService.save(creditApply);
			if(StringUtils.isNotBlank(creditApply.getProductId())){
				/*if("6320307f3c724c86a61d5f73512af954".equals(creditApply.getProductId())){*///种植贷
					reportPlantedService.save(creditApply.getReportPlanted());;//贷款项目调查报告养殖贷
				/*}*//*else if("84054c94119440588719741f1c42d6e1".equals(creditApply.getProductId())){//养殖贷
					reportCultureService.save(creditApply.getReportCulture());;//贷款项目调查报告养殖贷
				}else if("61bc8ee4b0014c5983058c610c8991c8".equals(creditApply.getProductId())){//员工贷
					reportEmployeeService.save(creditApply.getReportEmployee());
				}else if("3b36391cb60a47a0895e555d0ca53e94".equals(creditApply.getProductId())){//企业贷
					reportCompanyService.save(creditApply.getReportCompany());
				}*/
			}
			
			addMessage(redirectAttributes, "保存调查报告内容成功");
			//return "redirect:"+Global.getAdminPath()+"/credit/creditApply/?repage";
			if(StringUtils.isNotBlank(creditApply.getReportPlanted().getTmp()) && "1".equals(creditApply.getReportPlanted().getTmp())){//暂存处理
				//先查询授信金额是否已经存在，已经存在则更新
				creditApply.setCredit(creditApply.getReportPlanted().getCreditMoney());
				//授信金额为空   用贷款金额代替授信金额  都为null  为 0
				if(creditApply.getCredit() == null){
					if(creditApply.getReportPlanted().getLoanMoney() != null){
						creditApply.setCredit(creditApply.getReportPlanted().getLoanMoney());
					}else{
						creditApply.setCredit(new BigDecimal(0));
					}
				}
				//授信是否存在  存更  不存加
				CreditApply temp = creditApplyService.get(creditApply.getId());
				if(temp == null){
					creditApply.setApplyNum(UserUtils.getSqeNo(Cons.CountCategory.CREDIT_APPLY, "授信"));
					creditApply.setStatus(Cons.CreditApplyStatus.TEMP);
					creditApply.preCreateInfo();
					creditApplyService.insert(creditApply);
				}else{
					creditApply.preUpdate();
					creditApplyService.update(creditApply);
				}
				
				return "redirect:"+Global.getAdminPath()+"/credit/creditApply/?repage";
			}else{
				//creditApply.setCredit(creditApply.getReportPlanted().getCreditMoney());
				creditApply.setTab("lc");
			}
			return form(creditApply, "", request,model);
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, e.getMessage());
			return form(creditApply, "", request,model);
		}
	}
	
	@RequiresPermissions("credit:creditApply:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditApply creditApply, RedirectAttributes redirectAttributes) {
		creditApplyService.delete(creditApply);
		addMessage(redirectAttributes, "删除授信申请成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditApply/?repage";
	}
	
	/**
	 * @Description 调用风控接口获取客户的授信额度
	 * @param customerId
	 * @return
	 * @author zzm
	 * @date 2016-7-14 下午3:14:15  
	 */
	@ResponseBody
	@RequestMapping(value = "getFkCredit")
	public Map<String, Object> getFkCredit(CreditApply creditApply, HttpServletResponse response, Model model){
		Map<String, Object> result = Maps.newHashMap();
		TEmployee employee = tEmployeeService.get(creditApply.getCustomerId());
		try {
			if(employee != null){
				CsPersonVo param = new CsPersonVo(employee);
				creditApplyService.calculateGrade(param );
				result.put("credit", param.getAmount());
			}
			result.put("status", 1);
		} catch (ServiceException e) {
			e.printStackTrace();
			result.put("status", 0);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	/**
	 * @Description 获取授信申请的状态
	 * @param id
	 * @return
	 * @author zzm
	 * @date 2016-7-26 下午2:01:10  
	 */
	@ResponseBody
	@RequestMapping(value = "getCreditApplyStatus")
	public String getCreditApplyStatus(CreditApply creditApply, Model model, RedirectAttributes redirectAttributes) {
		return creditApply.getStatus();
	}

}