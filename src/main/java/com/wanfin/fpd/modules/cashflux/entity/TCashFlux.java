/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.cashflux.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 现金流量分析Entity
 * @author lx
 * @version 2016-05-20
 */
public class TCashFlux extends DataEntity<TCashFlux> {
	
	private static final long serialVersionUID = 1L;
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
	private String row10Two;		// row10_two
	private String row11One;		// row11_one
	private String row11Two;		// row11_two
	private String row12One;		// row12_one
	private String row12Two;		// row12_two
	private String row13One;		// row13_one
	private String row13Two;		// row13_two
	private String row14One;		// row14_one
	private String row14Two;		// row14_two
	private String row15One;		// row15_one
	private String row15Two;		// row15_two
	private String row16One;		// row16_one
	private String row16Two;		// row16_two
	private String row17One;		// row17_one
	private String row18One;		// row18_one
	private String row19One;		// row19_one
	private String row20One;		// row20_one
	private String row21Two;		// row21_two
	private String row22One;		// row22_one
	private String row22Two;		// row22_two
	private String row23One;		// row23_one
	private String row23Two;		// row23_two
	private String row24One;		// row24_one
	private String row25One;		// row25_one
	private String row26One;		// row26_one
	private String row27One;		// row27_one
	private String row28One;		// row28_one
	private String row28Two;		// row28_two
	private String row29One;		// row29_one
	private String row29Two;		// row29_two
	private String row30One;		// row30_one
	private String row30Two;		// row30_two
	private String row31One;		// row31_one
	private String row31Two;		// row31_two
	private String row32One;		// row32_one
	private String row32Two;		// row32_two
	
	
	private String createTime;//程序暂时使用，不持久化到数据库

	
	public TCashFlux() {
		super();
	}

	public TCashFlux(String id){
		super(id);
	}

	@Length(min=1, max=64, message="报表名称长度必须介于 1 和 64 之间")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	@Length(min=1, max=64, message="租户ID长度必须介于 1 和 64 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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
	
	@Length(min=0, max=64, message="row10_two长度必须介于 0 和 64 之间")
	public String getRow10Two() {
		return row10Two;
	}

	public void setRow10Two(String row10Two) {
		this.row10Two = row10Two;
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
	
	@Length(min=0, max=64, message="row14_one长度必须介于 0 和 64 之间")
	public String getRow14One() {
		return row14One;
	}

	public void setRow14One(String row14One) {
		this.row14One = row14One;
	}
	
	@Length(min=0, max=64, message="row14_two长度必须介于 0 和 64 之间")
	public String getRow14Two() {
		return row14Two;
	}

	public void setRow14Two(String row14Two) {
		this.row14Two = row14Two;
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
	
	@Length(min=0, max=64, message="row17_one长度必须介于 0 和 64 之间")
	public String getRow17One() {
		return row17One;
	}

	public void setRow17One(String row17One) {
		this.row17One = row17One;
	}
	
	@Length(min=0, max=64, message="row18_one长度必须介于 0 和 64 之间")
	public String getRow18One() {
		return row18One;
	}

	public void setRow18One(String row18One) {
		this.row18One = row18One;
	}
	
	@Length(min=0, max=64, message="row19_one长度必须介于 0 和 64 之间")
	public String getRow19One() {
		return row19One;
	}

	public void setRow19One(String row19One) {
		this.row19One = row19One;
	}
	
	@Length(min=0, max=64, message="row20_one长度必须介于 0 和 64 之间")
	public String getRow20One() {
		return row20One;
	}

	public void setRow20One(String row20One) {
		this.row20One = row20One;
	}
	
	@Length(min=0, max=64, message="row21_two长度必须介于 0 和 64 之间")
	public String getRow21Two() {
		return row21Two;
	}

	public void setRow21Two(String row21Two) {
		this.row21Two = row21Two;
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
	
	@Length(min=0, max=64, message="row24_one长度必须介于 0 和 64 之间")
	public String getRow24One() {
		return row24One;
	}

	public void setRow24One(String row24One) {
		this.row24One = row24One;
	}
	
	@Length(min=0, max=64, message="row25_one长度必须介于 0 和 64 之间")
	public String getRow25One() {
		return row25One;
	}

	public void setRow25One(String row25One) {
		this.row25One = row25One;
	}
	
	@Length(min=0, max=64, message="row26_one长度必须介于 0 和 64 之间")
	public String getRow26One() {
		return row26One;
	}

	public void setRow26One(String row26One) {
		this.row26One = row26One;
	}
	
	@Length(min=0, max=64, message="row27_one长度必须介于 0 和 64 之间")
	public String getRow27One() {
		return row27One;
	}

	public void setRow27One(String row27One) {
		this.row27One = row27One;
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
	
	@Length(min=0, max=64, message="row30_one长度必须介于 0 和 64 之间")
	public String getRow30One() {
		return row30One;
	}

	public void setRow30One(String row30One) {
		this.row30One = row30One;
	}
	
	@Length(min=0, max=64, message="row30_two长度必须介于 0 和 64 之间")
	public String getRow30Two() {
		return row30Two;
	}

	public void setRow30Two(String row30Two) {
		this.row30Two = row30Two;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}