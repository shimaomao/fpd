/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 旧利润表Entity
 * @author xzt
 * @version 2016-05-17
 */
public class JrjOldProfit extends DataEntity<JrjOldProfit> {
	
	private static final long serialVersionUID = 1L;
	private String companyName;  // 机构名称
	private String companyId;    //机构ID
	private String reportName;		// 报表名称
	private String rowOne;		// row_one
	private String rowTwo;		// row_two
	private String row1One;		// row1_one
	private String row1Two;		// row1_two
	private String row2One;		// row2_one
	private String row2Two;		// row2_two
	private String row3One;		// row3_one
	private String row3Two;		// row3_two
	private String row4One;		// row4_one
	private String row4Two;		// row4_two
	private String row5One;		// row5_one
	private String row5Two;		// row5_two
	private String row6One;		// row6_one
	private String row6Two;		// row6_two
	private String row7One;		// row7_one
	private String row7Two;		// row7_two
	private String row8One;		// row8_one
	private String row8Two;		// row8_two
	private String row9One;		// row9_one
	private String row9Two;		// row9_two
	private String row10One;		// row10_one
	private String row10Two;		// row10_two
	private String row11One;		// row11_one
	private String row11Two;		// row11_two	
	private String row12One;		// row12_one
	private String row12Two;		// row12_two
	private String row13One;		// row13_one
	private String row13Two;		// row13_two
	private String row14One;		// row13_one
	private String row14Two;		// row13_two
	private String row15One;		// row15_one
	private String row15Two;		// row15_two
	private String row16One;		// row16_one
	private String row16Two;		// row16_two
	private String row17One;		// row17_one
	private String row17Two;		// row17_two
	private String row18One;		// row18_one
	private String row18Two;		// row18_two
	private String row19One;		// row19_one
	private String row19Two;		// row19_two
	private String row20One;		// row20_one
	private String row20Two;		// row20_two
	private String row21One;		// row21_one
	private String row21Two;		// row21_two
	private String row22One;		// row21_one
	private String row22Two;		// row21_two
	private String row23One;		// row21_one
	private String row23Two;		// row21_two
	private String row24One;		// row21_one
	private String row24Two;		// row21_two
	private String row25One;		// row21_one
	private String row25Two;		// row21_two
	private String row26One;		// row21_one
	private String row26Two;		// row21_two
	private String row27One;		// row21_one
	private String row27Two;		// row21_two
	private String row28One;		// row21_one
	private String row28Two;		// row21_two	
	private String row29One;		// row21_one
	private String row29Two;		// row21_two
	
	private String principal;   //负责人
	
	private String statistics; //统计负责人
	
	private String fitOut;      //填表人
	
	private String submitDate;   //报出日期
	
	private String scanFlag;  //扫描标示  (0:未扫描  1:已经扫描)
	
	private String createTime;//程序暂时使用，不持久化到数据库
	
	private String pushStatus; //推送状态： 0未推送过  1：已推送过
	
	public JrjOldProfit() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getRow22One() {
		return row22One;
	}

	public void setRow22One(String row22One) {
		this.row22One = row22One;
	}

	public String getRow22Two() {
		return row22Two;
	}

	public void setRow22Two(String row22Two) {
		this.row22Two = row22Two;
	}

	public String getRow23One() {
		return row23One;
	}

	public void setRow23One(String row23One) {
		this.row23One = row23One;
	}

	public String getRow23Two() {
		return row23Two;
	}

	public void setRow23Two(String row23Two) {
		this.row23Two = row23Two;
	}

	public String getRow24One() {
		return row24One;
	}

	public void setRow24One(String row24One) {
		this.row24One = row24One;
	}

	public String getRow24Two() {
		return row24Two;
	}

	public void setRow24Two(String row24Two) {
		this.row24Two = row24Two;
	}

	public String getRow25One() {
		return row25One;
	}

	public void setRow25One(String row25One) {
		this.row25One = row25One;
	}

	public String getRow25Two() {
		return row25Two;
	}

	public void setRow25Two(String row25Two) {
		this.row25Two = row25Two;
	}

	public String getRow26One() {
		return row26One;
	}

	public void setRow26One(String row26One) {
		this.row26One = row26One;
	}

