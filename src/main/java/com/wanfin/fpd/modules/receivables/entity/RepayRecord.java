/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * 真实还款记录Entity
 * @author srf
 * @version 2016-03-30
 */
@ApiModel
public class RepayRecord extends ApiEntity<RepayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String isYuQi;		// 是否逾期
	private Double money;		// 还款金额
	private Date repayDate;		// 还款日期
	private String loanContractId;		// 关联合同主键id
	
	private String scanFlag;//扫描标识	
	private String pushStatus;//同步状态
	
	private Double rePrincipal;//还款本金
	
	private Double reInterest;//还款利息

	private Double wishOverFee;//wish业务逾期罚息
	
	private String sumRepayMoney;////wish:截止本次还款 总计已还---变态的月报表要用
	
	private int num;//期数
	
	private String reportName;		// 公司名称
	
	
	private String isFinsh="0";//是否结清本期-----不持久化
	
	
	public RepayRecord() {
		super();
	}

	public RepayRecord(String id){
		super(id);
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	
	@Length(min=0, max=255, message="是否逾期长度必须介于 0 和 255 之间")
	public String getIsYuQi() {
		return isYuQi;
	}

	public void setIsYuQi(String isYuQi) {
		this.isYuQi = isYuQi;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	@Length(min=0, max=64, message="关联合同主键id长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Double getRePrincipal() {
		return rePrincipal;
	}

	public void setRePrincipal(Double rePrincipal) {
		this.rePrincipal = rePrincipal;
	}

	public Double getReInterest() {
		return reInterest;
	}

	public void setReInterest(Double reInterest) {
		this.reInterest = reInterest;
	}

	
	public String getIsFinsh() {
		return isFinsh;
	}

	public void setIsFinsh(String isFinsh) {
		this.isFinsh = isFinsh;
	}

	public String getSumRepayMoney() {
		return sumRepayMoney;
	}

	public void setSumRepayMoney(String sumRepayMoney) {
		this.sumRepayMoney = sumRepayMoney;
	}

	public Double getWishOverFee() {
		return wishOverFee;
	}

	public void setWishOverFee(Double wishOverFee) {
		this.wishOverFee = wishOverFee;
	}

	public String toString(RepayRecord rr, TLoanContract lc) {	
		
		
		String str = "";
		if("1".equals(this.getDelFlag())){
			str += "D"+"|"+this.getReportName()+"|";
		}else{
			if("0".equals(this.getPushStatus())){
				str += "A"+"|"+this.getReportName()+"|";
			}else{
				str += "U"+"|"+this.getReportName()+"|";
			}
			
		}	
		
		str += lc.getContractNumber()+"|" + TRepayPlan.fmtMoney(rr.getMoney() + "")+"|" + DateUtils.formatDate(this.getRepayDate())+"|"  + this.getId();
		

		/*
		 *字段1	标识符	Varchar(1)	A 新增   U 更新  D 删除
字段2	小贷公司唯一标示	Varchar(25)	唯一识别一个小贷公司
字段3	关联主合同编号	Varchar(25)	必填  关联合同信息
字段4	还款金额	Varchar(25)	必填
字段5	还款日期	Varchar(10)	必填
字段6	第三方系统记录ID	Varchar(32)	第三方系统的唯一标识码(便于错误记录回查)


		 */

		

		return str;
	}
	
	
	public String toETString(RepayRecord rr, TLoanContract lc) {	
		
		
		String str = "";
		if("1".equals(this.getDelFlag())){
			str += "D"+"|"+this.getReportName()+"|";
		}else{
			if("0".equals(this.getPushStatus())){
				str += "A"+"|"+this.getReportName()+"|";
			}else{
				str += "U"+"|"+this.getReportName()+"|";
			}
			
		}	
		
		TLoanContract lcParent = lc.getParent();
		String tempCn = lcParent.getContractNumber();
		String tempNum = tempCn.split("-")[1].replaceAll("ET", "");
		
		str += tempCn+"|" + tempNum+"|" + TRepayPlan.fmtMoney(rr.getMoney() + "")+"|" + DateUtils.formatDate(this.getRepayDate())+"|"  + this.getId();
		

		/*
		 *字段1	标识符	Varchar(1)	A 新增   U 更新  D 删除
字段2	小贷公司唯一标示	Varchar(25)	唯一识别一个小贷公司
字段3	关联主合同编号	Varchar(25)	必填  关联合同信息
字段4	展期次数	Varchar(10)	必填，对应展期计划中的展期次数（第几次展期）
字段5	还款本金(元)	Varchar(25)	必填
字段6	还款利息(元)	Varchar(25)	必填
字段7	还款日期	Varchar(10)	必填
字段8	第三方系统记录ID	Varchar(32)	第三方系统的唯一标识码(便于错误记录回查)


		 */

		

		return str;
	}
	
	
}