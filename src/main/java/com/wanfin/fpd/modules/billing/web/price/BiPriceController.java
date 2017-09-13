/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.web.price;

import java.util.List;

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

import com.google.common.collect.Lists;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.billing.BillingEngine;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;

/**
 * 收费单价Controller
 * @author chenh
 * @version 2016-07-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/billing/price/biPrice")
public class BiPriceController extends BaseController {

	@Autowired
	private BillingEngine engine;
	
	@ModelAttribute
	public BiPrice get(@RequestParam(required=false) String id) {
		BiPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = engine.price().get(id);
		}
		if (entity == null){
			entity = new BiPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("billing:price:biPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(BiPrice biPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
//		biPrice.getPage().setPageSize(100000);
//		Page<BiPrice> page = engine.price().findPage(new Page<BiPrice>(request, response), biPrice); 
//		List<BiElement> elements = engine.element().findAllList(new BiElement()); 
//		
//		List<BiPrice> dhList = Lists.newArrayList();
//		BiPrice.sortList(dhList, page.getList(), BiPrice.getRootId(), true);
//		page.setList(dhList);
//		
//		model.addAttribute("elements", elements);
//		model.addAttribute("page", page);
//		model.addAttribute("biPrice", biPrice);
 
		List<BiPrice> lists = engine.price().findList(biPrice); 
		List<BiElement> elements = engine.element().findAllList(new BiElement()); 
		
		List<BiPrice> dhList = Lists.newArrayList();
		BiPrice.sortList(dhList, lists, BiPrice.getRootId(), true);
		
		model.addAttribute("elements", elements);
		model.addAttribute("lists", dhList);
		model.addAttribute("biPrice", biPrice);
		return "modules/billing/price/biPriceList";
	}

	@RequiresPermissions("billing:price:biPrice:view")
	@RequestMapping(value = "form")
	public String form(BiPrice biPrice, Model model) {
		List<BiElement> elements = engine.element().findAllList(new BiElement()); 
		
		model.addAttribute("elements", elements);
		model.addAttribute("biPrice", biPrice);
		return "modules/billing/price/biPriceForm";
	}

	@RequiresPermissions("billing:price:biPrice:edit")
	@RequestMapping(value = "save")
	public String save(BiPrice biPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, biPrice)){
			return form(biPrice, model);
		}
		engine.price().save(biPrice);
		addMessage(redirectAttributes, "保存收费单价成功");
		return "redirect:"+Global.getAdminPath()+"/billing/price/biPrice/?repage";
	}
	
	@RequiresPermissions("billing:price:biPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(BiPrice biPrice, RedirectAttributes redirectAttributes) {
		engine.price().delete(biPrice);
		addMessage(redirectAttributes, "删除收费单价成功");
		return "redirect:"+Global.getAdminPath()+"/billing/price/biPrice/?repage";
	}

}