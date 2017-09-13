package com.wanfin.fpd.modules.contract.vo;




/**
 * @Description [[_客户联系人信息_]] CsContract类
 * @author zzm
 * @date 2016-7-18 上午9:34:02 
 */
public class CsContactVo implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	private Integer id;		
	private Integer customerId;	//客户id
	private String name;	//姓名
	private String sex;		//性别
	private String relation;	//关系
	private String contactWay;	//联系方式
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	
}