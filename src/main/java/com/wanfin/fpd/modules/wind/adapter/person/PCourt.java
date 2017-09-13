/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 用户法院信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class PCourt implements IAdapter<PCourt>{
//	private enum CEnum{ id, personid, casedate, caseno, courtname, amount, reason, executetype, attachment, area, gistid, duty, performance, dishonestybehavior; }
	private enum CEnum{ 
		id("id", "主键"), 
		personid("personid", "个人主键"), 
		casedate("casedate", "立案日期"), 
		caseno("caseno", "案件号"), 
		courtname("courtname", "执行法院"), 
		amount("amount", "金额"), 
		reason("reason", "事由"), 
		executetype("executetype", "执行类型"), 
		attachment("attachment", "凭证"), 
		area("area", "省份"), 
		gistid("gistid", "执行依据文号"), 
		duty("duty", "生效法律文书确定的义务 "), 
		performance("performance", "被执行人的履行情况  "), 
		dishonestybehavior("dishonestybehavior", "失信被执行人行为具体情况"); 
	
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
	private Date casedate;		// 立案日期
	private String caseno;		// 案件号
	private String courtname;		// 执行法院
	private String amount;		// 金额
	private String reason;		// 事由
	private String executetype;		// 执行类型
	private String attachment;		// 凭证
	
	private String area;   //省份  
	private String gistid;//执行依据文号 
	private String duty;//生效法律文书确定的义务 
	private String performance;//被执行人的履行情况  
	private String dishonestybehavior;//失信被执行人行为具体情况

	private List<CMap> cmaps;
	
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
	public Date getCasedate() {
		return casedate;
	}
	public void setCasedate(Date casedate) {
		this.casedate = casedate;
	}
	public String getCaseno() {
		return caseno;
	}
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	public String getCourtname() {
		return courtname;
	}
	public void setCourtname(String courtname) {
		this.courtname = courtname;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getExecutetype() {
		return executetype;
	}
	public void setExecutetype(String executetype) {
		this.executetype = executetype;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGistid() {
		return gistid;
	}
	public void setGistid(String gistid) {
		this.gistid = gistid;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public String getDishonestybehavior() {
		return dishonestybehavior;
	}
	public void setDishonestybehavior(String dishonestybehavior) {
		this.dishonestybehavior = dishonestybehavior;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	
	@Override
	public List<PCourt> init(JSONArray jarray) throws JSONException {
		List<PCourt> entitys = new ArrayList<PCourt>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}

	@Override
	public PCourt init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PCourt init(JSONObject jo) throws JSONException {
		PCourt entity = new PCourt();
		entity.setAmount(jo.optString(CEnum.amount.getKey()));
		entity.setArea(jo.optString(CEnum.area.getKey()));
		entity.setAttachment(jo.optString(CEnum.attachment.getKey()));

		entity.setCasedate(new Date(jo.optLong(CEnum.casedate.getKey())));
		entity.setCaseno(jo.optString(CEnum.caseno.getKey()));
		entity.setCourtname(jo.optString(CEnum.courtname.getKey()));

		entity.setDishonestybehavior(jo.optString(CEnum.dishonestybehavior.getKey()));
		entity.setDuty(jo.optString(CEnum.duty.getKey()));
		entity.setExecutetype(jo.optString(CEnum.executetype.getKey()));
		entity.setGistid(jo.optString(CEnum.gistid.getKey()));
		entity.setPerformance(jo.optString(CEnum.performance.getKey()));
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setReason(jo.optString(CEnum.reason.getKey()));

		entity.setReason(jo.optString(CEnum.reason.getKey()));

		entity.setId(jo.optString(CEnum.id.getKey()));
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		
		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(PCourt entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.casedate.getKey(), entity.getCasedate(), CEnum.casedate.getRemark()));
		cmaps.add(new CMap(CEnum.caseno.getKey(), entity.getCaseno(), CEnum.caseno.getRemark()));
		cmaps.add(new CMap(CEnum.courtname.getKey(), entity.getCourtname(), CEnum.courtname.getRemark()));
		cmaps.add(new CMap(CEnum.amount.getKey(), entity.getAmount(), CEnum.amount.getRemark()));
		cmaps.add(new CMap(CEnum.reason.getKey(), entity.getReason(), CEnum.reason.getRemark()));
		cmaps.add(new CMap(CEnum.executetype.getKey(), entity.getExecutetype(), CEnum.executetype.getRemark()));
		cmaps.add(new CMap(CEnum.attachment.getKey(), entity.getAttachment(), CEnum.attachment.getRemark()));
		cmaps.add(new CMap(CEnum.area.getKey(), entity.getArea(), CEnum.area.getRemark()));
		cmaps.add(new CMap(CEnum.gistid.getKey(), entity.getGistid(), CEnum.gistid.getRemark()));
		cmaps.add(new CMap(CEnum.duty.getKey(), entity.getDuty(), CEnum.duty.getRemark()));
		cmaps.add(new CMap(CEnum.performance.getKey(), entity.getPerformance(), CEnum.performance.getRemark()));
		cmaps.add(new CMap(CEnum.dishonestybehavior.getKey(), entity.getDishonestybehavior(), CEnum.dishonestybehavior.getRemark()));
		
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		return cmaps;
	}
	
	public static void main(String[] args) throws JSONException {
		String s= "{'zz':1453046400000}";
		JSONObject so = new JSONObject(s);
		System.out.println(so.optString("zz"));
		System.out.println(new Date(so.optLong("zz")));
	}
}