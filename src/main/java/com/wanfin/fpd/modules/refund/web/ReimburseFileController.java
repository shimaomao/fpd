/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.refund.entity.ReimburseFile;
import com.wanfin.fpd.modules.refund.service.ReimburseFileService;

/**
 * 申请退款上传文件Controller
 * @author srf
 * @version 2016-04-06
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/refund/reimburseFile")
public class ReimburseFileController extends BaseController {

	@Autowired
	private ReimburseFileService reimburseFileService;
	
	@ModelAttribute
	public ReimburseFile get(@RequestParam(required=false) String id) {
		ReimburseFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reimburseFileService.get(id);
		}
		if (entity == null){
			entity = new ReimburseFile();
		}
		return entity;
	}
	
	@RequiresPermissions("refund:reimburseFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReimburseFile reimburseFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReimburseFile> page = reimburseFileService.findPage(new Page<ReimburseFile>(request, response), reimburseFile); 
		model.addAttribute("page", page);
		model.addAttribute("reimburseFile", reimburseFile);
		return "modules/refund/reimburseFileList";
	}

	@RequiresPermissions("refund:reimburseFile:view")
	@RequestMapping(value = "form")
	public String form(ReimburseFile reimburseFile, Model model) {
		model.addAttribute("reimburseFile", reimburseFile);
		return "modules/refund/reimburseFileForm";
	}

	@RequestMapping(value="upload", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> upload(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title,
			@RequestParam("reimburseId") String reimburseId,
			@RequestParam("type") String type) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		try{
			reimburseFileService.uploadFile(reimburseId, type, title, file, request);
		} catch (IOException e) {
			map.put("status", 0);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	@RequiresPermissions("refund:reimburseFile:edit")
	@RequestMapping(value = "save")
	public String save(ReimburseFile reimburseFile, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, reimburseFile)){
			return form(reimburseFile, model);
		}
		reimburseFileService.save(reimburseFile);
		addMessage(redirectAttributes, "保存申请退款上传文件成功");
		return "redirect:"+Global.getAdminPath()+"/refund/reimburseFile/?repage";
	}
	
	@RequiresPermissions("refund:reimburseFile:edit")
	@RequestMapping(value = "delete")
	public String delete(ReimburseFile reimburseFile, RedirectAttributes redirectAttributes) {
		reimburseFileService.delete(reimburseFile);
		addMessage(redirectAttributes, "删除申请退款上传文件成功");
		return "redirect:"+Global.getAdminPath()+"/refund/reimburseFile/?repage";
	}

}