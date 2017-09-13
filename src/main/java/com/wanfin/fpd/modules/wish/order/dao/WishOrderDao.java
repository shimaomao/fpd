/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;

/**
 * 原始订单数据DAO接口
 * @author cjp
 * @version 2017-06-27
 */
@MyBatisDao
public interface WishOrderDao extends CrudDao<WishOrder> {

	Double getSumAmount(WishOrder wishOrder);

	WishOrder getByOrderId(String merchantOrderId);

	/*void delOldDatas(WishOrder wishOrder);*/

	void unlock(WishOrder wishOrder);

	int updateOldDate(WishOrder wishOrder);
	
}