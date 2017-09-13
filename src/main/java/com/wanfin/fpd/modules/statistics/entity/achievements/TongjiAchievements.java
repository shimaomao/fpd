/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.entity.achievements;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 绩效所需项目Entity
 * @author lxh
 * @version 2017-04-20
 */
public class TongjiAchievements extends DataEntity<TongjiAchievements> {
	
	private static final long serialVersionUID = 1L;
	private String dept;		// 部门
	private String customerManager;		// 客户经理
	private String manage;		// 项目
	private String number;		// 序号
	private String borrower;		// 借款人
	private String loanMoney;		// 贷款金额
	private Date loanDate;		// 贷款期限
	private String yearRate;		// 年化利率
	private String manthRate;		// 月利率
	private String repayMent;		// 还款方式
	private String yearRateMoney;		// 年化利率余额
	private String repaymentStatus;		// 还款是否正常
	private String provisionRatio;		// 计提比例
	private String fortyPercentage;		// 个人计提40%部分
	private String personalMoney;		// 个人计提其中70%个人奖金
	private String tenPersonalMoney;		// 个人计提10月已支出个人费用
	private String preMonthMoney;		// 个人计提前月已支出未扣减费用
	private String personMoney;		// 个人计提本次发放个人奖励
	private String marketMoneyStart;		// 首次计提---其中30%营销费用额度
	private String sixPercentage;		// 60%部分
	private String sevenPercentage;		// 到期对现其中70%个人奖金
	private String marketMoneyEnd;		// 到期兑现---其中30%营销费用额度
	private String total;		// 合计
	private String deptAccruedProportion;		// 部门计提比例
	private String deptAccruedMoney;		// 计提金额
	private String deptMoney;		// 已支出部门费用
	private String deptMoneyUse;		// 可用部门费用额度
	private String compAccruedProportion;		// 公司计提比例
	private String compAccruedMoney;		// 公司计提金额
	private String accruedTotal;		// 计提合计
	
	
	
	
	
	//------------------------------------
	//           查询参数
	private Date currentMonth;//当期时间,格式:2017-03 查询用，必须
	
	
	
	
	public Date getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(Date currentMonth) {
		this.currentMonth = currentMonth;
	}

	public TongjiAchievements() {
		super();
	}

	public TongjiAchievements(String id){
		super(id);
	}

