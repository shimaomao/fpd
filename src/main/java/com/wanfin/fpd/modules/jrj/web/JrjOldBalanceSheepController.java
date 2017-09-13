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
import com.wanfin.fpd.modules.jrj.entity.JrjOldBalanceSheep;
import com.wanfin.fpd.modules.jrj.service.JrjOldBalanceSheepService;
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
@RequestMapping(value = "${adminPath}/balancesheep/oldJrjBalanceSheep")
public class JrjOldBalanceSheepController extends BaseController {

	@Autowired
	private JrjOldBalanceSheepService jrjBalanceSheepService;
	
	@ModelAttribute
	public JrjOldBalanceSheep get(@RequestParam(required=false) String id) {
		JrjOldBalanceSheep entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjBalanceSheepService.get(id);
		}
		if (entity == null){
			entity = new JrjOldBalanceSheep();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjOldBalanceSheep tBalanceSheep, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjOldBalanceSheep> page = jrjBalanceSheepService.findPage(new Page<JrjOldBalanceSheep>(request, response), tBalanceSheep); 
		String  first = "";
		if(tBalanceSheep.getSubmitDate()==null||tBalanceSheep.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      tBalanceSheep.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("tBalanceSheep", tBalanceSheep);
		return "modules/jrj/tOldBalanceSheepList";
	}

	@RequestMapping(value = "form")
	public String form(JrjOldBalanceSheep tBalanceSheep, Model model) {
		
		String  first = "";
		if(tBalanceSheep.getCreateTime()==null||tBalanceSheep.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    tBalanceSheep.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		tBalanceSheep.setCompanyName(company.getName());
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("tBalanceSheep", tBalanceSheep);
		return "modules/jrj/tOldBalanceSheepForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjOldBalanceSheep tBalanceSheep, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, tBalanceSheep)){
			return form(tBalanceSheep, model);
		}
		if(tBalanceSheep.getId() == null || "".equals(tBalanceSheep.getId())){
			tBalanceSheep.setReportName(currentUser.getCompany().getName());
			tBalanceSheep.setOrganId(currentUser.getCompany().getId()); 
			tBalanceSheep.setCompanyId(currentUser.getCompany().getId());
			tBalanceSheep.setCreateBy(currentUser);	
			tBalanceSheep.setCompanyName(currentUser.getCompany().getName());
		}					
		tBalanceSheep.setScanFlag("0");
		jrjBalanceSheepService.save(tBalanceSheep);
		addMessage(redirectAttributes, "保存旧资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/balancesheep/oldJrjBalanceSheep/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjOldBalanceSheep tBalanceSheep, RedirectAttributes redirectAttributes) {
		tBalanceSheep.setScanFlag("0");
		jrjBalanceSheepService.delete(tBalanceSheep);
		addMessage(redirectAttributes, "删除旧资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/balancesheep/oldJrjBalanceSheep/?repage";
	}

}