/**  
 * @Project fpd 
 * @Title CCAdapterUtil.java
 * @Package com.wanfin.fpd.modules.wind.adapter
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月3日 上午10:42:32 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Description [[_xxx_]] CCAdapterUtil类
 * @author Chenh
 * @date 2016年6月3日 上午10:42:32 
 */
public class IAdapterUtil {
		/**
	 * @Description str转JsonArray
	 * @author Chenh 
	 * @throws JSONException 
	 * @date 2016年6月2日 下午3:50:47  
	 */
	public static JSONArray toJson(String jsonStr) throws JSONException{
		JSONArray jay = null;
		if((jsonStr).startsWith("{")){
			jay = new JSONArray();
			jay.put(new JSONObject(jsonStr));
		}else if((jsonStr).startsWith("[")){
			jay = new JSONArray(jsonStr);
		}
		return jay;
	}
}
