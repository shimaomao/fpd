package com.wanfin.fpd.modules.sys.dao;
import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUser;



/**
 * 用户认证DAO接口
 * @author lx
 * @version 2016-10-25
 */
@MyBatisDao
public interface AuthenticatorDao extends CrudDao<AuthenticationUser> {

	AuthenticationUser findUserByName(String username);
	  
	
}
