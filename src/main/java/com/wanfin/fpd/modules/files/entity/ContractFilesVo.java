package com.wanfin.fpd.modules.files.entity;

public class ContractFilesVo {
	private String id;//24143,
	private String productId;//4626
	private String name;//信用报告
	private String path;//"/upload_file_dir/archive/2017-02-09/20170209-11-23-44_988.jpg",
	private String url;//"/upload_file_dir/archive/2017-02-09/20170209-11-23-44_988.jpg",
	private String childPath;//"/data0/apache-tomcat-7.0.62/webapps/ROOT/upload_file_dir/archive/2017-02-09/20170209-11-23-44_988",
	
	private int childSize;//": "2",
	private int rowCount;//": 3,
	
	private int dealed;	//是否被处理过，理财马赛克处理用
	
	public ContractFilesVo() {
		super();
	}
	public ContractFilesVo(TContractFiles tContractFiles) {
		super();
		this.id = tContractFiles.getId();
		this.productId = tContractFiles.getTaskId();
		this.name = tContractFiles.getTitle(); 
		this.path = tContractFiles.getFilePath();
		this.url = tContractFiles.getFilePath();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getChildSize() {
		return childSize;
	}
	public void setChildSize(int childSize) {
		this.childSize = childSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getChildPath() {
		return childPath;
	}
	public void setChildPath(String childPath) {
		this.childPath = childPath;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getDealed() {
		return dealed;
	}
	public void setDealed(int dealed) {
		this.dealed = dealed;
	}
	
}
