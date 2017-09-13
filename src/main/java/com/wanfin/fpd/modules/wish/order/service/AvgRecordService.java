/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wish.order.entity.AvgRecord;
import com.wanfin.fpd.modules.wish.order.dao.AvgRecordDao;

/**
 * 历史平均数据Service
 * @author cjp
 * @version 2017-06-28
 */
@Service
@Transactional(readOnly = true)
public class AvgRecordService extends CrudService<AvgRecordDao, AvgRecord> {

	public AvgRecord get(String id) {
		return super.get(id);
	}
	
	public List<AvgRecord> findList(AvgRecord avgRecord) {
		return super.findList(avgRecord);
	}
	
	public Page<AvgRecord> findPage(Page<AvgRecord> page, AvgRecord avgRecord) {
		return super.findPage(page, avgRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AvgRecord avgRecord) {
		super.save(avgRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AvgRecord avgRecord) {
		super.delete(avgRecord);
	}
	
}