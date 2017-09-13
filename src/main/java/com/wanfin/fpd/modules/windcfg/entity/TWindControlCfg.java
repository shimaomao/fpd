/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.windcfg.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.wind.entity.TWindControl;

/**
 * 风控配置Entity
 * @author chenh
 * @version 2016-05-26
 */
public class TWindControlCfg extends DataEntity<TWindControlCfg> {
	
	private static final long serialVersionUID = 1L;
	private TWindControl wind;		// 风控
	private TProduct product;		// 产品
	private String organId;		// 租户ID
	
	public TWindControlCfg() {
		super();
	}

	public TWindControlCfg(String id){
		super(id);
	}

	public TWindControl getWind() {
		return wind;
	}

	public void setWind(TWindControl wind) {
		this.wind = wind;
	}
	
	@Length(min=0, max=64, message="产品长度必须介于 0 和 64 之间")
	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}