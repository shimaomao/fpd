/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerintent.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * customerintentEntity
 * @author lx
 * @version 2016-08-13
 */
public class TCustomerIntent extends DataEntity<TCustomerIntent> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户名称
	private String tel;		// 联系电话
	private String productId;		// 产品类型
	private String repaymentMode;		// 还款方式
	private String loanLimit;		// 贷款额度
	private String repaymentPeriod;		// 还款期数
	private String annualRate;		// 年利率
	private String overdueAnnualRate;		// 逾期年利率
	private String application;		// 贷款用途
	private String guaranteeMode;		// 担保方式
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String companyId;		// 企业客户
	private String employeeId;		// 个人客户
	
	public TCustomerIntent() {
		super();
	}

	public TCustomerIntent(String id){
		super(id);
	}

	@Length(min=0, max=100, message="客户名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=50, message="产品类型长度必须介于 0 和 50 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=50, message="还款方式长度必须介于 0 和 50 之间")
	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	
	@Length(min=0, max=50, message="贷款额度长度必须介于 0 和 50 之间")
	public String getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(String loanLimit) {
		this.loanLimit = loanLimit;
	}
	
	@Length(min=0, max=10, message="还款期数长度必须介于 0 和 10 之间")
	public String getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(String repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}
	
	@Length(min=0, max=10, message="年利率长度必须介于 0 和 10 之间")
	public String getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	
	@Length(min=0, max=10, message="逾期年利率长度必须介于 0 和 10 之间")
	public String getOverdueAnnualRate() {
		return overdueAnnualRate;
	}

	public void setOverdueAnnualRate(String overdueAnnualRate) {
		this.overdueAnnualRate = overdueAnnualRate;
	}
	
	@Length(min=0, max=50, message="贷款用途长度必须介于 0 和 50 之间")
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	
	@Length(min=0, max=50, message="担保方式长度必须介于 0 和 50 之间")
	public String getGuaranteeMode() {
		return guaranteeMode;
	}

	public void setGuaranteeMode(String guaranteeMode) {
		this.guaranteeMode = guaranteeMode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=50, message="企业客户长度必须介于 0 和 50 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=50, message="个人客户长度必须介于 0 和 50 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}