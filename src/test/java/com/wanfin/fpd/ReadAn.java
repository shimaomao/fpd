package com.wanfin.fpd;

import java.lang.annotation.Annotation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;

public class ReadAn {

	public enum Color{ BULE,RED,GREEN};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Class clazz = Class.forName("com.wanfin.fpd.modules.sys.web.MenuController");
			boolean annotationPresent = clazz.isAnnotationPresent(RequestMapping.class);
			if(annotationPresent){
				RequestMapping annotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
					String[] params = annotation.params();
					for (String string : params) {
						System.out.println(string);
					}
					String[] value = annotation.value();
					for (String string : value) {
						System.out.println(string);
					}
			}else{
				System.out.println("Don't have an");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
