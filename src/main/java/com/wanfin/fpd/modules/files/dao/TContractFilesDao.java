/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.files.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.files.entity.TContractFiles;

/**
 * 附件管理DAO接口
 * @author zzm
 * @version 2016-03-21
 */
@MyBatisDao
public interface TContractFilesDao extends CrudDao<TContractFiles> {
		
	public int updateFileTaskId(Map<String, Object> map);
	//taskId
	public 	List<TContractFiles> getByTaskId(@Param("taskId")String taskId);
	
	public List<TContractFiles> findListByTaskIds(@Param("taskIds")String taskIds);
	
	public TContractFiles checkFile(@Param("taskId")String financialProductId, @Param("filePath")String filePath);
	
	//taskId
	public 	List<TContractFiles> getByTaskId(@Param("taskId")String taskId, @Param("type")String type);
}