package com.wanfin.fpd.http.creditScoring;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.common.utils.InterfaceUtil;

/**
* @author Chenh  
* @date 2016年8月19日 上午10:40:44 
* @Description [[_授信评分接口测试_]] TestCreditScoring类
* 授信评分接口测试 
*
 */
public class TestCreditScoring {
	public static void main(String[] args) throws JSONException {
//		String s1 = InterfaceUtil.sendGet("http://localhost:8084/jeesite/api/users", "");
//		String s2 = InterfaceUtil.sendGet("http://localhost:8084/jeesite/api/users", "id=7f98a63178f2492c917a561bab1430ed");
//		System.out.println(s1);
//		System.out.println(s2);
		
//		String s1 = InterfaceUtil.sendPost("http://localhost:8084/jeesite/api/users", "name=zhangsan&company.id=4e298b6fd90d4220aca605590824e790&office.id=a98f8f6bf0b44ecf982252b655e8d055&loginName=zhangsan&password=zhangsan");
//		String s1 = InterfaceUtil.sendPost("http://localhost:8084/jeesite/api/users", "name=xiaoming&company.id=4e298b6fd90d4220aca605590824e790&office.id=a98f8f6bf0b44ecf982252b655e8d055&loginName=xiaoming&password=xiaoming");
		JSONObject json = new JSONObject();
		json.put("token", "123456");
		json.put("name", "kehu");
		//json.put("businessType", "apply");
		JSONArray list = new JSONArray();
		list.put(new JSONObject("{\"carNum\":\"12345\"}"));
		list.put(new JSONObject("{\"carNum\":\"abcdef\"}"));
//		list.put(new JSONObject(new TRepayPlan("123")));
//		list.put(new JSONObject(new TRepayPlan("456789")));
		json.put("cars", list);
//		System.out.println(json);
//		String s1 = InterfaceUtil.sendPostJson("http://localhost:8686/drools-kie/createPlansW.html", "{\"payAmount\":0,\"amount\":10000,\"token\":\"123456\",\"unPayPeriod\":0,\"periodType\":\"2\",\"payType\":\"1\",\"businessType\":\"apply\",\"loanDate\":\"2016-07-06\",\"loanPeriod\":45,\"loanRate\":8,\"periodUnit\":\"3\"}");
//		String s1 = InterfaceUtil.sendPostJson("http://localhost:8686/drools-kie/risk/calculatePersonalGrade.html", "{\"spouseCompanyAddress\":null,\"houseHold\":null,\"position\":\"5\",\"companyAddress\":\"1324\",\"sex\":\"1\",\"phone\":\"123\",\"householdAddress\":\"123456\",\"spousePosition\":null,\"spouseCompanyName\":null,\"score\":0,\"monthlyIncome\":\"5000\",\"maritalStatus\":\"2\",\"education\":\"1\",\"id\":0,\"time\":null,\"token\":\"123456\",\"age\":27,\"name\":\"个人客户0526-1\",\"credit\":null,\"corpId\":null,\"carCount\":null,\"householdType\":null,\"spousePhone\":null,\"maxCreditCard\":null,\"spouseEducation\":null,\"spouseCompanyTelephone\":null,\"identityCard\":\"421182198902111111\",\"companyName\":\"65132456\",\"companyScale\":null,\"professional\":null,\"companyTelephone\":\"123456\",\"spouseProfessional\":null,\"spouseName\":null,\"spouseMonthlyIncome\":null,\"spouseCompanyType\":null,\"companyNature\":\"2\",\"spouseCompanyNature\":null,\"email\":\"111912@qq.com\",\"depositAndInvestment\":null,\"address\":\"132456\",\"spouseTime\":null,\"carProperty\":null,\"houseCount\":null,\"creditCardCount\":null,\"perMonthlyPay\":null,\"companyType\":null,\"houseProperty\":null,\"telephone\":\"123\"}");
//		String s1 = InterfaceUtil.sendPostJson("http://localhost:8080/fpd/api/wOrders", "{\"customerName\":\"11111\",\"productname\":\"\u5965\u8feaA6\",\"loanPeriod\":\"12\",\"loanRate\":\"0.08\",\"loanRateType\":\"\u6708\",\"payDay\":\"26\",\"periodType\":2,\"status\":1,\"id\":\"59\",\"loanId\":\"2\",\"agencyId\":\"49073ac8b237456da601a8eb3cf09b47\",\"employee\":{\"name\":\"\u90d1\u5c11\u79cb\",\"wtypeId\":\"15\",\"cardType\":\"1\",\"cardNum\":\"441481188188121128\",\"sex\":\"0\",\"birthday\":\"1994-12-15\",\"education\":\"5\",\"mobile\":\"18300112121\",\"telephone\":\"3323233\",\"email\":\"13232323@qq.com\",\"householdRegAddr\":\"2_143_162_12312\",\"registerStatus\":\"0\",\"currentLiveAddress\":\"20_3467_3481_\u6c5f\u590f\",\"resideTime\":\"2016-07-12\",\"resideStatus\":\"1\",\"marriedInfo\":\"1\",\"houseHold\":\"4\",\"driveNo\":\"201612122\",\"workUnit\":\"\u5e7f\u4e1c\u5e7f\u5dde\",\"bizCategory\":\"1\",\"natureOfUnit\":\"1\",\"unitScale\":\"2\",\"unitAddress\":\"20_3467_3481_\u6c5f\u590f\u5317\u4e00\u8def\",\"post\":\"0\",\"workTelephone\":\"22223\",\"entryTime\":\"2016-07-01\",\"techTitle\":\"0\",\"monthIncome\":\"2000000\",\"creditCardCount\":\"3\",\"maxCreditCard\":\"1000000\",\"depositAndInvest\":\"3000000\",\"mate\":{\"pouseName\":\"\u90d1\u5c11\u79cb\u5979\u8001\u5a46\",\"cardType\":\"0\",\"pouseCardNum\":\"441444144414414149\",\"birthday\":\"2016-07-13\",\"pouseEducation\":\"4\",\"phone\":\"13322222222\",\"politicalStatus\":\"19_3258_3279_\",\"address\":\"19_3258_3279_\u6c5f\u590f\u5317\u4e00\u8def\",\"liveTime\":\"2016-07-13\",\"units\":\"\u5e7f\u5ddexxxxxx\",\"industry\":\"6\",\"unitNature\":\"cc\u64e6\",\"unitSize\":\"2\",\"unitsAddress\":\"19_3258_3279_\",\"position\":\"0\",\"unitsPhone\":\"3332322\",\"monthlyIncome\":\"5000000\",\"unitTime\":\"2016-07-08\",\"political\":\"0\"},\"cars\":[{\"carCode\":\"1231312\",\"carNum\":\"3\",\"engineNum\":\"3\",\"registTime\":\"0000-00-00\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0\",\"evalprice\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\",\"loanDueTime\":\"0000-00-00\",\"carPur\":\"0\"},{\"carCode\":\"13213123\",\"carNum\":\"1\",\"engineNum\":\"1\",\"registTime\":\"2016-07-13\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0\",\"evalprice\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\",\"loanDueTime\":\"0000-00-00\",\"carPur\":\"0\"},{\"carCode\":\"13123213\",\"carNum\":\"1\",\"engineNum\":\"1\",\"registTime\":\"0000-00-00\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0\",\"evalprice\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\",\"loanDueTime\":\"0000-00-00\",\"carPur\":\"0\"}],\"houses\":[{\"ownershipNo\":\"11232131\",\"commonStatus\":\"1\",\"owner\":\"1\",\"programme\":\"\",\"location\":\"1\",\"purpose\":\"\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0000-00-00\",\"buildTime\":\"0000-00-00\",\"usages\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\"},{\"ownershipNo\":\"2\",\"commonStatus\":\"2\",\"owner\":\"2\",\"programme\":\"2\",\"location\":\"2\",\"purpose\":\"\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0000-00-00\",\"buildTime\":\"0000-00-00\",\"usages\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\"},{\"ownershipNo\":\"5\",\"commonStatus\":\"5\",\"owner\":\"5\",\"programme\":\"\",\"location\":\"5\",\"purpose\":\"\",\"buyTime\":\"0000-00-00\",\"buyprice\":\"0000-00-00\",\"buildTime\":\"0000-00-00\",\"usages\":\"0\",\"isMortgage\":\"0\",\"mortgagee\":\"\",\"mortgageAmount\":\"0\",\"mortgageBalance\":\"0\"}],\"contracts\":[{\"name\":\"\u5567\u5567\u5567\",\"sex\":\"1\",\"relation\":\"0\",\"contactWay\":\"13312313213\"},{\"name\":\"zz\",\"sex\":\"1\",\"relation\":\"0\",\"contactWay\":\"13312313213\"},{\"name\":\"yy\",\"sex\":\"1\",\"relation\":\"0\",\"contactWay\":\"131221212121\"}]},\"catId\":2,\"uid\":\"15\",\"userType\":2,\"loanFee\":\"20.00\",\"repayWay\":4,\"applyAmount\":\"150000.00\",\"rstatus\":1,\"product\":{\"name\":\"\u5965\u8feaA6\",\"type\":1,\"payType\":1,\"rate\":\"0.08\",\"wtypeId\":\"2\"},\"gatheringBank\":\"\u4e2d\u56fd\u5de5\u5546\u94f6\u884c\",\"gatheringName\":\"\u90d1\u5c11\u79cb\",\"gatheringNumber\":\"6985115445668884323\"}" );
		//String s1 = InterfaceUtil.sendPostJson("http://192.168.1.245:8686/drools-kie/risk/calculatePersonalCredit.html", json.toString() );
		String s1 = InterfaceUtil.sendPost("http://192.168.1.183:99/index/test/getCatId.html", "");
		System.out.println(s1);
	}
}
