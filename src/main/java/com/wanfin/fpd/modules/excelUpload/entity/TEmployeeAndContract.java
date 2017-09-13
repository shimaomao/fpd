package com.wanfin.fpd.modules.excelUpload.entity;

import java.util.Date;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.lending.entity.TLending;

/**
 * excel上传数据实体类
 * @author lzj
 * @version 2017-03-24
 * */

@SuppressWarnings("serial")
public class TEmployeeAndContract extends DataEntity<TLoanContract>{
	/**
	 * 
	 */
	@ExcelField(title = "数据编号")
	private String id;                  //业务编号
	private String customerId;			//客户ID
	@ExcelField(title = "姓名")
	private String customerName;		// 客户姓名
	@ExcelField(title = "性别（男/女）")
	private String sex;	                //性别
	@ExcelField(title = "身份证号码")
	private String cardNum;		        // 身份证号码
	@ExcelField(title = "手机号码")
	private String mobile;		        // 手机号码
	@ExcelField(title = "婚姻状况（1.未婚/2.已婚/3.离婚/4.丧偶）")
	private String marriedInfo;		    //married_info 婚姻状况(未婚、已、离婚 、丧偶)
	@ExcelField(title = "现住址")
	private String currentLiveAddress;  // 现住址
	@ExcelField(title = "户籍地")
	private String householdRegAddr;    // 户籍地
	@ExcelField(title = "开户名")
	private String gatheringName;		// 开户名
	@ExcelField(title = "开户行")
	private String gatheringBank;		// 开户行
	@ExcelField(title = "开户账号")
	private String gatheringNumber;		// 开户账号
	@ExcelField(title = "客户经理")
	private String customerManager;     //客户经理
	@ExcelField(title = "客户状态")
	private String customerStatus;     //客户状态    (  black:黑名，black_audit:黑名审核中 正常:normal)
	@ExcelField(title = "业务编号")
	private String contractNumber;		// 合同编号
	@ExcelField(title = "客户类型（1.企业/2.个人）")
	private String customerType;		// 客户类型
	@ExcelField(title = "借款主体(1.个人贷款/2.个体工商户贷款/3.农村专业合作组织贷款/4.微型企业贷款/5.小型企业贷款/6.中型及以上企业贷款/7.其它组织贷款)")
	private String borrower;	        //借款主体 add by shirf 20170218
	@ExcelField(title = "行业")
	private String industryId;		    // 贷款行业
	/*@ExcelField(title = "是否涉农")
	private String agriculture;	        //是否涉农 add by shirf 20170218
*/	@ExcelField(title = "申请日期")
	private Date applyDate;		        // 申请日期
	@ExcelField(title = "贷款方式（1.抵押/2.质押/3.信用/4.保证）")
	private String loanType;		    // 贷款方式
	@ExcelField(title = "贷款金额（元）")
	private String loanAmount;		    // 贷款金额
	@ExcelField(title = "放款日期")
	private Date loanDate;		        // 放款日期
	@ExcelField(title = "贷款利率（%）")
	private String loanRate;		    // 贷款利率
	@ExcelField(title = "利率类型（年/月/日/季）")
	private String loanRateType;		// 利率类型
	@ExcelField(title = "贷款期限（期）")
	private String loanPeriod;		    // 贷款期限
	@ExcelField(title = "贷款用途")
	private String purposeId;		    // 贷款用途
	@ExcelField(title = "还款方式（ 1.等额本息/2.等额本金/3.按月付息到期还款）")
	private String payType;		        // 还款方式
	@ExcelField(title = "还款周期（1.年/2.月/3.日/4.季）")
	private String periodType;		    // 还款周期
	private String productId = "6320307f3c724c86a61d5f73512af954";		    // 产品主键
	@ExcelField(title = "是否可提前还款（是、否）")
	private String ifAdvance;		    //是否可提前还款
	@ExcelField(title = "还本金日期（最后一个还款日）")
	private Date payPrincipalDate;		// 还本金日期
	private String organId = "f7382d92e58c423cb55d9ed33fd4f622";	            // 租户ID
	@ExcelField(title = "管理费（%）")
	private String mangeFee;	 	    //管理费
	@ExcelField(title = "宽限期(天)")
	private String gracePeriod;		    //宽限期（天）
	@ExcelField(title = "前期服务费（按次本金%）")
	private String serverFee;	        //前期服务%
	@ExcelField(title = "违约金（按天逾期金额年化%）")
	private String lateFee;			  //滞纳金%
	@ExcelField(title = "提前还款违约金（放款后不足一个月收取一个月利息，满一个月不收）")
	private String advanceDamages;	    //提前还款违约金%
	private String isdirectLoan = "1";       //是否直接放款（是的话，不走放款申请，业务申请之后直接放款）
	private String creditApplyId = id;	     //授信申请表ID 等于客户以及业务
	@ExcelField(title = "删除标记（0.正常/1.删除/2.审核）")
	private String delFlag; 	           // 删除标记（0：正常；1：删除；2：审核）
	@ExcelField(title = "业务状态（/6.未结清 /7.已结清    /8.已逾期   /9.已终止）")
	private String status;		// 状态   1(客户经理新增，还没提交申请) 2:待审批  3:待签订   4:放款审批， 5:待放款   6：未结清   7：已结清    8：已逾期   9:已终止  
	

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}
	public String getLateFee() {
		return lateFee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCreditApplyId() {
		return creditApplyId;
	}
	public void setCreditApplyId(String creditApplyId) {
		this.creditApplyId = creditApplyId;
	}
	public String getIsdirectLoan() {
		return isdirectLoan;
	}
	public void setIsdirectLoan(String isdirectLoan) {
		this.isdirectLoan = isdirectLoan;
	}
	//get and set
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSex() {
		//return sex == "男" ? "1" : "2";
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMarriedInfo() {
		return marriedInfo;
	}
	public void setMarriedInfo(String marriedInfo) {
		this.marriedInfo = marriedInfo;
	}
	public String getCurrentLiveAddress() {
		return currentLiveAddress;
	}
	public void setCurrentLiveAddress(String currentLiveAddress) {
		this.currentLiveAddress = currentLiveAddress;
	}
	public String getHouseholdRegAddr() {
		return householdRegAddr;
	}
	public void setHouseholdRegAddr(String householdRegAddr) {
		this.householdRegAddr = householdRegAddr;
	}
	public String getGatheringName() {
		return gatheringName;
	}
	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	public String getGatheringBank() {
		return gatheringBank;
	}
	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	public String getGatheringNumber() {
		return gatheringNumber;
	}
	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	/*public String getAgriculture() {
		return agriculture;
	}
	public void setAgriculture(String agriculture) {
		this.agriculture = agriculture;
	}*/
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
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
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
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
	public String getIfAdvance() {
		return ifAdvance;
	}
	public void setIfAdvance(String ifAdvance) {
		this.ifAdvance = ifAdvance;
	}
	public Date getPayPrincipalDate() {
		return payPrincipalDate;
	}
	public void setPayPrincipalDate(Date payPrincipalDate) {
		this.payPrincipalDate = payPrincipalDate;
	}
	public String getMangeFee() {
		return mangeFee;
	}
	public void setMangeFee(String mangeFee) {
		this.mangeFee = mangeFee;
	}
	public String getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getServerFee() {
		return serverFee;
	}
	public void setServerFee(String serverFee) {
		this.serverFee = serverFee;
	}
	public String getAdvanceDamages() {
		return advanceDamages;
	}
	public void setAdvanceDamages(String advanceDamages) {
		this.advanceDamages = advanceDamages;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	public TEmployee otTEmployee(TEmployee t){
		t.setName(customerName);
		t.setSex(sex);
		t.setCardNum(cardNum);
		t.setMobile(mobile);
		t.setMarriedInfo(marriedInfo);
		t.setCurrentLiveAddress(currentLiveAddress);
		t.setHouseholdRegAddr(householdRegAddr);
		t.setGatheringBank(gatheringBank);	
		t.setGatheringName(gatheringName);
		t.setGatheringNumber(gatheringNumber);
		
		//租户id
		t.setOrganId(organId);
		//客户状态
		t.setStatus(customerStatus);
		
		
		return t;
	}
	public TLoanContract otTLoanContract ( TLoanContract tl){
		tl.setCustomerName(customerName);
		tl.setContractNumber(contractNumber);
		
		tl.setCustomerType(customerType);
		tl.setBorrower(borrower);
		tl.setIndustryId(industryId);
		/*tl.setAgriculture(agriculture);*/
		tl.setApplyDate(applyDate);
		tl.setLoanType(loanType);
		tl.setLoanAmount(loanAmount);
		tl.setLoanDate(loanDate);
		tl.setLoanRate(loanRate);
		tl.setLoanRateType(loanRateType);
		tl.setLoanPeriod(loanPeriod);
		tl.setPurposeId(purposeId);
		tl.setPayType(payType);
		tl.setProductId(productId);
		tl.setIfAdvance(ifAdvance);
		tl.setPayPrincipalDate(payPrincipalDate);
		tl.setGatheringBank(gatheringBank);;		// 开户行
		tl.setGatheringName(gatheringName);;		// 开户名
		tl.setGatheringNumber(gatheringNumber);;		// 开户账号
		tl.setOrganId(organId);						//租户id
		tl.setPeriodType(periodType);
		tl.setMangeFee(mangeFee);
		tl.setGracePeriod(gracePeriod);
		tl.setServerFee(serverFee);
		tl.setLateFee(lateFee);
		tl.setAdvanceDamages(advanceDamages);
		tl.setIsdirectLoan(isdirectLoan);
		tl.setCreditApplyId(creditApplyId);
		tl.setDelFlag(delFlag);
		tl.setStatus(status);
		return tl;
		
	}
	
	public TLending toTLending(TLending tl){
		tl.setAmount(loanAmount);
		tl.setTime(loanDate);
		TLoanContract tloan = new TLoanContract();
		tloan.setContractNumber(contractNumber);  
		tl.setContract(tloan);
		return tl;
	}
	
	
}
