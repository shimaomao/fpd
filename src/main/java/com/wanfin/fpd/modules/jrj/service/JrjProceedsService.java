/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.jrj.dao.JrjProceedsDao;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;

/**
 * 收益情况Service
 * @author xzt
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class JrjProceedsService extends CrudService<JrjProceedsDao, JrjProceeds> {

	@Autowired
	JrjProceedsDao jrjProceedsDao;
	
	public JrjProceeds get(String id) {
		return super.get(id);
	}
	
	public List<JrjProceeds> findList(JrjProceeds tBalanceSheep) {
		return super.findList(tBalanceSheep);
	}
	
	public Page<JrjProceeds> findPage(Page<JrjProceeds> page, JrjProceeds tBalanceSheep) {
		return super.findPage(page, tBalanceSheep);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjProceeds jrjProceeds) {
		super.save(jrjProceeds);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjProceeds jrjProceeds) {
		super.delete(jrjProceeds);
	}
	
	public List<JrjProceeds> findListByScanFlag(JrjProceeds jrjProceeds){
		return jrjProceedsDao.findListByScanFlag(jrjProceeds);
	}
	
}