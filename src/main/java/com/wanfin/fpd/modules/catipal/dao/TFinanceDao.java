/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.catipal.entity.TFinance;

/**
 * 融资列表DAO接口
 * @author lx
 * @version 2016-05-09
 */
@MyBatisDao
public interface TFinanceDao extends CrudDao<TFinance> {
	
}