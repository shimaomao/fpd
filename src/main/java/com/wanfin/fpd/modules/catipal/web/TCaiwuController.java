/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.web;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.ftp.DownLoadFileFromFtpTask;
import com.wanfin.fpd.common.quartz.ftp.FileUtil;
import com.wanfin.fpd.common.quartz.ftp.UploadFileFromFtpTask;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.catipal.service.TCaiwuService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 小贷财务报表Controller
 * @author lxh
 * @version 2016-11-02
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/catipal/tCaiwu")
public class TCaiwuController extends BaseController {

	@Autowired
	private TCaiwuService tCaiwuService;
	
	@ModelAttribute
	public TCaiwu get(@RequestParam(required=false) String id) {
		TCaiwu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCaiwuService.get(id);
		}
		if (entity == null){
			entity = new TCaiwu();
		}
		return entity;
	}
	
	@RequestMapping(value = {"caiwuUpload"})
	public String caiwuUpload(TCaiwu tCaiwu, HttpServletRequest request, HttpServletResponse response, Model model) {
	
	
		return "modules/catipal/caiwuUploadForm";
	}
	
	//@RequiresPermissions("catipal:tCaiwu:view")
		@RequestMapping(value = {"listXj", ""})
		public String listXj(TCaiwu tCaiwu, HttpServletRequest request, HttpServletResponse response, Model model) {
			tCaiwu.setInformFilingType(new Long(3));
			
			Page<TCaiwu> page  = new Page<TCaiwu>(request, response);
			page.setOrderBy("bao_date desc");
			page = tCaiwuService.findPage(page, tCaiwu); 
			
			model.addAttribute("page", page);
			model.addAttribute("tCaiwu", tCaiwu);
			return "modules/catipal/caiwuXjList";
		}
		
	//@RequiresPermissions("catipal:tCaiwu:view")
	@RequestMapping(value = {"listZcfz", ""})
	public String listZcfz(TCaiwu tCaiwu, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCaiwu.setInformFilingType(new Long(1));
		Page<TCaiwu> page  = new Page<TCaiwu>(request, response);
		page.setOrderBy("bao_date desc");
		page = tCaiwuService.findPage(page, tCaiwu); 
		
		model.addAttribute("page", page);
		model.addAttribute("tCaiwu", tCaiwu);
		return "modules/catipal/caiwuZcfzList";
	}
	
	//@RequiresPermissions("catipal:tCaiwu:view")
	@RequestMapping(value = {"listLr", ""})
	public String listLr(TCaiwu tCaiwu, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCaiwu.setInformFilingType(new Long(2));
		
		Page<TCaiwu> page  = new Page<TCaiwu>(request, response);
		page.setOrderBy("bao_date desc");
		page = tCaiwuService.findPage(page, tCaiwu); 
		
		model.addAttribute("page", page);
		model.addAttribute("tCaiwu", tCaiwu);
		return "modules/catipal/caiwuLrList";
	}

	//@RequiresPermissions("catipal:tCaiwu:view")
	@RequestMapping(value = "form")
	public String form(TCaiwu tCaiwu, Model model) {
		if (!StringUtils.isBlank(tCaiwu.getId())){
			tCaiwu = tCaiwuService.get(tCaiwu.getId());
		}
		
		model.addAttribute("caiwu", tCaiwu);
		
		//// 1：小货资产负债 2：小货利润表
		if (tCaiwu.getInformFilingType() != null && tCaiwu.getInformFilingType() == 1) {
			return "modules/catipal/caiwuZcfzForm";
		} else if (tCaiwu.getInformFilingType() != null && tCaiwu.getInformFilingType() == 2) {
			return "modules/catipal/caiwuLrForm";
		} else if (tCaiwu.getInformFilingType() != null && tCaiwu.getInformFilingType() == 3) {
			return "modules/catipal/caiwuXjForm";
		}
	
		return "modules/catipal/caiwuLrForm";
	}

	//@RequiresPermissions("catipal:tCaiwu:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(value = "file", required = false) MultipartFile file,
			TCaiwu caiwu, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		User currentUser = UserUtils.getUser();//当前登录人
		if (!beanValidator(model, caiwu)){
			return form(caiwu, model);
		}
		
		User user = UserUtils.getUser();
		if((user == null) || (user.getCompany() == null)){
			user = UserUtils.getAdmin();
		}
		
		if(StringUtils.isEmpty(caiwu.getOrganId())){
			caiwu.setOrganId(user.getCompany().getId());
		}
		
		if(StringUtils.isEmpty(caiwu.getId())){
			caiwu.setId(UUID.randomUUID().toString());
		}
		
		
		//if(caiwu.getId() == null || "".equals(caiwu.getId())){
			caiwu.setReportName(currentUser.getCompany().getName());
			//caiwu.setOrganId(currentUser.getCompany().getId()); 
			//caiwu.setCreateBy(currentUser);	
		//}	
		
		caiwu.setScanFlag("0");	
		String savePath = "";
		String newFileName = "";
		//保存附件
		if (file != null) {
			// 文件保存目录URL
			///String saveFilePath =  "/userfiles" + FileUtil.createDirPath(caiwu.getInformFilingType());	
			String saveFilePath =  "/userfiles" ;	
			savePath = request.getSession().getServletContext().getRealPath("/")  
					+ saveFilePath;	
			
			
			String fileFileName = file.getOriginalFilename();  
			newFileName = FileUtil.generateFileName(fileFileName);	
	       // File targetFile = new File(savePath, newFileName);  
	        
	        //if(!targetFile.exists()){  
	           // targetFile.mkdirs();  
	        //}  
	  
	        //保存  
	        try {  
	            //file.transferTo(targetFile);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        
	        caiwu.setFileName(fileFileName);
	        caiwu.setFilePath(saveFilePath +"/"+ newFileName);
	        caiwu.setFilingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));	
	      //tCaiwuService.save(caiwu);
			this.jiexi(caiwu.getBaoDate(), caiwu, savePath +"/"+ newFileName, file );
		} else {		
			tCaiwuService.save(caiwu);
		}
		
		
		addMessage(redirectAttributes, "保存小贷财务报表成功");
		//// 1：小货资产负债 2：小货利润表
		if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType() == 1) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listZcfz?repage";
		} else if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType()==2) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listLr?repage";
		}else if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType()==3) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listXj?repage";
		}
		return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listLr?repage";
	}
	
	
	/**
	 * 解析文件内容
	 */	
	private void jiexi(String baoDate,TCaiwu tCaiwu,String downLoadPath,MultipartFile file) throws Exception {

		
		String fileFileName = "";
		
		TCaiwu query = new TCaiwu();
		//query.setScanFlag("0");
		query.setBaoDate(baoDate);	
		query.setInformFilingType(new Long("1"));
		List<TCaiwu> proceedsList = tCaiwuService.findList(query);
		TCaiwu tempCaiwu = null;
		if (proceedsList != null && proceedsList.size() > 0) {
			tempCaiwu = proceedsList.get(0);
		}
		
		
		if (file != null) {
			//caiwuService.deleteCaiwu(tempCaiwu.getId());
			
			//downLoadPath = tempCaiwu.getFilePath();
			//fileFileName = tempCaiwu.getFileName();
			
			//判断新增还是修改
			if (tempCaiwu == null) {				
				tempCaiwu = new TCaiwu();
				tempCaiwu.setId(null);
			}
		
			tempCaiwu.setBaoDate(tCaiwu.getBaoDate());
			tempCaiwu.setFileName(tCaiwu.getFileName());
			tempCaiwu.setFilePath(tCaiwu.getFilePath());
			tempCaiwu.setFilingDate(tCaiwu.getFilingDate());	
			//tempCaiwu.setInformFilingType(tCaiwu.getInformFilingType());
			//tempCaiwu.preInsert();
			//downLoadPath = tCaiwu.getFilePath();
			fileFileName = tCaiwu.getFileName();
			
			
			//从本地读取文件流
	    	//File file = new File(downLoadPath); 
	    	String flag = "";
			try {
				tCaiwuService.insertExcel(file.getInputStream(), fileFileName, 4, 6, tempCaiwu );	
			
				flag = "解析成功";
			} catch (Exception e) {
				e.printStackTrace();
				flag = "解析失败";
			}		
		} 
		
		
		
	}
	
	/**
	 * 删除附件
	 * @return
	 */
	@RequestMapping(value = "deleteCWFile")
	public String deleteCWFile(
			TCaiwu caiwu, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		TCaiwu tempCaiwu = tCaiwuService.get(caiwu.getId());
		this.deleteFile( request.getSession().getServletContext().getRealPath("/") + tempCaiwu.getFilePath());//删除文件
		tempCaiwu.setFileName(null);
		tempCaiwu.setFilePath(null);
		tempCaiwu.setFilingDate(null);
		tCaiwuService.save(tempCaiwu);
	
		return this.form(caiwu, model);
	}
	
	
	/**
	 * 删除附件
	 * @return
	 */
	public void deleteFile(String tempFilePath) throws Exception {		
		String tempFileType = tempFilePath.substring(tempFilePath.lastIndexOf(".") + 1);
		File file = new File(tempFilePath);
		file.delete();
		if (tempFileType.equals("doc") || tempFileType.equals("ppt") || tempFileType.equals("xls")
				|| tempFileType.equals("docx") || tempFileType.equals("pptx") || tempFileType.equals("xlsx")
				|| tempFileType.equals("txt")) {
			file = new File(tempFilePath.substring(0, tempFilePath.lastIndexOf(".")) + ".pdf");
			file.delete();
			file = new File(tempFilePath.substring(0, tempFilePath.lastIndexOf(".")) + ".swf");
			file.delete();
		} else if(tempFileType.equals("pdf")) {
			file = new File(tempFilePath.substring(0, tempFilePath.lastIndexOf(".")) + ".swf");
			file.delete();
		}
	}
	
	//@RequiresPermissions("catipal:tCaiwu:edit")
	@RequestMapping(value = "delete")
	public String delete(TCaiwu caiwu, RedirectAttributes redirectAttributes) {
		TCaiwu query = new TCaiwu();
		//query.setScanFlag("0");
		if (!StringUtils.isBlank(caiwu.getBaoDate())) {
			query.setBaoDate(caiwu.getBaoDate());;
			List<TCaiwu> proceedsList = tCaiwuService.findList(query);		
			for (TCaiwu tempCaiwu : proceedsList) {
				//tempCaiwu = proceedsList.get(0);
				tCaiwuService.delete(tempCaiwu);
			}			
			
			addMessage(redirectAttributes, "删除小贷财务报表成功");
		} else {
			addMessage(redirectAttributes, "删除小贷财务报表失败");
		}
		
		if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType() == 1) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listZcfz?repage";
		} else if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType()==2) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listLr?repage";
		}else if (caiwu.getInformFilingType() != null && caiwu.getInformFilingType()==3) {
			return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/listXj?repage";
		}
		
		return "redirect:"+Global.getAdminPath()+"/catipal/tCaiwu/?repage";
	}

	
	

	@RequestMapping(value = "testDowloadFtp")
	public String testDowloadFtp(RedirectAttributes redirectAttributes) {
		DownLoadFileFromFtpTask dlff = new DownLoadFileFromFtpTask();
		dlff.downloadFileScheduler();
		return "";
	}
	
	@RequestMapping(value = "testUploadFtp")
	public String testUploadFtp(RedirectAttributes redirectAttributes) {
		UploadFileFromFtpTask up = new UploadFileFromFtpTask();
		up.execute();
		return "";
	}

	
	/**
     * 子跳转不同的上传页面
     */		
	/*@RequestMapping(value = "subLookZcfz")
	public String subLookZcfz(TCaiwu tCaiwu, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {			
		if (informFilingId != null && !informFilingId.trim().equals("")) {
			informFiling = informService.getInformFiling(new Long(informFilingId));
			caiwu = informFiling.getCaiwu();
			fileTypeId = informFiling.getFileType().getId();			
		} else {
			informFiling = new InformFiling();
			caiwu = new Caiwu();
		}
		if (fileTypeId == 115) {
			return "lr";
		} else if (fileTypeId == 116) {			
			return "zcfz";
		} 
		return "";
	}*/
	
}