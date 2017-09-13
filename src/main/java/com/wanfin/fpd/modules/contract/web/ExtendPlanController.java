/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web;

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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.ExtendPlan;
import com.wanfin.fpd.modules.contract.service.ExtendPlanService;

/**
 * 展期还款计划Controller
 * @author zzm
 * @version 2016-04-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/extendPlan")
public class ExtendPlanController extends BaseController {

	@Autowired
	private ExtendPlanService extendPlanService;
	
	@ModelAttribute
	public ExtendPlan get(@RequestParam(required=false) String id) {
		ExtendPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = extendPlanService.get(id);
		}
		if (entity == null){
			entity = new ExtendPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:extendPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExtendPlan extendPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExtendPlan> page = extendPlanService.findPage(new Page<ExtendPlan>(request, response), extendPlan); 
		model.addAttribute("page", page);
		model.addAttribute("extendPlan", extendPlan);
		return "modules/contract/extendPlanList";
	}

	@RequiresPermissions("contract:extendPlan:view")
	@RequestMapping(value = "form")
	public String form(ExtendPlan extendPlan, Model model) {
		model.addAttribute("extendPlan", extendPlan);
		return "modules/contract/extendPlanForm";
	}

	@RequiresPermissions("contract:extendPlan:edit")
	@RequestMapping(value = "save")
	public String save(ExtendPlan extendPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, extendPlan)){
			return form(extendPlan, model);
		}
		extendPlanService.save(extendPlan);
		addMessage(redirectAttributes, "保存还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendPlan/?repage";
	}
	
	@RequiresPermissions("contract:extendPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(ExtendPlan extendPlan, RedirectAttributes redirectAttributes) {
		extendPlanService.delete(extendPlan);
		addMessage(redirectAttributes, "删除还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendPlan/?repage";
	}

}