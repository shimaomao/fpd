/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity.report;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 贷款项目调查报告养殖贷Entity
 * @author srf
 * @version 2017-01-16
 */
public class ReportCulture extends DataEntity<ReportCulture> {
	
	private static final long serialVersionUID = 1L;
	private String creditApplyId;		// 客户授信申请表ID
	private String tplCode;		// 模板编码
	private String department;		// 部门
	private String name;		// 借款人
	private String salesman;		// 客户经理
	private Date acceptDate;		// 受理日期
	private BigDecimal creditMoney;		// 授信金额
	private Integer creditPeriod;		// 授信期限
	private BigDecimal rate;		// 利率
	private BigDecimal loanMoney;		// 贷款金额
	private Integer loanPeriod;		// 贷款期限
	private BigDecimal consultingFee;		// 咨询费
	private Integer repayType;		// 还款方式
	private String businessSource;		// 业务来源
	private String ftlContent;		// 富文本内容
	private String status;		// 状态
	private String organId;		// 租户ID
	
	public ReportCulture() {
		super();
	}

	public ReportCulture(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户授信申请表ID长度必须介于 1 和 64 之间")
	public String getCreditApplyId() {
		return creditApplyId;
	}

	public void setCreditApplyId(String creditApplyId) {
		this.creditApplyId = creditApplyId;
	}
	
	@Length(min=1, max=50, message="模板编码长度必须介于 1 和 50 之间")
	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}
	
	@Length(min=1, max=64, message="部门长度必须介于 1 和 64 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=1, max=50, message="借款人长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=50, message="客户经理长度必须介于 1 和 50 之间")
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
	
	@NotNull(message="授信金额不能为空")
	public BigDecimal getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney;
	}
	
	@NotNull(message="授信期限不能为空")
	public Integer getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(Integer creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	
	@NotNull(message="利率不能为空")
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@NotNull(message="贷款金额不能为空")
	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	
	@NotNull(message="贷款期限不能为空")
	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	@NotNull(message="咨询费不能为空")
	public BigDecimal getConsultingFee() {
		return consultingFee;
	}

	public void setConsultingFee(BigDecimal consultingFee) {
		this.consultingFee = consultingFee;
	}
	
	@NotNull(message="还款方式不能为空")
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
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}