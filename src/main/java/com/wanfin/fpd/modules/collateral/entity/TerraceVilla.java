/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 联排别墅Entity
 * @author srf
 * @version 2016-03-24
 */
public class TerraceVilla extends DataEntity<TerraceVilla> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址
	private Double area;		// 面积
	private Double balance;		// 按揭余额
	private String chanQuality;		// 产权性质
	private String dengji;		// 登记情况
	private String framework;		// 建筑结构
	private String jiegou;		// 户型结构
	private Date jyear;		// 建成年份
	private String man;		// 共有人
	private Double moMoney;		// 模型估价
	private Double money1;		// 同等房产单位单价1
	private Double money2;		// 同等房产单位单价2
	private Double money3;		// 同等房产单位单价3
	private Double newMoney;		// 新房单价
	private String quanNum;		// 权证编号
	private String remark;		// 地段描述
	private String yangshi;		// 建筑样式
	private Integer yearLimit;		// 剩余年限
	private String dizhiContractId;		// 关联外键抵质押信息
	private Double beginArea;		// 开始 面积
	private Double endArea;		// 结束 面积
	private Double beginBalance;		// 开始 按揭余额
	private Double endBalance;		// 结束 按揭余额
	private Date beginJyear;		// 开始 建成年份
	private Date endJyear;		// 结束 建成年份
	private Integer beginYearLimit;		// 开始 剩余年限
	private Integer endYearLimit;		// 结束 剩余年限
	
	public TerraceVilla() {
		super();
	}

	public TerraceVilla(String id){
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
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
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
	
	@Length(min=0, max=255, message="共有人长度必须介于 0 和 255 之间")
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
	
	public Double getMoney1() {
		return money1;
	}

	public void setMoney1(Double money1) {
		this.money1 = money1;
	}
	
	public Double getMoney2() {
		return money2;
	}

	public void setMoney2(Double money2) {
		this.money2 = money2;
	}
	
	public Double getMoney3() {
		return money3;
	}

	public void setMoney3(Double money3) {
		this.money3 = money3;
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
		
	public Double getBeginBalance() {
		return beginBalance;
	}

	public void setBeginBalance(Double beginBalance) {
		this.beginBalance = beginBalance;
	}
	
	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}
		
	public Date getBeginJyear() {
		return beginJyear;
	}

	public void setBeginJyear(Date beginJyear) {
		this.beginJyear = beginJyear;
	}
	
	public Date getEndJyear() {
		return endJyear;
	}

	public void setEndJyear(Date endJyear) {
		this.endJyear = endJyear;
	}
		
	public Integer getBeginYearLimit() {
		return beginYearLimit;
	}

	public void setBeginYearLimit(Integer beginYearLimit) {
		this.beginYearLimit = beginYearLimit;
	}
	
	public Integer getEndYearLimit() {
		return endYearLimit;
	}

	public void setEndYearLimit(Integer endYearLimit) {
		this.endYearLimit = endYearLimit;
	}
		
}