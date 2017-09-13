/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sms.entity.TMsgSwitch;
import com.wanfin.fpd.modules.sms.dao.TMsgSwitchDao;

/**
 * 消息管理Service
 * @author kewenxiu
 * @version 2017-03-24
 */
@Service("tMsgSwitchService")
@Transactional(readOnly = true)
public class TMsgSwitchService extends CrudService<TMsgSwitchDao, TMsgSwitch> {
	
	public TMsgSwitch checkMsgSwitch(String businessType) {
		return dao.checkMsgSwitch(businessType);
	}
	
	public TMsgSwitch get(String id) {
		return super.get(id);
	}
	
	public List<TMsgSwitch> findList(TMsgSwitch tMsgSwitch) {
		return super.findList(tMsgSwitch);
	}
	
	public Page<TMsgSwitch> findPage(Page<TMsgSwitch> page, TMsgSwitch tMsgSwitch) {
		return super.findPage(page, tMsgSwitch);
	}
	
	@Transactional(readOnly = false)
	public void save(TMsgSwitch tMsgSwitch) {
		super.save(tMsgSwitch);
	}
	
	@Transactional(readOnly = false)
	public void delete(TMsgSwitch tMsgSwitch) {
		super.delete(tMsgSwitch);
	}
	
}