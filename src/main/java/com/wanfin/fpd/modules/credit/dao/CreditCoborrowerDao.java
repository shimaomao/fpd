/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao;

import java.util.Map;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.CreditCoborrower;

/**
 * 客户授信_共同借款人DAO接口
 * @author srf
 * @version 2017-03-29
 */
@MyBatisDao
public interface CreditCoborrowerDao extends CrudDao<CreditCoborrower> {

	CreditCoborrower checkCreditCoborrower(CreditCoborrower creditCoborrower);

	public int updateCoborrowerId(Map<String, Object> map);
	
}