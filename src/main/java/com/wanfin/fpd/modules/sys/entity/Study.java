/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 学习经历Entity
 * @author kewenxiu
 * @version 2017-03-10
 */
public class Study extends DataEntity<Study> {
	
	private static final long serialVersionUID = 1L;
	private String job;		// 学历
	private String professional;		// 专业
	private String school;		// 学校
	private String studyDate;		// 学习时间起
	private String employId;		// 关联人员
	private String signDate;		// 报备日期
	private String studyEndDate;		// 学习时间止
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	private String nifId;		// nif_id
	
	public Study() {
		super();
	}

	public Study(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="职务长度必须介于 0 和 255 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Length(min=0, max=255, message="专业长度必须介于 0 和 255 之间")
	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}
	
	@Length(min=0, max=255, message="学校长度必须介于 0 和 255 之间")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	@Length(min=0, max=255, message="学习时间起长度必须介于 0 和 255 之间")
	public String getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(String studyDate) {
		this.studyDate = studyDate;
	}
	
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
	
	@Length(min=0, max=255, message="学习时间止长度必须介于 0 和 255 之间")
	public String getStudyEndDate() {
		return studyEndDate;
	}

	public void setStudyEndDate(String studyEndDate) {
		this.studyEndDate = studyEndDate;
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
	
	@Length(min=0, max=255, message="nif_id长度必须介于 0 和 255 之间")
	public String getNifId() {
		return nifId;
	}

	public void setNifId(String nifId) {
		this.nifId = nifId;
	}
	
}