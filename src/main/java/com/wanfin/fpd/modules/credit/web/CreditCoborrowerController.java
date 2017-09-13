/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web;

import java.util.List;
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
import com.wanfin.fpd.modules.credit.entity.CreditCoborrower;
import com.wanfin.fpd.modules.credit.service.CreditCoborrowerService;

/**
 * 客户授信_共同借款人Controller
 * @author srf
 * @version 2017-03-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/creditCoborrower")
public class CreditCoborrowerController extends BaseController {

	@Autowired
	private CreditCoborrowerService creditCoborrowerService;
	
	@ModelAttribute
	public CreditCoborrower get(@RequestParam(required=false) String id) {
		CreditCoborrower entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditCoborrowerService.get(id);
		}
		if (entity == null){
			entity = new CreditCoborrower();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditCoborrower:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreditCoborrower creditCoborrower, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreditCoborrower> page = creditCoborrowerService.findPage(new Page<CreditCoborrower>(request, response), creditCoborrower); 
		model.addAttribute("page", page);
		model.addAttribute("creditCoborrower", creditCoborrower);
		return "modules/credit/creditCoborrowerList";
	}

	@RequiresPermissions("credit:creditCoborrower:view")
	@RequestMapping(value = "form")
	public String form(CreditCoborrower creditCoborrower, Model model) {
		model.addAttribute("creditCoborrower", creditCoborrower);
		return "modules/credit/creditCoborrowerForm";
	}

	@RequiresPermissions("credit:creditCoborrower:edit")
	@RequestMapping(value = "save")
	public String save(CreditCoborrower creditCoborrower, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, creditCoborrower)){
			return form(creditCoborrower, model);
		}
		creditCoborrowerService.save(creditCoborrower);
		addMessage(redirectAttributes, "保存共同借款人成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditCoborrower/?repage";
	}
	
	@RequiresPermissions("credit:creditCoborrower:edit")
	@RequestMapping(value = "delete")
	public String delete(CreditCoborrower creditCoborrower, RedirectAttributes redirectAttributes) {
		creditCoborrowerService.delete(creditCoborrower);
		addMessage(redirectAttributes, "删除共同借款人成功");
		return "redirect:"+Global.getAdminPath()+"/credit/creditCoborrower/?repage";
	}
	
	@RequiresPermissions("credit:creditApply:edit")
	@ResponseBody
	@RequestMapping(value = "addJsonCoborrower")
	public Map<String, Object> addJsonCoborrower(CreditCoborrower creditCoborrower, Model model, RedirectAttributes redirectAttributes){
		Map<String, Object> result = Maps.newHashMap();
		
		if(StringUtils.isBlank(creditCoborrower.getCreditApplyId()) || StringUtils.isBlank(creditCoborrower.getCustomerId()) || StringUtils.isBlank(creditCoborrower.getCustomerName()) || StringUtils.isBlank(creditCoborrower.getCustomerType())){
			result.put("status", 0);
			result.put("message", "添加失败");
		}else{
			//判断该共同借款人是否已经存在
			if(creditCoborrowerService.checkCreditCoborrower(creditCoborrower)){
				result.put("status", 4);
				result.put("message", "该共同借款人已经存在");
			}else{
				creditCoborrowerService.save(creditCoborrower);
				List<CreditCoborrower> list = creditCoborrowerService.findAllList(creditCoborrower);
				
				result.put("status", 1);
				result.put("message", "成功");
				result.put("colist", list);//共同借款人
			}
		}
		return result;
	}
	
	@RequiresPermissions("credit:creditApply:edit")
	@ResponseBody
	@RequestMapping(value = "delJsonCoborrower")
	public Map<String, Object> delJsonCoborrower(CreditCoborrower creditCoborrower, Model model, RedirectAttributes redirectAttributes){
		Map<String, Object> result = Maps.newHashMap();
		
		if(StringUtils.isBlank(creditCoborrower.getCreditApplyId()) || StringUtils.isBlank(creditCoborrower.getId())){
			result.put("status", 0);
			result.put("message", "删除参数错误！");
		}else{
			creditCoborrowerService.delete(creditCoborrower);
			List<CreditCoborrower> list = creditCoborrowerService.findAllList(creditCoborrower);
			
			result.put("status", 1);
			result.put("message", "删除成功");
			result.put("colist", list);//共同借款人
		}
		return result;
	}
}