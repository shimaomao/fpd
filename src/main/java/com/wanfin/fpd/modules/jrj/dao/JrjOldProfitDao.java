/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.jrj.entity.JrjOldProfit;

/**
 * 利润表表DAO接口
 * @author xzt
 * @version 2016-11-13
 */
@MyBatisDao
public interface JrjOldProfitDao extends CrudDao<JrjOldProfit> {
	
	List<JrjOldProfit> findListByScanFlag(JrjOldProfit jrjProfit);
	
	int updateByPushStatus(JrjOldProfit entity);
	
	List<JrjOldProfit> findListBySubmitDate(JrjOldProfit jrjProfit);
}