/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 商户店铺信息Entity
 * @author cjp
 * @version 2017-07-11
 */
public class WishShop extends DataEntity<WishShop> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户id
	private String isStoreCurrentlyTrusted;		// 店铺是否可信 0否  1是
	private String admittanceDuration;		// 店铺经营总时长
	private String continuousOperation;		// 商户最近一次在平台经营时长(月)
	private String userId; //用户id
	
	public WishShop() {
		super();
	}

	public WishShop(String id){
		super(id);
	}

	@Length(min=0, max=255, message="商户id长度必须介于 0 和 255 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=255, message="店铺是否可信 0否  1是长度必须介于 0 和 255 之间")
	public String getIsStoreCurrentlyTrusted() {
		return isStoreCurrentlyTrusted;
	}

	public void setIsStoreCurrentlyTrusted(String isStoreCurrentlyTrusted) {
		this.isStoreCurrentlyTrusted = isStoreCurrentlyTrusted;
	}
	
	@Length(min=0, max=255, message="店铺经营总时长长度必须介于 0 和 255 之间")
	public String getAdmittanceDuration() {
		return admittanceDuration;
	}

	public void setAdmittanceDuration(String admittanceDuration) {
		this.admittanceDuration = admittanceDuration;
	}
	
	@Length(min=0, max=255, message="商户最近一次在平台经营时长(月)长度必须介于 0 和 255 之间")
	public String getContinuousOperation() {
		return continuousOperation;
	}

	public void setContinuousOperation(String continuousOperation) {
		this.continuousOperation = continuousOperation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}