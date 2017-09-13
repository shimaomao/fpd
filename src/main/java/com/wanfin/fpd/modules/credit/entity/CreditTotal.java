/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 征信次数统计Entity
 * @author cjp
 * @version 2017-05-09
 */
public class CreditTotal extends DataEntity<CreditTotal> {
	
	private static final long serialVersionUID = 1L;
	private String organId;		// organ_id
	private Double totalNum;		// 查询征信次数次数
	private Double ownNum;		// 欠费条数
	private Double payNum;		// 已结账条数
	private String type;		// 征信类型
	private String code;		// 征信类型编号
	
	public CreditTotal() {
		super();
	}

	public CreditTotal(String id){
		super(id);
	}

	@Length(min=0, max=255, message="organ_id长度必须介于 0 和 255 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
	
	public Double getOwnNum() {
		return ownNum;
	}

	public void setOwnNum(Double ownNum) {
		this.ownNum = ownNum;
	}
	
	public Double getPayNum() {
		return payNum;
	}

	public void setPayNum(Double payNum) {
		this.payNum = payNum;
	}
	
	@Length(min=0, max=255, message="征信类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="征信类型编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}