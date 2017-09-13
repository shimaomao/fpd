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
import com.wanfin.fpd.modules.collateral.dao.BuildingDao;
import com.wanfin.fpd.modules.collateral.entity.Building;

/**
 * 商铺写字楼Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class BuildingService extends CrudService<BuildingDao, Building> {
	
	@Autowired
	protected BuildingDao buildingDao;
	
	public Building get(String id) {
		return super.get(id);
	}
	
	public Building getByPledge(String dizhi_contract_id) {
		return buildingDao.getByPledge(dizhi_contract_id);
	}
	
	public List<Building> findList(Building building) {
		return super.findList(building);
	}
	
	public Page<Building> findPage(Page<Building> page, Building building) {
		return super.findPage(page, building);
	}
	
	@Transactional(readOnly = false)
	public void save(Building building) {
		super.save(building);
	}
	
	@Transactional(readOnly = false)
	public void delete(Building building) {
		super.delete(building);
	}
	
}