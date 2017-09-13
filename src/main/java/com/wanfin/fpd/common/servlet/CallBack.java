package com.wanfin.fpd.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.core.billing.BillingEngine;

public class CallBack extends HttpServlet {
	private static final long serialVersionUID = -4292507132770318115L;
	@Autowired
	private BillingEngine engine;

	public CallBack() {
		super();
	}

	public void destroy() {
		super.destroy(); 
		// Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	@SuppressWarnings("restriction")
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		final String CharSet = "UTF-8";
		response.setContentType("text/html");
		request.setCharacterEncoding(CharSet);
        response.setCharacterEncoding(CharSet);
		PrintWriter out = response.getWriter();
        String response_text = request.getParameter("response_text");
		String xml = "";
		if(response_text != null && !"".equals(response_text)){
			out.println("<br/>");
			String urlText = URLDecoder.decode(response_text, CharSet);//如果解出来的xml是乱码的，请将这个步骤给注释掉，有的服务器程序会自动做UrlDecode
			xml = new String(new sun.misc.BASE64Decoder().decodeBuffer(urlText),CharSet);
			String rerurnStr = getValue(xml,"ProcCode")+getString(getValue(xml,"RespCode"));
			out.print("0000");//通知易联接收成功
			
			String biRuleId = Global.BIRULE_ID;
			if(StringUtils.isNotEmpty(biRuleId)&&" 0000".equals(rerurnStr)){
				 Db.update("update bi_collect set status = 1 where id = ?",biRuleId);
				 out.print("服务开通成功！");
			}else{
				 out.print("服务开通失败！请联系管理人员");
			}
//			out.print("<script type='text/javascript'>window.close();</script>");
			out.flush();
			out.close();
		}
	}
	
	private String getValue(String xml, String name){
		if(xml==null || "".equals(xml.trim()) 
				|| name == null || "".equals(name.trim())){
			return "";
		}
		String tag = "<" + name + ">";
		String endTag = "</" + name + ">";
		if(!xml.contains(tag) || !xml.contains(endTag)){
			return "";
		}
		String value = xml.substring(xml.indexOf(tag) + tag.length(), xml.indexOf(endTag));
		if(value != null && !"".equals(value)){
			return value;
		}
		return "";
	}
	
	private String getString(String src) {
	    return (isNullOrEmpty(src) ? "" : (" " + src.trim()));
	}
	
	private boolean isNullOrEmpty(String src){
		return src == null || "".equals(src.trim());
	}

	public void init() throws ServletException {
		// Put your code here
	}
}
