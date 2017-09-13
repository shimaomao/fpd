/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerrelevan.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 客户关联Entity
 * @author lx
 * @version 2016-08-12
 */
public class TCustomerRelevan extends DataEntity<TCustomerRelevan> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 企业名称
	private String zzjgCard;		// 组织机构代码证
	private String yyzzCard;		// 营业执照
	private String relevan;		// 关系
	private String linkMan;		// 联系人
	private String cardId;		// 身份证号码
	private String phone;		// 联系电话
	private Date registerDate;		// 注册时间
	private String registerCapital;		// 注册资本
	private String address;		// 经营地址
	private String companyId;		// 企业关联id
	private String employeeId;		// 个人关联id
	
	public TCustomerRelevan() {
		super();
	}

	public TCustomerRelevan(String id){
		super(id);
	}

	@Length(min=0, max=255, message="企业名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="组织机构代码证长度必须介于 0 和 255 之间")
	public String getZzjgCard() {
		return zzjgCard;
	}

	public void setZzjgCard(String zzjgCard) {
		this.zzjgCard = zzjgCard;
	}
	
	@Length(min=0, max=255, message="营业执照长度必须介于 0 和 255 之间")
	public String getYyzzCard() {
		return yyzzCard;
	}

	public void setYyzzCard(String yyzzCard) {
		this.yyzzCard = yyzzCard;
	}
	
	@Length(min=0, max=255, message="关系长度必须介于 0 和 255 之间")
	public String getRelevan() {
		return relevan;
	}

	public void setRelevan(String relevan) {
		this.relevan = relevan;
	}
	
	@Length(min=0, max=255, message="联系人长度必须介于 0 和 255 之间")
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	@Length(min=0, max=255, message="身份证号码长度必须介于 0 和 255 之间")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="注册时间不能为空")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Length(min=0, max=255, message="注册资本长度必须介于 0 和 255 之间")
	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	@Length(min=0, max=255, message="经营地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
}