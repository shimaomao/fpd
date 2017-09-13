/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 风控模型Entity
 * @author lx
 * @version 2016-05-03
 */
public class TWindControl extends DataEntity<TWindControl> {
	
	private static final long serialVersionUID = 1L;
	protected String name;		// 模型名称
	protected String url;		// 模型路径
	protected String type;		// 模型类别
	protected String param;		// 模型参数
	protected String status;		// 状态
	protected Date createTime;		// 创建时间
	protected Date updateTime;		// 更新时间
	protected String organId;		// 租户编号
	
	public List<String> getIds(){
		if(StringUtils.isEmpty(getId())){
			 return null;
		}else{
			return Arrays.asList(getId().split(","));
		}
	}
	
	public TWindControl() {
		super();
	}

	public TWindControl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="模型名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="模型路径长度必须介于 0 和 64 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=64, message="模型类别长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="模型参数长度必须介于 0 和 64 之间")
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=64, message="租户编号长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}