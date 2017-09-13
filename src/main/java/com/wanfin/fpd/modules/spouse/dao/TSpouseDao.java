/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.spouse.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;

/**
 * spouseDAO接口
 * @author spouse
 * @version 2016-07-04
 */
@MyBatisDao
public interface TSpouseDao extends CrudDao<TSpouse> {
	
}