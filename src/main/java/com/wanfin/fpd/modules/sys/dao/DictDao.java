/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
	public List<Dict> findByCondition(Dict dict);

	/**
	 * @Description 批量保存
	 * @param dicts
	 * @return
	 * @author Chenh 
	 * @date 2016年6月1日 下午2:43:09  
	 */
	public void savePL(@Param("entitys")List<Dict> entitys);

	/**
	 * @Description 批量删除
	 * @param dicts
	 * @author Chenh 
	 * @date 2016年6月1日 下午3:20:01  
	 */
	public void deletePLWL(@Param("entitys")List<Dict> dicts);

	/**
	 * @Description 根据CODE获取字典项
	 * @param dicts
	 * @author Chenh 
	 * @date 2016年6月1日 下午3:20:01  
	 */
	public Dict getByCode(Dict dict);
}
