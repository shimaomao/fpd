/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 资本信息Entity
 * @author lx
 * @version 2016-05-09
 */
public class TCapital extends DataEntity<TCapital> {
	
	private static final long serialVersionUID = 1L;
	private String des;		// 描述
	private String limitamount;		// 贷款上限金额
	private String loanamount;		// 放贷注入资本
	private String profitamount;		// 未分配利润
	private String rongziamount;		// 外部融资
	private String zhuceamount;		// 注册资本
	private String organId;		// 租户ID
	
	public TCapital() {
		super();
	}

	public TCapital(String id){
		super(id);
	}

	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	public String getLimitamount() {
		return limitamount;
	}

	public void setLimitamount(String limitamount) {
		this.limitamount = limitamount;
	}
	
	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}
	
	public String getProfitamount() {
		return profitamount;
	}

	public void setProfitamount(String profitamount) {
		this.profitamount = profitamount;
	}
	
	public String getRongziamount() {
		return rongziamount;
	}

	public void setRongziamount(String rongziamount) {
		this.rongziamount = rongziamount;
	}
	
	public String getZhuceamount() {
		return zhuceamount;
	}

	public void setZhuceamount(String zhuceamount) {
		this.zhuceamount = zhuceamount;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}