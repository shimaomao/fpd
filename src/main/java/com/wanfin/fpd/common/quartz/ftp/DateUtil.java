/**
 * Project Name:dataExchange
 * File Name:DateUtil.java
 * Package Name:cn.jrjzx.dataExchange.common
 * Date:2016年6月28日上午11:04:49
 * Copyright (c) 2016, hengwei.xiao@jrjzx.cn All Rights Reserved.
 *
*/

package com.wanfin.fpd.common.quartz.ftp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:DateUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年6月28日 上午11:04:49 <br/>
 * @author   xiaohengwei
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class DateUtil {

	private static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	
	private static SimpleDateFormat formatter = new java.text.SimpleDateFormat(FORMAT_YYYYMMDD);
	public static String getCurrentYearMonthDay() {
		Date currentTime = new java.util.Date();// 得到当前系统时间
		String yearAndDayString = formatter.format(currentTime); // 将日期时间格式化
		return yearAndDayString;
	}	
	
	public static void main(String[] args) {
		System.out.println(getCurrentYearMonthDay());
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean areSameDay(Date dateA,Date dateB) {
	    Calendar calDateA = Calendar.getInstance();
	    calDateA.setTime(dateA);

	    Calendar calDateB = Calendar.getInstance();
	    calDateB.setTime(dateB);

	    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getYesterdayYearMonthDay() {
		Calendar calendar = Calendar.getInstance();// 得到当前系统时间
		calendar.add(Calendar.DAY_OF_MONTH, -1);//昨天时间
		String yearAndDayString = formatter.format(calendar.getTime());//将日期时间格式化
		return yearAndDayString;
	}
	
	public static Date getDateByStr(String str){	
		try {
			return  formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**  
    * 计算两个日期之间相差的天数  
    * @param smdate 较小的时间 
    * @param bdate  较大的时间 
    * @return 相差天数 
    * @throws ParseException  
    */    
   public static int daysBetween(Date smdate,Date bdate) throws ParseException    
   {    
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
       smdate=sdf.parse(sdf.format(smdate));  
       bdate=sdf.parse(sdf.format(bdate));  
       Calendar cal = Calendar.getInstance();    
       cal.setTime(smdate);    
       long time1 = cal.getTimeInMillis();                 
       cal.setTime(bdate);    
       long time2 = cal.getTimeInMillis();         
       long between_days=(time2-time1)/(1000*3600*24);  
           
      return Integer.parseInt(String.valueOf(between_days));           
   }    
     
	/** 
	*字符串的日期格式的计算 
	*/  
   public static int daysBetween(String smdate, String bdate) throws ParseException{  
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
       Calendar cal = Calendar.getInstance();    
       cal.setTime(sdf.parse(smdate));    
       long time1 = cal.getTimeInMillis();                 
       cal.setTime(sdf.parse(bdate));    
       long time2 = cal.getTimeInMillis();         
       long between_days=(time2-time1)/(1000*3600*24);  
           
      return Integer.parseInt(String.valueOf(between_days));     
   }  
	
	
	
}

