/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 从业人员家庭成员Entity
 * @author kewenxiu
 * @version 2017-03-13
 */
public class Family extends DataEntity<Family> {
	
	private static final long serialVersionUID = 1L;
	private String job;		// 职务
	private String name;		// 姓名
	private String sex;		// 性别
	private String type;		// 成员类别
	private String unit;		// 现工作或学习单位
	private String employId;		// 关联人员
	private String signDate;		// 报备日期
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	
	public Family() {
		super();
	}

	public Family(String id){
		super(id);
	}

	@Length(min=0, max=255, message="职务长度必须介于 0 和 255 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="性别长度必须介于 0 和 255 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="成员类别长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="现工作或学习单位长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="关联人员长度必须介于 0 和 64 之间")
	public String getEmployId() {
		return employId;
	}

	public void setEmployId(String employId) {
		this.employId = employId;
	}
	
	@Length(min=0, max=255, message="报备日期长度必须介于 0 和 255 之间")
	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=255, message="source_flag长度必须介于 0 和 255 之间")
	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
	
	@Length(min=0, max=255, message="source_status长度必须介于 0 和 255 之间")
	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	
}