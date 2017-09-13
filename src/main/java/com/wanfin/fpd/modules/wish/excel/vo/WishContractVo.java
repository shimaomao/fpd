package com.wanfin.fpd.modules.wish.excel.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wanfin.fpd.modules.sys.entity.Office;
/**
 * 统计分析   贷款明细entity
 * @author lzj
 *
 */
public class WishContractVo extends DataEntity<WishContractVo>{
	private static final long serialVersionUID = 1L;
	private String id;
	private String serial;			//序号 导出xls用

	@ExcelField(title="业务编号", align=2, sort=1)
	private String contractNumber;		// 业务编号
	
	@ExcelField(title="收款人", align=2, sort=5)
	private String customerName;		// 客户姓名
	
	@ExcelField(title="申请日期", align=2, sort=7)
	private Date applyDate;		// 申请日期
	
	@ExcelField(title="金额", align=2, sort=9)
	private String loanAmount;		// 贷款金额
	
	@ExcelField(title="手续费", align=2, sort=11)
	private String wishCharge;		// 贷款金额
	
	

	private String customerId;		// 客户id

	private String customerType;		// 客户类型
	private String fiveLevel;		// 五级分类
	private String isExtend;		// 是否展期

	private Date loanDate;		// 放款日期
	private String loanPeriod;		// 贷款期限
	private String loanRate;		// 贷款利率
	private String loanRateType;		// 利率类型
	private Date payPrincipalDate;		// 还本金日期
	private String loanType;		// 贷款方式
	private List<String> loanTypeItem;		// 贷款方式
	//private Integer maxNumber;		// 最大数值
	private String maxNumber;		// 最大数值
	private String payType;		// 还款方式
	private String periodType;		// 还款周期
	private Date signDate;		// 签合同日期
	private String status;		// 状态   1(客户经理新增，还没提交申请) 2:待审批  3:待签订   4:放款审批， 5:待放款   6：未结清   7：已结清    8：已逾期   9:已终止  
	private String productId;		// 产品主键
	private String productname;		// 产品名称
	private String purposeId;		// 贷款用途
	/*private Area area;		// 贷款地区
*/	/*private String areaIdT;*/
	private String industryId;		// 贷款行业
	private String borrower;	//借款主体 add by shirf 20170218
	private String gatheringBank;		// 开户行
	private String gatheringName;		// 开户名
	private String gatheringNumber;		// 开户账号
	private String guarantNumber;		// 担保编号
	private String payOptions;		// 还款选项
	private String payDayType;		// 每期还款日(固定某号或者放款日对日)
	private String payDay;		// 付息日
	private String creditApplyId;	//授信申请表ID add by shirf 2017-03-25
	private String creditApplyComments;	//授信评审意见
	private TLoanContract parent;		// 关联合同主键id
	
	private String isDeal;             //是否被坏账处理标志（0：否，1：是，）
	private String isdirectLoan;       //是否直接放款（是的话，不走放款申请，业务申请之后直接放款）
    private String ifRealityDay;       //是否大小月
	
	//用于接收表单数据，并不关联数据库
	private List<TRepayPlan> repayPlanList;
	private String serverFee;	      //前期服务%
	private String mangeFee;	 	  //管理费%
	private String ifAdvance;		  //是否可提前还款
	private String advanceDamages;	  //提前还款违约金%
	private String lateFee;			  //滞纳金%
	private String rateDiscont;		  //费利优惠率%
	private String ifInterestRelief;  //允许利息减免%
	private String gracePeriod;		  //宽限期（天）
	private String gracePeriodPenalty;//宽限期罚息%
	private String latePenalty;		  //逾期罚息%
	private String latePenaltyFee;	   //逾期罚费%
	private Advance advance;		//提前还款信息 
	private String authUserId;//卖家认证ID
	private String buyAuthId;//买家认证ID
	
	private String moveStatus;//订单转移状态
	private Office moveOffice;//订单转移对象机构
	
	//业务统计分析搜索框，不存里数据库
	private Date starttime;
	private Date endtime;
	
	private String type;//业务类型：0,B端；1，W端  2,秒杀货款
	private String wtypeId;//业务主键：W端订单号
	
