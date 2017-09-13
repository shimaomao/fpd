/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 历史平均数据Entity
 * @author cjp
 * @version 2017-06-28
 */
public class AvgRecord extends DataEntity<AvgRecord> {
	
	private static final long serialVersionUID = 1L;
	private String organId;		// organ_id
	private String avgSales;		// 历史平均销售额，rmb/month
	private String avgRefundTimeRate;		// 历史平均退货金额比例
	private String avgRefundAmountRate;		// 历史平均退货金额比例
	
	public AvgRecord() {
		super();
	}

	public AvgRecord(String id){
		super(id);
	}

	@Length(min=0, max=255, message="organ_id长度必须介于 0 和 255 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	@Length(min=0, max=255, message="历史平均销售额，rmb/month长度必须介于 0 和 255 之间")
	public String getAvgSales() {
		return avgSales;
	}

	public void setAvgSales(String avgSales) {
		this.avgSales = avgSales;
	}
	
	@Length(min=0, max=255, message="历史平均退货金额比例长度必须介于 0 和 255 之间")
	public String getAvgRefundTimeRate() {
		return avgRefundTimeRate;
	}

	public void setAvgRefundTimeRate(String avgRefundTimeRate) {
		this.avgRefundTimeRate = avgRefundTimeRate;
	}
	
	@Length(min=0, max=255, message="历史平均退货金额比例长度必须介于 0 和 255 之间")
	public String getAvgRefundAmountRate() {
		return avgRefundAmountRate;
	}

	public void setAvgRefundAmountRate(String avgRefundAmountRate) {
		this.avgRefundAmountRate = avgRefundAmountRate;
	}
	
}