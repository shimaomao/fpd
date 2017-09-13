package com.wanfin.fpd.common.quartz.ftp;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * 文件工具类
 * @author xiangwang
 *
 */
public class FileUtil {
	
	
	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	   public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             //delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	  }
	   
	 //删除文件夹
	 //param folderPath 文件夹完整绝对路径
	  public static void delFolder(String folderPath) {
	      try {
	         delAllFile(folderPath); //删除完里面所有内容
	         String filePath = folderPath;
	         filePath = filePath.toString();
	         java.io.File myFilePath = new java.io.File(filePath);
	         myFilePath.delete(); //删除空文件夹
	      } catch (Exception e) {
	        e.printStackTrace(); 
	      }
	  }	   
	   
	  /**
		 * 重新生成文件名
		 * @param fileName
		 * @return
		 */
		public static String  generateFileName(String fileName) {   
	        DateFormat format = new SimpleDateFormat("yyMMddHHmmss");   
	        String formatDate = format.format(new Date());  
	        int random = new Random().nextInt(10000);              
	        int position = fileName.lastIndexOf(".");   
	        String extension = fileName.substring(position);  		
	        return formatDate + random + extension;   
	    } 
		
		/**
		 * 创建文件夹路径
		 */
		public static String createDirPath(Long fileTypeId) {		
			String dirPath = "";
			if (fileTypeId == 1) {
				dirPath += "/zcfz";
			} else if (fileTypeId == 2) {
				dirPath += "/lrb";
			} else if (fileTypeId == 3) {
				dirPath += "/xjll";
			}
			return dirPath;
		}
		
		
		 /**
		 * 金额格式化
		 * @param strFlag
		 * @return
		 */
		 public static String formatMoney2(double dou) {		
			DecimalFormat fmt = new DecimalFormat("0.00");
			String outStr = null;		
			try {				
				outStr = fmt.format(dou);
			} catch (Exception e) {
			}
			return outStr;
		}  
}
	
	
	
	
	
	

