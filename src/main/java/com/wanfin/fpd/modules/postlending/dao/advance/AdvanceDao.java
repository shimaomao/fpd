/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.dao.advance;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;

/**
 * 提前还款DAO接口
 * @author srf
 * @version 2016-04-18
 */
@MyBatisDao
public interface AdvanceDao extends CrudDao<Advance> {
	/**
	 * 更新Status
	 * @param id id
	 * @param status 新的状态值
	 * @return
	 */
	public int updateStatus(String id,String status);
}