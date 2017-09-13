/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 住宅信息Entity
 * @author srf
 * @version 2016-03-24
 */
public class ZhuZhai extends DataEntity<ZhuZhai> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private Double area;		// 面积
	private String chanQuality;		// 土地性质
	private Double daiBalance;		// 土地抵押贷款余额
	private String dengji;		// 登记情况
	private Double landMoney1;		// 同等土地单价1
	private Double landMoney2;		// 同等土地单价2
	private Double landMoney3;		// 同等土地单价3
	private String man;		// 产权人
	private Double moMoney1;		// 模型估价
	private Double moMoney2;		// 模型估价2
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private Double yuArea;		// 预规划住宅面积
	private String dizhiContractId;		// 关联外键抵质押合同id
	private Double beginArea;		// 开始 面积
	private Double endArea;		// 结束 面积
	private Double beginDaiBalance;		// 开始 土地抵押贷款余额
	private Double endDaiBalance;		// 结束 土地抵押贷款余额
	
	public ZhuZhai() {
		super();
	}

	public ZhuZhai(String id){
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
	
	@Length(min=0, max=255, message="土地性质长度必须介于 0 和 255 之间")
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
	
	public Double getLandMoney3() {
		return landMoney3;
	}

	public void setLandMoney3(Double landMoney3) {
		this.landMoney3 = landMoney3;
	}
	
	@Length(min=0, max=255, message="产权人长度必须介于 0 和 255 之间")
	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}
	
	public Double getMoMoney1() {
		return moMoney1;
	}

	public void setMoMoney1(Double moMoney1) {
		this.moMoney1 = moMoney1;
	}
	
	public Double getMoMoney2() {
		return moMoney2;
	}

	public void setMoMoney2(Double moMoney2) {
		this.moMoney2 = moMoney2;
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
		
}