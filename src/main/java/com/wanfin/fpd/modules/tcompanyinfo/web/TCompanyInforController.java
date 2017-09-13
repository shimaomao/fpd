/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tcompanyinfo.web;

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
import com.wanfin.fpd.modules.tcompanyinfo.entity.TCompanyInfor;
import com.wanfin.fpd.modules.tcompanyinfo.service.TCompanyInforService;

/**
 * 企业高管信息Controller
 * @author TcompanyInfo
 * @version 2016-08-11
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/tcompanyinfo/tCompanyInfor")
public class TCompanyInforController extends BaseController {

	@Autowired
	private TCompanyInforService tCompanyInforService;
	
	@ModelAttribute
	public TCompanyInfor get(@RequestParam(required=false) String id) {
		TCompanyInfor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCompanyInforService.get(id);
		}
		if (entity == null){
			entity = new TCompanyInfor();
		}
		return entity;
	}
	
	@RequiresPermissions("tcompanyinfo:tCompanyInfor:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCompanyInfor tCompanyInfor, HttpServletRequest request, HttpServletResponse response, Model model) {
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCompanyInfor.setCompanyId(companyId);
		}
		Page<TCompanyInfor> page = tCompanyInforService.findPage(new Page<TCompanyInfor>(request, response), tCompanyInfor); 
		model.addAttribute("page", page);
		model.addAttribute("tCompanyInfor", tCompanyInfor);
		
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return "modules/tcompanyinfo/tCompanyInforList";
	}

	@RequiresPermissions("tcompanyinfo:tCompanyInfor:view")
	@RequestMapping(value = "form")
	public String form(TCompanyInfor tCompanyInfor, Model model, HttpServletRequest request) {
		String companyId = request.getParameter("companyId");
		if(companyId!=null&&!"".equals(companyId)){
			tCompanyInfor.setCompanyId(companyId);
		}
		model.addAttribute("tCompanyInfor", tCompanyInfor);
		return "modules/tcompanyinfo/tCompanyInforForm";
	}

	@RequiresPermissions("tcompanyinfo:tCompanyInfor:edit")
	@RequestMapping(value = "save")
	public String save(TCompanyInfor tCompanyInfor, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, tCompanyInfor)){
			return form(tCompanyInfor, model,request);
		}
		tCompanyInforService.save(tCompanyInfor);
		addMessage(redirectAttributes, "保存高管信息成功");
		return "redirect:"+Global.getAdminPath()+"/tcompanyinfo/tCompanyInfor/?repage&isClose=1&companyId="+tCompanyInfor.getCompanyId();
	}
	
	@RequiresPermissions("tcompanyinfo:tCompanyInfor:edit")
	@RequestMapping(value = "delete")
	public String delete(TCompanyInfor tCompanyInfor, RedirectAttributes redirectAttributes) {
		tCompanyInforService.delete(tCompanyInfor);
		addMessage(redirectAttributes, "删除高管信息成功");
		return "redirect:"+Global.getAdminPath()+"/tcompanyinfo/tCompanyInfor/?repage&companyId="+tCompanyInfor.getCompanyId();
	}

}