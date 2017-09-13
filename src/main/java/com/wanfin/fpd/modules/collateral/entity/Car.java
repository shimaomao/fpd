/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 车辆信息Entity
 * @author srf
 * @version 2016-03-24
 */
public class Car extends DataEntity<Car> {
	
	private static final long serialVersionUID = 1L;
	private Integer accident;		// 事故次数
	private String carKuang;		// 车况
	private Double carMoney;		// 新车价格
	private String carNum;		// 车型编号
	private String carType;		// 车型
	private String carXi;		// 车系
	private String color;		// 车辆颜色
	private String configuration;		// 配置
	private String creator;		// 制造商
	private String dengji;		// 登记情况
	private String displacement;		// 排量
	private String engine;		// 发动机型号
	private String frame;		// 车架号
	private Date madeDate;		// 出厂日期
	private Double market1;		// 市场交易价格1
	private Double market2;		// 市场交易价格2
	private Double mileage;		// 里程数
	private Double moMoney;		// 模型估价
	private String producing;		// 车辆产地
	private Integer seating;		// 座位数
	private Integer useDate;		// 使用年限
	private String dizhiContractId;		// 外键关联抵质押信息表
	private Integer beginAccident;		// 开始 事故次数
	private Integer endAccident;		// 结束 事故次数
	private Double beginCarMoney;		// 开始 新车价格
	private Double endCarMoney;		// 结束 新车价格
	private Date beginMadeDate;		// 开始 出厂日期
	private Date endMadeDate;		// 结束 出厂日期
	private Double beginMoMoney;		// 开始 模型估价
	private Double endMoMoney;		// 结束 模型估价
	
	public Car() {
		super();
	}

	public Car(String id){
		super(id);
	}

	public Integer getAccident() {
		return accident;
	}

	public void setAccident(Integer accident) {
		this.accident = accident;
	}
	
	@Length(min=0, max=255, message="车况长度必须介于 0 和 255 之间")
	public String getCarKuang() {
		return carKuang;
	}

	public void setCarKuang(String carKuang) {
		this.carKuang = carKuang;
	}
	
	public Double getCarMoney() {
		return carMoney;
	}

	public void setCarMoney(Double carMoney) {
		this.carMoney = carMoney;
	}
	
	@Length(min=0, max=255, message="车型编号长度必须介于 0 和 255 之间")
	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	@Length(min=0, max=255, message="车型长度必须介于 0 和 255 之间")
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	@Length(min=0, max=255, message="车系长度必须介于 0 和 255 之间")
	public String getCarXi() {
		return carXi;
	}

	public void setCarXi(String carXi) {
		this.carXi = carXi;
	}
	
	@Length(min=0, max=255, message="车辆颜色长度必须介于 0 和 255 之间")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Length(min=0, max=255, message="配置长度必须介于 0 和 255 之间")
	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	@Length(min=0, max=255, message="制造商长度必须介于 0 和 255 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Length(min=0, max=255, message="登记情况长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	@Length(min=0, max=255, message="排量长度必须介于 0 和 255 之间")
	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	
	@Length(min=0, max=255, message="发动机型号长度必须介于 0 和 255 之间")
	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	@Length(min=0, max=255, message="车架号长度必须介于 0 和 255 之间")
	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMadeDate() {
		return madeDate;
	}

	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
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
	
	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
	}
	
	@Length(min=0, max=255, message="车辆产地长度必须介于 0 和 255 之间")
	public String getProducing() {
		return producing;
	}

	public void setProducing(String producing) {
		this.producing = producing;
	}
	
	public Integer getSeating() {
		return seating;
	}

	public void setSeating(Integer seating) {
		this.seating = seating;
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
	
	public Integer getBeginAccident() {
		return beginAccident;
	}

	public void setBeginAccident(Integer beginAccident) {
		this.beginAccident = beginAccident;
	}
	
	public Integer getEndAccident() {
		return endAccident;
	}

	public void setEndAccident(Integer endAccident) {
		this.endAccident = endAccident;
	}
		
	public Double getBeginCarMoney() {
		return beginCarMoney;
	}

	public void setBeginCarMoney(Double beginCarMoney) {
		this.beginCarMoney = beginCarMoney;
	}
	
	public Double getEndCarMoney() {
		return endCarMoney;
	}

	public void setEndCarMoney(Double endCarMoney) {
		this.endCarMoney = endCarMoney;
	}
		
	public Date getBeginMadeDate() {
		return beginMadeDate;
	}

	public void setBeginMadeDate(Date beginMadeDate) {
		this.beginMadeDate = beginMadeDate;
	}
	
	public Date getEndMadeDate() {
		return endMadeDate;
	}

	public void setEndMadeDate(Date endMadeDate) {
		this.endMadeDate = endMadeDate;
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