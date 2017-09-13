/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web;

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
import com.wanfin.fpd.modules.credit.entity.CreditTotal;
import com.wanfin.fpd.modules.credit.service.CreditTotalService;

/**
 * 征信次数统计Controller
 * @author cjp
 * @version 2017-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/creditTotal")
public class CreditTotalController extends BaseController {

	@Autowired
	private CreditTotalService creditTotalService;
	
	@ModelAttribute
	public CreditTotal get(@RequestParam(required=false) String id) {
		CreditTotal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditTotalService.get(id);
		}
		if (entity == null){
			entity = new CreditTotal();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditTotal:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditTotal creditTotal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditTotal> page = creditTotalService.findPage(new Page<CreditTotal>(request, response), creditTotal); 
		model.addAttribute("page", page);
		model.addAttribute("creditTotal", creditTotal);
		return "modules/credit/creditTotalList";
	}

	@RequiresPermissions("credit:creditTotal:view")
	@RequestMapping(value = "form")
	public String form(CreditTotal creditTotal, Model model) {
		model.addAttribute("creditTotal", creditTotal);
		return "modules/credit/creditTotalForm";
	}

	@RequiresPermissions("credit:creditTotal:edit")
	@RequestMapping(value = "save")
	public String save(CreditTotal creditTotal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditTotal)){
			return form(creditTotal, model);
		}
		creditTotalService.save(creditTotal);
		addMessage(redirectAttributes, "保存征信次数统计成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditTotal/?repage";
	}
	
	@RequiresPermissions("credit:creditTotal:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditTotal creditTotal, RedirectAttributes redirectAttributes) {
		creditTotalService.delete(creditTotal);
		addMessage(redirectAttributes, "删除征信次数统计成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditTotal/?repage";
	}

}