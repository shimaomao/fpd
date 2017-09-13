/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity.report;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 贷款项目调查报告职工信用贷Entity
 * @author srf
 * @version 2017-01-23
 */
public class ReportEmployee extends DataEntity<ReportEmployee> {
	
	private static final long serialVersionUID = 1L;
	private String creditApplyId;		// 客户授信申请表ID
	private String tplCode;		// 模板编码
	private String department;		// 部门
	private String name;		// 借款人
	private String salesman;		// 客户经理
	private Date acceptDate;		// 受理日期
	private String creditMoney;		// 授信金额
	private String creditPeriod;		// 授信期限
	private String rate;		// 利率
	private String loanMoney;		// 贷款金额
	private String loanPeriod;		// 贷款期限
	private String consultingFee;		// 咨询费
	private Integer repayType;		// 还款方式
	private String businessSource;		// 业务来源
	private String ftlContent;		// 富文本内容
	private String status;		// 状态
	//private String organId;		// 租户ID
	
	public ReportEmployee() {
		super();
	}

	public ReportEmployee(String id){
		super(id);
	}

	@Length(min=0, max=64, message="客户授信申请表ID长度必须介于 0 和 64 之间")
	public String getCreditApplyId() {
		return creditApplyId;
	}

	public void setCreditApplyId(String creditApplyId) {
		this.creditApplyId = creditApplyId;
	}
	
	@Length(min=0, max=50, message="模板编码长度必须介于 0 和 50 之间")
	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}
	
	@Length(min=0, max=64, message="部门长度必须介于 0 和 64 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=50, message="借款人长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="客户经理长度必须介于 0 和 50 之间")
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="受理日期不能为空")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	public String getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(String creditMoney) {
		this.creditMoney = creditMoney;
	}
	
	@Length(min=0, max=10, message="授信期限长度必须介于 0 和 10 之间")
	public String getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	
	@Length(min=0, max=10, message="贷款期限长度必须介于 0 和 10 之间")
	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	public String getConsultingFee() {
		return consultingFee;
	}

	public void setConsultingFee(String consultingFee) {
		this.consultingFee = consultingFee;
	}
	
	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}
	
	@Length(min=0, max=5, message="业务来源长度必须介于 0 和 5 之间")
	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	
	public String getFtlContent() {
		return ftlContent;
	}

	public void setFtlContent(String ftlContent) {
		this.ftlContent = ftlContent;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}