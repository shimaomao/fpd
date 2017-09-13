/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 利润列表Entity
 * @author lx
 * @version 2016-05-09
 */
public class TProfit extends DataEntity<TProfit> {
	
	private static final long serialVersionUID = 1L;
	private String profitmoney;		// 利润金额
	private Date profittime;		// 利润时间
	private String profittype;		// 状态
	private String profitway;		// 利润途径
	private String organId;		// 租户ID
	
	public TProfit() {
		super();
	}

	public TProfit(String id){
		super(id);
	}

	public String getProfitmoney() {
		return profitmoney;
	}

	public void setProfitmoney(String profitmoney) {
		this.profitmoney = profitmoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProfittime() {
		return profittime;
	}

	public void setProfittime(Date profittime) {
		this.profittime = profittime;
	}
	
	@Length(min=0, max=255, message="状态长度必须介于 0 和 255 之间")
	public String getProfittype() {
		return profittype;
	}

	public void setProfittype(String profittype) {
		this.profittype = profittype;
	}
	
	@Length(min=0, max=255, message="利润途径长度必须介于 0 和 255 之间")
	public String getProfitway() {
		return profitway;
	}

	public void setProfitway(String profitway) {
		this.profitway = profitway;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}