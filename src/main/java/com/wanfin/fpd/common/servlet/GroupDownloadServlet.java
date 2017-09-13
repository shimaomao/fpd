package com.wanfin.fpd.common.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.FileUtils;

/**
 * 查看CK上传的图片
 * @author ThinkGem
 * @version 2014-06-25
 */
public class GroupDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws IOException {
		String file = "http://192.168.1.206/group1/M00/00/04/wKgBzljLW32AD1D-AAMYxFjJQg8604.png";
//		FileReader file = new FileReader(url);
//		BufferedReader br = new BufferedReader(file);
//		String str;
//		while ((str = br.readLine()) != null) {
//			System.out.println(str);
//		}
//		br.close();
//		new File(uri);

		try {
			URL url = new URL(file);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
			uc.connect();
			InputStream iputstream = uc.getInputStream();
			System.out.println("file size is:" + uc.getContentLength());// 打印文件长度
			byte[] buffer = new byte[4 * 1024];
			int byteRead = -1;
			while ((byteRead = (iputstream.read(buffer))) != -1) {
				System.out.println(byteRead+"---------------------------");
			}
			iputstream.close();
		} catch (Exception e) {
			System.out.println("读取失败！");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String filepath = req.getRequestURI();
		int index = filepath.indexOf(Global.GROUP_BASE_URL);
		if(index >= 0) {
			filepath = filepath.substring(index + Global.GROUP_BASE_URL.length());
		}
		// @2016-4-15 by zzm 下载后的文件名，如：xxx?outFileName=name
		String outFileName = req.getParameter("outFileName");
		try {
			filepath = UriUtils.decode(filepath, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(String.format("解释文件路径失败，URL地址为%s", filepath), e1);
		}
		try {
			FileCopyUtils.copy(FileUtils.getFileInputStream(Cons.Ips.IP_WFW_FDFS_PATH + Global.GROUP_BASE_URL + filepath), resp.getOutputStream());
			return;
		} catch (FileNotFoundException e) {
			req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
			req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}
}
