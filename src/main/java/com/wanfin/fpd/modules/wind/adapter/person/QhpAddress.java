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
 * 前海-地址通数据
 * @author srf
 * @version 2016-06-08
 */
public class QhpAddress implements IAdapter<QhpAddress>{
	private enum CEnum{
		//id, personid, resId, idno, idtype, name, seqno, mobileno, address, sourceid, province, cityinfo, borough, fmtaddress, longitude, latitude, buildingname, buildingaddress, housearodavgprice, houseavgprice, state, buildingtype, databuildtime, ismatch, addressporperty, ercode, ermsg, office;
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		idno("idno", "证件号码"),
		idtype("idtype", "证件类型"),
		
		name("name", "主体名称"),
		seqno("seqno", "序列号"),
		mobileno("mobileno", "手机号码"),
		address("address", "地址"),
		sourceid("sourceid", "来源代码"),
		province("province", "省（简称）"),
		cityinfo("cityinfo", "城市信息"),
		borough("borough", "区（简称）"),
		fmtaddress("fmtaddress", "格式化地址"),
		longitude("longitude", "经度"),
		latitude("latitude", "纬度"),
		buildingname("buildingname", "楼盘名称"),
		buildingaddress("buildingaddress", "楼盘地址"),
		housearodavgprice("housearodavgprice", "楼盘周边均价"),
		houseavgprice("houseavgprice", "楼盘均价"),
		state("state", "查询数据状态"),
		buildingtype("buildingtype", "建筑类型"),
		databuildtime("databuildtime", "业务发生时间"),
		ismatch("ismatch", "是否匹配"),
		addressporperty("addressporperty", "地址属性"),
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
	private String mobileno;		// 手机号码
	private String address;		// 地址
	private String sourceid;		// 来源代码
	private String province;		// 省（简称）
	private String cityinfo;		// 城市信息
	private String borough;		// 区（简称）
	private String fmtaddress;		// 格式化地址
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String buildingname;		// 楼盘名称
	private String buildingaddress;		// 楼盘地址
	private String housearodavgprice;		// 楼盘周边均价
	private String houseavgprice;		// 楼盘均价
	private String state;		// 查询数据状态
	private String buildingtype;		// 建筑类型
	private String databuildtime;		// 业务发生时间
	private String ismatch;		// 是否匹配
	private String addressporperty;		// 地址属性
	private String ercode;		// 错误代码
	private String ermsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpAddress() {
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

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityinfo() {
		return cityinfo;
	}

	public void setCityinfo(String cityinfo) {
		this.cityinfo = cityinfo;
	}

	public String getBorough() {
		return borough;
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}

	public String getFmtaddress() {
		return fmtaddress;
	}

	public void setFmtaddress(String fmtaddress) {
		this.fmtaddress = fmtaddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBuildingname() {
		return buildingname;
	}

	public void setBuildingname(String buildingname) {
		this.buildingname = buildingname;
	}

	public String getBuildingaddress() {
		return buildingaddress;
	}

	public void setBuildingaddress(String buildingaddress) {
		this.buildingaddress = buildingaddress;
	}

	public String getHousearodavgprice() {
		return housearodavgprice;
	}

	public void setHousearodavgprice(String housearodavgprice) {
		this.housearodavgprice = housearodavgprice;
	}

	public String getHouseavgprice() {
		return houseavgprice;
	}

	public void setHouseavgprice(String houseavgprice) {
		this.houseavgprice = houseavgprice;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBuildingtype() {
		return buildingtype;
	}

	public void setBuildingtype(String buildingtype) {
		this.buildingtype = buildingtype;
	}

	public String getDatabuildtime() {
		return databuildtime;
	}

	public void setDatabuildtime(String databuildtime) {
		this.databuildtime = databuildtime;
	}

	public String getIsmatch() {
		return ismatch;
	}

	public void setIsmatch(String ismatch) {
		this.ismatch = ismatch;
	}

	public String getAddressporperty() {
		return addressporperty;
	}

	public void setAddressporperty(String addressporperty) {
		this.addressporperty = addressporperty;
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
	public List<QhpAddress> init(JSONArray jarray) throws JSONException {
		List<QhpAddress> entitys = new ArrayList<QhpAddress>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpAddress init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpAddress init(JSONObject jo) throws JSONException {
		QhpAddress entity = new QhpAddress();
		entity.setAddress(jo.optString(CEnum.address.getKey()));
		entity.setAddressporperty(jo.optString(CEnum.addressporperty.getKey()));
		entity.setBorough(jo.optString(CEnum.borough.getKey()));
		entity.setBuildingaddress(jo.optString(CEnum.buildingaddress.getKey()));
		entity.setBuildingname(jo.optString(CEnum.buildingname.getKey()));
		entity.setBuildingtype(jo.optString(CEnum.buildingtype.getKey()));
		entity.setCityinfo(jo.optString(CEnum.cityinfo.getKey()));
		entity.setDatabuildtime(jo.optString(CEnum.databuildtime.getKey()));
		entity.setFmtaddress(jo.optString(CEnum.fmtaddress.getKey()));
		entity.setHousearodavgprice(jo.optString(CEnum.housearodavgprice.getKey()));
		entity.setHouseavgprice(jo.optString(CEnum.houseavgprice.getKey()));
		entity.setIdno(jo.optString(CEnum.idno.getKey()));
		entity.setIdtype(jo.optString(CEnum.idtype.getKey()));
		entity.setIsmatch(jo.optString(CEnum.ismatch.getKey()));
		entity.setLatitude(jo.optString(CEnum.latitude.getKey()));
		entity.setLongitude(jo.optString(CEnum.longitude.getKey()));
		entity.setMobileno(jo.optString(CEnum.mobileno.getKey()));
		entity.setName(jo.optString(CEnum.name.getKey()));
		entity.setSourceid(jo.optString(CEnum.sourceid.getKey()));
		entity.setState(jo.optString(CEnum.state.getKey()));

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
	
	private List<CMap> initCmaps(QhpAddress entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.address.getKey(), entity.getAddress(), CEnum.address.getRemark()));
		cmaps.add(new CMap(CEnum.addressporperty.getKey(), entity.getAddressporperty(), CEnum.addressporperty.getRemark()));
		cmaps.add(new CMap(CEnum.borough.getKey(), entity.getBorough(), CEnum.borough.getRemark()));
		cmaps.add(new CMap(CEnum.buildingaddress.getKey(), entity.getBuildingaddress(), CEnum.buildingaddress.getRemark()));
		cmaps.add(new CMap(CEnum.buildingname.getKey(), entity.getBuildingname(), CEnum.buildingname.getRemark()));
		cmaps.add(new CMap(CEnum.buildingtype.getKey(), entity.getBuildingtype(), CEnum.buildingtype.getRemark()));
		cmaps.add(new CMap(CEnum.cityinfo.getKey(), entity.getCityinfo(), CEnum.cityinfo.getRemark()));
		cmaps.add(new CMap(CEnum.databuildtime.getKey(), entity.getDatabuildtime(), CEnum.databuildtime.getRemark()));
		cmaps.add(new CMap(CEnum.fmtaddress.getKey(), entity.getFmtaddress(), CEnum.fmtaddress.getRemark()));
		cmaps.add(new CMap(CEnum.housearodavgprice.getKey(), entity.getHousearodavgprice(), CEnum.housearodavgprice.getRemark()));
		cmaps.add(new CMap(CEnum.houseavgprice.getKey(), entity.getHouseavgprice(), CEnum.houseavgprice.getRemark()));
		cmaps.add(new CMap(CEnum.idno.getKey(), entity.getIdno(), CEnum.idno.getRemark()));
		cmaps.add(new CMap(CEnum.idtype.getKey(), entity.getIdtype(), CEnum.idtype.getRemark()));
		cmaps.add(new CMap(CEnum.ismatch.getKey(), entity.getIsmatch(), CEnum.ismatch.getRemark()));
		cmaps.add(new CMap(CEnum.latitude.getKey(), entity.getLatitude(), CEnum.latitude.getRemark()));
		cmaps.add(new CMap(CEnum.longitude.getKey(), entity.getLongitude(), CEnum.longitude.getRemark()));
		cmaps.add(new CMap(CEnum.mobileno.getKey(), entity.getMobileno(), CEnum.mobileno.getRemark()));
		cmaps.add(new CMap(CEnum.name.getKey(), entity.getName(), CEnum.name.getRemark()));
		cmaps.add(new CMap(CEnum.province.getKey(), entity.getProvince(), CEnum.province.getRemark()));
		cmaps.add(new CMap(CEnum.sourceid.getKey(), entity.getSourceid(), CEnum.sourceid.getRemark()));
		cmaps.add(new CMap(CEnum.state.getKey(), entity.getState(), CEnum.state.getRemark()));

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