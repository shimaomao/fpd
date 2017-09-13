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
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;
import com.wanfin.fpd.modules.common.service.tpl.TplPublicService;
import com.wanfin.fpd.modules.credit.entity.report.ReportCulture;
import com.wanfin.fpd.modules.credit.service.report.ReportCultureService;

/**
 * 贷款项目调查报告养殖贷Controller
 * @author srf
 * @version 2017-01-16
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/report/reportCulture")
public class ReportCultureController extends BaseController {
	//@Autowired
	//private TplPublicService tplPublicService;//公共模板
	@Autowired
	private ReportCultureService reportCultureService;
	
	@ModelAttribute
	public ReportCulture get(@RequestParam(required=false) String id) {
		ReportCulture entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportCultureService.get(id);
		}
		if (entity == null){
			entity = new ReportCulture();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:report:reportCulture:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportCulture reportCulture, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportCulture> page = reportCultureService.findPage(new Page<ReportCulture>(request, response), reportCulture); 
		model.addAttribute("page", page);
		model.addAttribute("reportCulture", reportCulture);
		return "modules/credit/report/reportCultureList";
	}

	@RequiresPermissions("credit:report:reportCulture:view")
	@RequestMapping(value = "form")
	public String form(ReportCulture reportCulture, Model model) {
		model.addAttribute("reportCulture", reportCulture);
		return "modules/credit/report/reportCultureForm";
	}

	/*@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String creditApplyId, ReportCulture reportCulture, Model model) {
		if(StringUtils.isNotBlank(creditApplyId)){
			reportCulture = reportCultureService.getByCreditApply(creditApplyId);
		}
		if(reportCulture == null || StringUtils.isBlank(reportCulture.getId())){
			TplPublic tplPublic = tplPublicService.getByCode("nk_yangzhidai");
			if(tplPublic != null && StringUtils.isNotBlank(tplPublic.getFtlContent())){
				reportCulture.setFtlContent(tplPublic.getFtlContent());
			}
		}
		model.addAttribute("reportCulture", reportCulture);
		return "modules/credit/report/reportCultureAdd";
	}*/
	
	@RequiresPermissions("credit:report:reportCulture:edit")
	@RequestMapping(value = "save")
	public String save(ReportCulture reportCulture, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportCulture)){
			return form(reportCulture, model);
		}
		reportCultureService.save(reportCulture);
		addMessage(redirectAttributes, "保存贷款项目调查报告养殖贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportCulture/?repage";
	}
	
	@RequiresPermissions("credit:report:reportCulture:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportCulture reportCulture, RedirectAttributes redirectAttributes) {
		reportCultureService.delete(reportCulture);
		addMessage(redirectAttributes, "删除贷款项目调查报告养殖贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportCulture/?repage";
	}

}