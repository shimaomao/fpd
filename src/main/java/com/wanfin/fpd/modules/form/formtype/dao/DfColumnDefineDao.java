/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.formtype.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.form.formtype.entity.DfColumnDefine;

/**
 * 字段库维护DAO接口
 * @author lx
 * @version 2016-05-05
 */
@MyBatisDao
public interface DfColumnDefineDao extends CrudDao<DfColumnDefine> {
	
}