/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.entity.price;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;

/**
 * 收费单价Entity
 * @author chenh
 * @version 2016-07-01
 */
public class BiPrice extends DataEntity<BiPrice> {
	
	private static final long serialVersionUID = 1L;
	private BiElement element;		// 收费项标识
	private Double price;		// 价格
	private Integer unit;		// 单位
	private Long unitVal;		// 单位值
	private Integer type;		// 计价类型
	private String organId;		// 租户ID
	
	public BiPrice() {
		super();
	}

	public BiPrice(String id){
		super(id);
	}
	
	public BiElement getElement() {
		return element;
	}

	public void setElement(BiElement element) {
		this.element = element;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
	public Long getUnitVal() {
		return unitVal;
	}

	public void setUnitVal(Long unitVal) {
		this.unitVal = unitVal;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	@JsonIgnore
	public static void sortList(List<BiPrice> list, List<BiPrice> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			BiPrice price = sourcelist.get(i);
			BiElement e = price.getElement();
			if ((e.getElement() != null) && (e.getElement().getId() != null) && (e.getElement().getId().equals(parentId))){
				list.add(price);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						BiPrice sprice = sourcelist.get(j);
						BiElement child = sprice.getElement();
						if ((child.getElement() != null) && (child.getElement().getId() != null) && (child.getElement().getId().equals(e.getId()))){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @Description 根节点
	 * @return
	 * @author Chenh 
	 * @date 2016年5月19日 下午2:50:41  
	 */
	@JsonIgnore
	public static String getRootId(){
		return Global.ROOT_ID;
	}
}