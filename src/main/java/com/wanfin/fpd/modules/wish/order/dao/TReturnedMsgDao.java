/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 扣款明细表DAO接口
 *
 * @author lzj
 * @version 2017-08-29
 */
@MyBatisDao
public interface TReturnedMsgDao extends CrudDao<TReturnedMsg> {
	List<TReturnedMsg> findListByReturnMoneyIds(TReturnedMsg rm);

	Integer getMaxPeriods(@Param("loanContractId") String loanContractId);
}