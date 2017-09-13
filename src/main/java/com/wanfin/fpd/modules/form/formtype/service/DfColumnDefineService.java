/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.formtype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.form.formtype.dao.DfColumnDefineDao;
import com.wanfin.fpd.modules.form.formtype.entity.DfColumnDefine;

/**
 * 字段库维护Service
 * @author lx
 * @version 2016-05-05
 */
@Service
@Transactional(readOnly = true)
public class DfColumnDefineService extends CrudService<DfColumnDefineDao, DfColumnDefine> {

	public DfColumnDefine get(String id) {
		return super.get(id);
	}
	
	public List<DfColumnDefine> findList(DfColumnDefine dfColumnDefine) {
		return super.findList(dfColumnDefine);
	}
	
	public Page<DfColumnDefine> findPage(Page<DfColumnDefine> page, DfColumnDefine dfColumnDefine) {
		return super.findPage(page, dfColumnDefine);
	}
	
	@Transactional(readOnly = false)
	public void save(DfColumnDefine dfColumnDefine) {
		super.save(dfColumnDefine);
	}
	
	@Transactional(readOnly = false)
	public void delete(DfColumnDefine dfColumnDefine) {
		super.delete(dfColumnDefine);
	}
	
}