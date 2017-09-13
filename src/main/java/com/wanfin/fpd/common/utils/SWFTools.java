package com.wanfin.fpd.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SWFTools {
	private static Runtime runtime = Runtime.getRuntime();
	private static  String pdf2swfPath = "";//window环境下

	
	static {
		if (pdf2swfPath.equals("")) {
			//读取配置文件
			InputStream inputStream = SWFTools.class.getResourceAsStream("/application.properties");  
			Properties p = new Properties();  
	        try {  
	            p.load(inputStream);  
	            inputStream.close();  
	        } catch (IOException e1) {  
	            e1.printStackTrace();  
	        }  
	        //String tempIp = p.getProperty("ip");
	        pdf2swfPath = p.getProperty("pdf2swfPath");//ffmpeg安装路径
	        
	    	//System.out.println("读取配置文件");
		}
		
    	
	}
	
	
	public static String convert(String fileUrl) {
		//long start = System.currentTimeMillis();
		String toFilename = fileUrl.substring(0, fileUrl.lastIndexOf(".")+1) + "swf";		
		String command = pdf2swfPath + " " + fileUrl + " -o " + toFilename + " -T 9";
		//String command = pdf2swf + " " + fileUrl + " -o " + toFilename;
		try {
			runtime.exec(command);
			//Process process = runtime.exec(command);
			//process.waitFor();
			//process.destroy();
			//long end = System.currentTimeMillis();
			//System.out.println("转swf用时：" + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return toFilename;
	}	
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();				
		String path = "d:\\hqzs\\zs.txt";
		String toPath = "d:\\hq\\";
		//java.lang.Runtime.getRuntime().exec("cmd.exe /c copy C:\\cubeETL_TEST\\cog\\buildDir\\231.mdc C:\\cubeETL_TEST\\cog\\publish1\\231.mdc");
		String command = "cmd.exe /c xcopy /H/R/C/Y " + path + " " + toPath;
		try {
			Process process = runtime.exec(command);
			process.waitFor();
			process.destroy();
			long end = System.currentTimeMillis();
			System.out.println("用时：" + (end - start));
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		SWFTools.convert("E:\\uploadfiles\\yewuxinxi\\test.pdf");
	}
	
}
