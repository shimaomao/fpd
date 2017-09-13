/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.CreditTotal;

/**
 * 征信次数统计DAO接口
 * @author cjp
 * @version 2017-05-09
 */
@MyBatisDao
public interface CreditTotalDao extends CrudDao<CreditTotal> {
	
}