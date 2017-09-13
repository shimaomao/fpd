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
 * 前海-好信手机综合资讯
 * @author srf
 * @version 2016-06-08
 */
public class QhpMobileConsult implements IAdapter<QhpMobileConsult>{
	private enum CEnum{
		//id, personid, resId, seqNo, mobileno, username, idno, idtype, transid, clientid, overAllInc, erCode, erMsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqNo("seqNo", "序列号"),
		mobileno("mobileno", "手机号码"),
		username("username", "用户姓名"),
		idno("idno", "证件号码"),
		idtype("idtype", "证件类型"),
		transid("transid", "交易ID"),
		clientid("clientid", "客户端ID"),
		overAllInc("overAllInc", "综合信息"),
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
	private String resId;		// qhzc_response主键
	private String seqNo;		// 序列号
	private String mobileno;		// 手机号码
	private String username;		// 用户姓名
	private String idno;		// 证件号码
	private String idtype;		// 证件类型
	private String transid;		// 交易ID
	private String clientid;		// 客户端ID
	private QhpMobileOverAllInc overAllInc; // 综合信息
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpMobileConsult() {
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

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getTransid() {
		return transid;
	}

	public void setTransid(String transid) {
		this.transid = transid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public QhpMobileOverAllInc getOverAllInc() {
		return overAllInc;
	}

	public void setOverAllInc(QhpMobileOverAllInc overAllInc) {
		this.overAllInc = overAllInc;
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

	public void setResId(String resId) {
		this.resId = resId;
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
	public List<QhpMobileConsult> init(JSONArray jarray) throws JSONException {
		List<QhpMobileConsult> entitys = new ArrayList<QhpMobileConsult>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpMobileConsult init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpMobileConsult init(JSONObject jo) throws JSONException {
		QhpMobileConsult entity = new QhpMobileConsult();
		entity.setClientid(jo.optString(CEnum.clientid.getKey()));
		entity.setIdno(jo.optString(CEnum.idno.getKey()));
		entity.setIdtype(jo.optString(CEnum.idtype.getKey()));
		entity.setMobileno(jo.optString(CEnum.mobileno.getKey()));
		entity.setOverAllInc(new QhpMobileOverAllInc().init(jo.optString(CEnum.overAllInc.getKey())));
		entity.setTransid(jo.optString(CEnum.transid.getKey()));
		entity.setUsername(jo.optString(CEnum.username.getKey()));
		
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
	
	private List<CMap> initCmaps(QhpMobileConsult entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.clientid.getKey(), entity.getClientid(), CEnum.clientid.getRemark()));
		cmaps.add(new CMap(CEnum.idno.getKey(), entity.getIdno(), CEnum.idno.getRemark()));
		cmaps.add(new CMap(CEnum.idtype.getKey(), entity.getIdtype(), CEnum.idtype.getRemark()));
		cmaps.add(new CMap(CEnum.mobileno.getKey(), entity.getMobileno(), CEnum.mobileno.getRemark()));
		cmaps.add(new CMap(CEnum.overAllInc.getKey(), entity.getOverAllInc(), CEnum.overAllInc.getRemark()));
		cmaps.add(new CMap(CEnum.transid.getKey(), entity.getTransid(), CEnum.transid.getRemark()));
		cmaps.add(new CMap(CEnum.username.getKey(), entity.getUsername(), CEnum.username.getRemark()));

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