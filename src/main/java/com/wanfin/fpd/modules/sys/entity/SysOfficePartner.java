/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 机构股东Entity
 * @author chenh
 * @version 2017-03-09
 */
public class SysOfficePartner extends DataEntity<SysOfficePartner> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 机构ID
	private String address;		// 地址
	private String busAddr;		// 营业地址
	private String email;		// 邮件
	private String fanWei;		// 经营范围
	private String flag;		// 删除标识
	private String funds;		// 出资额
	private String legalPerson;		// 法定代表人
	private String licNum;		// 营业执照
	private String name;		// 股东名称
	private String orgCode;		// 组织机构代码证
	private String proportion;		// 出资比例
	private String regAddr;		// 注册地
	private String status;		// 是否在职
	private String telephone;		// 手机
	private String signDate;		// 报备日期
	private String chezhi;		// chezhi
	private String chiguDate1;		// chigu_date1
	private String chiguDate2;		// chigu_date2
	private String zaizhiDate1;		// zaizhi_date1
	private String zaizhiDate2;		// zaizhi_date2
	private String bianBi;		// bian_bi
	private String bianDate;		// bian_date
	private Long bianId;		// bian_id
	private String bianShi;		// bian_shi
	private String bianZi;		// bian_zi
	private String islisi;		// islisi
	private String pingJia;		// ping_jia
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	private String nifId;		// nif_id
	
	private String creditNum;	//统一信用代码
	private String isGuoqi;		//是否国有企业
	private String isShangshi;	//是否上市
	
	public SysOfficePartner() {
		super();
	}

	public SysOfficePartner(String id){
		super(id);
	}

	@Length(min=0, max=64, message="机构ID长度必须介于 0 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="营业地址长度必须介于 0 和 255 之间")
	public String getBusAddr() {
		return busAddr;
	}

	public void setBusAddr(String busAddr) {
		this.busAddr = busAddr;
	}
	
	@Length(min=0, max=255, message="邮件长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="经营范围长度必须介于 0 和 255 之间")
	public String getFanWei() {
		return fanWei;
	}

	public void setFanWei(String fanWei) {
		this.fanWei = fanWei;
	}
	
	@Length(min=0, max=255, message="删除标识长度必须介于 0 和 255 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=255, message="出资额长度必须介于 0 和 255 之间")
	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}
	
	@Length(min=0, max=255, message="法定代表人长度必须介于 0 和 255 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@Length(min=0, max=255, message="营业执照长度必须介于 0 和 255 之间")
	public String getLicNum() {
		return licNum;
	}

	public void setLicNum(String licNum) {
		this.licNum = licNum;
	}
	
	@Length(min=0, max=255, message="股东名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="组织机构代码证长度必须介于 0 和 255 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=255, message="出资比例长度必须介于 0 和 255 之间")
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	
	@Length(min=0, max=255, message="注册地长度必须介于 0 和 255 之间")
	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}
	
	@Length(min=0, max=255, message="是否在职长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="手机长度必须介于 0 和 255 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=255, message="报备日期长度必须介于 0 和 255 之间")
	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=255, message="chezhi长度必须介于 0 和 255 之间")
	public String getChezhi() {
		return chezhi;
	}

	public void setChezhi(String chezhi) {
		this.chezhi = chezhi;
	}
	
	@Length(min=0, max=255, message="chigu_date1长度必须介于 0 和 255 之间")
	public String getChiguDate1() {
		return chiguDate1;
	}

	public void setChiguDate1(String chiguDate1) {
		this.chiguDate1 = chiguDate1;
	}
	
	@Length(min=0, max=255, message="chigu_date2长度必须介于 0 和 255 之间")
	public String getChiguDate2() {
		return chiguDate2;
	}

	public void setChiguDate2(String chiguDate2) {
		this.chiguDate2 = chiguDate2;
	}
	
	@Length(min=0, max=255, message="zaizhi_date1长度必须介于 0 和 255 之间")
	public String getZaizhiDate1() {
		return zaizhiDate1;
	}

	public void setZaizhiDate1(String zaizhiDate1) {
		this.zaizhiDate1 = zaizhiDate1;
	}
	
	@Length(min=0, max=255, message="zaizhi_date2长度必须介于 0 和 255 之间")
	public String getZaizhiDate2() {
		return zaizhiDate2;
	}

	public void setZaizhiDate2(String zaizhiDate2) {
		this.zaizhiDate2 = zaizhiDate2;
	}
	
	@Length(min=0, max=255, message="bian_bi长度必须介于 0 和 255 之间")
	public String getBianBi() {
		return bianBi;
	}

	public void setBianBi(String bianBi) {
		this.bianBi = bianBi;
	}
	
	@Length(min=0, max=255, message="bian_date长度必须介于 0 和 255 之间")
	public String getBianDate() {
		return bianDate;
	}

	public void setBianDate(String bianDate) {
		this.bianDate = bianDate;
	}
	
	public Long getBianId() {
		return bianId;
	}

	public void setBianId(Long bianId) {
		this.bianId = bianId;
	}
	
	@Length(min=0, max=255, message="bian_shi长度必须介于 0 和 255 之间")
	public String getBianShi() {
		return bianShi;
	}

	public void setBianShi(String bianShi) {
		this.bianShi = bianShi;
	}
	
	@Length(min=0, max=255, message="bian_zi长度必须介于 0 和 255 之间")
	public String getBianZi() {
		return bianZi;
	}

	public void setBianZi(String bianZi) {
		this.bianZi = bianZi;
	}
	
	@Length(min=0, max=255, message="islisi长度必须介于 0 和 255 之间")
	public String getIslisi() {
		return islisi;
	}

	public void setIslisi(String islisi) {
		this.islisi = islisi;
	}
	
	@Length(min=0, max=255, message="ping_jia长度必须介于 0 和 255 之间")
	public String getPingJia() {
		return pingJia;
	}

	public void setPingJia(String pingJia) {
		this.pingJia = pingJia;
	}
	
	@Length(min=0, max=255, message="source_flag长度必须介于 0 和 255 之间")
	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
	
	@Length(min=0, max=255, message="source_status长度必须介于 0 和 255 之间")
	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	
	@Length(min=0, max=255, message="nif_id长度必须介于 0 和 255 之间")
	public String getNifId() {
		return nifId;
	}

	public void setNifId(String nifId) {
		this.nifId = nifId;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public String getIsGuoqi() {
		return isGuoqi;
	}

	public void setIsGuoqi(String isGuoqi) {
		this.isGuoqi = isGuoqi;
	}

	public String getIsShangshi() {
		return isShangshi;
	}

	public void setIsShangshi(String isShangshi) {
		this.isShangshi = isShangshi;
	}
	
}