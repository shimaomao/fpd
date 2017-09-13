/**
 * Project Name:dataExchange
 * File Name:DownLoadFileFromFtpTask.java
 * Package Name:cn.jrjzx.dataExchange.task
 * Date:2016年6月30日上午9:29:13
 * Copyright (c) 2016, hengwei.xiao@jrjzx.cn All Rights Reserved.
 *
 */

package com.wanfin.fpd.common.quartz.ftp;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wanfin.fpd.common.utils.SftpUtils;
import com.wanfin.fpd.common.utils.sftp.DownLoadFileFromFtp;


/**
 * ClassName:DownLoadFileFromFtpTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年6月30日 上午9:29:13 <br/>
 * 
 * @author xiaohengwei
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
@Transactional
public class DownLoadFileFromFtpTask {

	private Logger logger = Logger.getLogger(DownLoadFileFromFtpTask.class);
	
	private WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();


	public static void main(String[] args) {		
		DownLoadFileFromFtpTask dff = new DownLoadFileFromFtpTask();
		dff.downloadFileScheduler();		
	}
	
	public void downloadFileScheduler() {	
		long t1 = System.currentTimeMillis();		
		String[] ftpcompanydirArray = { DownLoadFileFromFtp.ftptodir};
		
		for (String fdir : ftpcompanydirArray) {
			String remoteDirectory = fdir + "/" + DateUtil.getYesterdayYearMonthDay()+"/"+ "baddata" +"/";	//相对路径
			//String remoteDirectory = fdir + "/20160926/"+ "baddata" +"/";	//相对路径
			//String remoteDirectory = "/";	//相对路径
			String localDirectory = DownLoadFileFromFtp.ftptempdir + "/return/";	//绝对路径
			
			logger.info("---ftp下载路径...remoteDirectory="+remoteDirectory+"   localDirectory="+localDirectory);
			//FtpUtils.createDir(localDirectory);  //ftp连接方式			
			SftpUtils.createLoalDir(localDirectory);   //sftp连接方式
			//删除拉去数据文件夹里面的所有文件
			FileUtil.delAllFile(localDirectory);
			
			DownLoadFileFromFtp loadFileFromFtp = new DownLoadFileFromFtp();
			loadFileFromFtp.downloadList(remoteDirectory, localDirectory);	
			
			File filePath = new File(localDirectory);
			if(filePath.exists() && filePath.isDirectory()){
				File[] files =  filePath.listFiles();
				
				//其次处理其它的文件
				for (File ldaFile : files) {
					String ldaFileName = ldaFile.getName();
					if(ldaFileName.toUpperCase().contains("BAD") && 
							ldaFile.isFile() && ldaFileName.toUpperCase().endsWith(".DAT") && !ldaFileName.toUpperCase().contains("BAS")){						
						String localAbsolutePath = ldaFile.getAbsolutePath();						
						logger.info("---开始解析文件"+localAbsolutePath);
						
						List<String> resultList = WriteAndReadUtil.readDataFromFile(localAbsolutePath);
						
						
						if(resultList != null && resultList.size() > 0) {								
							logger.info("----业务回执中业务记录有 " + resultList.size() + " 笔");
							
							Map<String, List<Object>> tempMap  = ResponseUtil.resultResponse(loadFileFromFtp, remoteDirectory, ldaFileName, resultList);
						}		
					}
				}
			}
			
			//删除 发送回执文件夹里面的所有文件
			//FileUtil.delAllFile(DownLoadFileFromFtp.ftptempdir);
			
			//删除拉去数据文件夹里面的所有文件
			FileUtil.delAllFile(localDirectory);	
			
		}		
		long t2 = System.currentTimeMillis();
		
		logger.info("本次任务耗时----"+(t2-t1)+" ms");		
	}	
	
	
	

}
