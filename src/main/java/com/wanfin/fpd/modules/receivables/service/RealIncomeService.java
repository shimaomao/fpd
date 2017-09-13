/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.dao.RealIncomeDao;

/**
 * 违约金，咨询费Service
 * @author srf
 * @version 2016-04-06
 */
@Service
@Transactional(readOnly = true)
public class RealIncomeService extends CrudService<RealIncomeDao, RealIncome> {

	public RealIncome get(String id) {
		return super.get(id);
	}
	
	public List<RealIncome> findList(RealIncome realIncome) {
		return super.findList(realIncome);
	}
	
	public Page<RealIncome> findPage(Page<RealIncome> page, RealIncome realIncome) {
		return super.findPage(page, realIncome);
	}
	
	@Transactional(readOnly = false)
	public void save(RealIncome realIncome) {
		super.save(realIncome);
	}
	
	@Transactional(readOnly = false)
	public void delete(RealIncome realIncome) {
		super.delete(realIncome);
	}
	
}