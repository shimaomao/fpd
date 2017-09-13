/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.cashflux.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.cashflux.entity.TCashFlux;

/**
 * 现金流量分析DAO接口
 * @author lx
 * @version 2016-05-20
 */
@MyBatisDao
public interface TCashFluxDao extends CrudDao<TCashFlux> {
	
}