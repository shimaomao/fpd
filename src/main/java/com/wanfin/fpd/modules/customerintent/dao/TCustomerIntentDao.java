/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerintent.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.customerintent.entity.TCustomerIntent;

/**
 * customerintentDAO接口
 * @author lx
 * @version 2016-08-13
 */
@MyBatisDao
public interface TCustomerIntentDao extends CrudDao<TCustomerIntent> {
	
}