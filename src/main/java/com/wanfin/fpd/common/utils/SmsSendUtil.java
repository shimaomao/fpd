package com.wanfin.fpd.common.utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 *
 * @author songchao
 * @since 2015-04-03
 */

public class SmsSendUtil {

    //查账户信息的http地址
    private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";
    //智能匹配模版发送接口的http地址(单条)
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    //智能匹配模版发送接口的http地址(批量)
    private static String URI_BATCH_SMS = "https://sms.yunpian.com/v2/sms/batch_send.json";
    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
    //发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";
    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    
    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws java.io.IOException
     */
    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
     * 智能匹配模版接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return 
     * @return json格式字符串
     * @throws IOException
     * @throws JSONException 
     */
    public static String sendSms(String text, String mobile) throws IOException, JSONException {
    	Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", "74324bf301b919e6c9d19b18c7707eb2");
        params.put("text", text);
        params.put("mobile",mobile);
        String str = post(URI_SEND_SMS, params);
        JSONObject jsonobject = new JSONObject(str);
       
        //保存发送短信记录
        User user = UserUtils.getUser();
        Record record = new Record();
        record.set("content", text);
        record.set("date", new Date());
        record.set("mobile", mobile);
        record.set("return_str", str);
        record.set("create_by", user.getId());
        record.set("organ_id", user.getCompany().getId());
        Db.save("t_sms_record", record);
        
        return jsonobject.getString("code")+","+jsonobject.getString("msg");
    }

    
    /**
     * 智能匹配模版接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号(可多个，批量)
     * @return 
     * @return json格式字符串
     * @throws IOException
     * @throws JSONException 
     */
    public static String sendSmsBatch(String text, String mobile,String type) throws IOException, JSONException {
    	Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", "74324bf301b919e6c9d19b18c7707eb2");
        params.put("text", text);
        params.put("mobile", mobile);
        String str = post(URI_BATCH_SMS, params);
        JSONObject jsonobject = new JSONObject(str);
        System.out.println(jsonobject);
        JSONArray jsonarray =  jsonobject.getJSONArray("data");    
        //保存发送短信记录
        User user = UserUtils.getUser();
        for (int i = 0; i < jsonarray.length(); i++) {
        	 JSONObject jsonItem = jsonarray.getJSONObject(i);
        	 Record record = new Record();
	         record.set("content", text);
	         record.set("type", type);
	         record.set("date", new Date());
	         record.set("mobile", jsonItem.get("mobile"));
	         record.set("return_str", jsonItem+"");
	         record.set("create_by", user.getId());
	         record.set("organ_id", user.getCompany().getId());
	         Db.save("t_sms_record", record);
		}
        return jsonobject.getString("total_count");
    }


    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 通过接口发送语音验证码
     * @param apikey apikey
     * @param mobile 接收的手机号
     * @param code   验证码
     * @return
     */
    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    
    
    
    public static void main(String[] args) throws IOException, URISyntaxException, JSONException {

    	
        //修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后获取
       // String apikey = "74324bf301b919e6c9d19b18c7707eb2";
        //修改为您要发送的手机号
        //String mobile = URLEncoder.encode("15989067522",ENCODING);

        /**************** 使用智能匹配模版接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【创世跬科】 你的验证码  97675 (万众)";
        //发短信调用示例
//        System.out.println(SmsSendUtil.sendSms(text, "15989067522,13825180207"));
         // System.out.println(SmsSendUtil.sendSmsBatch(text, "15989067522"));



    }
}
