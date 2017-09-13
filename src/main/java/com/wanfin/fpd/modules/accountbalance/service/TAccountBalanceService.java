/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountbalance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.accountbalance.dao.TAccountBalanceDao;
import com.wanfin.fpd.modules.accountbalance.entity.TAccountBalance;

/**
 * 科目余额Service
 * @author lx
 * @version 2016-05-16
 */
@Service
@Transactional(readOnly = true)
public class TAccountBalanceService extends CrudService<TAccountBalanceDao, TAccountBalance> {

	public TAccountBalance get(String id) {
		return super.get(id);
	}
	
	public List<TAccountBalance> findList(TAccountBalance tAccountBalance) {
		return super.findList(tAccountBalance);
	}
	
	public Page<TAccountBalance> findPage(Page<TAccountBalance> page, TAccountBalance tAccountBalance) {
		return super.findPage(page, tAccountBalance);
	}
	
	@Transactional(readOnly = false)
	public void save(TAccountBalance tAccountBalance) {
		super.save(tAccountBalance);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAccountBalance tAccountBalance) {
		super.delete(tAccountBalance);
	}
	
}