/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 从业人员Entity
 * @author kewenxiu
 * @version 2017-03-09
 */
public class SysOfficeEmployee extends DataEntity<SysOfficeEmployee> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 机构标识
	private String address;		// 户口所在地
	private String birthday;		// 出生年月
	private String email;		// 电子邮件
	private String flag;		// 删除标识
	private String gender;		// 性别
	private String idCard;		// 身份证号码
	private String jianLi;		// 简历
	private String jinzhi;		// 从业禁止情形
	private String messAddr;		// 通讯地址
	private String name;		// 姓名
	private String national;		// 民族
	private String nativePlace;		// 籍贯
	private String phone;		// 联系电话
	private String politicalAffiliation;		// 政治面貌
	private String position;		// 现任职务
	private String status;		// 是否在职
	private String twomenu;		// 董事-监事-高管-其他从业人员
	private String xueLi;		// 学历,文化程度
	private String jishuzhicheng;		// 技术职称
	private String jobYear;		// 工作年限
	private String marry;		// 婚否
	private String professional;		// 专业
	private String school;		// 毕业院校
	private String xiangguanJobYear;		// 相关工作年限
	private String signDate;		// 报备日期
	private String cardType;		// 身份证类型
	private String liDate;		// li_date
	private String ruDate;		// ru_date
	private String biDate;		// bi_date
	private String depart;		// depart
	private String sourceFlag;		// source_flag
	private String sourceStatus;		// source_status
	private String approvalDate;		// approval_date
	private String nifId;		// nif_id
	
	public SysOfficeEmployee() {
		super();
	}

	public SysOfficeEmployee(String id){
		super(id);
	}

	@Length(min=1, max=64, message="机构标识长度必须介于 1 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=255, message="户口所在地长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="出生年月长度必须介于 0 和 255 之间")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=255, message="电子邮件长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="删除标识长度必须介于 0 和 255 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=255, message="性别长度必须介于 0 和 255 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=255, message="身份证号码长度必须介于 0 和 255 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Length(min=0, max=5000, message="简历长度必须介于 0 和 5000 之间")
	public String getJianLi() {
		return jianLi;
	}

	public void setJianLi(String jianLi) {
		this.jianLi = jianLi;
	}
	
	@Length(min=0, max=255, message="从业禁止情形长度必须介于 0 和 255 之间")
	public String getJinzhi() {
		return jinzhi;
	}

	public void setJinzhi(String jinzhi) {
		this.jinzhi = jinzhi;
	}
	
	@Length(min=0, max=255, message="通讯地址长度必须介于 0 和 255 之间")
	public String getMessAddr() {
		return messAddr;
	}

	public void setMessAddr(String messAddr) {
		this.messAddr = messAddr;
	}
	
	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="民族长度必须介于 0 和 255 之间")
	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}
	
	@Length(min=0, max=255, message="籍贯长度必须介于 0 和 255 之间")
	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="政治面貌长度必须介于 0 和 255 之间")
	public String getPoliticalAffiliation() {
		return politicalAffiliation;
	}

	public void setPoliticalAffiliation(String politicalAffiliation) {
		this.politicalAffiliation = politicalAffiliation;
	}
	
	@Length(min=0, max=255, message="现任职务长度必须介于 0 和 255 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=255, message="是否在职长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="董事-监事-高管-其他从业人员长度必须介于 0 和 255 之间")
	public String getTwomenu() {
		return twomenu;
	}

	public void setTwomenu(String twomenu) {
		this.twomenu = twomenu;
	}
	
	@Length(min=0, max=255, message="学历,文化程度长度必须介于 0 和 255 之间")
	public String getXueLi() {
		return xueLi;
	}

	public void setXueLi(String xueLi) {
		this.xueLi = xueLi;
	}
	
	@Length(min=0, max=255, message="技术职称长度必须介于 0 和 255 之间")
	public String getJishuzhicheng() {
		return jishuzhicheng;
	}

	public void setJishuzhicheng(String jishuzhicheng) {
		this.jishuzhicheng = jishuzhicheng;
	}
	
	@Length(min=0, max=255, message="工作年限长度必须介于 0 和 255 之间")
	public String getJobYear() {
		return jobYear;
	}

	public void setJobYear(String jobYear) {
		this.jobYear = jobYear;
	}
	
	@Length(min=0, max=255, message="婚否长度必须介于 0 和 255 之间")
	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}
	
	@Length(min=0, max=255, message="专业长度必须介于 0 和 255 之间")
	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}
	
	@Length(min=0, max=255, message="毕业院校长度必须介于 0 和 255 之间")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	@Length(min=0, max=255, message="相关工作年限长度必须介于 0 和 255 之间")
	public String getXiangguanJobYear() {
		return xiangguanJobYear;
	}

	public void setXiangguanJobYear(String xiangguanJobYear) {
		this.xiangguanJobYear = xiangguanJobYear;
	}
	
	@Length(min=0, max=255, message="报备日期长度必须介于 0 和 255 之间")
	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=255, message="身份证类型长度必须介于 0 和 255 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Length(min=0, max=255, message="li_date长度必须介于 0 和 255 之间")
	public String getLiDate() {
		return liDate;
	}

	public void setLiDate(String liDate) {
		this.liDate = liDate;
	}
	
	@Length(min=0, max=255, message="ru_date长度必须介于 0 和 255 之间")
	public String getRuDate() {
		return ruDate;
	}

	public void setRuDate(String ruDate) {
		this.ruDate = ruDate;
	}
	
	@Length(min=0, max=255, message="bi_date长度必须介于 0 和 255 之间")
	public String getBiDate() {
		return biDate;
	}

	public void setBiDate(String biDate) {
		this.biDate = biDate;
	}
	
	@Length(min=0, max=255, message="depart长度必须介于 0 和 255 之间")
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
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
	
	@Length(min=0, max=255, message="approval_date长度必须介于 0 和 255 之间")
	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	@Length(min=0, max=255, message="nif_id长度必须介于 0 和 255 之间")
	public String getNifId() {
		return nifId;
	}

	public void setNifId(String nifId) {
		this.nifId = nifId;
	}
	
}