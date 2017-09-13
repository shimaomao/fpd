/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.web;

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
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;

/**
 * 违约金，咨询费Controller
 * @author srf
 * @version 2016-04-06
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/receivables/realIncome")
public class RealIncomeController extends BaseController {

	@Autowired
	private RealIncomeService realIncomeService;
	
	@ModelAttribute
	public RealIncome get(@RequestParam(required=false) String id) {
		RealIncome entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = realIncomeService.get(id);
		}
		if (entity == null){
			entity = new RealIncome();
		}
		return entity;
	}
	
	@RequiresPermissions("receivables:realIncome:view")
	@RequestMapping(value = {"list", ""})
	public String list(RealIncome realIncome, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RealIncome> page = realIncomeService.findPage(new Page<RealIncome>(request, response), realIncome); 
		model.addAttribute("page", page);
		model.addAttribute("realIncome", realIncome);
		return "modules/receivables/realIncomeList";
	}

	@RequiresPermissions("receivables:realIncome:view")
	@RequestMapping(value = "form")
	public String form(RealIncome realIncome, Model model) {
		model.addAttribute("realIncome", realIncome);
		return "modules/receivables/realIncomeForm";
	}

	@RequiresPermissions("receivables:realIncome:edit")
	@RequestMapping(value = "save")
	public String save(RealIncome realIncome, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, realIncome)){
			return form(realIncome, model);
		}
		realIncomeService.save(realIncome);
		addMessage(redirectAttributes, "保存违约金，咨询费成功");
		return "redirect:"+Global.getAdminPath()+"/receivables/realIncome/?repage";
	}
	
	@RequiresPermissions("receivables:realIncome:edit")
	@RequestMapping(value = "delete")
	public String delete(RealIncome realIncome, RedirectAttributes redirectAttributes) {
		realIncomeService.delete(realIncome);
		addMessage(redirectAttributes, "删除违约金，咨询费成功");
		return "redirect:"+Global.getAdminPath()+"/receivables/realIncome/?repage";
	}

}