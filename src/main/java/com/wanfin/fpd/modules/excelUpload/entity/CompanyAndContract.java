package com.wanfin.fpd.modules.excelUpload.entity;

import java.util.Date;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.contract.IContract;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 企业用户历史记录
 * 
 * */
@SuppressWarnings("serial")
public class CompanyAndContract extends DataEntity<TLoanContract>{
	/**
	 * 
	 */
	@ExcelField(title = "数据编号")
	private String id;              //企业编号
	private String customerId;			//客户ID
	@ExcelField(title = "企业名称")
	private String name;		    // 企业名称
	@ExcelField(title = "企业证件号码")
	private String cardNum;		   // 证件号码
	@ExcelField(title = "证件类型")
	private String cardType;	   // 证件类型
	@ExcelField(title = "企业性质")
	private String properties;		// 企业性质
	@ExcelField(title = "注册资金")
	private String captial;		   // 注册资金
	@ExcelField(title = "企业地址")
	private String address;		   // 企业地址
	@ExcelField(title = "企业电话")
	private String phone;		   // 企业电话
	@ExcelField(title = "法定代表人名称")
	private String surety;		   // 法定代表人
	@ExcelField(title = "法定代表人证件号码")
	private String suretyCardnum;  // 法人身份证
	@ExcelField(title = "法定代表人性别（男/女）")
	private String suretySex;	   // 法人性别
	@ExcelField(title = "法人手机号码")
	private String suretyMobile;   // 法人手机号码
	@ExcelField(title = "法人现住址")
	private String suretyAddress;  // 法人现住址
	@ExcelField(title = "法人户籍地")
	private String suretyRegaddr;  // 法人户籍地址
	@ExcelField(title = "开户名")
	private String gatheringName;  // 法人开户名
	@ExcelField(title = "开户行")
	private String gatheringBank;  // 法人开户行
	@ExcelField(title = "开户账号")
	private String gatheringNumber;// 法人开户账号
	@ExcelField(title = "客户经理")
	private String customerManager;//客户经理
	@ExcelField(title = "客户状态")
	private String companyStatus;     //客户状态    (  black:黑名，black_audit:黑名审核中 正常:normal)
	@ExcelField(title = "业务编号")
	private String contractNumber; // 合同编号
	@ExcelField(title = "客户类型（1.企业/2.个人）")
	private String customerType;   // 客户类型
	@ExcelField(title = "借款主体(1.个人贷款/2.个体工商户贷款/3.农村专业合作组织贷款/4.微型企业贷款/5.小型企业贷款/6.中型及以上企业贷款/7.其它组织贷款)")
	private String borrower;	    //借款主体 add by shirf 20170218
	@ExcelField(title = "行业")
	private String industryId;		    // 贷款行业
/*	@ExcelField(title = "是否涉农")
	private String agriculture;	        //是否涉农 add by shirf 20170218  */
	@ExcelField(title = "申请日期")
	private Date applyDate;		        // 申请日期
	@ExcelField(title = "贷款方式（1.抵押/2.质押/3.信用/4.保证）")
	private String loanType;		    // 贷款方式
	@ExcelField(title = "贷款金额（元）")
	private String loanAmount;		    // 贷款金额
	@ExcelField(title = "放款日期")
	private Date loanDate;		       // 放款日期
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
	private String productId = "6320307f3c724c86a61d5f73512af954";	// 产品主键
	@ExcelField(title = "是否可提前还款（0.否/1.是）")
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
	@ExcelField(title = "客户来源(1 上门2 存量3 新增4 员工5 W端6 其它)")
	private String customerSource;		// 客户来源
	
	
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
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaptial() {
		return captial;
	}
	public void setCaptial(String captial) {
		this.captial = captial;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSurety() {
		return surety;
	}
	public void setSurety(String surety) {
		this.surety = surety;
	}
	public String getSuretyCardnum() {
		return suretyCardnum;
	}
	public void setSuretyCardnum(String suretyCardnum) {
		this.suretyCardnum = suretyCardnum;
	}
	public String getSuretySex() {
		return suretySex;
	}
	public void setSuretySex(String suretySex) {
		this.suretySex = suretySex;
	}
	public String getSuretyMobile() {
		return suretyMobile;
	}
	public void setSuretyMobile(String suretyMobile) {
		this.suretyMobile = suretyMobile;
	}
	public String getSuretyAddress() {
		return suretyAddress;
	}
	public void setSuretyAddress(String suretyAddress) {
		this.suretyAddress = suretyAddress;
	}
	public String getSuretyRegaddr() {
		return suretyRegaddr;
	}
	public void setSuretyRegaddr(String suretyRegaddr) {
		this.suretyRegaddr = suretyRegaddr;
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
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	
	public String getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
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
	public String getLateFee() {
		return lateFee;
	}
	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}
	public String getAdvanceDamages() {
		return advanceDamages;
	}
	public void setAdvanceDamages(String advanceDamages) {
		this.advanceDamages = advanceDamages;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	
	public TCompany otTCompany( TCompany c){
		c.setName(name);
		c.setCardNum(cardNum);
		c.setCardType(cardType);
		c.setProperties(properties);
		c.setCaptial(captial);
		c.setAddress(address);
		c.setPhone(phone);
		c.setSurety(surety);
		c.setSuretyCardnum(suretyCardnum);
		c.setSuretySex(suretySex);
		c.setSuretyMobile(suretyMobile);
		c.setSuretyAddress(suretyAddress);
		c.setSuretyRegaddr(suretyRegaddr);
		c.setGatheringName(gatheringName);
		c.setGatheringBank(gatheringBank);
		c.setGatheringName(gatheringName);
		c.setGatheringNumber(gatheringNumber);
		c.setStatus(companyStatus);
		c.setCustomerSource(customerSource);
		
		c.setOrganId(organId);
		
		return c;
	}
	
	public TLoanContract otTLoanContract (TLoanContract tl){
		tl.setCustomerName(name);
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
		tl.setGatheringBank(gatheringBank);;		// 开户行
		tl.setGatheringName(gatheringName);;		// 开户名
		tl.setGatheringNumber(gatheringNumber);;	// 开户账号
		tl.setOrganId(organId);  					//租户id
		tl.setProductId(productId);
		tl.setIfAdvance(ifAdvance);
		tl.setPayPrincipalDate(payPrincipalDate);
		tl.setPeriodType(periodType);
		tl.setOrganId(organId);   
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
	public TLending toTLending(TLending tl) {
		tl.setAmount(loanAmount);
		tl.setTime(loanDate);
		TLoanContract tloan = new TLoanContract();
		tloan.setContractNumber(contractNumber);  
		tl.setContract(tloan);
		return tl;
	}
}
