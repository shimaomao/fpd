/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.auto.entity.TTenant;

/**
 * 租户DAO接口
 * @author Chenh
 * @version 2016-09-08
 */
@MyBatisDao
public interface TTenantDao extends CrudDao<TTenant> {
	
}