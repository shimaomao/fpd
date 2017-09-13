/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.fonds.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.fonds.entity.TFiling;

/**
 * 资料归档DAO接口
 * @author lx
 * @version 2016-06-14
 */
@MyBatisDao
public interface TFilingDao extends CrudDao<TFiling> {
	
}