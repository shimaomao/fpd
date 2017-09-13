/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.BusinessContract;

/**
 * 业务合同DAO接口
 * @author srf
 * @version 2017-02-22
 */
@MyBatisDao
public interface BusinessContractDao extends CrudDao<BusinessContract> {

	BusinessContract getByCondition(BusinessContract businessContract);

	BusinessContract getForCheck(BusinessContract businessContract);
	
}