/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.pledge.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.collateral.entity.Car;
import com.wanfin.fpd.modules.collateral.entity.Cunhuo;
import com.wanfin.fpd.modules.collateral.entity.Guquan;
import com.wanfin.fpd.modules.collateral.entity.Machine;
import com.wanfin.fpd.modules.collateral.entity.Quanli;
import com.wanfin.fpd.modules.collateral.entity.YongLand;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 质押信息Entity
 * @author zzm
 * @version 2016-03-28
 */
public class PledgeContract extends DataEntity<PledgeContract> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 存放地址
	private String name;		// 质押物名称
	private Integer pledgeType;		// 抵押物类型
	private Integer struts;		// 抵押物的状态
	private String unit;		// 数量
	private Date apply_time;       //'办理日期' ,
	private Date abort_time;       //质押截止日期' ,
	private String pledge_amount;    //质押金额' ,
	private String username;         //质押物所有人' ,
	private String worth;		// 价值
	private String businessTable;		// 关联业务表的名称
	private String businessId;		// 关联业务的id
	private String contractNumber;  //关联合同业务编号  页面使用，不存入数据库字段
	private TProduct product;
	private String customerName;
	
	private String scanFlag; //监管系统扫描标示 0：未扫描 1：已扫描
	private String companyName; //不持久化
	private String pushStatus; 
	
	//以下用于对应页面表单，不与数据库关联
	private Car car;
	private Cunhuo cunhuo;
	private Guquan guquan;
	private Machine machine;
	private Quanli quanli;
	private YongLand yongLand;
	public PledgeContract() {
		super();
	}

	public PledgeContract(String id){
		super(id);
	}

	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="质押名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(Integer pledgeType) {
		this.pledgeType = pledgeType;
	}
	
	public Integer getStruts() {
		return struts;
	}

	public void setStruts(Integer struts) {
		this.struts = struts;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=255, message="价值长度必须介于 0 和 255 之间")
	public String getWorth() {
		return worth;
	}

	public void setWorth(String worth) {
		this.worth = worth;
	}
	
	@Length(min=0, max=64, message="关联业务表的名称长度必须介于 0 和 64 之间")
	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}
	
	@Length(min=0, max=64, message="关联业务的id长度必须介于 0 和 64 之间")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Cunhuo getCunhuo() {
		return cunhuo;
	}

	public void setCunhuo(Cunhuo cunhuo) {
		this.cunhuo = cunhuo;
	}

	public Guquan getGuquan() {
		return guquan;
	}

	public void setGuquan(Guquan guquan) {
		this.guquan = guquan;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Quanli getQuanli() {
		return quanli;
	}

	public void setQuanli(Quanli quanli) {
		this.quanli = quanli;
	}

	public YongLand getYongLand() {
		return yongLand;
	}

	public void setYongLand(YongLand yongLand) {
		this.yongLand = yongLand;
	}

	public TProduct getProduct() {
		return product;
	}

	public void setProduct(TProduct product) {
		this.product = product;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public Date getAbort_time() {
		return abort_time;
	}

	public void setAbort_time(Date abort_time) {
		this.abort_time = abort_time;
	}

	public String getPledge_amount() {
		return pledge_amount;
	}

	public void setPledge_amount(String pledge_amount) {
		this.pledge_amount = pledge_amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String sendData(){
		  String pleType = "";
		  if("3".equals(this.getPledgeType())){
			  pleType = "1";
		  }else{
			  pleType = "5";
		  }
		  SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  String date = "";
		  if(this.getApply_time() != null && !"".equals(this.getApply_time())){
			date = formatter.format(this.getApply_time());
		  }
		
		   if("1".equals(this.getDelFlag())){ //判断是否为删除
			   return "D"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"2"+"|"+pleType+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
				       +this.getAddress()+"|"+date+"|"+this.getId();
	      }else{
	    	  if("0".equals(this.getPushStatus())){
	    		  return "A"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"2"+"|"+this.getPledgeType()+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
					       +this.getAddress()+"|"+date+"|"+this.getId();
	    	  }else{
	    		  return "U"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"2"+"|"+this.getPledgeType()+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
					       +this.getAddress()+"|"+date+"|"+this.getId();
	    	   }
	    }		   
	}
	
	
}