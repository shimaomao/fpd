package com.wanfin.fpd.common.config;

public enum CreditType {
	QHZX_TOTAL("WZ201607180615476700", "前海征信"),
	QHZX_VERIFY_ALL("WZ2016071806415937317", "好信一鉴通"),
	QHZX_AUTHENTICATION("WZ2016071810234452400", "好信一鉴通--身份验证"),
	QHZX_VERIFY_MOBILE("WZ201607181024544001", "好信一鉴通--手机验证"),
	QHZX_VERIFY_RELA_PERSON("WZ2016071810254988302", "好信一鉴通--关系人验证"),
	QHZX_VERIFY_HOUSE("WZ2016071810263472603", "好信一鉴通--房产验证"),
	QHZX_VERIFY_CAR("WZ2016071810265950004", "好信一鉴通--车辆验证"),
	QHZX_VERIFY_WORK_UNIT("WZ2016071810283590505", "好信一鉴通--工作单位验证 "),
	QHZX_VERIFY_FACE("WZ2016071810290345306", "好信一鉴通--人脸识别"),
	QHZX_VERIFY_ADDRES("WZ2016071810301249807", "好信一鉴通--地址验证 "),
	QHZX_VERIFY_ACADEMIC("WZ201607181031178408", "好信一鉴通--学历验证"),
	QHZX_CREDIT_LOINE("WZ2016071806234699502", "好信度分"),
	QHZX_RISK_DEGREE("WZ2016071806260260203", "风险度提示 "),
	QHZX_LOAN_INFO("WZ2016071806283562504", "常贷客数据"),
	QHZX_CHEAT_INFO("WZ2016071806314876005", "好信欺诈度提示"),
	QHZX_ADDRESS_INFO("WZ2016071806335470406", "好信地址通"),
	QHZX_CREDIT_TRACK("WZ2016071806344032507", "好信信用轨迹"),
	QHZX_RENT_CAR("WZ2016071806350973608", "好信租车分"),
	QHZX_DEIVE_SCORE("WZ2016071806353261809", "好信驾驶分 "),
	QHZX_COTACT_INFO("WZ2016071806355920010", "好信联络通"),
	QHZX_SPACE_TIME_MAP("WZ2016071806362689311", "好信时空地图 "),
	QHZX_MOBILE_COMPREHENSIVE("WZ2016071806364922312", "好信手机综合资讯"),
	QHZX_BANK_CARD_INFO("WZ2016071806371547913", "好信银行卡咨询"),
	
	QHZX_BANK_SCORE("WZ2016071806374036014", "好信银行卡评分"),
	QHZX_BANK_AUTHENTICATION("WZ2016072101593612700", "一鉴通银行卡鉴权"),
	QHZX_BUSINESS_INFO("WZ2016071806381118715", "好信工商通"),
	QHZX_COURT_ENFORCEMENT("WZ2016071806383481616", "好信法院通-被执行人信息 "),
	
	QHZX_COURT_BREAKWORD("WZ2016071806433355718", "好信法院通-失信被执行信息"),
	QHZX_COURT_ANNOUNCE_RETRIEVE("WZ2016071806440418219", "好信法院通-法院公告信息检索"),
	QHZX_COURT_ANNOUNCE_SEARCH("WZ2016071806442938320", "好信法院通-法院公告信息查询"),
	QHZX_COURT_JUDGE_RETRIEVE("WZ2016071806454771022", "好信法院通-裁判文书信息检索 "),
	QHZX_DEIVE_CERT_TASK("WZ2016072601422991900", "提交驾驶证比对任务数据,a查询"),
	QHZX_DEIVE_CERT_STATE("WZ2016072601425382601", "提交驾驶证比对任务数据,b查询"),
	QHZX_DEIVE_CERT_RESULT("WZ2016072602005664100", "驾驶证比对查询数据,a、b的结果 "),
	PYZX_PERSON_FG("WZ201607060249471200", "个人信息查询"),
	PYZX_PERSON_MOBILE("WZ2016071003512726902", "手机号码核查"),
	PYZX_PERSON_RISK_NUM("WZ2016071003501493101", "个人风险信息 "),
	PYZX_CORP_INFO("WZ2016071003523568003", "企业信息查询");
	
	
	
	private final String code;//征信编号
	private final String type;//征信类型
	
	private CreditType(final String code, final String type) {
		this.code = code;
		this.type = type;
	}
	
	public static String getCreditType(String code){
		System.out.println(code);
		for(CreditType creditType : CreditType.values()){
			if(creditType.code.equals(code)){
				return creditType.type;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}
	

}