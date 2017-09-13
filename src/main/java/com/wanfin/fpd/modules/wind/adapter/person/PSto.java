/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;
import com.wanfin.fpd.modules.wind.adapter.corporations.CCorp;

/**
 * 用户持股信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class PSto implements IAdapter<PSto>{
	private enum CEnum{
		//id, personid, corpid, corpBase, stockcount, percent; 
		id("id", "主键"),
		personid("personid", "个人主键"),
		corpid("corpid", "持有股票公司"),
		
		corpBase("corpBase", "持有股票公司信息"),
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
	private String personid;		// 个人主键
	private String corpid;		// 持有股票公司
	private CCorp corpBase; //持有股票公司信息
	private String stockcount;		// 持股数量
	private String percent;		// 持股比率
	private List<CMap> cmaps;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public CCorp getCorpBase() {
		return corpBase;
	}
	public void setCorpBase(CCorp corpBase) {
		this.corpBase = corpBase;
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
	public List<PSto> init(JSONArray jarray) throws JSONException {
		List<PSto> entitys = new ArrayList<PSto>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
		}
		return entitys;
	}
	
	@Override
	public PSto init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PSto init(JSONObject jo) throws JSONException {
		PSto entity = new PSto();

		if(StringUtils.isNotEmpty(jo.optString(CEnum.corpBase.getKey()))){
			entity.setCorpBase(new CCorp().init(jo.optString(CEnum.corpBase.getKey())));
		}
		entity.setStockcount(jo.optString(CEnum.stockcount.getKey()));
		entity.setPercent(jo.optString(CEnum.percent.getKey()));

		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
//		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(PSto entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.corpBase.getKey(), entity.getCorpBase(), CEnum.corpBase.getRemark()));
		cmaps.add(new CMap(CEnum.stockcount.getKey(), entity.getStockcount(), CEnum.stockcount.getRemark()));
		cmaps.add(new CMap(CEnum.percent.getKey(), entity.getPercent(), CEnum.percent.getRemark()));
	
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}