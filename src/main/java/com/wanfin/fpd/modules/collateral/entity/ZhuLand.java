/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 商宅用地Entity
 * @author srf
 * @version 2016-03-24
 */
public class ZhuLand extends DataEntity<ZhuLand> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private Double area;		// 面积
	private String chanQuality;		// chan_quality
	private Double daiBalance;		// 土地抵质押贷款余额
	private String dengji;		// 登记情况
	private Double landMoney1;		// 同等土地单价1
	private Double landMoney2;		// 同等土地单价2
	private String man;		// 产权人
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private Double yuArea;		// 预规划住宅面积
	private Double housingMonMoney;		// 同等商业房屋每月出租价格
	private Double housingMoney;		// 同等商业房屋价格
	private Double leaseValuation;		// 租赁模型估值
	private Double modelValuation;		// 模型估值
	private Double salesValuation;		// 销售租赁估值
	private String dizhiContractId;		// 关联外键抵质押信息id
	private Double beginArea;		// 开始 面积
	private Double endArea;		// 结束 面积
	private Double beginDaiBalance;		// 开始 土地抵质押贷款余额
	private Double endDaiBalance;		// 结束 土地抵质押贷款余额
	private Double beginModelValuation;		// 开始 模型估值
	private Double endModelValuation;		// 结束 模型估值
	
	public ZhuLand() {
		super();
	}

	public ZhuLand(String id){
		super(id);
	}

	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="chan_quality长度必须介于 0 和 255 之间")
	public String getChanQuality() {
		return chanQuality;
	}

	public void setChanQuality(String chanQuality) {
		this.chanQuality = chanQuality;
	}
	
	public Double getDaiBalance() {
		return daiBalance;
	}

	public void setDaiBalance(Double daiBalance) {
		this.daiBalance = daiBalance;
	}
	
	@Length(min=0, max=255, message="登记情况长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	public Double getLandMoney1() {
		return landMoney1;
	}

	public void setLandMoney1(Double landMoney1) {
		this.landMoney1 = landMoney1;
	}
	
	public Double getLandMoney2() {
		return landMoney2;
	}

	public void setLandMoney2(Double landMoney2) {
		this.landMoney2 = landMoney2;
	}
	
	@Length(min=0, max=255, message="产权人长度必须介于 0 和 255 之间")
	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}
	
	@Length(min=0, max=255, message="权证编号长度必须介于 0 和 255 之间")
	public String getQuanNum() {
		return quanNum;
	}

	public void setQuanNum(String quanNum) {
		this.quanNum = quanNum;
	}
	
	@Length(min=0, max=255, message="地段描述长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getYuArea() {
		return yuArea;
	}

	public void setYuArea(Double yuArea) {
		this.yuArea = yuArea;
	}
	
	public Double getHousingMonMoney() {
		return housingMonMoney;
	}

	public void setHousingMonMoney(Double housingMonMoney) {
		this.housingMonMoney = housingMonMoney;
	}
	
	public Double getHousingMoney() {
		return housingMoney;
	}

	public void setHousingMoney(Double housingMoney) {
		this.housingMoney = housingMoney;
	}
	
	public Double getLeaseValuation() {
		return leaseValuation;
	}

	public void setLeaseValuation(Double leaseValuation) {
		this.leaseValuation = leaseValuation;
	}
	
	public Double getModelValuation() {
		return modelValuation;
	}

	public void setModelValuation(Double modelValuation) {
		this.modelValuation = modelValuation;
	}
	
	public Double getSalesValuation() {
		return salesValuation;
	}

	public void setSalesValuation(Double salesValuation) {
		this.salesValuation = salesValuation;
	}
	
	@Length(min=0, max=11, message="关联外键抵质押信息id长度必须介于 0 和 11 之间")
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
	}
	
	public Double getBeginArea() {
		return beginArea;
	}

	public void setBeginArea(Double beginArea) {
		this.beginArea = beginArea;
	}
	
	public Double getEndArea() {
		return endArea;
	}

	public void setEndArea(Double endArea) {
		this.endArea = endArea;
	}
		
	public Double getBeginDaiBalance() {
		return beginDaiBalance;
	}

	public void setBeginDaiBalance(Double beginDaiBalance) {
		this.beginDaiBalance = beginDaiBalance;
	}
	
	public Double getEndDaiBalance() {
		return endDaiBalance;
	}

	public void setEndDaiBalance(Double endDaiBalance) {
		this.endDaiBalance = endDaiBalance;
	}
		
	public Double getBeginModelValuation() {
		return beginModelValuation;
	}

	public void setBeginModelValuation(Double beginModelValuation) {
		this.beginModelValuation = beginModelValuation;
	}
	
	public Double getEndModelValuation() {
		return endModelValuation;
	}

	public void setEndModelValuation(Double endModelValuation) {
		this.endModelValuation = endModelValuation;
	}
		
}