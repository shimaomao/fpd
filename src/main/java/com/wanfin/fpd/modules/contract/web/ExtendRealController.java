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
import com.wanfin.fpd.modules.contract.entity.ExtendReal;
import com.wanfin.fpd.modules.contract.service.ExtendRealService;

/**
 * 展期真实还款记录Controller
 * @author zzm
 * @version 2016-04-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/extendReal")
public class ExtendRealController extends BaseController {

	@Autowired
	private ExtendRealService extendRealService;
	
	@ModelAttribute
	public ExtendReal get(@RequestParam(required=false) String id) {
		ExtendReal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = extendRealService.get(id);
		}
		if (entity == null){
			entity = new ExtendReal();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:extendReal:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExtendReal extendReal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExtendReal> page = extendRealService.findPage(new Page<ExtendReal>(request, response), extendReal); 
		model.addAttribute("page", page);
		model.addAttribute("extendReal", extendReal);
		return "modules/contract/extendRealList";
	}

	@RequiresPermissions("contract:extendReal:view")
	@RequestMapping(value = "form")
	public String form(ExtendReal extendReal, Model model) {
		model.addAttribute("extendReal", extendReal);
		return "modules/contract/extendRealForm";
	}

	@RequiresPermissions("contract:extendReal:edit")
	@RequestMapping(value = "save")
	public String save(ExtendReal extendReal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, extendReal)){
			return form(extendReal, model);
		}
		extendRealService.save(extendReal);
		addMessage(redirectAttributes, "保存还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendReal/?repage";
	}
	
	@RequiresPermissions("contract:extendReal:edit")
	@RequestMapping(value = "delete")
	public String delete(ExtendReal extendReal, RedirectAttributes redirectAttributes) {
		extendRealService.delete(extendReal);
		addMessage(redirectAttributes, "删除还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendReal/?repage";
	}

}