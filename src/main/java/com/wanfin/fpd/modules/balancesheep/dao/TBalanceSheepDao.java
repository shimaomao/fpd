/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balancesheep.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;

/**
 * 资产负债表DAO接口
 * @author lx
 * @version 2016-05-17
 */
@MyBatisDao
public interface TBalanceSheepDao extends CrudDao<TBalanceSheep> {
	
}