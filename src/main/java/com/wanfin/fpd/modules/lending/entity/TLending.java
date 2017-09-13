/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lending.entity;

import java.util.Date;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * 放款记录Entity
 * @author chenh
 * @version 2016-03-25
 */
public class TLending extends DataEntity<TLending> {
	
	private static final long serialVersionUID = 1L;
	private String amount;		// 放款金额
	private Date time;		// 放款时间
	private TLoanContract contract;		// 关联合同主键id
	
	private String ifOnline;//0:线下 1:线上(通过W端资金账户)
	
	
	public TLending() {
		super();
	}
	
	/**
	 * @param contract
	 */
	public TLending(TLoanContract contract) {
		super();
		this.contract = contract;
	}


	public TLending(String id){
		super(id);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public TLoanContract getContract() {
		return contract;
	}

	public void setContract(TLoanContract contract) {
		this.contract = contract;
	}

	public String getIfOnline() {
		return ifOnline;
	}

	public void setIfOnline(String ifOnline) {
		this.ifOnline = ifOnline;
	}
	
	
	
}