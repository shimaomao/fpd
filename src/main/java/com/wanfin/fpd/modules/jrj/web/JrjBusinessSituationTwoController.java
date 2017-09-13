/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationTwo;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessSituationTwoService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 经营情况月报表二Controller
 * @author xzt
 * @version 2016-11-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/business/situationTwo")
public class JrjBusinessSituationTwoController extends BaseController {

	@Autowired
	private JrjBusinessSituationTwoService jrjBusinessSituationService;
	
	@ModelAttribute
	public JrjBusinessSituationTwo get(@RequestParam(required=false) String id) {
		JrjBusinessSituationTwo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjBusinessSituationService.get(id);
		}
		if (entity == null){
			entity = new JrjBusinessSituationTwo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjBusinessSituationTwo entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjBusinessSituationTwo> page = jrjBusinessSituationService.findPage(new Page<JrjBusinessSituationTwo>(request, response), entity); 
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
		model.addAttribute("jrjBusinessSituationTwo", entity);
		return "modules/jrj/tSituationTwoList";
	}

	@RequestMapping(value = "form")
	public String form(JrjBusinessSituationTwo jrjBusinessSituationTwo, Model model) {
		
		String  first = "";
		if(jrjBusinessSituationTwo.getCreateTime()==null||jrjBusinessSituationTwo.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjBusinessSituationTwo.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjBusinessSituationTwo", jrjBusinessSituationTwo);
		return "modules/jrj/tSituationTwoForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjBusinessSituationTwo jrjBusinessSituationTwo, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjBusinessSituationTwo)){
			return form(jrjBusinessSituationTwo, model);
		}
		if(jrjBusinessSituationTwo.getId() == null || "".equals(jrjBusinessSituationTwo.getId())){
			jrjBusinessSituationTwo.setReportName(currentUser.getCompany().getName());
			jrjBusinessSituationTwo.setOrganId(currentUser.getCompany().getId()); 
			jrjBusinessSituationTwo.setCreateBy(currentUser);	
			jrjBusinessSituationTwo.setCompanyName(currentUser.getCompany().getName());
			
		}							
		jrjBusinessSituationTwo.setScanFlag("0");
		jrjBusinessSituationService.save(jrjBusinessSituationTwo);
		addMessage(redirectAttributes, "保存经营情况月报一成功");
		return "redirect:"+Global.getAdminPath()+"/business/situationTwo/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjBusinessSituationTwo jrjBusinessSituationTwo, RedirectAttributes redirectAttributes) {
		jrjBusinessSituationTwo.setScanFlag("0");
		jrjBusinessSituationService.delete(jrjBusinessSituationTwo);
		addMessage(redirectAttributes, "删除经营情况月报一成功");
		return "redirect:"+Global.getAdminPath()+"/business/situationTwo/?repage";
	}

}