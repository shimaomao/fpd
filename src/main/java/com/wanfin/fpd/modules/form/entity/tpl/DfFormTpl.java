/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.entity.tpl;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.Office;

/**
 * 自定义表单Entity
 * @author chenh
 * @version 2016-05-04
 */
public class DfFormTpl extends DataEntity<DfFormTpl>{
	
	private static final long serialVersionUID = 1L;
	private String name;		// 表单名
	private String sname;		// 表单名称
	private String type;		// 表类型
	private String model;		// 模块
	private String modsub;		// 子模块
	private String json;		// 表单JSON
	private String originalhtml;		// 源码
	private String parsehtml;		// 转码
	private Office office;		// office_id
	private DfFormTpl parent;
	private String relId;	
	private TProduct product;	
	
	public DfFormTpl() {
		super();
	}

	public DfFormTpl(String id){
		super(id);
	}

	@Length(min=1, max=200, message="表单名长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="表单名称长度必须介于 0 和 200 之间")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Length(min=0, max=1, message="表类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="模块长度必须介于 0 和 255 之间")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Length(min=0, max=255, message="模块长度必须介于 0 和 255 之间")
	public String getModsub() {
		return modsub;
	}

	public void setModsub(String modsub) {
		this.modsub = modsub;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public String getOriginalhtml() {
		return originalhtml;
	}

	public void setOriginalhtml(String originalhtml) {
		this.originalhtml = originalhtml;
	}
	
	public String getParsehtml() {
		return parsehtml;
	}

	public void setParsehtml(String parsehtml) {
		this.parsehtml = parsehtml;
	}
	
	@NotNull(message="office_id不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public DfFormTpl getParent() {
		return parent;
	}

	public void setParent(DfFormTpl parent) {
		this.parent = parent;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
}