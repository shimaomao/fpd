package com.wanfin.fpd.common.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送163邮件要设置POP3/SMTP/IMAP：开启SMTP服务，包括发件人和收件人都要开启。其他邮箱也是 
 * 
 */
public class JavaMail {

	// 设置服务器
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.163.com";
	//服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	private static String VALUE_PROPS = "true";              //注意是String类型,不是boolean类型
	//初始账号
	private String SEND_USER = "xxx@163.com";   //发件人
	private String SEND_UNAME = "xxx@163.com";  //用户名(发件人和用户名是一致的)
	private String SEND_PWD = "xxxxx";                //密码
	//建立会话
	private MimeMessage message;
	private Session s;

	/*
	 * 初始化方法
	 */
	public JavaMail() {
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, VALUE_SMTP);
		props.put(KEY_PROPS, VALUE_PROPS);
		s = Session.getInstance(props);
		 //s.setDebug(true);//开启后有调试信息 
		message = new MimeMessage(s);
	}

	/**
	 * 发送邮件
	 * @param subject 邮件标题     
	 * @param mailContent 邮件内容 
	 * @param mailAddress 收件人邮箱地址
	 *            
	 */
	public void sendHtmlEmail(String subject, String mailContent,String mailAddress) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(SEND_USER);
			message.setFrom(from);
			// 收件人
			InternetAddress to = new InternetAddress(mailAddress);
			message.setRecipient(Message.RecipientType.TO, to);
			// 邮件标题
			message.setSubject(subject);
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(mailContent, "text/html;charset=GBK");
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			//smtp验证,这是用来发邮件的邮箱用户名和密码
			transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JavaMail mail = new JavaMail();
		mail.sendHtmlEmail("某某小贷催收函文件", "邮件内容", "1119129837@qq.com");
		
	}
}
