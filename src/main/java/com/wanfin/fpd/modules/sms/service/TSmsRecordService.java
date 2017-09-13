/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sms.entity.TSmsRecord;
import com.wanfin.fpd.modules.sms.dao.TSmsRecordDao;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * smsService
 * @author sms
 * @version 2016-07-25
 */
@Service
@Transactional(readOnly = true)
public class TSmsRecordService extends CrudService<TSmsRecordDao, TSmsRecord> {

	public TSmsRecord get(String id) {
		return super.get(id);
	}
	
	public List<TSmsRecord> findList(TSmsRecord tSmsRecord) {
		return super.findList(tSmsRecord);
	}
	
	public Page<TSmsRecord> findPage(Page<TSmsRecord> page, TSmsRecord tSmsRecord) {
		tSmsRecord.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tSmsRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(TSmsRecord tSmsRecord) {
		super.save(tSmsRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(TSmsRecord tSmsRecord) {
		super.delete(tSmsRecord);
	}
	
}