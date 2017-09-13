/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.hastenrepay.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.hastenrepay.entity.HastenRepayRecord;
import com.wanfin.fpd.modules.hastenrepay.service.HastenRepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 催收记录Controller
 * @author zzm
 * @version 2016-06-08
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/hastenrepay/hastenRepayRecord")
public class HastenRepayRecordController extends BaseController {

	@Autowired
	private HastenRepayRecordService hastenRepayRecordService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@ModelAttribute
	public HastenRepayRecord get(@RequestParam(required=false) String id) {
		HastenRepayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hastenRepayRecordService.get(id);
		}
		if (entity == null){
			entity = new HastenRepayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("hastenrepay:hastenRepayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(HastenRepayRecord hastenRepayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HastenRepayRecord> page = hastenRepayRecordService.findPage(new Page<HastenRepayRecord>(request, response), hastenRepayRecord); 
		model.addAttribute("page", page);
		model.addAttribute("hastenRepayRecord", hastenRepayRecord);
		return "modules/hastenrepay/hastenRepayRecordList";
	}

	@RequiresPermissions("hastenrepay:hastenRepayRecord:view")
	@RequestMapping(value = "form")
	public String form(HastenRepayRecord hastenRepayRecord, Model model) {
		TLoanContract contract = tLoanContractService.get(hastenRepayRecord.getContractId());
		if(StringUtils.isBlank(hastenRepayRecord.getId()) && contract != null){
			hastenRepayRecord.setBorrower(contract.getCustomerName());
		}
		model.addAttribute("contract", contract);
		model.addAttribute("hastenRepayRecord", hastenRepayRecord);
		return "modules/hastenrepay/hastenRepayRecordForm";
	}

	@RequiresPermissions("hastenrepay:hastenRepayRecord:edit")
	@RequestMapping(value = "save")
	public String save(HastenRepayRecord hastenRepayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hastenRepayRecord)){
			return form(hastenRepayRecord, model);
		}
		hastenRepayRecordService.save(hastenRepayRecord);
		addMessage(redirectAttributes, "保存催收记录成功");
		return "redirect:"+Global.getAdminPath()+"/hastenrepay/hastenRepayRecord/?repage&contractId="+hastenRepayRecord.getContractId();
	}
	
	@RequiresPermissions("hastenrepay:hastenRepayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(HastenRepayRecord hastenRepayRecord, RedirectAttributes redirectAttributes) {
		hastenRepayRecordService.delete(hastenRepayRecord);
		addMessage(redirectAttributes, "删除催收记录成功");
		return "redirect:"+Global.getAdminPath()+"/hastenrepay/hastenRepayRecord/?repage";
	}

	@RequiresPermissions("hastenrepay:hastenRepayRecord:view")
	@RequestMapping(value = "overdueList")
	public String overdueList(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TLoanContract> page = tLoanContractService.findOverduePage(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		return "modules/hastenrepay/overdueList";
	}
	
	

	@RequestMapping(value = "repaywarn")
	public String repaywarn(String id, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		TLoanContract tLoanContract = tLoanContractService.get(id);
		
		TRepayPlan tRepayPlan = new TRepayPlan();
		
		tRepayPlan.setProductid((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		tRepayPlan.setLoanContractId(id);
		List<TRepayPlan> repaylist  =  tRepayPlanService.findList(tRepayPlan);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String accountDate = "";
		float money = 0;
		int day = 0;
		float accrue = 0;
		for (int i = 0; i < repaylist.size(); i++) {
			TRepayPlan repayplan = repaylist.get(i);
			if("0".equals(repayplan.getStatus())||"1".equals(repayplan.getStatus())){//未还、未结清的进入。
				
				String interes = repayplan.getInterestReal();
				interes = (interes==null||"".equals(interes))?"0":interes;
				
				String Principal = repayplan.getPrincipalReal();
				Principal = (Principal==null||"".equals(Principal))?"0":Principal;
				
				accountDate = repayplan.getAccountDate();
				money = Float.parseFloat(repayplan.getInterest())+ Float.parseFloat(repayplan.getPrincipal())-Float.parseFloat(interes)-Float.parseFloat(Principal);
				day = daysBetween(accountDate,sdf.format(new Date()));
				accrue = Float.parseFloat(tLoanContract.getLoanAmount())*day*Float.parseFloat(tLoanContract.getLatePenalty())/100;
				break;//只需获取第一笔已经逾期的，后面的逾期等当笔收完之后再计算
			}
		}
		model.addAttribute("repayplanList",repaylist);
		if("1".equals(tLoanContract.getCustomerType())){
			TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
			model.addAttribute("company",company);
		}else if("2".equals(tLoanContract.getCustomerType())){
			TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
			model.addAttribute("employee",employee);
		}                                        
		String overwarn = Cons.Msg.MSG_OVER_WARN;
		if(overwarn.contains("#number#") && overwarn.contains("#date#") && overwarn.contains("#money#") && overwarn.contains("#day#") && overwarn.contains("#accrue#")){
			overwarn = overwarn.replace("#number#",tLoanContract.getContractNumber());
			overwarn = overwarn.replace("#date#",accountDate);
			overwarn = overwarn.replace("#money#",money+"");
			overwarn = overwarn.replace("#day#",day+"");
			overwarn = overwarn.replace("#accrue#",accrue+"");
		}
		
		model.addAttribute("overwarn",overwarn);
		model.addAttribute("tLoanContract",tLoanContract);
		return "modules/hastenrepay/overwarn";
	}
	
	
	/***仲裁 提醒***/
	@RequestMapping(value = "zhongCaiSms")
	public String zhongCaiSms(String id, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		TLoanContract tLoanContract = tLoanContractService.get(id);
		
		TRepayPlan tRepayPlan = new TRepayPlan();
		
		tRepayPlan.setProductid((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		tRepayPlan.setLoanContractId(id);
		List<TRepayPlan> repaylist  =  tRepayPlanService.findList(tRepayPlan);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String accountDate = "";
		float money = 0;
		int day = 0;
		float accrue = 0;
		for (int i = 0; i < repaylist.size(); i++) {
			TRepayPlan repayplan = repaylist.get(i);
			if("0".equals(repayplan.getStatus())||"1".equals(repayplan.getStatus())){//未还、未结清的进入。
				
				String interes = repayplan.getInterestReal();
				interes = (interes==null||"".equals(interes))?"0":interes;
				
				String Principal = repayplan.getPrincipalReal();
				Principal = (Principal==null||"".equals(Principal))?"0":Principal;
				
				accountDate = repayplan.getAccountDate();
				money = Float.parseFloat(repayplan.getInterest())+ Float.parseFloat(repayplan.getPrincipal())-Float.parseFloat(interes)-Float.parseFloat(Principal);
				day = daysBetween(accountDate,sdf.format(new Date()));
				accrue = Float.parseFloat(tLoanContract.getLoanAmount())*day*Float.parseFloat(tLoanContract.getLatePenalty())/100;
				break;//只需获取第一笔已经逾期的，后面的逾期等当笔收完之后再计算
			}
		}
		model.addAttribute("repayplanList",repaylist);
		if("1".equals(tLoanContract.getCustomerType())){
			TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
			model.addAttribute("company",company);
		}else if("2".equals(tLoanContract.getCustomerType())){
			TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
			model.addAttribute("employee",employee);
		}                                        
		String tempSms = Cons.Msg.MSG_ZHONG_CAI;
		if(tempSms.contains("#number#") && tempSms.contains("#date#") && tempSms.contains("#money#") && tempSms.contains("#day#") && tempSms.contains("#accrue#")){
			tempSms = tempSms.replace("#number#",tLoanContract.getContractNumber());
			tempSms = tempSms.replace("#date#",accountDate);
			tempSms = tempSms.replace("#money#",money+"");
			tempSms = tempSms.replace("#day#",day+"");
			tempSms = tempSms.replace("#accrue#",accrue+"");
		}
		
		model.addAttribute("zhongCai",tempSms + "["+Global.getConfig("productName")+"]");
		model.addAttribute("tLoanContract",tLoanContract);
		return "modules/hastenrepay/zhongCaiSms";
	}
	
	
	/***律师函 短信提醒***/
	@RequestMapping(value = "letterSms")
	public String letterSms(String id, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		TLoanContract tLoanContract = tLoanContractService.get(id);
		
		TRepayPlan tRepayPlan = new TRepayPlan();
		
		tRepayPlan.setProductid((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		tRepayPlan.setLoanContractId(id);
		List<TRepayPlan> repaylist  =  tRepayPlanService.findList(tRepayPlan);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String accountDate = "";
		float money = 0;
		int day = 0;
		float accrue = 0;
		for (int i = 0; i < repaylist.size(); i++) {
			TRepayPlan repayplan = repaylist.get(i);
			if("0".equals(repayplan.getStatus())||"1".equals(repayplan.getStatus())){//未还、未结清的进入。
				
				String interes = repayplan.getInterestReal();
				interes = (interes==null||"".equals(interes))?"0":interes;
				
				String Principal = repayplan.getPrincipalReal();
				Principal = (Principal==null||"".equals(Principal))?"0":Principal;
				
				accountDate = repayplan.getAccountDate();
				money = Float.parseFloat(repayplan.getInterest())+ Float.parseFloat(repayplan.getPrincipal())-Float.parseFloat(interes)-Float.parseFloat(Principal);
				day = daysBetween(accountDate,sdf.format(new Date()));
				accrue = Float.parseFloat(tLoanContract.getLoanAmount())*day*Float.parseFloat(tLoanContract.getLatePenalty())/100;
				break;//只需获取第一笔已经逾期的，后面的逾期等当笔收完之后再计算
			}
		}
		model.addAttribute("repayplanList",repaylist);
		if("1".equals(tLoanContract.getCustomerType())){
			TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
			model.addAttribute("company",company);
		}else if("2".equals(tLoanContract.getCustomerType())){
			TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
			model.addAttribute("employee",employee);
		}                                        
		String tempSms = Cons.Msg.MSG_LETTER;
		if(tempSms.contains("#number#") && tempSms.contains("#date#") && tempSms.contains("#money#") && tempSms.contains("#day#") && tempSms.contains("#accrue#")){
			tempSms = tempSms.replace("#number#",tLoanContract.getContractNumber());
			tempSms = tempSms.replace("#date#",accountDate);
			tempSms = tempSms.replace("#money#",money+"");
			tempSms = tempSms.replace("#day#",day+"");
			tempSms = tempSms.replace("#accrue#",accrue+"");
		}
		
		model.addAttribute("letter",tempSms + "["+Global.getConfig("productName")+"]");
		model.addAttribute("tLoanContract",tLoanContract);
		return "modules/hastenrepay/letterSms";
	}
	
	
	
	public static int daysBetween(String smdate,String bdate) throws ParseException{ 
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		 Calendar cal = Calendar.getInstance(); 
		 cal.setTime(sdf.parse(smdate)); 
		 long time1 = cal.getTimeInMillis(); 
		 cal.setTime(sdf.parse(bdate)); 
		 long time2 = cal.getTimeInMillis(); 
		 long between_days=(time2-time1)/(1000*3600*24); 

		 return Integer.parseInt(String.valueOf(between_days)); 
		 } 
	
	
}