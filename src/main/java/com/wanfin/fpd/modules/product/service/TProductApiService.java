/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.dao.TProductDao;

/**
 * 产品管理Service
 * @author Chenh
 * @version 2016-06-23
 */
@Service
@Transactional(readOnly = true)
public class TProductApiService extends CrudService<TProductDao, TProduct> {

	public TProduct get(String id) {
		return super.get(id);
	}
	
	public List<TProduct> findList(TProduct entity) {
		return super.findList(entity);
	}
	
	public Page<TProduct> findPage(Page<TProduct> page, TProduct entity) {
		return super.findPage(page, entity);
	}
	
	@Transactional(readOnly = false)
	public void save(TProduct entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(TProduct entity) {
		super.delete(entity);
	}
	
}