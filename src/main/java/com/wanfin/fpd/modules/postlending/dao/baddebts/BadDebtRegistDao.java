/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.dao.baddebts;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.postlending.entity.baddebts.BadDebtRegist;

/**
 * 坏账处理DAO接口
 * @author srf
 * @version 2016-04-15
 */
@MyBatisDao
public interface BadDebtRegistDao extends CrudDao<BadDebtRegist> {
	public BadDebtRegist getByProcInsId(String  procInsId);

	public List<BadDebtRegist> getAuditStatus(BadDebtRegist badDebtRegist);
}