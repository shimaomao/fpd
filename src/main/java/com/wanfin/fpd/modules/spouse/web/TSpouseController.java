/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.spouse.web;

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
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wanfin.fpd.modules.spouse.service.TSpouseService;

/**
 * spouseController
 * @author spouse
 * @version 2016-07-04
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/spouse/tSpouse")
public class TSpouseController extends BaseController {

	@Autowired
	private TSpouseService tSpouseService;
	
	@ModelAttribute
	public TSpouse get(@RequestParam(required=false) String id) {
		TSpouse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tSpouseService.get(id);
		}
		if (entity == null){
			entity = new TSpouse();
		}
		return entity;
	}
	
	@RequiresPermissions("spouse:tSpouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(TSpouse tSpouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TSpouse> page = tSpouseService.findPage(new Page<TSpouse>(request, response), tSpouse); 
		model.addAttribute("page", page);
		model.addAttribute("tSpouse", tSpouse);
		return "modules/spouse/tSpouseList";
	}

	@RequiresPermissions("spouse:tSpouse:view")
	@RequestMapping(value = "form")
	public String form(TSpouse tSpouse, Model model) {
		model.addAttribute("tSpouse", tSpouse);
		return "modules/spouse/tSpouseForm";
	}

	@RequiresPermissions("spouse:tSpouse:edit")
	@RequestMapping(value = "save")
	public String save(TSpouse tSpouse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tSpouse)){
			return form(tSpouse, model);
		}
		tSpouseService.save(tSpouse);
		addMessage(redirectAttributes, "保存spouse成功");
		return "redirect:"+Global.getAdminPath()+"/spouse/tSpouse/?repage";
	}
	
	@RequiresPermissions("spouse:tSpouse:edit")
	@RequestMapping(value = "delete")
	public String delete(TSpouse tSpouse, RedirectAttributes redirectAttributes) {
		tSpouseService.delete(tSpouse);
		addMessage(redirectAttributes, "删除spouse成功");
		return "redirect:"+Global.getAdminPath()+"/spouse/tSpouse/?repage";
	}

}