/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 资产负债表Entity
 * @author xzt
 * @version 2016-05-17
 */
public class JrjOldCost extends DataEntity<JrjOldCost> {
	
	private static final long serialVersionUID = 1L;
	private String companyName;  // 机构名称
	private String reportName;		// 报表名称
	
	
	private String rowOne;		
	private String rowTwo;		
	private String rowThree;
	private String rowFour;
	
	private String row1One;		
	private String row1Two;		
	private String row1Three;
	private String row1Four;
	
	private String row2One;		
	private String row2Two;		
	private String row2Three;
	private String row2Four;
	
	private String row3One;		
	private String row3Two;		
	private String row3Three;
	private String row3Four;
	
	private String row4One;		
	private String row4Two;		
	private String row4Three;
	private String row4Four;
	
	private String row5One;		
	private String row5Two;		
	private String row5Three;
	private String row5Four;
	
	private String row6One;		
	private String row6Two;		
	private String row6Three;
	private String row6Four;
	
	private String row7One;		
	private String row7Two;		
	private String row7Three;
	private String row7Four;
	
	private String row8One;		
	private String row8Two;		
	private String row8Three;
	private String row8Four;
	
	private String row9One;		
	private String row9Two;		
	private String row9Three;
	private String row9Four;
	
	private String row10One;		
	private String row10Two;		
	private String row10Three;
	private String row10Four;
	
	private String row11One;		
	private String row11Two;		
	private String row11Three;
	private String row11Four;
	
	private String row12One;		
	private String row12Two;		
	private String row12Three;
	private String row12Four;
	
	private String row13One;		
	private String row13Two;		
	private String row13Three;
	private String row13Four;
	
	private String row14One;		
	private String row14Two;		
	private String row14Three;
	private String row14Four;
	
	private String row15One;		
	private String row15Two;		
	private String row15Three;
	private String row15Four;
	
	private String row16One;		
	private String row16Two;		
	private String row16Three;
	private String row16Four;
	
	private String row17One;		
	private String row17Two;		
	private String row17Three;
	private String row17Four;
	
	private String row18One;		
	private String row18Two;		
	private String row18Three;
	private String row18Four;
	
	private String row19One;		
	private String row19Two;		
	private String row19Three;
	private String row19Four;
	
	private String row20One;		
	private String row20Two;		
	private String row20Three;
	private String row20Four;
	
	private String row21One;		
	private String row21Two;		
	private String row21Three;
	private String row21Four;
	
	
	private String principal;   //负责人
	
	private String statistics; //统计负责人
	
	private String fitOut;      //填表人
	
	private String submitDate;   //报出日期
	
	private String scanFlag;  //扫描标示  (0:未扫描  1:已经扫描)
	
	private String createTime;//程序暂时使用，不持久化到数据库
	 
	private String pushStatus; //推送状态 0：未推送过  1：已推送过
	
	private String companyId;  //机构ID

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getRowOne() {
		return rowOne;
	}

	public void setRowOne(String rowOne) {
		this.rowOne = rowOne;
	}

	public String getRowTwo() {
		return rowTwo;
	}

	public void setRowTwo(String rowTwo) {
		this.rowTwo = rowTwo;
	}

	public String getRowThree() {
		return rowThree;
	}

	public void setRowThree(String rowThree) {
		this.rowThree = rowThree;
	}

	public String getRowFour() {
		return rowFour;
	}

	public void setRowFour(String rowFour) {
		this.rowFour = rowFour;
	}

	public String getRow1One() {
		return row1One;
	}

	public void setRow1One(String row1One) {
		this.row1One = row1One;
	}

	public String getRow1Two() {
		return row1Two;
	}

	public void setRow1Two(String row1Two) {
		this.row1Two = row1Two;
	}

	public String getRow1Three() {
		return row1Three;
	}

	public void setRow1Three(String row1Three) {
		this.row1Three = row1Three;
	}

	public String getRow1Four() {
		return row1Four;
	}

	public void setRow1Four(String row1Four) {
		this.row1Four = row1Four;
	}

	public String getRow2One() {
		return row2One;
	}

	public void setRow2One(String row2One) {
		this.row2One = row2One;
	}

	public String getRow2Two() {
		return row2Two;
	}

	public void setRow2Two(String row2Two) {
		this.row2Two = row2Two;
	}

	public String getRow2Three() {
		return row2Three;
	}

