/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.dao.usetracking;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.postlending.entity.usetracking.ActualPurpose;

/**
 * 用途跟踪DAO接口
 * @author srf
 * @version 2016-04-09
 */
@MyBatisDao
public interface ActualPurposeDao extends CrudDao<ActualPurpose> {
	public int updateStatus(ActualPurpose actualPurpose);
	
	public ActualPurpose getByProcInsId(String  procInsId);
}