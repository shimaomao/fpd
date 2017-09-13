/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 产品管理Entity
 * @author lx
 * @version 2016-03-23
 */
@ApiModel(value="产品信息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TProduct extends ApiEntity<TProduct> {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="产品类型", dataType="String", notes="产品类型", hidden=false, required=true)
	private String type;		// 产品类型
	@ApiModelProperty(value="产品名称", dataType="String", notes="产品名称", hidden=false, required=true)
	private String name;		// 产品名称
	@ApiModelProperty(value="产品介绍", dataType="String", notes="产品介绍", hidden=false, required=true)
	private String detail;		// 产品介绍
	@ApiModelProperty(value="风险措施说明", dataType="String", notes="风险措施说明", hidden=false, required=true)
	private String riskDetail;		// 风险措施说明
	@ApiModelProperty(value="发行对象", dataType="String", notes="发行对象", hidden=false, required=true)
	private String releasesObje;		// 发行对象
	@ApiModelProperty(value="最低销售额", dataType="String", notes="最低销售额", hidden=false, required=true)
	private String comSales;		// 最低销售额
	@ApiModelProperty(value="发行地区", dataType="String", notes="发行地区", hidden=false, required=true)
	private Area area;		// 发行地区
	@ApiModelProperty(value="发行渠道", dataType="String", notes="发行渠道", hidden=false, required=true)
	private String releasesWay;		// 发行渠道  1:万众金融     2：自营平台
	
	@ApiModelProperty(value="产品规模", dataType="String", notes="产品规模", hidden=false, required=true)
	private String amount;		// 产品规模（金额）
	@ApiModelProperty(value="额度上限", dataType="String", notes="额度上限", hidden=false, required=true)
	private String amountMax;		// 额度上限
	@ApiModelProperty(value="额度下限", dataType="String", notes="额度下限", hidden=false, required=true)
	private String amountMin;		// 额度下限
	@ApiModelProperty(value="放款方式", dataType="String", notes="放款方式", hidden=false, required=true)
	private String loanWay;		// 放款方式
	@ApiModelProperty(value="还款方式", dataType="String", notes="还款方式", hidden=false, required=true)
	private String payType;		// 还款方式
	@ApiModelProperty(value="期限", dataType="String", notes="期限", hidden=false, required=true)
	private Date period;		// 期限
	@ApiModelProperty(value="计息年天数", dataType="Date", notes="计息年天数", hidden=false, required=true)
	private String yearDays;		// 计息年天数
	@ApiModelProperty(value="年化利率", dataType="String", notes="年化利率", hidden=false, required=true)
	private String rate;		// 年化利率
	@ApiModelProperty(value="服务费", dataType="String", notes="服务费", hidden=false, required=true)
	private String serverFee;		// 服务费
	@ApiModelProperty(value="服务费是否可退费", dataType="String", notes="服务费是否可退费", hidden=false, required=true)
	private String serverRefund;		// 服务费是否可退费
	@ApiModelProperty(value="担保费", dataType="String", notes="担保费", hidden=false, required=true)
	private String guaranteeFee;		// 担保费
	@ApiModelProperty(value="担保费是否可退费", dataType="String", notes="担保费是否可退费", hidden=false, required=true)
	private String guaranteeRefund;		// 担保费是否可退费
	@ApiModelProperty(value="管理费", dataType="String", notes="管理费", hidden=false, required=true)
	private String mangeFee;		// 管理费
	@ApiModelProperty(value="管理费是否可退费", dataType="String", notes="管理费是否可退费", hidden=false, required=true)
	private String mangeRefund;		// 管理费是否可退费
	@ApiModelProperty(value="提前还款", dataType="String", notes="提前还款", hidden=false, required=true)
	private String advanceRepay;		// 提前还款
	@ApiModelProperty(value="提前还款违约费用", dataType="String", notes="提前还款违约费用", hidden=false, required=true)
	private String breakFee;		// 提前还款违约费用
	@ApiModelProperty(value="滞纳金", dataType="String", notes="滞纳金", hidden=false, required=true)
	private String lateFee;		// 滞纳金
	@ApiModelProperty(value="费率优惠率", dataType="String", notes="费率优惠率", hidden=false, required=true)
	private String rateDiscount;		// 费率优惠率
	@ApiModelProperty(value="运行利息减免", dataType="String", notes="运行利息减免", hidden=false, required=true)
	private String iflixiRedu;		// 运行利息减免
	@ApiModelProperty(value="宽限期", dataType="String", notes="宽限期", hidden=false, required=true)
	private String gracePeriod;		// 宽限期
	@ApiModelProperty(value="宽限期罚息", dataType="String", notes="宽限期罚息", hidden=false, required=true)
	private String graceFaxi;		// 宽限期罚息
	@ApiModelProperty(value="逾期罚息", dataType="String", notes="逾期罚息", hidden=false, required=true)
	private String yuqiFaxi;		// 逾期罚息
	@ApiModelProperty(value="逾期罚费", dataType="String", notes="逾期罚费", hidden=false, required=true)
	private String yuqiFee;		// 逾期罚费
	@ApiModelProperty(value="贷款方式", dataType="String", notes="贷款方式", hidden=false, required=true)
	private String loanType;		// 贷款方式
	@ApiModelProperty(value="贷款方式二级", dataType="String", notes="贷款方式二级", hidden=false, required=true)
	private String loanType2;		// 贷款方式二级
	@ApiModelProperty(value="抵押率", dataType="String", notes="抵押率", hidden=false, required=true)
	private String diyalv;		// 抵押率
	@ApiModelProperty(value="质押率", dataType="String", notes="质押率", hidden=false, required=true)
	private String zhiyalv;		// 质押率
	@ApiModelProperty(value="授信方式", dataType="String", notes="授信方式", hidden=false, required=true)
	private String creditWay;		// 授信方式
	@ApiModelProperty(value="授信期限", dataType="String", notes="授信期限", hidden=false, required=true)
	private String creditPeriod;		// 授信期限
	@ApiModelProperty(value="状态", dataType="String", notes="状态", hidden=false, required=true)
	private String status;		//new 新建、 published 已发布
	
	//用于接收前台参数，不用与保存
	@ApiModelProperty(value="表单模板", dataType="String", notes="表单模板", hidden=false, required=true)
	private String modelId;//表单模板id
	@ApiModelProperty(value="W端产品主键", dataType="String", notes="W端产品主键", hidden=false, required=true)
	private String wtypeId;//W端产品ID
	private List<ProductBizCfg> bizCfgList;//产品各业务节点的配置
	
	
	
	//一下不跟数据库挂钩，仅供页面使用
	private Integer exanple_amount;//待审批业务合同数
	private Integer sign_amount;//待签订业务合同数
	private Integer loan_amount;//待放款业务合同数
	
	private String minLoanTime; //至少贷款期限
	private String maxLoanTime;	//最多贷款期限
	
	public TProduct() {
		super();
	}

	public TProduct(String id){
		super(id);
	}

	@Length(min=0, max=11, message="产品类型长度必须介于 0 和 11 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="产品名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="产品介绍长度必须介于 0 和 255 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Length(min=0, max=255, message="风险措施说明长度必须介于 0 和 255 之间")
	public String getRiskDetail() {
		return riskDetail;
	}

	public void setRiskDetail(String riskDetail) {
		this.riskDetail = riskDetail;
	}
	
	@Length(min=0, max=11, message="发行对象长度必须介于 0 和 11 之间")
	public String getReleasesObje() {
		return releasesObje;
	}

	public void setReleasesObje(String releasesObje) {
		this.releasesObje = releasesObje;
	}
	
	@Length(min=0, max=255, message="最低销售额长度必须介于 0 和 255 之间")
	public String getComSales() {
		return comSales;
	}

	public void setComSales(String comSales) {
		this.comSales = comSales;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=11, message="发行渠道长度必须介于 0 和 11 之间")
	public String getReleasesWay() {
		return releasesWay;
	}

	public void setReleasesWay(String releasesWay) {
		this.releasesWay = releasesWay;
	}
	
	public String getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(String amountMax) {
		this.amountMax = amountMax;
	}
	
	public String getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(String amountMin) {
		this.amountMin = amountMin;
	}
	
	@Length(min=0, max=255, message="放款方式长度必须介于 0 和 255 之间")
	public String getLoanWay() {
		return loanWay;
	}

	public void setLoanWay(String loanWay) {
		this.loanWay = loanWay;
	}
	
	@Length(min=0, max=11, message="还款方式长度必须介于 0 和 11 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}
	
	@Length(min=0, max=255, message="计息年天数长度必须介于 0 和 255 之间")
	public String getYearDays() {
		return yearDays;
	}

	public void setYearDays(String yearDays) {
		this.yearDays = yearDays;
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Length(min=0, max=255, message="服务费长度必须介于 0 和 255 之间")
	public String getServerFee() {
		return serverFee;
	}

	public void setServerFee(String serverFee) {
		this.serverFee = serverFee;
	}
	
	@Length(min=0, max=255, message="服务费是否可退费长度必须介于 0 和 255 之间")
	public String getServerRefund() {
		return serverRefund;
	}

	public void setServerRefund(String serverRefund) {
		this.serverRefund = serverRefund;
	}
	
	@Length(min=0, max=255, message="担保费长度必须介于 0 和 255 之间")
	public String getGuaranteeFee() {
		return guaranteeFee;
	}

	public void setGuaranteeFee(String guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	@Length(min=0, max=255, message="担保费是否可退费长度必须介于 0 和 255 之间")
	public String getGuaranteeRefund() {
		return guaranteeRefund;
	}

	public void setGuaranteeRefund(String guaranteeRefund) {
		this.guaranteeRefund = guaranteeRefund;
	}
	
	@Length(min=0, max=255, message="管理费长度必须介于 0 和 255 之间")
	public String getMangeFee() {
		return mangeFee;
	}

	public void setMangeFee(String mangeFee) {
		this.mangeFee = mangeFee;
	}
	
	@Length(min=0, max=255, message="管理费是否可退费长度必须介于 0 和 255 之间")
	public String getMangeRefund() {
		return mangeRefund;
	}

	public void setMangeRefund(String mangeRefund) {
		this.mangeRefund = mangeRefund;
	}
	
	@Length(min=0, max=255, message="提前还款长度必须介于 0 和 255 之间")
	public String getAdvanceRepay() {
		return advanceRepay;
	}

	public void setAdvanceRepay(String advanceRepay) {
		this.advanceRepay = advanceRepay;
	}
	
	@Length(min=0, max=255, message="提前还款违约费用长度必须介于 0 和 255 之间")
	public String getBreakFee() {
		return breakFee;
	}

	public void setBreakFee(String breakFee) {
		this.breakFee = breakFee;
	}
	
	@Length(min=0, max=255, message="滞纳金长度必须介于 0 和 255 之间")
	public String getLateFee() {
		return lateFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}
	
	@Length(min=0, max=255, message="费率优惠率长度必须介于 0 和 255 之间")
	public String getRateDiscount() {
		return rateDiscount;
	}

	public void setRateDiscount(String rateDiscount) {
		this.rateDiscount = rateDiscount;
	}
	
	@Length(min=0, max=255, message="运行利息减免长度必须介于 0 和 255 之间")
	public String getIflixiRedu() {
		return iflixiRedu;
	}

	public void setIflixiRedu(String iflixiRedu) {
		this.iflixiRedu = iflixiRedu;
	}
	
	@Length(min=0, max=255, message="宽限期长度必须介于 0 和 255 之间")
	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	
	@Length(min=0, max=255, message="宽限期罚息长度必须介于 0 和 255 之间")
	public String getGraceFaxi() {
		return graceFaxi;
	}

	public void setGraceFaxi(String graceFaxi) {
		this.graceFaxi = graceFaxi;
	}
	
	@Length(min=0, max=255, message="逾期罚息长度必须介于 0 和 255 之间")
	public String getYuqiFaxi() {
		return yuqiFaxi;
	}

	public void setYuqiFaxi(String yuqiFaxi) {
		this.yuqiFaxi = yuqiFaxi;
	}
	
	@Length(min=0, max=255, message="逾期罚费长度必须介于 0 和 255 之间")
	public String getYuqiFee() {
		return yuqiFee;
	}

	public void setYuqiFee(String yuqiFee) {
		this.yuqiFee = yuqiFee;
	}
	
	@Length(min=0, max=11, message="贷款方式长度必须介于 0 和 11 之间")
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
	
	
	
	@Length(min=0, max=255, message="贷款方式二级长度必须介于 0 和 255 之间")
	public String getLoanType2() {
		return loanType2;
	}

	public void setLoanType2(String loanType2) {
		this.loanType2 = loanType2;
	}
	
	@Length(min=0, max=255, message="抵押率长度必须介于 0 和 255 之间")
	public String getDiyalv() {
		return diyalv;
	}

	public void setDiyalv(String diyalv) {
		this.diyalv = diyalv;
	}
	
	@Length(min=0, max=255, message="质押率长度必须介于 0 和 255 之间")
	public String getZhiyalv() {
		return zhiyalv;
	}

	public void setZhiyalv(String zhiyalv) {
		this.zhiyalv = zhiyalv;
	}
	
	@Length(min=0, max=11, message="授信方式长度必须介于 0 和 11 之间")
	public String getCreditWay() {
		return creditWay;
	}

	public void setCreditWay(String creditWay) {
		this.creditWay = creditWay;
	}
	
	@Length(min=0, max=255, message="授信期限长度必须介于 0 和 255 之间")
	public String getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public List<ProductBizCfg> getBizCfgList() {
		return bizCfgList;
	}

	public void setBizCfgList(List<ProductBizCfg> bizCfgList) {
		this.bizCfgList = bizCfgList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getExanple_amount() {
		return exanple_amount;
	}

	public void setExanple_amount(Integer exanple_amount) {
		this.exanple_amount = exanple_amount;
	}

	public Integer getSign_amount() {
		return sign_amount;
	}

	public void setSign_amount(Integer sign_amount) {
		this.sign_amount = sign_amount;
	}

	public Integer getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(Integer loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getMinLoanTime() {
		return minLoanTime;
	}

	public void setMinLoanTime(String minLoanTime) {
		this.minLoanTime = minLoanTime;
	}

	public String getMaxLoanTime() {
		return maxLoanTime;
	}

	public void setMaxLoanTime(String maxLoanTime) {
		this.maxLoanTime = maxLoanTime;
	}
	
	
}