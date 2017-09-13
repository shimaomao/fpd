/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.employee.entity.CsHouse;

/**
 * 客户房产DAO接口
 * @author zzm
 * @version 2016-07-21
 */
@MyBatisDao
public interface CsHouseDao extends CrudDao<CsHouse> {
	
}