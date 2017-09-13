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
 * 前海-一鉴通银行卡鉴权信息
 * @author srf
 * @version 2016-06-08
 */
public class QhpCardVerify implements IAdapter<QhpCardVerify>{
	private enum CEnum{
		//id, personid, resId, seqno, authresult, ercode, ermsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqno("seqno", "序列号"),
		authresult("authresult", "鉴权结果"),
		ercode("ercode", "错误代码"),
		ermsg("ermsg", "错误信息"),
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
	private String seqno;		// 序列号
	private String authresult;		// 鉴权结果
	private String ercode;		// 错误代码
	private String ermsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCardVerify() {
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

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getAuthresult() {
		return authresult;
	}

	public void setAuthresult(String authresult) {
		this.authresult = authresult;
	}

	public String getErcode() {
		return ercode;
	}

	public void setErcode(String ercode) {
		this.ercode = ercode;
	}

	public String getErmsg() {
		return ermsg;
	}

	public void setErmsg(String ermsg) {
		this.ermsg = ermsg;
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
	public List<QhpCardVerify> init(JSONArray jarray) throws JSONException {
		List<QhpCardVerify> entitys = new ArrayList<QhpCardVerify>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCardVerify init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCardVerify init(JSONObject jo) throws JSONException {
		QhpCardVerify entity = new QhpCardVerify();
		entity.setAuthresult(jo.optString(CEnum.authresult.getKey()));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setSeqno(jo.optString(CEnum.seqno.getKey()));
		entity.setErcode(jo.optString(CEnum.ercode.getKey()));
		entity.setErmsg(jo.optString(CEnum.ermsg.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCardVerify entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.authresult.getKey(), entity.getAuthresult(), CEnum.authresult.getRemark()));
		
		cmaps.add(new CMap(CEnum.ercode.getKey(), entity.getErcode(), CEnum.ercode.getRemark()));
		cmaps.add(new CMap(CEnum.ermsg.getKey(), entity.getErmsg(), CEnum.ermsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqno.getKey(), entity.getSeqno(), CEnum.seqno.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
	
	/**
	 * @author Chenh  
	 * @date 2016年8月25日 下午5:42:57 
	 * @Description [[_鉴权类型_]] CVauthType类
	 * AuthType:鉴权类型
	 * 	A3：鉴权三要素（姓名、证件号、银行卡号）
	 * 	A4：鉴权四要素（姓名、证件号、银行卡号、手机号码）
	 * 	A6：鉴权六要素（姓名、证件号、银行卡号、手机号码、有效期（年、月）、cvn码）
	 *
	 */
	public enum CVauthType{
		A3("姓名、证件号、银行卡号"),A4("姓名、证件号、银行卡号、手机号码"),A6("姓名、证件号、银行卡号、手机号码、有效期（年、月）、cvn码");
		private String msg;

		private CVauthType(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}