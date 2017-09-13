/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.entity.rule;

import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;

/**
 * 收费规则Entity
 * @author chenh
 * @version 2016-07-01
 */
public class BiRule extends DataEntity<BiRule> {
	
	private static final long serialVersionUID = 1L;
	private BiPrice price;		// 单价标识
	private String name;		// 标题
	private Long number;		// 数量
	private Long waringNumber;		// 提醒数量
	private Integer unit;		// 单位类型
	private Integer unitVal;		// 单位类型值
	private Double rate;		// 优惠率
	private Double averagePrice;		// 平均价
	private Double totalPrice;		// 总价
	private Long totalTime;		// 总使用时间
	private Integer type;		// 规则类型
	private String organId;		// 租户ID
	private Boolean hasBuy;		// 是否已购买
	
	public BiRule() {
		super();
	}

	public BiRule(String id){
		super(id);
	}
	
	public BiRule(Integer type) {
		super();
		this.type = type;
	}

	public BiPrice getPrice() {
		return price;
	}

	public void setPrice(BiPrice price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	public Double getRate() {
		return rate;
	}

	public Long getWaringNumber() {
		return waringNumber;
	}

	public void setWaringNumber(Long waringNumber) {
		this.waringNumber = waringNumber;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	public Integer getUnitVal() {
		return unitVal;
	}

	public void setUnitVal(Integer unitVal) {
		this.unitVal = unitVal;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	@Transactional
	public Boolean getHasBuy() {
		if(this.hasBuy == null){
			hasBuy = false;
		}
		return hasBuy;
	}
	
	@Transactional
	public void setHasBuy(Boolean hasBuy) {
		this.hasBuy = hasBuy;
	}
}