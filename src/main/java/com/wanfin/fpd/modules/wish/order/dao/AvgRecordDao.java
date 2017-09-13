/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wish.order.entity.AvgRecord;

/**
 * 历史平均数据DAO接口
 * @author cjp
 * @version 2017-06-28
 */
@MyBatisDao
public interface AvgRecordDao extends CrudDao<AvgRecord> {
	
}