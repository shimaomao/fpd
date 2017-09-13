/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.collateral.entity.Building;
import com.wanfin.fpd.modules.collateral.entity.Car;
import com.wanfin.fpd.modules.collateral.entity.GongLand;
import com.wanfin.fpd.modules.collateral.entity.Gongyu;
import com.wanfin.fpd.modules.collateral.entity.House;
import com.wanfin.fpd.modules.collateral.entity.Machine;
import com.wanfin.fpd.modules.collateral.entity.Quanli;
import com.wanfin.fpd.modules.collateral.entity.SingleVilla;
import com.wanfin.fpd.modules.collateral.entity.TerraceVilla;
import com.wanfin.fpd.modules.collateral.entity.ZhuLand;
import com.wanfin.fpd.modules.collateral.entity.ZhuZhai;
import com.wanfin.fpd.modules.product.entity.TProduct;

/**
 * 抵押物品Entity 20160328 changed by srf
 * 
 * @author zzm
 * @version 2016-03-27
 */
public class MortgageContract extends DataEntity<MortgageContract> {

	private static final long serialVersionUID = 1L;

	private String address; // 存放地点
	private String name; // 名称
	private String unit; // 数量及单位
	private String worth; // 评估价值(元)
	private String businessTable; // 业务编号
	private Integer struts; // 抵押物的状态：1、未借出 2、审批中 3、已借出 4、已归还 5、未归还6、已解除
	private String businessId; // 关联业务的id
	private String contractNumber;  //关联合同业务编号  页面使用，不存入数据库字段
	private String number; // 抵押性质：二次、三次、四次...
	private Integer pledgeType; // 抵押物类型
	
	private Date apply_time;    //办理日期
	private Date abort_time;//抵押截止日期
	private String mortgage_amount;//抵押金额
	private String username;//抵押物有权人
	private String mortgage_rate;//抵押率
	private String rvalua_organ;//评估机构
	private String customerName;//客户名称
	
	// add by shirf 20160327
	private Building building; // 商铺写字楼
	private GongLand GongLand; // 工业\农业用地信息
	private Gongyu Gongyu; // 公寓信息
	private House House; // 住宅
	private SingleVilla SingleVilla;// 独栋别墅
	private TerraceVilla TerraceVilla;// 联排别墅
	private ZhuLand ZhuLand; // 商宅用地
	private ZhuZhai ZhuZhai; // 住宅信息
	private Car car;//车辆信息
	private Machine machine;//机器设备
	private Quanli quanli;//无形权力.林权

	
    private String scanFlag; //监管系统扫描标示 0:位扫描  1:已经扫描
    private String companyName;  //不持久化
    private String pushStatus;  
	
	private TProduct product;

	public MortgageContract() {
		super();
	}

	public MortgageContract(String id) {
		super(id);
	}

	
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Length(min = 0, max = 255, message = "存放地点长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 0, max = 255, message = "名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "数量及单位长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Length(min = 0, max = 255, message = "评估价值(元)长度必须介于 0 和 255 之间")
	public String getWorth() {
		return worth;
	}

	public void setWorth(String worth) {
		this.worth = worth;
	}

	@Length(min = 0, max = 64, message = "关联业务表的名称长度必须介于 0 和 64 之间")
	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public Integer getStruts() {
		return struts;
	}

	public void setStruts(Integer struts) {
		this.struts = struts;
	}

	@Length(min = 0, max = 64, message = "关联业务的id长度必须介于 0 和 64 之间")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Length(min = 0, max = 64, message = "抵押性质长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(Integer pledgeType) {
		this.pledgeType = pledgeType;
	}


	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public GongLand getGongLand() {
		return GongLand;
	}

	public void setGongLand(GongLand gongLand) {
		GongLand = gongLand;
	}

	public Gongyu getGongyu() {
		return Gongyu;
	}

	public void setGongyu(Gongyu gongyu) {
		Gongyu = gongyu;
	}

	public House getHouse() {
		return House;
	}

	public void setHouse(House house) {
		House = house;
	}

	public SingleVilla getSingleVilla() {
		return SingleVilla;
	}

	public void setSingleVilla(SingleVilla singleVilla) {
		SingleVilla = singleVilla;
	}

	public TerraceVilla getTerraceVilla() {
		return TerraceVilla;
	}

	public void setTerraceVilla(TerraceVilla terraceVilla) {
		TerraceVilla = terraceVilla;
	}

	public ZhuLand getZhuLand() {
		return ZhuLand;
	}

	public void setZhuLand(ZhuLand zhuLand) {
		ZhuLand = zhuLand;
	}

	public ZhuZhai getZhuZhai() {
		return ZhuZhai;
	}

	public void setZhuZhai(ZhuZhai zhuZhai) {
		ZhuZhai = zhuZhai;
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

	public String getMortgage_amount() {
		return mortgage_amount;
	}

	public void setMortgage_amount(String mortgage_amount) {
		this.mortgage_amount = mortgage_amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMortgage_rate() {
		return mortgage_rate;
	}

	public void setMortgage_rate(String mortgage_rate) {
		this.mortgage_rate = mortgage_rate;
	}

	public String getRvalua_organ() {
		return rvalua_organ;
	}

	public void setRvalua_organ(String rvalua_organ) {
		this.rvalua_organ = rvalua_organ;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
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
	public Quanli getQuanli() {
		return quanli;
	}

	public void setQuanli(Quanli quanli) {
		this.quanli = quanli;
	}

	public String sendData(){	
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		if(this.getApply_time() != null && !"".equals(this.getApply_time())){
			date = formatter.format(this.getApply_time());
		 }
		  String pleType = "5";	
		
		   if("1".equals(this.getDelFlag())){ //判断是否为删除
			   return "D"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"1"+"|"+pleType+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
				       +this.getAddress()+"|"+date+"|"+this.getId();
	      }else{
	    	  if("0".equals(this.getPushStatus())){
	    		  return "A"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"1"+"|"+this.getPledgeType()+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
					       +this.getAddress()+"|"+date+"|"+this.getId();
	    	  }else{
	    		  return "U"+"|"+this.getCompanyName()+"|"+this.getContractNumber()+"|"+"1"+"|"+this.getPledgeType()+"|"+this.getName()+"|"+""+"|"+this.getWorth()+"|"+this.getUsername()+"|"
					       +this.getAddress()+"|"+date+"|"+this.getId();
	    	   }
	    }		   
	}
	
	
}