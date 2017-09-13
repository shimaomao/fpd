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
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 资产负债表Service
 * @author lx
 * @version 2016-05-17
 */
@Service("jrjBalanceSheepService")
@Transactional(readOnly = true)
public class JrjBalanceSheepService extends CrudService<JrjBalanceSheepDao, JrjBalanceSheep> {
	
	@Autowired
	JrjBalanceSheepDao jrjBalanceSheepDao;

	public JrjBalanceSheep get(String id) {
		return super.get(id);
	}
	
	public List<JrjBalanceSheep> findList(JrjBalanceSheep tBalanceSheep) {
		return super.findList(tBalanceSheep);
	}
	
	public Page<JrjBalanceSheep> findPage(Page<JrjBalanceSheep> page, JrjBalanceSheep tBalanceSheep) {
		return super.findPage(page, tBalanceSheep);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjBalanceSheep tBalanceSheep) {
		super.save(tBalanceSheep);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjBalanceSheep tBalanceSheep) {
		super.delete(tBalanceSheep);
	}
	
	public List<JrjBalanceSheep> findListByScanFlag(JrjBalanceSheep jrjBalanceSheep){
		return jrjBalanceSheepDao.findListByScanFlag(jrjBalanceSheep);
	}
	
	public List<JrjBalanceSheep> findListBySubmitDate(JrjBalanceSheep jrjBalanceSheep){
		return jrjBalanceSheepDao.findListBySubmitDate(jrjBalanceSheep);
	}
	
	public int updateByPushStatus(JrjBalanceSheep entity){
		return jrjBalanceSheepDao.updateByPushStatus(entity);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName) {
		StringBuffer data = new StringBuffer();
		JrjBalanceSheep queryEntity = new JrjBalanceSheep();
		queryEntity.setScanFlag("0");
		List<JrjBalanceSheep> list = jrjBalanceSheepDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjBalanceSheep temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			/*for (JrjBalanceSheep temp : list) {
				temp.setScanFlag("1");
				jrjBalanceSheepDao.update(temp);
			}*/
			for (JrjBalanceSheep temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjBalanceSheepDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}