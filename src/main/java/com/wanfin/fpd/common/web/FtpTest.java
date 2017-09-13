package com.wanfin.fpd.common.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wanfin.fpd.common.utils.CompressUtil;
import com.wanfin.fpd.common.utils.SWFTools;
import com.wanfin.fpd.common.utils.SftpUtils;

import net.lingala.zip4j.exception.ZipException;

public class FtpTest {	
	
	
	private static  String ftpserverIp = ""; //ftp服务器 
	private static  String userName = "";  //用户名
	private static  String password = "";  //密码	
	private static  int downPort; //ftp下载文件端口
	private static  int importPort; //ftp上传文件端口
	private static  String ftpdowndir = ""; //ftp下载文件目录
	private static  String ftptodir = ""; //ftp上传文件目录
	private static  String ftpfileend = ""; //ftp文件扩展名	
	private static  String ftptempdir = "";  //临时文件目录
	
	static {
		
		InputStream inputStream = SWFTools.class.getResourceAsStream("/ftpServer.properties");  
		Properties p = new Properties();  
	    try {  
	        p.load(inputStream);  
	        inputStream.close();  
	    } catch (IOException e1) {  
	        e1.printStackTrace();  
	    }  	    

	    ftpserverIp = p.getProperty("ftpserver");
	    userName = p.getProperty("ftpuser");
	    password = p.getProperty("ftppwd");	    
	    downPort = Integer.valueOf(p.getProperty("ftpdownport"));
	    importPort = Integer.valueOf(p.getProperty("ftpimporport"));
	    ftpdowndir = p.getProperty("ftpdowndir");
	    ftptodir = p.getProperty("ftptodir");
	    ftpfileend = p.getProperty("ftpfileend");
	    ftptempdir = p.getProperty("ftptempdir");
		
	}
	
	
	public static void main(String[] args) {
		
		
		  CompressUtil.zip("C:\\FTPtemp","20161102",false,"122");
		

		/*SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// FtpUtils myFtp = new FtpUtils();
		SftpUtils sftp = new SftpUtils();

			try {
				sftp.connect(ftpserverIp, importPort, userName, password);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			*//**
			 * @param directory
			 *            下载目录
			 * @param downloadFile
			 *            下载的文件
			 * @param saveFile
			 *            存在本地的路径
			 *//*			
			
			
			//压缩
		    //CompressUtil.zip("C:\\ftpTest", "123");
		   // CompressUtil.zip("C:\\ftpTest","20150319","123");
		    
		    try {
				SftpUtils.sftpUpload("/upload/91440101MA59AD7Y7K/20161102/20161102.zip", new File("C:\\FTPtemp\\20161102.zip"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			/**
			  * @param directory
			 *            上传目录
			 * @param uploadFile
			 *            要上传的文件 
			 * @param saveFile
			 *//*
			for(int i=0;i<2;i++){
				if(i==0){
					sftp.sftpUpload("/upload/440104000569953/20160909", "C:\\FTPtemp\\BAD_440104000569953_LDA_20160908.DAT");
				}else if(i==1){
					sftp.sftpUpload("/upload/440104000569953/20160909", "C:\\FTPtemp\\CON_440104000569953_LDA_20160908.DAT");
				}				
			}*/
			
			

		

		// try
		// {
		// myFtp.connect(ftpserverIp, importPort, userName, password);

		// 文件名 例如：CUT+YYYYMMDD.DAT
		// String fileName = "CUT"+sFormat.format(new Date())+ftpfileend;
		// 上传路径
		// String filePath = ftptodir+"/customer"+"/"+fileName;

		// 产生本地文件
		// StringBuffer data = new StringBuffer();

		// 检索客户信息
		/*
		 * List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		 * List<LenderInfor> inforList =
		 * lenderInforService.searchLenderInforByFilters(filters); if(inforList
		 * != null && inforList.size() > 0){ for(int
		 * i=0;i<inforList.size();i++){ LenderInfor temp = inforList.get(0);
		 * LoanContract loanContract = temp.getLoanContract();
		 * //判断时候当天新增、修改、删除记录 //1.先判断创建时间和更新时间是否为同一天： 同一天则 为新增 反之为更新 Date
		 * createDate = temp.getCreateDate(); Date updateDate =
		 * temp.getUpdateDate();
		 * 
		 * if(areSameDay(createDate,updateDate)){ //同一天 //判断是否为当天
		 * if(areSameDay(createDate,new Date())){ //判断是否为删除
		 * if("0".equals(temp.getDelFlag())){
		 * if("自然人".equals(loanContract.getLoanType())){ data.append("opt=A");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=P");
		 * data.append("name="+temp.getName()); data.append("/r"); }else
		 * if("企业".equals(loanContract.getLoanType())){ data.append("opt=A");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=E");
		 * data.append("name="+temp.getName()); data.append("/r"); } } } }else{
		 * //不同一天 //判断是否为当天 if(areSameDay(updateDate,new Date())){
		 * if("0".equals(temp.getDelFlag())){
		 * if("自然人".equals(loanContract.getLoanType())){ data.append("opt=U");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=P");
		 * data.append("name="+temp.getName()); data.append("/r"); }else
		 * if("企业".equals(loanContract.getLoanType())){ data.append("opt=U");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=E");
		 * data.append("name="+temp.getName()); data.append("/r"); } }else
		 * if("1".equals(temp.getDelFlag())){
		 * if("自然人".equals(loanContract.getLoanType())){ data.append("opt=D");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=P");
		 * data.append("name="+temp.getName()); data.append("/r"); }else
		 * if("企业".equals(loanContract.getLoanType())){ data.append("opt=D");
		 * //操作 data.append("id="+temp.getId()); data.append("loanType=E");
		 * data.append("name="+temp.getName()); data.append("/r"); } } } } } }
		 * 
		 * File localFile = new File(ftptempdir+"/"+fileName);
		 * if(!localFile.exists()){ localFile.createNewFile(); }
		 * FileOutputStream o=null; try { o = new FileOutputStream(localFile);
		 * o.write(data.toString().getBytes("GBK")); o.close(); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * System.out.println(myFtp.upload(localFile,filePath));
		 * myFtp.disconnect(); } catch (Exception e) {
		 * 
		 * System.out.println("连接FTP出错：" + e.getMessage()); }
		 */
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean areSameDay(Date dateA,Date dateB) {
	    Calendar calDateA = Calendar.getInstance();
	    calDateA.setTime(dateA);

	    Calendar calDateB = Calendar.getInstance();
	    calDateB.setTime(dateB);

	    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
	
	
	
}
