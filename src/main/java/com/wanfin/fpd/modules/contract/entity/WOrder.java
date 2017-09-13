package com.wanfin.fpd.modules.contract.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="订单信息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WOrder extends TLoanContract{
	private static final long serialVersionUID = 1L;
	//客户信息
	//产品信息
	@ApiModelProperty(required=true, notes="是否自动化", dataType="String", position=21)
	private String isAuto;
	@ApiModelProperty(required=true, notes="借款人名称", dataType="String", position=21)
	private String customerName;
	@ApiModelProperty(required=true, notes="产品名称", dataType="String", position=22)
	private String productname;
	@ApiModelProperty(required=true, notes="放款日期", dataType="Date", position=23)
	private Date loanDate;		
	@ApiModelProperty(required=true, notes="贷款期限", dataType="String", position=24)
	private String loanPeriod;	
	@ApiModelProperty(required=true, notes="贷款利率", dataType="String", position=25)
	private String loanRate;		
	@ApiModelProperty(required=true, notes="利率类型（年、月、日）", dataType="String", position=26)
	private String loanRateType;		
	@ApiModelProperty(required=true, notes="还本金日期", dataType="Date", position=27)
	private Date payPrincipalDate;		// 还本金日期（日）
	@ApiModelProperty(required=true, notes="付息日", dataType="String", position=28)
	private String payDay;
	@ApiModelProperty(required=true, notes="还款周期", dataType="String", position=29)
	private String periodType;		
	@ApiModelProperty(required=true, notes="状态", dataType="String", position=30)
	private String status;		// 状态   1(客户经理新增，还没提交申请) 2:待审批  3:待签订   4:放款审批， 5:待放款   6：未结清   7：已结清    8：已逾期   9:已终止  
	
	@ApiModelProperty(required=true, notes="订单主键", dataType="String", position=1)
	private String id;
	@ApiModelProperty(required=true, notes="订单号", dataType="String", position=2)
	private String orderSn;
	@ApiModelProperty(required=true, notes="借款产品主键", dataType="String", position=3)
	private String loanId;
	@ApiModelProperty(required=true, notes="贷款产品所属的机构主键", dataType="String", position=4)
	private String agencyId;
	@ApiModelProperty(required=true, notes="借款分类", dataType="String", position=5)
	private String catId;
	@ApiModelProperty(required=true, notes="借款人主键", dataType="String", position=6)
	private String uid;
	@ApiModelProperty(required=true, notes="借款人类型(角色:1、机构,2、个人)", dataType="Integer", position=7)
	private String userType;
	@ApiModelProperty(required=true, notes="借款手续费", dataType="Double", position=8)
	private String loanFee;
	@ApiModelProperty(required=true, notes="还款方式", dataType="Integer", position=9)
	private String repayWay;
	@ApiModelProperty(required=true, notes="申请借款金额", dataType="Double", position=10)
	private String applyAmount;
	@ApiModelProperty(required=true, notes="借款期限", dataType="String", position=11)
	private String installment;
	@ApiModelProperty(required=true, notes="订单状态 1待审核 2不通过，3已通过 4待放款，5已放款，6已拒绝", dataType="Integer", position=12)
	private String rstatus;
	@ApiModelProperty(required=true, notes="添加时间", dataType="Date", position=12)
	private Date addTime;

	private TProduct product;
	private TEmployee employee;
	private TCompany company;
	private Office organ;//租户
	
	public Office getOrgan() {
		return organ;
	}
	public void setOrgan(Office organ) {
		this.organ = organ;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
		if(StringUtils.isNotEmpty(this.agencyId)){
			User u = new User();
			u.setCompany(new Office(this.agencyId ));
			this.setCurrentUser(u);
		}
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getLoanFee() {
		return loanFee;
	}
	public void setLoanFee(String loanFee) {
		this.loanFee = loanFee;
	}
	public String getRepayWay() {
		return repayWay;
	}
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
		setLoanAmount(applyAmount);
	}
	public String getInstallment() {
		return installment;
	}
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
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
	public String getLoanRateType() {
		return loanRateType;
	}
	public void setLoanRateType(String loanRateType) {
		this.loanRateType = loanRateType;
	}
	public Date getPayPrincipalDate() {
		return payPrincipalDate;
	}
	public void setPayPrincipalDate(Date payPrincipalDate) {
		this.payPrincipalDate = payPrincipalDate;
	}
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getRstatus() {
		return rstatus;
	}
	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}
	public TProduct getProduct() {
		return product;
	}
	public void setProduct(TProduct product) {
		this.product = product;
	}
	public TEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(TEmployee employee) {
		this.employee = employee;
	}
	public TCompany getCompany() {
		return company;
	}
	public void setCompany(TCompany company) {
		this.company = company;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
}