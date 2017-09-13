/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.employee.dao.CsCarDao;
import com.wanfin.fpd.modules.employee.entity.CsCar;

/**
 * 客户车产Service
 * @author zzm
 * @version 2016-07-21
 */
@Service
@Transactional(readOnly = true)
public class CsCarService extends CrudService<CsCarDao, CsCar> {

	public CsCar get(String id) {
		return super.get(id);
	}
	
	public List<CsCar> findList(CsCar csCar) {
		return super.findList(csCar);
	}
	
	public Page<CsCar> findPage(Page<CsCar> page, CsCar csCar) {
		return super.findPage(page, csCar);
	}
	
	@Transactional(readOnly = false)
	public void save(CsCar csCar) {
		super.save(csCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(CsCar csCar) {
		super.delete(csCar);
	}
	
}