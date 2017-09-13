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
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationOne;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessSituationService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 经营情况月报表一Controller
 * @author xzt
 * @version 2016-11-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/business/situationOne")
public class JrjBusinessSituationController extends BaseController {

	@Autowired
	private JrjBusinessSituationService jrjBusinessSituationService;
	
	@ModelAttribute
	public JrjBusinessSituationOne get(@RequestParam(required=false) String id) {
		JrjBusinessSituationOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjBusinessSituationService.get(id);
		}
		if (entity == null){
			entity = new JrjBusinessSituationOne();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjBusinessSituationOne entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjBusinessSituationOne> page = jrjBusinessSituationService.findPage(new Page<JrjBusinessSituationOne>(request, response), entity); 
		String  first = "";
		if(entity.getSubmitDate()==null||entity.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      entity.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("jrjBusinessSituationOne", entity);
		return "modules/jrj/tSituationOneList";
	}

	@RequestMapping(value = "form")
	public String form(JrjBusinessSituationOne jrjBusinessSituationOne, Model model) {
		
		String  first = "";
		if(jrjBusinessSituationOne.getCreateTime()==null||jrjBusinessSituationOne.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjBusinessSituationOne.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjBusinessSituationOne", jrjBusinessSituationOne);
		return "modules/jrj/tSituationOneForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjBusinessSituationOne jrjBusinessSituationOne, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjBusinessSituationOne)){
			return form(jrjBusinessSituationOne, model);
		}
		if(jrjBusinessSituationOne.getId() == null || "".equals(jrjBusinessSituationOne.getId())){
			jrjBusinessSituationOne.setReportName(currentUser.getCompany().getName());
			jrjBusinessSituationOne.setOrganId(currentUser.getCompany().getId()); 
			jrjBusinessSituationOne.setCreateBy(currentUser);	
			jrjBusinessSituationOne.setCompanyName(currentUser.getCompany().getName());
			
		}							
		jrjBusinessSituationOne.setScanFlag("0");
		jrjBusinessSituationService.save(jrjBusinessSituationOne);
		addMessage(redirectAttributes, "保存经营情况月报一成功");
		return "redirect:"+Global.getAdminPath()+"/business/situationOne/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjBusinessSituationOne jrjBusinessSituationOne, RedirectAttributes redirectAttributes) {
		jrjBusinessSituationOne.setScanFlag("0");
		jrjBusinessSituationService.delete(jrjBusinessSituationOne);
		addMessage(redirectAttributes, "删除经营情况月报一成功");
		return "redirect:"+Global.getAdminPath()+"/business/situationOne/?repage";
	}

}