/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.api.wiss.entity.YlExchangeRates;

/**
 * 汇率-易联DAO接口
 * @author srf
 * @version 2017-07-05
 */
@MyBatisDao
public interface YlExchangeRatesDao extends CrudDao<YlExchangeRates> {

	YlExchangeRates getRate(YlExchangeRates ylExchangeRates);
	
}