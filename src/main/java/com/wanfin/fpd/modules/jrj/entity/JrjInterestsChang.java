/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 利润表Entity
 * @author xzt
 * @version 2016-05-17
 */
public class JrjInterestsChang extends DataEntity<JrjInterestsChang> {
	
	private static final long serialVersionUID = 1L;
	private String companyName;  // 机构名称
	private String companyId;    //机构ID
	private String reportName;		// 报表名称
	private String rowOne;		// row_one
	private String rowTwo;		// row_two
	private String rowThree;		
	private String rowFour;	
	private String rowFive;	
	private String rowSix;	
	private String rowSeven;
	private String rowEight;
	private String rowNine;
	private String rowTen;
	private String rowEleven;
	private String rowTwelve;
	private String rowThirteen;
	private String rowFourteen;
	private String rowFifteen;
	private String rowSixteen;
	
	private String row1One;		// row_one
	private String row1Two;		// row_two
	private String row1Three;		
	private String row1Four;	
	private String row1Five;	
	private String row1Six;	
	private String row1Seven;
	private String row1Eight;
	private String row1Nine;
	private String row1Ten;
	private String row1Eleven;
	private String row1Twelve;
	private String row1Thirteen;
	private String row1Fourteen;
	private String row1Fifteen;
	private String row1Sixteen;
	
	private String row2One;		// row_one
	private String row2Two;		// row_two
	private String row2Three;		
	private String row2Four;	
	private String row2Five;	
	private String row2Six;	
	private String row2Seven;
	private String row2Eight;
	private String row2Nine;
	private String row2Ten;
	private String row2Eleven;
	private String row2Twelve;
	private String row2Thirteen;
	private String row2Fourteen;
	private String row2Fifteen;
	private String row2Sixteen;
	
	private String row3One;		// row_one
	private String row3Two;		// row_two
	private String row3Three;		
	private String row3Four;	
	private String row3Five;	
	private String row3Six;	
	private String row3Seven;
	private String row3Eight;
	private String row3Nine;
	private String row3Ten;
	private String row3Eleven;
	private String row3Twelve;
	private String row3Thirteen;
	private String row3Fourteen;
	private String row3Fifteen;
	private String row3Sixteen;
	
	private String row4One;		// row_one
	private String row4Two;		// row_two
	private String row4Three;		
	private String row4Four;	
	private String row4Five;	
	private String row4Six;	
	private String row4Seven;
	private String row4Eight;
	private String row4Nine;
	private String row4Ten;
	private String row4Eleven;
	private String row4Twelve;
	private String row4Thirteen;
	private String row4Fourteen;
	private String row4Fifteen;
	private String row4Sixteen;
	
	private String row5One;		// row_one
	private String row5Two;		// row_two
	private String row5Three;		
	private String row5Four;	
	private String row5Five;	
	private String row5Six;	
	private String row5Seven;
	private String row5Eight;
	private String row5Nine;
	private String row5Ten;
	private String row5Eleven;
	private String row5Twelve;
	private String row5Thirteen;
	private String row5Fourteen;
	private String row5Fifteen;
	private String row5Sixteen;
	
	
	private String row6One;		// row_one
	private String row6Two;		// row_two
	private String row6Three;		
	private String row6Four;	
	private String row6Five;	
	private String row6Six;	
	private String row6Seven;
	private String row6Eight;
	private String row6Nine;
	private String row6Ten;
	private String row6Eleven;
	private String row6Twelve;
	private String row6Thirteen;
	private String row6Fourteen;
	private String row6Fifteen;
	private String row6Sixteen;
	
	private String row7One;		// row_one
	private String row7Two;		// row_two
	private String row7Three;		
	private String row7Four;	
	private String row7Five;	
	private String row7Six;	
	private String row7Seven;
	private String row7Eight;
	private String row7Nine;
	private String row7Ten;
	private String row7Eleven;
	private String row7Twelve;
	private String row7Thirteen;
	private String row7Fourteen;
	private String row7Fifteen;
	private String row7Sixteen;
	
	private String row8One;		// row_one
	private String row8Two;		// row_two
	private String row8Three;		
	private String row8Four;	
	private String row8Five;	
	private String row8Six;	
	private String row8Seven;
	private String row8Eight;
	private String row8Nine;
	private String row8Ten;
	private String row8Eleven;
	private String row8Twelve;
	private String row8Thirteen;
	private String row8Fourteen;
	private String row8Fifteen;
	private String row8Sixteen;
	
	private String row9One;		// row_one
	private String row9Two;		// row_two
	private String row9Three;		
	private String row9Four;	
	private String row9Five;	
	private String row9Six;	
	private String row9Seven;
	private String row9Eight;
	private String row9Nine;
	private String row9Ten;
	private String row9Eleven;
	private String row9Twelve;
	private String row9Thirteen;
	private String row9Fourteen;
	private String row9Fifteen;
	private String row9Sixteen;
	
	
	private String row10One;		// row_one
	private String row10Two;		// row_two
	private String row10Three;		
	private String row10Four;	
	private String row10Five;	
	private String row10Six;	
	private String row10Seven;
	private String row10Eight;
	private String row10Nine;
	private String row10Ten;
	private String row10Eleven;
	private String row10Twelve;
	private String row10Thirteen;
	private String row10Fourteen;
	private String row10Fifteen;
	private String row10Sixteen;
	
	private String row11One;		// row_one
	private String row11Two;		// row_two
	private String row11Three;		
	private String row11Four;	
	private String row11Five;	
	private String row11Six;	
	private String row11Seven;
	private String row11Eight;
	private String row11Nine;
	private String row11Ten;
	private String row11Eleven;
	private String row11Twelve;
	private String row11Thirteen;
	private String row11Fourteen;
	private String row11Fifteen;
	private String row11Sixteen;
	
	private String row12One;		// row_one
	private String row12Two;		// row_two
	private String row12Three;		
	private String row12Four;	
	private String row12Five;	
	private String row12Six;	
	private String row12Seven;
	private String row12Eight;
	private String row12Nine;
	private String row12Ten;
	private String row12Eleven;
	private String row12Twelve;
	private String row12Thirteen;
	private String row12Fourteen;
	private String row12Fifteen;
	private String row12Sixteen;
	
	private String row13One;		// row_one
	private String row13Two;		// row_two
	private String row13Three;		
	private String row13Four;	
	private String row13Five;	
	private String row13Six;	
	private String row13Seven;
	private String row13Eight;
	private String row13Nine;
	private String row13Ten;
	private String row13Eleven;
	private String row13Twelve;
	private String row13Thirteen;
	private String row13Fourteen;
	private String row13Fifteen;
	private String row13Sixteen;
	
	private String row14One;		// row_one
	private String row14Two;		// row_two
	private String row14Three;		
	private String row14Four;	
	private String row14Five;	
	private String row14Six;	
	private String row14Seven;
	private String row14Eight;
	private String row14Nine;
	private String row14Ten;
	private String row14Eleven;
	private String row14Twelve;
	private String row14Thirteen;
	private String row14Fourteen;
	private String row14Fifteen;
	private String row14Sixteen;
	
	private String row15One;		// row_one
	private String row15Two;		// row_two
	private String row15Three;		
	private String row15Four;	
	private String row15Five;	
	private String row15Six;	
	private String row15Seven;
	private String row15Eight;
	private String row15Nine;
	private String row15Ten;
	private String row15Eleven;
	private String row15Twelve;
	private String row15Thirteen;
	private String row15Fourteen;
	private String row15Fifteen;
	private String row15Sixteen;
	
	
	private String row16One;		// row_one
	private String row16Two;		// row_two
	private String row16Three;		
	private String row16Four;	
	private String row16Five;	
	private String row16Six;	
	private String row16Seven;
	private String row16Eight;
	private String row16Nine;
	private String row16Ten;
	private String row16Eleven;
	private String row16Twelve;
	private String row16Thirteen;
	private String row16Fourteen;
	private String row16Fifteen;
	private String row16Sixteen;
	
	private String row17One;		// row_one
	private String row17Two;		// row_two
	private String row17Three;		
	private String row17Four;	
	private String row17Five;	
	private String row17Six;	
	private String row17Seven;
	private String row17Eight;
	private String row17Nine;
	private String row17Ten;
	private String row17Eleven;
	private String row17Twelve;
	private String row17Thirteen;
	private String row17Fourteen;
	private String row17Fifteen;
	private String row17Sixteen;
	
	private String row18One;		// row_one
	private String row18Two;		// row_two
	private String row18Three;		
	private String row18Four;	
	private String row18Five;	
	private String row18Six;	
	private String row18Seven;
	private String row18Eight;
	private String row18Nine;
	private String row18Ten;
	private String row18Eleven;
	private String row18Twelve;
	private String row18Thirteen;
	private String row18Fourteen;
	private String row18Fifteen;
	private String row18Sixteen;
	
	private String row19One;		// row_one
	private String row19Two;		// row_two
	private String row19Three;		
	private String row19Four;	
	private String row19Five;	
	private String row19Six;	
	private String row19Seven;
	private String row19Eight;
	private String row19Nine;
	private String row19Ten;
	private String row19Eleven;
	private String row19Twelve;
	private String row19Thirteen;
	private String row19Fourteen;
	private String row19Fifteen;
	private String row19Sixteen;
	
	private String row20One;		// row_one
	private String row20Two;		// row_two
	private String row20Three;		
	private String row20Four;	
	private String row20Five;	
	private String row20Six;	
	private String row20Seven;
	private String row20Eight;
	private String row20Nine;
	private String row20Ten;
	private String row20Eleven;
	private String row20Twelve;
	private String row20Thirteen;
	private String row20Fourteen;
	private String row20Fifteen;
	private String row20Sixteen;
	
	private String row21One;		// row_one
	private String row21Two;		// row_two
	private String row21Three;		
	private String row21Four;	
	private String row21Five;	
	private String row21Six;	
	private String row21Seven;
	private String row21Eight;
	private String row21Nine;
	private String row21Ten;
	private String row21Eleven;
	private String row21Twelve;
	private String row21Thirteen;
	private String row21Fourteen;
	private String row21Fifteen;
	private String row21Sixteen;
	
	private String row22One;		// row_one
	private String row22Two;		// row_two
	private String row22Three;		
	private String row22Four;	
	private String row22Five;	
	private String row22Six;	
	private String row22Seven;
	private String row22Eight;
	private String row22Nine;
	private String row22Ten;
	private String row22Eleven;
	private String row22Twelve;
	private String row22Thirteen;
	private String row22Fourteen;
	private String row22Fifteen;
	private String row22Sixteen;
	
	private String row23One;		// row_one
	private String row23Two;		// row_two
	private String row23Three;		
	private String row23Four;	
	private String row23Five;	
	private String row23Six;	
	private String row23Seven;
	private String row23Eight;
	private String row23Nine;
	private String row23Ten;
	private String row23Eleven;
	private String row23Twelve;
	private String row23Thirteen;
	private String row23Fourteen;
	private String row23Fifteen;
	private String row23Sixteen;
	
	
	private String principal;   //负责人
	
	private String statistics; //统计负责人
	
	private String fitOut;      //填表人
	
	private String submitDate;   //报出日期
	
	private String scanFlag;  //扫描标示  (0:未扫描  1:已经扫描)
	
	private String createTime;//程序暂时使用，不持久化到数据库
	
	private String pushStatus; //推送状态： 0未推送过  1：已推送过

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

	public String getRowFive() {
		return rowFive;
	}

	public void setRowFive(String rowFive) {
		this.rowFive = rowFive;
	}

	public String getRowSix() {
		return rowSix;
	}

	public void setRowSix(String rowSix) {
		this.rowSix = rowSix;
	}

	public String getRowSeven() {
		return rowSeven;
	}

	public void setRowSeven(String rowSeven) {
		this.rowSeven = rowSeven;
	}

	public String getRowEight() {
		return rowEight;
	}

	public void setRowEight(String rowEight) {
		this.rowEight = rowEight;
	}

	public String getRowNine() {
		return rowNine;
	}

	public void setRowNine(String rowNine) {
		this.rowNine = rowNine;
	}

	public String getRowTen() {
		return rowTen;
	}

	public void setRowTen(String rowTen) {
		this.rowTen = rowTen;
	}

	public String getRowEleven() {
		return rowEleven;
	}

	public void setRowEleven(String rowEleven) {
		this.rowEleven = rowEleven;
	}

	public String getRowTwelve() {
		return rowTwelve;
	}

	public void setRowTwelve(String rowTwelve) {
		this.rowTwelve = rowTwelve;
	}

	public String getRowThirteen() {
		return rowThirteen;
	}

	public void setRowThirteen(String rowThirteen) {
		this.rowThirteen = rowThirteen;
	}

	public String getRowFourteen() {
		return rowFourteen;
	}

	public void setRowFourteen(String rowFourteen) {
		this.rowFourteen = rowFourteen;
	}

	public String getRowFifteen() {
		return rowFifteen;
	}