	public void setRow2Three(String row2Three) {
		this.row2Three = row2Three;
	}

	public String getRow2Four() {
		return row2Four;
	}

	public void setRow2Four(String row2Four) {
		this.row2Four = row2Four;
	}

	public String getRow3One() {
		return row3One;
	}

	public void setRow3One(String row3One) {
		this.row3One = row3One;
	}

	public String getRow3Two() {
		return row3Two;
	}

	public void setRow3Two(String row3Two) {
		this.row3Two = row3Two;
	}

	public String getRow3Three() {
		return row3Three;
	}

	public void setRow3Three(String row3Three) {
		this.row3Three = row3Three;
	}

	public String getRow3Four() {
		return row3Four;
	}

	public void setRow3Four(String row3Four) {
		this.row3Four = row3Four;
	}

	public String getRow4One() {
		return row4One;
	}

	public void setRow4One(String row4One) {
		this.row4One = row4One;
	}

	public String getRow4Two() {
		return row4Two;
	}

	public void setRow4Two(String row4Two) {
		this.row4Two = row4Two;
	}

	public String getRow4Three() {
		return row4Three;
	}

	public void setRow4Three(String row4Three) {
		this.row4Three = row4Three;
	}

	public String getRow4Four() {
		return row4Four;
	}

	public void setRow4Four(String row4Four) {
		this.row4Four = row4Four;
	}

	public String getRow5One() {
		return row5One;
	}

	public void setRow5One(String row5One) {
		this.row5One = row5One;
	}

	public String getRow5Two() {
		return row5Two;
	}

	public void setRow5Two(String row5Two) {
		this.row5Two = row5Two;
	}

	public String getRow5Three() {
		return row5Three;
	}

	public void setRow5Three(String row5Three) {
		this.row5Three = row5Three;
	}

	public String getRow5Four() {
		return row5Four;
	}

	public void setRow5Four(String row5Four) {
		this.row5Four = row5Four;
	}

	public String getRow6One() {
		return row6One;
	}

	public void setRow6One(String row6One) {
		this.row6One = row6One;
	}

	public String getRow6Two() {
		return row6Two;
	}

	public void setRow6Two(String row6Two) {
		this.row6Two = row6Two;
	}

	public String getRow6Three() {
		return row6Three;
	}

	public void setRow6Three(String row6Three) {
		this.row6Three = row6Three;
	}

	public String getRow6Four() {
		return row6Four;
	}

	public void setRow6Four(String row6Four) {
		this.row6Four = row6Four;
	}

	public String getRow7One() {
		return row7One;
	}

	public void setRow7One(String row7One) {
		this.row7One = row7One;
	}

	public String getRow7Two() {
		return row7Two;
	}

	public void setRow7Two(String row7Two) {
		this.row7Two = row7Two;
	}

	public String getRow7Three() {
		return row7Three;
	}

	public void setRow7Three(String row7Three) {
		this.row7Three = row7Three;
	}

	public String getRow7Four() {
		return row7Four;
	}

	public void setRow7Four(String row7Four) {
		this.row7Four = row7Four;
	}

	public String getRow8One() {
		return row8One;
	}

	public void setRow8One(String row8One) {
		this.row8One = row8One;
	}

	public String getRow8Two() {
		return row8Two;
	}

	public void setRow8Two(String row8Two) {
		this.row8Two = row8Two;
	}

	public String getRow8Three() {
		return row8Three;
	}

	public void setRow8Three(String row8Three) {
		this.row8Three = row8Three;
	}

	public String getRow8Four() {
		return row8Four;
	}

	public void setRow8Four(String row8Four) {
		this.row8Four = row8Four;
	}

	public String getRow9One() {
		return row9One;
	}

	public void setRow9One(String row9One) {
		this.row9One = row9One;
	}

	public String getRow9Two() {
		return row9Two;
	}

	public void setRow9Two(String row9Two) {
		this.row9Two = row9Two;
	}

	public String getRow9Three() {
		return row9Three;
	}

	public void setRow9Three(String row9Three) {
		this.row9Three = row9Three;
	}

	public String getRow9Four() {
		return row9Four;
	}

	public void setRow9Four(String row9Four) {
		this.row9Four = row9Four;
	}

	public String getRow10One() {
		return row10One;
	}

	public void setRow10One(String row10One) {
		this.row10One = row10One;
	}

