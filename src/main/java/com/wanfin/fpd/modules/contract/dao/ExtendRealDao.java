/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.ExtendReal;

/**
 * 展期真实还款记录DAO接口
 * @author zzm
 * @version 2016-04-01
 */
@MyBatisDao
public interface ExtendRealDao extends CrudDao<ExtendReal> {
	
}