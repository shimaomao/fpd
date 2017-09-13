/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.windcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.windcfg.entity.TWindControlCfg;

/**
 * 风控配置DAO接口
 * @author chenh
 * @version 2016-05-26
 */
@MyBatisDao
public interface TWindControlCfgDao extends CrudDao<TWindControlCfg> {

	/**
	 * @Description TODO
	 * @param ids
	 * @author Chenh 
	 * @date 2016年5月27日 上午11:29:05  
	 */
	public void deleteWLByRelId(String id);
	
	/**
	 * @Description TODO
	 * @param ids
	 * @author Chenh 
	 * @date 2016年5月27日 上午11:29:05  
	 */
	public void deleteWLByRelIdList(@Param("list")List<String> list, @Param("productId")String productId);
}