/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import com.wanfin.fpd.modules.sys.entity.User;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 租户初始化数据Entity
 * @author Chenh
 * @version 2016-08-31
 */
public class SysUserData extends DataEntity<SysUserData> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 数据项
	private String status;		// 状态
	private String relid;		// 关联ID
	private User user;		// 用户
	private String organId;		// 租户ID
	
	public SysUserData() {
		super();
	}

	public SysUserData(String id){
		super(id);
	}

	@Length(min=0, max=64, message="数据项长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="关联ID长度必须介于 0 和 64 之间")
	public String getRelid() {
		return relid;
	}

	public void setRelid(String relid) {
		this.relid = relid;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}