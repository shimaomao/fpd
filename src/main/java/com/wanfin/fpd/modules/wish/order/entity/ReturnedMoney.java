/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.entity;

import java.beans.Transient;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.contract.IContract;
import com.wanfin.fpd.modules.contract.IContract.IContractUkey;

/**
 * 回款记录Entity
 * @author cjp
 * @version 2017-07-07
 */
public class ReturnedMoney extends DataEntity<ReturnedMoney> implements IContract{
	
	private String amount;		// 结算金额
	private String channel;		// 通道
	private String merchantId;		// 收款地址(商铺id)
	
	
	private static final long serialVersionUID = 1L;

	private String enterTime;		// 录入时间
	private String accountNum;		// 收款账号
	private String accountName;		// 收款开户名
	private String accountBank;		// s收款方银行
	private String realPayMoney;		// 实付金额
	private String realPayCurrency;		// 实付金额币种
	
	private String orderId; //代收付订单号
	private String accountProvice; //开户省份
	private String accountCity; //开户城市
	private String accountCategory; //账户种类
	private String accountType; //账户类型
	private String identityType; //开户证件类型
	private String identityNum; //开户证件号
	private String tradeCurrency; //交易币种
	private String merchantOrderId; //wish商户订单号+
	private String merchantSerialNum; //商户流水号
	private String fileName; //回款文件名
	
	
	private String status; //  0初始状态 1 通知易联生成扣款报表  2通知贝通还款报表
	
	
	private String isDeal;//是否处理过-----1，已处理计算应扣款    2，收到操作完成， 3，收到操作完成且通知易联成功
	private String repayLoanMoney;//当次还款总金额--处理时添加
	private String fee;//当次罚息总金额--处理时添加
	private String userId;//借款人id--处理时添加
	private String userName;//借款人姓名--处理时添加
	
	private String cashBackStatus;//回款状态，所有借款汇总状态
	private String lastBackAmount;//剩余未还款金额,所有借款汇总剩余未还
	private String fileId;//回款excel ID用于关联打款图片
	
	//业务统计分析搜索框，不存里数据库
	private Date starttime;
	private Date endtime;
	
	
	public ReturnedMoney() {
		super();
	}

	public ReturnedMoney(String id){
		super(id);
	}
	

	@Length(min=0, max=255, message="录入时间长度必须介于 0 和 255 之间")
	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	
	@Length(min=0, max=255, message="结算金额长度必须介于 0 和 255 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=255, message="收款账号长度必须介于 0 和 255 之间")
	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	@Length(min=0, max=255, message="收款开户名长度必须介于 0 和 255 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=0, max=255, message="s收款方银行长度必须介于 0 和 255 之间")
	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	
	@Length(min=0, max=255, message="收款地址(商铺id)长度必须介于 0 和 255 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=255, message="通道长度必须介于 0 和 255 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Length(min=0, max=255, message="实付金额长度必须介于 0 和 255 之间")
	public String getRealPayMoney() {
		return realPayMoney;
	}

	public void setRealPayMoney(String realPayMoney) {
		this.realPayMoney = realPayMoney;
	}
	
	@Length(min=0, max=255, message="实付金额币种长度必须介于 0 和 255 之间")
	public String getRealPayCurrency() {
		return realPayCurrency;
	}

	public void setRealPayCurrency(String realPayCurrency) {
		this.realPayCurrency = realPayCurrency;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountProvice() {
		return accountProvice;
	}

	public void setAccountProvice(String accountProvice) {
		this.accountProvice = accountProvice;
	}

	public String getAccountCity() {
		return accountCity;
	}

	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getTradeCurrency() {
		return tradeCurrency;
	}

	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public String getMerchantSerialNum() {
		return merchantSerialNum;
	}

	public void setMerchantSerialNum(String merchantSerialNum) {
		this.merchantSerialNum = merchantSerialNum;
	}



	public String getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public String getCashBackStatus() {
		return cashBackStatus;
	}

	public void setCashBackStatus(String cashBackStatus) {
		this.cashBackStatus = cashBackStatus;
	}

	public String getLastBackAmount() {
		return lastBackAmount;
	}

	public void setLastBackAmount(String lastBackAmount) {
		this.lastBackAmount = lastBackAmount;
	}

	@Override
	@Transient
	public String getUkey() {
		return IContractUkey.LOAN;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


}