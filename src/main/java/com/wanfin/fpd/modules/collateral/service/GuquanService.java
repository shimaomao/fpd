/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.collateral.dao.GuquanDao;
import com.wanfin.fpd.modules.collateral.entity.Guquan;

/**
 * 股权Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class GuquanService extends CrudService<GuquanDao, Guquan> {

	@Autowired
	protected GuquanDao guquanDao;
	
	public Guquan get(String id) {
		return super.get(id);
	}
	
	public Guquan getByPledge(String id) {
		return guquanDao.getByPledge(id);
	}
	
	public List<Guquan> findList(Guquan guquan) {
		return super.findList(guquan);
	}
	
	public Page<Guquan> findPage(Page<Guquan> page, Guquan guquan) {
		return super.findPage(page, guquan);
	}
	
	@Transactional(readOnly = false)
	public void save(Guquan guquan) {
		super.save(guquan);
	}
	
	@Transactional(readOnly = false)
	public void delete(Guquan guquan) {
		super.delete(guquan);
	}
	
}