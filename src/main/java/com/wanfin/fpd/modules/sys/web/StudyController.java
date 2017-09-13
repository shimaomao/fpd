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
import com.wanfin.fpd.modules.sys.entity.Study;
import com.wanfin.fpd.modules.sys.entity.SysOfficeEmployee;
import com.wanfin.fpd.modules.sys.service.StudyService;

/**
 * 学习经历Controller
 * @author kewenxiu
 * @version 2017-03-10
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/study")
public class StudyController extends BaseController {

	@Autowired
	private StudyService studyService;
	
	@ModelAttribute
	public Study get(@RequestParam(required=false) String id) {
		Study entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studyService.get(id);
		}
		if (entity == null){
			entity = new Study();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:study:view")
	@RequestMapping(value = {"list", ""})
	public String list(Study study, HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		/*if(StringUtils.isBlank(study.getEmployId())){
			addMessage(redirectAttributes, "请先保存基本信息");
			model.addAttribute("sysOfficeEmployee", new SysOfficeEmployee());
			return "redirect:"+Global.getAdminPath()+"/sys/sysOfficeEmployee/form";
		}*/
		Page<Study> page = studyService.findPage(new Page<Study>(request, response), study); 
		model.addAttribute("page", page);
		model.addAttribute("study", study);
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return "modules/sys/studyList";
	}

	@RequiresPermissions("sys:study:view")
	@RequestMapping(value = "form")
	public String form(Study study, Model model) {
		model.addAttribute("study", study);
		return "modules/sys/studyForm";
	}

	@RequiresPermissions("sys:study:edit")
	@RequestMapping(value = "save")
	public String save(Study study, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, study)){
			return form(study, model);
		}
		studyService.save(study);
		addMessage(redirectAttributes, "保存学习经历成功");
		return "redirect:"+Global.getAdminPath()+"/sys/study/?repage&isClose=1&employId="+study.getEmployId();
	}
	
	@RequiresPermissions("sys:study:edit")
	@RequestMapping(value = "delete")
	public String delete(Study study, RedirectAttributes redirectAttributes) {
		studyService.delete(study);
		addMessage(redirectAttributes, "删除学习经历成功");
		return "redirect:"+Global.getAdminPath()+"/sys/study/?repage&employId="+study.getEmployId();
	}

}