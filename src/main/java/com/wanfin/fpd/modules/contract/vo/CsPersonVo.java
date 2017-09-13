package com.wanfin.fpd.modules.contract.vo;

import java.math.BigDecimal;
import java.util.List;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.employee.entity.CsCar;
import com.wanfin.fpd.modules.employee.entity.CsContact;
import com.wanfin.fpd.modules.employee.entity.CsHouse;
import com.wanfin.fpd.modules.employee.entity.TEmployee;



/**
 * @Description [[_获取个人用户评分传参对象_]] GradeParam类
 * @author zzm
 * @date 2016-7-8 上午9:53:58 
 */
public class CsPersonVo implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	private Integer id;
	private String organCode;	//组织机构代码
	private String token;
	private String seqNo;	//订单号
	private BigDecimal amount;	//业务金额
	private double score;	//评分
	private int audit;	//评审结果
	//基本资料
	private String name;	//姓名
	private String cardType;	//证件类型
	private String cardNo;	//证件号码
	private String sex;	//性别
	private String birthday;	//出生日期
	private String education;	//最高学历
	private String phone;		//手机号
	private String telephone;	//家庭电话
	private String email;		//邮箱
	private String registerAdr;	//户籍地址
	private String registerStatus;	//户籍情况
	private String address;		//现居地址
	private String resideTime;	//现居地入住时间
	private String resideStatus;	//居住情况
	private String maritalStatus;	//婚姻状况
	private Integer houseHold;	//供养人数
	private String driveNo;		//驾驶证号
	private String unitName;	//单位名称
	private String bizCategory;	//行业类别
	private String unitNature;	//单位性质
	private String unitScale;	//公司规模
	private String unitAdr;		//单位地址
	private String position;	//职位
	private String unitPhone;	//单位电话
	private String entryTime;	//入职时间
	private String techTitle;	//职称
	private BigDecimal monthlyIncome;	//税后月薪收入
	private Integer creditCardCount;	//信用卡数目
	private BigDecimal maxCreditCard;	//信用卡最大额度
	private BigDecimal perMonthlyPay;	//家庭人均月固定支出
	private BigDecimal depositAndInvest;	//存款及投资
	private String sponsorType;		//担保方类型(法人、自然人)
	private String sponsorName;		//担保方名称
	private String sponsorNo;		//担保方证件号（身份证号码、工商注册号）
	private CsPersonVo mate;		//配偶
	private List<CsCar> cars;	//车产
	private List<CsHouse> houses;	//房产
	private List<CsContact> contacts;	//联系人
	
	public CsPersonVo(){
	}
	
	public CsPersonVo(TEmployee employee){
		if(employee != null){
			this.name = employee.getName();
			this.cardType = "0";
			this.cardNo = employee.getCardNum();
			this.sex = employee.getSex();
			this.birthday = employee.getBirthday();
			this.education = employee.getEducation();
			this.phone = employee.getMobile();
			this.telephone = employee.getTelephone();
			this.email = employee.getEmail();
			this.registerAdr = employee.getHouseholdRegAddr();
			this.registerStatus = employee.getRegisterStatus();
			this.address = employee.getCurrentLiveAddress();
			this.resideTime = employee.getResideTime();
			this.resideStatus = employee.getResideStatus();
			this.maritalStatus = employee.getMarriedInfo();
			this.houseHold = employee.getHouseHold();
			this.driveNo = employee.getDriveNo();
			this.unitName = employee.getWorkUnit();
			this.bizCategory = employee.getBizCategory();
			this.unitNature = employee.getNatureOfUnit();
			this.unitAdr = employee.getUnitAddress();
			this.position = employee.getPost();
			this.unitPhone = employee.getWorkTelephone();
			this.unitScale = employee.getUnitScale();
			this.entryTime = employee.getEntryTime() != null ? DateUtils.formatDate(employee.getEntryTime()) : null;
			this.monthlyIncome = employee.getMonthIncome();
			this.techTitle = employee.getTechTitle();
			this.monthlyIncome = employee.getMonthIncome();
			this.creditCardCount = employee.getCreditCardCount();
			this.maxCreditCard = employee.getMaxCreditCard();
			this.perMonthlyPay = employee.getPerMonthlyPay();
			this.depositAndInvest = employee.getDepositAndInvest();
			
			if(employee.getMate() != null){
				mate = new CsPersonVo();
				mate.setName(employee.getMate().getPouseName());
				mate.setCardType(employee.getMate().getCardType());
				mate.setCardNo(employee.getMate().getPouseCardNum());
				mate.setBirthday(employee.getMate().getBirthday() != null ? DateUtils.formatDate(employee.getMate().getBirthday()) : null);
				mate.setUnitName(employee.getMate().getUnits());
				mate.setBizCategory(employee.getMate().getUnits());
				mate.setUnitNature(employee.getMate().getUnitNature());
				mate.setUnitScale(employee.getMate().getUnitSize());
				mate.setUnitAdr(employee.getMate().getUnitsAddress());
				mate.setPosition(employee.getMate().getPosition());
				mate.setUnitPhone(employee.getMate().getUnitsPhone());
				if(employee.getMate().getPouseMonthIncome() != null ) mate.setMonthlyIncome(new BigDecimal(employee.getMate().getPouseMonthIncome()));
				mate.setPosition(employee.getMate().getPosition());
				mate.setTechTitle(employee.getMate().getPolitical());
			}
			
			this.cars = employee.getCars();
			this.houses = employee.getHouses();
			this.contacts = employee.getContacts();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterAdr() {
		return registerAdr;
	}

	public void setRegisterAdr(String registerAdr) {
		this.registerAdr = registerAdr;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBizCategory() {
		return bizCategory;
	}

	public void setBizCategory(String bizCategory) {
		this.bizCategory = bizCategory;
	}

	public String getUnitNature() {
		return unitNature;
	}

	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}

	public String getUnitScale() {
		return unitScale;
	}

	public void setUnitScale(String unitScale) {
		this.unitScale = unitScale;
	}

	public String getUnitAdr() {
		return unitAdr;
	}

	public void setUnitAdr(String unitAdr) {
		this.unitAdr = unitAdr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUnitPhone() {
		return unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}

	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
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

	public String getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(String sponsorType) {
		this.sponsorType = sponsorType;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorNo() {
		return sponsorNo;
	}

	public void setSponsorNo(String sponsorNo) {
		this.sponsorNo = sponsorNo;
	}

	public CsPersonVo getMate() {
		return mate;
	}

	public void setMate(CsPersonVo mate) {
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

}