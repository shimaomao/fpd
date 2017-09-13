/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.company.entity;

import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 客户（包含企业客户和个人客户 ，不用与保存数据）
 * @author zzm
 * @version 2016-03-14
 */
public class Customer extends ActEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String customerType;		//客户类型  1：企业客户，2：个人客户
	private String phone;		// 电话
	private String cardNum;		// 证件号码
	private String cardType;		// 证件号码
	private String gatheringBank;		// 开户行
	private String gatheringName;		// 开户名
	private String gatheringNumber;		// 开户账户
	private String email;		// 邮件
	private String status;		// 黑名状态  black:黑名，black_audit:黑名审核中
	private String reason;		// 黑名原因说明
	private TProduct product;
	
	public Customer() {
		super();
	}

	public Customer(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public String getGatheringBank() {
		return gatheringBank;
	}

	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}

	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}

	public String getGatheringNumber() {
		return gatheringNumber;
	}

	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}

	
}