/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.files.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 附件管理Entity
 * @author zzm
 * @version 2016-03-21
 */
public class TContractFiles extends DataEntity<TContractFiles> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名
	private String filePath;		// 文件路径
	private String sourceName;		// 源文件名
	private String title;		// 文件标题
	private String type;		// 文件类型
	private String taskId;		// 业务id
	private String creditTaskId;  //授信id
	private int dealed;	//是否被处理过，理财马赛克处理用
	
	private String sysFileId; //sys_file 的id
	
	private String extName;//用做传参，不持久化
	
	public TContractFiles() {
		super();
	}

	public TContractFiles(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文件名长度必须介于 0 和 255 之间")
	public String getFileName() {
		return fileName;
	}

	
	public String getCreditTaskId() {
		return creditTaskId;
	}

	public void setCreditTaskId(String creditTaskId) {
		this.creditTaskId = creditTaskId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=255, message="文件路径长度必须介于 0 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="业务id长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSysFileId() {
		return sysFileId;
	}

	public void setSysFileId(String sysFileId) {
		this.sysFileId = sysFileId;
	}
	

	public int getDealed() {
		return dealed;
	}

	public void setDealed(int dealed) {
		this.dealed = dealed;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}	
	
	
}