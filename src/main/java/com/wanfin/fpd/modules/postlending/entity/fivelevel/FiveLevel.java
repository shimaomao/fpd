/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.entity.fivelevel;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * 五级分类Entity
 * @author srf
 * @version 2016-04-14
 */
public class FiveLevel extends ActEntity<FiveLevel> {
	
	private static final long serialVersionUID = 1L;
	private String fiveLevel;		// //五级分类[ '1_正常', '2_关注', '3_次级', '4_可疑', '5_损失']
	private Integer status;		// /状态 1待审批（当前申请更改五级分类） 2通过（当前合同的五级分类 3未通过（历史记录）
	private String loanContractId;		// loan_contract_id
	private String oldFiveLevel;		// 原本分级
	
	private TLoanContract loanContract;//合同信息
	
	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}

	public FiveLevel() {
		super();
	}

	public FiveLevel(String id){
		super(id);
	}

	@Length(min=0, max=255, message="//五级分类[ '1_正常', '2_关注', '3_次级', '4_可疑', '5_损失']长度必须介于 0 和 255 之间")
	public String getFiveLevel() {
		return fiveLevel;
	}

	public void setFiveLevel(String fiveLevel) {
		this.fiveLevel = fiveLevel;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="loan_contract_id长度必须介于 0 和 11 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=255, message="原本分级长度必须介于 0 和 255 之间")
	public String getOldFiveLevel() {
		return oldFiveLevel;
	}

	public void setOldFiveLevel(String oldFiveLevel) {
		this.oldFiveLevel = oldFiveLevel;
	}
	
}