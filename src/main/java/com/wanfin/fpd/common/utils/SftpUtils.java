package com.wanfin.fpd.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;



import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * sftp server工具类
 * @author XZT
 *
 */
public class SftpUtils {	
	
	  private static final Logger logger = Logger.getLogger(SftpUtils.class);
	    
	  public static ChannelSftp sftpClient = new ChannelSftp();
	
	
	 /** 
     * 连接sftp服务器 
     *  
     * @param host 
     *            主机 
     * @param port 
     *            端口 
     * @param username 
     *            用户名 
     * @param password 
     *            密码 
     * @return 
	 * @throws Exception 
     */  
    public static ChannelSftp connect(String host, int port, String username, String password) throws Exception  
    {  
	    try {
	        JSch jsch = new JSch();  
	        jsch.getSession(username, host, port);  
	        Session sshSession = jsch.getSession(username, host, port);  
	        sshSession.setPassword(password);  
	        Properties sshConfig = new Properties();  
	        sshConfig.put("StrictHostKeyChecking", "no");  
	        sshSession.setConfig(sshConfig);  
	        sshSession.connect();  
	        Channel channel = sshSession.openChannel("sftp");  
	        channel.connect();  
	        sftpClient = (ChannelSftp) channel;	        
	       }catch (Exception e)	    
	       {
	            throw new Exception("登陆异常，请检查主机端口");
	       }
        return sftpClient;  
    }   
  
