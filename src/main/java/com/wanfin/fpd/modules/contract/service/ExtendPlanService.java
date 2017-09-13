/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.contract.dao.ExtendPlanDao;
import com.wanfin.fpd.modules.contract.entity.ExtendPlan;

/**
 * 展期还款计划Service
 * @author zzm
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class ExtendPlanService extends CrudService<ExtendPlanDao, ExtendPlan> {

	public ExtendPlan get(String id) {
		return super.get(id);
	}
	
	public List<ExtendPlan> findList(ExtendPlan extendPlan) {
		return super.findList(extendPlan);
	}
	
	/**
	 * @Description 期限内待还款的展期还款计划
	 * @param num	多少天内到还款期限
	 * @return
	 * @author zzm
	 * @date 2016-4-12 下午3:12:29  
	 */
	public List<ExtendPlan> findToDoRepayPlans(int num){
		ExtendPlan extendPlan = new ExtendPlan();
		//未还+未结清
		extendPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		//时间范围
		Date beginAccountDate = DateUtils.parseDate(DateUtils.getDate());
		extendPlan.setBeginAccountDate(beginAccountDate);
		extendPlan.setEndAccountDate(DateUtils.addDays(beginAccountDate, num));
		return findList(extendPlan);
	}
	
	public Page<ExtendPlan> findPage(Page<ExtendPlan> page, ExtendPlan extendPlan) {
		return super.findPage(page, extendPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(ExtendPlan extendPlan) {
		super.save(extendPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExtendPlan extendPlan) {
		super.delete(extendPlan);
	}
	
}