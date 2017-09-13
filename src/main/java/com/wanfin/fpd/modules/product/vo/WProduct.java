package com.wanfin.fpd.modules.product.vo;

import java.sql.Timestamp;

import com.wanfin.fpd.modules.product.entity.TProduct;

public class WProduct {
	private String id;
	private String agency_id;
	private String cat_id;
	private String summary;
	private String loan_name;
	private String loan_img;
	private Double loan_fee;
	private Long installment_min;
	private Long installment_max;
	private Integer repay_way;
	private Integer prepay;
	private Integer grace_period;
	private Double amount_min;
	private Double amount_max;
	private Double monthly_rate;
	private Double monthly_service_rate;
	private Double prepay_rate;
	private Double grace_period_rate;
	private Double overdue_rate;
	private Integer on_sale;
	private Integer sales;
	private Integer hot;
	private Timestamp add_time;
	private Timestamp update_time;
	private String contract_id;
	private String operate_uid;
	private TProduct product;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAgency_id() {
		return agency_id;
	}
	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLoan_name() {
		return loan_name;
	}
	public void setLoan_name(String loan_name) {
		this.loan_name = loan_name;
	}
	public String getLoan_img() {
		return loan_img;
	}
	public void setLoan_img(String loan_img) {
		this.loan_img = loan_img;
	}
	public Double getLoan_fee() {
		return loan_fee;
	}
	public void setLoan_fee(Double loan_fee) {
		this.loan_fee = loan_fee;
	}
	public Long getInstallment_min() {
		return installment_min;
	}
	public void setInstallment_min(Long installment_min) {
		this.installment_min = installment_min;
	}
	public Long getInstallment_max() {
		return installment_max;
	}
	public void setInstallment_max(Long installment_max) {
		this.installment_max = installment_max;
	}
	public Integer getRepay_way() {
		return repay_way;
	}
	public void setRepay_way(Integer repay_way) {
		this.repay_way = repay_way;
	}
	public Integer getPrepay() {
		return prepay;
	}
	public void setPrepay(Integer prepay) {
		this.prepay = prepay;
	}
	public Integer getGrace_period() {
		return grace_period;
	}
	public void setGrace_period(Integer grace_period) {
		this.grace_period = grace_period;
	}
	public Double getAmount_min() {
		return amount_min;
	}
	public void setAmount_min(Double amount_min) {
		this.amount_min = amount_min;
	}
	public Double getAmount_max() {
		return amount_max;
	}
	public void setAmount_max(Double amount_max) {
		this.amount_max = amount_max;
	}
	public Double getMonthly_rate() {
		return monthly_rate;
	}
	public void setMonthly_rate(Double monthly_rate) {
		this.monthly_rate = monthly_rate;
	}
	public Double getMonthly_service_rate() {
		return monthly_service_rate;
	}
	public void setMonthly_service_rate(Double monthly_service_rate) {
		this.monthly_service_rate = monthly_service_rate;
	}
	public Double getPrepay_rate() {
		return prepay_rate;
	}
	public void setPrepay_rate(Double prepay_rate) {
		this.prepay_rate = prepay_rate;
	}
	public Double getGrace_period_rate() {
		return grace_period_rate;
	}
	public void setGrace_period_rate(Double grace_period_rate) {
		this.grace_period_rate = grace_period_rate;
	}
	public Double getOverdue_rate() {
		return overdue_rate;
	}
	public void setOverdue_rate(Double overdue_rate) {
		this.overdue_rate = overdue_rate;
	}
	public Integer getOn_sale() {
		return on_sale;
	}
	public void setOn_sale(Integer on_sale) {
		this.on_sale = on_sale;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getOperate_uid() {
		return operate_uid;
	}
	public void setOperate_uid(String operate_uid) {
		this.operate_uid = operate_uid;
	}
	public TProduct getProduct() {
		return product;
	}
	public void setProduct(TProduct product) {
		this.product = product;
	}
}
