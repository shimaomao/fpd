/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wauto.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wauto.entity.WAuto;

/**
 * 自动业务DAO接口
 * @author chenh
 * @version 2016-07-28
 */
@MyBatisDao
public interface WAutoDao extends CrudDao<WAuto> {
	
}