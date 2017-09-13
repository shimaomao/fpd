/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 商户数据信息Entity
 * @author cjp
 * @version 2017-07-03
 */
public class Merchant extends DataEntity<Merchant> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 卖家在易联的编号
	private String pvdUid;		// 贷款供应商用户id
	private String regStatus; //开通状态 :1、开通成功;2、开通失败
	private String comparison;		// 用户比对结果
	private String userNameComp;	//姓名比对结果
	private String identityNumComp;	//身份证号比对结果
	private String phoneComp;	//手机号码比对结果
	private String accountNumComp;	//银行账号比对结果
	private String accountNo;//银行账号
	private String accountOwnerName;//银行账号名称
	private String accountOwnerNumber;//银行账号绑定证件号
	private String accountRelatedPhone;//银行账号绑定手机号码

	private String merchantId;      //店铺ID
	private String userName;		// 用户名
	private String shopCode;		// 商户号
	private String shopName;		// 商户名称
	private String email;		// 邮箱
	private String corpName;		// 公司名称
	private String phone;		// 电话号码
	private String ecommerce;		// 电商平台
	private String ecommerceLink;		// 电商平台链接
	private String wareHouse;		// 仓库
	private String goodsType;		// 商品类别
	private String incomeAllotment;		// 收益分成
	private String merchantAddress;		// 商户地址
	private String merchantRegDate;		// 商户注册日期
	private String assessment;		// 评价数量
	private String avgAssessment;		// 平均评价水平
	private String shopType;		// 商户类型  1个人  2企业
	private String qqNumb;		// qq号
	private String idPhoto;		// 身份证照片
	private String idPhotoPath;//身份证照片保存路径
	private String idName;		// 身份证姓名
	private String idNumber;		// 身份证号码
	private String recentUpdateDate;		// 最近一次更新时间
	private String firstWriteName;		// 初始填写姓名
	private String businessLicense;		// 营业执照
	private String businessLicensePath;		// 营业执照照片保存路径
	private String businessLicenseNumb;		// 营业执照号
	private String taxRegistRation;		// 税务登记证
	private String legalPersonPhoto;		// 法人身份证照片
	private String legalPersonPhotoPath;//法人身份证照片保存路径
	private String legalPersonName;		// 法人代表姓名
	private String legalPersonIdNumber;		// 法人代表身份证号
	private String recentLegalUpdateDate;		// 最近更新时间（法人代表）
	private String firstWritelegalPersonName;		// 最初始填写法人姓名
	private Date shopCreateDate;//商户创建时间
	private Date shopUpdateDate;//商户最近修改时间
	private String shopBusiLength; 		//店铺经营总时长，单位：月
	private String lastBusiLength;		//商户一年内最近一次在平台连续经营时长，单位：月

	private String code; //短信验证码
	
	private String openStatus;//开通状态    0：新增(默认)  1开通申请中    2开通成功     3开通失败
	
	public Merchant() {
		super();
	}

	public Merchant(String id){
		super(id);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=255, message="贷款供应商用户id长度必须介于 0 和 255 之间")
	public String getPvdUid() {
		return pvdUid;
	}

	public void setPvdUid(String pvdUid) {
		this.pvdUid = pvdUid;
	}
	
	@Length(min=0, max=255, message="用户比对结果长度必须介于 0 和 255 之间")
	public String getComparison() {
		return comparison;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Length(min=0, max=255, message="用户名长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=255, message="商户号长度必须介于 0 和 255 之间")
	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
	@Length(min=0, max=255, message="商户名称长度必须介于 0 和 255 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="公司名称长度必须介于 0 和 255 之间")
	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	
	@Length(min=0, max=255, message="电话号码长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="电商平台长度必须介于 0 和 255 之间")
	public String getEcommerce() {
		return ecommerce;
	}

	public void setEcommerce(String ecommerce) {
		this.ecommerce = ecommerce;
	}
	
	@Length(min=0, max=255, message="电商平台链接长度必须介于 0 和 255 之间")
	public String getEcommerceLink() {
		return ecommerceLink;
	}

	public void setEcommerceLink(String ecommerceLink) {
		this.ecommerceLink = ecommerceLink;
	}
	
	@Length(min=0, max=255, message="仓库长度必须介于 0 和 255 之间")
	public String getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	
	@Length(min=0, max=255, message="商品类别长度必须介于 0 和 255 之间")
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	@Length(min=0, max=255, message="收益分成长度必须介于 0 和 255 之间")
	public String getIncomeAllotment() {
		return incomeAllotment;
	}

	public void setIncomeAllotment(String incomeAllotment) {
		this.incomeAllotment = incomeAllotment;
	}
	
	@Length(min=0, max=255, message="商户地址长度必须介于 0 和 255 之间")
	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}
	
	@Length(min=0, max=255, message="商户注册日期长度必须介于 0 和 255 之间")
	public String getMerchantRegDate() {
		return merchantRegDate;
	}

	public void setMerchantRegDate(String merchantRegDate) {
		this.merchantRegDate = merchantRegDate;
	}
	
	@Length(min=0, max=255, message="评价数量长度必须介于 0 和 255 之间")
	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	
	@Length(min=0, max=255, message="平均评价水平长度必须介于 0 和 255 之间")
	public String getAvgAssessment() {
		return avgAssessment;
	}

	public void setAvgAssessment(String avgAssessment) {
		this.avgAssessment = avgAssessment;
	}
	
	@Length(min=0, max=255, message="商户类型长度必须介于 0 和 255 之间")
	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	
	@Length(min=0, max=255, message="qq号长度必须介于 0 和 255 之间")
	public String getQqNumb() {
		return qqNumb;
	}

	public void setQqNumb(String qqNumb) {
		this.qqNumb = qqNumb;
	}
	
	public String getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(String idPhoto) {
		this.idPhoto = idPhoto;
	}
	
	@Length(min=0, max=255, message="身份证照片长度必须介于 0 和 255 之间")
	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}
	
	@Length(min=0, max=255, message="身份证号码长度必须介于 0 和 255 之间")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	@Length(min=0, max=255, message="最近一次更新时间长度必须介于 0 和 255 之间")
	public String getRecentUpdateDate() {
		return recentUpdateDate;
	}

	public void setRecentUpdateDate(String recentUpdateDate) {
		this.recentUpdateDate = recentUpdateDate;
	}
	
	@Length(min=0, max=255, message="初始填写姓名长度必须介于 0 和 255 之间")
	public String getFirstWriteName() {
		return firstWriteName;
	}

	public void setFirstWriteName(String firstWriteName) {
		this.firstWriteName = firstWriteName;
	}
	
	@Length(min=0, max=255, message="营业执照长度必须介于 0 和 255 之间")
	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	
	@Length(min=0, max=255, message="营业执照号长度必须介于 0 和 255 之间")
	public String getBusinessLicenseNumb() {
		return businessLicenseNumb;
	}

	public void setBusinessLicenseNumb(String businessLicenseNumb) {
		this.businessLicenseNumb = businessLicenseNumb;
	}
	
	@Length(min=0, max=255, message="税务登记证长度必须介于 0 和 255 之间")
	public String getTaxRegistRation() {
		return taxRegistRation;
	}

	public void setTaxRegistRation(String taxRegistRation) {
		this.taxRegistRation = taxRegistRation;
	}
	
	public String getLegalPersonPhoto() {
		return legalPersonPhoto;
	}

	public void setLegalPersonPhoto(String legalPersonPhoto) {
		this.legalPersonPhoto = legalPersonPhoto;
	}
	
	@Length(min=0, max=255, message="法人代表姓名长度必须介于 0 和 255 之间")
	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	
	@Length(min=0, max=255, message="法人代表身份证号长度必须介于 0 和 255 之间")
	public String getLegalPersonIdNumber() {
		return legalPersonIdNumber;
	}

	public void setLegalPersonIdNumber(String legalPersonIdNumber) {
		this.legalPersonIdNumber = legalPersonIdNumber;
	}
	
	@Length(min=0, max=255, message="最近更新时间（法人代表）长度必须介于 0 和 255 之间")
	public String getRecentLegalUpdateDate() {
		return recentLegalUpdateDate;
	}

	public void setRecentLegalUpdateDate(String recentLegalUpdateDate) {
		this.recentLegalUpdateDate = recentLegalUpdateDate;
	}
	
	@Length(min=0, max=255, message="最初始填写法人姓名长度必须介于 0 和 255 之间")
	public String getFirstWritelegalPersonName() {
		return firstWritelegalPersonName;
	}

	public void setFirstWritelegalPersonName(String firstWritelegalPersonName) {
		this.firstWritelegalPersonName = firstWritelegalPersonName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}

	public String getUserNameComp() {
		return userNameComp;
	}

	public void setUserNameComp(String userNameComp) {
		this.userNameComp = userNameComp;
	}

	public String getIdentityNumComp() {
		return identityNumComp;
	}

	public void setIdentityNumComp(String identityNumComp) {
		this.identityNumComp = identityNumComp;
	}

	public String getPhoneComp() {
		return phoneComp;
	}

	public void setPhoneComp(String phoneComp) {
		this.phoneComp = phoneComp;
	}

	public String getAccountNumComp() {
		return accountNumComp;
	}

	public void setAccountNumComp(String accountNumComp) {
		this.accountNumComp = accountNumComp;
	}

	public Date getShopCreateDate() {
		return shopCreateDate;
	}

	public void setShopCreateDate(Date shopCreateDate) {
		this.shopCreateDate = shopCreateDate;
	}

	public Date getShopUpdateDate() {
		return shopUpdateDate;
	}

	public void setShopUpdateDate(Date shopUpdateDate) {
		this.shopUpdateDate = shopUpdateDate;
	}

	public String getShopBusiLength() {
		return shopBusiLength;
	}

	public void setShopBusiLength(String shopBusiLength) {
		this.shopBusiLength = shopBusiLength;
	}

	public String getLastBusiLength() {
		return lastBusiLength;
	}

	public void setLastBusiLength(String lastBusiLength) {
		this.lastBusiLength = lastBusiLength;
	}
	
	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountOwnerName() {
		return accountOwnerName;
	}

	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}

	public String getAccountOwnerNumber() {
		return accountOwnerNumber;
	}

	public void setAccountOwnerNumber(String accountOwnerNumber) {
		this.accountOwnerNumber = accountOwnerNumber;
	}

	public String getAccountRelatedPhone() {
		return accountRelatedPhone;
	}

	public void setAccountRelatedPhone(String accountRelatedPhone) {
		this.accountRelatedPhone = accountRelatedPhone;
	}

	public String getIdPhotoPath() {
		return idPhotoPath;
	}

	public void setIdPhotoPath(String idPhotoPath) {
		this.idPhotoPath = idPhotoPath;
	}

	public String getLegalPersonPhotoPath() {
		return legalPersonPhotoPath;
	}

	public void setLegalPersonPhotoPath(String legalPersonPhotoPath) {
		this.legalPersonPhotoPath = legalPersonPhotoPath;
	}

	public String getBusinessLicensePath() {
		return businessLicensePath;
	}

	public void setBusinessLicensePath(String businessLicensePath) {
		this.businessLicensePath = businessLicensePath;
	}

	/**
	 * 参数简单校验
	 * @return
	 */
	public boolean verifyDatas(boolean isAllMust){
		if(StringUtils.isBlank(userId)){
			return false;
		}else if(StringUtils.isBlank(userName)){
			return false;
		}else if(StringUtils.isBlank(merchantId)){//shopCode
			return false;
		}else if(StringUtils.isBlank(shopName)){
			return false;
		}else if(StringUtils.isBlank(regStatus)){
			return false;
		}else if(StringUtils.isBlank(shopType)){
			return false;
		}else if("1".equals(shopType) && (StringUtils.isBlank(idName) || StringUtils.isBlank(idNumber))){// || StringUtils.isBlank(idPhoto)
			return false;
		}else if("2".equals(shopType) && (StringUtils.isBlank(corpName) || StringUtils.isBlank(businessLicenseNumb))){// || StringUtils.isBlank(legalPersonName) || StringUtils.isBlank(legalPersonIdNumber) || StringUtils.isBlank(legalPersonPhoto)
			return false;
		}
		if(isAllMust){
			if(StringUtils.isBlank(accountNo)){//
				return false;
			}else if(StringUtils.isBlank(accountOwnerName)){//
				return false;
			}else if(StringUtils.isBlank(accountOwnerNumber)){//
				return false;
			}else if(StringUtils.isBlank(accountRelatedPhone)){//
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Merchant [id=" + id + ", userId=" + userId + ", pvdUid=" + pvdUid + ", regStatus=" + regStatus + ", comparison="
				+ comparison + ", userNameComp=" + userNameComp + ", identityNumComp=" + identityNumComp
				+ ", phoneComp=" + phoneComp + ", accountNumComp=" + accountNumComp + ", accountNo=" + accountNo
				+ ", accountOwnerName=" + accountOwnerName + ", accountOwnerNumber=" + accountOwnerNumber
				+ ", accountRelatedPhone=" + accountRelatedPhone + ", merchantId=" + merchantId + ", userName="
				+ userName + ", shopCode=" + shopCode + ", shopName=" + shopName + ", email=" + email + ", corpName="
				+ corpName + ", phone=" + phone + ", ecommerce=" + ecommerce + ", ecommerceLink=" + ecommerceLink
				+ ", wareHouse=" + wareHouse + ", goodsType=" + goodsType + ", incomeAllotment=" + incomeAllotment
				+ ", merchantAddress=" + merchantAddress + ", merchantRegDate=" + merchantRegDate + ", assessment="
				+ assessment + ", avgAssessment=" + avgAssessment + ", shopType=" + shopType + ", qqNumb=" + qqNumb
				+ ", idPhoto=" + idPhoto + ", idPhotoPath=" + idPhotoPath + ", idName=" + idName + ", idNumber="
				+ idNumber + ", recentUpdateDate=" + recentUpdateDate + ", firstWriteName=" + firstWriteName
				+ ", businessLicense=" + businessLicense + ", businessLicenseNumb=" + businessLicenseNumb
				+ ", taxRegistRation=" + taxRegistRation + ", legalPersonPhoto=" + legalPersonPhoto
				+ ", legalPersonPhotoPath=" + legalPersonPhotoPath + ", legalPersonName=" + legalPersonName
				+ ", legalPersonIdNumber=" + legalPersonIdNumber + ", recentLegalUpdateDate=" + recentLegalUpdateDate
				+ ", firstWritelegalPersonName=" + firstWritelegalPersonName + ", shopCreateDate=" + shopCreateDate
				+ ", shopUpdateDate=" + shopUpdateDate + ", shopBusiLength=" + shopBusiLength + ", lastBusiLength="
				+ lastBusiLength + ", code=" + code + ", openStatus=" + openStatus + "]";
	}
}