/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.Family;
import com.wanfin.fpd.modules.sys.dao.FamilyDao;

/**
 * 从业人员家庭成员Service
 * @author kewenxiu
 * @version 2017-03-13
 */
@Service
@Transactional(readOnly = true)
public class FamilyService extends CrudService<FamilyDao, Family> {

	public Family get(String id) {
		return super.get(id);
	}
	
	public List<Family> findList(Family family) {
		return super.findList(family);
	}
	
	public Page<Family> findPage(Page<Family> page, Family family) {
		return super.findPage(page, family);
	}
	
	@Transactional(readOnly = false)
	public void save(Family family) {
		super.save(family);
	}
	
	@Transactional(readOnly = false)
	public void delete(Family family) {
		super.delete(family);
	}
	
}