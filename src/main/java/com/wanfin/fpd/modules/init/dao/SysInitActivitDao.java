/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.init.entity.SysInitActivit;

/**
 * 流程初始化DAO接口
 * @author zxj
 * @version 2016-09-22
 */
@MyBatisDao
public interface SysInitActivitDao extends CrudDao<SysInitActivit> {
	public void saveAllData(@Param("sysInitActivit")SysInitActivit sysInitActivit,@Param("list")List<Map<String,String>> list);
}