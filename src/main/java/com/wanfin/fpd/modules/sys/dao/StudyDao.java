/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.Study;

/**
 * 学习经历DAO接口
 * @author kewenxiu
 * @version 2017-03-10
 */
@MyBatisDao
public interface StudyDao extends CrudDao<Study> {
	
}