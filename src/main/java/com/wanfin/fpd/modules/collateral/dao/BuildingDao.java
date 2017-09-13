/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.collateral.entity.Building;

/**
 * 商铺写字楼DAO接口
 * 
 * @author srf
 * @version 2016-03-24
 */
@MyBatisDao
public interface BuildingDao extends CrudDao<Building> {
	
	public Building getByPledge(String dizhi_contract_id);//通过dizhi_contract_id来查找内容
	
}