/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.repayplan.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 还款计划Entity
 * @author lx
 * @version 2016-03-22
 */
@ApiModel(value="还款计划")
public class TRepayPlan extends DataEntity<TRepayPlan> {
	
	
	private static final long serialVersionUID = 1L;
	private String scanFlag;//扫描标识
	private String pushStatus;//同步状态


	private String reportName;		// 公司名称

	@ApiModelProperty(value="批号", dataType="String", notes="批号", hidden=false, required=true)
	private String lotNum;		// 批号
	@ApiModelProperty(value="催收次数", dataType="String", notes="催收次数", hidden=false, required=true)
	private Integer csNum;		// 催收次数
	@ApiModelProperty(value="利息", dataType="String", notes="利息", hidden=false, required=true)
	private String interest;		// 利息
	@ApiModelProperty(value="真实还款利息", dataType="String", notes="真实还款利息", hidden=false, required=true)
	private String interestReal;		// 真实还款利息
	@ApiModelProperty(value="是否逾期", dataType="String", notes="是否逾期", hidden=false, required=true)
	private String isYuQi;		// 是否逾期
	@ApiModelProperty(value="第几笔", dataType="String", notes="第几笔", hidden=false, required=true)
	private Integer num;		// 第几笔
	@ApiModelProperty(value="结清日期", dataType="String", notes="结清日期", hidden=false, required=true)
	private String overDate;		// 结清日期
	@ApiModelProperty(value="付息时间", dataType="String", notes="付息时间", hidden=false, required=true)
	private Date payInterestDate;		// 付息时间
	@ApiModelProperty(value="付息状态", dataType="Date", notes="付息状态", hidden=false, required=true)
	private String payInterestStatus;		// 付息状态
	@ApiModelProperty(value="本金", dataType="String", notes="本金", hidden=false, required=true)
	private String principal;		// 本金
	@ApiModelProperty(value="真实本金", dataType="String", notes="真实本金", hidden=false, required=true)
	private String principalReal;		// 真实本金
	@ApiModelProperty(value="状态", dataType="String", notes="状态", hidden=false, required=true)
	private String status;		// 状态
	@ApiModelProperty(value="逾期天数", dataType="String", notes="逾期天数", hidden=false, required=true)
	private String yuQi;		// 逾期天数
	
	@ApiModelProperty(required=true, notes="合同主键", dataType="String", position=1)
	private String loanContractId;		// 合同主键
	private String startDate;		// 还款开始时间
	private String accountDate;		// 到账时间
	private String endDate;		// 还款截止日期
	private String isValid;		//是否有效
	
	private TLoanContract loanContract;
	private Date beginAccountDate;		// 到账时间范围（开始）
	private Date endAccountDate;		// 到账时间范围（结束）
	
	private String repayAuthId;//还款人认证ID
	
	private String type;//还款计划类型：0:B端借款业务，1:W端借款还款计划  2，W端理财还款计划
	
	private String wishUserId;//wish还款人id
	
	
	//还款提醒时使用，不持久化到数据库
	private String productid;
	
	//jqGrid操作时用
	private String oper;//jqGrid操作标识符;  添加:add;编辑:edit;删除:del
	
	
	
	

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
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
	
	public TRepayPlan() {
		super();
	}

	public TRepayPlan(String id){
		super(id);
	}
	
	@Override
	public void preInsert() {
		super.preInsert();
		this.isValid = "1";
	}

	@Length(min=0, max=11, message="催收次数长度必须介于 0 和 11 之间")
	public Integer getCsNum() {
		return csNum;
	}

	public void setCsNum(Integer csNum) {
		this.csNum = csNum;
	}
	
	@Length(min=0, max=255, message="利息长度必须介于 0 和 255 之间")
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	public String getInterestReal() {
		return interestReal;
	}

	public void setInterestReal(String interestReal) {
		this.interestReal = interestReal;
	}
	
	@Length(min=0, max=11, message="是否逾期长度必须介于 0 和 11 之间")
	public String getIsYuQi() {
		return isYuQi;
	}

	public void setIsYuQi(String isYuQi) {
		this.isYuQi = isYuQi;
	}
	
