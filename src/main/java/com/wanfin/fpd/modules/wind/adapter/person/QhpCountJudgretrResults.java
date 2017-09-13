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
 * 前海-好信法院通-裁判文书信息检索查询结果
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountJudgretrResults implements IAdapter<QhpCountJudgretrResults>{
	private enum CEnum{
		//id, personid, annoretrId, judgeDocId, queryId, searchTransNo, content, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		annoretrId("annoretrId", "裁判文书ID"),
		judgeDocId("judgeDocId", "查询交易ID"),
		queryId("queryId", "检索交易流水号"),
		searchTransNo("searchTransNo", "匹配文书内容"),
		content("content", "部门ID"),
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
	private String annoretrId;		// qhzc_records_count_judge_retrieve主键
	private String judgeDocId;		// 裁判文书ID
	private String queryId;		// 查询交易ID
	private String searchTransNo;	//检索交易流水号
	private String content;		// 匹配文书内容
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountJudgretrResults() {
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

	public String getAnnoretrId() {
		return annoretrId;
	}

	public void setAnnoretrId(String annoretrId) {
		this.annoretrId = annoretrId;
	}

	public String getJudgeDocId() {
		return judgeDocId;
	}

	public void setJudgeDocId(String judgeDocId) {
		this.judgeDocId = judgeDocId;
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
	public List<QhpCountJudgretrResults> init(JSONArray jarray) throws JSONException {
		List<QhpCountJudgretrResults> entitys = new ArrayList<QhpCountJudgretrResults>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountJudgretrResults init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountJudgretrResults init(JSONObject jo) throws JSONException {
		QhpCountJudgretrResults entity = new QhpCountJudgretrResults();
		entity.setAnnoretrId(jo.optString(CEnum.annoretrId.getKey()));
		entity.setContent(jo.optString(CEnum.content.getKey()));
		entity.setJudgeDocId(jo.optString(CEnum.judgeDocId.getKey()));
		entity.setQueryId(jo.optString(CEnum.queryId.getKey()));
		entity.setSearchTransNo(jo.optString(CEnum.searchTransNo.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountJudgretrResults entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.annoretrId.getKey(), entity.getAnnoretrId(), CEnum.annoretrId.getRemark()));
		cmaps.add(new CMap(CEnum.content.getKey(), entity.getContent(), CEnum.content.getRemark()));
		cmaps.add(new CMap(CEnum.judgeDocId.getKey(), entity.getJudgeDocId(), CEnum.judgeDocId.getRemark()));
		cmaps.add(new CMap(CEnum.queryId.getKey(), entity.getQueryId(), CEnum.queryId.getRemark()));
		cmaps.add(new CMap(CEnum.searchTransNo.getKey(), entity.getSearchTransNo(), CEnum.searchTransNo.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}