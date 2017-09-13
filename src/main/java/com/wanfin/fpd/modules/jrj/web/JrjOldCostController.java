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
import com.wanfin.fpd.modules.jrj.entity.JrjOldCost;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjOldCostService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 费用表Controller
 * @author xzt
 * @version 2016-05-17
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/cost/oldCost")
public class JrjOldCostController extends BaseController {

	@Autowired
	private JrjOldCostService jrjOldCostService;
	
	@ModelAttribute
	public JrjOldCost get(@RequestParam(required=false) String id) {
		JrjOldCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjOldCostService.get(id);
		}
		if (entity == null){
			entity = new JrjOldCost();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjOldCost jrjOldCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JrjOldCost> page = jrjOldCostService.findPage(new Page<JrjOldCost>(request, response), jrjOldCost); 
		String  first = "";
		if(jrjOldCost.getSubmitDate()==null||jrjOldCost.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjOldCost.setSubmitDate(first);
		}		
		
		model.addAttribute("page", page);
		model.addAttribute("jrjOldCost", jrjOldCost);
		return "modules/jrj/tOldCostList";
	}

	@RequestMapping(value = "form")
	public String form(JrjOldCost jrjOldCost, Model model) {
		
		String  first = "";
		if(jrjOldCost.getCreateTime()==null||jrjOldCost.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjOldCost.setCreateTime(first);
		}
		User user = UserUtils.getUser();
		Office company = user.getCompany();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		jrjOldCost.setCompanyName(company.getName());
		model.addAttribute("createTime", format.format(new Date()));
		model.addAttribute("officeName", company.getName());		
		model.addAttribute("jrjOldCost", jrjOldCost);
		return "modules/jrj/tOldCostForm";
	}

	@RequestMapping(value = "save")
	public String save(JrjOldCost jrjOldCost, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, jrjOldCost)){
			return form(jrjOldCost, model);
		}
		if(jrjOldCost.getId() == null || "".equals(jrjOldCost.getId())){
			jrjOldCost.setReportName(currentUser.getCompany().getName());
			jrjOldCost.setOrganId(currentUser.getCompany().getId()); 
			jrjOldCost.setCompanyId(currentUser.getCompany().getId());
			jrjOldCost.setCreateBy(currentUser);	
			jrjOldCost.setCompanyName(currentUser.getCompany().getName());
		}					
		jrjOldCost.setScanFlag("0");
		jrjOldCostService.save(jrjOldCost);
		addMessage(redirectAttributes, "保存资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/cost/oldCost/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjOldCost jrjOldCost, RedirectAttributes redirectAttributes) {
		jrjOldCost.setScanFlag("0");
		jrjOldCostService.delete(jrjOldCost);
		addMessage(redirectAttributes, "删除资产负债成功");
		return "redirect:"+Global.getAdminPath()+"/cost/oldCost/?repage";
	}

}