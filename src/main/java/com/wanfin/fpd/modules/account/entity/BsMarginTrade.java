/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.entity;

import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import com.wanfin.fpd.modules.sys.entity.Office;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 保证金记录表Entity
 * @author srf
 * @version 2017-01-03
 */
public class BsMarginTrade extends DataEntity<BsMarginTrade> {
	
	private static final long serialVersionUID = 1L;
	//private String financialProductId;		// 理财产品ID
	private String entityId;//对应类型ID
	private String entityType;//类型：1理财
	private BigDecimal marginMoney;		// 保证金
	private Integer status;		// 状态(0:新增；1:在押；2:退回)
	private String organId;		// 公司ID
	private Office office;		// 部门ID
	private String authUserId;		// 认证用户
	private String marginRatio;	//保证金比例
	
	public BsMarginTrade() {
		super();
	}

	public BsMarginTrade(String id){
		super(id);
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	@NotNull(message="保证金不能为空")
	public BigDecimal getMarginMoney() {
		return marginMoney;
	}

	public void setMarginMoney(BigDecimal marginMoney) {
		this.marginMoney = marginMoney;
	}
	
	@NotNull(message="状态(0:新增；1:在押；2:退回)不能为空")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="公司ID长度必须介于 0 和 100 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getMarginRatio() {
		return marginRatio;
	}

	public void setMarginRatio(String marginRatio) {
		this.marginRatio = marginRatio;
	}
	
}