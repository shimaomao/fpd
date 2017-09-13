/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.collateral.entity.Guquan;

/**
 * 股权DAO接口
 * @author srf
 * @version 2016-03-24
 */
@MyBatisDao
public interface GuquanDao extends CrudDao<Guquan> {
	public Guquan getByPledge(String id);//通过dizhi_contract_id来查找内容
}