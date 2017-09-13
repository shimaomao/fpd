/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * smsEntity
 * @author sms
 * @version 2016-07-25
 */
public class TSmsRecord extends DataEntity<TSmsRecord> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 短信内容
	private Date date;		// 发送时间
	private String mobile;		// 发送号码
	private String type;		// 短信分类  1还款提醒   2催收提醒
	private String returnStr;		// 返回值
	private String organId;		// 租户编号
	
	public TSmsRecord() {
		super();
	}

	public TSmsRecord(String id){
		super(id);
	}

	@Length(min=0, max=255, message="短信内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Length(min=0, max=255, message="发送号码长度必须介于 0 和 255 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=10, message="短信分类长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="返回值长度必须介于 0 和 255 之间")
	public String getReturnStr() {
		return returnStr;
	}

	public void setReturnStr(String returnStr) {
		this.returnStr = returnStr;
	}
	
	@Length(min=0, max=64, message="租户编号长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}