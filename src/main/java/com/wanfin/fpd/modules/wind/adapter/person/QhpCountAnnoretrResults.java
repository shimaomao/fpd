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
 * 前海-好信法院通-法院公告信息检索查询结果
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountAnnoretrResults implements IAdapter<QhpCountAnnoretrResults>{
	private enum CEnum{
		//id, personid, annoretrId, courtNoticeId, queryId, searchTransNo, content, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		annoretrId("annoretrId", "主键"),
		courtNoticeId("courtNoticeId", "法院公告ID"),
		queryId("queryId", "查询交易ID"),
		searchTransNo("searchTransNo", "检索交易流水号"),
		content("content", "匹配文书内容"),
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
	private String annoretrId;		// qhzc_records_count_announce_retrieve主键
	private String courtNoticeId;		// 法院公告ID
	private String queryId;		// 查询交易ID
	private String searchTransNo;	//检索交易流水号
	private String content;		// 匹配文书内容
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountAnnoretrResults() {
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

	public String getCourtNoticeId() {
		return courtNoticeId;
	}

	public void setCourtNoticeId(String courtNoticeId) {
		this.courtNoticeId = courtNoticeId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getSearchTransNo() {
		return searchTransNo;
	}

	public void setSearchTransNo(String searchTransNo) {
		this.searchTransNo = searchTransNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	public List<QhpCountAnnoretrResults> init(JSONArray jarray) throws JSONException {
		List<QhpCountAnnoretrResults> entitys = new ArrayList<QhpCountAnnoretrResults>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountAnnoretrResults init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountAnnoretrResults init(JSONObject jo) throws JSONException {
		QhpCountAnnoretrResults entity = new QhpCountAnnoretrResults();
		entity.setAnnoretrId(jo.optString(CEnum.annoretrId.getKey()));
		entity.setContent(jo.optString(CEnum.content.getKey()));
		entity.setCourtNoticeId(jo.optString(CEnum.courtNoticeId.getKey()));
		entity.setQueryId(jo.optString(CEnum.queryId.getKey()));
		entity.setSearchTransNo(jo.optString(CEnum.searchTransNo.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountAnnoretrResults entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.annoretrId.getKey(), entity.getAnnoretrId(), CEnum.annoretrId.getRemark()));
		cmaps.add(new CMap(CEnum.content.getKey(), entity.getContent(), CEnum.content.getRemark()));
		cmaps.add(new CMap(CEnum.courtNoticeId.getKey(), entity.getCourtNoticeId(), CEnum.courtNoticeId.getRemark()));
		cmaps.add(new CMap(CEnum.queryId.getKey(), entity.getQueryId(), CEnum.queryId.getRemark()));
		cmaps.add(new CMap(CEnum.searchTransNo.getKey(), entity.getSearchTransNo(), CEnum.searchTransNo.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}