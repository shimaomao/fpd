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
import com.wanfin.fpd.modules.collateral.dao.TerraceVillaDao;
import com.wanfin.fpd.modules.collateral.entity.TerraceVilla;

/**
 * 联排别墅Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class TerraceVillaService extends CrudService<TerraceVillaDao, TerraceVilla> {

	@Autowired
	protected TerraceVillaDao terraceVillaDao;
	
	public TerraceVilla get(String id) {
		return super.get(id);
	}
	
	public TerraceVilla getByPledge(String id) {
		return terraceVillaDao.getByPledge(id);
	}
	
	public List<TerraceVilla> findList(TerraceVilla terraceVilla) {
		return super.findList(terraceVilla);
	}
	
	public Page<TerraceVilla> findPage(Page<TerraceVilla> page, TerraceVilla terraceVilla) {
		return super.findPage(page, terraceVilla);
	}
	
	@Transactional(readOnly = false)
	public void save(TerraceVilla terraceVilla) {
		super.save(terraceVilla);
	}
	
	@Transactional(readOnly = false)
	public void delete(TerraceVilla terraceVilla) {
		super.delete(terraceVilla);
	}
	
}