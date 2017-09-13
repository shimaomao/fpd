package com.wanfin.fpd.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
/**
 * com.alibaba.fastjson.JSON
 * @deprecated  JSON工具类
 * @author wangwei
 * @param
 */
public class JsonAUtils {
	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * @param <T>
	 * 
	 * @param 主实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @return
	 */
	public static <T> List<T> toBean(String jsonString, Class<T> mainClass) {
		return (ArrayList<T>) JSON.parseArray(jsonString, mainClass);
	}
}