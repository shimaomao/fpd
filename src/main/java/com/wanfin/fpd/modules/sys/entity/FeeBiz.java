/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 开通业务Entity
 * @author zzm
 * @version 2016-06-06
 */
public class FeeBiz implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String category;	//业务类型
	private String bizName;		// 付费业务名称
	private String bizCode;		// 付费业务编码
	private String remarks;		// 说明
	private BigDecimal unitPrice;	//单价
	private String status;		// 状态
	
	private String isSelect;
	private String organId;
	public FeeBiz() {
	}

	public FeeBiz(String id){
		setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}