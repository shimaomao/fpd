/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.repayplan.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.repayplan.vo.MapVo;
import com.wanfin.fpd.modules.repayplan.vo.PlanCreateParam;

/**
 * 还款计划Controller
 * @author lx
 * @version 2016-03-22
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/repayplan/tRepayPlan")
public class TRepayPlanController extends BaseController {

	@Autowired
	private TRepayPlanService tRepayPlanService;
	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	@ModelAttribute
	public TRepayPlan get(@RequestParam(required=false) String id) {
		TRepayPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tRepayPlanService.get(id);
		}
		if (entity == null){
			entity = new TRepayPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("repayplan:tRepayPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(TRepayPlan tRepayPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TRepayPlan> page = tRepayPlanService.findPage(new Page<TRepayPlan>(request, response), tRepayPlan); 
		model.addAttribute("page", page);
		return "modules/repayplan/tRepayPlanList";
	}

	@RequiresPermissions("repayplan:tRepayPlan:view")
	@RequestMapping(value = "form")
	public String form(TRepayPlan tRepayPlan, Model model) {
		model.addAttribute("tRepayPlan", tRepayPlan);
		return "modules/repayplan/tRepayPlanForm";
	}

	@RequiresPermissions("repayplan:tRepayPlan:edit")
	@RequestMapping(value = "save")
	public String save(TRepayPlan tRepayPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tRepayPlan)){
			return form(tRepayPlan, model);
		}
		tRepayPlanService.save(tRepayPlan);
		addMessage(redirectAttributes, "保存还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/repayplan/tRepayPlan/?repage";
	}
	
	@ResponseBody
	@POST
	@RequestMapping(value = "getMyRepayPlan")
	public List<TRepayPlan> getMyRepayPlan(MapVo mapVo, Model model,HttpServletRequest request, HttpServletResponse response) {
		List<TRepayPlan> planList=new ArrayList<TRepayPlan>();
		JSONArray list = null;
		try {
			TRepayPlan plan=new TRepayPlan();
			plan.setLoanContractId(mapVo.getLoanContractId());
		    planList = tRepayPlanService.findList(plan);
	
		} catch (ServiceException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		model.addAttribute("list", list);
		return planList;
	}
	
	/**
	 * 异步修改还款计划数据
	 * @param tRepayPlan
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxRepayPlanForms")
	public Map<String,Object> ajaxRepayPlanForms(TRepayPlan tRepayPlan, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String,Object>();
//		if (!beanValidator(model, tRepayPlan)){
//			//return null;//form(tRepayPlan, model);
//			map.put("msg", "参数校验错误！");
//			map.put("status", "false");
//		}
		// || StringUtils.isBlank(tRepayPlan.getStatus())
		if(StringUtils.isBlank(tRepayPlan.getLoanContractId()) || tRepayPlan.getNum() == null || StringUtils.isBlank(tRepayPlan.getInterest()) || StringUtils.isBlank(tRepayPlan.getPrincipal()) || StringUtils.isBlank(tRepayPlan.getStartDate()) || StringUtils.isBlank(tRepayPlan.getEndDate()) || StringUtils.isBlank(tRepayPlan.getAccountDate())){
			map.put("msg", "参数错误或参数不全！");
			map.put("status", "false");
		}else{
			if(StringUtils.isNotBlank(tRepayPlan.getId()) && ( "_empty".equals(tRepayPlan.getId()) || tRepayPlan.getId().startsWith("jqg") )){
				tRepayPlan.setId(null);
			}
			System.out.println("oper="+tRepayPlan.getOper());
			if("del".equals(tRepayPlan.getOper())){
				tRepayPlanService.delete(tRepayPlan);
				map.put("msg", "删除还款计划成功");
			}else{
				if(StringUtils.isBlank(tRepayPlan.getStatus())){
					tRepayPlan.setStatus("0");
				}
				tRepayPlanService.save(tRepayPlan);
				map.put("msg", "保存还款计划成功");
			}
			map.put("status", "succeed");
		}
		return map;
	}
	
	@RequiresPermissions("repayplan:tRepayPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(TRepayPlan tRepayPlan, RedirectAttributes redirectAttributes) {
		tRepayPlanService.delete(tRepayPlan);
		addMessage(redirectAttributes, "删除还款计划成功");
		return "redirect:"+Global.getAdminPath()+"/repayplan/tRepayPlan/?repage";
	}
	
	/**
	 * @Description 根据参数，生成相应还款计划
	 * @param   
	 * 		贷款:
	 * 			{
	 * 				businessType: 'apply'（必填）,
	 * 				amount: 贷款金额（必填）,
	 * 				loanRate: 利率（必填）,
	 * 				loanPeriod: 期限（必填）,
	 * 				loanDate: 放款日期（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				periodType: 还款周期,
	 * 				payDay: 每期还款日期,
	 * 				payOptions: 还款选项
	 * 			}
	 * 		展期:
	 * 			{
	 * 				businessType: 'extend'（必填）,
	 * 				businessId: '业务id'（必填）,
	 * 				loanRate: 展期利率（必填）,
	 * 				loanPeriod: 展期期限（必填）,
	 * 				loanDate: 展期申请日期（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				periodType: 还款周期,
	 * 				payDay: 每期还款日期,
	 * 				payOptions: 还款选项
	 * 			}
	 * 		提前还款:
	 * 			{
	 * 				businessType: 'earlyrepay'（必填）,
	 * 				businessId: '业务id'（必填）,
	 * 				loanRate: 利率（必填）,
	 * 				loanPeriod: 期限（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				payAmount: 提前还款金额（必填）,
	 * 				payDate: 提前还款时间（必填）,
	 * 				payOptions: 还款选项
	 * 			}
	 * 
	 * 
	 * @return
	 * @author zzm 
	 * @date 2016-5-3 下午5:35:02  
	 */
	@RequestMapping(value = "getRepayPlan")
	public String getRepayPlan(PlanCreateParam param, Model model,HttpServletRequest request, HttpServletResponse response) {
		JSONArray list = null;
		//读取已经存在的还款计划。如果没有则去kie获取
		if(StringUtils.isNotBlank(param.getBusinessId()) && StringUtils.isNotBlank(param.getBusinessType()) && "apply".equals(param.getBusinessType())){
			TRepayPlan repayPlan = new TRepayPlan();
			repayPlan.setLoanContractId(param.getBusinessId());
			List<TRepayPlan> listRepayPlan = tRepayPlanService.findList(repayPlan);
			if(listRepayPlan != null && listRepayPlan.size()>0){
				String jsonText = JSON.toJSONString(listRepayPlan,true);
				try {
					list = new JSONArray(jsonText);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		if(list == null)
		{
			if("apply".equals(param.getBusinessType())){//贷款业务申请
				
			}else if("extend".equals(param.getBusinessType())||"extend1".equals(param.getBusinessType())){//展期申请业务
				TRepayPlan repayPlan = new TRepayPlan();
				
	//			if("extend1".equals(param.getBusinessType())){//此处是展期审批时初始化还款计划使用，审批时使用则不用时间换化
	//				param.setBusinessType("extend");
	//			}else{
	//				if(param.getPayPrincipalDate()!=null&&!param.getPayPrincipalDate().equals("")){
	//					param.setPayPrincipalDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(param.getPayPrincipalDate())));
	//				}
	//			}
				repayPlan.setLoanContractId(param.getBusinessId());
				List<TRepayPlan> listRepayPlan = tRepayPlanService.findList(repayPlan);
				//展期申请时，计算未还期数，把未还的剩余本金作为展期金额
				if(listRepayPlan != null ){
					BigDecimal remainAmount = new BigDecimal(0);
					HashMap<Object, Object> numMap = Maps.newHashMap();
					for(TRepayPlan p : listRepayPlan){
						if(Cons.RepayStatus.NO_PAID.equals(p.getStatus())){
							//未还的本金
							remainAmount= remainAmount.add(new BigDecimal(p.getPrincipal()));
							numMap.put(p.getNum(), p.getNum());
						}
					}
					//未还期数
					param.setUnPayPeriod(numMap.size());
					//展期金额
					//param.setAmount(remainAmount.doubleValue()); //old #3121
					param.setAmount(remainAmount);//#3121
				}
			}else if("earlyrepay".equals(param.getBusinessType())){//提前还款业务
				TRepayPlan repayPlan = new TRepayPlan();
				repayPlan.setLoanContractId(param.getBusinessId());
				List<TRepayPlan> orList = tRepayPlanService.findList(repayPlan);
				param.setList(orList);//原还款计划
			}
			
			
			try {
				//start by shirf #3121 原没有获取利率类型，kie只获取年利率，现添加进行转换
	//			if(StringUtils.isBlank(param.getLoanRateType())){
	//				
	//			}else if("月".equals(param.getLoanRateType())){
	//				param.setLoanRate(param.getLoanRate().multiply(new BigDecimal(12)));
	//			}else if("日".equals(param.getLoanRateType())){
	//				param.setLoanRate(param.getLoanRate().multiply(new BigDecimal(360)));
	//			}
				//end by shirf #3121
				
				if(StringUtils.isBlank(param.getLoanRateType())){
					
				}else if("年".equals(param.getLoanRateType())){
					param.setLoanRateType("1");//利率类型   1年  2月  3日  
				}else if("月".equals(param.getLoanRateType())){
					param.setLoanRateType("2");//利率类型   1年  2月  3日  
				}else if("日".equals(param.getLoanRateType())){
					param.setLoanRateType("3");//利率类型   1年  2月  3日  
				}
				list = tRepayPlanService.createRepayPlans(param);//从KIE获取还款计划
				
			} catch (ServiceException e) {
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
				model.addAttribute("message", e1.getMessage());
			}
		}
		model.addAttribute("list", list);
		BigDecimal all = new BigDecimal(0);
		BigDecimal interest = new BigDecimal(0);
		if(list != null){
			for(int i=0;i<list.length();i++){
				String ss;
				try {
					ss = (String)list.getJSONObject(i).get("interest");
					interest=interest.add(new BigDecimal(ss));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			   
			}
		}
		all=param.getAmount().add(interest);
		model.addAttribute("interest", interest);
		model.addAttribute("all", all);
		return "common/repayPlanList";
	}
	
	
	@RequestMapping(value = "getLocalRepayPlan")
	public String getLocalRepayPlan(TRepayPlan tRepayPlan, Model model) {
		List<TRepayPlan> list = Lists.newArrayList();
		list = tRepayPlanService.findList(tRepayPlan);
		model.addAttribute("list", list);
		return "common/repayPlanList2";
	}
	
	/**
	 * 获取还款计划，有则直接读取，没有则通过KIE生成  TODO
	 * @param loanContractId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxGetRepayPlan")
	public List<TRepayPlan> ajaxGetRepayPlan(PlanCreateParam param, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(param == null || StringUtils.isBlank(param.getBusinessId())){
			return null;
		}
		
		//boolean ifRead = false;//fpd  放款后才  还款计划才保存到数据库
		boolean ifRead = true;//是否已读取历史记录为先
		if(param != null && StringUtils.isNotBlank(param.getDealType()) && "new".equals(param.getDealType())){
			ifRead = false;
		}
		
		List<TRepayPlan> repayPlanList = null;
		if(ifRead){
			TRepayPlan repayPlan = new TRepayPlan();
			repayPlan.setLoanContractId(param.getBusinessId());
			repayPlanList = tRepayPlanService.findList(repayPlan);
		}
		if(repayPlanList != null && repayPlanList.size()>0){
			return repayPlanList;
		}else{
			if("apply".equals(param.getBusinessType())){//贷款业务申请
				
			}else if("extend".equals(param.getBusinessType())||"extend1".equals(param.getBusinessType())){//展期申请业务
				TRepayPlan repayPlan = new TRepayPlan();
				
				repayPlan.setLoanContractId(param.getBusinessId());
				List<TRepayPlan> listRepayPlan = tRepayPlanService.findList(repayPlan);
				//展期申请时，计算未还期数，把未还的剩余本金作为展期金额
				if(listRepayPlan != null ){
					BigDecimal remainAmount = new BigDecimal(0);
					HashMap<Object, Object> numMap = Maps.newHashMap();
					for(TRepayPlan p : listRepayPlan){
						if(Cons.RepayStatus.NO_PAID.equals(p.getStatus())){
							//未还的本金
							remainAmount= remainAmount.add(new BigDecimal(p.getPrincipal()));
							numMap.put(p.getNum(), p.getNum());
						}
					}
					//未还期数
					param.setUnPayPeriod(numMap.size());
					//展期金额
					param.setAmount(remainAmount);//#3121
				}
			}else if("earlyrepay".equals(param.getBusinessType())){//提前还款业务
				TRepayPlan repayPlan = new TRepayPlan();
				repayPlan.setLoanContractId(param.getBusinessId());
				List<TRepayPlan> orList = tRepayPlanService.findList(repayPlan);
				param.setList(orList);//原还款计划
			}
			
			if("10".equals(param.getPayType())){
				param.setPayType("3");
			}
			try {
				return tRepayPlanService.upDateFullRepayPlans(param);
			} catch (Exception e1) {
				e1.printStackTrace();
				model.addAttribute("message", e1.getMessage());
			}
		}
		
		return null;
	}
	

}