/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao.tpl;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.tpl.TContractTpl;

/**
 * 合同模板DAO接口
 * @author chenh
 * @version 2016-03-30
 */
@MyBatisDao
public interface TContractTplDao extends CrudDao<TContractTpl> {
	
}