package com.wanfin.fpd.common.quartz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.SmsSendUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
/**
 * 还款提醒
 * @Description 
 * @author lx
 * @date 20160729
 */
public class repaywarn {
	@Autowired
	private TRepayPlanService tRepayPlanService;
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private TEmployeeService tEmployeeService;
	
    /** 
     * 定时任务，执行方法 
     * #2316 #2314
     * */  
    public void execute(){  
    	String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.CHINESE).format(System.currentTimeMillis());  
    	System.out.println("——————————————————————————————————————————————————————————————————————————1——");
        System.out.println("time:"+time+">>当前定时任务正在执行,还款提醒");  
        System.out.println("——————————————————————————————————————————————————————————————————————————1——");
        TRepayPlan repayPlan = new TRepayPlan();
        repayPlan.setStatus(Cons.RepayStatus.NEED_PAY);
        List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(repayPlan);
        for(TRepayPlan tmpPlan:repayPlanList){
        	if(tmpPlan.getLoanContract() == null){
        		continue;
        	}
        	String contractNo = tmpPlan.getLoanContract().getContractNumber();		// 合同编号
        	//Integer num = tmpPlan.getNum();		//还款笔数
        	String endDate = tmpPlan.getEndDate();		//还款截止日期
        	if(StringUtils.isAnyBlank(contractNo, endDate)){
        		continue;
        	}
        	
        	BigDecimal interest = new BigDecimal(tmpPlan.getInterest());	//应还利息
        	BigDecimal principal = new BigDecimal(tmpPlan.getPrincipal());	//应还本金
        	BigDecimal interestReal = null;	//已还利息
        	BigDecimal principalReal = null;	//已还本金
        	if(StringUtils.isNotBlank(tmpPlan.getInterestReal())){
        		interestReal = new BigDecimal(tmpPlan.getInterestReal());
        	}
        	if(StringUtils.isNotBlank(tmpPlan.getPrincipalReal())){
        		principalReal = new BigDecimal(tmpPlan.getPrincipalReal());
        	}
        	BigDecimal repaymentAmount = interest.add(principal);//所需还款金额
        	if(interestReal != null){//扣除已还利息
        		repaymentAmount = repaymentAmount.subtract(interestReal);
        	}
        	if(principalReal != null){//扣除已还本金
        		repaymentAmount = repaymentAmount.subtract(principalReal);
        	}
        	
        	//时间判断
        	double subDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(DateUtils.getDate()),DateUtils.parseDate(endDate));
        	//System.out.println(DateUtils.getDate() + "与" + endDate + "相差天数:"+subDays);
        	if(subDays == 0 || subDays == 5){
        		String sendSms = null;
	        	if(subDays == 0){
	        		sendSms = Cons.Msg.MSG_ALERT_NOWDATE;
	        		
	        	}else if(subDays == 5){
	        		sendSms = Cons.Msg.MSG_ALERT_AHEAD;
	        		sendSms = sendSms.replace("#date#",endDate);
	        	}
	        	
	        	//获取要发送短信的手机号码
	        	String phone = null;
	        	TLoanContract entity = null;
	        	if (StringUtils.isNotBlank(tmpPlan.getLoanContractId())){
	        		entity = tLoanContractService.get(tmpPlan.getLoanContractId());
	        		if(entity != null){
	        			if("1".equals(entity.getCustomerType())){
	    					TCompany company = tCompanyService.get(entity.getCustomerId());
	    					phone = company.getSuretyMobile();
	    				}else if("2".equals(entity.getCustomerType())){
	    					TEmployee employee = tEmployeeService.get(entity.getCustomerId());
	    					phone = employee.getMobile();
	    				}
	        		}
	        	}
	        	
	        	if(StringUtils.isNotBlank(sendSms) && StringUtils.isNotBlank(phone) && sendSms.contains("#number#") && sendSms.contains("#money#")){
	        		sendSms = sendSms.replace("#number#",contractNo);
	        		sendSms = sendSms.replace("#money#",String.valueOf(repaymentAmount));
	        		System.out.println("向"+phone+"发送提醒短信内容:"+sendSms);
	        		try {
						SmsSendUtil.sendSmsBatch(sendSms, phone,"1");//发送短信，3代表是放款提醒（给客户提醒已经放款）
					} catch (Exception e) {
						e.printStackTrace();
					} 
	        	}
        	}
        }
    }
}