	@Length(min=0, max=100, message="部门长度必须介于 0 和 100 之间")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@Length(min=0, max=100, message="客户经理长度必须介于 0 和 100 之间")
	public String getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	@Length(min=0, max=100, message="项目长度必须介于 0 和 100 之间")
	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}
	
	@Length(min=0, max=100, message="序号长度必须介于 0 和 100 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=100, message="借款人长度必须介于 0 和 100 之间")
	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	
	@Length(min=0, max=255, message="贷款金额长度必须介于 0 和 255 之间")
	public String getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
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
	
	@Length(min=0, max=255, message="月利率长度必须介于 0 和 255 之间")
	public String getManthRate() {
		return manthRate;
	}

	public void setManthRate(String manthRate) {
		this.manthRate = manthRate;
	}
	
	@Length(min=0, max=255, message="还款方式长度必须介于 0 和 255 之间")
	public String getRepayMent() {
		return repayMent;
	}

	public void setRepayMent(String repayMent) {
		this.repayMent = repayMent;
	}
	
	@Length(min=0, max=255, message="年化利率余额长度必须介于 0 和 255 之间")
	public String getYearRateMoney() {
		return yearRateMoney;
	}

	public void setYearRateMoney(String yearRateMoney) {
		this.yearRateMoney = yearRateMoney;
	}
	
	@Length(min=0, max=255, message="还款是否正常长度必须介于 0 和 255 之间")
	public String getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	
	@Length(min=0, max=255, message="计提比例长度必须介于 0 和 255 之间")
	public String getProvisionRatio() {
		return provisionRatio;
	}

	public void setProvisionRatio(String provisionRatio) {
		this.provisionRatio = provisionRatio;
	}
	
	@Length(min=0, max=255, message="个人计提40%部分长度必须介于 0 和 255 之间")
	public String getFortyPercentage() {
		return fortyPercentage;
	}

	public void setFortyPercentage(String fortyPercentage) {
		this.fortyPercentage = fortyPercentage;
	}
	
	@Length(min=0, max=255, message="个人计提其中70%个人奖金长度必须介于 0 和 255 之间")
	public String getPersonalMoney() {
		return personalMoney;
	}

	public void setPersonalMoney(String personalMoney) {
		this.personalMoney = personalMoney;
	}
	
	@Length(min=0, max=255, message="个人计提10月已支出个人费用长度必须介于 0 和 255 之间")
	public String getTenPersonalMoney() {
		return tenPersonalMoney;
	}

	public void setTenPersonalMoney(String tenPersonalMoney) {
		this.tenPersonalMoney = tenPersonalMoney;
	}
	
	@Length(min=0, max=255, message="个人计提前月已支出未扣减费用长度必须介于 0 和 255 之间")
	public String getPreMonthMoney() {
		return preMonthMoney;
	}

	public void setPreMonthMoney(String preMonthMoney) {
		this.preMonthMoney = preMonthMoney;
	}
	
	@Length(min=0, max=255, message="个人计提本次发放个人奖励长度必须介于 0 和 255 之间")
	public String getPersonMoney() {
		return personMoney;
	}

	public void setPersonMoney(String personMoney) {
		this.personMoney = personMoney;
	}
	
	@Length(min=0, max=255, message="首次计提---其中30%营销费用额度长度必须介于 0 和 255 之间")
	public String getMarketMoneyStart() {
		return marketMoneyStart;
	}

	public void setMarketMoneyStart(String marketMoneyStart) {
		this.marketMoneyStart = marketMoneyStart;
	}
	
	@Length(min=0, max=255, message="60%部分长度必须介于 0 和 255 之间")
	public String getSixPercentage() {
		return sixPercentage;
	}

	public void setSixPercentage(String sixPercentage) {
		this.sixPercentage = sixPercentage;
	}
	
	@Length(min=0, max=255, message="到期对现其中70%个人奖金长度必须介于 0 和 255 之间")
	public String getSevenPercentage() {
		return sevenPercentage;
	}

	public void setSevenPercentage(String sevenPercentage) {
		this.sevenPercentage = sevenPercentage;
	}
	
	@Length(min=0, max=255, message="到期兑现---其中30%营销费用额度长度必须介于 0 和 255 之间")
	public String getMarketMoneyEnd() {
		return marketMoneyEnd;
	}

	public void setMarketMoneyEnd(String marketMoneyEnd) {
		this.marketMoneyEnd = marketMoneyEnd;
	}
	
	@Length(min=0, max=255, message="合计长度必须介于 0 和 255 之间")
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	@Length(min=0, max=255, message="部门计提比例长度必须介于 0 和 255 之间")
	public String getDeptAccruedProportion() {
		return deptAccruedProportion;
	}

	public void setDeptAccruedProportion(String deptAccruedProportion) {
		this.deptAccruedProportion = deptAccruedProportion;
	}
	
	@Length(min=0, max=255, message="计提金额长度必须介于 0 和 255 之间")
	public String getDeptAccruedMoney() {
		return deptAccruedMoney;
	}

	public void setDeptAccruedMoney(String deptAccruedMoney) {
		this.deptAccruedMoney = deptAccruedMoney;
	}
	
	@Length(min=0, max=255, message="已支出部门费用长度必须介于 0 和 255 之间")
	public String getDeptMoney() {
		return deptMoney;
	}

	public void setDeptMoney(String deptMoney) {
		this.deptMoney = deptMoney;
	}
	
	@Length(min=0, max=255, message="可用部门费用额度长度必须介于 0 和 255 之间")
	public String getDeptMoneyUse() {
		return deptMoneyUse;
	}

	public void setDeptMoneyUse(String deptMoneyUse) {
		this.deptMoneyUse = deptMoneyUse;
	}
	
	@Length(min=0, max=255, message="公司计提比例长度必须介于 0 和 255 之间")
	public String getCompAccruedProportion() {
		return compAccruedProportion;
	}

	public void setCompAccruedProportion(String compAccruedProportion) {
		this.compAccruedProportion = compAccruedProportion;
	}
	
	@Length(min=0, max=255, message="公司计提金额长度必须介于 0 和 255 之间")
	public String getCompAccruedMoney() {
		return compAccruedMoney;
	}

	public void setCompAccruedMoney(String compAccruedMoney) {
		this.compAccruedMoney = compAccruedMoney;
	}
	
	@Length(min=0, max=255, message="计提合计长度必须介于 0 和 255 之间")
	public String getAccruedTotal() {
		return accruedTotal;
	}

	public void setAccruedTotal(String accruedTotal) {
		this.accruedTotal = accruedTotal;
	}

	
	
}