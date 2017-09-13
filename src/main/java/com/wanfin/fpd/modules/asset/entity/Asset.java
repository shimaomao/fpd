/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.asset.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 账户资产Entity
 * @author zzm
 * @version 2016-06-14
 */
public class Asset extends DataEntity<Asset> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		// 用户登录名
	private BigDecimal totalAsset;		// 总资产
	private BigDecimal balance;		// 余额
	private BigDecimal availableBalance;		// 可用余额
	private BigDecimal freezeAmount;		// 冻结资金
	
	public Asset() {
		super();
	}

	public Asset(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户登录名长度必须介于 0 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public BigDecimal getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(BigDecimal totalAsset) {
		this.totalAsset = totalAsset;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	
	
}