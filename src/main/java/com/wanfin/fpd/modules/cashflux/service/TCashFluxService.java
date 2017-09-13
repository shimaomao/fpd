/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.cashflux.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.cashflux.entity.TCashFlux;
import com.wanfin.fpd.modules.cashflux.dao.TCashFluxDao;

/**
 * 现金流量分析Service
 * @author lx
 * @version 2016-05-20
 */
@Service
@Transactional(readOnly = true)
public class TCashFluxService extends CrudService<TCashFluxDao, TCashFlux> {

	public TCashFlux get(String id) {
		return super.get(id);
	}
	
	public List<TCashFlux> findList(TCashFlux tCashFlux) {
		return super.findList(tCashFlux);
	}
	
	public Page<TCashFlux> findPage(Page<TCashFlux> page, TCashFlux tCashFlux) {
		return super.findPage(page, tCashFlux);
	}
	
	@Transactional(readOnly = false)
	public void save(TCashFlux tCashFlux) {
		super.save(tCashFlux);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCashFlux tCashFlux) {
		super.delete(tCashFlux);
	}
	
}