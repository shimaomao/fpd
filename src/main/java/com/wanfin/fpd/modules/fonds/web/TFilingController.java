/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.fonds.web;

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
import com.wanfin.fpd.modules.fonds.entity.TFiling;
import com.wanfin.fpd.modules.fonds.service.TFilingService;

/**
 * 资料归档Controller
 * @author lx
 * @version 2016-06-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/fonds/tFiling")
public class TFilingController extends BaseController {

	@Autowired
	private TFilingService tFilingService;
	
	@ModelAttribute
	public TFiling get(@RequestParam(required=false) String id) {
		TFiling entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tFilingService.get(id);
		}
		if (entity == null){
			entity = new TFiling();
		}
		return entity;
	}
	
	@RequiresPermissions("fonds:tFiling:view")
	@RequestMapping(value = {"list", ""})
	public String list(TFiling tFiling, HttpServletRequest request, HttpServletResponse response, Model model) {
		String loanId = request.getParameter("loancontractId");
		tFiling.setLoancontractId(loanId);
		model.addAttribute("loancontractId", loanId);
		Page<TFiling> page = tFilingService.findPage(new Page<TFiling>(request, response), tFiling); 
		model.addAttribute("page", page);
		model.addAttribute("tFiling", tFiling);
		return "modules/fonds/tFilingList";
	}

	@RequiresPermissions("fonds:tFiling:view")
	@RequestMapping(value = "form")
	public String form(TFiling tFiling, Model model,HttpServletRequest request) {
		String loan_contractid = request.getParameter("loancontractId");
		System.out.println(loan_contractid);            
		tFiling.setLoancontractId(loan_contractid);
		model.addAttribute("tFiling", tFiling);
		return "modules/fonds/tFilingForm";
	}

	@RequiresPermissions("fonds:tFiling:edit")
	@RequestMapping(value = "save")
	public String save(TFiling tFiling, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String loan_contractid = request.getParameter("loancontractId");
		if(loan_contractid!=null&&!"".equals(loan_contractid)){
			tFiling.setLoancontractId(loan_contractid);
		}
		if (!beanValidator(model, tFiling)){
			return form(tFiling, model,request);
		}
		tFilingService.save(tFiling);
		addMessage(redirectAttributes, "保存资料归档成功");
		return "redirect:"+Global.getAdminPath()+"/fonds/tFiling/?repage&loancontractId="+loan_contractid;
	}
	
	@RequiresPermissions("fonds:tFiling:edit")
	@RequestMapping(value = "delete")
	public String delete(TFiling tFiling, RedirectAttributes redirectAttributes) {
        String loancontractId=tFiling.getLoancontractId();
		tFilingService.delete(tFiling);
		addMessage(redirectAttributes, "删除资料归档成功");
		return "redirect:"+Global.getAdminPath()+"/fonds/tFiling/?repage&loancontractId="+loancontractId;
	}

}