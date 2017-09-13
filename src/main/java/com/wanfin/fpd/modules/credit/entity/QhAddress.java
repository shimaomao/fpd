package com.wanfin.fpd.modules.credit.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;


public class QhAddress extends DataEntity<QhAddress> {
	
	private static final long serialVersionUID = 1L;
	private String searchCode; //征信服务类型
	
	private String resId;		// qhzc_res_struct主键
	private String idNo;		// 证件号码
	private String idType;		// 证件类型
	private String name;		// 主体名称
	private String seqNo;		// 序列号
	private String mobileNo;		// 手机号码
	private String address;		// 地址
	private String sourceId;		// 来源代码
	private String province;		// 省（简称）
	private String cityInfo;		// 城市信息
	private String borough;		// 区（简称）
	private String fmtAddress;		// 格式化地址
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String buildingName;		// 楼盘名称
	private String buildingAddress;		// 楼盘地址
	private String houseArodAvgPrice;		// 楼盘周边均价
	private String houseAvgPrice;		// 楼盘均价
	private String state;		// 查询数据状态
	private String buildingType;		// 建筑类型
	private String dataBuildTime;		// 业务发生时间
	private String isMatch;		// 是否匹配
	private String addressPorperty;		// 地址属性
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private String organId;		// 公司ID
	
	public QhAddress() {
		super();
	}

	@Length(min=0, max=64, message="qhzc_res_struct主键长度必须介于 0 和 64 之间")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	@Length(min=0, max=64, message="证件号码长度必须介于 0 和 64 之间")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Length(min=0, max=2, message="证件类型长度必须介于 0 和 2 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=128, message="主体名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=24, message="序列号长度必须介于 0 和 24 之间")
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	@Length(min=0, max=24, message="手机号码长度必须介于 0 和 24 之间")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Length(min=0, max=128, message="地址长度必须介于 0 和 128 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=2, message="来源代码长度必须介于 0 和 2 之间")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=64, message="省（简称）长度必须介于 0 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="城市信息长度必须介于 0 和 64 之间")
	public String getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}
	
	@Length(min=0, max=64, message="区（简称）长度必须介于 0 和 64 之间")
	public String getBorough() {
		return borough;
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}
	
	@Length(min=0, max=128, message="格式化地址长度必须介于 0 和 128 之间")
	public String getFmtAddress() {
		return fmtAddress;
	}

	public void setFmtAddress(String fmtAddress) {
		this.fmtAddress = fmtAddress;
	}
	
	@Length(min=0, max=24, message="经度长度必须介于 0 和 24 之间")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=24, message="纬度长度必须介于 0 和 24 之间")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Length(min=0, max=32, message="楼盘名称长度必须介于 0 和 32 之间")
	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	
	@Length(min=0, max=128, message="楼盘地址长度必须介于 0 和 128 之间")
	public String getBuildingAddress() {
		return buildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		this.buildingAddress = buildingAddress;
	}
	
	@Length(min=0, max=16, message="楼盘周边均价长度必须介于 0 和 16 之间")
	public String getHouseArodAvgPrice() {
		return houseArodAvgPrice;
	}

	public void setHouseArodAvgPrice(String houseArodAvgPrice) {
		this.houseArodAvgPrice = houseArodAvgPrice;
	}
	
	@Length(min=0, max=16, message="楼盘均价长度必须介于 0 和 16 之间")
	public String getHouseAvgPrice() {
		return houseAvgPrice;
	}

	public void setHouseAvgPrice(String houseAvgPrice) {
		this.houseAvgPrice = houseAvgPrice;
	}
	
	@Length(min=0, max=2, message="查询数据状态长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=16, message="建筑类型长度必须介于 0 和 16 之间")
	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	
	@Length(min=0, max=20, message="业务发生时间长度必须介于 0 和 20 之间")
	public String getDataBuildTime() {
		return dataBuildTime;
	}

	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
	}
	
	@Length(min=0, max=2, message="是否匹配长度必须介于 0 和 2 之间")
	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}
	
	@Length(min=0, max=256, message="地址属性长度必须介于 0 和 256 之间")
	public String getAddressPorperty() {
		return addressPorperty;
	}

	public void setAddressPorperty(String addressPorperty) {
		this.addressPorperty = addressPorperty;
	}
	
	@Length(min=0, max=8, message="错误代码长度必须介于 0 和 8 之间")
	public String getErCode() {
		return erCode;
	}

	public void setErCode(String erCode) {
		this.erCode = erCode;
	}
	
	@Length(min=0, max=256, message="错误信息长度必须介于 0 和 256 之间")
	public String getErMsg() {
		return erMsg;
	}

	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}
	
	@Length(min=0, max=100, message="公司ID长度必须介于 0 和 100 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	
}
