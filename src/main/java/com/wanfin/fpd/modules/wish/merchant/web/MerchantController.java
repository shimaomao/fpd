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
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;

/**
 * 商户数据信息Controller
 * @author cjp
 * @version 2017-07-03
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wish.merchant/merchant")
public class MerchantController extends BaseController {

	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public Merchant get(@RequestParam(required=false) String id) {
		Merchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantService.get(id);
		}
		if (entity == null){
			entity = new Merchant();
		}
		return entity;
	}
	
	@RequiresPermissions("wish.merchant:merchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Merchant> page = merchantService.findPage(new Page<Merchant>(request, response), merchant); 
		model.addAttribute("page", page);
		model.addAttribute("merchant", merchant);
		return "modules/wish.merchant/merchantList";
	}

	@RequiresPermissions("wish.merchant:merchant:view")
	@RequestMapping(value = "form")
	public String form(Merchant merchant, Model model) {
		model.addAttribute("merchant", merchant);
		return "modules/wish.merchant/merchantForm";
	}

	@RequiresPermissions("wish.merchant:merchant:edit")
	@RequestMapping(value = "save")
	public String save(Merchant merchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchant)){
			return form(merchant, model);
		}
		merchantService.save(merchant);
		addMessage(redirectAttributes, "保存商户数据信息成功");
		return "redirect:"+Global.getAdminPath()+"/wish.merchant/merchant/?repage";
	}
	
	@RequiresPermissions("wish.merchant:merchant:edit")
	@RequestMapping(value = "delete")
	public String delete(Merchant merchant, RedirectAttributes redirectAttributes) {
		merchantService.delete(merchant);
		addMessage(redirectAttributes, "删除商户数据信息成功");
		return "redirect:"+Global.getAdminPath()+"/wish.merchant/merchant/?repage";
	}

}