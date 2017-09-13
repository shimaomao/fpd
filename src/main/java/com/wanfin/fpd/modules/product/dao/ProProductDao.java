/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.product.entity.ProProduct;

/**
 * pro_productDAO接口
 * @author srf
 * @version 2016-12-16
 */
@MyBatisDao
public interface ProProductDao extends CrudDao<ProProduct> {
	
}