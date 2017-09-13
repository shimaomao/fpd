/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customertogether.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 共同借款Entity
 * @author lx
 * @version 2016-08-22
 */
public class TCustomerTogether extends DataEntity<TCustomerTogether> {
	
	private static final long serialVersionUID = 1L;
	private String customerType;		// 客户类型
	private String customerId;		// 客户id
	private String customerName;		// 客户姓名
	private String togetherMoney;		// 共同借款金额
	private String loanMoney;		// 单人借款金额
	private String mainEmployeeid;		// 主借款人（个人客户）
	private String mainCompanyid;		// 主借款人（企业客户id）
	
	public TCustomerTogether() {
		super();
	}

	public TCustomerTogether(String id){
		super(id);
	}

	@Length(min=1, max=50, message="客户类型长度必须介于 1 和 50 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=50, message="客户id长度必须介于 0 和 50 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=50, message="客户姓名长度必须介于 0 和 50 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=50, message="共同借款金额长度必须介于 0 和 50 之间")
	public String getTogetherMoney() {
		return togetherMoney;
	}

	public void setTogetherMoney(String togetherMoney) {
		this.togetherMoney = togetherMoney;
	}
	
	@Length(min=0, max=50, message="单人借款金额长度必须介于 0 和 50 之间")
	public String getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	
	@Length(min=0, max=50, message="主借款人（个人客户）长度必须介于 0 和 50 之间")
	public String getMainEmployeeid() {
		return mainEmployeeid;
	}

	public void setMainEmployeeid(String mainEmployeeid) {
		this.mainEmployeeid = mainEmployeeid;
	}
	
	@Length(min=0, max=50, message="主借款人（企业客户id）长度必须介于 0 和 50 之间")
	public String getMainCompanyid() {
		return mainCompanyid;
	}

	public void setMainCompanyid(String mainCompanyid) {
		this.mainCompanyid = mainCompanyid;
	}
	
}