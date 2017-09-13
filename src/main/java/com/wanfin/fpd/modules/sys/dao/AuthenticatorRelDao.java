package com.wanfin.fpd.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUserRel;


/**
 * 用户认证权限DAO接口
 * @author lx
 * @version 2016-10-25
 */
@MyBatisDao
public interface AuthenticatorRelDao extends CrudDao<AuthenticationUserRel> {

	AuthenticationUserRel getBid(String id);

	AuthenticationUserRel findRelByAnuthenuserId(String anuthenuserId);
	
	AuthenticationUserRel findAuthByBuserId(@Param("buserId")String buserId);
}
