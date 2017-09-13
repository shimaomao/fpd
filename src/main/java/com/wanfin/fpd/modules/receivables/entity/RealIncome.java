/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 违约金，咨询费Entity
 * @author srf
 * @version 2016-04-06
 */
public class RealIncome extends DataEntity<RealIncome> {
	
	private static final long serialVersionUID = 1L;
	private Date payRealDate;		// 收费时间
	
	private Double guaranteeFee;         //担保费
	private Double reviewFee;           //担保业务评审费
	private Double consultancyAmount;		// 咨询服务费
	private Double punishAmount;		//违约金
	private Double interestPenalties;//罚息
	private String loanContractId;		// 合同主键id
	private String organId;		// organ_id
	
	public RealIncome() {
		super();
	}

	public RealIncome(String id){
		super(id);
	}

	@NotNull(message="consultancy_amount不能为空")
	public Double getConsultancyAmount() {
		return consultancyAmount;
	}

	public void setConsultancyAmount(Double consultancyAmount) {
		this.consultancyAmount = consultancyAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayRealDate() {
		return payRealDate;
	}

	public void setPayRealDate(Date payRealDate) {
		this.payRealDate = payRealDate;
	}
	
	@NotNull(message="&Upsilon;Լ不能为空")
	public Double getPunishAmount() {
		return punishAmount;
	}

	public void setPunishAmount(Double punishAmount) {
		this.punishAmount = punishAmount;
	}
	
	@Length(min=1, max=64, message="loan_contract_id长度必须介于 1 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=64, message="organ_id长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Double getInterestPenalties() {
		return interestPenalties;
	}

	public void setInterestPenalties(Double interestPenalties) {
		this.interestPenalties = interestPenalties;
	}

	public Double getGuaranteeFee() {
		return guaranteeFee;
	}

	public void setGuaranteeFee(Double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}

	public Double getReviewFee() {
		return reviewFee;
	}

	public void setReviewFee(Double reviewFee) {
		this.reviewFee = reviewFee;
	}


	
}