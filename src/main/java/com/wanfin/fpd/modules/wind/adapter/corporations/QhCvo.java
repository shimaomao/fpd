/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.corporations;

import java.io.Serializable;

import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;

/**
 * 前海征信机构查询输入参数Entity
 * @author srf
 * @version 2016-06-12
 */
public class QhCvo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String token;		// 用户token
	private Office office;		// 部门ID
	
	public QhCvo() {
		super();
	}

	public QhCvo(TCompany company, IeAdapter ieAdapter) {
		super();
		if(company != null){
//			if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATESF)){
//				this.idType = "0";//默认身份证
//				this.idNo = employee.getCardNum();//身份证号码
//				this.name = employee.getName();//名称
//				this.mobileNo = employee.getMobile();//手机号
//				this.reasonCode = "99";
//			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATEDZ)){
//				this.idType = "0";//默认身份证
//				this.idNo = employee.getCardNum();//身份证号码
//				this.name = employee.getName();//名称
//				this.mobileNo = employee.getMobile();//手机号
//				this.address = employee.getHouseholdRegAddr();
//				this.reasonCode = "99";
//			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATEWORK)){
//				this.idType = "0";//默认身份证
//				this.idNo = employee.getCardNum();//身份证号码
//				this.name = employee.getName();//名称
//				this.mobileNo = employee.getMobile();//手机号
//				this.reasonCode = "99";
//			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATEWORK)){
//				this.idType = "0";//默认身份证
//				this.idNo = employee.getCardNum();//身份证号码
//				this.name = employee.getName();//名称
//				this.mobileNo = employee.getMobile();//手机号
//				this.reasonCode = "99";
//			}
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
}