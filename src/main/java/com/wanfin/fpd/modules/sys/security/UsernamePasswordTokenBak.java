/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @author ThinkGem
 * @version 2013-5-19
 */
public class UsernamePasswordTokenBak extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
    //验证码
	private String captcha;
	//移动登录
	private boolean mobileLogin;
	//自动登录(第三方)
	private boolean autoLogin;
	
	public UsernamePasswordTokenBak() {
		super();
	}

	public UsernamePasswordTokenBak(String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
	}
	//添加自动登录lzj
	public UsernamePasswordTokenBak(String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin,boolean autoLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.autoLogin=autoLogin;
	}
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}
	public boolean isAutoLogin() {
		return autoLogin;
	}

	
}