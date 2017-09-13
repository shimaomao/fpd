/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.credit.entity.CreditApply;

/**
 * 押品借出审批Entity
 * @author lzj
 * @version 2016-09-23
 */
public class TMortgageApplay extends ActEntity<TMortgageApplay>  {
	
	private static final long serialVersionUID = 1L;
	private String mortgageContractId;		// 抵押(质押)合同ID
	private String applayType;		// 押品类型(抵押，质押)
	private Date startTime;		// 押出开始时间
	private Date endTime;		// 押出结束时间
	private String status;		// 审核状态
	private String organId;		// organ_id
	private String auditType;   //审核类型(收押，借出)
	
	public TMortgageApplay() {
		super();
	}

	public TMortgageApplay(String id){
		super(id);
	}

	@Length(min=1, max=64, message="抵押合同ID长度必须介于 1 和 64 之间")
	public String getMortgageContractId() {
		return mortgageContractId;
	}

	public void setMortgageContractId(String mortgageContractId) {
		this.mortgageContractId = mortgageContractId;
	}
	
	@Length(min=1, max=255, message="申请类型(抵押，质押)长度必须介于 1 和 255 之间")
	public String getApplayType() {
		return applayType;
	}

	public void setApplayType(String applayType) {
		this.applayType = applayType;
	}
	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Length(min=0, max=255, message="审核状态长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="organ_id长度必须介于 0 和 255 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	
}