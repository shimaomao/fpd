/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.ContractAudit;

/**
 * 签订合同审核流程DAO接口
 * @author srf
 * @version 2016-12-27
 */
@MyBatisDao
public interface ContractAuditDao extends CrudDao<ContractAudit> {
	//通过合同获取信息
	ContractAudit getByLoanContract(ContractAudit contractAudit);
	//删除重复的（一个合同只有一条记录）
	void delByLoanContract(ContractAudit contractAudit);
}