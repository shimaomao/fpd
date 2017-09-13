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
 * 前海-好信法院通-法院公告信息查询结果
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountAnnosearNotice implements IAdapter<QhpCountAnnosearNotice>{
	private enum CEnum{
		//id, personid, annoretrId, content, announcer, litigant, type, publishDate, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		annoretrId("annoretrId", "主键"),
		content("content", "公告内容"),
		announcer("announcer", "公告人"),
		litigant("litigant", "当事人"),
		type("type", "公告类型"),
		publishDate("publishDate", "公告日期"),
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
	private String annoretrId;		// qhzc_records_count_announce_search主键
	private String content;		// 公告内容
	private String announcer;		// 公告人
	private String litigant;		// 当事人
	private String type;		// 公告类型
	private String publishDate;		// 公告日期
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountAnnosearNotice() {
		super();
	}
	
	public String getId() {
		return id;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getAnnoretrId() {
		return annoretrId;
	}

	public void setAnnoretrId(String annoretrId) {
		this.annoretrId = annoretrId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnnouncer() {
		return announcer;
	}

	public void setAnnouncer(String announcer) {
		this.announcer = announcer;
	}

	public String getLitigant() {
		return litigant;
	}

	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public void setId(String id) {
		this.id = id;
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
	public List<QhpCountAnnosearNotice> init(JSONArray jarray) throws JSONException {
		List<QhpCountAnnosearNotice> entitys = new ArrayList<QhpCountAnnosearNotice>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountAnnosearNotice init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountAnnosearNotice init(JSONObject jo) throws JSONException {
		QhpCountAnnosearNotice entity = new QhpCountAnnosearNotice();
		entity.setAnnoretrId(jo.optString(CEnum.annoretrId.getKey()));
		entity.setContent(jo.optString(CEnum.content.getKey()));
		entity.setAnnouncer(jo.optString(CEnum.announcer.getKey()));
		entity.setLitigant(jo.optString(CEnum.litigant.getKey()));
		entity.setType(jo.optString(CEnum.type.getKey()));
		entity.setPublishDate(jo.optString(CEnum.publishDate.getKey()));

		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountAnnosearNotice entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.annoretrId.getKey(), entity.getAnnoretrId(), CEnum.annoretrId.getRemark()));
		cmaps.add(new CMap(CEnum.content.getKey(), entity.getContent(), CEnum.content.getRemark()));
		cmaps.add(new CMap(CEnum.announcer.getKey(), entity.getAnnouncer(), CEnum.announcer.getRemark()));
		cmaps.add(new CMap(CEnum.litigant.getKey(), entity.getLitigant(), CEnum.litigant.getRemark()));
		cmaps.add(new CMap(CEnum.type.getKey(), entity.getType(), CEnum.type.getRemark()));
		cmaps.add(new CMap(CEnum.publishDate.getKey(), entity.getPublishDate(), CEnum.publishDate.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}