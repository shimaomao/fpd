/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.wish.contract.service.WishContractService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;
import com.wanfin.fpd.modules.wish.order.dao.ReturnedMoneyDao;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import com.wanfin.fpd.modules.wish.utils.DoubleTwo;

/**
 * 回款记录Service
 * 
 * @author cjp
 * @version 2017-07-07
 */
@Service("returnedMoneyService")
@Transactional(readOnly = true)
public class ReturnedMoneyService extends CrudService<ReturnedMoneyDao, ReturnedMoney> {

	@Autowired
	WishOrderService wishOrderService;
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	TRepayPlanService tRepayPlanService;
	@Autowired
	TEmployeeService tEmployeeService;
	
	@Autowired
	MerchantService merchantService;
	
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private RepayRecordService repayRecordService;
	
	@Autowired
	private WishContractService wishContractService;
	
	@Autowired
	private TReturnedMsgService tReturnedMsgService;

	private String tLoanContractId = "";
	
/*	@Autowired
	private InteractionService interactionService;
	
	private String pvdUid;
	private String loanStatus;
	private String amount;
	private String diffLoanAmount;*/


	public ReturnedMoney get(String id) {
		return super.get(id);
	}

	public List<ReturnedMoney> findList(ReturnedMoney returnedMoney) {
		return super.findList(returnedMoney);
	}

	public Page<ReturnedMoney> findPage(Page<ReturnedMoney> page,
			ReturnedMoney returnedMoney) {
		return super.findPage(page, returnedMoney);
	}

	@Transactional(readOnly = false)
	public void save(ReturnedMoney returnedMoney) {
		super.save(returnedMoney);
	}

