/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balancesheep.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 资产负债表Service
 * @author lx
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class TBalanceSheepService extends CrudService<TBalanceSheepDao, TBalanceSheep> {

	public TBalanceSheep get(String id) {
		return super.get(id);
	}
	
	public List<TBalanceSheep> findList(TBalanceSheep tBalanceSheep) {
		return super.findList(tBalanceSheep);
	}
	
	public Page<TBalanceSheep> findPage(Page<TBalanceSheep> page, TBalanceSheep tBalanceSheep) {
		return super.findPage(page, tBalanceSheep);
	}
	
	@Transactional(readOnly = false)
	public void save(TBalanceSheep tBalanceSheep) {
		super.save(tBalanceSheep);
	}
	
	@Transactional(readOnly = false)
	public void delete(TBalanceSheep tBalanceSheep) {
		super.delete(tBalanceSheep);
	}
	
}