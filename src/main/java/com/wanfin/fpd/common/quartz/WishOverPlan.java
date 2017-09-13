package com.wanfin.fpd.common.quartz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.fop.cli.Main;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;

public class WishOverPlan {
		@Autowired
		private TLoanContractService tLoanContractService;
		
		
		@Autowired
		TRepayPlanService tRepayPlanService;
		/** 
	     * 定时任务，执行方法 
	     * */  
	    public void execute(){
	    	TRepayPlan overPlan = new TRepayPlan();
			System.out.println("------------quartz定时更新wish业务逾期情况---------------");
			overPlan.setStatus(Cons.RepayStatus.NEED_PAY);
			overPlan.setNum(2);
			List<TRepayPlan> wishList = tRepayPlanService.findAllListByWish(overPlan);//统计逾期未还多少笔
			
			for(int i=0;i<wishList.size();i++){
				TRepayPlan wishtrp=wishList.get(i);
				TLoanContract tct=tLoanContractService.get(wishtrp.getLoanContractId());
		        BigDecimal principal=StringUtils.isBlank(wishtrp.getPrincipal())?new BigDecimal("0"):new BigDecimal(wishtrp.getPrincipal());
		        BigDecimal principalReal=StringUtils.isBlank(wishtrp.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(wishtrp.getPrincipalReal());
		        BigDecimal diffPrincipal=principal.subtract(principalReal);//剩余应还本金
		        BigDecimal interest=StringUtils.isBlank(wishtrp.getInterest())?new BigDecimal("0"):new BigDecimal(wishtrp.getInterest());
		        BigDecimal interestReal=StringUtils.isBlank(wishtrp.getInterestReal())?new BigDecimal("0"):new BigDecimal(wishtrp.getInterestReal());
		        BigDecimal diffInterest=interest.subtract(interestReal);//由于首期未结清 造成的本期应还金额
		        double overdueDays=0.0;
		        if(StringUtils.isBlank(wishtrp.getAccountDate())){
		            overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(wishtrp.getEndDate()),new Date());//发生过还款-----逾期天数
		        }else{
		            overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(wishtrp.getAccountDate()),new Date());//未发生过还款-----逾期天数
		        }
		        if (overdueDays > 0) {//逾期
		            wishtrp.setYuQi(String.valueOf(overdueDays));
		            wishtrp.setIsYuQi(Global.YES);
		            TRepayPlan onePlan=new TRepayPlan();
		            onePlan.setNum(1);
		            onePlan.setLoanContractId(wishtrp.getLoanContractId());
		            onePlan=tRepayPlanService.get(onePlan);
		            if(onePlan.getStatus().equals(Cons.RepayStatus.NO_PAID)){//没有处理过还款操作(如果已经发过还款会在操作时处理两批还款计划的关系)
		            	 BigDecimal onePrincipal=StringUtils.isBlank(onePlan.getPrincipal())?new BigDecimal("0"):new BigDecimal(onePlan.getPrincipal());
		 		         wishtrp.setInterest(onePrincipal.toString());//转至第二期
		 		         onePlan.setPrincipalReal("0");
		 		         onePlan.setStatus(Cons.RepayStatus.PAID);
		 		         tRepayPlanService.save(onePlan);
		            }
		            tRepayPlanService.save(wishtrp);
		            tct.setStatus(Cons.LoanContractStatus.OVERDUE);//业务逾期
                    tLoanContractService.updateStatus(tct);
		            
		      	}
				
			
			}
			
		    
	    }
	    
	
	    
}
