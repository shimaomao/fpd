/**  
 * @Project jeesite 
 * @Title IContract.java
 * @Package com.wanfin.fpd.modules.contract
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年3月30日 下午4:16:23 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.contract;


/**
 * @Description [[合同接口]] IContract类
 * @author Chenh
 * @date 2016年3月30日 下午4:16:23 
 */
public interface IContract {

	/**
	 * 业务合同类型唯一标示
	 */
	public static final class IContractUkey{
		/** 贷款合同 **/
		public static final String LOAN = "1";
		/** 授信合同 **/
		public static final String LOOPLOAN = "2";
		/** 展期合同 **/
		public static final String EXTEND = "4";
		/** 担保合同 **/
		public static final String GUARANTEE = "6";
	}
	
	/**
	 * @Description 获取合同ID
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 下午4:19:38
	 */
	public String getId();

	/**
	 * @Description 获取合同类型唯一标示(使用@Transient标识，区分数据库属性)
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 下午4:19:38
	 */
	public String getUkey();
}
