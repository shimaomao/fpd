package com.wanfin.fpd.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;

/**
 * 贷款利息等计算
 * 
 * @author user
 * @date 20160419
 */
public class LoanCalculation {
	/**
	 * 100
	 */
	public static final BigDecimal RATE = new BigDecimal(100);
	/**
	 * 12
	 */
	public static final BigDecimal MONTHOFYEAR = new BigDecimal(12);
	/**
	 * 30
	 */
	public static final BigDecimal DAYOFMONTH = new BigDecimal(30);//一月天数
	/**
	 * 365
	 */
	public static final BigDecimal DAYOFYEAR = new BigDecimal(365);//一年天数
	/**
	 * SCALE = 2
	 */
	public static final int SCALE = 2;//小数点位数
	
	/**
	 * 根据业务信息生成还款计划
	 * @param loanContract
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<TRepayPlan> getRepayPlan(TLoanContract loanContract){
		List<TRepayPlan> list = new ArrayList<TRepayPlan>();
		try{
			//还款周期确定periodType 1年；2月；3日
			int periodType = Integer.parseInt(loanContract.getPeriodType());
			//贷款期限确定loanPeriod还款期数
			int loanPeriod = Integer.parseInt(loanContract.getLoanPeriod());
			//还款本金
			BigDecimal loanAmount = new BigDecimal(loanContract.getLoanAmount());
			//贷款利率
			BigDecimal loanRate = new BigDecimal(loanContract.getLoanRate());
			//贷款利率类型
			String loanRateType = loanContract.getLoanRateType();
			//放款日期
			Date loanDate = loanContract.getLoanDate();
			//还本金日期
			Date payPrincipalDate = loanContract.getPayPrincipalDate();
			//每期还款日 1表示按payDay来计算，2表示按放款日期计算,是控制还利息时间
			String payDayType = loanContract.getPayDayType();
			String payDay = loanContract.getPayDay();
			Date repayDate = null;
			if("1".equals(payDayType)){
				repayDate = DateUtils.setDays(loanDate, Integer.parseInt(payDay));
				int comp = repayDate.compareTo(loanDate);
				if(comp<=0){
					repayDate = DateUtils.addMonths(repayDate, 1);
				}
			}
			//1.先计算期数
			//2.该期是否满期
			//3.不满期按天算
			//4.满期按期算
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	
	/**
	 * 按月付息，按月付息到期还本
	 * 1.一次性支付全部利息：直接计算利息，然后再还本金，只设定两期
	 * 2.非一次性付息：
	 *             a:计算每期的利息生成计划，周期循环（前置和后置只是时间不同）
	 *             b:本金
	 */
	
	
	//前置付息
	
	//等额本息:Matching service
	public void matchingService(){
		
	}
	//等额本金:Equal principal
	public void equalPrincipal(){
		
	}
	
	/**
	 * 根据还款方式、还款周期、放款日期和贷款期限计算还本金日期
	 * @param payType 还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
	 * @param periodType 还款周期 : 1|2|3  = 年|月|日 
	 * @param loanDate 放款日期
	 * @param loanPeriod 贷款期限
	 * @return 还本金日期
	 */
	public static Date getPayPrincipalDate(String payType,String periodType,Date loanDate,int loanPeriod){
		Date payPrincipalDate = null;
		
		if("3".equals(payType)){//按期收息，到期还本
			if("3".equals(periodType)){//日
				payPrincipalDate = addDate(loanDate,0,0,loanPeriod-1,0);
			}else if("2".equals(periodType)){//月
				payPrincipalDate = addDate(loanDate,0,loanPeriod,-1,0);
			}else if("1".equals(periodType)){//年
				payPrincipalDate = addDate(loanDate,loanPeriod,0,-1,0);
			}
		}else if("1".equals(payType) || "2".equals(payType)){
			payPrincipalDate = addDate(loanDate,0,loanPeriod,-1,0);
		}
		
		return payPrincipalDate;
	}
	
	/**
	 * 日期处理
	 * @param date 日期
	 * @param years 加减年
	 * @param months 加减年
	 * @param days 加减日
	 * @param interestDay 设定日期
	 * @return
	 */
	public static Date addDate(Date date,int years,int months, int days, int interestDay) {
		if(years != 0){
			date = DateUtils.addYears(date, years);
		}
		if(months != 0){
			date = DateUtils.addMonths(date, months);
		}
		if(days != 0){
			date = DateUtils.addDays(date, days);
		}
		if(interestDay != 0){
			date = DateUtils.setDays(date, interestDay);
		}
		return date;
	}
	
