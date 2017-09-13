/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.dao.fivelevel;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;

/**
 * 五级分类DAO接口
 * @author srf
 * @version 2016-04-14
 */
@MyBatisDao
public interface FiveLevelDao extends CrudDao<FiveLevel> {
	public int updateStatus(FiveLevel fiveLevel);
	
	public FiveLevel getByProcInsId(String  procInsId);

	public int findSizeByStatus(FiveLevel fiveLevel);
}