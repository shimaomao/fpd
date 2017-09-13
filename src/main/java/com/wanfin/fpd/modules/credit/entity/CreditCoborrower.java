/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 客户授信_共同借款人Entity
 * @author srf
 * @version 2017-03-29
 */
public class CreditCoborrower extends DataEntity<CreditCoborrower> {
	
	private static final long serialVersionUID = 1L;
	private String creditApplyId;		// credit_apply_id
	private String customerId;		// 客户id
	private String customerName;		// 客户姓名
	private String customerType;		// 客户类型（1=企业、2=个人）
	private String status;		// 状态
	private String organId;		// 租户ID
	
	public CreditCoborrower() {
		super();
	}

	public CreditCoborrower(String id){
		super(id);
	}

	@Length(min=0, max=64, message="credit_apply_id长度必须介于 0 和 64 之间")
	public String getCreditApplyId() {
		return creditApplyId;
	}

	public void setCreditApplyId(String creditApplyId) {
		this.creditApplyId = creditApplyId;
	}
	
	@Length(min=1, max=64, message="客户id长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=255, message="客户姓名长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=10, message="客户类型（1=企业、2=个人）长度必须介于 0 和 10 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=20, message="状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}