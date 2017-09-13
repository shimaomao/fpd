package com.wanfin.fpd.modules.wind.adapter;

import java.util.ArrayList;
import java.util.List;

public enum IeAdapterDB{
	DB_WZ("wz", "万众金融"),
	DB_KL("kl", "考拉征信"),
	DB_PY("py", "鹏元征信"),
	DB_QH("qh", "前海征信");
	
	private String key;//数据源标识
	private String name;//数据源名称
	private IeAdapterDB(String key, String name) {
		this.key = key;
		this.name = name;
	}

	/**
	 * 获取数据源Key
	 * @param db
	 * @return
	 */
	public static List<String> getDbKeys() {
		List<String> dbs = new ArrayList<String>();
		IeAdapterDB[] creditDBs = IeAdapterDB.values();
		for (IeAdapterDB ieAdapterDB : creditDBs) {
			dbs.add(ieAdapterDB.getKey());
		}
		return dbs;
	}

	/**
	 * 根据Key获取数据源
	 * @param db
	 * @return
	 */
	public static IeAdapterDB getIeAdapterDBByKey(String key) {
		IeAdapterDB[] creditDBs = IeAdapterDB.values();
		for (IeAdapterDB ieAdapterDB : creditDBs) {
			if((ieAdapterDB.getKey()).equals(key)){
				return ieAdapterDB;
			}
		}
		return null;
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
}
