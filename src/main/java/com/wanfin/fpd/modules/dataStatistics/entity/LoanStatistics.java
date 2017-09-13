package com.wanfin.fpd.modules.dataStatistics.entity;

import com.wanfin.fpd.common.persistence.DataEntity;

public class LoanStatistics extends DataEntity<LoanStatistics> {
	private static final long serialVersionUID = 1L;

	private String yearMonth;
	private String status;		// 状态
	private String interest;		// 利息
	private String interestReal;		// 真实还款利息
	private String principal;		// 本金
	private String principalReal;		// 真实本金
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
}
