package com.wanfin.fpd.modules.wind.adapter.vo;

import java.util.List;

import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;

public class DbGroupVo {
	private String pkey;
	private String key;
	private String name;
	private Boolean status;
	private List<DbItemVo> items;

	public DbGroupVo() {
		super();
	}
	
	public DbGroupVo(IeAdapterType adapterType) {
		super();
		this.key = adapterType.getKey();
		this.name = adapterType.getName();
		this.pkey = adapterType.getDb().getKey();
	}

	public DbGroupVo(IeAdapterType adapterType, List<DbItemVo> items) {
		super();
		this.key = adapterType.getKey();
		this.name = adapterType.getName();
		this.pkey = adapterType.getDb().getKey();
		this.items = items;
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

	public List<DbItemVo> getItems() {
		return items;
	}

	public void setItems(List<DbItemVo> items) {
		this.items = items;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
}
