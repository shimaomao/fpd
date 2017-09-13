/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 存货信息Entity
 * @author srf
 * @version 2016-03-24
 */
public class Cunhuo extends DataEntity<Cunhuo> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 存货地点
	private String bianXian;		// 变形能力
	private String brand;		// 品牌
	private String control;		// 控制权人
	private String general;		// 通用程度
	private Double market1;		// 市场交易价1
	private Double market2;		// 市场交易价2
	private String method;		// 控制器方式
	private Double moMoney;		// 模型估价
	private String name;		// 存货名称
	private Integer num;		// 数量
	private String type;		// 型号
	private String dizhiContractId;		// 外键抵质押表
	private Double beginMoMoney;		// 开始 模型估价
	private Double endMoMoney;		// 结束 模型估价
	
	public Cunhuo() {
		super();
	}

	public Cunhuo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="存货地点长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="变形能力长度必须介于 0 和 255 之间")
	public String getBianXian() {
		return bianXian;
	}

	public void setBianXian(String bianXian) {
		this.bianXian = bianXian;
	}
	
	@Length(min=0, max=255, message="品牌长度必须介于 0 和 255 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=255, message="控制权人长度必须介于 0 和 255 之间")
	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
	
	@Length(min=0, max=255, message="通用程度长度必须介于 0 和 255 之间")
	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}
	
	public Double getMarket1() {
		return market1;
	}

	public void setMarket1(Double market1) {
		this.market1 = market1;
	}
	
	public Double getMarket2() {
		return market2;
	}

	public void setMarket2(Double market2) {
		this.market2 = market2;
	}
	
	@Length(min=0, max=255, message="控制器方式长度必须介于 0 和 255 之间")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
	}
	
	@Length(min=0, max=255, message="存货名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="型号长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
	}
	
	public Double getBeginMoMoney() {
		return beginMoMoney;
	}

	public void setBeginMoMoney(Double beginMoMoney) {
		this.beginMoMoney = beginMoMoney;
	}
	
	public Double getEndMoMoney() {
		return endMoMoney;
	}

	public void setEndMoMoney(Double endMoMoney) {
		this.endMoMoney = endMoMoney;
	}
		
}