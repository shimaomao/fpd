/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 机器设备Entity
 * @author srf
 * @version 2016-03-24
 */
public class Machine extends DataEntity<Machine> {
	
	private static final long serialVersionUID = 1L;
	private String bianXian;		// 变现能力
	private String control;		// 控制劝人
	private String dengji;		// 登记情况
	private String general;		// 通用程度
	private Date madeDate;		// 出厂日期
	private String method;		// 控制权方式
	private Double moMoney;		// 模型估价
	private String model;		// 设备型号
	private String name;		// 设备名称
	private Double newMoney;		// 新货价格
	private String performance;		// 性能状况
	private Double second1;		// 二手价值1
	private Double second2;		// 二手价值2
	private Integer useDate;		// 使用年限
	private String dizhiContractId;		// 抵质押表外键id
	private Integer beginUseDate;		// 开始 使用年限
	private Integer endUseDate;		// 结束 使用年限
	
	public Machine() {
		super();
	}

	public Machine(String id){
		super(id);
	}

	@Length(min=0, max=255, message="变现能力长度必须介于 0 和 255 之间")
	public String getBianXian() {
		return bianXian;
	}

	public void setBianXian(String bianXian) {
		this.bianXian = bianXian;
	}
	
	@Length(min=0, max=255, message="控制劝人长度必须介于 0 和 255 之间")
	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
	
	@Length(min=0, max=255, message="登记情况长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	@Length(min=0, max=255, message="通用程度长度必须介于 0 和 255 之间")
	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	
	@Length(min=0, max=255, message="控制权方式长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="设备型号长度必须介于 0 和 255 之间")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@Length(min=0, max=255, message="设备名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getNewMoney() {
		return newMoney;
	}

	public void setNewMoney(Double newMoney) {
		this.newMoney = newMoney;
	}
	
	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}
	
	public Double getSecond1() {
		return second1;
	}

	public void setSecond1(Double second1) {
		this.second1 = second1;
	}
	
	public Double getSecond2() {
		return second2;
	}

	public void setSecond2(Double second2) {
		this.second2 = second2;
	}
	
	public Integer getUseDate() {
		return useDate;
	}

	public void setUseDate(Integer useDate) {
		this.useDate = useDate;
	}
	
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
	}
	
	public Integer getBeginUseDate() {
		return beginUseDate;
	}

	public void setBeginUseDate(Integer beginUseDate) {
		this.beginUseDate = beginUseDate;
	}
	
	public Integer getEndUseDate() {
		return endUseDate;
	}

	public void setEndUseDate(Integer endUseDate) {
		this.endUseDate = endUseDate;
	}
		
}