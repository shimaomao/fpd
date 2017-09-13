/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 无形权力Entity
 * @author srf
 * @version 2016-03-24
 */
public class Quanli extends DataEntity<Quanli> {
	
	private static final long serialVersionUID = 1L;
	private Integer already;		// 已经经营权利时间
	private String biRate;		// 享有权重比例
	private String dengji;		// 登记情况
	private String liuTong;		// 流通性
	private Double moMoney;		// 模型估价
	private String name;		// 权利名称
	private String quality;		// 权利质量
	private String runStatus;		// 经营状况
	private Integer shengDate;		// 享有权利剩余期限
	private Double shouYi;		// 收益
	private String dizhiContractId;		// 外键关联抵质押信息id
	private Double beginMoMoney;		// 开始 模型估价
	private Double endMoMoney;		// 结束 模型估价
	private Integer beginShengDate;		// 开始 享有权利剩余期限
	private Integer endShengDate;		// 结束 享有权利剩余期限
	
	public Quanli() {
		super();
	}

	public Quanli(String id){
		super(id);
	}

	public Integer getAlready() {
		return already;
	}

	public void setAlready(Integer already) {
		this.already = already;
	}
	
	@Length(min=0, max=255, message="享有权重比例长度必须介于 0 和 255 之间")
	public String getBiRate() {
		return biRate;
	}

	public void setBiRate(String biRate) {
		this.biRate = biRate;
	}
	
	@Length(min=0, max=255, message="登记情况长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	@Length(min=0, max=255, message="流通性长度必须介于 0 和 255 之间")
	public String getLiuTong() {
		return liuTong;
	}

	public void setLiuTong(String liuTong) {
		this.liuTong = liuTong;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
	}
	
	@Length(min=0, max=255, message="权利名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="权利质量长度必须介于 0 和 255 之间")
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	@Length(min=0, max=255, message="经营状况长度必须介于 0 和 255 之间")
	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	
	public Integer getShengDate() {
		return shengDate;
	}

	public void setShengDate(Integer shengDate) {
		this.shengDate = shengDate;
	}
	
	public Double getShouYi() {
		return shouYi;
	}

	public void setShouYi(Double shouYi) {
		this.shouYi = shouYi;
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
		
	public Integer getBeginShengDate() {
		return beginShengDate;
	}

	public void setBeginShengDate(Integer beginShengDate) {
		this.beginShengDate = beginShengDate;
	}
	
	public Integer getEndShengDate() {
		return endShengDate;
	}

	public void setEndShengDate(Integer endShengDate) {
		this.endShengDate = endShengDate;
	}
		
}