package com.wanfin.fpd.modules.statistics.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;
/**
 * 统计分析   贷款明细entity
 * @author srf
 *
 */
public class LoanContractVo extends DataEntity<LoanContractVo>{
	private static final long serialVersionUID = 1L;
	private String id;
	@ExcelField(title="序号", align=2, sort=1)
	private String serial;			//序号 导出xls用
	private Date applyDate;		// 申请日期
	private String contractNumber;		// 业务编号
	private String customerId;		// 客户id
	@ExcelField(title="客户", align=2, sort=5)
	private String customerName;		// 客户姓名
	private String customerType;		// 客户类型
	private String fiveLevel;		// 五级分类
	//@ExcelField(title="是否展期", align=2, sort=2, dictType="yes_no")
	private Integer isExtend;		// 是否展期
	@ExcelField(title="贷款本金", align=2, sort=7)
	private String loanAmount;		// 贷款金额
	
	private String productNname;   //产品类型
	
	@ExcelField(title="放款日期", align=2, sort=2)
	private Date loanDate;		// 放款日期
	@ExcelField(title="贷款期限(月)", align=2, sort=11)
	private String loanPeriod;		// 贷款期限
	@ExcelField(title="贷款利率", align=2, sort=12)
	private String loanRate;		// 贷款利率 Float
	private String loanRateType;		// 利率类型
	private Date payPrincipalDate;		// 还本金日期
	private String loanType;		// 贷款方式
	private Integer maxNumber;		// 最大数值
	private String payDay;		// 付息日
	private String payType;		// 还款方式
	private String periodType;		// 还款周期
	private Date signDate;		// 签合同日期
	private String status;		// 状态
	private String productId;		// 产品主键
	@ExcelField(title="贷款用途", align=2, sort=9, dictType="product_purpose")
	private String purposeId;		// 贷款用途
	private String areaID;		// 贷款地区
	@ExcelField(title="客户地区", align=2, sort=27)
	private String areaName;		// 贷款地区
	private String industryId;		// 贷款行业
	//@ExcelField(title="借款主体", align=2, sort=6, dictType="borrower")
	private String borrower;		// 借款主体
	//@ExcelField(title="是否涉农", align=2, sort=10, dictType="yes_no")
	private String agriculture;		// 是否涉农
	private String gatheringBank;		// 开户行
	private String gatheringName;		// 开户名
	private String gatheringNumber;		// 开户账号
	private String guarantNumber;		// 担保编号
	private String payOptions;		// 还款选项
	private String payDayType;		// 每期还款日
	private String procInsId;		// proc_ins_id
	private String serverFee;		// 前期服务%
	private String mangeFee;		// 管理费%	
	
	private String ifAdvance;		// 是否可提前还款%
	private String advanceDamages;		// 提前还款违约金%
	private String lateFee;		// 滞纳金%(逾期违约金)
	/** 已还逾期违约金*/
	private BigDecimal repPunishAmount;  
	private String rateDiscont;		// 费利优惠率%
	private String ifInterestRelief;		// 允许利息减免%
	private String gracePeriod;		// 宽限期（天）
	private String gracePeriodPenalty;		// 宽限期罚息%
	private String latePenalty;		// 逾期罚息%
	/**已还罚息*/
	private BigDecimal repInterestPenalties;     
	private String latePenaltyFee;		// 逾期罚费%
	
	private String parentID;		// 展期合同的父合同
	private String isDeal;		// 是否被坏账处理
	private String scanFlag;		// 监管系统 扫描标示 0:未扫描  1:已扫描
	private String pushStatus;		// push_status
	private String isdirectLoan;		// 是否直接放款
	private String ifRealityDay;		// 是否大小月
	private String  remarks;	// 备注
	private String createId;	// 创建者
	@ExcelField(title="客户经理", align=2, sort=4)
	private String createName;	// 创建者
	@ExcelField(title="所属部门", align=2, sort=3)
	private String createOfficeName;	// 创建者所在部门
	private Date createDate;	// 创建日期
	//private User updateBy;	// 更新者
	//private Date updateDate;	// 更新日期
	private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	private String organId;		// 组织机构id
	
	
	
	
	
	//------------------------------------
	//           查询参数
	private Date beginLoanDate;		// 开始 放款日期
	private Date endLoanDate;		// 结束 放款日期
	private Date beginLastPayDate;	//开始 最后还款日
	private Date endLastPayDate;	//结束 最后还款日
	private Date currentMonth;//当期时间,格式:2017-03 查询用，必须
	//------------------------------------
	private String lastRepayDate;//最后一次还款日
	
