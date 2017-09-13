/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.ExtendContract;

/**
 * 展期合同DAO接口
 * @author zzm
 * @version 2016-04-01
 */
@MyBatisDao
public interface ExtendContractDao extends CrudDao<ExtendContract> {
	
	public Integer getMaxNum(String loanContractId);
	
}