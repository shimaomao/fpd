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
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.jrj.dao.JrjBalanceSheepDao;
import com.wanfin.fpd.modules.jrj.dao.JrjOldCostDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCost;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 资产负债表Service
 * @author lx
 * @version 2016-05-17
 */
@Service("jrjOldCostService")
@Transactional(readOnly = true)
public class JrjOldCostService extends CrudService<JrjOldCostDao, JrjOldCost> {
	
	@Autowired
	JrjOldCostDao jrjOldCostDao;

	public JrjOldCost get(String id) {
		return super.get(id);
	}
	
	public List<JrjOldCost> findList(JrjOldCost jrjOldCost) {
		return super.findList(jrjOldCost);
	}
	
	public Page<JrjOldCost> findPage(Page<JrjOldCost> page, JrjOldCost jrjOldCost) {
		return super.findPage(page, jrjOldCost);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjOldCost jrjOldCost) {
		super.save(jrjOldCost);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjOldCost jrjOldCost) {
		super.delete(jrjOldCost);
	}
	
	public List<JrjOldCost> findListByScanFlag(JrjOldCost jrjOldCost){
		return jrjOldCostDao.findListByScanFlag(jrjOldCost);
	}
	
	public List<JrjOldCost> findListBySubmitDate(JrjOldCost jrjOldCost){
		return jrjOldCostDao.findListBySubmitDate(jrjOldCost);
	}
	
	public int updateByPushStatus(JrjOldCost entity){
		return jrjOldCostDao.updateByPushStatus(entity);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName) {
		StringBuffer data = new StringBuffer();
		JrjOldCost queryEntity = new JrjOldCost();
		queryEntity.setScanFlag("0");
		List<JrjOldCost> list = jrjOldCostDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjOldCost temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			for (JrjOldCost temp : list) {
				temp.setScanFlag("1");
				jrjOldCostDao.update(temp);
			}
			for (JrjOldCost temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjOldCostDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}