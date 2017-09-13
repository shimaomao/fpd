/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountbalance.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.accountbalance.entity.TAccountBalance;

/**
 * 科目余额DAO接口
 * @author lx
 * @version 2016-05-16
 */
@MyBatisDao
public interface TAccountBalanceDao extends CrudDao<TAccountBalance> {
	
}