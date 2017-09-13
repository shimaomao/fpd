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
import com.wanfin.fpd.modules.collateral.dao.SingleVillaDao;
import com.wanfin.fpd.modules.collateral.entity.SingleVilla;

/**
 * 独栋别墅Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class SingleVillaService extends CrudService<SingleVillaDao, SingleVilla> {

	@Autowired
	protected SingleVillaDao SingleVillaDao;
	
	public SingleVilla get(String id) {
		return super.get(id);
	}
	
	public SingleVilla getByPledge(String id) {
		return SingleVillaDao.getByPledge(id);
	}
	
	public List<SingleVilla> findList(SingleVilla singleVilla) {
		return super.findList(singleVilla);
	}
	
	public Page<SingleVilla> findPage(Page<SingleVilla> page, SingleVilla singleVilla) {
		return super.findPage(page, singleVilla);
	}
	
	@Transactional(readOnly = false)
	public void save(SingleVilla singleVilla) {
		super.save(singleVilla);
	}
	
	@Transactional(readOnly = false)
	public void delete(SingleVilla singleVilla) {
		super.delete(singleVilla);
	}
	
}