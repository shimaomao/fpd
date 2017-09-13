/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.CifQhValidate;
import com.wanfin.fpd.modules.credit.dao.CifQhValidateDao;

/**
 * creditresultService
 * @author cjp
 * @version 2017-05-09
 */
@Service
@Transactional(readOnly = true)
public class CifQhValidateService extends CrudService<CifQhValidateDao, CifQhValidate> {

	public CifQhValidate get(String id) {
		return super.get(id);
	}
	
	public List<CifQhValidate> findList(CifQhValidate cifQhValidate) {
		return super.findList(cifQhValidate);
	}
	
	public Page<CifQhValidate> findPage(Page<CifQhValidate> page, CifQhValidate cifQhValidate) {
		return super.findPage(page, cifQhValidate);
	}
	
	@Transactional(readOnly = false)
	public void save(CifQhValidate cifQhValidate) {
		super.save(cifQhValidate);
	}
	
	@Transactional(readOnly = false)
	public void delete(CifQhValidate cifQhValidate) {
		super.delete(cifQhValidate);
	}
	
}