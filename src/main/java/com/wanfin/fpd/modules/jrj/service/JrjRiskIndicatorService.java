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
import com.wanfin.fpd.modules.jrj.dao.JrjProceedsDao;
import com.wanfin.fpd.modules.jrj.dao.JrjRiskIndicatorDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 收益情况Service
 * @author xzt
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class JrjRiskIndicatorService extends CrudService<JrjRiskIndicatorDao, JrjRiskIndicator> {

	@Autowired
	JrjRiskIndicatorDao jrjRiskIndicatorDao;
	
	public JrjRiskIndicator get(String id) {
		return super.get(id);
	}
	
	public List<JrjRiskIndicator> findList(JrjRiskIndicator jrjRiskIndicator) {
		return super.findList(jrjRiskIndicator);
	}
	
	public Page<JrjRiskIndicator> findPage(Page<JrjRiskIndicator> page, JrjRiskIndicator jrjRiskIndicator) {
		return super.findPage(page, jrjRiskIndicator);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjRiskIndicator jrjRiskIndicator) {
		super.save(jrjRiskIndicator);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjRiskIndicator jrjRiskIndicator) {
		super.delete(jrjRiskIndicator);
	}	
	
	public List<JrjRiskIndicator> findListByscanFlag(JrjRiskIndicator jrjRiskIndicator){
		return jrjRiskIndicatorDao.findListByscanFlag(jrjRiskIndicator);
	}
}