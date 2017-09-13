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
 * 用户基本信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class PPersion implements IAdapter<PPersion>{
	private enum CEnum{
//		id, name, idnumber, idstartdate, idenddate, idvalidity, homeaddress, bookletaddress, mobiletelephone, phone, maritalstatus, cardfileid, bookletfileid, maritalfileid, ismaxstockholder; 
		
		id("id", "主键"),
		name("name", "姓名"),
		idnumber("idnumber", "身份证号"),
		idstartdate("idstartdate", "身份证开始日期"),
		idenddate("idenddate", "身份证截止日期"),
		idvalidity("idvalidity", "身份证有效期"),
		homeaddress("homeaddress", "常住地址"),
		bookletaddress("bookletaddress", "户籍地址"),
		mobiletelephone("mobiletelephone", "移动电话"),
		phone("phone", "固定电话"),
		maritalstatus("maritalstatus", "婚姻状况"),
		cardfileid("cardfileid", "身份证附件"),
		bookletfileid("bookletfileid", "户口簿附件"),
		maritalfileid("maritalfileid", "婚姻附件"),
		ismaxstockholder("ismaxstockholder", "是否最大股东"); 
		
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
	private String name;		// 姓名
	private String idnumber;		// 身份证号
	private Date idstartdate;		// 身份证开始日期
	private Date idenddate;		// 身份证截止日期
	private String idvalidity;		// 身份证有效期
	private String homeaddress;		// 常住地址
	private String bookletaddress;		// 户籍地址
	private String mobiletelephone;		// 移动电话
	private String phone;		// 固定电话
	private String maritalstatus;		// 婚姻状况
	private String cardfileid;		// 身份证附件
	private String bookletfileid;		// 户口簿附件
	private String maritalfileid;		// 婚姻附件
	private String ismaxstockholder;		// 是否最大股东
	private List<CMap> cmaps;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public Date getIdstartdate() {
		return idstartdate;
	}
	public void setIdstartdate(Date idstartdate) {
		this.idstartdate = idstartdate;
	}
	public Date getIdenddate() {
		return idenddate;
	}
	public void setIdenddate(Date idenddate) {
		this.idenddate = idenddate;
	}
	public String getIdvalidity() {
		return idvalidity;
	}
	public void setIdvalidity(String idvalidity) {
		this.idvalidity = idvalidity;
	}
	public String getHomeaddress() {
		return homeaddress;
	}
	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}
	public String getBookletaddress() {
		return bookletaddress;
	}
	public void setBookletaddress(String bookletaddress) {
		this.bookletaddress = bookletaddress;
	}
	public String getMobiletelephone() {
		return mobiletelephone;
	}
	public void setMobiletelephone(String mobiletelephone) {
		this.mobiletelephone = mobiletelephone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMaritalstatus() {
		return maritalstatus;
	}
	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}
	public String getCardfileid() {
		return cardfileid;
	}
	public void setCardfileid(String cardfileid) {
		this.cardfileid = cardfileid;
	}
	public String getBookletfileid() {
		return bookletfileid;
	}
	public void setBookletfileid(String bookletfileid) {
		this.bookletfileid = bookletfileid;
	}
	public String getMaritalfileid() {
		return maritalfileid;
	}
	public void setMaritalfileid(String maritalfileid) {
		this.maritalfileid = maritalfileid;
	}
	public String getIsmaxstockholder() {
		return ismaxstockholder;
	}
	public void setIsmaxstockholder(String ismaxstockholder) {
		this.ismaxstockholder = ismaxstockholder;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	@Override
	public List<PPersion> init(JSONArray jarray) throws JSONException {
		List<PPersion> entitys = new ArrayList<PPersion>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public PPersion init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PPersion init(JSONObject jo) throws JSONException {
		PPersion entity = new PPersion();
		entity.setBookletaddress(jo.optString(CEnum.bookletaddress.getKey()));
		entity.setBookletfileid(jo.optString(CEnum.bookletfileid.getKey()));
		entity.setCardfileid(jo.optString(CEnum.cardfileid.getKey()));
		entity.setHomeaddress(jo.optString(CEnum.homeaddress.getKey()));
		entity.setIdenddate(new Date(jo.optLong(CEnum.idenddate.getKey())));
		entity.setIdnumber(jo.optString(CEnum.idnumber.getKey()));
		entity.setIdstartdate(new Date(jo.optLong(CEnum.idstartdate.getKey())));
		
		entity.setIdvalidity(jo.optString(CEnum.idvalidity.getKey()));
		entity.setIsmaxstockholder(jo.optString(CEnum.ismaxstockholder.getKey()));
		
		entity.setMaritalfileid(jo.optString(CEnum.maritalfileid.getKey()));
		entity.setMaritalstatus(jo.optString(CEnum.maritalstatus.getKey()));
		entity.setMobiletelephone(jo.optString(CEnum.mobiletelephone.getKey()));
		entity.setName(jo.optString(CEnum.name.getKey()));
		entity.setPhone(jo.optString(CEnum.phone.getKey()));
		
		entity.setId(jo.optString(CEnum.id.getKey()));
		
		entity.setCmaps(initCmaps(entity));
		return entity;
	}

	private List<CMap> initCmaps(PPersion entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.bookletaddress.getKey(), entity.getBookletaddress(), CEnum.bookletaddress.getRemark()));
		cmaps.add(new CMap(CEnum.bookletfileid.getKey(), entity.getBookletfileid(), CEnum.bookletfileid.getRemark()));
		cmaps.add(new CMap(CEnum.cardfileid.getKey(), entity.getCardfileid(), CEnum.cardfileid.getRemark()));
		cmaps.add(new CMap(CEnum.homeaddress.getKey(), entity.getHomeaddress(), CEnum.homeaddress.getRemark()));
		cmaps.add(new CMap(CEnum.idenddate.getKey(), entity.getIdenddate(), CEnum.idenddate.getRemark()));
		cmaps.add(new CMap(CEnum.idnumber.getKey(), entity.getIdnumber(), CEnum.idnumber.getRemark()));
		cmaps.add(new CMap(CEnum.idstartdate.getKey(), entity.getIdstartdate(), CEnum.idstartdate.getRemark()));
		cmaps.add(new CMap(CEnum.idvalidity.getKey(), entity.getIdvalidity(), CEnum.idvalidity.getRemark()));
		cmaps.add(new CMap(CEnum.ismaxstockholder.getKey(), entity.getIsmaxstockholder(), CEnum.ismaxstockholder.getRemark()));
		cmaps.add(new CMap(CEnum.maritalfileid.getKey(), entity.getMaritalfileid(), CEnum.maritalfileid.getRemark()));
		cmaps.add(new CMap(CEnum.maritalstatus.getKey(), entity.getMaritalstatus(), CEnum.maritalstatus.getRemark()));
		cmaps.add(new CMap(CEnum.mobiletelephone.getKey(), entity.getMobiletelephone(), CEnum.mobiletelephone.getRemark()));
		cmaps.add(new CMap(CEnum.name.getKey(), entity.getName(), CEnum.name.getRemark()));
		cmaps.add(new CMap(CEnum.phone.getKey(), entity.getPhone(), CEnum.phone.getRemark()));

//		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}