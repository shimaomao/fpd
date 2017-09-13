/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.web;

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
import com.wanfin.fpd.modules.sms.entity.TMsgSwitch;
import com.wanfin.fpd.modules.sms.service.TMsgSwitchService;

/**
 * 消息管理Controller
 * @author kewenxiu
 * @version 2017-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sms/tMsgSwitch")
public class TMsgSwitchController extends BaseController {

	@Autowired
	private TMsgSwitchService tMsgSwitchService;
	
	@ModelAttribute
	public TMsgSwitch get(@RequestParam(required=false) String id) {
		TMsgSwitch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tMsgSwitchService.get(id);
		}
		if (entity == null){
			entity = new TMsgSwitch();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:tMsgSwitch:view")
	@RequestMapping(value = {"list", ""})
	public String list(TMsgSwitch tMsgSwitch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TMsgSwitch> page = tMsgSwitchService.findPage(new Page<TMsgSwitch>(request, response), tMsgSwitch); 
		model.addAttribute("page", page);
		model.addAttribute("tMsgSwitch", tMsgSwitch);
		return "modules/sms/tMsgSwitchList";
	}

	@RequiresPermissions("sms:tMsgSwitch:view")
	@RequestMapping(value = "form")
	public String form(TMsgSwitch tMsgSwitch, Model model) {
		model.addAttribute("tMsgSwitch", tMsgSwitch);
		return "modules/sms/tMsgSwitchForm";
	}

	@RequiresPermissions("sms:tMsgSwitch:edit")
	@RequestMapping(value = "save")
	public String save(TMsgSwitch tMsgSwitch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tMsgSwitch)){
			return form(tMsgSwitch, model);
		}
		tMsgSwitchService.save(tMsgSwitch);
		addMessage(redirectAttributes, "保存消息管理成功");
		return "redirect:"+Global.getAdminPath()+"/sms/tMsgSwitch/?repage";
	}
	
	@RequiresPermissions("sms:tMsgSwitch:edit")
	@RequestMapping(value = "delete")
	public String delete(TMsgSwitch tMsgSwitch, RedirectAttributes redirectAttributes) {
		tMsgSwitchService.delete(tMsgSwitch);
		addMessage(redirectAttributes, "删除消息管理成功");
		return "redirect:"+Global.getAdminPath()+"/sms/tMsgSwitch/?repage";
	}

}