/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 从业人员工作经历Entity
 * @author kewenxiu
 * @version 2017-03-13
 */
public class Experience extends DataEntity<Experience> {
	
	private static final long serialVersionUID = 1L;
	private String position;		// 职位
	private String unit;		// 工作单位
	private String workDate;		// 时间
	private String employId;		// 关联人员
	private String signDate;		// 报备日期
	private String workEndDate;		// 时间止
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	
	public Experience() {
		super();
	}

	public Experience(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="职位长度必须介于 0 和 255 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=255, message="工作单位长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=255, message="时间长度必须介于 0 和 255 之间")
	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
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
	
	@Length(min=0, max=255, message="时间止长度必须介于 0 和 255 之间")
	public String getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
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