	public void setRowFifteen(String rowFifteen) {
		this.rowFifteen = rowFifteen;
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

	public String getRow1Five() {
		return row1Five;
	}

	public void setRow1Five(String row1Five) {
		this.row1Five = row1Five;
	}

	public String getRow1Six() {
		return row1Six;
	}

	public void setRow1Six(String row1Six) {
		this.row1Six = row1Six;
	}

	public String getRow1Seven() {
		return row1Seven;
	}

	public void setRow1Seven(String row1Seven) {
		this.row1Seven = row1Seven;
	}

	public String getRow1Eight() {
		return row1Eight;
	}

	public void setRow1Eight(String row1Eight) {
		this.row1Eight = row1Eight;
	}

	public String getRow1Nine() {
		return row1Nine;
	}

	public void setRow1Nine(String row1Nine) {
		this.row1Nine = row1Nine;
	}

	public String getRow1Ten() {
		return row1Ten;
	}

	public void setRow1Ten(String row1Ten) {
		this.row1Ten = row1Ten;
	}

	public String getRow1Eleven() {
		return row1Eleven;
	}

	public void setRow1Eleven(String row1Eleven) {
		this.row1Eleven = row1Eleven;
	}

	public String getRow1Twelve() {
		return row1Twelve;
	}

	public void setRow1Twelve(String row1Twelve) {
		this.row1Twelve = row1Twelve;
	}

	public String getRow1Thirteen() {
		return row1Thirteen;
	}

	public void setRow1Thirteen(String row1Thirteen) {
		this.row1Thirteen = row1Thirteen;
	}

	public String getRow1Fourteen() {
		return row1Fourteen;
	}

	public void setRow1Fourteen(String row1Fourteen) {
		this.row1Fourteen = row1Fourteen;
	}

	public String getRow1Fifteen() {
		return row1Fifteen;
	}

	public void setRow1Fifteen(String row1Fifteen) {
		this.row1Fifteen = row1Fifteen;
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

	public String getRow2Five() {
		return row2Five;
	}

	public void setRow2Five(String row2Five) {
		this.row2Five = row2Five;
	}

	public String getRow2Six() {
		return row2Six;
	}

	public void setRow2Six(String row2Six) {
		this.row2Six = row2Six;
	}

	public String getRow2Seven() {
		return row2Seven;
	}

	public void setRow2Seven(String row2Seven) {
		this.row2Seven = row2Seven;
	}

	public String getRow2Eight() {
		return row2Eight;
	}

	public void setRow2Eight(String row2Eight) {
		this.row2Eight = row2Eight;
	}

	public String getRow2Nine() {
		return row2Nine;
	}

	public void setRow2Nine(String row2Nine) {
		this.row2Nine = row2Nine;
	}

	public String getRow2Ten() {
		return row2Ten;
	}

	public void setRow2Ten(String row2Ten) {
		this.row2Ten = row2Ten;
	}

	public String getRow2Eleven() {
		return row2Eleven;
	}

	public void setRow2Eleven(String row2Eleven) {
		this.row2Eleven = row2Eleven;
	}

	public String getRow2Twelve() {
		return row2Twelve;
	}

	public void setRow2Twelve(String row2Twelve) {
		this.row2Twelve = row2Twelve;
	}

	public String getRow2Thirteen() {
		return row2Thirteen;
	}

	public void setRow2Thirteen(String row2Thirteen) {
		this.row2Thirteen = row2Thirteen;
	}

	public String getRow2Fourteen() {
		return row2Fourteen;
	}

	public void setRow2Fourteen(String row2Fourteen) {
		this.row2Fourteen = row2Fourteen;
	}

	public String getRow2Fifteen() {
		return row2Fifteen;
	}

	public void setRow2Fifteen(String row2Fifteen) {
		this.row2Fifteen = row2Fifteen;
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

	public String getRow3Five() {
		return row3Five;
	}

	public void setRow3Five(String row3Five) {
		this.row3Five = row3Five;
	}

	public String getRow3Six() {
		return row3Six;
	}

	public void setRow3Six(String row3Six) {
		this.row3Six = row3Six;
	}

	public String getRow3Seven() {
		return row3Seven;
	}

	public void setRow3Seven(String row3Seven) {
		this.row3Seven = row3Seven;
	}

	public String getRow3Eight() {
		return row3Eight;
	}

	public void setRow3Eight(String row3Eight) {
		this.row3Eight = row3Eight;
	}

	public String getRow3Nine() {
		return row3Nine;
	}

	public void setRow3Nine(String row3Nine) {
		this.row3Nine = row3Nine;
	}

	public String getRow3Ten() {
		return row3Ten;
	}

	public void setRow3Ten(String row3Ten) {
		this.row3Ten = row3Ten;
	}

	public String getRow3Eleven() {
		return row3Eleven;
	}

	public void setRow3Eleven(String row3Eleven) {
		this.row3Eleven = row3Eleven;
	}

	public String getRow3Twelve() {
		return row3Twelve;
	}

	public void setRow3Twelve(String row3Twelve) {
		this.row3Twelve = row3Twelve;
	}

	public String getRow3Thirteen() {
		return row3Thirteen;
	}

	public void setRow3Thirteen(String row3Thirteen) {
		this.row3Thirteen = row3Thirteen;
	}

	public String getRow3Fourteen() {
		return row3Fourteen;
	}

	public void setRow3Fourteen(String row3Fourteen) {
		this.row3Fourteen = row3Fourteen;
	}

	public String getRow3Fifteen() {
		return row3Fifteen;
	}

	public void setRow3Fifteen(String row3Fifteen) {
		this.row3Fifteen = row3Fifteen;
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

	public String getRow4Five() {
		return row4Five;
	}

	public void setRow4Five(String row4Five) {
		this.row4Five = row4Five;
	}

	public String getRow4Six() {
		return row4Six;
	}

	public void setRow4Six(String row4Six) {
		this.row4Six = row4Six;
	}

	public String getRow4Seven() {
		return row4Seven;
	}

	public void setRow4Seven(String row4Seven) {
		this.row4Seven = row4Seven;
	}

	public String getRow4Eight() {
		return row4Eight;
	}

	public void setRow4Eight(String row4Eight) {
		this.row4Eight = row4Eight;
	}

	public String getRow4Nine() {
		return row4Nine;
	}

	public void setRow4Nine(String row4Nine) {
		this.row4Nine = row4Nine;
	}

	public String getRow4Ten() {
		return row4Ten;
	}

	public void setRow4Ten(String row4Ten) {
		this.row4Ten = row4Ten;
	}

	public String getRow4Eleven() {
		return row4Eleven;
	}

	public void setRow4Eleven(String row4Eleven) {
		this.row4Eleven = row4Eleven;
	}

	public String getRow4Twelve() {
		return row4Twelve;
	}

	public void setRow4Twelve(String row4Twelve) {
		this.row4Twelve = row4Twelve;
	}

	public String getRow4Thirteen() {
		return row4Thirteen;
	}

	public void setRow4Thirteen(String row4Thirteen) {
		this.row4Thirteen = row4Thirteen;
	}

	public String getRow4Fourteen() {
		return row4Fourteen;
	}

	public void setRow4Fourteen(String row4Fourteen) {
		this.row4Fourteen = row4Fourteen;
	}

	public String getRow4Fifteen() {
		return row4Fifteen;
	}

	public void setRow4Fifteen(String row4Fifteen) {
		this.row4Fifteen = row4Fifteen;
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

	public String getRow5Five() {
		return row5Five;
	}

	public void setRow5Five(String row5Five) {
		this.row5Five = row5Five;
	}

	public String getRow5Six() {
		return row5Six;
	}

	public void setRow5Six(String row5Six) {
		this.row5Six = row5Six;
	}

	public String getRow5Seven() {
		return row5Seven;
	}

	public void setRow5Seven(String row5Seven) {
		this.row5Seven = row5Seven;
	}

	public String getRow5Eight() {
		return row5Eight;
	}

	public void setRow5Eight(String row5Eight) {
		this.row5Eight = row5Eight;
	}

	public String getRow5Nine() {
		return row5Nine;
	}

	public void setRow5Nine(String row5Nine) {
		this.row5Nine = row5Nine;
	}

	public String getRow5Ten() {
		return row5Ten;
	}

	public void setRow5Ten(String row5Ten) {
		this.row5Ten = row5Ten;
	}

	public String getRow5Eleven() {
		return row5Eleven;
	}

	public void setRow5Eleven(String row5Eleven) {
		this.row5Eleven = row5Eleven;
	}

	public String getRow5Twelve() {
		return row5Twelve;
	}

	public void setRow5Twelve(String row5Twelve) {
		this.row5Twelve = row5Twelve;
	}

	public String getRow5Thirteen() {
		return row5Thirteen;
	}

	public void setRow5Thirteen(String row5Thirteen) {
		this.row5Thirteen = row5Thirteen;
	}

	public String getRow5Fourteen() {
		return row5Fourteen;
	}

	public void setRow5Fourteen(String row5Fourteen) {
		this.row5Fourteen = row5Fourteen;
	}

	public String getRow5Fifteen() {
		return row5Fifteen;
	}

	public void setRow5Fifteen(String row5Fifteen) {
		this.row5Fifteen = row5Fifteen;
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

	public String getRow6Five() {
		return row6Five;
	}

	public void setRow6Five(String row6Five) {
		this.row6Five = row6Five;
	}

	public String getRow6Six() {
		return row6Six;
	}

	public void setRow6Six(String row6Six) {
		this.row6Six = row6Six;
	}

	public String getRow6Seven() {
		return row6Seven;
	}

	public void setRow6Seven(String row6Seven) {
		this.row6Seven = row6Seven;
	}

	public String getRow6Eight() {
		return row6Eight;
	}

	public void setRow6Eight(String row6Eight) {
		this.row6Eight = row6Eight;
	}

	public String getRow6Nine() {
		return row6Nine;
	}

	public void setRow6Nine(String row6Nine) {
		this.row6Nine = row6Nine;
	}

	public String getRow6Ten() {
		return row6Ten;
	}

	public void setRow6Ten(String row6Ten) {
		this.row6Ten = row6Ten;
	}

	public String getRow6Eleven() {
		return row6Eleven;
	}

	public void setRow6Eleven(String row6Eleven) {
		this.row6Eleven = row6Eleven;
	}

	public String getRow6Twelve() {
		return row6Twelve;
	}

	public void setRow6Twelve(String row6Twelve) {
		this.row6Twelve = row6Twelve;
	}

	public String getRow6Thirteen() {
		return row6Thirteen;
	}

	public void setRow6Thirteen(String row6Thirteen) {
		this.row6Thirteen = row6Thirteen;
	}

	public String getRow6Fourteen() {
		return row6Fourteen;
	}

	public void setRow6Fourteen(String row6Fourteen) {
		this.row6Fourteen = row6Fourteen;
	}

	public String getRow6Fifteen() {
		return row6Fifteen;
	}

	public void setRow6Fifteen(String row6Fifteen) {
		this.row6Fifteen = row6Fifteen;
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

	public String getRow7Five() {
		return row7Five;
	}

	public void setRow7Five(String row7Five) {
		this.row7Five = row7Five;
	}

	public String getRow7Six() {
		return row7Six;
	}

	public void setRow7Six(String row7Six) {
		this.row7Six = row7Six;
	}

	public String getRow7Seven() {
		return row7Seven;
	}

	public void setRow7Seven(String row7Seven) {
		this.row7Seven = row7Seven;
	}

	public String getRow7Eight() {
		return row7Eight;
	}

	public void setRow7Eight(String row7Eight) {
		this.row7Eight = row7Eight;
	}

	public String getRow7Nine() {
		return row7Nine;
	}

	public void setRow7Nine(String row7Nine) {
		this.row7Nine = row7Nine;
	}

	public String getRow7Ten() {
		return row7Ten;
	}

	public void setRow7Ten(String row7Ten) {
		this.row7Ten = row7Ten;
	}

	public String getRow7Eleven() {
		return row7Eleven;
	}

	public void setRow7Eleven(String row7Eleven) {
		this.row7Eleven = row7Eleven;
	}

	public String getRow7Twelve() {
		return row7Twelve;
	}

	public void setRow7Twelve(String row7Twelve) {
		this.row7Twelve = row7Twelve;
	}

	public String getRow7Thirteen() {
		return row7Thirteen;
	}

	public void setRow7Thirteen(String row7Thirteen) {
		this.row7Thirteen = row7Thirteen;
	}

	public String getRow7Fourteen() {
		return row7Fourteen;
	}

	public void setRow7Fourteen(String row7Fourteen) {
		this.row7Fourteen = row7Fourteen;
	}

	public String getRow7Fifteen() {
		return row7Fifteen;
	}

	public void setRow7Fifteen(String row7Fifteen) {
		this.row7Fifteen = row7Fifteen;
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

	public String getRow8Five() {
		return row8Five;
	}

	public void setRow8Five(String row8Five) {
		this.row8Five = row8Five;
	}

	public String getRow8Six() {
		return row8Six;
	}

	public void setRow8Six(String row8Six) {
		this.row8Six = row8Six;
	}

	public String getRow8Seven() {
		return row8Seven;
	}

	public void setRow8Seven(String row8Seven) {
		this.row8Seven = row8Seven;
	}

	public String getRow8Eight() {
		return row8Eight;
	}

	public void setRow8Eight(String row8Eight) {
		this.row8Eight = row8Eight;
	}

	public String getRow8Nine() {
		return row8Nine;
	}

	public void setRow8Nine(String row8Nine) {
		this.row8Nine = row8Nine;
	}

	public String getRow8Ten() {
		return row8Ten;
	}

	public void setRow8Ten(String row8Ten) {
		this.row8Ten = row8Ten;
	}

	public String getRow8Eleven() {
		return row8Eleven;
	}

	public void setRow8Eleven(String row8Eleven) {
		this.row8Eleven = row8Eleven;
	}

	public String getRow8Twelve() {
		return row8Twelve;
	}

	public void setRow8Twelve(String row8Twelve) {
		this.row8Twelve = row8Twelve;
	}

	public String getRow8Thirteen() {
		return row8Thirteen;
	}

	public void setRow8Thirteen(String row8Thirteen) {
		this.row8Thirteen = row8Thirteen;
	}

	public String getRow8Fourteen() {
		return row8Fourteen;
	}

	public void setRow8Fourteen(String row8Fourteen) {
		this.row8Fourteen = row8Fourteen;
	}

	public String getRow8Fifteen() {
		return row8Fifteen;
	}

	public void setRow8Fifteen(String row8Fifteen) {
		this.row8Fifteen = row8Fifteen;
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

	public String getRow9Five() {
		return row9Five;
	}

	public void setRow9Five(String row9Five) {
		this.row9Five = row9Five;
	}

	public String getRow9Six() {
		return row9Six;
	}

	public void setRow9Six(String row9Six) {
		this.row9Six = row9Six;
	}

	public String getRow9Seven() {
		return row9Seven;
	}

	public void setRow9Seven(String row9Seven) {
		this.row9Seven = row9Seven;
	}

	public String getRow9Eight() {
		return row9Eight;
	}

	public void setRow9Eight(String row9Eight) {
		this.row9Eight = row9Eight;
	}

	public String getRow9Nine() {
		return row9Nine;
	}

	public void setRow9Nine(String row9Nine) {
		this.row9Nine = row9Nine;
	}

	public String getRow9Ten() {
		return row9Ten;
	}

	public void setRow9Ten(String row9Ten) {
		this.row9Ten = row9Ten;
	}

	public String getRow9Eleven() {
		return row9Eleven;
	}

	public void setRow9Eleven(String row9Eleven) {
		this.row9Eleven = row9Eleven;
	}

	public String getRow9Twelve() {
		return row9Twelve;
	}

	public void setRow9Twelve(String row9Twelve) {
		this.row9Twelve = row9Twelve;
	}

	public String getRow9Thirteen() {
		return row9Thirteen;
	}

	public void setRow9Thirteen(String row9Thirteen) {
		this.row9Thirteen = row9Thirteen;
	}

	public String getRow9Fourteen() {
		return row9Fourteen;
	}

	public void setRow9Fourteen(String row9Fourteen) {
		this.row9Fourteen = row9Fourteen;
	}

	public String getRow9Fifteen() {
		return row9Fifteen;
	}

	public void setRow9Fifteen(String row9Fifteen) {
		this.row9Fifteen = row9Fifteen;
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

	public String getRow10Five() {
		return row10Five;
	}

	public void setRow10Five(String row10Five) {
		this.row10Five = row10Five;
	}

	public String getRow10Six() {
		return row10Six;
	}

	public void setRow10Six(String row10Six) {
		this.row10Six = row10Six;
	}

	public String getRow10Seven() {
		return row10Seven;
	}

	public void setRow10Seven(String row10Seven) {
		this.row10Seven = row10Seven;
	}

	public String getRow10Eight() {
		return row10Eight;
	}

	public void setRow10Eight(String row10Eight) {
		this.row10Eight = row10Eight;
	}

	public String getRow10Nine() {
		return row10Nine;
	}

	public void setRow10Nine(String row10Nine) {
		this.row10Nine = row10Nine;
	}

	public String getRow10Ten() {
		return row10Ten;
	}

	public void setRow10Ten(String row10Ten) {
		this.row10Ten = row10Ten;
	}

	public String getRow10Eleven() {
		return row10Eleven;
	}

	public void setRow10Eleven(String row10Eleven) {
		this.row10Eleven = row10Eleven;
	}

	public String getRow10Twelve() {
		return row10Twelve;
	}

	public void setRow10Twelve(String row10Twelve) {
		this.row10Twelve = row10Twelve;
	}

	public String getRow10Thirteen() {
		return row10Thirteen;
	}

	public void setRow10Thirteen(String row10Thirteen) {
		this.row10Thirteen = row10Thirteen;
	}

	public String getRow10Fourteen() {
		return row10Fourteen;
	}

	public void setRow10Fourteen(String row10Fourteen) {
		this.row10Fourteen = row10Fourteen;
	}

	public String getRow10Fifteen() {
		return row10Fifteen;
	}

	public void setRow10Fifteen(String row10Fifteen) {
		this.row10Fifteen = row10Fifteen;
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

	public String getRow11Five() {
		return row11Five;
	}

	public void setRow11Five(String row11Five) {
		this.row11Five = row11Five;
	}

	public String getRow11Six() {
		return row11Six;
	}

	public void setRow11Six(String row11Six) {
		this.row11Six = row11Six;
	}

	public String getRow11Seven() {
		return row11Seven;
	}

	public void setRow11Seven(String row11Seven) {
		this.row11Seven = row11Seven;
	}

	public String getRow11Eight() {
		return row11Eight;
	}

	public void setRow11Eight(String row11Eight) {
		this.row11Eight = row11Eight;
	}

	public String getRow11Nine() {
		return row11Nine;
	}

	public void setRow11Nine(String row11Nine) {
		this.row11Nine = row11Nine;
	}

	public String getRow11Ten() {
		return row11Ten;
	}

	public void setRow11Ten(String row11Ten) {
		this.row11Ten = row11Ten;
	}

	public String getRow11Eleven() {
		return row11Eleven;
	}

	public void setRow11Eleven(String row11Eleven) {
		this.row11Eleven = row11Eleven;
	}

	public String getRow11Twelve() {
		return row11Twelve;
	}

	public void setRow11Twelve(String row11Twelve) {
		this.row11Twelve = row11Twelve;
	}

	public String getRow11Thirteen() {
		return row11Thirteen;
	}

	public void setRow11Thirteen(String row11Thirteen) {
		this.row11Thirteen = row11Thirteen;
	}

	public String getRow11Fourteen() {
		return row11Fourteen;
	}

	public void setRow11Fourteen(String row11Fourteen) {
		this.row11Fourteen = row11Fourteen;
	}

	public String getRow11Fifteen() {
		return row11Fifteen;
	}

	public void setRow11Fifteen(String row11Fifteen) {
		this.row11Fifteen = row11Fifteen;
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

	public String getRow12Five() {
		return row12Five;
	}

	public void setRow12Five(String row12Five) {
		this.row12Five = row12Five;
	}

	public String getRow12Six() {
		return row12Six;
	}

	public void setRow12Six(String row12Six) {
		this.row12Six = row12Six;
	}

	public String getRow12Seven() {
		return row12Seven;
	}

	public void setRow12Seven(String row12Seven) {
		this.row12Seven = row12Seven;
	}

	public String getRow12Eight() {
		return row12Eight;
	}

	public void setRow12Eight(String row12Eight) {
		this.row12Eight = row12Eight;
	}

	public String getRow12Nine() {
		return row12Nine;
	}

	public void setRow12Nine(String row12Nine) {
		this.row12Nine = row12Nine;
	}

	public String getRow12Ten() {
		return row12Ten;
	}

	public void setRow12Ten(String row12Ten) {
		this.row12Ten = row12Ten;
	}

	public String getRow12Eleven() {
		return row12Eleven;
	}

	public void setRow12Eleven(String row12Eleven) {
		this.row12Eleven = row12Eleven;
	}

	public String getRow12Twelve() {
		return row12Twelve;
	}

	public void setRow12Twelve(String row12Twelve) {
		this.row12Twelve = row12Twelve;
	}

	public String getRow12Thirteen() {
		return row12Thirteen;
	}

	public void setRow12Thirteen(String row12Thirteen) {
		this.row12Thirteen = row12Thirteen;
	}

	public String getRow12Fourteen() {
		return row12Fourteen;
	}

	public void setRow12Fourteen(String row12Fourteen) {
		this.row12Fourteen = row12Fourteen;
	}

	public String getRow12Fifteen() {
		return row12Fifteen;
	}

	public void setRow12Fifteen(String row12Fifteen) {
		this.row12Fifteen = row12Fifteen;
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

	public String getRow13Five() {
		return row13Five;
	}

	public void setRow13Five(String row13Five) {
		this.row13Five = row13Five;
	}

	public String getRow13Six() {
		return row13Six;
	}

	public void setRow13Six(String row13Six) {
		this.row13Six = row13Six;
	}

	public String getRow13Seven() {
		return row13Seven;
	}

	public void setRow13Seven(String row13Seven) {
		this.row13Seven = row13Seven;
	}

	public String getRow13Eight() {
		return row13Eight;
	}

	public void setRow13Eight(String row13Eight) {
		this.row13Eight = row13Eight;
	}

	public String getRow13Nine() {
		return row13Nine;
	}

	public void setRow13Nine(String row13Nine) {
		this.row13Nine = row13Nine;
	}

	public String getRow13Ten() {
		return row13Ten;
	}

	public void setRow13Ten(String row13Ten) {
		this.row13Ten = row13Ten;
	}

	public String getRow13Eleven() {
		return row13Eleven;
	}

	public void setRow13Eleven(String row13Eleven) {
		this.row13Eleven = row13Eleven;
	}

	public String getRow13Twelve() {
		return row13Twelve;
	}

	public void setRow13Twelve(String row13Twelve) {
		this.row13Twelve = row13Twelve;
	}

	public String getRow13Thirteen() {
		return row13Thirteen;
	}

	public void setRow13Thirteen(String row13Thirteen) {
		this.row13Thirteen = row13Thirteen;
	}

	public String getRow13Fourteen() {
		return row13Fourteen;
	}

	public void setRow13Fourteen(String row13Fourteen) {
		this.row13Fourteen = row13Fourteen;
	}

	public String getRow13Fifteen() {
		return row13Fifteen;
	}

	public void setRow13Fifteen(String row13Fifteen) {
		this.row13Fifteen = row13Fifteen;
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

	public String getRow14Five() {
		return row14Five;
	}

	public void setRow14Five(String row14Five) {
		this.row14Five = row14Five;
	}

	public String getRow14Six() {
		return row14Six;
	}

	public void setRow14Six(String row14Six) {
		this.row14Six = row14Six;
	}

	public String getRow14Seven() {
		return row14Seven;
	}

	public void setRow14Seven(String row14Seven) {
		this.row14Seven = row14Seven;
	}

	public String getRow14Eight() {
		return row14Eight;
	}

	public void setRow14Eight(String row14Eight) {
		this.row14Eight = row14Eight;
	}

	public String getRow14Nine() {
		return row14Nine;
	}

	public void setRow14Nine(String row14Nine) {
		this.row14Nine = row14Nine;
	}

	public String getRow14Ten() {
		return row14Ten;
	}

	public void setRow14Ten(String row14Ten) {
		this.row14Ten = row14Ten;
	}

	public String getRow14Eleven() {
		return row14Eleven;
	}

	public void setRow14Eleven(String row14Eleven) {
		this.row14Eleven = row14Eleven;
	}

	public String getRow14Twelve() {
		return row14Twelve;
	}

	public void setRow14Twelve(String row14Twelve) {
		this.row14Twelve = row14Twelve;
	}

	public String getRow14Thirteen() {
		return row14Thirteen;
	}

	public void setRow14Thirteen(String row14Thirteen) {
		this.row14Thirteen = row14Thirteen;
	}

	public String getRow14Fourteen() {
		return row14Fourteen;
	}

	public void setRow14Fourteen(String row14Fourteen) {
		this.row14Fourteen = row14Fourteen;
	}

	public String getRow14Fifteen() {
		return row14Fifteen;
	}

	public void setRow14Fifteen(String row14Fifteen) {
		this.row14Fifteen = row14Fifteen;
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

	public String getRow15Five() {
		return row15Five;
	}

	public void setRow15Five(String row15Five) {
		this.row15Five = row15Five;
	}

	public String getRow15Six() {
		return row15Six;
	}

	public void setRow15Six(String row15Six) {
		this.row15Six = row15Six;
	}

	public String getRow15Seven() {
		return row15Seven;
	}

	public void setRow15Seven(String row15Seven) {
		this.row15Seven = row15Seven;
	}

	public String getRow15Eight() {
		return row15Eight;
	}

	public void setRow15Eight(String row15Eight) {
		this.row15Eight = row15Eight;
	}

	public String getRow15Nine() {
		return row15Nine;
	}

	public void setRow15Nine(String row15Nine) {
		this.row15Nine = row15Nine;
	}

	public String getRow15Ten() {
		return row15Ten;
	}

	public void setRow15Ten(String row15Ten) {
		this.row15Ten = row15Ten;
	}

	public String getRow15Eleven() {
		return row15Eleven;
	}

	public void setRow15Eleven(String row15Eleven) {
		this.row15Eleven = row15Eleven;
	}

	public String getRow15Twelve() {
		return row15Twelve;
	}

	public void setRow15Twelve(String row15Twelve) {
		this.row15Twelve = row15Twelve;
	}

	public String getRow15Thirteen() {
		return row15Thirteen;
	}

	public void setRow15Thirteen(String row15Thirteen) {
		this.row15Thirteen = row15Thirteen;
	}

	public String getRow15Fourteen() {
		return row15Fourteen;
	}

	public void setRow15Fourteen(String row15Fourteen) {
		this.row15Fourteen = row15Fourteen;
	}

	public String getRow15Fifteen() {
		return row15Fifteen;
	}

	public void setRow15Fifteen(String row15Fifteen) {
		this.row15Fifteen = row15Fifteen;
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

	public String getRow16Five() {
		return row16Five;
	}

	public void setRow16Five(String row16Five) {
		this.row16Five = row16Five;
	}

	public String getRow16Six() {
		return row16Six;
	}

	public void setRow16Six(String row16Six) {
		this.row16Six = row16Six;
	}

	public String getRow16Seven() {
		return row16Seven;
	}

	public void setRow16Seven(String row16Seven) {
		this.row16Seven = row16Seven;
	}

	public String getRow16Eight() {
		return row16Eight;
	}

	public void setRow16Eight(String row16Eight) {
		this.row16Eight = row16Eight;
	}

	public String getRow16Nine() {
		return row16Nine;
	}

	public void setRow16Nine(String row16Nine) {
		this.row16Nine = row16Nine;
	}

	public String getRow16Ten() {
		return row16Ten;
	}

	public void setRow16Ten(String row16Ten) {
		this.row16Ten = row16Ten;
	}

	public String getRow16Eleven() {
		return row16Eleven;
	}

	public void setRow16Eleven(String row16Eleven) {
		this.row16Eleven = row16Eleven;
	}

	public String getRow16Twelve() {
		return row16Twelve;
	}

	public void setRow16Twelve(String row16Twelve) {
		this.row16Twelve = row16Twelve;
	}

	public String getRow16Thirteen() {
		return row16Thirteen;
	}

	public void setRow16Thirteen(String row16Thirteen) {
		this.row16Thirteen = row16Thirteen;
	}

	public String getRow16Fourteen() {
		return row16Fourteen;
	}

	public void setRow16Fourteen(String row16Fourteen) {
		this.row16Fourteen = row16Fourteen;
	}

	public String getRow16Fifteen() {
		return row16Fifteen;
	}

	public void setRow16Fifteen(String row16Fifteen) {
		this.row16Fifteen = row16Fifteen;
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

	public String getRow17Five() {
		return row17Five;
	}

	public void setRow17Five(String row17Five) {
		this.row17Five = row17Five;
	}

	public String getRow17Six() {
		return row17Six;
	}

	public void setRow17Six(String row17Six) {
		this.row17Six = row17Six;
	}

	public String getRow17Seven() {
		return row17Seven;
	}

	public void setRow17Seven(String row17Seven) {
		this.row17Seven = row17Seven;
	}

	public String getRow17Eight() {
		return row17Eight;
	}

	public void setRow17Eight(String row17Eight) {
		this.row17Eight = row17Eight;
	}

	public String getRow17Nine() {
		return row17Nine;
	}

	public void setRow17Nine(String row17Nine) {
		this.row17Nine = row17Nine;
	}

	public String getRow17Ten() {
		return row17Ten;
	}

	public void setRow17Ten(String row17Ten) {
		this.row17Ten = row17Ten;
	}

	public String getRow17Eleven() {
		return row17Eleven;
	}

	public void setRow17Eleven(String row17Eleven) {
		this.row17Eleven = row17Eleven;
	}

	public String getRow17Twelve() {
		return row17Twelve;
	}

	public void setRow17Twelve(String row17Twelve) {
		this.row17Twelve = row17Twelve;
	}

	public String getRow17Thirteen() {
		return row17Thirteen;
	}

	public void setRow17Thirteen(String row17Thirteen) {
		this.row17Thirteen = row17Thirteen;
	}

	public String getRow17Fourteen() {
		return row17Fourteen;
	}

	public void setRow17Fourteen(String row17Fourteen) {
		this.row17Fourteen = row17Fourteen;
	}

	public String getRow17Fifteen() {
		return row17Fifteen;
	}

	public void setRow17Fifteen(String row17Fifteen) {
		this.row17Fifteen = row17Fifteen;
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

	public String getRow18Five() {
		return row18Five;
	}

	public void setRow18Five(String row18Five) {
		this.row18Five = row18Five;
	}

	public String getRow18Six() {
		return row18Six;
	}

	public void setRow18Six(String row18Six) {
		this.row18Six = row18Six;
	}

	public String getRow18Seven() {
		return row18Seven;
	}

	public void setRow18Seven(String row18Seven) {
		this.row18Seven = row18Seven;
	}

	public String getRow18Eight() {
		return row18Eight;
	}

	public void setRow18Eight(String row18Eight) {
		this.row18Eight = row18Eight;
	}

	public String getRow18Nine() {
		return row18Nine;
	}

	public void setRow18Nine(String row18Nine) {
		this.row18Nine = row18Nine;
	}

	public String getRow18Ten() {
		return row18Ten;
	}

	public void setRow18Ten(String row18Ten) {
		this.row18Ten = row18Ten;
	}

	public String getRow18Eleven() {
		return row18Eleven;
	}

	public void setRow18Eleven(String row18Eleven) {
		this.row18Eleven = row18Eleven;
	}

	public String getRow18Twelve() {
		return row18Twelve;
	}

	public void setRow18Twelve(String row18Twelve) {
		this.row18Twelve = row18Twelve;
	}

	public String getRow18Thirteen() {
		return row18Thirteen;
	}

	public void setRow18Thirteen(String row18Thirteen) {
		this.row18Thirteen = row18Thirteen;
	}

	public String getRow18Fourteen() {
		return row18Fourteen;
	}

	public void setRow18Fourteen(String row18Fourteen) {
		this.row18Fourteen = row18Fourteen;
	}

	public String getRow18Fifteen() {
		return row18Fifteen;
	}

	public void setRow18Fifteen(String row18Fifteen) {
		this.row18Fifteen = row18Fifteen;
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

	public String getRow19Five() {
		return row19Five;
	}

	public void setRow19Five(String row19Five) {
		this.row19Five = row19Five;
	}

	public String getRow19Six() {
		return row19Six;
	}

	public void setRow19Six(String row19Six) {
		this.row19Six = row19Six;
	}

	public String getRow19Seven() {
		return row19Seven;
	}

	public void setRow19Seven(String row19Seven) {
		this.row19Seven = row19Seven;
	}

	public String getRow19Eight() {
		return row19Eight;
	}

	public void setRow19Eight(String row19Eight) {
		this.row19Eight = row19Eight;
	}

	public String getRow19Nine() {
		return row19Nine;
	}

	public void setRow19Nine(String row19Nine) {
		this.row19Nine = row19Nine;
	}

	public String getRow19Ten() {
		return row19Ten;
	}

	public void setRow19Ten(String row19Ten) {
		this.row19Ten = row19Ten;
	}

	public String getRow19Eleven() {
		return row19Eleven;
	}

	public void setRow19Eleven(String row19Eleven) {
		this.row19Eleven = row19Eleven;
	}

	public String getRow19Twelve() {
		return row19Twelve;
	}

	public void setRow19Twelve(String row19Twelve) {
		this.row19Twelve = row19Twelve;
	}

	public String getRow19Thirteen() {
		return row19Thirteen;
	}

	public void setRow19Thirteen(String row19Thirteen) {
		this.row19Thirteen = row19Thirteen;
	}

	public String getRow19Fourteen() {
		return row19Fourteen;
	}

	public void setRow19Fourteen(String row19Fourteen) {
		this.row19Fourteen = row19Fourteen;
	}

	public String getRow19Fifteen() {
		return row19Fifteen;
	}

	public void setRow19Fifteen(String row19Fifteen) {
		this.row19Fifteen = row19Fifteen;
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

	public String getRow20Five() {
		return row20Five;
	}

	public void setRow20Five(String row20Five) {
		this.row20Five = row20Five;
	}

	public String getRow20Six() {
		return row20Six;
	}

	public void setRow20Six(String row20Six) {
		this.row20Six = row20Six;
	}

	public String getRow20Seven() {
		return row20Seven;
	}

	public void setRow20Seven(String row20Seven) {
		this.row20Seven = row20Seven;
	}

	public String getRow20Eight() {
		return row20Eight;
	}

	public void setRow20Eight(String row20Eight) {
		this.row20Eight = row20Eight;
	}

	public String getRow20Nine() {
		return row20Nine;
	}

	public void setRow20Nine(String row20Nine) {
		this.row20Nine = row20Nine;
	}

	public String getRow20Ten() {
		return row20Ten;
	}

	public void setRow20Ten(String row20Ten) {
		this.row20Ten = row20Ten;
	}

	public String getRow20Eleven() {
		return row20Eleven;
	}

	public void setRow20Eleven(String row20Eleven) {
		this.row20Eleven = row20Eleven;
	}

	public String getRow20Twelve() {
		return row20Twelve;
	}

	public void setRow20Twelve(String row20Twelve) {
		this.row20Twelve = row20Twelve;
	}

	public String getRow20Thirteen() {
		return row20Thirteen;
	}

	public void setRow20Thirteen(String row20Thirteen) {
		this.row20Thirteen = row20Thirteen;
	}

	public String getRow20Fourteen() {
		return row20Fourteen;
	}

	public void setRow20Fourteen(String row20Fourteen) {
		this.row20Fourteen = row20Fourteen;
	}

	public String getRow20Fifteen() {
		return row20Fifteen;
	}

	public void setRow20Fifteen(String row20Fifteen) {
		this.row20Fifteen = row20Fifteen;
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

	public String getRow21Five() {
		return row21Five;
	}

	public void setRow21Five(String row21Five) {
		this.row21Five = row21Five;
	}

	public String getRow21Six() {
		return row21Six;
	}

	public void setRow21Six(String row21Six) {
		this.row21Six = row21Six;
	}

	public String getRow21Seven() {
		return row21Seven;
	}

	public void setRow21Seven(String row21Seven) {
		this.row21Seven = row21Seven;
	}

	public String getRow21Eight() {
		return row21Eight;
	}

	public void setRow21Eight(String row21Eight) {
		this.row21Eight = row21Eight;
	}

	public String getRow21Nine() {
		return row21Nine;
	}

	public void setRow21Nine(String row21Nine) {
		this.row21Nine = row21Nine;
	}

	public String getRow21Ten() {
		return row21Ten;
	}

	public void setRow21Ten(String row21Ten) {
		this.row21Ten = row21Ten;
	}

	public String getRow21Eleven() {
		return row21Eleven;
	}

	public void setRow21Eleven(String row21Eleven) {
		this.row21Eleven = row21Eleven;
	}

	public String getRow21Twelve() {
		return row21Twelve;
	}

	public void setRow21Twelve(String row21Twelve) {
		this.row21Twelve = row21Twelve;
	}

	public String getRow21Thirteen() {
		return row21Thirteen;
	}

	public void setRow21Thirteen(String row21Thirteen) {
		this.row21Thirteen = row21Thirteen;
	}

	public String getRow21Fourteen() {
		return row21Fourteen;
	}

	public void setRow21Fourteen(String row21Fourteen) {
		this.row21Fourteen = row21Fourteen;
	}

	public String getRow21Fifteen() {
		return row21Fifteen;
	}

	public void setRow21Fifteen(String row21Fifteen) {
		this.row21Fifteen = row21Fifteen;
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

	public String getRow22Three() {
		return row22Three;
	}

	public void setRow22Three(String row22Three) {
		this.row22Three = row22Three;
	}

	public String getRow22Four() {
		return row22Four;
	}

	public void setRow22Four(String row22Four) {
		this.row22Four = row22Four;
	}

	public String getRow22Five() {
		return row22Five;
	}

	public void setRow22Five(String row22Five) {
		this.row22Five = row22Five;
	}

	public String getRow22Six() {
		return row22Six;
	}

	public void setRow22Six(String row22Six) {
		this.row22Six = row22Six;
	}

	public String getRow22Seven() {
		return row22Seven;
	}

	public void setRow22Seven(String row22Seven) {
		this.row22Seven = row22Seven;
	}

	public String getRow22Eight() {
		return row22Eight;
	}

	public void setRow22Eight(String row22Eight) {
		this.row22Eight = row22Eight;
	}

	public String getRow22Nine() {
		return row22Nine;
	}

	public void setRow22Nine(String row22Nine) {
		this.row22Nine = row22Nine;
	}

	public String getRow22Ten() {
		return row22Ten;
	}

	public void setRow22Ten(String row22Ten) {
		this.row22Ten = row22Ten;
	}

	public String getRow22Eleven() {
		return row22Eleven;
	}

	public void setRow22Eleven(String row22Eleven) {
		this.row22Eleven = row22Eleven;
	}

	public String getRow22Twelve() {
		return row22Twelve;
	}

	public void setRow22Twelve(String row22Twelve) {
		this.row22Twelve = row22Twelve;
	}

	public String getRow22Thirteen() {
		return row22Thirteen;
	}

	public void setRow22Thirteen(String row22Thirteen) {
		this.row22Thirteen = row22Thirteen;
	}

	public String getRow22Fourteen() {
		return row22Fourteen;
	}

	public void setRow22Fourteen(String row22Fourteen) {
		this.row22Fourteen = row22Fourteen;
	}

	public String getRow22Fifteen() {
		return row22Fifteen;
	}

	public void setRow22Fifteen(String row22Fifteen) {
		this.row22Fifteen = row22Fifteen;
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

	public String getRow23Three() {
		return row23Three;
	}

	public void setRow23Three(String row23Three) {
		this.row23Three = row23Three;
	}

	public String getRow23Four() {
		return row23Four;
	}

	public void setRow23Four(String row23Four) {
		this.row23Four = row23Four;
	}

	public String getRow23Five() {
		return row23Five;
	}

	public void setRow23Five(String row23Five) {
		this.row23Five = row23Five;
	}

	public String getRow23Six() {
		return row23Six;
	}

	public void setRow23Six(String row23Six) {
		this.row23Six = row23Six;
	}

	public String getRow23Seven() {
		return row23Seven;
	}

	public void setRow23Seven(String row23Seven) {
		this.row23Seven = row23Seven;
	}

	public String getRow23Eight() {
		return row23Eight;
	}

	public void setRow23Eight(String row23Eight) {
		this.row23Eight = row23Eight;
	}

	public String getRow23Nine() {
		return row23Nine;
	}

	public void setRow23Nine(String row23Nine) {
		this.row23Nine = row23Nine;
	}

	public String getRow23Ten() {
		return row23Ten;
	}

	public void setRow23Ten(String row23Ten) {
		this.row23Ten = row23Ten;
	}

	public String getRow23Eleven() {
		return row23Eleven;
	}

	public void setRow23Eleven(String row23Eleven) {
		this.row23Eleven = row23Eleven;
	}

	public String getRow23Twelve() {
		return row23Twelve;
	}

	public void setRow23Twelve(String row23Twelve) {
		this.row23Twelve = row23Twelve;
	}

	public String getRow23Thirteen() {
		return row23Thirteen;
	}

	public void setRow23Thirteen(String row23Thirteen) {
		this.row23Thirteen = row23Thirteen;
	}

	public String getRow23Fourteen() {
		return row23Fourteen;
	}

	public void setRow23Fourteen(String row23Fourteen) {
		this.row23Fourteen = row23Fourteen;
	}

	public String getRow23Fifteen() {
		return row23Fifteen;
	}

	public void setRow23Fifteen(String row23Fifteen) {
		this.row23Fifteen = row23Fifteen;
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

	public String getRowSixteen() {
		return rowSixteen;
	}

	public void setRowSixteen(String rowSixteen) {
		this.rowSixteen = rowSixteen;
	}

	public String getRow1Sixteen() {
		return row1Sixteen;
	}

	public void setRow1Sixteen(String row1Sixteen) {
		this.row1Sixteen = row1Sixteen;
	}

	public String getRow2Sixteen() {
		return row2Sixteen;
	}

	public void setRow2Sixteen(String row2Sixteen) {
		this.row2Sixteen = row2Sixteen;
	}

	public String getRow3Sixteen() {
		return row3Sixteen;
	}

	public void setRow3Sixteen(String row3Sixteen) {
		this.row3Sixteen = row3Sixteen;
	}

	public String getRow4Sixteen() {
		return row4Sixteen;
	}

	public void setRow4Sixteen(String row4Sixteen) {
		this.row4Sixteen = row4Sixteen;
	}

	public String getRow5Sixteen() {
		return row5Sixteen;
	}

	public void setRow5Sixteen(String row5Sixteen) {
		this.row5Sixteen = row5Sixteen;
	}

	public String getRow6Sixteen() {
		return row6Sixteen;
	}

	public void setRow6Sixteen(String row6Sixteen) {
		this.row6Sixteen = row6Sixteen;
	}

	public String getRow7Sixteen() {
		return row7Sixteen;
	}

	public void setRow7Sixteen(String row7Sixteen) {
		this.row7Sixteen = row7Sixteen;
	}

	public String getRow8Sixteen() {
		return row8Sixteen;
	}

	public void setRow8Sixteen(String row8Sixteen) {
		this.row8Sixteen = row8Sixteen;
	}

	public String getRow9Sixteen() {
		return row9Sixteen;
	}

	public void setRow9Sixteen(String row9Sixteen) {
		this.row9Sixteen = row9Sixteen;
	}

	public String getRow10Sixteen() {
		return row10Sixteen;
	}

	public void setRow10Sixteen(String row10Sixteen) {
		this.row10Sixteen = row10Sixteen;
	}

	public String getRow11Sixteen() {
		return row11Sixteen;
	}

	public void setRow11Sixteen(String row11Sixteen) {
		this.row11Sixteen = row11Sixteen;
	}

	public String getRow12Sixteen() {
		return row12Sixteen;
	}

	public void setRow12Sixteen(String row12Sixteen) {
		this.row12Sixteen = row12Sixteen;
	}

	public String getRow14Sixteen() {
		return row14Sixteen;
	}

	public void setRow14Sixteen(String row14Sixteen) {
		this.row14Sixteen = row14Sixteen;
	}

	public String getRow15Sixteen() {
		return row15Sixteen;
	}

	public void setRow15Sixteen(String row15Sixteen) {
		this.row15Sixteen = row15Sixteen;
	}

	public String getRow16Sixteen() {
		return row16Sixteen;
	}

	public void setRow16Sixteen(String row16Sixteen) {
		this.row16Sixteen = row16Sixteen;
	}

	public String getRow17Sixteen() {
		return row17Sixteen;
	}

	public void setRow17Sixteen(String row17Sixteen) {
		this.row17Sixteen = row17Sixteen;
	}

	public String getRow18Sixteen() {
		return row18Sixteen;
	}

	public void setRow18Sixteen(String row18Sixteen) {
		this.row18Sixteen = row18Sixteen;
	}

	public String getRow19Sixteen() {
		return row19Sixteen;
	}

	public void setRow19Sixteen(String row19Sixteen) {
		this.row19Sixteen = row19Sixteen;
	}

	public String getRow20Sixteen() {
		return row20Sixteen;
	}

	public void setRow20Sixteen(String row20Sixteen) {
		this.row20Sixteen = row20Sixteen;
	}

	public String getRow21Sixteen() {
		return row21Sixteen;
	}

	public void setRow21Sixteen(String row21Sixteen) {
		this.row21Sixteen = row21Sixteen;
	}

	public String getRow22Sixteen() {
		return row22Sixteen;
	}

	public void setRow22Sixteen(String row22Sixteen) {
		this.row22Sixteen = row22Sixteen;
	}

	public String getRow23Sixteen() {
		return row23Sixteen;
	}

	public void setRow23Sixteen(String row23Sixteen) {
		this.row23Sixteen = row23Sixteen;
	}

	public String getRow13Sixteen() {
		return row13Sixteen;
	}

	public void setRow13Sixteen(String row13Sixteen) {
		this.row13Sixteen = row13Sixteen;
	}	
	

	public String senData(){
		if("1".equals(this.getDelFlag())){ //删除数据
    		return "D" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
    				+ this.getRowOne() + "|" + this.getRowTwo() + "|" + this.getRowThree() + "|" + this.getRowFour() + "|" + this.getRowFive() + "|"
    				+ this.getRowSix() + "|" + this.getRowSeven() + "|" + this.getRowEight() + "|" + this.getRowNine() + "|" + this.getRowTen() + "|"
    				+ this.getRowEleven() + "|" + this.getRowTwelve() + "|" + this.getRowThirteen() + "|" + this.getRowFourteen() + "|" + this.getRowFifteen() + "|" + this.getRowSixteen() + "|"
    				
					+ this.getRow1One() + "|" + this.getRow1Two() + "|" + this.getRow1Three() + "|" + this.getRow1Four() + "|" + this.getRow1Five() + "|"
					+ this.getRow1Six() + "|" + this.getRow1Seven() + "|" + this.getRow1Eight() + "|" + this.getRow1Nine() + "|" + this.getRow1Ten() + "|"
					+ this.getRow1Eleven() + "|" + this.getRow1Twelve() + "|" + this.getRow1Thirteen() + "|" + this.getRow1Fourteen() + "|" + this.getRow1Fifteen() + "|" + this.getRow1Sixteen() + "|"
					
					+ this.getRow2One() + "|" + this.getRow2Two() + "|" + this.getRow2Three() + "|" + this.getRow2Four() + "|" + this.getRow2Five() + "|"
					+ this.getRow2Six() + "|" + this.getRow2Seven() + "|" + this.getRow2Eight() + "|" + this.getRow2Nine() + "|" + this.getRow2Ten() + "|"
					+ this.getRow2Eleven() + "|" + this.getRow2Twelve() + "|" + this.getRow2Thirteen() + "|" + this.getRow2Fourteen() + "|" + this.getRow2Fifteen() + "|" + this.getRow2Sixteen() + "|"
					
					+ this.getRow3One() + "|" + this.getRow3Two() + "|" + this.getRow3Three() + "|" + this.getRow3Four() + "|" + this.getRow3Five() + "|"
					+ this.getRow3Six() + "|" + this.getRow3Seven() + "|" + this.getRow3Eight() + "|" + this.getRow3Nine() + "|" + this.getRow3Ten() + "|"
					+ this.getRow3Eleven() + "|" + this.getRow3Twelve() + "|" + this.getRow3Thirteen() + "|" + this.getRow3Fourteen() + "|" + this.getRow3Fifteen() + "|" + this.getRow3Sixteen() + "|"
					
					+ this.getRow4One() + "|" + this.getRow4Two() + "|" + this.getRow4Three() + "|" + this.getRow4Four() + "|" + this.getRow4Five() + "|"
					+ this.getRow4Six() + "|" + this.getRow4Seven() + "|" + this.getRow4Eight() + "|" + this.getRow4Nine() + "|" + this.getRow4Ten() + "|"
					+ this.getRow4Eleven() + "|" + this.getRow4Twelve() + "|" + this.getRow4Thirteen() + "|" + this.getRow4Fourteen() + "|" + this.getRow4Fifteen() + "|" + this.getRow4Sixteen() + "|"
					
					+ this.getRow5One() + "|" + this.getRow5Two() + "|" + this.getRow5Three() + "|" + this.getRow5Four() + "|" + this.getRow5Five() + "|"
					+ this.getRow5Six() + "|" + this.getRow5Seven() + "|" + this.getRow5Eight() + "|" + this.getRow5Nine() + "|" + this.getRow5Ten() + "|"
					+ this.getRow5Eleven() + "|" + this.getRow5Twelve() + "|" + this.getRow5Thirteen() + "|" + this.getRow5Fourteen() + "|" + this.getRow5Fifteen() + "|" + this.getRow5Sixteen() + "|"
					
					+ this.getRow6One() + "|" + this.getRow6Two() + "|" + this.getRow6Three() + "|" + this.getRow6Four() + "|" + this.getRow6Five() + "|"
					+ this.getRow6Six() + "|" + this.getRow6Seven() + "|" + this.getRow6Eight() + "|" + this.getRow6Nine() + "|" + this.getRow6Ten() + "|"
					+ this.getRow6Eleven() + "|" + this.getRow6Twelve() + "|" + this.getRow6Thirteen() + "|" + this.getRow6Fourteen() + "|" + this.getRow6Fifteen() + "|" + this.getRow6Sixteen() + "|"
					
					+ this.getRow7One() + "|" + this.getRow7Two() + "|" + this.getRow7Three() + "|" + this.getRow7Four() + "|" + this.getRow7Five() + "|"
					+ this.getRow7Six() + "|" + this.getRow7Seven() + "|" + this.getRow7Eight() + "|" + this.getRow7Nine() + "|" + this.getRow7Ten() + "|"
					+ this.getRow7Eleven() + "|" + this.getRow7Twelve() + "|" + this.getRow7Thirteen() + "|" + this.getRow7Fourteen() + "|" + this.getRow7Fifteen() + "|" + this.getRow7Sixteen() + "|"
					
					+ this.getRow8One() + "|" + this.getRow8Two() + "|" + this.getRow8Three() + "|" + this.getRow8Four() + "|" + this.getRow8Five() + "|"
					+ this.getRow8Six() + "|" + this.getRow8Seven() + "|" + this.getRow8Eight() + "|" + this.getRow8Nine() + "|" + this.getRow8Ten() + "|"
					+ this.getRow8Eleven() + "|" + this.getRow8Twelve() + "|" + this.getRow8Thirteen() + "|" + this.getRow8Fourteen() + "|" + this.getRow8Fifteen() + "|" + this.getRow8Sixteen() + "|"
					
					+ this.getRow9One() + "|" + this.getRow9Two() + "|" + this.getRow9Three() + "|" + this.getRow9Four() + "|" + this.getRow9Five() + "|"
					+ this.getRow9Six() + "|" + this.getRow9Seven() + "|" + this.getRow9Eight() + "|" + this.getRow9Nine() + "|" + this.getRow9Ten() + "|"
					+ this.getRow9Eleven() + "|" + this.getRow9Twelve() + "|" + this.getRow9Thirteen() + "|" + this.getRow9Fourteen() + "|" + this.getRow9Fifteen() + "|" + this.getRow9Sixteen() + "|"
					
					+ this.getRow10One() + "|" + this.getRow10Two() + "|" + this.getRow10Three() + "|" + this.getRow10Four() + "|" + this.getRow10Five() + "|"
					+ this.getRow10Six() + "|" + this.getRow10Seven() + "|" + this.getRow10Eight() + "|" + this.getRow10Nine() + "|" + this.getRow10Ten() + "|"
					+ this.getRow10Eleven() + "|" + this.getRow10Twelve() + "|" + this.getRow10Thirteen() + "|" + this.getRow10Fourteen() + "|" + this.getRow10Fifteen() + "|" + this.getRow10Sixteen() + "|"
					
					
					+ this.getRow11One() + "|" + this.getRow11Two() + "|" + this.getRow11Three() + "|" + this.getRow11Four() + "|" + this.getRow11Five() + "|"
					+ this.getRow11Six() + "|" + this.getRow11Seven() + "|" + this.getRow11Eight() + "|" + this.getRow11Nine() + "|" + this.getRow11Ten() + "|"
					+ this.getRow11Eleven() + "|" + this.getRow11Twelve() + "|" + this.getRow11Thirteen() + "|" + this.getRow11Fourteen() + "|" + this.getRow11Fifteen() + "|" + this.getRow11Sixteen() + "|"
					
					+ this.getRow12One() + "|" + this.getRow12Two() + "|" + this.getRow12Three() + "|" + this.getRow12Four() + "|" + this.getRow12Five() + "|"
					+ this.getRow12Six() + "|" + this.getRow12Seven() + "|" + this.getRow12Eight() + "|" + this.getRow12Nine() + "|" + this.getRow12Ten() + "|"
					+ this.getRow12Eleven() + "|" + this.getRow12Twelve() + "|" + this.getRow12Thirteen() + "|" + this.getRow12Fourteen() + "|" + this.getRow12Fifteen() + "|" + this.getRow12Sixteen() + "|"

					+ this.getRow13One() + "|" + this.getRow13Two() + "|" + this.getRow13Three() + "|" + this.getRow13Four() + "|" + this.getRow13Five() + "|"
					+ this.getRow13Six() + "|" + this.getRow13Seven() + "|" + this.getRow13Eight() + "|" + this.getRow13Nine() + "|" + this.getRow13Ten() + "|"
					+ this.getRow13Eleven() + "|" + this.getRow13Twelve() + "|" + this.getRow13Thirteen() + "|" + this.getRow13Fourteen() + "|" + this.getRow13Fifteen() + "|" + this.getRow13Sixteen() + "|"

					+ this.getRow14One() + "|" + this.getRow14Two() + "|" + this.getRow14Three() + "|" + this.getRow14Four() + "|" + this.getRow14Five() + "|"
					+ this.getRow14Six() + "|" + this.getRow14Seven() + "|" + this.getRow14Eight() + "|" + this.getRow14Nine() + "|" + this.getRow14Ten() + "|"
					+ this.getRow14Eleven() + "|" + this.getRow14Twelve() + "|" + this.getRow14Thirteen() + "|" + this.getRow14Fourteen() + "|" + this.getRow14Fifteen() + "|" + this.getRow14Sixteen() + "|"

					+ this.getRow15One() + "|" + this.getRow15Two() + "|" + this.getRow15Three() + "|" + this.getRow15Four() + "|" + this.getRow15Five() + "|"
					+ this.getRow15Six() + "|" + this.getRow15Seven() + "|" + this.getRow15Eight() + "|" + this.getRow15Nine() + "|" + this.getRow15Ten() + "|"
					+ this.getRow15Eleven() + "|" + this.getRow15Twelve() + "|" + this.getRow15Thirteen() + "|" + this.getRow15Fourteen() + "|" + this.getRow15Fifteen() + "|" + this.getRow15Sixteen() + "|"
  
					+ this.getRow16One() + "|" + this.getRow16Two() + "|" + this.getRow16Three() + "|" + this.getRow16Four() + "|" + this.getRow16Five() + "|"
					+ this.getRow16Six() + "|" + this.getRow16Seven() + "|" + this.getRow16Eight() + "|" + this.getRow16Nine() + "|" + this.getRow16Ten() + "|"
					+ this.getRow16Eleven() + "|" + this.getRow16Twelve() + "|" + this.getRow16Thirteen() + "|" + this.getRow16Fourteen() + "|" + this.getRow16Fifteen() + "|" + this.getRow16Sixteen() + "|"

					+ this.getRow17One() + "|" + this.getRow17Two() + "|" + this.getRow17Three() + "|" + this.getRow17Four() + "|" + this.getRow17Five() + "|"
					+ this.getRow17Six() + "|" + this.getRow17Seven() + "|" + this.getRow17Eight() + "|" + this.getRow17Nine() + "|" + this.getRow17Ten() + "|"
					+ this.getRow17Eleven() + "|" + this.getRow17Twelve() + "|" + this.getRow17Thirteen() + "|" + this.getRow17Fourteen() + "|" + this.getRow17Fifteen() + "|" + this.getRow17Sixteen() + "|"
					
					+ this.getRow18One() + "|" + this.getRow18Two() + "|" + this.getRow18Three() + "|" + this.getRow18Four() + "|" + this.getRow18Five() + "|"
					+ this.getRow18Six() + "|" + this.getRow18Seven() + "|" + this.getRow18Eight() + "|" + this.getRow18Nine() + "|" + this.getRow18Ten() + "|"
					+ this.getRow18Eleven() + "|" + this.getRow18Twelve() + "|" + this.getRow18Thirteen() + "|" + this.getRow18Fourteen() + "|" + this.getRow18Fifteen() + "|" + this.getRow18Sixteen() + "|"
					
					+ this.getRow19One() + "|" + this.getRow19Two() + "|" + this.getRow19Three() + "|" + this.getRow19Four() + "|" + this.getRow19Five() + "|"
					+ this.getRow19Six() + "|" + this.getRow19Seven() + "|" + this.getRow19Eight() + "|" + this.getRow19Nine() + "|" + this.getRow19Ten() + "|"
					+ this.getRow19Eleven() + "|" + this.getRow19Twelve() + "|" + this.getRow19Thirteen() + "|" + this.getRow19Fourteen() + "|" + this.getRow19Fifteen() + "|" + this.getRow19Sixteen() + "|"
					
					+ this.getRow20One() + "|" + this.getRow20Two() + "|" + this.getRow20Three() + "|" + this.getRow20Four() + "|" + this.getRow20Five() + "|"
					+ this.getRow20Six() + "|" + this.getRow20Seven() + "|" + this.getRow20Eight() + "|" + this.getRow20Nine() + "|" + this.getRow20Ten() + "|"
					+ this.getRow20Eleven() + "|" + this.getRow20Twelve() + "|" + this.getRow20Thirteen() + "|" + this.getRow20Fourteen() + "|" + this.getRow20Fifteen() + "|" + this.getRow20Sixteen() + "|"
					
					+ this.getRow21One() + "|" + this.getRow21Two() + "|" + this.getRow21Three() + "|" + this.getRow21Four() + "|" + this.getRow21Five() + "|"
					+ this.getRow21Six() + "|" + this.getRow21Seven() + "|" + this.getRow21Eight() + "|" + this.getRow21Nine() + "|" + this.getRow21Ten() + "|"
					+ this.getRow21Eleven() + "|" + this.getRow21Twelve() + "|" + this.getRow21Thirteen() + "|" + this.getRow21Fourteen() + "|" + this.getRow21Fifteen() + "|" + this.getRow21Sixteen() + "|"

					+ this.getRow22One() + "|" + this.getRow22Two() + "|" + this.getRow22Three() + "|" + this.getRow22Four() + "|" + this.getRow22Five() + "|"
					+ this.getRow22Six() + "|" + this.getRow22Seven() + "|" + this.getRow22Eight() + "|" + this.getRow22Nine() + "|" + this.getRow22Ten() + "|"
					+ this.getRow22Eleven() + "|" + this.getRow22Twelve() + "|" + this.getRow22Thirteen() + "|" + this.getRow22Fourteen() + "|" + this.getRow22Fifteen() + "|" + this.getRow22Sixteen() + "|"

					+ this.getRow23One() + "|" + this.getRow23Two() + "|" + this.getRow23Three() + "|" + this.getRow23Four() + "|" + this.getRow23Five() + "|"
					+ this.getRow23Six() + "|" + this.getRow23Seven() + "|" + this.getRow23Eight() + "|" + this.getRow23Nine() + "|" + this.getRow23Ten() + "|"
					+ this.getRow23Eleven() + "|" + this.getRow23Twelve() + "|" + this.getRow23Thirteen() + "|" + this.getRow23Fourteen() + "|" + this.getRow23Fifteen() + "|" + this.getRow23Sixteen() + "|"
    				
    			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
    			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId();    		
    	}else{ //新增修改数据
    		if("0".equals(this.getPushStatus())){
    			return "A" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() + "|" + this.getRowTwo() + "|" + this.getRowThree() + "|" + this.getRowFour() + "|" + this.getRowFive() + "|"
        				+ this.getRowSix() + "|" + this.getRowSeven() + "|" + this.getRowEight() + "|" + this.getRowNine() + "|" + this.getRowTen() + "|"
        				+ this.getRowEleven() + "|" + this.getRowTwelve() + "|" + this.getRowThirteen() + "|" + this.getRowFourteen() + "|" + this.getRowFifteen() + "|" + this.getRowSixteen() + "|"
        				
    					+ this.getRow1One() + "|" + this.getRow1Two() + "|" + this.getRow1Three() + "|" + this.getRow1Four() + "|" + this.getRow1Five() + "|"
    					+ this.getRow1Six() + "|" + this.getRow1Seven() + "|" + this.getRow1Eight() + "|" + this.getRow1Nine() + "|" + this.getRow1Ten() + "|"
    					+ this.getRow1Eleven() + "|" + this.getRow1Twelve() + "|" + this.getRow1Thirteen() + "|" + this.getRow1Fourteen() + "|" + this.getRow1Fifteen() + "|" + this.getRow1Sixteen() + "|"
    					
    					+ this.getRow2One() + "|" + this.getRow2Two() + "|" + this.getRow2Three() + "|" + this.getRow2Four() + "|" + this.getRow2Five() + "|"
    					+ this.getRow2Six() + "|" + this.getRow2Seven() + "|" + this.getRow2Eight() + "|" + this.getRow2Nine() + "|" + this.getRow2Ten() + "|"
    					+ this.getRow2Eleven() + "|" + this.getRow2Twelve() + "|" + this.getRow2Thirteen() + "|" + this.getRow2Fourteen() + "|" + this.getRow2Fifteen() + "|" + this.getRow2Sixteen() + "|"
    					
    					+ this.getRow3One() + "|" + this.getRow3Two() + "|" + this.getRow3Three() + "|" + this.getRow3Four() + "|" + this.getRow3Five() + "|"
    					+ this.getRow3Six() + "|" + this.getRow3Seven() + "|" + this.getRow3Eight() + "|" + this.getRow3Nine() + "|" + this.getRow3Ten() + "|"
    					+ this.getRow3Eleven() + "|" + this.getRow3Twelve() + "|" + this.getRow3Thirteen() + "|" + this.getRow3Fourteen() + "|" + this.getRow3Fifteen() + "|" + this.getRow3Sixteen() + "|"
    					
    					+ this.getRow4One() + "|" + this.getRow4Two() + "|" + this.getRow4Three() + "|" + this.getRow4Four() + "|" + this.getRow4Five() + "|"
    					+ this.getRow4Six() + "|" + this.getRow4Seven() + "|" + this.getRow4Eight() + "|" + this.getRow4Nine() + "|" + this.getRow4Ten() + "|"
    					+ this.getRow4Eleven() + "|" + this.getRow4Twelve() + "|" + this.getRow4Thirteen() + "|" + this.getRow4Fourteen() + "|" + this.getRow4Fifteen() + "|" + this.getRow4Sixteen() + "|"
    					
    					+ this.getRow5One() + "|" + this.getRow5Two() + "|" + this.getRow5Three() + "|" + this.getRow5Four() + "|" + this.getRow5Five() + "|"
    					+ this.getRow5Six() + "|" + this.getRow5Seven() + "|" + this.getRow5Eight() + "|" + this.getRow5Nine() + "|" + this.getRow5Ten() + "|"
    					+ this.getRow5Eleven() + "|" + this.getRow5Twelve() + "|" + this.getRow5Thirteen() + "|" + this.getRow5Fourteen() + "|" + this.getRow5Fifteen() + "|" + this.getRow5Sixteen() + "|"
    					
    					+ this.getRow6One() + "|" + this.getRow6Two() + "|" + this.getRow6Three() + "|" + this.getRow6Four() + "|" + this.getRow6Five() + "|"
    					+ this.getRow6Six() + "|" + this.getRow6Seven() + "|" + this.getRow6Eight() + "|" + this.getRow6Nine() + "|" + this.getRow6Ten() + "|"
    					+ this.getRow6Eleven() + "|" + this.getRow6Twelve() + "|" + this.getRow6Thirteen() + "|" + this.getRow6Fourteen() + "|" + this.getRow6Fifteen() + "|" + this.getRow6Sixteen() + "|"
    					
    					+ this.getRow7One() + "|" + this.getRow7Two() + "|" + this.getRow7Three() + "|" + this.getRow7Four() + "|" + this.getRow7Five() + "|"
    					+ this.getRow7Six() + "|" + this.getRow7Seven() + "|" + this.getRow7Eight() + "|" + this.getRow7Nine() + "|" + this.getRow7Ten() + "|"
    					+ this.getRow7Eleven() + "|" + this.getRow7Twelve() + "|" + this.getRow7Thirteen() + "|" + this.getRow7Fourteen() + "|" + this.getRow7Fifteen() + "|" + this.getRow7Sixteen() + "|"
    					
    					+ this.getRow8One() + "|" + this.getRow8Two() + "|" + this.getRow8Three() + "|" + this.getRow8Four() + "|" + this.getRow8Five() + "|"
    					+ this.getRow8Six() + "|" + this.getRow8Seven() + "|" + this.getRow8Eight() + "|" + this.getRow8Nine() + "|" + this.getRow8Ten() + "|"
    					+ this.getRow8Eleven() + "|" + this.getRow8Twelve() + "|" + this.getRow8Thirteen() + "|" + this.getRow8Fourteen() + "|" + this.getRow8Fifteen() + "|" + this.getRow8Sixteen() + "|"
    					
    					+ this.getRow9One() + "|" + this.getRow9Two() + "|" + this.getRow9Three() + "|" + this.getRow9Four() + "|" + this.getRow9Five() + "|"
    					+ this.getRow9Six() + "|" + this.getRow9Seven() + "|" + this.getRow9Eight() + "|" + this.getRow9Nine() + "|" + this.getRow9Ten() + "|"
    					+ this.getRow9Eleven() + "|" + this.getRow9Twelve() + "|" + this.getRow9Thirteen() + "|" + this.getRow9Fourteen() + "|" + this.getRow9Fifteen() + "|" + this.getRow9Sixteen() + "|"
    					
    					+ this.getRow10One() + "|" + this.getRow10Two() + "|" + this.getRow10Three() + "|" + this.getRow10Four() + "|" + this.getRow10Five() + "|"
    					+ this.getRow10Six() + "|" + this.getRow10Seven() + "|" + this.getRow10Eight() + "|" + this.getRow10Nine() + "|" + this.getRow10Ten() + "|"
    					+ this.getRow10Eleven() + "|" + this.getRow10Twelve() + "|" + this.getRow10Thirteen() + "|" + this.getRow10Fourteen() + "|" + this.getRow10Fifteen() + "|" + this.getRow10Sixteen() + "|"
    					
    					
    					+ this.getRow11One() + "|" + this.getRow11Two() + "|" + this.getRow11Three() + "|" + this.getRow11Four() + "|" + this.getRow11Five() + "|"
    					+ this.getRow11Six() + "|" + this.getRow11Seven() + "|" + this.getRow11Eight() + "|" + this.getRow11Nine() + "|" + this.getRow11Ten() + "|"
    					+ this.getRow11Eleven() + "|" + this.getRow11Twelve() + "|" + this.getRow11Thirteen() + "|" + this.getRow11Fourteen() + "|" + this.getRow11Fifteen() + "|" + this.getRow11Sixteen() + "|"
    					
    					+ this.getRow12One() + "|" + this.getRow12Two() + "|" + this.getRow12Three() + "|" + this.getRow12Four() + "|" + this.getRow12Five() + "|"
    					+ this.getRow12Six() + "|" + this.getRow12Seven() + "|" + this.getRow12Eight() + "|" + this.getRow12Nine() + "|" + this.getRow12Ten() + "|"
    					+ this.getRow12Eleven() + "|" + this.getRow12Twelve() + "|" + this.getRow12Thirteen() + "|" + this.getRow12Fourteen() + "|" + this.getRow12Fifteen() + "|" + this.getRow12Sixteen() + "|"

    					+ this.getRow13One() + "|" + this.getRow13Two() + "|" + this.getRow13Three() + "|" + this.getRow13Four() + "|" + this.getRow13Five() + "|"
    					+ this.getRow13Six() + "|" + this.getRow13Seven() + "|" + this.getRow13Eight() + "|" + this.getRow13Nine() + "|" + this.getRow13Ten() + "|"
    					+ this.getRow13Eleven() + "|" + this.getRow13Twelve() + "|" + this.getRow13Thirteen() + "|" + this.getRow13Fourteen() + "|" + this.getRow13Fifteen() + "|" + this.getRow13Sixteen() + "|"

    					+ this.getRow14One() + "|" + this.getRow14Two() + "|" + this.getRow14Three() + "|" + this.getRow14Four() + "|" + this.getRow14Five() + "|"
    					+ this.getRow14Six() + "|" + this.getRow14Seven() + "|" + this.getRow14Eight() + "|" + this.getRow14Nine() + "|" + this.getRow14Ten() + "|"
    					+ this.getRow14Eleven() + "|" + this.getRow14Twelve() + "|" + this.getRow14Thirteen() + "|" + this.getRow14Fourteen() + "|" + this.getRow14Fifteen() + "|" + this.getRow14Sixteen() + "|"

    					+ this.getRow15One() + "|" + this.getRow15Two() + "|" + this.getRow15Three() + "|" + this.getRow15Four() + "|" + this.getRow15Five() + "|"
    					+ this.getRow15Six() + "|" + this.getRow15Seven() + "|" + this.getRow15Eight() + "|" + this.getRow15Nine() + "|" + this.getRow15Ten() + "|"
    					+ this.getRow15Eleven() + "|" + this.getRow15Twelve() + "|" + this.getRow15Thirteen() + "|" + this.getRow15Fourteen() + "|" + this.getRow15Fifteen() + "|" + this.getRow15Sixteen() + "|"
      
    					+ this.getRow16One() + "|" + this.getRow16Two() + "|" + this.getRow16Three() + "|" + this.getRow16Four() + "|" + this.getRow16Five() + "|"
    					+ this.getRow16Six() + "|" + this.getRow16Seven() + "|" + this.getRow16Eight() + "|" + this.getRow16Nine() + "|" + this.getRow16Ten() + "|"
    					+ this.getRow16Eleven() + "|" + this.getRow16Twelve() + "|" + this.getRow16Thirteen() + "|" + this.getRow16Fourteen() + "|" + this.getRow16Fifteen() + "|" + this.getRow16Sixteen() + "|"

    					+ this.getRow17One() + "|" + this.getRow17Two() + "|" + this.getRow17Three() + "|" + this.getRow17Four() + "|" + this.getRow17Five() + "|"
    					+ this.getRow17Six() + "|" + this.getRow17Seven() + "|" + this.getRow17Eight() + "|" + this.getRow17Nine() + "|" + this.getRow17Ten() + "|"
    					+ this.getRow17Eleven() + "|" + this.getRow17Twelve() + "|" + this.getRow17Thirteen() + "|" + this.getRow17Fourteen() + "|" + this.getRow17Fifteen() + "|" + this.getRow17Sixteen() + "|"
    					
    					+ this.getRow18One() + "|" + this.getRow18Two() + "|" + this.getRow18Three() + "|" + this.getRow18Four() + "|" + this.getRow18Five() + "|"
    					+ this.getRow18Six() + "|" + this.getRow18Seven() + "|" + this.getRow18Eight() + "|" + this.getRow18Nine() + "|" + this.getRow18Ten() + "|"
    					+ this.getRow18Eleven() + "|" + this.getRow18Twelve() + "|" + this.getRow18Thirteen() + "|" + this.getRow18Fourteen() + "|" + this.getRow18Fifteen() + "|" + this.getRow18Sixteen() + "|"
    					
    					+ this.getRow19One() + "|" + this.getRow19Two() + "|" + this.getRow19Three() + "|" + this.getRow19Four() + "|" + this.getRow19Five() + "|"
    					+ this.getRow19Six() + "|" + this.getRow19Seven() + "|" + this.getRow19Eight() + "|" + this.getRow19Nine() + "|" + this.getRow19Ten() + "|"
    					+ this.getRow19Eleven() + "|" + this.getRow19Twelve() + "|" + this.getRow19Thirteen() + "|" + this.getRow19Fourteen() + "|" + this.getRow19Fifteen() + "|" + this.getRow19Sixteen() + "|"
    					
    					+ this.getRow20One() + "|" + this.getRow20Two() + "|" + this.getRow20Three() + "|" + this.getRow20Four() + "|" + this.getRow20Five() + "|"
    					+ this.getRow20Six() + "|" + this.getRow20Seven() + "|" + this.getRow20Eight() + "|" + this.getRow20Nine() + "|" + this.getRow20Ten() + "|"
    					+ this.getRow20Eleven() + "|" + this.getRow20Twelve() + "|" + this.getRow20Thirteen() + "|" + this.getRow20Fourteen() + "|" + this.getRow20Fifteen() + "|" + this.getRow20Sixteen() + "|"
    					
    					+ this.getRow21One() + "|" + this.getRow21Two() + "|" + this.getRow21Three() + "|" + this.getRow21Four() + "|" + this.getRow21Five() + "|"
    					+ this.getRow21Six() + "|" + this.getRow21Seven() + "|" + this.getRow21Eight() + "|" + this.getRow21Nine() + "|" + this.getRow21Ten() + "|"
    					+ this.getRow21Eleven() + "|" + this.getRow21Twelve() + "|" + this.getRow21Thirteen() + "|" + this.getRow21Fourteen() + "|" + this.getRow21Fifteen() + "|" + this.getRow21Sixteen() + "|"

    					+ this.getRow22One() + "|" + this.getRow22Two() + "|" + this.getRow22Three() + "|" + this.getRow22Four() + "|" + this.getRow22Five() + "|"
    					+ this.getRow22Six() + "|" + this.getRow22Seven() + "|" + this.getRow22Eight() + "|" + this.getRow22Nine() + "|" + this.getRow22Ten() + "|"
    					+ this.getRow22Eleven() + "|" + this.getRow22Twelve() + "|" + this.getRow22Thirteen() + "|" + this.getRow22Fourteen() + "|" + this.getRow22Fifteen() + "|" + this.getRow22Sixteen() + "|"

    					+ this.getRow23One() + "|" + this.getRow23Two() + "|" + this.getRow23Three() + "|" + this.getRow23Four() + "|" + this.getRow23Five() + "|"
    					+ this.getRow23Six() + "|" + this.getRow23Seven() + "|" + this.getRow23Eight() + "|" + this.getRow23Nine() + "|" + this.getRow23Ten() + "|"
    					+ this.getRow23Eleven() + "|" + this.getRow23Twelve() + "|" + this.getRow23Thirteen() + "|" + this.getRow23Fourteen() + "|" + this.getRow23Fifteen() + "|" + this.getRow23Sixteen() + "|"
        				
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
        			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId(); 
    		}else{
    			return "U" + "|" + this.getCompanyName() + "|" +this.getReportName()+"|"
        				+ this.getRowOne() + "|" + this.getRowTwo() + "|" + this.getRowThree() + "|" + this.getRowFour() + "|" + this.getRowFive() + "|"
        				+ this.getRowSix() + "|" + this.getRowSeven() + "|" + this.getRowEight() + "|" + this.getRowNine() + "|" + this.getRowTen() + "|"
        				+ this.getRowEleven() + "|" + this.getRowTwelve() + "|" + this.getRowThirteen() + "|" + this.getRowFourteen() + "|" + this.getRowFifteen() + "|" + this.getRowSixteen() + "|"
        				
    					+ this.getRow1One() + "|" + this.getRow1Two() + "|" + this.getRow1Three() + "|" + this.getRow1Four() + "|" + this.getRow1Five() + "|"
    					+ this.getRow1Six() + "|" + this.getRow1Seven() + "|" + this.getRow1Eight() + "|" + this.getRow1Nine() + "|" + this.getRow1Ten() + "|"
    					+ this.getRow1Eleven() + "|" + this.getRow1Twelve() + "|" + this.getRow1Thirteen() + "|" + this.getRow1Fourteen() + "|" + this.getRow1Fifteen() + "|" + this.getRow1Sixteen() + "|"
    					
    					+ this.getRow2One() + "|" + this.getRow2Two() + "|" + this.getRow2Three() + "|" + this.getRow2Four() + "|" + this.getRow2Five() + "|"
    					+ this.getRow2Six() + "|" + this.getRow2Seven() + "|" + this.getRow2Eight() + "|" + this.getRow2Nine() + "|" + this.getRow2Ten() + "|"
    					+ this.getRow2Eleven() + "|" + this.getRow2Twelve() + "|" + this.getRow2Thirteen() + "|" + this.getRow2Fourteen() + "|" + this.getRow2Fifteen() + "|" + this.getRow2Sixteen() + "|"
    					
    					+ this.getRow3One() + "|" + this.getRow3Two() + "|" + this.getRow3Three() + "|" + this.getRow3Four() + "|" + this.getRow3Five() + "|"
    					+ this.getRow3Six() + "|" + this.getRow3Seven() + "|" + this.getRow3Eight() + "|" + this.getRow3Nine() + "|" + this.getRow3Ten() + "|"
    					+ this.getRow3Eleven() + "|" + this.getRow3Twelve() + "|" + this.getRow3Thirteen() + "|" + this.getRow3Fourteen() + "|" + this.getRow3Fifteen() + "|" + this.getRow3Sixteen() + "|"
    					
    					+ this.getRow4One() + "|" + this.getRow4Two() + "|" + this.getRow4Three() + "|" + this.getRow4Four() + "|" + this.getRow4Five() + "|"
    					+ this.getRow4Six() + "|" + this.getRow4Seven() + "|" + this.getRow4Eight() + "|" + this.getRow4Nine() + "|" + this.getRow4Ten() + "|"
    					+ this.getRow4Eleven() + "|" + this.getRow4Twelve() + "|" + this.getRow4Thirteen() + "|" + this.getRow4Fourteen() + "|" + this.getRow4Fifteen() + "|" + this.getRow4Sixteen() + "|"
    					
    					+ this.getRow5One() + "|" + this.getRow5Two() + "|" + this.getRow5Three() + "|" + this.getRow5Four() + "|" + this.getRow5Five() + "|"
    					+ this.getRow5Six() + "|" + this.getRow5Seven() + "|" + this.getRow5Eight() + "|" + this.getRow5Nine() + "|" + this.getRow5Ten() + "|"
    					+ this.getRow5Eleven() + "|" + this.getRow5Twelve() + "|" + this.getRow5Thirteen() + "|" + this.getRow5Fourteen() + "|" + this.getRow5Fifteen() + "|" + this.getRow5Sixteen() + "|"
    					
    					+ this.getRow6One() + "|" + this.getRow6Two() + "|" + this.getRow6Three() + "|" + this.getRow6Four() + "|" + this.getRow6Five() + "|"
    					+ this.getRow6Six() + "|" + this.getRow6Seven() + "|" + this.getRow6Eight() + "|" + this.getRow6Nine() + "|" + this.getRow6Ten() + "|"
    					+ this.getRow6Eleven() + "|" + this.getRow6Twelve() + "|" + this.getRow6Thirteen() + "|" + this.getRow6Fourteen() + "|" + this.getRow6Fifteen() + "|" + this.getRow6Sixteen() + "|"
    					
    					+ this.getRow7One() + "|" + this.getRow7Two() + "|" + this.getRow7Three() + "|" + this.getRow7Four() + "|" + this.getRow7Five() + "|"
    					+ this.getRow7Six() + "|" + this.getRow7Seven() + "|" + this.getRow7Eight() + "|" + this.getRow7Nine() + "|" + this.getRow7Ten() + "|"
    					+ this.getRow7Eleven() + "|" + this.getRow7Twelve() + "|" + this.getRow7Thirteen() + "|" + this.getRow7Fourteen() + "|" + this.getRow7Fifteen() + "|" + this.getRow7Sixteen() + "|"
    					
    					+ this.getRow8One() + "|" + this.getRow8Two() + "|" + this.getRow8Three() + "|" + this.getRow8Four() + "|" + this.getRow8Five() + "|"
    					+ this.getRow8Six() + "|" + this.getRow8Seven() + "|" + this.getRow8Eight() + "|" + this.getRow8Nine() + "|" + this.getRow8Ten() + "|"
    					+ this.getRow8Eleven() + "|" + this.getRow8Twelve() + "|" + this.getRow8Thirteen() + "|" + this.getRow8Fourteen() + "|" + this.getRow8Fifteen() + "|" + this.getRow8Sixteen() + "|"
    					
    					+ this.getRow9One() + "|" + this.getRow9Two() + "|" + this.getRow9Three() + "|" + this.getRow9Four() + "|" + this.getRow9Five() + "|"
    					+ this.getRow9Six() + "|" + this.getRow9Seven() + "|" + this.getRow9Eight() + "|" + this.getRow9Nine() + "|" + this.getRow9Ten() + "|"
    					+ this.getRow9Eleven() + "|" + this.getRow9Twelve() + "|" + this.getRow9Thirteen() + "|" + this.getRow9Fourteen() + "|" + this.getRow9Fifteen() + "|" + this.getRow9Sixteen() + "|"
    					
    					+ this.getRow10One() + "|" + this.getRow10Two() + "|" + this.getRow10Three() + "|" + this.getRow10Four() + "|" + this.getRow10Five() + "|"
    					+ this.getRow10Six() + "|" + this.getRow10Seven() + "|" + this.getRow10Eight() + "|" + this.getRow10Nine() + "|" + this.getRow10Ten() + "|"
    					+ this.getRow10Eleven() + "|" + this.getRow10Twelve() + "|" + this.getRow10Thirteen() + "|" + this.getRow10Fourteen() + "|" + this.getRow10Fifteen() + "|" + this.getRow10Sixteen() + "|"
    					
    					
    					+ this.getRow11One() + "|" + this.getRow11Two() + "|" + this.getRow11Three() + "|" + this.getRow11Four() + "|" + this.getRow11Five() + "|"
    					+ this.getRow11Six() + "|" + this.getRow11Seven() + "|" + this.getRow11Eight() + "|" + this.getRow11Nine() + "|" + this.getRow11Ten() + "|"
    					+ this.getRow11Eleven() + "|" + this.getRow11Twelve() + "|" + this.getRow11Thirteen() + "|" + this.getRow11Fourteen() + "|" + this.getRow11Fifteen() + "|" + this.getRow11Sixteen() + "|"
    					
    					+ this.getRow12One() + "|" + this.getRow12Two() + "|" + this.getRow12Three() + "|" + this.getRow12Four() + "|" + this.getRow12Five() + "|"
    					+ this.getRow12Six() + "|" + this.getRow12Seven() + "|" + this.getRow12Eight() + "|" + this.getRow12Nine() + "|" + this.getRow12Ten() + "|"
    					+ this.getRow12Eleven() + "|" + this.getRow12Twelve() + "|" + this.getRow12Thirteen() + "|" + this.getRow12Fourteen() + "|" + this.getRow12Fifteen() + "|" + this.getRow12Sixteen() + "|"

    					+ this.getRow13One() + "|" + this.getRow13Two() + "|" + this.getRow13Three() + "|" + this.getRow13Four() + "|" + this.getRow13Five() + "|"
    					+ this.getRow13Six() + "|" + this.getRow13Seven() + "|" + this.getRow13Eight() + "|" + this.getRow13Nine() + "|" + this.getRow13Ten() + "|"
    					+ this.getRow13Eleven() + "|" + this.getRow13Twelve() + "|" + this.getRow13Thirteen() + "|" + this.getRow13Fourteen() + "|" + this.getRow13Fifteen() + "|" + this.getRow13Sixteen() + "|"

    					+ this.getRow14One() + "|" + this.getRow14Two() + "|" + this.getRow14Three() + "|" + this.getRow14Four() + "|" + this.getRow14Five() + "|"
    					+ this.getRow14Six() + "|" + this.getRow14Seven() + "|" + this.getRow14Eight() + "|" + this.getRow14Nine() + "|" + this.getRow14Ten() + "|"
    					+ this.getRow14Eleven() + "|" + this.getRow14Twelve() + "|" + this.getRow14Thirteen() + "|" + this.getRow14Fourteen() + "|" + this.getRow14Fifteen() + "|" + this.getRow14Sixteen() + "|"

    					+ this.getRow15One() + "|" + this.getRow15Two() + "|" + this.getRow15Three() + "|" + this.getRow15Four() + "|" + this.getRow15Five() + "|"
    					+ this.getRow15Six() + "|" + this.getRow15Seven() + "|" + this.getRow15Eight() + "|" + this.getRow15Nine() + "|" + this.getRow15Ten() + "|"
    					+ this.getRow15Eleven() + "|" + this.getRow15Twelve() + "|" + this.getRow15Thirteen() + "|" + this.getRow15Fourteen() + "|" + this.getRow15Fifteen() + "|" + this.getRow15Sixteen() + "|"
      
    					+ this.getRow16One() + "|" + this.getRow16Two() + "|" + this.getRow16Three() + "|" + this.getRow16Four() + "|" + this.getRow16Five() + "|"
    					+ this.getRow16Six() + "|" + this.getRow16Seven() + "|" + this.getRow16Eight() + "|" + this.getRow16Nine() + "|" + this.getRow16Ten() + "|"
    					+ this.getRow16Eleven() + "|" + this.getRow16Twelve() + "|" + this.getRow16Thirteen() + "|" + this.getRow16Fourteen() + "|" + this.getRow16Fifteen() + "|" + this.getRow16Sixteen() + "|"

    					+ this.getRow17One() + "|" + this.getRow17Two() + "|" + this.getRow17Three() + "|" + this.getRow17Four() + "|" + this.getRow17Five() + "|"
    					+ this.getRow17Six() + "|" + this.getRow17Seven() + "|" + this.getRow17Eight() + "|" + this.getRow17Nine() + "|" + this.getRow17Ten() + "|"
    					+ this.getRow17Eleven() + "|" + this.getRow17Twelve() + "|" + this.getRow17Thirteen() + "|" + this.getRow17Fourteen() + "|" + this.getRow17Fifteen() + "|" + this.getRow17Sixteen() + "|"
    					
    					+ this.getRow18One() + "|" + this.getRow18Two() + "|" + this.getRow18Three() + "|" + this.getRow18Four() + "|" + this.getRow18Five() + "|"
    					+ this.getRow18Six() + "|" + this.getRow18Seven() + "|" + this.getRow18Eight() + "|" + this.getRow18Nine() + "|" + this.getRow18Ten() + "|"
    					+ this.getRow18Eleven() + "|" + this.getRow18Twelve() + "|" + this.getRow18Thirteen() + "|" + this.getRow18Fourteen() + "|" + this.getRow18Fifteen() + "|" + this.getRow18Sixteen() + "|"
    					
    					+ this.getRow19One() + "|" + this.getRow19Two() + "|" + this.getRow19Three() + "|" + this.getRow19Four() + "|" + this.getRow19Five() + "|"
    					+ this.getRow19Six() + "|" + this.getRow19Seven() + "|" + this.getRow19Eight() + "|" + this.getRow19Nine() + "|" + this.getRow19Ten() + "|"
    					+ this.getRow19Eleven() + "|" + this.getRow19Twelve() + "|" + this.getRow19Thirteen() + "|" + this.getRow19Fourteen() + "|" + this.getRow19Fifteen() + "|" + this.getRow19Sixteen() + "|"
    					
    					+ this.getRow20One() + "|" + this.getRow20Two() + "|" + this.getRow20Three() + "|" + this.getRow20Four() + "|" + this.getRow20Five() + "|"
    					+ this.getRow20Six() + "|" + this.getRow20Seven() + "|" + this.getRow20Eight() + "|" + this.getRow20Nine() + "|" + this.getRow20Ten() + "|"
    					+ this.getRow20Eleven() + "|" + this.getRow20Twelve() + "|" + this.getRow20Thirteen() + "|" + this.getRow20Fourteen() + "|" + this.getRow20Fifteen() + "|" + this.getRow20Sixteen() + "|"
    					
    					+ this.getRow21One() + "|" + this.getRow21Two() + "|" + this.getRow21Three() + "|" + this.getRow21Four() + "|" + this.getRow21Five() + "|"
    					+ this.getRow21Six() + "|" + this.getRow21Seven() + "|" + this.getRow21Eight() + "|" + this.getRow21Nine() + "|" + this.getRow21Ten() + "|"
    					+ this.getRow21Eleven() + "|" + this.getRow21Twelve() + "|" + this.getRow21Thirteen() + "|" + this.getRow21Fourteen() + "|" + this.getRow21Fifteen() + "|" + this.getRow21Sixteen() + "|"

    					+ this.getRow22One() + "|" + this.getRow22Two() + "|" + this.getRow22Three() + "|" + this.getRow22Four() + "|" + this.getRow22Five() + "|"
    					+ this.getRow22Six() + "|" + this.getRow22Seven() + "|" + this.getRow22Eight() + "|" + this.getRow22Nine() + "|" + this.getRow22Ten() + "|"
    					+ this.getRow22Eleven() + "|" + this.getRow22Twelve() + "|" + this.getRow22Thirteen() + "|" + this.getRow22Fourteen() + "|" + this.getRow22Fifteen() + "|" + this.getRow22Sixteen() + "|"

    					+ this.getRow23One() + "|" + this.getRow23Two() + "|" + this.getRow23Three() + "|" + this.getRow23Four() + "|" + this.getRow23Five() + "|"
    					+ this.getRow23Six() + "|" + this.getRow23Seven() + "|" + this.getRow23Eight() + "|" + this.getRow23Nine() + "|" + this.getRow23Ten() + "|"
    					+ this.getRow23Eleven() + "|" + this.getRow23Twelve() + "|" + this.getRow23Thirteen() + "|" + this.getRow23Fourteen() + "|" + this.getRow23Fifteen() + "|" + this.getRow23Sixteen() + "|"
        				
        			    + this.getPrincipal() + "|"+this.getStatistics()+"|"
        			    + this.getFitOut() + "|" + this.getSubmitDate() + "|" + this.getId();  
    		}
    	}    
	}
	
	
}