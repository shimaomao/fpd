/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sms.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sms.entity.TMsgSwitch;

/**
 * 消息管理DAO接口
 * @author kewenxiu
 * @version 2017-03-24
 */
@MyBatisDao
public interface TMsgSwitchDao extends CrudDao<TMsgSwitch> {

	TMsgSwitch checkMsgSwitch(String businessType);
	
}