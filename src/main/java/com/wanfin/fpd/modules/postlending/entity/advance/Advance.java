/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.entity.advance;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * 提前还款Entity
 * @author srf
 * @version 2016-04-18
 */
public class Advance extends ActEntity<Advance> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程ID
	private BigDecimal advanceamount;		// 提前还款金额
	
	private BigDecimal advanceInterest;//提前还款利息
	private BigDecimal advanceDamages;//提前还款违约金
	
	private Date applyDate;		// 申请日期
	private String floatrate;		// 违约金比率
	private String status;		// 合同状态[数字]（0：待审批  1：审批不通过2：审批通过 ）
	private BigDecimal surplusamount;		// /剩余还款金额
	private String loanContractId;		// 外键关联合同id
	private TLoanContract loanContract; //合同信息内容
	
	private BigDecimal totPrincipal;//总本金
	private BigDecimal nowPrincipal;//当前本金
	private BigDecimal nowInterest;//当前利息
	private int surplusPeriod;//还有几期还款
	private Date interestDate;//新计划的计息时间

	public Advance() {
		super();
	}

	public Advance(String id){
		super(id);
	}

	public BigDecimal getTotPrincipal() {
		return totPrincipal;
	}

	public void setTotPrincipal(BigDecimal totPrincipal) {
		this.totPrincipal = totPrincipal;
	}

	@Length(min=0, max=64, message="流程ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public BigDecimal getAdvanceamount() {
		return advanceamount;
	}

	public void setAdvanceamount(BigDecimal advanceamount) {
		this.advanceamount = advanceamount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	public String getFloatrate() {
		return floatrate;
	}

	public void setFloatrate(String floatrate) {
		this.floatrate = floatrate;
	}
	
	@Length(min=0, max=255, message="合同状态[数字]（0：待审批  1：审批不通过2：审批通过 ）长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getSurplusamount() {
		return surplusamount;
	}

	public void setSurplusamount(BigDecimal surplusamount) {
		this.surplusamount = surplusamount;
	}
	
	@Length(min=0, max=64, message="外键关联合同id长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}

	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}

	public BigDecimal getNowPrincipal() {
		return nowPrincipal;
	}

	public void setNowPrincipal(BigDecimal nowPrincipal) {
		this.nowPrincipal = nowPrincipal;
	}

	public BigDecimal getNowInterest() {
		return nowInterest;
	}

	public void setNowInterest(BigDecimal nowInterest) {
		this.nowInterest = nowInterest;
	}

	public int getSurplusPeriod() {
		return surplusPeriod;
	}

	public void setSurplusPeriod(int surplusPeriod) {
		this.surplusPeriod = surplusPeriod;
	}

	public Date getInterestDate() {
		return interestDate;
	}

	public void setInterestDate(Date interestDate) {
		this.interestDate = interestDate;
	}

	public BigDecimal getAdvanceInterest() {
		return advanceInterest;
	}

	public BigDecimal getAdvanceDamages() {
		return advanceDamages;
	}

	public void setAdvanceDamages(BigDecimal advanceDamages) {
		this.advanceDamages = advanceDamages;
	}

	public void setAdvanceInterest(BigDecimal advanceInterest) {
		this.advanceInterest = advanceInterest;
	}

	
	
}