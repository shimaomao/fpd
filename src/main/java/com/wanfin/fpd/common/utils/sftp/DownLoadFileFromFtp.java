package com.wanfin.fpd.common.utils.sftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.wanfin.fpd.common.utils.CompressUtil;
import com.wanfin.fpd.common.utils.SWFTools;
import com.wanfin.fpd.common.utils.SftpUtils;

import net.lingala.zip4j.exception.ZipException;

public class DownLoadFileFromFtp {

    private Logger logger = Logger.getLogger(DownLoadFileFromFtp.class);
	
	public static  String ftpserverIp = ""; //ftp服务器 
	public static  String userName = "";  //用户名
	public static  String password = "";  //密码	
	public static  int downPort; //ftp下载文件端口
	public static  int importPort; //ftp上传文件端口
	public static  String ftpdowndir = ""; //ftp下载文件目录
	public static  String ftptodir = ""; //ftp上传文件目录
	public static  String ftpfileend = ""; //ftp文件扩展名	
	public static  String ftptempdir = "";  //临时文件目录
	public static  String[] ftpcompanydir;  //各公司文件目录
	
	
	
	
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
	
	/**
	 * 下载远程sftp中某目录下所有文件
	 * @param remoteDirectory	ftp目录名称（相对路径，例：20160703/）
	 * @param localDirectory	本地服务器目录路径（绝对路径，例：/var/local/download/20160703/baddata/）
	 */
	public void downloadList(String remoteDirectory, String localDirectory){	
		
		SftpUtils sftp = new SftpUtils();		
		try {			
			sftp.connect(ftpserverIp, downPort, userName, password);			
			sftp.downloadList(remoteDirectory, localDirectory);			
			logger.info("file dowload from sftp end...");
			sftp.disconnect();
			logger.info("sftp释放资源连接........");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void sendUploadFile(String remoteDir, String fileName, StringBuffer data) {		
	     
	    /**
	     * sftp上传方式
	     */
	     SftpUtils sftp = new SftpUtils();
	     try {
			sftp.connect(ftpserverIp, importPort, userName, password);
			remoteDir = remoteDir + "/" + getCurrentYearMonthDay()+ "/";			
			//创建上传错误文件的目录
			sftp.createDir(remoteDir, sftp.sftpClient);
			sftp.sftpClient.cd(remoteDir);
		    SftpUtils.createLoalDir(ftptempdir);
			File localFile = new File(ftptempdir+"/"+fileName); 
			 if(!localFile.exists()){
			 	localFile.createNewFile();
			 }
			 FileOutputStream o=null;			
			 o = new FileOutputStream(localFile);  
		     o.write(data.toString().getBytes("UTF-8"));  
		     o.close();    	 	     
		     
		     //SftpUtils.sftpUpload(fileName.replaceAll("LDA", "BAD"), localFile);		     	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 指定某个目录的所有文件  进行压缩加密成zip包
	 * @param path
	 * @param passwd
	 */
	public void uploadZip(String path,String zip,String passwd){			
	   CompressUtil.zip(path,zip,false,passwd);
	   String fileName = path+File.separator+zip+".zip";
	   File localFile = new File(fileName);
	   try {
		SftpUtils.sftpUpload(zip+".zip", localFile);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SftpException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSchException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	public static String getCurrentYearMonthDay() {
	    	String FORMAT_YYYYMMDD = "yyyyMMdd";
	        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYYMMDD);
			Date currentTime = new java.util.Date();// 得到当前系统时间
			String yearAndDayString = formatter.format(currentTime); // 将日期时间格式化
			return yearAndDayString;
	}	

	
}
