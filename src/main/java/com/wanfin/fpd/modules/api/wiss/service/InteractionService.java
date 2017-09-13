package com.wanfin.fpd.modules.api.wiss.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.utils.KeystoreDeal;
import com.wanfin.fpd.modules.api.wiss.dao.InteractionDao;
import com.wanfin.fpd.modules.api.wiss.entity.Interaction;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackHeader;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Service("interactionService")
@Transactional(readOnly = true)
public class InteractionService extends CrudService<InteractionDao, Interaction> {
	
	/**
	 * 组装参数并提交请求到易联接口
	 * @param tardeId 接口码（交易码）
	 * @param reqBody 提交body部分的参数，因各个body结构不同，故采用net.sf.json.JSONObject格式
	 * @return PayecoBackParams 返回为空则为异常
	 */
	public PayecoBackParams getPayecoRequestByParams(String tardeId,JSONObject reqBody){
		Map<String, String> reqMap = new LinkedHashMap<String, String>();
		try{
			reqMap = composinngPayecoApiParameters(tardeId, reqBody);
			
			return getPayecoApiContent(reqMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 组装对接易联接口参数
	 * @param tardeId 接口码（交易码）
	 * @param reqBody 提交body部分的参数，因各个body结构不同，故采用net.sf.json.JSONObject格式
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private Map<String, String> composinngPayecoApiParameters(String tardeId,JSONObject reqBody) throws UnsupportedEncodingException{
		//组织通讯的请求参数
        Map<String, String> reqMap = new LinkedHashMap<String, String>();
        
        //组织请求报文数据  -- data内容   { header: {...}, body: {...} }
        //--组织请求data header
        JSONObject reqHeader = new JSONObject();
        reqHeader.put("tradeId", tardeId);
        reqHeader.put("pvdId", Global.getApiConfig("payeco.pvdId"));
        reqHeader.put("reqTime", DateUtils.getDate("yyyyMMddHHmmss"));
        
        //--拼装data, { header: {...}, body: {...} }
        JSONObject reqData = new JSONObject();
        reqData.put("header", reqHeader);
        reqData.put("body", reqBody);
        
        //--生成请求的data数据（字符串）
        String reqDataStr = reqData.toString();
        System.out.println("请求data：" + reqDataStr);
        
        //生成签名
        //String reqSign = KeystoreDeal.getApiSign(reqDataStr);
        String reqSign = KeystoreDeal.getPlatformSign(reqDataStr);
        System.out.println("请求sign：" + reqSign);
        
        reqMap.put("tradeId", tardeId);
        reqMap.put("version", Global.getApiConfig("wfw.api.version"));
        reqMap.put("pvdId", Global.getApiConfig("payeco.pvdId"));
        reqMap.put("data", URLEncoder.encode(reqDataStr, "utf-8"));
        reqMap.put("sign", URLEncoder.encode(reqSign, "utf-8"));
        
		return reqMap;
	}
	
	/**
	 * 通过map形式和易联交互数据并将结果初步转换成PayecoBackParams形式
	 * 请求易联接口后将返回信息除了data中除body(各个body结构不同)外全部解析为实体类内容
	 * @param reqMap 生成通讯请求数据
	 * @return 返回结果的实体类型（body因各个body结构不同而维持json内容）
	 */
	private PayecoBackParams getPayecoApiContent(Map<String, String> reqMap) {
		PayecoBackParams payecoBackParams = null;
		//--生成通讯请求数据
        String reqStr = getReqStr(reqMap);
		
		//请求地址
        String url = Global.getApiConfig("payeco.api.url");
        System.out.println("请求地址：" + url);
        System.out.println("请求数据：" + reqStr);
		String resStr;
		try {
			resStr = post(url, reqStr, "utf-8");
			System.out.println("通讯响应数据：" + resStr);
			if(StringUtils.isBlank(resStr)){
				return null;
			}else{
				JSONObject jsonObject = JSONObject.fromObject(resStr);
				if(jsonObject != null){
					//payecoBackParams = (PayecoBackParams)JSONObject.toBean(jsonObject, PayecoBackParams.class);
					//如果自动转换没有获取到相关数据则通过json直接获取
					//if(payecoBackParams == null || StringUtils.isAnyBlank(payecoBackParams.getData(),payecoBackParams.getSign())){
					payecoBackParams = new PayecoBackParams();
					payecoBackParams.setData(jsonObject.getString("data"));
					payecoBackParams.setSign(jsonObject.getString("sign"));
					JSONObject jsonData = jsonObject.getJSONObject("data");
					payecoBackParams.setJsonHeader(jsonData.getJSONObject("header"));
					payecoBackParams.setJsonBody(jsonData.getJSONObject("body"));
					//验证签名
					if(StringUtils.isNotBlank(payecoBackParams.getData()) && StringUtils.isNotBlank(payecoBackParams.getSign())){
						//if(KeystoreDeal.checkApiSign(payecoBackParams.getData(), payecoBackParams.getSign())){
						if(KeystoreDeal.checkApiSign(payecoBackParams.getData(), payecoBackParams.getSign())){
							System.out.println("签名验证成功！");
							
							//解析data中的header内容
							//String[] dateFormats = new String[] {"yyyyMMddHHmmss" };//"yyyy-MM-dd HH:mm:ss"
							String[] dateFormats = new String[] {"yyyyMMddHHmmss","yyyyMMdd","yyyy-MM-dd" };
							JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
							PayecoBackHeader header = (PayecoBackHeader)JSONObject.toBean(payecoBackParams.getJsonHeader(), PayecoBackHeader.class);
							payecoBackParams.setHeader(header);
						}else{
							System.err.println("签名验证失败！");
							payecoBackParams = null;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return payecoBackParams;
	}
	
	/**
	 * 将Map内容转换成普通get/post形式参数
	 * @param reqMap
	 * @return
	 */
	private String getReqStr(Map<String, String> reqMap) {
        StringBuffer buffReqStr = new StringBuffer();
        for (String key : reqMap.keySet()) {
            if (buffReqStr.length() > 0) {
                buffReqStr.append("&");
            }
            String value = reqMap.get(key);
            buffReqStr.append(key).append("=").append(value==null ? "" : value);
        }
        return buffReqStr.toString();
    }

	/**
	 * 发送请求
	 * @param strUrl
	 * @param reqStr
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String post(String strUrl, String reqStr, String encoding) throws Exception {

		int connectTimeout = 10; // 连接超时时间
		int readTimeout = 120; // 数据

		if (strUrl.startsWith("https://")) {
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
			javax.net.ssl.TrustManager tm = new MiTM();
			trustAllCerts[0] = tm;
			javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, null);
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}

		URL url = new URL(strUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setUseCaches(false);
		httpConn.setInstanceFollowRedirects(true);
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + encoding);

		httpConn.setConnectTimeout(1000 * connectTimeout);
		httpConn.setReadTimeout(1000 * readTimeout);
		httpConn.connect();
		DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
		out.write(reqStr.getBytes(encoding));
		out.flush();
		out.close();
		int status = httpConn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			System.err.println("发送请求失败，状态码" + status);
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), encoding));
		StringBuffer responseSb = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			responseSb.append(line.trim());
		}
		reader.close();
		return responseSb.toString();
	}

	public static class MiTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		/**
		 * 服务是否可信
		 *
		 * @param certs
		 *            证书
		 * @return 结果 true可信/false不可信
		 */
		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		/**
		 * 客户端是否可信
		 *
		 * @param certs
		 *            证书
		 * @return 结果 true可信/false不可信
		 */
		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		/**
		 * 检查服务是否可信
		 *
		 * @param certs
		 *            证书
		 * @param authType
		 *            authType
		 * @throws java.security.cert.CertificateException
		 *             证书异常
		 */
		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		/**
		 * 检查客户端是否可信
		 *
		 * @param certs
		 *            证书
		 * @param authType
		 *            authType
		 * @throws java.security.cert.CertificateException
		 *             证书异常
		 */
		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
	
	public static void main(String args[]){
		String reqDataStr = "测试验签";
		String reqSign = KeystoreDeal.getPlatformSign(reqDataStr);
		System.out.println("价签："+reqSign);
		System.out.println(KeystoreDeal.checkServiceSign(reqDataStr, reqSign));
	}
}
