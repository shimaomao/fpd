/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.entity.Study;
import com.wanfin.fpd.modules.sys.dao.StudyDao;

/**
 * 学习经历Service
 * @author kewenxiu
 * @version 2017-03-10
 */
@Service
@Transactional(readOnly = true)
public class StudyService extends CrudService<StudyDao, Study> {

	public Study get(String id) {
		return super.get(id);
	}
	
	public List<Study> findList(Study study) {
		return super.findList(study);
	}
	
	public Page<Study> findPage(Page<Study> page, Study study) {
		return super.findPage(page, study);
	}
	
	@Transactional(readOnly = false)
	public void save(Study study) {
		super.save(study);
	}
	
	@Transactional(readOnly = false)
	public void delete(Study study) {
		super.delete(study);
	}
	
}