	private BigDecimal backPrincipaTotal;	//本次总贷款本金金额,sql
	private BigDecimal backInterestTotal;	//本次总贷款利息金额,sql
	@ExcelField(title="当前已收本金", align=2, sort=23)
	private BigDecimal backPrincipalMoney;	//已还本金金额，总的,sql
	private BigDecimal backInterestMoney;	//已还利息金额，总的,sql
	
	@ExcelField(title="本月期初贷款余额", align=2, sort=13)
	/**
	 * 未还本金金额，总的，计算
	 */
	private BigDecimal needPrincipalMoney;	
	private BigDecimal needInterestMoney;	//未还利息金额，总的，计算
	@ExcelField(title="本月末贷款余额", align=2, sort=22)
	private BigDecimal preiodEndNeedRepay;//期末未还贷款总数，计算
	@ExcelField(title="当前已收业务收入", align=2, sort=24)
	private BigDecimal currentRepay;//到目前已收收入（利息+费用合计）
	
	@ExcelField(title="本月应收本金", align=2, sort=15)
	private BigDecimal currentPrincipalMoney;	//当期应还本金金额,sql
	@ExcelField(title="本月应收利息", align=2, sort=16)
	private BigDecimal currentInterestMoney;	//当期应还利息金额,sql
	@ExcelField(title="本月提前还本金", align=2, sort=14)
	private BigDecimal adviceBackPrincipalMoney;  //期间提前还本金
	private BigDecimal currentBackPrincipalMoney;//期间    已还本金金额,sql
	private BigDecimal currentBackInterestMoney;//期间    已还利息金额,sql
	private BigDecimal currentNeedPrincipalMoney;//期间    未还本金金额,计算
	private BigDecimal currentNeedInterestMoney;//期间    未还利息金额,计算
	
	
	@ExcelField(title="本月应收利息", align=2, sort=21)
	private BigDecimal currentNeedIntePriMoney;//当期应收本息,计算
	
	private BigDecimal lendAmount;//本次贷款放款金额(可能分期)
	@ExcelField(title="前期服务费(本月应收)", align=2, sort=17)
	private BigDecimal feeService;//当期服务费,根据放款金额计算
	@ExcelField(title="管理费(本月应收)", align=2, sort=18)
	private BigDecimal feeManage;//当期管理费,根据放款金额计算
	
	@ExcelField(title="是否已结清贷款", align=2, sort=25, dictType="yes_no")
	private String isSquare;//是否结清
	@ExcelField(title="结清状态", align=2, sort=26)
	private String squareType;//结清状态
	
