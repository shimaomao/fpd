/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.product.entity.ProProduct;
import com.wanfin.fpd.modules.product.dao.ProProductDao;

/**
 * pro_productService
 * @author srf
 * @version 2016-12-16
 */
@Service
@Transactional(readOnly = true)
public class ProProductService extends CrudService<ProProductDao, ProProduct> {

	public ProProduct get(String id) {
		return super.get(id);
	}
	
	public List<ProProduct> findList(ProProduct proProduct) {
		return super.findList(proProduct);
	}
	
	public Page<ProProduct> findPage(Page<ProProduct> page, ProProduct proProduct) {
		return super.findPage(page, proProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(ProProduct proProduct) {
		super.save(proProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProProduct proProduct) {
		super.delete(proProduct);
	}
	
}