/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.web;

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
import com.wanfin.fpd.modules.wish.merchant.entity.WishShop;
import com.wanfin.fpd.modules.wish.merchant.service.WishShopService;

/**
 * 商户店铺信息Controller
 * @author cjp
 * @version 2017-07-11
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wish.merchant/wishShop")
public class WishShopController extends BaseController {

	@Autowired
	private WishShopService wishShopService;
	
	@ModelAttribute
	public WishShop get(@RequestParam(required=false) String id) {
		WishShop entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wishShopService.get(id);
		}
		if (entity == null){
			entity = new WishShop();
		}
		return entity;
	}
	
	@RequiresPermissions("wish.merchant:wishShop:view")
	@RequestMapping(value = {"list", ""})
	public String list(WishShop wishShop, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WishShop> page = wishShopService.findPage(new Page<WishShop>(request, response), wishShop); 
		model.addAttribute("page", page);
		model.addAttribute("wishShop", wishShop);
		return "modules/wish.merchant/wishShopList";
	}

	@RequiresPermissions("wish.merchant:wishShop:view")
	@RequestMapping(value = "form")
	public String form(WishShop wishShop, Model model) {
		model.addAttribute("wishShop", wishShop);
		return "modules/wish.merchant/wishShopForm";
	}

	@RequiresPermissions("wish.merchant:wishShop:edit")
	@RequestMapping(value = "save")
	public String save(WishShop wishShop, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wishShop)){
			return form(wishShop, model);
		}
		wishShopService.save(wishShop);
		addMessage(redirectAttributes, "保存商户店铺信息成功");
		return "redirect:"+Global.getAdminPath()+"/wish.merchant/wishShop/?repage";
	}
	
	@RequiresPermissions("wish.merchant:wishShop:edit")
	@RequestMapping(value = "delete")
	public String delete(WishShop wishShop, RedirectAttributes redirectAttributes) {
		wishShopService.delete(wishShop);
		addMessage(redirectAttributes, "删除商户店铺信息成功");
		return "redirect:"+Global.getAdminPath()+"/wish.merchant/wishShop/?repage";
	}

}