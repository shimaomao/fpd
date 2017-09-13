package com.wanfin.fpd.modules.api.wiss.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.api.wiss.entity.Interaction;

@MyBatisDao
public interface InteractionDao extends CrudDao<Interaction>{

}
