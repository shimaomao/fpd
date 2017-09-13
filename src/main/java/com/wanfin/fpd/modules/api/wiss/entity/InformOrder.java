/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 通知获取订单信息Entity
 * @author srf
 * @version 2017-06-30
 */
public class InformOrder extends DataEntity<InformOrder> {
	
	private static final long serialVersionUID = 1L;
	private String userId;	//卖家在易联的编号
	private String merchantId;//商户店铺ID
	private Date startDate;		// 开始时间
	private Date endDate;		// 截至时间
	private Date backStartDate;		// 开始时间
	private Date backEndDate;		// 截至时间
	private String reqOrdersNo;		// 获取历史信息请求号
	private String dealStatus;		// 处理状态信息，0新建；1通知易联；2获取到；3已处理；4获取失败；5没有获取到相关内容
	
	public InformOrder() {
		super();
	}

	public InformOrder(String id){
		super(id);
	}

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

	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartDate(String startDate) {
		if(StringUtils.isNotBlank(startDate)){
			this.startDate = DateUtils.stringToDate(startDate, "yyyyMMdd");
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="截至时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndDate(String endDate) {
		if(StringUtils.isNotBlank(endDate)){
			this.endDate = DateUtils.stringToDate(endDate, "yyyyMMdd");
		}
	}
	
	@Length(min=0, max=64, message="获取历史信息请求号长度必须介于 0 和 64 之间")
	public String getReqOrdersNo() {
		return reqOrdersNo;
	}

	public void setReqOrdersNo(String reqOrdersNo) {
		this.reqOrdersNo = reqOrdersNo;
	}
	
	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Date getBackStartDate() {
		return backStartDate;
	}

	public void setBackStartDate(Date backStartDate) {
		this.backStartDate = backStartDate;
	}

	public Date getBackEndDate() {
		return backEndDate;
	}

	public void setBackEndDate(Date backEndDate) {
		this.backEndDate = backEndDate;
	}

	/**
	 * 参数简单校验
	 * @return
	 */
	public boolean verifyDatas(){
		if(StringUtils.isBlank(userId)){
			return false;
		}else if(StringUtils.isBlank(reqOrdersNo)){
			return false;
		}else if(StringUtils.isBlank(dealStatus)){
			return false;
		}
		
		return true;
	}
}