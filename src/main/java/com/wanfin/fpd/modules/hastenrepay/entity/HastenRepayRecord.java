/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.hastenrepay.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 催收记录Entity
 * @author zzm
 * @version 2016-06-08
 */
public class HastenRepayRecord extends DataEntity<HastenRepayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String description;		// 催收说明
	private String borrower;		// 借款人
	private Date hastenDate;		// 催收时间
	private String contact;		// 联系方式
	private String contractId;		// 合同id
	private String organId;		// 租户ID
	
	public HastenRepayRecord() {
		super();
	}

	public HastenRepayRecord(String id){
		super(id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=20, message="借款人长度必须介于 0 和 20 之间")
	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHastenDate() {
		return hastenDate;
	}

	public void setHastenDate(Date hastenDate) {
		this.hastenDate = hastenDate;
	}
	
	@Length(min=0, max=20, message="联系方式长度必须介于 0 和 20 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=64, message="合同id长度必须介于 0 和 64 之间")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}