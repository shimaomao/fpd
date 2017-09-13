/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * pro_product_relEntity
 * @author srf
 * @version 2016-12-16
 */
public class ProProductRel extends DataEntity<ProProductRel> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 主产品id
	private String productManagerId;		// 理财产品id
	private String tfinancialProductId;		// tfinancial_product_id
	private String tProductId;		// 借款产品id
	
	public ProProductRel() {
		super();
	}

	public ProProductRel(String id){
		super(id);
	}

	@Length(min=0, max=155, message="主产品id长度必须介于 0 和 155 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=155, message="理财产品id长度必须介于 0 和 155 之间")
	public String getProductManagerId() {
		return productManagerId;
	}

	public void setProductManagerId(String productManagerId) {
		this.productManagerId = productManagerId;
	}
	
	@Length(min=0, max=64, message="tfinancial_product_id长度必须介于 0 和 64 之间")
	public String getTfinancialProductId() {
		return tfinancialProductId;
	}

	public void setTfinancialProductId(String tfinancialProductId) {
		this.tfinancialProductId = tfinancialProductId;
	}
	
	@Length(min=0, max=155, message="借款产品id长度必须介于 0 和 155 之间")
	public String getTProductId() {
		return tProductId;
	}

	public void setTProductId(String tProductId) {
		this.tProductId = tProductId;
	}
	
}