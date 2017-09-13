/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balancesheep.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.service.TBalanceSheepService;

/**
 * 资产负债表Controller
 * @author lx
 * @version 2016-05-17
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/balancesheep/tBalanceSheep")
public class TBalanceSheepController extends BaseController {

	@Autowired
	private TBalanceSheepService tBalanceSheepService;
	
	@ModelAttribute
	public TBalanceSheep get(@RequestParam(required=false) String id) {
		TBalanceSheep entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tBalanceSheepService.get(id);
		}
		if (entity == null){
			entity = new TBalanceSheep();
		}
		return entity;
	}
	
	@RequiresPermissions("balancesheep:tBalanceSheep:view")
	@RequestMapping(value = {"list", ""})
	public String list(TBalanceSheep tBalanceSheep, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String  first = "";
		if(tBalanceSheep.getCreateTime()==null||tBalanceSheep.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      tBalanceSheep.setCreateTime(first+"-%");
		}else{
			tBalanceSheep.setCreateTime(tBalanceSheep.getCreateTime()+"-%");
		}
		Page<TBalanceSheep> page = tBalanceSheepService.findPage(new Page<TBalanceSheep>(request, response), tBalanceSheep); 
		model.addAttribute("page", page);
		tBalanceSheep.setCreateTime(tBalanceSheep.getCreateTime().substring(0, 7));//不加后面的"-%",用于桌面查询框显示
		model.addAttribute("tBalanceSheep", tBalanceSheep);
		return "modules/balancesheep/tBalanceSheepList";
	}

	@RequiresPermissions("balancesheep:tBalanceSheep:view")
	@RequestMapping(value = "form")
	public String form(TBalanceSheep tBalanceSheep, Model model) {
		model.addAttribute("tBalanceSheep", tBalanceSheep);
		return "modules/balancesheep/tBalanceSheepForm";
	}

	@RequiresPermissions("balancesheep:tBalanceSheep:edit")
	@RequestMapping(value = "save")
	public String save(TBalanceSheep tBalanceSheep, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tBalanceSheep)){
			return form(tBalanceSheep, model);
		}
		tBalanceSheepService.save(tBalanceSheep);
		addMessage(redirectAttributes, "保存资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/balancesheep/tBalanceSheep/?repage";
	}
	
	@RequiresPermissions("balancesheep:tBalanceSheep:edit")
	@RequestMapping(value = "delete")
	public String delete(TBalanceSheep tBalanceSheep, RedirectAttributes redirectAttributes) {
		tBalanceSheepService.delete(tBalanceSheep);
		addMessage(redirectAttributes, "删除资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/balancesheep/tBalanceSheep/?repage";
	}

}