/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.SysOfficeDetail;

/**
 * 机构详情DAO接口
 * @author kewenxiu
 * @version 2017-03-09
 */
@MyBatisDao
public interface SysOfficeDetailDao extends CrudDao<SysOfficeDetail> {

	SysOfficeDetail getByField(SysOfficeDetail sysOfficeDetail);

	SysOfficeDetail getByPid(String id);
	
}