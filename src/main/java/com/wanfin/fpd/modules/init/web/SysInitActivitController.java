/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.init.entity.SysInitActivit;
import com.wanfin.fpd.modules.init.service.SysInitActivitService;

/**
 * 流程初始化Controller
 * @author zxj
 * @version 2016-09-22
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/init/sysInitActivit")
public class SysInitActivitController extends BaseController {

	@Autowired
	private SysInitActivitService sysInitActivitService;
	
	@ModelAttribute
	public SysInitActivit get(@RequestParam(required=false) String id) {
		SysInitActivit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysInitActivitService.get(id);
		}
		if (entity == null){
			entity = new SysInitActivit();
		}
		return entity;
	}
	
	@RequiresPermissions("init:sysInitActivit:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysInitActivit sysInitActivit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysInitActivit> page = sysInitActivitService.findPage(new Page<SysInitActivit>(request, response), sysInitActivit); 
		model.addAttribute("page", page);
		model.addAttribute("sysInitActivit", sysInitActivit);
		return "modules/init/sysInitActivitList";
	}

	@RequiresPermissions("init:sysInitActivit:view")
	@RequestMapping(value = "form")
	public String form(SysInitActivit sysInitActivit, Model model) {
		//获取文件列表
		File actXd = new File(AutoUtil.getActivitOrgXd());
		File actDb = new File(AutoUtil.getActivitOrgDb());
		List<String> fileXd = new ArrayList<String>();
		List<String> fileDb = new ArrayList<String>();
		for(File file:actXd.listFiles()){
			fileXd.add(file.getName());
		}
		for(File file:actDb.listFiles()){
			fileDb.add(file.getName());
		}
		model.addAttribute("fileDb", fileDb);
		model.addAttribute("fileXd", fileXd);
		model.addAttribute("officeList", sysInitActivitService.getOfficeList());
		model.addAttribute("sysInitActivit", sysInitActivit);
		return "modules/init/sysInitActivitForm";
	}

	@RequiresPermissions("init:sysInitActivit:edit")
	@RequestMapping(value = "save")
	public String save(SysInitActivit sysInitActivit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysInitActivit)){
			return form(sysInitActivit, model);
		}
		String[] officeIds = sysInitActivit.getOfficeName().split(";");		
		if(sysInitActivitService.activitSave(sysInitActivit, officeIds)){
			addMessage(redirectAttributes, "保存流程初始化成功");
			return "redirect:"+Global.getAdminPath()+"/init/sysInitActivit/?repage";
		}else{
			addMessage(model, "保存流程初始化失败");
			return form(sysInitActivit, model);
		}
	}
	
	@RequiresPermissions("init:sysInitActivit:edit")
	@RequestMapping(value = "delete")
	public String delete(SysInitActivit sysInitActivit, RedirectAttributes redirectAttributes) {
		sysInitActivitService.delete(sysInitActivit);
		addMessage(redirectAttributes, "删除流程初始化成功");
		return "redirect:"+Global.getAdminPath()+"/init/sysInitActivit/?repage";
	}

}