/**  
 * @Project fpd 
 * @Title ICCAdapter.java
 * @Package com.wanfin.fpd.modules.wind.adapter
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月2日 上午10:32:51 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.adapter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * @Description [[_xxx_]] ICCAdapter类
 * @author Chenh
 * @date 2016年6月2日 上午10:32:51 
 */
public interface IAdapter<T> {
	/**
	 * @Description 转换器
	 * @author Chenh 
	 * @throws JSONException 
	 * @date 2016年6月2日 下午1:06:32  
	 */
	public T init(String data) throws JSONException;
	
	/**
	 * @Description 转换器
	 * @author Chenh 
	 * @throws JSONException 
	 * @date 2016年6月2日 下午1:06:32  
	 */
	public List<T> init(JSONArray jarray) throws JSONException;
}