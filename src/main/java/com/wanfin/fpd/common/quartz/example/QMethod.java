/**  
 * @Project fpd 
 * @Title QMethod.java
 * @Package com.wanfin.fpd.common.quartz.example
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月31日 上午9:36:02 
 * @version V1.0   
 */ 
package com.wanfin.fpd.common.quartz.example;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @Description [[_xxx_]] QMethod类
 * @author Chenh
 * @date 2016年5月31日 上午9:36:02 
 */
public class QMethod {
    /** 
     * 定时任务，执行方法 
     * */  
    public void execute(){  
        String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.ENGLISH).format(System.currentTimeMillis());  
        System.out.println("time:"+time);  
    }  
}