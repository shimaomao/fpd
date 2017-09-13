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
 * 前海-好信法院通-失信被执行人详情信息表
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountBrewordInfo implements IAdapter<QhpCountBrewordInfo>{
	private enum CEnum{
		//id, personid, brwiId, entityName, entityId, province, type, gistId, exedOrg, lawerDuty, lawerUserName, gender, age, buildDate, distrustType, fulfillInc, unfulfill, fulfilled, courtName, publishDate, caseCode, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		brwiId("brwiId", "主键"),
		entityName("entityName", "名称"),
		entityId("entityId", "证件号码"),
		province("province", "省份"),
		type("type", "类型"),
		gistId("gistId", "执行依据文号"),
		exedOrg("exedOrg", "做出执行依据单位"),
		lawerDuty("lawerDuty", "生效法律文书确定的义务"),
		lawerUserName("lawerUserName", "法定代表人"),
		gender("gender", "性别"),
		age("age", "年龄"),
		buildDate("buildDate", "立案时间"),
		distrustType("distrustType", "失信类型"),
		fulfillInc("fulfillInc", "被执行人的履情况"),
		unfulfill("unfulfill", "未履行"),
		fulfilled("fulfilled", "已履行"),
		courtName("courtName", "执行法院"),
		publishDate("publishDate", "发布日期"),
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
	private String brwiId;		// qhzc_records_count_breakword主键
	private String entityName;		// 姓名\企业名称
	private String entityId;		// 证件号码
	private String province;		// 省份
	private String type;		// 类型
	private String gistId;		// 执行依据文号
	private String exedOrg;		// 做出执行依据单位
	private String lawerDuty;		// 生效法律文书确定的义务
	private String lawerUserName;		// 法定代表人或者负责人姓名
	private String gender;		// 性别
	private String age;		// 年龄
	private String buildDate;		// 立案时间
	private String distrustType;		// 失信类型
	private String fulfillInc;		// 被执行人的履情况
	private String unfulfill;		// 未履行
	private String fulfilled;		// 已履行
	private String courtName;		// 执行法院
	private String publishDate;		// 发布日期
	private String caseCode;		// 案号
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountBrewordInfo() {
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
	
	public String getBrwiId() {
		return brwiId;
	}

	public void setBrwiId(String brwiId) {
		this.brwiId = brwiId;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGistId() {
		return gistId;
	}

	public void setGistId(String gistId) {
		this.gistId = gistId;
	}

	public String getExedOrg() {
		return exedOrg;
	}

	public void setExedOrg(String exedOrg) {
		this.exedOrg = exedOrg;
	}

	public String getLawerDuty() {
		return lawerDuty;
	}

	public void setLawerDuty(String lawerDuty) {
		this.lawerDuty = lawerDuty;
	}

	public String getLawerUserName() {
		return lawerUserName;
	}

	public void setLawerUserName(String lawerUserName) {
		this.lawerUserName = lawerUserName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getDistrustType() {
		return distrustType;
	}

	public void setDistrustType(String distrustType) {
		this.distrustType = distrustType;
	}

	public String getFulfillInc() {
		return fulfillInc;
	}

	public void setFulfillInc(String fulfillInc) {
		this.fulfillInc = fulfillInc;
	}

	public String getUnfulfill() {
		return unfulfill;
	}

	public void setUnfulfill(String unfulfill) {
		this.unfulfill = unfulfill;
	}

	public String getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(String fulfilled) {
		this.fulfilled = fulfilled;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
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

	public void setPersonid(String personid) {
		this.personid = personid;
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
	public List<QhpCountBrewordInfo> init(JSONArray jarray) throws JSONException {
		List<QhpCountBrewordInfo> entitys = new ArrayList<QhpCountBrewordInfo>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountBrewordInfo init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountBrewordInfo init(JSONObject jo) throws JSONException {
		QhpCountBrewordInfo entity = new QhpCountBrewordInfo();
		entity.setAge(jo.optString(CEnum.age.getKey()));
		entity.setBrwiId(jo.optString(CEnum.brwiId.getKey()));
		entity.setBuildDate(jo.optString(CEnum.buildDate.getKey()));
		entity.setCaseCode(jo.optString(CEnum.caseCode.getKey()));
		entity.setCourtName(jo.optString(CEnum.courtName.getKey()));
		entity.setDistrustType(jo.optString(CEnum.distrustType.getKey()));
		entity.setEntityId(jo.optString(CEnum.entityId.getKey()));
		entity.setEntityName(jo.optString(CEnum.entityName.getKey()));
		entity.setExedOrg(jo.optString(CEnum.exedOrg.getKey()));
		entity.setFulfilled(jo.optString(CEnum.fulfilled.getKey()));
		entity.setFulfillInc(jo.optString(CEnum.fulfillInc.getKey()));
		entity.setGender(jo.optString(CEnum.gender.getKey()));
		entity.setGistId(jo.optString(CEnum.gistId.getKey()));
		entity.setLawerDuty(jo.optString(CEnum.lawerDuty.getKey()));
		entity.setLawerUserName(jo.optString(CEnum.lawerUserName.getKey()));
		entity.setProvince(jo.optString(CEnum.province.getKey()));
		entity.setPublishDate(jo.optString(CEnum.publishDate.getKey()));
		entity.setType(jo.optString(CEnum.type.getKey()));
		entity.setUnfulfill(jo.optString(CEnum.unfulfill.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountBrewordInfo entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.age.getKey(), entity.getAge(), CEnum.age.getRemark()));
		cmaps.add(new CMap(CEnum.brwiId.getKey(), entity.getBrwiId(), CEnum.brwiId.getRemark()));
		cmaps.add(new CMap(CEnum.buildDate.getKey(), entity.getBuildDate(), CEnum.buildDate.getRemark()));
		cmaps.add(new CMap(CEnum.caseCode.getKey(), entity.getCaseCode(), CEnum.caseCode.getRemark()));
		cmaps.add(new CMap(CEnum.courtName.getKey(), entity.getCourtName(), CEnum.courtName.getRemark()));
		cmaps.add(new CMap(CEnum.distrustType.getKey(), entity.getDistrustType(), CEnum.distrustType.getRemark()));
		cmaps.add(new CMap(CEnum.entityId.getKey(), entity.getEntityId(), CEnum.entityId.getRemark()));
		cmaps.add(new CMap(CEnum.entityName.getKey(), entity.getEntityName(), CEnum.entityName.getRemark()));
		cmaps.add(new CMap(CEnum.exedOrg.getKey(), entity.getExedOrg(), CEnum.exedOrg.getRemark()));
		cmaps.add(new CMap(CEnum.fulfilled.getKey(), entity.getFulfilled(), CEnum.fulfilled.getRemark()));
		cmaps.add(new CMap(CEnum.fulfillInc.getKey(), entity.getFulfillInc(), CEnum.fulfillInc.getRemark()));
		cmaps.add(new CMap(CEnum.gender.getKey(), entity.getGender(), CEnum.gender.getRemark()));
		cmaps.add(new CMap(CEnum.gistId.getKey(), entity.getGistId(), CEnum.gistId.getRemark()));
		cmaps.add(new CMap(CEnum.lawerDuty.getKey(), entity.getLawerDuty(), CEnum.lawerDuty.getRemark()));
		cmaps.add(new CMap(CEnum.lawerUserName.getKey(), entity.getLawerUserName(), CEnum.lawerUserName.getRemark()));
		cmaps.add(new CMap(CEnum.province.getKey(), entity.getProvince(), CEnum.province.getRemark()));
		cmaps.add(new CMap(CEnum.publishDate.getKey(), entity.getPublishDate(), CEnum.publishDate.getRemark()));
		cmaps.add(new CMap(CEnum.type.getKey(), entity.getType(), CEnum.type.getRemark()));
		cmaps.add(new CMap(CEnum.unfulfill.getKey(), entity.getUnfulfill(), CEnum.unfulfill.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}