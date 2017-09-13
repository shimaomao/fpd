/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.ActEntity;
import com.wanfin.fpd.modules.contract.IContract;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wordnik.swagger.annotations.ApiModel;

/**
 * 业务办理Entity
 * @author lx
 * @version 2016-03-18
 */
@ApiModel(description="合同信息-备份", value="TLoanContractBak")
public class TLoanContractBak extends ActEntity<TLoanContractBak> implements IContract{
	
	private static final long serialVersionUID = 1L;
	private Date applyDate;		// 申请日期
	private String contractNumber;		// 合同编号
	private String customerId;		// 客户id
	private String customerName;		// 客户姓名
	private String customerType;		// 客户类型
	private String fiveLevel;		// 五级分类
	private String isExtend;		// 是否展期
	private String loanAmount;		// 贷款金额
	private Date loanDate;		// 放款日期
	private String loanPeriod;		// 贷款期限
	private String loanRate;		// 贷款利率
	private String loanRateType;		// 利率类型
	private Date payPrincipalDate;		// 还本金日期
	private String loanType;		// 贷款方式
	private String maxNumber;		// 最大数值
	private String payDay;		// 付息日
	private String payType;		// 还款方式
	private String periodType;		// 还款周期
	private Date signDate;		// 签合同日期
	private String status;		// 状态   1(客户经理新增，还没提交申请) 2:待审批  3:待签订   4:放款审批， 5:待放款   6：未结清   7：已结清    8：已逾期   9:已终止  
	private String productId;		// 产品主键
	private String productname;		// 产品名称
	private String purposeId;		// 贷款用途
	private Area area;		// 贷款地区
	private String areaIdT;
	private String industryId;		// 贷款行业
	private String gatheringBank;		// 开户行
	private String gatheringName;		// 开户名
	private String gatheringNumber;		// 开户账号
	private String guarantNumber;		// 担保编号
	private String payOptions;		// 还款选项
	private String payDayType;		// 每期还款日
	private TLoanContractBak parent;		// 关联合同主键id
	
	private String isDeal;             //是否被坏账处理标志（0：否，1：是，）

	
	//用于接收表单数据，并不关联数据库

	private String serverFee;	      //前期服务%
	private String mangeFee;	 	  //管理费%
	private String ifAdvance;		  //是否可提前还款
	private String advanceDamages;	  //提前还款违约金%
	private String lateFee;			  //滞纳金%
	private String rateDiscont;		  //费利优惠率%
	private String ifInterestRelief;  //允许利息减免%
	private String gracePeriod;		  //宽限期（天）
	private String gracePeriodPenalty;//宽限期罚息%
	private String latePenalty;		  //逾期罚息%
	private String latePenaltyFee;	   //逾期罚费%
	private Advance advance;		//提前还款信息 
	
	
	/**
	 * 台账记录使用，不存录数据库
	 */
	private String backmoney;//已还款金额
	private String backlixi;//已还款金额
	private Date backtime;//最近还款时间
	private String legalPerson;//企业表---法定代表人
	private String suretyTelephone;//企业表---法人联系电话
	private String linkphone;//个人表 -----联系电话
	private String searchtime;
	
	//业务统计分析搜索框，不存里数据库
	private Date starttime;
	private Date endtime;
	
	private String type;//业务类型：0,B端；1，W端
	private String wtypeId;//业务主键：W端订单号
	
	
	//用于放款时标示谁做的业务，不存入数据库
	private String creatByerName;
	private String deptname;
	
	
	
	//转理财   add by zzm
	private String transferId;	//转理财后对应W端的借款id
	private String isTransfered;	//是否已转理财
	
	private TLoanContract souceContract; // 变更前的贷款合同ID
	private Date bakTime;// 变更/备份时间
	private String dataStatus;// 变更合同状态1待审批（当前申请变更的备份合同） 2通过 3未通过（历史变更合同）4已签订
	
	public Advance getAdvance() {
		return advance;
	}

	public void setAdvance(Advance advance) {
		this.advance = advance;
	}

	public TLoanContractBak() {
		super();
	}

	public TLoanContractBak(String id){
		super(id);
	}

