/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.trading.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 交易记录Entity
 * @author zzm
 * @version 2016-06-14
 */
public class TradingRecord extends DataEntity<TradingRecord> {
	
	private static final long serialVersionUID = 1L;
	private String seqNo;		// 交易流水号
	private String tradingType;		// 交易类型
	private String tradingWay;		// 交易方式
	private BigDecimal amount;		// 交易金额
	private String loginName;		// 账户
	private String trader;		// 交易对象
	private Date tradingTime;		// 交易时间
	private String businessId;		// 交易业务id
	private String businessName;		// 交易业务名称
	
	//用于查询
	private Date beginTradingTime;		
	private Date endTradingTime;		
	
	public TradingRecord() {
		super();
	}

	public TradingRecord(String id){
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
	
	@Length(min=0, max=100, message="账户长度必须介于 0 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=50, message="交易对象长度必须介于 0 和 50 之间")
	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
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

	public Date getBeginTradingTime() {
		return beginTradingTime;
	}

	public void setBeginTradingTime(Date beginTradingTime) {
		this.beginTradingTime = beginTradingTime;
	}

	public Date getEndTradingTime() {
		return endTradingTime;
	}

	public void setEndTradingTime(Date endTradingTime) {
		this.endTradingTime = endTradingTime;
	}
	
}