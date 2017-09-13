/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.balancesheep.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 资产负债表Entity
 * @author lx
 * @version 2016-05-17
 */
public class TBalanceSheep extends DataEntity<TBalanceSheep> {
	
	private static final long serialVersionUID = 1L;
	private String reportName;		// 报表名称
	private String rowOne;		// row_one
	private String rowTwo;		// row_two
	private String rowThree;		// row_three
	private String rowFour;		// row_four
	private String row1One;		// row1_one
	private String row1Two;		// row1_two
	private String row1Three;		// row1_three
	private String row1Four;		// row1_four
	private String row2One;		// row2_one
	private String row2Two;		// row2_two
	private String row2Three;		// row2_three
	private String row2Four;		// row2_four
	private String row3One;		// row3_one
	private String row3Two;		// row3_two
	private String row3Three;		// row3_three
	private String row3Four;		// row3_four
	private String row4One;		// row4_one
	private String row4Two;		// row4_two
	private String row4Three;		// row4_three
	private String row4Four;		// row4_four
	private String row5One;		// row5_one
	private String row5Two;		// row5_two
	private String row5Three;		// row5_three
	private String row5Four;		// row5_four
	private String row6One;		// row6_one
	private String row6Two;		// row6_two
	private String row6Three;		// row6_three
	private String row6Four;		// row6_four
	private String row7One;		// row7_one
	private String row7Two;		// row7_two
	private String row7Three;		// row7_three
	private String row7Four;		// row7_four
	private String row8One;		// row8_one
	private String row8Two;		// row8_two
	private String row8Three;		// row8_three
	private String row8Four;		// row8_four
	private String row9One;		// row9_one
	private String row9Two;		// row9_two
	private String row9Three;		// row9_three
	private String row9Four;		// row9_four
	private String row10One;		// row10_one
	private String row10Two;		// row10_two
	private String row10Three;		// row10_three
	private String row10Four;		// row10_four
	private String row11One;		// row11_one
	private String row11Two;		// row11_two
	private String row11Three;		// row11_three
	private String row11Four;		// row11_four
	private String row12One;		// row12_one
	private String row12Two;		// row12_two
	private String row12Three;		// row12_three
	private String row12Four;		// row12_four
	private String row13One;		// row13_one
	private String row13Two;		// row13_two
	private String row13Three;		// row13_three
	private String row13Four;		// row13_four
	private String row14Three;		// row14_three
	private String row14Four;		// row14_four
	
	
	private String row15One;		// row15_one
	private String row15Two;		// row15_two
	private String row15Three;		// row15_three
	private String row15Four;		// row15_four
	private String row16One;		// row16_one
	private String row16Two;		// row16_two
	private String row16Three;		// row16_three
	private String row16Four;		// row16_four
	private String row17One;		// row17_one
	private String row17Two;		// row17_two
	private String row17Three;		// row17_three
	private String row17Four;		// row17_four
	private String row18One;		// row18_one
	private String row18Two;		// row18_two
	private String row18Three;		// row18_three
	private String row18Four;		// row18_four
	private String row19One;		// row19_one
	private String row19Two;		// row19_two
	private String row19Three;		// row19_three
	private String row19Four;		// row19_four
	private String row20One;		// row20_one
	private String row20Two;		// row20_two
	private String row20Three;		// row20_three
	private String row20Four;		// row20_four
	private String row21One;		// row21_one
	private String row21Two;		// row21_two
	private String row21Three;		// row21_three
	private String row21Four;		// row21_four
	private String row22One;		// row22_one
	private String row22Two;		// row22_two
	private String row22Three;		// row22_three
	private String row22Four;		// row22_four
	private String row23One;		// row23_one
	private String row23Two;		// row23_two
	private String row23Three;		// row23_three
	private String row23Four;		// row23_four
	private String row24One;		// row24_one
	private String row24Two;		// row24_two
	private String row24Three;		// row24_three
	private String row24Four;		// row24_four
	private String row25One;		// row24_one
	private String row25Two;		// row24_two
	private String row25Three;		// row24_three
	private String row25Four;		// row24_four
	private String row26One;		// row26_one
	private String row26Two;		// row26_two
	private String row26Three;		// row26_three
	private String row26Four;		// row26_four
	private String row27One;		// row27_one
	private String row27Two;		// row27_two
	private String row27Three;		// row27_three
	private String row27Four;		// row27_four
	private String row28One;		// row28_one
	private String row28Two;		// row28_two
	private String row28Three;		// row28_three
	private String row28Four;		// row28_four
	private String row29One;		// row29_one
	private String row29Two;		// row29_two
	private String row30Three;		// row31_one
	private String row30Four;		// row31_two
	private String row31One;		// row31_one
	private String row31Two;		// row31_two
	private String row31Three;		// row31_one
	private String row31Four;		// row31_two
	private String row32One;		// row32_one
	private String row32Two;		// row32_two
	private String row32Three;		// row32_three
	private String row32Four;		// row32_four
	private String row33One;		// row33_one
	private String row33Two;		// row33_two
	private String row33Three;		// row33_three
	private String row33Four;		// row33_four
	private String row34One;		// row34_one
	private String row34Two;		// row34_two
	private String row34Three;		// row34_three
	private String row34Four;		// row34_four
	private String row35One;		// row35_one
	private String row35Two;		// row35_two
	private String row35Three;		// row35_three
	private String row35Four;		// row35_four
	private String row36One;		// row36_one
	private String row36Two;		// row36_two
	private String row36Three;		// row36_three
	private String row36Four;		// row36_four
	
	
	private String createTime;//程序暂时使用，不持久化到数据库
	public TBalanceSheep() {
		super();
	}

