package com.wanfin.fpd.common.quartz;

import com.wanfin.fpd.modules.wish.utils.Rservice;

public class RiskControlQuartz {
	
	
	 public void execute(){
		 
		 Rservice.riskControl30_90("1");
		 //Rservice.riskControl("1");
		 
	 }
}
