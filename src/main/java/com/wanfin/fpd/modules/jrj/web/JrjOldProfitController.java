/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldProfit;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjOldProfitService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 旧利润表Controller
 * @author xzt 
 * @version 2016-11-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/profit/oldJrjProfit")
public class JrjOldProfitController extends BaseController {

	@Autowired
	private JrjOldProfitService jrjProfitService;
	
	@ModelAttribute
	public JrjOldProfit get(@RequestParam(required=false) String id) {
		JrjOldProfit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjProfitService.get(id);
		}
		if (entity == null){
			entity = new JrjOldProfit();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjOldProfit jrjProfit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjOldProfit> page = jrjProfitService.findPage(new Page<JrjOldProfit>(request, response), jrjProfit); 
		String  first = "";
		if(jrjProfit.getSubmitDate()==null||jrjProfit.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjProfit.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("jrjProfit", jrjProfit);
		return "modules/jrj/tOldProfitList";
	}

	@RequestMapping(value = "form")
	public String form(JrjOldProfit jrjProfit, Model model) {
		
		String  first = "";
		if(jrjProfit.getCreateTime()==null||jrjProfit.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjProfit.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		jrjProfit.setCompanyName(company.getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjProfit", jrjProfit);
		return "modules/jrj/tOldProfitForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjOldProfit jrjProfit, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjProfit)){
			return form(jrjProfit, model);
		}
		if(jrjProfit.getId() == null || "".equals(jrjProfit.getId())){
			jrjProfit.setReportName(currentUser.getCompany().getName());
			jrjProfit.setOrganId(currentUser.getCompany().getId()); 
			jrjProfit.setCreateBy(currentUser);	
			jrjProfit.setCompanyName(currentUser.getCompany().getName());
		}					
		jrjProfit.setScanFlag("0");
		jrjProfitService.save(jrjProfit);
		addMessage(redirectAttributes, "保存利润成功");
		return "redirect:"+Global.getAdminPath()+"/profit/oldJrjProfit/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjOldProfit jrjProfit, RedirectAttributes redirectAttributes) {
		jrjProfitService.delete(jrjProfit);
		addMessage(redirectAttributes, "删除利润表成功");
		return "redirect:"+Global.getAdminPath()+"/profit/oldJrjProfit/?repage";
	}

}