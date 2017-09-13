/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountdetail.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 账目明细Entity
 * @author lx
 * @version 2016-05-13
 */
public class TAccountDetail extends DataEntity<TAccountDetail> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String income;		// 收入
	private String defray;		// 支出
	private String balance;		// 余额
	private String organId;		// 租户ID
	private String publishTime;	//发布时间

	
	public TAccountDetail() {
		super();
	}

	public TAccountDetail(String id){
		super(id);
	}

	@Length(min=1, max=255, message="内容长度必须介于 1 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}
	
	public String getDefray() {
		return defray;
	}

	public void setDefray(String defray) {
		this.defray = defray;
	}
	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
}