	public String getRow10Two() {
		return row10Two;
	}

	public void setRow10Two(String row10Two) {
		this.row10Two = row10Two;
	}

	public String getRow10Three() {
		return row10Three;
	}

	public void setRow10Three(String row10Three) {
		this.row10Three = row10Three;
	}

	public String getRow10Four() {
		return row10Four;
	}

	public void setRow10Four(String row10Four) {
		this.row10Four = row10Four;
	}

	public String getRow11One() {
		return row11One;
	}

	public void setRow11One(String row11One) {
		this.row11One = row11One;
	}

	public String getRow11Two() {
		return row11Two;
	}

	public void setRow11Two(String row11Two) {
		this.row11Two = row11Two;
	}

	public String getRow11Three() {
		return row11Three;
	}

	public void setRow11Three(String row11Three) {
		this.row11Three = row11Three;
	}

	public String getRow11Four() {
		return row11Four;
	}

	public void setRow11Four(String row11Four) {
		this.row11Four = row11Four;
	}

	public String getRow12One() {
		return row12One;
	}

	public void setRow12One(String row12One) {
		this.row12One = row12One;
	}

	public String getRow12Two() {
		return row12Two;
	}

	public void setRow12Two(String row12Two) {
		this.row12Two = row12Two;
	}

	public String getRow12Three() {
		return row12Three;
	}

	public void setRow12Three(String row12Three) {
		this.row12Three = row12Three;
	}

	public String getRow12Four() {
		return row12Four;
	}

	public void setRow12Four(String row12Four) {
		this.row12Four = row12Four;
	}

	public String getRow13One() {
		return row13One;
	}

	public void setRow13One(String row13One) {
		this.row13One = row13One;
	}

	public String getRow13Two() {
		return row13Two;
	}

	public void setRow13Two(String row13Two) {
		this.row13Two = row13Two;
	}

	public String getRow13Three() {
		return row13Three;
	}

	public void setRow13Three(String row13Three) {
		this.row13Three = row13Three;
	}

	public String getRow13Four() {
		return row13Four;
	}

	public void setRow13Four(String row13Four) {
		this.row13Four = row13Four;
	}

	public String getRow14One() {
		return row14One;
	}

	public void setRow14One(String row14One) {
		this.row14One = row14One;
	}

	public String getRow14Two() {
		return row14Two;
	}

	public void setRow14Two(String row14Two) {
		this.row14Two = row14Two;
	}

	public String getRow14Three() {
		return row14Three;
	}

	public void setRow14Three(String row14Three) {
		this.row14Three = row14Three;
	}

	public String getRow14Four() {
		return row14Four;
	}

	public void setRow14Four(String row14Four) {
		this.row14Four = row14Four;
	}

	public String getRow15One() {
		return row15One;
	}

	public void setRow15One(String row15One) {
		this.row15One = row15One;
	}

	public String getRow15Two() {
		return row15Two;
	}

	public void setRow15Two(String row15Two) {
		this.row15Two = row15Two;
	}

	public String getRow15Three() {
		return row15Three;
	}

	public void setRow15Three(String row15Three) {
		this.row15Three = row15Three;
	}

	public String getRow15Four() {
		return row15Four;
	}

	public void setRow15Four(String row15Four) {
		this.row15Four = row15Four;
	}

	public String getRow16One() {
		return row16One;
	}

	public void setRow16One(String row16One) {
		this.row16One = row16One;
	}

	public String getRow16Two() {
		return row16Two;
	}

	public void setRow16Two(String row16Two) {
		this.row16Two = row16Two;
	}

	public String getRow16Three() {
		return row16Three;
	}

	public void setRow16Three(String row16Three) {
		this.row16Three = row16Three;
	}

	public String getRow16Four() {
		return row16Four;
	}

	public void setRow16Four(String row16Four) {
		this.row16Four = row16Four;
	}

	public String getRow17One() {
		return row17One;
	}

	public void setRow17One(String row17One) {
		this.row17One = row17One;
	}

	public String getRow17Two() {
		return row17Two;
	}

	public void setRow17Two(String row17Two) {
		this.row17Two = row17Two;
	}

	public String getRow17Three() {
		return row17Three;
	}

	public void setRow17Three(String row17Three) {
		this.row17Three = row17Three;
	}

	public String getRow17Four() {
		return row17Four;
	}

