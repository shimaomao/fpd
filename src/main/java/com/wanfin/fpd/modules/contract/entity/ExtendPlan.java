/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 展期还款计划Entity
 * @author zzm
 * @version 2016-04-01
 */
public class ExtendPlan extends DataEntity<ExtendPlan> {
	
	private static final long serialVersionUID = 1L;
	private Integer urgeCount;		// 催收次数
	private Double interest;		// 利息
	private Double realInterest;		// 真实还款利息
	private String isOverdue;		// 是否逾期
	private Integer num;		// 第几期
	private Date overDate;		// 真实结清日期
	private Date payInterestDate;		// 利息还清时间
	private String payInterestStatus;		// 付息状态
	private Double principal;		// 本金
	private Double principalReal;		// 真实还款本金
	private String status;		// 未还
	private Integer overdueDay;		// 逾期天数
	private Date startDate;		// 计划开始日期
	private Date accountDate;		// 真实到账日期
	private String extendContractId;		// 展期业务外键id
	private Date endDate;		// 截止日期
	
	private Date beginAccountDate;		// 到账时间范围（开始）
	private Date endAccountDate;		// 到账时间范围（结束）
	
	public ExtendPlan() {
		super();
	}

	public ExtendPlan(String id){
		super(id);
	}

	public Integer getUrgeCount() {
		return urgeCount;
	}

	public void setUrgeCount(Integer urgeCount) {
		this.urgeCount = urgeCount;
	}
	
	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
	public Double getRealInterest() {
		return realInterest;
	}

	public void setRealInterest(Double realInterest) {
		this.realInterest = realInterest;
	}
	
	@Length(min=0, max=1, message="是否逾期长度必须介于 0 和 1 之间")
	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayInterestDate() {
		return payInterestDate;
	}

	public void setPayInterestDate(Date payInterestDate) {
		this.payInterestDate = payInterestDate;
	}
	
	@Length(min=0, max=5, message="付息状态长度必须介于 0 和 5 之间")
	public String getPayInterestStatus() {
		return payInterestStatus;
	}

	public void setPayInterestStatus(String payInterestStatus) {
		this.payInterestStatus = payInterestStatus;
	}
	
	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	
	public Double getPrincipalReal() {
		return principalReal;
	}

	public void setPrincipalReal(Double principalReal) {
		this.principalReal = principalReal;
	}
	
	@Length(min=0, max=5, message="未还长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=64, message="展期业务外键id长度必须介于 0 和 64 之间")
	public String getExtendContractId() {
		return extendContractId;
	}

	public void setExtendContractId(String extendContractId) {
		this.extendContractId = extendContractId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBeginAccountDate() {
		return beginAccountDate;
	}

	public void setBeginAccountDate(Date beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	public Date getEndAccountDate() {
		return endAccountDate;
	}

	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}
	
}