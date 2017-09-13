/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.hastenrepay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.hastenrepay.entity.HastenRepayRecord;
import com.wanfin.fpd.modules.hastenrepay.dao.HastenRepayRecordDao;

/**
 * 催收记录Service
 * @author zzm
 * @version 2016-06-08
 */
@Service
@Transactional(readOnly = true)
public class HastenRepayRecordService extends CrudService<HastenRepayRecordDao, HastenRepayRecord> {

	public HastenRepayRecord get(String id) {
		return super.get(id);
	}
	
	public List<HastenRepayRecord> findList(HastenRepayRecord hastenRepayRecord) {
		return super.findList(hastenRepayRecord);
	}
	
	public Page<HastenRepayRecord> findPage(Page<HastenRepayRecord> page, HastenRepayRecord hastenRepayRecord) {
		return super.findPage(page, hastenRepayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(HastenRepayRecord hastenRepayRecord) {
		super.save(hastenRepayRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(HastenRepayRecord hastenRepayRecord) {
		super.delete(hastenRepayRecord);
	}
	
}