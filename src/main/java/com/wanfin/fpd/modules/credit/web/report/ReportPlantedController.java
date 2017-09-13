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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;
import com.wanfin.fpd.modules.common.service.tpl.TplPublicService;
import com.wanfin.fpd.modules.credit.entity.report.ReportPlanted;
import com.wanfin.fpd.modules.credit.service.report.ReportPlantedService;

/**
 * 贷款项目调查报告种殖贷Controller
 * @author srf
 * @version 2017-01-16
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/report/reportPlanted")
public class ReportPlantedController extends BaseController {
	@Autowired
	private TplPublicService tplPublicService;//公共模板
	@Autowired
	private ReportPlantedService reportPlantedService;
	
	@ModelAttribute
	public ReportPlanted get(@RequestParam(required=false) String id) {
		ReportPlanted entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportPlantedService.get(id);
		}
		if (entity == null){
			entity = new ReportPlanted();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:report:reportPlanted:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReportPlanted reportPlanted, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportPlanted> page = reportPlantedService.findPage(new Page<ReportPlanted>(request, response), reportPlanted); 
		model.addAttribute("page", page);
		model.addAttribute("reportPlanted", reportPlanted);
		return "modules/credit/report/reportPlantedList";
	}

	@RequiresPermissions("credit:report:reportPlanted:view")
	@RequestMapping(value = "form")
	public String form(ReportPlanted reportPlanted, Model model) {
		model.addAttribute("reportPlanted", reportPlanted);
		return "modules/credit/report/reportPlantedForm";
	}
	
	/*@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String creditApplyId, ReportPlanted reportPlanted, Model model) {
		if(StringUtils.isNotBlank(creditApplyId)){
			reportPlanted = reportPlantedService.getByCreditApply(creditApplyId);
		}
		if(reportPlanted == null || StringUtils.isBlank(reportPlanted.getId())){
			TplPublic tplPublic = tplPublicService.getByCode("nk_zhongzhidai");
			if(tplPublic != null && StringUtils.isNotBlank(tplPublic.getFtlContent())){
				reportPlanted.setFtlContent(tplPublic.getFtlContent());
			}
		}
		model.addAttribute("reportPlanted", reportPlanted);
		return "modules/credit/report/reportPlantedAdd";
	}*/

	@RequiresPermissions("credit:report:reportPlanted:edit")
	@RequestMapping(value = "save")
	public String save(ReportPlanted reportPlanted, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reportPlanted)){
			return form(reportPlanted, model);
		}
		reportPlantedService.save(reportPlanted);
		addMessage(redirectAttributes, "保存贷款项目调查报告种殖贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportPlanted/?repage";
	}
	
	@RequiresPermissions("credit:report:reportPlanted:edit")
	@RequestMapping(value = "delete")
	public String delete(ReportPlanted reportPlanted, RedirectAttributes redirectAttributes) {
		reportPlantedService.delete(reportPlanted);
		addMessage(redirectAttributes, "删除贷款项目调查报告种殖贷成功");
		return "redirect:"+Global.getAdminPath()+"/credit/report/reportPlanted/?repage";
	}

}