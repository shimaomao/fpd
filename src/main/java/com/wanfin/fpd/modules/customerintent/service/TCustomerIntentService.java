/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customerintent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.customerintent.entity.TCustomerIntent;
import com.wanfin.fpd.modules.customerintent.dao.TCustomerIntentDao;

/**
 * customerintentService
 * @author lx
 * @version 2016-08-13
 */
@Service
@Transactional(readOnly = true)
public class TCustomerIntentService extends CrudService<TCustomerIntentDao, TCustomerIntent> {

	public TCustomerIntent get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerIntent> findList(TCustomerIntent tCustomerIntent) {
		return super.findList(tCustomerIntent);
	}
	
	public Page<TCustomerIntent> findPage(Page<TCustomerIntent> page, TCustomerIntent tCustomerIntent) {
		return super.findPage(page, tCustomerIntent);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomerIntent tCustomerIntent) {
		super.save(tCustomerIntent);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerIntent tCustomerIntent) {
		super.delete(tCustomerIntent);
	}
	
}