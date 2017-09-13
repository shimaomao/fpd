/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.account.dao.AccountTradingRecordDao;
import com.wanfin.fpd.modules.account.entity.AccountTradingRecord;




/**
 * 资金明细Service
 * @author lzj
 * @version 2016-11-23
 */
@Service("accountTradingRecordService")
@Transactional(readOnly = true)
public class AccountTradingRecordService extends CrudService<AccountTradingRecordDao, AccountTradingRecord>{

    /**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public AccountTradingRecord get(String id) {
		return super.get(id);
	}
	
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<AccountTradingRecord> findList(AccountTradingRecord entity) {
		return super.findList(entity);
	}
	
	/**
	 * 分页查询数据列表
	 * @param entity
	 * @return
	 */
	public Page<AccountTradingRecord> findPage(Page<AccountTradingRecord> page, AccountTradingRecord entity) {
		return super.findPage(page, entity);
	}
	

	
}