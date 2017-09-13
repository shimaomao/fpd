/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.corporations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 公司执照信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CLicense implements IAdapter<CLicense>{
	private enum CEnum{
//		id, corpid, fileid, licensetype, licensecode, startdate, enddate, idvalidity; 
		id("id", "主键"),
		corpid("corpid", "公司主键"),
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
	
	private String id;		// 公司ID
	private String corpid;		// 公司主键
	private String licensetype;		// 证照类型
	private String licensecode;		// 证照编号
	private Date startdate;		// 证照开始时间
	private Date enddate;		// 证照结束时间
	private String idvalidity;		// 是否长期
	private String fileid;		// 证照附件
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
	public List<CLicense> init(JSONArray jarray) throws JSONException {
		List<CLicense> entitys = new ArrayList<CLicense>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public CLicense init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CLicense init(JSONObject jo) throws JSONException {
		CLicense entity = new CLicense();
		entity.setEnddate(new Date(jo.optLong(CEnum.enddate.getKey())));
		entity.setIdvalidity(jo.optString(CEnum.idvalidity.getKey()));
		entity.setLicensecode(jo.optString(CEnum.licensecode.getKey()));
		entity.setLicensetype(jo.optString(CEnum.licensetype.getKey()));
		entity.setStartdate(new Date(jo.optLong(CEnum.startdate.getKey())));

		
		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}

	private List<CMap> initCmaps(CLicense entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.enddate.getKey(), entity.getEnddate(), CEnum.enddate.getRemark()));
		cmaps.add(new CMap(CEnum.idvalidity.getKey(), entity.getIdvalidity(), CEnum.idvalidity.getRemark()));
		cmaps.add(new CMap(CEnum.licensecode.getKey(), entity.getLicensecode(), CEnum.licensecode.getRemark()));
		cmaps.add(new CMap(CEnum.licensetype.getKey(), entity.getLicensetype(), CEnum.licensetype.getRemark()));
		cmaps.add(new CMap(CEnum.startdate.getKey(), entity.getStartdate(), CEnum.startdate.getRemark()));
		
		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}