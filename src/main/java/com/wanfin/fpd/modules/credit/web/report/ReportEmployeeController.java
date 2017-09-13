/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web.report;

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
import com.wanfin.fpd.common.utils.StringUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.credit.entity.report.ReportEmployee;
import com.wanfin.fpd.modules.credit.service.report.ReportEmployeeService;

/**
 * 贷款项目调查报告职工信用贷Controller
 * @author srf
 * @version 2017-01-23
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/report/reportEmployee")
public class ReportEmployeeController extends BaseController {

	@Autowired
	private ReportEmployeeService reportEmployeeService;
	
	@ModelAttribute
	public ReportEmployee get(@RequestParam(required=false) String id) {
		ReportEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportEmployeeService.get(id);
		}
		if (entity == null){
			entity = new ReportEmployee();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:report:reportEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportEmployee reportEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportEmployee> page = reportEmployeeService.findPage(new Page<ReportEmployee>(request, response), reportEmployee); 
		model.addAttribute("page", page);
		model.addAttribute("reportEmployee", reportEmployee);
		return "modules/credit/report/reportEmployeeList";
	}

	@RequiresPermissions("credit:report:reportEmployee:view")
	@RequestMapping(value = "form")
	public String form(ReportEmployee reportEmployee, Model model) {
		model.addAttribute("reportEmployee", reportEmployee);
		return "modules/credit/report/reportEmployeeForm";
	}

	@RequiresPermissions("credit:report:reportEmployee:edit")
	@RequestMapping(value = "save")
	public String save(ReportEmployee reportEmployee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportEmployee)){
			return form(reportEmployee, model);
		}
		reportEmployeeService.save(reportEmployee);
		addMessage(redirectAttributes, "保存贷款项目调查报告职工信用贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportEmployee/?repage";
	}
	
	@RequiresPermissions("credit:report:reportEmployee:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportEmployee reportEmployee, RedirectAttributes redirectAttributes) {
		reportEmployeeService.delete(reportEmployee);
		addMessage(redirectAttributes, "删除贷款项目调查报告职工信用贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportEmployee/?repage";
	}

}