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
import com.wanfin.fpd.modules.credit.entity.report.ReportCompany;
import com.wanfin.fpd.modules.credit.service.report.ReportCompanyService;

/**
 * 贷款项目调查报告企业贷Controller
 * @author srf
 * @version 2017-01-23
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/report/reportCompany")
public class ReportCompanyController extends BaseController {

	@Autowired
	private ReportCompanyService reportCompanyService;
	
	@ModelAttribute
	public ReportCompany get(@RequestParam(required=false) String id) {
		ReportCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportCompanyService.get(id);
		}
		if (entity == null){
			entity = new ReportCompany();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:report:reportCompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportCompany reportCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportCompany> page = reportCompanyService.findPage(new Page<ReportCompany>(request, response), reportCompany); 
		model.addAttribute("page", page);
		model.addAttribute("reportCompany", reportCompany);
		return "modules/credit/report/reportCompanyList";
	}

	@RequiresPermissions("credit:report:reportCompany:view")
	@RequestMapping(value = "form")
	public String form(ReportCompany reportCompany, Model model) {
		model.addAttribute("reportCompany", reportCompany);
		return "modules/credit/report/reportCompanyForm";
	}

	@RequiresPermissions("credit:report:reportCompany:edit")
	@RequestMapping(value = "save")
	public String save(ReportCompany reportCompany, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportCompany)){
			return form(reportCompany, model);
		}
		reportCompanyService.save(reportCompany);
		addMessage(redirectAttributes, "保存贷款项目调查报告企业贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportCompany/?repage";
	}
	
	@RequiresPermissions("credit:report:reportCompany:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportCompany reportCompany, RedirectAttributes redirectAttributes) {
		reportCompanyService.delete(reportCompany);
		addMessage(redirectAttributes, "删除贷款项目调查报告企业贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportCompany/?repage";
	}

}