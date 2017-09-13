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
import com.wanfin.fpd.modules.wind.adapter.person.PPersion;

/**
 * 公司重要个人信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CPerson implements IAdapter<CPerson>{
	private enum CEnum{
//		id, corpid, personid, persionBase, relationtype; 
			
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		personid("personid", "个人主键"),
			
		persionBase("persionBase", "个人信息"),
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
	
	private String id;		// 公司ID
	private String corpid;		// 公司主键
	private String personid;		// 个人主键
	private PPersion persionBase; //个人信息
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
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public PPersion getPersionBase() {
		return persionBase;
	}
	public void setPersionBase(PPersion persionBase) {
		this.persionBase = persionBase;
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
	public List<CPerson> init(JSONArray jarray) throws JSONException {
		List<CPerson> entitys = new ArrayList<CPerson>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public CPerson init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CPerson init(JSONObject jo) throws JSONException {
		CPerson entity = new CPerson();
		entity.setPersionBase(new PPersion().init(jo.optString(CEnum.persionBase.getKey())));
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setRelationtype(jo.optString(CEnum.relationtype.getKey()));

		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}

	private List<CMap> initCmaps(CPerson entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.persionBase.getKey(), entity.getPersionBase(), CEnum.persionBase.getRemark()));
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.relationtype.getKey(), entity.getRelationtype(), CEnum.relationtype.getRemark()));
		
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}