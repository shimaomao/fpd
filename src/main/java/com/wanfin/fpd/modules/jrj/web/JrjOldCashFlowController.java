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
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjOldCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 现金流量表Controller
 * @author xzt
 * @version 2016-11-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/cash/oldJrjCashFlow")
public class JrjOldCashFlowController extends BaseController {

	@Autowired
	private JrjOldCashFlowService jrjCashFlowService;
	
	@ModelAttribute
	public JrjOldCashFlow get(@RequestParam(required=false) String id) {
		JrjOldCashFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjCashFlowService.get(id);
		}
		if (entity == null){
			entity = new JrjOldCashFlow();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjOldCashFlow jrjCashFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjOldCashFlow> page = jrjCashFlowService.findPage(new Page<JrjOldCashFlow>(request, response), jrjCashFlow); 
		String  first = "";
		if(jrjCashFlow.getSubmitDate()==null||jrjCashFlow.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjCashFlow.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("jrjCashFlow", jrjCashFlow);
		return "modules/jrj/tOldCashFlowList";
	}

	@RequestMapping(value = "form")
	public String form(JrjOldCashFlow jrjCashFlow, Model model) {
		
		String  first = "";
		if(jrjCashFlow.getCreateTime()==null||jrjCashFlow.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjCashFlow.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		jrjCashFlow.setCompanyName(company.getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjCashFlow", jrjCashFlow);
		return "modules/jrj/tOldCashFlowForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjOldCashFlow jrjCashFlow, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjCashFlow)){
			return form(jrjCashFlow, model);
		}
		if(jrjCashFlow.getId() == null || "".equals(jrjCashFlow.getId())){
			jrjCashFlow.setReportName(currentUser.getCompany().getName());
			jrjCashFlow.setOrganId(currentUser.getCompany().getId()); 
			jrjCashFlow.setCreateBy(currentUser);	
			jrjCashFlow.setCompanyName(currentUser.getCompany().getName());
		}					
		jrjCashFlow.setScanFlag("0");
		jrjCashFlowService.save(jrjCashFlow);
		addMessage(redirectAttributes, "保存旧现金流量成功");
		return "redirect:"+Global.getAdminPath()+"/cash/oldJrjCashFlow/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjOldCashFlow jrjCashFlow, RedirectAttributes redirectAttributes) {
		jrjCashFlowService.delete(jrjCashFlow);
		addMessage(redirectAttributes, "删除旧现金流量成功");
		return "redirect:"+Global.getAdminPath()+"/cash/oldJrjCashFlow/?repage";
	}

}