/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.entity;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 客户房产Entity
 * @author zzm
 * @version 2016-07-21
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CsHouse extends DataEntity<CsHouse> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String ownershipNo;		// 房产权证号
	private String commonStatus;		// 共有情况
	private String owner;		// 所有权人
	private String programme;		// 规划
	private String location;		// 位置
	private String purpose;		// 用途
	private String buyTime;		// 购买时间
	private BigDecimal buyprice;		// 购买价格
	private String buildTime;		// 建成时间
	private String usages;		// 使用情况
	private String isMortgage;		// 是否抵押
	private String mortgagee;		// 抵押权人
	private BigDecimal mortgageAmount;		// 抵押贷款金额
	private BigDecimal mortgageBalance;		// 抵押贷款余额
	private String loanDueTime;		// 贷款到期时间
	private String organId;		// 租户ID
	
	public CsHouse() {
		super();
	}

	public CsHouse(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户id长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=50, message="房产权证号长度必须介于 0 和 50 之间")
	public String getOwnershipNo() {
		return ownershipNo;
	}

	public void setOwnershipNo(String ownershipNo) {
		this.ownershipNo = ownershipNo;
	}
	
	@Length(min=0, max=50, message="共有情况长度必须介于 0 和 50 之间")
	public String getCommonStatus() {
		return commonStatus;
	}

	public void setCommonStatus(String commonStatus) {
		this.commonStatus = commonStatus;
	}
	
	@Length(min=0, max=20, message="所有权人长度必须介于 0 和 20 之间")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@Length(min=0, max=50, message="规划长度必须介于 0 和 50 之间")
	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}
	
	@Length(min=0, max=255, message="位置长度必须介于 0 和 255 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=50, message="用途长度必须介于 0 和 50 之间")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@Length(min=0, max=10, message="购买时间长度必须介于 0 和 10 之间")
	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	
	public BigDecimal getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(BigDecimal buyprice) {
		this.buyprice = buyprice;
	}
	
	@Length(min=0, max=10, message="建成时间长度必须介于 0 和 10 之间")
	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	
	@Length(min=0, max=50, message="使用情况长度必须介于 0 和 50 之间")
	public String getUsages() {
		return usages;
	}

	public void setUsages(String usages) {
		this.usages = usages;
	}
	
	@Length(min=0, max=1, message="是否抵押长度必须介于 0 和 1 之间")
	public String getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}
	
	@Length(min=0, max=50, message="抵押权人长度必须介于 0 和 50 之间")
	public String getMortgagee() {
		return mortgagee;
	}

	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	
	public BigDecimal getMortgageAmount() {
		return mortgageAmount;
	}

	public void setMortgageAmount(BigDecimal mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}
	
	public BigDecimal getMortgageBalance() {
		return mortgageBalance;
	}

	public void setMortgageBalance(BigDecimal mortgageBalance) {
		this.mortgageBalance = mortgageBalance;
	}
	
	@Length(min=0, max=10, message="贷款到期时间长度必须介于 0 和 10 之间")
	public String getLoanDueTime() {
		return loanDueTime;
	}

	public void setLoanDueTime(String loanDueTime) {
		this.loanDueTime = loanDueTime;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}