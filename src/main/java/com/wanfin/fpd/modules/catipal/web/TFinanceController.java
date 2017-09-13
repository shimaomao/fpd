/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.web;

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
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.entity.TFinance;
import com.wanfin.fpd.modules.catipal.service.TCapitalService;
import com.wanfin.fpd.modules.catipal.service.TFinanceService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 融资列表Controller
 * @author lx
 * @version 2016-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/catipal/tFinance")
public class TFinanceController extends BaseController {

	@Autowired
	private TFinanceService tFinanceService;
	
	@Autowired
	private TCapitalService tCapitalService;
	
	@ModelAttribute
	public TFinance get(@RequestParam(required=false) String id) {
		TFinance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tFinanceService.get(id);
		}
		if (entity == null){
			entity = new TFinance();
		}
		return entity;
	}
	
	@RequiresPermissions("catipal:tFinance:view")
	@RequestMapping(value = {"list", ""})
	public String list(TFinance tFinance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TFinance> page = tFinanceService.findPage(new Page<TFinance>(request, response), tFinance); 
		model.addAttribute("page", page);
		model.addAttribute("tFinance", tFinance);
		return "modules/catipal/tFinanceList";
	}

	@RequiresPermissions("catipal:tFinance:view")
	@RequestMapping(value = "form")
	public String form(TFinance tFinance, Model model) {
		TCapital capital = tCapitalService.getByOrganId(UserUtils.getUser().getCompany().getId());
		model.addAttribute("tCapital", capital);
		model.addAttribute("tFinance", tFinance);
		return "modules/catipal/tFinanceForm";
	}

	@RequiresPermissions("catipal:tFinance:edit")
	@RequestMapping(value = "save")
	public String save(TFinance tFinance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tFinance)){
			return form(tFinance, model);
		}
		TCapital capital = tCapitalService.getByOrganId(UserUtils.getUser().getCompany().getId());
		if (capital == null) {
			addMessage(redirectAttributes, "请先添加资本信息");
			return "redirect:"+Global.getAdminPath()+"/catipal/tFinance/?repage";			
		}
		tFinanceService.save(tFinance,capital);
		addMessage(redirectAttributes, "保存融资列表成功");
		return "redirect:"+Global.getAdminPath()+"/catipal/tFinance/?repage";
	}
	
	@RequiresPermissions("catipal:tFinance:edit")
	@RequestMapping(value = "delete")
	public String delete(TFinance tFinance, RedirectAttributes redirectAttributes) {
		tFinanceService.delete(tFinance);
		addMessage(redirectAttributes, "删除融资列表成功");
		return "redirect:"+Global.getAdminPath()+"/catipal/tFinance/?repage";
	}

}