/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.employee.dao.CsContactDao;
import com.wanfin.fpd.modules.employee.entity.CsContact;

/**
 * 联系人Service
 * @author zzm
 * @version 2016-07-21
 */
@Service
@Transactional(readOnly = true)
public class CsContactService extends CrudService<CsContactDao, CsContact> {

	public CsContact get(String id) {
		return super.get(id);
	}
	
	public List<CsContact> findList(CsContact csContact) {
		return super.findList(csContact);
	}
	
	public Page<CsContact> findPage(Page<CsContact> page, CsContact csContact) {
		return super.findPage(page, csContact);
	}
	
	@Transactional(readOnly = false)
	public void save(CsContact csContact) {
		super.save(csContact);
	}
	
	@Transactional(readOnly = false)
	public void delete(CsContact csContact) {
		super.delete(csContact);
	}
	
}