/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.hastenrepay.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.hastenrepay.entity.HastenRepayRecord;

/**
 * 催收记录DAO接口
 * @author zzm
 * @version 2016-06-08
 */
@MyBatisDao
public interface HastenRepayRecordDao extends CrudDao<HastenRepayRecord> {
	
}