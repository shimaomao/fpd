package com.wanfin.fpd.modules.wind.adapter;

import java.util.ArrayList;
import java.util.List;

public enum IeAdapterType{
	WZ_PERSON(IeAdapterDB.DB_WZ, WindHttp.WT_PERSON, "个人", "person/queryPerson"),
	WZ_CORPORATION(IeAdapterDB.DB_WZ, WindHttp.WT_CORPORATION, "机构", "corporations/queryCorporation"),

	KL_PERSON(IeAdapterDB.DB_KL, WindHttp.WT_PERSON, "个人", ""),
	KL_CORPORATION(IeAdapterDB.DB_KL, WindHttp.WT_CORPORATION, "机构", ""),
	
	QH_PERSON(IeAdapterDB.DB_QH, WindHttp.WT_PERSON, "个人", "query/v1/"),
	QH_CORPORATION(IeAdapterDB.DB_QH, WindHttp.WT_CORPORATION, "机构", "query/v1/"),
	
	PY_PERSON(IeAdapterDB.DB_PY, WindHttp.WT_PERSON, "个人", "pycredit/query"),
	PY_CORPORATION(IeAdapterDB.DB_PY, WindHttp.WT_CORPORATION, "机构", "pycredit/query");

	private IeAdapterDB db;//数据源
	private String key;//标识
	private String name;//名称
	private String api;//
	private IeAdapterType(IeAdapterDB db, String key, String name, String api) {
		this.db = db;
		this.key = key;
		this.name = name;
		this.api = api;
	}
	
	/**
	 * 根据数据源获取Key
	 * @param db
	 * @return
	 */
	public static List<String> getKeysByDb(IeAdapterDB db) {
		List<String> types = new ArrayList<String>();
		IeAdapterType[] ieAdapterTypes = IeAdapterType.values();
		for (IeAdapterType ieAdapterType : ieAdapterTypes) {
			if((db).equals(ieAdapterType.getDb())){
				types.add(ieAdapterType.getKey());
			}
		}
		return types;
	}
	
	/**
	 * 根据数据源获取IeAdapterType
	 * @param db
	 * @return
	 */
	public static List<IeAdapterType> getIeAdapterTypeByKey(String key) {
		List<IeAdapterType> types = new ArrayList<IeAdapterType>();
		IeAdapterType[] ieAdapterTypes = IeAdapterType.values();
		for (IeAdapterType ieAdapterType : ieAdapterTypes) {
			if((key).equals(ieAdapterType.getKey())){
				types.add(ieAdapterType);
			}
		}
		return types;
	}
	
	/**
	 * 根据数据源获取类型
	 * @param db
	 * @return
	 */
	public static List<IeAdapterType> getIeAdapterTypeByDb(IeAdapterDB db) {
		List<IeAdapterType> types = new ArrayList<IeAdapterType>();
		IeAdapterType[] ieAdapterTypes = IeAdapterType.values();
		for (IeAdapterType ieAdapterType : ieAdapterTypes) {
			if((db).equals(ieAdapterType.getDb())){
				types.add(ieAdapterType);
			}
		}
		return types;
	}
	
	/**
	 * 根据DB和Key获取数据类型枚举
	 * @param db
	 * @return
	 */
	public static IeAdapterType getIeAdapterTypeByDBKey(IeAdapterDB db, String key) {
		IeAdapterType[] ieAdapterTypes = IeAdapterType.values();
		for (IeAdapterType ieAdapterType : ieAdapterTypes) {
			if((ieAdapterType.getDb()).equals(db) && (ieAdapterType.getKey()).equals(key)){
				return ieAdapterType;
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

	public IeAdapterDB getDb() {
		return db;
	}

	public void setDb(IeAdapterDB db) {
		this.db = db;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
}
