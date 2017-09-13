/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.common.dao.tpl;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;

/**
 * 公共模板库表DAO接口
 * @author srf
 * @version 2017-01-16
 */
@MyBatisDao
public interface TplPublicDao extends CrudDao<TplPublic> {

	int chageState(TplPublic tplPublic);

	TplPublic getByCode(@Param("tplCode")String tplCode);
	
}