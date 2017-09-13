package com.wanfin.fpd.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumPojo {
  
	public static void main(String[] args) {  
        String no=getOrderNum("运保贷","运保贷17040001");  
        System.out.println("流水号"+'\n'+no);  
  
    }  
  /**
   * 生成单号---新的标号规则  产品名称+年份后两位+月份两位+四位流水
   * @param productName  产品名称
   * @param lastNo    当前最后一条编码
   * @return
   */
    public static String getOrderNum(String productName,String lastNo){  
    	    SimpleDateFormat format= new SimpleDateFormat("yyMM");  
            String date=format.format(new Date());  
    	    if(checkNum(productName,lastNo)){//不需要重新计数
                String temp=lastNo.substring(lastNo.length()-4, lastNo.length());  
                if(Integer.parseInt(temp)>=1&&Integer.parseInt(temp)<9999){  
                    temp=String.valueOf(Integer.parseInt(temp)+1);  
                }  
                switch (temp.length()) {  
                case 1:  
                    temp="000"+temp;  
                    break;  
                case 2:  
                    temp="00"+temp;  
                    break;  
                case 3:  
                    temp="0"+temp;  
                    break;  
                default:  
                    break;  
                }  
                lastNo=productName+date+temp;  
    	    }else{//重新计数
    	    	 lastNo=productName+date+"0001";
    	    }
        return lastNo;  
          
    }
    /**
     * 验证符合单号流水编码是否重新计数
     * @param productName  产品名称
     * @param lastNo    当前最后一条编码
     * @return false 重新计数    true不需要重新计数
     */
      public static Boolean checkNum(String productName,String lastNo){  
            if(StringUtils.isNotBlank(lastNo) && lastNo.indexOf(productName)!=-1){//包含产品名称----lastNo可能为空
            	 SimpleDateFormat format= new SimpleDateFormat("yyMM");  
                 String date=format.format(new Date());
                 String yymm=lastNo.substring(lastNo.length()-8, lastNo.length()-4); 
                 if(date.equals(yymm)){//校验是否属于同一年月  不是要重新计数
                	 return true;
                 }else{
                	 return false;
                 }
            }else{
            	return false;
            }
      }
}
