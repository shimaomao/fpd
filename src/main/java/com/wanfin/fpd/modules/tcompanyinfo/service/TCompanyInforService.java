/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tcompanyinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.tcompanyinfo.entity.TCompanyInfor;
import com.wanfin.fpd.modules.tcompanyinfo.dao.TCompanyInforDao;

/**
 * 企业高管信息Service
 * @author TcompanyInfo
 * @version 2016-08-11
 */
@Service
@Transactional(readOnly = true)
public class TCompanyInforService extends CrudService<TCompanyInforDao, TCompanyInfor> {

	public TCompanyInfor get(String id) {
		return super.get(id);
	}
	
	public List<TCompanyInfor> findList(TCompanyInfor tCompanyInfor) {
		return super.findList(tCompanyInfor);
	}
	
	public Page<TCompanyInfor> findPage(Page<TCompanyInfor> page, TCompanyInfor tCompanyInfor) {
		return super.findPage(page, tCompanyInfor);
	}
	
	@Transactional(readOnly = false)
	public void save(TCompanyInfor tCompanyInfor) {
		super.save(tCompanyInfor);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCompanyInfor tCompanyInfor) {
		super.delete(tCompanyInfor);
	}
	
}