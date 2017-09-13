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
 * 前海-好信平安主体结构信息
 * @author srf
 * @version 2016-06-08
 */
public class QhpPinganResStruct implements IAdapter<QhpPinganResStruct>{
	private enum CEnum{
		//id, personid, busiDataType, searchMethod, orgCode, chnlId, transNo, transDate, authCode, authDate, rtCode, rtMsg, batchNo, subProductInc, signatureValue, busiDateId, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		busiDataType("busiDataType", "具体接口的messageCode"),
		searchMethod("searchMethod", "查询方式"),
		orgCode("orgCode", "机构代码"),
		chnlId("chnlId", "渠道、系统ID"),
		transNo("transNo", "交易流水号"),
		transDate("transDate", "交易时间"),
		authCode("authCode", "授权代码"),
		authDate("authDate", "授权时间"),
		rtCode("rtCode", "错误代码"),
		rtMsg("rtMsg", "错误消息"),
		batchNo("batchNo", "批次号"),
		subProductInc("subProductInc", "子产品信息"),
		signatureValue("signatureValue", "签名"),
		busiDateId("busiDateId", "请求内容ID"),
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
	private String busiDataType;		// 具体接口的messageCode
	private String searchMethod;		// 查询方式
	private String orgCode;		// 机构代码
	private String chnlId;		// 渠道、系统ID
	private String transNo;		// 交易流水号
	private String transDate;		// 交易时间
	private String authCode;		// 授权代码
	private String authDate;		// 授权时间
	private String rtCode;		// 错误代码
	private String rtMsg;		// 错误消息
	private String batchNo;		// 批次号
	private String subProductInc;		// 子产品信息
	private String signatureValue;		// 签名
	private String busiDateId;		// 请求内容ID
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpPinganResStruct() {
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

	public String getBusiDataType() {
		return busiDataType;
	}

	public void setBusiDataType(String busiDataType) {
		this.busiDataType = busiDataType;
	}

	public String getSearchMethod() {
		return searchMethod;
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getChnlId() {
		return chnlId;
	}

	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getRtCode() {
		return rtCode;
	}

	public void setRtCode(String rtCode) {
		this.rtCode = rtCode;
	}

	public String getRtMsg() {
		return rtMsg;
	}

	public void setRtMsg(String rtMsg) {
		this.rtMsg = rtMsg;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSubProductInc() {
		return subProductInc;
	}

	public void setSubProductInc(String subProductInc) {
		this.subProductInc = subProductInc;
	}

	public String getSignatureValue() {
		return signatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}

	public String getBusiDateId() {
		return busiDateId;
	}

	public void setBusiDateId(String busiDateId) {
		this.busiDateId = busiDateId;
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
	public List<QhpPinganResStruct> init(JSONArray jarray) throws JSONException {
		List<QhpPinganResStruct> entitys = new ArrayList<QhpPinganResStruct>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpPinganResStruct init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpPinganResStruct init(JSONObject jo) throws JSONException {
		QhpPinganResStruct entity = new QhpPinganResStruct();
		entity.setAuthCode(jo.optString(CEnum.authCode.getKey()));
		entity.setAuthDate(jo.optString(CEnum.authDate.getKey()));
		entity.setBatchNo(jo.optString(CEnum.batchNo.getKey()));
		entity.setBusiDataType(jo.optString(CEnum.busiDataType.getKey()));
		entity.setBusiDateId(jo.optString(CEnum.busiDateId.getKey()));
		entity.setChnlId(jo.optString(CEnum.chnlId.getKey()));
		entity.setOrgCode(jo.optString(CEnum.orgCode.getKey()));
		entity.setRtMsg(jo.optString(CEnum.rtCode.getKey()));
		entity.setRtMsg(jo.optString(CEnum.rtMsg.getKey()));
		entity.setSearchMethod(jo.optString(CEnum.searchMethod.getKey()));
		entity.setSignatureValue(jo.optString(CEnum.signatureValue.getKey()));
		entity.setSubProductInc(jo.optString(CEnum.subProductInc.getKey()));
		entity.setTransDate(jo.optString(CEnum.transDate.getKey()));
		entity.setTransNo(jo.optString(CEnum.transNo.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpPinganResStruct entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.authCode.getKey(), entity.getAuthCode(), CEnum.authCode.getRemark()));
		cmaps.add(new CMap(CEnum.authDate.getKey(), entity.getAuthDate(), CEnum.authDate.getRemark()));
		cmaps.add(new CMap(CEnum.batchNo.getKey(), entity.getBatchNo(), CEnum.batchNo.getRemark()));
		cmaps.add(new CMap(CEnum.busiDataType.getKey(), entity.getBusiDataType(), CEnum.busiDataType.getRemark()));
		cmaps.add(new CMap(CEnum.busiDateId.getKey(), entity.getBusiDateId(), CEnum.busiDateId.getRemark()));
		cmaps.add(new CMap(CEnum.chnlId.getKey(), entity.getChnlId(), CEnum.chnlId.getRemark()));
		cmaps.add(new CMap(CEnum.orgCode.getKey(), entity.getOrgCode(), CEnum.orgCode.getRemark()));
		cmaps.add(new CMap(CEnum.rtCode.getKey(), entity.getRtCode(), CEnum.rtCode.getRemark()));
		cmaps.add(new CMap(CEnum.rtMsg.getKey(), entity.getRtMsg(), CEnum.rtMsg.getRemark()));
		cmaps.add(new CMap(CEnum.searchMethod.getKey(), entity.getSearchMethod(), CEnum.searchMethod.getRemark()));
		cmaps.add(new CMap(CEnum.signatureValue.getKey(), entity.getSignatureValue(), CEnum.signatureValue.getRemark()));
		cmaps.add(new CMap(CEnum.subProductInc.getKey(), entity.getSubProductInc(), CEnum.subProductInc.getRemark()));
		cmaps.add(new CMap(CEnum.transDate.getKey(), entity.getTransDate(), CEnum.transDate.getRemark()));
		cmaps.add(new CMap(CEnum.transNo.getKey(), entity.getTransNo(), CEnum.transNo.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}