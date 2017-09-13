package com.wanfin.fpd.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialUtils {
    private static final String PFIX = "WZ";
    public static final String FORMAT_YMDH = "yyyyMMddhh";
	private static final String FORMAT_ALL = "yyyyMMddhhmmss";
	private static long orderNum = 0l;  
	private static String count = "000";
	private static String dateValue = "10000000";

	/****************************************************************************************
	 * 产生序号
	 */
	public synchronized static String getSerialNo() {
		return getSerialNo(PFIX, FORMAT_ALL);
	}
	public synchronized static String getSerialNo(String pfix) {
		return getSerialNo(pfix, FORMAT_ALL);
	}
	public synchronized static String getSerialNo(String pfix, String FORMAT_ALL) {
		long No = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ALL);
		String nowdate = sdf.format(new Date());
		No = Long.parseLong(nowdate);
		if (!(String.valueOf(No)).equals(dateValue)) {
			count = "000";
			dateValue = String.valueOf(No);
		}
		String num = String.valueOf(No);
		num += getNo(count);
		num = pfix + num;
		return num;
	}

	/**
	 * 返回当天的订单数+1
	 */
	public static String getNo(String s) {
		String rs = s;
		int i = Integer.parseInt(rs);
		i += 1;
		rs = "" + i;
		for (int j = rs.length(); j < 3; j++) {
			rs = "0" + rs;
		}
		count = rs;
		return rs;
	}
	
    /**************************************************************************************** 
     * 生成订单编号 
     * @return 
     */  
    public static synchronized String getOrderNo() {  
		return getOrderNo(PFIX);
    }
	public static synchronized String getOrderNo(String pfix) { 
		return getOrderNo(pfix, FORMAT_ALL);
	}
	public static synchronized String getOrderNo(String pfix, String FORMAT_ALL) {
		String str = "";
		if((FORMAT_ALL != null) && (FORMAT_ALL != "") && (FORMAT_ALL != " ")){
	        str = new SimpleDateFormat(FORMAT_ALL).format(new Date()); 
		}else{
			str = "0";
		}
		
        if((dateValue == null) || (!dateValue.equals(str))){  
        	dateValue = str;  
            orderNum  = 0l;  
        }
        
        orderNum ++;  
        long orderNo = Long.parseLong((dateValue)) * 10000;  
        orderNo += orderNum;;  
        return pfix+(orderNo+"");  
    }  

	public static void main(String[] args) {
		System.out.println(SerialUtils.getSerialNo(StringUtils.upperCase("Product"), "yyyMMddhh"));
		System.out.println(getSerialNo());
		System.out.println(getOrderNo());
		System.out.println(SerialUtils.getOrderNo("admin1000", null));
		System.out.println(SerialUtils.getOrderNo("admin1000", null));
		System.out.println(SerialUtils.getOrderNo("admin1000", null));
		System.out.println(SerialUtils.getOrderNo("admin2000", null));
		System.out.println(SerialUtils.getOrderNo("admin2000", null));
		System.out.println(SerialUtils.getOrderNo("admin2000", null));
	}
}
