/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 用户证书信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class PLicense implements IAdapter<PLicense>{
	private enum CEnum{ 
		id("id", "主键"),
		personid("personid", "个人主键"),
		fileid("fileid", "证照附件"),
		licensetype("licensetype", "证照类型"),
		licensecode("licensecode", "证照编号"),
		startdate("startdate", "证照开始时间"),
		enddate("enddate", "证照结束时间"),
		idvalidity("idvalidity", "是否长期"); 
		
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
	private String licensetype;		// 证照类型
	private String licensecode;		// 证照编号
	private Date startdate;		// 证照开始时间
	private Date enddate;		// 证照结束时间
	private String idvalidity;		// 是否长期
	private String fileid;		// 证照附件
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
	public String getLicensetype() {
		return licensetype;
	}
	public void setLicensetype(String licensetype) {
		this.licensetype = licensetype;
	}
	public String getLicensecode() {
		return licensecode;
	}
	public void setLicensecode(String licensecode) {
		this.licensecode = licensecode;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getIdvalidity() {
		return idvalidity;
	}
	public void setIdvalidity(String idvalidity) {
		this.idvalidity = idvalidity;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	@Override
	public List<PLicense> init(JSONArray jarray) throws JSONException {
		List<PLicense> entitys = new ArrayList<PLicense>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public PLicense init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PLicense init(JSONObject jo) throws JSONException {
		PLicense entity = new PLicense();
		entity.setEnddate(new Date(jo.optLong(CEnum.enddate.getKey())));
		entity.setIdvalidity(jo.optString(CEnum.idvalidity.getKey()));
		entity.setLicensecode(jo.optString(CEnum.licensecode.getKey()));
		entity.setLicensetype(jo.optString(CEnum.licensetype.getKey()));
		entity.setStartdate(new Date(jo.optLong(CEnum.startdate.getKey())));
		
		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));
		
		entity.setCmaps(initCmaps(entity));
		return entity;
	}

	private List<CMap> initCmaps(PLicense entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.enddate.getKey(), entity.getEnddate(), CEnum.enddate.getRemark()));
		cmaps.add(new CMap(CEnum.idvalidity.getKey(), entity.getIdvalidity(), CEnum.idvalidity.getRemark()));
		cmaps.add(new CMap(CEnum.licensecode.getKey(), entity.getLicensecode(), CEnum.licensecode.getRemark()));
		cmaps.add(new CMap(CEnum.licensetype.getKey(), entity.getLicensetype(), CEnum.licensetype.getRemark()));
		cmaps.add(new CMap(CEnum.startdate.getKey(), entity.getStartdate(), CEnum.startdate.getRemark()));

		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}