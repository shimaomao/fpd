/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.company.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.company.entity.Customer;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;

/**
 * 企业客户DAO接口
 * @author lx
 * @version 2016-03-14
 */
@MyBatisDao
public interface TCompanyDao extends CrudDao<TCompany> {
	/**
	 * @Description 客户查询查询
	 * @param customer
	 * @return
	 * @author zzm
	 * @date 2016-4-18 下午2:53:12  
	 */
	public List<Customer> findCustomerList(Customer customer);

	/**
	 * @Description 
	 * 
	 * @param id
	 * @return
	 * @author zzm
	 * @date 2016-6-3 下午2:23:26  
	 */
	public Customer getCustomer(String id);
	
	/**
	 * @Description 保存客户与产品的关联
	 * @param productId	产品id
	 * @param customerId	客户id（个人/企业）
	 * @param customerType	客户类型（1 企业，2 个人）
	 * @author zzm 
	 * @date 2016-6-7 上午10:38:07  
	 */
	public void insertCustomerProductLink(@Param("productId")String productId,
			@Param("customerId")String customerId,@Param("customerType")String customerType);
	
	/**
	 * @Description 获取客户与产品的关联
	 * @param productId	产品id
	 * @param customerId	客户id（个人/企业）
	 * @author zzm 
	 * @date 2016-6-7 上午10:38:07  
	 */
	public Map<String,String> getCustomerProductLink(@Param("productId")String productId,@Param("customerId")String customerId);
	
	/**
	 * @Description 删除客户与产品的关联
	 * @param productId	产品id
	 * @param customerId	客户id（个人/企业）
	 * @author zzm 
	 * @date 2016-6-7 上午10:38:07  
	 */
	public int deleteCustomerProductLink(@Param("productId")String productId,@Param("customerId")String customerId);

	/**
	 * 
	 * @param productid产品id
	 * @param filedName字段名称
	 * @param table表名  1企业    2个人
	 * @param sex 性别类型  1男   2女
	 * @return
	 */
	//public int findCustomerSexAmount(@Param("productId")String productId,@Param("tableName")String tableName,@Param("filedName")String filedName, @Param("tableType")int tableType, @Param("sex")int sex);

	/**
	 * @Description 根据W端企业客户ID获取B端企业客户
	 * @param wtypeId
	 * @return
	 */
	public TCompany getByWtypeId(String wtypeId);

	/**
	 * @Description 获取所有客户唯一标识号(工商注册号)
	 * @param wtypeId
	 * @return
	 */
	List<String> findAllCardNumList();

	/**
	 * 根据扫描状态进行查询数据
	 * @param company
	 * @return
	 */
	List<TCompany> findListByScanTime(TCompany company);
	
	
	/**
	 * 根据扫描状态进行查询数据
	 * @param company
	 * @return
	 */
	List<TCompany> findListByScanTimeAndPushStatus(TCompany company);
	
	/**
	 * 根据扫描状态更新记录
	 * @param entity
	 * @return
	 */
    int updateByPushStatus(TCompany entity);

    /**
     * 通过条件查询记录
     * @param tCompany
     * @return
     */
	public List<TCompany> getByCondition(TCompany tCompany);

	/**
	 * 历史记录excel数据操作
	 * */
	public TCompany byCompanyAndContract(CompanyAndContract company);
}