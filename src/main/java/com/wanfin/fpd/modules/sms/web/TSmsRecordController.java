/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.web;

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
import com.wanfin.fpd.modules.sms.entity.TSmsRecord;
import com.wanfin.fpd.modules.sms.service.TSmsRecordService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * smsController
 * @author sms
 * @version 2016-07-25
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sms/tSmsRecord")
public class TSmsRecordController extends BaseController {

	@Autowired
	private TSmsRecordService tSmsRecordService;
	
	@ModelAttribute
	public TSmsRecord get(@RequestParam(required=false) String id) {
		TSmsRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tSmsRecordService.get(id);
		}
		if (entity == null){
			entity = new TSmsRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:tSmsRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(TSmsRecord tSmsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TSmsRecord> page = tSmsRecordService.findPage(new Page<TSmsRecord>(request, response), tSmsRecord); 
		model.addAttribute("page", page);
		model.addAttribute("tSmsRecord", tSmsRecord);
		return "modules/sms/tSmsRecordList";
	}

	@RequiresPermissions("sms:tSmsRecord:view")
	@RequestMapping(value = "form")
	public String form(TSmsRecord tSmsRecord, Model model) {
		model.addAttribute("tSmsRecord", tSmsRecord);
		return "modules/sms/tSmsRecordForm";
	}

	@RequiresPermissions("sms:tSmsRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(TSmsRecord tSmsRecord, RedirectAttributes redirectAttributes) {
		tSmsRecordService.delete(tSmsRecord);
		addMessage(redirectAttributes, "删除sms成功");
		return "redirect:"+Global.getAdminPath()+"/sms/tSmsRecord/?repage";
	}

}