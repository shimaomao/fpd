/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.collateral.dao.ZhuZhaiDao;
import com.wanfin.fpd.modules.collateral.entity.ZhuZhai;

/**
 * 住宅信息Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class ZhuZhaiService extends CrudService<ZhuZhaiDao, ZhuZhai> {

	@Autowired
	protected ZhuZhaiDao zhuZhaiDao;
	
	public ZhuZhai get(String id) {
		return super.get(id);
	}
	
	public ZhuZhai getByPledge(String id) {
		return zhuZhaiDao.getByPledge(id);
	}
	
	public List<ZhuZhai> findList(ZhuZhai zhuZhai) {
		return super.findList(zhuZhai);
	}
	
	public Page<ZhuZhai> findPage(Page<ZhuZhai> page, ZhuZhai zhuZhai) {
		return super.findPage(page, zhuZhai);
	}
	
	@Transactional(readOnly = false)
	public void save(ZhuZhai zhuZhai) {
		super.save(zhuZhai);
	}
	
	@Transactional(readOnly = false)
	public void delete(ZhuZhai zhuZhai) {
		super.delete(zhuZhai);
	}
	
}