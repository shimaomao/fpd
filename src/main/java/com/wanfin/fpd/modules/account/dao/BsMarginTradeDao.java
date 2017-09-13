/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.account.entity.BsMarginTrade;

/**
 * 保证金记录表DAO接口
 * @author srf
 * @version 2017-01-03
 */
@MyBatisDao
public interface BsMarginTradeDao extends CrudDao<BsMarginTrade> {
	public BsMarginTrade getEntityId(BsMarginTrade bsMarginTrade);
}