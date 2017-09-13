package com.wanfin.fpd.modules.dataStatistics.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.dataStatistics.dao.LoanStatisticsDao;
import com.wanfin.fpd.modules.dataStatistics.entity.LoanStatistics;

@Service
@Transactional(readOnly = true)
public class LoanStatisticsService extends CrudService<LoanStatisticsDao, LoanStatistics> {
	
	/**
	 * 获取还款计划表中的还款金额数据
	 * @return
	 */
	public List<LoanStatistics> repaymentPlanAmount(){
		LoanStatistics loanStatistics = new LoanStatistics();
		loanStatistics.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		
		return super.dao.repaymentPlanAmount(loanStatistics);
	}
}
