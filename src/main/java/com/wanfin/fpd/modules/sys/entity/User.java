/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.common.supcan.annotation.treelist.cols.SupCol;
import com.wanfin.fpd.common.utils.Collections3;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;
import com.wanfin.fpd.common.utils.excel.fieldtype.RoleListType;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-12-05
 */
@ApiModel(value="用户")
public class User extends ApiEntity<User> {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="归属公司", dataType="Office", notes="归属公司", hidden=false, required=true)
	private Office company;	// 归属公司
	@ApiModelProperty(value="归属部门", dataType="Office", notes="归属部门", hidden=false, required=true)
	private Office office;	// 归属部门
	@ApiModelProperty(value="登录名", dataType="String", notes="登录名", hidden=false, required=true)
	private String loginName;// 登录名
	@ApiModelProperty(value="密码", dataType="String", notes="密码", hidden=false, required=true)
	private String password;// 密码
	@ApiModelProperty(value="工号", dataType="String", notes="工号", hidden=false, required=true)
	private String no;		// 工号
	@ApiModelProperty(value="姓名", dataType="String", notes="姓名", hidden=false, required=true)
	private String name;	// 姓名
	@ApiModelProperty(value="邮箱", dataType="String", notes="邮箱", hidden=false)
	private String email;	// 邮箱
	@ApiModelProperty(value="电话", dataType="String", notes="电话", hidden=false)
	private String phone;	// 电话
	@ApiModelProperty(value="手机", dataType="String", notes="手机", hidden=false)
	private String mobile;	// 手机
	@ApiModelProperty(value="用户类型", dataType="String", notes="用户类型", hidden=false, required=true)
	private String userType;// 用户类型
	@ApiModelProperty(value="最后登陆", dataType="String", notes="最后登陆", hidden=false)
	private String loginIp;	// 最后登陆IP
	@ApiModelProperty(value="最后登陆日期", dataType="String", notes="最后登陆日期", hidden=false)
	private Date loginDate;	// 最后登陆日期
	@ApiModelProperty(value="是否允许登陆", dataType="String", notes="是否允许登陆", hidden=false)
	private String loginFlag;	// 是否允许登陆
	@ApiModelProperty(value="头像", dataType="String", notes="头像", hidden=false)
	private String photo;	// 头像

	@ApiModelProperty(value="原登录名", dataType="String", notes="原登录名", hidden=true)
	private String oldLoginName;// 原登录名
	@ApiModelProperty(value="新密码", dataType="String", notes="新密码", hidden=true)
	private String newPassword;	// 新密码

	@ApiModelProperty(value="上次登陆IP", dataType="String", notes="上次登陆IP", hidden=true)
	private String oldLoginIp;	// 上次登陆IP
	@ApiModelProperty(value="上次登陆日期", dataType="Date", notes="上次登陆日期", hidden=true)
	private Date oldLoginDate;	// 上次登陆日期

	@ApiModelProperty(value="根据角色查询用户条件", dataType="Role", notes="根据角色查询用户条件", hidden=true)
	private Role role;	// 根据角色查询用户条件
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	@ApiModelProperty(value="上次登陆IP", dataType="String", notes="上次登陆IP", hidden=true)
	private String wtypeId;//业务主键：W端订单号
	public User() {
		super();
		this.loginFlag = Global.YES;
	}
	
	public User(String id){
		super(id);
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public User(Role role){
		super();
		this.role = role;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@SupCol(isUnique="true", isHide="true")
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

//	@JsonIgnore
	@NotNull(message="归属公司不能为空")
	@ExcelField(title="归属公司", align=2, sort=20)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
//	@JsonIgnore
	@NotNull(message="归属部门不能为空")
	@ExcelField(title="归属部门", align=2, sort=25)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	@ExcelField(title="登录名", align=2, sort=30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

//	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}
	
	@Length(min=1, max=100, message="工号长度必须介于 1 和 100 之间")
	@ExcelField(title="工号", align=2, sort=45)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message="邮箱格式不正确")
	@Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title="邮箱", align=1, sort=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
	@ExcelField(title="电话", align=2, sort=60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200, message="手机长度必须介于 1 和 200 之间")
	@ExcelField(title="手机", align=2, sort=70)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title="备注", align=1, sort=900)
	public String getRemarks() {
		return remarks;
	}
	
	@Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
	@ExcelField(title="用户类型", align=2, sort=80, dictType="sys_user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@ExcelField(title="创建时间", type=0, align=1, sort=90)
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title="最后登录IP", type=1, align=1, sort=100)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登录日期", type=1, align=1, sort=110)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null){
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null){
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	@ExcelField(title="拥有角色", align=1, sort=800, fieldType=RoleListType.class)
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public boolean isZadmin(){
		return isZadmin(this.roleList);
	}
	public static boolean isZadmin(List<Role> roles){
		if((roles != null) && (roles.size() > 0)){
			for (Role role : roles) {
				if((role.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_XD) || (role.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_DB)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAdmin(){
		return isAdmin(this.id);
	}
	public static boolean isAdmin(String id){
		return id != null && (Cons.SysDF.DF_SUPER_USER).equals(id);
	}

	@Override
	public String toString() {
		return id;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}
}