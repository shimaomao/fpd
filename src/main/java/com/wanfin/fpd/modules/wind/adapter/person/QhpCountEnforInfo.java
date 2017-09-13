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
 * 前海-好信信用轨迹信息
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountEnforInfo implements IAdapter<QhpCountEnforInfo>{
	private enum CEnum{
		//id, personid, rceId, entityName, entityId, caseStatus, buildDate, courtName, execMoney, caseCode, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		rceId("rceId", "主键"),
		entityName("entityName", "名称"),
		entityId("entityId", "证件号码"),
		caseStatus("caseStatus", "案件状态"),
		buildDate("buildDate", "立案时间"),
		courtName("courtName", "法院"),
		execMoney("execMoney", "执行标的"),
		caseCode("caseCode", "案号"),
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
	private String rceId;		// qhzc_records_count_enforcement主键
	private String entityName;		// 姓名\企业名称
	private String entityId;		// 证件号码
	private String caseStatus;		// 案件状态
	private String buildDate;		// 立案时间
	private String courtName;		// 法院
	private String execMoney;		// 执行标的
	private String caseCode;		// 案号
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountEnforInfo() {
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
	
	public String getRceId() {
		return rceId;
	}

	public void setRceId(String rceId) {
		this.rceId = rceId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getExecMoney() {
		return execMoney;
	}

	public void setExecMoney(String execMoney) {
		this.execMoney = execMoney;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
	public List<QhpCountEnforInfo> init(JSONArray jarray) throws JSONException {
		List<QhpCountEnforInfo> entitys = new ArrayList<QhpCountEnforInfo>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountEnforInfo init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountEnforInfo init(JSONObject jo) throws JSONException {
		QhpCountEnforInfo entity = new QhpCountEnforInfo();
		entity.setBuildDate(jo.optString(CEnum.buildDate.getKey()));
		entity.setCaseCode(jo.optString(CEnum.caseCode.getKey()));
		entity.setCaseStatus(jo.optString(CEnum.caseStatus.getKey()));
		entity.setCourtName(jo.optString(CEnum.courtName.getKey()));
		entity.setEntityId(jo.optString(CEnum.entityId.getKey()));
		entity.setEntityName(jo.optString(CEnum.entityName.getKey()));
		entity.setExecMoney(jo.optString(CEnum.execMoney.getKey()));
		entity.setRceId(jo.optString(CEnum.rceId.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountEnforInfo entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.buildDate.getKey(), entity.getBuildDate(), CEnum.buildDate.getRemark()));
		cmaps.add(new CMap(CEnum.caseCode.getKey(), entity.getCaseCode(), CEnum.caseCode.getRemark()));
		cmaps.add(new CMap(CEnum.caseStatus.getKey(), entity.getCaseStatus(), CEnum.caseStatus.getRemark()));
		cmaps.add(new CMap(CEnum.courtName.getKey(), entity.getCourtName(), CEnum.courtName.getRemark()));
		cmaps.add(new CMap(CEnum.entityId.getKey(), entity.getEntityId(), CEnum.entityId.getRemark()));
		cmaps.add(new CMap(CEnum.entityName.getKey(), entity.getEntityName(), CEnum.entityName.getRemark()));
		cmaps.add(new CMap(CEnum.execMoney.getKey(), entity.getExecMoney(), CEnum.execMoney.getRemark()));
		cmaps.add(new CMap(CEnum.rceId.getKey(), entity.getRceId(), CEnum.rceId.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}