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
import com.wanfin.fpd.modules.collateral.dao.HouseDao;
import com.wanfin.fpd.modules.collateral.entity.House;

/**
 * 住宅Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class HouseService extends CrudService<HouseDao, House> {

	@Autowired
	protected HouseDao houseDao;
	
	public House get(String id) {
		return super.get(id);
	}
	
	public House getByPledge(String id) {
		return houseDao.getByPledge(id);
	}
	
	public List<House> findList(House house) {
		return super.findList(house);
	}
	
	public Page<House> findPage(Page<House> page, House house) {
		return super.findPage(page, house);
	}
	
	@Transactional(readOnly = false)
	public void save(House house) {
		super.save(house);
	}
	
	@Transactional(readOnly = false)
	public void delete(House house) {
		super.delete(house);
	}
	
}