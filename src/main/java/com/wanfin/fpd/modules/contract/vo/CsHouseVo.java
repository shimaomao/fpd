package com.wanfin.fpd.modules.contract.vo;

import java.math.BigDecimal;



/**
 * @Description [[_客户房产息_]] CsHouse类
 * @author zzm
 * @date 2016-7-18 上午9:34:02 
 */
public class CsHouseVo implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	private Integer id;		
	private Integer customerId;	//客户id
	private String ownershipNo;		//房产权证号
	private String commonStatus;	//共有情况
	private String owner;		//所有权人
	private String programme;	//规划
	private String location;	//位置
	private String purpose;		//用途
	private String buyTime;		//购买时间
	private BigDecimal buyprice;	//购买价格
	private String buildTime;	//建成时间
	private String usages;		//使用情况
	private String isMortgage;	//是否抵押
	private String mortgagee;	//抵押权人
	private BigDecimal mortgageAmount;	//抵押贷款金额
	private BigDecimal mortgageBalance;	//抵押贷款余额
	private String loanDueTime;		//贷款到期时间
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
	public String getOwnershipNo() {
		return ownershipNo;
	}
	public void setOwnershipNo(String ownershipNo) {
		this.ownershipNo = ownershipNo;
	}
	public String getCommonStatus() {
		return commonStatus;
	}
	public void setCommonStatus(String commonStatus) {
		this.commonStatus = commonStatus;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getProgramme() {
		return programme;
	}
	public void setProgramme(String programme) {
		this.programme = programme;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getUsages() {
		return usages;
	}
	public void setUsages(String usages) {
		this.usages = usages;
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
	

}