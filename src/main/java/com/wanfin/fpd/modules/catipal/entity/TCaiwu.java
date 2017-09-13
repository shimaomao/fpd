/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.entity;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 小贷财务报表Entity
 * @author lxh
 * @version 2016-11-02
 */
public class TCaiwu extends DataEntity<TCaiwu> {
	
	private static final long serialVersionUID = 1L;
	

	private String c1;		// c1
	private String c10;		// c10
	private String c10t;		// c10t
	private String c11;		// c11
	private String c11t;		// c11t
	private String c12;		// c12
	private String c12t;		// c12t
	private String c13;		// c13
	private String c13t;		// c13t
	private String c14;		// c14
	private String c14t;		// c14t
	private String c15;		// c15
	private String c15t;		// c15t
	private String c16;		// c16
	private String c16t;		// c16t
	private String c17;		// c17
	private String c17t;		// c17t
	private String c18;		// c18
	private String c18t;		// c18t
	private String c19;		// c19
	private String c19t;		// c19t
	private String c1t;		// c1t
	private String c2;		// c2
	private String c20;		// c20
	private String c20t;		// c20t
	private String c21;		// c21
	private String c21t;		// c21t
	private String c22;		// c22
	private String c22t;		// c22t
	private String c23;		// c23
	private String c23t;		// c23t
	private String c24;		// c24
	private String c24t;		// c24t
	private String c25;		// c25
	private String c25t;		// c25t
	private String c26;		// c26
	private String c26t;		// c26t
	private String c27;		// c27
	private String c27t;		// c27t
	private String c28;		// c28
	private String c28t;		// c28t
	private String c29;		// c29
	private String c29t;		// c29t
	private String c2t;		// c2t
	private String c3;		// c3
	private String c30;		// c30
	private String c30t;		// c30t
	private String c31;		// c31
	private String c31t;		// c31t
	private String c32;		// c32
	private String c32t;		// c32t
	private String c33;		// c33
	private String c33t;		// c33t
	private String c34;		// c34
	private String c34t;		// c34t
	private String c35;		// c35
	private String c35t;		// c35t
	private String c36;		// c36
	private String c36t;		// c36t
	private String c37;		// c37
	private String c37t;		// c37t
	private String c38;		// c38
	private String c38t;		// c38t
	private String c39;		// c39
	private String c39t;		// c39t
	private String c3t;		// c3t
	private String c4;		// c4
	private String c40;		// c40
	private String c40t;		// c40t
	private String c41;		// c41
	private String c41t;		// c41t
	private String c42;		// c42
	private String c42t;		// c42t
	private String c43;		// c43
	private String c43t;		// c43t
	private String c44;		// c44
	private String c44t;		// c44t
	private String c45;		// c45
	private String c45t;		// c45t
	private String c46;		// c46
	private String c46t;		// c46t
	private String c47;		// c47
	private String c47t;		// c47t
	private String c48;		// c48
	private String c48t;		// c48t
	private String c49;		// c49
	private String c49t;		// c49t
	private String c4t;		// c4t
	private String c5;		// c5
	private String c50;		// c50
	private String c50t;		// c50t
	private String c51;		// c51
	private String c51t;		// c51t
	private String c52;		// c52
	private String c52t;		// c52t
	private String c53;		// c53
	private String c53t;		// c53t
	private String c54;		// c54
	private String c54t;		// c54t
	private String c55;		// c55
	private String c55t;		// c55t
	private String c56;		// c56
	private String c56t;		// c56t
	private String c57;		// c57
	private String c57t;		// c57t
	private String c58;		// c58
	private String c58t;		// c58t
	private String c59;		// c59
	private String c59t;		// c59t
	private String c5t;		// c5t
	private String c6;		// c6
	private String c60;		// c60
	private String c60t;		// c60t
	private String c61;		// c61
	private String c61t;		// c61t
	private String c62;		// c62
	private String c62t;		// c62t
	private String c63;		// c63
	private String c63t;		// c63t
	private String c64;		// c64
	private String c64t;		// c64t
	private String c65;		// c65
	private String c65t;		// c65t
	private String c66;		// c66
	private String c66t;		// c66t
	private String c67;		// c67
	private String c67t;		// c67t
	private String c68;		// c68
	private String c68t;		// c68t
	private String c69;		// c69
	private String c69t;		// c69t
	private String c6t;		// c6t
	private String c7;		// c7
	private String c70;		// c70
	private String c70t;		// c70t
	private String c71;		// c71
	private String c71t;		// c71t
	private String c72;		// c72
	private String c72t;		// c72t
	private String c73;		// c73
	private String c73t;		// c73t
	private String c74;		// c74
	private String c74t;		// c74t
	private String c75;		// c75
	private String c75t;		// c75t
	private String c76;		// c76
	private String c76t;		// c76t
	private String c77;		// c77
	private String c77t;		// c77t
	private String c78;		// c78
	private String c78t;		// c78t
	private String c79;		// c79
	private String c79t;		// c79t
	private String c7t;		// c7t
	private String c8;		// c8
	private String c8t;		// c8t
	private String c9;		// c9
	private String c9t;		// c9t
	private String cd;		// cd
	private String cf;		// cf
	private String cj;		// cj
	private String cr;		// cr
	private String cz;		// cz
	private String baoDate;		// 报备日期
	private String organId;		// 组织机构id
	private Long informFilingType;		// 1：小货资产负债 2：小货利润表
	private String scanFlag;//扫描标识
	private String pushStatus;//同步状态
	private String reportName;		// 公司名称
	
	
	private String filingDate;
	private String fileName;
	private String filePath;	
	

	/*
	 * set and get method
	 */
	public String getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	
	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public TCaiwu() {
		super();
	}

