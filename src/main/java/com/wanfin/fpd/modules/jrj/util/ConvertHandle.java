package com.wanfin.fpd.modules.jrj.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjInterestsChang;
import com.wanfin.fpd.modules.jrj.entity.JrjOldBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCost;
import com.wanfin.fpd.modules.jrj.entity.JrjOldProfit;
import com.wanfin.fpd.modules.jrj.entity.JrjOldReport;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjInterestsChangService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.jrj.vo.JrjBalanceSheepVo;
import com.wanfin.fpd.modules.jrj.vo.JrjCashFlowVo;
import com.wanfin.fpd.modules.jrj.vo.JrjInterestsChangVo;
import com.wanfin.fpd.modules.jrj.vo.JrjOldCashFlowVo;
import com.wanfin.fpd.modules.jrj.vo.JrjOldCostVo;
import com.wanfin.fpd.modules.jrj.vo.JrjOldReportVo;
import com.wanfin.fpd.modules.jrj.vo.JrjProfitVo;


public class ConvertHandle {
	
	
	/**
	 * 根据vo转换成资产负债表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjBalanceSheep voConvertBalanceSheep(JrjBalanceSheep entity,List<JrjBalanceSheepVo> vos) throws NoSuchMethodException, SecurityException{		
		Class<JrjBalanceSheep> clazz = JrjBalanceSheep.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjBalanceSheepVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3 ){ //1 2 3 4 5行跳过
				
				}else if(i>=4 && i<=41){
					if(i==4){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);
						Method method2 = clazz.getMethod("setRowThree", String.class);
						Method method3 = clazz.getMethod("setRowFour", String.class);					
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());
							method2.invoke(entity, vo.getSeven());
							method3.invoke(entity, vo.getEight());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Three", String.class);
						Method method3 = clazz.getMethod("setRow"+num+"Four", String.class);					
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());
							method2.invoke(entity, vo.getSeven());
							method3.invoke(entity, vo.getEight());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();							
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	/**
	 * 根据vo转换成利润表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjProfit voConvertProfit(JrjProfit entity,List<JrjProfitVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjProfit> clazz = JrjProfit.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjProfitVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3){ //1 2 3 4 5行跳过
				
				}else if(i>=4 && i<=32){
					if(i==4){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);											
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	

	/**
	 * 根据vo转换成现金流量表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjCashFlow voConvertCashFlow(JrjCashFlow entity,List<JrjCashFlowVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjCashFlow> clazz = JrjCashFlow.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjCashFlowVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3){ //1 2 3 4 5行跳过
				
				}else if(i>=4 && i<=44){
					if(i==4){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);											
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	/**
	 * 根据vo转换成现金流量表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjInterestsChang voConvertJrjInterestsChang(JrjInterestsChang entity,List<JrjInterestsChangVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjInterestsChang> clazz = JrjInterestsChang.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjInterestsChangVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3 || i==4){ //1 2 3 4 5行跳过
				
				}else if(i>=5 && i<=28){
					
					if(i == 5){
						Method method1 = clazz.getMethod("setRowOne", String.class);
						Method method2 = clazz.getMethod("setRowTwo", String.class);
						Method method3 = clazz.getMethod("setRowThree", String.class);
						Method method4 = clazz.getMethod("setRowFour", String.class);
						Method method5 = clazz.getMethod("setRowFive", String.class);
						Method method6 = clazz.getMethod("setRowSix", String.class);
						Method method7 = clazz.getMethod("setRowSeven", String.class);
						Method method8 = clazz.getMethod("setRowEight", String.class);
						Method method9 = clazz.getMethod("setRowNine", String.class);
						Method method10 = clazz.getMethod("setRowTen", String.class);
						Method method11 = clazz.getMethod("setRowEleven", String.class);
						Method method12 = clazz.getMethod("setRowTwelve", String.class);
						Method method13 = clazz.getMethod("setRowThirteen", String.class);
						Method method14 = clazz.getMethod("setRowFourteen", String.class);
						Method method15 = clazz.getMethod("setRowFifteen", String.class);
						Method method16 = clazz.getMethod("setRowSixteen", String.class);
						
						
						try {
							method1.invoke(entity, vo.getTwo());
							method2.invoke(entity, vo.getThree());
							method3.invoke(entity, vo.getFour());
							method4.invoke(entity, vo.getFive());
							method5.invoke(entity, vo.getSix());
							method6.invoke(entity, vo.getSeven());
							method7.invoke(entity, vo.getEight());
							method8.invoke(entity, vo.getNine());
							method9.invoke(entity, vo.getTen());
							method10.invoke(entity, vo.getEleven());
							method11.invoke(entity, vo.getTwelve());
							method12.invoke(entity, vo.getThirteen());
							method13.invoke(entity, vo.getFourteen());
							method14.invoke(entity, vo.getFifteen());
							method15.invoke(entity, vo.getSixteen());
							method16.invoke(entity, vo.getSeventeen());
							
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						
						Method method1 = clazz.getMethod("setRow"+num+"One", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method3 = clazz.getMethod("setRow"+num+"Three", String.class);
						Method method4 = clazz.getMethod("setRow"+num+"Four", String.class);
						Method method5 = clazz.getMethod("setRow"+num+"Five", String.class);
						Method method6 = clazz.getMethod("setRow"+num+"Six", String.class);
						Method method7 = clazz.getMethod("setRow"+num+"Seven", String.class);
						Method method8 = clazz.getMethod("setRow"+num+"Eight", String.class);
						Method method9 = clazz.getMethod("setRow"+num+"Nine", String.class);
						Method method10 = clazz.getMethod("setRow"+num+"Ten", String.class);
						Method method11 = clazz.getMethod("setRow"+num+"Eleven", String.class);
						Method method12 = clazz.getMethod("setRow"+num+"Twelve", String.class);
						Method method13 = clazz.getMethod("setRow"+num+"Thirteen", String.class);
						Method method14 = clazz.getMethod("setRow"+num+"Fourteen", String.class);
						Method method15 = clazz.getMethod("setRow"+num+"Fifteen", String.class);
						Method method16 = clazz.getMethod("setRow"+num+"Sixteen", String.class);
						
						try {
							method1.invoke(entity, vo.getTwo());
							method2.invoke(entity, vo.getThree());
							method3.invoke(entity, vo.getFour());
							method4.invoke(entity, vo.getFive());
							method5.invoke(entity, vo.getSix());
							method6.invoke(entity, vo.getSeven());
							method7.invoke(entity, vo.getEight());
							method8.invoke(entity, vo.getNine());
							method9.invoke(entity, vo.getTen());
							method10.invoke(entity, vo.getEleven());
							method11.invoke(entity, vo.getTwelve());
							method12.invoke(entity, vo.getThirteen());
							method13.invoke(entity, vo.getFourteen());
							method14.invoke(entity, vo.getFifteen());
							method15.invoke(entity, vo.getSixteen());
							method16.invoke(entity, vo.getSeventeen());					
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	
	/**
	 * 旧报表
	 * 根据vo转换成利润表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjOldProfit voConvertOldProfit(JrjOldProfit entity,List<JrjProfitVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjOldProfit> clazz = JrjOldProfit.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjProfitVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3 ){ //1 2 3 4 5行跳过
				
				}else if(i>=4 && i<=33){
					if(i==4){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);											
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());						
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	/**
	 * 根据vo转换成资产负债表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjOldBalanceSheep voConvertOldBalanceSheep(JrjOldBalanceSheep entity,List<JrjBalanceSheepVo> vos) throws NoSuchMethodException, SecurityException{		
		Class<JrjOldBalanceSheep> clazz = JrjOldBalanceSheep.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjBalanceSheepVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3 || i==4 ){ //1 2 3 4 5行跳过
				
				}else if(i>=5 && i<=43){
					if(i==5){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);
						Method method2 = clazz.getMethod("setRowThree", String.class);
						Method method3 = clazz.getMethod("setRowFour", String.class);					
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());
							method2.invoke(entity, vo.getSeven());
							method3.invoke(entity, vo.getEight());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Three", String.class);
						Method method3 = clazz.getMethod("setRow"+num+"Four", String.class);					
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());
							method2.invoke(entity, vo.getSeven());
							method3.invoke(entity, vo.getEight());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();							
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	/**
	 * 根据vo转换成现金流量表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjOldCashFlow voConvertOldCashFlow(JrjOldCashFlow entity,List<JrjOldCashFlowVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjOldCashFlow> clazz = JrjOldCashFlow.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjOldCashFlowVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3 || i==4 || i==5){ //1 2 3 4 5行跳过
				
				}else if(i>=6 && i<=44){
					if(i==6){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);
						Method method2 = clazz.getMethod("setRowThree", String.class);
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());	
							method2.invoke(entity, vo.getFive());	
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Three", String.class);
						
						try {
							method.invoke(entity, vo.getThree());
							method1.invoke(entity, vo.getFour());
							method2.invoke(entity, vo.getFive());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	/**
	 * 根据vo转换成现金流量表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjOldCost voConvertOldCost(JrjOldCost entity,List<JrjOldCostVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjOldCost> clazz = JrjOldCost.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjOldCostVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 || i==3){ //1 2 3 4 5行跳过
				
				}else if(i>=4 && i<=25){
					if(i==4){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);
						Method method2 = clazz.getMethod("setRowThree", String.class);
						Method method3 = clazz.getMethod("setRowFour", String.class);
						try {
							method.invoke(entity, vo.getTwo());
							method1.invoke(entity, vo.getThree());	
							method2.invoke(entity, vo.getFive());	
							method3.invoke(entity, vo.getSix());	
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Three", String.class);
						Method method3 = clazz.getMethod("setRow"+num+"Four", String.class);
						
						try {
							method.invoke(entity, vo.getTwo());
							method1.invoke(entity, vo.getThree());
							method2.invoke(entity, vo.getFive());
							method3.invoke(entity, vo.getSix());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	/**
	 * 根据vo转换成现金流量表实体
	 * @param profit
	 * @param vos
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static JrjOldReport voConvertOldReport(JrjOldReport entity,List<JrjOldReportVo> vos) throws NoSuchMethodException, SecurityException{
		Class<JrjOldReport> clazz = JrjOldReport.class;
		int num = 0;
		if(vos != null && vos.size() > 0){
			for(int i=0;i<vos.size();i++){
				JrjOldReportVo vo = vos.get(i);
				if(i==0 || i==1 || i==2 ){ //1 2 3 4 5行跳过
				
				}else if(i>=3 && i<=36){
					if(i==3){
						Method method = clazz.getMethod("setRowOne", String.class);
						Method method1 = clazz.getMethod("setRowTwo", String.class);
						Method method2 = clazz.getMethod("setRowThree", String.class);
						Method method3 = clazz.getMethod("setRowFour", String.class);
						Method method4 = clazz.getMethod("setRowFive", String.class);
						try {
							method.invoke(entity, vo.getTwo());
							method1.invoke(entity, vo.getThree());	
							method2.invoke(entity, vo.getFour());	
							method3.invoke(entity, vo.getFive());
							method4.invoke(entity, vo.getSix());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						Method method = clazz.getMethod("setRow"+num+"One", String.class);
						Method method1 = clazz.getMethod("setRow"+num+"Two", String.class);
						Method method2 = clazz.getMethod("setRow"+num+"Three", String.class);
						Method method3 = clazz.getMethod("setRow"+num+"Four", String.class);
						Method method4 = clazz.getMethod("setRow"+num+"Five", String.class);
						try {
							method.invoke(entity, vo.getTwo());
							method1.invoke(entity, vo.getThree());
							method2.invoke(entity, vo.getFour());
							method3.invoke(entity, vo.getFive());
							method4.invoke(entity, vo.getSix());
							num++;
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}
				
			}
		}
		return entity;
	}
	
	
	
}
