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
 * 前海-反欺诈风险度认证数据
 * @author srf
 * @version 2016-06-08
 */
public class QhpCheatInfo implements IAdapter<QhpCheatInfo>{
	private enum CEnum{
		//id, personid, resId, mobileNo, ip, seqNo, isMachdForce, isMachdDNS, isMachdMailServ, isMachdSEO, isMachdOrg, isMachdOrg, isMachdCrawler, isMachdProxy, isMachdBlacklist, isMachdWebServ, isMachdVPN, isMachdVPN, isMachdBlMakt, isMachCraCall, isMachFraud, isMachEmpty, isMachYZmobile, isMachSmallNo, isMachSZNo, iUpdateDate, mUpdateDate, iRskDesc, mRskDesc, erCode, erMsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqNo("seqNo", "序列号"),
		mobileNo("mobileNo", "手机号码"),
		ip("ip", "IP地址"),
		isMachdForce("isMachdForce", "命中暴力破解"),
		isMachdDNS("isMachdDNS", "命中DNS服务器"),
		isMachdMailServ("isMachdMailServ", "命中邮件服务器"),
		isMachdSEO("isMachdSEO", "命中seo"),
		isMachdOrg("isMachdOrg", "命中组织出口"),
		isMachdCrawler("isMachdCrawler", "命中爬虫"),
		isMachdProxy("isMachdProxy", "命中代理服务器"),
		isMachdBlacklist("isMachdBlacklist", "命中第三方标注黑名单"),
		isMachdWebServ("isMachdWebServ", "命中web服务器"),
		isMachdVPN("isMachdVPN", "命中vpn服务器"),
		isMachdBlMakt("isMachdBlMakt", "命中第三方标注黑名单"),
		isMachCraCall("isMachCraCall", "命中骚扰电话"),
		isMachFraud("isMachFraud", "命中欺诈号码"),
		isMachEmpty("isMachEmpty", "命中空号（非正常短信语音号码）"),
		isMachYZmobile("isMachYZmobile", "命中收码平台号码"),
		isMachSmallNo("isMachSmallNo", "命中小号"),
		isMachSZNo("isMachSZNo", "命中社工库号码"),
		iUpdateDate("iUpdateDate", "ip风险时间"),
		mUpdateDate("mUpdateDate", "手机号码风险时间"),
		iRskDesc("iRskDesc", "IP风险描述"),
		mRskDesc("mRskDesc", "手机号码风险描述"),
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
	private String mobileNo;		// 手机号码
	private String ip;		// IP地址
	private String seqNo;		// 序列号
	private String isMachdForce;		// 命中暴力破解
	private String isMachdDNS;		// 命中DNS服务器
	private String isMachdMailServ;		// 命中邮件服务器
	private String isMachdSEO;		// 命中seo
	private String isMachdOrg;		// 命中组织出口
	private String isMachdCrawler;		// 命中爬虫
	private String isMachdProxy;		// 命中代理服务器
	private String isMachdBlacklist;		// 命中第三方标注黑名单
	private String isMachdWebServ;		// 命中web服务器
	private String isMachdVPN;		// 命中vpn服务器
	private String isMachdBlMakt;		// 命中第三方标注黑名单
	private String isMachCraCall;		// 命中骚扰电话
	private String isMachFraud;		// 命中欺诈号码
	private String isMachEmpty;		// 命中空号（非正常短信语音号码）
	private String isMachYZmobile;		// 命中收码平台号码
	private String isMachSmallNo;		// 命中小号
	private String isMachSZNo;		// 命中社工库号码
	private String iUpdateDate;		// ip风险时间
	private String mUpdateDate;		// 手机号码风险时间
	private String iRskDesc;		// IP风险描述
	private String mRskDesc;		// 手机号码风险描述
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCheatInfo() {
		super();
	}
	
