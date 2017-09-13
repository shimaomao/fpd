/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.files.entity;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 附件Entity
 * @author srf
 * @version 2016-05-16
 */
public class Files extends DataEntity<Files> {
	
	private static final long serialVersionUID = 1L;
	private String relId;		// 文件对应业务id
	private String relTable;		// 文件对应库表名
	private String type;		// 文件类型、包括附件
	private String fileName;		// 上传后的文件名
	private String filePath;		// 服务器文件路径
	private String filePfix;		// 服务器文件后缀(doc、docx、txt、jpg...)
	private String sourceName;		// 源文件名
	private String title;		// 文件标题
	
	public Files() {
		super();
	}

	public Files(String id){
		super(id);
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
	
	public String getRelTable() {
		return relTable;
	}

	public void setRelTable(String relTable) {
		this.relTable = relTable;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePfix() {
		return filePfix;
	}

	public void setFilePfix(String filePfix) {
		this.filePfix = filePfix;
	}
	
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}