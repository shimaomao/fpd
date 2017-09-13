/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wanfin.fpd.modules.spouse.service.TSpouseService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 客户个人Controller
 * @author lx
 * @version 2016-03-12
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/employee/tEmployee")
public class TEmployeeController extends BaseController {
  
	@Autowired 
	private TEmployeeService tEmployeeService;
	@Autowired 
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TSpouseService tSpouseService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	@ModelAttribute
	public TEmployee get(@RequestParam(required=false) String id) {
		TEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tEmployeeService.get(id);
		}
		if (entity == null){
			entity = new TEmployee();
		}
		return entity;
	}
	
	@RequiresPermissions("employee:tEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(TEmployee tEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		tEmployee.setStatus(Cons.CustomerStatus.UNNORMAL);
		Page<TEmployee> page = tEmployeeService.findPage(new Page<TEmployee>(request, response), tEmployee); 
		model.addAttribute("page", page);
		model.addAttribute("tEmployee",tEmployee);
		model.addAttribute("goal_customer","goal_customer");
		return "modules/employee/tEmployeeList";
	}
	
	@RequiresPermissions("employee:tEmployee:view")
	@RequestMapping(value = "listP")
	public String listP(TEmployee tEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		tEmployee.setStatus(Cons.CustomerStatus.NORMAL);
		/*Page<TEmployee> page = tEmployeeService.findPage(new Page<TEmployee>(request, response), tEmployee);  ----old*/
		Page<TEmployee> page = tEmployeeService.findEmployeePage(new Page<TEmployee>(request, response), tEmployee);
		model.addAttribute("page", page);
		model.addAttribute("tEmployee",tEmployee);
		model.addAttribute("goal_customer","customer");
		return "modules/employee/tEmployeeList";
	}

	@RequiresPermissions("employee:tEmployee:view")
	@RequestMapping(value = "listAll")
	public String listAll(TEmployee tEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		tEmployee.setOrganId(UserUtils.getUser().getCompany().getId());
		Page<TEmployee> page = tEmployeeService.findPage(new Page<TEmployee>(request, response), tEmployee); 
		model.addAttribute("page", page);
		model.addAttribute("tEmployee", tEmployee);
		return "modules/employee/tEmployeeListAll";
	}

	@RequiresPermissions("employee:tEmployee:view")
	@RequestMapping(value = "form")
	public String form(TEmployee tEmployee, Model model) {
		if (StringUtils.isBlank(tEmployee.getId())){
			//新增是设置一个临时id, 以"new_"开头表示
			tEmployee.setId("new_"+IdGen.uuid());
		}
		FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
		/** 
		 * Start By Chenh 2016-08-24 Start 
		 * Bug#2775 个人客户新增找不到模板
		 **/
//		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		/** Old*******************************/
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_TEMPLOYEE_FORM_ID);
		/** 
		 * Update By Chenh 2016-08-24 End 
		 **/
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tEmployee);
		
		TSpouse   spouse = new TSpouse();
		if(!tEmployee.getId().startsWith("new_")){//不以new开头，说明是修改，根据客户信息查出其配偶信息主键id
			Record id  = tEmployeeService.getSpouseId(tEmployee.getId());
			if(id!=null&&!"".equals(id)){
				 spouse= tSpouseService.get(id.getStr("id"));
			}
		}
		
		model.addAttribute("spouse", spouse);
		return "modules/employee/tEmployeeForm";
	}

	@RequiresPermissions("employee:tEmployee:view")
	@RequestMapping(value = "view")
	public String view(TEmployee tEmployee, Model model) {
		tEmployee = tEmployeeService.get(tEmployee);
		model.addAttribute("tEmployee", tEmployee);
		return "modules/wind/creditchecking/toViewPersion";
	}

	/**
	 * 
	 * @param tEmployee
	 * @param spouse
	 * @param model
	 * @param redirectAttributes
	 * @param response
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 * @throws ParseException
	 * @编辑  2017-02-06添加判断同一个租户该用户是否已经存在
	 */
	@RequiresPermissions("employee:tEmployee:edit")
	@RequestMapping(value = "save")
	public String save(TEmployee tEmployee,TSpouse spouse, Model model, RedirectAttributes redirectAttributes,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException, JSONException, ParseException {
		if (!beanValidator(model, tEmployee)){
			return form(tEmployee, model);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//判断同一个租户该用户是否已经存在
		//if(StringUtils.isBlank(tEmployee.getId()) || tEmployee.getId().startsWith("new_")){
			if(StringUtils.isNotBlank(tEmployee.getName()) && StringUtils.isNotBlank(tEmployee.getCardNum())){
				//User user = UserUtils.getUser();
				//System.out.println("UserUtils.getUser().getCompany()="+user.getCompany().getId());
				TEmployee tmp = new TEmployee();
				tmp.setId(tEmployee.getId());
				//tmp.setName(tEmployee.getName());
				tmp.setCardNum(tEmployee.getCardNum());
				tmp.setOrganId(UserUtils.getUser().getCompany().getId());
				List<TEmployee> list = tEmployeeService.getByCondition(tmp);
				
				if(list.size()>0){// && (StringUtils.isBlank(tEmployee.getId()) || tEmployee.getId().startsWith("new_"))
					model.addAttribute("message", "该用户已经存在，请确认是否已经是公司客户或填写错误！");
					return form(tEmployee, model);
				}
				//else if(list.size()>1){
				//	model.addAttribute("message", "该身份证号码已经存在,请确保是否填写正确！");
				//	return form(tEmployee, model);
				//}
			}else{
				model.addAttribute("message", "姓名、身份证号码不能为空！");
				return form(tEmployee, model);
			}
		//}
		Boolean sta=tLoanContractService.checkWorkStatu(tEmployee);//判断当前用户是否有业务存在
		if(sta){
			String json_str = request.getParameter("pouseName");//new String().getBytes("ISO-8859-1"),"UTF-8");
			JSONObject json = new JSONObject(json_str);
			 JSONArray ja = json.getJSONArray("spouse");
			 for (int i = 0; i < ja.length(); i++) {
		            spouse.setPouseName(ja.getJSONObject(0).optString("pouseName"));
		            spouse.setPouseCardNum(ja.getJSONObject(0).optString("pouseCardNum"));
		            spouse.setPouseEducation(ja.getJSONObject(0).optString("pouseEducation"));
		            spouse.setPouseMonthIncome(ja.getJSONObject(0).optString("pouseMonthIncome"));
		            spouse.setCardType(ja.getJSONObject(0).optString("cardType"));
		            spouse.setUnits(ja.getJSONObject(0).optString("units"));
		            spouse.setUnitsPhone(ja.getJSONObject(0).optString("unitsPhone"));
		            spouse.setUnitsAddress(ja.getJSONObject(0).optString("unitsAddress"));
		            spouse.setPosition(ja.getJSONObject(0).optString("position"));
		            spouse.setPoliticalStatus(ja.getJSONObject(0).optString("politicalStatus"));
		            spouse.setPhone(ja.getJSONObject(0).optString("phone"));
		            spouse.setAddress(ja.getJSONObject(0).optString("address"));
		            if(StringUtils.isNotBlank(ja.getJSONObject(0).optString("birthday"))){
		            	spouse.setBirthday(sdf.parse(ja.getJSONObject(0).optString("birthday")));
		            }
		            if(StringUtils.isNotBlank(ja.getJSONObject(0).optString("liveTime"))){
		            	spouse.setLiveTime(sdf.parse(ja.getJSONObject(0).optString("liveTime")));
		            }
		            if(StringUtils.isNotBlank(ja.getJSONObject(0).optString("unitTime"))){
		            	spouse.setUnitTime(sdf.parse(ja.getJSONObject(0).optString("unitTime")));
		            }
		            spouse.setIndustry(ja.getJSONObject(0).optString("industry"));
		            spouse.setUnitNature(ja.getJSONObject(0).optString("unitNature"));
		            spouse.setUnitSize(ja.getJSONObject(0).optString("unitSize"));
		            spouse.setPolitical(ja.getJSONObject(0).optString("political"));
		      } 
			User currentUser = UserUtils.getUser();//当前登录人
			tEmployee.setOrganId(currentUser.getOffice().getParent().getId()); 
			
		
			tEmployee.setMate(spouse);
			tEmployee.setScanFlag("0");
			tEmployeeService.save(tEmployee);
			addMessage(redirectAttributes, "保存客户个人成功");
		}else{
			addMessage(redirectAttributes, "客户已办理业务，禁止修改基本信息！");
		}
		
		return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/?repage";
	}
	
	@RequiresPermissions("employee:tEmployee:edit")
	@RequestMapping(value = "delete")
	public String delete(TEmployee tEmployee, RedirectAttributes redirectAttributes) {
		tEmployee.setScanFlag("0");
		tEmployeeService.delete(tEmployee);
		addMessage(redirectAttributes, "删除客户个人成功");
		return "redirect:"+Global.getAdminPath()+"/employee/tEmployee/?repage";
	}

}