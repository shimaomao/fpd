/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;

/**
 * 产品业务节点管理DAO接口
 * @author zzm
 * @version 2016-05-09
 */
@MyBatisDao
public interface ProductBizCfgDao extends CrudDao<ProductBizCfg> {

	/**
	 * @Description 批量删除
	 * @param productBizCfg
	 * @author Chenh 
	 * @date 2016年5月10日 上午9:47:16  
	 */
	public void deletePLByIds(ProductBizCfg productBizCfg);

	/**
	 * @Description 批量删除
	 * @param productBizCfg
	 * @author Chenh 
	 * @date 2016年5月10日 上午9:47:16  
	 */
	public void deletePLByBizIds(ProductBizCfg productBizCfg);
	
	/**
	 * @Description 批量删除
	 * @param productBizCfg
	 * @author Chenh 
	 * @date 2016年5月10日 上午9:47:16  
	 */
	public void deletePLByOrgAndProduct(ProductBizCfg productBizCfg);
	
	/**
	 * @Description 批量删除
	 * @param productBizCfg
	 * @author Chenh 
	 * @date 2016年5月10日 上午9:47:16  
	 */
	public void deletePLByOrgAndProductBizIds(ProductBizCfg productBizCfg);
	
}