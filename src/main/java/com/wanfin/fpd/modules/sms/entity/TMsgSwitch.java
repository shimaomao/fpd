/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 消息管理Entity
 * @author kewenxiu
 * @version 2017-03-24
 */
public class TMsgSwitch extends DataEntity<TMsgSwitch> {
	
	private static final long serialVersionUID = 1L;
	private String businessName;		// 开关业务节点名称
	private String letterStatus;		// 站内信开关状态（0，关  1，开）
	private String businessType;		// 开关业务节点类型
	private String marketStatus;		// 营销开关状态（0，关  1，开）
	private String mailStatus;		// 邮件开关状态（0，关  1，开）
	
	public TMsgSwitch() {
		super();
	}

	public TMsgSwitch(String id){
		super(id);
	}

	@Length(min=0, max=255, message="开关业务节点名称长度必须介于 0 和 255 之间")
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@Length(min=0, max=5, message="站内信开关状态（0，关  1，开）长度必须介于 0 和 5 之间")
	public String getLetterStatus() {
		return letterStatus;
	}

	public void setLetterStatus(String letterStatus) {
		this.letterStatus = letterStatus;
	}
	
	@Length(min=0, max=255, message="开关业务节点类型长度必须介于 0 和 255 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Length(min=0, max=5, message="营销开关状态（0，关  1，开）长度必须介于 0 和 5 之间")
	public String getMarketStatus() {
		return marketStatus;
	}

	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}
	
	@Length(min=0, max=5, message="邮件开关状态（0，关  1，开）长度必须介于 0 和 5 之间")
	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	
}