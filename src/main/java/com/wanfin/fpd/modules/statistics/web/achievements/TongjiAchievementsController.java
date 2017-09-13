/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.web.achievements;

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
import com.wanfin.fpd.modules.statistics.entity.achievements.TongjiAchievements;
import com.wanfin.fpd.modules.statistics.service.achievements.TongjiAchievementsService;

/**
 * 绩效所需项目Controller
 * @author lxh
 * @version 2017-04-20
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/statistics/achievements/tongjiAchievements")
public class TongjiAchievementsController extends BaseController {

	@Autowired
	private TongjiAchievementsService tongjiAchievementsService;
	
/*	@ModelAttribute
	public TongjiAchievements get(@RequestParam(required=false) String id) {
		TongjiAchievements entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tongjiAchievementsService.get(id);
		}
		if (entity == null){
			entity = new TongjiAchievements();
		}
		return entity;
	}*/
	
	@RequiresPermissions("statistics:achievements:tongjiAchievements:view")
	@RequestMapping(value = {"list", ""})
	public String list(TongjiAchievements tongjiAchievements, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TongjiAchievements> page = tongjiAchievementsService.findPage(new Page<TongjiAchievements>(request, response), tongjiAchievements); 
		model.addAttribute("page", page);
		model.addAttribute("tongjiAchievements", tongjiAchievements);
		return "modules/statistics/achievements/tongjiAchievementsList";
	}

	@RequiresPermissions("statistics:achievements:tongjiAchievements:view")
	@RequestMapping(value = "form")
	public String form(TongjiAchievements tongjiAchievements, Model model) {
		model.addAttribute("tongjiAchievements", tongjiAchievements);
		return "modules/statistics/achievements/tongjiAchievementsForm";
	}

	@RequiresPermissions("statistics:achievements:tongjiAchievements:edit")
	@RequestMapping(value = "save")
	public String save(TongjiAchievements tongjiAchievements, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tongjiAchievements)){
			return form(tongjiAchievements, model);
		}
		tongjiAchievementsService.save(tongjiAchievements);
		addMessage(redirectAttributes, "保存绩效所需项目成功");
		return "redirect:"+Global.getAdminPath()+"/statistics/achievements/tongjiAchievements/?repage";
	}
	
	@RequiresPermissions("statistics:achievements:tongjiAchievements:edit")
	@RequestMapping(value = "delete")
	public String delete(TongjiAchievements tongjiAchievements, RedirectAttributes redirectAttributes) {
		tongjiAchievementsService.delete(tongjiAchievements);
		addMessage(redirectAttributes, "删除绩效所需项目成功");
		return "redirect:"+Global.getAdminPath()+"/statistics/achievements/tongjiAchievements/?repage";
	}

}