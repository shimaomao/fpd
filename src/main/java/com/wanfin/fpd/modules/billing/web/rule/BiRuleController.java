/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.web.rule;

import java.util.ArrayList;
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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.billing.BiTime;
import com.wanfin.fpd.core.billing.BillingEngine;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;

/**
 * 收费规则Controller
 * @author chenh
 * @version 2016-07-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/billing/rule/biRule")
public class BiRuleController extends BaseController {
	@Autowired
	private BillingEngine engine;
	
	@ModelAttribute
	public BiRule get(@RequestParam(required=false) String id) {
		BiRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = engine.rule().get(id);
		}
		if (entity == null){
			entity = new BiRule();
		}
		return entity;
	}
	
	@RequiresPermissions("billing:rule:biRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(BiRule biRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BiRule> page = engine.rule().findPage(new Page<BiRule>(request, response), biRule); 
		List<BiPrice> prices = engine.price().findAllList(new BiPrice());
		
		model.addAttribute("prices", prices);
		model.addAttribute("page", page);
		model.addAttribute("biRule", biRule);
		return "modules/billing/rule/biRuleList";
	}

	@RequiresPermissions("billing:rule:biRule:view")
	@RequestMapping(value = "form")
	public String form(BiRule biRule, Model model) {
		List<BiPrice> prices = new ArrayList<BiPrice>();
		if((biRule.getPrice() != null) && (biRule.getPrice().getType() != null)){
			prices = engine.price().findList(biRule.getPrice());
		}
		model.addAttribute("units", BiTime.values());
		model.addAttribute("prices", prices);
		model.addAttribute("biRule", biRule);
		return "modules/billing/rule/biRuleForm";
	}

	@RequiresPermissions("billing:rule:biRule:edit")
	@RequestMapping(value = "save")
	public String save(BiRule biRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, biRule)){
			return form(biRule, model);
		}
		engine.rule().save(biRule);
		addMessage(redirectAttributes, "保存收费规则成功");
		return "redirect:"+Global.getAdminPath()+"/billing/rule/biRule/?repage";
	}
	
	@RequiresPermissions("billing:rule:biRule:edit")
	@RequestMapping(value = "delete")
	public String delete(BiRule biRule, RedirectAttributes redirectAttributes) {
		engine.rule().delete(biRule);
		addMessage(redirectAttributes, "删除收费规则成功");
		return "redirect:"+Global.getAdminPath()+"/billing/rule/biRule/?repage";
	}
}