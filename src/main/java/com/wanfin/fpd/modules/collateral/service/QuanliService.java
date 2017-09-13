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
import com.wanfin.fpd.modules.collateral.dao.QuanliDao;
import com.wanfin.fpd.modules.collateral.entity.Quanli;

/**
 * 无形权力Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class QuanliService extends CrudService<QuanliDao, Quanli> {
	@Autowired
	protected QuanliDao QuanliDao;
	
	public Quanli get(String id) {
		return super.get(id);
	}
	
	public Quanli getByPledge(String id) {
		return QuanliDao.getByPledge(id);
	}
	
	public List<Quanli> findList(Quanli quanli) {
		return super.findList(quanli);
	}
	
	public Page<Quanli> findPage(Page<Quanli> page, Quanli quanli) {
		return super.findPage(page, quanli);
	}
	
	@Transactional(readOnly = false)
	public void save(Quanli quanli) {
		super.save(quanli);
	}
	
	@Transactional(readOnly = false)
	public void delete(Quanli quanli) {
		super.delete(quanli);
	}
	
}