	public TCaiwu(String id){
		super(id);
	}

	@Length(min=0, max=20, message="c1长度必须介于 0 和 15 之间")
	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}
	
	@Length(min=0, max=20, message="c10长度必须介于 0 和 15 之间")
	public String getC10() {
		return c10;
	}

	public void setC10(String c10) {
		this.c10 = c10;
	}
	
	@Length(min=0, max=20, message="c10t长度必须介于 0 和 15 之间")
	public String getC10t() {
		return c10t;
	}

	public void setC10t(String c10t) {
		this.c10t = c10t;
	}
	
	@Length(min=0, max=20, message="c11长度必须介于 0 和 15 之间")
	public String getC11() {
		return c11;
	}

	public void setC11(String c11) {
		this.c11 = c11;
	}
	
	@Length(min=0, max=20, message="c11t长度必须介于 0 和 15 之间")
	public String getC11t() {
		return c11t;
	}

	public void setC11t(String c11t) {
		this.c11t = c11t;
	}
	
	@Length(min=0, max=20, message="c12长度必须介于 0 和 15 之间")
	public String getC12() {
		return c12;
	}

	public void setC12(String c12) {
		this.c12 = c12;
	}
	
	@Length(min=0, max=20, message="c12t长度必须介于 0 和 15 之间")
	public String getC12t() {
		return c12t;
	}

	public void setC12t(String c12t) {
		this.c12t = c12t;
	}
	
	@Length(min=0, max=20, message="c13长度必须介于 0 和 15 之间")
	public String getC13() {
		return c13;
	}

	public void setC13(String c13) {
		this.c13 = c13;
	}
	
	@Length(min=0, max=20, message="c13t长度必须介于 0 和 15 之间")
	public String getC13t() {
		return c13t;
	}

	public void setC13t(String c13t) {
		this.c13t = c13t;
	}
	
	@Length(min=0, max=20, message="c14长度必须介于 0 和 15 之间")
	public String getC14() {
		return c14;
	}

	public void setC14(String c14) {
		this.c14 = c14;
	}
	
	@Length(min=0, max=20, message="c14t长度必须介于 0 和 15 之间")
	public String getC14t() {
		return c14t;
	}

	public void setC14t(String c14t) {
		this.c14t = c14t;
	}
	
	@Length(min=0, max=20, message="c15长度必须介于 0 和 15 之间")
	public String getC15() {
		return c15;
	}

	public void setC15(String c15) {
		this.c15 = c15;
	}
	
	@Length(min=0, max=20, message="c15t长度必须介于 0 和 15 之间")
	public String getC15t() {
		return c15t;
	}

	public void setC15t(String c15t) {
		this.c15t = c15t;
	}
	
	@Length(min=0, max=20, message="c16长度必须介于 0 和 15 之间")
	public String getC16() {
		return c16;
	}

	public void setC16(String c16) {
		this.c16 = c16;
	}
	
	@Length(min=0, max=20, message="c16t长度必须介于 0 和 15 之间")
	public String getC16t() {
		return c16t;
	}

	public void setC16t(String c16t) {
		this.c16t = c16t;
	}
	
	@Length(min=0, max=20, message="c17长度必须介于 0 和 15 之间")
	public String getC17() {
		return c17;
	}

	public void setC17(String c17) {
		this.c17 = c17;
	}
	
	@Length(min=0, max=20, message="c17t长度必须介于 0 和 15 之间")
	public String getC17t() {
		return c17t;
	}

	public void setC17t(String c17t) {
		this.c17t = c17t;
	}
	
	@Length(min=0, max=20, message="c18长度必须介于 0 和 15 之间")
	public String getC18() {
		return c18;
	}

	public void setC18(String c18) {
		this.c18 = c18;
	}
	
	@Length(min=0, max=20, message="c18t长度必须介于 0 和 15 之间")
	public String getC18t() {
		return c18t;
	}

	public void setC18t(String c18t) {
		this.c18t = c18t;
	}
	
	@Length(min=0, max=20, message="c19长度必须介于 0 和 15 之间")
	public String getC19() {
		return c19;
	}

	public void setC19(String c19) {
		this.c19 = c19;
	}
	
	@Length(min=0, max=20, message="c19t长度必须介于 0 和 15 之间")
	public String getC19t() {
		return c19t;
	}

	public void setC19t(String c19t) {
		this.c19t = c19t;
	}
	
	@Length(min=0, max=20, message="c1t长度必须介于 0 和 15 之间")
	public String getC1t() {
		return c1t;
	}

	public void setC1t(String c1t) {
		this.c1t = c1t;
	}
	
	@Length(min=0, max=20, message="c2长度必须介于 0 和 15 之间")
	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}
	
	@Length(min=0, max=20, message="c20长度必须介于 0 和 15 之间")
	public String getC20() {
		return c20;
	}

	public void setC20(String c20) {
		this.c20 = c20;
	}
	
	@Length(min=0, max=20, message="c20t长度必须介于 0 和 15 之间")
	public String getC20t() {
		return c20t;
	}

	public void setC20t(String c20t) {
		this.c20t = c20t;
	}
	
	@Length(min=0, max=20, message="c21长度必须介于 0 和 15 之间")
	public String getC21() {
		return c21;
	}

	public void setC21(String c21) {
		this.c21 = c21;
	}
	
	@Length(min=0, max=20, message="c21t长度必须介于 0 和 15 之间")
	public String getC21t() {
		return c21t;
	}

	public void setC21t(String c21t) {
		this.c21t = c21t;
	}
	
	@Length(min=0, max=20, message="c22长度必须介于 0 和 15 之间")
	public String getC22() {
		return c22;
	}

	public void setC22(String c22) {
		this.c22 = c22;
	}
	
	@Length(min=0, max=20, message="c22t长度必须介于 0 和 15 之间")
	public String getC22t() {
		return c22t;
	}

	public void setC22t(String c22t) {
		this.c22t = c22t;
	}
	
	@Length(min=0, max=20, message="c23长度必须介于 0 和 15 之间")
	public String getC23() {
		return c23;
	}

	public void setC23(String c23) {
		this.c23 = c23;
	}
	
	@Length(min=0, max=20, message="c23t长度必须介于 0 和 15 之间")
	public String getC23t() {
		return c23t;
	}

	public void setC23t(String c23t) {
		this.c23t = c23t;
	}
	
	@Length(min=0, max=20, message="c24长度必须介于 0 和 15 之间")
	public String getC24() {
		return c24;
	}

	public void setC24(String c24) {
		this.c24 = c24;
	}
	
	@Length(min=0, max=20, message="c24t长度必须介于 0 和 15 之间")
	public String getC24t() {
		return c24t;
	}

	public void setC24t(String c24t) {
		this.c24t = c24t;
	}
	
	@Length(min=0, max=20, message="c25长度必须介于 0 和 15 之间")
	public String getC25() {
		return c25;
	}

	public void setC25(String c25) {
		this.c25 = c25;
	}
	
	@Length(min=0, max=20, message="c25t长度必须介于 0 和 15 之间")
	public String getC25t() {
		return c25t;
	}

	public void setC25t(String c25t) {
		this.c25t = c25t;
	}
	
	@Length(min=0, max=20, message="c26长度必须介于 0 和 15 之间")
	public String getC26() {
		return c26;
	}

	public void setC26(String c26) {
		this.c26 = c26;
	}
	
	@Length(min=0, max=20, message="c26t长度必须介于 0 和 15 之间")
	public String getC26t() {
		return c26t;
	}

	public void setC26t(String c26t) {
		this.c26t = c26t;
	}
	
	@Length(min=0, max=20, message="c27长度必须介于 0 和 15 之间")
	public String getC27() {
		return c27;
	}

	public void setC27(String c27) {
		this.c27 = c27;
	}
	
	@Length(min=0, max=20, message="c27t长度必须介于 0 和 15 之间")
	public String getC27t() {
		return c27t;
	}

	public void setC27t(String c27t) {
		this.c27t = c27t;
	}
	
	@Length(min=0, max=20, message="c28长度必须介于 0 和 15 之间")
	public String getC28() {
		return c28;
	}

	public void setC28(String c28) {
		this.c28 = c28;
	}
	
	@Length(min=0, max=20, message="c28t长度必须介于 0 和 15 之间")
	public String getC28t() {
		return c28t;
	}

	public void setC28t(String c28t) {
		this.c28t = c28t;
	}
	
	@Length(min=0, max=20, message="c29长度必须介于 0 和 15 之间")
	public String getC29() {
		return c29;
	}

	public void setC29(String c29) {
		this.c29 = c29;
	}
	
	@Length(min=0, max=20, message="c29t长度必须介于 0 和 15 之间")
	public String getC29t() {
		return c29t;
	}

	public void setC29t(String c29t) {
		this.c29t = c29t;
	}
	
	@Length(min=0, max=20, message="c2t长度必须介于 0 和 15 之间")
	public String getC2t() {
		return c2t;
	}

	public void setC2t(String c2t) {
		this.c2t = c2t;
	}
	
	@Length(min=0, max=20, message="c3长度必须介于 0 和 15 之间")
	public String getC3() {
		return c3;
	}

	public void setC3(String c3) {
		this.c3 = c3;
	}
	
	@Length(min=0, max=20, message="c30长度必须介于 0 和 15 之间")
	public String getC30() {
		return c30;
	}

	public void setC30(String c30) {
		this.c30 = c30;
	}
	
	@Length(min=0, max=20, message="c30t长度必须介于 0 和 15 之间")
	public String getC30t() {
		return c30t;
	}

	public void setC30t(String c30t) {
		this.c30t = c30t;
	}
	
	@Length(min=0, max=20, message="c31长度必须介于 0 和 15 之间")
	public String getC31() {
		return c31;
	}

	public void setC31(String c31) {
		this.c31 = c31;
	}
	
	@Length(min=0, max=20, message="c31t长度必须介于 0 和 15 之间")
	public String getC31t() {
		return c31t;
	}

	public void setC31t(String c31t) {
		this.c31t = c31t;
	}
	
	@Length(min=0, max=20, message="c32长度必须介于 0 和 15 之间")
	public String getC32() {
		return c32;
	}

	public void setC32(String c32) {
		this.c32 = c32;
	}
	
	@Length(min=0, max=20, message="c32t长度必须介于 0 和 15 之间")
	public String getC32t() {
		return c32t;
	}

	public void setC32t(String c32t) {
		this.c32t = c32t;
	}
	
	@Length(min=0, max=20, message="c33长度必须介于 0 和 15 之间")
	public String getC33() {
		return c33;
	}

	public void setC33(String c33) {
		this.c33 = c33;
	}
	
	@Length(min=0, max=20, message="c33t长度必须介于 0 和 15 之间")
	public String getC33t() {
		return c33t;
	}

	public void setC33t(String c33t) {
		this.c33t = c33t;
	}
	
	@Length(min=0, max=20, message="c34长度必须介于 0 和 15 之间")
	public String getC34() {
		return c34;
	}

	public void setC34(String c34) {
		this.c34 = c34;
	}
	
	@Length(min=0, max=20, message="c34t长度必须介于 0 和 15 之间")
	public String getC34t() {
		return c34t;
	}

	public void setC34t(String c34t) {
		this.c34t = c34t;
	}
	
	@Length(min=0, max=20, message="c35长度必须介于 0 和 15 之间")
	public String getC35() {
		return c35;
	}

	public void setC35(String c35) {
		this.c35 = c35;
	}
	
	@Length(min=0, max=20, message="c35t长度必须介于 0 和 15 之间")
	public String getC35t() {
		return c35t;
	}

	public void setC35t(String c35t) {
		this.c35t = c35t;
	}
	
	@Length(min=0, max=20, message="c36长度必须介于 0 和 15 之间")
	public String getC36() {
		return c36;
	}

	public void setC36(String c36) {
		this.c36 = c36;
	}
	
	@Length(min=0, max=20, message="c36t长度必须介于 0 和 15 之间")
	public String getC36t() {
		return c36t;
	}

	public void setC36t(String c36t) {
		this.c36t = c36t;
	}
	
	@Length(min=0, max=20, message="c37长度必须介于 0 和 15 之间")
	public String getC37() {
		return c37;
	}

	public void setC37(String c37) {
		this.c37 = c37;
	}
	
	@Length(min=0, max=20, message="c37t长度必须介于 0 和 15 之间")
	public String getC37t() {
		return c37t;
	}

	public void setC37t(String c37t) {
		this.c37t = c37t;
	}
	
	@Length(min=0, max=20, message="c38长度必须介于 0 和 15 之间")
	public String getC38() {
		return c38;
	}

	public void setC38(String c38) {
		this.c38 = c38;
	}
	
	@Length(min=0, max=20, message="c38t长度必须介于 0 和 15 之间")
	public String getC38t() {
		return c38t;
	}

	public void setC38t(String c38t) {
		this.c38t = c38t;
	}
	
	@Length(min=0, max=20, message="c39长度必须介于 0 和 15 之间")
	public String getC39() {
		return c39;
	}

	public void setC39(String c39) {
		this.c39 = c39;
	}
	
	@Length(min=0, max=20, message="c39t长度必须介于 0 和 15 之间")
	public String getC39t() {
		return c39t;
	}

	public void setC39t(String c39t) {
		this.c39t = c39t;
	}
	
	@Length(min=0, max=20, message="c3t长度必须介于 0 和 15 之间")
	public String getC3t() {
		return c3t;
	}

	public void setC3t(String c3t) {
		this.c3t = c3t;
	}
	
	@Length(min=0, max=20, message="c4长度必须介于 0 和 15 之间")
	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}
	
	@Length(min=0, max=20, message="c40长度必须介于 0 和 15 之间")
	public String getC40() {
		return c40;
	}

	public void setC40(String c40) {
		this.c40 = c40;
	}
	
	@Length(min=0, max=20, message="c40t长度必须介于 0 和 15 之间")
	public String getC40t() {
		return c40t;
	}

	public void setC40t(String c40t) {
		this.c40t = c40t;
	}
	
	@Length(min=0, max=20, message="c41长度必须介于 0 和 15 之间")
	public String getC41() {
		return c41;
	}

	public void setC41(String c41) {
		this.c41 = c41;
	}
	
	@Length(min=0, max=20, message="c41t长度必须介于 0 和 15 之间")
	public String getC41t() {
		return c41t;
	}

	public void setC41t(String c41t) {
		this.c41t = c41t;
	}
	
	@Length(min=0, max=20, message="c42长度必须介于 0 和 15 之间")
	public String getC42() {
		return c42;
	}

	public void setC42(String c42) {
		this.c42 = c42;
	}
	
	@Length(min=0, max=20, message="c42t长度必须介于 0 和 15 之间")
	public String getC42t() {
		return c42t;
	}

	public void setC42t(String c42t) {
		this.c42t = c42t;
	}
	
	@Length(min=0, max=20, message="c43长度必须介于 0 和 15 之间")
	public String getC43() {
		return c43;
	}

	public void setC43(String c43) {
		this.c43 = c43;
	}
	
	@Length(min=0, max=20, message="c43t长度必须介于 0 和 15 之间")
	public String getC43t() {
		return c43t;
	}

	public void setC43t(String c43t) {
		this.c43t = c43t;
	}
	
	@Length(min=0, max=20, message="c44长度必须介于 0 和 15 之间")
	public String getC44() {
		return c44;
	}

	public void setC44(String c44) {
		this.c44 = c44;
	}
	
	@Length(min=0, max=20, message="c44t长度必须介于 0 和 15 之间")
	public String getC44t() {
		return c44t;
	}

	public void setC44t(String c44t) {
		this.c44t = c44t;
	}
	
	@Length(min=0, max=20, message="c45长度必须介于 0 和 15 之间")
	public String getC45() {
		return c45;
	}

	public void setC45(String c45) {
		this.c45 = c45;
	}
	
	@Length(min=0, max=20, message="c45t长度必须介于 0 和 15 之间")
	public String getC45t() {
		return c45t;
	}

	public void setC45t(String c45t) {
		this.c45t = c45t;
	}
	
	@Length(min=0, max=20, message="c46长度必须介于 0 和 15 之间")
	public String getC46() {
		return c46;
	}

	public void setC46(String c46) {
		this.c46 = c46;
	}
	
	@Length(min=0, max=20, message="c46t长度必须介于 0 和 15 之间")
	public String getC46t() {
		return c46t;
	}

	public void setC46t(String c46t) {
		this.c46t = c46t;
	}
	
	@Length(min=0, max=20, message="c47长度必须介于 0 和 15 之间")
	public String getC47() {
		return c47;
	}

	public void setC47(String c47) {
		this.c47 = c47;
	}
	
	@Length(min=0, max=20, message="c47t长度必须介于 0 和 15 之间")
	public String getC47t() {
		return c47t;
	}

	public void setC47t(String c47t) {
		this.c47t = c47t;
	}
	
	@Length(min=0, max=20, message="c48长度必须介于 0 和 15 之间")
	public String getC48() {
		return c48;
	}

	public void setC48(String c48) {
		this.c48 = c48;
	}
	
	@Length(min=0, max=20, message="c48t长度必须介于 0 和 15 之间")
	public String getC48t() {
		return c48t;
	}

	public void setC48t(String c48t) {
		this.c48t = c48t;
	}
	
	@Length(min=0, max=20, message="c49长度必须介于 0 和 15 之间")
	public String getC49() {
		return c49;
	}

	public void setC49(String c49) {
		this.c49 = c49;
	}
	
	@Length(min=0, max=20, message="c49t长度必须介于 0 和 15 之间")
	public String getC49t() {
		return c49t;
	}

	public void setC49t(String c49t) {
		this.c49t = c49t;
	}
	
	@Length(min=0, max=20, message="c4t长度必须介于 0 和 15 之间")
	public String getC4t() {
		return c4t;
	}

	public void setC4t(String c4t) {
		this.c4t = c4t;
	}
	
	@Length(min=0, max=20, message="c5长度必须介于 0 和 15 之间")
	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}
	
	@Length(min=0, max=20, message="c50长度必须介于 0 和 15 之间")
	public String getC50() {
		return c50;
	}

	public void setC50(String c50) {
		this.c50 = c50;
	}
	
	@Length(min=0, max=20, message="c50t长度必须介于 0 和 15 之间")
	public String getC50t() {
		return c50t;
	}

	public void setC50t(String c50t) {
		this.c50t = c50t;
	}
	
	@Length(min=0, max=20, message="c51长度必须介于 0 和 15 之间")
	public String getC51() {
		return c51;
	}

	public void setC51(String c51) {
		this.c51 = c51;
	}
	
	@Length(min=0, max=20, message="c51t长度必须介于 0 和 15 之间")
	public String getC51t() {
		return c51t;
	}

	public void setC51t(String c51t) {
		this.c51t = c51t;
	}
	
	@Length(min=0, max=20, message="c52长度必须介于 0 和 15 之间")
	public String getC52() {
		return c52;
	}

	public void setC52(String c52) {
		this.c52 = c52;
	}
	
	@Length(min=0, max=20, message="c52t长度必须介于 0 和 15 之间")
	public String getC52t() {
		return c52t;
	}

	public void setC52t(String c52t) {
		this.c52t = c52t;
	}
	
	@Length(min=0, max=20, message="c53长度必须介于 0 和 15 之间")
	public String getC53() {
		return c53;
	}

	public void setC53(String c53) {
		this.c53 = c53;
	}
	
	@Length(min=0, max=20, message="c53t长度必须介于 0 和 15 之间")
	public String getC53t() {
		return c53t;
	}

	public void setC53t(String c53t) {
		this.c53t = c53t;
	}
	
	@Length(min=0, max=20, message="c54长度必须介于 0 和 15 之间")
	public String getC54() {
		return c54;
	}

	public void setC54(String c54) {
		this.c54 = c54;
	}
	
	@Length(min=0, max=20, message="c54t长度必须介于 0 和 15 之间")
	public String getC54t() {
		return c54t;
	}

	public void setC54t(String c54t) {
		this.c54t = c54t;
	}
	
	@Length(min=0, max=20, message="c55长度必须介于 0 和 15 之间")
	public String getC55() {
		return c55;
	}

	public void setC55(String c55) {
		this.c55 = c55;
	}
	
	@Length(min=0, max=20, message="c55t长度必须介于 0 和 15 之间")
	public String getC55t() {
		return c55t;
	}

	public void setC55t(String c55t) {
		this.c55t = c55t;
	}
	
	@Length(min=0, max=20, message="c56长度必须介于 0 和 15 之间")
	public String getC56() {
		return c56;
	}

	public void setC56(String c56) {
		this.c56 = c56;
	}
	
	@Length(min=0, max=20, message="c56t长度必须介于 0 和 15 之间")
	public String getC56t() {
		return c56t;
	}

	public void setC56t(String c56t) {
		this.c56t = c56t;
	}
	
	@Length(min=0, max=20, message="c57长度必须介于 0 和 15 之间")
	public String getC57() {
		return c57;
	}

	public void setC57(String c57) {
		this.c57 = c57;
	}
	
	@Length(min=0, max=20, message="c57t长度必须介于 0 和 15 之间")
	public String getC57t() {
		return c57t;
	}

	public void setC57t(String c57t) {
		this.c57t = c57t;
	}
	
	@Length(min=0, max=20, message="c58长度必须介于 0 和 15 之间")
	public String getC58() {
		return c58;
	}

	public void setC58(String c58) {
		this.c58 = c58;
	}
	
	@Length(min=0, max=20, message="c58t长度必须介于 0 和 15 之间")
	public String getC58t() {
		return c58t;
	}

	public void setC58t(String c58t) {
		this.c58t = c58t;
	}
	
	@Length(min=0, max=20, message="c59长度必须介于 0 和 15 之间")
	public String getC59() {
		return c59;
	}

	public void setC59(String c59) {
		this.c59 = c59;
	}
	
	@Length(min=0, max=20, message="c59t长度必须介于 0 和 15 之间")
	public String getC59t() {
		return c59t;
	}

	public void setC59t(String c59t) {
		this.c59t = c59t;
	}
	
	@Length(min=0, max=20, message="c5t长度必须介于 0 和 15 之间")
	public String getC5t() {
		return c5t;
	}

	public void setC5t(String c5t) {
		this.c5t = c5t;
	}
	
	@Length(min=0, max=20, message="c6长度必须介于 0 和 15 之间")
	public String getC6() {
		return c6;
	}

	public void setC6(String c6) {
		this.c6 = c6;
	}
	
	@Length(min=0, max=20, message="c60长度必须介于 0 和 15 之间")
	public String getC60() {
		return c60;
	}

	public void setC60(String c60) {
		this.c60 = c60;
	}
	
	@Length(min=0, max=20, message="c60t长度必须介于 0 和 15 之间")
	public String getC60t() {
		return c60t;
	}

	public void setC60t(String c60t) {
		this.c60t = c60t;
	}
	
	@Length(min=0, max=20, message="c61长度必须介于 0 和 15 之间")
	public String getC61() {
		return c61;
	}

	public void setC61(String c61) {
		this.c61 = c61;
	}
	
	@Length(min=0, max=20, message="c61t长度必须介于 0 和 15 之间")
	public String getC61t() {
		return c61t;
	}

	public void setC61t(String c61t) {
		this.c61t = c61t;
	}
	
	@Length(min=0, max=20, message="c62长度必须介于 0 和 15 之间")
	public String getC62() {
		return c62;
	}

	public void setC62(String c62) {
		this.c62 = c62;
	}
	
	@Length(min=0, max=20, message="c62t长度必须介于 0 和 15 之间")
	public String getC62t() {
		return c62t;
	}

	public void setC62t(String c62t) {
		this.c62t = c62t;
	}
	
	@Length(min=0, max=20, message="c63长度必须介于 0 和 15 之间")
	public String getC63() {
		return c63;
	}

	public void setC63(String c63) {
		this.c63 = c63;
	}
	
	@Length(min=0, max=20, message="c63t长度必须介于 0 和 15 之间")
	public String getC63t() {
		return c63t;
	}

	public void setC63t(String c63t) {
		this.c63t = c63t;
	}
	
	@Length(min=0, max=20, message="c64长度必须介于 0 和 15 之间")
	public String getC64() {
		return c64;
	}

	public void setC64(String c64) {
		this.c64 = c64;
	}
	
	@Length(min=0, max=20, message="c64t长度必须介于 0 和 15 之间")
	public String getC64t() {
		return c64t;
	}

	public void setC64t(String c64t) {
		this.c64t = c64t;
	}
	
	@Length(min=0, max=20, message="c65长度必须介于 0 和 15 之间")
	public String getC65() {
		return c65;
	}

	public void setC65(String c65) {
		this.c65 = c65;
	}
	
	@Length(min=0, max=20, message="c65t长度必须介于 0 和 15 之间")
	public String getC65t() {
		return c65t;
	}

	public void setC65t(String c65t) {
		this.c65t = c65t;
	}
	
	@Length(min=0, max=20, message="c66长度必须介于 0 和 15 之间")
	public String getC66() {
		return c66;
	}

	public void setC66(String c66) {
		this.c66 = c66;
	}
	
	@Length(min=0, max=20, message="c66t长度必须介于 0 和 15 之间")
	public String getC66t() {
		return c66t;
	}

	public void setC66t(String c66t) {
		this.c66t = c66t;
	}
	
	@Length(min=0, max=20, message="c67长度必须介于 0 和 15 之间")
	public String getC67() {
		return c67;
	}

	public void setC67(String c67) {
		this.c67 = c67;
	}
	
	@Length(min=0, max=20, message="c67t长度必须介于 0 和 15 之间")
	public String getC67t() {
		return c67t;
	}

	public void setC67t(String c67t) {
		this.c67t = c67t;
	}
	
	@Length(min=0, max=20, message="c68长度必须介于 0 和 15 之间")
	public String getC68() {
		return c68;
	}

	public void setC68(String c68) {
		this.c68 = c68;
	}
	
	@Length(min=0, max=20, message="c68t长度必须介于 0 和 15 之间")
	public String getC68t() {
		return c68t;
	}

	public void setC68t(String c68t) {
		this.c68t = c68t;
	}
	
	@Length(min=0, max=20, message="c69长度必须介于 0 和 15 之间")
	public String getC69() {
		return c69;
	}

	public void setC69(String c69) {
		this.c69 = c69;
	}
	
	@Length(min=0, max=20, message="c69t长度必须介于 0 和 15 之间")
	public String getC69t() {
		return c69t;
	}

	public void setC69t(String c69t) {
		this.c69t = c69t;
	}
	
	@Length(min=0, max=20, message="c6t长度必须介于 0 和 15 之间")
	public String getC6t() {
		return c6t;
	}

	public void setC6t(String c6t) {
		this.c6t = c6t;
	}
	
	@Length(min=0, max=20, message="c7长度必须介于 0 和 15 之间")
	public String getC7() {
		return c7;
	}

	public void setC7(String c7) {
		this.c7 = c7;
	}
	
	@Length(min=0, max=20, message="c70长度必须介于 0 和 15 之间")
	public String getC70() {
		return c70;
	}

	public void setC70(String c70) {
		this.c70 = c70;
	}
	
	@Length(min=0, max=20, message="c70t长度必须介于 0 和 15 之间")
	public String getC70t() {
		return c70t;
	}

	public void setC70t(String c70t) {
		this.c70t = c70t;
	}
	
	@Length(min=0, max=20, message="c71长度必须介于 0 和 15 之间")
	public String getC71() {
		return c71;
	}

	public void setC71(String c71) {
		this.c71 = c71;
	}
	
	@Length(min=0, max=20, message="c71t长度必须介于 0 和 15 之间")
	public String getC71t() {
		return c71t;
	}

	public void setC71t(String c71t) {
		this.c71t = c71t;
	}
	
	@Length(min=0, max=20, message="c72长度必须介于 0 和 15 之间")
	public String getC72() {
		return c72;
	}

	public void setC72(String c72) {
		this.c72 = c72;
	}
	
	@Length(min=0, max=20, message="c72t长度必须介于 0 和 15 之间")
	public String getC72t() {
		return c72t;
	}

	public void setC72t(String c72t) {
		this.c72t = c72t;
	}
	
	@Length(min=0, max=20, message="c73长度必须介于 0 和 15 之间")
	public String getC73() {
		return c73;
	}

	public void setC73(String c73) {
		this.c73 = c73;
	}
	
	@Length(min=0, max=20, message="c73t长度必须介于 0 和 15 之间")
	public String getC73t() {
		return c73t;
	}

	public void setC73t(String c73t) {
		this.c73t = c73t;
	}
	
	@Length(min=0, max=20, message="c74长度必须介于 0 和 15 之间")
	public String getC74() {
		return c74;
	}

	public void setC74(String c74) {
		this.c74 = c74;
	}
	
	@Length(min=0, max=20, message="c74t长度必须介于 0 和 15 之间")
	public String getC74t() {
		return c74t;
	}

	public void setC74t(String c74t) {
		this.c74t = c74t;
	}
	
	@Length(min=0, max=20, message="c75长度必须介于 0 和 15 之间")
	public String getC75() {
		return c75;
	}

	public void setC75(String c75) {
		this.c75 = c75;
	}
	
	@Length(min=0, max=20, message="c75t长度必须介于 0 和 15 之间")
	public String getC75t() {
		return c75t;
	}

	public void setC75t(String c75t) {
		this.c75t = c75t;
	}
	
	@Length(min=0, max=20, message="c76长度必须介于 0 和 15 之间")
	public String getC76() {
		return c76;
	}

	public void setC76(String c76) {
		this.c76 = c76;
	}
	
	@Length(min=0, max=20, message="c76t长度必须介于 0 和 15 之间")
	public String getC76t() {
		return c76t;
	}

	public void setC76t(String c76t) {
		this.c76t = c76t;
	}
	
	@Length(min=0, max=20, message="c77长度必须介于 0 和 15 之间")
	public String getC77() {
		return c77;
	}

	public void setC77(String c77) {
		this.c77 = c77;
	}
	
	@Length(min=0, max=20, message="c77t长度必须介于 0 和 15 之间")
	public String getC77t() {
		return c77t;
	}

	public void setC77t(String c77t) {
		this.c77t = c77t;
	}
	
	@Length(min=0, max=20, message="c78长度必须介于 0 和 15 之间")
	public String getC78() {
		return c78;
	}

	public void setC78(String c78) {
		this.c78 = c78;
	}
	
	@Length(min=0, max=20, message="c78t长度必须介于 0 和 15 之间")
	public String getC78t() {
		return c78t;
	}

	public void setC78t(String c78t) {
		this.c78t = c78t;
	}
	
	@Length(min=0, max=20, message="c79长度必须介于 0 和 15 之间")
	public String getC79() {
		return c79;
	}

	public void setC79(String c79) {
		this.c79 = c79;
	}
	
	@Length(min=0, max=20, message="c79t长度必须介于 0 和 15 之间")
	public String getC79t() {
		return c79t;
	}

	public void setC79t(String c79t) {
		this.c79t = c79t;
	}
	
	@Length(min=0, max=20, message="c7t长度必须介于 0 和 15 之间")
	public String getC7t() {
		return c7t;
	}

	public void setC7t(String c7t) {
		this.c7t = c7t;
	}
	
	@Length(min=0, max=20, message="c8长度必须介于 0 和 15 之间")
	public String getC8() {
		return c8;
	}

	public void setC8(String c8) {
		this.c8 = c8;
	}
	
	@Length(min=0, max=20, message="c8t长度必须介于 0 和 15 之间")
	public String getC8t() {
		return c8t;
	}

	public void setC8t(String c8t) {
		this.c8t = c8t;
	}
	
	@Length(min=0, max=20, message="c9长度必须介于 0 和 15 之间")
	public String getC9() {
		return c9;
	}

	public void setC9(String c9) {
		this.c9 = c9;
	}
	
	@Length(min=0, max=20, message="c9t长度必须介于 0 和 15 之间")
	public String getC9t() {
		return c9t;
	}

	public void setC9t(String c9t) {
		this.c9t = c9t;
	}
	
	@Length(min=0, max=100, message="cd长度必须介于 0 和 20 之间")
	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}
	
	@Length(min=0, max=10, message="cf长度必须介于 0 和 10 之间")
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}
	
	@Length(min=0, max=10, message="cj长度必须介于 0 和 10 之间")
	public String getCj() {
		return cj;
	}

	public void setCj(String cj) {
		this.cj = cj;
	}
	
	@Length(min=0, max=20, message="cr长度必须介于 0 和 20 之间")
	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}
	
	@Length(min=0, max=10, message="cz长度必须介于 0 和 10 之间")
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}
	
	@Length(min=0, max=50, message="报备日期长度必须介于 0 和 50 之间")
	public String getBaoDate() {
		return baoDate;
	}

	public void setBaoDate(String baoDate) {
		this.baoDate = baoDate;
	}
	
	@Length(min=1, max=255, message="组织机构id长度必须介于 1 和 255 之间")
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	public Long getInformFilingType() {
		return informFilingType;
	}

	public void setInformFilingType(Long informFilingType) {
		this.informFilingType = informFilingType;
	}
	
	
	@Override
	public String toString() {		
		String str = "";
		if("1".equals(this.getDelFlag())){
			str += "D"+"|"+this.getReportName()+"|";
		}else{
			if("0".equals(this.getPushStatus())){
				str += "A"+"|"+this.getReportName()+"|";
			}else{
				str += "U"+"|"+this.getReportName()+"|";
			}
			
		}	
		
		str += this.getC1()+"|" + this.getC2()+"|" + this.getC3()+"|" + this.getC4()+"|" + this.getC5()+"|" + this.getC6()+"|";
		str += this.getC1t()+"|" + this.getC2t()+"|" + this.getC3t()+"|" + this.getC4t()+"|" + this.getC5t()+"|" + this.getC6t()+"|";
		str += this.getC7()+"|" + this.getC8()+"|" + this.getC9()+"|" + this.getC10()+"|" + this.getC11()+"|" + this.getC12()+"|";
		str += this.getC7t()+"|" + this.getC8t()+"|" + this.getC9t()+"|" + this.getC10t()+"|" + this.getC11t()+"|" + this.getC12t()+"|";
		str += this.getC13()+"|" + this.getC14()+"|" + this.getC15()+"|" + this.getC16()+"|" + this.getC17()+"|" + this.getC18()+"|";
		str += this.getC13t()+"|" + this.getC14t()+"|" + this.getC15t()+"|" + this.getC16t()+"|" + this.getC17t()+"|" + this.getC18t()+"|";
		str += this.getC19()+"|" + this.getC20()+"|" + this.getC21()+"|" + this.getC22()+"|" + this.getC23()+"|" + this.getC24()+"|";
		str += this.getC19t()+"|" + this.getC20t()+"|" + this.getC21t()+"|" + this.getC22t()+"|" + this.getC23t()+"|" + this.getC24t()+"|";
		str += this.getC25()+"|" + this.getC26()+"|" + this.getC27()+"|" + this.getC28()+"|" + this.getC29()+"|" + this.getC30()+"|";
		str += this.getC25t()+"|" + this.getC26t()+"|" + this.getC27t()+"|" + this.getC28t()+"|" + this.getC29t()+"|" + this.getC30t()+"|";
		str += this.getC31()+"|" + this.getC32()+"|" + this.getC33()+"|" + this.getC34()+"|" + this.getC35()+"|" + this.getC36()+"|";
		str += this.getC31t()+"|" + this.getC32t()+"|" + this.getC33t()+"|" + this.getC34t()+"|" + this.getC35t()+"|" + this.getC36t()+"|";
		str += this.getC37()+"|" + this.getC38()+"|" + this.getC39()+"|" + this.getC40()+"|" + this.getC41()+"|" + this.getC42()+"|";
		str += this.getC37t()+"|" + this.getC38t()+"|" + this.getC39t()+"|" + this.getC40t()+"|" + this.getC41t()+"|" + this.getC42t()+"|";
		str += this.getC43()+"|" + this.getC44()+"|" + this.getC45()+"|" + this.getC46()+"|" + this.getC47()+"|" + this.getC48()+"|";
		str += this.getC43t()+"|" + this.getC44t()+"|" + this.getC45t()+"|" + this.getC46t()+"|" + this.getC47t()+"|" + this.getC48t()+"|";
		str += this.getC49()+"|" + this.getC50()+"|" + this.getC51()+"|" + this.getC52()+"|" + this.getC53()+"|" + this.getC54()+"|";
		str += this.getC49t()+"|" + this.getC50t()+"|" + this.getC51t()+"|" + this.getC52t()+"|" + this.getC53t()+"|" + this.getC54t()+"|";
		str += this.getC55()+"|" + this.getC56()+"|" + this.getC57()+"|" + this.getC58()+"|" + this.getC59()+"|" + this.getC60()+"|";
		str += this.getC55t()+"|" + this.getC56t()+"|" + this.getC57t()+"|" + this.getC58t()+"|" + this.getC59t()+"|" + this.getC60t()+"|";
		str += this.getC61()+"|" + this.getC62()+"|" + this.getC63()+"|" + this.getC64()+"|" + this.getC65()+"|" + this.getC66()+"|";
		str += this.getC61t()+"|" + this.getC62t()+"|" + this.getC63t()+"|" + this.getC64t()+"|" + this.getC65t()+"|" + this.getC66t()+"|";
		str += this.getC67()+"|" + this.getC68()+"|" + this.getC69()+"|" + this.getC70()+"|" + this.getC71()+"|" + this.getC72()+"|";
		str += this.getC67t()+"|" + this.getC68t()+"|" + this.getC69t()+"|" + this.getC70t()+"|" + this.getC71t()+"|" + this.getC72t()+"|";
		str += this.getC73()+"|" + this.getC74()+"|" + this.getC75()+"|" + this.getC76()+"|" + this.getC77()+"|" + this.getC78()+"|";
		str += this.getC73t()+"|" + this.getC74t()+"|" + this.getC75t()+"|" + this.getC76t()+"|" + this.getC77t()+"|" + this.getC78t()+"|";
		/*
		 * private String cd;		// cd
	private String cf;		// cf
	private String cj;		// cj
	private String cr;		// cr
	private String cz;		// cz
	private String baoDate;		// 报备日期
	private Long informFilingType;		// 1：小货资产负债 2：小货利润表
		 */
		str += this.getC79()+"|"+ this.getC79t()+"|"+ this.getCd()+"|"+ this.getCf()+"|"+ this.getCj()+"|"+ this.getCr()+"|"+ this.getCz()+"|"+ this.getBaoDate()+"|";
		str += this.getInformFilingType()+"|" + this.getId();
		

		return str;
	}
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	
}