	public TLoanContractBak(WOrder order) {
		super();
		this.setWtypeId(order.getId());
		this.setProductId(order.getLoanId());
		this.setOrganId(order.getAgencyId());
		this.setLoanType(order.getCatId());
		this.setCustomerId(order.getUid());
		this.setCustomerType(order.getUserType());
		this.setServerFee(order.getLoanFee());
		this.setPayType(order.getRepayWay());
		this.setLoanAmount(order.getApplyAmount());
		this.setLoanPeriod(order.getInstallment());
		this.setStatus(order.getStatus());
		this.setCreateDate(order.getAddTime());
		this.setPayDayType(order.getPayDay());
		this.setStatus(Cons.LoanContractStatus.TO_APPROVE);
		
		
		this.setCustomerName(order.getCustomerName());
		this.setProductname(order.getProductname());
		this.setLoanPeriod(order.getLoanPeriod());
		this.setLoanRate(order.getLoanRate());
		this.setLoanRateType(order.getLoanRateType());
		this.setPayPrincipalDate(order.getPayPrincipalDate());
		this.setPayDay(order.getPayDay());
		this.setPeriodType(order.getPeriodType());
		this.setStatus(order.getStatus());
		
		this.setGatheringBank(order.getGatheringBank());
		this.setGatheringName(order.getGatheringName());
		this.setGatheringNumber(order.getGatheringNumber());
		
		this.setId(null);
		this.setPayOptions("");
		this.setType(Cons.LoanOrderType.TYPE_W);
		this.setLoanDate(new Date());
		this.setApplyDate(new Date());
		this.setSignDate(new Date());
		this.setFiveLevel(Cons.FiveLevelStatus.NORMAL_N);
		this.setIsExtend(Cons.NO);
		this.setIsDeal(Cons.NO);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@Length(min=0, max=255, message="合同编号长度必须介于 0 和 255 之间")
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@Length(min=0, max=50, message="客户id长度必须介于 0 和50 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=255, message="客户姓名长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=50, message="客户类型长度必须介于 0 和 50 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@Length(min=0, max=20, message="五级分类长度必须介于 0 和 20 之间")
	public String getFiveLevel() {
		return fiveLevel;
	}

	public void setFiveLevel(String fiveLevel) {
		this.fiveLevel = fiveLevel;
	}
	
	@Length(min=0, max=11, message="是否展期长度必须介于 0 和 11 之间")
	public String getIsExtend() {
		return isExtend;
	}

	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	
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
	
	@Length(min=0, max=50, message="贷款期限长度必须介于 0 和 50 之间")
	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	
	public String getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	
	@Length(min=0, max=255, message="利率类型长度必须介于 0 和 255 之间")
	public String getLoanRateType() {
		return loanRateType;
	}

	public void setLoanRateType(String loanRateType) {
		this.loanRateType = loanRateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayPrincipalDate() {
		return payPrincipalDate;
	}

	public void setPayPrincipalDate(Date payPrincipalDate) {
		this.payPrincipalDate = payPrincipalDate;
	}
	
	@Length(min=0, max=255, message="贷款方式长度必须介于 0 和 255 之间")
	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	
	
	public List<String> getLoanTypeList() {
		List<String> list = Lists.newArrayList();
		if (loanType != null){
			for (String s : StringUtils.split(loanType, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	
	public void setLoanTypeList(List<String> list) {
		loanType= ","+StringUtils.join(list, ",")+",";
	}

	public List<String> getPayOptionsList() {
		List<String> list = Lists.newArrayList();
		if (payOptions != null){
			for (String s : StringUtils.split(payOptions, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	
	public void setPayOptionsList(List<String> list) {
		payOptions= ","+StringUtils.join(list, ",")+",";
	}
	
	
	
	
	@Length(min=0, max=11, message="最大数值长度必须介于 0 和 11 之间")
	public String getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	@Length(min=0, max=255, message="付息日长度必须介于 0 和 255 之间")
	public String getPayDay() {
		return payDay;
	}

	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	
	@Length(min=0, max=255, message="还款方式长度必须介于 0 和 255 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=255, message="还款周期长度必须介于 0 和 255 之间")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=255, message="状态长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="产品主键长度必须介于 0 和 50 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=50, message="贷款用途长度必须介于 0 和 50 之间")
	public String getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="贷款行业长度必须介于 0 和 255 之间")
	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	
	@Length(min=0, max=255, message="开户行长度必须介于 0 和 255 之间")
	public String getGatheringBank() {
		return gatheringBank;
	}

	public void setGatheringBank(String gatheringBank) {
		this.gatheringBank = gatheringBank;
	}
	
	@Length(min=0, max=255, message="开户名长度必须介于 0 和 255 之间")
	public String getGatheringName() {
		return gatheringName;
	}

	public void setGatheringName(String gatheringName) {
		this.gatheringName = gatheringName;
	}
	
	@Length(min=0, max=255, message="开户账号长度必须介于 0 和 255 之间")
	public String getGatheringNumber() {
		return gatheringNumber;
	}

	public void setGatheringNumber(String gatheringNumber) {
		this.gatheringNumber = gatheringNumber;
	}
	
	
	@Length(min=0, max=255, message="担保编号长度必须介于 0 和 255 之间")
	public String getGuarantNumber() {
		return guarantNumber;
	}

	public void setGuarantNumber(String guarantNumber) {
		this.guarantNumber = guarantNumber;
	}
	
	@Length(min=0, max=255, message="还款选项长度必须介于 0 和 255 之间")
	public String getPayOptions() {
		return payOptions;
	}

	public void setPayOptions(String payOptions) {
		this.payOptions = payOptions;
	}
	
	@Length(min=0, max=255, message="每期还款日长度必须介于 0 和 255 之间")
	public String getPayDayType() {
		return payDayType;
	}

	public void setPayDayType(String payDayType) {
		this.payDayType = payDayType;
	}

	public TLoanContractBak getParent() {
		return parent;
	}

	public void setParent(TLoanContractBak parent) {
		this.parent = parent;
	}

	@Override
	@Transient
	public String getUkey() {
		return IContractUkey.LOAN;
	}


	
	

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getServerFee() {
		return serverFee;
	}

	public void setServerFee(String serverFee) {
		this.serverFee = serverFee;
	}

	public String getMangeFee() {
		return mangeFee;
	}

	public void setMangeFee(String mangeFee) {
		this.mangeFee = mangeFee;
	}

	public String getIfAdvance() {
		return ifAdvance;
	}

	public void setIfAdvance(String ifAdvance) {
		this.ifAdvance = ifAdvance;
	}

	public String getAdvanceDamages() {
		return advanceDamages;
	}

	public void setAdvanceDamages(String advanceDamages) {
		this.advanceDamages = advanceDamages;
	}

	public String getLateFee() {
		return lateFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}

	public String getRateDiscont() {
		return rateDiscont;
	}

	public void setRateDiscont(String rateDiscont) {
		this.rateDiscont = rateDiscont;
	}

	public String getIfInterestRelief() {
		return ifInterestRelief;
	}

	public void setIfInterestRelief(String ifInterestRelief) {
		this.ifInterestRelief = ifInterestRelief;
	}

	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getGracePeriodPenalty() {
		return gracePeriodPenalty;
	}

	public void setGracePeriodPenalty(String gracePeriodPenalty) {
		this.gracePeriodPenalty = gracePeriodPenalty;
	}

	public String getLatePenalty() {
		return latePenalty;
	}

	public void setLatePenalty(String latePenalty) {
		this.latePenalty = latePenalty;
	}

	public String getLatePenaltyFee() {
		return latePenaltyFee;
	}

	public void setLatePenaltyFee(String latePenaltyFee) {
		this.latePenaltyFee = latePenaltyFee;
	}

	public String getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}

	public String getBackmoney() {
		return backmoney;
	}

	public void setBackmoney(String backmoney) {
		this.backmoney = backmoney;
	}

	public String getBacklixi() {
		return backlixi;
	}

	public void setBacklixi(String backlixi) {
		this.backlixi = backlixi;
	}

	public Date getBacktime() {
		return backtime;
	}

	public void setBacktime(Date backtime) {
		this.backtime = backtime;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}


	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getSuretyTelephone() {
		return suretyTelephone;
	}

	public void setSuretyTelephone(String suretyTelephone) {
		this.suretyTelephone = suretyTelephone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}

	public String getSearchtime() {
		return searchtime;
	}

	public void setSearchtime(String searchtime) {
		this.searchtime = searchtime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getIsTransfered() {
		return isTransfered;
	}

	public void setIsTransfered(String isTransfered) {
		this.isTransfered = isTransfered;
	}

	public String getAreaIdT() {
		return areaIdT;
	}

	public void setAreaIdT(String areaIdT) {
		this.areaIdT = areaIdT;
	}

	public String getCreatByerName() {
		return creatByerName;
	}

	public void setCreatByerName(String creatByerName) {
		this.creatByerName = creatByerName;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	

	public TLoanContract getSouceContract() {
		return souceContract;
	}

	public void setSouceContract(TLoanContract souceContract) {
		this.souceContract = souceContract;
	}

	public Date getBakTime() {
		return bakTime;
	}

	public void setBakTime(Date bakTime) {
		this.bakTime = bakTime;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	
}