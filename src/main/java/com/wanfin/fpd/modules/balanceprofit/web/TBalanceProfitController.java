/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balanceprofit.web;

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
import com.wanfin.fpd.modules.balanceprofit.entity.TBalanceProfit;
import com.wanfin.fpd.modules.balanceprofit.service.TBalanceProfitService;

/**
 * 利润分析Controller
 * @author lx
 * @version 2016-05-18
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/balanceprofit/tBalanceProfit")
public class TBalanceProfitController extends BaseController {

	@Autowired
	private TBalanceProfitService tBalanceProfitService;
	
	@ModelAttribute
	public TBalanceProfit get(@RequestParam(required=false) String id) {
		TBalanceProfit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tBalanceProfitService.get(id);
		}
		if (entity == null){
			entity = new TBalanceProfit();
		}
		return entity;
	}
	
	@RequiresPermissions("balanceprofit:tBalanceProfit:view")
	@RequestMapping(value = {"list", ""})
	public String list(TBalanceProfit tBalanceProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
		String  first = "";
		if(tBalanceProfit.getCreateTime()==null||tBalanceProfit.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      tBalanceProfit.setCreateTime(first+"-%");
		}else{
			tBalanceProfit.setCreateTime(tBalanceProfit.getCreateTime()+"-%");
		}
		
		Page<TBalanceProfit> page = tBalanceProfitService.findPage(new Page<TBalanceProfit>(request, response), tBalanceProfit); 
		model.addAttribute("page", page);
		tBalanceProfit.setCreateTime(tBalanceProfit.getCreateTime().substring(0, 7));//不加后面的"-%",用于桌面查询框显示
		model.addAttribute("tBalanceProfit", tBalanceProfit);
		return "modules/balanceprofit/tBalanceProfitList";
	}

	@RequiresPermissions("balanceprofit:tBalanceProfit:view")
	@RequestMapping(value = "form")
	public String form(TBalanceProfit tBalanceProfit, Model model) {
		model.addAttribute("tBalanceProfit", tBalanceProfit);
		return "modules/balanceprofit/tBalanceProfitForm";
	}

	@RequiresPermissions("balanceprofit:tBalanceProfit:edit")
	@RequestMapping(value = "save")
	public String save(TBalanceProfit tBalanceProfit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tBalanceProfit)){
			return form(tBalanceProfit, model);
		}
		tBalanceProfitService.save(tBalanceProfit);
		addMessage(redirectAttributes, "保存利润分析成功");
		return "redirect:"+Global.getAdminPath()+"/balanceprofit/tBalanceProfit/?repage";
	}
	
	@RequiresPermissions("balanceprofit:tBalanceProfit:edit")
	@RequestMapping(value = "delete")
	public String delete(TBalanceProfit tBalanceProfit, RedirectAttributes redirectAttributes) {
		tBalanceProfitService.delete(tBalanceProfit);
		addMessage(redirectAttributes, "删除利润分析成功");
		return "redirect:"+Global.getAdminPath()+"/balanceprofit/tBalanceProfit/?repage";
	}

}