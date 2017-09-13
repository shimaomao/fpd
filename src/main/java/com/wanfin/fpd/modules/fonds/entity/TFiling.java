/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.fonds.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 资料归档Entity
 * @author lx
 * @version 2016-06-14
 */
public class TFiling extends DataEntity<TFiling> {
	
	private static final long serialVersionUID = 1L;
	private String fondsName;		// 全宗名称
	private String fondsNumber;		// 全宗编号
	private String fileNumber;		// 案卷目录号
	private String fileName;		// 案卷目录名称
	private String fileSheets;		// 案卷张数
	private String fileEndash;		// 目录中案卷起止号
	private String position;		// 存放位置
	private String organId;		// 机构
	private String loancontractId;   //业务合同id
	
	public TFiling() {
		super();
	}

	public TFiling(String id){
		super(id);
	}

	@Length(min=0, max=64, message="全宗名称长度必须介于 0 和 64 之间")
	public String getFondsName() {
		return fondsName;
	}

	public void setFondsName(String fondsName) {
		this.fondsName = fondsName;
	}
	
	@Length(min=0, max=64, message="全宗编号长度必须介于 0 和 64 之间")
	public String getFondsNumber() {
		return fondsNumber;
	}

	public void setFondsNumber(String fondsNumber) {
		this.fondsNumber = fondsNumber;
	}
	
	@Length(min=0, max=64, message="案卷目录号长度必须介于 0 和 64 之间")
	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	@Length(min=0, max=64, message="案卷目录名称长度必须介于 0 和 64 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=64, message="案卷张数长度必须介于 0 和 64 之间")
	public String getFileSheets() {
		return fileSheets;
	}

	public void setFileSheets(String fileSheets) {
		this.fileSheets = fileSheets;
	}
	
	@Length(min=0, max=64, message="目录中案卷起止号长度必须介于 0 和 64 之间")
	public String getFileEndash() {
		return fileEndash;
	}

	public void setFileEndash(String fileEndash) {
		this.fileEndash = fileEndash;
	}
	
	@Length(min=0, max=64, message="存放位置长度必须介于 0 和 64 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=64, message="机构长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getLoancontractId() {
		return loancontractId;
	}

	public void setLoancontractId(String loancontractId) {
		this.loancontractId = loancontractId;
	}
	
}