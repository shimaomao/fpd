/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 融资列表Entity
 * @author lx
 * @version 2016-05-09
 */
public class TFinance extends DataEntity<TFinance> {
	
	private static final long serialVersionUID = 1L;
	private String rongzimoney;		// 融资金额
	private Date rongzitime;		// 融资时间
	private String rongzitype;		// 状态
	private String rongziway;		// 融资途径
	private String organId;		// 租户ID
	
	public TFinance() {
		super();
	}

	public TFinance(String id){
		super(id);
	}

	public String getRongzimoney() {
		return rongzimoney;
	}

	public void setRongzimoney(String rongzimoney) {
		this.rongzimoney = rongzimoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRongzitime() {
		return rongzitime;
	}

	public void setRongzitime(Date rongzitime) {
		this.rongzitime = rongzitime;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getRongzitype() {
		return rongzitype;
	}

	public void setRongzitype(String rongzitype) {
		this.rongzitype = rongzitype;
	}
	
	@Length(min=0, max=64, message="融资途径长度必须介于 0 和 64 之间")
	public String getRongziway() {
		return rongziway;
	}

	public void setRongziway(String rongziway) {
		this.rongziway = rongziway;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}