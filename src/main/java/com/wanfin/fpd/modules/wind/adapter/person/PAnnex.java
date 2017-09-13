/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.files.entity.Files;
import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 用户的附件信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class PAnnex implements IAdapter<PAnnex>{
	private enum CEnum{
		personid("personid", "个人主键 "),
		fileid("fileid", "附件"),
		attachmenttype("attachmenttype", "文件类型"),
		attachmentname("attachmentname", "附件名称"),
		files("files", "附件"); 
		
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
	
	private String personid;		//个人主键 
	private String attachmenttype;		// 文件类型
	private String attachmentname;		// 附件名称
	private String fileid;		// 附件
	private Files files;		// 附件
	
	private List<CMap> cmaps;

	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
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
	public Files getFiles() {
		return files;
	}
	public void setFiles(Files files) {
		this.files = files;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	
	@Override
	public List<PAnnex> init(JSONArray jarray) throws JSONException {
		List<PAnnex> entitys = new ArrayList<PAnnex>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public PAnnex init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PAnnex init(JSONObject jo) throws JSONException {
		PAnnex entity = new PAnnex();
		entity.setAttachmentname(jo.optString(CEnum.attachmentname.getKey()));
		entity.setAttachmenttype(jo.optString(CEnum.attachmenttype.getKey()));

		//CEnum.files.getKey());
		entity.setFiles(new Files());
		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		
		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(PAnnex entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.attachmentname.getKey(), entity.getAttachmentname(), CEnum.attachmentname.getRemark()));
		cmaps.add(new CMap(CEnum.attachmenttype.getKey(), entity.getAttachmentname(), CEnum.attachmenttype.getRemark()));
		
		cmaps.add(new CMap(CEnum.files.getKey(), entity.getFiles(), CEnum.files.getRemark()));
		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		return cmaps;
	}
}