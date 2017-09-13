/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customertogether.web;

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
import com.wanfin.fpd.modules.customertogether.entity.TCustomerTogether;
import com.wanfin.fpd.modules.customertogether.service.TCustomerTogetherService;

/**
 * 共同借款Controller
 * @author lx
 * @version 2016-08-22
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/customertogether/tCustomerTogether")
public class TCustomerTogetherController extends BaseController {

	@Autowired
	private TCustomerTogetherService tCustomerTogetherService;
	
	@ModelAttribute
	public TCustomerTogether get(@RequestParam(required=false) String id) {
		TCustomerTogether entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerTogetherService.get(id);
		}
		if (entity == null){
			entity = new TCustomerTogether();
		}
		return entity;
	}
	
	@RequiresPermissions("customertogether:tCustomerTogether:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomerTogether tCustomerTogether, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCustomerTogether> page = tCustomerTogetherService.findPage(new Page<TCustomerTogether>(request, response), tCustomerTogether); 
		model.addAttribute("page", page);
		model.addAttribute("tCustomerTogether", tCustomerTogether);
		return "modules/customertogether/tCustomerTogetherList";
	}

	@RequiresPermissions("customertogether:tCustomerTogether:view")
	@RequestMapping(value = "form")
	public String form(TCustomerTogether tCustomerTogether, Model model) {
		model.addAttribute("tCustomerTogether", tCustomerTogether);
		return "modules/customertogether/tCustomerTogetherForm";
	}

	@RequiresPermissions("customertogether:tCustomerTogether:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerTogether tCustomerTogether, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomerTogether)){
			return form(tCustomerTogether, model);
		}
		tCustomerTogetherService.save(tCustomerTogether);
		addMessage(redirectAttributes, "保存共同借款成功");
		return "redirect:"+Global.getAdminPath()+"/customertogether/tCustomerTogether/?repage&mainEmployeeid="+tCustomerTogether.getMainEmployeeid()+"&mainCompanyid="+tCustomerTogether.getMainCompanyid();
	}
	
	@RequiresPermissions("customertogether:tCustomerTogether:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomerTogether tCustomerTogether, RedirectAttributes redirectAttributes) {
		tCustomerTogetherService.delete(tCustomerTogether);
		addMessage(redirectAttributes, "删除共同借款成功");
		return "redirect:"+Global.getAdminPath()+"/customertogether/tCustomerTogether/?repage&mainEmployeeid="+tCustomerTogether.getMainEmployeeid()+"&mainCompanyid="+tCustomerTogether.getMainCompanyid();
	}
}