/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.entity;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 客户车产Entity
 * @author zzm
 * @version 2016-07-21
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CsCar extends DataEntity<CsCar> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String carCode;		// 车辆识别号
	private String carNum;		// 车牌号
	private String engineNum;		// 引擎号
	private String registTime;		// 注册时间
	private String buyTime;		// 购买时间
	private BigDecimal buyprice;		// 购买价格
	private BigDecimal evalprice;		// 评估价格
	private String isMortgage;		// 是否抵押
	private String mortgagee;		// 抵押权人
	private String mortgageAmount;		// 抵押贷款金额
	private String mortgageBalance;		// 抵押贷款余额
	private String loanDueTime;		// 贷款到期时间
	private String carPur;		// 车辆用途
	private String organId;		// 租户ID
	
	public CsCar() {
		super();
	}

	public CsCar(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户id长度必须介于 1 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=50, message="车辆识别号长度必须介于 0 和 50 之间")
	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	
	@Length(min=0, max=20, message="车牌号长度必须介于 0 和 20 之间")
	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	@Length(min=0, max=50, message="引擎号长度必须介于 0 和 50 之间")
	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	
	@Length(min=0, max=10, message="注册时间长度必须介于 0 和 10 之间")
	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	
	@Length(min=0, max=10, message="购买时间长度必须介于 0 和 10 之间")
	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	
	public BigDecimal getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(BigDecimal buyprice) {
		this.buyprice = buyprice;
	}
	
	public BigDecimal getEvalprice() {
		return evalprice;
	}

	public void setEvalprice(BigDecimal evalprice) {
		this.evalprice = evalprice;
	}
	
	@Length(min=0, max=1, message="是否抵押长度必须介于 0 和 1 之间")
	public String getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}
	
	@Length(min=0, max=50, message="抵押权人长度必须介于 0 和 50 之间")
	public String getMortgagee() {
		return mortgagee;
	}

	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	
	public String getMortgageAmount() {
		return mortgageAmount;
	}

	public void setMortgageAmount(String mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}
	
	public String getMortgageBalance() {
		return mortgageBalance;
	}

	public void setMortgageBalance(String mortgageBalance) {
		this.mortgageBalance = mortgageBalance;
	}
	
	@Length(min=0, max=10, message="贷款到期时间长度必须介于 0 和 10 之间")
	public String getLoanDueTime() {
		return loanDueTime;
	}

	public void setLoanDueTime(String loanDueTime) {
		this.loanDueTime = loanDueTime;
	}
	
	@Length(min=0, max=20, message="车辆用途长度必须介于 0 和 20 之间")
	public String getCarPur() {
		return carPur;
	}

	public void setCarPur(String carPur) {
		this.carPur = carPur;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}