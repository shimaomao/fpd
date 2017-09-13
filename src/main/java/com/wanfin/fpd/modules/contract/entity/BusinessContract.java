/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.act.entity.Act;

/**
 * 业务合同Entity
 * @author srf
 * @version 2017-02-22
 */
public class BusinessContract extends DataEntity<BusinessContract> {
	
	private static final long serialVersionUID = 1L;
	private String contractName;		// 合同名称
	private String tplId;		// 模板ID
	private String contractType;		// 合同类型，默认1为业务办理合同
	//private String loanContractId;		// 业务ID
	private TLoanContract loanContract; //业务信息
	private String contractAuditId;		// 签订合同审核表ID
	private String contractContent;		// 合同富文本内容
	private String crosswise;//是否横向，1位横向
	private Act act; 		// 流程任务对象 为ContractAudit维持数据临时使用
	
	public BusinessContract() {
		super();
	}

	public BusinessContract(String id){
		super(id);
	}

	@Length(min=0, max=64, message="合同名称长度必须介于 0 和 64 之间")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	@Length(min=0, max=64, message="模板ID长度必须介于 0 和 64 之间")
	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}
	
	@Length(min=0, max=8, message="合同类型，默认1为业务办理合同长度必须介于 0 和 8 之间")
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
//	@Length(min=0, max=64, message="业务ID长度必须介于 0 和 64 之间")
//	public String getLoanContractId() {
//		return loanContractId;
//	}
//
//	public void setLoanContractId(String loanContractId) {
//		this.loanContractId = loanContractId;
//	}
	
	public TLoanContract getLoanContract() {
		return loanContract;
	}
	
	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}
	
	@Length(min=0, max=64, message="签订合同审核流程ID长度必须介于 0 和 64 之间")
	public String getContractAuditId() {
		return contractAuditId;
	}

	public void setContractAuditId(String contractAuditId) {
		this.contractAuditId = contractAuditId;
	}
	
	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public String getCrosswise() {
		return crosswise;
	}

	public void setCrosswise(String crosswise) {
		this.crosswise = crosswise;
	}

	public Act getAct() {
		if (act == null){
			act = new Act();
		}
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}
}