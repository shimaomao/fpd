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
import com.wanfin.fpd.modules.collateral.dao.CunhuoDao;
import com.wanfin.fpd.modules.collateral.entity.Cunhuo;

/**
 * 存货信息Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class CunhuoService extends CrudService<CunhuoDao, Cunhuo> {

	@Autowired
	protected CunhuoDao cunhuoDao;
	
	public Cunhuo get(String id) {
		return super.get(id);
	}
	
	public Cunhuo getByPledge(String id) {
		return cunhuoDao.getByPledge(id);
	}
	
	public List<Cunhuo> findList(Cunhuo cunhuo) {
		return super.findList(cunhuo);
	}
	
	public Page<Cunhuo> findPage(Page<Cunhuo> page, Cunhuo cunhuo) {
		return super.findPage(page, cunhuo);
	}
	
	@Transactional(readOnly = false)
	public void save(Cunhuo cunhuo) {
		super.save(cunhuo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cunhuo cunhuo) {
		super.delete(cunhuo);
	}
	
}