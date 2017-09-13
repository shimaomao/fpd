/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

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
import com.wanfin.fpd.modules.sys.entity.Family;
import com.wanfin.fpd.modules.sys.service.FamilyService;

/**
 * 从业人员家庭成员Controller
 * @author kewenxiu
 * @version 2017-03-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/family")
public class FamilyController extends BaseController {

	@Autowired
	private FamilyService familyService;
	
	@ModelAttribute
	public Family get(@RequestParam(required=false) String id) {
		Family entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = familyService.get(id);
		}
		if (entity == null){
			entity = new Family();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:family:view")
	@RequestMapping(value = {"list", ""})
	public String list(Family family, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Family> page = familyService.findPage(new Page<Family>(request, response), family); 
		model.addAttribute("page", page);
		model.addAttribute("family", family);
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return "modules/sys/familyList";
	}

	@RequiresPermissions("sys:family:view")
	@RequestMapping(value = "form")
	public String form(Family family, Model model) {
		model.addAttribute("family", family);
		return "modules/sys/familyForm";
	}

	@RequiresPermissions("sys:family:edit")
	@RequestMapping(value = "save")
	public String save(Family family, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, family)){
			return form(family, model);
		}
		familyService.save(family);
		addMessage(redirectAttributes, "保存从业人员家庭成员成功");
		return "redirect:"+Global.getAdminPath()+"/sys/family/?repage&isClose=1&employId="+family.getEmployId();
	}
	
	@RequiresPermissions("sys:family:edit")
	@RequestMapping(value = "delete")
	public String delete(Family family, RedirectAttributes redirectAttributes) {
		familyService.delete(family);
		addMessage(redirectAttributes, "删除从业人员家庭成员成功");
		return "redirect:"+Global.getAdminPath()+"/sys/family/?repage&employId="+family.getEmployId();
	}

}