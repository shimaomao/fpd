/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.entity;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 易联回款通知Entity
 * @author srf
 * @version 2017-07-08
 */
public class HkInform extends DataEntity<HkInform> {
	
	private static final long serialVersionUID = 1L;
	private String filePath;		// 回款文件路径（sftp约定目录+ filePath）
	private String dealStatus;		// 处理状态信息，0新建；1已处理
	private String organId;		// 租户ID
	private String status; //回款通知状态 0新建；1通知易联；2获取到；3已处理
	
	public HkInform() {
		super();
	}

	public HkInform(String id){
		super(id);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}