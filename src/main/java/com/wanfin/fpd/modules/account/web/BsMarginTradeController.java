/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.web;

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
import com.wanfin.fpd.modules.account.entity.BsMarginTrade;
import com.wanfin.fpd.modules.account.service.BsMarginTradeService;

/**
 * 保证金记录表Controller
 * @author srf
 * @version 2017-01-03
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/account/bsMarginTrade")
public class BsMarginTradeController extends BaseController {

	@Autowired
	private BsMarginTradeService bsMarginTradeService;
	
	/*@ModelAttribute
	public BsMarginTrade get(@RequestParam(required=false) String id) {
		BsMarginTrade entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsMarginTradeService.get(id);
		}
		if (entity == null){
			entity = new BsMarginTrade();
		}
		return entity;
	}*/
	
	@RequiresPermissions("account:bsMarginTrade:view")
	@RequestMapping(value = {"list", ""})
	public String list(BsMarginTrade bsMarginTrade, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsMarginTrade> page = bsMarginTradeService.findPage(new Page<BsMarginTrade>(request, response), bsMarginTrade); 
		model.addAttribute("page", page);
		model.addAttribute("bsMarginTrade", bsMarginTrade);
		return "modules/account/bsMarginTradeList";
	}

	@RequiresPermissions("account:bsMarginTrade:view")
	@RequestMapping(value = "form")
	public String form(BsMarginTrade bsMarginTrade, Model model) {
		model.addAttribute("bsMarginTrade", bsMarginTrade);
		return "modules/account/bsMarginTradeForm";
	}

	@RequiresPermissions("account:bsMarginTrade:edit")
	@RequestMapping(value = "save")
	public String save(BsMarginTrade bsMarginTrade, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsMarginTrade)){
			return form(bsMarginTrade, model);
		}
		bsMarginTradeService.save(bsMarginTrade);
		addMessage(redirectAttributes, "保存保证金记录表成功");
		return "redirect:"+Global.getAdminPath()+"/account/bsMarginTrade/?repage";
	}
	
	@RequiresPermissions("account:bsMarginTrade:edit")
	@RequestMapping(value = "delete")
	public String delete(BsMarginTrade bsMarginTrade, RedirectAttributes redirectAttributes) {
		bsMarginTradeService.delete(bsMarginTrade);
		addMessage(redirectAttributes, "删除保证金记录表成功");
		return "redirect:"+Global.getAdminPath()+"/account/bsMarginTrade/?repage";
	}

}