/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 机构详情Entity
 * @author kewenxiu
 * @version 2017-03-09
 */
public class SysOfficeDetail extends DataEntity<SysOfficeDetail> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 机构标识
	private String address;		// 地址
	private String fanWei;		// 范围
	private String fangFan;		// 防范措施
	private String flag;		// 删除标识
	private String licNumScan;		// 营业执照扫描件
	private String licNumScanPath;		// 营业执照扫描件路径
	private String licenseNumbers;		// 营业执照编号
	private String mianJi;		// 面积
	private String muDi;		// 目的
	private String name;		// 名称
	private String orgCode;		// 组织机构代码
	private String orgCodeScan;		// 组织机构代码扫描件
	private String orgCodeScanPath;		// 组织机构代码扫描件路径
	private String orgForm;		// 组织形式
	private String protocol;		// 股东协议书
	private String protocolPath;		// 股东协议书路径
	private String registerMoney;		// 注册资金
	private String type;		// 机构类型
	private String diTaxCode;		// 地税编码
	private String diTaxScan;		// 地税税务登记证扫描件附件
	private String diTaxScanPath;		// 地税税务登记证扫描件路径
	private String guoTaxCode;		// guo_tax_code
	private String guoTaxScan;		// 国税税务登记证扫描件附件
	private String guoTaxScanPath;		// 国税税务登记证扫描件路径
	private String openDate;		// 开业时间
	private String signDate;		// 报备日期
	private String diTaxScanRename;		// 地税税务登记证扫描件附件重新命名
	private String guoTaxScanRename;		// guo_tax_scan_rename
	private String licNumScanRename;		// 营业执照扫描件重新命名
	private String orgCodeScanRename;		// 织机构代码扫描件重新命名
	private String protocolRename;		// 股东协议书附件重新命名
	private String foundDate;		// found_date
	private String juanMoney;		// juan_money
	private String faren;		// faren
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	private String nifId;		// nif_id
	private String creditNum;    //统一信用代码
	
	public SysOfficeDetail() {
		super();
	}

	public SysOfficeDetail(String id){
		super(id);
	}

	@Length(min=1, max=64, message="机构标识长度必须介于 1 和 64 之间")
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
	
	@Length(min=0, max=255, message="范围长度必须介于 0 和 255 之间")
	public String getFanWei() {
		return fanWei;
	}

	public void setFanWei(String fanWei) {
		this.fanWei = fanWei;
	}
	
	@Length(min=0, max=255, message="防范措施长度必须介于 0 和 255 之间")
	public String getFangFan() {
		return fangFan;
	}

	public void setFangFan(String fangFan) {
		this.fangFan = fangFan;
	}
	
	@Length(min=0, max=255, message="删除标识长度必须介于 0 和 255 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=255, message="营业执照扫描件长度必须介于 0 和 255 之间")
	public String getLicNumScan() {
		return licNumScan;
	}

	public void setLicNumScan(String licNumScan) {
		this.licNumScan = licNumScan;
	}
	
	@Length(min=0, max=255, message="营业执照扫描件路径长度必须介于 0 和 255 之间")
	public String getLicNumScanPath() {
		return licNumScanPath;
	}

	public void setLicNumScanPath(String licNumScanPath) {
		this.licNumScanPath = licNumScanPath;
	}
	
	@Length(min=0, max=255, message="营业执照编号长度必须介于 0 和 255 之间")
	public String getLicenseNumbers() {
		return licenseNumbers;
	}

	public void setLicenseNumbers(String licenseNumbers) {
		this.licenseNumbers = licenseNumbers;
	}
	
	@Length(min=0, max=255, message="面积长度必须介于 0 和 255 之间")
	public String getMianJi() {
		return mianJi;
	}

	public void setMianJi(String mianJi) {
		this.mianJi = mianJi;
	}
	
	@Length(min=0, max=255, message="目的长度必须介于 0 和 255 之间")
	public String getMuDi() {
		return muDi;
	}

	public void setMuDi(String muDi) {
		this.muDi = muDi;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="组织机构代码长度必须介于 0 和 255 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=255, message="组织机构代码扫描件长度必须介于 0 和 255 之间")
	public String getOrgCodeScan() {
		return orgCodeScan;
	}

	public void setOrgCodeScan(String orgCodeScan) {
		this.orgCodeScan = orgCodeScan;
	}
	
	@Length(min=0, max=255, message="组织机构代码扫描件路径长度必须介于 0 和 255 之间")
	public String getOrgCodeScanPath() {
		return orgCodeScanPath;
	}

	public void setOrgCodeScanPath(String orgCodeScanPath) {
		this.orgCodeScanPath = orgCodeScanPath;
	}
	
	@Length(min=0, max=255, message="组织形式长度必须介于 0 和 255 之间")
	public String getOrgForm() {
		return orgForm;
	}

	public void setOrgForm(String orgForm) {
		this.orgForm = orgForm;
	}
	
	@Length(min=0, max=255, message="股东协议书长度必须介于 0 和 255 之间")
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Length(min=0, max=255, message="股东协议书路径长度必须介于 0 和 255 之间")
	public String getProtocolPath() {
		return protocolPath;
	}

	public void setProtocolPath(String protocolPath) {
		this.protocolPath = protocolPath;
	}
	
	@Length(min=0, max=255, message="注册资金长度必须介于 0 和 255 之间")
	public String getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(String registerMoney) {
		this.registerMoney = registerMoney;
	}
	
	@Length(min=0, max=255, message="机构类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="地税编码长度必须介于 0 和 255 之间")
	public String getDiTaxCode() {
		return diTaxCode;
	}

	public void setDiTaxCode(String diTaxCode) {
		this.diTaxCode = diTaxCode;
	}
	
	@Length(min=0, max=255, message="地税税务登记证扫描件附件长度必须介于 0 和 255 之间")
	public String getDiTaxScan() {
		return diTaxScan;
	}

	public void setDiTaxScan(String diTaxScan) {
		this.diTaxScan = diTaxScan;
	}
	
	@Length(min=0, max=255, message="地税税务登记证扫描件路径长度必须介于 0 和 255 之间")
	public String getDiTaxScanPath() {
		return diTaxScanPath;
	}

	public void setDiTaxScanPath(String diTaxScanPath) {
		this.diTaxScanPath = diTaxScanPath;
	}
	
	@Length(min=0, max=255, message="guo_tax_code长度必须介于 0 和 255 之间")
	public String getGuoTaxCode() {
		return guoTaxCode;
	}

	public void setGuoTaxCode(String guoTaxCode) {
		this.guoTaxCode = guoTaxCode;
	}
	
	@Length(min=0, max=255, message="国税税务登记证扫描件附件长度必须介于 0 和 255 之间")
	public String getGuoTaxScan() {
		return guoTaxScan;
	}

	public void setGuoTaxScan(String guoTaxScan) {
		this.guoTaxScan = guoTaxScan;
	}
	
	@Length(min=0, max=255, message="国税税务登记证扫描件路径长度必须介于 0 和 255 之间")
	public String getGuoTaxScanPath() {
		return guoTaxScanPath;
	}

	public void setGuoTaxScanPath(String guoTaxScanPath) {
		this.guoTaxScanPath = guoTaxScanPath;
	}
	
	@Length(min=0, max=255, message="开业时间长度必须介于 0 和 255 之间")
	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	
	@Length(min=0, max=255, message="报备日期长度必须介于 0 和 255 之间")
	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=255, message="地税税务登记证扫描件附件重新命名长度必须介于 0 和 255 之间")
	public String getDiTaxScanRename() {
		return diTaxScanRename;
	}

	public void setDiTaxScanRename(String diTaxScanRename) {
		this.diTaxScanRename = diTaxScanRename;
	}
	
	@Length(min=0, max=255, message="guo_tax_scan_rename长度必须介于 0 和 255 之间")
	public String getGuoTaxScanRename() {
		return guoTaxScanRename;
	}

	public void setGuoTaxScanRename(String guoTaxScanRename) {
		this.guoTaxScanRename = guoTaxScanRename;
	}
	
	@Length(min=0, max=255, message="营业执照扫描件重新命名长度必须介于 0 和 255 之间")
	public String getLicNumScanRename() {
		return licNumScanRename;
	}

	public void setLicNumScanRename(String licNumScanRename) {
		this.licNumScanRename = licNumScanRename;
	}
	
	@Length(min=0, max=255, message="织机构代码扫描件重新命名长度必须介于 0 和 255 之间")
	public String getOrgCodeScanRename() {
		return orgCodeScanRename;
	}

	public void setOrgCodeScanRename(String orgCodeScanRename) {
		this.orgCodeScanRename = orgCodeScanRename;
	}
	
	@Length(min=0, max=255, message="股东协议书附件重新命名长度必须介于 0 和 255 之间")
	public String getProtocolRename() {
		return protocolRename;
	}

	public void setProtocolRename(String protocolRename) {
		this.protocolRename = protocolRename;
	}
	
	@Length(min=0, max=255, message="found_date长度必须介于 0 和 255 之间")
	public String getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(String foundDate) {
		this.foundDate = foundDate;
	}
	
	@Length(min=0, max=255, message="juan_money长度必须介于 0 和 255 之间")
	public String getJuanMoney() {
		return juanMoney;
	}

	public void setJuanMoney(String juanMoney) {
		this.juanMoney = juanMoney;
	}
	
	@Length(min=0, max=255, message="faren长度必须介于 0 和 255 之间")
	public String getFaren() {
		return faren;
	}

	public void setFaren(String faren) {
		this.faren = faren;
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
	
}