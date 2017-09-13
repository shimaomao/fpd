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
 * 公司工附件信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class CAnnex implements IAdapter<CAnnex>{
	private enum CEnum{ 
		//id, corpid, fileid, attachmenttype, attachmentname; 
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		
		fileid("fileid", "工商信息附件"),
		attachmenttype("attachmenttype", "文件类型"),
		attachmentname("attachmentname", "附件名称"); 
		
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
	private String attachmenttype;		// 文件类型
	private String attachmentname;		// 附件名称
	private String fileid;		// 工商信息附件
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
	public String getAttachmenttype() {
		return attachmenttype;
	}
	public void setAttachmenttype(String attachmenttype) {
		this.attachmenttype = attachmenttype;
	}
	public String getAttachmentname() {
		return attachmentname;
	}
	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
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
	public List<CAnnex> init(JSONArray jarray) throws JSONException {
		List<CAnnex> entitys = new ArrayList<CAnnex>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
		}
		return entitys;
	}
	
	@Override
	public CAnnex init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CAnnex init(JSONObject jo) throws JSONException {
		CAnnex entity = new CAnnex();
		entity.setAttachmentname(jo.optString(CEnum.attachmentname.getKey()));
		entity.setAttachmenttype(jo.optString(CEnum.attachmenttype.getKey()));

		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(CAnnex entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.attachmentname.getKey(), entity.getAttachmentname(), CEnum.attachmentname.getRemark()));
		cmaps.add(new CMap(CEnum.attachmenttype.getKey(), entity.getAttachmentname(), CEnum.attachmenttype.getRemark()));
		
		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}