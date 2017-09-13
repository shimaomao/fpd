/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * pro_productEntity
 * @author srf
 * @version 2016-12-16
 */
public class ProProduct extends DataEntity<ProProduct> {
	
	private static final long serialVersionUID = 1L;
	private String anuthenuserId;		// 认证用户ID(卖方)
	private String type;		// 产品类型:1：贷款；2：理财
	private String name;		// 产品名称
	private String status;		// 发布状态: new 新建、published 已发布
	private String pubType;		// 发布类型:1 、永久;2、指定日期
	private Date pubEndDate;		// 发布截止日期
	
	public ProProduct() {
		super();
	}

	public ProProduct(String id){
		super(id);
	}

	@Length(min=1, max=64, message="认证用户ID(卖方)长度必须介于 1 和 64 之间")
	public String getAnuthenuserId() {
		return anuthenuserId;
	}

	public void setAnuthenuserId(String anuthenuserId) {
		this.anuthenuserId = anuthenuserId;
	}
	
	@Length(min=1, max=11, message="产品类型长度必须介于 1 和 11 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=255, message="产品名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=20, message="发布状态: new 新建、published 已发布长度必须介于 1 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=20, message="发布类型:1 、永久;2、指定日期长度必须介于 0 和 20 之间")
	public String getPubType() {
		return pubType;
	}

	public void setPubType(String pubType) {
		this.pubType = pubType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPubEndDate() {
		return pubEndDate;
	}

	public void setPubEndDate(Date pubEndDate) {
		this.pubEndDate = pubEndDate;
	}
	
}