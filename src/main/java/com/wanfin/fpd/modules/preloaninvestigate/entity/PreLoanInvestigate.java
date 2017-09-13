/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.preloaninvestigate.entity;

import java.util.Date;

import javax.validation.constraints.Min;

import com.wanfin.fpd.common.persistence.ActEntity;

/**
 * 立项调查Entity
 * @author zzm
 * @version 2016-06-13
 */
public class PreLoanInvestigate extends ActEntity<PreLoanInvestigate> {
	
	private static final long serialVersionUID = 1L;
	private String title;	//标题
//	private TLoanContract loanContract;		// 合同
	private String content;		// 内容
	
	//新增    20160804---lx
	private String customerId;		// 客户id
	private String customerName;		// 客户姓名
	private String customerType;		// 客户类型
	private String loanamount;       //贷款金额
	private String rate;          // 年利率
	private String period;         //期限
	private String loantype;             //贷款方式
	private String paytype;		        // 还款方式
	private String purpose;		// 贷款用途
	private String investigateNumber;   //立项编号
	private String customerPhone;        //客户联系电话
	private Date investigaDate;          //调查时间   
	private String status;              //状态   新建   审批中    审批通过， 审批不通过

	
	
	public PreLoanInvestigate() {
		super();
	}

	public PreLoanInvestigate(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	@NotNull(message="合同不能为空")
//	public TLoanContract getLoanContract() {
//		return loanContract;
//	}
//
//	public void setLoanContract(TLoanContract loanContract) {
//		this.loanContract = loanContract;
//	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	@Min(value=100,message="借款金额需要大于等于100元")
	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getInvestigateNumber() {
		return investigateNumber;
	}

	public void setInvestigateNumber(String investigateNumber) {
		this.investigateNumber = investigateNumber;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Date getInvestigaDate() {
		return investigaDate;
	}

	public void setInvestigaDate(Date investigaDate) {
		this.investigaDate = investigaDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}