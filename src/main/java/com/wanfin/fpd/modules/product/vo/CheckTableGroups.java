/**  
 * @Project fpd 
 * @Title CheckTableGroups.java
 * @Package com.wanfin.fpd.modules.product.vo
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月23日 下午3:56:56 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.product.vo;

import java.util.List;

import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;

/**
 * @Description [[_xxx_]] CheckTableGroups类
 * @author Chenh
 * @date 2016年5月23日 下午3:56:56 
 */
public class CheckTableGroups {
	private String title;
	private Integer size;
	private Integer gsize;
	private List<TProductBiz> rows;
	public String getTitle() {
		return title;
	}
	public Integer getSize() {
		if ((this.getRows() != null) && (this.getRows().size() > 0)) {
			this.size = this.getRows().size();
		}
		return this.size;
	}
	public Integer getGsize() {
		if (this.getRows() != null) {
			this.gsize = 0;
			for (TProductBiz tProductBiz : this.getRows()) {
				if((tProductBiz.getChildrens() != null) && (tProductBiz.getChildrens().size() > 0)){
					this.gsize += tProductBiz.getChildrens().size();
				}else{
					this.gsize += 1;
				}
			}
		}
		System.out.println(this.gsize);
		return this.gsize;
	}
	public List<TProductBiz> getRows() {
		return rows;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setGsize(Integer gsize) {
		this.gsize = gsize;
	}
	public void setRows(List<TProductBiz> rows) {
		this.rows = rows;
	}
}