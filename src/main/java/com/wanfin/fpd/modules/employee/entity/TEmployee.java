/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 客户个人Entity
 * @author lx
 * @version 2016-03-12
 */
@ApiModel(value="个人客户")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TEmployee extends ActEntity<TEmployee> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="姓名", dataType="String", notes="姓名", hidden=false, required=true)
	private String name;		// 姓名
	@ApiModelProperty(value="性别", dataType="String", notes="性别", hidden=false, required=true)
	private String sex;		// 性别
	@ApiModelProperty(value="年龄", dataType="String", notes="年龄", hidden=false, required=true)
	private String age;		// 年龄
	@ApiModelProperty(value="出生日", dataType="String", notes="出生日", hidden=false, required=true)
	private String birthday;		// 出生日
	@ApiModelProperty(value="身份证号码", dataType="String", notes="身份证号码", hidden=false, required=true)
	private String cardNum;		// 身份证号码
	@ApiModelProperty(value="邮件", dataType="String", notes="邮件", hidden=false, required=true)
	private String email;		// 邮件
	@ApiModelProperty(value="开户行", dataType="String", notes="开户行", hidden=false, required=true)
	private String gatheringBank;		// 开户行
	@ApiModelProperty(value="开户名", dataType="String", notes="开户名", hidden=false, required=true)
	private String gatheringName;		// 开户名
	@ApiModelProperty(value="户籍地", dataType="String", notes="户籍地", hidden=false, required=true)
	private String householdRegAddr;		// 户籍地
	@ApiModelProperty(value="现住址", dataType="String", notes="现住址", hidden=false, required=true)
	private String currentLiveAddress;		// 现住址
	@ApiModelProperty(value="开户账户", dataType="String", notes="开户账户", hidden=false, required=true)
	private String gatheringNumber;		// 开户账户
	@ApiModelProperty(value="手机号码", dataType="String", notes="手机号码", hidden=false, required=true)
	private String mobile;		// 手机号码
	@ApiModelProperty(value="联系电话", dataType="String", notes="联系电话", hidden=false, required=true)
	private String telephone;		// 联系电话
	@ApiModelProperty(value="学历(初中或以下、高中/专科、本科、硕士研究生或以上)", dataType="String", notes="学历(初中或以下、高中/专科、本科、硕士研究生或以上)", hidden=false, required=true)
	private String education;		// 学历(初中或以下、高中/专科、本科、硕士研究生或以上)
	@ApiModelProperty(value="是否本地户口", dataType="String", notes="是否本地户口", hidden=false, required=true)
	private String isLocalHousehold;		// 是否本地户口
	@ApiModelProperty(value="居住年限", dataType="String", notes="居住年限", hidden=false, required=true)
	private String liveAgeLimit;		// 居住年限
	@ApiModelProperty(value="婚姻状况(未婚、已、离婚 、丧偶)", dataType="String", notes="婚姻状况(未婚、已、离婚 、丧偶)", hidden=false, required=true)
	private String marriedInfo;		// 婚姻状况(未婚、已、离婚 、丧偶)
	@ApiModelProperty(value="单位名称", dataType="String", notes="单位名称", hidden=false, required=true)
	private String workUnit;		// 单位名称
	@ApiModelProperty(value="单位性质", dataType="String", notes="单位性质", hidden=false, required=true)
	private String natureOfUnit;		// 单位性质
	@ApiModelProperty(value="个人职务", dataType="String", notes="个人职务", hidden=false, required=true)
	private String post;		// 个人职务
	@ApiModelProperty(value="考评结果", dataType="String", notes="考评结果", hidden=false, required=true)
	private String evaluate;		//上一年考评结果:5:优秀、4:良好、3:称职、2:基本称职、1:不称职
	@ApiModelProperty(value="职位", dataType="String", notes="职位", hidden=false, required=true)
	private String position;		//职位:3：高级、2：中级、1：一般员工
	@ApiModelProperty(value="单位地址", dataType="String", notes="单位地址", hidden=false, required=true)
	private String unitAddress;		// 单位地址
	@ApiModelProperty(value="单位电话", dataType="String", notes="单位电话", hidden=false, required=true)
	private String workTelephone;		// 单位电话
	@ApiModelProperty(value="月收入", dataType="BigDecimal", notes="月收入", hidden=false, required=true)
	private BigDecimal monthIncome;		// 月收入
	@ApiModelProperty(value="黑名状态", dataType="String", notes="黑名状态", hidden=false, required=true)
	private String status;		// 黑名状态  black:黑名，black_audit:黑名审核中
	@ApiModelProperty(value="加入黑名单理由", dataType="String", notes="加入黑名单理由", hidden=false, required=true)
	private String reason;		// 加入黑名单理由
	@ApiModelProperty(value="客户来源（上门、存量、新增、员工、其它）", dataType="String", notes="客户来源（上门、存量、新增、员工、其它）", hidden=false, required=true)
	private String customerSource;		// 客户来源（上门、存量、新增、员工、其它）
	@ApiModelProperty(value="工作年限", dataType="String", notes="工作年限", hidden=false, required=true)
	private String yearsOfWorking;		// 工作年限
	@ApiModelProperty(value="产品", dataType="TProduct", notes="产品", hidden=false, required=true)
	private TProduct product;

	@ApiModelProperty(value="W端客户主键", dataType="TProduct", notes="W端客户主键", hidden=false, required=true)
	private String wtypeId;//w端个人客户ID
	
	private String scanFlag; //监管系统扫描标示 0：未扫描 1：已扫描
	private String companyName; //不持久化数据
	private String pushStatus; //标示推送监管的状态  0：位推送过  1：已推送过
	
	//add by zzm @20160721
	private String registerStatus;	//户籍情况
	private String resideTime;	//现居地入住时间
	private String resideStatus;	//居住情况
	private Integer houseHold;			//供养人数
	private String driveNo;		//驾驶证号
	private String bizCategory;	//行业类别
	private String unitScale;		//公司规模
	private Date entryTime;			//入职时间
	private String techTitle;	//职称
	private Integer creditCardCount;	//信用卡数目
	private BigDecimal maxCreditCard;	//信用卡最大额度
	private BigDecimal perMonthlyPay;	//家庭人均月固定支出
	private BigDecimal depositAndInvest;	//存款及投资
	
	private TSpouse mate;		//配偶
	private List<CsCar> cars;	//车产
	private List<CsHouse> houses;	//房产
	private List<CsContact> contacts;	//联系人
	
	public TEmployee() {
		super();
	}

	public TEmployee(String id){
		super(id);
	}

	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="性别长度必须介于 0 和 11 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=50, message="身份证号码长度必须介于 0 和 50 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Length(min=0, max=50, message="邮件长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=500, message="开户行长度必须介于 0 和 500 之间")
	public String getGatheringBank() {
		return gatheringBank;
	}

	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	
	@Length(min=0, max=20, message="开户名长度必须介于 0 和 20 之间")
	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	
	@Length(min=0, max=500, message="户籍地长度必须介于 0 和 500 之间")
	public String getHouseholdRegAddr() {
		return householdRegAddr;
	}

	public void setHouseholdRegAddr(String householdRegAddr) {
		this.householdRegAddr = householdRegAddr;
	}
	
	@Length(min=0, max=500, message="现住址长度必须介于 0 和 500 之间")
	public String getCurrentLiveAddress() {
		return currentLiveAddress;
	}

	public void setCurrentLiveAddress(String currentLiveAddress) {
		this.currentLiveAddress = currentLiveAddress;
	}
	
	@Length(min=0, max=30, message="开户账户长度必须介于 0 和 30 之间")
	public String getGatheringNumber() {
		return gatheringNumber;
	}

	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	
	@Length(min=0, max=20, message="手机号码长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=20, message="学历(初中或以下、高中/专科、本科、硕士研究生或以上)长度必须介于 0 和 20 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=2, message="是否本地户口长度必须介于 0 和 2 之间")
	public String getIsLocalHousehold() {
		return isLocalHousehold;
	}

	public void setIsLocalHousehold(String isLocalHousehold) {
		this.isLocalHousehold = isLocalHousehold;
	}
	
	@Length(min=0, max=20, message="居住年限长度必须介于 0 和 20 之间")
	public String getLiveAgeLimit() {
		return liveAgeLimit;
	}

	public void setLiveAgeLimit(String liveAgeLimit) {
		this.liveAgeLimit = liveAgeLimit;
	}
	
	@Length(min=0, max=30, message="婚姻状况(未婚、已、离婚 、丧偶)长度必须介于 0 和 30 之间")
	public String getMarriedInfo() {
		return marriedInfo;
	}

	public void setMarriedInfo(String marriedInfo) {
		this.marriedInfo = marriedInfo;
	}
	
	@Length(min=0, max=100, message="单位名称长度必须介于 0 和 100 之间")
	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	
	@Length(min=0, max=60, message="单位性质长度必须介于 0 和 60 之间")
	public String getNatureOfUnit() {
		return natureOfUnit;
	}

	public void setNatureOfUnit(String natureOfUnit) {
		this.natureOfUnit = natureOfUnit;
	}
	
	@Length(min=0, max=60, message="个人职务长度必须介于 0 和 60 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=50, message="单位地址长度必须介于 0 和 50 之间")
	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}
	
	@Length(min=0, max=20, message="单位电话长度必须介于 0 和 20 之间")
	public String getWorkTelephone() {
		return workTelephone;
	}

	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
	}
	
	public BigDecimal getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(BigDecimal monthIncome) {
		this.monthIncome = monthIncome;
	}
	
	@Length(min=0, max=20, message="黑名状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="加入黑名单理由长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=11, message="客户来源（上门、存量、新增、员工、其它）长度必须介于 0 和 11 之间")
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	
	@Length(min=0, max=20, message="工作年限长度必须介于 0 和 20 之间")
	public String getYearsOfWorking() {
		return yearsOfWorking;
	}

	public void setYearsOfWorking(String yearsOfWorking) {
		this.yearsOfWorking = yearsOfWorking;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getResideTime() {
		return resideTime;
	}

	public void setResideTime(String resideTime) {
		this.resideTime = resideTime;
	}

	public String getResideStatus() {
		return resideStatus;
	}

	public void setResideStatus(String resideStatus) {
		this.resideStatus = resideStatus;
	}

	public Integer getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(Integer houseHold) {
		this.houseHold = houseHold;
	}

	public String getDriveNo() {
		return driveNo;
	}

	public void setDriveNo(String driveNo) {
		this.driveNo = driveNo;
	}

	public String getBizCategory() {
		return bizCategory;
	}

	public void setBizCategory(String bizCategory) {
		this.bizCategory = bizCategory;
	}

	public String getUnitScale() {
		return unitScale;
	}

	public void setUnitScale(String unitScale) {
		this.unitScale = unitScale;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}

	public Integer getCreditCardCount() {
		return creditCardCount;
	}

	public void setCreditCardCount(Integer creditCardCount) {
		this.creditCardCount = creditCardCount;
	}

	public BigDecimal getMaxCreditCard() {
		return maxCreditCard;
	}

	public void setMaxCreditCard(BigDecimal maxCreditCard) {
		this.maxCreditCard = maxCreditCard;
	}

	public BigDecimal getPerMonthlyPay() {
		return perMonthlyPay;
	}

	public void setPerMonthlyPay(BigDecimal perMonthlyPay) {
		this.perMonthlyPay = perMonthlyPay;
	}

	public BigDecimal getDepositAndInvest() {
		return depositAndInvest;
	}

	public void setDepositAndInvest(BigDecimal depositAndInvest) {
		this.depositAndInvest = depositAndInvest;
	}

	public TSpouse getMate() {
		return mate;
	}

	public void setMate(TSpouse mate) {
		this.mate = mate;
	}

	public List<CsCar> getCars() {
		return cars;
	}

	public void setCars(List<CsCar> cars) {
		this.cars = cars;
	}

	public List<CsHouse> getHouses() {
		return houses;
	}

	public void setHouses(List<CsHouse> houses) {
		this.houses = houses;
	}

	public List<CsContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CsContact> contacts) {
		this.contacts = contacts;
	}
	
	 public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String senData(){
	    	if("1".equals(this.getDelFlag())){ //删除数据
	    		return "D" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
	    				+ this.getSex() + "|" +"1"+"|"+this.getCardNum() + "|" +this.getTelephone()+"|" +"2" +"|" +this.getEducation() + "|" + "" +"|" + this.getEmail() +"|" + this.getMarriedInfo() + "|" 
	    				+ "" + "|" + this.getBirthday() + "|" + "" + "|" + this.getCurrentLiveAddress() + "|" + "" +"|" + this.getHouseholdRegAddr() +"|" +this.getHouseholdRegAddr() + "|" + this.getWorkUnit() +"|" 
	    				+ this.getPost() + "|" + "2,"+this.getId();    		
	    	}else{ //新增修改数据
	    		if("0".equals(this.getPushStatus())){
	    			return "A" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
		    				+ this.getSex() + "|" +"1"+"|"+this.getCardNum() + "|" +this.getTelephone()+"|" +"2" +"|" +this.getEducation() + "|" + "" +"|" + this.getEmail() +"|" + this.getMarriedInfo() + "|" 
		    				+ "" + "|" + this.getBirthday() + "|" + "" + "|" + this.getCurrentLiveAddress() + "|" + "" +"|" + this.getHouseholdRegAddr() +"|" +this.getHouseholdRegAddr() + "|" + this.getWorkUnit() +"|" 
		    				+ this.getPost() + "|" +"2,"+ this.getId();    
	    		}else{
	    			return "U" + "|"+this.getCompanyName()+"|"+this.getName() + "|"
		    				+ this.getSex() + "|" +"1"+"|"+this.getCardNum() + "|" +this.getTelephone()+"|" +"2" +"|" +this.getEducation() + "|" + "" +"|" + this.getEmail() +"|" + this.getMarriedInfo() + "|" 
		    				+ "" + "|" + this.getBirthday() + "|" + "" + "|" + this.getCurrentLiveAddress() + "|" + "" +"|" + this.getHouseholdRegAddr() +"|" +this.getHouseholdRegAddr() + "|" + this.getWorkUnit() +"|" 
		    				+ this.getPost() + "|" + "2,"+this.getId(); 
	    		}
	    	}    	
	    }
	
}