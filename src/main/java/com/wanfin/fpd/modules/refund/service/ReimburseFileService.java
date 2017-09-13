/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.FileUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.refund.dao.ReimburseFileDao;
import com.wanfin.fpd.modules.refund.entity.ReimburseFile;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 申请退款上传文件Service
 * @author srf
 * @version 2016-04-06
 */
@Service
@Transactional(readOnly = true)
public class ReimburseFileService extends CrudService<ReimburseFileDao, ReimburseFile> {

	public ReimburseFile get(String id) {
		return super.get(id);
	}
	
	public List<ReimburseFile> findList(ReimburseFile reimburseFile) {
		return super.findList(reimburseFile);
	}
	
	public List<ReimburseFile> findListCondition(ReimburseFile reimburseFile) {
		return super.dao.findListCondition(reimburseFile);
	}
	
	public Page<ReimburseFile> findPage(Page<ReimburseFile> page, ReimburseFile reimburseFile) {
		return super.findPage(page, reimburseFile);
	}
	
	/**
	 * 文件上传
	 * @param reimburseId 申请退款的ID
	 * @param title 文件标题
	 * @param file 要上传的文件
	 * @param request
	 * @throws IOException
	 */
	@Transactional(readOnly = false)
	public void uploadFile(String reimburseId, String type, String title, MultipartFile file, HttpServletRequest request) throws IOException{
		uploadFiles(reimburseId, type, new String[]{title} , new MultipartFile[]{file}, request);
	}
	
	/**
	 * 多文件上传
	 * @param reimburseId 申请退款的ID
	 * @param title 文件标题
	 * @param file 要上传的文件
	 * @param request
	 * @throws IOException
	 */
	@Transactional(readOnly = false)
	public void uploadFiles(String reimburseId, String type, String title[], MultipartFile files[], HttpServletRequest request) throws IOException{
		if(files != null && files.length>0){
			Principal principal = UserUtils.getPrincipal();
			String root = Global.USERFILES_BASE_URL + principal.getLoginName() + "/" + (StringUtils.isBlank(type) ? "defalut" : type )
					+ "/" + DateUtils.getDate();
			String realPath = Global.getUserfilesBaseDir() + root;
			
			for(int i=0; i<files.length; i++){
				MultipartFile file=files[i];
				if(file.isEmpty()){
					continue;
				}
				
				String fileName = file.getOriginalFilename();//上传文件名
				fileName = DateUtils.getDate("yyyyMMddHHmmssSSS")+"_"+(int)(Math.random()*1000)+fileName.substring(fileName.lastIndexOf("."),fileName.length());
				
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(FileUtils.path(realPath), fileName));
				
				ReimburseFile reimburseFile = new ReimburseFile();
				// 文件保存的相对路径
				reimburseFile.setFilePath(root+"/"+fileName);
				//文件的原名字
				reimburseFile.setSourceName(file.getOriginalFilename());
				//标题
				if(title==null || title[i] == null || "".equals(title[i])){
					reimburseFile.setTitle(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")-1));
				}else{
					reimburseFile.setTitle(title[i]);
				}
				//文件保存后名称
				reimburseFile.setFileName(fileName);
				//文件类型
				reimburseFile.setFileType(type);
				//文件所对应申请退款的ID
				reimburseFile.setReimburseId(reimburseId);
				
				super.save(reimburseFile);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void save(ReimburseFile reimburseFile) {
		super.save(reimburseFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReimburseFile reimburseFile) {
		super.delete(reimburseFile);
	}
	
}