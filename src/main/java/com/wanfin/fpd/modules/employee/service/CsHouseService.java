/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.employee.dao.CsHouseDao;
import com.wanfin.fpd.modules.employee.entity.CsHouse;

/**
 * 客户房产Service
 * @author zzm
 * @version 2016-07-21
 */
@Service
@Transactional(readOnly = true)
public class CsHouseService extends CrudService<CsHouseDao, CsHouse> {

	public CsHouse get(String id) {
		return super.get(id);
	}
	
	public List<CsHouse> findList(CsHouse csHouse) {
		return super.findList(csHouse);
	}
	
	public Page<CsHouse> findPage(Page<CsHouse> page, CsHouse csHouse) {
		return super.findPage(page, csHouse);
	}
	
	@Transactional(readOnly = false)
	public void save(CsHouse csHouse) {
		super.save(csHouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(CsHouse csHouse) {
		super.delete(csHouse);
	}
	
}