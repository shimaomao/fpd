/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tcompanyinfo.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.tcompanyinfo.entity.TCompanyInfor;

/**
 * 企业高管信息DAO接口
 * @author TcompanyInfo
 * @version 2016-08-11
 */
@MyBatisDao
public interface TCompanyInforDao extends CrudDao<TCompanyInfor> {
	
}