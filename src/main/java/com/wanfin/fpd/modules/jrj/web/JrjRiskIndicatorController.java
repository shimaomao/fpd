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
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.jrj.service.JrjRiskIndicatorService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 风险指标Controller
 * @author xzt
 * @version 2016-10-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/risk/jrjRiskIndicator")
public class JrjRiskIndicatorController extends BaseController {

	@Autowired
	private JrjRiskIndicatorService jrjRiskIndicatorService;	
	
	@ModelAttribute
	public JrjRiskIndicator get(@RequestParam(required=false) String id) {
		JrjRiskIndicator entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjRiskIndicatorService.get(id);
		}
		if (entity == null){
			entity = new JrjRiskIndicator();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjRiskIndicator jrjRiskIndicator, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjRiskIndicator> page = jrjRiskIndicatorService.findPage(new Page<JrjRiskIndicator>(request, response), jrjRiskIndicator);
		String  first = "";
		if(jrjRiskIndicator.getSubmitDate()==null||jrjRiskIndicator.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjRiskIndicator.setSubmitDate(first);
		}		
		model.addAttribute("page", page);
		model.addAttribute("jrjRiskIndicator", jrjRiskIndicator);
		return "modules/jrj/tRiskIndicatorList";
	}

	@RequestMapping(value = "form")
	public String form(JrjRiskIndicator jrjRiskIndicator, Model model) {		
		String  first = "";
		if(jrjRiskIndicator.getSubmitDate()==null||jrjRiskIndicator.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjRiskIndicator.setSubmitDate(first);
		}	
		User user = UserUtils.getUser();
		Office company = user.getCompany();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());
		model.addAttribute("jrjRiskIndicator", jrjRiskIndicator);
		return "modules/jrj/tRiskIndicatorForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjRiskIndicator jrjRiskIndicator, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjRiskIndicator)){
			return form(jrjRiskIndicator, model);
		}
		if(jrjRiskIndicator.getId() == null || "".equals(jrjRiskIndicator.getId())){
			jrjRiskIndicator.setReportName(currentUser.getCompany().getName());
			jrjRiskIndicator.setOrganId(currentUser.getCompany().getId()); 
			jrjRiskIndicator.setCreateBy(currentUser);
			jrjRiskIndicator.setCompanyName(currentUser.getCompany().getName());
		}
		
		jrjRiskIndicator.setScanFlag("0");
		jrjRiskIndicatorService.save(jrjRiskIndicator);
		addMessage(redirectAttributes, "保存风险指标成功");
		return "redirect:"+Global.getAdminPath()+"/risk/jrjRiskIndicator/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjRiskIndicator jrjRiskIndicator, RedirectAttributes redirectAttributes) {			
		jrjRiskIndicatorService.delete(jrjRiskIndicator);
		addMessage(redirectAttributes, "删除风险指标成功");
		return "redirect:"+Global.getAdminPath()+"/risk/jrjRiskIndicator/?repage";
	}
	

}