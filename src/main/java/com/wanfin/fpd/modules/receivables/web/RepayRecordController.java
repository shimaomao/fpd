/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.web;

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
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;

/**
 * 真实还款记录Controller
 * @author srf
 * @version 2016-03-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/receivables/repayRecord")
public class RepayRecordController extends BaseController {

	@Autowired
	private RepayRecordService repayRecordService;
	
	@ModelAttribute
	public RepayRecord get(@RequestParam(required=false) String id) {
		RepayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = repayRecordService.get(id);
		}
		if (entity == null){
			entity = new RepayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("receivables:repayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(RepayRecord repayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RepayRecord> page = repayRecordService.findPage(new Page<RepayRecord>(request, response), repayRecord); 
		model.addAttribute("page", page);
		model.addAttribute("repayRecord", repayRecord);
		return "modules/receivables/repayRecordList";
	}

	@RequiresPermissions("receivables:repayRecord:view")
	@RequestMapping(value = "form")
	public String form(RepayRecord repayRecord, Model model) {
		model.addAttribute("repayRecord", repayRecord);
		return "modules/receivables/repayRecordForm";
	}

	@RequiresPermissions("receivables:repayRecord:edit")
	@RequestMapping(value = "save")
	public String save(RepayRecord repayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, repayRecord)){
			return form(repayRecord, model);
		}
		repayRecordService.save(repayRecord);
		addMessage(redirectAttributes, "保存真实还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/receivables/repayRecord/?repage";
	}
	
	@RequiresPermissions("receivables:repayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(RepayRecord repayRecord, RedirectAttributes redirectAttributes) {
		repayRecordService.delete(repayRecord);
		addMessage(redirectAttributes, "删除真实还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/receivables/repayRecord/?repage";
	}

}