/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.dao.achievements;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.statistics.entity.achievements.TongjiAchievements;

/**
 * 绩效所需项目DAO接口
 * @author lxh
 * @version 2017-04-20
 */
@MyBatisDao
public interface TongjiAchievementsDao extends CrudDao<TongjiAchievements> {

	List<LoanContractVo> findLoanDetailList(TongjiAchievements tongjiAchievements);
	
}