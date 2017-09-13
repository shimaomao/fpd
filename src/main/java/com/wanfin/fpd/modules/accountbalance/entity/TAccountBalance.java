/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountbalance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 科目余额Entity
 * @author lx
 * @version 2016-05-16
 */
public class TAccountBalance extends DataEntity<TAccountBalance> {
	
	private static final long serialVersionUID = 1L;
	private TAccountBalance parent;		// 上级id
	private String parentIds;		// 上级ids
	private String subjectNumber;		// 客户编号
	private String subjectName;		// 科目名称
	private String level;		// 级数
	private String detail;		// 明细
	private String beginBalance;		// 期初金额
	private String beginDirec;		// 期初方向
	private String currentDebit;		// 借方
	private String currentLender;		// 贷方
	private String currentDirec;		// 当前方向
	private String currentBalance;		// 当前金额
	private String organId;		// 租户ID
	private String createTime;//程序暂时使用，不持久化到数据库
	
	public TAccountBalance() {
		super();
	}

	public TAccountBalance(String id){
		super(id);
	}

	@JsonBackReference
	public TAccountBalance getParent() {
		return parent;
	}

	public void setParent(TAccountBalance parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="上级ids长度必须介于 0 和 64 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="客户编号长度必须介于 0 和 64 之间")
	public String getSubjectNumber() {
		return subjectNumber;
	}

	public void setSubjectNumber(String subjectNumber) {
		this.subjectNumber = subjectNumber;
	}
	
	@Length(min=0, max=64, message="科目名称长度必须介于 0 和 64 之间")
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	@Length(min=0, max=19, message="级数长度必须介于 0 和 19 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=64, message="明细长度必须介于 0 和 64 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Length(min=0, max=19, message="期初金额长度必须介于 0 和 19 之间")
	public String getBeginBalance() {
		return beginBalance;
	}

	public void setBeginBalance(String beginBalance) {
		this.beginBalance = beginBalance;
	}
	
	@Length(min=0, max=19, message="期初方向长度必须介于 0 和 19 之间")
	public String getBeginDirec() {
		return beginDirec;
	}

	public void setBeginDirec(String beginDirec) {
		this.beginDirec = beginDirec;
	}
	
	@Length(min=0, max=64, message="借方长度必须介于 0 和 64 之间")
	public String getCurrentDebit() {
		return currentDebit;
	}

	public void setCurrentDebit(String currentDebit) {
		this.currentDebit = currentDebit;
	}
	
	@Length(min=0, max=64, message="贷方长度必须介于 0 和 64 之间")
	public String getCurrentLender() {
		return currentLender;
	}

	public void setCurrentLender(String currentLender) {
		this.currentLender = currentLender;
	}
	
	@Length(min=0, max=64, message="当前方向长度必须介于 0 和 64 之间")
	public String getCurrentDirec() {
		return currentDirec;
	}

	public void setCurrentDirec(String currentDirec) {
		this.currentDirec = currentDirec;
	}
	
	@Length(min=0, max=255, message="当前金额长度必须介于 0 和 255 之间")
	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	@Length(min=0, max=64, message="租户ID长度必须介于 0 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}