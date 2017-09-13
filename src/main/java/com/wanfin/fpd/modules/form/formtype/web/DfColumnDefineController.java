/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.formtype.web;

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
import com.wanfin.fpd.modules.form.builder.web.BuilderController;
import com.wanfin.fpd.modules.form.formtype.entity.DfColumnDefine;
import com.wanfin.fpd.modules.form.formtype.service.DfColumnDefineService;

/**
 * 字段库维护Controller
 * @author lx
 * @version 2016-05-05
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/form/formtype/dfColumnDefine")
public class DfColumnDefineController extends BaseController {

	@Autowired
	private DfColumnDefineService dfColumnDefineService;
	
	@ModelAttribute
	public DfColumnDefine get(@RequestParam(required=false) String id) {
		DfColumnDefine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dfColumnDefineService.get(id);
		}
		if (entity == null){
			entity = new DfColumnDefine();
		}
		return entity;
	}
	
	@RequiresPermissions("form:formtype:dfColumnDefine:view")
	@RequestMapping(value = {"list", ""})
	public String list(DfColumnDefine dfColumnDefine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DfColumnDefine> page = dfColumnDefineService.findPage(new Page<DfColumnDefine>(request, response), dfColumnDefine); 
		model.addAttribute("page", page);
		model.addAttribute("dfColumnDefine", dfColumnDefine);
		model.addAttribute("categoryList",BuilderController.getCategoryList());
		return "modules/form/formtype/dfColumnDefineList";
	}

	@RequiresPermissions("form:formtype:dfColumnDefine:view")
	@RequestMapping(value = "form")
	public String form(DfColumnDefine dfColumnDefine, Model model) {
		model.addAttribute("dfColumnDefine", dfColumnDefine);
		return "modules/form/formtype/dfColumnDefineForm";
	}

	@RequiresPermissions("form:formtype:dfColumnDefine:edit")
	@RequestMapping(value = "save")
	public String save(DfColumnDefine dfColumnDefine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dfColumnDefine)){
			return form(dfColumnDefine, model);
		}
		dfColumnDefineService.save(dfColumnDefine);
		addMessage(redirectAttributes, "保存字段库维护成功");
		return "redirect:"+Global.getAdminPath()+"/form/formtype/dfColumnDefine/?repage";
	}
	
	@RequiresPermissions("form:formtype:dfColumnDefine:edit")
	@RequestMapping(value = "delete")
	public String delete(DfColumnDefine dfColumnDefine, RedirectAttributes redirectAttributes) {
		dfColumnDefineService.delete(dfColumnDefine);
		addMessage(redirectAttributes, "删除字段库维护成功");
		return "redirect:"+Global.getAdminPath()+"/form/formtype/dfColumnDefine/?repage";
	}

}