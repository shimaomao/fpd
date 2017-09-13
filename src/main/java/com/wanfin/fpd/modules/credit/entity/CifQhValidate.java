/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.hibernate.validator.constraints.Length;
import com.wanfin.fpd.modules.sys.entity.Office;

import net.sf.json.JSONObject;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.HttpPostJson;

/**
 * creditresultEntity
 * @author cjp
 * @version 2017-05-09
 */
public class CifQhValidate extends DataEntity<CifQhValidate> {
	
	private static final long serialVersionUID = 1L;
	private String resId;		// res_struct主键
	private String idNo;		// 证件号码
	private String idType;		// 证件类型
	private String name;		// 主体名称
	private String mobileNo;		// 手机号码
	private String seqNo;		// 序列号
	private String isRealIdentity;		// 是否真实身份
	private String isValidAddress;		// 是否本人真实活动地址
	private String addressType;		// 地址类型
	private String isRealCompany;		// 是否真实工作单位
	private String companySimDeg;		// 单位匹配度分值
	private String appear1ThDate;		// 单位最初出现时间
	private String appearLastDate;		// 单位最近一次出现时间
	private String isOwnerMobile;		// 手机验证结果
	private String ownerMobileStatus;		// 手机状态
	private String useTimeScore;		// 使用时间分数
	private String isExistRel;		// 是否存在关系
	private String relLevel;		// 关系力度
	private String carChkResult;		// 车辆验证结果
	private String isAsyned;		// 是否异步返回结果
	private String carTypeInc;		// 车型
	private String carFactoryInc;		// 厂牌
	private String carPrice;		// 新车购置价
	private String drvStatusQryId;		// 行驶证状态查询Id号
	private String houseChkResult;		// 房屋验证结果
	private String houseDataDate;		// 房屋数据获取时间
	private String photoCmpResult;		// 相片比对结果
	private String photoSimDeg;		// 相片相似度
	private String isHighestEduBkg;		// 是否本人真实最高学历
	private String dataDate;		// 数据获取时间
	private String graduateSchool;		// 毕业院校
	private String graduateMajor;		// 毕业专业
	private String graduateYear;		// 毕业年份
	private String isOwnerMobileTwo;		// 手机验证II结果
	private String ownerMobileStatusTwo;		// 手机状态II
	private String useTimeScoreTwo;		// 使用时间分数II
	private String errorInfo;		// 错误信息
	private Office office;		// 部门ID
	private String searchCode; //征信服务类型
	
	
	private String sourseId; //来源代码
	private String credooScore; //总和评分
	private String dataBuildTime; //业务发生时间
	private String dataStatus; //数据状态
	private String erCode; //返回状态码
	private String erMsg; //返回信息
	
	private String rskScore; //风险评分
	private String rskMark; //风险标记
	
	private String isMachdBlMakt; //命中第三方标注黑名单 0否 1是
	private String isMachCraCall; //命中骚扰电话   0否 1是
	private String isMachFraud;		// 命中欺诈号码 0否 1是
	private String isMachEmpty;		// 命中空号（非正常短信语音号码）
	private String isMachYZmobile;		// 命中收码平台号码
	private String isMachSmallNo;		// 命中小号
	private String isMachSzNo;		// 命中社工库号码
	private String iUpdateDate;		// ip风险时间
	private String mUpdateDate;		// 手机号码风险时间
	private String iRskDesc;		// IP风险描述
	private String mRskDesc;		// 手机号码风险描述
	
	private String sourceId;		// 来源代码
	private String province;		// 省（简称）
	private String cityInfo;		// 城市信息
	private String borough;		// 区（简称）
	private String fmtAddress;		// 格式化地址
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String buildingName;		// 楼盘名称
	private String buildingAddress;		// 楼盘地址
	private String houseArodAvgPrice;		// 楼盘周边均价
	private String houseAvgPrice;		// 楼盘均价
	private String state;		// 查询数据状态
	private String buildingType;		// 建筑类型
	private String isMatch;		// 是否匹配
	private String addressPorperty;		// 地址属性
	
	private String baseDate;		// 基准日期
	private String credooScoreM0;		// 基准日期好信度分
	private String credooScoreM1;		// 基准日期前推1个月好信度分
	private String credooScoreM2;		// 基准日期前推2个月好信度分
	private String credooScoreM3;		// 基准日期前推3个月好信度分
	private String credooScoreM4;		// 基准日期前推4个月好信度分
	private String credooScoreM5;		// 基准日期前推5个月好信度分
	
