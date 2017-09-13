/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.account.entity.BsAccount;

/**
 * 资金账户信息DAO接口
 * @author srf
 * @version 2017-01-03
 */
@MyBatisDao
public interface BsAccountDao extends CrudDao<BsAccount> {
	public BsAccount getByAuthUser(String authUserId);
	
	public BsAccount getByAuthUser(BsAccount bsAccount);

	public BsAccount getbyOrganId(String organId);
}