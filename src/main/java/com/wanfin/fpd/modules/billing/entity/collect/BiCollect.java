/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.entity.collect;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;

/**
 * 计费汇总Entity
 * @author chenh
 * @version 2016-07-02
 */
public class BiCollect extends DataEntity<BiCollect> {
	
	private static final long serialVersionUID = 1L;
	private BiElement element;		// 收费项
	private BiRule rule;		// 收费规则标识
	private Double totalPrice;		// 总价
	private Long surplusNumber;		// 使用数量
	private Long surplusTime;		// 使用时间
	private Long number;		// 数量
	private Long WaringNumber;		// 数量
	private Long totalTime;		// 总使用时间
	private Integer status;		// 可用状态
	private Integer txFlag;		// 提醒标识
	private String organId;		// 租户ID
	
	public BiCollect() {
		super();
	}

	public BiCollect(String id){
		super(id);
	}
	
	public BiRule getRule() {
		return rule;
	}

	public Long getWaringNumber() {
		return WaringNumber;
	}

	public void setWaringNumber(Long waringNumber) {
		WaringNumber = waringNumber;
	}

	public void setRule(BiRule rule) {
		this.rule = rule;
	}

	@NotNull(message="总价不能为空")
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Long getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Long surplusNumber) {
		this.surplusNumber = surplusNumber;
	}
	
	@NotNull(message="使用时间不能为空")
	public Long getSurplusTime() {
		return surplusTime;
	}
	
	public BiElement getElement() {
		return element;
	}

	public void setElement(BiElement element) {
		this.element = element;
	}

	public void setSurplusTime(Long surplusTime) {
		this.surplusTime = surplusTime;
	}
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	@NotNull(message="总使用时间不能为空")
	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getTxFlag() {
		return txFlag;
	}

	public void setTxFlag(Integer txFlag) {
		this.txFlag = txFlag;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
}