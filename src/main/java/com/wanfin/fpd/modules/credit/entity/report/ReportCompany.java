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
 * 贷款项目调查报告企业贷Entity
 * @author srf
 * @version 2017-01-23
 */
public class ReportCompany extends DataEntity<ReportCompany> {
	
	private static final long serialVersionUID = 1L;
	private String creditApplyId;		// 客户授信申请表ID
	private String tplCode;		// 模板编码
	private String department;		// 部门
	private String name;		// 借款人
	private String salesman;		// 客户经理
	private Date acceptDate;		// 受理日期
	private String ftlContent;		// 富文本内容
	private String status;		// 状态
//	private String organId;		// 租户ID
	
	public ReportCompany() {
		super();
	}

	public ReportCompany(String id){
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