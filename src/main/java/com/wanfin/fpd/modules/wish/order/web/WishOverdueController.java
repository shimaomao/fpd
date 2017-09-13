/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanfin.fpd.modules.wish.order.entity.WishOverdue;
import com.wanfin.fpd.modules.wish.order.service.WishOverdueService;
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


/**
 * 逾期金额Controller
 * @author cjp
 * @version 2017-08-22
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wish/order/wishOverdue")
public class WishOverdueController extends BaseController {

	@Autowired
	private WishOverdueService wishOverdueService;
	
	@ModelAttribute
	public WishOverdue get(@RequestParam(required=false) String id) {
		WishOverdue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wishOverdueService.get(id);
		}
		if (entity == null){
			entity = new WishOverdue();
		}
		return entity;
	}
	
	@RequiresPermissions("wish:order:wishOverdue:view")
	@RequestMapping(value = {"list", ""})
	public String list(WishOverdue wishOverdue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WishOverdue> page = wishOverdueService.findPage(new Page<WishOverdue>(request, response), wishOverdue); 
		model.addAttribute("page", page);
		model.addAttribute("wishOverdue", wishOverdue);
		return "modules/wish/order/wishOverdueList";
	}

	@RequiresPermissions("wish:order:wishOverdue:view")
	@RequestMapping(value = "form")
	public String form(WishOverdue wishOverdue, Model model) {
		model.addAttribute("wishOverdue", wishOverdue);
		return "modules/wish/order/wishOverdueForm";
	}

	@RequiresPermissions("wish:order:wishOverdue:edit")
	@RequestMapping(value = "save")
	public String save(WishOverdue wishOverdue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wishOverdue)){
			return form(wishOverdue, model);
		}
		wishOverdueService.save(wishOverdue);
		addMessage(redirectAttributes, "保存逾期金额成功");
		return "redirect:"+Global.getAdminPath()+"/wish/order/wishOverdue/?repage";
	}
	
	@RequiresPermissions("wish:order:wishOverdue:edit")
	@RequestMapping(value = "delete")
	public String delete(WishOverdue wishOverdue, RedirectAttributes redirectAttributes) {
		wishOverdueService.delete(wishOverdue);
		addMessage(redirectAttributes, "删除逾期金额成功");
		return "redirect:"+Global.getAdminPath()+"/wish/order/wishOverdue/?repage";
	}

}