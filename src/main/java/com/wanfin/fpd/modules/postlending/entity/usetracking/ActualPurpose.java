/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.entity.usetracking;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 用途跟踪Entity
 * 
 * @author srf
 * @version 2016-04-09
 */
public class ActualPurpose extends ActEntity<ActualPurpose> {

	private static final long serialVersionUID = 1L;
	//private String procInsId; // 进程ID
	private String content; // 用途内容
	private Date insertTime; // 插入时间
	private String title; // 标题
	private String loanContractId; // 合同ID
	private String fromUserId; // 用户ID
	private String address; // 通讯地址、营业地址、经营地址
	private Date applyDate; // 申请时间
	private Double badAmount; // 不良贷款金额
	private Double badLixi; // 结欠利息
	private Integer badNum; // 不良贷款笔数
	private String bxnlAnalysis; // 变现能力分析
	private String bzxlAnalysis; // 保证效力分析
	private String cardNum; // 证件号码(身份证号码、组织机构代码证...)
	private String cardType; // 证件类型(身份证、组织机构代码证、营业执照)
	private Double checkAmount; // 检查金额
	private Double checkBadAmount; // 其中：不良贷款金额
	private Integer checkNum; // 检查笔数
	private String contractNumber; // 合同编号(由时间和maxNumber组成)
	private String customerId; // 客户标识ID
	private String customerName; // 借款人名称 申请客户名称
	private String customerType; // 客户类型（1：职员职工 2:企业 3:个体工商户）
	private String cznlAnalysis; // 偿债能力分析
	private String dbqtAnalysis; // 其他影响执行担保的因素分析
	private String dcyyAnalysis; // 代偿意愿分析
	private Date endDate; // 结束日期(只是做临时存储，数据库数据为空)
	private String fzrIdea; // 负责人意见
	private String hkyyAnalysis; // 还款意愿分析
	private String jcrIdea; // 检查负责人意见
	private String jzAnalysis; // 担保品价值分析
	private String lackAmount; // 结欠金额
	private Integer lackNum; // 结欠笔数
	private String lawAnalysis; // 司法纠纷和经济纠纷分析
	private Date lixiEndDate; // 结息截止日期
	private Double loanAmount; // 借款金额
	private Integer loanNum; // 借款笔数
	private String otherAnalysis; // 其他影响偿还贷款的因素分析
	private String purpose; // 贷款用途
	private String qszkAnalysis; // 权属状况分析
	private String runAnalysis; // 经营状况分析
	private String surety; // 法定代表人
	private String tcnlAnalysis; // 代偿能力分析
	private String usages; // 贷款使用情况
	private String xzAnalysis; // 担保品现状分析
	private String isRead; // 被退回后 是否还显示在退回提醒中 （已看过就不显示）1 已看
	private String status; // 状态 1待审批 2通过 3未通过（历史记录）
	private TProduct product;

	public ActualPurpose() {
		super();
	}

	public ActualPurpose(String id) {
		super(id);
	}

//	@Length(min = 0, max = 64, message = "进程ID长度必须介于 0 和 64 之间")
//	public String getProcInsId() {
//		return procInsId;
//	}
//
//	public void setProcInsId(String procInsId) {
//		this.procInsId = procInsId;
//	}

