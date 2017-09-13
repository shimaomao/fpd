/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 租户Entity
 * @author Chenh
 * @version 2016-09-08
 */
public class TTenant extends DataEntity<TTenant> {
	
	private static final long serialVersionUID = 1L;
	private String adminName;		// 默认管理员
	private String description;		// 描述
	private String tenantName;		// 名称
	private Long state;		// 状态
	private String bsys;		//系统数据
	private String type;		// 类型
	private String logoPath;		// logo_path
	private String simpleName;		// 简称
	private String mark;		// 备注信息
	
	public TTenant() {
		super();
	}

	public TTenant(String id){
		super(id);
	}

	@Length(min=0, max=255, message="默认管理员长度必须介于 0 和 255 之间")
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
	
	@Length(min=0, max=2, message="类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="logo_path长度必须介于 0 和 255 之间")
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	@Length(min=0, max=64, message="简称长度必须介于 0 和 64 之间")
	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	
	@Length(min=0, max=255, message="备注信息长度必须介于 0 和 255 之间")
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getBsys() {
		return bsys;
	}

	public void setBsys(String bsys) {
		this.bsys = bsys;
	}
}