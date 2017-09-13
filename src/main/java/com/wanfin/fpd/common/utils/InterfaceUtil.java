package com.wanfin.fpd.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InterfaceUtil {
	/** 
     * 向指定URL发送GET方法的请求 
     *  
     * @param url 
     *            发送请求的URL 
     * @param param 
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。 
     * @return URL所代表远程资源的响应 
     */  
    public static String sendGet(String url, String param) {  
    	StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            if(param != null && param != ""){
            	urlNameString = url + (url.indexOf("?") > 0 ? "&" : "?") + param;
            }
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
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
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
    /**  
     * 向指定URL发送POST方法的请求  
     *   
     * @param url  
     *            发送请求的URL  
     * @param param  
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。  
     * @return URL所代表远程资源的响应  
     */  
    public static String sendPost(String url, String param) {  
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
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
            	result.append(line);
            }  
        } catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);  
            result = new StringBuffer("");
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
            	result = new StringBuffer("");
                ex.printStackTrace();  
            }  
        }  
        return result.toString();  
    }
    
    public static String sendPostJson(String surl, String json){
		URL url;
		StringBuffer sb;
		DataOutputStream out;
		BufferedReader reader;
		HttpURLConnection conn;
		try {
			url = new URL(surl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestMethod("POST");// 提交模式
			conn.setRequestProperty("Content-Length", json.getBytes().length + "");
			conn.setConnectTimeout(100000);// 连接超时单位毫秒 //
			conn.setReadTimeout(200000);// 读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.connect();
			
			out = new DataOutputStream(conn.getOutputStream());
			out.write(json.getBytes());
			out.flush();
			out.close();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			conn.disconnect();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
    public static String sendPostClient(String surl, Entity<Object> entity){
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target(surl);
        Response response = target.request().post(entity);
        System.out.println(response.getStatus());
        System.out.println(response.getEntity());
        
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
		return surl;
	}
    
    public static void main(String[] args) {
    	AccountVo accountVo = new AccountVo();
    	accountVo.setAuthId("622472e2b07d11e691176cf049b682c5");
    	accountVo.setMoney(BigDecimal.valueOf(50.00));
    	
//    	
//
//    	p.getUser().put("id", "7bdf4d22419147c48cd95a4c5f851e43");
//    	p.getUser().put("organId", null);
//    	JSONObject pjson = JSONObject.fromObject(p);
//    	String json = pjson.toString().replaceAll("\"", "\\\"");
    	//String sends = InterfaceUtil.sendPostJson("http://192.168.1.205:8080/services/bsAccount/outAccount/", "{\"authId\":\""+rel.getAnuthenuserId()+"\",\"money\":\""+tLending.getAmount()+"\"}");
    //	String iii = InterfaceUtil.sendPostJson("http://192.168.1.164:8080/services/bsAccount/outAccount/", "{\"authId\":\"622472e2b07d11e691176cf049b682c5\",\"money\":\"500\"}");
    //	System.out.println(iii);
//    	String iii2 = InterfaceUtil.sendPostJson("http://192.168.1.184:8088/services/pro/pupdateStatus", "{\"product\":{\"name\":\"陈建鹏\",\"id\":\"142f7468b89b45a88c4851bad1b464b0\",\"type\":\"1\",\"status\":\"new\"},\"user\":{\"organId\":null,\"id\":\"7bdf4d22419147c48cd95a4c5f851e43\"}}");
//    	System.out.println(iii2);
//    	String iii = InterfaceUtil.sendPostJson("http://192.168.1.184:8088/services/pro/psave", "{\"product\":{\"name\":\"陈建鹏\",\"id\":\"陈建鹏\",\"type\":\"1\",\"status\":\"new\"},\"user\":{\"organId\":null,\"id\":\"7bdf4d22419147c48cd95a4c5f851e43\"}}");

    	
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://192.168.1.164:8080/services/bsAccount/outAccount");
        Response response = target.request().post(Entity.entity(accountVo, MediaType.APPLICATION_JSON));
        System.out.println(response.getStatus());
        System.out.println(response.getEntity());
        
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    	
    	Entity e = Entity.entity(accountVo, MediaType.APPLICATION_JSON);
    	sendPostClient("http://192.168.1.164:8080/services/bsAccount/outAccount", e);
    	
    }
}
