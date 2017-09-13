package com.wanfin.fpd.modules.wish.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.wanfin.fpd.common.utils.StringUtils;


public class FileWrite {
	private static Logger logger = Logger.getLogger(FileWrite.class.getName());


	private FileWrite() {
		throw new IllegalAccessError("FileWrite class");
	}

	public static String concatenate(String s1, String s2) {
		return s1 + s2;
	}

	public static String copyFile(String filePath, String directoryPath, String name){
		URL url;
		String imageName = null;
		FileOutputStream fileOutputStream = null;
		try {  
			File f = new File(directoryPath);
			if (!f.exists()  && !f.isDirectory()) {
				f.mkdirs();
			}

			url = new URL(filePath);  
			DataInputStream dataInputStream = new DataInputStream(url.openStream());  
			if (StringUtils.isNotEmpty(name)){
				imageName = new Date().getTime() + name + ".jpg";  
			} else {
				imageName = new Date().getTime() + ".jpg";  
			}
			File file = new File(directoryPath , imageName);
			fileOutputStream = new FileOutputStream(file);  

			byte[] buffer = new byte[1024];  
			int length;  

			while ((length = dataInputStream.read(buffer)) > 0) {  
				fileOutputStream.write(buffer, 0, length);  
			}  
			fileOutputStream.flush();
			fileOutputStream.close();
			dataInputStream.close();  
		} catch (IOException e) {  
			e.printStackTrace();
		} 
		return imageName;
	}
	public static void main(String[] args){
		int i = 1;
		int j = 2; 
		System.out.println(++j);
		System.out.println(i++);
		System.out.println(i++);
		System.out.println(i);
	}
}
