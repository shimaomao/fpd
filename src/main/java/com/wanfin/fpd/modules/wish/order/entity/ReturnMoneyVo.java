package com.wanfin.fpd.modules.wish.order.entity;

import java.util.Date;

public class ReturnMoneyVo {
	
	
	

	private String accountNum;		// 收款账号

	
	private String orderId; //代收付订单号

	private String fileName; //回款文件名
	
	private String repayLoanMoney;//当次还款总金额--处理时添加
	private String fee;//当次罚息总金额--处理时添加
	private String userId;//借款人id--处理时添加
	private String userName; 
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getRepayLoanMoney() {
		return repayLoanMoney;
	}
	public void setRepayLoanMoney(String repayLoanMoney) {
		this.repayLoanMoney = repayLoanMoney;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	
}
