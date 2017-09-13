/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.entity.element;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 收费项Entity
 * @author chenh
 * @version 2016-07-01
 */
public class BiElement extends DataEntity<BiElement> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 收费项
	private BiElement element;		// 父收费项
	private String type;		// 收费项类型
	private String typeId;		// 关联业务标识
	private String organId;		// 租户ID
	
	public BiElement() {
		super();
	}

	public BiElement(String id){
		super(id);
	}

	@Length(min=1, max=255, message="收费项长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=11, message="收费项类型长度必须介于 1 和 11 之间")
	public String getType() {
		return type;
	}

	@JsonBackReference
	public BiElement getElement() {
		return element;
	}

	public void setElement(BiElement element) {
		this.element = element;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="关联业务标识长度必须介于 0 和 64 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=64, message="租户ID长度必须介于 1 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	@JsonIgnore
	public static void sortList(List<BiElement> list, List<BiElement> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			BiElement e = sourcelist.get(i);
			if (e.getElement()!=null && e.getElement().getId()!=null
					&& e.getElement().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						BiElement child = sourcelist.get(j);
						if (child.getElement()!=null && child.getElement().getId()!=null && child.getElement().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	public String getElementId() {
		return (element != null && StringUtils.isNotEmpty(element.getId())) ? element.getId() : "0";
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