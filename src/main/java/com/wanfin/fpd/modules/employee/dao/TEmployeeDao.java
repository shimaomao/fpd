/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;

/**
 * 客户个人DAO接口
 * @author lx
 * @version 2016-03-12
 */
@MyBatisDao
public interface TEmployeeDao extends CrudDao<TEmployee> {

	/**
	 * @Description 根据W端个人客户ID获取B端个人客户
	 * @param wtypeId
	 * @return
	 */
	TEmployee getByWtypeId(String wtypeId);

	/**
	 * @Description 获取所有个人客户身份证号
	 * @param wtypeId
	 * @return
	 */
	List<String> findAllCardNumList();
	
	/**
	 * 根据扫描状态进行查询就来
	 * @param tEmployee
	 * @return
	 */
	List<TEmployee> findListByScanFlag(TEmployee tEmployee);
	

	/**
	 * 根据扫描状态进行查询就来
	 * @param tEmployee
	 * @return
	 */
	List<TEmployee> findListByScanFlagAndPushStatus(TEmployee tEmployee);
	
	/**
	 * 根据扫描状态查询记录
	 * @param entity
	 * @return
	 */
    int updateByPushStatus(TEmployee entity);

    /**
     * 通过条件查询记录
     * @param tEmployee
     * @return
     */
	List<TEmployee> getByCondition(TEmployee tEmployee);

	/**
	 * 根据历史上传excel表添加数据
	 * @param TEmployeeAndContract
	 * @return
	 * */
	TEmployee byTEmployeeAndContract(TEmployeeAndContract temployeeandcontract);

	
}