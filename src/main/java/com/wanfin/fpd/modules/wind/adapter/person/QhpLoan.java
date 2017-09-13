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
 * 前海-常贷客数据
 * @author srf
 * @version 2016-06-08
 */
public class QhpLoan implements IAdapter<QhpLoan>{
	private enum CEnum{
		//id, personid, resId, seqno, , idtype, name, reasoncode, industry, amount, busidate, ercode, ermsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqno("seqno", "序列号"),
		idno("idno", "证件号码"),
		idtype("idtype", "证件类型"),
		name("name", "名称"),
		reasoncode("reasoncode", "查询原因"),
		industry("industry", "机构所属行业"),
		amount("amount", "命中机构数目"),
		busidate("busidate", "业务发生时间日期"),
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
	private String resId;		// qhzc_response主键
	private String idno;		// 证件号码
	private String idtype;		// 证件类型
	private String name;		// 主体名称
	private String seqno;		// 序列号
	private String reasoncode;		// 查询原因
	private String industry;		// 机构所属行业
	private String amount;		// 命中机构数目
	private String busidate;		// 业务发生时间日期
	private String ercode;		// 错误代码
	private String ermsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpLoan() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getReasoncode() {
		return reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBusidate() {
		return busidate;
	}

	public void setBusidate(String busidate) {
		this.busidate = busidate;
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
	public List<QhpLoan> init(JSONArray jarray) throws JSONException {
		List<QhpLoan> entitys = new ArrayList<QhpLoan>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpLoan init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpLoan init(JSONObject jo) throws JSONException {
		QhpLoan entity = new QhpLoan();
		entity.setAmount(jo.optString(CEnum.amount.getKey()));
		entity.setBusidate(jo.optString(CEnum.busidate.getKey()));
		entity.setIdno(jo.optString(CEnum.idno.getKey()));
		entity.setIdtype(jo.optString(CEnum.idtype.getKey()));
		entity.setIndustry(jo.optString(CEnum.industry.getKey()));
		entity.setName(jo.optString(CEnum.name.getKey()));
		entity.setReasoncode(jo.optString(CEnum.reasoncode.getKey()));
		
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
	
	private List<CMap> initCmaps(QhpLoan entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.amount.getKey(), entity.getAmount(), CEnum.amount.getRemark()));
		cmaps.add(new CMap(CEnum.busidate.getKey(), entity.getBusidate(), CEnum.busidate.getRemark()));
		cmaps.add(new CMap(CEnum.idno.getKey(), entity.getIdno(), CEnum.idno.getRemark()));
		cmaps.add(new CMap(CEnum.idtype.getKey(), entity.getIdtype(), CEnum.idtype.getRemark()));
		cmaps.add(new CMap(CEnum.industry.getKey(), entity.getIndustry(), CEnum.industry.getRemark()));
		cmaps.add(new CMap(CEnum.name.getKey(), entity.getName(), CEnum.name.getRemark()));
		cmaps.add(new CMap(CEnum.reasoncode.getKey(), entity.getReasoncode(), CEnum.reasoncode.getRemark()));

		cmaps.add(new CMap(CEnum.ercode.getKey(), entity.getErcode(), CEnum.ercode.getRemark()));
		cmaps.add(new CMap(CEnum.ermsg.getKey(), entity.getErmsg(), CEnum.ermsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqno.getKey(), entity.getSeqno(), CEnum.seqno.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}