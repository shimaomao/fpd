/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.entity.baddebts;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * 坏账处理Entity
 * @author srf
 * @version 2016-04-15
 */
public class BadDebtRegist extends ActEntity<BadDebtRegist> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程ID
	private String loanContractId;		// 贷款合同ID
	private Date registerDate;		// 申报日期
	private String registName;		// 填报人名称
	private String department;		// 申报部门
	private String lossMoney;		// 目前欠款本金额
	private String gainedMoney;		// 已收回本金额
	private String lossInterest;		// 目前欠利息额
	private String gainedInterest;		// 已收回利息额
	private String currentInfo;		// 债务人现况
	private String reason;		// 逾期原因
	private Date exceedTime;		// 逾期时间
	private String info;		// 诉讼及委托情况
	private String debtCollecter;		// 催收责任人
	private String phone;		// 催收责任人联系电话
	private Date lastRepay;		// 最后一次还款时间
	private String approvalStatis;//审批状态
	private TLoanContract loanContract;//合同信息
	
	public BadDebtRegist() {
		super();
	}

	public BadDebtRegist(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="贷款合同ID长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Length(min=0, max=255, message="填报人名称长度必须介于 0 和 255 之间")
	public String getRegistName() {
		return registName;
	}

	public void setRegistName(String registName) {
		this.registName = registName;
	}
	
	@Length(min=0, max=255, message="申报部门长度必须介于 0 和 255 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=255, message="目前欠款本金额长度必须介于 0 和 255 之间")
	public String getLossMoney() {
		return lossMoney;
	}

	public void setLossMoney(String lossMoney) {
		this.lossMoney = lossMoney;
	}
	
	@Length(min=0, max=255, message="已收回本金额长度必须介于 0 和 255 之间")
	public String getGainedMoney() {
		return gainedMoney;
	}

	public void setGainedMoney(String gainedMoney) {
		this.gainedMoney = gainedMoney;
	}
	
	@Length(min=0, max=255, message="目前欠利息额长度必须介于 0 和 255 之间")
	public String getLossInterest() {
		return lossInterest;
	}

	public void setLossInterest(String lossInterest) {
		this.lossInterest = lossInterest;
	}
	
	@Length(min=0, max=255, message="已收回利息额长度必须介于 0 和 255 之间")
	public String getGainedInterest() {
		return gainedInterest;
	}

	public void setGainedInterest(String gainedInterest) {
		this.gainedInterest = gainedInterest;
	}
	
	@Length(min=0, max=1000, message="债务人现况长度必须介于 0 和 1000 之间")
	public String getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(String currentInfo) {
		this.currentInfo = currentInfo;
	}
	
	@Length(min=0, max=1000, message="逾期原因长度必须介于 0 和 1000 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExceedTime() {
		return exceedTime;
	}

	public void setExceedTime(Date exceedTime) {
		this.exceedTime = exceedTime;
	}
	
	@Length(min=0, max=1000, message="诉讼及委托情况长度必须介于 0 和 1000 之间")
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@Length(min=0, max=255, message="催收责任人长度必须介于 0 和 255 之间")
	public String getDebtCollecter() {
		return debtCollecter;
	}

	public void setDebtCollecter(String debtCollecter) {
		this.debtCollecter = debtCollecter;
	}
	
	@Length(min=0, max=255, message="催收责任人联系电话长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastRepay() {
		return lastRepay;
	}

	public void setLastRepay(Date lastRepay) {
		this.lastRepay = lastRepay;
	}

	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}

	public String getApprovalStatis() {
		return approvalStatis;
	}

	public void setApprovalStatis(String approvalStatis) {
		this.approvalStatis = approvalStatis;
	}
	
}