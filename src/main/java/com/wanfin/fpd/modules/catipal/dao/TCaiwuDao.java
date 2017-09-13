/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;

/**
 * 小贷财务报表DAO接口
 * @author lxh
 * @version 2016-11-02
 */
@MyBatisDao
public interface TCaiwuDao extends CrudDao<TCaiwu> {
	
	List<TCaiwu> findListByscanFlag(TCaiwu tCaiwu);
	public int updateByscanFlag(TCaiwu tCaiwu);
	
}