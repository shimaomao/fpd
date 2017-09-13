/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.web;

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
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;
import com.wanfin.fpd.modules.wish.order.service.TReturnedMsgService;
import com.mangofactory.swagger.annotations.ApiIgnore;


/**
 * 扣款明细表Controller
 * @author lzj
 * @version 2017-08-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wish/tReturnedMsg")
public class TReturnedMsgController extends BaseController {

	@Autowired
	private TReturnedMsgService tReturnedMsgService;
	
	@ModelAttribute
	public TReturnedMsg get(@RequestParam(required=false) String id) {
		TReturnedMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tReturnedMsgService.get(id);
		}
		if (entity == null){
			entity = new TReturnedMsg();
		}
		return entity;
	}
	
	@RequiresPermissions("wish:tReturnedMsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(TReturnedMsg tReturnedMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TReturnedMsg> page = tReturnedMsgService.findPage(new Page<TReturnedMsg>(request, response), tReturnedMsg); 
		model.addAttribute("page", page);
		model.addAttribute("tReturnedMsg", tReturnedMsg);
		return "modules/wish/tReturnedMsgList";
	}

	@RequiresPermissions("wish:tReturnedMsg:view")
	@RequestMapping(value = "form")
	public String form(TReturnedMsg tReturnedMsg, Model model) {
		model.addAttribute("tReturnedMsg", tReturnedMsg);
		return "modules/wish/tReturnedMsgForm";
	}

	@RequiresPermissions("wish:tReturnedMsg:edit")
	@RequestMapping(value = "save")
	public String save(TReturnedMsg tReturnedMsg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tReturnedMsg)){
			return form(tReturnedMsg, model);
		}
		tReturnedMsgService.save(tReturnedMsg);
		addMessage(redirectAttributes, "保存扣款明细表成功");
		return "redirect:"+Global.getAdminPath()+"/wish/tReturnedMsg/?repage";
	}
	
	@RequiresPermissions("wish:tReturnedMsg:edit")
	@RequestMapping(value = "delete")
	public String delete(TReturnedMsg tReturnedMsg, RedirectAttributes redirectAttributes) {
		tReturnedMsgService.delete(tReturnedMsg);
		addMessage(redirectAttributes, "删除扣款明细表成功");
		return "redirect:"+Global.getAdminPath()+"/wish/tReturnedMsg/?repage";
	}

}