	//用于放款时标示谁做的业务，不存入数据库
	private String creatByerName;
	private String deptname;
	
	//转理财   add by zzm
	private String transferId;	//转理财后对应W端的借款id
	private String isTransfered;	//是否已转理财
	
	private String capitalsAmount;//大写的贷款金额，不存入数据库，查看担保函时业务使用
	
	private String scanFlag;  //监管系统 扫描标示 0:未扫描  1:已扫描
	private String companyName;  //不持久化
	
	private String pushStatus; 

	private Integer extendDays;//展期天数  
	
	private String accountStatus;//支付通道状态 0:未锁定；1:已锁定；2:已解锁；9:其他
	private String cashBackStatus;//回款状态  0:未回款；1:已回款；2:已收款；4:回款异常；5:收款异常；9:其他

	//------------------------------------
	//           查询参数
	private Date beginLoanDate;		// 开始 放款日期
	private Date endLoanDate;		// 结束 放款日期
	private Date beginLastPayDate;	//开始 最后还款日
	private Date endLastPayDate;	//结束 最后还款日
	private Date currentMonth;//当期时间,格式:2017-03 查询用，必须
	//------------------------------------
	private String lastRepayDate;//最后一次还款日
	

	
	public WishContractVo() {// TODO Auto-generated constructor stub
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
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getWishCharge() {
		return wishCharge;
	}
	public void setWishCharge(String wishCharge) {
		this.wishCharge = wishCharge;
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
	public Date getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}
	public String getLastRepayDate() {
		return lastRepayDate;
	}
	public void setLastRepayDate(String lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getIsExtend() {
		return isExtend;
	}
	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
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
	public List<String> getLoanTypeItem() {
		return loanTypeItem;
	}
	public void setLoanTypeItem(List<String> loanTypeItem) {
		this.loanTypeItem = loanTypeItem;
	}
	public String getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
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
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
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
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getCreditApplyId() {
		return creditApplyId;
	}
	public void setCreditApplyId(String creditApplyId) {
		this.creditApplyId = creditApplyId;
	}
	public String getCreditApplyComments() {
		return creditApplyComments;
	}
	public void setCreditApplyComments(String creditApplyComments) {
		this.creditApplyComments = creditApplyComments;
	}
	public TLoanContract getParent() {
		return parent;
	}
	public void setParent(TLoanContract parent) {
		this.parent = parent;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
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
	public List<TRepayPlan> getRepayPlanList() {
		return repayPlanList;
	}
	public void setRepayPlanList(List<TRepayPlan> repayPlanList) {
		this.repayPlanList = repayPlanList;
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
	public Advance getAdvance() {
		return advance;
	}
	public void setAdvance(Advance advance) {
		this.advance = advance;
	}
	public String getAuthUserId() {
		return authUserId;
	}
	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}
	public String getBuyAuthId() {
		return buyAuthId;
	}
	public void setBuyAuthId(String buyAuthId) {
		this.buyAuthId = buyAuthId;
	}
	public String getMoveStatus() {
		return moveStatus;
	}
	public void setMoveStatus(String moveStatus) {
		this.moveStatus = moveStatus;
	}
	public Office getMoveOffice() {
		return moveOffice;
	}
	public void setMoveOffice(Office moveOffice) {
		this.moveOffice = moveOffice;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWtypeId() {
		return wtypeId;
	}
	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}
	public String getCreatByerName() {
		return creatByerName;
	}
	public void setCreatByerName(String creatByerName) {
		this.creatByerName = creatByerName;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getIsTransfered() {
		return isTransfered;
	}
	public void setIsTransfered(String isTransfered) {
		this.isTransfered = isTransfered;
	}
	public String getCapitalsAmount() {
		return capitalsAmount;
	}
	public void setCapitalsAmount(String capitalsAmount) {
		this.capitalsAmount = capitalsAmount;
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
	public Integer getExtendDays() {
		return extendDays;
	}
	public void setExtendDays(Integer extendDays) {
		this.extendDays = extendDays;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getCashBackStatus() {
		return cashBackStatus;
	}
	public void setCashBackStatus(String cashBackStatus) {
		this.cashBackStatus = cashBackStatus;
	}
	
	

	
}
