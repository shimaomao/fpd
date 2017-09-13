/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao;


import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;

/**
 * 客户授信额度DAO接口
 * @author zzm
 * @version 2016-07-13
 */
@MyBatisDao
public interface CustomerCreditDao extends CrudDao<CustomerCredit> {

	void deleteByCondition(CustomerCredit customerCredit);

	CustomerCredit getByCustorId(String customerId);
	
}