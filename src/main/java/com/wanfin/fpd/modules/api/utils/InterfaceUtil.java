package com.wanfin.fpd.modules.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wanfin.fpd.common.utils.JsonOUtils;
import org.apache.commons.lang.StringUtils;

import com.wanfin.fpd.modules.api.utils.test.SignEntity;

import net.sf.json.JSONObject;

/**
 * 处理和贝通的接口交互
 * @author user
 *
 */
public class InterfaceUtil {
	
	/**
	 * 对贝通返回内容进行两次encode处理
	 * @param str 需要加密的内容
	 * @return 加密后内容，如果加密失败返回null;
	 */
	public static String doubleEncode(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		
		try {
			return URLEncoder.encode(URLEncoder.encode(str,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("两次解密失败："+str);
			return null;
		}
	}
	
	/**
	 * 对贝通返回内容进行两次decode处理
	 * @param str 需要解密的内容
	 * @return 解密后内容，如果解密失败返回null;
	 */
	public static String doubleDecode(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		
		try {
			return URLDecoder.decode(URLDecoder.decode(str, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("两次解密失败："+str);
			return null;
		}
	}
	
	/**
	 * 将json内容转换成实体
	 * @param json json内容
	 * @param beanClass 实体内容
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object jsonToObject(String json, java.lang.Class beanClass){
		if(StringUtils.isBlank(json)){
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, beanClass);
	}
	
	/**
	 * 向指定URL发送POST方法的请求
	 * @param url 发送请求的URL
	 * @param object 请求参数，会自动组装成data=value形式，value为object转json后两次encode编码处理
	 * @return
	 */
	public static String sendRequestByEntity(String url, Object object){
		if(StringUtils.isBlank(url)){
			return "";
		}
		
		StringBuilder param = new StringBuilder();
		if(object != null){
			try {
				param.append("data=");
				JSONObject jsonObject = JSONObject.fromObject(object);
				param.append(URLEncoder.encode(URLEncoder.encode(jsonObject.toString(),"utf-8"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		System.out.println("sendPost : param->"+param.toString());
		return sendPost(url, param.toString());
	}
	
	/**  
     * 向指定URL发送POST方法的请求  
     *   
     * @param url 发送请求的URL  
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。  
     * @return URL所代表远程资源的响应  
     */
	public static String sendPost(String url, String param) {
		System.out.println("sendPost : url->"+url);
		System.out.println("sendPost : param->"+param);
    	
    	StringBuffer result = new StringBuffer();
        PrintWriter out = null;  
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
            	result.append(line);
            }
            System.out.println("sendPost : result->"+result.toString());
        } catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);  
            result.append("发送POST请求出现异常");
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result.toString(); 
	}
	
	/** 
     * 向指定URL发送GET方法的请求 
     *  
     * @param url 发送请求的URL 
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。 
     * @return URL 所代表远程资源的响应 
     */  
    public static String sendGet(String url, String param) {  
    	System.out.println("sendGet : url->"+url);
    	System.out.println("sendGet : param->"+param);
    	
    	StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            
            System.out.println("sendPost : result->"+result.toString());
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            result.append("发送GET请求出现异常");
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String secretKey = "fhahhgo-haohgio-ahgoi";
		String getSign = "http://101.200.36.138:8080/interface/orderloan/getSign.html";
		
		JSONObject paramJson = new JSONObject();
		paramJson.put("corpId","1207");
		paramJson.put("amount","1000");
		paramJson.put("bankowner","张三");
		paramJson.put("bankno","1111111111111111111");
		paramJson.put("idSeq","201708141522");//业务唯一标识
		paramJson.put("yearConversion","10");
		paramJson.put("limitTime","100");
		paramJson.put("secretKey",secretKey);//秘钥
		
		String param = paramJson.toString();
		System.out.println("发送原内容:"+param);
		param = "data="+URLEncoder.encode(URLEncoder.encode(param,"utf-8"),"utf-8");
		System.out.println("发送两次encode加密后内容:"+param);
		
		String backContent = sendPost(getSign, param);
		System.out.println("接收原内容:"+backContent);
		backContent = URLDecoder.decode(URLDecoder.decode(backContent, "utf-8"), "utf-8");
		System.out.println("接收两次decode后内容:"+backContent);

		Map map = JsonOUtils.toMap(backContent);

		System.out.println("sign"+map.get("sign"));
		System.out.println("message"+map.get("message"));
		/*
		SignEntity signEntity = new SignEntity();
		signEntity.setCorpId("1207");
		signEntity.setAmount("1000");
		signEntity.setBankowner("张三");
		signEntity.setBankno("1111111111111111111");
		signEntity.setIdSeq("201708141522");
		signEntity.setYearConversion("10");
		signEntity.setLimitTime("100");
		signEntity.setSecretKey(secretKey);
		
		String backContent = sendRequestByEntity(getSign, signEntity9);
		System.out.println("接收原内容:"+backContent);
		backContent = URLDecoder.decode(URLDecoder.decode(backContent, "utf-8"), "utf-8");
		System.out.println("接收两次decode后内容:"+backContent);
		*/
		
		/*JSONObject paramJson = new JSONObject();
		paramJson.put("corpId","1207");
		paramJson.put("amount","1000");
		paramJson.put("bankowner","张三");
		paramJson.put("bankno","1111111111111111111");
		paramJson.put("idSeq","201708141522");//业务唯一标识
		paramJson.put("yearConversion","10");
		paramJson.put("limitTime","100");
		paramJson.put("secretKey",secretKey);//秘钥
		
		String param = paramJson.toString();
		System.out.println("json内容:"+param);
		SignEntity sign = (SignEntity) jsonToObject(param, SignEntity.class);
		System.out.println("sign内容:"+sign.toString());*/
	}

}
