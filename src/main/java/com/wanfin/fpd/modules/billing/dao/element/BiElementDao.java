/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.dao.element;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;

/**
 * 收费项DAO接口
 * @author chenh
 * @version 2016-07-01
 */
@MyBatisDao
public interface BiElementDao extends CrudDao<BiElement> {
	
}