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
import com.wanfin.fpd.modules.jrj.dao.JrjBusinessSituationTwoDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationOne;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationTwo;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 经营情况月报表一Service
 * @author zxt
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class JrjBusinessSituationTwoService extends CrudService<JrjBusinessSituationTwoDao, JrjBusinessSituationTwo> {
	
	@Autowired
	JrjBusinessSituationTwoDao jrjBusinessSituationTwoDao;

	public JrjBusinessSituationTwo get(String id) {
		return super.get(id);
	}
	
	public List<JrjBusinessSituationTwo> findList(JrjBusinessSituationTwo entity) {
		return super.findList(entity);
	}
	
	public Page<JrjBusinessSituationTwo> findPage(Page<JrjBusinessSituationTwo> page, JrjBusinessSituationTwo entity) {
		return super.findPage(page, entity);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjBusinessSituationTwo entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjBusinessSituationTwo entity) {
		super.delete(entity);
	}
	
	public List<JrjBusinessSituationTwo> findListByScanFlag(JrjBusinessSituationTwo entity){
		return jrjBusinessSituationTwoDao.findListByScanFlag(entity);
	}
	
	public int updateByPushStatus(JrjBusinessSituationTwo entity){
		return jrjBusinessSituationTwoDao.updateByPushStatus(entity);
	}
	
}