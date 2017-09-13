/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.web.advance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.LoanCalculation;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.postlending.service.advance.AdvanceService;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;

/**
 * 提前还款Controller
 * @author srf
 * @version 2016-04-18
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/postlending/advance/advance")
public class AdvanceController extends BaseController {
	// 还款计划
	@Autowired
	private TRepayPlanService tRepayPlanService;
	// 真实还款记录
	@Autowired
	private RepayRecordService repayRecordService;
	// 违约金，咨询费
	@Autowired
	private RealIncomeService realIncomeService;
		
	@Autowired
	private TLoanContractService loanContractService;
	
	@Autowired
	private TProductService productService;
	
	@Autowired 
	private ActTaskService actTaskService;
	
	@Autowired
	private AdvanceService advanceService;
	
	@ModelAttribute
	public Advance get(@RequestParam(required=false) String id) {
		Advance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = advanceService.get(id);
		}
		if (entity == null){
			entity = new Advance();
		}
		return entity;
	}
	
	@RequiresPermissions("postlending:advance:advance:view")
	@RequestMapping(value = {"list", ""})
	public String list(Advance advance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Advance> page = advanceService.findPage(new Page<Advance>(request, response), advance); 
		model.addAttribute("page", page);
		model.addAttribute("advance", advance);
		return "modules/postlending/advance/advanceList";
	}

	@RequiresPermissions("postlending:advance:advance:view")
	@RequestMapping(value = "form")
	public String form(Advance advance, Model model, RedirectAttributes redirectAttributes) {
		Advance temp = advanceService.get(advance.getAct().getBusinessId());
		if(temp == null){ //这里表示传来的id/businessId是合同id，表示新增提前还款
			temp = new Advance();
			temp.setLoanContractId(advance.getAct().getBusinessId());
			temp.setStatus(Cons.AdvanceStatus.TO_ADUIT);
			List<Advance> tempList = advanceService.findList(temp);
			if(tempList!=null && !tempList.isEmpty()){
				addMessage(redirectAttributes, "此合同已有正在审批中的提前还款申请！");
				return "redirect:" + adminPath + "/refund/reimburse";
			}
			advance.setId(null);  
			advance.setLoanContractId(advance.getAct().getBusinessId());
		}
		//获取合同信息
		TLoanContract loanContract = loanContractService.get(advance.getLoanContractId());
		// 1 显示还款计划列表 t_repay_plan
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(advance.getLoanContractId());
		List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("repay_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(advance.getLoanContractId());
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		tRepayPlan.setIsYuQi(Cons.LoanContractStatus.OVERDUE);
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(advance.getLoanContractId());
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);
		
		//计算还还有多少本金未还和需要的利息
		BigDecimal totPrincipal = null;//总本金
		BigDecimal currentPrincipal = null;//当前本金
		BigDecimal currentInterest = null;//利息
		// 获取需要还款的数据
		TRepayPlan repayPlan = new TRepayPlan();
		repayPlan.setLoanContractId(advance.getLoanContractId());
		HashMap<String, Double> map = tRepayPlanService.allRepayment(repayPlan);
		if (map != null) {//有还款计划
			BigDecimal interest = map.get("interest") == null ? new BigDecimal(0) : new BigDecimal(map.get("interest"));// 利息
			BigDecimal interestReal = map.get("interest_real") == null ? new BigDecimal(0) : new BigDecimal(map.get("interest_real"));// 真实利息
			BigDecimal principal = map.get("principal") == null ? new BigDecimal(0) : new BigDecimal(map.get("principal"));// 本金
			BigDecimal principalReal = map.get("principal_real") == null ? new BigDecimal(0) : new BigDecimal(map.get("principal_real"));// 真实本金
			
			totPrincipal = principal;
			currentPrincipal = principal.subtract(principalReal);//当前本金
			//提前还款涉及到计息的重新计算
			currentInterest = interest.subtract(interestReal);//当前利息（计划中的）
		}else{
			totPrincipal = new BigDecimal(0);
			currentPrincipal = new BigDecimal(0);//当前本金
			currentInterest = new BigDecimal(0);//当前利息
		}
		
		int surplusPeriod=0;//未还期数
		Double noInterest=0.0;//未收利息
		String interestDate = null;//新计划的计息时间
		for(TRepayPlan tempPlan:repayPlanList){
			//未还
			if(Cons.RepayStatus.NO_PAID.equals(tempPlan.getStatus())){
				surplusPeriod++;//未还期数
				noInterest=noInterest+Double.parseDouble(tempPlan.getInterest());
			}
			if(StringUtils.isBlank(interestDate)){
				interestDate = tempPlan.getStartDate();
			}else{
				if(DateUtils.parseDate(interestDate).compareTo(DateUtils.parseDate(tempPlan.getStartDate()))>0){
					interestDate = tempPlan.getStartDate();
				}
			}
			
		}
				
		if(loanContract.getPayType().equals("4")){
			surplusPeriod=surplusPeriod-1;//按月付息到期还本  需要-1
		}
		
		if(actTaskService.checkIsEnd(advance.getAct())){////判断是否为最后节点(即财务节点)
			advance.getAct().setIsEnd("yes");
			/*****************************提前还款应收利息***************************/
			String nextDateString="";//下一个还款日
			for(TRepayPlan entity:repayPlanList){
				if(Cons.RepayStatus.NO_PAID.equals(entity.getStatus())){
					nextDateString=entity.getEndDate();
					break;
				}
			} 
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = dateFormat.parse(nextDateString);
				Double days=DateUtils.getDistanceOfTwoDate(advance.getApplyDate(), date1);//获取日期之间的天数
				model.addAttribute("days",days);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("totPrincipal",totPrincipal.setScale(2,BigDecimal.ROUND_HALF_UP));//总本金
		model.addAttribute("nowPrincipal", currentPrincipal.setScale(2,BigDecimal.ROUND_HALF_UP));//当前本金
		model.addAttribute("nowInterest", currentInterest.setScale(2,BigDecimal.ROUND_HALF_UP));//当前利息
		model.addAttribute("surplusPeriod", surplusPeriod);//还有几期还款
		model.addAttribute("noInterest", noInterest);//未收利息
		model.addAttribute("interestDate", interestDate);//新计划的计息时间
		
		model.addAttribute("repayPlanList", repayPlanList); // 1 还款计划列表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("tLoanContract", loanContract);//合同信息
		model.addAttribute("productName",productService.get(loanContract.getProductId()).getName());
		model.addAttribute("advance", advance);
		return "modules/postlending/advance/advanceForm";
	}
	
	@RequiresPermissions("postlending:advance:advance:view")
	@RequestMapping(value = "getNewPlan")
	@ResponseBody
	public Map<String, Object> getNewPlan(Advance advance,HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		
		//新的还款计划列表
		List<TRepayPlan> newRepayPlanList = new ArrayList<TRepayPlan>();
		if(advance.getLoanContractId() != null ){
			//获取合同信息
			TLoanContract loanContract = loanContractService.get(advance.getLoanContractId());
			
			//获取还款计划列表
			TRepayPlan repayPlan = new TRepayPlan();
			repayPlan.setLoanContractId(advance.getLoanContractId());
			List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(repayPlan);
			
			//当前本金,未扣除提前还款部分
			BigDecimal currentPrincipal = advance.getTotPrincipal();//总的本金
			//计算申请期后还有几期未还
			int nopayForApply=0;
			for(TRepayPlan entity:repayPlanList){
				if("0".equals(entity.getStatus()) || "1".equals(entity.getStatus())){
					int position = DateUtils.dateDuring(advance.getApplyDate(), DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
					if(position > 0){
						nopayForApply++;
					}
				}
			}
			//计算未还期数
			BigDecimal dealPeriod = new BigDecimal(nopayForApply);
			boolean needDealPer = true;//是否需要计算提前还款后每期的还款本金
			//日贷款利率
			BigDecimal dayRate = LoanCalculation.calcDayLendingRates(new BigDecimal(loanContract.getLoanRate()), loanContract.getLoanRateType());
			//处理还款计划
			//1等额本息
			if("1".equals( loanContract.getPayType() )){
				BigDecimal perPayAmount = null;//每月还款数（本金+利息）
				//月利率
				BigDecimal monthRate = LoanCalculation.calcMonthLendingRates(new BigDecimal(loanContract.getLoanRate()), loanContract.getLoanRateType());
				for(TRepayPlan entity : repayPlanList){
					double principal = Double.parseDouble(entity.getPrincipal());//原计划本金
					//double interest = Double.parseDouble(entity.getInterest());//原计划利息
					
					int position = DateUtils.dateDuring(advance.getApplyDate(), DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
					if(position == 2){//在申请之前
						currentPrincipal = currentPrincipal.subtract( new BigDecimal(principal) );
					}else if(position == 1){//正好等于enddate
						if(needDealPer){//计算新的还款关系
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							
							BigDecimal pubData = monthRate.add(new BigDecimal(1)).pow(Integer.parseInt(loanContract.getPeriodType()));
							BigDecimal xia = pubData.subtract(new BigDecimal(1));
							perPayAmount = currentPrincipal.multiply(monthRate).multiply(pubData).divide(xia);
							
							needDealPer = false;
						}
						currentPrincipal = currentPrincipal.subtract( new BigDecimal(principal) );
					}else if(position == 0){//在中间
						//前半部分利息
						BigDecimal tmpInte = LoanCalculation.calculateInterest(currentPrincipal, dayRate, DateUtils.parseDate(entity.getStartDate()), advance.getApplyDate());
						
						if(needDealPer){//计算新的还款关系
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							
							BigDecimal pubData = monthRate.add(new BigDecimal(1)).pow(Integer.parseInt(loanContract.getPeriodType()));
							BigDecimal xia = pubData.subtract(new BigDecimal(1));
							perPayAmount = currentPrincipal.multiply(monthRate).multiply(pubData).divide(xia);
							
							needDealPer = false;
						}
						
						//后半部分利息
						tmpInte = tmpInte.add(LoanCalculation.calculateInterest(currentPrincipal, dayRate, advance.getApplyDate(), DateUtils.parseDate(entity.getEndDate()) ));
						
						entity.setInterest(tmpInte.toString());
						entity.setPrincipal(perPayAmount.subtract(tmpInte).toString());
						
						currentPrincipal = currentPrincipal.subtract(perPayAmount.subtract(tmpInte));
					}else{//正好等于startdate 在申请之后
						if(needDealPer){//计算新的还款关系
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							
							BigDecimal pubData = monthRate.add(new BigDecimal(1)).pow(Integer.parseInt(loanContract.getPeriodType()));
							BigDecimal xia = pubData.subtract(new BigDecimal(1));
							perPayAmount = currentPrincipal.multiply(monthRate).multiply(pubData).divide(xia);
							
							needDealPer = false;
						}
						
						BigDecimal tmpInte = LoanCalculation.calculateInterest(currentPrincipal, dayRate, DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
						
						entity.setInterest(tmpInte.toString());
						entity.setPrincipal(perPayAmount.subtract(tmpInte).toString());
						
						currentPrincipal = currentPrincipal.subtract(perPayAmount.subtract(tmpInte));
					}
					newRepayPlanList.add(entity);
				}
			}
			//2等额本金
			else if("2".equals( loanContract.getPayType() )){
				//每期的本金数量
				BigDecimal perPrincipal = null;// currentPrincipal.divide(dealPeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				for(TRepayPlan entity : repayPlanList){
					if("2".equals(entity.getStatus())){//已经还清的不处理
						continue;
					}
					
					//重新计算本金
					double principal = Double.parseDouble(entity.getPrincipal());//原计划本金
					//double interest = Double.parseDouble(entity.getInterest());//原计划利息
					
//					if(principal>0){
//						entity.setPrincipal(perPrincipal.toString());
//					}
					int position = DateUtils.dateDuring(advance.getApplyDate(), DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
					if(position == 2){//在申请之前
						currentPrincipal = currentPrincipal.subtract( new BigDecimal(principal) );
					}else if(position == 1){//正好等于enddate
						currentPrincipal = currentPrincipal.subtract( new BigDecimal(principal) );
						if(needDealPer){//计算每期的本金数量
							perPrincipal = currentPrincipal.divide(dealPeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							needDealPer = false;
						}
					}else if(position == 0){//在中间
						//前半部分利息
						BigDecimal tmpInte = LoanCalculation.calculateInterest(currentPrincipal, dayRate, DateUtils.parseDate(entity.getStartDate()), advance.getApplyDate());
						
						if(needDealPer){
							//计算每期的本金数量
							perPrincipal = currentPrincipal.divide(dealPeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							needDealPer = false;
						}
						
						entity.setPrincipal(perPrincipal.toString());//本期还款本金
						
						//后半部分利息
						Date tmpDate = DateUtils.addDays(advance.getApplyDate(), 1);
						tmpInte = tmpInte.add( LoanCalculation.calculateInterest(currentPrincipal, dayRate,tmpDate, DateUtils.parseDate(entity.getEndDate())) );
						entity.setInterest(tmpInte.toString());
						
						
						currentPrincipal = currentPrincipal.subtract( perPrincipal );
					}else{//正好等于startdate 在申请之后
						if(needDealPer){//计算每期的本金数量
							perPrincipal = currentPrincipal.divide(dealPeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
							//扣除提前还款金额
							currentPrincipal = currentPrincipal.subtract(advance.getAdvanceamount());
							needDealPer = false;
						}
						
						entity.setPrincipal(perPrincipal.toString());//本期还款本金
						
						BigDecimal tmpInte = LoanCalculation.calculateInterest(currentPrincipal, dayRate, DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
						entity.setInterest(tmpInte.toString());
						
						currentPrincipal = currentPrincipal.subtract( perPrincipal );
					}
					
					newRepayPlanList.add(entity);
				}
			}
			//3按月付息到期还本(到期本金一次性还完)
			else if("3".equals( loanContract.getPayType() )){
				/**
				 * 一次性付息。
				 * 利息需要分两段计算：提前还款前，提前还款后
				 * 处理：1：计算提前还款金额涉及的利息
				 *       2：之前的金额产生的利息-1的结果
				 */
				if("2".equals(loanContract.getPayOptions())){
					for(TRepayPlan entity : repayPlanList){
						double principal = Double.parseDouble(entity.getPrincipal());//原计划本金
						double interest = Double.parseDouble(entity.getInterest());//原计划利息
						if(interest>0){
							int position = DateUtils.dateDuring(advance.getApplyDate(), DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
							if(position == 0){//扣除部分
								BigDecimal needSub = LoanCalculation.calculateInterest(advance.getAdvanceamount(), dayRate, advance.getApplyDate(), loanContract.getPayPrincipalDate());
								
								BigDecimal nowInterest = new BigDecimal(interest);
								entity.setInterest( nowInterest.subtract(needSub).toString() );
							}
						}
						if(principal>0){
							BigDecimal nowPrincipal = new BigDecimal(principal);
							entity.setPrincipal(nowPrincipal.subtract( advance.getAdvanceamount() ).toString());
						}
					}
				}
				/**
				 * 非一次性付息
				 * 处理方式和一次性付息相同，计算利息需要按期来计算，而非一次性计算
				 */
				else{
					for(TRepayPlan entity : repayPlanList){
						double principal = Double.parseDouble(entity.getPrincipal());//原计划本金
						double interest = Double.parseDouble(entity.getInterest());//原计划利息
						if(interest>0){
							int position = DateUtils.dateDuring(advance.getApplyDate(), DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
							if(position == 0){//扣除部分
								BigDecimal needSub = LoanCalculation.calculateInterest(advance.getAdvanceamount(), dayRate, advance.getApplyDate(), DateUtils.parseDate(entity.getEndDate()));
								
								BigDecimal nowInterest = new BigDecimal(interest);
								entity.setInterest( nowInterest.subtract(needSub).toString() );
							}else if(position < 0){
								BigDecimal needSub = LoanCalculation.calculateInterest(advance.getAdvanceamount(), dayRate, DateUtils.parseDate(entity.getStartDate()), DateUtils.parseDate(entity.getEndDate()));
								
								BigDecimal nowInterest = new BigDecimal(interest);
								entity.setInterest( nowInterest.subtract(needSub).toString() );
							}
						}
						if(principal>0){
							BigDecimal nowPrincipal = new BigDecimal(principal);
							entity.setPrincipal(nowPrincipal.subtract( advance.getAdvanceamount() ).toString());
						}
					}
				}
			}
			
		}
		
		return result;
	}
	
	@RequiresPermissions("postlending:advance:advance:edit")
	@RequestMapping(value = "save")
	public String save(Advance advance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advance)){
			return form(advance, model,redirectAttributes);
		}
		try {
			advanceService.saveAndAudit(advance);
			addMessage(redirectAttributes, "保存提前还款成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/act/task/historic/?repage";
	}
	
	@RequiresPermissions("postlending:advance:advance:edit")
	@RequestMapping(value = "delete")
	public String delete(Advance advance, RedirectAttributes redirectAttributes) {
		advanceService.delete(advance);
		addMessage(redirectAttributes, "删除提前还款成功");
		return "redirect:"+Global.getAdminPath()+"/postlending/advance/advance/?repage";
	}

}