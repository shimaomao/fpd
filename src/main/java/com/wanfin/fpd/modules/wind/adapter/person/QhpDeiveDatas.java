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
 * 前海-驾驶证比对查询数据
 * @author srf
 * @version 2016-06-08
 */
public class QhpDeiveDatas implements IAdapter<QhpDeiveDatas>{
	private enum CEnum{
		//id, personid, resId, seqNo, queryId, submitTransNo, chkResult, chkDriverNo, chkName, chkBirthday, chkBirthday4, chkBirthday6, chkArchivesNo, chkQuasiDrivingVehicle, chkNationality, chkFirstLicensingDate, chkFirstLicensingDate4, chkFirstLicensingDate6, chkValidDateStart, chkValidDateStart4, chkValidDateStart6, chkValidDateEnd, chkValidDateEnd4, chkValidDateEnd6, chkStatus, erCode, erMsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqNo("seqNo", "序列号"),
		queryId("queryId", "任务ID"),
		submitTransNo("submitTransNo", "检索交易流水号"),
		chkResult("chkResult", "整体比对结果"),
		chkDriverNo("chkDriverNo", "驾驶证号"),
		chkName("chkName", "姓名"),
		chkBirthday("chkBirthday", "出生日期"),
		chkBirthday4("chkBirthday4", "出生日期前4位"),
		chkBirthday6("chkBirthday6", "出生日期前6位"),
		chkArchivesNo("chkArchivesNo", "档案编号"),
		chkQuasiDrivingVehicle("chkQuasiDrivingVehicle", "准驾车型"),
		chkNationality("chkNationality", "国籍"),
		chkFirstLicensingDate("chkFirstLicensingDate", "初次领证日期"),
		chkFirstLicensingDate4("chkFirstLicensingDate4", "初次领证日期前4位(年份)"),
		chkFirstLicensingDate6("chkFirstLicensingDate6", "初次领证日期前6位(年月)"),
		chkValidDateStart("chkValidDateStart", "有效期始"),
		chkValidDateStart4("chkValidDateStart4", "有效期始日期前4位(年份)"),
		chkValidDateStart6("chkValidDateStart6", "有效期始日期前6位(年月)"),
		chkValidDateEnd("chkValidDateEnd", "有效期止"),
		chkValidDateEnd4("chkValidDateEnd4", "有效期止日期前4位(年份)"),
		chkValidDateEnd6("chkValidDateEnd6", "有效期止日期前6位(年月)"),
		chkStatus("chkStatus", "驾驶证状态的查询结果"),
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
	private String queryId;		// 任务ID
	private String submitTransNo;		// 检索交易流水号
	private String chkResult;		// 整体比对结果
	private String chkDriverNo;		// 驾驶证号
	private String chkName;		// 姓名
	private String chkBirthday;		// 出生日期
	private String chkBirthday4;		// 出生日期前4位
	private String chkBirthday6;		// 出生日期前6位
	private String chkArchivesNo;		// 档案编号
	private String chkQuasiDrivingVehicle;		// 准驾车型
	private String chkNationality;		// 国籍
	private String chkFirstLicensingDate;		// 初次领证日期
	private String chkFirstLicensingDate4;		// 初次领证日期前4位(年份)
	private String chkFirstLicensingDate6;		// 初次领证日期前6位(年月)
	private String chkValidDateStart;		// 有效期始
	private String chkValidDateStart4;		// 有效期始日期前4位(年份)
	private String chkValidDateStart6;		// 有效期始日期前6位(年月)
	private String chkValidDateEnd;		// 有效期止
	private String chkValidDateEnd4;		// 有效期止日期前4位(年份)
	private String chkValidDateEnd6;		// 有效期止日期前6位(年月)
	private String chkStatus;		// 驾驶证状态的查询结果
	private String seqNo;		// 序列号
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpDeiveDatas() {
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

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getSubmitTransNo() {
		return submitTransNo;
	}

	public void setSubmitTransNo(String submitTransNo) {
		this.submitTransNo = submitTransNo;
	}

	public String getChkResult() {
		return chkResult;
	}

	public void setChkResult(String chkResult) {
		this.chkResult = chkResult;
	}

	public String getChkDriverNo() {
		return chkDriverNo;
	}

	public void setChkDriverNo(String chkDriverNo) {
		this.chkDriverNo = chkDriverNo;
	}

	public String getChkName() {
		return chkName;
	}

	public void setChkName(String chkName) {
		this.chkName = chkName;
	}

	public String getChkBirthday() {
		return chkBirthday;
	}

	public void setChkBirthday(String chkBirthday) {
		this.chkBirthday = chkBirthday;
	}

	public String getChkBirthday4() {
		return chkBirthday4;
	}

	public void setChkBirthday4(String chkBirthday4) {
		this.chkBirthday4 = chkBirthday4;
	}

	public String getChkBirthday6() {
		return chkBirthday6;
	}

	public void setChkBirthday6(String chkBirthday6) {
		this.chkBirthday6 = chkBirthday6;
	}

	public String getChkArchivesNo() {
		return chkArchivesNo;
	}

	public void setChkArchivesNo(String chkArchivesNo) {
		this.chkArchivesNo = chkArchivesNo;
	}

	public String getChkQuasiDrivingVehicle() {
		return chkQuasiDrivingVehicle;
	}

	public void setChkQuasiDrivingVehicle(String chkQuasiDrivingVehicle) {
		this.chkQuasiDrivingVehicle = chkQuasiDrivingVehicle;
	}

	public String getChkNationality() {
		return chkNationality;
	}

	public void setChkNationality(String chkNationality) {
		this.chkNationality = chkNationality;
	}

	public String getChkFirstLicensingDate() {
		return chkFirstLicensingDate;
	}

	public void setChkFirstLicensingDate(String chkFirstLicensingDate) {
		this.chkFirstLicensingDate = chkFirstLicensingDate;
	}

	public String getChkFirstLicensingDate4() {
		return chkFirstLicensingDate4;
	}

	public void setChkFirstLicensingDate4(String chkFirstLicensingDate4) {
		this.chkFirstLicensingDate4 = chkFirstLicensingDate4;
	}

	public String getChkFirstLicensingDate6() {
		return chkFirstLicensingDate6;
	}

	public void setChkFirstLicensingDate6(String chkFirstLicensingDate6) {
		this.chkFirstLicensingDate6 = chkFirstLicensingDate6;
	}

	public String getChkValidDateStart() {
		return chkValidDateStart;
	}

	public void setChkValidDateStart(String chkValidDateStart) {
		this.chkValidDateStart = chkValidDateStart;
	}

	public String getChkValidDateStart4() {
		return chkValidDateStart4;
	}

	public void setChkValidDateStart4(String chkValidDateStart4) {
		this.chkValidDateStart4 = chkValidDateStart4;
	}

	public String getChkValidDateStart6() {
		return chkValidDateStart6;
	}

	public void setChkValidDateStart6(String chkValidDateStart6) {
		this.chkValidDateStart6 = chkValidDateStart6;
	}

	public String getChkValidDateEnd() {
		return chkValidDateEnd;
	}

	public void setChkValidDateEnd(String chkValidDateEnd) {
		this.chkValidDateEnd = chkValidDateEnd;
	}

	public String getChkValidDateEnd4() {
		return chkValidDateEnd4;
	}

	public void setChkValidDateEnd4(String chkValidDateEnd4) {
		this.chkValidDateEnd4 = chkValidDateEnd4;
	}

	public String getChkValidDateEnd6() {
		return chkValidDateEnd6;
	}

	public void setChkValidDateEnd6(String chkValidDateEnd6) {
		this.chkValidDateEnd6 = chkValidDateEnd6;
	}

	public String getChkStatus() {
		return chkStatus;
	}

	public void setChkStatus(String chkStatus) {
		this.chkStatus = chkStatus;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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
	public List<QhpDeiveDatas> init(JSONArray jarray) throws JSONException {
		List<QhpDeiveDatas> entitys = new ArrayList<QhpDeiveDatas>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpDeiveDatas init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpDeiveDatas init(JSONObject jo) throws JSONException {
		QhpDeiveDatas entity = new QhpDeiveDatas();
		entity.setChkArchivesNo(jo.optString(CEnum.chkArchivesNo.getKey()));
		entity.setChkBirthday(jo.optString(CEnum.chkBirthday.getKey()));
		entity.setChkBirthday4(jo.optString(CEnum.chkBirthday4.getKey()));
		entity.setChkBirthday6(jo.optString(CEnum.chkBirthday6.getKey()));
		entity.setChkDriverNo(jo.optString(CEnum.chkDriverNo.getKey()));
		entity.setChkFirstLicensingDate(jo.optString(CEnum.chkFirstLicensingDate.getKey()));
		entity.setChkFirstLicensingDate4(jo.optString(CEnum.chkFirstLicensingDate4.getKey()));
		entity.setChkFirstLicensingDate6(jo.optString(CEnum.chkFirstLicensingDate6.getKey()));
		entity.setChkName(jo.optString(CEnum.chkName.getKey()));
		entity.setChkNationality(jo.optString(CEnum.chkNationality.getKey()));
		entity.setChkQuasiDrivingVehicle(jo.optString(CEnum.chkQuasiDrivingVehicle.getKey()));
		entity.setChkResult(jo.optString(CEnum.chkResult.getKey()));
		entity.setChkStatus(jo.optString(CEnum.chkStatus.getKey()));
		entity.setChkValidDateEnd(jo.optString(CEnum.chkValidDateEnd.getKey()));
		entity.setChkValidDateEnd4(jo.optString(CEnum.chkValidDateEnd4.getKey()));
		entity.setChkValidDateEnd6(jo.optString(CEnum.chkValidDateEnd6.getKey()));
		entity.setChkValidDateStart(jo.optString(CEnum.chkValidDateStart.getKey()));
		entity.setChkValidDateStart4(jo.optString(CEnum.chkValidDateStart4.getKey()));
		entity.setChkValidDateStart6(jo.optString(CEnum.chkValidDateStart6.getKey()));
		entity.setQueryId(jo.optString(CEnum.queryId.getKey()));
		entity.setSubmitTransNo(jo.optString(CEnum.submitTransNo.getKey()));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setSeqNo(jo.optString(CEnum.seqNo.getKey()));
		entity.setErCode(jo.optString(CEnum.erCode.getKey()));
		entity.setErMsg(jo.optString(CEnum.erMsg.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpDeiveDatas entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.chkArchivesNo.getKey(), entity.getChkArchivesNo(), CEnum.chkArchivesNo.getRemark()));
		cmaps.add(new CMap(CEnum.chkBirthday.getKey(), entity.getChkBirthday4(), CEnum.chkBirthday.getRemark()));
		cmaps.add(new CMap(CEnum.chkBirthday4.getKey(), entity.getChkBirthday4(), CEnum.chkBirthday4.getRemark()));
		cmaps.add(new CMap(CEnum.chkBirthday6.getKey(), entity.getChkBirthday6(), CEnum.chkBirthday6.getRemark()));
		cmaps.add(new CMap(CEnum.chkDriverNo.getKey(), entity.getChkDriverNo(), CEnum.chkDriverNo.getRemark()));
		cmaps.add(new CMap(CEnum.chkFirstLicensingDate.getKey(), entity.getChkFirstLicensingDate(), CEnum.chkFirstLicensingDate.getRemark()));
		cmaps.add(new CMap(CEnum.chkFirstLicensingDate4.getKey(), entity.getChkFirstLicensingDate4(), CEnum.chkFirstLicensingDate4.getRemark()));
		cmaps.add(new CMap(CEnum.chkFirstLicensingDate6.getKey(), entity.getChkFirstLicensingDate6(), CEnum.chkFirstLicensingDate6.getRemark()));
		cmaps.add(new CMap(CEnum.chkName.getKey(), entity.getChkName(), CEnum.chkName.getRemark()));
		cmaps.add(new CMap(CEnum.chkNationality.getKey(), entity.getChkNationality(), CEnum.chkNationality.getRemark()));
		cmaps.add(new CMap(CEnum.chkQuasiDrivingVehicle.getKey(), entity.getChkQuasiDrivingVehicle(), CEnum.chkQuasiDrivingVehicle.getRemark()));
		cmaps.add(new CMap(CEnum.chkResult.getKey(), entity.getChkResult(), CEnum.chkResult.getRemark()));
		cmaps.add(new CMap(CEnum.chkStatus.getKey(), entity.getChkStatus(), CEnum.chkStatus.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateEnd.getKey(), entity.getChkValidDateEnd(), CEnum.chkValidDateEnd.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateEnd4.getKey(), entity.getChkValidDateEnd4(), CEnum.chkValidDateEnd4.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateEnd6.getKey(), entity.getChkValidDateEnd6(), CEnum.chkValidDateEnd6.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateStart.getKey(), entity.getChkValidDateStart(), CEnum.chkValidDateStart.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateStart4.getKey(), entity.getChkValidDateStart4(), CEnum.chkValidDateStart4.getRemark()));
		cmaps.add(new CMap(CEnum.chkValidDateStart6.getKey(), entity.getChkValidDateStart6(), CEnum.chkValidDateStart6.getRemark()));
		cmaps.add(new CMap(CEnum.queryId.getKey(), entity.getQueryId(), CEnum.queryId.getRemark()));
		cmaps.add(new CMap(CEnum.submitTransNo.getKey(), entity.getSubmitTransNo(), CEnum.submitTransNo.getRemark()));

		cmaps.add(new CMap(CEnum.erCode.getKey(), entity.getErCode(), CEnum.erCode.getRemark()));
		cmaps.add(new CMap(CEnum.erMsg.getKey(), entity.getErMsg(), CEnum.erMsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqNo.getKey(), entity.getSeqNo(), CEnum.seqNo.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}