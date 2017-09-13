/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;
import com.wanfin.fpd.modules.sys.entity.User;

/**
 * 业务办理DAO接口
 * @author lx
 * @version 2016-03-18
 */
@MyBatisDao
public interface TLoanContractDao extends CrudDao<TLoanContract> {
	/**
	 * 更新状态
	 * @param id 合同ID
	 * @param status 更新的状态
	 * @return
	 */
	public int updateStatus(TLoanContract tLoanContract);
	
	
	
	/**
	 * 更新状态
	 * @param id 合同ID
	 * @param isdirectLoan 是否直接放款值
	 * @return
	 */
	public int updateIsdirectLoan(TLoanContract tLoanContract);
	
    /**
     * 根据推送状态更新记录
     * @param tLoanContract
     * @return
     */
	public int updateByPushStatus(TLoanContract tLoanContract);
	
	/**
	 * 获取
	 */
	public List<TLoanContract> findRefundList(TLoanContract tLoanContract);
	
	/**
	 * 更新对应的业务ID
	 * @author srf
	 * @date 20160413
	 * @param map
	 * @return
	 */
	public int updateBusinessId(Map<String, Object> map);

	/**
	 * @Description 获取逾期业务
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-6-8 上午11:01:30  
	 */
	public List<TLoanContract> findOverdueList(TLoanContract tLoanContract);
	
	/**
	 * @Description 获取未贷前调查的合同
	 * @param tLoanContract
	 * @return
	 * @author zzm
	 * @date 2016-6-13 下午3:11:30  
	 */
	public List<TLoanContract> findPreLoanIList(TLoanContract tLoanContract);
	
	
	
    /**
     * 扫描为推送的记录
     * @param tLoanContract
     * @return
     */
	public List<TLoanContract> findListByScanFlag(TLoanContract tLoanContract);
	
	 /**
     * 扫描为推送的记录
     * @param tLoanContract
     * @return
     */
	public List<TLoanContract> findListByScanFlagAndPushStatus(TLoanContract tLoanContract);
	
	
	 /**
     * 扫描为推送的记录 担保
     * @param tLoanContract
     * @return
     */
	public List<TLoanContract> findListDbByScanFlagAndPushStatus(TLoanContract tLoanContract);
	
	/**
     * 扫描为推送的记录 担保
     * @param tLoanContract
     * @return
     */
	public List<TLoanContract> findListDbByScanFlag(TLoanContract tLoanContract);


	/**
	 * @Description 根据订单号获取合同
	 * @param wtypeId
	 * @return
	 */
	public TLoanContract getByWtypeId(String wtypeId);
	/**
	 * @Description 获取已经生效的业务列表
	 * @param wtypeId
	 * @return
	 */
	public List<TLoanContract> findLoanStatusList(TLoanContract tLoanContract);
	
	
	public List<TLoanContract> getLoanListsByIds(@Param("id")String id);

	public void updateOrderLoan(TLoanContract tLoanContract);




	/**
	 * 根据合同编号查询业务信息
	 * */
	public TLoanContract contractByNumber(TEmployeeAndContract tEmployeeAndContract);


	//根据合同标号查询企业业务信息
	public TLoanContract companyByNumber(CompanyAndContract company);

	public List<TLoanContract> pushMoveContractlist(TLoanContract tLoanContract);



	public void updateMonveStatus(TLoanContract contract);



	public List<TLoanContract> pullMoveContractlist(TLoanContract tLoanContract);



	public List<TLoanContract> moveContractlist(TLoanContract tLoanContract);



	public String getLoanLastNo(@Param("productId")String productId);



	public List<TLoanContract> wishContractlist(TLoanContract tLoanContract);



	public List<TLoanContract> findBlockContractLists(
			TLoanContract tLoanContract);



	public void updateWishStatus(TLoanContract tLt);



	public List<TLoanContract> findRepayContractLists(
			TLoanContract tLoanContract);



	public List<Double> collectContract(TLoanContract tLoanContract);



	public List<TLoanContract> findWishAllList(TLoanContract tloanContract);



	public List<TLoanContract> getLoanMsgByCustomerId(TLoanContract tLoanContract);


 

}