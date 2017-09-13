/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wauto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wauto.entity.WAuto;
import com.wanfin.fpd.modules.wauto.dao.WAutoDao;

/**
 * 自动业务Service
 * @author chenh
 * @version 2016-07-28
 */
@Service
@Transactional(readOnly = true)
public class WAutoService extends CrudService<WAutoDao, WAuto> {

	public WAuto get(String id) {
		return super.get(id);
	}
	
	public List<WAuto> findList(WAuto wAuto) {
		return super.findList(wAuto);
	}
	
	public Page<WAuto> findPage(Page<WAuto> page, WAuto wAuto) {
		return super.findPage(page, wAuto);
	}
	
	@Transactional(readOnly = false)
	public void save(WAuto wAuto) {
		super.save(wAuto);
	}
	
	@Transactional(readOnly = false)
	public void delete(WAuto wAuto) {
		super.delete(wAuto);
	}
	
}