/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.advisory.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 业务受理咨询Entity
 * @author cdy
 * @version 2016-03-15
 */
public class TAdvisory extends DataEntity<TAdvisory> {
	
	private static final long serialVersionUID = 1L;
	private Long customerType;		// 客户类型（企业、个人）
	private String customerName;		// 客户名称
	private String customerTel;		// 客户电话
	private String cardType;		// 证件类型
	private String cardNo;		// 证件号
	private String advisoryFees;		// 咨询费
	private Date advisoryTime;		// 咨询时间
	private String content;		// 咨询内容
	private String suggestion;		// 咨询后意愿
	
	private String loanPurpose;//贷款用途
	private String loanPeriod;//贷款期限
	private String loanAmount;//贷款金额
	private String repaySourse;//还款来源

	
	private TProduct product;	
	
	public TAdvisory() {
		super();
	}

	public TAdvisory(String id){
		super(id);
	}

	public Long getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Long customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=200, message="客户名称长度必须介于 0 和 200 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=50, message="客户电话长度必须介于 0 和 50 之间")
	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	
	@Length(min=0, max=50, message="证件类型长度必须介于 0 和 50 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Length(min=0, max=50, message="证件号长度必须介于 0 和 50 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getAdvisoryFees() {
		return advisoryFees;
	}

	public void setAdvisoryFees(String advisoryFees) {
		this.advisoryFees = advisoryFees;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAdvisoryTime() {
		return advisoryTime;
	}

	public void setAdvisoryTime(Date advisoryTime) {
		this.advisoryTime = advisoryTime;
	}
	
	@Length(min=0, max=200, message="咨询内容长度必须介于 0 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=200, message="咨询后意愿长度必须介于 0 和 200 之间")
	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRepaySourse() {
		return repaySourse;
	}

	public void setRepaySourse(String repaySourse) {
		this.repaySourse = repaySourse;
	}
	
}