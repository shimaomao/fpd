/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.bank.entity.account;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 银行账户Entity
 * @author chenh
 * @version 2016-03-29
 */
public class TAccount extends DataEntity<TAccount> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String type;		// 客户类型
	private String gatheringBank;		// 开户行
	private String gatheringName;		// 开户名
	private String gatheringNumber;		// 开户账号
	private String organId;		// 租户ID
	
	public TAccount() {
		super();
	}

	public TAccount(String id){
		super(id);
	}

	@Length(min=0, max=50, message="客户id长度必须介于 0 和 50 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=11, message="客户类型长度必须介于 0 和 11 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="开户行长度必须介于 0 和 255 之间")
	public String getGatheringBank() {
		return gatheringBank;
	}

	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	
	@Length(min=0, max=255, message="开户名长度必须介于 0 和 255 之间")
	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	
	@Length(min=0, max=255, message="开户账号长度必须介于 0 和 255 之间")
	public String getGatheringNumber() {
		return gatheringNumber;
	}

	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	
	@Length(min=0, max=50, message="租户ID长度必须介于 0 和 50 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}