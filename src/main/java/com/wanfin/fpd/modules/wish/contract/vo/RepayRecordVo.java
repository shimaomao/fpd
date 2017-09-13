package com.wanfin.fpd.modules.wish.contract.vo;

import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;

public class RepayRecordVo extends ApiEntity<RepayRecordVo>{

	@ExcelField(title="客户id", align=2, sort=1)
	private String customerId;		// 客户id
	
	@ExcelField(title="客户姓名", align=2, sort=2)
	private String customerName;		// 客户姓名

	@ExcelField(title="开户账号", align=2, sort=3)
	private String gatheringNumber;		// 开户账号
	
	@ExcelField(title="借款金额", align=2, sort=4)
	private String sumLoanAmount;//借款金额
	
	@ExcelField(title="手续费", align=2, sort=5)
	private String sumCharge;//手续费
	
	@ExcelField(title="当次还款金额", align=2, sort=6)
	private String repayMoney;//当次还款金额
	
	@ExcelField(title="当次还款时间", align=2, sort=7)
	private  String repayTime;//当次还款时间
	
	@ExcelField(title="已还款金额", align=2, sort=8)
	private  String sumRepayAmount;//已还款金额
	
	@ExcelField(title="剩余未还金额", align=2, sort=9)
	private  String diffAmount;//剩余未还金额
	
	@ExcelField(title="当次罚息金额", align=2, sort=10)
	private String overFee;//当次罚息金额
	
	@ExcelField(title="业务id", align=2, sort=11)
	private String loanContractId;//业务id
	

	@ExcelField(title="业务状态", align=2, sort=11)
	private String status;//业务状态
	
	private String starttime;
	private String endtime;
	
	
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
	
	public String getGatheringNumber() {
		return gatheringNumber;
	}
	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	public String getSumLoanAmount() {
		return sumLoanAmount;
	}
	public void setSumLoanAmount(String sumLoanAmount) {
		this.sumLoanAmount = sumLoanAmount;
	}
	public String getSumCharge() {
		return sumCharge;
	}
	public void setSumCharge(String sumCharge) {
		this.sumCharge = sumCharge;
	}
	public String getRepayMoney() {
		return repayMoney;
	}
	public void setRepayMoney(String repayMoney) {
		this.repayMoney = repayMoney;
	}
	public String getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	public String getSumRepayAmount() {
		return sumRepayAmount;
	}
	public void setSumRepayAmount(String sumRepayAmount) {
		this.sumRepayAmount = sumRepayAmount;
	}
	public String getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(String diffAmount) {
		this.diffAmount = diffAmount;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getOverFee() {
		return overFee;
	}
	public void setOverFee(String overFee) {
		this.overFee = overFee;
	}
	public String getLoanContractId() {
		return loanContractId;
	}
	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