	public LoanContractVo() {// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getFiveLevel() {
		return fiveLevel;
	}
	public void setFiveLevel(String fiveLevel) {
		this.fiveLevel = fiveLevel;
	}
	public Integer getIsExtend() {
		return isExtend;
	}
	public void setIsExtend(Integer isExtend) {
		this.isExtend = isExtend;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public BigDecimal getRepPunishAmount() {
		return repPunishAmount;
	}
	public void setRepPunishAmount(BigDecimal repPunishAmount) {
		this.repPunishAmount = repPunishAmount;
	}
	public BigDecimal getRepInterestPenalties() {
		return repInterestPenalties;
	}
	public void setRepInterestPenalties(BigDecimal repInterestPenalties) {
		this.repInterestPenalties = repInterestPenalties;
	}
	public BigDecimal getAdviceBackPrincipalMoney() {
		return adviceBackPrincipalMoney;
	}
	public void setAdviceBackPrincipalMoney(BigDecimal adviceBackPrincipalMoney) {
		this.adviceBackPrincipalMoney = adviceBackPrincipalMoney;
	}
	public String getProductNname() {
		return productNname;
	}
	public void setProductNname(String productNname) {
		this.productNname = productNname;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getLoanRateType() {
		return loanRateType;
	}
	public void setLoanRateType(String loanRateType) {
		this.loanRateType = loanRateType;
	}
	public Date getPayPrincipalDate() {
		return payPrincipalDate;
	}
	public void setPayPrincipalDate(Date payPrincipalDate) {
		this.payPrincipalDate = payPrincipalDate;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public Integer getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
	}
	public String getAreaID() {
		return areaID;
	}
	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getAgriculture() {
		return agriculture;
	}
	public void setAgriculture(String agriculture) {
		this.agriculture = agriculture;
	}
	public String getGatheringBank() {
		return gatheringBank;
	}
	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	public String getGatheringName() {
		return gatheringName;
	}
	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	public String getGatheringNumber() {
		return gatheringNumber;
	}
	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	public String getGuarantNumber() {
		return guarantNumber;
	}
	public void setGuarantNumber(String guarantNumber) {
		this.guarantNumber = guarantNumber;
	}
	public String getPayOptions() {
		return payOptions;
	}
	public void setPayOptions(String payOptions) {
		this.payOptions = payOptions;
	}
	public String getPayDayType() {
		return payDayType;
	}
	public void setPayDayType(String payDayType) {
		this.payDayType = payDayType;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getServerFee() {
		return serverFee;
	}
	public void setServerFee(String serverFee) {
		this.serverFee = serverFee;
	}
	public String getMangeFee() {
		return mangeFee;
	}
	public void setMangeFee(String mangeFee) {
		this.mangeFee = mangeFee;
	}
	public String getIfAdvance() {
		return ifAdvance;
	}
	public void setIfAdvance(String ifAdvance) {
		this.ifAdvance = ifAdvance;
	}
	public String getAdvanceDamages() {
		return advanceDamages;
	}
	public void setAdvanceDamages(String advanceDamages) {
		this.advanceDamages = advanceDamages;
	}
	public String getLateFee() {
		return lateFee;
	}
	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}
	public String getRateDiscont() {
		return rateDiscont;
	}
	public void setRateDiscont(String rateDiscont) {
		this.rateDiscont = rateDiscont;
	}
	public String getIfInterestRelief() {
		return ifInterestRelief;
	}
	public void setIfInterestRelief(String ifInterestRelief) {
		this.ifInterestRelief = ifInterestRelief;
	}
	public String getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getGracePeriodPenalty() {
		return gracePeriodPenalty;
	}
	public void setGracePeriodPenalty(String gracePeriodPenalty) {
		this.gracePeriodPenalty = gracePeriodPenalty;
	}
	public String getLatePenalty() {
		return latePenalty;
	}
	public void setLatePenalty(String latePenalty) {
		this.latePenalty = latePenalty;
	}
	public String getLatePenaltyFee() {
		return latePenaltyFee;
	}
	public void setLatePenaltyFee(String latePenaltyFee) {
		this.latePenaltyFee = latePenaltyFee;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
	public String getScanFlag() {
		return scanFlag;
	}
	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	public String getIsdirectLoan() {
		return isdirectLoan;
	}
	public void setIsdirectLoan(String isdirectLoan) {
		this.isdirectLoan = isdirectLoan;
	}
	public String getIfRealityDay() {
		return ifRealityDay;
	}
	public void setIfRealityDay(String ifRealityDay) {
		this.ifRealityDay = ifRealityDay;
	}
	public Date getBeginLoanDate() {
		return beginLoanDate;
	}
	public void setBeginLoanDate(Date beginLoanDate) {
		this.beginLoanDate = beginLoanDate;
	}
	public Date getEndLoanDate() {
		return endLoanDate;
	}
	public void setEndLoanDate(Date endLoanDate) {
		this.endLoanDate = endLoanDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public BigDecimal getBackInterestTotal() {
		if(backInterestTotal == null){
			backInterestTotal = ZERO;
		}
		return backInterestTotal;
	}
	public void setBackInterestTotal(BigDecimal backInterestTotal) {
		this.backInterestTotal = backInterestTotal;
	}
	public BigDecimal getBackPrincipalMoney() {
		if(backPrincipalMoney == null){
			backPrincipalMoney = ZERO;
		}
		return backPrincipalMoney;
	}
	public void setBackPrincipalMoney(BigDecimal backPrincipalMoney) {
		this.backPrincipalMoney = backPrincipalMoney;
	}
	public BigDecimal getBackInterestMoney() {
		if(backInterestMoney == null){
			backInterestMoney = ZERO;
		}
		return backInterestMoney;
	}
	public void setBackInterestMoney(BigDecimal backInterestMoney) {
		this.backInterestMoney = backInterestMoney;
	}
	public BigDecimal getNeedPrincipalMoney() {
		if(needPrincipalMoney == null){
			needPrincipalMoney = ZERO;
		}
		return needPrincipalMoney;
	}
	public void setNeedPrincipalMoney(BigDecimal needPrincipalMoney) {
		this.needPrincipalMoney = needPrincipalMoney;
	}
	public BigDecimal getNeedInterestMoney() {
		if(needInterestMoney == null){
			needInterestMoney = ZERO;
		}
		return needInterestMoney;
	}
	public void setNeedInterestMoney(BigDecimal needInterestMoney) {
		this.needInterestMoney = needInterestMoney;
	}
	public BigDecimal getCurrentPrincipalMoney() {
		if(currentPrincipalMoney == null){
			currentPrincipalMoney = ZERO;
		}
		return currentPrincipalMoney;
	}
	public void setCurrentPrincipalMoney(BigDecimal currentPrincipalMoney) {
		this.currentPrincipalMoney = currentPrincipalMoney;
	}
	public BigDecimal getCurrentInterestMoney() {
		if(currentInterestMoney == null){
			currentInterestMoney = ZERO;
		}
		return currentInterestMoney;
	}
	public void setCurrentInterestMoney(BigDecimal currentInterestMoney) {
		this.currentInterestMoney = currentInterestMoney;
	}
	public String getCreateOfficeName() {
		return createOfficeName;
	}
	public void setCreateOfficeName(String createOfficeName) {
		this.createOfficeName = createOfficeName;
	}
	public Date getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}
	public BigDecimal getBackPrincipaTotal() {
		if(backPrincipaTotal == null){
			backPrincipaTotal = ZERO;
		}
		return backPrincipaTotal;
	}
	public void setBackPrincipaTotal(BigDecimal backPrincipaTotal) {
		this.backPrincipaTotal = backPrincipaTotal;
	}
	public BigDecimal getCurrentBackPrincipalMoney() {
		if(currentBackPrincipalMoney == null){
			currentBackPrincipalMoney = ZERO;
		}
		return currentBackPrincipalMoney;
	}
	public void setCurrentBackPrincipalMoney(BigDecimal currentBackPrincipalMoney) {
		this.currentBackPrincipalMoney = currentBackPrincipalMoney;
	}
	public BigDecimal getCurrentBackInterestMoney() {
		if(currentBackInterestMoney == null){
			currentBackInterestMoney = ZERO;
		}
		return currentBackInterestMoney;
	}
	public void setCurrentBackInterestMoney(BigDecimal currentBackInterestMoney) {
		this.currentBackInterestMoney = currentBackInterestMoney;
	}
	public BigDecimal getCurrentNeedPrincipalMoney() {
		if(currentNeedPrincipalMoney == null){
			currentNeedPrincipalMoney = ZERO;
		}
		return currentNeedPrincipalMoney;
	}
	public void setCurrentNeedPrincipalMoney(BigDecimal currentNeedPrincipalMoney) {
		this.currentNeedPrincipalMoney = currentNeedPrincipalMoney;
	}
	public BigDecimal getCurrentNeedInterestMoney() {
		if(currentNeedInterestMoney == null){
			currentNeedInterestMoney = ZERO;
		}
		return currentNeedInterestMoney;
	}
	public void setCurrentNeedInterestMoney(BigDecimal currentNeedInterestMoney) {
		this.currentNeedInterestMoney = currentNeedInterestMoney;
	}
	public Date getBeginLastPayDate() {
		return beginLastPayDate;
	}
	public void setBeginLastPayDate(Date beginLastPayDate) {
		this.beginLastPayDate = beginLastPayDate;
	}
	public Date getEndLastPayDate() {
		return endLastPayDate;
	}
	public void setEndLastPayDate(Date endLastPayDate) {
		this.endLastPayDate = endLastPayDate;
	}
	public BigDecimal getLendAmount() {
		if(lendAmount == null){
			lendAmount = ZERO;
		}
		return lendAmount;
	}
	public void setLendAmount(BigDecimal lendAmount) {
		this.lendAmount = lendAmount;
	}
	public BigDecimal getFeeService() {
		if(feeService == null){
			feeService = ZERO;
		}
		return feeService;
	}
	public void setFeeService(BigDecimal feeService) {
		this.feeService = feeService;
	}
	public BigDecimal getFeeManage() {
		if(feeManage == null){
			feeManage = ZERO;
		}
		return feeManage;
	}
	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}
	public BigDecimal getPreiodEndNeedRepay() {
		if(preiodEndNeedRepay == null){
			preiodEndNeedRepay = ZERO;
		}
		return preiodEndNeedRepay;
	}
	public void setPreiodEndNeedRepay(BigDecimal preiodEndNeedRepay) {
		this.preiodEndNeedRepay = preiodEndNeedRepay;
	}
	public BigDecimal getCurrentRepay() {
		if(currentRepay == null){
			currentRepay = ZERO;
		}
		return currentRepay;
	}
	public void setCurrentRepay(BigDecimal currentRepay) {
		this.currentRepay = currentRepay;
	}
	public BigDecimal getCurrentNeedIntePriMoney() {
		if(currentNeedIntePriMoney == null){
			currentNeedIntePriMoney = ZERO;
		}
		return currentNeedIntePriMoney;
	}
	public void setCurrentNeedIntePriMoney(BigDecimal currentNeedIntePriMoney) {
		this.currentNeedIntePriMoney = currentNeedIntePriMoney;
	}
	public String getIsSquare() {
		return isSquare;
	}
	public void setIsSquare(String isSquare) {
		this.isSquare = isSquare;
	}
	public String getSquareType() {
		return squareType;
	}
	public void setSquareType(String squareType) {
		this.squareType = squareType;
	}
	public String getLastRepayDate() {
		return lastRepayDate;
	}
	public void setLastRepayDate(String lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}
}
