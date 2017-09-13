/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.SysUserData;
import com.wanfin.fpd.modules.sys.dao.SysUserDataDao;

/**
 * 租户初始化数据Service
 * @author Chenh
 * @version 2016-08-31
 */
@Service
@Transactional(readOnly = true)
public class SysUserDataService extends CrudService<SysUserDataDao, SysUserData> {

	public SysUserData get(String id) {
		return super.get(id);
	}
	
	public List<SysUserData> findList(SysUserData sysUserData) {
		return super.findList(sysUserData);
	}
	
	public Page<SysUserData> findPage(Page<SysUserData> page, SysUserData sysUserData) {
		return super.findPage(page, sysUserData);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserData sysUserData) {
		super.save(sysUserData);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserData sysUserData) {
		super.delete(sysUserData);
	}
	
}