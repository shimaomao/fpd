/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.company.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 企业客户Entity
 * @author lx
 * @version 2016-03-14
 */
@ApiModel(value="企业客户")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TCompany extends ActEntity<TCompany> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="企业名称", dataType="String", notes="企业名称", hidden=false, required=true)
	private String name;		// 企业名称
	@ApiModelProperty(value="企业地址", dataType="String", notes="企业地址", hidden=false, required=true)
	private String address;		// 企业地址
	@ApiModelProperty(value="企业电话", dataType="String", notes="企业电话", hidden=false, required=true)
	private String phone;		// 企业电话
	@ApiModelProperty(value="证件号码", dataType="String", notes="证件号码", hidden=false, required=true)
	private String cardNum;		// 证件号码
	@ApiModelProperty(value="证件类型", dataType="String", notes="证件类型", hidden=false, required=true)
	private String cardType;		// 证件类型
	@ApiModelProperty(value="企业性质", dataType="String", notes="企业性质", hidden=false, required=true)
	private String properties;		// 企业性质
	@ApiModelProperty(value="成立时间", dataType="String", notes="成立时间", hidden=false, required=true)
	private Date setupTime;		// 成立时间
	@ApiModelProperty(value="月营业额", dataType="String", notes="月营业额", hidden=false, required=true)
	private String turnover;		// 月营业额
	@ApiModelProperty(value="注册资金", dataType="String", notes="注册资金", hidden=false, required=true)
	private String captial;		// 注册资金
	@ApiModelProperty(value="主营范围", dataType="String", notes="主营范围", hidden=false, required=true)
	private String mainBusiness;		// 主营范围
	@ApiModelProperty(value="法定代表人", dataType="String", notes="法定代表人", hidden=false, required=true)
	private String surety;		// 法定代表人
	@ApiModelProperty(value="法人性别", dataType="String", notes="法人性别", hidden=false, required=true)
	private String suretySex;		// 法人性别
	@ApiModelProperty(value="法人年龄", dataType="String", notes="法人年龄", hidden=false, required=true)
	private String suretyAge;		// 法人年龄
	@ApiModelProperty(value="法人出生日", dataType="String", notes="法人出生日", hidden=false, required=true)
	private String suretyBirthday;		// 法人出生日
	@ApiModelProperty(value="法人邮箱", dataType="String", notes="法人邮箱", hidden=false, required=true)
	private String suretyEmail;		// 法人邮箱
	@ApiModelProperty(value="法人开户行", dataType="String", notes="法人开户行", hidden=false, required=true)
	private String gatheringBank;		// 法人开户行
	@ApiModelProperty(value="法人开户名", dataType="String", notes="法人开户名", hidden=false, required=true)
	private String gatheringName;		// 法人开户名
	@ApiModelProperty(value="法人开户账号", dataType="String", notes="法人开户账号", hidden=false, required=true)
	private String gatheringNumber;		// 法人开户账号
	@ApiModelProperty(value="法人身份证", dataType="String", notes="人身份证", hidden=false, required=true)
	private String suretyCardnum;		// 法人身份证
	@ApiModelProperty(value="法人手机号码", dataType="String", notes="法人手机号码", hidden=false, required=true)
	private String suretyMobile;		// 法人手机号码
	@ApiModelProperty(value="法人联系电话", dataType="String", notes="法人联系电话", hidden=false, required=true)
	private String suretyTelephone;		// 法人联系电话
	@ApiModelProperty(value="法人户籍地址", dataType="String", notes="法人户籍地址", hidden=false, required=true)
	private String suretyRegaddr;		// 法人户籍地址
	@ApiModelProperty(value="法人现住址", dataType="String", notes="法人现住址", hidden=false, required=true)
	private String suretyAddress;		// 法人现住址
	@ApiModelProperty(value="黑名状态", dataType="String", notes="黑名状态", hidden=false, required=true)
	private String status;		// 黑名状态  black:黑名，black_audit:黑名审核中unnormal目标客户   normal正常客户
	@ApiModelProperty(value="黑名原因说明", dataType="String", notes="黑名原因说明", hidden=false, required=true)
	private String reason;		// 黑名原因说明
	@ApiModelProperty(value="客户类型", dataType="String", notes="客户类型", hidden=false, required=true)
	private String customerSource;		// 客户来源

	@ApiModelProperty(value="W端产品", dataType="String", notes="W端产品", hidden=false, required=true)
	private String wtypeId;
	
	
	private String scanFlag; //监管系统扫描标示  0:为扫描  1:已经扫描
    private String companyName; //不持久化数据库
    private String pushStatus; //标示推送监管的状态  0：位推送过  1：已推送过
	public TCompany() {
		super();
	}

	public TCompany(String id){
		super(id);
	}

	@Length(min=0, max=50, message="企业名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="企业地址长度必须介于 0 和 50 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=20, message="企业电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=80, message="证件号码长度必须介于 0 和 80 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	@Length(min=0, max=50, message="证件类型长度必须介于 0 和 50 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Length(min=0, max=11, message="企业性质长度必须介于 0 和 11 之间")
	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSetupTime() {
		return setupTime;
	}

	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}
	
	@Length(min=0, max=255, message="月营业额长度必须介于 0 和 255 之间")
	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	
	@Length(min=0, max=20, message="注册资金长度必须介于 0 和 20 之间")
	public String getCaptial() {
		return captial;
	}

	public void setCaptial(String captial) {
		this.captial = captial;
	}
	
	@Length(min=0, max=100, message="主营范围长度必须介于 0 和 100 之间")
	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	
	@Length(min=0, max=20, message="法定代表人长度必须介于 0 和 20 之间")
	public String getSurety() {
		return surety;
	}

	public void setSurety(String surety) {
		this.surety = surety;
	}
	
	@Length(min=0, max=11, message="法人性别长度必须介于 0 和 11 之间")
	public String getSuretySex() {
		return suretySex;
	}

	public void setSuretySex(String suretySex) {
		this.suretySex = suretySex;
	}
	
	@Length(min=0, max=80, message="法人邮箱长度必须介于 0 和 80 之间")
	public String getSuretyEmail() {
		return suretyEmail;
	}

	public void setSuretyEmail(String suretyEmail) {
		this.suretyEmail = suretyEmail;
	}
	
	@Length(min=0, max=100, message="法人开户行长度必须介于 0 和 100 之间")
	public String getGatheringBank() {
		return gatheringBank;
	}

	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	
	@Length(min=0, max=20, message="法人开户名长度必须介于 0 和 20 之间")
	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	
	@Length(min=0, max=30, message="法人开户账号长度必须介于 0 和 30 之间")
	public String getGatheringNumber() {
		return gatheringNumber;
	}

	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	
	@Length(min=0, max=20, message="法人身份证长度必须介于 0 和 20 之间")
	public String getSuretyCardnum() {
		return suretyCardnum;
	}

	public void setSuretyCardnum(String suretyCardnum) {
		this.suretyCardnum = suretyCardnum;
	}
	
	@Length(min=0, max=13, message="法人手机号码长度必须介于 0 和 13 之间")
	public String getSuretyMobile() {
		return suretyMobile;
	}

	public void setSuretyMobile(String suretyMobile) {
		this.suretyMobile = suretyMobile;
	}
	
	@Length(min=0, max=20, message="法人联系电话长度必须介于 0 和 20 之间")
	public String getSuretyTelephone() {
		return suretyTelephone;
	}

	public void setSuretyTelephone(String suretyTelephone) {
		this.suretyTelephone = suretyTelephone;
	}
	
	@Length(min=0, max=20, message="法人户籍地址长度必须介于 0 和 20 之间")
	public String getSuretyRegaddr() {
		return suretyRegaddr;
	}

	public void setSuretyRegaddr(String suretyRegaddr) {
		this.suretyRegaddr = suretyRegaddr;
	}
	
	@Length(min=0, max=20, message="法人现住址长度必须介于 0 和 20 之间")
	public String getSuretyAddress() {
		return suretyAddress;
	}

	public void setSuretyAddress(String suretyAddress) {
		this.suretyAddress = suretyAddress;
	}
	
	@Length(min=0, max=20, message="黑名状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="黑名原因说明长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=11, message="客户类型长度必须介于 0 和 11 之间")
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}

	public String getSuretyAge() {
		return suretyAge;
	}

	public void setSuretyAge(String suretyAge) {
		this.suretyAge = suretyAge;
	}

	public String getSuretyBirthday() {
		return suretyBirthday;
	}

	public void setSuretyBirthday(String suretyBirthday) {
		this.suretyBirthday = suretyBirthday;
	}

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
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

	public String senData(){
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		if(this.getSetupTime() != null && !"".equals(this.getSetupTime())){
			date = formatter.format(this.getSetupTime());
		}
		
    	//this.currentUser.getOffice().getName();
    	if("1".equals(this.getDelFlag())){ //删除数据
    		return "D" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
    				+ "" + "|" +""+"|"+"" + "|" +""+"|" +"" +"|" +"" + "|" + "" +"|" + "" +"|" + "" + "|" 
    				+ this.getCardNum() + "|" + "" + "|" + this.getProperties() + "|" + "" + "|" + this.getSurety() +"|" + date +"|" +"" + "|" + this.getCaptial() +"|" 
    				+ "" + "|" + this.getMainBusiness() + "|" + "" + "|" + "" +"|" + "" + "|" + "" + "|" + "" + "|" + "" +"|"
    				+ "" + "|" + "" +"|" +"1,"+this.getId();    		
    	}else{ //新增修改数据  		
    		
    		if("0".equals(this.getPushStatus())){
    			return "A" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
        				+ "" + "|" +""+"|"+"" + "|" +""+"|" +"" +"|" +"" + "|" + "" +"|" + "" +"|" + "" + "|" 
        				+ this.getCardNum() + "|" + "" + "|" + this.getProperties() + "|" + "" + "|" + this.getSurety() +"|" + date +"|" +"" + "|" + this.getCaptial() +"|" 
        				+ "" + "|" + this.getMainBusiness() + "|" + "" + "|" + "" +"|" + "" + "|" + "" + "|" + "" + "|" + "" +"|"
        				+ "" + "|" + "" +"|" +"1,"+this.getId();   
    		}else{
    			return "U" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
        				+ "" + "|" +""+"|"+"" + "|" +""+"|" +"" +"|" +"" + "|" + "" +"|" + "" +"|" + "" + "|" 
        				+ this.getCardNum() + "|" + "" + "|" + this.getProperties() + "|" + "" + "|" + this.getSurety() +"|" + date +"|" +"" + "|" + this.getCaptial() +"|" 
        				+ "" + "|" + this.getMainBusiness() + "|" + "" + "|" + "" +"|" + "" + "|" + "" + "|" + "" + "|" + "" +"|"
        				+ "" + "|" + "" +"|" +"1,"+this.getId();   
    		}
    	}    	
    }
	
}