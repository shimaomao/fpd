/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.bank.dao.account;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.bank.entity.account.TAccount;

/**
 * 银行账户DAO接口
 * @author chenh
 * @version 2016-03-29
 */
@MyBatisDao
public interface TAccountDao extends CrudDao<TAccount> {
	
}