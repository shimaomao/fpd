/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.SysOfficeEmployee;
import com.wanfin.fpd.modules.sys.dao.SysOfficeEmployeeDao;

/**
 * 从业人员Service
 * @author kewenxiu
 * @version 2017-03-09
 */
@Service
@Transactional(readOnly = true)
public class SysOfficeEmployeeService extends CrudService<SysOfficeEmployeeDao, SysOfficeEmployee> {

	public SysOfficeEmployee get(String id) {
		return super.get(id);
	}
	
	public List<SysOfficeEmployee> findList(SysOfficeEmployee sysOfficeEmployee) {
		return super.findList(sysOfficeEmployee);
	}
	
	public Page<SysOfficeEmployee> findPage(Page<SysOfficeEmployee> page, SysOfficeEmployee sysOfficeEmployee) {
		return super.findPage(page, sysOfficeEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficeEmployee sysOfficeEmployee) {
		super.save(sysOfficeEmployee);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOfficeEmployee sysOfficeEmployee) {
		super.delete(sysOfficeEmployee);
	}
	
}