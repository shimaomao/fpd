/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tfinancialproduct.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 理财产品Entity
 * @author lx
 * @version 2016-11-14
 */
public class TFinancialProduct extends DataEntity<TFinancialProduct> {
	
	private static final long serialVersionUID = 1L;
	private String pid;		// 父类id（出现转让时有数据）
	private String productName;		// 产品名称
	private String productType;		// 产品类型(固收、票据)
	private String productDetail;		// 产品说明
	private BigDecimal yearConversion;		// 年化率（年收益率）
	private BigDecimal shiConversion;		// 转让之后的产品实际年化收益
	private Integer limitTime;		// 融资期限
	private String danwei;		// 期限单位
	private Integer progress;		// 融资进度
	private BigDecimal kemoney;		// 已融资金额
	private BigDecimal zrmoney;		// 转让金额
	private BigDecimal amount;		// 融资金额（项目规模）
	//private Integer releasesObje;		// 发行对象（企业、个人）
	private String releasesObje;		// 发行对象（企业、个人）
	private BigDecimal buyAmountMin;		// 起投金额
	private BigDecimal buyAmountMax;		// 最高购买金额
	private Boolean ifRansfer;		// 是否可转让
	private Integer transferLimit;		// 转让限定（从开始起息到转让限定期结束之前，不允许转让）
	private BigDecimal rateDiscount;		// 转让收费费率
	private String fundRecordId;		// 互助基金id
	private String repayType;		// 回款方式
	private String rzstatus;		// 融资状态：1:募集中,2：已流标,3：已满标, 4： 还款中,5：  转让中 ,6： 已转让 , 7： 已完成
	private String status;		// 产品状态:"0": 未审核,"1":未通过审核 , "2": 通过审核 ,  "3": 发布上线, "4": 已下架 ,  "pass W端审核通过，nopass W端审核不通过 " ',
	private Date endDate;		// 购买截止日期
	private Date publishTime;		// 产品发布时间
	private Date fullScaleDate;		// 满标日期
	private Date loanDate;		// 放款日期(等于起息日期，什么时候放款，什么时候开始计息)
	private Date startRepayDate;		// 起息时间
	private String organName;		// 发行方（信贷公司名称）
	private String anuthenuserId;	//卖家认证ID
	private Boolean ifDiscount;//是否允许折价转让
	
	private BigDecimal yuqiFree;//逾期费率
	
	private String loancontractIds;//关联的债权信息id（字段不持久化到数据库   lx）
	private String yesOrNo;//审核是否通过（字段不持久化到数据库   lx）
	private long materialTotal;//#4498资料总数
	private long materialDeal; //#4498资料已经处理的数量
	
	private List contractids = new ArrayList();
	
	public TFinancialProduct() {
		super();
	}

	public TFinancialProduct(String id){
		super(id);
	}

	@Length(min=0, max=50, message="父类id（出现转让时有数据）长度必须介于 0 和 50 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=50, message="产品名称长度必须介于 0 和 50 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=50, message="产品类型(固收、票据)长度必须介于 0 和 50 之间")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	
	public BigDecimal getYearConversion() {
		return yearConversion;
	}

	public void setYearConversion(BigDecimal yearConversion) {
		this.yearConversion = yearConversion;
	}
	
	public BigDecimal getShiConversion() {
		return shiConversion;
	}

	public void setShiConversion(BigDecimal shiConversion) {
		this.shiConversion = shiConversion;
	}
	
