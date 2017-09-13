package com.wanfin.fpd.modules.sys.entity;

import com.wanfin.fpd.common.persistence.DataEntity;


/**
 * 用户认证Entity
 * @author lx
 * @version 2016-10-24
 */
public class AuthenticationUser extends DataEntity<AuthenticationUser>{

	private static final long serialVersionUID = 1L;
	
	private String  username;              //用户名
	private String  name;                  //真实姓名
	private String  password;              //密码
	private String  mobile;                //手机
	private String  email;                 //邮件
	private String  token;                 //令牌
	private String  loginTime;            //登录时间
	

	public AuthenticationUser() {
		super();
	}

	public AuthenticationUser(String id) {
		super();
		this.id = id;
	}

	public AuthenticationUser(AuthenticationUser authuser) {
		super();
		if(authuser != null){
			this.id = authuser.getId();
			this.username = authuser.getUsername();
			this.name = authuser.getName();
			this.password = authuser.getPassword();
			this.mobile = authuser.getMobile();
			this.email = authuser.getEmail();
			this.token = authuser.getToken();
		}
	}
	
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


}