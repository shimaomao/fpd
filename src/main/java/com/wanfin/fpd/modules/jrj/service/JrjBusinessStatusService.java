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
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.dao.JrjBalanceSheepDao;
import com.wanfin.fpd.modules.jrj.dao.JrjBusinessStatusDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProceedsDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 担保业务状况Service
 * @author xzt
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class JrjBusinessStatusService extends CrudService<JrjBusinessStatusDao, JrjBusinessStatus> {

	@Autowired
	JrjBusinessStatusDao jrjBusinessStatusDao;
	
	public JrjBusinessStatus get(String id) {
		return super.get(id);
	}
	
	public List<JrjBusinessStatus> findList(JrjBusinessStatus jrjBusinessStatus) {
		return super.findList(jrjBusinessStatus);
	}
	
	public Page<JrjBusinessStatus> findPage(Page<JrjBusinessStatus> page, JrjBusinessStatus jrjBusinessStatus) {
		return super.findPage(page, jrjBusinessStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjBusinessStatus jrjBusinessStatus) {
		super.save(jrjBusinessStatus);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjBusinessStatus jrjBusinessStatus) {
		super.delete(jrjBusinessStatus);
	}
	
	public List<JrjBusinessStatus> findListOrderByCreateDate(JrjBusinessStatus jrjBusinessStatus){
		return jrjBusinessStatusDao.findListOrderByCreateDate(jrjBusinessStatus);
	}
	
	public List<JrjBusinessStatus> findListByCreateDate(JrjBusinessStatus jrjBusinessStatus){
		return jrjBusinessStatusDao.findListByCreateDate(jrjBusinessStatus);
	}
	
	public List<JrjBusinessStatus> findListByScanFlag(JrjBusinessStatus jrjBusinessStatus){
		return 	jrjBusinessStatusDao.findListByScanFlag(jrjBusinessStatus);
	}		
	
}