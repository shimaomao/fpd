/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerintent.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.customerintent.entity.TCustomerIntent;
import com.wanfin.fpd.modules.customerintent.service.TCustomerIntentService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;

/**
 * customerintentController
 * @author lx
 * @version 2016-08-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/customerintent/tCustomerIntent")
public class TCustomerIntentController extends BaseController {

	@Autowired
	private TCustomerIntentService tCustomerIntentService;
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired 
	private TEmployeeService tEmployeeService;
	
	@ModelAttribute
	public TCustomerIntent get(@RequestParam(required=false) String id) {
		TCustomerIntent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerIntentService.get(id);
		}
		if (entity == null){
			entity = new TCustomerIntent();
		}
		return entity;
	}
	
	@RequiresPermissions("customerintent:tCustomerIntent:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomerIntent tCustomerIntent, HttpServletRequest request, HttpServletResponse response, Model model) {
		String strUrl = "";
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCustomerIntent.setCompanyId(companyId);
			strUrl = "modules/customerintent/tCustomerIntentList";
		}
		String employeeId = request.getParameter("employeeId");
		if(employeeId!=null&&!"".equals(employeeId)){
			tCustomerIntent.setEmployeeId(employeeId);
			strUrl = "modules/customerintent/tCustomerIntentListEmp";
		}
		
		Page<TCustomerIntent> page = tCustomerIntentService.findPage(new Page<TCustomerIntent>(request, response), tCustomerIntent); 
		model.addAttribute("page", page);
		model.addAttribute("tCustomerIntent", tCustomerIntent);
		
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return strUrl;
	}

	@RequiresPermissions("customerintent:tCustomerIntent:view")
	@RequestMapping(value = "form")
	public String form(TCustomerIntent tCustomerIntent, Model model,HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCustomerIntent.setCompanyId(companyId);
             if(tCustomerIntent.getId()==null){//代表是新增
            	TCompany tcompany = tCompanyService.get(companyId);
     			model.addAttribute("name", tcompany.getName());
     			model.addAttribute("Mobile", tcompany.getSuretyMobile());
             }
		}
		
		String employeeId = request.getParameter("employeeId");
		if(employeeId!=null&&!"".equals(employeeId)){
			tCustomerIntent.setEmployeeId(employeeId);
             if(tCustomerIntent.getId()==null){//代表是新增
            	 TEmployee employee = tEmployeeService.get(employeeId);
            	 model.addAttribute("name", employee.getName());
      			model.addAttribute("Mobile", employee.getMobile());
             }
		}
		model.addAttribute("tCustomerIntent", tCustomerIntent);
		return "modules/customerintent/tCustomerIntentForm";
	}

	@RequiresPermissions("customerintent:tCustomerIntent:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerIntent tCustomerIntent, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, tCustomerIntent)){
			return form(tCustomerIntent, model,request);
		}
		tCustomerIntentService.save(tCustomerIntent);
		addMessage(redirectAttributes, "保存意向调查成功");
		String strUrl = "";
		if(tCustomerIntent.getCompanyId()!=null&&!"".equals(tCustomerIntent.getCompanyId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerintent/tCustomerIntent/?repage&isClose=1&companyId="+tCustomerIntent.getCompanyId();
		}
		if(tCustomerIntent.getEmployeeId()!=null&&!"".equals(tCustomerIntent.getEmployeeId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerintent/tCustomerIntent/?repage&isClose=1&employeeId="+tCustomerIntent.getEmployeeId();
		}
		return strUrl;
	}
	
	@RequiresPermissions("customerintent:tCustomerIntent:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomerIntent tCustomerIntent, RedirectAttributes redirectAttributes) {
		tCustomerIntentService.delete(tCustomerIntent);
		addMessage(redirectAttributes, "删除意向调查成功");
		String strUrl = "";
		if(tCustomerIntent.getCompanyId()!=null&&!"".equals(tCustomerIntent.getCompanyId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerintent/tCustomerIntent/?repage&companyId="+tCustomerIntent.getCompanyId();
		}
		if(tCustomerIntent.getEmployeeId()!=null&&!"".equals(tCustomerIntent.getEmployeeId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerintent/tCustomerIntent/?repage&employeeId="+tCustomerIntent.getEmployeeId();
		}
		return strUrl;
	}

}