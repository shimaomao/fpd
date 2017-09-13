/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wauto.web;

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
import com.wanfin.fpd.modules.wauto.entity.WAuto;
import com.wanfin.fpd.modules.wauto.service.WAutoService;

/**
 * 自动业务Controller
 * @author chenh
 * @version 2016-07-28
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wauto/wAuto")
public class WAutoController extends BaseController {

	@Autowired
	private WAutoService wAutoService;
	
	@ModelAttribute
	public WAuto get(@RequestParam(required=false) String id) {
		WAuto entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wAutoService.get(id);
		}
		if (entity == null){
			entity = new WAuto();
		}
		return entity;
	}
	
	@RequiresPermissions("wauto:wAuto:view")
	@RequestMapping(value = {"list", ""})
	public String list(WAuto wAuto, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WAuto> page = wAutoService.findPage(new Page<WAuto>(request, response), wAuto); 
		model.addAttribute("page", page);
		model.addAttribute("wAuto", wAuto);
		return "modules/wauto/wAutoList";
	}

	@RequiresPermissions("wauto:wAuto:view")
	@RequestMapping(value = "form")
	public String form(WAuto wAuto, Model model) {
		model.addAttribute("wAuto", wAuto);
		return "modules/wauto/wAutoForm";
	}

	@RequiresPermissions("wauto:wAuto:edit")
	@RequestMapping(value = "save")
	public String save(WAuto wAuto, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wAuto)){
			return form(wAuto, model);
		}
		wAutoService.save(wAuto);
		addMessage(redirectAttributes, "保存自动业务成功");
		return "redirect:"+Global.getAdminPath()+"/wauto/wAuto/?repage";
	}
	
	@RequiresPermissions("wauto:wAuto:edit")
	@RequestMapping(value = "delete")
	public String delete(WAuto wAuto, RedirectAttributes redirectAttributes) {
		wAutoService.delete(wAuto);
		addMessage(redirectAttributes, "删除自动业务成功");
		return "redirect:"+Global.getAdminPath()+"/wauto/wAuto/?repage";
	}
	
	
}