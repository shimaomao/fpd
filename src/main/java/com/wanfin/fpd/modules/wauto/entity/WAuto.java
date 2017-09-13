/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wauto.entity;

import org.hibernate.validator.constraints.Length;
import org.json.JSONObject;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.contract.entity.WOrder;

/**
 * 自动业务Entity
 * @author chenh
 * @version 2016-07-28
 */
public class WAuto extends DataEntity<WAuto> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String orgName;		// 产品机构
	private String name;		// 产品名
	private Integer isAgree;		// 是否同意放款
	private String datas;		// 数据包
	private String organId;		// 租户ID
	
	public WAuto() {
		super();
	}
	
	public WAuto(WOrder entity) {
		if(entity != null){
			JSONObject jo = new JSONObject(entity);
			this.orderNo = entity.getOrderSn();
			this.orgName = entity.getOrgan().getName();
			this.name = entity.getProductname();
			this.organId = entity.getOrganId();
			this.datas = jo.toString();
		}
	}

	public WAuto(String id){
		super(id);
	}

	@Length(min=1, max=200, message="订单号长度必须介于 1 和 200 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=1, max=200, message="产品机构长度必须介于 1 和 200 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=1, max=200, message="表单名长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	
	@Length(min=1, max=200, message="数据包长度必须介于 1 和 200 之间")
	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}
	
	@Length(min=0, max=100, message="租户ID长度必须介于 0 和 100 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}