/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.entity.normal;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 正常结清期末兑现Entity
 * @author 虎
 * @version 2017-04-20
 */
public class TongjiNormal extends DataEntity<TongjiNormal> {
	
	private static final long serialVersionUID = 1L;
	private String dept;		// 部门
	private String number;		// 序号
	private String loanAmount;		// 贷款金额
	private Date loanDate;		// 原贷款期限
	private String yearRate;		// 年化利率
	private String oldPayPlan;		// 原还款方式
	private String yearMoney;		// 年化贷款余额
	private Date endDate;		// 结清日期
	private String ratio;		// 计提比例
	private String sixP;		// 60%部分
	private String sevenP;		// 70%个人奖金
	private String duePayment;		// 本次到期发放
	private String treeP;		// 其中30%
	private String organId;		// 租户ID
	
	
	
	
	//------------------------------------
	//           查询参数
	private Date currentMonth;//当期时间,格式:2017-03 查询用，必须
	
	
	
	
	
	public Date getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}

	public TongjiNormal() {
		super();
	}

	public TongjiNormal(String id){
		super(id);
	}

	@Length(min=0, max=255, message="部门长度必须介于 0 和 255 之间")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@Length(min=0, max=11, message="序号长度必须介于 0 和 11 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=255, message="贷款金额长度必须介于 0 和 255 之间")
	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	
	@Length(min=0, max=255, message="年化利率长度必须介于 0 和 255 之间")
	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	
	@Length(min=0, max=255, message="原还款方式长度必须介于 0 和 255 之间")
	public String getOldPayPlan() {
		return oldPayPlan;
	}

	public void setOldPayPlan(String oldPayPlan) {
		this.oldPayPlan = oldPayPlan;
	}
	
	@Length(min=0, max=255, message="年化贷款余额长度必须介于 0 和 255 之间")
	public String getYearMoney() {
		return yearMoney;
	}

	public void setYearMoney(String yearMoney) {
		this.yearMoney = yearMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=255, message="计提比例长度必须介于 0 和 255 之间")
	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
	@Length(min=0, max=255, message="60%部分长度必须介于 0 和 255 之间")
	public String getSixP() {
		return sixP;
	}

	public void setSixP(String sixP) {
		this.sixP = sixP;
	}
	
	@Length(min=0, max=255, message="70%个人奖金长度必须介于 0 和 255 之间")
	public String getSevenP() {
		return sevenP;
	}

	public void setSevenP(String sevenP) {
		this.sevenP = sevenP;
	}
	
	@Length(min=0, max=255, message="本次到期发放长度必须介于 0 和 255 之间")
	public String getDuePayment() {
		return duePayment;
	}

	public void setDuePayment(String duePayment) {
		this.duePayment = duePayment;
	}
	
	@Length(min=0, max=255, message="其中30%长度必须介于 0 和 255 之间")
	public String getTreeP() {
		return treeP;
	}

	public void setTreeP(String treeP) {
		this.treeP = treeP;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}