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
import com.wanfin.fpd.modules.catipal.entity.TProfit;
import com.wanfin.fpd.modules.catipal.service.TCapitalService;
import com.wanfin.fpd.modules.catipal.service.TProfitService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 利润列表Controller
 * @author lx
 * @version 2016-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/catipal/tProfit")
public class TProfitController extends BaseController {

	@Autowired
	private TProfitService tProfitService;
	
	@Autowired
	private TCapitalService tCapitalService;
	
	@ModelAttribute
	public TProfit get(@RequestParam(required=false) String id) {
		TProfit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tProfitService.get(id);
		}
		if (entity == null){
			entity = new TProfit();
		}
		return entity;
	}
	
	@RequiresPermissions("catipal:tProfit:view")
	@RequestMapping(value = {"list", ""})
	public String list(TProfit tProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TProfit> page = tProfitService.findPage(new Page<TProfit>(request, response), tProfit); 
		model.addAttribute("page", page);
		model.addAttribute("tProfit", tProfit);
		return "modules/catipal/tProfitList";
	}

	@RequiresPermissions("catipal:tProfit:view")
	@RequestMapping(value = "form")
	public String form(TProfit tProfit, Model model) {
		TCapital capital = tCapitalService.getByOrganId(UserUtils.getUser().getCompany().getId());
		model.addAttribute("tCapital", capital);
		model.addAttribute("tProfit", tProfit);
		return "modules/catipal/tProfitForm";
	}

	@RequiresPermissions("catipal:tProfit:edit")
	@RequestMapping(value = "save")
	public String save(TProfit tProfit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tProfit)){
			return form(tProfit, model);
		}		
		//TCapital capital = tCapitalService.get("1");
		TCapital capital = tCapitalService.getByOrganId(UserUtils.getUser().getCompany().getId());		
		tProfitService.save(tProfit,capital);
		addMessage(redirectAttributes, "保存利润列表成功");
		return "redirect:"+Global.getAdminPath()+"/catipal/tProfit/?repage";
	}
	
	@RequiresPermissions("catipal:tProfit:edit")
	@RequestMapping(value = "delete")
	public String delete(TProfit tProfit, RedirectAttributes redirectAttributes) {
		tProfitService.delete(tProfit);
		addMessage(redirectAttributes, "删除利润列表成功");
		return "redirect:"+Global.getAdminPath()+"/catipal/tProfit/?repage";
	}

}