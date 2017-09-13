/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 关联用户Entity
 * @author srf
 * @version 2016-05-16
 */
public class PRelPe implements IAdapter<PRelPe>{
	private enum CEnum{
//		id, personid, relapersonid, relaPersionBase, relationtype; 
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		
		relapersonid("relapersonid", "关联的个人"),
		relaPersionBase("relaPersionBase", "个人信息"),
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

	private String id;
	private String personid;		// 个人主键
	private String relapersonid;		// 关联的个人
	private PPersion relaPersionBase; //个人信息
	private String relationtype;		// 关系类型
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
	public String getRelapersonid() {
		return relapersonid;
	}
	public void setRelapersonid(String relapersonid) {
		this.relapersonid = relapersonid;
	}
	public PPersion getRelaPersionBase() {
		return relaPersionBase;
	}
	public void setRelaPersionBase(PPersion relaPersionBase) {
		this.relaPersionBase = relaPersionBase;
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
	public List<PRelPe> init(JSONArray jarray) throws JSONException {
		List<PRelPe> entitys = new ArrayList<PRelPe>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
		}
		return entitys;
	}
	
	@Override
	public PRelPe init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PRelPe init(JSONObject jo) throws JSONException {
		PRelPe entity = new PRelPe();
		entity.setRelaPersionBase(new PPersion().init(jo.optString(CEnum.relaPersionBase.getKey())));
		entity.setRelapersonid(jo.optString(CEnum.relapersonid.getKey()));
		entity.setRelationtype(jo.optString(CEnum.relationtype.getKey()));

		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}

	private List<CMap> initCmaps(PRelPe entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.relaPersionBase.getKey(), entity.getRelaPersionBase(), CEnum.relaPersionBase.getRemark()));
		cmaps.add(new CMap(CEnum.relapersonid.getKey(), entity.getRelapersonid(), CEnum.relapersonid.getRemark()));
		cmaps.add(new CMap(CEnum.relationtype.getKey(), entity.getRelationtype(), CEnum.relationtype.getRemark()));

		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}