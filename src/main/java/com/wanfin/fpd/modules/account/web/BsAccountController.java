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
import com.wanfin.fpd.modules.account.entity.BsAccount;
import com.wanfin.fpd.modules.account.service.BsAccountService;

/**
 * 资金账户信息Controller
 * @author srf
 * @version 2017-01-03
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/account/bsAccount")
public class BsAccountController extends BaseController {

	@Autowired
	private BsAccountService bsAccountService;
	
	@ModelAttribute
	public BsAccount get(@RequestParam(required=false) String id) {
		BsAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bsAccountService.get(id);
		}
		if (entity == null){
			entity = new BsAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("account:bsAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(BsAccount bsAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BsAccount> page = bsAccountService.findPage(new Page<BsAccount>(request, response), bsAccount); 
		model.addAttribute("page", page);
		model.addAttribute("bsAccount", bsAccount);
		return "modules/account/bsAccountList";
	}

	@RequiresPermissions("account:bsAccount:view")
	@RequestMapping(value = "form")
	public String form(BsAccount bsAccount, Model model) {
		model.addAttribute("bsAccount", bsAccount);
		return "modules/account/bsAccountForm";
	}

	@RequiresPermissions("account:bsAccount:edit")
	@RequestMapping(value = "save")
	public String save(BsAccount bsAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bsAccount)){
			return form(bsAccount, model);
		}
		bsAccountService.save(bsAccount);
		addMessage(redirectAttributes, "保存资金账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/account/bsAccount/?repage";
	}
	
	@RequiresPermissions("account:bsAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(BsAccount bsAccount, RedirectAttributes redirectAttributes) {
		bsAccountService.delete(bsAccount);
		addMessage(redirectAttributes, "删除资金账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/account/bsAccount/?repage";
	}

}