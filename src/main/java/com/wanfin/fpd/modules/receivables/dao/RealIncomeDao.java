/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;

/**
 * 违约金，咨询费DAO接口
 * @author srf
 * @version 2016-04-06
 */
@MyBatisDao
public interface RealIncomeDao extends CrudDao<RealIncome> {
	
}