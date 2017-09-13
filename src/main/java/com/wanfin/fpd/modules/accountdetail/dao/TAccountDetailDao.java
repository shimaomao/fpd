/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountdetail.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.accountdetail.entity.TAccountDetail;

/**
 * 账目明细DAO接口
 * @author lx
 * @version 2016-05-13
 */
@MyBatisDao
public interface TAccountDetailDao extends CrudDao<TAccountDetail> {
	
}