	/**
	 * 利息计算，小数点后两位，可以通过SCALE来设置
	 * @param principal 本金
	 * @param dayRate 日利率
	 * @param startDay 计息开始时间
	 * @param endDay 计息结束时间
	 * @return 利息,小数点后两位
	 */
	public static BigDecimal calculateInterest(BigDecimal principal,BigDecimal dayRate, String startDay, String endDay){
		return calculateInterest( principal, dayRate, DateUtils.parseDate(startDay), DateUtils.parseDate(endDay));
	}
	/**
	 * 利息计算，小数点后两位，可以通过SCALE来设置
	 * @param principal 本金
	 * @param dayRate 日利率
	 * @param startDay 计息开始时间
	 * @param endDay 计息结束时间
	 * @return 利息,小数点后两位
	 */
	public static BigDecimal calculateInterest(BigDecimal principal,BigDecimal dayRate, Date startDay, Date endDay){
		double days = getDistanceOfTwoDate(startDay,endDay);//计算天数
		
		//利息 = 本金*日利率*天数
		BigDecimal interest = principal.multiply(dayRate).multiply(new BigDecimal(days));
		return interest.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 利息计算，小数点后两位，可以通过SCALE来设置
	 * @param principal 本金
	 * @param LendingRates 利率
	 * @param loanPeriodType 利率周期（年:3;月:2,日:1 ）
	 * @param startDay 计息开始时间
	 * @param endDay 计息结束时间
	 * @return 利息,小数点后两位
	 */
	public static BigDecimal calculateInterest(BigDecimal principal,BigDecimal LendingRates,int loanPeriodType, Date startDay, Date endDay){
		BigDecimal dayRate = calcDayLendingRates(LendingRates, loanPeriodType);//日利率
		double days = getDistanceOfTwoDate(startDay,endDay);//计算天数
		
		//利息 = 本金*日利率*天数
		BigDecimal interest = principal.multiply(dayRate).multiply(new BigDecimal(days));
		return interest.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 计算月贷款利率
	 * 
	 * @param LendingRates
	 *            原贷款利率
	 * @param loanType
	 *            贷款周期（年:3;月:2,日:1 ）
	 * @return 日贷款利率
	 */
	public static BigDecimal calcMonthLendingRates(BigDecimal LendingRates, int loanType) {
		switch (loanType) {
			case 3:		//日
				return LendingRates.multiply(DAYOFMONTH).divide(RATE);
			case 2:		//月
				return LendingRates.divide(RATE);
			case 1:		//年
				return LendingRates.divide(MONTHOFYEAR).divide(RATE);
			default:
				return null;
		}
	}
	
	/**
	 * 计算月贷款利率
	 * 
	 * @param LendingRates
	 *            原贷款利率
	 * @param periodType
	 *            贷款周期（年:3;月:2,日:1 ）
	 * @return 日贷款利率
	 */
	public static BigDecimal calcMonthLendingRates(BigDecimal LendingRates, String loanType) {
		if("日".equals(loanType)){
			return LendingRates.multiply(DAYOFMONTH).divide(RATE);
		}else if("月".equals(loanType)){//
			 LendingRates.divide(RATE);
		}else if("年".equals(loanType)){
			LendingRates.divide(MONTHOFYEAR).divide(RATE);
		}
		
		return null;
	}
	
	/**
	 * 计算日贷款利率
	 * 
	 * @param LendingRates
	 *            原贷款利率
	 * @param loanType
	 *            贷款周期（年:3;月:2,日:1 ）
	 * @return 日贷款利率
	 */
	public static BigDecimal calcDayLendingRates(BigDecimal LendingRates, int loanType) {
		BigDecimal days = null;

		switch (loanType) {
			case 3:		//日
				days = new BigDecimal(1);
				break;
			case 2:		//月
				days = DAYOFMONTH;
				break;
			case 1:		//年
				days = DAYOFYEAR;
				break;
			default:
				days = new BigDecimal(1);
		}
		
		return LendingRates.divide(days).divide(RATE);
	}
	
	/**
	 * 计算日贷款利率
	 * 
	 * @param LendingRates
	 *            原贷款利率
	 * @param periodType
	 *            贷款周期（年:3;月:2,日:1 ）
	 * @return 日贷款利率
	 */
	public static BigDecimal calcDayLendingRates(BigDecimal LendingRates, String loanType) {
		BigDecimal days = null;

		if("日".equals(loanType)){
			days = new BigDecimal(1);
		}
		else if("月".equals(loanType)){//
			days = DAYOFMONTH;
		}else if("年".equals(loanType)){//年
			days = DAYOFYEAR;
		}
		
		return LendingRates.divide(days).divide(RATE);
	}

	/**
	 * 计算天数
	 * 
	 * @param startDay
	 * @param endDay
	 * @return 两个时间之间的天数(正数)，-1为日期错误
	 */
	public static double getDistanceOfTwoDate(Date startDay, Date endDay) {
		if (startDay == null || endDay == null) {
			return -1;
		}

		double days = DateUtils.getDistanceOfTwoDate(startDay, endDay);
		if (days < 0) {
			return -days;
		} else {
			return days;
		}
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 *            前面的日期
	 * @param after
	 *            后面的日期
	 * @return 两个时间之间的天数(正数)，-1为日期错误
	 */
	public static double getDistanceOfTwoDate(String before, String after) {
		Date beforeDate = DateUtils.parseDate(before);
		Date afterDate = DateUtils.parseDate(after);
		if (beforeDate == null || afterDate == null) {
			return -1;
		}
		double days = DateUtils.getDistanceOfTwoDate(beforeDate, afterDate);
		if (days < 0) {
			return -days;
		} else {
			return days;
		}
	}
	
	public static void main(String args[]) throws Exception{
		String first = "2016-04-29";
		String second = "2016-05-19";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/*
		
		Date d1 = DateUtils.parseDate(first);
		Date d2 = DateUtils.parseDate(second);
		int i = d1.compareTo(d2);
		if(i<=0){
			Date tmp = DateUtils.addMonths(d1, 1);
			System.out.println(DateUtils.formatDate(tmp));
		}
		
		String str = "2016-01-31";
		Date dstr = DateUtils.parseDate(str);
		Date dsAdd = DateUtils.addMonths(dstr, 1);
		System.out.println(DateUtils.formatDate(dsAdd));
		*/
		//日期比较
		System.out.println(DateUtils.truncatedCompareTo(sdf.parse(first), sdf.parse(second), 0));
	}
}
