/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.dao.price;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;

/**
 * 收费单价DAO接口
 * @author chenh
 * @version 2016-07-01
 */
@MyBatisDao
public interface BiPriceDao extends CrudDao<BiPrice> {
	
}