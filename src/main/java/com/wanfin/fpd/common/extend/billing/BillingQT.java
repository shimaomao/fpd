package com.wanfin.fpd.common.extend.billing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.core.billing.BillingEngine;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
/**
 * 收费计时业务更新计划
 * @author Chenh
 */
public class BillingQT {
	@Autowired
	private BillingEngine engine;
	
	/** 
     * 定时任务，执行方法 
     * */  
    public void execute(){
    	System.out.println("————————————————————————————————————————————————————————————————————————————"); 
    	System.out.println("收费计时业务更新计划正在执行");
    	System.out.println("————————————————————————————————————————————————————————————————————————————"); 
//    	List<BiCollect> biCollects = engine.bi().getServering();
//    	for (BiCollect biCollect : biCollects) {
//    		if((BiSval.BiType.TIME).equals(biCollect.getRule().getType())){
//        		biCollect.setSurplusTime(biCollect.getSurplusTime()-1);
//            	engine.collect().save(biCollect);
//    		}else if((BiSval.BiType.NUM).equals(biCollect.getRule().getType())){}
//		}
    	System.out.println("————————————————————————————————————————————————————————————————————————————"); 
    	System.out.println("收费计时业务更新计划执行结束");
    	System.out.println("————————————————————————————————————————————————————————————————————————————");
    }
}
