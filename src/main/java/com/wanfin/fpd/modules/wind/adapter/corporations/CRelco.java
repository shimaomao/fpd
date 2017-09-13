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
 * 公司重要关联公司信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CRelco implements IAdapter<CRelco>{
	private enum CEnum{
//		id, corpid, relacorpid, relacorpBase, relationtype; 
			
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		relacorpid("relacorpid", "关联公司主键"),
			
		relacorpBase("relacorpBase", "关联公司信息"),
		relationtype("relationtype", "关系类型"); 
		
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
	
	private String id;		// ID
	private String corpid;		// 公司主键
	private String relacorpid;		// 关联公司ID
	private CCorp relacorpBase; //关联公司信息
	private String relationtype;		// 关系类型
	private List<CMap> cmaps;
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRelacorpid() {
		return relacorpid;
	}
	public void setRelacorpid(String relacorpid) {
		this.relacorpid = relacorpid;
	}
	public CCorp getRelacorpBase() {
		return relacorpBase;
	}
	public void setRelacorpBase(CCorp relacorpBase) {
		this.relacorpBase = relacorpBase;
	}
	public String getRelationtype() {
		return relationtype;
	}
	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	@Override
	public List<CRelco> init(JSONArray jarray) throws JSONException {
		List<CRelco> entitys = new ArrayList<CRelco>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public CRelco init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CRelco init(JSONObject jo) throws JSONException {
		CRelco entity = new CRelco();
		entity.setRelacorpBase(new CCorp().init(jo.optString(CEnum.relacorpBase.getKey())));
		entity.setRelacorpid(jo.optString(CEnum.relacorpid.getKey()));
		entity.setRelationtype(jo.optString(CEnum.relationtype.getKey()));

		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}
	
	private List<CMap> initCmaps(CRelco entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.relacorpBase.getKey(), entity.getRelacorpBase(), CEnum.relacorpBase.getRemark()));
		cmaps.add(new CMap(CEnum.relacorpid.getKey(), entity.getRelacorpid(), CEnum.relacorpid.getRemark()));
		cmaps.add(new CMap(CEnum.relationtype.getKey(), entity.getRelationtype(), CEnum.relationtype.getRemark()));
		
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}