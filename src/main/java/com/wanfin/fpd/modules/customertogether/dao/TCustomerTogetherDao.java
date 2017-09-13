/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customertogether.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.customertogether.entity.TCustomerTogether;

/**
 * 共同借款DAO接口
 * @author lx
 * @version 2016-08-22
 */
@MyBatisDao
public interface TCustomerTogetherDao extends CrudDao<TCustomerTogether> {
	
}