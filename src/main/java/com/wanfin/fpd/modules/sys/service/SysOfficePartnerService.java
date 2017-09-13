/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.SysOfficePartner;
import com.wanfin.fpd.modules.sys.dao.SysOfficePartnerDao;

/**
 * 机构股东Service
 * @author chenh
 * @version 2017-03-09
 */
@Service
@Transactional(readOnly = true)
public class SysOfficePartnerService extends CrudService<SysOfficePartnerDao, SysOfficePartner> {

	public SysOfficePartner get(String id) {
		return super.get(id);
	}
	
	public List<SysOfficePartner> findList(SysOfficePartner sysOfficePartner) {
		return super.findList(sysOfficePartner);
	}
	
	public Page<SysOfficePartner> findPage(Page<SysOfficePartner> page, SysOfficePartner sysOfficePartner) {
		return super.findPage(page, sysOfficePartner);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficePartner sysOfficePartner) {
		super.save(sysOfficePartner);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOfficePartner sysOfficePartner) {
		super.delete(sysOfficePartner);
	}
	
}