    /** 
     * 上传文件 
     *  
     * @param fileName 
     *            文件名 
     * @param uploadFile 
     *            要上传的文件 
     * @param sftp 
     * @throws SftpException 
     * @throws FileNotFoundException 
     * @throws JSchException  
     */  
    public static void sftpUpload(String fileName, File uploadFile) throws FileNotFoundException, SftpException, JSchException  
    {          
    	//sftpClient.cd(directory);  
        //File file = new File(uploadFile);  
        sftpClient.put(new FileInputStream(uploadFile), fileName);  
        sftpClient.quit();
        sftpClient.disconnect();
    }    
  
  
    /** 
     * 下载文件 
     *  
     * @param directory 
     *            下载目录 
     * @param downloadFile 
     *            下载的文件 
     * @param saveFile 
     *            存在本地的路径 
     * @param sftp 
     */  
    public void download(String directory, String downloadFile, String saveFile)  
    {  
        try  
        {  
        	sftpClient.cd(directory);        	
            File file = new File(saveFile);  
            sftpClient.get(downloadFile, new FileOutputStream(file));  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }    
  
    /** 
     * 删除文件 
     *  
     * @param directory 
     *            要删除文件所在目录 
     * @param deleteFile 
     *            要删除的文件 
     * @param sftp 
     */  
    public void delete(String directory, String deleteFile)  
    {  
        try  
        {  
        	sftpClient.cd(directory);  
        	sftpClient.rm(deleteFile);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }       
    
    
     /**
	    * 下载目录下面所有文件 
	    * @param directory
	    * @param localPath
	    * @throws IOException
        * @throws SftpException 
	    */
	    public void downloadList(String directory,String localPath) throws IOException, SftpException{  
	    	List<String> str = getFileList(directory);
	 	    if(str != null && str.size() > 0){
	 	    	for(int i=0;i<str.size();i++){
	 	    		//组装规定的文件路径
	             	String newLoalPath = localPath+"/" + str.get(i);
	             	download(directory,str.get(i),newLoalPath);	
	 	    	}	    	    	
	 	    }
	    }  	
	    
	    /**
	     * 获取目录下文件名称
	     * @param filedir
	     * @return
	     * @throws IOException
	     * @throws SftpException 
	     */
	    public List<String> getFileList(String filedir)
		        throws IOException, SftpException
		    {		             
		    	Vector<?> fileList = sftpClient.ls(filedir);
		    	List<String> str = new ArrayList<String>();
		         
				for(int i=0;i<fileList.size();i++){					
					LsEntry ly = (LsEntry) fileList.get(i);		
					//if(!"".equals(ly.getFilename()) && ly.getFilename().toUpperCase().contains(".ZIP")){
						str.add(((LsEntry) fileList.get(i)).getFilename());
					//}				
				}		       
		        return str;
		}
	    
	    
	    /**
	     * 创建本地目录
	     * @param destDirName
	     * @return
	     */
	    public static boolean createLoalDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            //System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        //创建目录  
	        if (dir.mkdirs()) {  
	            //System.out.println("创建目录" + destDirName + "成功！");  
	            return true;  
	        } else {  
	            //System.out.println("创建目录" + destDirName + "失败！");  
	            return false;  
	        }  
	    }      
	    
	    
	    /**
	     * 断开与远程服务器的连接
	     * 
	     * @throws IOException
	     */
	    public void disconnect()
	        throws IOException
	    {
	        if (sftpClient.isConnected())
	        {
	        	sftpClient.disconnect();
	        }
	    }
	    
	    
	    
	    /** 
	     * 创建远程目录 
	     */  
	    public void createDir(String createpath, ChannelSftp sftpClient) {  
		     try {  
			      if (isDirExist(createpath)) {  
			       this.sftpClient.cd(createpath);  
			      }  
			      String pathArry[] = createpath.split("/");  
			      StringBuffer filePath = new StringBuffer("/");  
			      for (String path : pathArry) {  
			       if (path.equals("")) {  
			        continue;  
			       }  
			       filePath.append(path + "/");  
			       if (isDirExist(filePath.toString())) {  
			    	   sftpClient.cd(filePath.toString());  
			       } else {  
			        // 建立目录  
			    	   sftpClient.mkdir(filePath.toString());  
			        // 进入并设置为当前目录  
			    	   sftpClient.cd(filePath.toString());  
			       }  
			      }  
			      this.sftpClient.cd(createpath);  
		     } catch (SftpException e) {  
		    	 logger.info("sftp创建目录失败！");
		     }  
	   }  
	    
	    
	    /** 
	     * 判断目录是否存在 
	     */  
	    public boolean isDirExist(String directory) {  
		   boolean isDirExistFlag = false;  
	      try {  
		      SftpATTRS sftpATTRS = sftpClient.lstat(directory);  
		      isDirExistFlag = true;  
		      return sftpATTRS.isDir();  
		      
		      } catch (Exception e) {  
			      if (e.getMessage().toLowerCase().equals("no such file")) {  
			       isDirExistFlag = false;  
		      }  
	      }  
		 return isDirExistFlag;  
	   }  
	    
	    public static void main(String[] args) throws Exception {  
	    	SftpUtils sftpUtils = new SftpUtils();
	    	ChannelSftp sftp = connect("183.62.252.139", 65005, "yilian", "!12345678");  
//	        String pathString = "C:\\Users\\qiao\\Documents\\WeChat Files\\ldu_wizard\\Files\\识别验证平台测试环境部署-创世跬科.xlsx";  
	    	 String pathString = "C:\\Users\\qiao\\Documents\\20170717181832.xlsx";  
	        File file = new File(pathString);  
	        System.out.println("上传文件开始...");  
//	        uploadFile(pathString, sftp);  
	        sftpUtils.sftpUpload("testFile", file);
	        System.out.println("上传成功，开始删除本地文件...");  
//	        file.delete();  
	        System.out.println("删除完成，开始校验本地文件...");  
//	        if (!file.exists()) {  
//	            System.out.println("文件不存在，开始从远程服务器获取...");  
//	            sftpUtils.download(pathString, pathString, sftp);  
//	            System.out.println("下载完成");  
//	        } else {  
//	            System.out.println("在本地找到文件");  
//	        }  
//	        exit(sftp);  
	    }  
}
