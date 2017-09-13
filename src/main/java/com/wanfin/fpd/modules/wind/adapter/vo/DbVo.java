package com.wanfin.fpd.modules.wind.adapter.vo;

import java.util.List;

import com.wanfin.fpd.modules.wind.adapter.IAdapterDb;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;

public class DbVo {
	private String key;
	private String name;
	private Boolean status;
	private List<DbGroupVo> groups;
	
	public DbVo() {
		super();
	}
	public DbVo(IAdapterDb db) {
		super();
		this.key = db.getDb().getKey();
		this.name = db.getDb().getName();
	}
	public DbVo(IAdapterDb db, List<DbGroupVo> groups) {
		super();
		this.key = db.getDb().getKey();
		this.name = db.getDb().getName();
		this.groups = groups;
	}
	public DbVo(IeAdapterDB db) {
		super();
		this.key = db.getKey();
		this.name = db.getName();
	}

	public DbVo(IeAdapterDB db, List<DbGroupVo> groups) {
		super();
		this.key = db.getKey();
		this.name = db.getName();
		this.groups = groups;
	}
	
	public DbVo(String key, String name) {
		super();
		this.key = key;
		this.name = name;
	}
	public DbVo(String key, String name, List<DbGroupVo> groups) {
		super();
		this.key = key;
		this.name = name;
		this.groups = groups;
	}
	public DbVo(String key, String name, Boolean status, List<DbGroupVo> groups) {
		super();
		this.key = key;
		this.name = name;
		this.status = status;
		this.groups = groups;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DbGroupVo> getGroups() {
		return groups;
	}
	public void setGroups(List<DbGroupVo> groups) {
		this.groups = groups;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
