/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 风险控制评分Entity
 * @author zzm
 * @version 2016-06-17
 */
public class RiskScore extends DataEntity<RiskScore> {
	
	private static final long serialVersionUID = 1L;
	private String loanContractId;		// 业务id
	private String category;	// 分类
	private Double grade;		// 评分
	private String rpn;		// 风险系数
	public String getLoanContractId() {
		return loanContractId;
	}
	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	public String getRpn() {
		return rpn;
	}
	public void setRpn(String rpn) {
		this.rpn = rpn;
	}

	
}