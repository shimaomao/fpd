/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balanceprofit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.balanceprofit.entity.TBalanceProfit;
import com.wanfin.fpd.modules.balanceprofit.dao.TBalanceProfitDao;

/**
 * 利润分析Service
 * @author lx
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class TBalanceProfitService extends CrudService<TBalanceProfitDao, TBalanceProfit> {

	public TBalanceProfit get(String id) {
		return super.get(id);
	}
	
	public List<TBalanceProfit> findList(TBalanceProfit tBalanceProfit) {
		return super.findList(tBalanceProfit);
	}
	
	public Page<TBalanceProfit> findPage(Page<TBalanceProfit> page, TBalanceProfit tBalanceProfit) {
		return super.findPage(page, tBalanceProfit);
	}
	
	@Transactional(readOnly = false)
	public void save(TBalanceProfit tBalanceProfit) {
		super.save(tBalanceProfit);
	}
	
	@Transactional(readOnly = false)
	public void delete(TBalanceProfit tBalanceProfit) {
		super.delete(tBalanceProfit);
	}
	
}