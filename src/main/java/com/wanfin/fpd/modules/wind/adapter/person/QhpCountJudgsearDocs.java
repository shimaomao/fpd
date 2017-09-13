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
 * 前海-好信法院通-裁判文书信息查询详情信息表
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountJudgsearDocs implements IAdapter<QhpCountJudgsearDocs>{
	private enum CEnum{
		//id, personid, annoretrId, content, courtName, title, caseCode, publishDate, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		annoretrId("annoretrId", "主键"),
		content("content", "文书内容"),
		courtName("courtName", "判决法院"),
		title("title", "文书标题"),
		caseCode("caseCode", "案号"),
		publishDate("publishDate", "发布日期"),
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
	private String annoretrId;		// qhzc_records_count_judge_search主键
	private String content;		// 文书内容
	private String courtName;		// 判决法院
	private String title;		// 文书标题
	private String caseCode;		// 案号
	private String publishDate;		// 发布日期
	//private String organId;		// 公司ID
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountJudgsearDocs() {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
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
	public List<QhpCountJudgsearDocs> init(JSONArray jarray) throws JSONException {
		List<QhpCountJudgsearDocs> entitys = new ArrayList<QhpCountJudgsearDocs>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountJudgsearDocs init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountJudgsearDocs init(JSONObject jo) throws JSONException {
		QhpCountJudgsearDocs entity = new QhpCountJudgsearDocs();
		entity.setAnnoretrId(jo.optString(CEnum.annoretrId.getKey()));
		entity.setCaseCode(jo.optString(CEnum.caseCode.getKey()));
		entity.setContent(jo.optString(CEnum.content.getKey()));
		entity.setCourtName(jo.optString(CEnum.courtName.getKey()));
		entity.setPublishDate(jo.optString(CEnum.publishDate.getKey()));
		entity.setTitle(jo.optString(CEnum.title.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountJudgsearDocs entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.annoretrId.getKey(), entity.getAnnoretrId(), CEnum.annoretrId.getRemark()));
		cmaps.add(new CMap(CEnum.caseCode.getKey(), entity.getCaseCode(), CEnum.caseCode.getRemark()));
		cmaps.add(new CMap(CEnum.content.getKey(), entity.getContent(), CEnum.content.getRemark()));
		cmaps.add(new CMap(CEnum.courtName.getKey(), entity.getCourtName(), CEnum.courtName.getRemark()));
		cmaps.add(new CMap(CEnum.publishDate.getKey(), entity.getPublishDate(), CEnum.publishDate.getRemark()));
		cmaps.add(new CMap(CEnum.title.getKey(), entity.getTitle(), CEnum.title.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}