/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.web.normal;

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
import com.wanfin.fpd.modules.statistics.entity.normal.TongjiNormal;
import com.wanfin.fpd.modules.statistics.service.normal.TongjiNormalService;

/**
 * 正常结清期末兑现Controller
 * @author 虎
 * @version 2017-04-20
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/statistics/normal/tongjiNormal")
public class TongjiNormalController extends BaseController {

	@Autowired
	private TongjiNormalService tongjiNormalService;
	
/*	@ModelAttribute
	public TongjiNormal get(@RequestParam(required=false) String id) {
		TongjiNormal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tongjiNormalService.get(id);
		}
		if (entity == null){
			entity = new TongjiNormal();
		}
		return entity;
	}*/
	
	@RequiresPermissions("statistics:normal:tongjiNormal:view")
	@RequestMapping(value = {"list", ""})
	public String list(TongjiNormal tongjiNormal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TongjiNormal> page = tongjiNormalService.findPage(new Page<TongjiNormal>(request, response), tongjiNormal); 
		model.addAttribute("page", page);
		model.addAttribute("tongjiNormal", tongjiNormal);
		return "modules/statistics/normal/tongjiNormalList";
	}

	@RequiresPermissions("statistics:normal:tongjiNormal:view")
	@RequestMapping(value = "form")
	public String form(TongjiNormal tongjiNormal, Model model) {
		model.addAttribute("tongjiNormal", tongjiNormal);
		return "modules/statistics/normal/tongjiNormalForm";
	}

	@RequiresPermissions("statistics:normal:tongjiNormal:edit")
	@RequestMapping(value = "save")
	public String save(TongjiNormal tongjiNormal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tongjiNormal)){
			return form(tongjiNormal, model);
		}
		tongjiNormalService.save(tongjiNormal);
		addMessage(redirectAttributes, "保存正常结清期末兑现成功");
		return "redirect:"+Global.getAdminPath()+"/statistics/normal/tongjiNormal/?repage";
	}
	
	@RequiresPermissions("statistics:normal:tongjiNormal:edit")
	@RequestMapping(value = "delete")
	public String delete(TongjiNormal tongjiNormal, RedirectAttributes redirectAttributes) {
		tongjiNormalService.delete(tongjiNormal);
		addMessage(redirectAttributes, "删除正常结清期末兑现成功");
		return "redirect:"+Global.getAdminPath()+"/statistics/normal/tongjiNormal/?repage";
	}

}