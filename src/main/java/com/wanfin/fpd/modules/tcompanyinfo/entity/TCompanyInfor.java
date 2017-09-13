/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tcompanyinfo.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 企业高管信息Entity
 * @author TcompanyInfo
 * @version 2016-08-11
 */
public class TCompanyInfor extends DataEntity<TCompanyInfor> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 户口所在地
	private Date birthday;		// 出生日期
	private String cardType;		// 证件类型
	private String email;		// 电子邮件
	private String sex;		// 性别
	private String cardid;		// 证件号码
	private String jishuzhicheng;		// 技术职称
	private String jobYear;		// 工作年限
	private String marry;		// 婚姻状况
	private String messAddr;		// 通讯地址
	private String name;		// 姓名
	private String national;		// 民族
	private String nativePlace;		// 籍贯
	private String phone;		// 联系电话
	private String politicalAffiliation;		// 政治面貌
	private String position;		// 现任职务
	private String professional;		// 专业
	private String school;		// 毕业院校
	private String status;		// 是否在职
	private String twomenu;		// 人员类别
	private String xiangguanJobYear;		// 相关工作年限
	private String xueLi;		// 学历
	private String companyId;		// company_id
	
	public TCompanyInfor() {
		super();
	}

	public TCompanyInfor(String id){
		super(id);
	}

	@Length(min=0, max=255, message="户口所在地长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=11, message="证件类型长度必须介于 0 和 11 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Length(min=0, max=255, message="电子邮件长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=11, message="性别长度必须介于 0 和 11 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="证件号码长度必须介于 0 和 255 之间")
	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
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
	
	@Length(min=0, max=255, message="婚姻状况长度必须介于 0 和 255 之间")
	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
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
	
	@Length(min=0, max=11, message="是否在职长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="人员类别长度必须介于 0 和 255 之间")
	public String getTwomenu() {
		return twomenu;
	}

	public void setTwomenu(String twomenu) {
		this.twomenu = twomenu;
	}
	
	@Length(min=0, max=255, message="相关工作年限长度必须介于 0 和 255 之间")
	public String getXiangguanJobYear() {
		return xiangguanJobYear;
	}

	public void setXiangguanJobYear(String xiangguanJobYear) {
		this.xiangguanJobYear = xiangguanJobYear;
	}
	
	@Length(min=0, max=11, message="学历长度必须介于 0 和 11 之间")
	public String getXueLi() {
		return xueLi;
	}

	public void setXueLi(String xueLi) {
		this.xueLi = xueLi;
	}
	
	@Length(min=0, max=50, message="company_id长度必须介于 0 和 50 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}