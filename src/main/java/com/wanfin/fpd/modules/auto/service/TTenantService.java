/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.auto.entity.TTenant;
import com.wanfin.fpd.modules.auto.dao.TTenantDao;

/**
 * 租户Service
 * @author Chenh
 * @version 2016-09-08
 */
@Service
@Transactional(readOnly = true)
public class TTenantService extends CrudService<TTenantDao, TTenant> {

	public TTenant get(String id) {
		return super.get(id);
	}
	
	public List<TTenant> findList(TTenant tTenant) {
		return super.findList(tTenant);
	}
	
	public Page<TTenant> findPage(Page<TTenant> page, TTenant tTenant) {
		return super.findPage(page, tTenant);
	}
	
	@Transactional(readOnly = false)
	public void save(TTenant tTenant) {
		super.save(tTenant);
	}
	
	@Transactional(readOnly = false)
	public void delete(TTenant tTenant) {
		super.delete(tTenant);
	}
	
}