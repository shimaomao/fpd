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
import com.wanfin.fpd.modules.sys.entity.SysOfficePpartner;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.SysOfficePpartnerService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 机构个人股东Controller
 * @author kewenxiu
 * @version 2017-03-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOfficePpartner")
public class SysOfficePpartnerController extends BaseController {

	@Autowired
	private SysOfficePpartnerService sysOfficePpartnerService;
	
	@ModelAttribute
	public SysOfficePpartner get(@RequestParam(required=false) String id) {
		SysOfficePpartner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOfficePpartnerService.get(id);
		}
		if (entity == null){
			entity = new SysOfficePpartner();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysOfficePpartner:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOfficePpartner sysOfficePpartner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOfficePpartner> page = sysOfficePpartnerService.findPage(new Page<SysOfficePpartner>(request, response), sysOfficePpartner); 
		model.addAttribute("page", page);
		model.addAttribute("sysOfficePpartner", sysOfficePpartner);
		return "modules/sys/sysOfficePpartnerList";
	}

	@RequestMapping(value = "form")
	public String form(SysOfficePpartner sysOfficePpartner, Model model) {
		if(StringUtils.isBlank(sysOfficePpartner.getPid())){
			//设置机构ID
			User user = UserUtils.getUser();
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				sysOfficePpartner.setPid(user.getCompany().getId());
			}
		}
		model.addAttribute("sysOfficePpartner", sysOfficePpartner);
		return "modules/sys/sysOfficePpartnerForm";
	}

	@RequestMapping(value = "save")
	public String save(SysOfficePpartner sysOfficePpartner, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOfficePpartner)){
			return form(sysOfficePpartner, model);
		}
		sysOfficePpartnerService.save(sysOfficePpartner);
		addMessage(redirectAttributes, "保存机构个人股东成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficePartner/partners";
	}
	
	@RequiresPermissions("sys:sysOfficePpartner:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOfficePpartner sysOfficePpartner, RedirectAttributes redirectAttributes) {
		sysOfficePpartnerService.delete(sysOfficePpartner);
		addMessage(redirectAttributes, "删除机构个人股东成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficePpartner/?repage";
	}

}