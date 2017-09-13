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
import com.wanfin.fpd.modules.collateral.dao.GongyuDao;
import com.wanfin.fpd.modules.collateral.entity.Gongyu;

/**
 * 公寓信息Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class GongyuService extends CrudService<GongyuDao, Gongyu> {

	@Autowired
	protected GongyuDao gongyuDao;
	
	public Gongyu get(String id) {
		return super.get(id);
	}
	
	public Gongyu getByPledge(String id) {
		return gongyuDao.getByPledge(id);
	}
	
	public List<Gongyu> findList(Gongyu gongyu) {
		return super.findList(gongyu);
	}
	
	public Page<Gongyu> findPage(Page<Gongyu> page, Gongyu gongyu) {
		return super.findPage(page, gongyu);
	}
	
	@Transactional(readOnly = false)
	public void save(Gongyu gongyu) {
		super.save(gongyu);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gongyu gongyu) {
		super.delete(gongyu);
	}
	
}