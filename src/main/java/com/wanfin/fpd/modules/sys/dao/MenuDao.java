/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	/**
	 * 超级管理员获取菜单
	 * @Description	只获取公用菜单（即sysCode=null） 
	 * @param menu
	 * @return
	 * @author zzm 
	 * @date 2016-4-27 上午11:58:58  
	 */
	public List<Menu> findByAdmin(Menu menu);
	
	public List<Menu> findByUserId(Menu menu);
	
	/**
	 * @Description 获取角色拥有的产品菜单（除当前所选的产品）
	 * @param menu
	 * @return
	 * @author zzm
	 * @date 2016-5-23 上午11:33:59  
	 */
	public List<Menu> findExtSysCodeByRoleId(Menu menu);
	
	public List<Menu> findBybuiness();
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	

	public List<Menu> findAllByNoSysCode(Menu menu);

	public List<Menu> findByProAdmin(Menu menu);
	
	public List<Menu> findByProAndUserId(Menu menu);


	/**
	 * @Description 删除菜单
	 * @param menu
	 * @author Chenh 
	 * @date 2016年5月13日 下午1:58:58  
	 */
	public void deleteWL(Menu menu);

	/**
	 * @Description 批量删除菜单
	 * @param menus
	 * @author Chenh 
	 * @date 2016年5月13日 下午1:58:58  
	 */
	public void deletePLWL(List<Menu> menus);
	
	/**
	 * @Description 批量删除菜单
	 * @param ids
	 * @author Chenh 
	 * @date 2016年5月13日 下午2:07:09  
	 */
	public void deletePLWLByIds(List<String> ids);
	
	/**
	 * @Description  删除全部产品菜单
	 * @param sysCode	产品id
	 * @return
	 * @author zzm
	 * @date 2016-5-23 下午5:43:46  
	 */
	public int deleteBySysCode(Menu menu);
	
	
	/**
	 * @Description 批量删除角色菜单表数据
	 * @param ids
	 * @author linx 
	 * @date 2016年9月99日 9
	 */
	public void deletePLRoleMenuByIds(List<String> ids);
}
