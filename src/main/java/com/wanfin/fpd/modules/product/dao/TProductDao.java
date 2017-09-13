/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.vo.TProductVo;

/**
 * 产品管理DAO接口
 * @author lx
 * @version 2016-03-23
 */
@MyBatisDao
public interface TProductDao extends CrudDao<TProduct> {

	/**
	 * @Description TODO
	 * @param tProductVo
	 * @return
	 * @author Chenh 
	 * @date 2016年5月12日 下午5:01:43  
	 */
	List<TProductVo> findListVo(TProductVo tProductVo);
	
	/**
	 * @Description 根据W端产品ID获取B段产品
	 * @param wtypeId
	 * @return
	 */
	TProduct getByWtypeId(String wtypeId);
	
	/**
	 * @Description 根据租户ID获取所有产品
	 * @param organId
	 * @return
	 */
	List<TProduct> findByOrganId(TProduct entity);
}