/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lettertpl.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 函件模板Entity
 * @author zzm
 * @version 2016-06-08
 */
public class LetterTpl extends DataEntity<LetterTpl> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 模板名称
	private String type;		// 函件类型
	private String content;		// 内容描述
	private String status;		// 状态
	private String version;		// 版本
	private String wordUrl;		// 上传word布局模板的URL
	private String organId;		// 租户ID
	
	public LetterTpl() {
		super();
	}

	public LetterTpl(String id){
		super(id);
	}

	@Length(min=0, max=50, message="模板名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="函件类型长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=50, message="状态长度必须介于 0 和 50 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=11, message="版本长度必须介于 0 和 11 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=200, message="上传word布局模板的URL长度必须介于 0 和 200 之间")
	public String getWordUrl() {
		return wordUrl;
	}

	public void setWordUrl(String wordUrl) {
		this.wordUrl = wordUrl;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}