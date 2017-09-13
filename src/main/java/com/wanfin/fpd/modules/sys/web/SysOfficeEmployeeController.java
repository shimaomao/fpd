/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import java.io.IOException;

import javax.servlet.ServletException;
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

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.servlet.ValidateCodeServlet;
import com.wanfin.fpd.common.utils.StringUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.sys.entity.SysOfficeEmployee;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SysOfficeEmployeeService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 从业人员Controller
 * @author kewenxiu
 * @version 2017-03-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOfficeEmployee")
public class SysOfficeEmployeeController extends BaseController {

	@Autowired
	private SysOfficeEmployeeService sysOfficeEmployeeService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public SysOfficeEmployee get(@RequestParam(required=false) String id) {
		SysOfficeEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOfficeEmployeeService.get(id);
		}
		if (entity == null){
			entity = new SysOfficeEmployee();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysOfficeEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOfficeEmployee sysOfficeEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Boolean isNull = false;
		User user = UserUtils.getUser();
		if(StringUtils.isBlank(sysOfficeEmployee.getPid())){
			//普通用户
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				sysOfficeEmployee.setPid(user.getCompany().getId());
			}else{
				model.addAttribute("page", new Page<SysOfficeEmployee>());
				isNull = true;
			}
		}
		if(!isNull){
			Page<SysOfficeEmployee> page = sysOfficeEmployeeService.findPage(new Page<SysOfficeEmployee>(request, response), sysOfficeEmployee); 
			model.addAttribute("page", page);
			model.addAttribute("office", officeService.get(sysOfficeEmployee.getPid()));
		}
		model.addAttribute("user", user);
		model.addAttribute("sysOfficeEmployee", sysOfficeEmployee);
		return "modules/sys/sysOfficeEmployeeList";
	}

	/*@RequiresPermissions("sys:sysOfficeEmployee:view")*/
	@RequestMapping(value = "form")
	public String form(SysOfficeEmployee sysOfficeEmployee, Model model) {
		if(StringUtils.isBlank(sysOfficeEmployee.getPid())){
			//设置机构ID
			User user = UserUtils.getUser();
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				sysOfficeEmployee.setPid(user.getCompany().getId());
			}
		}
		model.addAttribute("sysOfficeEmployee", sysOfficeEmployee);
		return "modules/sys/sysOfficeEmployeeForm";
	}

	/*@RequiresPermissions("sys:sysOfficeEmployee:edit")*/
	@RequestMapping(value = "save")
	public String save(SysOfficeEmployee sysOfficeEmployee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOfficeEmployee)){
			return form(sysOfficeEmployee, model);
		}
		sysOfficeEmployeeService.save(sysOfficeEmployee);
		addMessage(redirectAttributes, "保存从业人员成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficeEmployee/?repage";
	}
	
	@RequiresPermissions("sys:sysOfficeEmployee:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOfficeEmployee sysOfficeEmployee, RedirectAttributes redirectAttributes) {
		sysOfficeEmployeeService.delete(sysOfficeEmployee);
		addMessage(redirectAttributes, "删除从业人员成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficeEmployee/?repage";
	}

}