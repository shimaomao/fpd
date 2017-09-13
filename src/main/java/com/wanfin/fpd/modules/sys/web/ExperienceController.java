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
import com.wanfin.fpd.modules.sys.entity.Experience;
import com.wanfin.fpd.modules.sys.service.ExperienceService;

/**
 * 从业人员工作经历Controller
 * @author kewenxiu
 * @version 2017-03-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/experience")
public class ExperienceController extends BaseController {

	@Autowired
	private ExperienceService experienceService;
	
	@ModelAttribute
	public Experience get(@RequestParam(required=false) String id) {
		Experience entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = experienceService.get(id);
		}
		if (entity == null){
			entity = new Experience();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:experience:view")
	@RequestMapping(value = {"list", ""})
	public String list(Experience experience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Experience> page = experienceService.findPage(new Page<Experience>(request, response), experience); 
		model.addAttribute("page", page);
		model.addAttribute("experience", experience);
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		return "modules/sys/experienceList";
	}

	@RequiresPermissions("sys:experience:view")
	@RequestMapping(value = "form")
	public String form(Experience experience, Model model) {
		model.addAttribute("experience", experience);
		return "modules/sys/experienceForm";
	}

	@RequiresPermissions("sys:experience:edit")
	@RequestMapping(value = "save")
	public String save(Experience experience, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, experience)){
			return form(experience, model);
		}
		experienceService.save(experience);
		addMessage(redirectAttributes, "保存从业人员工作经历成功");
		return "redirect:"+Global.getAdminPath()+"/sys/experience/?repage&isClose=1&employId="+experience.getEmployId();
	}
	
	@RequiresPermissions("sys:experience:edit")
	@RequestMapping(value = "delete")
	public String delete(Experience experience, RedirectAttributes redirectAttributes) {
		experienceService.delete(experience);
		addMessage(redirectAttributes, "删除从业人员工作经历成功");
		return "redirect:"+Global.getAdminPath()+"/sys/experience/?repage&employId="+experience.getEmployId();
	}

}