	@Length(min=0, max=11, message="第几笔长度必须介于 0 和 11 之间")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="结清日期长度必须介于 0 和 255 之间")
	public String getOverDate() {
		return overDate;
	}

	

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayInterestDate() {
		return payInterestDate;
	}

	public void setPayInterestDate(Date payInterestDate) {
		this.payInterestDate = payInterestDate;
	}
	
	@Length(min=0, max=255, message="付息状态长度必须介于 0 和 255 之间")
	public String getPayInterestStatus() {
		return payInterestStatus;
	}

	public void setPayInterestStatus(String payInterestStatus) {
		this.payInterestStatus = payInterestStatus;
	}
	
	@Length(min=0, max=255, message="本金长度必须介于 0 和 255 之间")
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getPrincipalReal() {
		return principalReal;
	}

	public void setPrincipalReal(String principalReal) {
		this.principalReal = principalReal;
	}
	
	@Length(min=0, max=11, message="状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="逾期天数长度必须介于 0 和 11 之间")
	public String getYuQi() {
		return yuQi;
	}

	public void setYuQi(String yuQi) {
		this.yuQi = yuQi;
	}
	
	@Length(min=0, max=11, message="合同主键长度必须介于 0 和 11 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=255, message="还款开始时间长度必须介于 0 和 255 之间")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=255, message="到账时间长度必须介于 0 和 255 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=255, message="还款截止日期长度必须介于 0 和 255 之间")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginAccountDate() {
		return beginAccountDate;
	}

	public void setBeginAccountDate(Date beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	public TLoanContract getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(TLoanContract loanContract) {
		this.loanContract = loanContract;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	
	
	public String getWishUserId() {
		return wishUserId;
	}

	public void setWishUserId(String wishUserId) {
		this.wishUserId = wishUserId;
	}

	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepayAuthId() {
		return repayAuthId;
	}

	public void setRepayAuthId(String repayAuthId) {
		this.repayAuthId = repayAuthId;
	}

	public String toData(TRepayPlan temp,  TLoanContract lc) {		
		String str = "";
		if("1".equals(temp.getDelFlag())){
			str += "D"+"|"+temp.getReportName()+"|";
		}else{
			if("0".equals(this.getPushStatus())){
				str += "A"+"|"+temp.getReportName()+"|";
			}else{
				str += "U"+"|"+temp.getReportName()+"|";
			}
			
		}	
		if (lc != null) {
			str += lc.getContractNumber()+"|" + temp.getEndDate()+"|" + fmtMoney(temp.getPrincipal())+"|" + fmtMoney(temp.getPrincipalReal())+"|" + fmtMoney(temp.getInterest())+"|" + fmtMoney(temp.getInterestReal())+"|";
			str += temp.getIsYuQi()+"|" + temp.getYuQi()+"|" + temp.getStatus()+"|" + temp.getId();
		}
		
	
		/*
		 *字段1	标识符	Varchar(1)	A 新增   U 更新  D 删除
字段2	小贷公司唯一标示	Varchar(25)	唯一识别一个小贷公司
字段3	关联主合同编号	Varchar(25)	必填  关联合同信息
字段4	还款日期	Varchar(10)	必填
字段5	还款本金（元）	Varchar(25)	必填
字段6	已还本金（元）	Varchar(25)	有实际还款需填充数据
字段7	还款利息（元）	Varchar(25)	必填
字段8	已还利息（元）	Varchar(25)	有实际还款需填充数据
字段9	是否逾期	Varchar(25)	0-否，1-是
字段10	逾期天数	Varchar(25)	如果发生逾期按实际天数填充
字段11	状态	Varchar(25)	必填，值为未还、已还
字段12	第三方系统记录ID	Varchar(32)	第三方系统的唯一标识码(便于错误记录回查)

		 */
	///	str += this.getInformFilingType()+"|" + this.getId()+"|";
		

		return str;
	}
	
	
	public static String fmtMoney(String temp) {
		String res = "0.00";
		if (temp == null || temp.trim().equals("") || temp.trim().equals("null") ) {
			return res;
		}		
		NumberFormat df= new DecimalFormat("###.##");	
		df.setMaximumFractionDigits(2);
		res = df.format(Double.parseDouble(temp));
		return res;		
	}
	
	
	
	public String toString(TRepayPlan temp, TLoanContract lc ) {		
		String str = "";
		if("1".equals(this.getDelFlag())){
			str += "D"+"|"+this.getReportName()+"|";
		}else{
			if(this.getCreateDate().getTime() == this.getUpdateDate().getTime()){
				str += "A"+"|"+this.getReportName()+"|";
			}else{
				str += "U"+"|"+this.getReportName()+"|";
			}
			
		}	
		
		TLoanContract lcParent = lc.getParent();
		String tempCn = lcParent.getContractNumber();
		String tempNum = tempCn.split("-")[1].replaceAll("ET", "");
				
		str += tempCn+"|" + tempNum+"|"  + lcParent.getLoanPeriod()+"|" + this.getEndDate()+"|" + fmtMoney(this.getPrincipal())+"|" + fmtMoney(this.getPrincipalReal())+"|" + fmtMoney(this.getInterest())+"|" + fmtMoney(this.getInterestReal())+"|";
		str += this.getStatus()  +"|" +this.getIsYuQi()+"|" + this.getYuQi()+"|" + this.getId();
		

		/*
		 *字段1	标识符	Varchar(1)	A 新增   U 更新  D 删除
字段2	小贷公司唯一标示	Varchar(25)	唯一识别一个小贷公司
字段3	关联主合同编号	Varchar(25)	必填  关联合同信息
字段4	展期次数	Varchar(10)	必填，属于第几次展期
字段5	展期期限	Varchar(25)	必填
字段6	展期后的新还款日期	Varchar(10)	必填，格式YYYY-MM-DD
字段7	展期后的新还款本金（元）	Varchar(25)	必填
字段8	已还本金（元）	Varchar(25)	有实际还款需填充数据
字段9	展期后的新还款利息（元）	Varchar(25)	必填
字段10	已还利息（元）	Varchar(25)	有实际还款需填充数据
字段11	状态	Varchar(25)	必填，值为未还、已还
字段12	是否逾期	Varchar(25)	0-否，1-是
字段13	逾期天数	Varchar(25)	如果发生逾期按实际天数填充
字段14	第三方系统记录ID	Varchar(32)	第三方系统的唯一标识码(便于错误记录回查)
*/
		

		return str;
	}
	
}