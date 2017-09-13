/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.common.entity.tpl;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 公共模板库表Entity
 * @author srf
 * @version 2017-01-16
 */
public class TplPublic extends DataEntity<TplPublic> {
	
	private static final long serialVersionUID = 1L;
	private String tplCode;		// 模板编码
	private String name;		// 模板名称
	private String ftlContent;		// 模板内容
	private String tplStatus;		// 模板状态：0默认，1使用
	private String organId;		// 租户ID
	
	public TplPublic() {
		super();
	}

	public TplPublic(String id){
		super(id);
	}

	@Length(min=0, max=50, message="模板编码长度必须介于 0 和 50 之间")
	public String getTplCode() {
		return tplCode;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}
	
	@Length(min=0, max=25, message="模板名称长度必须介于 0 和 25 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFtlContent() {
		return ftlContent;
	}

	public void setFtlContent(String ftlContent) {
		this.ftlContent = ftlContent;
	}
	
	@Length(min=0, max=10, message="模板状态：0默认，1使用长度必须介于 0 和 10 之间")
	public String getTplStatus() {
		return tplStatus;
	}

	public void setTplStatus(String tplStatus) {
		this.tplStatus = tplStatus;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}