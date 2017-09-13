/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.SysOfficeDetail;
import com.wanfin.fpd.modules.sys.dao.SysOfficeDetailDao;

/**
 * 机构详情Service
 * @author kewenxiu
 * @version 2017-03-09
 */
@Service
@Transactional(readOnly = true)
public class SysOfficeDetailService extends CrudService<SysOfficeDetailDao, SysOfficeDetail> {

	@Autowired
	private SysOfficeDetailDao sysOfficeDetailDao;
	
	public SysOfficeDetail get(String id) {
		return super.get(id);
	}
	
	public SysOfficeDetail getByField(SysOfficeDetail sysOfficeDetail) {
		return sysOfficeDetailDao.getByField(sysOfficeDetail);
	}
	
	public List<SysOfficeDetail> findList(SysOfficeDetail sysOfficeDetail) {
		return super.findList(sysOfficeDetail);
	}
	
	public Page<SysOfficeDetail> findPage(Page<SysOfficeDetail> page, SysOfficeDetail sysOfficeDetail) {
		return super.findPage(page, sysOfficeDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficeDetail sysOfficeDetail) {
		super.save(sysOfficeDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOfficeDetail sysOfficeDetail) {
		super.delete(sysOfficeDetail);
	}

	public SysOfficeDetail getByPid(String id) {
		// TODO Auto-generated method stub
		return sysOfficeDetailDao.getByPid(id);
	}
	
}