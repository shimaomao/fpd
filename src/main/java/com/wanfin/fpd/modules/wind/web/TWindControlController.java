/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.web;

import java.util.Date;

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
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wind.entity.TWindControl;
import com.wanfin.fpd.modules.wind.service.TWindControlService;

/**
 * 风控模型Controller
 * @author lx
 * @version 2016-05-03
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wind/tWindControl")
public class TWindControlController extends BaseController {

	@Autowired
	private TWindControlService tWindControlService;
	
	@ModelAttribute
	public TWindControl get(@RequestParam(required=false) String id) {
		TWindControl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tWindControlService.get(id);
		}
		if (entity == null){
			entity = new TWindControl();
		}
		return entity;
	}
	
	@RequiresPermissions("wind:tWindControl:view")
	@RequestMapping(value = {"list", ""})
	public String list(TWindControl tWindControl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TWindControl> page = tWindControlService.findPage(new Page<TWindControl>(request, response), tWindControl); 
		model.addAttribute("page", page);
		model.addAttribute("tWindControl", tWindControl);
		return "modules/wind/tWindControlList";
	}

	@RequiresPermissions("wind:tWindControl:view")
	@RequestMapping(value = "form")
	public String form(TWindControl tWindControl, Model model) {
		model.addAttribute("tWindControl", tWindControl);
		return "modules/wind/tWindControlForm";
	}

	@RequiresPermissions("wind:tWindControl:edit")
	@RequestMapping(value = "save")
	public String save(TWindControl tWindControl, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		tWindControl.setOrganId(currentUser.getOffice().getParent().getId()); 
		tWindControl.setCreateTime(new Date());
		tWindControl.setUpdateTime(new Date());
		if (!beanValidator(model, tWindControl)){
			return form(tWindControl, model);
		}
		tWindControlService.save(tWindControl);
		addMessage(redirectAttributes, "保存风控模型成功");
		return "redirect:"+Global.getAdminPath()+"/wind/tWindControl/?repage";
	}
	
	@RequiresPermissions("wind:tWindControl:edit")
	@RequestMapping(value = "delete")
	public String delete(TWindControl tWindControl, RedirectAttributes redirectAttributes) {
		tWindControlService.delete(tWindControl);
		addMessage(redirectAttributes, "删除风控模型成功");
		return "redirect:"+Global.getAdminPath()+"/wind/tWindControl/?repage";
	}

}