	public void setRow17Four(String row17Four) {
		this.row17Four = row17Four;
	}

	public String getRow18One() {
		return row18One;
	}

	public void setRow18One(String row18One) {
		this.row18One = row18One;
	}

	public String getRow18Two() {
		return row18Two;
	}

	public void setRow18Two(String row18Two) {
		this.row18Two = row18Two;
	}

	public String getRow18Three() {
		return row18Three;
	}

	public void setRow18Three(String row18Three) {
		this.row18Three = row18Three;
	}

	public String getRow18Four() {
		return row18Four;
	}

	public void setRow18Four(String row18Four) {
		this.row18Four = row18Four;
	}

	public String getRow19One() {
		return row19One;
	}

	public void setRow19One(String row19One) {
		this.row19One = row19One;
	}

	public String getRow19Two() {
		return row19Two;
	}

	public void setRow19Two(String row19Two) {
		this.row19Two = row19Two;
	}

	public String getRow19Three() {
		return row19Three;
	}

	public void setRow19Three(String row19Three) {
		this.row19Three = row19Three;
	}

	public String getRow19Four() {
		return row19Four;
	}

	public void setRow19Four(String row19Four) {
		this.row19Four = row19Four;
	}

	public String getRow20One() {
		return row20One;
	}

	public void setRow20One(String row20One) {
		this.row20One = row20One;
	}

	public String getRow20Two() {
		return row20Two;
	}

	public void setRow20Two(String row20Two) {
		this.row20Two = row20Two;
	}

	public String getRow20Three() {
		return row20Three;
	}

	public void setRow20Three(String row20Three) {
		this.row20Three = row20Three;
	}

	public String getRow20Four() {
		return row20Four;
	}

	public void setRow20Four(String row20Four) {
		this.row20Four = row20Four;
	}

	public String getRow21One() {
		return row21One;
	}

	public void setRow21One(String row21One) {
		this.row21One = row21One;
	}

	public String getRow21Two() {
		return row21Two;
	}

	public void setRow21Two(String row21Two) {
		this.row21Two = row21Two;
	}

	public String getRow21Three() {
		return row21Three;
	}

	public void setRow21Three(String row21Three) {
		this.row21Three = row21Three;
	}

	public String getRow21Four() {
		return row21Four;
	}

	public void setRow21Four(String row21Four) {
		this.row21Four = row21Four;
	}

	

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public String getFitOut() {
		return fitOut;
	}

