/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;

/**
 * 申请退款Entity
 * @author srf
 * @version 2016-04-06
 */
public class Reimburse extends ActEntity<Reimburse> {
	
	private static final long serialVersionUID = 1L;
	private String backMonth;		// 还款月数
	private String backName;		// 借款人
	private Double backPrice;		// 退回还款金额
	private String businessType;		// 业务类型(0:常规贷款， 2：循环授信贷款)
	private String customerName;		// 申请人
	private Date insertTime;		// 申请时间
	private String loanContractId;		// 合同ID
	private String loanPeriod;		// 贷款期限
	private Integer outDay;		// 超前天数
	private Double outPrice;		// 超支费用
	private Date reimburseyDate;		// 收款日期
	private Date returnTime;		// 退费实际时间
	private String status;		// 退款状态[数字]1新增，可提交审批 2待审批    3待放款     4已退费  5 已撤回   6待签订
	private String isRead;		// 被退回后  是否还显示在退回提醒中 （已看过就不显示）   1 已看
	private String jiePrice;		// 借款金额(元)
	private String tiPrice;		// 提前还款金额(元)
	private String yongDay;		// 用款天数
	private String bankname;		// 开户银行
	private String banknumber;		// 开户账号
	private String bankusername;		// 开户人名
	
	private String contractNumber;		// 合同编号

	
	private List<ReimburseFile> reimburseFile ;//= new ArrayList<ReimburseFile>()
	
	public Reimburse() {
		super();
	}

	public Reimburse(String id){
		super(id);
	}

	@Length(min=0, max=255, message="还款月数长度必须介于 0 和 255 之间")
	public String getBackMonth() {
		return backMonth;
	}

	public void setBackMonth(String backMonth) {
		this.backMonth = backMonth;
	}
	
	@Length(min=0, max=255, message="借款人长度必须介于 0 和 255 之间")
	public String getBackName() {
		return backName;
	}

	public void setBackName(String backName) {
		this.backName = backName;
	}
	
	public Double getBackPrice() {
		return backPrice;
	}

	public void setBackPrice(Double backPrice) {
		this.backPrice = backPrice;
	}
	
	@Length(min=0, max=255, message="业务类型(0:常规贷款， 2：循环授信贷款)长度必须介于 0 和 255 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Length(min=0, max=255, message="申请人长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Length(min=0, max=64, message="合同ID长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=50, message="贷款期限长度必须介于 0 和 50 之间")
	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	public Integer getOutDay() {
		return outDay;
	}

	public void setOutDay(Integer outDay) {
		this.outDay = outDay;
	}
	
	public Double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReimburseyDate() {
		return reimburseyDate;
	}

	public void setReimburseyDate(Date reimburseyDate) {
		this.reimburseyDate = reimburseyDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=255, message="退款状态[数字]1待审批    2待放款     3已退费   4 已撤回   5待签订长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="被退回后  是否还显示在退回提醒中 （已看过就不显示）   1 已看长度必须介于 0 和 11 之间")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	public String getJiePrice() {
		return jiePrice;
	}

	public void setJiePrice(String jiePrice) {
		this.jiePrice = jiePrice;
	}
	
	public String getTiPrice() {
		return tiPrice;
	}

	public void setTiPrice(String tiPrice) {
		this.tiPrice = tiPrice;
	}
	
	@Length(min=0, max=255, message="用款天数长度必须介于 0 和 255 之间")
	public String getYongDay() {
		return yongDay;
	}

	public void setYongDay(String yongDay) {
		this.yongDay = yongDay;
	}
	
	@Length(min=0, max=255, message="开户银行长度必须介于 0 和 255 之间")
	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@Length(min=0, max=255, message="开户账号长度必须介于 0 和 255 之间")
	public String getBanknumber() {
		return banknumber;
	}

	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}
	
	@Length(min=0, max=255, message="开户人名长度必须介于 0 和 255 之间")
	public String getBankusername() {
		return bankusername;
	}

	public void setBankusername(String bankusername) {
		this.bankusername = bankusername;
	}
	
	public List<ReimburseFile> getReimburseFile() {
		return reimburseFile;
	}

	public void setReimburseFile(List<ReimburseFile> reimburseFile) {
		this.reimburseFile = reimburseFile;
	}



	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	@Override
	public String toString() {
		return "Reimburse [backMonth=" + backMonth + ", backName=" + backName + ", backPrice=" + backPrice
				+ ", businessType=" + businessType + ", customerName=" + customerName + ", insertTime=" + insertTime
				+ ", loanContractId=" + loanContractId + ", loanPeriod=" + loanPeriod + ", outDay=" + outDay
				+ ", outPrice=" + outPrice + ", reimburseyDate=" + reimburseyDate + ", returnTime=" + returnTime
				+ ", status=" + status + ", isRead=" + isRead + ", jiePrice=" + jiePrice + ", tiPrice=" + tiPrice
				+ ", yongDay=" + yongDay + ", bankname=" + bankname + ", banknumber=" + banknumber + ", bankusername="
				+ bankusername + ", organId=" + organId + ", reimburseFile=" + reimburseFile + "]";
	}
	
}