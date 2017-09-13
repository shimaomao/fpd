/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.looploan.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 申请授信Entity
 * @author zzm
 * @version 2016-03-17
 */
public class TLoopLoan extends ActEntity<TLoopLoan> {
	
	private static final long serialVersionUID = 1L;
	private String cardNum;		// 证件号码
	private Integer cardType;		// 证件类型
	private Double creditPrice;		// 授信金额
	private Double remainPrice;		// 剩余可贷款金额
	private String loopNumber;		// 授信编号
	private Integer maxNumber;		// 合同编号最大数值,用于产生合同编号
	private Integer period;		// 授信期限
	private String periodType;		// 授信周期
	private Date applyDate;		// 申请日期
	private String customerId;		// 客户id
	private String customerName;		// 客户名称
	private Integer customerType;		// 客户类型
	private Double floatRate;		// 授信利率
	private String loanRateType;		// 利率的类型
	private String loanType;		// 授信方式
	private String payType;		// 还款方式
	private String status;		// 授信合同状态（0：待申请  1：授信合同待审批  2：授信成功（可以放款）  3：授信合同退回  4：授信结束）
	private String industry;		// 投向行业
	private String area;		// 借款区域
	private String purpose;		// 贷款用途
	private TProduct product;
	public TLoopLoan() {
		super();
	}

	public TLoopLoan(String id){
		super(id);
	}

	@Length(min=0, max=25, message="证件号码长度必须介于 0 和 25 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	
	public Double getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(Double creditPrice) {
		this.creditPrice = creditPrice;
	}
	
	public Double getRemainPrice() {
		return remainPrice;
	}

	public void setRemainPrice(Double remainPrice) {
		this.remainPrice = remainPrice;
	}
	
	@Length(min=0, max=50, message="授信编号长度必须介于 0 和 50 之间")
	public String getLoopNumber() {
		return loopNumber;
	}

	public void setLoopNumber(String loopNumber) {
		this.loopNumber = loopNumber;
	}
	
	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	@Length(min=0, max=255, message="授信周期长度必须介于 0 和 255 之间")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=255, message="客户名称长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	
	public Double getFloatRate() {
		return floatRate;
	}

	public void setFloatRate(Double floatRate) {
		this.floatRate = floatRate;
	}
	
	@Length(min=0, max=255, message="利率的类型长度必须介于 0 和 255 之间")
	public String getLoanRateType() {
		return loanRateType;
	}

	public void setLoanRateType(String loanRateType) {
		this.loanRateType = loanRateType;
	}
	
	
	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	@Length(min=0, max=255, message="授信方式长度必须介于 0 和 255 之间")
	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	@Length(min=0, max=255, message="还款方式长度必须介于 0 和 255 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=255, message="授信合同状态（1：授信合同待审批  2:授信合同待签订  3：授信成功（可以放款）  4：授信合同退回  5：授信结束）长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="投向行业长度必须介于 0 和 64 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=255, message="借款区域长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="贷款用途长度必须介于 0 和 255 之间")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public List<String> getLoanTypeList() {
		List<String> list = Lists.newArrayList();
		if (loanType != null){
			for (String s : StringUtils.split(loanType, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	
	public void setLoanTypeList(List<String> list) {
		loanType= ","+StringUtils.join(list, ",")+",";
	}
	
}