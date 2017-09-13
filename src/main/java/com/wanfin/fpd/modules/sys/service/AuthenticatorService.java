package com.wanfin.fpd.modules.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorDao;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorRelDao;
import com.wanfin.fpd.modules.sys.dao.UserDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUser;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUserRel;
import com.wanfin.fpd.modules.sys.entity.User;




/**
 * 用户认证Service
 * @author lx
 * @version 2016-10-25
 */
@Service("authenticatorService")
@Transactional(readOnly = true)
public class AuthenticatorService extends BaseService{
	@Autowired
	private AuthenticatorDao authenticatorDao;
	@Autowired
	private AuthenticatorRelDao authenticatorRelDao;
	@Autowired
	private UserDao userDao;
	
	public Map<String,String> checkAuth(String authUsername, String autoToken) {
		Map<String,String> map=new HashMap<String, String>();
		AuthenticationUser authUser=authenticatorDao.findUserByName(authUsername);//根据用户名查找用户
		if(authUser !=null){
			      String tok=authUser.getToken();
			     /* if(autoToken.equals(tok)){*/
			    	  AuthenticationUserRel rel=authenticatorRelDao.findRelByAnuthenuserId(authUser.getId());
						if(rel!=null && StringUtils.isNotBlank(rel.getBuserId())){
							User user = userDao.get(rel.getBuserId());
							map.put("msg", "OK");
							map.put("username", user.getLoginName());
							map.put("password", user.getPassword());
						}else{
							map.put("msg", "没有B端登录权限");
							
						}
			     /*  }else{
			    	   map.put("msg", "认证token不通过");
			       }*/
				
		}else{
			  map.put("msg", "用户名不存在");
		}	
		return map;
	}

	
}