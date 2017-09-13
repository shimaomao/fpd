package com.wanfin.fpd.modules.wish.order.entity;

public class WishOrderVo {
	
	
	private String userId; //用户id
	private String merchantId; //商户id
	private String avgAmount; //平均金额
	private String avgRefundAmount; //平均退款金额
	private String avgRefundRate; //平均退款频率
	private String startTime; //开始时间
	private String endTime; //结束时间
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAvgAmount() {
		return avgAmount;
	}
	public void setAvgAmount(String avgAmount) {
		this.avgAmount = avgAmount;
	}
	public String getAvgRefundAmount() {
		return avgRefundAmount;
	}
	public void setAvgRefundAmount(String avgRefundAmount) {
		this.avgRefundAmount = avgRefundAmount;
	}
	public String getAvgRefundRate() {
		return avgRefundRate;
	}
	public void setAvgRefundRate(String avgRefundRate) {
		this.avgRefundRate = avgRefundRate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
}
