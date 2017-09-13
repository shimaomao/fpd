/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.entity.tpl;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 合同模板Entity
 * @author chenh
 * @version 2016-03-30
 */
public class TContractTpl extends DataEntity<TContractTpl> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 合同类型
	private String formId;		// 表单ID
	private String formName;		// 表单名
	private String createName;		// 创建名
	private String ftlContent;		// 内容描述
	private String ftlStatus;		// 状态
	private String ftlVersion;		// 版本
	private String ftlWordUrl;		// 上传word布局模板的URL
	private String organId;		// 租户ID
	private String crosswise;//是否横向，1位横向
	
	public TContractTpl() {
		super();
	}

	public TContractTpl(String id){
		super(id);
	}

	@Length(min=1, max=11, message="合同类型长度必须介于 1 和 11 之间")
	public String getType() {
		return type;
	}

	
	public String getCrosswise() {
		return crosswise;
	}

	public void setCrosswise(String crosswise) {
		this.crosswise = crosswise;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="表单ID长度必须介于 0 和 64 之间")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=0, max=100, message="表单名长度必须介于 0 和 100 之间")
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	@Length(min=0, max=32, message="创建名长度必须介于 0 和 32 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	public String getFtlContent() {
		return ftlContent;
	}

	public void setFtlContent(String ftlContent) {
		this.ftlContent = ftlContent;
	}
	
	@Length(min=0, max=50, message="状态长度必须介于 0 和 50 之间")
	public String getFtlStatus() {
		return ftlStatus;
	}

	public void setFtlStatus(String ftlStatus) {
		this.ftlStatus = ftlStatus;
	}
	
	@Length(min=0, max=11, message="版本长度必须介于 0 和 11 之间")
	public String getFtlVersion() {
		return ftlVersion;
	}

	public void setFtlVersion(String ftlVersion) {
		this.ftlVersion = ftlVersion;
	}
	
	@Length(min=0, max=200, message="上传word布局模板的URL长度必须介于 0 和 200 之间")
	public String getFtlWordUrl() {
		return ftlWordUrl;
	}

	public void setFtlWordUrl(String ftlWordUrl) {
		this.ftlWordUrl = ftlWordUrl;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}