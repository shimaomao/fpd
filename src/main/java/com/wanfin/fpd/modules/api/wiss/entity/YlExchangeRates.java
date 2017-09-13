/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.entity;

import java.util.Date;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.DateUtils;

/**
 * 汇率-易联Entity
 * @author srf
 * @version 2017-07-05
 */
public class YlExchangeRates extends DataEntity<YlExchangeRates> {
	
	private static final long serialVersionUID = 1L;
	private String origCurrency;		// 原币种
	private String exchCurrency;		// 兑换币种
	private String rate;		// 汇率
	private String date;		// 所属日期 解析用
	private Date validDate;// 所属日期
	
	public YlExchangeRates() {
		super();
	}

	public YlExchangeRates(String id){
		super(id);
	}

	public String getOrigCurrency() {
		return origCurrency;
	}

	public void setOrigCurrency(String origCurrency) {
		this.origCurrency = origCurrency;
	}
	
	public String getExchCurrency() {
		return exchCurrency;
	}

	public void setExchCurrency(String exchCurrency) {
		this.exchCurrency = exchCurrency;
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
		if(this.validDate == null){
			this.validDate = DateUtils.stringToDate(date, "yyyyMMdd");
		}
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
}