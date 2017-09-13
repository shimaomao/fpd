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
 * 公司公告信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CAnno implements IAdapter<CAnno>{
	private enum CEnum{
//		id, corpid, fileid, reporttype, attachmenttype, reportdate; 
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		fileid("fileid", "附件"),
		
		reporttype("reporttype", "报表类型"),
		attachmenttype("attachmenttype", "文件类型"),
		reportdate("reportdate", "报告日期"); 
		
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
	private String reporttype;		// 报表类型
	private String attachmenttype;		// 附件类型
	private Date reportdate;		// 报告日期
	private String fileid;		// 附件
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
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getAttachmenttype() {
		return attachmenttype;
	}
	public void setAttachmenttype(String attachmenttype) {
		this.attachmenttype = attachmenttype;
	}
	public Date getReportdate() {
		return reportdate;
	}
	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
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
	public List<CAnno> init(JSONArray jarray) throws JSONException {
		List<CAnno> entitys = new ArrayList<CAnno>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
		}
		return entitys;
	}
	
	@Override
	public CAnno init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private CAnno init(JSONObject jo) throws JSONException {
		CAnno entity = new CAnno();
		entity.setReportdate(new Date(jo.optLong(CEnum.reportdate.getKey())));
		entity.setAttachmenttype(jo.optString(CEnum.attachmenttype.getKey()));
		entity.setReporttype(jo.optString(CEnum.reporttype.getKey()));
		
		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}

	private List<CMap> initCmaps(CAnno entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.reportdate.getKey(), entity.getReportdate(), CEnum.reportdate.getRemark()));
		cmaps.add(new CMap(CEnum.attachmenttype.getKey(), entity.getAttachmenttype(), CEnum.attachmenttype.getRemark()));
		cmaps.add(new CMap(CEnum.reporttype.getKey(), entity.getReporttype(), CEnum.reporttype.getRemark()));
		
		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}