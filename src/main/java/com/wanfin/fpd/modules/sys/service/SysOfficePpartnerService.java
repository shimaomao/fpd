/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.SysOfficePpartner;
import com.wanfin.fpd.modules.sys.dao.SysOfficePpartnerDao;

/**
 * 机构个人股东Service
 * @author kewenxiu
 * @version 2017-03-09
 */
@Service
@Transactional(readOnly = true)
public class SysOfficePpartnerService extends CrudService<SysOfficePpartnerDao, SysOfficePpartner> {

	public SysOfficePpartner get(String id) {
		return super.get(id);
	}
	
	public List<SysOfficePpartner> findList(SysOfficePpartner sysOfficePpartner) {
		return super.findList(sysOfficePpartner);
	}
	
	public Page<SysOfficePpartner> findPage(Page<SysOfficePpartner> page, SysOfficePpartner sysOfficePpartner) {
		return super.findPage(page, sysOfficePpartner);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficePpartner sysOfficePpartner) {
		super.save(sysOfficePpartner);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOfficePpartner sysOfficePpartner) {
		super.delete(sysOfficePpartner);
	}
	
}