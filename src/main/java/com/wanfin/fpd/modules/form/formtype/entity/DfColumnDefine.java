/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.formtype.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 字段库维护Entity
 * @author lx
 * @version 2016-05-05
 */
public class DfColumnDefine extends DataEntity<DfColumnDefine> {
	
	private static final long serialVersionUID = 1L;
	private String category;		// 产品模块标识（字段所属模块）
	private String categoryname;		// 字段所属模块名称
	private String filed;		// 字段名称（对应实体类属性名）
	private String colname;		// 字段名称（对应数据表字段）
	private String type;		// 字段存储类型
	private String formtype;		// 字段的表单类型
	private String formurl;		// 字段的表单请求数据
	private String name;		// name
	private String filed2;	//标签name
	private String dataType;	//表单数据格式(标签属性)，用于校验 （equalTo="" accept="" maxlength="" minlength="" rangelength="" range="" max="" min=""）
	private String dataType2;	//表单数据格式(标签属性)，用于校验 （equalTo="" accept="" maxlength="" minlength="" rangelength="" range="" max="" min=""）
	private String suffix;		//后缀，如：元、年、月、日、%
	
	public DfColumnDefine() {
		super();
	}

	public DfColumnDefine(String id){
		super(id);
	}

	@Length(min=0, max=50, message="产品模块标识（字段所属模块）长度必须介于 0 和 50 之间")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Length(min=0, max=50, message="字段所属模块名称长度必须介于 0 和 50 之间")
	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	@Length(min=0, max=50, message="字段名称（对应实体类属性名）长度必须介于 0 和 50 之间")
	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}
	
	@Length(min=0, max=50, message="字段名称（对应数据表字段）长度必须介于 0 和 50 之间")
	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}
	
	@Length(min=0, max=50, message="字段存储类型长度必须介于 0 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=50, message="字段的表单类型长度必须介于 0 和 50 之间")
	public String getFormtype() {
		return formtype;
	}

	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}
	
	@Length(min=0, max=255, message="字段的表单请求数据长度必须介于 0 和 255 之间")
	public String getFormurl() {
		return formurl;
	}

	public void setFormurl(String formurl) {
		this.formurl = formurl;
	}
	
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFiled2() {
		return filed2;
	}

	public void setFiled2(String filed2) {
		this.filed2 = filed2;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType2() {
		return dataType2;
	}

	public void setDataType2(String dataType2) {
		this.dataType2 = dataType2;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}