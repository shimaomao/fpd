/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.advisory.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.advisory.entity.TAdvisory;

/**
 * 业务受理咨询DAO接口
 * @author cdy
 * @version 2016-03-15
 */
@MyBatisDao
public interface TAdvisoryDao extends CrudDao<TAdvisory> {
	
}