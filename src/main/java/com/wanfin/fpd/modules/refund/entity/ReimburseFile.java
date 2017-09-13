/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 申请退款上传文件Entity
 * @author srf
 * @version 2016-04-06
 */
public class ReimburseFile extends DataEntity<ReimburseFile> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 上传后的文件名
	private String sourceName;		// 源文件名
	private String title;		// 文件标题
	private String filePath;		// 服务器文件路径
	private String fileType;		// 文件类型(图片、记事本、office文档、PDF、音频、视频等)
	private String reimburseId;		// 文件对申请还款id
	private Date uploadDate;		// 附件上传的日期
	private String organId;		// 租户ID
	
	public ReimburseFile() {
		super();
	}

	public ReimburseFile(String id){
		super(id);
	}

	@Length(min=0, max=255, message="上传后的文件名长度必须介于 0 和 255 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=255, message="源文件名长度必须介于 0 和 255 之间")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	@Length(min=0, max=255, message="文件标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="服务器文件路径长度必须介于 0 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=255, message="文件类型(图片、记事本、office文档、PDF、音频、视频等)长度必须介于 0 和 255 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=64, message="文件对申请还款id长度必须介于 0 和 64 之间")
	public String getReimburseId() {
		return reimburseId;
	}

	public void setReimburseId(String reimburseId) {
		this.reimburseId = reimburseId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}