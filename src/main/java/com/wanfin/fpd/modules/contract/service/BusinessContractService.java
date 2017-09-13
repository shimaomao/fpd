/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.awt.Insets;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.BusinessContract;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.contract.dao.BusinessContractDao;

/**
 * 业务合同Service
 * @author srf
 * @version 2017-02-22
 */
@Service
@Transactional(readOnly = true)
public class BusinessContractService extends CrudService<BusinessContractDao, BusinessContract> {

	public BusinessContract get(String id) {
		return super.get(id);
	}
	
	public List<BusinessContract> findList(BusinessContract businessContract) {
		return super.findList(businessContract);
	}
	
	public Page<BusinessContract> findPage(Page<BusinessContract> page, BusinessContract businessContract) {
		return super.findPage(page, businessContract);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessContract businessContract) {
		super.save(businessContract);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessContract businessContract) {
		super.delete(businessContract);
	}
	
	/**
	 * 通过条件查询单挑内容
	 * @param businessContract
	 * @return
	 */
	public BusinessContract getByCondition(BusinessContract businessContract) {
		return dao.getByCondition(businessContract);
	}
	
	public BusinessContract getForCheck(BusinessContract businessContract) {
		return dao.getForCheck(businessContract);
	}

	/**
	 * 生成pdf文件，成功则返回pdf的路径
	 * @param fileName 生成pdf文件名称
	 * @param content 生成pdf文件内容
	 * @param contextpath 项目访问根路径(水印用)
	 * @param crosswise 是否横向，1为横向
	 * @return
	 */
	public String CreatePdf(String fileName, String content, String contextpath, String crosswise) {// TODO
		System.out.println("contextpath="+contextpath);
		content = "<html><head><meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\"><meta name=Generator content=\"Microsoft Word 14 (filtered)\"><style></style></head><body lang=ZH-CN style='text-justify-trim:punctuation'>"+content;
		content = content+"</body></html>";
		content = content.replace("&gt;", ">")  
        .replace("&lt;", "<")  
        .replace("&quot;", "\"")
        .replace("&amp;", "&");
		//content = content.replace("&yen;", "\u00A5");
		String rmbcode = "<img alt=\"¥\" src=\""+Global.INTRANET_URL + Global.USERFILES_BASE_URL + "r.png\" style=\"height: 16px; width: 9px;\" />";
		content = content.replace("&yen;", rmbcode);
		Principal principal = UserUtils.getPrincipal();
		String root = Global.USERFILES_BASE_URL + principal.getLoginName() + "/pdf/" + DateUtils.getDate();
		String realPath = Global.getUserfilesBaseDir() + root;
		if(!new File(realPath).exists()){
		    new File(realPath).mkdirs();
		}
		fileName = DateUtils.getDate("yyyyMMddHHmmssSSS") + (int)(Math.random()*1000) + "_" + fileName;
		//System.out.println("BASE_URL:"+Global.USERFILES_BASE_URL);
		//System.out.println("root:"+root);
		//System.out.println("realPath:"+realPath);
		//System.out.println("fileName:"+fileName);
		try{
			//生成pdf
			FileOutputStream fos = new FileOutputStream( new File(realPath + "/" + fileName +".pdf") );
			//FileOutputStream fos = new FileOutputStream( new File( "C:/Temp/test_web.pdf") );
			PD4ML pd4ml = new PD4ML();
			//定义边距 上 左 下 右
			//pd4ml.setPageInsets(new Insets(50, 70, 50, 70));
			pd4ml.setPageInsets(new Insets(53, 50, 30, 50));
			if(StringUtils.isNotBlank(crosswise) && "1".equals(crosswise)){
				//横向A4
				pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
			}else{
				//正向A4
				pd4ml.setPageSize(PD4Constants.A4);
			}
	        pd4ml.useTTF("java:fonts", true);
	        pd4ml.setDefaultTTFs("SimHei", "Arial", "Courier New");
			
	        //设置页码
	        PD4PageMark footer = new PD4PageMark();  
	        footer.setPageNumberTemplate("$[page]/$[total]");
	        footer.setTitleAlignment(PD4PageMark.LEFT_ALIGN);
	        footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN); 
	        footer.setInitialPageNumber(1);
	        footer.setFontSize(8);  
	        footer.setAreaHeight(10);
	        
	        //设置水印 水印的图片需要用URL方式，否则会报错，无法使用水印
	        //System.out.println("设置水印:"+Global.getUserfilesBaseDir()+Global.USERFILES_BASE_URL+"Watermark.png");
	        //String Watermark = "C:/eclipseNeon/WorkSpaces/Wanfin_GIT/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/guangken/userfiles/Watermark.png";
	        if(StringUtils.isNotBlank(contextpath) && (StringUtils.isBlank(crosswise) || "0".equals(crosswise))){
	        	//String Watermark = contextpath + Global.USERFILES_BASE_URL + "Watermark.png";//非农垦（可以调用外网）
	        	//String Watermark = "http://127.0.0.1:8011/userfiles/Watermark.png";//农垦(不可以调用外网)
	        	String Watermark = Global.INTRANET_URL + Global.USERFILES_BASE_URL + "Watermark.png";//使用配置，适应农垦
		        footer.setWatermark(
		        		Watermark,   
		        		//new Rectangle(57,20,480,33),//有横线
		        		new Rectangle(57,20,99,35),
		                // watermark opacity(透明度) in percents  
		                100);
	        }
	        pd4ml.setPageFooter(footer);
	        
	        pd4ml.enableDebugInfo();
	        
	        StringReader strReader = new StringReader(content);
	        pd4ml.render(strReader, fos);
	        return root+"/"+fileName +".pdf";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}