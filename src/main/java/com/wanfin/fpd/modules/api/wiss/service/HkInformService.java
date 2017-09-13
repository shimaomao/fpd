/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.dao.HkInformDao;

/**
 * 易联回款通知Service
 * @author srf
 * @version 2017-07-08
 */
@Service
@Transactional(readOnly = true)
public class HkInformService extends CrudService<HkInformDao, HkInform> {

	public HkInform get(String id) {
		return super.get(id);
	}
	
	public List<HkInform> findList(HkInform hkInform) {
		return super.findList(hkInform);
	}
	
	public Page<HkInform> findPage(Page<HkInform> page, HkInform hkInform) {
		return super.findPage(page, hkInform);
	}
	
	@Transactional(readOnly = false)
	public void save(HkInform hkInform) {
		super.save(hkInform);
	}
	
	@Transactional(readOnly = false)
	public void delete(HkInform hkInform) {
		super.delete(hkInform);
	}
	
}