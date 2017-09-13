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
import com.wanfin.fpd.modules.jrj.dao.JrjBusinessSituationOneDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationOne;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 经营情况月报表一Service
 * @author zxt
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class JrjBusinessSituationService extends CrudService<JrjBusinessSituationOneDao, JrjBusinessSituationOne> {
	
	@Autowired
	JrjBusinessSituationOneDao jrjBusinessSituationOneDao;

	public JrjBusinessSituationOne get(String id) {
		return super.get(id);
	}
	
	public List<JrjBusinessSituationOne> findList(JrjBusinessSituationOne entity) {
		return super.findList(entity);
	}
	
	public Page<JrjBusinessSituationOne> findPage(Page<JrjBusinessSituationOne> page, JrjBusinessSituationOne entity) {
		return super.findPage(page, entity);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjBusinessSituationOne entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjBusinessSituationOne entity) {
		super.delete(entity);
	}
	
	public List<JrjBusinessSituationOne> findListByScanFlag(JrjBusinessSituationOne entity){
		return jrjBusinessSituationOneDao.findListByScanFlag(entity);
	}
	
	public int updateByPushStatus(JrjBusinessSituationOne entity){
		return jrjBusinessSituationOneDao.updateByPushStatus(entity);
	}
	
}