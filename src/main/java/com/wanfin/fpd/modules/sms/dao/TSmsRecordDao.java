/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sms.entity.TSmsRecord;

/**
 * smsDAO接口
 * @author sms
 * @version 2016-07-25
 */
@MyBatisDao
public interface TSmsRecordDao extends CrudDao<TSmsRecord> {
	
}