	public String getRow26Two() {
		return row26Two;
	}

	public void setRow26Two(String row26Two) {
		this.row26Two = row26Two;
	}

	public String getRow27One() {
		return row27One;
	}

	public void setRow27One(String row27One) {
		this.row27One = row27One;
	}

	public String getRow27Two() {
		return row27Two;
	}

	public void setRow27Two(String row27Two) {
		this.row27Two = row27Two;
	}

	public String getRow28One() {
		return row28One;
	}

	public void setRow28One(String row28One) {
		this.row28One = row28One;
	}

	public String getRow28Two() {
		return row28Two;
	}

	public void setRow28Two(String row28Two) {
		this.row28Two = row28Two;
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
	
	public String getRow29One() {
		return row29One;
	}

	public void setRow29One(String row29One) {
		this.row29One = row29One;
	}

	public String getRow29Two() {
		return row29Two;
	}

	public void setRow29Two(String row29Two) {
		this.row29Two = row29Two;
	}

	public String senData(){
		if("1".equals(this.getDelFlag())){ //删除数据
    		return "D" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
    				+ this.getRowOne() +"|" + this.getRowTwo() +"|"
    				+ this.getRow1One() +"|" + this.getRow1Two() +"|" 
    				+ this.getRow2One() +"|" + this.getRow2Two() +"|" 
    				+ this.getRow3One() +"|" + this.getRow3Two() +"|" 
    				+ this.getRow4One() +"|" + this.getRow4Two() +"|" 
    				+ this.getRow5One() +"|" + this.getRow5Two() +"|" 
    				+ this.getRow6One() +"|" + this.getRow6Two() +"|" 
    				+ this.getRow7One() +"|" + this.getRow7Two() +"|" 
    				+ this.getRow8One() +"|" + this.getRow8Two() +"|" 
    				+ this.getRow9One() +"|" + this.getRow9Two() +"|"
    				+ this.getRow10One() +"|" + this.getRow10Two() +"|" 
    				+ this.getRow11One() +"|" + this.getRow11Two() +"|"
    				+ this.getRow12One() +"|" + this.getRow12Two() +"|" 
    				+ this.getRow13One() +"|" + this.getRow13Two() +"|" 
    				+ this.getRow14One() +"|" + this.getRow14Two() +"|" 
    				+ this.getRow15One() +"|" + this.getRow15Two() +"|"
    				+ this.getRow16One() +"|" + this.getRow16Two() +"|" 
    				+ this.getRow17One() +"|" + this.getRow17Two() +"|"
    				+ this.getRow18One() +"|" + this.getRow18Two() +"|"
    				+ this.getRow19One() +"|" + this.getRow19Two() +"|" 
    				+ this.getRow20One() +"|" + this.getRow20Two() +"|"
    				+ this.getRow21One() +"|" + this.getRow21Two() +"|"
    				+ this.getRow22One() +"|" + this.getRow22Two() +"|"
    				+ this.getRow23One() +"|" + this.getRow23Two() +"|" 
    				+ this.getRow24One() +"|" + this.getRow24Two() +"|" 
    				+ this.getRow25One() +"|" + this.getRow25Two() +"|" 
    				+ this.getRow26One() +"|" + this.getRow26Two() +"|" 
    			    + this.getRow27One() +"|" + this.getRow27Two() +"|" 
    			    + this.getRow28One() +"|" + this.getRow28Two() +"|" 
    			    + this.getRow29One() +"|" + this.getRow29Two() +"|" 
    			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
    			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId();    		
    	}else{ //新增修改数据
    		if("0".equals(this.getPushStatus())){
    			return "A" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() +"|" + this.getRowTwo() +"|"
        				+ this.getRow1One() +"|" + this.getRow1Two() +"|" 
        				+ this.getRow2One() +"|" + this.getRow2Two() +"|" 
        				+ this.getRow3One() +"|" + this.getRow3Two() +"|" 
        				+ this.getRow4One() +"|" + this.getRow4Two() +"|" 
        				+ this.getRow5One() +"|" + this.getRow5Two() +"|" 
        				+ this.getRow6One() +"|" + this.getRow6Two() +"|" 
        				+ this.getRow7One() +"|" + this.getRow7Two() +"|" 
        				+ this.getRow8One() +"|" + this.getRow8Two() +"|" 
        				+ this.getRow9One() +"|" + this.getRow9Two() +"|"
        				+ this.getRow10One() +"|" + this.getRow10Two() +"|" 
        				+ this.getRow11One() +"|" + this.getRow11Two() +"|"
        				+ this.getRow12One() +"|" + this.getRow12Two() +"|" 
        				+ this.getRow13One() +"|" + this.getRow13Two() +"|" 
        				+ this.getRow14One() +"|" + this.getRow14Two() +"|" 
        				+ this.getRow15One() +"|" + this.getRow15Two() +"|"
        				+ this.getRow16One() +"|" + this.getRow16Two() +"|" 
        				+ this.getRow17One() +"|" + this.getRow17Two() +"|"
        				+ this.getRow18One() +"|" + this.getRow18Two() +"|"
        				+ this.getRow19One() +"|" + this.getRow19Two() +"|" 
        				+ this.getRow20One() +"|" + this.getRow20Two() +"|"
        				+ this.getRow21One() +"|" + this.getRow21Two() +"|"
        				+ this.getRow22One() +"|" + this.getRow22Two() +"|"
        				+ this.getRow23One() +"|" + this.getRow23Two() +"|" 
        				+ this.getRow24One() +"|" + this.getRow24Two() +"|" 
        				+ this.getRow25One() +"|" + this.getRow25Two() +"|" 
        				+ this.getRow26One() +"|" + this.getRow26Two() +"|" 
        			    + this.getRow27One() +"|" + this.getRow27Two() +"|" 
        			    + this.getRow28One() +"|" + this.getRow28Two() +"|"    
        			    + this.getRow29One() +"|" + this.getRow29Two() +"|" 
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
        			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId(); 
    		}else{
    			return "U" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() +"|" + this.getRowTwo() +"|"
        				+ this.getRow1One() +"|" + this.getRow1Two() +"|" 
        				+ this.getRow2One() +"|" + this.getRow2Two() +"|" 
        				+ this.getRow3One() +"|" + this.getRow3Two() +"|" 
        				+ this.getRow4One() +"|" + this.getRow4Two() +"|" 
        				+ this.getRow5One() +"|" + this.getRow5Two() +"|" 
        				+ this.getRow6One() +"|" + this.getRow6Two() +"|" 
        				+ this.getRow7One() +"|" + this.getRow7Two() +"|" 
        				+ this.getRow8One() +"|" + this.getRow8Two() +"|" 
        				+ this.getRow9One() +"|" + this.getRow9Two() +"|"
        				+ this.getRow10One() +"|" + this.getRow10Two() +"|" 
        				+ this.getRow11One() +"|" + this.getRow11Two() +"|"
        				+ this.getRow12One() +"|" + this.getRow12Two() +"|" 
        				+ this.getRow13One() +"|" + this.getRow13Two() +"|" 
        				+ this.getRow14One() +"|" + this.getRow14Two() +"|" 
        				+ this.getRow15One() +"|" + this.getRow15Two() +"|"
        				+ this.getRow16One() +"|" + this.getRow16Two() +"|" 
        				+ this.getRow17One() +"|" + this.getRow17Two() +"|"
        				+ this.getRow18One() +"|" + this.getRow18Two() +"|"
        				+ this.getRow19One() +"|" + this.getRow19Two() +"|" 
        				+ this.getRow20One() +"|" + this.getRow20Two() +"|"
        				+ this.getRow21One() +"|" + this.getRow21Two() +"|"
        				+ this.getRow22One() +"|" + this.getRow22Two() +"|"
        				+ this.getRow23One() +"|" + this.getRow23Two() +"|" 
        				+ this.getRow24One() +"|" + this.getRow24Two() +"|" 
        				+ this.getRow25One() +"|" + this.getRow25Two() +"|" 
        				+ this.getRow26One() +"|" + this.getRow26Two() +"|" 
        			    + this.getRow27One() +"|" + this.getRow27Two() +"|" 
        			    + this.getRow28One() +"|" + this.getRow28Two() +"|"  
        			    + this.getRow29One() +"|" + this.getRow29Two() +"|" 
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
        			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId(); 
    		}
    	}    
	}
}