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
 * 公司基本信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CCorp implements IAdapter<CCorp>{
	private enum CEnum{ 
//		id, businesslicensenumber, companyname, controltype, entitytype, registeredcapital, registeredaddress, businessaddress, builddate, cooperationtime, paidcapital, mainbusiness, postalcode, fax, phone, paidcurrencytype, registeredcurrencytype, ismaxstockholder, companytype, legalrepresentative, operatingstart, operatingend, registrationAuthority, approveddate, registstatus; 
		id("id", "主键"),

		businesslicensenumber("businesslicensenumber", "工商注册号"),
		companyname("companyname", "企业名称"),
		controltype("controltype", "控制人性质"),
		entitytype("entitytype", "法人性质"),
		registeredcapital("registeredcapital", "注册资本"),
		registeredaddress("registeredaddress", "注册地址"),
		businessaddress("businessaddress", "办公地址"),
		builddate("builddate", "建立时间"),
		cooperationtime("cooperationtime", "首次合作时间"),
		paidcapital("paidcapital", "实收资本"),
		mainbusiness("mainbusiness", "主营业务及品牌"),
		postalcode("postalcode", "邮编"),
		fax("fax", "传真"),
		phone("phone", "联系电话"),
		paidcurrencytype("paidcurrencytype", "实收资本币种"),
		registeredcurrencytype("registeredcurrencytype", "注册资本币种"),
		ismaxstockholder("ismaxstockholder", "是否最大股东"),
		companytype("companytype", "公司类型"),
		legalrepresentative("legalrepresentative", "法定代表人或负责人"),
		operatingstart("operatingstart", "营业期限自"),
		operatingend("operatingend", "营业期限至"),
		registrationAuthority("registrationAuthority", "登记机关"),
		approveddate("approveddate", "核准日期"),
		registstatus("registstatus", "登记状态"); 
		
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
	private String businesslicensenumber;		// 工商注册号
	private String companyname;		// 企业名称
	private String controltype;		// 控制人性质
	private String entitytype;		// 法人性质
	private String registeredcapital;		// 注册资本
	private String registeredaddress;		// 注册地址
	private String businessaddress;		// 办公地址
	private Date builddate;		// 建立时间
	private Date cooperationtime;		// 首次合作时间
	private String paidcapital;		// 实收资本
	private String mainbusiness;		// 主营业务及品牌
	private String postalcode;		// 邮编
	private String fax;		// 传真
	private String phone;		// 联系电话
	private String paidcurrencytype;		// 实收资本币种
	private String registeredcurrencytype;		// 注册资本币种
	private String ismaxstockholder;		// 是否最大股东
	
	private String companytype;		// 公司类型
	private String legalrepresentative;		// 法定代表人或负责人
	private String operatingstart;		// 营业期限自
	private String operatingend;		// 营业期限至
	private String registrationAuthority;		// 登记机关
	private Date approveddate;		// 核准日期
	private String registstatus;		// 登记状态

	private List<CMap> cmaps;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinesslicensenumber() {
		return businesslicensenumber;
	}
	public void setBusinesslicensenumber(String businesslicensenumber) {
		this.businesslicensenumber = businesslicensenumber;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getControltype() {
		return controltype;
	}
	public void setControltype(String controltype) {
		this.controltype = controltype;
	}
	public String getEntitytype() {
		return entitytype;
	}
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}
	public String getRegisteredcapital() {
		return registeredcapital;
	}
	public void setRegisteredcapital(String registeredcapital) {
		this.registeredcapital = registeredcapital;
	}
	public String getRegisteredaddress() {
		return registeredaddress;
	}
	public void setRegisteredaddress(String registeredaddress) {
		this.registeredaddress = registeredaddress;
	}
	public String getBusinessaddress() {
		return businessaddress;
	}
	public void setBusinessaddress(String businessaddress) {
		this.businessaddress = businessaddress;
	}
	public Date getBuilddate() {
		return builddate;
	}
	public void setBuilddate(Date builddate) {
		this.builddate = builddate;
	}
	public Date getCooperationtime() {
		return cooperationtime;
	}
	public void setCooperationtime(Date cooperationtime) {
		this.cooperationtime = cooperationtime;
	}
	public String getPaidcapital() {
		return paidcapital;
	}
	public void setPaidcapital(String paidcapital) {
		this.paidcapital = paidcapital;
	}
	public String getMainbusiness() {
		return mainbusiness;
	}
	public void setMainbusiness(String mainbusiness) {
		this.mainbusiness = mainbusiness;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPaidcurrencytype() {
		return paidcurrencytype;
	}
	public void setPaidcurrencytype(String paidcurrencytype) {
		this.paidcurrencytype = paidcurrencytype;
	}
	public String getRegisteredcurrencytype() {
		return registeredcurrencytype;
	}
	public void setRegisteredcurrencytype(String registeredcurrencytype) {
		this.registeredcurrencytype = registeredcurrencytype;
	}
	public String getIsmaxstockholder() {
		return ismaxstockholder;
	}
	public void setIsmaxstockholder(String ismaxstockholder) {
		this.ismaxstockholder = ismaxstockholder;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getLegalrepresentative() {
		return legalrepresentative;
	}
	public void setLegalrepresentative(String legalrepresentative) {
		this.legalrepresentative = legalrepresentative;
	}
	public String getOperatingstart() {
		return operatingstart;
	}
	public void setOperatingstart(String operatingstart) {
		this.operatingstart = operatingstart;
	}
	public String getOperatingend() {
		return operatingend;
	}
	public void setOperatingend(String operatingend) {
		this.operatingend = operatingend;
	}
	public String getRegistrationAuthority() {
		return registrationAuthority;
	}
	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}
	public Date getApproveddate() {
		return approveddate;
	}
	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}
	public String getRegiststatus() {
		return registstatus;
	}
	public void setRegiststatus(String registstatus) {
		this.registstatus = registstatus;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	public CCorp init(String jarray) throws JSONException {
		return init(new JSONObject(jarray));
	}
	
	@Override
	public List<CCorp> init(JSONArray jarray) throws JSONException {
		List<CCorp> entitys = new ArrayList<CCorp>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
		}
		return entitys;
	}
	
	private CCorp init(JSONObject jo) throws JSONException {
		CCorp entity = new CCorp();
		entity.setApproveddate(new Date(jo.optLong(CEnum.approveddate.getKey())));
		entity.setBuilddate(new Date(jo.optLong(CEnum.builddate.getKey())));
		entity.setBusinessaddress(jo.optString(CEnum.businessaddress.getKey()));
		entity.setBusinesslicensenumber(jo.optString(CEnum.businesslicensenumber.getKey()));
		entity.setCompanyname(jo.optString(CEnum.companyname.getKey()));
		entity.setCompanytype(jo.optString(CEnum.companytype.getKey()));
		
		entity.setControltype(jo.optString(CEnum.controltype.getKey()));
		entity.setCooperationtime(new Date(jo.optLong(CEnum.cooperationtime.getKey())));
		entity.setEntitytype(jo.optString(CEnum.entitytype.getKey()));
		entity.setFax(jo.optString(CEnum.fax.getKey()));
		entity.setIsmaxstockholder(jo.optString(CEnum.ismaxstockholder.getKey()));
		entity.setLegalrepresentative(jo.optString(CEnum.legalrepresentative.getKey()));
		entity.setMainbusiness(jo.optString(CEnum.mainbusiness.getKey()));
		entity.setOperatingend(jo.optString(CEnum.operatingend.getKey()));
		entity.setOperatingstart(jo.optString(CEnum.operatingstart.getKey()));
		entity.setPaidcapital(jo.optString(CEnum.paidcapital.getKey()));
		entity.setPaidcurrencytype(jo.optString(CEnum.paidcurrencytype.getKey()));
		entity.setPhone(jo.optString(CEnum.phone.getKey()));
		entity.setPostalcode(jo.optString(CEnum.postalcode.getKey()));
		
		entity.setRegisteredaddress(jo.optString(CEnum.registeredaddress.getKey()));
		entity.setRegisteredcapital(jo.optString(CEnum.registeredcapital.getKey()));
		entity.setRegisteredcurrencytype(jo.optString(CEnum.registeredcurrencytype.getKey()));
		entity.setRegistrationAuthority(jo.optString(CEnum.registrationAuthority.getKey()));
		entity.setRegiststatus(jo.optString(CEnum.registstatus.getKey()));
		
		entity.setId(jo.optString(CEnum.id.getKey()));
		
		entity.setCmaps(initCmaps(entity));
		return entity;
	}

	private List<CMap> initCmaps(CCorp entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.approveddate.getKey(), entity.getApproveddate(), CEnum.approveddate.getRemark()));
		cmaps.add(new CMap(CEnum.builddate.getKey(), entity.getBuilddate(), CEnum.builddate.getRemark()));
		cmaps.add(new CMap(CEnum.businessaddress.getKey(), entity.getBusinessaddress(), CEnum.businessaddress.getRemark()));
		cmaps.add(new CMap(CEnum.businesslicensenumber.getKey(), entity.getBusinesslicensenumber(), CEnum.businesslicensenumber.getRemark()));
		cmaps.add(new CMap(CEnum.companyname.getKey(), entity.getCompanyname(), CEnum.companyname.getRemark()));
		cmaps.add(new CMap(CEnum.companytype.getKey(), entity.getCompanytype(), CEnum.companytype.getRemark()));		
		cmaps.add(new CMap(CEnum.controltype.getKey(), entity.getControltype(), CEnum.controltype.getRemark()));
		cmaps.add(new CMap(CEnum.cooperationtime.getKey(), entity.getCooperationtime(), CEnum.cooperationtime.getRemark()));
		cmaps.add(new CMap(CEnum.entitytype.getKey(), entity.getEntitytype(), CEnum.entitytype.getRemark()));
		cmaps.add(new CMap(CEnum.fax.getKey(), entity.getFax(), CEnum.fax.getRemark()));
		cmaps.add(new CMap(CEnum.ismaxstockholder.getKey(), entity.getIsmaxstockholder(), CEnum.ismaxstockholder.getRemark()));
		cmaps.add(new CMap(CEnum.legalrepresentative.getKey(), entity.getLegalrepresentative(), CEnum.legalrepresentative.getRemark()));
		cmaps.add(new CMap(CEnum.mainbusiness.getKey(), entity.getMainbusiness(), CEnum.mainbusiness.getRemark()));
		cmaps.add(new CMap(CEnum.operatingend.getKey(), entity.getOperatingend(), CEnum.operatingend.getRemark()));
		cmaps.add(new CMap(CEnum.operatingstart.getKey(), entity.getOperatingstart(), CEnum.operatingstart.getRemark()));
		cmaps.add(new CMap(CEnum.paidcapital.getKey(), entity.getPaidcapital(), CEnum.paidcapital.getRemark()));
		cmaps.add(new CMap(CEnum.paidcurrencytype.getKey(), entity.getPaidcurrencytype(), CEnum.paidcurrencytype.getRemark()));
		cmaps.add(new CMap(CEnum.phone.getKey(), entity.getPhone(), CEnum.phone.getRemark()));
		cmaps.add(new CMap(CEnum.postalcode.getKey(), entity.getPostalcode(), CEnum.postalcode.getRemark()));
		cmaps.add(new CMap(CEnum.registeredaddress.getKey(), entity.getRegisteredaddress(), CEnum.registeredaddress.getRemark()));
		cmaps.add(new CMap(CEnum.registeredcapital.getKey(), entity.getRegisteredcapital(), CEnum.registeredcapital.getRemark()));
		cmaps.add(new CMap(CEnum.registeredcurrencytype.getKey(), entity.getRegisteredcurrencytype(), CEnum.registeredcurrencytype.getRemark()));
		cmaps.add(new CMap(CEnum.registrationAuthority.getKey(), entity.getRegistrationAuthority(), CEnum.registrationAuthority.getRemark()));
		cmaps.add(new CMap(CEnum.registstatus.getKey(), entity.getRegiststatus(), CEnum.registstatus.getRemark()));
		
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}