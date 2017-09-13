/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.asset.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.asset.entity.Asset;

/**
 * 账户资产DAO接口
 * @author zzm
 * @version 2016-06-14
 */
@MyBatisDao
public interface AssetDao extends CrudDao<Asset> {

	/**
	 * @Description 获取用户资产信息
	 * @param loginName 用户登录名
	 * @return
	 * @author zzm 
	 * @date 2016-6-14 下午3:52:24  
	 */
	public Asset getByLoginName(String loginName);

	/**
	 * @Description 增加资产
	 * @param amount
	 * @param loginName 账户
	 * @author zzm
	 * @date 2016-6-14 下午5:01:17  
	 */
	public void addAsset(@Param("loginName")String loginName, @Param("amount")BigDecimal amount);
	
	/**
	 * @Description 冻结资产
	 * @param amount	冻结的额度（amount>0 冻结，amount<0 解冻）
	 * @param loginName 账户
	 * @author zzm
	 * @date 2016-6-14 下午5:01:17  
	 */
	public void freezeAsset(@Param("loginName")String loginName, @Param("amount")BigDecimal amount);
	
}