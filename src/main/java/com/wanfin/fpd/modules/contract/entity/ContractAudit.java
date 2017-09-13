/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.ActEntity;

/**
 * 签订合同审核流程Entity
 * @author srf
 * @version 2016-12-27
 */
public class ContractAudit extends ActEntity<ContractAudit> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	//private String loanContractId;		// 合同信息ID
	private TLoanContract loanContract;
	private String status;		// 状态
	private String organId;		// 租户ID
	private String subType;//传递参数用
	
	public ContractAudit() {
		super();
	}

	public ContractAudit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
//	@Length(min=0, max=64, message="合同信息ID长度必须介于 0 和 64 之间")
//	public String getLoanContractId() {
//		return loanContractId;
//	}
//
//	public void setLoanContractId(String loanContractId) {
//		this.loanContractId = loanContractId;
//	}
	
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

	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
}