	public void setFitOut(String fitOut) {
		this.fitOut = fitOut;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
	public String senData(){
		if("1".equals(this.getDelFlag())){ //删除数据
    		return "D" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
    				+ this.getRowOne() +"|" + this.getRowTwo() +"|" + this.getRowThree()  + "|" + this.getRowFour() + "|"
    				+ this.getRow1One() +"|" + this.getRow1Two() +"|" + this.getRow1Three()  + "|" + this.getRow1Four() + "|"
    				+ this.getRow2One() +"|" + this.getRow2Two() +"|" + this.getRow2Three()  + "|" + this.getRow2Four() + "|"
    				+ this.getRow3One() +"|" + this.getRow3Two() +"|" + this.getRow3Three()  + "|" + this.getRow3Four() + "|"
    				+ this.getRow4One() +"|" + this.getRow4Two() +"|" + this.getRow4Three()  + "|" + this.getRow4Four() + "|"
    				+ this.getRow5One() +"|" + this.getRow5Two() +"|" + this.getRow5Three()  + "|" + this.getRow5Four() + "|"
    				+ this.getRow6One() +"|" + this.getRow6Two() +"|" + this.getRow6Three()  + "|" + this.getRow6Four() + "|"
    				+ this.getRow7One() +"|" + this.getRow7Two() +"|" + this.getRow7Three()  + "|" + this.getRow7Four() + "|"
    				+ this.getRow8One() +"|" + this.getRow8Two() +"|" + this.getRow8Three()  + "|" + this.getRow8Four() + "|"
    				+ this.getRow9One() +"|" + this.getRow9Two() +"|" + this.getRow9Three()  + "|" + this.getRow9Four() + "|"
    				+ this.getRow10One() +"|" + this.getRow10Two() +"|" + this.getRow10Three()  + "|" + this.getRow10Four() + "|"
    				+ this.getRow11One() +"|" + this.getRow11Two() +"|" + this.getRow11Three()  + "|" + this.getRow11Four() + "|"
    				+ this.getRow12One() +"|" + this.getRow12Two() +"|" + this.getRow12Three()  + "|" + this.getRow12Four() + "|"
    				+ this.getRow13One() +"|" + this.getRow13Two() +"|" + this.getRow13Three()  + "|" + this.getRow13Four() + "|"
    				+ this.getRow14One() +"|" + this.getRow14Two() +"|" + this.getRow14Three()  + "|" + this.getRow14Four() + "|"
    				+ this.getRow15One() +"|" + this.getRow15Two() +"|" + this.getRow15Three()  + "|" + this.getRow15Four() + "|"
    				+ this.getRow16One() +"|" + this.getRow16Two() +"|" + this.getRow16Three()  + "|" + this.getRow16Four() + "|"
    				+ this.getRow17One() +"|" + this.getRow17Two() +"|" + this.getRow17Three()  + "|" + this.getRow17Four() + "|"
    				+ this.getRow18One() +"|" + this.getRow18Two() +"|" + this.getRow18Three()  + "|" + this.getRow18Four() + "|"
    				+ this.getRow19One() +"|" + this.getRow19Two() +"|" + this.getRow19Three()  + "|" + this.getRow19Four() + "|"
    				+ this.getRow20One() +"|" + this.getRow20Two() +"|" + this.getRow20Three()  + "|" + this.getRow20Four() + "|"
    				+ this.getRow21One() +"|" + this.getRow21Two() +"|" + this.getRow21Three()  + "|" + this.getRow21Four() + "|"
    				
    			    + this.getPrincipal() + "|"+this.getStatistics()+"|" +this.getFitOut()+"|"+this.getSubmitDate()+"|"+this.getId();    		
    	}else{ //新增修改数据
    		if("0".equals(this.getPushStatus())){
    			return "A" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() +"|" + this.getRowTwo() +"|" + this.getRowThree()  + "|" + this.getRowFour() + "|"
        				+ this.getRow1One() +"|" + this.getRow1Two() +"|" + this.getRow1Three()  + "|" + this.getRow1Four() + "|"
        				+ this.getRow2One() +"|" + this.getRow2Two() +"|" + this.getRow2Three()  + "|" + this.getRow2Four() + "|"
        				+ this.getRow3One() +"|" + this.getRow3Two() +"|" + this.getRow3Three()  + "|" + this.getRow3Four() + "|"
        				+ this.getRow4One() +"|" + this.getRow4Two() +"|" + this.getRow4Three()  + "|" + this.getRow4Four() + "|"
        				+ this.getRow5One() +"|" + this.getRow5Two() +"|" + this.getRow5Three()  + "|" + this.getRow5Four() + "|"
        				+ this.getRow6One() +"|" + this.getRow6Two() +"|" + this.getRow6Three()  + "|" + this.getRow6Four() + "|"
        				+ this.getRow7One() +"|" + this.getRow7Two() +"|" + this.getRow7Three()  + "|" + this.getRow7Four() + "|"
        				+ this.getRow8One() +"|" + this.getRow8Two() +"|" + this.getRow8Three()  + "|" + this.getRow8Four() + "|"
        				+ this.getRow9One() +"|" + this.getRow9Two() +"|" + this.getRow9Three()  + "|" + this.getRow9Four() + "|"
        				+ this.getRow10One() +"|" + this.getRow10Two() +"|" + this.getRow10Three()  + "|" + this.getRow10Four() + "|"
        				+ this.getRow11One() +"|" + this.getRow11Two() +"|" + this.getRow11Three()  + "|" + this.getRow11Four() + "|"
        				+ this.getRow12One() +"|" + this.getRow12Two() +"|" + this.getRow12Three()  + "|" + this.getRow12Four() + "|"
        				+ this.getRow13One() +"|" + this.getRow13Two() +"|" + this.getRow13Three()  + "|" + this.getRow13Four() + "|"
        				+ this.getRow14One() +"|" + this.getRow14Two() +"|" + this.getRow14Three()  + "|" + this.getRow14Four() + "|"
        				+ this.getRow15One() +"|" + this.getRow15Two() +"|" + this.getRow15Three()  + "|" + this.getRow15Four() + "|"
        				+ this.getRow16One() +"|" + this.getRow16Two() +"|" + this.getRow16Three()  + "|" + this.getRow16Four() + "|"
        				+ this.getRow17One() +"|" + this.getRow17Two() +"|" + this.getRow17Three()  + "|" + this.getRow17Four() + "|"
        				+ this.getRow18One() +"|" + this.getRow18Two() +"|" + this.getRow18Three()  + "|" + this.getRow18Four() + "|"
        				+ this.getRow19One() +"|" + this.getRow19Two() +"|" + this.getRow19Three()  + "|" + this.getRow19Four() + "|"
        				+ this.getRow20One() +"|" + this.getRow20Two() +"|" + this.getRow20Three()  + "|" + this.getRow20Four() + "|"
        				+ this.getRow21One() +"|" + this.getRow21Two() +"|" + this.getRow21Three()  + "|" + this.getRow21Four() + "|"
        				
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|" +this.getFitOut()+"|"+this.getSubmitDate()+"|"+this.getId();     
    		}else{
    			return "U" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() +"|" + this.getRowTwo() +"|" + this.getRowThree()  + "|" + this.getRowFour() + "|"
        				+ this.getRow1One() +"|" + this.getRow1Two() +"|" + this.getRow1Three()  + "|" + this.getRow1Four() + "|"
        				+ this.getRow2One() +"|" + this.getRow2Two() +"|" + this.getRow2Three()  + "|" + this.getRow2Four() + "|"
        				+ this.getRow3One() +"|" + this.getRow3Two() +"|" + this.getRow3Three()  + "|" + this.getRow3Four() + "|"
        				+ this.getRow4One() +"|" + this.getRow4Two() +"|" + this.getRow4Three()  + "|" + this.getRow4Four() + "|"
        				+ this.getRow5One() +"|" + this.getRow5Two() +"|" + this.getRow5Three()  + "|" + this.getRow5Four() + "|"
        				+ this.getRow6One() +"|" + this.getRow6Two() +"|" + this.getRow6Three()  + "|" + this.getRow6Four() + "|"
        				+ this.getRow7One() +"|" + this.getRow7Two() +"|" + this.getRow7Three()  + "|" + this.getRow7Four() + "|"
        				+ this.getRow8One() +"|" + this.getRow8Two() +"|" + this.getRow8Three()  + "|" + this.getRow8Four() + "|"
        				+ this.getRow9One() +"|" + this.getRow9Two() +"|" + this.getRow9Three()  + "|" + this.getRow9Four() + "|"
        				+ this.getRow10One() +"|" + this.getRow10Two() +"|" + this.getRow10Three()  + "|" + this.getRow10Four() + "|"
        				+ this.getRow11One() +"|" + this.getRow11Two() +"|" + this.getRow11Three()  + "|" + this.getRow11Four() + "|"
        				+ this.getRow12One() +"|" + this.getRow12Two() +"|" + this.getRow12Three()  + "|" + this.getRow12Four() + "|"
        				+ this.getRow13One() +"|" + this.getRow13Two() +"|" + this.getRow13Three()  + "|" + this.getRow13Four() + "|"
        				+ this.getRow14One() +"|" + this.getRow14Two() +"|" + this.getRow14Three()  + "|" + this.getRow14Four() + "|"
        				+ this.getRow15One() +"|" + this.getRow15Two() +"|" + this.getRow15Three()  + "|" + this.getRow15Four() + "|"
        				+ this.getRow16One() +"|" + this.getRow16Two() +"|" + this.getRow16Three()  + "|" + this.getRow16Four() + "|"
        				+ this.getRow17One() +"|" + this.getRow17Two() +"|" + this.getRow17Three()  + "|" + this.getRow17Four() + "|"
        				+ this.getRow18One() +"|" + this.getRow18Two() +"|" + this.getRow18Three()  + "|" + this.getRow18Four() + "|"
        				+ this.getRow19One() +"|" + this.getRow19Two() +"|" + this.getRow19Three()  + "|" + this.getRow19Four() + "|"
        				+ this.getRow20One() +"|" + this.getRow20Two() +"|" + this.getRow20Three()  + "|" + this.getRow20Four() + "|"
        				+ this.getRow21One() +"|" + this.getRow21Two() +"|" + this.getRow21Three()  + "|" + this.getRow21Four() + "|"
        				
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|" +this.getFitOut()+"|"+this.getSubmitDate()+"|"+this.getId();   
    		}
    	}    
	}
	
}