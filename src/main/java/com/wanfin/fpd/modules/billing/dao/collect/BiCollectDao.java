/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.dao.collect;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;

/**
 * 计费汇总DAO接口
 * @author chenh
 * @version 2016-07-02
 */
@MyBatisDao
public interface BiCollectDao extends CrudDao<BiCollect> {

	/**
	 * 根据租户ID获取客户购买项
	 * @param biCollect
	 * @return
	 */
	public List<BiCollect> findListByOrganId(BiCollect biCollect);
	
	/**
	 * 根据租户ID获取客户购买最久的记录
	 * @param biCollect
	 * @return
	 */
	public BiCollect getMin(BiCollect biCollect);
	
	/**
	 * 批量修改记录
	 * @param biCollects
	 * @return
	 */
	public void updatePL(List<BiCollect> biCollects);
	
}