	public TBalanceSheep(String id){
		super(id);
	}

	@Length(min=1, max=64, message="报表名称长度必须介于 1 和 64 之间")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	@Length(min=0, max=64, message="row_one长度必须介于 0 和 64 之间")
	public String getRowOne() {
		return rowOne;
	}

	public void setRowOne(String rowOne) {
		this.rowOne = rowOne;
	}
	
	@Length(min=0, max=64, message="row_two长度必须介于 0 和 64 之间")
	public String getRowTwo() {
		return rowTwo;
	}

	public void setRowTwo(String rowTwo) {
		this.rowTwo = rowTwo;
	}
	
	@Length(min=0, max=64, message="row_three长度必须介于 0 和 64 之间")
	public String getRowThree() {
		return rowThree;
	}

	public void setRowThree(String rowThree) {
		this.rowThree = rowThree;
	}
	
	@Length(min=0, max=64, message="row_four长度必须介于 0 和 64 之间")
	public String getRowFour() {
		return rowFour;
	}

	public void setRowFour(String rowFour) {
		this.rowFour = rowFour;
	}
	
	@Length(min=1, max=64, message="租户ID长度必须介于 1 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	@Length(min=0, max=64, message="row1_one长度必须介于 0 和 64 之间")
	public String getRow1One() {
		return row1One;
	}

	public void setRow1One(String row1One) {
		this.row1One = row1One;
	}
	
	@Length(min=0, max=64, message="row1_two长度必须介于 0 和 64 之间")
	public String getRow1Two() {
		return row1Two;
	}

	public void setRow1Two(String row1Two) {
		this.row1Two = row1Two;
	}
	
	@Length(min=0, max=64, message="row1_three长度必须介于 0 和 64 之间")
	public String getRow1Three() {
		return row1Three;
	}

	public void setRow1Three(String row1Three) {
		this.row1Three = row1Three;
	}
	
	@Length(min=0, max=64, message="row1_four长度必须介于 0 和 64 之间")
	public String getRow1Four() {
		return row1Four;
	}

	public void setRow1Four(String row1Four) {
		this.row1Four = row1Four;
	}
	
	@Length(min=0, max=64, message="row2_one长度必须介于 0 和 64 之间")
	public String getRow2One() {
		return row2One;
	}

	public void setRow2One(String row2One) {
		this.row2One = row2One;
	}
	
	@Length(min=0, max=64, message="row2_two长度必须介于 0 和 64 之间")
	public String getRow2Two() {
		return row2Two;
	}

	public void setRow2Two(String row2Two) {
		this.row2Two = row2Two;
	}
	
	@Length(min=0, max=64, message="row2_three长度必须介于 0 和 64 之间")
	public String getRow2Three() {
		return row2Three;
	}

	public void setRow2Three(String row2Three) {
		this.row2Three = row2Three;
	}
	
	@Length(min=0, max=64, message="row2_four长度必须介于 0 和 64 之间")
	public String getRow2Four() {
		return row2Four;
	}

	public void setRow2Four(String row2Four) {
		this.row2Four = row2Four;
	}
	
	@Length(min=0, max=64, message="row3_one长度必须介于 0 和 64 之间")
	public String getRow3One() {
		return row3One;
	}

	public void setRow3One(String row3One) {
		this.row3One = row3One;
	}
	
	@Length(min=0, max=64, message="row3_two长度必须介于 0 和 64 之间")
	public String getRow3Two() {
		return row3Two;
	}

	public void setRow3Two(String row3Two) {
		this.row3Two = row3Two;
	}
	
	@Length(min=0, max=64, message="row3_three长度必须介于 0 和 64 之间")
	public String getRow3Three() {
		return row3Three;
	}

	public void setRow3Three(String row3Three) {
		this.row3Three = row3Three;
	}
	
	@Length(min=0, max=64, message="row3_four长度必须介于 0 和 64 之间")
	public String getRow3Four() {
		return row3Four;
	}

	public void setRow3Four(String row3Four) {
		this.row3Four = row3Four;
	}
	
	@Length(min=0, max=64, message="row4_one长度必须介于 0 和 64 之间")
	public String getRow4One() {
		return row4One;
	}

	public void setRow4One(String row4One) {
		this.row4One = row4One;
	}
	
	@Length(min=0, max=64, message="row4_two长度必须介于 0 和 64 之间")
	public String getRow4Two() {
		return row4Two;
	}

	public void setRow4Two(String row4Two) {
		this.row4Two = row4Two;
	}
	
	@Length(min=0, max=64, message="row4_three长度必须介于 0 和 64 之间")
	public String getRow4Three() {
		return row4Three;
	}

	public void setRow4Three(String row4Three) {
		this.row4Three = row4Three;
	}
	
	@Length(min=0, max=64, message="row4_four长度必须介于 0 和 64 之间")
	public String getRow4Four() {
		return row4Four;
	}

	public void setRow4Four(String row4Four) {
		this.row4Four = row4Four;
	}
	
	@Length(min=0, max=64, message="row5_one长度必须介于 0 和 64 之间")
	public String getRow5One() {
		return row5One;
	}

	public void setRow5One(String row5One) {
		this.row5One = row5One;
	}
	
	@Length(min=0, max=64, message="row5_two长度必须介于 0 和 64 之间")
	public String getRow5Two() {
		return row5Two;
	}

	public void setRow5Two(String row5Two) {
		this.row5Two = row5Two;
	}
	
	@Length(min=0, max=64, message="row5_three长度必须介于 0 和 64 之间")
	public String getRow5Three() {
		return row5Three;
	}

	public void setRow5Three(String row5Three) {
		this.row5Three = row5Three;
	}
	
	@Length(min=0, max=64, message="row5_four长度必须介于 0 和 64 之间")
	public String getRow5Four() {
		return row5Four;
	}

	public void setRow5Four(String row5Four) {
		this.row5Four = row5Four;
	}
	
	@Length(min=0, max=64, message="row6_one长度必须介于 0 和 64 之间")
	public String getRow6One() {
		return row6One;
	}

	public void setRow6One(String row6One) {
		this.row6One = row6One;
	}
	
	@Length(min=0, max=64, message="row6_two长度必须介于 0 和 64 之间")
	public String getRow6Two() {
		return row6Two;
	}

	public void setRow6Two(String row6Two) {
		this.row6Two = row6Two;
	}
	
	@Length(min=0, max=64, message="row6_three长度必须介于 0 和 64 之间")
	public String getRow6Three() {
		return row6Three;
	}

	public void setRow6Three(String row6Three) {
		this.row6Three = row6Three;
	}
	
	@Length(min=0, max=64, message="row6_four长度必须介于 0 和 64 之间")
	public String getRow6Four() {
		return row6Four;
	}

	public void setRow6Four(String row6Four) {
		this.row6Four = row6Four;
	}
	
	@Length(min=0, max=64, message="row7_one长度必须介于 0 和 64 之间")
	public String getRow7One() {
		return row7One;
	}

	public void setRow7One(String row7One) {
		this.row7One = row7One;
	}
	
	@Length(min=0, max=64, message="row7_two长度必须介于 0 和 64 之间")
	public String getRow7Two() {
		return row7Two;
	}

	public void setRow7Two(String row7Two) {
		this.row7Two = row7Two;
	}
	
	@Length(min=0, max=64, message="row7_three长度必须介于 0 和 64 之间")
	public String getRow7Three() {
		return row7Three;
	}

	public void setRow7Three(String row7Three) {
		this.row7Three = row7Three;
	}
	
	@Length(min=0, max=64, message="row7_four长度必须介于 0 和 64 之间")
	public String getRow7Four() {
		return row7Four;
	}

	public void setRow7Four(String row7Four) {
		this.row7Four = row7Four;
	}
	
	@Length(min=0, max=64, message="row8_one长度必须介于 0 和 64 之间")
	public String getRow8One() {
		return row8One;
	}

	public void setRow8One(String row8One) {
		this.row8One = row8One;
	}
	
	@Length(min=0, max=64, message="row8_two长度必须介于 0 和 64 之间")
	public String getRow8Two() {
		return row8Two;
	}

	public void setRow8Two(String row8Two) {
		this.row8Two = row8Two;
	}
	
	@Length(min=0, max=64, message="row8_three长度必须介于 0 和 64 之间")
	public String getRow8Three() {
		return row8Three;
	}

	public void setRow8Three(String row8Three) {
		this.row8Three = row8Three;
	}
	
	@Length(min=0, max=64, message="row8_four长度必须介于 0 和 64 之间")
	public String getRow8Four() {
		return row8Four;
	}

	public void setRow8Four(String row8Four) {
		this.row8Four = row8Four;
	}
	
	@Length(min=0, max=64, message="row9_one长度必须介于 0 和 64 之间")
	public String getRow9One() {
		return row9One;
	}

	public void setRow9One(String row9One) {
		this.row9One = row9One;
	}
	
	@Length(min=0, max=64, message="row9_two长度必须介于 0 和 64 之间")
	public String getRow9Two() {
		return row9Two;
	}

	public void setRow9Two(String row9Two) {
		this.row9Two = row9Two;
	}
	
	@Length(min=0, max=64, message="row9_three长度必须介于 0 和 64 之间")
	public String getRow9Three() {
		return row9Three;
	}

	public void setRow9Three(String row9Three) {
		this.row9Three = row9Three;
	}
	
	@Length(min=0, max=64, message="row9_four长度必须介于 0 和 64 之间")
	public String getRow9Four() {
		return row9Four;
	}

	public void setRow9Four(String row9Four) {
		this.row9Four = row9Four;
	}
	
	@Length(min=0, max=64, message="row10_one长度必须介于 0 和 64 之间")
	public String getRow10One() {
		return row10One;
	}

	public void setRow10One(String row10One) {
		this.row10One = row10One;
	}
	
	@Length(min=0, max=64, message="row10_two长度必须介于 0 和 64 之间")
	public String getRow10Two() {
		return row10Two;
	}

	public void setRow10Two(String row10Two) {
		this.row10Two = row10Two;
	}
	
	@Length(min=0, max=64, message="row10_three长度必须介于 0 和 64 之间")
	public String getRow10Three() {
		return row10Three;
	}

	public void setRow10Three(String row10Three) {
		this.row10Three = row10Three;
	}
	
	@Length(min=0, max=64, message="row10_four长度必须介于 0 和 64 之间")
	public String getRow10Four() {
		return row10Four;
	}

	public void setRow10Four(String row10Four) {
		this.row10Four = row10Four;
	}
	
	@Length(min=0, max=64, message="row11_one长度必须介于 0 和 64 之间")
	public String getRow11One() {
		return row11One;
	}

	public void setRow11One(String row11One) {
		this.row11One = row11One;
	}
	
	@Length(min=0, max=64, message="row11_two长度必须介于 0 和 64 之间")
	public String getRow11Two() {
		return row11Two;
	}

	public void setRow11Two(String row11Two) {
		this.row11Two = row11Two;
	}
	
	@Length(min=0, max=64, message="row11_three长度必须介于 0 和 64 之间")
	public String getRow11Three() {
		return row11Three;
	}

	public void setRow11Three(String row11Three) {
		this.row11Three = row11Three;
	}
	
	@Length(min=0, max=64, message="row11_four长度必须介于 0 和 64 之间")
	public String getRow11Four() {
		return row11Four;
	}

	public void setRow11Four(String row11Four) {
		this.row11Four = row11Four;
	}
	
	@Length(min=0, max=64, message="row12_one长度必须介于 0 和 64 之间")
	public String getRow12One() {
		return row12One;
	}

	public void setRow12One(String row12One) {
		this.row12One = row12One;
	}
	
	@Length(min=0, max=64, message="row12_two长度必须介于 0 和 64 之间")
	public String getRow12Two() {
		return row12Two;
	}

	public void setRow12Two(String row12Two) {
		this.row12Two = row12Two;
	}
	
	@Length(min=0, max=64, message="row12_three长度必须介于 0 和 64 之间")
	public String getRow12Three() {
		return row12Three;
	}

	public void setRow12Three(String row12Three) {
		this.row12Three = row12Three;
	}
	
	@Length(min=0, max=64, message="row12_four长度必须介于 0 和 64 之间")
	public String getRow12Four() {
		return row12Four;
	}

	public void setRow12Four(String row12Four) {
		this.row12Four = row12Four;
	}
	
	@Length(min=0, max=64, message="row13_one长度必须介于 0 和 64 之间")
	public String getRow13One() {
		return row13One;
	}

	public void setRow13One(String row13One) {
		this.row13One = row13One;
	}
	
	@Length(min=0, max=64, message="row13_two长度必须介于 0 和 64 之间")
	public String getRow13Two() {
		return row13Two;
	}

	public void setRow13Two(String row13Two) {
		this.row13Two = row13Two;
	}
	
	@Length(min=0, max=64, message="row13_three长度必须介于 0 和 64 之间")
	public String getRow13Three() {
		return row13Three;
	}

	public void setRow13Three(String row13Three) {
		this.row13Three = row13Three;
	}
	
	@Length(min=0, max=64, message="row13_four长度必须介于 0 和 64 之间")
	public String getRow13Four() {
		return row13Four;
	}

	public void setRow13Four(String row13Four) {
		this.row13Four = row13Four;
	}
	
	@Length(min=0, max=64, message="row14_three长度必须介于 0 和 64 之间")
	public String getRow14Three() {
		return row14Three;
	}

	public void setRow14Three(String row14Three) {
		this.row14Three = row14Three;
	}
	
	@Length(min=0, max=64, message="row14_four长度必须介于 0 和 64 之间")
	public String getRow14Four() {
		return row14Four;
	}

	public void setRow14Four(String row14Four) {
		this.row14Four = row14Four;
	}
	
	@Length(min=0, max=64, message="row15_one长度必须介于 0 和 64 之间")
	public String getRow15One() {
		return row15One;
	}

	public void setRow15One(String row15One) {
		this.row15One = row15One;
	}
	
	@Length(min=0, max=64, message="row15_two长度必须介于 0 和 64 之间")
	public String getRow15Two() {
		return row15Two;
	}

	public void setRow15Two(String row15Two) {
		this.row15Two = row15Two;
	}
	
	@Length(min=0, max=64, message="row15_three长度必须介于 0 和 64 之间")
	public String getRow15Three() {
		return row15Three;
	}

	public void setRow15Three(String row15Three) {
		this.row15Three = row15Three;
	}
	
	@Length(min=0, max=64, message="row15_four长度必须介于 0 和 64 之间")
	public String getRow15Four() {
		return row15Four;
	}

	public void setRow15Four(String row15Four) {
		this.row15Four = row15Four;
	}
	
	@Length(min=0, max=64, message="row16_one长度必须介于 0 和 64 之间")
	public String getRow16One() {
		return row16One;
	}

	public void setRow16One(String row16One) {
		this.row16One = row16One;
	}
	
	@Length(min=0, max=64, message="row16_two长度必须介于 0 和 64 之间")
	public String getRow16Two() {
		return row16Two;
	}

	public void setRow16Two(String row16Two) {
		this.row16Two = row16Two;
	}
	
	@Length(min=0, max=64, message="row16_three长度必须介于 0 和 64 之间")
	public String getRow16Three() {
		return row16Three;
	}

	public void setRow16Three(String row16Three) {
		this.row16Three = row16Three;
	}
	
	@Length(min=0, max=64, message="row16_four长度必须介于 0 和 64 之间")
	public String getRow16Four() {
		return row16Four;
	}

	public void setRow16Four(String row16Four) {
		this.row16Four = row16Four;
	}
	
	@Length(min=0, max=64, message="row17_one长度必须介于 0 和 64 之间")
	public String getRow17One() {
		return row17One;
	}

	public void setRow17One(String row17One) {
		this.row17One = row17One;
	}
	
	@Length(min=0, max=64, message="row17_two长度必须介于 0 和 64 之间")
	public String getRow17Two() {
		return row17Two;
	}

	public void setRow17Two(String row17Two) {
		this.row17Two = row17Two;
	}
	
	@Length(min=0, max=64, message="row17_three长度必须介于 0 和 64 之间")
	public String getRow17Three() {
		return row17Three;
	}

	public void setRow17Three(String row17Three) {
		this.row17Three = row17Three;
	}
	
	@Length(min=0, max=64, message="row17_four长度必须介于 0 和 64 之间")
	public String getRow17Four() {
		return row17Four;
	}

	public void setRow17Four(String row17Four) {
		this.row17Four = row17Four;
	}
	
	@Length(min=0, max=64, message="row18_one长度必须介于 0 和 64 之间")
	public String getRow18One() {
		return row18One;
	}

	public void setRow18One(String row18One) {
		this.row18One = row18One;
	}
	
	@Length(min=0, max=64, message="row18_two长度必须介于 0 和 64 之间")
	public String getRow18Two() {
		return row18Two;
	}

	public void setRow18Two(String row18Two) {
		this.row18Two = row18Two;
	}
	
	@Length(min=0, max=64, message="row18_three长度必须介于 0 和 64 之间")
	public String getRow18Three() {
		return row18Three;
	}

	public void setRow18Three(String row18Three) {
		this.row18Three = row18Three;
	}
	
	@Length(min=0, max=64, message="row18_four长度必须介于 0 和 64 之间")
	public String getRow18Four() {
		return row18Four;
	}

	public void setRow18Four(String row18Four) {
		this.row18Four = row18Four;
	}
	
	@Length(min=0, max=64, message="row19_one长度必须介于 0 和 64 之间")
	public String getRow19One() {
		return row19One;
	}

	public void setRow19One(String row19One) {
		this.row19One = row19One;
	}
	
	@Length(min=0, max=64, message="row19_two长度必须介于 0 和 64 之间")
	public String getRow19Two() {
		return row19Two;
	}

	public void setRow19Two(String row19Two) {
		this.row19Two = row19Two;
	}
	
	@Length(min=0, max=64, message="row19_three长度必须介于 0 和 64 之间")
	public String getRow19Three() {
		return row19Three;
	}

	public void setRow19Three(String row19Three) {
		this.row19Three = row19Three;
	}
	
	@Length(min=0, max=64, message="row19_four长度必须介于 0 和 64 之间")
	public String getRow19Four() {
		return row19Four;
	}

	public void setRow19Four(String row19Four) {
		this.row19Four = row19Four;
	}
	
	@Length(min=0, max=64, message="row20_one长度必须介于 0 和 64 之间")
	public String getRow20One() {
		return row20One;
	}

	public void setRow20One(String row20One) {
		this.row20One = row20One;
	}
	
	@Length(min=0, max=64, message="row20_two长度必须介于 0 和 64 之间")
	public String getRow20Two() {
		return row20Two;
	}

	public void setRow20Two(String row20Two) {
		this.row20Two = row20Two;
	}
	
	@Length(min=0, max=64, message="row20_three长度必须介于 0 和 64 之间")
	public String getRow20Three() {
		return row20Three;
	}

	public void setRow20Three(String row20Three) {
		this.row20Three = row20Three;
	}
	
	@Length(min=0, max=64, message="row20_four长度必须介于 0 和 64 之间")
	public String getRow20Four() {
		return row20Four;
	}

	public void setRow20Four(String row20Four) {
		this.row20Four = row20Four;
	}
	
	@Length(min=0, max=64, message="row21_one长度必须介于 0 和 64 之间")
	public String getRow21One() {
		return row21One;
	}

	public void setRow21One(String row21One) {
		this.row21One = row21One;
	}
	
	@Length(min=0, max=64, message="row21_two长度必须介于 0 和 64 之间")
	public String getRow21Two() {
		return row21Two;
	}

	public void setRow21Two(String row21Two) {
		this.row21Two = row21Two;
	}
	
	@Length(min=0, max=64, message="row21_three长度必须介于 0 和 64 之间")
	public String getRow21Three() {
		return row21Three;
	}

	public void setRow21Three(String row21Three) {
		this.row21Three = row21Three;
	}
	
	@Length(min=0, max=64, message="row21_four长度必须介于 0 和 64 之间")
	public String getRow21Four() {
		return row21Four;
	}

	public void setRow21Four(String row21Four) {
		this.row21Four = row21Four;
	}
	
	@Length(min=0, max=64, message="row22_one长度必须介于 0 和 64 之间")
	public String getRow22One() {
		return row22One;
	}

	public void setRow22One(String row22One) {
		this.row22One = row22One;
	}
	
	@Length(min=0, max=64, message="row22_two长度必须介于 0 和 64 之间")
	public String getRow22Two() {
		return row22Two;
	}

	public void setRow22Two(String row22Two) {
		this.row22Two = row22Two;
	}
	
	@Length(min=0, max=64, message="row22_three长度必须介于 0 和 64 之间")
	public String getRow22Three() {
		return row22Three;
	}

	public void setRow22Three(String row22Three) {
		this.row22Three = row22Three;
	}
	
	@Length(min=0, max=64, message="row22_four长度必须介于 0 和 64 之间")
	public String getRow22Four() {
		return row22Four;
	}

	public void setRow22Four(String row22Four) {
		this.row22Four = row22Four;
	}
	
	@Length(min=0, max=64, message="row23_one长度必须介于 0 和 64 之间")
	public String getRow23One() {
		return row23One;
	}

	public void setRow23One(String row23One) {
		this.row23One = row23One;
	}
	
	@Length(min=0, max=64, message="row23_two长度必须介于 0 和 64 之间")
	public String getRow23Two() {
		return row23Two;
	}

	public void setRow23Two(String row23Two) {
		this.row23Two = row23Two;
	}
	
	@Length(min=0, max=64, message="row23_three长度必须介于 0 和 64 之间")
	public String getRow23Three() {
		return row23Three;
	}

	public void setRow23Three(String row23Three) {
		this.row23Three = row23Three;
	}
	
	@Length(min=0, max=64, message="row23_four长度必须介于 0 和 64 之间")
	public String getRow23Four() {
		return row23Four;
	}

	public void setRow23Four(String row23Four) {
		this.row23Four = row23Four;
	}
	
	@Length(min=0, max=64, message="row24_one长度必须介于 0 和 64 之间")
	public String getRow24One() {
		return row24One;
	}

	public void setRow24One(String row24One) {
		this.row24One = row24One;
	}
	
	@Length(min=0, max=64, message="row24_two长度必须介于 0 和 64 之间")
	public String getRow24Two() {
		return row24Two;
	}

	public void setRow24Two(String row24Two) {
		this.row24Two = row24Two;
	}
	
	@Length(min=0, max=64, message="row24_three长度必须介于 0 和 64 之间")
	public String getRow24Three() {
		return row24Three;
	}

	public void setRow24Three(String row24Three) {
		this.row24Three = row24Three;
	}
	
	@Length(min=0, max=64, message="row24_four长度必须介于 0 和 64 之间")
	public String getRow24Four() {
		return row24Four;
	}

	public void setRow24Four(String row24Four) {
		this.row24Four = row24Four;
	}
	
	@Length(min=0, max=64, message="row26_one长度必须介于 0 和 64 之间")
	public String getRow26One() {
		return row26One;
	}

	public void setRow26One(String row26One) {
		this.row26One = row26One;
	}
	
	@Length(min=0, max=64, message="row26_two长度必须介于 0 和 64 之间")
	public String getRow26Two() {
		return row26Two;
	}

	public void setRow26Two(String row26Two) {
		this.row26Two = row26Two;
	}
	
	@Length(min=0, max=64, message="row26_three长度必须介于 0 和 64 之间")
	public String getRow26Three() {
		return row26Three;
	}

	public void setRow26Three(String row26Three) {
		this.row26Three = row26Three;
	}
	
	@Length(min=0, max=64, message="row26_four长度必须介于 0 和 64 之间")
	public String getRow26Four() {
		return row26Four;
	}

	public void setRow26Four(String row26Four) {
		this.row26Four = row26Four;
	}
	
	@Length(min=0, max=64, message="row27_one长度必须介于 0 和 64 之间")
	public String getRow27One() {
		return row27One;
	}

	public void setRow27One(String row27One) {
		this.row27One = row27One;
	}
	
	@Length(min=0, max=64, message="row27_two长度必须介于 0 和 64 之间")
	public String getRow27Two() {
		return row27Two;
	}

	public void setRow27Two(String row27Two) {
		this.row27Two = row27Two;
	}
	
	@Length(min=0, max=64, message="row27_three长度必须介于 0 和 64 之间")
	public String getRow27Three() {
		return row27Three;
	}

	public void setRow27Three(String row27Three) {
		this.row27Three = row27Three;
	}
	
	@Length(min=0, max=64, message="row27_four长度必须介于 0 和 64 之间")
	public String getRow27Four() {
		return row27Four;
	}

	public void setRow27Four(String row27Four) {
		this.row27Four = row27Four;
	}
	
	@Length(min=0, max=64, message="row28_one长度必须介于 0 和 64 之间")
	public String getRow28One() {
		return row28One;
	}

	public void setRow28One(String row28One) {
		this.row28One = row28One;
	}
	
	@Length(min=0, max=64, message="row28_two长度必须介于 0 和 64 之间")
	public String getRow28Two() {
		return row28Two;
	}

	public void setRow28Two(String row28Two) {
		this.row28Two = row28Two;
	}
	
	@Length(min=0, max=64, message="row28_three长度必须介于 0 和 64 之间")
	public String getRow28Three() {
		return row28Three;
	}

	public void setRow28Three(String row28Three) {
		this.row28Three = row28Three;
	}
	
	@Length(min=0, max=64, message="row28_four长度必须介于 0 和 64 之间")
	public String getRow28Four() {
		return row28Four;
	}

	public void setRow28Four(String row28Four) {
		this.row28Four = row28Four;
	}
	
	@Length(min=0, max=64, message="row29_one长度必须介于 0 和 64 之间")
	public String getRow29One() {
		return row29One;
	}

	public void setRow29One(String row29One) {
		this.row29One = row29One;
	}
	
	@Length(min=0, max=64, message="row29_two长度必须介于 0 和 64 之间")
	public String getRow29Two() {
		return row29Two;
	}

	public void setRow29Two(String row29Two) {
		this.row29Two = row29Two;
	}
	
	@Length(min=0, max=64, message="row30_three长度必须介于 0 和 64 之间")
	public String getRow30Three() {
		return row30Three;
	}

	public void setRow30Three(String row30Three) {
		this.row30Three = row30Three;
	}
	
	@Length(min=0, max=64, message="row30_four长度必须介于 0 和 64 之间")
	public String getRow30Four() {
		return row30Four;
	}

	public void setRow30Four(String row30Four) {
		this.row30Four = row30Four;
	}
	
	@Length(min=0, max=64, message="row31_one长度必须介于 0 和 64 之间")
	public String getRow31One() {
		return row31One;
	}

	public void setRow31One(String row31One) {
		this.row31One = row31One;
	}
	
	@Length(min=0, max=64, message="row31_two长度必须介于 0 和 64 之间")
	public String getRow31Two() {
		return row31Two;
	}

	public void setRow31Two(String row31Two) {
		this.row31Two = row31Two;
	}
	
	@Length(min=0, max=64, message="row31_three长度必须介于 0 和 64 之间")
	public String getRow31Three() {
		return row31Three;
	}

	public void setRow31Three(String row31Three) {
		this.row31Three = row31Three;
	}
	
	@Length(min=0, max=64, message="row31_four长度必须介于 0 和 64 之间")
	public String getRow31Four() {
		return row31Four;
	}

	public void setRow31Four(String row31Four) {
		this.row31Four = row31Four;
	}
	
	@Length(min=0, max=64, message="row32_one长度必须介于 0 和 64 之间")
	public String getRow32One() {
		return row32One;
	}

	public void setRow32One(String row32One) {
		this.row32One = row32One;
	}
	
	@Length(min=0, max=64, message="row32_two长度必须介于 0 和 64 之间")
	public String getRow32Two() {
		return row32Two;
	}

	public void setRow32Two(String row32Two) {
		this.row32Two = row32Two;
	}
	
	@Length(min=0, max=64, message="row32_three长度必须介于 0 和 64 之间")
	public String getRow32Three() {
		return row32Three;
	}

	public void setRow32Three(String row32Three) {
		this.row32Three = row32Three;
	}
	
	@Length(min=0, max=64, message="row32_four长度必须介于 0 和 64 之间")
	public String getRow32Four() {
		return row32Four;
	}

	public void setRow32Four(String row32Four) {
		this.row32Four = row32Four;
	}
	
	@Length(min=0, max=64, message="row33_one长度必须介于 0 和 64 之间")
	public String getRow33One() {
		return row33One;
	}

	public void setRow33One(String row33One) {
		this.row33One = row33One;
	}
	
	@Length(min=0, max=64, message="row33_two长度必须介于 0 和 64 之间")
	public String getRow33Two() {
		return row33Two;
	}

	public void setRow33Two(String row33Two) {
		this.row33Two = row33Two;
	}
	
	@Length(min=0, max=64, message="row33_three长度必须介于 0 和 64 之间")
	public String getRow33Three() {
		return row33Three;
	}

	public void setRow33Three(String row33Three) {
		this.row33Three = row33Three;
	}
	
	@Length(min=0, max=64, message="row33_four长度必须介于 0 和 64 之间")
	public String getRow33Four() {
		return row33Four;
	}

	public void setRow33Four(String row33Four) {
		this.row33Four = row33Four;
	}
	
	@Length(min=0, max=64, message="row34_one长度必须介于 0 和 64 之间")
	public String getRow34One() {
		return row34One;
	}

	public void setRow34One(String row34One) {
		this.row34One = row34One;
	}
	
	@Length(min=0, max=64, message="row34_two长度必须介于 0 和 64 之间")
	public String getRow34Two() {
		return row34Two;
	}

	public void setRow34Two(String row34Two) {
		this.row34Two = row34Two;
	}
	
	@Length(min=0, max=64, message="row34_three长度必须介于 0 和 64 之间")
	public String getRow34Three() {
		return row34Three;
	}

	public void setRow34Three(String row34Three) {
		this.row34Three = row34Three;
	}
	
	@Length(min=0, max=64, message="row34_four长度必须介于 0 和 64 之间")
	public String getRow34Four() {
		return row34Four;
	}

	public void setRow34Four(String row34Four) {
		this.row34Four = row34Four;
	}
	
	@Length(min=0, max=64, message="row35_one长度必须介于 0 和 64 之间")
	public String getRow35One() {
		return row35One;
	}

	public void setRow35One(String row35One) {
		this.row35One = row35One;
	}
	
	@Length(min=0, max=64, message="row35_two长度必须介于 0 和 64 之间")
	public String getRow35Two() {
		return row35Two;
	}

	public void setRow35Two(String row35Two) {
		this.row35Two = row35Two;
	}
	
	@Length(min=0, max=64, message="row35_three长度必须介于 0 和 64 之间")
	public String getRow35Three() {
		return row35Three;
	}

	public void setRow35Three(String row35Three) {
		this.row35Three = row35Three;
	}
	
	@Length(min=0, max=64, message="row35_four长度必须介于 0 和 64 之间")
	public String getRow35Four() {
		return row35Four;
	}

	public void setRow35Four(String row35Four) {
		this.row35Four = row35Four;
	}
	
	@Length(min=0, max=64, message="row36_one长度必须介于 0 和 64 之间")
	public String getRow36One() {
		return row36One;
	}

	public void setRow36One(String row36One) {
		this.row36One = row36One;
	}
	
	@Length(min=0, max=64, message="row36_two长度必须介于 0 和 64 之间")
	public String getRow36Two() {
		return row36Two;
	}

	public void setRow36Two(String row36Two) {
		this.row36Two = row36Two;
	}
	
	@Length(min=0, max=64, message="row36_three长度必须介于 0 和 64 之间")
	public String getRow36Three() {
		return row36Three;
	}

	public void setRow36Three(String row36Three) {
		this.row36Three = row36Three;
	}
	
	@Length(min=0, max=64, message="row36_four长度必须介于 0 和 64 之间")
	public String getRow36Four() {
		return row36Four;
	}

	public void setRow36Four(String row36Four) {
		this.row36Four = row36Four;
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

	public String getRow25Three() {
		return row25Three;
	}

	public void setRow25Three(String row25Three) {
		this.row25Three = row25Three;
	}

	public String getRow25Four() {
		return row25Four;
	}

	public void setRow25Four(String row25Four) {
		this.row25Four = row25Four;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}