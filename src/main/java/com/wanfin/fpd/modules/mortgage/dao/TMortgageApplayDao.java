/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.mortgage.entity.TMortgageApplay;

/**
 * 押品借出审批DAO接口
 * @author lzj
 * @version 2016-09-23
 */
@MyBatisDao
public interface TMortgageApplayDao extends CrudDao<TMortgageApplay> {

	TMortgageApplay getPojoByContract(String mortgageContractId);
	
}