	@Transactional(readOnly = false)
	public void delete(ReturnedMoney returnedMoney) {
		super.delete(returnedMoney);
	}

	
	
	
	/**
	 * 回款操作计算应扣款------------此处只用于计算应扣款金额，具体的业务更新在实际收到款项时realRepayMoney()方法中处理
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = false)
	public Map<String,String> repayMoney() throws ParseException {
		Map<String,String> map=new HashMap<String, String>();
		String userId = "";
		String userName="";
		
		List<String> tempList=new ArrayList<String>();
		Date nowDate=new Date();
		
		ReturnedMoney returnedMoney = new ReturnedMoney();
		returnedMoney.setIsDeal("0");
		List<ReturnedMoney> rlist = this.findList(returnedMoney);
		if(rlist.size()<1){
			map.put("msg", "操作失败，待处理回款数据条数为：0");
			return map;
		}
		for (ReturnedMoney rm : rlist) {
			String merchantId=rm.getMerchantId();
			Merchant merchant=new Merchant();
			merchant.setMerchantId(merchantId);
		    merchant=merchantService.getByMerchantId(merchant);
		    
		    
		    Date createTime = rm.getCreateDate();
			/*Calendar cal = Calendar.getInstance();
			cal.setTime(createTime);
			int year = cal.get(Calendar.YEAR);// 获取年份
			int month = cal.get(Calendar.MONTH)+1;// 获取月份---Calendar月份初始值为0
			int dd = cal.get(Calendar.DATE);

			String startTime = "";
			String endTime = "";
			String months="";
			String dds="";
			if (dd > 14) {
				if(month<10){
					months="0"+month;
				}
				if(dd<10){
				    dds="0"+dd;
				}else{
					 dds=""+dd;
				}
				startTime = year + "-" + months + "-" + "15";
				endTime = lastDate(year + "-" + months + "-" + dds);
			} else {
				if(month<10){
					months="0"+month;
				}
				startTime = year + "-" + months + "-" + "01";
				endTime = year + "-" + months + "-" + "14";
			}*/
		    
		    
		    
		    
		    
		    
		    if(merchant!=null){
				userId=merchant.getUserId();
				userName=merchant.getUserName();		
				if (StringUtils.isBlank(merchantId) || StringUtils.isBlank(rm.getRealPayMoney())) {
					map.put("msg", "操作失败，回款数据信息有误");
					return map;
				}
			
				if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName)) {
				
					BigDecimal realMoney = StringUtils.isBlank(rm.getRealPayMoney())?new BigDecimal("0"):new BigDecimal(rm.getRealPayMoney());
					BigDecimal fee=new BigDecimal("0");//罚息金额
				
					TLoanContract tloanContract=new TLoanContract();
					tloanContract.setType("2");
					tloanContract.setCustomerId(userId);
					tloanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED+","+Cons.LoanContractStatus.OVERDUE);
					List<TLoanContract> tctList=tLoanContractService.findWishAllList(tloanContract);
					for(TLoanContract tc:tctList){
						
						String startTime=tRepayPlanService.getFirstTime(tc.getId());
						if(StringUtils.isNotBlank(startTime) && createTime.after(DateUtils.parseDate(startTime))){
							TReturnedMsg returnedMsg=new TReturnedMsg();
							BigDecimal recordMoney=new BigDecimal("0");
							
							TRepayPlan overPlan = new TRepayPlan();
							overPlan.setLoanContractId(tc.getId());
							List<TRepayPlan> planList = tRepayPlanService.findListByNum(overPlan);
							for(int i=0;i<planList.size();i++){
								  TRepayPlan plan=planList.get(i);

								  BigDecimal principal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
			                	  BigDecimal principalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
			                	  BigDecimal diffPrincipal=principal.subtract(principalReal);//剩余应还本金
			                	  BigDecimal interest=StringUtils.isBlank(plan.getInterest())?new BigDecimal("0"):new BigDecimal(plan.getInterest());
			                	  BigDecimal interestReal=StringUtils.isBlank(plan.getInterestReal())?new BigDecimal("0"):new BigDecimal(plan.getInterestReal());
			                	  BigDecimal diffInterest=interest.subtract(interestReal);//由于首期未结清 造成的本期应还金额
			                	  //double overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getAccountDate()),createTime);//逾期天数
			                	  //BigDecimal rate=new BigDecimal(tct.getLoanRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
			                      //（实际回款日期-实际第二回款日期）*逾期金额*年利率/360
			                	 // BigDecimal overFee=new BigDecimal(overdueDays).multiply(diffPrincipal.add(diffInterest)).multiply(rate);
			                	//  BigDecimal sumAmount=diffPrincipal.add(diffInterest).add(overFee);//总计应还
			                	    BigDecimal sumAmount=diffPrincipal.add(diffInterest);//总计应还
			                	  //fee=fee.add(overFee);
			                	 // realMoney=realMoney.subtract(overFee);//扣除罚息--剩下金额再还款
			                	    
			                	    if(tc.getStatus().equals(Cons.LoanContractStatus.OVERDUE)){
			                	    	  if(realMoney.compareTo(BigDecimal.ZERO)==1){
					                		  if(realMoney.compareTo(sumAmount)>=0){//结清
					  	                        plan.setStatus(Cons.RepayStatus.PAID);
					  	                        plan.setPrincipalReal(principal.toString());
					  	                        plan.setInterestReal(interest.toString());
					  	                       // tRepayPlanService.save(overtrp);
					  	                        //tct.setStatus(Cons.LoanContractStatus.CLEARED);//业务完成
					  	                        //tLoanContractService.updateStatus(tct);
					  	                        realMoney=realMoney.subtract(sumAmount);
					  	                        recordMoney=sumAmount;
					  	                        
					  	                        
					  	                      }else{//未结清---------产生逾期
					  	                        if(realMoney.compareTo(diffInterest)>=0){//先处理interest
					  	                        	plan.setInterestReal(interest.toString());
					  	                        	plan.setPrincipalReal((principalReal.add(realMoney.subtract(diffInterest)).toString()));
					  	                        }else{
					  	                        	plan.setInterestReal((interestReal.add(realMoney)).toString());
					  	                        }
					  	                         plan.setStatus(Cons.RepayStatus.IN_PAYMENT);
					  	                    	// tRepayPlanService.save(overtrp);
					  	                         recordMoney=realMoney;
					  	                    	 realMoney=new BigDecimal("0");
					  	                        }
					  	                      
					                	  }
			                	    }else{
			                	    	if(plan.getNum()==1 && !"2".equals(plan.getStatus())){
			                	    	if(realMoney.compareTo(BigDecimal.ZERO)==1){
			                	    		if(realMoney.subtract(diffPrincipal).compareTo(BigDecimal.ZERO)>=0){//结清
				                    			plan.setStatus(Cons.RepayStatus.PAID);
				                    			plan.setPrincipalReal(principal.toString());
				                    			//tRepayPlanService.save(trp);
				                    			realMoney=realMoney.subtract(diffPrincipal);
				                    			recordMoney=diffPrincipal;
				                    	
				      	                	    TRepayPlan ntp = new TRepayPlan();
					                    		ntp.setNum(2);//获取同笔借款第二批还款计划
					                    		ntp.setLoanContractId(plan.getLoanContractId());
					                    		List<TRepayPlan> nextList = tRepayPlanService.findListByNum(ntp);
					                    		if(nextList.size()==1){
					                    			TRepayPlan nextPlan=nextList.get(0);
					                    			nextPlan.setStatus(Cons.RepayStatus.PAID);
					                        		//tRepayPlanService.save(nextPlan);
					                    		}else{
				                    				map.put("msg", "操作失败，获取第二批回款计划数据错误！");
				                    				return map;
				                    			}
					                    		tempList.add(plan.getLoanContractId());//首期多还  加入缓存 在第二期时不做处理
					                    			
				                    			
				                        	}else{//未结清---------首批少还的部分放到下一期----------这里业务没还完不需要生成还款记录推至下期生成
				                        		//repayRecord.setMoney(realMoney.doubleValue());
				                        		plan.setStatus(Cons.RepayStatus.PAID);
				                    			plan.setPrincipalReal(realMoney.toString());
				                    			//tRepayPlanService.save(trp);
				                    			recordMoney=realMoney;
				                    			
				                    			TRepayPlan ntp = new TRepayPlan();
				                    			ntp.setNum(2);//获取同笔借款第二批还款计划
				                    			ntp.setLoanContractId(plan.getLoanContractId());
				                    			List<TRepayPlan> nextList = tRepayPlanService.findListByNum(ntp);
				                    			if(nextList.size()==1){
				                    				TRepayPlan nextPlan=nextList.get(0);
				                        			//将首期未还的金额计算到下期的利息中，在二批的还款中处理
				                        			nextPlan.setInterest((new BigDecimal(plan.getPrincipal()).subtract(new BigDecimal(plan.getPrincipalReal()))).toString());
				                        			//tRepayPlanService.save(nextPlan);
				                        			realMoney=new BigDecimal("0");
				                    			}else{
				                    				map.put("msg", "操作失败，获取第二批回款计划数据错误！");
				                    				return map;
				                    			}
				                        	}
			                	    	}
			                	    	}else if(plan.getNum()==2 && !"2".equals(plan.getStatus())){
			                	    		
			                	    		if(!tempList.contains(plan.getLoanContractId()) && sumAmount.compareTo(BigDecimal.ZERO)==1){
			                	    			
			                	    			if(realMoney.compareTo(BigDecimal.ZERO)==1){
			                	    				
			                	    				if(realMoney.subtract(sumAmount).compareTo(BigDecimal.ZERO)>=0){//结清
						                    			plan.setStatus(Cons.RepayStatus.PAID);
						                    			plan.setPrincipalReal(principal.toString());
						                    			plan.setInterestReal(interest.toString());
						                    			realMoney=realMoney.subtract(sumAmount);
						                    			recordMoney=sumAmount;
						             
						                        	}else{//未结清---------产生逾期
						                        		if(realMoney.compareTo(diffInterest)>=0){//先处理interest
						                        			plan.setInterestReal(interest.toString());
						                        			//plan.setPrincipalReal((principalReal.add(realMoney.subtract(diffInterest)).toString()));
						                        			plan.setPrincipalReal((realMoney.subtract(diffInterest).toString()));

						                        		}else{
						                        			//plan.setInterestReal((interestReal.add(realMoney)).toString());
						                        			plan.setInterestReal(realMoney.toString());
						                        		
						                        		}
						                        		recordMoney=realMoney;
						                        		realMoney=new BigDecimal("0");
						                        	}
			                	    				
			                	    			}
			                	    			
			                	    		}
			                	    	}

			                	    }
		    
							}
							
							returnedMsg.setLoanContractId(tc.getId());
							Integer maxPeriods=tReturnedMsgService.getMaxPeriods(tc.getId());
							returnedMsg.setReturnedMoneyId(rm.getId());
							returnedMsg.setUserId(rm.getUserId());
							returnedMsg.setDelFlag("0");
							returnedMsg.setCreateDate(nowDate);
							returnedMsg.setMoney(recordMoney.toString());
							if(maxPeriods!=null){
								returnedMsg.setPeriods(maxPeriods+1);
							}else{
								returnedMsg.setPeriods(1);
							}
							tReturnedMsgService.save(returnedMsg);
						}
					}
					rm.setFee(fee.toString());
					rm.setRepayLoanMoney(new BigDecimal(rm.getRealPayMoney()).subtract(realMoney).toString());
					rm.setUserId(userId);
					rm.setUserName(userName);
					BigDecimal realPayMoney=StringUtils.isBlank(rm.getRealPayMoney())?new BigDecimal("0"):new BigDecimal(rm.getRealPayMoney());
					if(realPayMoney.compareTo(BigDecimal.ZERO)==1){
						rm.setIsDeal("1");
					}
					this.save(rm);
					map.put("msg", "success");
		        }
		    }
	
	    }
	    return map;
}
	
	
	
	
	/**
	 * 回款操作------------:处理顺序：逾期---》首批计划----》第二批计划
	 * 
	 * 说明： 1.定时器处理 第二批一直没有还款操作的收款计划，更新逾期accountDate
	 *      2.收款操作先处理逾期的计划
	 *      3.收款操作处理首批计划
	 *      4 收款操作处理二批计划
	 *      
	 *      注意：由于逾期由步骤1和步骤4触发，所以在步骤2会包含所有的逾期记录 ，所以逾期罚息只在步骤2处理即可
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = false)
	public Map<String,String> realRepayMoney(String returnedMoneyIds) throws ParseException {
		Map<String,String> map=new HashMap<String, String>();
		String userId = "";
		String userName="";
		Date nowDate=new Date();
		
		List<String> tempList=new ArrayList<String>();
		
		ReturnedMoney returnedMoney = new ReturnedMoney();
		returnedMoney.setIsDeal("1");
		returnedMoney.setId(returnedMoneyIds);
		List<ReturnedMoney> rlist = this.findListByIds(returnedMoney);
		String rmUserIds="";
		String rmIds="";
		
		if(rlist.size()<1){
			map.put("msg", "操作失败，待处理回款数据条数为：0");
			return map;
		}
		
		for (ReturnedMoney rm : rlist) {
		//	String accountNum = rm.getAccountNum();
			String merchantId=rm.getMerchantId();
			Merchant merchant=new Merchant();
			merchant.setMerchantId(merchantId);
		    merchant=merchantService.getByMerchantId(merchant);
		    Date createTime = rm.getCreateDate();
		    
		    if(merchant!=null){
		    	userId=merchant.getUserId();
				userName=merchant.getUserName();
				if (StringUtils.isBlank(merchantId) || StringUtils.isBlank(rm.getRealPayMoney())) {
					map.put("msg", "操作失败，回款数据信息有误");
					return map;
				}
				//if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName) && gatheringNumber.endsWith(rm.getAccountNum())) {
				if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName)) {
				
					BigDecimal realMoney = StringUtils.isBlank(rm.getRealPayMoney())?new BigDecimal("0"):new BigDecimal(rm.getRealPayMoney());
					//BigDecimal repayLoanMoney=realMoney;
					BigDecimal fee=new BigDecimal("0");//罚息金额
					BigDecimal lastBackAmount=new BigDecimal("0");////剩余未还款金额,所有借款汇总剩余未还
				
					TLoanContract tloanContract=new TLoanContract();
					tloanContract.setType("2");
					tloanContract.setCustomerId(userId);
					tloanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED+","+Cons.LoanContractStatus.OVERDUE);
					List<TLoanContract> tctList=tLoanContractService.findWishAllList(tloanContract);
					
					for(TLoanContract tc:tctList){
					  String startTime=tRepayPlanService.getFirstTime(tc.getId());
					  if(StringUtils.isNotBlank(startTime) && createTime.after(DateUtils.parseDate(startTime))){
						
						BigDecimal serverFee=new BigDecimal("0");//每次还款产生的月息
						RepayRecord repayRecord = new RepayRecord();
						TRepayPlan overPlan = new TRepayPlan();
						overPlan.setLoanContractId(tc.getId());
						List<TRepayPlan> planList = tRepayPlanService.findListByNum(overPlan);
						
						BigDecimal recordMoney=new BigDecimal("0");
						
						for(int i=0;i<planList.size();i++){
							  TRepayPlan plan=planList.get(i);
							  BigDecimal principal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
		                	  BigDecimal principalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
		                	  BigDecimal diffPrincipal=principal.subtract(principalReal);//剩余应还本金
		                	  BigDecimal interest=StringUtils.isBlank(plan.getInterest())?new BigDecimal("0"):new BigDecimal(plan.getInterest());
		                	  BigDecimal interestReal=StringUtils.isBlank(plan.getInterestReal())?new BigDecimal("0"):new BigDecimal(plan.getInterestReal());
		                	  BigDecimal diffInterest=interest.subtract(interestReal);//由于首期未结清 造成的本期应还金额
		                	  //double overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getAccountDate()),createTime);//逾期天数
		                	  //BigDecimal rate=new BigDecimal(tct.getLoanRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
		                      //（实际回款日期-实际第二回款日期）*逾期金额*年利率/360
		                	 // BigDecimal overFee=new BigDecimal(overdueDays).multiply(diffPrincipal.add(diffInterest)).multiply(rate);
		                	//  BigDecimal sumAmount=diffPrincipal.add(diffInterest).add(overFee);//总计应还
		                	    BigDecimal sumAmount=diffPrincipal.add(diffInterest);//总计应还
		                	  //fee=fee.add(overFee);
		                	 // realMoney=realMoney.subtract(overFee);//扣除罚息--剩下金额再还款
		                	    
		                	    if(tc.getStatus().equals(Cons.LoanContractStatus.OVERDUE)){
		                	  	  if(realMoney.compareTo(BigDecimal.ZERO)==1){
			                		  if(realMoney.compareTo(sumAmount)>=0){//结清
			  	                        plan.setStatus(Cons.RepayStatus.PAID);
			  	                        plan.setPrincipalReal(principal.toString());
			  	                        plan.setInterestReal(interest.toString());
			  	                        
			  	                        if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
			  	                        	plan.setAccountDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			  	                        }
			  	                        
			  	                        tRepayPlanService.save(plan);
			  	                        tc.setStatus(Cons.LoanContractStatus.CLEARED);//业务完成
			  	                        tLoanContractService.updateStatus(tc);	
			  	                        realMoney=realMoney.subtract(sumAmount);
			  	                        recordMoney=sumAmount;
			  	                        
			  	                      }else{//未结清---------产生逾期
			  	                        if(realMoney.compareTo(diffInterest)>=0){//先处理interest
			  	                        	plan.setInterestReal(interest.toString());
			  	                        	plan.setPrincipalReal((principalReal.add(realMoney.subtract(diffInterest)).toString()));
			  	                        }else{
			  	                        	plan.setInterestReal((interestReal.add(realMoney)).toString());
			  	                        }
			  	                        
			  	                        if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
			  	                        	plan.setAccountDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			  	                        }
			  	                         plan.setStatus(Cons.RepayStatus.IN_PAYMENT);
			  	                    	 tRepayPlanService.save(plan);
			  	                    	 
			  	                    	 recordMoney=realMoney;
			  	                    	 realMoney=new BigDecimal("0");
			  	                    	 
			  	                    	 //剩余未还
			  	                    	 BigDecimal lastInterest=StringUtils.isBlank(plan.getInterest())?new BigDecimal("0"):new BigDecimal(plan.getInterest());
			  	                    	 BigDecimal lastInterestReal=StringUtils.isBlank(plan.getInterestReal())?new BigDecimal("0"):new BigDecimal(plan.getInterestReal());
			  	                    	 BigDecimal  lastPrincipal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
			  		                	 BigDecimal lastPrincipalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
			  		                	 lastBackAmount=lastBackAmount.add(lastInterest.subtract(lastInterestReal).add(lastPrincipal.subtract(lastPrincipalReal)));
			  	                       }
			                	  }
		                	    }else{
		                	    	if(plan.getNum()==1 && !"2".equals(plan.getStatus())){
		                	    		 if(realMoney.compareTo(BigDecimal.ZERO)==1){
		                	    		  if(realMoney.subtract(diffPrincipal).compareTo(BigDecimal.ZERO)>=0){//结清----------首批多还不做处理
				                    			plan.setStatus(Cons.RepayStatus.PAID);
				                    			plan.setPrincipalReal(principal.toString());
				                    			if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
				                    				plan.setAccountDate(DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
				   	  	                        }
				                    			tRepayPlanService.save(plan);
				                    			realMoney=realMoney.subtract(diffPrincipal);
				                    			
				                    			double days = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getStartDate()),nowDate);//使用天数
				      	                	    BigDecimal feeRate=new BigDecimal(Cons.wishInterestRate.WISH_INTEREST_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
				      	                	    BigDecimal planFee=new BigDecimal(days).multiply(principal).multiply(feeRate);
				      	                	    serverFee=serverFee.add(planFee);
				                    			
				                    			recordMoney=diffPrincipal;

					                    		TRepayPlan ntp = new TRepayPlan();
					                    		ntp.setNum(2);//获取同笔借款第二批还款计划
					                    		ntp.setLoanContractId(plan.getLoanContractId());
					                    		List<TRepayPlan> nextList = tRepayPlanService.findListByNum(ntp);
					                    		if(nextList.size()==1){
					                    			TRepayPlan nextPlan=nextList.get(0);
					                    			nextPlan.setStatus(Cons.RepayStatus.PAID);
					                        		tRepayPlanService.save(nextPlan);
					                    		}
					                    		tc.setStatus(Cons.LoanContractStatus.CLEARED);//业务完成
					                    		tLoanContractService.updateStatus(tc);
					                    		
					                    		tempList.add(plan.getLoanContractId());//首期多还  加入缓存 在第二期时不做处理
					                    	
				                    			
				                        	}else{//未结清---------首批少还的部分放到下一期----------这里业务没还完不需要生成还款记录推至下期生成
				                        		plan.setStatus(Cons.RepayStatus.PAID);
				                    			plan.setPrincipalReal(principalReal.add(realMoney).toString());
				                    			
				                    			double days = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getStartDate()),nowDate);//使用天数
				      	                	    BigDecimal feeRate=new BigDecimal(Cons.wishInterestRate.WISH_INTEREST_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
				      	                	    BigDecimal planFee=new BigDecimal(days).multiply(realMoney).multiply(feeRate);
				      	                	    serverFee=serverFee.add(planFee);
				                    			
				                    			
				                    			if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
				                    				plan.setAccountDate(DateUtils.formatDate(nowDate, "yyyy-MM-dd"));
				   	  	                        }
				                    			tRepayPlanService.save(plan);
				                    			TRepayPlan ntp = new TRepayPlan();
					                    		ntp.setNum(2);//获取同笔借款第二批还款计划
					                    		ntp.setLoanContractId(plan.getLoanContractId());
				                    			List<TRepayPlan> nextList = tRepayPlanService.findListByNum(ntp);
				                    			if(nextList.size()==1){
				                    				TRepayPlan nextPlan=nextList.get(0);
				                        			//将首期未还的金额计算到下期的利息中，在二批的还款中处理
				                    				nextPlan.setStatus(Cons.RepayStatus.NO_PAID);
				                    				BigDecimal nextInterest=StringUtils.isBlank(nextPlan.getInterest())?new BigDecimal("0"):new BigDecimal(nextPlan.getInterest());
				                        			nextPlan.setInterest(nextInterest.add(principal.subtract(principalReal)).toString());
				                        			
				                        			tRepayPlanService.save(nextPlan);
				                        			
				                        			lastBackAmount=lastBackAmount.add(new BigDecimal(nextPlan.getInterest().toString()));
				                        			
				                        			recordMoney=realMoney;
				                        			realMoney=new BigDecimal("0");    
				                    			}else{
				                    				map.put("msg", "操作失败，获取第二批回款计划数据错误！");
				                    				return map;
				                    			}
				                    			
				                    			
					  		                	
				                        	}
		                	    		 }

		                	     }else if(plan.getNum()==2 && !"2".equals(plan.getStatus())){
		                	    	 if(!tempList.contains(plan.getLoanContractId()) && sumAmount.compareTo(BigDecimal.ZERO)==1){
		                	    		if(realMoney.compareTo(BigDecimal.ZERO)==1){
		                	    		 if(realMoney.subtract(sumAmount).compareTo(BigDecimal.ZERO)>=0){//结清
			                    			plan.setStatus(Cons.RepayStatus.PAID);
			                    			plan.setPrincipalReal(principal.toString());
			                    			plan.setInterestReal(interest.toString());
			                    			
			                    			double days = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getStartDate()),nowDate);//使用天数
			      	                	    BigDecimal feeRate=new BigDecimal(Cons.wishInterestRate.WISH_INTEREST_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
			      	                	    BigDecimal planFee=new BigDecimal(days).multiply(principal.add(interest)).multiply(feeRate);
			      	                	    serverFee=serverFee.add(planFee);
			                    			
			                    			
			                    			
			                    			if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
			                    				plan.setAccountDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			   	  	                        }
			                    			tRepayPlanService.save(plan);
			                    			tc.setStatus(Cons.LoanContractStatus.CLEARED);//业务完成
			                    			tLoanContractService.updateStatus(tc);
			                    			realMoney=realMoney.subtract(sumAmount);
			                    			recordMoney=sumAmount;
			                    			
			                        	}else{//未结清---------产生逾期
			                        		if(realMoney.compareTo(diffInterest)>=0){//先处理interest
			                        			plan.setInterestReal(interest.toString());
			                        			//plan.setPrincipalReal((realMoney.subtract(diffInterest).toString()));
			                        			plan.setPrincipalReal((principalReal.add(realMoney.subtract(diffInterest)).toString()));
			                        			
			                        			double days = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getStartDate()),nowDate);//使用天数
				      	                	    BigDecimal feeRate=new BigDecimal(Cons.wishInterestRate.WISH_INTEREST_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
				      	                	    BigDecimal planFee=new BigDecimal(days).multiply(interest.add(realMoney.subtract(diffInterest))).multiply(feeRate);
				      	                	    serverFee=serverFee.add(planFee);
			                        			
			                        		}else{
			                        			//plan.setInterestReal(realMoney.toString());
			                        			plan.setInterestReal((interestReal.add(realMoney)).toString());
			                        			
			                        			double days = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(plan.getStartDate()),nowDate);//使用天数
				      	                	    BigDecimal feeRate=new BigDecimal(Cons.wishInterestRate.WISH_INTEREST_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
				      	                	    BigDecimal planFee=new BigDecimal(days).multiply(realMoney).multiply(feeRate);
				      	                	    serverFee=serverFee.add(planFee);
			                        		}
			                        		//plan.setIsYuQi(Global.YES);//第二次还款没有结清，则开始逾期
			                        		//plan.setYuQi(String.valueOf(0));
			                        		plan.setStatus(Cons.RepayStatus.IN_PAYMENT);
			                        		if(StringUtils.isBlank(plan.getAccountDate())){//只以第一次还款时间为准，有则不再更新
			                    				plan.setAccountDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			   	  	                        }
			                        		recordMoney=realMoney;
			                        		realMoney=new BigDecimal("0");

			                        		tRepayPlanService.save(plan);
			                    			tc.setStatus(Cons.LoanContractStatus.OVERDUE);//业务逾期
			                    			tLoanContractService.updateStatus(tc);
			                    			 //剩余未还
			                    			 BigDecimal lastInterest=StringUtils.isBlank(plan.getInterest())?new BigDecimal("0"):new BigDecimal(plan.getInterest());
				  	                    	 BigDecimal lastInterestReal=StringUtils.isBlank(plan.getInterestReal())?new BigDecimal("0"):new BigDecimal(plan.getInterestReal());
				  	                    	 BigDecimal  lastPrincipal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
				  		                	 BigDecimal lastPrincipalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
				  		                	 lastBackAmount=lastBackAmount.add(lastInterest.subtract(lastInterestReal).add(lastPrincipal.subtract(lastPrincipalReal)));
			                        	}

		                	    	   }
		                	    		 
		                	    	}
		                	    	
		                	      }
		                	    }
	    
						}
						
					    if(recordMoney.compareTo(BigDecimal.ZERO)==1){
					    	
					    	repayRecord.setLoanContractId(tc.getId());
					    	List<RepayRecord> rrcList=repayRecordService.findList(repayRecord);
					    	BigDecimal sumRepayMoney=new BigDecimal("0");//截止本次还款 总计已还---变态的月报表要用
					    	for(RepayRecord rrd:rrcList){
					    		sumRepayMoney=sumRepayMoney.add(new BigDecimal(rrd.getMoney())); 
					    	}
					    	sumRepayMoney=sumRepayMoney.add(recordMoney);
					    	//DoubleTwo.formatDoubleUp(Double.valueOf(sumRepayMoney.doubleValue()))+""
					    	//repayRecord.setSumRepayMoney(DoubleTwo.formatDoubleUp(sumRepayMoney.doubleValue())+"");
					    	repayRecord.setSumRepayMoney(sumRepayMoney.toString());
					    	repayRecord.setMoney(recordMoney.doubleValue());
		                    repayRecord.setId(IdGen.uuid());
		                    repayRecord.setReInterest(serverFee.doubleValue());
		            		
		            		repayRecord.setRepayDate(nowDate);
		            		repayRecord.setIsYuQi("0");
		            		repayRecord.setOrganId("wish");
		            		repayRecord.setWishOverFee(0.0);
		            		repayRecord.setRepayDate(nowDate);
		            		repayRecordService.insert(repayRecord);
		            		
		            		
		            		
		            		
					    }
						
					}
				}
					rm.setFee(fee.toString());
					rm.setRepayLoanMoney(new BigDecimal(rm.getRealPayMoney()).subtract(realMoney).toString());
					rm.setUserId(userId);
					rm.setUserName(userName);
					rm.setLastBackAmount(lastBackAmount.toString());
				
					TLoanContract tt=new TLoanContract();
					tt.setCustomerId(userId);
					tt.setStatus(Cons.LoanContractStatus.UN_CLEARED+","+Cons.LoanContractStatus.CLEARED+","+Cons.LoanContractStatus.OVERDUE);
					List<TLoanContract> allList=tLoanContractService.findAllList(tt);
					int cleared=0;
					if(allList.size()>0){
						for(int i=0;i<allList.size();i++){
							TLoanContract contract=allList.get(i);
							if(contract.getStatus().equals(Cons.LoanContractStatus.UN_CLEARED) || contract.getStatus().equals(Cons.LoanContractStatus.OVERDUE)){
								cleared++;
							}
						}
						if(cleared==0){
							rm.setCashBackStatus("2");
						}else{
							rm.setCashBackStatus("1");
						}
						
					}
					
					
					BigDecimal realPayMoney=StringUtils.isBlank(rm.getRealPayMoney())?new BigDecimal("0"):new BigDecimal(rm.getRealPayMoney());
					if(realPayMoney.compareTo(BigDecimal.ZERO)==1){
						rm.setIsDeal("2");
						rmUserIds=rmUserIds+","+rm.getUserId();
						rmIds=rmIds+","+rm.getId();
					}
					this.save(rm);
					map.put("msg", "success");
					map.put("rmUserIds", rmUserIds);
					map.put("rmIds", rmIds);
					
		        }
		    }
	    }
		return map;
	}

	
	
	
	public List<ReturnedMoney> findListByIds(ReturnedMoney returnedMoney) {
		return dao.findListByIds(returnedMoney);
	}

	
	
	
	private String lastDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		int dd = cal.get(Calendar.DATE);
		String last = sdf.format(cal.getTime());
		return last;
	}

	private int getDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dd = cal.get(Calendar.DATE);
		return dd;
	}


	private Map<String,String> dateMap(Date date) throws ParseException {
		Map<String,String> map=new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH)+1;// 获取月份---Calendar月份初始值为0
		int dd = cal.get(Calendar.DATE);

		String startTime = "";
		String endTime = "";
		String months="";
		String dds="";
		if (dd > 14) {
			if(month<10){
				months="0"+month;
			}
			if(dd<10){
			    dds="0"+dd;
			}else{
				 dds=""+dd;
			}
			startTime = year + "-" + months + "-" + "15";
			endTime = lastDate(year + "-" + months + "-" + dds);
		} else {
			if(month<10){
				months="0"+month;
			}
			startTime = year + "-" + months + "-" + "01";
			endTime = year + "-" + months + "-" + "14";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}
	
	
	
	public static void main(String[] str) throws ParseException {
		/*
		 * SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd"); Date
		 * endTime1= sdf.parse("2017-07-30"); Date
		 * endTime2=sdf.parse("2017-07-31");
		 * 
		 * System.out.println(endTime1.before(endTime2));
		 */
		// System.out.println(lastDate("2017-06-02"));
		/*String returnedMoneyIds=",1,2,2,2";
		returnedMoneyIds=returnedMoneyIds.substring(1, returnedMoneyIds.length());
		String[] ids=returnedMoneyIds.split(",");
		for(int i=0;i<ids.length;i++){
			if(i==0){
				returnedMoneyIds="'"+ids[i]+"'";
			}else{
				returnedMoneyIds=returnedMoneyIds+","+"'"+ids[i]+"'";
			}
		}
		System.out.println(returnedMoneyIds);*/
		BigDecimal a=new BigDecimal("1000.00");
		BigDecimal b=new BigDecimal("500.16");
		/*a=a.multiply(new BigDecimal("100"));
		b=b.multiply(new BigDecimal("100"));*/
		a.add(b);
    	//DoubleTwo.formatDoubleUp(Double.valueOf(sumRepayMoney.doubleValue()))+""
		System.out.println((a.add(b)).toString()+"");
	}
	
	/**
	 * 秒杀货款---判断通知状态
	*/
	public Map<String, String> getWishStatus(TLoanContract tc) {
		Map<String, String> map = new HashMap<String, String>();
		if(tc.getWishStatus().equals(tc.getStatus())){//已通知
			map.put("status", "1");
		}else{//未通知
			if(tc.getStatus().equals("1")){
				map.put("msg", "操作失败，新增业务状态(1)没有通知到易联接口");
			}else if(tc.getStatus().equals("2")){
				map.put("msg", "操作失败，审核中业务状态(2)没有通知到易联接口");
			}else if(tc.getStatus().equals("5")){
				map.put("msg", "操作失败，审核通过业务状态(3)没有通知到易联接口");
			}else if(tc.getStatus().equals("0")){
				map.put("msg", "操作失败，审核不通过业务状态(4)没有通知到易联接口");
			}else if(tc.getStatus().equals("6")){
				map.put("msg", "操作失败，已放款业务状态(5)没有通知到易联接口");
			}else if(tc.getStatus().equals("8")){
				map.put("msg", "操作失败，已逾期业务状态(6)没有通知到易联接口");
			}
			map.put("status", "0");
			
		}
		return map;
	}
	
	

	
}