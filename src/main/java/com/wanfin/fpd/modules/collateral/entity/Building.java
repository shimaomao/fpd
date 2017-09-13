/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 商铺写字楼Entity
 * @author srf
 * @version 2016-03-24
 */
public class Building extends DataEntity<Building> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 房地产地点
	private Double area;		// 建筑面积
	private Double balance;		// 按揭余额
	private String chanMan;		// 产权人
	private String chanQuality;		// 产权性质
	private String dengji;		// 登记情况
	private String framework;		// 建筑结构
	private String gao;		// 层高
	private String gongMan;		// 共有人
	private Double guMoney;		// 租赁模型估算 万元
	private String jiegou;		// 户型结构
	private Date jyear;		// 建成年份
	private Double moMoney;		// 模型估价
	private Double money1;		// 同等房产单位单价
	private Double newMoney;		// 新房单价
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private String yangshi;		// 建筑样式
	private Integer yearLimit;		// 剩余年限
	private Double zumoney1;		// 同等房产租金
	private String dizhiContractId;		// 抵质押信息id（外键）
	
	public Building() {
		super();
	}

	public Building(String id){
		super(id);
	}

	@Length(min=0, max=255, message="房地产地点长度必须介于 0 和 255 之间")
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
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=255, message="产权人长度必须介于 0 和 255 之间")
	public String getChanMan() {
		return chanMan;
	}

	public void setChanMan(String chanMan) {
		this.chanMan = chanMan;
	}
	
	@Length(min=0, max=255, message="产权性质长度必须介于 0 和 255 之间")
	public String getChanQuality() {
		return chanQuality;
	}

	public void setChanQuality(String chanQuality) {
		this.chanQuality = chanQuality;
	}
	
	@Length(min=0, max=255, message="登记情况长度必须介于 0 和 255 之间")
	public String getDengji() {
		return dengji;
	}

	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	
	@Length(min=0, max=255, message="建筑结构长度必须介于 0 和 255 之间")
	public String getFramework() {
		return framework;
	}

	public void setFramework(String framework) {
		this.framework = framework;
	}
	
	@Length(min=0, max=255, message="层高长度必须介于 0 和 255 之间")
	public String getGao() {
		return gao;
	}

	public void setGao(String gao) {
		this.gao = gao;
	}
	
	@Length(min=0, max=255, message="共有人长度必须介于 0 和 255 之间")
	public String getGongMan() {
		return gongMan;
	}

	public void setGongMan(String gongMan) {
		this.gongMan = gongMan;
	}
	
	public Double getGuMoney() {
		return guMoney;
	}

	public void setGuMoney(Double guMoney) {
		this.guMoney = guMoney;
	}
	
	@Length(min=0, max=255, message="户型结构长度必须介于 0 和 255 之间")
	public String getJiegou() {
		return jiegou;
	}

	public void setJiegou(String jiegou) {
		this.jiegou = jiegou;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJyear() {
		return jyear;
	}

	public void setJyear(Date jyear) {
		this.jyear = jyear;
	}
	
	public Double getMoMoney() {
		return moMoney;
	}

	public void setMoMoney(Double moMoney) {
		this.moMoney = moMoney;
	}
	
	public Double getMoney1() {
		return money1;
	}

	public void setMoney1(Double money1) {
		this.money1 = money1;
	}
	
	public Double getNewMoney() {
		return newMoney;
	}

	public void setNewMoney(Double newMoney) {
		this.newMoney = newMoney;
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
	
	@Length(min=0, max=255, message="建筑样式长度必须介于 0 和 255 之间")
	public String getYangshi() {
		return yangshi;
	}

	public void setYangshi(String yangshi) {
		this.yangshi = yangshi;
	}
	
	public Integer getYearLimit() {
		return yearLimit;
	}

	public void setYearLimit(Integer yearLimit) {
		this.yearLimit = yearLimit;
	}
	
	public Double getZumoney1() {
		return zumoney1;
	}

	public void setZumoney1(Double zumoney1) {
		this.zumoney1 = zumoney1;
	}
	
	@Length(min=0, max=64, message="抵质押信息id（外键）长度必须介于 0 和 64 之间")
	public String getDizhiContractId() {
		return dizhiContractId;
	}

	public void setDizhiContractId(String dizhiContractId) {
		this.dizhiContractId = dizhiContractId;
	}
	
}