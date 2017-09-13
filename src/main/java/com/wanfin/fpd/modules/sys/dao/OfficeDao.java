/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.TreeDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	public Office getBywtypeId(String wtypeId);

	/**   
	* @Description: 获取父机构的综合部 
	* @author Chenh
	* @param office
	* @return  
	* @throws
	*/
	public List<Office> getParentZHB(Office office);

	public Office getFindParam(String name);
	
}
