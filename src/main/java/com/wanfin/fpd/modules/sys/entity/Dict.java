/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 字典Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@ApiModel
public class Dict extends ApiEntity<Dict> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="数据值", dataType="value", notes="数据值", hidden=false, required=true)
	private String value;	// 数据值
	@ApiModelProperty(value="标签名", dataType="label", notes="标签名", hidden=false, required=true)
	private String label;	// 标签名
	@ApiModelProperty(value="类型", dataType="type", notes="类型", hidden=false, required=true)
	private String type;	// 类型
	@ApiModelProperty(value="描述", dataType="description", notes="描述", hidden=false, required=true)
	private String description;// 描述
	@ApiModelProperty(value="排序", dataType="Integer", notes="排序", hidden=false, required=true)
	private Integer sort;	// 排序
	private String parentId;//父Id

	public Dict() {
		super();
	}
	
	public Dict(String id){
		super(id);
	}
	
	public Dict(String value, String label){
		this.value = value;
		this.label = label;
	}
	
	@XmlAttribute
	@Length(min=1, max=100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute
	@Length(min=1, max=100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min=1, max=100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	@Length(min=0, max=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Length(min=1, max=100)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public String toString() {
		return label;
	}
}