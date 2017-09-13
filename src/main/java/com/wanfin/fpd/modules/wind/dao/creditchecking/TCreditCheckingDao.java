/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.dao.creditchecking;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;

/**
 * 征信DAO接口
 * @author chenh
 * @version 2016-05-30
 */
@MyBatisDao
public interface TCreditCheckingDao extends CrudDao<TCreditChecking> {
	/**
	 * @Description 根据TCreditChecking,关联ID,子类型查询征信信息
	 * @param type
	 * @param typeIds
	 * @param typeSubs
	 * @return
	 * @author Chenh 
	 * @date 2016年5月31日 下午5:04:29  
	 */
	public List<TCreditChecking> findListByRelId(TCreditChecking entity);
	
	/**
	 * @Description 根据类型,关联ID,子类型查询征信信息
	 * @param type
	 * @param typeIds
	 * @param typeSubs
	 * @return
	 * @author Chenh 
	 * @date 2016年5月31日 下午5:04:29  
	 */
	public List<TCreditChecking> findListByTypeAndTypeIds(@Param("db")List<String> dbs, @Param("types")List<String> types, @Param("typeIds")List<String> typeIds, @Param("typeSubs")List<String> typeSubs);
	
	/**
	 * @Description 批量保存
	 * @param entitys
	 * @author Chenh 
	 * @date 2016年5月31日 下午2:54:31  
	 */
	public void savePL(@Param("entitys")List<TCreditChecking> entitys);

	/**
	 * @Description 批量删除
	 * @param entitys
	 * @author Chenh 
	 * @date 2016年5月31日 下午4:51:42  
	 */
	public void deletePLWL(@Param("entitys")List<TCreditChecking> entitys);
	
	/**
	 * @Description 批量删除
	 * @param entitys
	 * @author Chenh 
	 * @date 2016年5月31日 下午4:51:42  
	 */
	public void deletePLWLByIds(@Param("entitys")List<TCreditChecking> entitys);
}