/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.SysUserData;

/**
 * 租户初始化数据DAO接口
 * @author Chenh
 * @version 2016-08-31
 */
@MyBatisDao
public interface SysUserDataDao extends CrudDao<SysUserData> {
	
}