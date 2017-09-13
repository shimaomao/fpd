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
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 收益情况Controller
 * @author xzt
 * @version 2016-10-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/proceeds/jrjProceeds")
public class JrjProceedsController extends BaseController {

	@Autowired
	private JrjProceedsService jrjProceedsService;
	
	private String submitDate;
	
	@ModelAttribute
	public JrjProceeds get(@RequestParam(required=false) String id) {
		JrjProceeds entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjProceedsService.get(id);
		}
		if (entity == null){
			entity = new JrjProceeds();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjProceeds jrjProceeds, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjProceeds> page = jrjProceedsService.findPage(new Page<JrjProceeds>(request, response), jrjProceeds);
		String  first = "";
		if(jrjProceeds.getSubmitDate()==null||jrjProceeds.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjProceeds.setSubmitDate(first);
		}		
		model.addAttribute("page", page);
		model.addAttribute("jrjProceeds", jrjProceeds);
		return "modules/jrj/tProceedsList";
	}

	@RequestMapping(value = "form")
	public String form(JrjProceeds jrjProceeds, Model model) {		
		String  first = "";
		if(jrjProceeds.getSubmitDate()==null||jrjProceeds.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjProceeds.setSubmitDate(first);
		}	
		User user = UserUtils.getUser();
		Office company = user.getCompany();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());
		model.addAttribute("jrjProceeds", jrjProceeds);
		return "modules/jrj/tProceedsForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjProceeds jrjProceeds, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjProceeds)){
			return form(jrjProceeds, model);
		}
		if(jrjProceeds.getId() == null || "".equals(jrjProceeds.getId())){
			jrjProceeds.setReportName(currentUser.getCompany().getName());
			jrjProceeds.setOrganId(currentUser.getCompany().getId()); 
			jrjProceeds.setCreateBy(currentUser);	
			jrjProceeds.setCompanyName(currentUser.getCompany().getName());
		}			
		jrjProceeds.setScanFlag("0");
		jrjProceedsService.save(jrjProceeds);
		addMessage(redirectAttributes, "保存收益情况成功");
		return "redirect:"+Global.getAdminPath()+"/proceeds/jrjProceeds/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjProceeds jrjProceeds, RedirectAttributes redirectAttributes) {
		jrjProceedsService.delete(jrjProceeds);
		addMessage(redirectAttributes, "删除收益情况成功");
		return "redirect:"+Global.getAdminPath()+"/proceeds/jrjProceeds/?repage";
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	

}