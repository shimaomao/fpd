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
 * 前海-好信银行卡资讯信息
 * @author srf
 * @version 2016-06-08
 */
public class QhpBankCardInfo implements IAdapter<QhpBankCardInfo>{
	private enum CEnum{
		//id, personid, resId, erCode, erMsg, records, office;
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		records("records", "记录"),
		erCode("erCode", "错误代码"),
		erMsg("erMsg", "错误信息"),
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
	private String resId;		// qhzc_res_struct主键
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private String records;		// records内容,太长存json
	//private String organId;		// 公司ID
	private Office office;		// 部门ID
	
	private List<CMap> cmaps;
	
	public QhpBankCardInfo() {
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

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getErCode() {
		return erCode;
	}

	public void setErCode(String erCode) {
		this.erCode = erCode;
	}

	public String getErMsg() {
		return erMsg;
	}

	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CMap> getCmaps() {
		return cmaps;
	}

	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	@Override
	public List<QhpBankCardInfo> init(JSONArray jarray) throws JSONException {
		List<QhpBankCardInfo> entitys = new ArrayList<QhpBankCardInfo>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpBankCardInfo init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpBankCardInfo init(JSONObject jo) throws JSONException {
		QhpBankCardInfo entity = new QhpBankCardInfo();
		entity.setRecords(jo.optString(CEnum.records.getKey()));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setErCode(jo.optString(CEnum.erCode.getKey()));
		entity.setErMsg(jo.optString(CEnum.erMsg.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpBankCardInfo entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.records.getKey(), entity.getRecords(), CEnum.records.getRemark()));

		cmaps.add(new CMap(CEnum.erCode.getKey(), entity.getErCode(), CEnum.erCode.getRemark()));
		cmaps.add(new CMap(CEnum.erMsg.getKey(), entity.getErMsg(), CEnum.erMsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}