/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.refund.entity.Reimburse;

/**
 * 申请退款DAO接口
 * @author srf
 * @version 2016-04-06
 */
@MyBatisDao
public interface ReimburseDao extends CrudDao<Reimburse> {
	
	public List<Reimburse> getContition(Reimburse reimburse);
	
	public int updateStatus(Reimburse reimburse);
}