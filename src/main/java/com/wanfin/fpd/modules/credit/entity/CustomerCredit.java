/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 客户授信额度Entity
 * @author zzm
 * @version 2016-07-13
 */
public class CustomerCredit extends DataEntity<CustomerCredit> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String productId;		//产品主键
	private BigDecimal credit;		// 授信额度
	private Double score;		// 评分
	private BigDecimal balance;		// 剩余可用额度
	private String creditWay;		// 授信方式
	private String relId;		// 关联id
	private Date creditDate;		// 授信时间
	private Date overDate;		// 授信失效时间
	private Date applyDate;		//申请时间 #3545
	
	public CustomerCredit() {
		super();
	}

	public CustomerCredit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@NotNull(message="授信额度不能为空")
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=50, message="授信方式长度必须介于 0 和 50 之间")
	public String getCreditWay() {
		return creditWay;
	}

	public void setCreditWay(String creditWay) {
		this.creditWay = creditWay;
	}
	
	@Length(min=0, max=64, message="关联id长度必须介于 0 和 64 之间")
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="授信时间不能为空")
	public Date getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
}