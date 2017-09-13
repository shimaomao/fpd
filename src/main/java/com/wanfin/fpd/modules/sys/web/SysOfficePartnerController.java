/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

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
import com.wanfin.fpd.modules.sys.entity.SysOfficePartner;
import com.wanfin.fpd.modules.sys.entity.SysOfficePpartner;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SysOfficePartnerService;
import com.wanfin.fpd.modules.sys.service.SysOfficePpartnerService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 机构股东Controller
 * @author chenh
 * @version 2017-03-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOfficePartner")
public class SysOfficePartnerController extends BaseController {

	@Autowired
	private SysOfficePartnerService sysOfficePartnerService;
	@Autowired
	private SysOfficePpartnerService sysOfficePpartnerService;
	@Autowired
	private OfficeService officeService;
	
	public int doublePageSize = 10;
	
	@ModelAttribute
	public SysOfficePartner get(@RequestParam(required=false) String id) {
		SysOfficePartner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOfficePartnerService.get(id);
		}
		if (entity == null){
			entity = new SysOfficePartner();
		}
		return entity;
	}
	
	@RequestMapping(value = "partners")
	public String partners(SysOfficePartner sysOfficePartner,SysOfficePpartner sysOfficePpartner,HttpServletRequest request, HttpServletResponse response, Model model) {
		Boolean isNull = false;
		User user = UserUtils.getUser();
		if(StringUtils.isBlank(sysOfficePartner.getPid())&&StringUtils.isBlank(sysOfficePpartner.getPid())){
			//普通用户，获取当前用户机构
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				sysOfficePartner.setPid(user.getCompany().getId());
				sysOfficePpartner.setPid(user.getCompany().getId());
			}else{
				model.addAttribute("page", new Page<SysOfficePartner>());
				model.addAttribute("pPage", new Page<SysOfficePpartner>());
				isNull = true;
			}
		}
		if(!isNull){
			//admin，获取点击选择的机构
			Page<SysOfficePartner> page = sysOfficePartnerService.findPage(new Page<SysOfficePartner>(request, response,doublePageSize), sysOfficePartner); 
			Page<SysOfficePpartner> pPage = sysOfficePpartnerService.findPage(new Page<SysOfficePpartner>(request, response,doublePageSize,"p"), sysOfficePpartner); 
			//設置前端分頁方法名稱
			pPage.setFuncName("pPage");
			model.addAttribute("page", page);
			model.addAttribute("pPage", pPage);
			model.addAttribute("office", officeService.get(sysOfficePartner.getPid()));
		}
		model.addAttribute("user", user);
		model.addAttribute("sysOfficePartner", sysOfficePartner);
		model.addAttribute("sysOfficePpartner", sysOfficePpartner);
		return "modules/sys/sysOfficePartnerList";
	}
	
	@RequiresPermissions("sys:sysOfficePartner:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOfficePartner sysOfficePartner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOfficePartner> page = sysOfficePartnerService.findPage(new Page<SysOfficePartner>(request, response), sysOfficePartner); 
		model.addAttribute("page", page);
		model.addAttribute("sysOfficePartner", sysOfficePartner);
		return "modules/sys/sysOfficePartnerList";
	}

	@RequiresPermissions("sys:sysOfficePartner:view")
	@RequestMapping(value = "form")
	public String form(SysOfficePartner sysOfficePartner, Model model) {
		if(StringUtils.isBlank(sysOfficePartner.getPid())){
			//设置机构ID
			User user = UserUtils.getUser();
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				sysOfficePartner.setPid(user.getCompany().getId());
			}
		}
		model.addAttribute("sysOfficePartner", sysOfficePartner);
		return "modules/sys/sysOfficePartnerForm";
	}

	@RequiresPermissions("sys:sysOfficePartner:edit")
	@RequestMapping(value = "save")
	public String save(SysOfficePartner sysOfficePartner, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOfficePartner)){
			return form(sysOfficePartner, model);
		}
		sysOfficePartnerService.save(sysOfficePartner);
		addMessage(redirectAttributes, "保存机构股东成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficePartner/partners";
	}
	
	@RequiresPermissions("sys:sysOfficePartner:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOfficePartner sysOfficePartner, RedirectAttributes redirectAttributes) {
		sysOfficePartnerService.delete(sysOfficePartner);
		addMessage(redirectAttributes, "删除机构股东成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficePartner/?repage";
	}

}