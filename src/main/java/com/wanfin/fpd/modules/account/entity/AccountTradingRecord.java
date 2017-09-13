/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.entity;

import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;


/**
 * 资金明细Entity
 * @author lzj
 * @version 2016-11-23
 */
public class AccountTradingRecord extends DataEntity<AccountTradingRecord> {
	
	private static final long serialVersionUID = 1L;
	private String seqNo;		// 交易流水号
	private String tradingType;		// 交易类型:交易类型(1充值，2提现，3回收利息，4回收本金，5，回收本息 6投资 ，7还款，8放款，9借款 10提现手续费)
	private String tradingWay;		// 交易方式:交易方式(1，线上流水,2，线下流水)
	private BigDecimal amount;		// 交易金额
	private BigDecimal availableBalance; //交易后可用余额
	private BigDecimal totalFunds; //交易后总资金
	private BigDecimal freezingFunds; //交易后总冻结资金
	
	private String accountId;		// 账户ID
	private String auhtUserId;  //认证用户
	private Date tradingTime;		// 交易时间
	private String businessId;		// 交易业务id
	private String businessName;		// 交易业务名称(推荐交易类型与交易名称组合)
	
	private String recordType;//记录类型：1.收入  2.支出
	
	public AccountTradingRecord() {
		super();
	}

	public AccountTradingRecord(String id){
		super(id);
	}

	@Length(min=0, max=50, message="交易流水号长度必须介于 0 和 50 之间")
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	@Length(min=0, max=100, message="交易类型长度必须介于 0 和 100 之间")
	public String getTradingType() {
		return tradingType;
	}


	public void setTradingType(String tradingType) {
		this.tradingType = tradingType;
	}
	
	@Length(min=0, max=100, message="交易方式长度必须介于 0 和 100 之间")
	public String getTradingWay() {
		return tradingWay;
	}

	public void setTradingWay(String tradingWay) {
		this.tradingWay = tradingWay;
	}
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTradingTime() {
		return tradingTime;
	}

	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}
	
	@Length(min=0, max=64, message="交易业务id长度必须介于 0 和 64 之间")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	@Length(min=0, max=100, message="交易业务名称长度必须介于 0 和 100 之间")
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAuhtUserId() {
		return auhtUserId;
	}

	public void setAuhtUserId(String auhtUserId) {
		this.auhtUserId = auhtUserId;
	}

	public BigDecimal getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(BigDecimal totalFunds) {
		this.totalFunds = totalFunds;
	}

	public BigDecimal getFreezingFunds() {
		return freezingFunds;
	}

	public void setFreezingFunds(BigDecimal freezingFunds) {
		this.freezingFunds = freezingFunds;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
}