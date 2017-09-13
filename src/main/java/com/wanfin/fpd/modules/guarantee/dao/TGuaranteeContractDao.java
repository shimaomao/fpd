/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.guarantee.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;

/**
 * 担保合同DAO接口
 * @author zzm
 * @version 2016-03-24
 */
@MyBatisDao
public interface TGuaranteeContractDao extends CrudDao<TGuaranteeContract> {	
	
	List<TGuaranteeContract> findListBybusinessId(TGuaranteeContract contract);
	
	List<TGuaranteeContract> findListByscanFlag(TGuaranteeContract temp);
	public int updateByscanFlag(TGuaranteeContract temp);
	
}