	public Integer getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}
	
	@Length(min=0, max=10, message="期限单位长度必须介于 0 和 10 之间")
	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	
	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public BigDecimal getKemoney() {
		return kemoney;
	}

	public void setKemoney(BigDecimal kemoney) {
		this.kemoney = kemoney;
	}
	
	public BigDecimal getZrmoney() {
		return zrmoney;
	}

	public void setZrmoney(BigDecimal zrmoney) {
		this.zrmoney = zrmoney;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getReleasesObje() {
		return releasesObje;
	}

	public void setReleasesObje(String releasesObje) {
		this.releasesObje = releasesObje;
	}
	
	public BigDecimal getBuyAmountMin() {
		return buyAmountMin;
	}

	public void setBuyAmountMin(BigDecimal buyAmountMin) {
		this.buyAmountMin = buyAmountMin;
	}
	
	public BigDecimal getBuyAmountMax() {
		return buyAmountMax;
	}

	public void setBuyAmountMax(BigDecimal buyAmountMax) {
		this.buyAmountMax = buyAmountMax;
	}
	
	public Boolean getIfRansfer() {
		return ifRansfer;
	}

	public void setIfRansfer(Boolean ifRansfer) {
		this.ifRansfer = ifRansfer;
	}
	
	public Integer getTransferLimit() {
		return transferLimit;
	}

	public void setTransferLimit(Integer transferLimit) {
		this.transferLimit = transferLimit;
	}
	
	public BigDecimal getRateDiscount() {
		return rateDiscount;
	}

	public void setRateDiscount(BigDecimal rateDiscount) {
		this.rateDiscount = rateDiscount;
	}
	
	@Length(min=0, max=50, message="互助基金id长度必须介于 0 和 50 之间")
	public String getFundRecordId() {
		return fundRecordId;
	}

	public void setFundRecordId(String fundRecordId) {
		this.fundRecordId = fundRecordId;
	}
	
	@Length(min=0, max=20, message="回款方式长度必须介于 0 和 20 之间")
	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	
	@Length(min=0, max=50, message="融资状态：1:募集中,2：已流标,3：已满标, 4： 还款中,5：  转让中 ,6： 已转让 , 7： 已完成长度必须介于 0 和 50 之间")
	public String getRzstatus() {
		return rzstatus;
	}

	public void setRzstatus(String rzstatus) {
		this.rzstatus = rzstatus;
	}
	
	@Length(min=0, max=50, message="产品状态:长度必须介于 0 和 50 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFullScaleDate() {
		return fullScaleDate;
	}

	public void setFullScaleDate(Date fullScaleDate) {
		this.fullScaleDate = fullScaleDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartRepayDate() {
		return startRepayDate;
	}

	public void setStartRepayDate(Date startRepayDate) {
		this.startRepayDate = startRepayDate;
	}
	
	@Length(min=1, max=50, message="租户ID(发行方id)长度必须介于 1 和 50 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	@Length(min=0, max=50, message="发行方（信贷公司名称）长度必须介于 0 和 50 之间")
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getLoancontractIds() {
		return loancontractIds;
	}

	public void setLoancontractIds(String loancontractIds) {
		this.loancontractIds = loancontractIds;
	}

	public String getYesOrNo() {
		return yesOrNo;
	}

	public void setYesOrNo(String yesOrNo) {
		this.yesOrNo = yesOrNo;
	}

	public String getAnuthenuserId() {
		return anuthenuserId;
	}

	public void setAnuthenuserId(String anuthenuserId) {
		this.anuthenuserId = anuthenuserId;
	}

	public Boolean getIfDiscount() {
		return ifDiscount;
	}

	public void setIfDiscount(Boolean ifDiscount) {
		this.ifDiscount = ifDiscount;
	}

	public List getContractids() {
		return contractids;
	}

	public void setContractids(List contractids) {
		this.contractids = contractids;
	}

	public BigDecimal getYuqiFree() {
		return yuqiFree;
	}

	public void setYuqiFree(BigDecimal yuqiFree) {
		this.yuqiFree = yuqiFree;
	}

	public long getMaterialTotal() {
		return materialTotal;
	}

	public void setMaterialTotal(long materialTotal) {
		this.materialTotal = materialTotal;
	}

	public long getMaterialDeal() {
		return materialDeal;
	}

	public void setMaterialDeal(long materialDeal) {
		this.materialDeal = materialDeal;
	}
	public List<String> getReleasesObjeList() {
		List<String> list = Lists.newArrayList();
		if (releasesObje != null){
			for (String s : StringUtils.split(releasesObje, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	
	public void setReleasesObjeList(List<String> list) {
		releasesObje= ","+StringUtils.join(list, ",")+",";
	}
	
}