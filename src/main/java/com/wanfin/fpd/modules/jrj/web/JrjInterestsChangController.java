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
import com.wanfin.fpd.modules.jrj.entity.JrjInterestsChang;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjInterestsChangService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 权益变动表Controller
 * @author xzt
 * @version 2016-11-23
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/interests/chang")
public class JrjInterestsChangController extends BaseController {

	@Autowired
	private JrjInterestsChangService jrjIntestsChangService;
	
	@ModelAttribute
	public JrjInterestsChang get(@RequestParam(required=false) String id) {
		JrjInterestsChang entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjIntestsChangService.get(id);
		}
		if (entity == null){
			entity = new JrjInterestsChang();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjInterestsChang entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjInterestsChang> page = jrjIntestsChangService.findPage(new Page<JrjInterestsChang>(request, response), entity); 
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
		model.addAttribute("jrjInterestsChang", entity);
		return "modules/jrj/tInterestsChangList";
	}

	@RequestMapping(value = "form")
	public String form(JrjInterestsChang entity, Model model) {
		
		String  first = "";
		if(entity.getCreateTime()==null||entity.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    entity.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		entity.setCompanyName(company.getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjInterestsChang", entity);
		return "modules/jrj/tInterestsChangForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjInterestsChang jrjInterestsChang, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjInterestsChang)){
			return form(jrjInterestsChang, model);
		}
		if(jrjInterestsChang.getId() == null || "".equals(jrjInterestsChang.getId())){
			jrjInterestsChang.setReportName(currentUser.getCompany().getName());
			jrjInterestsChang.setOrganId(currentUser.getCompany().getId()); 
			jrjInterestsChang.setCreateBy(currentUser);	
			jrjInterestsChang.setCompanyName(currentUser.getCompany().getName());
		}					
		jrjInterestsChang.setScanFlag("0");
		jrjIntestsChangService.save(jrjInterestsChang);
		addMessage(redirectAttributes, "保存利润成功");
		return "redirect:"+Global.getAdminPath()+"/interests/chang/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjInterestsChang jrjInterestsChang, RedirectAttributes redirectAttributes) {
		jrjIntestsChangService.delete(jrjInterestsChang);
		addMessage(redirectAttributes, "删除利润表成功");
		return "redirect:"+Global.getAdminPath()+"/interests/chang/?repage";
	}

}