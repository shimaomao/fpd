/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;

/**
 * 真实还款记录DAO接口
 * @author srf
 * @version 2016-03-29
 */
@MyBatisDao
public interface RepayRecordDao extends CrudDao<RepayRecord> {
	public List<RepayRecord> getOneByOther(RepayRecord repayRecord);//获取除该次还款记录外最新的一条记录
	public List<RepayRecord> findListByScan(RepayRecord repayRecord);
	public int updateByscanFlag(RepayRecord repayRecord);
	
	public List<RepayRecordVo> findRepayRecordVoPage(RepayRecordVo repayRecordVo);
}