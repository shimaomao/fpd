/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerrelevan.web;

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
import com.wanfin.fpd.modules.customerrelevan.entity.TCustomerRelevan;
import com.wanfin.fpd.modules.customerrelevan.service.TCustomerRelevanService;

/**
 * 客户关联Controller
 * @author lx
 * @version 2016-08-12
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/customerrelevan/tCustomerRelevan")
public class TCustomerRelevanController extends BaseController {

	@Autowired
	private TCustomerRelevanService tCustomerRelevanService;
	
	@ModelAttribute
	public TCustomerRelevan get(@RequestParam(required=false) String id) {
		TCustomerRelevan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerRelevanService.get(id);
		}
		if (entity == null){
			entity = new TCustomerRelevan();
		}
		return entity;
	}
	
	@RequiresPermissions("customerrelevan:tCustomerRelevan:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomerRelevan tCustomerRelevan, HttpServletRequest request, HttpServletResponse response, Model model) {
		String strUrl = "";
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCustomerRelevan.setCompanyId(companyId);
			strUrl = "modules/customerrelevan/tCustomerRelevanList";
		}
		
		String employeeId = request.getParameter("employeeId");
		if(employeeId!=null&&!"".equals(employeeId)){
			tCustomerRelevan.setEmployeeId(employeeId);
			strUrl = "modules/customerrelevan/tCustomerRelevanListEmp";
		}
		
		Page<TCustomerRelevan> page = tCustomerRelevanService.findPage(new Page<TCustomerRelevan>(request, response), tCustomerRelevan); 
		model.addAttribute("page", page);
		model.addAttribute("tCustomerRelevan", tCustomerRelevan);
		
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return strUrl;
	}

	@RequiresPermissions("customerrelevan:tCustomerRelevan:view")
	@RequestMapping(value = "form")
	public String form(TCustomerRelevan tCustomerRelevan, Model model,HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCustomerRelevan.setCompanyId(companyId);
		}
		String employeeId = request.getParameter("employeeId");
		if(employeeId!=null&&!"".equals(employeeId)){
			tCustomerRelevan.setEmployeeId(employeeId);
		}
		model.addAttribute("tCustomerRelevan", tCustomerRelevan);
		return "modules/customerrelevan/tCustomerRelevanForm";
	}

	@RequiresPermissions("customerrelevan:tCustomerRelevan:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerRelevan tCustomerRelevan, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, tCustomerRelevan)){
			return form(tCustomerRelevan, model,request);
		}
		tCustomerRelevanService.save(tCustomerRelevan);
		addMessage(redirectAttributes, "保存客户关联成功");
		String strUrl = "";
		if(tCustomerRelevan.getCompanyId()!=null&&!"".equals(tCustomerRelevan.getCompanyId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerrelevan/tCustomerRelevan/?repage&isClose=1&companyId="+tCustomerRelevan.getCompanyId();
		}
		if(tCustomerRelevan.getEmployeeId()!=null&&!"".equals(tCustomerRelevan.getEmployeeId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerrelevan/tCustomerRelevan/?repage&isClose=1&employeeId="+tCustomerRelevan.getEmployeeId();
		}
		return strUrl;
	}
	
	@RequiresPermissions("customerrelevan:tCustomerRelevan:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomerRelevan tCustomerRelevan, RedirectAttributes redirectAttributes) {
		tCustomerRelevanService.delete(tCustomerRelevan);
		addMessage(redirectAttributes, "删除客户关联成功");
		String strUrl = "";
		if(tCustomerRelevan.getCompanyId()!=null&&!"".equals(tCustomerRelevan.getCompanyId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerrelevan/tCustomerRelevan/?repage&companyId="+tCustomerRelevan.getCompanyId();
		}
		if(tCustomerRelevan.getEmployeeId()!=null&&!"".equals(tCustomerRelevan.getEmployeeId())){
			strUrl = "redirect:"+Global.getAdminPath()+"/customerrelevan/tCustomerRelevan/?repage&employeeId="+tCustomerRelevan.getEmployeeId();
		}
		return strUrl;
	}

}