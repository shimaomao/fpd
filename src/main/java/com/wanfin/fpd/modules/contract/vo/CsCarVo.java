package com.wanfin.fpd.modules.contract.vo;

import java.math.BigDecimal;



/**
 * @Description [[_客户车辆信息_]] CsCar类
 * @author zzm
 * @date 2016-7-18 上午9:34:02 
 */
public class CsCarVo implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	private Integer id;		
	private Integer customerId;	//客户id
	private String carCode;		//车辆识别号
	private String carNum;		//车牌号
	private String engineNum;	//引擎号
	private String registTime;	//注册时间
	private String buyTime;		//购买时间
	private BigDecimal buyprice;	//购买价格
	private BigDecimal evalprice;	//评估价格
	private String isMortgage;	//是否抵押
	private String mortgagee;	//抵押权人
	private BigDecimal mortgageAmount;	//抵押贷款金额
	private BigDecimal mortgageBalance;	//抵押贷款余额
	private String loanDueTime;		//贷款到期时间
	private String carPur;	//车辆用途
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
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
	public String getIsMortgage() {
		return isMortgage;
	}
	public void setIsMortgage(String isMortgage) {
		this.isMortgage = isMortgage;
	}
	public String getMortgagee() {
		return mortgagee;
	}
	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	public BigDecimal getMortgageAmount() {
		return mortgageAmount;
	}
	public void setMortgageAmount(BigDecimal mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}
	public BigDecimal getMortgageBalance() {
		return mortgageBalance;
	}
	public void setMortgageBalance(BigDecimal mortgageBalance) {
		this.mortgageBalance = mortgageBalance;
	}
	public String getLoanDueTime() {
		return loanDueTime;
	}
	public void setLoanDueTime(String loanDueTime) {
		this.loanDueTime = loanDueTime;
	}
	public String getCarPur() {
		return carPur;
	}
	public void setCarPur(String carPur) {
		this.carPur = carPur;
	}
	

}