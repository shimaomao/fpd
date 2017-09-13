package com.wanfin.fpd.modules.wind.adapter;



public class IAdapterDb {
	private IeAdapterDB db;
	private String ip;
	private String token;
	private String tokenKey;
	private String tokenVal;
	private String api;
	private String params;
	public IAdapterDb() {
		super();
	}
	public IAdapterDb(IeAdapterDB db, String ip, String token) {
		super();
		this.db = db;
		this.ip = ip;
		this.token = token;
	}

	public IAdapterDb(IeAdapterDB db, String ip, String tokenKey, String tokenVal) {
		super();
		this.db = db;
		this.ip = ip;
		this.tokenKey = tokenKey;
		this.tokenVal = tokenVal;
	}
	public IeAdapterDB getDb() {
		return db;
	}
	public void setDb(IeAdapterDB db) {
		this.db = db;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getTokenKey() {
		return tokenKey;
	}
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	public String getTokenVal() {
		return tokenVal;
	}
	public void setTokenVal(String tokenVal) {
		this.tokenVal = tokenVal;
	}
	public String getUrl() {
		return getIp()+"/"+getApi();
	}
	public String getTokenAndParams() {
		return getTokenStr()+getParams();
	}
	public String getToken(Boolean isJson) {
		if(isJson){
			return getTokenJson();
		}
		return getTokenStr();
	}
	public String getTokenStr() {
		return getTokenKey()+"="+getTokenVal();
	}
	public String getTokenJson() {
		return "\""+getTokenKey()+"\":\""+getTokenVal()+"\"";
	}
}