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
 * 前海-好信一鉴通--人脸识别
 * @author srf
 * @version 2016-06-08
 */
public class QhpValidateFace implements IAdapter<QhpValidateFace>{
	private enum CEnum{
		//id, personid, resId, seqno, idno, idtype, name, mobileno, isrealidentity, isvalidaddress, addresstype, 
		
		//isrealcompany, companysimdeg, appear1thdate, appearlastdate, isownermobile, ownermobilestatus, usetimescore, 
		//isexistrel, rellevel, carchkresult, isasyned, cartypeinc, carfactoryinc, carprice, 
		//drvstatusqryid, housechkresult, housedatadate, photocmpresult, photosimdeg, ishighestedubkg, datadate, 
		
		//graduateschool, graduatemajor, graduateyear, isownermobiletwo, ownermobilestatustwo, usetimescoretwo, errorinfo, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqno("seqno", "序列号"),
		
		idno("idno", "证件号码"),
		idtype("idtype", "证件类型"),
		name("name", "名称"),
		mobileno("idno", "手机号码"),
		isrealidentity("isrealidentity", "是否真实身份"),
		isvalidaddress("isvalidaddress", "是否本人真实活动地址"),
		addresstype("addresstype", "地址类型"),
		isrealcompany("isrealcompany", "是否真实工作单位"),
		companysimdeg("companysimdeg", "单位匹配度分值"),
		appear1thdate("appear1thdate", "单位最初出现时间"),
		appearlastdate("appearlastdate", "单位最近一次出现时间"),
		isownermobile("isownermobile", "手机验证结果"),
		ownermobilestatus("ownermobilestatus", "手机状态"),
		usetimescore("usetimescore", "使用时间分数"),
		isexistrel("isexistrel", "是否存在关系"),
		rellevel("rellevel", "关系力度"),
		carchkresult("carchkresult", "车辆验证结果"),
		isasyned("isasyned", "是否异步返回结果"),
		cartypeinc("cartypeinc", "车型"),
		carfactoryinc("carfactoryinc", "厂牌"),
		carprice("carprice", "新车购置价"),
		drvstatusqryid("drvstatusqryid", "行驶证状态查询Id号"),
		housechkresult("housechkresult", "房屋验证结果"),
		housedatadate("housedatadate", "房屋数据获取时间"),
		photocmpresult("photocmpresult", "相片比对结果"),
		photosimdeg("photosimdeg", "相片相似度"),
		ishighestedubkg("ishighestedubkg", "是否本人真实最高学历"),
		datadate("datadate", "数据获取时间"),
		graduateschool("graduateschool", "毕业院校"),
		graduatemajor("graduatemajor", "毕业专业"),
		graduateyear("graduateyear", "毕业年份"),
		isownermobiletwo("isownermobiletwo", "手机状态II"),
		usetimescoretwo("usetimescoretwo", "使用时间分数II"),
		errorinfo("errorinfo", "错误信息"),
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
	private String seqno;		// 序列号
	private String idno;		// 证件号码
	private String idtype;		// 证件类型
	private String name;		// 主体名称
	private String mobileno;		// 手机号码
	private String isrealidentity;		// 是否真实身份
	private String isvalidaddress;		// 是否本人真实活动地址
	private String addresstype;		// 地址类型
	private String isrealcompany;		// 是否真实工作单位
	private String companysimdeg;		// 单位匹配度分值
	private String appear1thdate;		// 单位最初出现时间
	private String appearlastdate;		// 单位最近一次出现时间
	private String isownermobile;		// 手机验证结果
	private String ownermobilestatus;		// 手机状态
	private String usetimescore;		// 使用时间分数
	private String isexistrel;		// 是否存在关系
	private String rellevel;		// 关系力度
	private String carchkresult;		// 车辆验证结果
	private String isasyned;		// 是否异步返回结果
	private String cartypeinc;		// 车型
	private String carfactoryinc;		// 厂牌
	private String carprice;		// 新车购置价
	private String drvstatusqryid;		// 行驶证状态查询Id号
	private String housechkresult;		// 房屋验证结果
	private String housedatadate;		// 房屋数据获取时间
	private String photocmpresult;		// 相片比对结果
	private String photosimdeg;		// 相片相似度
	private String ishighestedubkg;		// 是否本人真实最高学历
	private String datadate;		// 数据获取时间
	private String graduateschool;		// 毕业院校
	private String graduatemajor;		// 毕业专业
	private String graduateyear;		// 毕业年份
	private String isownermobiletwo;		// 手机验证II结果
	private String ownermobilestatustwo;		// 手机状态II
	private String usetimescoretwo;		// 使用时间分数II
	private String errorinfo;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpValidateFace() {
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

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getIsrealidentity() {
		return isrealidentity;
	}

	public void setIsrealidentity(String isrealidentity) {
		this.isrealidentity = isrealidentity;
	}

	public String getIsvalidaddress() {
		return isvalidaddress;
	}

	public void setIsvalidaddress(String isvalidaddress) {
		this.isvalidaddress = isvalidaddress;
	}

	public String getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(String addresstype) {
		this.addresstype = addresstype;
	}

	public String getIsrealcompany() {
		return isrealcompany;
	}

	public void setIsrealcompany(String isrealcompany) {
		this.isrealcompany = isrealcompany;
	}

	public String getCompanysimdeg() {
		return companysimdeg;
	}

	public void setCompanysimdeg(String companysimdeg) {
		this.companysimdeg = companysimdeg;
	}

	public String getAppear1thdate() {
		return appear1thdate;
	}

	public void setAppear1thdate(String appear1thdate) {
		this.appear1thdate = appear1thdate;
	}

	public String getAppearlastdate() {
		return appearlastdate;
	}

	public void setAppearlastdate(String appearlastdate) {
		this.appearlastdate = appearlastdate;
	}

	public String getIsownermobile() {
		return isownermobile;
	}

	public void setIsownermobile(String isownermobile) {
		this.isownermobile = isownermobile;
	}

	public String getOwnermobilestatus() {
		return ownermobilestatus;
	}

	public void setOwnermobilestatus(String ownermobilestatus) {
		this.ownermobilestatus = ownermobilestatus;
	}

	public String getUsetimescore() {
		return usetimescore;
	}

	public void setUsetimescore(String usetimescore) {
		this.usetimescore = usetimescore;
	}

	public String getIsexistrel() {
		return isexistrel;
	}

	public void setIsexistrel(String isexistrel) {
		this.isexistrel = isexistrel;
	}

	public String getRellevel() {
		return rellevel;
	}

	public void setRellevel(String rellevel) {
		this.rellevel = rellevel;
	}

	public String getCarchkresult() {
		return carchkresult;
	}

	public void setCarchkresult(String carchkresult) {
		this.carchkresult = carchkresult;
	}

	public String getIsasyned() {
		return isasyned;
	}

	public void setIsasyned(String isasyned) {
		this.isasyned = isasyned;
	}

	public String getCartypeinc() {
		return cartypeinc;
	}

	public void setCartypeinc(String cartypeinc) {
		this.cartypeinc = cartypeinc;
	}

	public String getCarfactoryinc() {
		return carfactoryinc;
	}

	public void setCarfactoryinc(String carfactoryinc) {
		this.carfactoryinc = carfactoryinc;
	}

	public String getCarprice() {
		return carprice;
	}

	public void setCarprice(String carprice) {
		this.carprice = carprice;
	}

	public String getDrvstatusqryid() {
		return drvstatusqryid;
	}

	public void setDrvstatusqryid(String drvstatusqryid) {
		this.drvstatusqryid = drvstatusqryid;
	}

	public String getHousechkresult() {
		return housechkresult;
	}

	public void setHousechkresult(String housechkresult) {
		this.housechkresult = housechkresult;
	}

	public String getHousedatadate() {
		return housedatadate;
	}

	public void setHousedatadate(String housedatadate) {
		this.housedatadate = housedatadate;
	}

	public String getPhotocmpresult() {
		return photocmpresult;
	}

	public void setPhotocmpresult(String photocmpresult) {
		this.photocmpresult = photocmpresult;
	}

	public String getPhotosimdeg() {
		return photosimdeg;
	}

	public void setPhotosimdeg(String photosimdeg) {
		this.photosimdeg = photosimdeg;
	}

	public String getIshighestedubkg() {
		return ishighestedubkg;
	}

	public void setIshighestedubkg(String ishighestedubkg) {
		this.ishighestedubkg = ishighestedubkg;
	}

	public String getDatadate() {
		return datadate;
	}

	public void setDatadate(String datadate) {
		this.datadate = datadate;
	}

	public String getGraduateschool() {
		return graduateschool;
	}

	public void setGraduateschool(String graduateschool) {
		this.graduateschool = graduateschool;
	}

	public String getGraduatemajor() {
		return graduatemajor;
	}

	public void setGraduatemajor(String graduatemajor) {
		this.graduatemajor = graduatemajor;
	}

	public String getGraduateyear() {
		return graduateyear;
	}

	public void setGraduateyear(String graduateyear) {
		this.graduateyear = graduateyear;
	}

	public String getIsownermobiletwo() {
		return isownermobiletwo;
	}

	public void setIsownermobiletwo(String isownermobiletwo) {
		this.isownermobiletwo = isownermobiletwo;
	}

	public String getOwnermobilestatustwo() {
		return ownermobilestatustwo;
	}

	public void setOwnermobilestatustwo(String ownermobilestatustwo) {
		this.ownermobilestatustwo = ownermobilestatustwo;
	}

	public String getUsetimescoretwo() {
		return usetimescoretwo;
	}

	public void setUsetimescoretwo(String usetimescoretwo) {
		this.usetimescoretwo = usetimescoretwo;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
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
	public List<QhpValidateFace> init(JSONArray jarray) throws JSONException {
		List<QhpValidateFace> entitys = new ArrayList<QhpValidateFace>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpValidateFace init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpValidateFace init(JSONObject jo) throws JSONException {
		QhpValidateFace entity = new QhpValidateFace();
		entity.setAddresstype(jo.optString(CEnum.addresstype.getKey()));
		entity.setAppear1thdate(jo.optString(CEnum.appear1thdate.getKey()));
		entity.setAppearlastdate(jo.optString(CEnum.appearlastdate.getKey()));
		entity.setCarchkresult(jo.optString(CEnum.carchkresult.getKey()));
		entity.setCarfactoryinc(jo.optString(CEnum.carfactoryinc.getKey()));
		
		entity.setCarprice(jo.optString(CEnum.carprice.getKey()));
		entity.setCartypeinc(jo.optString(CEnum.cartypeinc.getKey()));
		entity.setCompanysimdeg(jo.optString(CEnum.companysimdeg.getKey()));
		entity.setDatadate(jo.optString(CEnum.datadate.getKey()));
		entity.setDrvstatusqryid(jo.optString(CEnum.drvstatusqryid.getKey()));
		entity.setErrorinfo(jo.optString(CEnum.errorinfo.getKey()));
		
		entity.setGraduatemajor(jo.optString(CEnum.graduatemajor.getKey()));
		entity.setGraduateschool(jo.optString(CEnum.graduateschool.getKey()));
		entity.setGraduateyear(jo.optString(CEnum.graduateyear.getKey()));
		entity.setHousechkresult(jo.optString(CEnum.housechkresult.getKey()));
		entity.setHousedatadate(jo.optString(CEnum.housedatadate.getKey()));
		entity.setIdno(jo.optString(CEnum.idno.getKey()));
		entity.setIdtype(jo.optString(CEnum.idtype.getKey()));
		entity.setIsasyned(jo.optString(CEnum.isasyned.getKey()));
		entity.setIsexistrel(jo.optString(CEnum.isexistrel.getKey()));
		entity.setIshighestedubkg(jo.optString(CEnum.ishighestedubkg.getKey()));
		entity.setIsownermobile(jo.optString(CEnum.isownermobile.getKey()));
		entity.setIsownermobiletwo(jo.optString(CEnum.isownermobiletwo.getKey()));
		entity.setIsrealcompany(jo.optString(CEnum.isrealcompany.getKey()));
		entity.setIsrealidentity(jo.optString(CEnum.isrealidentity.getKey()));
		entity.setIsvalidaddress(jo.optString(CEnum.isvalidaddress.getKey()));
		entity.setMobileno(jo.optString(CEnum.mobileno.getKey()));
		entity.setName(jo.optString(CEnum.name.getKey()));
		entity.setOwnermobilestatus(jo.optString(CEnum.ownermobilestatus.getKey()));
		entity.setPhotocmpresult(jo.optString(CEnum.photocmpresult.getKey()));
		entity.setPhotosimdeg(jo.optString(CEnum.photosimdeg.getKey()));
		entity.setRellevel(jo.optString(CEnum.rellevel.getKey()));
		entity.setUsetimescore(jo.optString(CEnum.usetimescore.getKey()));
		entity.setUsetimescoretwo(jo.optString(CEnum.usetimescoretwo.getKey()));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpValidateFace entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.addresstype.getKey(), entity.getAddresstype(), CEnum.addresstype.getRemark()));
		cmaps.add(new CMap(CEnum.appear1thdate.getKey(), entity.getAppear1thdate(), CEnum.appear1thdate.getRemark()));
		cmaps.add(new CMap(CEnum.appearlastdate.getKey(), entity.getAppearlastdate(), CEnum.appearlastdate.getRemark()));
		cmaps.add(new CMap(CEnum.carchkresult.getKey(), entity.getCarchkresult(), CEnum.carchkresult.getRemark()));
		cmaps.add(new CMap(CEnum.carfactoryinc.getKey(), entity.getCarfactoryinc(), CEnum.carfactoryinc.getRemark()));
		cmaps.add(new CMap(CEnum.carprice.getKey(), entity.getCarprice(), CEnum.carprice.getRemark()));
		cmaps.add(new CMap(CEnum.cartypeinc.getKey(), entity.getCartypeinc(), CEnum.cartypeinc.getRemark()));
		cmaps.add(new CMap(CEnum.companysimdeg.getKey(), entity.getCartypeinc(), CEnum.cartypeinc.getRemark()));
		cmaps.add(new CMap(CEnum.datadate.getKey(), entity.getDatadate(), CEnum.datadate.getRemark()));
		cmaps.add(new CMap(CEnum.drvstatusqryid.getKey(), entity.getDrvstatusqryid(), CEnum.drvstatusqryid.getRemark()));
		cmaps.add(new CMap(CEnum.errorinfo.getKey(), entity.getErrorinfo(), CEnum.errorinfo.getRemark()));
		cmaps.add(new CMap(CEnum.graduatemajor.getKey(), entity.getGraduatemajor(), CEnum.graduatemajor.getRemark()));
		cmaps.add(new CMap(CEnum.graduateschool.getKey(), entity.getGraduateschool(), CEnum.graduateschool.getRemark()));
		cmaps.add(new CMap(CEnum.graduateyear.getKey(), entity.getGraduateyear(), CEnum.graduateyear.getRemark()));
		cmaps.add(new CMap(CEnum.housechkresult.getKey(), entity.getHousechkresult(), CEnum.housechkresult.getRemark()));
		cmaps.add(new CMap(CEnum.housedatadate.getKey(), entity.getHousedatadate(), CEnum.housedatadate.getRemark()));
		cmaps.add(new CMap(CEnum.idno.getKey(), entity.getIdno(), CEnum.idno.getRemark()));
		cmaps.add(new CMap(CEnum.idtype.getKey(), entity.getIdtype(), CEnum.idtype.getRemark()));
		cmaps.add(new CMap(CEnum.isasyned.getKey(), entity.getIsasyned(), CEnum.isasyned.getRemark()));
		cmaps.add(new CMap(CEnum.isexistrel.getKey(), entity.getIsexistrel(), CEnum.isexistrel.getRemark()));
		cmaps.add(new CMap(CEnum.ishighestedubkg.getKey(), entity.getIshighestedubkg(), CEnum.ishighestedubkg.getRemark()));
		cmaps.add(new CMap(CEnum.isownermobile.getKey(), entity.getIsownermobile(), CEnum.isownermobile.getRemark()));
		cmaps.add(new CMap(CEnum.isownermobiletwo.getKey(), entity.getIsownermobiletwo(), CEnum.isownermobiletwo.getRemark()));
		cmaps.add(new CMap(CEnum.isrealcompany.getKey(), entity.getIsrealcompany(), CEnum.isrealcompany.getRemark()));
		cmaps.add(new CMap(CEnum.isrealidentity.getKey(), entity.getIsrealidentity(), CEnum.isrealidentity.getRemark()));
		cmaps.add(new CMap(CEnum.isvalidaddress.getKey(), entity.getIsvalidaddress(), CEnum.isvalidaddress.getRemark()));
		cmaps.add(new CMap(CEnum.mobileno.getKey(), entity.getMobileno(), CEnum.mobileno.getRemark()));
		cmaps.add(new CMap(CEnum.name.getKey(), entity.getName(), CEnum.name.getRemark()));
		cmaps.add(new CMap(CEnum.ownermobilestatus.getKey(), entity.getOwnermobilestatus(), CEnum.ownermobilestatus.getRemark()));
		cmaps.add(new CMap(CEnum.photocmpresult.getKey(), entity.getPhotocmpresult(), CEnum.photocmpresult.getRemark()));
		cmaps.add(new CMap(CEnum.photosimdeg.getKey(), entity.getPhotosimdeg(), CEnum.photosimdeg.getRemark()));
		cmaps.add(new CMap(CEnum.rellevel.getKey(), entity.getRellevel(), CEnum.rellevel.getRemark()));
		cmaps.add(new CMap(CEnum.usetimescore.getKey(), entity.getUsetimescore(), CEnum.usetimescore.getRemark()));
		cmaps.add(new CMap(CEnum.usetimescoretwo.getKey(), entity.getUsetimescoretwo(), CEnum.usetimescoretwo.getRemark()));

		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqno.getKey(), entity.getSeqno(), CEnum.seqno.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}