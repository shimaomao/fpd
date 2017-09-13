/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 用地Entity
 * @author srf
 * @version 2016-03-24
 */
public class YongLand extends DataEntity<YongLand> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private String area;		// 面积
	private Integer buyYear;		// 购买??份
	private String chanQuality;		// 土地性质
	private Double daiBalance;		// 土地抵押贷款余额
	private String dengji;		// 登记情况
	private Double guMoney;		// 租赁模型估值
	private Double jiaoMoney;		// 市场交易价格
	private Double landMoney;		// 同等土地单价
	private String man;		// 产权人
	private Double moMoney;		// 模型估价
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private Double tongMoney1;		// 同等商业房屋价格1
	private Double tongMoney2;		// 同等商业房屋价格2
	private Double xiaoMoney;		// 销售模型估值
	private Double yuArea;		// 预规划住宅面积
	private Integer yuYear;		// 剩余年限
	private Double zuMoney1;		// 同等商业房屋每月出租价格1
	private Double zuMoney2;		// 同等商业房屋每月出租价格2
	private String dizhiContractId;		// 关联抵质押信息表主键id
	private Double beginDaiBalance;		// 开始 土地抵押贷款余额
	private Double endDaiBalance;		// 结束 土地抵押贷款余额
	private Integer beginYuYear;		// 开始 剩余年限
	private Integer endYuYear;		// 结束 剩余年限
	
	public YongLand() {
		super();
	}

	public YongLand(String id){
		super(id);
	}

	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="面积长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getBuyYear() {
		return buyYear;
	}

	public void setBuyYear(Integer buyYear) {
		this.buyYear = buyYear;
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
	
	public Double getGuMoney() {
		return guMoney;
	}

	public void setGuMoney(Double guMoney) {
		this.guMoney = guMoney;
	}
	
	public Double getJiaoMoney() {
		return jiaoMoney;
	}

	public void setJiaoMoney(Double jiaoMoney) {
		this.jiaoMoney = jiaoMoney;
	}
	
	public Double getLandMoney() {
		return landMoney;
	}

	public void setLandMoney(Double landMoney) {
		this.landMoney = landMoney;
	}
	
	@Length(min=0, max=255, message="产权人长度必须介于 0 和 255 之间")
	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
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
	
	public Double getTongMoney1() {
		return tongMoney1;
	}

	public void setTongMoney1(Double tongMoney1) {
		this.tongMoney1 = tongMoney1;
	}
	
	public Double getTongMoney2() {
		return tongMoney2;
	}

	public void setTongMoney2(Double tongMoney2) {
		this.tongMoney2 = tongMoney2;
	}
	
	public Double getXiaoMoney() {
		return xiaoMoney;
	}

	public void setXiaoMoney(Double xiaoMoney) {
		this.xiaoMoney = xiaoMoney;
	}
	
	public Double getYuArea() {
		return yuArea;
	}

	public void setYuArea(Double yuArea) {
		this.yuArea = yuArea;
	}
	
	public Integer getYuYear() {
		return yuYear;
	}

	public void setYuYear(Integer yuYear) {
		this.yuYear = yuYear;
	}
	
	public Double getZuMoney1() {
		return zuMoney1;
	}

	public void setZuMoney1(Double zuMoney1) {
		this.zuMoney1 = zuMoney1;
	}
	
	public Double getZuMoney2() {
		return zuMoney2;
	}

	public void setZuMoney2(Double zuMoney2) {
		this.zuMoney2 = zuMoney2;
	}
	
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
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
		
	public Integer getBeginYuYear() {
		return beginYuYear;
	}

	public void setBeginYuYear(Integer beginYuYear) {
		this.beginYuYear = beginYuYear;
	}
	
	public Integer getEndYuYear() {
		return endYuYear;
	}

	public void setEndYuYear(Integer endYuYear) {
		this.endYuYear = endYuYear;
	}
		
}