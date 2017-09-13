/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.corporations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 公司贷款信息Entity
 * 
 * @author srf
 * @version 2016-05-13
 */
public class CLoan implements IAdapter<CLoan> {
	private enum CEnum {
//		id, corpid, financeagency, amount, startdate, enddate, reason, iswarrant, warrantentitytype, warrantentityid, isclear, isdelay, isdispute;
		
		id("id", "主键"),
		corpid("corpid", "公司主键"),
			
		financeagency("financeagency", "放款机构"),
		amount("amount", "金额"),
		startdate("startdate", "开始时间"),
		enddate("enddate", "结束时间"),
		
		reason("reason", "贷款原因"),
		iswarrant("iswarrant", "是否有担保"),
		warrantentitytype("warrantentitytype", "担保人类型"),
		warrantentityid("warrantentityid", "担保人外键"),
		isclear("isclear", "是否还清"),
		isdelay("isdelay", "是否逾期"),
		isdispute("isdispute", "是否纠纷"); 
		
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

	private String id; // ID
	private String corpid; // 公司主键
	private String financeagency; // 放款机构
	private String amount; // 金额
	private Date startdate; // 开始日期
	private Date enddate; // 结束日期
	private String reason; // 贷款原因
	private String iswarrant; // 是否有担保
	private String warrantentitytype; // 担保人类型
	private String warrantentityid; // 担保人外键
	private String isclear; // 是否还清
	private String isdelay; // 是否逾期
	private String isdispute; // 是否纠纷
	private List<CMap> cmaps;

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFinanceagency() {
		return financeagency;
	}

	public void setFinanceagency(String financeagency) {
		this.financeagency = financeagency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIswarrant() {
		return iswarrant;
	}

	public void setIswarrant(String iswarrant) {
		this.iswarrant = iswarrant;
	}

	public String getWarrantentitytype() {
		return warrantentitytype;
	}

	public void setWarrantentitytype(String warrantentitytype) {
		this.warrantentitytype = warrantentitytype;
	}

	public String getWarrantentityid() {
		return warrantentityid;
	}

	public void setWarrantentityid(String warrantentityid) {
		this.warrantentityid = warrantentityid;
	}

	public String getIsclear() {
		return isclear;
	}

	public void setIsclear(String isclear) {
		this.isclear = isclear;
	}

	public String getIsdelay() {
		return isdelay;
	}

	public void setIsdelay(String isdelay) {
		this.isdelay = isdelay;
	}

	public String getIsdispute() {
		return isdispute;
	}

	public void setIsdispute(String isdispute) {
		this.isdispute = isdispute;
	}

	public List<CMap> getCmaps() {
		return cmaps;
	}

	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	@Override
	public List<CLoan> init(JSONArray jarray) throws JSONException {
		List<CLoan> entitys = new ArrayList<CLoan>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));

		}
		return entitys;
	}

	@Override
	public CLoan init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private CLoan init(JSONObject jo) throws JSONException {
		CLoan entity = new CLoan();
		entity.setAmount(jo.optString(CEnum.amount.getKey()));
		entity.setEnddate(new Date(jo.optLong(CEnum.enddate.getKey())));
		entity.setFinanceagency(jo.optString(CEnum.financeagency.getKey()));
		entity.setIsclear(jo.optString(CEnum.isclear.getKey()));
		entity.setIsdelay(jo.optString(CEnum.isdelay.getKey()));
		entity.setIsdispute(jo.optString(CEnum.isdispute.getKey()));
		entity.setIswarrant(jo.optString(CEnum.iswarrant.getKey()));
		entity.setReason(jo.optString(CEnum.reason.getKey()));
		entity.setStartdate(new Date(jo.optLong(CEnum.startdate.getKey())));

		entity.setWarrantentityid(jo.optString(CEnum.warrantentityid.getKey()));
		entity.setWarrantentitytype(jo.optString(CEnum.warrantentitytype.getKey()));

		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}

	private List<CMap> initCmaps(CLoan entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.amount.getKey(), entity.getAmount(), CEnum.amount.getRemark()));
		cmaps.add(new CMap(CEnum.enddate.getKey(), entity.getEnddate(), CEnum.enddate.getRemark()));
		cmaps.add(new CMap(CEnum.financeagency.getKey(), entity.getFinanceagency(), CEnum.financeagency.getRemark()));
		cmaps.add(new CMap(CEnum.isclear.getKey(), entity.getIsclear(), CEnum.isclear.getRemark()));
		cmaps.add(new CMap(CEnum.isdelay.getKey(), entity.getIsdelay(), CEnum.isdelay.getRemark()));
		cmaps.add(new CMap(CEnum.isdispute.getKey(), entity.getIsdispute(), CEnum.isdispute.getRemark()));		
		cmaps.add(new CMap(CEnum.iswarrant.getKey(), entity.getIswarrant(), CEnum.iswarrant.getRemark()));
		cmaps.add(new CMap(CEnum.reason.getKey(), entity.getReason(), CEnum.reason.getRemark()));
		cmaps.add(new CMap(CEnum.startdate.getKey(), entity.getStartdate(), CEnum.startdate.getRemark()));
		cmaps.add(new CMap(CEnum.warrantentityid.getKey(), entity.getWarrantentityid(), CEnum.warrantentityid.getRemark()));
		cmaps.add(new CMap(CEnum.warrantentitytype.getKey(), entity.getWarrantentitytype(), CEnum.warrantentitytype.getRemark()));
		
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}