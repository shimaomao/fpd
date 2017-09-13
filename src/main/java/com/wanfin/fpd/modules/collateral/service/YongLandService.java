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
import com.wanfin.fpd.modules.collateral.dao.YongLandDao;
import com.wanfin.fpd.modules.collateral.entity.YongLand;

/**
 * 用地Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class YongLandService extends CrudService<YongLandDao, YongLand> {

	@Autowired
	protected YongLandDao yongLandDao;
	
	public YongLand get(String id) {
		return super.get(id);
	}
	
	public YongLand getByPledge(String id) {
		return yongLandDao.getByPledge(id);
	}
	
	public List<YongLand> findList(YongLand yongLand) {
		return super.findList(yongLand);
	}
	
	public Page<YongLand> findPage(Page<YongLand> page, YongLand yongLand) {
		return super.findPage(page, yongLand);
	}
	
	@Transactional(readOnly = false)
	public void save(YongLand yongLand) {
		super.save(yongLand);
	}
	
	@Transactional(readOnly = false)
	public void delete(YongLand yongLand) {
		super.delete(yongLand);
	}
	
}