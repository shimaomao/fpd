/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 工业用地信息Entity
 * @author srf
 * @version 2016-03-24
 */
public class GongLand extends DataEntity<GongLand> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private Double area;		// 面积
	private Integer buyYear;		// 购买年份
	private String chanQuality;		// 土地性质
	private Double daiBalance;		// 土地抵押贷款余额
	private String dengji;		// 登记情况
	private Double guMoney;		// 租赁模型估价
	private Double jiaoMoney;		// 市场交易价格
	private String man;		// 产权人
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private Double tongMoney1;		// 同等土地价格1
	private Double tongMoney2;		// 同等土地价格2
	private Double xiaoMoney;		// 销售模型估价
	private Integer yuYear;		// 剩余年限
	private Double zuMoney1;		// 出租价格1
	private Double zuMoney2;		// 出租价格2
	private String dizhiContractId;		// 外键关联抵质押信息id
	private Double beginArea;		// 开始 面积
	private Double endArea;		// 结束 面积
	private Integer beginBuyYear;		// 开始 购买年份
	private Integer endBuyYear;		// 结束 购买年份
	private Double beginJiaoMoney;		// 开始 市场交易价格
	private Double endJiaoMoney;		// 结束 市场交易价格
	private Integer beginYuYear;		// 开始 剩余年限
	private Integer endYuYear;		// 结束 剩余年限
	private Double beginZuMoney1;		// 开始 出租价格1
	private Double endZuMoney1;		// 结束 出租价格1
	private Double beginZuMoney2;		// 开始 出租价格2
	private Double endZuMoney2;		// 结束 出租价格2
	
	public GongLand() {
		super();
	}

	public GongLand(String id){
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
	
	@Length(min=0, max=11, message="外键关联抵质押信息id长度必须介于 0 和 11 之间")
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
		
	public Integer getBeginBuyYear() {
		return beginBuyYear;
	}

	public void setBeginBuyYear(Integer beginBuyYear) {
		this.beginBuyYear = beginBuyYear;
	}
	
	public Integer getEndBuyYear() {
		return endBuyYear;
	}

	public void setEndBuyYear(Integer endBuyYear) {
		this.endBuyYear = endBuyYear;
	}
		
	public Double getBeginJiaoMoney() {
		return beginJiaoMoney;
	}

	public void setBeginJiaoMoney(Double beginJiaoMoney) {
		this.beginJiaoMoney = beginJiaoMoney;
	}
	
	public Double getEndJiaoMoney() {
		return endJiaoMoney;
	}

	public void setEndJiaoMoney(Double endJiaoMoney) {
		this.endJiaoMoney = endJiaoMoney;
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
		
	public Double getBeginZuMoney1() {
		return beginZuMoney1;
	}

	public void setBeginZuMoney1(Double beginZuMoney1) {
		this.beginZuMoney1 = beginZuMoney1;
	}
	
	public Double getEndZuMoney1() {
		return endZuMoney1;
	}

	public void setEndZuMoney1(Double endZuMoney1) {
		this.endZuMoney1 = endZuMoney1;
	}
		
	public Double getBeginZuMoney2() {
		return beginZuMoney2;
	}

	public void setBeginZuMoney2(Double beginZuMoney2) {
		this.beginZuMoney2 = beginZuMoney2;
	}
	
	public Double getEndZuMoney2() {
		return endZuMoney2;
	}

	public void setEndZuMoney2(Double endZuMoney2) {
		this.endZuMoney2 = endZuMoney2;
	}
		
}