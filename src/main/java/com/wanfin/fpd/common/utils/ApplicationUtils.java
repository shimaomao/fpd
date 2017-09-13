package com.wanfin.fpd.common.utils;

import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.wanfin.fpd.modules.sys.entity.User;

@SuppressWarnings("deprecation")
public class ApplicationUtils {
	
	/**   
	 * @Description: 获取Web工程类路径
	 * @author Chenh  
	 * @throws
	 */
	public String getAbsClass() {
		return Class.class.getClass().getResource("/").getPath();
	}
	
	/**
	 * ClassPathXmlApplicationContext读取xml
	 * @param xmlFile
	 * @return
	 */
	public static ApplicationContext loadContext(String xmlFile) {
		return new ClassPathXmlApplicationContext(xmlFile);
	}
	
	/**
	 * ClassPathXmlApplicationContext读取xml
	 * @param xmlFiles
	 * @return
	 */
	public static ClassPathXmlApplicationContext loadContexts(String[] xmlFiles) {
		return new ClassPathXmlApplicationContext();
	}

	/**
	 * ClassPathResource读取xml
	 * @param xmlFile
	 * @return
	 */
	public static BeanFactory loadResource(String xmlFile) {
		return new XmlBeanFactory(new ClassPathResource(xmlFile));
	}

	/**
	 * XmlWebApplicationContext读取xml
	 * @param xmlFile
	 * @return
	 */
	public static XmlWebApplicationContext loadWebContexts(PageContext pageContext, String[] xmlWebFiles) {
		XmlWebApplicationContext xmlContext = new XmlWebApplicationContext();   
		xmlContext.setConfigLocations(xmlWebFiles);   
		xmlContext.setServletContext(pageContext.getServletContext());   
		xmlContext.refresh();  
		return xmlContext;
	}

	/**
	 * FileSystemResource读取xml
	 * @param xmlFile
	 * @return
	 */
	public static BeanFactory loadFileContexts(String xmlAbsFile) {
		return new XmlBeanFactory(new FileSystemResource(xmlAbsFile)); 
	}

	/**
	 * FileSystemXmlApplicationContext读取xml
	 * @param xmlSysFiles
	 * @return
	 */
	public static ApplicationContext loadSysContexts(String xmlSysFile) {
		return new FileSystemXmlApplicationContext(xmlSysFile); 
	}
	public static ApplicationContext loadSysContexts(String[] xmlSysFiles) {
		return new FileSystemXmlApplicationContext(xmlSysFiles); 
	}
	
	@SuppressWarnings({"unused"})
	public static void main(String[] args) {
		User user;
		PageContext pageContext = null;
		String[] xmlFiles =	new String[]{"applicationContextconfig.xml","applicationContext.xml","applicationContext-data.xml"};
		String[] xmlWebFiles =	new String[]{"/WEB-INF/applicationContextconfig.xml","/WEB-INF/applicationContext.xml","/WEB-INF/applicationContext-data.xml"};
		String[] xmlAbsFiles =	new String[]{"E:/Java/spring/WebRoot/WEB-INF/classes/applicationContextconfig.xml","E:/Java/spring/WebRoot/WEB-INF/classes/applicationContext-data.xml"};
		String[] xmlSysFiles = new String[]{"classpath:地址", "WebRoot/WEB-INF/applicationContext*.xml", "WebRoot/WEB-INF/applicationContext.xml"};
		
		/**
		 * ClassPathXmlApplicationContext
		 **/
		ApplicationContext context = loadContext(xmlFiles[0]);
		user = (User)context.getBean("user"); 

		BeanFactory factory= loadContexts(xmlFiles);
		user = (User)factory.getBean("user"); 

		/**
		 * ClassPathResource
		 **/
		BeanFactory bfactory = loadResource(xmlFiles[0]);   
		user = (User)bfactory.getBean("user");   

		/**
		 * XmlWebApplicationContext
		 **/
		XmlWebApplicationContext xmlContext = loadWebContexts(pageContext, xmlWebFiles);   
		user = (User)xmlContext.getBean("user");

		/**
		 * FileSystemResource
		 **/
		BeanFactory xmlFileContext = loadFileContexts(xmlAbsFiles[0]);
		user = (User)xmlFileContext.getBean("user");

		/**
		 * FileSystemXmlApplicationContext
		 **/
		ApplicationContext xmlSysContext;
		xmlSysContext = loadSysContexts(xmlSysFiles);
		xmlSysContext = loadSysContexts(xmlSysFiles[0]);
		xmlSysContext = loadSysContexts(xmlSysFiles[1]);
		user = (User)xmlFileContext.getBean("user");
	}
}
