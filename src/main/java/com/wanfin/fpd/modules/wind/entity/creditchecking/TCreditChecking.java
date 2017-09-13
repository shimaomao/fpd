/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.entity.creditchecking;

import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;

/**
 * 征信Entity
 * @author chenh
 * @version 2016-05-30
 */
public class TCreditChecking extends DataEntity<TCreditChecking>{
	
	private static final long serialVersionUID = 1L;
	private String db;		// 征信类型
	private String type;		// 征信类型
	private String typeId;		// 关联对象
	private String typeSub;		// 子类型
	private String data;		// 数据

	public TCompany company;		// 公司
	public TEmployee employee;		// 客户
	
	public TCreditChecking() {
		super();
	}

	public TCreditChecking(String id){
		super(id);
	}

	@Length(min=1, max=64, message="征信类型长度必须介于 1 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=64, message="关联对象长度必须介于 1 和 64 之间")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=1, max=64, message="子类型长度必须介于 1 和 64 之间")
	public String getTypeSub() {
		return typeSub;
	}

	public void setTypeSub(String typeSub) {
		this.typeSub = typeSub;
	}
	
	@Length(min=1, max=64, message="数据长度必须介于 1 和 64 之间")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Transactional
	public TCompany getCompany() {
		return company;
	}

	@Transactional
	public TEmployee getEmployee() {
		return employee;
	}

	@Transactional
	public void setCompany(TCompany company) {
		this.company = company;
	}

	@Transactional
	public void setEmployee(TEmployee employee) {
		this.employee = employee;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
}