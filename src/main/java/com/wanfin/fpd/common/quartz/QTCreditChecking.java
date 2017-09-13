/**  
 * @Project fpd 
 * @Title QTCreditChecking.java
 * @Package com.wanfin.fpd.common.quartz
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月31日 上午9:34:32 
 * @version V1.0   
 */ 
package com.wanfin.fpd.common.quartz;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.modules.wind.service.creditchecking.TCreditCheckingService;
/**
 * @Description [[_xxx_]] QTCreditChecking类
 * @author Chenh
 * @date 2016年5月31日 上午9:34:32 
 */
public class QTCreditChecking {
	
    /** 
     * 定时任务，执行方法 
     * */  
    public void execute(){  
    	String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.CHINESE).format(System.currentTimeMillis());  
    	System.out.println("——————————————————————————————————————————————————————————————————————————1——");
//		tCreditCheckingService.init();
    	System.out.println("——————————————————————————————————————————————————————————————————————————4——");  
        System.out.println("time:"+time+">>当前定时任务正在执行：具体用法详见-> B端-OA[Feature #1862]");  
    }
}