	private String driviliceNo; //驾驶证号
	private String score;		// 评分
	private String label;		// 标签
	private String userName;		// 用户姓名
	private String transId;		// 交易ID
	private String clientId;		// 客户端ID
	private String records; //records内容,太长存json
	
	private String accountNo;		// 银行卡号
	private String dcFlag;		// 借贷标记
	private String summaryScore;		// 银联智策消费综合评分
	private String cstScore;		// 卡状态得分表
	private String cotScore;		// 套现模型得分
	private String cntScore;		// 消费趋势得分
	private String cnaScore;		// 消费能力得分
	private String chvScore;		// 持卡人价值得分
	private String dsiScore;		// 消费自由度得分
	private String wlpScore;		// 钱包位置得分
	private String cnpScore;		// 消费偏好得分
	private String cotCluster;		// 套现模型分类
	private String dsiCluster;		// 消费自由度分类
	private String rskCluster;		// 风险得分分类
	private String crbScore;		// 跨境得分
	private String crbCluster;		// 跨境得分分类
	
	private String creditCardCVN; //信用卡cvn码
	private String expiresYear; //有效期年
	private String expiresMonth; //有效期月
	private String reasonCode; //原因代码
	private String entityAuthCode; //卡主授权代号
	private String entityAuthDate; //卡主授权时间
	private String authResult; //鉴定结果
	
	private String entityName;		// 姓名\企业名称
	private String entityId;		// 证件号码
	private String caseStatus;		// 案件状态
	private String buildDate;		// 立案时间
	private String courtName;		// 法院
	private String execMoney;		// 执行标的
	private String caseCode;		// 案号
	
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

	public String getIsMachSzNo() {
		return isMachSzNo;
	}

	public void setIsMachSzNo(String isMachSzNo) {
		this.isMachSzNo = isMachSzNo;
	}

	public String getiUpdateDate() {
		return iUpdateDate;
	}

	public void setiUpdateDate(String iUpdateDate) {
		this.iUpdateDate = iUpdateDate;
	}

	public String getmUpdateDate() {
		return mUpdateDate;
	}

	public void setmUpdateDate(String mUpdateDate) {
		this.mUpdateDate = mUpdateDate;
	}

	public String getiRskDesc() {
		return iRskDesc;
	}

	public void setiRskDesc(String iRskDesc) {
		this.iRskDesc = iRskDesc;
	}

	public String getmRskDesc() {
		return mRskDesc;
	}

	public void setmRskDesc(String mRskDesc) {
		this.mRskDesc = mRskDesc;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getSourseId() {
		return sourseId;
	}

	public void setSourseId(String sourseId) {
		this.sourseId = sourseId;
	}

	public String getCredooScore() {
		return credooScore;
	}

	public void setCredooScore(String credooScore) {
		this.credooScore = credooScore;
	}

	public String getDataBuildTime() {
		return dataBuildTime;
	}

	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
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

	public String getRskScore() {
		return rskScore;
	}

	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}

	public String getRskMark() {
		return rskMark;
	}

	public void setRskMark(String rskMark) {
		this.rskMark = rskMark;
	}

	private String address;	//地址
	private String company; //公司名称
	private String refName; //关系人姓名
	private String refMobileNo; //关系人手机号
	
	private String carNo; //车牌号
	private String engineNo; //发动机号
	private String needeQryDrvStatus; //是否查询行驶证状态 0否 1是
	private String carFrameNum; // 车架号
	private String eductionBckgrd; //学历编号
	private String cardNo; //银行卡号
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEductionBckgrd() {
		return eductionBckgrd;
	}

