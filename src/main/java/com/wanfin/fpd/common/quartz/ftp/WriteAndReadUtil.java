/**
 * Project Name:dataExchange
 * File Name:WriteAndReadUtil.java
 * Package Name:cn.jrjzx.dataExchange.common
 * Date:2016年6月28日上午10:44:54
 * Copyright (c) 2016, hengwei.xiao@jrjzx.cn All Rights Reserved.
 *
*/

package com.wanfin.fpd.common.quartz.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * ClassName:WriteAndReadUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年6月28日 上午10:44:54 <br/>
 * @author   xiaohengwei
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class WriteAndReadUtil {

	private static Logger logger = Logger.getLogger(WriteAndReadUtil.class);
	//文件名
	//private static final String fileName = "LDA"+DateUtil.getCurrentYearMonthDay()+".dat";
	//文件所在目录
	private static String filepath = "c://sif//data//";
	

	/**
	 * 
	 * ReadDataFromDatFile:读取文件.以行为单位读取文件内容，一次读一整行 <br/>
	 *
	 * @author xiaohengwei
	 * @param path
	 * @return 
	 * @since JDK 1.7
	 */
	public static Map<String, Object> ReadDataFromDatFile(String path){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList();
	    File file = new File(path);
        BufferedReader reader = null;
        map.put("code", -1);
        try {
            
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
         
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
        
                list.add(tempString);
            }
            map.put("code", 1);
            map.put("result", list);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return map;
	}
	
	/**
	 * 
	 * ReadDataFromDatFile:读取文件.以行为单位读取文件内容，一次读一整行 <br/>
	 *
	 * @author xiaohengwei
	 * @param path
	 * @return 
	 * @since JDK 1.7
	 */
	public static List<String> readDataFromFile(String path){
		List list = new ArrayList();
	    File file = new File(path);
	    InputStreamReader isr = null;
        BufferedReader reader = null;
      
        try {
        	isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String tempString = null;
            
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {  
                list.add(tempString);
            }          
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
	}
}

