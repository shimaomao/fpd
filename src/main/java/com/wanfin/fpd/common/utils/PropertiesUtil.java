package com.wanfin.fpd.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {
	
	/**
	 * 读取属性文件
	 * @param path 文件完整路径
	 * @return
	 */
	public static Properties load(String path) {
		Properties p = new Properties();
		try {
			InputStream is = new FileInputStream(path);
			if(path.endsWith(".xml")){
				p.loadFromXML(is);
			}else{
				p.load(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**拷贝对象的属性
	 * 目标对象有的属性才去把源数据取出来,拷贝到目标属性。
	 * @param tager目标对象
	 * @param souce原数据 对象
	 * @param felet 目标对象需要过滤的属性集合"id,name"
	 * @return 返回拷贝后的目标对象
	 */
	
	public static Object copyProperties(Object souce, Object tager, String felet) {
		if (souce == null || tager == null){return null;}
		if (felet == null){felet="";}	
		Method[] methods = tager.getClass().getMethods();
		for (Method method : methods) {
			// 取得所有的set方法 并过滤不需要拷贝的属性
			if (method.getName().startsWith("set")&& felet.toLowerCase().indexOf(method.getName().replaceFirst("set", "").toLowerCase()) <= -1) {
				try {
					// 获得数据源的方法名称
					String souceMethName = method.getName().replaceFirst("s","g");
					// 获得数据原的方法
					Method soumethod = souce.getClass().getMethod(souceMethName,null);
					if (soumethod != null) {
						// 取得原数据的值
						Object obj = soumethod.invoke(souce,null);
						if (obj != null) {
							// 把原数据拷贝到目标数据
							method.invoke(tager, obj);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return tager;
	}
	
	/**
	 * 
	 * @param dBegin开始时间
	 * @param dEnd结束时间
	 * @return获取年月时间段集合
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {		
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dBegin);
		boolean bContinue = true;
		while (bContinue) {
			cal.add(Calendar.MONTH, 1);
			if (dEnd.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(dEnd);
		return lDate;
	}
	
	
		/**
		 * 根据贷款利率的类型计算利率
		 * (默认是按月付息,包括：【按月付息，到期还本;等额本金;等额本息】)
		 * @return period+"_"+rateVal;期限 _利率/合同状态
		 * private Float loanRate;//贷款利率
		 * loanRateType; //贷款利率的类型(计算公式：日息：本金*日利率   月息：本金*月利率/30   年息：本金*年利率/360);不考虑2月份、含有31天、和闰年情况
		 * loanPeriod;  //贷款期限
		 * periodType;   //期限类型(年、月、日)
		 */
	public static String[] getPeriodAndRateVal(Float loanRate,String loanRateType,Integer loanPeriod,String periodType,Integer status) {		
		
		Float rateVal=0f;//根据贷款期限的类型转换后的利率
		if(loanRateType!=null && !loanRateType.isEmpty()){
			   if("日".equals(loanRateType)){
				 //日利率转为年    
			       rateVal = loanRate*360;
		    	  
		       }else if("月".equals(loanRateType)){
		    	 //月利率转为年    
		          rateVal =loanRate*12;
		       }else{
		    	   //年利率
				   rateVal=loanRate;
		       }
		    }
		
		String period="";//拼接后的贷款期限 10天 ,1个月，3个月，6个月，1年，1年以上
		if(periodType!=null && !periodType.isEmpty()){
		    //如果贷款期限的类型是年   
			if("年".equals(periodType)){
	    	    //期限转换
	    	    if(loanPeriod==1){
	    	    	period = "12个月";
			    }else{
			    	period = loanPeriod*12+"个月";
			    }
	    	    
		    }else if("月".equals(periodType)){
		    	//期限转换
	    	    /*if(loanPeriod==1){
	    	    	period = "1个月";
			    }else if(loanPeriod>1 && loanPeriod<4){
			    	period = "3个月";
			    }else if(loanPeriod>3 && loanPeriod<7){
			    	period = "6个月";
			    }else if(loanPeriod>6 && loanPeriod<13){
			    	period = "12个月";
			    }else{*/
			    	period = loanPeriod+"个月";
			    //}
		    }else if("日".equals(periodType)){
		    	//期限转换
	    	    if(loanPeriod<11){
	    	    	period = "10天";
			    }else if(loanPeriod>10 && loanPeriod<31){
			    	period = "1个月";
			    }else if(loanPeriod>30 && loanPeriod<91){
			    	period = "3个月";
			    }else if(loanPeriod>90 && loanPeriod<181){
			    	period = "6个月";
			    }else if(loanPeriod>180 && loanPeriod<361){
			    	period = "12个月";
			    }else{
			    	period = "1年以上";
			    }
		    }
		}
		String restatus="";//合同状态
		if(status==6){
			restatus="已结清";
		}else if(status==8){
			restatus="逾期未结清";
		}else{
			restatus="未结清";
		}
		 return new String[]{period,rateVal.toString(),restatus};
	}
	/**
	 * 返回两个时间的间隔天数 ed-sd
	 * @param sd 被减数
	 * @param ed 减数
	 * @return int 类型的间隔天数
	 */
	public static int getDays(Date sd,Date ed){
		 return (int) ((ed.getTime()-sd.getTime())/(3600*24*1000));
	} 

}
