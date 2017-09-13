/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wind.entity.TWindControl;

/**
 * 风控模型DAO接口
 * @author lx
 * @version 2016-05-03
 */
@MyBatisDao
public interface TWindControlDao extends CrudDao<TWindControl> {
	
	/**
	 * 根据Ids查询数据列表，
	 * @param entity
	 * @return
	 */
	List<TWindControl> findListByids(String id);
}