	public void setEductionBckgrd(String eductionBckgrd) {
		this.eductionBckgrd = eductionBckgrd;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getNeedeQryDrvStatus() {
		return needeQryDrvStatus;
	}

	public void setNeedeQryDrvStatus(String needeQryDrvStatus) {
		this.needeQryDrvStatus = needeQryDrvStatus;
	}

	public String getCarFrameNum() {
		return carFrameNum;
	}

	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRefMobileNo() {
		return refMobileNo;
	}

	public void setRefMobileNo(String refMobileNo) {
		this.refMobileNo = refMobileNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public CifQhValidate() {
		super();
	}

	public CifQhValidate(String id){
		super(id);
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=64, message="主体名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=24, message="手机号码长度必须介于 0 和 24 之间")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Length(min=0, max=24, message="序列号长度必须介于 0 和 24 之间")
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	public String getIsRealIdentity() {
		return isRealIdentity;
	}

	public void setIsRealIdentity(String isRealIdentity) {
		this.isRealIdentity = isRealIdentity;
	}
	
	public String getIsValidAddress() {
		return isValidAddress;
	}

	public void setIsValidAddress(String isValidAddress) {
		this.isValidAddress = isValidAddress;
	}
	
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	public String getIsRealCompany() {
		return isRealCompany;
	}

	public void setIsRealCompany(String isRealCompany) {
		this.isRealCompany = isRealCompany;
	}
	
	public String getCompanySimDeg() {
		return companySimDeg;
	}

	public void setCompanySimDeg(String companySimDeg) {
		this.companySimDeg = companySimDeg;
	}
	
	@Length(min=0, max=24, message="单位最初出现时间长度必须介于 0 和 24 之间")
	public String getAppear1ThDate() {
		return appear1ThDate;
	}

	public void setAppear1ThDate(String appear1ThDate) {
		this.appear1ThDate = appear1ThDate;
	}
	
	@Length(min=0, max=24, message="单位最近一次出现时间长度必须介于 0 和 24 之间")
	public String getAppearLastDate() {
		return appearLastDate;
	}

	public void setAppearLastDate(String appearLastDate) {
		this.appearLastDate = appearLastDate;
	}
	
	@Length(min=0, max=8, message="手机验证结果长度必须介于 0 和 8 之间")
	public String getIsOwnerMobile() {
		return isOwnerMobile;
	}

	public void setIsOwnerMobile(String isOwnerMobile) {
		this.isOwnerMobile = isOwnerMobile;
	}
	
	@Length(min=0, max=8, message="手机状态长度必须介于 0 和 8 之间")
	public String getOwnerMobileStatus() {
		return ownerMobileStatus;
	}

	public void setOwnerMobileStatus(String ownerMobileStatus) {
		this.ownerMobileStatus = ownerMobileStatus;
	}
	
	@Length(min=0, max=8, message="使用时间分数长度必须介于 0 和 8 之间")
	public String getUseTimeScore() {
		return useTimeScore;
	}

	public void setUseTimeScore(String useTimeScore) {
		this.useTimeScore = useTimeScore;
	}
	
	@Length(min=0, max=8, message="是否存在关系长度必须介于 0 和 8 之间")
	public String getIsExistRel() {
		return isExistRel;
	}

	public void setIsExistRel(String isExistRel) {
		this.isExistRel = isExistRel;
	}
	
	@Length(min=0, max=8, message="关系力度长度必须介于 0 和 8 之间")
	public String getRelLevel() {
		return relLevel;
	}

	public void setRelLevel(String relLevel) {
		this.relLevel = relLevel;
	}
	
	@Length(min=0, max=2, message="车辆验证结果长度必须介于 0 和 2 之间")
	public String getCarChkResult() {
		return carChkResult;
	}

	public void setCarChkResult(String carChkResult) {
		this.carChkResult = carChkResult;
	}
	
	@Length(min=0, max=2, message="是否异步返回结果长度必须介于 0 和 2 之间")
	public String getIsAsyned() {
		return isAsyned;
	}

	public void setIsAsyned(String isAsyned) {
		this.isAsyned = isAsyned;
	}
	
	@Length(min=0, max=64, message="车型长度必须介于 0 和 64 之间")
	public String getCarTypeInc() {
		return carTypeInc;
	}

	public void setCarTypeInc(String carTypeInc) {
		this.carTypeInc = carTypeInc;
	}
	
	@Length(min=0, max=128, message="厂牌长度必须介于 0 和 128 之间")
	public String getCarFactoryInc() {
		return carFactoryInc;
	}

	public void setCarFactoryInc(String carFactoryInc) {
		this.carFactoryInc = carFactoryInc;
	}
	
	@Length(min=0, max=24, message="新车购置价长度必须介于 0 和 24 之间")
	public String getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}
	
	@Length(min=0, max=64, message="行驶证状态查询Id号长度必须介于 0 和 64 之间")
	public String getDrvStatusQryId() {
		return drvStatusQryId;
	}

	public void setDrvStatusQryId(String drvStatusQryId) {
		this.drvStatusQryId = drvStatusQryId;
	}
	
	@Length(min=0, max=2, message="房屋验证结果长度必须介于 0 和 2 之间")
	public String getHouseChkResult() {
		return houseChkResult;
	}

	public void setHouseChkResult(String houseChkResult) {
		this.houseChkResult = houseChkResult;
	}
	
	@Length(min=0, max=24, message="房屋数据获取时间长度必须介于 0 和 24 之间")
	public String getHouseDataDate() {
		return houseDataDate;
	}

	public void setHouseDataDate(String houseDataDate) {
		this.houseDataDate = houseDataDate;
	}
	
	@Length(min=0, max=64, message="相片比对结果长度必须介于 0 和 64 之间")
	public String getPhotoCmpResult() {
		return photoCmpResult;
	}

	public void setPhotoCmpResult(String photoCmpResult) {
		this.photoCmpResult = photoCmpResult;
	}
	
	@Length(min=0, max=8, message="相片相似度长度必须介于 0 和 8 之间")
	public String getPhotoSimDeg() {
		return photoSimDeg;
	}

	public void setPhotoSimDeg(String photoSimDeg) {
		this.photoSimDeg = photoSimDeg;
	}
	
	@Length(min=0, max=2, message="是否本人真实最高学历长度必须介于 0 和 2 之间")
	public String getIsHighestEduBkg() {
		return isHighestEduBkg;
	}

	public void setIsHighestEduBkg(String isHighestEduBkg) {
		this.isHighestEduBkg = isHighestEduBkg;
	}
	
	@Length(min=0, max=8, message="数据获取时间长度必须介于 0 和 8 之间")
	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	
	@Length(min=0, max=64, message="毕业院校长度必须介于 0 和 64 之间")
	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	
	@Length(min=0, max=64, message="毕业专业长度必须介于 0 和 64 之间")
	public String getGraduateMajor() {
		return graduateMajor;
	}

	public void setGraduateMajor(String graduateMajor) {
		this.graduateMajor = graduateMajor;
	}
	
	@Length(min=0, max=8, message="毕业年份长度必须介于 0 和 8 之间")
	public String getGraduateYear() {
		return graduateYear;
	}

	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}
	
	@Length(min=0, max=8, message="手机验证II结果长度必须介于 0 和 8 之间")
	public String getIsOwnerMobileTwo() {
		return isOwnerMobileTwo;
	}

	public void setIsOwnerMobileTwo(String isOwnerMobileTwo) {
		this.isOwnerMobileTwo = isOwnerMobileTwo;
	}
	
	@Length(min=0, max=8, message="手机状态II长度必须介于 0 和 8 之间")
	public String getOwnerMobileStatusTwo() {
		return ownerMobileStatusTwo;
	}

	public void setOwnerMobileStatusTwo(String ownerMobileStatusTwo) {
		this.ownerMobileStatusTwo = ownerMobileStatusTwo;
	}
	
	@Length(min=0, max=8, message="使用时间分数II长度必须介于 0 和 8 之间")
	public String getUseTimeScoreTwo() {
		return useTimeScoreTwo;
	}

	public void setUseTimeScoreTwo(String useTimeScoreTwo) {
		this.useTimeScoreTwo = useTimeScoreTwo;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}

	public String getBorough() {
		return borough;
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}

	public String getFmtAddress() {
		return fmtAddress;
	}

	public void setFmtAddress(String fmtAddress) {
		this.fmtAddress = fmtAddress;
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

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingAddress() {
		return buildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		this.buildingAddress = buildingAddress;
	}

	public String getHouseArodAvgPrice() {
		return houseArodAvgPrice;
	}

	public void setHouseArodAvgPrice(String houseArodAvgPrice) {
		this.houseArodAvgPrice = houseArodAvgPrice;
	}

	public String getHouseAvgPrice() {
		return houseAvgPrice;
	}

	public void setHouseAvgPrice(String houseAvgPrice) {
		this.houseAvgPrice = houseAvgPrice;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}

	public String getAddressPorperty() {
		return addressPorperty;
	}

	public void setAddressPorperty(String addressPorperty) {
		this.addressPorperty = addressPorperty;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public String getCredooScoreM0() {
		return credooScoreM0;
	}

	public void setCredooScoreM0(String credooScoreM0) {
		this.credooScoreM0 = credooScoreM0;
	}

	public String getCredooScoreM1() {
		return credooScoreM1;
	}

	public void setCredooScoreM1(String credooScoreM1) {
		this.credooScoreM1 = credooScoreM1;
	}

	public String getCredooScoreM2() {
		return credooScoreM2;
	}

	public void setCredooScoreM2(String credooScoreM2) {
		this.credooScoreM2 = credooScoreM2;
	}

	public String getCredooScoreM3() {
		return credooScoreM3;
	}

	public void setCredooScoreM3(String credooScoreM3) {
		this.credooScoreM3 = credooScoreM3;
	}

	public String getCredooScoreM4() {
		return credooScoreM4;
	}

	public void setCredooScoreM4(String credooScoreM4) {
		this.credooScoreM4 = credooScoreM4;
	}

	public String getCredooScoreM5() {
		return credooScoreM5;
	}

	public void setCredooScoreM5(String credooScoreM5) {
		this.credooScoreM5 = credooScoreM5;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDriviliceNo() {
		return driviliceNo;
	}

	public void setDriviliceNo(String driviliceNo) {
		this.driviliceNo = driviliceNo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getSummaryScore() {
		return summaryScore;
	}

	public void setSummaryScore(String summaryScore) {
		this.summaryScore = summaryScore;
	}

	public String getCstScore() {
		return cstScore;
	}

	public void setCstScore(String cstScore) {
		this.cstScore = cstScore;
	}

	public String getCotScore() {
		return cotScore;
	}

	public void setCotScore(String cotScore) {
		this.cotScore = cotScore;
	}

	public String getCntScore() {
		return cntScore;
	}

	public void setCntScore(String cntScore) {
		this.cntScore = cntScore;
	}

	public String getCnaScore() {
		return cnaScore;
	}

	public void setCnaScore(String cnaScore) {
		this.cnaScore = cnaScore;
	}

	public String getChvScore() {
		return chvScore;
	}

	public void setChvScore(String chvScore) {
		this.chvScore = chvScore;
	}

	public String getDsiScore() {
		return dsiScore;
	}

	public void setDsiScore(String dsiScore) {
		this.dsiScore = dsiScore;
	}

	public String getWlpScore() {
		return wlpScore;
	}

	public void setWlpScore(String wlpScore) {
		this.wlpScore = wlpScore;
	}

	public String getCnpScore() {
		return cnpScore;
	}

	public void setCnpScore(String cnpScore) {
		this.cnpScore = cnpScore;
	}

	public String getCotCluster() {
		return cotCluster;
	}

	public void setCotCluster(String cotCluster) {
		this.cotCluster = cotCluster;
	}

	public String getDsiCluster() {
		return dsiCluster;
	}

	public void setDsiCluster(String dsiCluster) {
		this.dsiCluster = dsiCluster;
	}

	public String getRskCluster() {
		return rskCluster;
	}

	public void setRskCluster(String rskCluster) {
		this.rskCluster = rskCluster;
	}

	public String getCrbScore() {
		return crbScore;
	}

	public void setCrbScore(String crbScore) {
		this.crbScore = crbScore;
	}

	public String getCrbCluster() {
		return crbCluster;
	}

	public void setCrbCluster(String crbCluster) {
		this.crbCluster = crbCluster;
	}

	public String getCreditCardCVN() {
		return creditCardCVN;
	}

	public void setCreditCardCVN(String creditCardCVN) {
		this.creditCardCVN = creditCardCVN;
	}

	public String getExpiresYear() {
		return expiresYear;
	}

	public void setExpiresYear(String expiresYear) {
		this.expiresYear = expiresYear;
	}

	public String getExpiresMonth() {
		return expiresMonth;
	}

	public void setExpiresMonth(String expiresMonth) {
		this.expiresMonth = expiresMonth;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getEntityAuthCode() {
		return entityAuthCode;
	}

	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}

	public String getEntityAuthDate() {
		return entityAuthDate;
	}

	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}

	public String getAuthResult() {
		return authResult;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getExecMoney() {
		return execMoney;
	}

	public void setExecMoney(String execMoney) {
		this.execMoney = execMoney;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
}