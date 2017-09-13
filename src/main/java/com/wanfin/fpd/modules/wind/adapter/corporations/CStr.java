/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.corporations;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 公司企业控股信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CStr implements IAdapter<CStr>{
	private enum CEnum{
//		id, corpid, stockcorpid, stockcorpBase, stockcount, percent; 
		
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		stockcorpid("stockcorpid", "控股企业主键"),
			
		stockcorpBase("stockcorpBase", "控股企业信息"),
		stockcount("stockcount", "持股数量"),
		percent("percent", "持股比率"); 
		
		private String key;
		private String remark;
		private CEnum(String key, String remark) {
			this.key = key;
			this.remark = remark;
		}
		public String getKey() {
			return key;
		}
		public String getRemark() {
			return remark;
		}
	}
	
	private String id;
	private String corpid;		// 公司主键
	private String stockcorpid;		// 控股企业id
	private CCorp stockcorpBase; //控股企业信息
	private String stockcount;		// 持股数量
	private String percent;		// 持股比率
	private List<CMap> cmaps;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getStockcorpid() {
		return stockcorpid;
	}
	public void setStockcorpid(String stockcorpid) {
		this.stockcorpid = stockcorpid;
	}
	public CCorp getStockcorpBase() {
		return stockcorpBase;
	}
	public void setStockcorpBase(CCorp stockcorpBase) {
		this.stockcorpBase = stockcorpBase;
	}
	public String getStockcount() {
		return stockcount;
	}
	public void setStockcount(String stockcount) {
		this.stockcount = stockcount;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	@Override
	public List<CStr> init(JSONArray jarray) throws JSONException {
		List<CStr> entitys = new ArrayList<CStr>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public CStr init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CStr init(JSONObject jo) throws JSONException {
		CStr entity = new CStr();
		entity.setStockcorpBase(new CCorp().init(jo.optString(CEnum.stockcorpBase.getKey())));
		entity.setStockcorpid(jo.optString(CEnum.stockcorpid.getKey()));
		entity.setStockcount(jo.optString(CEnum.stockcount.getKey()));
		entity.setPercent(jo.optString(CEnum.percent.getKey()));

		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}
	
	private List<CMap> initCmaps(CStr entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.stockcorpBase.getKey(), entity.getStockcorpBase(), CEnum.stockcorpBase.getRemark()));
		cmaps.add(new CMap(CEnum.stockcorpid.getKey(), entity.getStockcorpid(), CEnum.stockcorpid.getRemark()));
		cmaps.add(new CMap(CEnum.stockcount.getKey(), entity.getStockcount(), CEnum.stockcount.getRemark()));
		
		cmaps.add(new CMap(CEnum.percent.getKey(), entity.getPercent(), CEnum.percent.getRemark()));
		
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}