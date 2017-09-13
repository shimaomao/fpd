/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.spouse.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * spouseEntity
 * @author spouse
 * @version 2016-07-04
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TSpouse extends DataEntity<TSpouse> {
	
	private static final long serialVersionUID = 1L;
	private String pouseName;		// 配偶姓名
	private String cardType;		// 证件类型
	private String pouseCardNum;	// 证件号码
	private String pouseEducation;		// 学历
	private String phone;		// 联系电话
	private String address;		// 现居住地址
	private String politicalStatus;		//户籍地址
	private String pouseMonthIncome;		// 月收入

	private String units;		// 工作单位
	private String unitsPhone;		// 单位电话
	private String unitsAddress;		// 单位地址
	private String position;		// 职位
	
	private Date birthday;            //出生日期
	private Date liveTime;           //现住址入住时间
	private String industry;         //行业类别
	private String unitNature;        //单位性质
	private String unitSize;           //单位规模
	private Date unitTime;           //本单位入职时间
	private String political;         //职称
	
	
	private String customerType;		// 客户类型
	private String customerId;		// 客户姓名
	private String customerName;		// 客户姓名
	
	
	
	
	
	
	
	
	public TSpouse() {
		super();
	}

	public TSpouse(String id){
		super(id);
	}


	
	@Length(min=0, max=11, message="证件类型长度必须介于 0 和 11 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	

	
	
	@Length(min=0, max=50, message="工作单位长度必须介于 0 和 50 之间")
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	
	
	@Length(min=0, max=50, message="单位电话长度必须介于 0 和 50 之间")
	public String getUnitsPhone() {
		return unitsPhone;
	}

	public void setUnitsPhone(String unitsPhone) {
		this.unitsPhone = unitsPhone;
	}
	
	@Length(min=0, max=50, message="单位地址长度必须介于 0 和 50 之间")
	public String getUnitsAddress() {
		return unitsAddress;
	}

	public void setUnitsAddress(String unitsAddress) {
		this.unitsAddress = unitsAddress;
	}
	
	@Length(min=0, max=50, message="职位长度必须介于 0 和 50 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=50, message="政治面貌长度必须介于 0 和 50 之间")
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	
	@Length(min=0, max=50, message="联系电话长度必须介于 0 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=50, message="联系地址长度必须介于 0 和 50 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=11, message="客户类型长度必须介于 0 和 11 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=50, message="客户姓名长度必须介于 0 和 50 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPouseName() {
		return pouseName;
	}

	public void setPouseName(String pouseName) {
		this.pouseName = pouseName;
	}

	public String getPouseCardNum() {
		return pouseCardNum;
	}

	public void setPouseCardNum(String pouseCardNum) {
		this.pouseCardNum = pouseCardNum;
	}

	public String getPouseEducation() {
		return pouseEducation;
	}

	public void setPouseEducation(String pouseEducation) {
		this.pouseEducation = pouseEducation;
	}

	public String getPouseMonthIncome() {
		return pouseMonthIncome;
	}

	public void setPouseMonthIncome(String pouseMonthIncome) {
		this.pouseMonthIncome = pouseMonthIncome;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getUnitNature() {
		return unitNature;
	}

	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}

	public String getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(String unitSize) {
		this.unitSize = unitSize;
	}

	public Date getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(Date unitTime) {
		this.unitTime = unitTime;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}
	
	
}