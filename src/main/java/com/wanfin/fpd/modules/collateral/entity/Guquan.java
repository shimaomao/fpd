/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 股权Entity
 * @author srf
 * @version 2016-03-24
 */
public class Guquan extends DataEntity<Guquan> {
	
	private static final long serialVersionUID = 1L;
	private Double capital;		// 注册资本
	private String control;		// 控制权人
	private String dengji;		// 登记信息
	private String hangType;		// 行业类别
	private String license;		// 营业执照号
	private Double moMoney;		// 模型估价
	private String name;		// 目标公司名称
	private Double netAssets;		// 净资产
	private Integer runDate;		// 经营期限
	private String runStatus;		// 经营状况
	private Date zhuDate;		// 注册时间
	private String dizhiContractId;		// 抵质押信息外键id
	private Double beginCapital;		// 开始 注册资本
	private Double endCapital;		// 结束 注册资本
	
	public Guquan() {
		super();
	}

	public Guquan(String id){
		super(id);
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}
	
	@Length(min=0, max=255, message="控制权人长度必须介于 0 和 255 之间")
	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
	
	@Length(min=0, max=255, message="登记信息长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	@Length(min=0, max=255, message="行业类别长度必须介于 0 和 255 之间")
	public String getHangType() {
		return hangType;
	}

	public void setHangType(String hangType) {
		this.hangType = hangType;
	}
	
	@Length(min=0, max=255, message="营业执照号长度必须介于 0 和 255 之间")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
	}
	
	@Length(min=0, max=255, message="目标公司名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getNetAssets() {
		return netAssets;
	}

	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}
	
	public Integer getRunDate() {
		return runDate;
	}

	public void setRunDate(Integer runDate) {
		this.runDate = runDate;
	}
	
	@Length(min=0, max=255, message="经营状况长度必须介于 0 和 255 之间")
	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getZhuDate() {
		return zhuDate;
	}

	public void setZhuDate(Date zhuDate) {
		this.zhuDate = zhuDate;
	}
	
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
	}
	
	public Double getBeginCapital() {
		return beginCapital;
	}

	public void setBeginCapital(Double beginCapital) {
		this.beginCapital = beginCapital;
	}
	
	public Double getEndCapital() {
		return endCapital;
	}

	public void setEndCapital(Double endCapital) {
		this.endCapital = endCapital;
	}
		
}