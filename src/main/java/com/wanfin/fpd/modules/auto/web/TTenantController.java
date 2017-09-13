/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.web;

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
import com.wanfin.fpd.modules.auto.entity.TTenant;
import com.wanfin.fpd.modules.auto.service.TTenantService;

/**
 * 租户Controller
 * @author Chenh
 * @version 2016-09-08
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/auto/tTenant")
public class TTenantController extends BaseController {

	@Autowired
	private TTenantService tTenantService;
	
	@ModelAttribute
	public TTenant get(@RequestParam(required=false) String id) {
		TTenant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tTenantService.get(id);
		}
		if (entity == null){
			entity = new TTenant();
		}
		return entity;
	}
	
	@RequiresPermissions("auto:tTenant:view")
	@RequestMapping(value = {"list", ""})
	public String list(TTenant tTenant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TTenant> page = tTenantService.findPage(new Page<TTenant>(request, response), tTenant); 
		model.addAttribute("page", page);
		model.addAttribute("tTenant", tTenant);
		return "modules/auto/tTenantList";
	}

	@RequiresPermissions("auto:tTenant:view")
	@RequestMapping(value = "form")
	public String form(TTenant tTenant, Model model) {
		model.addAttribute("tTenant", tTenant);
		return "modules/auto/tTenantForm";
	}

	@RequiresPermissions("auto:tTenant:edit")
	@RequestMapping(value = "save")
	public String save(TTenant tTenant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tTenant)){
			return form(tTenant, model);
		}
		tTenantService.save(tTenant);
		addMessage(redirectAttributes, "保存租户数据导入成功");
		return "redirect:"+Global.getAdminPath()+"/auto/tTenant/?repage";
	}
	
	@RequiresPermissions("auto:tTenant:edit")
	@RequestMapping(value = "delete")
	public String delete(TTenant tTenant, RedirectAttributes redirectAttributes) {
		tTenantService.delete(tTenant);
		addMessage(redirectAttributes, "删除租户数据导入成功");
		return "redirect:"+Global.getAdminPath()+"/auto/tTenant/?repage";
	}

}