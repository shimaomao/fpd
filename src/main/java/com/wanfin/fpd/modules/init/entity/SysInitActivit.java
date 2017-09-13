/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 流程初始化Entity
 * @author zxj
 * @version 2016-09-22
 */
public class SysInitActivit extends DataEntity<SysInitActivit> {
	
	private static final long serialVersionUID = 1L;
	private String officeName;		// 公司
	private String name;		// 流程名
	private String fileName;		// 流程文件名
	private String organId;		// 租户ID
	private String officeType; //公司类型
	
	public SysInitActivit() {
		super();
	}

	public SysInitActivit(String id){
		super(id);
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=1, max=64, message="流程名长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="流程文件名长度必须介于 1 和 64 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Length(min=1, max=64, message="租户ID长度必须介于 1 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	
}