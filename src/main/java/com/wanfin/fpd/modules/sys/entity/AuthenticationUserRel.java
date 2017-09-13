package com.wanfin.fpd.modules.sys.entity;

import com.wanfin.fpd.common.persistence.DataEntity;


/**
 * 用户认证权限Entity
 * @author lx
 * @version 2016-10-24
 */
public class AuthenticationUserRel extends DataEntity<AuthenticationUserRel>{

	private static final long serialVersionUID = 1L;
	
	private String anuthenuserId;//'主用户认证表id',
	private String wuserId;//W平台用户id',
	private String buserId;//B端用户id',
	private String fuserId;//风控用户id',
	
	public AuthenticationUserRel() {
		super();
	}

	public AuthenticationUserRel(String id) {
		super();
		this.id = id;
	}

	public String getAnuthenuserId() {
		return anuthenuserId;
	}

	public void setAnuthenuserId(String anuthenuserId) {
		this.anuthenuserId = anuthenuserId;
	}

	public String getWuserId() {
		return wuserId;
	}

	public void setWuserId(String wuserId) {
		this.wuserId = wuserId;
	}

	public String getBuserId() {
		return buserId;
	}

	public void setBuserId(String buserId) {
		this.buserId = buserId;
	}

	public String getFuserId() {
		return fuserId;
	}

	public void setFuserId(String fuserId) {
		this.fuserId = fuserId;
	}

}