/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;

/**
 * 展期合同Entity
 * @author zzm
 * @version 2016-04-01
 */
public class ExtendContract extends ActEntity<ExtendContract> {
	
	private static final long serialVersionUID = 1L;
	private Date applyDate;		// 申请日期
	private String contractNumber;		// 展期编号
	private String level;		// 五级分类
	private Double amount;		// 展期金额
	private Date loanDate;		// 通过日期
	private Integer loanPeriod;		// 展期期限
	private Float loanRate;		// 展期利率
	private Integer num;		// 展期次数
	private String payDay;		// 付息日
	private Date payPrincipalDate;		// 还本金日期
	private String payType;		// 还款方式
	private String periodType;		// 展期还款周期
	private String status;		// 合同状态
	private TLoanContract loanContract;		// 关联合同主键id
	private String payOptions;		// 还款选项
	private String payDayType;		// 每期还款日
	private String loanRateType;		// 利率类型
	private Float overdueFineRate;		// 预期罚息利率
	private Float overdueLoanRate;		// 预期贷款利率
	private Float manageRate;		// 管理咨询费费率
	private Float punishAmountRate;		// 违约金比例
	private Float serviceRate;		// 财务服务费率
	
	//用于接收表单数据，并不关联数据库
	private List<ExtendPlan> extendPlanList;
	public ExtendContract() {
		super();
	}

	public ExtendContract(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@Length(min=0, max=255, message="展期编号长度必须介于 0 和 255 之间")
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@Length(min=0, max=50, message="五级分类长度必须介于 0 和 50 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	
	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	public Float getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Float loanRate) {
		this.loanRate = loanRate;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=50, message="付息日长度必须介于 0 和 50 之间")
	public String getPayDay() {
		return payDay;
	}

	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayPrincipalDate() {
		return payPrincipalDate;
	}

	public void setPayPrincipalDate(Date payPrincipalDate) {
		this.payPrincipalDate = payPrincipalDate;
	}
	
	@Length(min=0, max=50, message="还款方式长度必须介于 0 和 50 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=255, message="展期还款周期长度必须介于 0 和 255 之间")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@Length(min=0, max=50, message="合同状态长度必须介于 0 和 50 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}
	
	@Length(min=0, max=50, message="还款选项长度必须介于 0 和 50 之间")
	public String getPayOptions() {
		return payOptions;
	}

	public void setPayOptions(String payOptions) {
		this.payOptions = payOptions;
	}
	
	@Length(min=0, max=50, message="每期还款日长度必须介于 0 和 50 之间")
	public String getPayDayType() {
		return payDayType;
	}

	public void setPayDayType(String payDayType) {
		this.payDayType = payDayType;
	}
	
	@Length(min=0, max=20, message="利率类型长度必须介于 0 和 20 之间")
	public String getLoanRateType() {
		return loanRateType;
	}

	public void setLoanRateType(String loanRateType) {
		this.loanRateType = loanRateType;
	}
	
	public Float getOverdueFineRate() {
		return overdueFineRate;
	}

	public void setOverdueFineRate(Float overdueFineRate) {
		this.overdueFineRate = overdueFineRate;
	}
	
	public Float getOverdueLoanRate() {
		return overdueLoanRate;
	}

	public void setOverdueLoanRate(Float overdueLoanRate) {
		this.overdueLoanRate = overdueLoanRate;
	}
	
	public Float getManageRate() {
		return manageRate;
	}

	public void setManageRate(Float manageRate) {
		this.manageRate = manageRate;
	}
	
	public Float getPunishAmountRate() {
		return punishAmountRate;
	}

	public void setPunishAmountRate(Float punishAmountRate) {
		this.punishAmountRate = punishAmountRate;
	}
	
	public Float getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Float serviceRate) {
		this.serviceRate = serviceRate;
	}
	
	public List<ExtendPlan> getExtendPlanList() {
		return extendPlanList;
	}

	public void setExtendPlanList(List<ExtendPlan> extendPlanList) {
		this.extendPlanList = extendPlanList;
	}

	
	
	
}