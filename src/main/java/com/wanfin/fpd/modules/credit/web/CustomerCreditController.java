/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
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

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 客户授信额度Controller
 * @author zzm
 * @version 2016-07-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/customerCredit")
public class CustomerCreditController extends BaseController {

	@Autowired
	private CustomerCreditService customerCreditService;
	@Autowired
	private CreditApplyService creditApplyService;
	
	@ModelAttribute
	public CustomerCredit get(@RequestParam(required=false) String id) {
		CustomerCredit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerCreditService.get(id);
		}
		if (entity == null){
			entity = new CustomerCredit();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:customerCredit:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerCredit customerCredit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerCredit> page = customerCreditService.findPage(new Page<CustomerCredit>(request, response), customerCredit); 
		model.addAttribute("page", page);
		model.addAttribute("customerCredit", customerCredit);
		String strURL = "modules/credit/customerCreditList";
		String type = request.getParameter("type");
		if(type!=null&&!"".equals(type)){
			if("1".equals(type)){
				strURL = "modules/employee/customerCreditList";
			}else if("2".equals(type)){
				strURL = "modules/company/customerCreditList";
			}
		}
		return strURL;
	}

	@RequiresPermissions("credit:customerCredit:view")
	@RequestMapping(value = "form")
	public String form(CustomerCredit customerCredit, Model model) {
		model.addAttribute("customerCredit", customerCredit);
		return "modules/credit/customerCreditForm";
	}

	@RequiresPermissions("credit:customerCredit:edit")
	@RequestMapping(value = "save")
	public String save(CustomerCredit customerCredit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerCredit)){
			return form(customerCredit, model);
		}
		//判断是否已经有该用户的授信额度记录 add by shirf 20161207
		boolean ifHas = false;
		if(StringUtils.isBlank(customerCredit.getId())){
			CustomerCredit tmpCredit = customerCreditService.getByCustomerId(customerCredit.getCustomerId());
			if(tmpCredit != null ){
				ifHas = true;
			}
		}
		if(ifHas){
			addMessage(redirectAttributes, "授信额度已经存在");
		}else{
			customerCreditService.save(customerCredit);
			addMessage(redirectAttributes, "保存授信额度成功");
		}
		return "redirect:"+Global.getAdminPath()+"/credit/customerCredit/?repage";
	}
	
	@RequiresPermissions("credit:customerCredit:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerCredit customerCredit, RedirectAttributes redirectAttributes) {
		customerCreditService.delete(customerCredit);
		addMessage(redirectAttributes, "删除授信额度成功");
		return "redirect:"+Global.getAdminPath()+"/credit/customerCredit/?repage";
	}
	
	/**
	 * @Description 调用风控接口获取客户的授信额度
	 * @param customerId
	 * @return
	 * @author zzm
	 * @throws ParseException 
	 * @date 2016-7-14 下午3:14:15  
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomerCredit")
	public Map<String, Object> getCustomerCredit(CustomerCredit customerCredit, HttpServletResponse response, Model model) throws ParseException{
		Map<String, Object> result = Maps.newHashMap();
		CustomerCredit credit = customerCreditService.getByCustomerId(customerCredit.getCustomerId());
		if((credit == null) || (customerCredit == null)){
			result.put("status", 0);
			result.put("message", "客户没有授信！");
		}else{
			if((customerCredit.getApplyDate() == null) || (credit.getOverDate() == null)){
				result.put("status", 0);
				result.put("message", "授信申请时间、过期时间为空！");
			}else if(DateUtils.compareTo(DateUtils.formatDate(customerCredit.getApplyDate()), DateUtils.formatDate(credit.getOverDate())) > 0 ){
				result.put("status", 0);
				result.put("message", "授信已过有效期！");
			}else{
				result.put("status", 1);
				result.put("credit", credit.getCredit());
				result.put("balance", credit.getBalance());
			}
		}
		return result;
	}

	 
	/**
	 * @Description 
	 * @param customerId
	 * @return   自动授信
	 * @author lx
	 * @throws ParseException "credit":amount,"customerId":customerId,"applyDate":applyDate
	 * 
	 * 
	 * @date 2016-7-14 下午3:14:15  
	 */
	@ResponseBody
	@RequestMapping(value = "AutoSaveCustomerCredit")
	public Map<String, Object> AutoSaveCustomerCredit(CustomerCredit customerCredit, HttpServletResponse response, Model model) throws ParseException{
		Map<String, Object> result = Maps.newHashMap();
		
		try {
			CreditApply creditApply = new CreditApply();
			
			creditApply.setApplyNum(UserUtils.getSqeNo(Cons.CountCategory.CREDIT_APPLY, "授信"));
			creditApply.setCustomerId(customerCredit.getCustomerId());
			creditApply.setCustomerType("");
			creditApply.setRemarks("自动授信");
			creditApply.setCredit(customerCredit.getCredit());
			creditApply.setExpirationDate(customerCredit.getOverDate());
			creditApply.setStatus(Cons.CreditApplyStatus.CREDIT_SUCCESS);
			creditApplyService.save(creditApply);
			result.put("status", 1);
			result.put("message", "自动授信成功，请重新点击保存！");
		} catch (ServiceException e) {
			result.put("status", 0);
			result.put("message", "授信失败，请联系管理员！");
		}
		
		return result;
	}

	 
}