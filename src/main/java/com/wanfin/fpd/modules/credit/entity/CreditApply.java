/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.credit.entity.report.ReportPlanted;

/**
 * 授信申请Entity
 * @author zzm
 * @version 2016-07-13
 */
public class CreditApply extends ActEntity<CreditApply> {
	
	private static final long serialVersionUID = 1L;
	private String applyNum;		// 授信申请单据号
	private String customerId;		// 客户id
	private String customerName;	//客户姓名
	private String customerType;	// 客户类型
	private BigDecimal credit;		// 申请额度
	private Date expirationDate;		// 授信额度有效截止日期
	private Integer validMonth;//有效期（月）
	private String status;		//申请状态
	private String productId;//产品主键
	private String productname;//产品名称
	private String creditType;		// 授信标识
	private String auditType;//审核类型 没用到
	private String approve;//是否开待审会：0不开；1开
	
	//list页面使用，不参与数据库
	private String employeeName;
	private String companyName;
	
	private Integer apprTotal ;//投票总人数
	private Integer apprAgree ;//投票同意的人数
	private Integer apprDisAgree ;//投票不同意的人数
	
	private BigDecimal residueCredit;//剩余额度

	//private ReportCulture reportCulture;//贷款项目调查报告养殖贷
	private ReportPlanted reportPlanted;//贷款项目调查报告种殖贷
	//private ReportEmployee reportEmployee;//贷款项目调查报告职工信用贷
	//private ReportCompany reportCompany;//贷款项目调查报告企业贷
	
	private String tab;//页面显示板块用
	private String ifEdit;//调查报告是否可以编辑,edit:可编辑；其他：查看
	private String subType;//传递参数用  
	
	List<CreditCoborrower> coborrowerList;
	
	private String remarks;	// 评审意见书
	public CreditApply() {
		super();
	}

	public CreditApply(String id){
		super(id);
	}

	@Length(min=0, max=100, message="授信申请单据号长度必须介于 0 和 100 之间")
	public String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}
	
	@Length(min=1, max=64, message="客户id长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=10, message="客户类型长度必须介于 0 和 10 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	//@NotNull(message="申请额度不能为空")
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getResidueCredit() {
		return residueCredit;
	}

	public void setResidueCredit(BigDecimal residueCredit) {
		this.residueCredit = residueCredit;
	}

	public Integer getApprTotal() {
		return apprTotal;
	}

	public void setApprTotal(Integer apprTotal) {
		this.apprTotal = apprTotal;
	}

	public Integer getApprAgree() {
		return apprAgree;
	}

	public void setApprAgree(Integer apprAgree) {
		this.apprAgree = apprAgree;
	}

	public Integer getApprDisAgree() {
		return apprDisAgree;
	}

	public void setApprDisAgree(Integer apprDisAgree) {
		this.apprDisAgree = apprDisAgree;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

//	public ReportCulture getReportCulture() {
//		return reportCulture;
//	}
//
//	public void setReportCulture(ReportCulture reportCulture) {
//		this.reportCulture = reportCulture;
//	}

	public ReportPlanted getReportPlanted() {
		return reportPlanted;
	}

	public void setReportPlanted(ReportPlanted reportPlanted) {
		this.reportPlanted = reportPlanted;
	}

//	public ReportEmployee getReportEmployee() {
//		return reportEmployee;
//	}
//
//	public void setReportEmployee(ReportEmployee reportEmployee) {
//		this.reportEmployee = reportEmployee;
//	}
//
//	public ReportCompany getReportCompany() {
//		return reportCompany;
//	}
//
//	public void setReportCompany(ReportCompany reportCompany) {
//		this.reportCompany = reportCompany;
//	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	
	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public Integer getValidMonth() {
		return validMonth;
	}

	public void setValidMonth(Integer validMonth) {
		this.validMonth = validMonth;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<CreditCoborrower> getCoborrowerList() {
		return coborrowerList;
	}

	public void setCoborrowerList(List<CreditCoborrower> coborrowerList) {
		this.coborrowerList = coborrowerList;
	}

	public String getIfEdit() {
		return ifEdit;
	}

	public void setIfEdit(String ifEdit) {
		this.ifEdit = ifEdit;
	}
}