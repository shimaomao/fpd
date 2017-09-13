/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 原始订单数据Entity
 * @author cjp
 * @version 2017-06-27
 */
public class WishOrder extends DataEntity<WishOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单号
	private String merchantId;  //商户id
	
	
	private String paymentAmount;		// 订单金额（美元）
	private Date orderDate;		// 订单日期
	private String currentExpectedPaymentEligibilityDate; // 
	private String loanPeriod; //放款周期
	private String isRefunded;		// 是否存在退货情况.0为否，1为是
	private String refundDate;		// 退货日期
	private String refundAmount; //退款金额
	private String isChargeback; //
	private String hasBeenDisbursed;		// 订单是否被支付。0否，1是
	private String isStoreCurrentlyTrusted;		// 是否为可信任店铺，0否，1是
	private String merchantDisplayName; //商户名
	private String merchantStoreLink; //商户店铺链接
	
	private String dateDiff;		// 距今时间差
	private String refundTimeDiff;		// 退货情况距下单日时间差
	private String orderMonth;		// 订单月份
	
	private String loanOperation; //0为默认操作，1为被锁定，2为确认已借款，3为已还款，4为逾期
	private Date beginDate; //开始时间 查询用
	private Date endDate; //结束时间 查询用
	private String amount; //订单金额（人民币）
	
	private String loanContractId;//借款业务id
	
	private String userId;

	public WishOrder() {
		super();
	}

	public WishOrder(String id){
		super(id);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	
	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getIsRefunded() {
		return isRefunded;
	}

	public void setIsRefunded(String isRefunded) {
		this.isRefunded = isRefunded;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getIsChargeback() {
		return isChargeback;
	}

	public void setIsChargeback(String isChargeback) {
		this.isChargeback = isChargeback;
	}

	public String getHasBeenDisbursed() {
		return hasBeenDisbursed;
	}

	public void setHasBeenDisbursed(String hasBeenDisbursed) {
		this.hasBeenDisbursed = hasBeenDisbursed;
	}

	public String getIsStoreCurrentlyTrusted() {
		return isStoreCurrentlyTrusted;
	}

	public void setIsStoreCurrentlyTrusted(String isStoreCurrentlyTrusted) {
		this.isStoreCurrentlyTrusted = isStoreCurrentlyTrusted;
	}

	public String getMerchantDisplayName() {
		return merchantDisplayName;
	}

	public void setMerchantDisplayName(String merchantDisplayName) {
		this.merchantDisplayName = merchantDisplayName;
	}

	public String getMerchantStoreLink() {
		return merchantStoreLink;
	}

	public void setMerchantStoreLink(String merchantStoreLink) {
		this.merchantStoreLink = merchantStoreLink;
	}

	public String getDateDiff() {
		return dateDiff;
	}

	public void setDateDiff(String dateDiff) {
		this.dateDiff = dateDiff;
	}

	public String getRefundTimeDiff() {
		return refundTimeDiff;
	}

	public void setRefundTimeDiff(String refundTimeDiff) {
		this.refundTimeDiff = refundTimeDiff;
	}

	public String getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}

	public String getLoanOperation() {
		return loanOperation;
	}

	public void setLoanOperation(String loanOperation) {
		this.loanOperation = loanOperation;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentExpectedPaymentEligibilityDate() {
		return currentExpectedPaymentEligibilityDate;
	}

	public void setCurrentExpectedPaymentEligibilityDate(
			String currentExpectedPaymentEligibilityDate) {
		this.currentExpectedPaymentEligibilityDate = currentExpectedPaymentEligibilityDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	

	
}