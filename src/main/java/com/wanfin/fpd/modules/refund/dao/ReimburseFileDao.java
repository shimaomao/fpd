/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.refund.entity.ReimburseFile;

/**
 * 申请退款上传文件DAO接口
 * @author srf
 * @version 2016-04-06
 */
@MyBatisDao
public interface ReimburseFileDao extends CrudDao<ReimburseFile> {
	/**
	 * 根据条件查询上传的文件
	 * @param reimburseFile
	 * @return
	 */
	public List<ReimburseFile> findListCondition(ReimburseFile reimburseFile);
}