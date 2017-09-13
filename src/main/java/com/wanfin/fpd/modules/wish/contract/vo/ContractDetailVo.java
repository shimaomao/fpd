package com.wanfin.fpd.modules.wish.contract.vo;

public class ContractDetailVo {
	String number;//业务编号 	YLZF91203091030131
	String type;//产品类型 	订单贷
	String days;//业务类型 	30日
	String amount;//贷款金额（元） 	20,000.00
	String appayDate;//申请时间 	2017-7-28
	String charge;//手续费 	200.00
	String realAmount;//实际到账金额 	219,800.00
	String loanDate;//放款时间 	2017-7-29
	String bankNum;//放款银行账户 	6216616502000655911
	String firstMoney;//第一个还款日应还金额（元) 	12,000.00
	String secondMoney;//第二个还款日应还金额（元） 	8,000.00
	String firstAccountDate;//实际第一个还款日期 	2017-8-8
	String realFirstMoney;//第一个还款日实际还款额（元） 	10,000.00
	
	String secondAccountDate;//实际第二个还款日期 	-
	String realSecondMoney;//第二个还款日实际还款额（元） 	-
	String diffAmount;//剩余未还金额（元） 	10,000.00
	String overAmount;//逾期金额（元） 	0
	String overFee;//逾期罚息（元） 	0
	String status;//业务状态 	未结清
	
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAppayDate() {
		return appayDate;
	}
	public void setAppayDate(String appayDate) {
		this.appayDate = appayDate;
	}

	
	
	
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getFirstMoney() {
		return firstMoney;
	}
	public void setFirstMoney(String firstMoney) {
		this.firstMoney = firstMoney;
	}
	public String getSecondMoney() {
		return secondMoney;
	}
	public void setSecondMoney(String secondMoney) {
		this.secondMoney = secondMoney;
	}
	public String getFirstAccountDate() {
		return firstAccountDate;
	}
	public void setFirstAccountDate(String firstAccountDate) {
		this.firstAccountDate = firstAccountDate;
	}
	public String getRealFirstMoney() {
		return realFirstMoney;
	}
	public void setRealFirstMoney(String realFirstMoney) {
		this.realFirstMoney = realFirstMoney;
	}
	public String getSecondAccountDate() {
		return secondAccountDate;
	}
	public void setSecondAccountDate(String secondAccountDate) {
		this.secondAccountDate = secondAccountDate;
	}
	public String getRealSecondMoney() {
		return realSecondMoney;
	}
	public void setRealSecondMoney(String realSecondMoney) {
		this.realSecondMoney = realSecondMoney;
	}
	public String getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(String diffAmount) {
		this.diffAmount = diffAmount;
	}
	public String getOverAmount() {
		return overAmount;
	}
	public void setOverAmount(String overAmount) {
		this.overAmount = overAmount;
	}
	public String getOverFee() {
		return overFee;
	}
	public void setOverFee(String overFee) {
		this.overFee = overFee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
