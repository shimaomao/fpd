/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.CreditTotal;
import com.wanfin.fpd.modules.credit.dao.CreditTotalDao;

/**
 * 征信次数统计Service
 * @author cjp
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class CreditTotalService extends CrudService<CreditTotalDao, CreditTotal> {

	public CreditTotal get(String id) {
		return super.get(id);
	}
	
	public List<CreditTotal> findList(CreditTotal creditTotal) {
		return super.findList(creditTotal);
	}
	
	public Page<CreditTotal> findPage(Page<CreditTotal> page, CreditTotal creditTotal) {
		return super.findPage(page, creditTotal);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditTotal creditTotal) {
		super.save(creditTotal);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditTotal creditTotal) {
		super.delete(creditTotal);
	}
	
}