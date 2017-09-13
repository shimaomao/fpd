/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.CreditApply;

/**
 * 授信申请DAO接口
 * @author zzm
 * @version 2016-07-13
 */
@MyBatisDao
public interface CreditApplyDao extends CrudDao<CreditApply> {

	int getCreditApplyByStatus(@Param("customerId")String customerId,@Param("status")String status);

	void deleteByCondition(CreditApply creditApply);
	
	public CreditApply getByCondition(CreditApply creditApply);
	
	CreditApply getNewestCredit(@Param("customerId")String customerId);
	
}