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
import com.wanfin.fpd.modules.sys.entity.SysUserData;
import com.wanfin.fpd.modules.sys.service.SysUserDataService;

/**
 * 租户初始化数据Controller
 * @author Chenh
 * @version 2016-08-31
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserData")
public class SysUserDataController extends BaseController {

	@Autowired
	private SysUserDataService sysUserDataService;
	
	@ModelAttribute
	public SysUserData get(@RequestParam(required=false) String id) {
		SysUserData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserDataService.get(id);
		}
		if (entity == null){
			entity = new SysUserData();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysUserData:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserData sysUserData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserData> page = sysUserDataService.findPage(new Page<SysUserData>(request, response), sysUserData); 
		model.addAttribute("page", page);
		model.addAttribute("sysUserData", sysUserData);
		return "modules/sys/sysUserDataList";
	}

	@RequiresPermissions("sys:sysUserData:view")
	@RequestMapping(value = "form")
	public String form(SysUserData sysUserData, Model model) {
		model.addAttribute("sysUserData", sysUserData);
		return "modules/sys/sysUserDataForm";
	}

	@RequiresPermissions("sys:sysUserData:edit")
	@RequestMapping(value = "save")
	public String save(SysUserData sysUserData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserData)){
			return form(sysUserData, model);
		}
		sysUserDataService.save(sysUserData);
		addMessage(redirectAttributes, "保存初始化数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserData/?repage";
	}
	
	@RequiresPermissions("sys:sysUserData:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserData sysUserData, RedirectAttributes redirectAttributes) {
		sysUserDataService.delete(sysUserData);
		addMessage(redirectAttributes, "删除初始化数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserData/?repage";
	}

}