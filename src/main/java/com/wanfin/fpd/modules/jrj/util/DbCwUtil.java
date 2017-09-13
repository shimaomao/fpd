package com.wanfin.fpd.modules.jrj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

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


/**
 * 担保财务报表工具
 * @author xzt
 *
 */
public class DbCwUtil {
	
	/**
	 * 解析excle
	 * @param filePath 文件路径
	 * @param fixLine 固定列数
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static Map<String,Object> read(InputStream stream,String fileName) throws IOException, NoSuchMethodException, SecurityException {
		Map<String,Object> map = new HashMap<String,Object>();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}	
		wb.setForceFormulaRecalculation(true);
		int length = wb.getNumberOfSheets();
		System.out.println(length);
		if(length > 0){
			for(int k=0;k<length;k++){
				String sheetName = wb.getSheetName(k);
				Sheet sheet = wb.getSheetAt(k);
                 //解析资产负债				
				if("资产负债表".equals(sheetName.trim())){
					List<JrjBalanceSheepVo> vos = new ArrayList<JrjBalanceSheepVo>();
					for (Row row : sheet) {
						JrjBalanceSheepVo vo = new JrjBalanceSheepVo();
						String result = "";
						//System.out.println("------"+row.getLastCellNum());
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							switch (cell.getCellType()) {
						    case Cell.CELL_TYPE_STRING:
						    	
						    	result = cell.getRichStringCellValue().getString()+" ";
						    	System.out.println("Cell : " + i + "\tresult : "  +result);
						        break;
						    case Cell.CELL_TYPE_NUMERIC:
						        if (DateUtil.isCellDateFormatted(cell)) {
						        	result = cell.getDateCellValue()+"";
						        } else {
						        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	result = big.toString();	
						        	if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
						    		}			        			        	
						        }
						        break;
						    case Cell.CELL_TYPE_BOOLEAN:			       
						    	result = cell.getBooleanCellValue()+"";
						        break;	
						    case Cell.CELL_TYPE_FORMULA:  	
					            try {  
					            /* 
					             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
					             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
					             */  
					             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
					                Date date = cell.getDateCellValue();  
					                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
					                break;  
					             } else {  
					            	 //result = String.valueOf(cell.getNumericCellValue());
					            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	 result = big.toString();	
						        	 if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
							    	 }	
					             }  
					             } catch (IllegalStateException e) {  
					            	 result = String.valueOf(cell.getRichStringCellValue());  
					             }  
					            break;  
						    default:
						    	result = "";
						    	break;
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ //第四列
								vo.setFour(result);
							}else if(i == 4){ //第五列
								vo.setFive(result);
							}else if(i == 5){ //第6列
								vo.setSix(result);								
							}else if(i == 6){ //第7列
								vo.setSeven(result);								
							}else if(i == 7){ //第8列
								vo.setEight(result);
								vos.add(vo);
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjBalanceSheep entity = new JrjBalanceSheep();
						entity = ConvertHandle.voConvertBalanceSheep(entity,vos);						
						map.put("sheep", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}
					
				}else if("利润表".equals(sheetName.trim())){
					
					List<JrjProfitVo> vos = new ArrayList<JrjProfitVo>();
					for (Row row : sheet) {
						JrjProfitVo vo = new JrjProfitVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							switch (cell.getCellType()) {
						    case Cell.CELL_TYPE_STRING:
						    	result = cell.getRichStringCellValue().getString()+" ";
						        break;
						    case Cell.CELL_TYPE_NUMERIC:
						        if (DateUtil.isCellDateFormatted(cell)) {
						        	result = cell.getDateCellValue()+"";
						        } else {
						        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	result = big.toString();	
						        	if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
						    		}			        			        	
						        }
						        break;
						    case Cell.CELL_TYPE_BOOLEAN:			       
						    	result = cell.getBooleanCellValue()+"";
						        break;	
						    case Cell.CELL_TYPE_FORMULA:  	
					            try {  
					            /* 
					             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
					             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
					             */  
					             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
					                Date date = cell.getDateCellValue();  
					                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
					                break;  
					             } else {  
					            	 //result = String.valueOf(cell.getNumericCellValue());
					            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	 result = big.toString();	
						        	 if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
							    	 }	
					             }  
					             } catch (IllegalStateException e) {  
					            	 result = String.valueOf(cell.getRichStringCellValue());  
					             }  
					            break;  
						    default:
						    	result = "";
						    	break;
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ //第四列
								vo.setFour(result);
								vos.add(vo);
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjProfit entity = new JrjProfit();
						entity = ConvertHandle.voConvertProfit(entity,vos);					
						map.put("profit", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}				
				}else if("现金流量表".equals(sheetName.trim())){
					
					List<JrjCashFlowVo> vos = new ArrayList<JrjCashFlowVo>();
					for (Row row : sheet) {
						JrjCashFlowVo vo = new JrjCashFlowVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							switch (cell.getCellType()) {
						    case Cell.CELL_TYPE_STRING:
						    	result = cell.getRichStringCellValue().getString()+" ";
						        break;
						    case Cell.CELL_TYPE_NUMERIC:
						        if (DateUtil.isCellDateFormatted(cell)) {
						        	result = cell.getDateCellValue()+"";
						        } else {
						        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	result = big.toString();	
						        	if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
						    		}			        			        	
						        }
						        break;
						    case Cell.CELL_TYPE_BOOLEAN:			       
						    	result = cell.getBooleanCellValue()+"";
						        break;	
						    case Cell.CELL_TYPE_FORMULA:  	
					            try {  
					            /* 
					             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
					             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
					             */  
					             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
					                Date date = cell.getDateCellValue();  
					                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
					                break;  
					             } else {  
					            	 //result = String.valueOf(cell.getNumericCellValue());
					            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	 result = big.toString();	
						        	 if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
							    	 }	
					             }  
					             } catch (IllegalStateException e) {  
					            	 result = String.valueOf(cell.getRichStringCellValue());  
					             }  
					            break;  
						    default:
						    	result = "";
						    	break;
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ //第四列
								vo.setFour(result);
								vos.add(vo);
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjCashFlow entity = new JrjCashFlow();
						entity = ConvertHandle.voConvertCashFlow(entity,vos);					
						map.put("flow", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}			
					
				}else if("所有者权益变动表".equals(sheetName.trim())){
					
					List<JrjInterestsChangVo> vos = new ArrayList<JrjInterestsChangVo>();
					for (Row row : sheet) {
						JrjInterestsChangVo vo = new JrjInterestsChangVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							switch (cell.getCellType()) {
						    case Cell.CELL_TYPE_STRING:
						    	result = cell.getRichStringCellValue().getString()+" ";
						        break;
						    case Cell.CELL_TYPE_NUMERIC:
						        if (DateUtil.isCellDateFormatted(cell)) {
						        	result = cell.getDateCellValue()+"";
						        } else {
						        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	result = big.toString();	
						        	if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
						    		}			        			        	
						        }
						        break;
						    case Cell.CELL_TYPE_BOOLEAN:			       
						    	result = cell.getBooleanCellValue()+"";
						        break;	
						    case Cell.CELL_TYPE_FORMULA:  	
					            try {  
					            /* 
					             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
					             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
					             */  
					             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
					                Date date = cell.getDateCellValue();  
					                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
					                break;  
					             } else {  
					            	 //result = String.valueOf(cell.getNumericCellValue());
					            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	 result = big.toString();	
						        	 if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
							    	 }	
					             }  
					             } catch (IllegalStateException e) {  
					            	 result = String.valueOf(cell.getRichStringCellValue());  
					             }  
					            break;  
						    default:
						    	result = "";
						    	break;
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ 
								vo.setFour(result);
							}else if(i == 4){ 
								vo.setFive(result);
							}else if(i == 5){ 
								vo.setSix(result);
							}else if(i == 6){ 
								vo.setSeven(result);
							}else if(i == 7){ 
								vo.setEight(result);
							}else if(i == 8){ 
								vo.setNine(result);
							}else if(i == 9){ 
								vo.setTen(result);
							}else if(i == 10){
								vo.setEleven(result);
							}else if(i == 11){ 
								vo.setTwelve(result);
							}else if(i == 12){ 
								vo.setThirteen(result);
							}else if(i == 13){ 
								vo.setFourteen(result);
							}else if(i == 14){ 
								vo.setFifteen(result);
							}else if(i == 15){ 
								vo.setSixteen(result);
							}else if(i == 16){ 
								vo.setSeventeen(result);
								vos.add(vo);
							}				            
						}						
					}					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjInterestsChang entity = new JrjInterestsChang();
						entity = ConvertHandle.voConvertJrjInterestsChang(entity,vos);					
						map.put("chang", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}	
					
				}
				
			}
			
		}
		return map;	
	
	}

	
	/**
	 * 解析excle
	 * @param filePath 文件路径
	 * @param fixLine 固定列数
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static Map<String,Object> readOldExcel(InputStream stream,String fileName) throws IOException, NoSuchMethodException, SecurityException {
		Map<String,Object> map = new HashMap<String,Object>();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}	
		wb.setForceFormulaRecalculation(true);
		int length = wb.getNumberOfSheets();
		System.out.println(length);
		if(length > 0){
			for(int k=0;k<length;k++){
				String sheetName = wb.getSheetName(k);
				Sheet sheet = wb.getSheetAt(k);
                 //解析资产负债				
				if("资产负债表".equals(sheetName.trim())){
					List<JrjBalanceSheepVo> vos = new ArrayList<JrjBalanceSheepVo>();
					for (Row row : sheet) {
						JrjBalanceSheepVo vo = new JrjBalanceSheepVo();
						String result = "";
						System.out.println("------"+row.getLastCellNum());
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							if(cell != null){
								switch (cell.getCellType()) {
							    case Cell.CELL_TYPE_STRING:
							    	result = cell.getRichStringCellValue().getString()+" ";
							        break;
							    case Cell.CELL_TYPE_NUMERIC:
							        if (DateUtil.isCellDateFormatted(cell)) {
							        	result = cell.getDateCellValue()+"";
							        } else {
							        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	result = big.toString();	
							        	if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
							    		}			        			        	
							        }
							        break;
							    case Cell.CELL_TYPE_BOOLEAN:			       
							    	result = cell.getBooleanCellValue()+"";
							        break;	
							    case Cell.CELL_TYPE_FORMULA:  	
						            try {  
						            /* 
						             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
						             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
						             */  
						             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
						                Date date = cell.getDateCellValue();  
						                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
						                break;  
						             } else {  
						            	 //result = String.valueOf(cell.getNumericCellValue());
						            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	 result = big.toString();	
							        	 if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
								    	 }	
						             }  
						             } catch (IllegalStateException e) {  
						            	 result = String.valueOf(cell.getRichStringCellValue());  
						             }  
						            break;  
							    default:
							    	result = "";
							    	break;
								}
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ //第四列
								vo.setFour(result);
							}else if(i == 4){ //第五列
								vo.setFive(result);
							}else if(i == 5){ //第6列
								vo.setSix(result);								
							}else if(i == 6){ //第7列
								vo.setSeven(result);								
							}else if(i == 7){ //第8列
								vo.setEight(result);
								vos.add(vo);
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjOldBalanceSheep entity = new JrjOldBalanceSheep();
						entity = ConvertHandle.voConvertOldBalanceSheep(entity,vos);						
						map.put("sheep", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}
					
				}else if("损益表".equals(sheetName.trim())){
					
					List<JrjProfitVo> vos = new ArrayList<JrjProfitVo>();
					for (Row row : sheet) {
						JrjProfitVo vo = new JrjProfitVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							if(cell != null){
								switch (cell.getCellType()) {
							    case Cell.CELL_TYPE_STRING:
							    	result = cell.getRichStringCellValue().getString()+" ";
							        break;
							    case Cell.CELL_TYPE_NUMERIC:
							        if (DateUtil.isCellDateFormatted(cell)) {
							        	result = cell.getDateCellValue()+"";
							        } else {
							        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	result = big.toString();	
							        	if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
							    		}			        			        	
							        }
							        break;
							    case Cell.CELL_TYPE_BOOLEAN:			       
							    	result = cell.getBooleanCellValue()+"";
							        break;	
							    case Cell.CELL_TYPE_FORMULA:  	
						            try {  
						            /* 
						             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
						             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
						             */  
						             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
						                Date date = cell.getDateCellValue();  
						                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
						                break;  
						             } else {  
						            	 //result = String.valueOf(cell.getNumericCellValue());
						            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	 result = big.toString();	
							        	 if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
								    	 }	
						             }  
						             } catch (IllegalStateException e) {  
						            	 result = String.valueOf(cell.getRichStringCellValue());  
						             }  
						            break;  
							    default:
							    	result = "";
							    	break;
								}
								
								/**
								 * 赋值到相应字段
								 */
					            if(i == 0){ //第一列
					            	vo.setOne(result);
								}else if(i == 1){ //第二列
									vo.setTwo(result);
								}else if(i == 2){ //第三列
									vo.setThree(result);
								}else if(i == 3){ //第四列
									vo.setFour(result);
									vos.add(vo);
								}
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjOldProfit entity = new JrjOldProfit();
						entity = ConvertHandle.voConvertOldProfit(entity,vos);					
						map.put("profit", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}				
				}else if("现金流量表".equals(sheetName.trim())){
					
					List<JrjOldCashFlowVo> vos = new ArrayList<JrjOldCashFlowVo>();
					for (Row row : sheet) {
						JrjOldCashFlowVo vo = new JrjOldCashFlowVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							switch (cell.getCellType()) {
						    case Cell.CELL_TYPE_STRING:
						    	result = cell.getRichStringCellValue().getString()+" ";
						        break;
						    case Cell.CELL_TYPE_NUMERIC:
						        if (DateUtil.isCellDateFormatted(cell)) {
						        	result = cell.getDateCellValue()+"";
						        } else {
						        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	result = big.toString();	
						        	if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
						    		}			        			        	
						        }
						        break;
						    case Cell.CELL_TYPE_BOOLEAN:			       
						    	result = cell.getBooleanCellValue()+"";
						        break;	
						    case Cell.CELL_TYPE_FORMULA:  	
					            try {  
					            /* 
					             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
					             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
					             */  
					             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
					                Date date = cell.getDateCellValue();  
					                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
					                break;  
					             } else {  
					            	 //result = String.valueOf(cell.getNumericCellValue());
					            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
						        	 result = big.toString();	
						        	 if(result.contains(".")){
						    			DecimalFormat df = new DecimalFormat("0.00");
						    	    	result = df.format(big);	
							    	 }	
					             }  
					             } catch (IllegalStateException e) {  
					            	 result = String.valueOf(cell.getRichStringCellValue());  
					             }  
					            break;  
						    default:
						    	result = "";
						    	break;
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ //第四列
								vo.setFour(result);
							}else if(i == 4){ //第四列
								vo.setFive(result);
								vos.add(vo);
							}
						}						
					}
					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						JrjOldCashFlow entity = new JrjOldCashFlow();
						entity = ConvertHandle.voConvertOldCashFlow(entity,vos);					
						map.put("flow", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}			
					
				}else if("费用表".equals(sheetName.trim())){
					
					List<JrjOldCostVo> vos = new ArrayList<JrjOldCostVo>();
					for (Row row : sheet) {
						JrjOldCostVo vo = new JrjOldCostVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							if(cell != null){
								switch (cell.getCellType()) {
							    case Cell.CELL_TYPE_STRING:
							    	result = cell.getRichStringCellValue().getString()+" ";
							        break;
							    case Cell.CELL_TYPE_NUMERIC:
							        if (DateUtil.isCellDateFormatted(cell)) {
							        	result = cell.getDateCellValue()+"";
							        } else {
							        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	result = big.toString();	
							        	if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
							    		}			        			        	
							        }
							        break;
							    case Cell.CELL_TYPE_BOOLEAN:			       
							    	result = cell.getBooleanCellValue()+"";
							        break;	
							    case Cell.CELL_TYPE_FORMULA:  	
						            try {  
						            /* 
						             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
						             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
						             */  
						             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
						                Date date = cell.getDateCellValue();  
						                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
						                break;  
						             } else {  
						            	 //result = String.valueOf(cell.getNumericCellValue());
						            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	 result = big.toString();	
							        	 if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
								    	 }	
						             }  
						             } catch (IllegalStateException e) {  
						            	 result = String.valueOf(cell.getRichStringCellValue());  
						             }  
						            break;  
							    default:
							    	result = "";
							    	break;
								}
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ 
								vo.setFour(result);
							}else if(i == 4){ 
								vo.setFive(result);
							}else if(i == 5){ 
								vo.setSix(result);
								vos.add(vo);
							}			            
						}						
					}					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjOldCost entity = new JrjOldCost();
						entity = ConvertHandle.voConvertOldCost(entity,vos);					
						map.put("cost", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}	
					
				}else if("报表说明(对外)".equals(sheetName.trim())){
					
					List<JrjOldReportVo> vos = new ArrayList<JrjOldReportVo>();
					for (Row row : sheet) {
						JrjOldReportVo vo = new JrjOldReportVo();
						String result = "";
						
						for(int i=0;i<row.getLastCellNum();i++){
							Cell cell = row.getCell(i);
							if(cell != null){
								switch (cell.getCellType()) {
							    case Cell.CELL_TYPE_STRING:
							    	result = cell.getRichStringCellValue().getString()+" ";
							        break;
							    case Cell.CELL_TYPE_NUMERIC:
							        if (DateUtil.isCellDateFormatted(cell)) {
							        	result = cell.getDateCellValue()+"";
							        } else {
							        	BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	result = big.toString();	
							        	if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
							    		}			        			        	
							        }
							        break;
							    case Cell.CELL_TYPE_BOOLEAN:			       
							    	result = cell.getBooleanCellValue()+"";
							        break;	
							    case Cell.CELL_TYPE_FORMULA:  	
						            try {  
						            /* 
						             * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell 
						             * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
						             */  
						             if (HSSFDateUtil.isCellDateFormatted(cell)) {  
						                Date date = cell.getDateCellValue();  
						                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) +"-" + date.getDate();  
						                break;  
						             } else {  
						            	 //result = String.valueOf(cell.getNumericCellValue());
						            	 BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
							        	 result = big.toString();	
							        	 if(result.contains(".")){
							    			DecimalFormat df = new DecimalFormat("0.00");
							    	    	result = df.format(big);	
								    	 }	
						             }  
						             } catch (IllegalStateException e) {  
						            	 result = String.valueOf(cell.getRichStringCellValue());  
						             }  
						            break;  
							    default:
							    	result = "";
							    	break;
								}
							}
							
							/**
							 * 赋值到相应字段
							 */
				            if(i == 0){ //第一列
				            	vo.setOne(result);
							}else if(i == 1){ //第二列
								vo.setTwo(result);
							}else if(i == 2){ //第三列
								vo.setThree(result);
							}else if(i == 3){ 
								vo.setFour(result);
							}else if(i == 4){ 
								vo.setFive(result);
							}else if(i == 5){ 
								vo.setSix(result);
								vos.add(vo);
							}			            
						}						
					}					
					if(vos != null & vos.size() > 0){
						//根据List<vo> 对实体字段赋值   convertHandle
						//profit = ConvertHandle.voConvertProfit(profit, vos);
						JrjOldReport entity = new JrjOldReport();
						entity = ConvertHandle.voConvertOldReport(entity,vos);					
						map.put("report", entity);
					}
					
					if (stream != null) {
						stream.close();
						stream = null;
					}	
					
				}
				
			}
			
		}
		return map;	
	
	}
	
	
}