	public String getId() {
		return id;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getIsMachdForce() {
		return isMachdForce;
	}

	public void setIsMachdForce(String isMachdForce) {
		this.isMachdForce = isMachdForce;
	}

	public String getIsMachdDNS() {
		return isMachdDNS;
	}

	public void setIsMachdDNS(String isMachdDNS) {
		this.isMachdDNS = isMachdDNS;
	}

	public String getIsMachdMailServ() {
		return isMachdMailServ;
	}

	public void setIsMachdMailServ(String isMachdMailServ) {
		this.isMachdMailServ = isMachdMailServ;
	}

	public String getIsMachdSEO() {
		return isMachdSEO;
	}

	public void setIsMachdSEO(String isMachdSEO) {
		this.isMachdSEO = isMachdSEO;
	}

	public String getIsMachdOrg() {
		return isMachdOrg;
	}

	public void setIsMachdOrg(String isMachdOrg) {
		this.isMachdOrg = isMachdOrg;
	}

	public String getIsMachdCrawler() {
		return isMachdCrawler;
	}

	public void setIsMachdCrawler(String isMachdCrawler) {
		this.isMachdCrawler = isMachdCrawler;
	}

	public String getIsMachdProxy() {
		return isMachdProxy;
	}

	public void setIsMachdProxy(String isMachdProxy) {
		this.isMachdProxy = isMachdProxy;
	}

	public String getIsMachdBlacklist() {
		return isMachdBlacklist;
	}

	public void setIsMachdBlacklist(String isMachdBlacklist) {
		this.isMachdBlacklist = isMachdBlacklist;
	}

	public String getIsMachdWebServ() {
		return isMachdWebServ;
	}

	public void setIsMachdWebServ(String isMachdWebServ) {
		this.isMachdWebServ = isMachdWebServ;
	}

	public String getIsMachdVPN() {
		return isMachdVPN;
	}

	public void setIsMachdVPN(String isMachdVPN) {
		this.isMachdVPN = isMachdVPN;
	}

	public String getIsMachdBlMakt() {
		return isMachdBlMakt;
	}

	public void setIsMachdBlMakt(String isMachdBlMakt) {
		this.isMachdBlMakt = isMachdBlMakt;
	}

	public String getIsMachCraCall() {
		return isMachCraCall;
	}

	public void setIsMachCraCall(String isMachCraCall) {
		this.isMachCraCall = isMachCraCall;
	}

	public String getIsMachFraud() {
		return isMachFraud;
	}

	public void setIsMachFraud(String isMachFraud) {
		this.isMachFraud = isMachFraud;
	}

	public String getIsMachEmpty() {
		return isMachEmpty;
	}

	public void setIsMachEmpty(String isMachEmpty) {
		this.isMachEmpty = isMachEmpty;
	}

	public String getIsMachYZmobile() {
		return isMachYZmobile;
	}

	public void setIsMachYZmobile(String isMachYZmobile) {
		this.isMachYZmobile = isMachYZmobile;
	}

	public String getIsMachSmallNo() {
		return isMachSmallNo;
	}

	public void setIsMachSmallNo(String isMachSmallNo) {
		this.isMachSmallNo = isMachSmallNo;
	}

	public String getIsMachSZNo() {
		return isMachSZNo;
	}

	public void setIsMachSZNo(String isMachSZNo) {
		this.isMachSZNo = isMachSZNo;
	}

	public String getIUpdateDate() {
		return iUpdateDate;
	}

	public void setIUpdateDate(String iUpdateDate) {
		this.iUpdateDate = iUpdateDate;
	}

	public String getMUpdateDate() {
		return mUpdateDate;
	}

	public void setMUpdateDate(String mUpdateDate) {
		this.mUpdateDate = mUpdateDate;
	}

	public String getIRskDesc() {
		return iRskDesc;
	}

	public void setIRskDesc(String iRskDesc) {
		this.iRskDesc = iRskDesc;
	}

	public String getMRskDesc() {
		return mRskDesc;
	}

	public void setMRskDesc(String mRskDesc) {
		this.mRskDesc = mRskDesc;
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

	public void setId(String id) {
		this.id = id;
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
	public List<QhpCheatInfo> init(JSONArray jarray) throws JSONException {
		List<QhpCheatInfo> entitys = new ArrayList<QhpCheatInfo>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCheatInfo init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCheatInfo init(JSONObject jo) throws JSONException {
		QhpCheatInfo entity = new QhpCheatInfo();
		entity.setIp(jo.optString(CEnum.ip.getKey()));
		entity.setIRskDesc(jo.optString(CEnum.iRskDesc.getKey()));
		entity.setIsMachCraCall(jo.optString(CEnum.isMachCraCall.getKey()));
		entity.setIsMachdBlacklist(jo.optString(CEnum.isMachdBlacklist.getKey()));
		entity.setIsMachdBlMakt(jo.optString(CEnum.isMachdBlMakt.getKey()));
		entity.setIsMachdCrawler(jo.optString(CEnum.isMachdCrawler.getKey()));
		entity.setIsMachdDNS(jo.optString(CEnum.isMachdDNS.getKey()));
		entity.setIsMachdForce(jo.optString(CEnum.isMachdForce.getKey()));
		entity.setIsMachdMailServ(jo.optString(CEnum.isMachdMailServ.getKey()));
		entity.setIsMachdOrg(jo.optString(CEnum.isMachdOrg.getKey()));
		entity.setIsMachdProxy(jo.optString(CEnum.isMachdProxy.getKey()));
		entity.setIsMachdSEO(jo.optString(CEnum.isMachdSEO.getKey()));
		entity.setIsMachdVPN(jo.optString(CEnum.isMachdVPN.getKey()));
		entity.setIsMachdWebServ(jo.optString(CEnum.isMachdWebServ.getKey()));
		entity.setIsMachEmpty(jo.optString(CEnum.isMachEmpty.getKey()));
		entity.setIsMachFraud(jo.optString(CEnum.isMachFraud.getKey()));
		entity.setIsMachSmallNo(jo.optString(CEnum.isMachSmallNo.getKey()));
		entity.setIsMachSZNo(jo.optString(CEnum.isMachSZNo.getKey()));
		entity.setIsMachYZmobile(jo.optString(CEnum.isMachYZmobile.getKey()));
		entity.setIUpdateDate(jo.optString(CEnum.iUpdateDate.getKey()));
		entity.setMobileNo(jo.optString(CEnum.mobileNo.getKey()));
		entity.setMRskDesc(jo.optString(CEnum.mRskDesc.getKey()));
		entity.setMUpdateDate(jo.optString(CEnum.mUpdateDate.getKey()));
		
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
	
	private List<CMap> initCmaps(QhpCheatInfo entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.ip.getKey(), entity.getIp(), CEnum.ip.getRemark()));
		cmaps.add(new CMap(CEnum.iRskDesc.getKey(), entity.getIRskDesc(), CEnum.iRskDesc.getRemark()));
		cmaps.add(new CMap(CEnum.isMachCraCall.getKey(), entity.getIsMachCraCall(), CEnum.isMachCraCall.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdBlacklist.getKey(), entity.getIsMachdBlacklist(), CEnum.isMachdBlacklist.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdBlMakt.getKey(), entity.getIsMachdBlMakt(), CEnum.isMachdBlMakt.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdCrawler.getKey(), entity.getIsMachdCrawler(), CEnum.isMachdCrawler.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdDNS.getKey(), entity.getIsMachdDNS(), CEnum.isMachdDNS.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdForce.getKey(), entity.getIsMachdForce(), CEnum.isMachdForce.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdMailServ.getKey(), entity.getIsMachdMailServ(), CEnum.isMachdMailServ.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdOrg.getKey(), entity.getIsMachdOrg(), CEnum.isMachdOrg.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdProxy.getKey(), entity.getIsMachdProxy(), CEnum.isMachdProxy.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdSEO.getKey(), entity.getIsMachdSEO(), CEnum.isMachdSEO.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdVPN.getKey(), entity.getIsMachdVPN(), CEnum.isMachdVPN.getRemark()));
		cmaps.add(new CMap(CEnum.isMachdWebServ.getKey(), entity.getIsMachdWebServ(), CEnum.isMachdWebServ.getRemark()));
		cmaps.add(new CMap(CEnum.isMachEmpty.getKey(), entity.getIsMachEmpty(), CEnum.isMachEmpty.getRemark()));
		cmaps.add(new CMap(CEnum.isMachFraud.getKey(), entity.getIsMachFraud(), CEnum.isMachFraud.getRemark()));
		cmaps.add(new CMap(CEnum.isMachSmallNo.getKey(), entity.getIsMachSmallNo(), CEnum.isMachSmallNo.getRemark()));
		cmaps.add(new CMap(CEnum.isMachSZNo.getKey(), entity.getIsMachSZNo(), CEnum.isMachSZNo.getRemark()));
		cmaps.add(new CMap(CEnum.isMachYZmobile.getKey(), entity.getIsMachYZmobile(), CEnum.isMachYZmobile.getRemark()));
		cmaps.add(new CMap(CEnum.iUpdateDate.getKey(), entity.getIUpdateDate(), CEnum.iUpdateDate.getRemark()));
		cmaps.add(new CMap(CEnum.mobileNo.getKey(), entity.getMobileNo(), CEnum.mobileNo.getRemark()));
		cmaps.add(new CMap(CEnum.mRskDesc.getKey(), entity.getMRskDesc(), CEnum.mRskDesc.getRemark()));
		cmaps.add(new CMap(CEnum.mUpdateDate.getKey(), entity.getMUpdateDate(), CEnum.mUpdateDate.getRemark()));

		
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