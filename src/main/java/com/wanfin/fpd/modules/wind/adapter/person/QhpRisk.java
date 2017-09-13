/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 前海-风险度提示信息
 * @author srf
 * @version 2016-06-08
 */
public class QhpRisk implements IAdapter<QhpRisk>{
	private enum CEnum{
		//id, personid, resId, idno, idtype, name, seqno, sourceid, rskscore, rskmark, databuildtime, datastatus, ercode, ermsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqno("seqno", "序列号"),
		idno("idno", "证件号码"),
		idtype("idtype", "证件类型"),
		name("name", "主体名称"),
		sourceid("sourceid", "来源代码"),
		rskscore("rskscore", "风险得分"),
		rskmark("rskmark", "风险标记"),
		databuildtime("databuildtime", "业务发生时间"),
		datastatus("datastatus", "数据状态"),
		ercode("ercode", "错误代码"),
		ermsg("ermsg", "错误信息"),
		office("office", "部门"); 
		
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
	private String resId;		// qhzc_response主键
	private String seqno;		// 序列号
	private String idno;		// 证件号码
	private String idtype;		// 证件类型
	private String name;		// 主体名称
	private String sourceid;		// 来源代码
	private String rskscore;		// 风险得分
	private String rskmark;		// 风险标记
	private String databuildtime;		// 业务发生时间
	private String datastatus;		// 数据状态
	private String ercode;		// 错误代码
	private String ermsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpRisk() {
		super();
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

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getRskscore() {
		return rskscore;
	}

	public void setRskscore(String rskscore) {
		this.rskscore = rskscore;
	}

	public String getRskmark() {
		return rskmark;
	}

	public void setRskmark(String rskmark) {
		this.rskmark = rskmark;
	}

	public String getDatabuildtime() {
		return databuildtime;
	}

	public void setDatabuildtime(String databuildtime) {
		this.databuildtime = databuildtime;
	}

	public String getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(String datastatus) {
		this.datastatus = datastatus;
	}

	public String getErcode() {
		return ercode;
	}

	public void setErcode(String ercode) {
		this.ercode = ercode;
	}

	public String getErmsg() {
		return ermsg;
	}

	public void setErmsg(String ermsg) {
		this.ermsg = ermsg;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<CMap> getCmaps() {
		return cmaps;
	}

	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	@Override
	public List<QhpRisk> init(JSONArray jarray) throws JSONException {
		List<QhpRisk> entitys = new ArrayList<QhpRisk>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpRisk init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpRisk init(JSONObject jo) throws JSONException {
		QhpRisk entity = new QhpRisk();
		entity.setDatabuildtime(jo.optString(CEnum.databuildtime.getKey()));
		entity.setDatastatus(jo.optString(CEnum.datastatus.getKey()));
		entity.setIdno(jo.optString(CEnum.idno.getKey()));
		entity.setIdtype(jo.optString(CEnum.idtype.getKey()));
		entity.setName(jo.optString(CEnum.name.getKey()));
		entity.setRskmark(jo.optString(CEnum.rskmark.getKey()));
		entity.setRskscore(jo.optString(CEnum.rskscore.getKey()));
		entity.setSourceid(jo.optString(CEnum.sourceid.getKey()));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setSeqno(jo.optString(CEnum.seqno.getKey()));
		entity.setErcode(jo.optString(CEnum.ercode.getKey()));
		entity.setErmsg(jo.optString(CEnum.ermsg.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpRisk entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.databuildtime.getKey(), entity.getDatabuildtime(), CEnum.databuildtime.getRemark()));
		cmaps.add(new CMap(CEnum.datastatus.getKey(), entity.getDatastatus(), CEnum.datastatus.getRemark()));
		cmaps.add(new CMap(CEnum.idno.getKey(), entity.getIdno(), CEnum.idno.getRemark()));
		cmaps.add(new CMap(CEnum.idtype.getKey(), entity.getIdtype(), CEnum.idtype.getRemark()));
		cmaps.add(new CMap(CEnum.name.getKey(), entity.getName(), CEnum.name.getRemark()));
		cmaps.add(new CMap(CEnum.rskmark.getKey(), entity.getRskmark(), CEnum.rskmark.getRemark()));
		cmaps.add(new CMap(CEnum.rskscore.getKey(), entity.getRskscore(), CEnum.rskscore.getRemark()));

		cmaps.add(new CMap(CEnum.ercode.getKey(), entity.getErcode(), CEnum.ercode.getRemark()));
		cmaps.add(new CMap(CEnum.ermsg.getKey(), entity.getErmsg(), CEnum.ermsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqno.getKey(), entity.getSeqno(), CEnum.seqno.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}