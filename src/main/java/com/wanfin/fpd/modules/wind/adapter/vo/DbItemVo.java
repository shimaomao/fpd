package com.wanfin.fpd.modules.wind.adapter.vo;

import java.util.List;

import com.wanfin.fpd.modules.wind.adapter.IAdapterVo;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;


public class DbItemVo {
	private String db;
	private String pkey;
	private String key;
	private String name;
	private Boolean status;
	private IAdapterVo vo;
	private List<IAdapterVo> vos;
	
	public DbItemVo() {
		super();
	}
	
	public DbItemVo(IeAdapter adapter) {
		super();
		this.key = adapter.getSub();
		this.name = adapter.getRemark();
	}

	public DbItemVo(IeAdapter adapter, IAdapterVo vo) {
		super();
		this.pkey = adapter.getSub();
		this.name = adapter.getRemark();
		this.vo = vo;
	}
	
	public DbItemVo(IeAdapter adapter, List<IAdapterVo> vos) {
		super();
		this.pkey = adapter.getType().getKey();
		this.pkey = adapter.getType().getDb().getKey();
		this.key = adapter.getSub();
		this.name = adapter.getRemark();
		this.vos = vos;
	}
	
	public DbItemVo(String key, String name, Boolean status) {
		super();
		this.key = key;
		this.name = name;
		this.status = status;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public IAdapterVo getVo() {
		return vo;
	}

	public void setVo(IAdapterVo vo) {
		this.vo = vo;
	}

	public List<IAdapterVo> getVos() {
		return vos;
	}

	public void setVos(List<IAdapterVo> vos) {
		this.vos = vos;
	}
}
