/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.fonds.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.fonds.entity.TFiling;
import com.wanfin.fpd.modules.fonds.dao.TFilingDao;

/**
 * 资料归档Service
 * @author lx
 * @version 2016-06-14
 */
@Service
@Transactional(readOnly = true)
public class TFilingService extends CrudService<TFilingDao, TFiling> {

	public TFiling get(String id) {
		return super.get(id);
	}
	
	public List<TFiling> findList(TFiling tFiling) {
		return super.findList(tFiling);
	}
	
	public Page<TFiling> findPage(Page<TFiling> page, TFiling tFiling) {
		return super.findPage(page, tFiling);
	}
	
	@Transactional(readOnly = false)
	public void save(TFiling tFiling) {
		super.save(tFiling);
	}
	
	@Transactional(readOnly = false)
	public void delete(TFiling tFiling) {
		super.delete(tFiling);
	}
	
}