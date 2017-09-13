/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.looploan.dao;

import java.util.Map;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;

/**
 * 申请授信DAO接口
 * @author zzm
 * @version 2016-03-17
 */
@MyBatisDao
public interface TLoopLoanDao extends CrudDao<TLoopLoan> {
	
	public int remmoveBanding(Map<String, Object> map);
	
	public int updateBusinessId(Map<String, Object> map);
}