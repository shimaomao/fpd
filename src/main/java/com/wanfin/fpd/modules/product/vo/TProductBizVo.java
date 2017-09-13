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

import java.util.List;

import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;

/**
 * @Description [[_xxx_]] TProductVo类
 * @author Chenh
 * @date 2016年5月12日 下午4:44:00 
 */
public class TProductBizVo{
	/**
	 * @Description serialVersionUID属性
	 * @Fields long serialVersionUID
	 * @author Chenh
	 * @date 2016年5月12日 下午4:46:08 
	 */
	private TProduct product;
	private Integer dqBizSize;
	private Integer dzBizSize;
	private Integer dhBizSize;
	private Integer dqBizGSize;
	private Integer dzBizGSize;
	private Integer dhBizGSize;
	private List<TProductBiz> dqBiz;
	private List<TProductBiz> dzBiz;
	private List<TProductBiz> dhBiz;
	public TProduct getProduct() {
		return product;
	}
	public void setProduct(TProduct product) {
		this.product = product;
	}
	public List<TProductBiz> getDqBiz() {
		return dqBiz;
	}
	public void setDqBiz(List<TProductBiz> dqBiz) {
		this.dqBiz = dqBiz;
	}
	public List<TProductBiz> getDzBiz() {
		return dzBiz;
	}
	public void setDzBiz(List<TProductBiz> dzBiz) {
		this.dzBiz = dzBiz;
	}
	public List<TProductBiz> getDhBiz() {
		return dhBiz;
	}
	public void setDhBiz(List<TProductBiz> dhBiz) {
		this.dhBiz = dhBiz;
	}
	public Integer getDqBizSize() {
		if (dqBiz != null) {
			this.dqBizSize = dqBiz.size();
		}
		
		if(this.dqBizSize == null){
			this.dqBizSize = 0;
		}
		return this.dqBizSize;
	}
	public Integer getDzBizSize() {
		if (dzBiz != null) {
			this.dzBizSize = dzBiz.size();
		}

		if(this.dzBizSize == null){
			this.dzBizSize = 0;
		}
		return this.dzBizSize;
	}
	public Integer getDhBizSize() {
		if (dhBiz != null) {
			this.dhBizSize = dhBiz.size();
		}
		
		if(this.dhBizSize == null){
			this.dhBizSize = 0;
		}
		return this.dhBizSize;
	}
	
	public Integer getDqBizGSize() {
		if (this.getDqBiz() != null) {
			this.dqBizGSize = 0;
			for (TProductBiz tProductBiz : this.getDqBiz()) {
				if(tProductBiz.getChildrens() != null){
					dqBizGSize += tProductBiz.getChildrens().size();
				}
			}
		}
		return this.dqBizGSize;
	}
	public Integer getDzBizGSize() {
		if (this.getDzBiz() != null) {
			this.dzBizGSize = 0;
			for (TProductBiz tProductBiz : this.getDzBiz()) {
				if(tProductBiz.getChildrens() != null){
					dzBizGSize += tProductBiz.getChildrens().size();
				}
			}
		}
		return this.dzBizGSize;
	}
	public Integer getDhBizGSize() {
		if (this.getDhBiz() != null) {
			this.dhBizGSize = 0;
			for (TProductBiz tProductBiz : this.getDhBiz()) {
				if(tProductBiz.getChildrens() != null){
					dhBizGSize += tProductBiz.getChildrens().size();
				}
			}
		}
		return this.dhBizGSize;
	}
	public void setDqBizGSize(Integer dqBizGSize) {
		this.dqBizGSize = dqBizGSize;
	}
	public void setDzBizGSize(Integer dzBizGSize) {
		this.dzBizGSize = dzBizGSize;
	}
	public void setDhBizGSize(Integer dhBizGSize) {
		this.dhBizGSize = dhBizGSize;
	}
	public void setDqBizSize(Integer dqBizSize) {
		this.dqBizSize = dqBizSize;
	}
	public void setDzBizSize(Integer dzBizSize) {
		this.dzBizSize = dzBizSize;
	}
	public void setDhBizSize(Integer dhBizSize) {
		this.dhBizSize = dhBizSize;
	}
	
}