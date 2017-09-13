/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountdetail.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.accountdetail.entity.TAccountDetail;
import com.wanfin.fpd.modules.accountdetail.dao.TAccountDetailDao;

/**
 * 账目明细Service
 * @author lx
 * @version 2016-05-13
 */
@Service
@Transactional(readOnly = true)
public class TAccountDetailService extends CrudService<TAccountDetailDao, TAccountDetail> {

	public TAccountDetail get(String id) {
		return super.get(id);
	}
	
	public List<TAccountDetail> findList(TAccountDetail tAccountDetail) {
		return super.findList(tAccountDetail);
	}
	
	public Page<TAccountDetail> findPage(Page<TAccountDetail> page, TAccountDetail tAccountDetail) {
		return super.findPage(page, tAccountDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(TAccountDetail tAccountDetail) {
		super.save(tAccountDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAccountDetail tAccountDetail) {
		super.delete(tAccountDetail);
	}
	
}