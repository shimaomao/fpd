/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;

/**
 * 产品业务节点管理Entity
 * @author zzm
 * @version 2016-05-09
 */
public class ProductBizCfg extends DataEntity<ProductBizCfg> {
	
	private static final long serialVersionUID = 1L;
	private TProduct product;		// 自定义产品
	private TProductBiz biz;		// 业务功能
	private String procDefId;		// 流程实例id
	private String riskId;		// 风控模型id
	
	//不用于保存数据
	private String procDefName;		//流程实例名称 ，
	
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public ProductBizCfg(TProductBiz biz) {
		super();
		this.biz = biz;
	}

	public ProductBizCfg() {
		super();
	}

	public ProductBizCfg(String id){
		super(id);
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}
	
	public TProductBiz getBiz() {
		return biz;
	}

	public void setBiz(TProductBiz biz) {
		this.biz = biz;
	}
	
	@Length(min=0, max=64, message="流程实例id长度必须介于 0 和 64 之间")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	@Length(min=0, max=64, message="风控模型id长度必须介于 0 和 64 之间")
	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public String getProcDefName() {
		return procDefName;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	
	
	
}