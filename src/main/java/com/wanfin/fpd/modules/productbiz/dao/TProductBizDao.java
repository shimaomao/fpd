/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.productbiz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.productbiz.vo.ProductBizVo;

/**
 * 业务功能DAO接口
 * @author lx
 * @version 2016-03-10
 */
@MyBatisDao
public interface TProductBizDao extends CrudDao<TProductBiz> {
	/**
	 * @Description 根据机构查询TProductBiz
	 * @param tProductBiz
	 * @return
	 * @author Chenh 
	 * @date 2016年5月6日 下午5:38:30  
	 */
	List<TProductBiz> findListByCompany(TProductBiz tProductBiz);
	/**
	 * @Description 根据TProductBiz查询TProductBiz
	 * @param tProductBiz
	 * @return
	 * @author Chenh 
	 * @date 2016年5月6日 下午5:38:30  
	 */
	List<TProductBiz> findAllListByTProductBiz(TProductBiz tProductBiz);
	/**
	 * @Description 根据TProductBiz查询TProductBiz
	 * @param tProductBiz
	 * @return
	 * @author Chenh 
	 * @date 2016年5月6日 下午5:38:30  
	 */
	List<TProductBiz> findByParentIdsLike(TProductBiz tProductBiz);
	/**
	 * @Description TODO
	 * @param tProductBiz
	 * @author Chenh 
	 * @date 2016年5月19日 下午4:52:24  
	 */
	void updateParentIds(TProductBiz tProductBiz);
	/**
	 * @Description TODO
	 * @param tProductBiz
	 * @author Chenh 
	 * @date 2016年5月19日 下午4:52:35  
	 */
	void updateSort(TProductBiz tProductBiz);
	/**
	 * @Description TODO
	 * @param productBizVo
	 * @return
	 * @author Chenh 
	 * @date 2016年5月24日 下午2:20:47  
	 */
	List<ProductBizVo> findByBizCfg(ProductBizVo productBizVo);
	
	
	/**
	 * @Description 维护业务节点与业务菜单权限关系
	 * @param tProductBiz
	 * @return
	 * @author zzm
	 * @date 2016-5-23 下午2:53:57  
	 */
	public int deleteBizMenu(TProductBiz tProductBiz);

	public int insertBizMenu(TProductBiz tProductBiz);

	public List<String> getMenuIdsListByBizIds(String bizIds);
	
	public List<String> getMenuIdsListByBizListIds(List<String> list);
	
	public List<String> findAllByMenuSysCode(String sysCode);
}