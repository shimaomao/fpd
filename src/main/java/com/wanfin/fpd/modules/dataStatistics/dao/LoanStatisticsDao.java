package com.wanfin.fpd.modules.dataStatistics.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.dataStatistics.entity.LoanStatistics;

@MyBatisDao
public interface LoanStatisticsDao extends CrudDao<LoanStatistics> {

	/**
	 * 获取还款计划表中的还款金额数据
	 * @return
	 */
	public List<LoanStatistics> repaymentPlanAmount(LoanStatistics loanStatistics);
}
