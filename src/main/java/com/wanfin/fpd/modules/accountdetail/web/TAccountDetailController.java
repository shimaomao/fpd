/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountdetail.web;

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
import com.wanfin.fpd.modules.accountdetail.entity.TAccountDetail;
import com.wanfin.fpd.modules.accountdetail.service.TAccountDetailService;

/**
 * 账目明细Controller
 * @author lx
 * @version 2016-05-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/accountdetail/tAccountDetail")
public class TAccountDetailController extends BaseController {

	@Autowired
	private TAccountDetailService tAccountDetailService;
	
	@ModelAttribute
	public TAccountDetail get(@RequestParam(required=false) String id) {
		TAccountDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAccountDetailService.get(id);
		}
		if (entity == null){
			entity = new TAccountDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("accountdetail:tAccountDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAccountDetail tAccountDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TAccountDetail> page = tAccountDetailService.findPage(new Page<TAccountDetail>(request, response), tAccountDetail); 
		model.addAttribute("page", page);
		model.addAttribute("tAccountDetail", tAccountDetail);
		return "modules/accountdetail/tAccountDetailList";
	}

	@RequiresPermissions("accountdetail:tAccountDetail:view")
	@RequestMapping(value = "form")
	public String form(TAccountDetail tAccountDetail, Model model) {
		model.addAttribute("tAccountDetail", tAccountDetail);
		return "modules/accountdetail/tAccountDetailForm";
	}

	@RequiresPermissions("accountdetail:tAccountDetail:edit")
	@RequestMapping(value = "save")
	public String save(TAccountDetail tAccountDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAccountDetail)){
			return form(tAccountDetail, model);
		}
		tAccountDetailService.save(tAccountDetail);
		addMessage(redirectAttributes, "保存账目明细成功");
		return "redirect:"+Global.getAdminPath()+"/accountdetail/tAccountDetail/?repage";
	}
	
	@RequiresPermissions("accountdetail:tAccountDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(TAccountDetail tAccountDetail, RedirectAttributes redirectAttributes) {
		tAccountDetailService.delete(tAccountDetail);
		addMessage(redirectAttributes, "删除账目明细成功");
		return "redirect:"+Global.getAdminPath()+"/accountdetail/tAccountDetail/?repage";
	}

}