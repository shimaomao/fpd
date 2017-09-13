/**  
 * @Project fpd 
 * @Title TProductVo.java
 * @Package com.wanfin.fpd.modules.product.vo
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午4:44:00 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.product.vo;

import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;

/**
 * @Description [[_xxx_]] TProductVo类
 * @author Chenh
 * @date 2016年5月12日 下午4:44:00 
 */
public class TProductVo extends TProduct{
	/**
	 * @Description serialVersionUID属性
	 * @Fields long serialVersionUID
	 * @author Chenh
	 * @date 2016年5月12日 下午4:46:08 
	 */
	private static final long serialVersionUID = 1L;
	private String procDefName;
	private TProductBiz biz;
	private ProductBizCfg cfg;
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public TProductVo(TProductBiz biz, ProductBizCfg cfg) {
		super();
		this.biz = biz;
		this.cfg = cfg;
	}

	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public TProductVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TProductBiz getBiz() {
		return biz;
	}

	public void setBiz(TProductBiz biz) {
		this.biz = biz;
	}

	public String getProcDefName() {
		return procDefName;
	}

	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}

	public ProductBizCfg getCfg() {
		return cfg;
	}

	public void setCfg(ProductBizCfg cfg) {
		this.cfg = cfg;
	}
}
