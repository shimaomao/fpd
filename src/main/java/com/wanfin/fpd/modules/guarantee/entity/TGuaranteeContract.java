/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.guarantee.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.contract.IContract;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 担保合同Entity
 * @author zzm
 * @version 2016-03-24
 */
public class TGuaranteeContract extends DataEntity<TGuaranteeContract> implements IContract{
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private Double amount;		// 保证金额
	private String cardNum;		// 证件号码
	private String cardType;		// 证件类型
	private Date contractDate;		// 担保日期
	private String guaranteeNumber;		// 担保合同编号
	private String guaranteeType;		// 保证方式
	private Integer period;		// 担保期限
	private String periodType;		// 担保周期
	private String rate;		// 担保费率
	private String rateType;		// 担保费率类型
	private String telephone;		// 联系电话
	private String businessTable;		// 关联的业务表名称
	private String businessId;		// 关联的业务id
	private String contractNumber;  //关联合同业务编号  页面使用，不存入数据库字段
	private String type;		// 类型
	private String assetsWorth;		// 资产价值
	private String guarantorName;		// 担保人姓名
	private String guarantorType;		// 担保人类型
	private String mainAssets;		// 主要资产
	private String post;		// 主要范围及主要产品
	private String relation;		// 与债务人关系
	private String surety;		// 法定代表人
	private String turnover;		// 月营业额
	private TProduct product;
	private String customerName;
	
	private int maritalStatus;               //婚姻状况
	private String otherGuarantor;             //对外担保金额
	private String  debtAmount;                  //担保人负债金额
	
    private String scanFlag; //监管系统扫描标示 0:位扫描  1:已经扫描
    private String companyName;  //不持久化
    private String pushStatus;  
	
	public TGuaranteeContract() {
		super();
	}

	public TGuaranteeContract(String id){
		super(id);
	}

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}		
	
	
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}	

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=255, message="证件号码长度必须介于 0 和 255 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	@Length(min=0, max=255, message="证件类型长度必须介于 0 和 255 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	
	@Length(min=0, max=255, message="担保合同编号长度必须介于 0 和 255 之间")
	public String getGuaranteeNumber() {
		return guaranteeNumber;
	}

	public void setGuaranteeNumber(String guaranteeNumber) {
		this.guaranteeNumber = guaranteeNumber;
	}
	
	@Length(min=0, max=255, message="担保方式长度必须介于 0 和 255 之间")
	public String getGuaranteeType() {
		return guaranteeType;
	}
	
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	@Length(min=0, max=20, message="担保周期长度必须介于 0 和 20 之间")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@Length(min=0, max=255, message="担保费率长度必须介于 0 和 255 之间")
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Length(min=0, max=255, message="担保费率类型长度必须介于 0 和 255 之间")
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=255, message="业务表名必须介于 0 和 64 之间")
	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}
	
	@Length(min=0, max=255, message="业务长度必须介于 0 和 64 之间")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Length(min=0, max=255, message="类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="资产价值长度必须介于 0 和 255 之间")
	public String getAssetsWorth() {
		return assetsWorth;
	}

	public void setAssetsWorth(String assetsWorth) {
		this.assetsWorth = assetsWorth;
	}
	
	
	@Length(min=0, max=255, message="担保人姓名长度必须介于 0 和 255 之间")
	public String getGuarantorName() {
		return guarantorName;
	}

	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}
	
	@Length(min=0, max=255, message="担保人类型长度必须介于 0 和 255 之间")
	public String getGuarantorType() {
		return guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	
	@Length(min=0, max=255, message="主要资产长度必须介于 0 和 255 之间")
	public String getMainAssets() {
		return mainAssets;
	}

	public void setMainAssets(String mainAssets) {
		this.mainAssets = mainAssets;
	}
	
	@Length(min=0, max=255, message="主要范围及主要产品长度必须介于 0 和 255 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=255, message="与债务人关系长度必须介于 0 和 255 之间")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	@Length(min=0, max=255, message="法定代表人长度必须介于 0 和 255 之间")
	public String getSurety() {
		return surety;
	}

	public void setSurety(String surety) {
		this.surety = surety;
	}
	
	@Length(min=0, max=255, message="月营业额长度必须介于 0 和 255 之间")
	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	@Override
	public String getUkey() {
		return IContractUkey.GUARANTEE;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public int getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getOtherGuarantor() {
		return otherGuarantor;
	}

	public void setOtherGuarantor(String otherGuarantor) {
		this.otherGuarantor = otherGuarantor;
	}

	public String getDebtAmount() {
		return debtAmount;
	}

	public void setDebtAmount(String debtAmount) {
		this.debtAmount = debtAmount;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	
	
	public String toSend() {		
		String str = "";
		if("1".equals(this.getDelFlag())){
			str += "D"+"|"+this.getCompanyName()+"|";
		}else{
			if("0".equals(this.getPushStatus())){
				str += "A"+"|"+this.getCompanyName()+"|";
			}else{
				str += "U"+"|"+this.getCompanyName()+"|";
			}
			
		}	
		
		/*
		 * /*
 * 		
							/*
							 * 字段1 	标识符	Varchar(1)
							字段2	担保公司唯一标示	Varchar(25)
							字段3	关联主合同编号	Varchar(25)
							字段4	担保人类型	Varchar(25)
							字段5	担保人名称	Varchar(25)
							字段6	证件类型	Varchar(25)
							字段7	证件号	Varchar(25)
							字段8	担保人电话	Varchar(25)
							字段9	担保人地址	Varchar(25)
							字段10	与借款人关系	Varchar(25)
							字段11	担保金额（单位：元）	Varchar(25)
							字段12	担保类型	Varchar(25)
							字段13	担保方式	Varchar(25)
							字段14	担保期限类型	Varchar(25)
							字段15	担保期限	Varchar(25)
							字段16	担保日期	Varchar(25)
							字段17	担保人资产价值	Varchar(25)
							字段18	担保人资产来源	Varchar(25)
							字段19	担保人负债金额	Varchar(25)
							字段20	担保人对外其他担保金额金额	Varchar(25)
							字段21	第三方系统记录ID

							
		
	 */
		 
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String getContractDate = "";
		if(this.getContractDate() != null){
			getContractDate = formatter.format(this.getContractDate());
		}
		
		str += this.getContractNumber()+"|" + this.getGuarantorType()+"|" + this.getGuarantorName()+"|" + this.getCardType()+"|" + this.getCardNum()+"|" + this.getTelephone()+"|";
		
		
		str += this.getAddress()+"|"+ this.getRelation()+"|"+ this.getAmount()+"|"+ this.getType()+"|"+ this.getGuaranteeType()+"|"+ this.getPeriodType()+"|"+ this.getPeriod()+"|"+ getContractDate+"|";
		str += this.getAssetsWorth()+"|" + this.getPost()+"|" +this.getDebtAmount()+"|" +this.getOtherGuarantor()+"|" +this.getId();
		
		

		return str;
	}
}