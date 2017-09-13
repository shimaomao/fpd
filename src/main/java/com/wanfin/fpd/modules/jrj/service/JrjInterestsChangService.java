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
import com.wanfin.fpd.modules.jrj.dao.JrjInterestsChangDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjInterestsChang;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 利润表Service
 * @author zxt
 * @version 2016-11-13
 */
@Service("jrjInterestsChangService")
@Transactional(readOnly = true)
public class JrjInterestsChangService extends CrudService<JrjInterestsChangDao, JrjInterestsChang> {
	
	@Autowired
	JrjInterestsChangDao jrjInterestsChangDao;

	public JrjInterestsChang get(String id) {
		return super.get(id);
	}
	
	public List<JrjInterestsChang> findList(JrjInterestsChang entity) {
		return super.findList(entity);
	}
	
	public Page<JrjInterestsChang> findPage(Page<JrjInterestsChang> page, JrjInterestsChang entity) {
		return super.findPage(page, entity);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjInterestsChang entity) {
		super.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjInterestsChang entity) {
		super.delete(entity);
	}
	
	public List<JrjInterestsChang> findListByScanFlag(JrjInterestsChang entity){
		return jrjInterestsChangDao.findListByScanFlag(entity);
	}
	
	public List<JrjInterestsChang> findListBySubmitDate(JrjInterestsChang entity){
		return jrjInterestsChangDao.findListBySubmitDate(entity);
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {
		StringBuffer data = new StringBuffer();
		JrjInterestsChang queryEntity = new JrjInterestsChang();
		queryEntity.setScanFlag("0");
		List<JrjInterestsChang> list = jrjInterestsChangDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjInterestsChang temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			/*for (JrjInterestsChang temp : list) {
				temp.setScanFlag("1");
				jrjInterestsChangDao.update(temp);
			}*/
			for (JrjInterestsChang temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjInterestsChangDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}