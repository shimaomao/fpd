/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.cashflux.web;

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
import com.wanfin.fpd.modules.cashflux.entity.TCashFlux;
import com.wanfin.fpd.modules.cashflux.service.TCashFluxService;

/**
 * 现金流量分析Controller
 * @author lx
 * @version 2016-05-20
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/cashflux/tCashFlux")
public class TCashFluxController extends BaseController {

	@Autowired
	private TCashFluxService tCashFluxService;
	
	@ModelAttribute
	public TCashFlux get(@RequestParam(required=false) String id) {
		TCashFlux entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCashFluxService.get(id);
		}
		if (entity == null){
			entity = new TCashFlux();
		}
		return entity;
	}
	
	@RequiresPermissions("cashflux:tCashFlux:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCashFlux tCashFlux, HttpServletRequest request, HttpServletResponse response, Model model) {
		String  first = "";
		if(tCashFlux.getCreateTime()==null||tCashFlux.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      tCashFlux.setCreateTime(first+"-%");
		}else{
			tCashFlux.setCreateTime(tCashFlux.getCreateTime()+"-%");
		}
		
		
		Page<TCashFlux> page = tCashFluxService.findPage(new Page<TCashFlux>(request, response), tCashFlux); 
		model.addAttribute("page", page);
		tCashFlux.setCreateTime(tCashFlux.getCreateTime().substring(0, 7));//不加后面的"-%",用于桌面查询框显示
		model.addAttribute("tCashFlux", tCashFlux);
		return "modules/cashflux/tCashFluxList";
	}

	@RequiresPermissions("cashflux:tCashFlux:view")
	@RequestMapping(value = "form")
	public String form(TCashFlux tCashFlux, Model model) {
		model.addAttribute("tCashFlux", tCashFlux);
		return "modules/cashflux/tCashFluxForm";
	}

	@RequiresPermissions("cashflux:tCashFlux:edit")
	@RequestMapping(value = "save")
	public String save(TCashFlux tCashFlux, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCashFlux)){
			return form(tCashFlux, model);
		}
		tCashFluxService.save(tCashFlux);
		addMessage(redirectAttributes, "保存现金流量分析成功");
		return "redirect:"+Global.getAdminPath()+"/cashflux/tCashFlux/?repage";
	}
	
	@RequiresPermissions("cashflux:tCashFlux:edit")
	@RequestMapping(value = "delete")
	public String delete(TCashFlux tCashFlux, RedirectAttributes redirectAttributes) {
		tCashFluxService.delete(tCashFlux);
		addMessage(redirectAttributes, "删除现金流量分析成功");
		return "redirect:"+Global.getAdminPath()+"/cashflux/tCashFlux/?repage";
	}

}