/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.contract.entity.ExtendReal;
import com.wanfin.fpd.modules.contract.dao.ExtendRealDao;

/**
 * 展期真实还款记录Service
 * @author zzm
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class ExtendRealService extends CrudService<ExtendRealDao, ExtendReal> {

	public ExtendReal get(String id) {
		return super.get(id);
	}
	
	public List<ExtendReal> findList(ExtendReal extendReal) {
		return super.findList(extendReal);
	}
	
	public Page<ExtendReal> findPage(Page<ExtendReal> page, ExtendReal extendReal) {
		return super.findPage(page, extendReal);
	}
	
	@Transactional(readOnly = false)
	public void save(ExtendReal extendReal) {
		super.save(extendReal);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExtendReal extendReal) {
		super.delete(extendReal);
	}
	
}