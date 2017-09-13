/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.web.element;

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
import com.wanfin.fpd.modules.billing.service.element.BiElementService;

/**
 * 收费项Controller
 * @author chenh
 * @version 2016-07-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/billing/element/biElement")
public class BiElementController extends BaseController {

	@Autowired
	private BillingEngine engine;
	
	@Autowired
	private BiElementService biElementService;
	
	@ModelAttribute
	public BiElement get(@RequestParam(required=false) String id) {
		BiElement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = biElementService.get(id);
		}
		if (entity == null){
			entity = new BiElement();
		}
		return entity;
	}
	
	@RequiresPermissions("billing:element:biElement:view")
	@RequestMapping(value = {"list", ""})
	public String list(BiElement biElement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BiElement> page = new Page<BiElement>(request, response);
		page.setPageSize(1000);
		page = biElementService.findPage(page, biElement); 
		

		List<BiElement> dhList = Lists.newArrayList();
		BiElement.sortList(dhList, page.getList(), BiElement.getRootId(), true);
		
		page.setList(dhList);
		model.addAttribute("page", page);
		model.addAttribute("biElement", biElement);
		return "modules/billing/element/biElementList";
	}

	@RequiresPermissions("billing:element:biElement:view")
	@RequestMapping(value = "form")
	public String form(BiElement biElement, Model model) {
		BiElement entity = new BiElement();
		/*entity.setElement(new BiElement(BiElement.getRootId()));*/
		List<BiElement> elements = engine.element().findList(entity); 
		
		model.addAttribute("elements", elements);
		model.addAttribute("biElement", biElement);
		return "modules/billing/element/biElementForm";
	}

	@RequiresPermissions("billing:element:biElement:edit")
	@RequestMapping(value = "save")
	public String save(BiElement biElement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, biElement)){
			return form(biElement, model);
		}
		biElementService.save(biElement);
		addMessage(redirectAttributes, "保存收费项成功");
		return "redirect:"+Global.getAdminPath()+"/billing/element/biElement/?repage";
	}
	
	@RequiresPermissions("billing:element:biElement:edit")
	@RequestMapping(value = "delete")
	public String delete(BiElement biElement, RedirectAttributes redirectAttributes) {
		biElementService.delete(biElement);
		addMessage(redirectAttributes, "删除收费项成功");
		return "redirect:"+Global.getAdminPath()+"/billing/element/biElement/?repage";
	}

}