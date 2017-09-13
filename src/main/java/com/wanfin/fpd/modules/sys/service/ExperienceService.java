/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.Experience;
import com.wanfin.fpd.modules.sys.dao.ExperienceDao;

/**
 * 从业人员工作经历Service
 * @author kewenxiu
 * @version 2017-03-13
 */
@Service
@Transactional(readOnly = true)
public class ExperienceService extends CrudService<ExperienceDao, Experience> {

	public Experience get(String id) {
		return super.get(id);
	}
	
	public List<Experience> findList(Experience experience) {
		return super.findList(experience);
	}
	
	public Page<Experience> findPage(Page<Experience> page, Experience experience) {
		return super.findPage(page, experience);
	}
	
	@Transactional(readOnly = false)
	public void save(Experience experience) {
		super.save(experience);
	}
	
	@Transactional(readOnly = false)
	public void delete(Experience experience) {
		super.delete(experience);
	}
	
}