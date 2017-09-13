/**    
* @Project: fpd 
* @Title: YYYYY 
* @Package com.wanfin.fpd.modules.wind.service 
* @Description [[_XXXXX_]]文件
* @author Chenh 
* @date 2016年8月17日 下午5:43:44   
* @version V1.0.0   */ 
package com.wanfin.fpd.http.credit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**   
 * @author Chenh  
 * @date 2016年8月17日 下午5:43:44 
 * @Description [[_征信测试接口_]] TestCedit类
 * 征信测试接口
 *   
 */
public class TestCedit {
	public static void main(String[] args) throws Exception {// TODO
		SearchParams searchParams = new SearchParams();
		searchParams.setToken("test123");
		
		String surl = "http://192.168.1.206:8082/riskcontrol//query/v1//";//本地测试
//		String surl = "http://192.168.1.7:8080/riskcontrol//query/v1//";//本地测试
//		String surl = "http://192.168.1.7:8080/riskcontrol/query/v1/";//本地测试
		//String surl = "http://183.62.252.139:63009/riskcontrol/query/v1/";//服务器
		
		//String searchCode = "WZ2016071806415937317";//好信一鉴通 下边有多个子接口不需要测试
		
		String searchCode = "WZ2016071810234452400";//好信一鉴通--身份验证 ok
		searchParams.setIdType("0");//0:身份证
		searchParams.setIdNo("430104196606273061");
		searchParams.setName("卢院平");
		searchParams.setMobileNo("18126098065");
		TestRunner(searchParams, surl, searchCode);
		
//		String searchCode = "WZ2016071810301249807";//好信一鉴通--地址验证 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setAddress("深圳市宝安区福永镇福海工业区B栋公司宿舍405");
		
//		String searchCode = "WZ2016071810283590505";//好信一鉴通--工作单位验证 测试未覆盖
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setCompany("中国平安");
		
//		String searchCode = "WZ201607181024544001";//好信一鉴通--手机验证 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
		
//		String searchCode = "WZ2016071810254988302";//好信一鉴通--关系人验证 未覆盖
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setRefName("欧阳文");
//		searchParams.setRefMobileNo("13028807803");
		
//		String searchCode = "WZ2016071810265950004";//好信一鉴通--车辆验证 获取到异步结果 异步时缺8077接口
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setCarNo("苏A0VG18");
//		searchParams.setEngineNo("CW200608");;
//		searchParams.setCarFrameNum("LBELMBKC6CY264945");
//		searchParams.setNeedeQryDrvStatus("1");
		
//		String searchCode = "WZ2016071810263472603";//好信一鉴通--房产验证 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
		
		//String searchCode = "WZ2016071810290345306";//人脸识别 没有数据未测试
		
//		String searchCode = "WZ201607181031178408";//好信一鉴通--学历验证 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setEductionBckgrd("3");
		
//		String searchCode = "WZ2016072101593612700";//一鉴通银行卡鉴权V1.0  测试返回结果E000001
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setCardNo("6228480402564890018");
//		searchParams.setAuthType("A3");
		
//		String searchCode = "WZ2016071806260260203";//风险度提示V2.0  ok  会查出list值
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("440102198301114447");
//		searchParams.setName("米么联调");
		
//		String searchCode = "WZ2016071806234699502";//好信度-基础版(好信度数据V1.1版) ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("431126198706237015");
//		searchParams.setName("欧阳文");
//		searchParams.setMobileNo("13028807803");
//		searchParams.setCardNo("6228480402564890018");
		
//		String searchCode = "WZ2016071806283562504";//常贷客 ok 获取了两个数据，但是两个数据完全相同
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("410329197511045073");
//		searchParams.setName("张三");
		
//		String searchCode = "WZ2016071806335470406";//好信地址通
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setAddress("深圳市宝安区福永镇福海工业区B栋公司宿舍405");
		
//		String searchCode = "WZ2016071806383481616";//好信法院通-被执行人信息  未找到数据
////		searchParams.setIdType("0");//0:身份证
////		searchParams.setIdNo("430104196606273061");
////		searchParams.setName("卢院平");
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("410329197511045073");
//		searchParams.setName("张三");
		
		//String searchCode = "WZ2016071806433355718";//好信法院通-失信被执行信息 暂未测试
		//String searchCode = "WZ2016071806440418219";//好信法院通-法院公告信息检索 暂未测试
		//String searchCode = "WZ2016071806442938320";//好信法院通-法院公告信息查询 暂未测试
		//String searchCode = "WZ2016071806450521321";//好信法院通-裁判文书信息检索 暂未测试
		//String searchCode = "WZ2016071806454771022";//好信法院通-裁判文书信息查询 暂未测试
		
		
//		String searchCode = "WZ2016071806314876005";//好信欺诈度提示 测试超时 MSC8075-反欺诈风险度认证接口说明书V1.0
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
		
//		String searchCode = "WZ2016071806371547913";//好信银行卡咨询 MSC8158 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("431126198706237015");
//		searchParams.setName("欧阳文");
//		searchParams.setCardNo("6228480402564890018");
		
//		String searchCode = "WZ2016071806344032507";//好信信用轨迹 MSC8150  ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("431126198706237015");
//		searchParams.setName("欧阳文");
//		searchParams.setMobileNo("13028807803");
		
//		String searchCode = "WZ2016071806353261809";//好信驾驶分 MSC8112 查询不成功
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setCarNo("苏A0VG18");
		
//		String searchCode = "WZ2016071806350973608";//好信租车分 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setDriviliceNo("430104196606273061");
		
//		String searchCode = "WZ2016071806374036014";//好信银行卡评分 MSC8012
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
//		searchParams.setAccountNo("6228480402564890018");
		
//		String searchCode = "WZ2016071806364922312";//好信手机综合资讯 ok
//		searchParams.setIdType("0");//0:身份证
//		searchParams.setIdNo("430104196606273061");
//		searchParams.setName("卢院平");
//		searchParams.setMobileNo("18126098065");
		
		//String searchCode = "WZ2016071806355920010";//好信联络通
		//String searchCode = "WZ2016071806362689311";//好信时空地图
		//String searchCode = "WZ2016071806381118715";//好信工商通
		//String searchCode = "WZ2016072601422991900";//提交驾驶证比对任务数据
		//String searchCode = "WZ2016072601425382601";//提交驾驶证状态比对数据
		//String searchCode = "WZ2016072602005664100";//驾驶证比对查询数据
		
		
		//String json = "{\"token\":\"tokenContent\",\"qqNo\":\"1\",\"idType\":\"1\",\"weixinNo\":\"1\",\"courtNoticeId\":\"1\",\"ips\":\"1\",\"reasonCode\":\"1\",\"driviliceNo\":\"1\",\"cardNo\":\"1\",\"searchMethod\":\"1\",\"cardNos\":\"1\",\"moblieNos\":\"1\",\"name\":\"1\",\"weiboNo\":\"1\",\"yhdNo\":\"1\",\"queryId\":\"1\",\"needeQryDrvStatus\":\"1\",\"idNo\":\"1\",\"photo64\":\"1\",\"searchCode\":\"1\",\"areaCode\":\"1\",\"busiDesc\":\"1\",\"carFrameNum\":\"1\",\"searchTransNo\":\"1\",\"accountNo\":\"1\",\"clientId\":\"1\",\"ip\":\"1\",\"carNno\":\"1\",\"judgeDocId\":\"1\",\"engineNo\":\"1\",\"address\":\"1\",\"email\":\"1\",\"transId\":\"1\",\"company\":\"1\",\"jdno\":\"1\",\"taobaoNo\":\"1\",\"mobileNo\":\"1\",\"eductionBckgrd\":\"1\",\"amazonNo\":\"1\"}";
		/*
		//System.out.println("json:"+json);
		//发送请求
		String url = surl+searchCode;
		String response = sendJsonWithHttp(url,searchParamsJson);
		System.out.println( "返回值：" + response );
		*/

	}

	public static void TestRunner(SearchParams searchParams, String surl,
			String searchCode) throws Exception {
		String searchParamsJson = JSON.toJSONString(searchParams);
		System.out.println("传递参数(json)："+searchParamsJson);
		String url = surl+searchCode;
		String response = sendJsonWithHttp(url,searchParamsJson);
		System.out.println( "返回值：" + response );
	}

	public static String sendJsonWithHttp(String surl, String json) throws Exception {
		URL url = new URL(surl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		conn.setRequestMethod("POST");// 提交模式
		conn.setRequestProperty("Content-Length", json.getBytes().length + "");
		conn.setConnectTimeout(10*60*1000);// 连接超时单位毫秒 //
		conn.setReadTimeout(30*60*1000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(json.getBytes());
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		conn.disconnect();
		return sb.toString();
	}
}
