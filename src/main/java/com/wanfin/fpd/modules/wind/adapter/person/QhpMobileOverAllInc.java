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
 * 前海-好信手机综合资讯附表
 * @author srf
 * @version 2016-06-08
 */
public class QhpMobileOverAllInc implements IAdapter<QhpMobileOverAllInc>{
	private enum CEnum{
		//id, personid, qrmcId, age, inaddress, custtp, custbill, databill, datausage, arrearsamount, historyarrears, hashouse, housenum, terminalmanufacturers, terminalmodel, operatingsystem, phonestate, imsi, meid, price, terminalnetsystem, terminalnettype, meid1changemdn, meid2changemdn, meid3changemdn, meid1changenet, meid2changenet, meid3changenet, meidchangemdnlevel, meidusedwangduan, meidusedwangduanchange, meid1changecity, meid2changecity, meid3changecity, meidchangecitylevel, mdn1changemeid, mdn3changemeid, mdn5changemeid, mdncurpinpai, mdncurpinpaitime, mdncurpinpaidur, mdnlastpinpai, mdnlastpinpaitime, mdnlastpinpaidur, mdnlast2pinpai, mdnlast2pinpaitime, mdnlast2pinpaidur, mdnusedotherpinpai, mdnusedavedur, mdncurmeidckprice, mdncurmeidshprice, mdncurmeidshtime, mdncurmeidshdur, mdnlastmeidckprice, mdnlastmeidshprice, mdnlastmeidshtime, mdnlastmeidshdur, mdnlast2meidckprice, mdnlast2meidshprice, mdnlast2meidshtime, mdnlast2meidshdur, mdnusedmeidaveprice, mdnusedmeidmodel, mdnusedmeidmodeltime, mdnusednet, mdnusednetchangetime, mdnusedpinpai, ischangemeid3mdn5, isblacklist, blacklisttype, result122, result124, result131, result221, result231, result232, result233, result234, result235, result236, office;

