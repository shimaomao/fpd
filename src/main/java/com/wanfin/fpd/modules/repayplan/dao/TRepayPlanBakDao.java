/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.repayplan.dao;

import java.util.HashMap;
import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlanBak;

/**
 * 还款计划DAO接口
 * @author lx
 * @version 2016-03-22
 */
@MyBatisDao
public interface TRepayPlanBakDao extends CrudDao<TRepayPlanBak> {
	
	/**
	 * @Description 根据状态选取内容，状态为NEED_PAY表示选取：未还和未结清
	 * @param tRepayPlan 还款计划
	 * @return
	 * @author srf
	 * @date 2016年3月30日 下午4:24:12
	 */
	public List<TRepayPlanBak> findListCondition(TRepayPlanBak tRepayPlan);
	
	/**
	 * @Description 根据合同ID，获取利息、本金、已还利息、已还本金总和
	 * @param tRepayPlan 还款计划
	 * @return HashMap<String, Double>
	 * @author srf
	 * @date 2016年3月30日 下午4:24:12
	 */
	public HashMap<String, Double> allRepayment(TRepayPlanBak tRepayPlan);
	
	/**
	 * 
	 * @Description 批量物理删除还款计划
	 * @param tRepayPlan 还款计划
	 * @return
	 * @author DC 
	 * @date 2016年3月29日 下午4:08:12
	 */
	public int deletePLWL(TRepayPlanBak tRepayPlan);
	
	/**
	 * @Description 批量废弃还款计划
	 * @param loanContractId	合同id（必需）
	 * @param lotNum	还款计划批号
	 * @author zzm
	 * @date 2016-6-2 下午2:52:46  
	 */
	public void inValidBatch(TRepayPlanBak tRepayPlan);
	
}