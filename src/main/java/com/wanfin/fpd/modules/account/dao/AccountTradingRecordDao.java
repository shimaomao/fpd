package com.wanfin.fpd.modules.account.dao;
import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.account.entity.AccountTradingRecord;


/**
 * 资金明细DAO接口
 * @author lzj
 * @version 2016-03-14
 */
@MyBatisDao
public interface AccountTradingRecordDao extends CrudDao<AccountTradingRecord>{

}
