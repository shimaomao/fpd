/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;

/**
 * 通知获取订单信息DAO接口
 * @author srf
 * @version 2017-06-30
 */
@MyBatisDao
public interface InformOrderDao extends CrudDao<InformOrder> {

	InformOrder getByCondition(InformOrder informOrder);
	
}