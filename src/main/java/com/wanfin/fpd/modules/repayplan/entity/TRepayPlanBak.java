/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.repayplan.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContractBak;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 还款计划Entity
 * @author lx
 * @version 2016-03-22
 */
@ApiModel(value="还款计划-备份")
public class TRepayPlanBak extends ApiEntity<TRepayPlanBak> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="批号", dataType="String", notes="批号", hidden=false, required=true)
	private String lotNum;		// 批号
	@ApiModelProperty(value="催收次数", dataType="String", notes="催收次数", hidden=false, required=true)
	private Integer csNum;		// 催收次数
	@ApiModelProperty(value="利息", dataType="String", notes="利息", hidden=false, required=true)
	private String interest;		// 利息
	@ApiModelProperty(value="真实还款利息", dataType="String", notes="真实还款利息", hidden=false, required=true)
	private String interestReal;		// 真实还款利息
	@ApiModelProperty(value="是否逾期", dataType="String", notes="是否逾期", hidden=false, required=true)
	private String isYuQi;		// 是否逾期
	@ApiModelProperty(value="第几笔", dataType="String", notes="第几笔", hidden=false, required=true)
	private Integer num;		// 第几笔
	@ApiModelProperty(value="结清日期", dataType="String", notes="结清日期", hidden=false, required=true)
	private String overDate;		// 结清日期
	@ApiModelProperty(value="付息时间", dataType="String", notes="付息时间", hidden=false, required=true)
	private Date payInterestDate;		// 付息时间
	@ApiModelProperty(value="付息状态", dataType="Date", notes="付息状态", hidden=false, required=true)
	private String payInterestStatus;		// 付息状态
	@ApiModelProperty(value="本金", dataType="String", notes="本金", hidden=false, required=true)
	private String principal;		// 本金
	@ApiModelProperty(value="真实本金", dataType="String", notes="真实本金", hidden=false, required=true)
	private String principalReal;		// 真实本金
	@ApiModelProperty(value="状态", dataType="String", notes="状态", hidden=false, required=true)
	private String status;		// 状态
	@ApiModelProperty(value="逾期天数", dataType="String", notes="逾期天数", hidden=false, required=true)
	private String yuQi;		// 逾期天数
	
	@ApiModelProperty(required=true, notes="合同主键", dataType="String", position=1)
	private String loanContractId;		// 合同主键
	private String startDate;		// 还款开始时间
	private String accountDate;		// 到账时间
	private String endDate;		// 还款截止日期
	private String isValid;		//是否有效
	
	private TLoanContractBak loanContractBak;
	private Date beginAccountDate;		// 到账时间范围（开始）
	private Date endAccountDate;		// 到账时间范围（结束）
	
	
	
	//还款提醒时使用，不持久化到数据库
	private String productid;
	
	public TRepayPlanBak() {
		super();
	}

	public TRepayPlanBak(String id){
		super(id);
	}
	
	@Override
	public void preInsert() {
		super.preInsert();
		this.isValid = "1";
	}

	@Length(min=0, max=11, message="催收次数长度必须介于 0 和 11 之间")
	public Integer getCsNum() {
		return csNum;
	}

	public void setCsNum(Integer csNum) {
		this.csNum = csNum;
	}
	
	@Length(min=0, max=255, message="利息长度必须介于 0 和 255 之间")
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	public String getInterestReal() {
		return interestReal;
	}

	public void setInterestReal(String interestReal) {
		this.interestReal = interestReal;
	}
	
	@Length(min=0, max=11, message="是否逾期长度必须介于 0 和 11 之间")
	public String getIsYuQi() {
		return isYuQi;
	}

	public void setIsYuQi(String isYuQi) {
		this.isYuQi = isYuQi;
	}
	
	@Length(min=0, max=11, message="第几笔长度必须介于 0 和 11 之间")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="结清日期长度必须介于 0 和 255 之间")
	public String getOverDate() {
		return overDate;
	}

	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayInterestDate() {
		return payInterestDate;
	}

	public void setPayInterestDate(Date payInterestDate) {
		this.payInterestDate = payInterestDate;
	}
	
	@Length(min=0, max=255, message="付息状态长度必须介于 0 和 255 之间")
	public String getPayInterestStatus() {
		return payInterestStatus;
	}

	public void setPayInterestStatus(String payInterestStatus) {
		this.payInterestStatus = payInterestStatus;
	}
	
	@Length(min=0, max=255, message="本金长度必须介于 0 和 255 之间")
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getPrincipalReal() {
		return principalReal;
	}

	public void setPrincipalReal(String principalReal) {
		this.principalReal = principalReal;
	}
	
	@Length(min=0, max=11, message="状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="逾期天数长度必须介于 0 和 11 之间")
	public String getYuQi() {
		return yuQi;
	}

	public void setYuQi(String yuQi) {
		this.yuQi = yuQi;
	}
	
	@Length(min=0, max=11, message="合同主键长度必须介于 0 和 11 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=255, message="还款开始时间长度必须介于 0 和 255 之间")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=255, message="到账时间长度必须介于 0 和 255 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=255, message="还款截止日期长度必须介于 0 和 255 之间")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginAccountDate() {
		return beginAccountDate;
	}

	public void setBeginAccountDate(Date beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	

	public TLoanContractBak getLoanContractBak() {
		return loanContractBak;
	}

	public void setLoanContractBak(TLoanContractBak loanContractBak) {
		this.loanContractBak = loanContractBak;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
}