		id("id", "主键"),
		personid("personid", "个人主键"),
		qrmcId("qrmcId", "主键"),
		age("age", "年龄层次"),
		inaddress("inaddress", "入网地点"),
		custtp("custtp", "消费套餐类别"),
		custbill("custbill", "手机消费账单费用"),
		databill("databill", "手机流量金额"),
		datausage("datausage", "手机流量情况"),
		arrearsamount("arrearsamount", "当前欠费金额"),
		historyarrears("historyarrears", "是否历史欠费"),
		hashouse("hashouse", "是否有房"),
		housenum("housenum", "房屋数量"),
		terminalmanufacturers("terminalmanufacturers", "终端厂家"),
		terminalmodel("terminalmodel", "终端机型"),
		operatingsystem("operatingsystem", "操作系统"),
		phonestate("phonestate", "号码状态"),
		imsi("imsi", "卡号"),
		meid("meid", "设备号"),
		price("price", "当前手机价格"),
		terminalnetsystem("terminalnetsystem", "当前手机终端网络制式"),
		terminalnettype("terminalnettype", "当前手机终端网络类型"),
		meid1changemdn("meid1changemdn", "1年内换号不换机次数"),
		meid2changemdn("meid2changemdn", "2年内换号不换机次数"),
		meid3changemdn("meid3changemdn", "3年内换号不换机次数"),
		meid1changenet("meid1changenet", "1年内换网络类型不换机次数"),
		meid2changenet("meid2changenet", "2年内换网络类型不换机次数"),
		meid3changenet("meid3changenet", "3年内换网络类型不换机次数"),
		meidchangemdnlevel("meidchangemdnlevel", "换号不换机综合频率等级"),
		meidusedwangduan("meidusedwangduan", "曾使用网段"),
		meidusedwangduanchange("meidusedwangduanchange", "曾使用网段变更时间"),
		meid1changecity("meid1changecity", "1年内手机归属地变化不换机次数"),
		meid2changecity("meid2changecity", "2年内手机归属地变化不换机次数"),
		meid3changecity("meid3changecity", "3年内手机归属地变化不换机次数"),
		meidchangecitylevel("meidchangecitylevel", "归属地变化综合频率等级"),
		mdn1changemeid("mdn1changemeid", "号码不换1年内换手机设备次数"),
		mdn3changemeid("mdn3changemeid", "号码不换3年内换手机设备次数"),
		mdn5changemeid("mdn5changemeid", "号码不换5年内换手机设备次数"),
		mdncurpinpai("mdncurpinpai", "当前使用品牌"),
		mdncurpinpaitime("mdncurpinpaitime", "当前品牌启用时间"),
		mdncurpinpaidur("mdncurpinpaidur", "当前品牌使用时长"),
		mdnlastpinpai("mdnlastpinpai", "上次使用品牌"),
		mdnlastpinpaitime("mdnlastpinpaitime", "上次品牌启用时间"),
		mdnlastpinpaidur("mdnlastpinpaidur", "上次品牌使用时长"),
		mdnlast2pinpai("mdnlast2pinpai", "上上次使用品牌"),
		mdnlast2pinpaitime("mdnlast2pinpaitime", "上上次品牌启用时间"),
		mdnlast2pinpaidur("mdnlast2pinpaidur", "上上次品牌使用时长"),
		mdnusedotherpinpai("mdnusedotherpinpai", "曾经使用终端品牌"),
		mdnusedavedur("mdnusedavedur", "平均使用品牌时长"),
		mdncurmeidckprice("mdncurmeidckprice", "当前使用手机市场参考价"),
		mdncurmeidshprice("mdncurmeidshprice", "当前手机型号上市价"),
		mdncurmeidshtime("mdncurmeidshtime", "当前手机型号上市时间"),
		mdncurmeidshdur("mdncurmeidshdur", "当前手机型号上市时长"),
		mdnlastmeidckprice("mdnlastmeidckprice", "上次使用手机市场参考价"),
		mdnlastmeidshprice("mdnlastmeidshprice", "上次手机型号上市价"),
		mdnlastmeidshtime("mdnlastmeidshtime", "上次手机型号上市时间"),
		mdnlastmeidshdur("mdnlastmeidshdur", "上次手机型号上市时长"),
		mdnlast2meidckprice("mdnlast2meidckprice", "上上次使用手机市场参考价"),
		mdnlast2meidshprice("mdnlast2meidshprice", "上上次手机型号上市价"),
		mdnlast2meidshtime("mdnlast2meidshtime", "上上次手机型号上市时间"),
		mdnlast2meidshdur("mdnlast2meidshdur", "上上次手机型号上市时长"),
		mdnusedmeidaveprice("mdnusedmeidaveprice", "平均使用终端上市价"),
		mdnusedmeidmodel("mdnusedmeidmodel", "曾用终端机型"),
		mdnusedmeidmodeltime("mdnusedmeidmodeltime", "曾用终端机型变更时间"),
		mdnusednet("mdnusednet", "曾用网络类型"),
		mdnusednetchangetime("mdnusednetchangetime", "曾用网络类型变更时间"),
		mdnusedpinpai("mdnusedpinpai", "曾用终端品牌"),
		ischangemeid3mdn5("ischangemeid3mdn5", "3年内设备5年内手机号码是否变更"),
		isblacklist("isblacklist", "是否黑名单"),
		blacklisttype("blacklisttype", "黑名单类型"),
		result122("result122", "身份证、户名、手机号码是否一致"),
		result124("result124", "同一个身份证下手机号码数量"),
		result131("result131", "手机号码状态"),
		result221("result221", "入网时长"),
		result231("result231", "主叫通话次数"),
		result232("result232", "被叫通话次数"),
		result233("result233", "主叫通话时长区间"),
		result234("result234", "被叫通话时长区间"),
		result235("result235", "短信发送次数区间"),
		result236("result236", "用户近3个月内活跃天数是否大于一半"),
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
	private String qrmcId;		// qhzc_records_mobile_consult主键
	private String age;		// 年龄层次
	private String inaddress;		// 入网地点
	private String custtp;		// 消费套餐类别
	private String custbill;		// 手机消费账单费用
	private String databill;		// 手机流量金额
	private String datausage;		// 手机流量情况
	private String arrearsamount;		// 当前欠费金额
	private String historyarrears;		// 是否历史欠费
	private String hashouse;		// 是否有房
	private String housenum;		// 房屋数量
	private String terminalmanufacturers;		// 终端厂家
	private String terminalmodel;		// 终端机型
	private String operatingsystem;		// 操作系统
	private String phonestate;		// 号码状态
	private String imsi;		// 卡号
	private String meid;		// 设备号
	private String price;		// 当前手机价格
	private String terminalnetsystem;		// 当前手机终端网络制式
	private String terminalnettype;		// 当前手机终端网络类型
	private String meid1changemdn;		// 1年内换号不换机次数
	private String meid2changemdn;		// 2年内换号不换机次数
	private String meid3changemdn;		// 3年内换号不换机次数
	private String meid1changenet;		// 1年内换网络类型不换机次数
	private String meid2changenet;		// 2年内换网络类型不换机次数
	private String meid3changenet;		// 3年内换网络类型不换机次数
	private String meidchangemdnlevel;		// 换号不换机综合频率等级
	private String meidusedwangduan;		// 曾使用网段
	private String meidusedwangduanchange;		// 曾使用网段变更时间
	private String meid1changecity;		// 1年内手机归属地变化不换机次数
	private String meid2changecity;		// 2年内手机归属地变化不换机次数
	private String meid3changecity;		// 3年内手机归属地变化不换机次数
	private String meidchangecitylevel;		// 归属地变化综合频率等级
	private String mdn1changemeid;		// 号码不换1年内换手机设备次数
	private String mdn3changemeid;		// 号码不换3年内换手机设备次数
	private String mdn5changemeid;		// 号码不换5年内换手机设备次数
	private String mdncurpinpai;		// 当前使用品牌
	private String mdncurpinpaitime;		// 当前品牌启用时间
	private String mdncurpinpaidur;		// 当前品牌使用时长
	private String mdnlastpinpai;		// 上次使用品牌
	private String mdnlastpinpaitime;		// 上次品牌启用时间
	private String mdnlastpinpaidur;		// 上次品牌使用时长
	private String mdnlast2pinpai;		// 上上次使用品牌
	private String mdnlast2pinpaitime;		// 上上次品牌启用时间
	private String mdnlast2pinpaidur;		// 上上次品牌使用时长
	private String mdnusedotherpinpai;		// 曾经使用终端品牌
	private String mdnusedavedur;		// 平均使用品牌时长
	private String mdncurmeidckprice;		// 当前使用手机市场参考价
	private String mdncurmeidshprice;		// 当前手机型号上市价
	private String mdncurmeidshtime;		// 当前手机型号上市时间
	private String mdncurmeidshdur;		// 当前手机型号上市时长
	private String mdnlastmeidckprice;		// 上次使用手机市场参考价
	private String mdnlastmeidshprice;		// 上次手机型号上市价
	private String mdnlastmeidshtime;		// 上次手机型号上市时间
	private String mdnlastmeidshdur;		// 上次手机型号上市时长
	private String mdnlast2meidckprice;		// 上上次使用手机市场参考价
	private String mdnlast2meidshprice;		// 上上次手机型号上市价
	private String mdnlast2meidshtime;		// 上上次手机型号上市时间
	private String mdnlast2meidshdur;		// 上上次手机型号上市时长
	private String mdnusedmeidaveprice;		// 平均使用终端上市价
	private String mdnusedmeidmodel;		// 曾用终端机型
	private String mdnusedmeidmodeltime;		// 曾用终端机型变更时间
	private String mdnusednet;		// 曾用网络类型
	private String mdnusednetchangetime;		// 曾用网络类型变更时间
	private String mdnusedpinpai;		// 曾用终端品牌
	private String ischangemeid3mdn5;		// 3年内设备5年内手机号码是否变更
	private String isblacklist;		// 是否黑名单
	private String blacklisttype;		// 黑名单类型
	private String result122;		// 身份证、户名、手机号码是否一致
	private String result124;		// 同一个身份证下手机号码数量
	private String result131;		// 手机号码状态
	private String result221;		// 入网时长
	private String result231;		// 主叫通话次数
	private String result232;		// 被叫通话次数
	private String result233;		// 主叫通话时长区间
	private String result234;		// 被叫通话时长区间
	private String result235;		// 短信发送次数区间
	private String result236;		// 用户近3个月内活跃天数是否大于一半
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpMobileOverAllInc() {
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

	public String getQrmcId() {
		return qrmcId;
	}

	public void setQrmcId(String qrmcId) {
		this.qrmcId = qrmcId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getInaddress() {
		return inaddress;
	}

	public void setInaddress(String inaddress) {
		this.inaddress = inaddress;
	}

	public String getCusttp() {
		return custtp;
	}

	public void setCusttp(String custtp) {
		this.custtp = custtp;
	}

	public String getCustbill() {
		return custbill;
	}

	public void setCustbill(String custbill) {
		this.custbill = custbill;
	}

	public String getDatabill() {
		return databill;
	}

	public void setDatabill(String databill) {
		this.databill = databill;
	}

	public String getDatausage() {
		return datausage;
	}

	public void setDatausage(String datausage) {
		this.datausage = datausage;
	}

	public String getArrearsamount() {
		return arrearsamount;
	}

	public void setArrearsamount(String arrearsamount) {
		this.arrearsamount = arrearsamount;
	}

	public String getHistoryarrears() {
		return historyarrears;
	}

	public void setHistoryarrears(String historyarrears) {
		this.historyarrears = historyarrears;
	}

	public String getHashouse() {
		return hashouse;
	}

	public void setHashouse(String hashouse) {
		this.hashouse = hashouse;
	}

	public String getHousenum() {
		return housenum;
	}

	public void setHousenum(String housenum) {
		this.housenum = housenum;
	}

	public String getTerminalmanufacturers() {
		return terminalmanufacturers;
	}

	public void setTerminalmanufacturers(String terminalmanufacturers) {
		this.terminalmanufacturers = terminalmanufacturers;
	}

	public String getTerminalmodel() {
		return terminalmodel;
	}

	public void setTerminalmodel(String terminalmodel) {
		this.terminalmodel = terminalmodel;
	}

	public String getOperatingsystem() {
		return operatingsystem;
	}

	public void setOperatingsystem(String operatingsystem) {
		this.operatingsystem = operatingsystem;
	}

	public String getPhonestate() {
		return phonestate;
	}

	public void setPhonestate(String phonestate) {
		this.phonestate = phonestate;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMeid() {
		return meid;
	}

	public void setMeid(String meid) {
		this.meid = meid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTerminalnetsystem() {
		return terminalnetsystem;
	}

	public void setTerminalnetsystem(String terminalnetsystem) {
		this.terminalnetsystem = terminalnetsystem;
	}

	public String getTerminalnettype() {
		return terminalnettype;
	}

	public void setTerminalnettype(String terminalnettype) {
		this.terminalnettype = terminalnettype;
	}

	public String getMeid1changemdn() {
		return meid1changemdn;
	}

	public void setMeid1changemdn(String meid1changemdn) {
		this.meid1changemdn = meid1changemdn;
	}

	public String getMeid2changemdn() {
		return meid2changemdn;
	}

	public void setMeid2changemdn(String meid2changemdn) {
		this.meid2changemdn = meid2changemdn;
	}

	public String getMeid3changemdn() {
		return meid3changemdn;
	}

	public void setMeid3changemdn(String meid3changemdn) {
		this.meid3changemdn = meid3changemdn;
	}

	public String getMeid1changenet() {
		return meid1changenet;
	}

	public void setMeid1changenet(String meid1changenet) {
		this.meid1changenet = meid1changenet;
	}

	public String getMeid2changenet() {
		return meid2changenet;
	}

	public void setMeid2changenet(String meid2changenet) {
		this.meid2changenet = meid2changenet;
	}

	public String getMeid3changenet() {
		return meid3changenet;
	}

	public void setMeid3changenet(String meid3changenet) {
		this.meid3changenet = meid3changenet;
	}

	public String getMeidchangemdnlevel() {
		return meidchangemdnlevel;
	}

	public void setMeidchangemdnlevel(String meidchangemdnlevel) {
		this.meidchangemdnlevel = meidchangemdnlevel;
	}

	public String getMeidusedwangduan() {
		return meidusedwangduan;
	}

	public void setMeidusedwangduan(String meidusedwangduan) {
		this.meidusedwangduan = meidusedwangduan;
	}

	public String getMeidusedwangduanchange() {
		return meidusedwangduanchange;
	}

	public void setMeidusedwangduanchange(String meidusedwangduanchange) {
		this.meidusedwangduanchange = meidusedwangduanchange;
	}

	public String getMeid1changecity() {
		return meid1changecity;
	}

	public void setMeid1changecity(String meid1changecity) {
		this.meid1changecity = meid1changecity;
	}

	public String getMeid2changecity() {
		return meid2changecity;
	}

	public void setMeid2changecity(String meid2changecity) {
		this.meid2changecity = meid2changecity;
	}

	public String getMeid3changecity() {
		return meid3changecity;
	}

	public void setMeid3changecity(String meid3changecity) {
		this.meid3changecity = meid3changecity;
	}

	public String getMeidchangecitylevel() {
		return meidchangecitylevel;
	}

	public void setMeidchangecitylevel(String meidchangecitylevel) {
		this.meidchangecitylevel = meidchangecitylevel;
	}

	public String getMdn1changemeid() {
		return mdn1changemeid;
	}

	public void setMdn1changemeid(String mdn1changemeid) {
		this.mdn1changemeid = mdn1changemeid;
	}

	public String getMdn3changemeid() {
		return mdn3changemeid;
	}

	public void setMdn3changemeid(String mdn3changemeid) {
		this.mdn3changemeid = mdn3changemeid;
	}

	public String getMdn5changemeid() {
		return mdn5changemeid;
	}

	public void setMdn5changemeid(String mdn5changemeid) {
		this.mdn5changemeid = mdn5changemeid;
	}

	public String getMdncurpinpai() {
		return mdncurpinpai;
	}

	public void setMdncurpinpai(String mdncurpinpai) {
		this.mdncurpinpai = mdncurpinpai;
	}

	public String getMdncurpinpaitime() {
		return mdncurpinpaitime;
	}

	public void setMdncurpinpaitime(String mdncurpinpaitime) {
		this.mdncurpinpaitime = mdncurpinpaitime;
	}

	public String getMdncurpinpaidur() {
		return mdncurpinpaidur;
	}

	public void setMdncurpinpaidur(String mdncurpinpaidur) {
		this.mdncurpinpaidur = mdncurpinpaidur;
	}

	public String getMdnlastpinpai() {
		return mdnlastpinpai;
	}

	public void setMdnlastpinpai(String mdnlastpinpai) {
		this.mdnlastpinpai = mdnlastpinpai;
	}

	public String getMdnlastpinpaitime() {
		return mdnlastpinpaitime;
	}

	public void setMdnlastpinpaitime(String mdnlastpinpaitime) {
		this.mdnlastpinpaitime = mdnlastpinpaitime;
	}

	public String getMdnlastpinpaidur() {
		return mdnlastpinpaidur;
	}

	public void setMdnlastpinpaidur(String mdnlastpinpaidur) {
		this.mdnlastpinpaidur = mdnlastpinpaidur;
	}

	public String getMdnlast2pinpai() {
		return mdnlast2pinpai;
	}

	public void setMdnlast2pinpai(String mdnlast2pinpai) {
		this.mdnlast2pinpai = mdnlast2pinpai;
	}

	public String getMdnlast2pinpaitime() {
		return mdnlast2pinpaitime;
	}

	public void setMdnlast2pinpaitime(String mdnlast2pinpaitime) {
		this.mdnlast2pinpaitime = mdnlast2pinpaitime;
	}

	public String getMdnlast2pinpaidur() {
		return mdnlast2pinpaidur;
	}

	public void setMdnlast2pinpaidur(String mdnlast2pinpaidur) {
		this.mdnlast2pinpaidur = mdnlast2pinpaidur;
	}

	public String getMdnusedotherpinpai() {
		return mdnusedotherpinpai;
	}

	public void setMdnusedotherpinpai(String mdnusedotherpinpai) {
		this.mdnusedotherpinpai = mdnusedotherpinpai;
	}

	public String getMdnusedavedur() {
		return mdnusedavedur;
	}

	public void setMdnusedavedur(String mdnusedavedur) {
		this.mdnusedavedur = mdnusedavedur;
	}

	public String getMdncurmeidckprice() {
		return mdncurmeidckprice;
	}

	public void setMdncurmeidckprice(String mdncurmeidckprice) {
		this.mdncurmeidckprice = mdncurmeidckprice;
	}

	public String getMdncurmeidshprice() {
		return mdncurmeidshprice;
	}

	public void setMdncurmeidshprice(String mdncurmeidshprice) {
		this.mdncurmeidshprice = mdncurmeidshprice;
	}

	public String getMdncurmeidshtime() {
		return mdncurmeidshtime;
	}

	public void setMdncurmeidshtime(String mdncurmeidshtime) {
		this.mdncurmeidshtime = mdncurmeidshtime;
	}

	public String getMdncurmeidshdur() {
		return mdncurmeidshdur;
	}

	public void setMdncurmeidshdur(String mdncurmeidshdur) {
		this.mdncurmeidshdur = mdncurmeidshdur;
	}

	public String getMdnlastmeidckprice() {
		return mdnlastmeidckprice;
	}

	public void setMdnlastmeidckprice(String mdnlastmeidckprice) {
		this.mdnlastmeidckprice = mdnlastmeidckprice;
	}

	public String getMdnlastmeidshprice() {
		return mdnlastmeidshprice;
	}

	public void setMdnlastmeidshprice(String mdnlastmeidshprice) {
		this.mdnlastmeidshprice = mdnlastmeidshprice;
	}

	public String getMdnlastmeidshtime() {
		return mdnlastmeidshtime;
	}

	public void setMdnlastmeidshtime(String mdnlastmeidshtime) {
		this.mdnlastmeidshtime = mdnlastmeidshtime;
	}

	public String getMdnlastmeidshdur() {
		return mdnlastmeidshdur;
	}

	public void setMdnlastmeidshdur(String mdnlastmeidshdur) {
		this.mdnlastmeidshdur = mdnlastmeidshdur;
	}

	public String getMdnlast2meidckprice() {
		return mdnlast2meidckprice;
	}

	public void setMdnlast2meidckprice(String mdnlast2meidckprice) {
		this.mdnlast2meidckprice = mdnlast2meidckprice;
	}

	public String getMdnlast2meidshprice() {
		return mdnlast2meidshprice;
	}

	public void setMdnlast2meidshprice(String mdnlast2meidshprice) {
		this.mdnlast2meidshprice = mdnlast2meidshprice;
	}

	public String getMdnlast2meidshtime() {
		return mdnlast2meidshtime;
	}

	public void setMdnlast2meidshtime(String mdnlast2meidshtime) {
		this.mdnlast2meidshtime = mdnlast2meidshtime;
	}

	public String getMdnlast2meidshdur() {
		return mdnlast2meidshdur;
	}

	public void setMdnlast2meidshdur(String mdnlast2meidshdur) {
		this.mdnlast2meidshdur = mdnlast2meidshdur;
	}

	public String getMdnusedmeidaveprice() {
		return mdnusedmeidaveprice;
	}

	public void setMdnusedmeidaveprice(String mdnusedmeidaveprice) {
		this.mdnusedmeidaveprice = mdnusedmeidaveprice;
	}

	public String getMdnusedmeidmodel() {
		return mdnusedmeidmodel;
	}

	public void setMdnusedmeidmodel(String mdnusedmeidmodel) {
		this.mdnusedmeidmodel = mdnusedmeidmodel;
	}

	public String getMdnusedmeidmodeltime() {
		return mdnusedmeidmodeltime;
	}

	public void setMdnusedmeidmodeltime(String mdnusedmeidmodeltime) {
		this.mdnusedmeidmodeltime = mdnusedmeidmodeltime;
	}

	public String getMdnusednet() {
		return mdnusednet;
	}

	public void setMdnusednet(String mdnusednet) {
		this.mdnusednet = mdnusednet;
	}

	public String getMdnusednetchangetime() {
		return mdnusednetchangetime;
	}

	public void setMdnusednetchangetime(String mdnusednetchangetime) {
		this.mdnusednetchangetime = mdnusednetchangetime;
	}

	public String getMdnusedpinpai() {
		return mdnusedpinpai;
	}

	public void setMdnusedpinpai(String mdnusedpinpai) {
		this.mdnusedpinpai = mdnusedpinpai;
	}

	public String getIschangemeid3mdn5() {
		return ischangemeid3mdn5;
	}

	public void setIschangemeid3mdn5(String ischangemeid3mdn5) {
		this.ischangemeid3mdn5 = ischangemeid3mdn5;
	}

	public String getIsblacklist() {
		return isblacklist;
	}

	public void setIsblacklist(String isblacklist) {
		this.isblacklist = isblacklist;
	}

	public String getBlacklisttype() {
		return blacklisttype;
	}

	public void setBlacklisttype(String blacklisttype) {
		this.blacklisttype = blacklisttype;
	}

	public String getResult122() {
		return result122;
	}

	public void setResult122(String result122) {
		this.result122 = result122;
	}

	public String getResult124() {
		return result124;
	}

	public void setResult124(String result124) {
		this.result124 = result124;
	}

	public String getResult131() {
		return result131;
	}

	public void setResult131(String result131) {
		this.result131 = result131;
	}

	public String getResult221() {
		return result221;
	}

	public void setResult221(String result221) {
		this.result221 = result221;
	}

	public String getResult231() {
		return result231;
	}

	public void setResult231(String result231) {
		this.result231 = result231;
	}

	public String getResult232() {
		return result232;
	}

	public void setResult232(String result232) {
		this.result232 = result232;
	}

	public String getResult233() {
		return result233;
	}

	public void setResult233(String result233) {
		this.result233 = result233;
	}

	public String getResult234() {
		return result234;
	}

	public void setResult234(String result234) {
		this.result234 = result234;
	}

	public String getResult235() {
		return result235;
	}

	public void setResult235(String result235) {
		this.result235 = result235;
	}

	public String getResult236() {
		return result236;
	}

	public void setResult236(String result236) {
		this.result236 = result236;
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
	public List<QhpMobileOverAllInc> init(JSONArray jarray) throws JSONException {
		List<QhpMobileOverAllInc> entitys = new ArrayList<QhpMobileOverAllInc>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpMobileOverAllInc init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpMobileOverAllInc init(JSONObject jo) throws JSONException {
		QhpMobileOverAllInc entity = new QhpMobileOverAllInc();
		entity.setAge(jo.optString(CEnum.age.getKey()));
		entity.setArrearsamount(jo.optString(CEnum.arrearsamount.getKey()));
		entity.setBlacklisttype(jo.optString(CEnum.blacklisttype.getKey()));
		entity.setCustbill(jo.optString(CEnum.custbill.getKey()));
		entity.setCusttp(jo.optString(CEnum.custtp.getKey()));
		entity.setDatabill(jo.optString(CEnum.databill.getKey()));
		entity.setDatausage(jo.optString(CEnum.datausage.getKey()));
		entity.setHashouse(jo.optString(CEnum.hashouse.getKey()));
		entity.setHistoryarrears(jo.optString(CEnum.historyarrears.getKey()));
		entity.setHousenum(jo.optString(CEnum.housenum.getKey()));
		entity.setImsi(jo.optString(CEnum.imsi.getKey()));
		entity.setInaddress(jo.optString(CEnum.inaddress.getKey()));
		entity.setIsblacklist(jo.optString(CEnum.isblacklist.getKey()));
		entity.setIschangemeid3mdn5(jo.optString(CEnum.ischangemeid3mdn5.getKey()));
		entity.setMdn1changemeid(jo.optString(CEnum.mdn1changemeid.getKey()));
		entity.setMdn3changemeid(jo.optString(CEnum.mdn3changemeid.getKey()));
		entity.setMdn5changemeid(jo.optString(CEnum.mdn5changemeid.getKey()));
		entity.setMdncurmeidckprice(jo.optString(CEnum.mdncurmeidckprice.getKey()));
		entity.setMdncurmeidshdur(jo.optString(CEnum.mdncurmeidshdur.getKey()));
		entity.setMdncurmeidshprice(jo.optString(CEnum.mdncurmeidshprice.getKey()));
		entity.setMdncurmeidshtime(jo.optString(CEnum.mdncurmeidshtime.getKey()));
		entity.setMdncurpinpai(jo.optString(CEnum.mdncurpinpai.getKey()));
		entity.setMdncurpinpaidur(jo.optString(CEnum.mdncurpinpaidur.getKey()));
		entity.setMdncurpinpaitime(jo.optString(CEnum.mdncurpinpaitime.getKey()));
		entity.setMdnlast2meidckprice(jo.optString(CEnum.mdnlast2meidckprice.getKey()));
		entity.setMdnlast2meidshdur(jo.optString(CEnum.mdnlast2meidshdur.getKey()));
		entity.setMdnlast2meidshprice(jo.optString(CEnum.mdnlast2meidshprice.getKey()));
		entity.setMdnlast2meidshtime(jo.optString(CEnum.mdnlast2meidshtime.getKey()));
		entity.setMdnlast2pinpai(jo.optString(CEnum.mdnlast2pinpai.getKey()));
		entity.setMdnlast2pinpaidur(jo.optString(CEnum.mdnlast2pinpaidur.getKey()));
		entity.setMdnlast2pinpaitime(jo.optString(CEnum.mdnlast2pinpaitime.getKey()));
		entity.setMdnlastmeidckprice(jo.optString(CEnum.mdnlastmeidckprice.getKey()));
		entity.setMdnlastmeidshdur(jo.optString(CEnum.mdnlastmeidshdur.getKey()));
		entity.setMdnlastmeidshprice(jo.optString(CEnum.mdnlastmeidshprice.getKey()));
		entity.setMdnlastmeidshtime(jo.optString(CEnum.mdnlastmeidshtime.getKey()));
		entity.setMdnlastpinpai(jo.optString(CEnum.mdnlastpinpai.getKey()));
		entity.setMdnlastpinpaidur(jo.optString(CEnum.mdnlastpinpaidur.getKey()));
		entity.setMdnlastpinpaitime(jo.optString(CEnum.mdnlastpinpaitime.getKey()));

		entity.setMdnusedavedur(jo.optString(CEnum.mdnusedavedur.getKey()));
		entity.setMdnusedmeidaveprice(jo.optString(CEnum.mdnusedmeidaveprice.getKey()));
		entity.setMdnusedmeidmodel(jo.optString(CEnum.mdnusedmeidmodel.getKey()));
		entity.setMdnusedmeidmodeltime(jo.optString(CEnum.mdnusedmeidmodeltime.getKey()));
		entity.setMdnusednet(jo.optString(CEnum.mdnusednet.getKey()));
		entity.setMdnusednetchangetime(jo.optString(CEnum.mdnusednetchangetime.getKey()));
		entity.setMdnusedotherpinpai(jo.optString(CEnum.mdnusedotherpinpai.getKey()));
		entity.setMdnusedpinpai(jo.optString(CEnum.mdnusedpinpai.getKey()));
		entity.setMeid(jo.optString(CEnum.meid.getKey()));
		entity.setMeid1changecity(jo.optString(CEnum.meid1changecity.getKey()));
		entity.setMeid1changemdn(jo.optString(CEnum.meid1changemdn.getKey()));
		entity.setMeid1changenet(jo.optString(CEnum.meid1changenet.getKey()));
		entity.setMeid2changecity(jo.optString(CEnum.meid2changecity.getKey()));
		entity.setMeid2changemdn(jo.optString(CEnum.meid2changemdn.getKey()));
		entity.setMeid2changenet(jo.optString(CEnum.meid2changenet.getKey()));
		entity.setMeid3changecity(jo.optString(CEnum.meid3changecity.getKey()));
		entity.setMeid3changemdn(jo.optString(CEnum.meid3changemdn.getKey()));
		entity.setMeid3changenet(jo.optString(CEnum.meid3changenet.getKey()));
		entity.setMeidchangecitylevel(jo.optString(CEnum.meidchangecitylevel.getKey()));
		entity.setMeidchangemdnlevel(jo.optString(CEnum.meidchangemdnlevel.getKey()));
		entity.setMeidusedwangduan(jo.optString(CEnum.meidusedwangduan.getKey()));
		entity.setMeidusedwangduanchange(jo.optString(CEnum.meidusedwangduanchange.getKey()));

		entity.setOperatingsystem(jo.optString(CEnum.operatingsystem.getKey()));
		entity.setPhonestate(jo.optString(CEnum.phonestate.getKey()));
		entity.setPrice(jo.optString(CEnum.price.getKey()));
		entity.setQrmcId(jo.optString(CEnum.qrmcId.getKey()));
		entity.setResult122(jo.optString(CEnum.result122.getKey()));
		entity.setResult124(jo.optString(CEnum.result124.getKey()));
		entity.setResult131(jo.optString(CEnum.result131.getKey()));
		entity.setResult221(jo.optString(CEnum.result221.getKey()));
		entity.setResult231(jo.optString(CEnum.result231.getKey()));
		entity.setResult232(jo.optString(CEnum.result232.getKey()));
		entity.setResult233(jo.optString(CEnum.result233.getKey()));
		entity.setResult234(jo.optString(CEnum.result234.getKey()));
		entity.setResult235(jo.optString(CEnum.result235.getKey()));
		entity.setResult236(jo.optString(CEnum.result236.getKey()));
		
		entity.setTerminalmanufacturers(jo.optString(CEnum.terminalmanufacturers.getKey()));
		entity.setTerminalmodel(jo.optString(CEnum.terminalmodel.getKey()));
		entity.setTerminalnetsystem(jo.optString(CEnum.terminalnetsystem.getKey()));
		entity.setTerminalnettype(jo.optString(CEnum.terminalnettype.getKey()));
		
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpMobileOverAllInc entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.age.getKey(), entity.getAge(), CEnum.age.getRemark()));
		cmaps.add(new CMap(CEnum.arrearsamount.getKey(), entity.getArrearsamount(), CEnum.arrearsamount.getRemark()));
		cmaps.add(new CMap(CEnum.blacklisttype.getKey(), entity.getBlacklisttype(), CEnum.blacklisttype.getRemark()));
		cmaps.add(new CMap(CEnum.custbill.getKey(), entity.getCustbill(), CEnum.custbill.getRemark()));
		cmaps.add(new CMap(CEnum.custtp.getKey(), entity.getCusttp(), CEnum.custtp.getRemark()));
		cmaps.add(new CMap(CEnum.databill.getKey(), entity.getDatabill(), CEnum.databill.getRemark()));
		cmaps.add(new CMap(CEnum.datausage.getKey(), entity.getDatausage(), CEnum.datausage.getRemark()));
		cmaps.add(new CMap(CEnum.hashouse.getKey(), entity.getHashouse(), CEnum.hashouse.getRemark()));
		cmaps.add(new CMap(CEnum.historyarrears.getKey(), entity.getHistoryarrears(), CEnum.historyarrears.getRemark()));
		cmaps.add(new CMap(CEnum.housenum.getKey(), entity.getHousenum(), CEnum.housenum.getRemark()));
		cmaps.add(new CMap(CEnum.imsi.getKey(), entity.getImsi(), CEnum.imsi.getRemark()));
		cmaps.add(new CMap(CEnum.inaddress.getKey(), entity.getInaddress(), CEnum.inaddress.getRemark()));
		cmaps.add(new CMap(CEnum.isblacklist.getKey(), entity.getIsblacklist(), CEnum.isblacklist.getRemark()));
		cmaps.add(new CMap(CEnum.ischangemeid3mdn5.getKey(), entity.getIschangemeid3mdn5(), CEnum.ischangemeid3mdn5.getRemark()));
		cmaps.add(new CMap(CEnum.mdn1changemeid.getKey(), entity.getMdn1changemeid(), CEnum.mdn1changemeid.getRemark()));
		cmaps.add(new CMap(CEnum.mdn3changemeid.getKey(), entity.getMdn3changemeid(), CEnum.mdn3changemeid.getRemark()));
		cmaps.add(new CMap(CEnum.mdn5changemeid.getKey(), entity.getMdn5changemeid(), CEnum.mdn5changemeid.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurmeidckprice.getKey(), entity.getMdncurmeidckprice(), CEnum.mdncurmeidckprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurmeidshdur.getKey(), entity.getMdncurmeidshdur(), CEnum.mdncurmeidshdur.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurmeidshprice.getKey(), entity.getMdncurmeidshprice(), CEnum.mdncurmeidshprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurmeidshtime.getKey(), entity.getMdncurmeidshtime(), CEnum.mdncurmeidshtime.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurpinpai.getKey(), entity.getMdncurpinpai(), CEnum.mdncurpinpai.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurpinpaidur.getKey(), entity.getMdncurpinpaidur(), CEnum.mdncurpinpaidur.getRemark()));
		cmaps.add(new CMap(CEnum.mdncurpinpaitime.getKey(), entity.getMdncurpinpaitime(), CEnum.mdncurpinpaitime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2meidckprice.getKey(), entity.getMdnlast2meidckprice(), CEnum.mdnlast2meidckprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2meidshdur.getKey(), entity.getMdnlast2meidshdur(), CEnum.mdnlast2meidshdur.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2meidshprice.getKey(), entity.getMdnlast2meidshprice(), CEnum.mdnlast2meidshprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2meidshtime.getKey(), entity.getMdnlast2meidshtime(), CEnum.mdnlast2meidshtime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2pinpai.getKey(), entity.getMdnlast2pinpai(), CEnum.mdnlast2pinpai.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2pinpaidur.getKey(), entity.getMdnlast2pinpaidur(), CEnum.mdnlast2pinpaidur.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlast2pinpaitime.getKey(), entity.getMdnlast2pinpaitime(), CEnum.mdnlast2pinpaitime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastmeidckprice.getKey(), entity.getMdnlastmeidckprice(), CEnum.mdnlastmeidckprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastmeidshdur.getKey(), entity.getMdnlastmeidshdur(), CEnum.mdnlastmeidshdur.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastmeidshprice.getKey(), entity.getMdnlastmeidshprice(), CEnum.mdnlastmeidshprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastmeidshtime.getKey(), entity.getMdnlastmeidshtime(), CEnum.mdnlastmeidshtime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastpinpai.getKey(), entity.getMdnlastpinpai(), CEnum.mdnlastpinpai.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastpinpaidur.getKey(), entity.getMdnlastpinpaidur(), CEnum.mdnlastpinpaidur.getRemark()));
		cmaps.add(new CMap(CEnum.mdnlastpinpaitime.getKey(), entity.getMdnlastpinpaitime(), CEnum.mdnlastpinpaitime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedavedur.getKey(), entity.getMdnusedavedur(), CEnum.mdnusedavedur.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedmeidaveprice.getKey(), entity.getMdnusedmeidaveprice(), CEnum.mdnusedmeidaveprice.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedmeidmodel.getKey(), entity.getMdnusedmeidmodel(), CEnum.mdnusedmeidmodel.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedmeidmodeltime.getKey(), entity.getMdnusedmeidmodeltime(), CEnum.mdnusedmeidmodeltime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusednet.getKey(), entity.getMdnusednet(), CEnum.mdnusednet.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusednetchangetime.getKey(), entity.getMdnusednetchangetime(), CEnum.mdnusednetchangetime.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedotherpinpai.getKey(), entity.getMdnusedotherpinpai(), CEnum.mdnusedotherpinpai.getRemark()));
		cmaps.add(new CMap(CEnum.mdnusedpinpai.getKey(), entity.getMdnusedpinpai(), CEnum.mdnusedpinpai.getRemark()));
		cmaps.add(new CMap(CEnum.meid.getKey(), entity.getMeid(), CEnum.meid.getRemark()));
		cmaps.add(new CMap(CEnum.meid1changecity.getKey(), entity.getMeid1changecity(), CEnum.meid1changecity.getRemark()));
		cmaps.add(new CMap(CEnum.meid1changemdn.getKey(), entity.getMeid1changemdn(), CEnum.meid1changemdn.getRemark()));
		cmaps.add(new CMap(CEnum.meid1changenet.getKey(), entity.getMeid1changenet(), CEnum.meid1changenet.getRemark()));
		cmaps.add(new CMap(CEnum.meid2changecity.getKey(), entity.getMeid2changecity(), CEnum.meid2changecity.getRemark()));
		cmaps.add(new CMap(CEnum.meid2changemdn.getKey(), entity.getMeid2changemdn(), CEnum.meid2changemdn.getRemark()));
		cmaps.add(new CMap(CEnum.meid2changenet.getKey(), entity.getMeid2changenet(), CEnum.meid2changenet.getRemark()));
		cmaps.add(new CMap(CEnum.meid3changecity.getKey(), entity.getMeid3changecity(), CEnum.meid3changecity.getRemark()));
		cmaps.add(new CMap(CEnum.meid3changemdn.getKey(), entity.getMeid3changemdn(), CEnum.meid3changemdn.getRemark()));
		cmaps.add(new CMap(CEnum.meid3changenet.getKey(), entity.getMeid3changenet(), CEnum.meid3changenet.getRemark()));
		cmaps.add(new CMap(CEnum.meidchangecitylevel.getKey(), entity.getMeidchangecitylevel(), CEnum.meidchangecitylevel.getRemark()));
		cmaps.add(new CMap(CEnum.meidchangemdnlevel.getKey(), entity.getMeidchangemdnlevel(), CEnum.meidchangemdnlevel.getRemark()));
		cmaps.add(new CMap(CEnum.meidusedwangduan.getKey(), entity.getMeidusedwangduan(), CEnum.meidusedwangduan.getRemark()));
		cmaps.add(new CMap(CEnum.meidusedwangduanchange.getKey(), entity.getMeidusedwangduanchange(), CEnum.meidusedwangduanchange.getRemark()));
		cmaps.add(new CMap(CEnum.operatingsystem.getKey(), entity.getOperatingsystem(), CEnum.operatingsystem.getRemark()));
		cmaps.add(new CMap(CEnum.phonestate.getKey(), entity.getPhonestate(), CEnum.phonestate.getRemark()));
		cmaps.add(new CMap(CEnum.price.getKey(), entity.getPrice(), CEnum.price.getRemark()));
		cmaps.add(new CMap(CEnum.qrmcId.getKey(), entity.getQrmcId(), CEnum.qrmcId.getRemark()));
		cmaps.add(new CMap(CEnum.result122.getKey(), entity.getResult122(), CEnum.result122.getRemark()));
		cmaps.add(new CMap(CEnum.result124.getKey(), entity.getResult124(), CEnum.result124.getRemark()));
		cmaps.add(new CMap(CEnum.result131.getKey(), entity.getResult131(), CEnum.result131.getRemark()));
		cmaps.add(new CMap(CEnum.result221.getKey(), entity.getResult221(), CEnum.result221.getRemark()));
		cmaps.add(new CMap(CEnum.result231.getKey(), entity.getResult231(), CEnum.result231.getRemark()));
		cmaps.add(new CMap(CEnum.result232.getKey(), entity.getResult232(), CEnum.result232.getRemark()));
		cmaps.add(new CMap(CEnum.result233.getKey(), entity.getResult233(), CEnum.result233.getRemark()));
		cmaps.add(new CMap(CEnum.result234.getKey(), entity.getResult234(), CEnum.result234.getRemark()));
		cmaps.add(new CMap(CEnum.result235.getKey(), entity.getResult235(), CEnum.result235.getRemark()));
		cmaps.add(new CMap(CEnum.result236.getKey(), entity.getResult236(), CEnum.result236.getRemark()));
		cmaps.add(new CMap(CEnum.terminalmanufacturers.getKey(), entity.getTerminalmanufacturers(), CEnum.terminalmanufacturers.getRemark()));
		cmaps.add(new CMap(CEnum.terminalmodel.getKey(), entity.getTerminalmodel(), CEnum.terminalmodel.getRemark()));
		cmaps.add(new CMap(CEnum.terminalnetsystem.getKey(), entity.getTerminalnetsystem(), CEnum.terminalnetsystem.getRemark()));
		cmaps.add(new CMap(CEnum.terminalnettype.getKey(), entity.getTerminalnetsystem(), CEnum.terminalnettype.getRemark()));

		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}