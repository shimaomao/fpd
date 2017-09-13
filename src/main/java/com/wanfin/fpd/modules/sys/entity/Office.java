/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.entity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.TreeEntity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 机构Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@ApiModel(value="机构")
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
//	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	@ApiModelProperty(value="归属区域", dataType="Area", notes="归属区域", hidden=false, required=true)
	private Area area;		// 归属区域
	@ApiModelProperty(value="机构编码", dataType="String", notes="机构编码", hidden=false, required=true)
	private String code; 	// 机构编码
//	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	@ApiModelProperty(value="机构类型", dataType="String", notes="机构类型", hidden=false, required=true)
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	@ApiModelProperty(value="机构等级", dataType="String", notes="机构等级", hidden=false, required=true)
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	@ApiModelProperty(value="联系地址", dataType="String", notes="联系地址", hidden=false, required=true)
	private String address; // 联系地址
	@ApiModelProperty(value="邮政编码", dataType="String", notes="邮政编码", hidden=false, required=true)
	private String zipCode; // 邮政编码
	@ApiModelProperty(value="负责人", dataType="String", notes="负责人", hidden=false, required=true)
	private String master; 	// 负责人
	@ApiModelProperty(value="电话", dataType="String", notes="电话", hidden=false, required=true)
	private String phone; 	// 电话
	@ApiModelProperty(value="传真", dataType="String", notes="传真", hidden=false, required=true)
	private String fax; 	// 传真
	@ApiModelProperty(value="邮箱", dataType="String", notes="邮箱", hidden=false, required=true)
	private String email; 	// 邮箱
	@ApiModelProperty(value="是否可用", dataType="String", notes="是否可用", hidden=false, required=true)
	private String useable;//是否可用
	@ApiModelProperty(value="公司类型", dataType="String", notes="公司类型", hidden=false, required=true)
	private String primaryPerson;//公司类型   1担保   2、贷款
	@ApiModelProperty(value="副负责人", dataType="String", notes="副负责人", hidden=false, required=true)
	private User deputyPerson;//副负责人
	@ApiModelProperty(value="快速添加子部门", dataType="String", notes="快速添加子部门", hidden=false, required=true)
	private List<String> childDeptList;//快速添加子部门
	@ApiModelProperty(value="机构唯一标识", dataType="String", notes="机构唯一标识", hidden=false, required=true)
	private String uniqueNumber;//机构唯一标识，用于自定义建表用
	@ApiModelProperty(value="合同编号的设定规则", dataType="String", notes="合同编号的设定规则", hidden=false, required=true)
	private String contractNumber;//合同编号的设定规则
	
	
	
	@ApiModelProperty(value="开户名", dataType="String", notes="开户名", hidden=false, required=true)
	private String accoName; 	// 公司法人开户名
	@ApiModelProperty(value="开户行", dataType="String", notes="开户行", hidden=false, required=true)
	private String bank; 	// 公司法人开户行
	@ApiModelProperty(value="账户", dataType="String", notes="账户", hidden=false, required=true)
	private String account; 	// 公司法人账户

	private String bsys;//系统生成数据：null、默认；1、系统生成；2、其它
	private String wtypeId;//业务主键：W端订单号
	private Map<String,String> userMap;//初始化借用属性
	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(String primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

//	@JsonBackReference
//	@NotNull
	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}

	@NotNull
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
//
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
	
	

	public String getAccoName() {
		return accoName;
	}

	public void setAccoName(String accoName) {
		this.accoName = accoName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	
	
	@Override
	public String toString() {
		return name;
	}

	public String getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getWtypeId() {
		return wtypeId;
	}

	public void setWtypeId(String wtypeId) {
		this.wtypeId = wtypeId;
	}

	public String getBsys() {
		return bsys;
	}

	public void setBsys(String bsys) {
		this.bsys = bsys;
	}

	public Map<String, String> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}
}