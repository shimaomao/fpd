/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;

/**
 * 回款记录DAO接口
 * @author cjp
 * @version 2017-07-07
 */
@MyBatisDao
public interface ReturnedMoneyDao extends CrudDao<ReturnedMoney> {

	List<ReturnedMoney> findListByIds(ReturnedMoney returnedMoney);
	
}