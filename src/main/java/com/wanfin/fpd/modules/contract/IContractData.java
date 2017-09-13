/**  
 * @Project jeesite 
 * @Title IContract.java
 * @Package com.wanfin.fpd.modules.contract
 * @Description [[_合同接口数据格式接口_]]文件 
 * @author Chenh
 * @date 2016年3月30日 下午4:16:23 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.contract;

import java.util.Map;


/**
 * @Description [[_合同接口数据格式接口_]] IContractData类
 * @author Chenh
 * @date 2016年3月30日 下午4:16:23 
 */
public interface IContractData<T> {
	/**
	 * @Description 合同模板数据格式
	 * 要求：Map<K, V>的V要求只能为java基础类
	 * @param entity
	 * @return
	 * @author Chenh 
	 * @date 2016年3月31日 下午12:56:38
	 */
	public Map<String, Object> getParams(T entity);
}
