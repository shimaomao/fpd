/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 开通业务Entity
 * @author zzm
 * @version 2016-06-06
 */
public class OpenBiz extends DataEntity<OpenBiz> {
	
	private static final long serialVersionUID = 1L;
	private FeeBiz feeBiz;		// 服务业务id
	private int count;			//数量
	private BigDecimal amount;		// 费用
	private String status;		// 状态
	
	
	public OpenBiz() {
		super();
	}

	public OpenBiz(String id){
		super(id);
	}

	public FeeBiz getFeeBiz() {
		return feeBiz;
	}

	public void setFeeBiz(FeeBiz feeBiz) {
		this.feeBiz = feeBiz;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=20, message="状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}