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
import com.wanfin.fpd.modules.jrj.entity.JrjOldReport;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjOldReportService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 资产负债表Controller
 * @author xzt
 * @version 2016-05-17
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/report/oldReport")
public class JrjOldReportController extends BaseController {

	@Autowired
	private JrjOldReportService jrjOldReportService;
	
	@ModelAttribute
	public JrjOldReport get(@RequestParam(required=false) String id) {
		JrjOldReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjOldReportService.get(id);
		}
		if (entity == null){
			entity = new JrjOldReport();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjOldReport jrjOldReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjOldReport> page = jrjOldReportService.findPage(new Page<JrjOldReport>(request, response), jrjOldReport); 
		String  first = "";
		if(jrjOldReport.getSubmitDate()==null||jrjOldReport.getSubmitDate().equals("")){
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		     first= format.format(c.getTime());
		     jrjOldReport.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("jrjOldReport", jrjOldReport);
		return "modules/jrj/tOldReportList";
	}

	@RequestMapping(value = "form")
	public String form(JrjOldReport jrjOldReport, Model model) {
		
		String  first = "";
		if(jrjOldReport.getCreateTime()==null||jrjOldReport.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjOldReport.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		jrjOldReport.setCompanyName(company.getName());
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjOldReport", jrjOldReport);
		return "modules/jrj/tOldReportForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjOldReport jrjOldReport, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjOldReport)){
			return form(jrjOldReport, model);
		}
		if(jrjOldReport.getId() == null || "".equals(jrjOldReport.getId())){
			jrjOldReport.setReportName(currentUser.getCompany().getName());
			jrjOldReport.setOrganId(currentUser.getCompany().getId()); 
			jrjOldReport.setCompanyId(currentUser.getCompany().getId());
			jrjOldReport.setCreateBy(currentUser);	
			jrjOldReport.setCompanyName(currentUser.getCompany().getName());
		}					
		jrjOldReport.setScanFlag("0");
		jrjOldReportService.save(jrjOldReport);
		addMessage(redirectAttributes, "保存报表说明成功");
		return "redirect:"+Global.getAdminPath()+"/report/oldReport/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjOldReport jrjOldReport, RedirectAttributes redirectAttributes) {
		jrjOldReport.setScanFlag("0");
		jrjOldReportService.delete(jrjOldReport);
		addMessage(redirectAttributes, "删除报表说明成功");
		return "redirect:"+Global.getAdminPath()+"/report/oldReport/?repage";
	}

}