/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balanceprofit.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balanceprofit.entity.TBalanceProfit;

/**
 * 利润分析DAO接口
 * @author lx
 * @version 2016-05-18
 */
@MyBatisDao
public interface TBalanceProfitDao extends CrudDao<TBalanceProfit> {
	
}