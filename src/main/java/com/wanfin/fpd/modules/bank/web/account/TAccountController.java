/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.bank.web.account;

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
import com.wanfin.fpd.modules.bank.entity.account.TAccount;
import com.wanfin.fpd.modules.bank.service.account.TAccountService;

/**
 * 银行账户Controller
 * @author chenh
 * @version 2016-03-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/bank/account/tAccount")
public class TAccountController extends BaseController {

	@Autowired
	private TAccountService tAccountService;
	
	@ModelAttribute
	public TAccount get(@RequestParam(required=false) String id) {
		TAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAccountService.get(id);
		}
		if (entity == null){
			entity = new TAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("bank:account:tAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAccount tAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TAccount> page = tAccountService.findPage(new Page<TAccount>(request, response), tAccount); 
		model.addAttribute("page", page);
		model.addAttribute("tAccount", tAccount);
		return "modules/bank/account/tAccountList";
	}

	@RequiresPermissions("bank:account:tAccount:view")
	@RequestMapping(value = "form")
	public String form(TAccount tAccount, Model model) {
		model.addAttribute("tAccount", tAccount);
		return "modules/bank/account/tAccountForm";
	}

	@RequiresPermissions("bank:account:tAccount:edit")
	@RequestMapping(value = "save")
	public String save(TAccount tAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAccount)){
			return form(tAccount, model);
		}
		tAccountService.save(tAccount);
		addMessage(redirectAttributes, "保存银行账户成功");
		return "redirect:"+Global.getAdminPath()+"/bank/account/tAccount/?repage";
	}
	
	@RequiresPermissions("bank:account:tAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(TAccount tAccount, RedirectAttributes redirectAttributes) {
		tAccountService.delete(tAccount);
		addMessage(redirectAttributes, "删除银行账户成功");
		return "redirect:"+Global.getAdminPath()+"/bank/account/tAccount/?repage";
	}

}