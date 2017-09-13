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
import com.wanfin.fpd.modules.collateral.dao.GongLandDao;
import com.wanfin.fpd.modules.collateral.entity.GongLand;

/**
 * 工业用地信息Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class GongLandService extends CrudService<GongLandDao, GongLand> {

	@Autowired
	protected GongLandDao gongLandDao;
	
	public GongLand get(String id) {
		return super.get(id);
	}
	
	public GongLand getByPledge(String id) {
		return gongLandDao.getByPledge(id);
	}
	
	public List<GongLand> findList(GongLand gongLand) {
		return super.findList(gongLand);
	}
	
	public Page<GongLand> findPage(Page<GongLand> page, GongLand gongLand) {
		return super.findPage(page, gongLand);
	}
	
	@Transactional(readOnly = false)
	public void save(GongLand gongLand) {
		super.save(gongLand);
	}
	
	@Transactional(readOnly = false)
	public void delete(GongLand gongLand) {
		super.delete(gongLand);
	}
	
}