/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wish.merchant.entity.WishShop;

/**
 * 商户店铺信息DAO接口
 * @author cjp
 * @version 2017-07-11
 */
@MyBatisDao
public interface WishShopDao extends CrudDao<WishShop> {
	
}