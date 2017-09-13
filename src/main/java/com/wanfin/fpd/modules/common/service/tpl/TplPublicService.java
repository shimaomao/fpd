/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.common.service.tpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;
import com.wanfin.fpd.modules.common.dao.tpl.TplPublicDao;

/**
 * 公共模板库表Service
 * @author srf
 * @version 2017-01-16
 */
@Service
@Transactional(readOnly = true)
public class TplPublicService extends CrudService<TplPublicDao, TplPublic> {

	public TplPublic get(String id) {
		return super.get(id);
	}
	
	public TplPublic getByCode(String tplCode) {
		return dao.getByCode(tplCode);
	}
	
	public List<TplPublic> findList(TplPublic tplPublic) {
		return super.findList(tplPublic);
	}
	
	public Page<TplPublic> findPage(Page<TplPublic> page, TplPublic tplPublic) {
		return super.findPage(page, tplPublic);
	}
	
	@Transactional(readOnly = false)
	public void save(TplPublic tplPublic) {
		super.save(tplPublic);
	}
	
	@Transactional(readOnly = false)
	public void delete(TplPublic tplPublic) {
		super.delete(tplPublic);
	}

	public int chageState(TplPublic tplPublic) {
		return dao.chageState(tplPublic);
	}
	
}