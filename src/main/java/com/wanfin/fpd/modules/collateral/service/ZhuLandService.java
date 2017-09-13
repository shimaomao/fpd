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
import com.wanfin.fpd.modules.collateral.dao.ZhuLandDao;
import com.wanfin.fpd.modules.collateral.entity.ZhuLand;

/**
 * 商宅用地Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class ZhuLandService extends CrudService<ZhuLandDao, ZhuLand> {

	@Autowired
	protected ZhuLandDao zhuLandDao;
	
	public ZhuLand get(String id) {
		return super.get(id);
	}
	
	public ZhuLand getByPledge(String id) {
		return zhuLandDao.getByPledge(id);
	}
	
	public List<ZhuLand> findList(ZhuLand zhuLand) {
		return super.findList(zhuLand);
	}
	
	public Page<ZhuLand> findPage(Page<ZhuLand> page, ZhuLand zhuLand) {
		return super.findPage(page, zhuLand);
	}
	
	@Transactional(readOnly = false)
	public void save(ZhuLand zhuLand) {
		super.save(zhuLand);
	}
	
	@Transactional(readOnly = false)
	public void delete(ZhuLand zhuLand) {
		super.delete(zhuLand);
	}
	
}