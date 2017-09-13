/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 展期真实还款记录Entity
 * @author zzm
 * @version 2016-04-01
 */
public class ExtendReal extends DataEntity<ExtendReal> {
	
	private static final long serialVersionUID = 1L;
	private String extendNum;		// 第几次展期
	private String isOver;		// 是否结清
	private String isOverdue;		// 是否逾期
	private String amount;		// 金额
	private Date repayDate;		// 缴费日期
	private String extendContractId;		// 关联展期表主键id
	
	public ExtendReal() {
		super();
	}

	public ExtendReal(String id){
		super(id);
	}

	@Length(min=0, max=255, message="第几次展期长度必须介于 0 和 255 之间")
	public String getExtendNum() {
		return extendNum;
	}

	public void setExtendNum(String extendNum) {
		this.extendNum = extendNum;
	}
	
	@Length(min=0, max=1, message="是否结清长度必须介于 0 和 1 之间")
	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	
	@Length(min=0, max=1, message="是否逾期长度必须介于 0 和 1 之间")
	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	@Length(min=0, max=64, message="关联展期表主键id长度必须介于 0 和 64 之间")
	public String getExtendContractId() {
		return extendContractId;
	}

	public void setExtendContractId(String extendContractId) {
		this.extendContractId = extendContractId;
	}
	
}