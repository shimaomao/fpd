/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerrelevan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.customerrelevan.entity.TCustomerRelevan;
import com.wanfin.fpd.modules.customerrelevan.dao.TCustomerRelevanDao;

/**
 * 客户关联Service
 * @author lx
 * @version 2016-08-12
 */
@Service
@Transactional(readOnly = true)
public class TCustomerRelevanService extends CrudService<TCustomerRelevanDao, TCustomerRelevan> {

	public TCustomerRelevan get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerRelevan> findList(TCustomerRelevan tCustomerRelevan) {
		return super.findList(tCustomerRelevan);
	}
	
	public Page<TCustomerRelevan> findPage(Page<TCustomerRelevan> page, TCustomerRelevan tCustomerRelevan) {
		return super.findPage(page, tCustomerRelevan);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomerRelevan tCustomerRelevan) {
		super.save(tCustomerRelevan);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerRelevan tCustomerRelevan) {
		super.delete(tCustomerRelevan);
	}
	
}