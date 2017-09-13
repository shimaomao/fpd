/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lettertpl.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.lettertpl.entity.LetterTpl;

/**
 * 函件模板DAO接口
 * @author zzm
 * @version 2016-06-08
 */
@MyBatisDao
public interface LetterTplDao extends CrudDao<LetterTpl> {
	
}