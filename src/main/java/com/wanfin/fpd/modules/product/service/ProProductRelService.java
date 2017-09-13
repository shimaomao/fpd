/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.product.entity.ProProductRel;
import com.wanfin.fpd.modules.product.dao.ProProductRelDao;

/**
 * pro_product_relService
 * @author srf
 * @version 2016-12-16
 */
@Service
@Transactional(readOnly = true)
public class ProProductRelService extends CrudService<ProProductRelDao, ProProductRel> {

	public ProProductRel get(String id) {
		return super.get(id);
	}
	
	public ProProductRel findProProductRel(ProProductRel proProductRel) {
		return dao.findProProductRel(proProductRel);
	}
	
	public List<ProProductRel> findList(ProProductRel proProductRel) {
		return super.findList(proProductRel);
	}
	
	public Page<ProProductRel> findPage(Page<ProProductRel> page, ProProductRel proProductRel) {
		return super.findPage(page, proProductRel);
	}
	
	@Transactional(readOnly = false)
	public void save(ProProductRel proProductRel) {
		super.save(proProductRel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProProductRel proProductRel) {
		super.delete(proProductRel);
	}
	
}