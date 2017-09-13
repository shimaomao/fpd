/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.FeeBiz;
import com.wanfin.fpd.modules.sys.entity.OpenBiz;

/**
 * 开通业务DAO接口
 * @author zzm
 * @version 2016-06-06
 */
@MyBatisDao
public interface OpenBizDao extends CrudDao<OpenBiz> {
	
	public List<FeeBiz> findFeeBizList(FeeBiz feeBiz);

	/**
	 * @Description 获取租户对应的一个付费服务的激活配置
	 * @param feeBizId
	 * @param organId
	 * @return
	 * @author zzm 
	 * @date 2016-6-6 下午5:29:03  
	 */
	public Map<String,String> getFeeBizOrganLink(@Param("feeBizId")String feeBizId, @Param("organId")String organId);
	
	/**
	 * @Description 保存租户对应的一个付费服务的激活配置
	 * @param feeBizId
	 * @param organId
	 * @param count
	 * @return
	 * @author zzm 
	 * @date 2016-6-6 下午5:29:03  
	 */
	public void saveFeeBizOrganLink(@Param("feeBizId")String feeBizId, @Param("organId")String organId, @Param("count")int count);
	
	/**
	 * @Description 更新租户对应的一个付费服务的激活配置
	 * @param feeBizId
	 * @param organId
	 * @param count
	 * @return
	 * @author zzm 
	 * @date 2016-6-6 下午5:29:03  
	 */
	public void updateFeeBizOrganLink(@Param("feeBizId")String feeBizId, @Param("organId")String organId, @Param("count")int count);

	
	/**
	 * @Description 获取单个付费业务对象
	 * @param id
	 * @return
	 * @author zzm
	 * @date 2016-6-21 上午10:16:09  
	 */
	public FeeBiz getFeeBiz(String id);

	/**
	 * @Description 保存付费业务
	 * @param feeBiz
	 * @author zzm 
	 * @date 2016-6-21 下午4:31:18  
	 */
	public void insertFeeBiz(FeeBiz feeBiz);
	
	/**
	 * @Description 更新付费业务
	 * @param feeBiz
	 * @author zzm
	 * @date 2016-6-21 下午4:38:43  
	 */
	public void updateFeeBiz(FeeBiz feeBiz);
}