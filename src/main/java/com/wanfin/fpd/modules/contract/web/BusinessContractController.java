/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.contract.entity.BusinessContract;
import com.wanfin.fpd.modules.contract.service.BusinessContractService;

/**
 * 业务合同Controller
 * @author srf
 * @version 2017-02-22
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/businessContract")
public class BusinessContractController extends BaseController {

	@Autowired
	private BusinessContractService businessContractService;
	
	@ModelAttribute
	public BusinessContract get(@RequestParam(required=false) String id) {
		BusinessContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessContractService.get(id);
		}
		if (entity == null){
			entity = new BusinessContract();
		}
		return entity;
	}
	
	//获取某个特定的合同内容
	@ResponseBody
	@RequestMapping(value = "getBusinessContract")
	public Map<String, Object> getBusinessContract(String id){
		Map<String, Object> result = Maps.newHashMap();
		BusinessContract businessContract = null;
		if(StringUtils.isNotBlank(id)){
			businessContract = businessContractService.get(id);
		}
		if(businessContract != null){
			result.put("status", 1);
			result.put("message", "成功");
			result.put("businessContract", businessContract);
		}else{
			result.put("status", 2);
			result.put("message", "没有对应的合同模板");
		}
		
		return result;
	}
	
	
	
	//生成pdf
	@ResponseBody
	@RequestMapping(value = "toCreatePdf")
	public Map<String, Object> toCreatePdf(@RequestParam("fileName") String fileName, @RequestParam("content") String content, @RequestParam("crosswise") String crosswise, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> result = Maps.newHashMap();
		//水印用
		String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
		//System.out.println("==========================");
		//System.out.println(content);
		//System.out.println("==========================");
		if(StringUtils.isBlank(content)){
			result.put("status", 2);
			result.put("message", "没有对应的合同模板");
		}else{
			//生成pdf返回pdf的路径
			String path = businessContractService.CreatePdf(fileName, content, contextpath, crosswise);
			if(StringUtils.isBlank(path)){
				result.put("status", 2);
				result.put("message", "生成pdf失败");
			}else{
				result.put("status", 1);
				result.put("pdfpath", path);
				result.put("message", "成功");
			}
		}
		
		return result;
	}
	
	/**
	 * 通过合同id生成pdf
	 * @param businessContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportBusinessContract")
	public Map<String, Object> exportBusinessContract(BusinessContract businessContract, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> result = Maps.newHashMap();
		//水印用
		String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
		if(businessContract == null || StringUtils.isAnyBlank(businessContract.getId(), businessContract.getContractName(), businessContract.getContractContent())){
			result.put("status", 8);
			result.put("message", "生成pdf失败");
		}else{
			//生成pdf返回pdf的路径
			String path = businessContractService.CreatePdf(businessContract.getContractName(), businessContract.getContractContent(), contextpath, businessContract.getCrosswise());
			if(StringUtils.isBlank(path)){
				result.put("status", 2);
				result.put("message", "生成pdf失败");
			}else{
				result.put("status", 1);
				result.put("pdfpath", path);
				result.put("message", "成功");
			}
		}
		return result;
	}
	
	@RequiresPermissions("contract:businessContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessContract businessContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessContract> page = businessContractService.findPage(new Page<BusinessContract>(request, response), businessContract); 
		model.addAttribute("page", page);
		model.addAttribute("businessContract", businessContract);
		return "modules/contract/businessContractList";
	}

	@RequiresPermissions("contract:businessContract:view")
	@RequestMapping(value = "form")
	public String form(BusinessContract businessContract, Model model) {
		model.addAttribute("businessContract", businessContract);
		return "modules/contract/businessContractForm";
	}

	@RequiresPermissions("contract:businessContract:edit")
	@RequestMapping(value = "save")
	public String save(BusinessContract businessContract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessContract)){
			return form(businessContract, model);
		}
		businessContractService.save(businessContract);
		addMessage(redirectAttributes, "保存业务合同成功");
		return "redirect:"+Global.getAdminPath()+"/contract/businessContract/?repage";
	}
	
	@RequiresPermissions("contract:businessContract:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessContract businessContract, RedirectAttributes redirectAttributes) {
		businessContractService.delete(businessContract);
		addMessage(redirectAttributes, "删除业务合同成功");
		return "redirect:"+Global.getAdminPath()+"/contract/businessContract/?repage";
	}

}