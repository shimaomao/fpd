package com.wanfin.fpd.common.utils;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.*;
public class JavaMailTextFile {
  
 private String host = "smtp.163.com";  //smtp服务器
 private String from = "15989067522@163.com";  //发件人地址
 private String user = "15989067522@163.com";  //用户名
 private String pwd = "";   //密码
 
 private String to = "";    //收件人地址
 private String affix = ""; //附件地址
 private String affixName = ""; //附件名称
 private String subject = ""; //邮件标题
 private String text = ""; //邮件标题

 
 public void setAddress(String to,String subject,String text){
  this.to   = to;
  this.subject = subject;
  this.text = text;
 }
 
 public void setAffix(String affix,String affixName){
  this.affix = affix;
  this.affixName = affixName;
 }
 
    @SuppressWarnings("restriction")
	public void send() {
	 
      
        Properties props = new Properties();
        //设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
      
        //用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
      
        //有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        //用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);
      
        //用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try{
         //加载发件人地址
            message.setFrom(new InternetAddress(from));
           //加载收件人地址
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
           //加载标题
            message.setSubject(subject);
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();         
            //   设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(text);
            multipart.addBodyPart(contentPart);
            //添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(affix);
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?"+enc.encode(affixName.getBytes())+"?=");
            multipart.addBodyPart(messageBodyPart);
            //将multipart对象放到message中
            message.setContent(multipart);
            //保存邮件
            message.saveChanges();
            //   发送邮件
            Transport transport = session.getTransport("smtp");
            //连接服务器的邮箱
            transport.connect(host, user, pwd);
            //把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
  
    public static void main(String[] args){
     JavaMailTextFile cn = new JavaMailTextFile();
     //收件人地址和邮件标题
     cn.setAddress("1119129837@qq.com","贷款催收函","函件内容请查看附件");
     cn.setAffix("f:/代码生成器.docx","代码生成器.docx"); //设置要发送附件的位置和标题
     cn.send();
    
    }
}