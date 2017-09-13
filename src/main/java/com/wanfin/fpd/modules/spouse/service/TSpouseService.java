/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.spouse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wanfin.fpd.modules.spouse.dao.TSpouseDao;

/**
 * spouseService
 * @author spouse
 * @version 2016-07-04
 */
@Service
@Transactional(readOnly = true)
public class TSpouseService extends CrudService<TSpouseDao, TSpouse> {

	public TSpouse get(String id) {
		return super.get(id);
	}
	
	public List<TSpouse> findList(TSpouse tSpouse) {
		return super.findList(tSpouse);
	}
	
	public Page<TSpouse> findPage(Page<TSpouse> page, TSpouse tSpouse) {
		return super.findPage(page, tSpouse);
	}
	
	@Transactional(readOnly = false)
	public void save(TSpouse tSpouse) {
		super.save(tSpouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(TSpouse tSpouse) {
		super.delete(tSpouse);
	}
	
}