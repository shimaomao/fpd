/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.customertogether.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.customertogether.entity.TCustomerTogether;
import com.wanfin.fpd.modules.customertogether.dao.TCustomerTogetherDao;

/**
 * 共同借款Service
 * @author lx
 * @version 2016-08-22
 */
@Service
@Transactional(readOnly = true)
public class TCustomerTogetherService extends CrudService<TCustomerTogetherDao, TCustomerTogether> {

	public TCustomerTogether get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerTogether> findList(TCustomerTogether tCustomerTogether) {
		return super.findList(tCustomerTogether);
	}
	
	public Page<TCustomerTogether> findPage(Page<TCustomerTogether> page, TCustomerTogether tCustomerTogether) {
		return super.findPage(page, tCustomerTogether);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomerTogether tCustomerTogether) {
		super.save(tCustomerTogether);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerTogether tCustomerTogether) {
		super.delete(tCustomerTogether);
	}
	
}