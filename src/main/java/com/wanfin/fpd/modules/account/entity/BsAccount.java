/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.entity;

import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 资金账户信息Entity
 * @author srf
 * @version 2017-01-03
 */
public class BsAccount extends DataEntity<BsAccount> {
	
	private static final long serialVersionUID = 1L;
	private String authUserId;		// 认证用户
	private String userName;		// 认证用户名
	private BigDecimal totalFunds;		// 总资金
	private BigDecimal availableFunds;		// 总可用资金
	private BigDecimal freezingFunds;		// 总冻结资金
	private BigDecimal marginAmount;		// 保证金
	private String buserId;			//B端用户ID
	
	public BsAccount() {
		super();
	}

	public BsAccount(String id){
		super(id);
	}

	@Length(min=1, max=64, message="认证用户长度必须介于 1 和 64 之间")
	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}
	
	@Length(min=0, max=255, message="认证用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public BigDecimal getTotalFunds() {
		return totalFunds;
	}

	public void setTotalFunds(BigDecimal totalFunds) {
		this.totalFunds = totalFunds;
	}
	
	public BigDecimal getAvailableFunds() {
		return availableFunds;
	}

	public void setAvailableFunds(BigDecimal availableFunds) {
		this.availableFunds = availableFunds;
	}
	
	public BigDecimal getFreezingFunds() {
		return freezingFunds;
	}

	public void setFreezingFunds(BigDecimal freezingFunds) {
		this.freezingFunds = freezingFunds;
	}
	
	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public String getBuserId() {
		return buserId;
	}

	public void setBuserId(String buserId) {
		this.buserId = buserId;
	}
	
}