	@Length(min = 0, max = 255, message = "用途内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Length(min = 0, max = 255, message = "标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min = 0, max = 64, message = "合同ID长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}

	@Length(min = 0, max = 64, message = "用户ID长度必须介于 0 和 64 之间")
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	@Length(min = 0, max = 255, message = "通讯地址、营业地址、经营地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Double getBadAmount() {
		return badAmount;
	}

	public void setBadAmount(Double badAmount) {
		this.badAmount = badAmount;
	}

	public Double getBadLixi() {
		return badLixi;
	}

	public void setBadLixi(Double badLixi) {
		this.badLixi = badLixi;
	}

	public Integer getBadNum() {
		return badNum;
	}

	public void setBadNum(Integer badNum) {
		this.badNum = badNum;
	}

	@Length(min = 0, max = 255, message = "变现能力分析长度必须介于 0 和 255 之间")
	public String getBxnlAnalysis() {
		return bxnlAnalysis;
	}

	public void setBxnlAnalysis(String bxnlAnalysis) {
		this.bxnlAnalysis = bxnlAnalysis;
	}

	@Length(min = 0, max = 255, message = "保证效力分析长度必须介于 0 和 255 之间")
	public String getBzxlAnalysis() {
		return bzxlAnalysis;
	}

	public void setBzxlAnalysis(String bzxlAnalysis) {
		this.bzxlAnalysis = bzxlAnalysis;
	}

	@Length(min = 0, max = 255, message = "证件类型长度必须介于 0 和 255 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	@Length(min = 0, max = 255, message = "证件类型(身份证、组织机构代码证、营业执照)长度必须介于 0 和 255 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public Double getCheckBadAmount() {
		return checkBadAmount;
	}

	public void setCheckBadAmount(Double checkBadAmount) {
		this.checkBadAmount = checkBadAmount;
	}

	public Integer getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}

	@Length(min = 0, max = 255, message = "合同编号(由时间和maxNumber组成)长度必须介于 0 和 255 之间")
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	@Length(min = 0, max = 64, message = "客户标识ID长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Length(min = 0, max = 255, message = "借款人名称 申请客户名称长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Length(min = 0, max = 11, message = "客户类型（1：职员职工 2:企业 3:个体工商户）长度必须介于 0 和 11 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Length(min = 0, max = 255, message = "偿债能力分析长度必须介于 0 和 255 之间")
	public String getCznlAnalysis() {
		return cznlAnalysis;
	}

	public void setCznlAnalysis(String cznlAnalysis) {
		this.cznlAnalysis = cznlAnalysis;
	}

	@Length(min = 0, max = 255, message = "其他影响执行担保的因素分析长度必须介于 0 和 255 之间")
	public String getDbqtAnalysis() {
		return dbqtAnalysis;
	}

	public void setDbqtAnalysis(String dbqtAnalysis) {
		this.dbqtAnalysis = dbqtAnalysis;
	}

	@Length(min = 0, max = 255, message = "代偿意愿分析长度必须介于 0 和 255 之间")
	public String getDcyyAnalysis() {
		return dcyyAnalysis;
	}

	public void setDcyyAnalysis(String dcyyAnalysis) {
		this.dcyyAnalysis = dcyyAnalysis;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Length(min = 0, max = 255, message = "负责人意见长度必须介于 0 和 255 之间")
	public String getFzrIdea() {
		return fzrIdea;
	}

	public void setFzrIdea(String fzrIdea) {
		this.fzrIdea = fzrIdea;
	}

	@Length(min = 0, max = 255, message = "还款意愿分析长度必须介于 0 和 255 之间")
	public String getHkyyAnalysis() {
		return hkyyAnalysis;
	}

	public void setHkyyAnalysis(String hkyyAnalysis) {
		this.hkyyAnalysis = hkyyAnalysis;
	}

	@Length(min = 0, max = 255, message = "检查负责人意见长度必须介于 0 和 255 之间")
	public String getJcrIdea() {
		return jcrIdea;
	}

	public void setJcrIdea(String jcrIdea) {
		this.jcrIdea = jcrIdea;
	}

	@Length(min = 0, max = 255, message = "担保品价值分析长度必须介于 0 和 255 之间")
	public String getJzAnalysis() {
		return jzAnalysis;
	}

	public void setJzAnalysis(String jzAnalysis) {
		this.jzAnalysis = jzAnalysis;
	}

	public String getLackAmount() {
		return lackAmount;
	}

	public void setLackAmount(String lackAmount) {
		this.lackAmount = lackAmount;
	}

	public Integer getLackNum() {
		return lackNum;
	}

	public void setLackNum(Integer lackNum) {
		this.lackNum = lackNum;
	}

	@Length(min = 0, max = 255, message = "司法纠纷和经济纠纷分析长度必须介于 0 和 255 之间")
	public String getLawAnalysis() {
		return lawAnalysis;
	}

	public void setLawAnalysis(String lawAnalysis) {
		this.lawAnalysis = lawAnalysis;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLixiEndDate() {
		return lixiEndDate;
	}

	public void setLixiEndDate(Date lixiEndDate) {
		this.lixiEndDate = lixiEndDate;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getLoanNum() {
		return loanNum;
	}

	public void setLoanNum(Integer loanNum) {
		this.loanNum = loanNum;
	}

	@Length(min = 0, max = 255, message = "其他影响偿还贷款的因素分析长度必须介于 0 和 255 之间")
	public String getOtherAnalysis() {
		return otherAnalysis;
	}

	public void setOtherAnalysis(String otherAnalysis) {
		this.otherAnalysis = otherAnalysis;
	}

	@Length(min = 0, max = 255, message = "贷款用途长度必须介于 0 和 255 之间")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Length(min = 0, max = 255, message = "权属状况分析长度必须介于 0 和 255 之间")
	public String getQszkAnalysis() {
		return qszkAnalysis;
	}

	public void setQszkAnalysis(String qszkAnalysis) {
		this.qszkAnalysis = qszkAnalysis;
	}

	@Length(min = 0, max = 255, message = "经营状况分析长度必须介于 0 和 255 之间")
	public String getRunAnalysis() {
		return runAnalysis;
	}

	public void setRunAnalysis(String runAnalysis) {
		this.runAnalysis = runAnalysis;
	}

	@Length(min = 0, max = 255, message = "法定代表人长度必须介于 0 和 255 之间")
	public String getSurety() {
		return surety;
	}

	public void setSurety(String surety) {
		this.surety = surety;
	}

	@Length(min = 0, max = 255, message = "代偿能力分析长度必须介于 0 和 255 之间")
	public String getTcnlAnalysis() {
		return tcnlAnalysis;
	}

	public void setTcnlAnalysis(String tcnlAnalysis) {
		this.tcnlAnalysis = tcnlAnalysis;
	}

	@Length(min = 0, max = 255, message = "贷款使用情况长度必须介于 0 和 255 之间")
	public String getUsages() {
		return usages;
	}

	public void setUsages(String usages) {
		this.usages = usages;
	}

	@Length(min = 0, max = 255, message = "担保品现状分析长度必须介于 0 和 255 之间")
	public String getXzAnalysis() {
		return xzAnalysis;
	}

	public void setXzAnalysis(String xzAnalysis) {
		this.xzAnalysis = xzAnalysis;
	}

	@Length(min = 0, max = 11, message = "被退回后  是否还显示在退回提醒中 （已看过就不显示）1 已看长度必须介于 0 和 11 之间")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Length(min = 0, max = 11, message = "状态 1待审批  2通过  3未通过（历史记录）长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}
	
}