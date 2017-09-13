/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.dao.rule;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;

/**
 * 收费规则DAO接口
 * @author chenh
 * @version 2016-07-01
 */
@MyBatisDao
public interface BiRuleDao extends CrudDao<BiRule> {
	
}