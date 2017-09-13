/**
\ * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.files.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.plugin.activerecord.Db;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.ImageDeal;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.files.entity.ContractFilesVo;
import com.wanfin.fpd.modules.files.entity.FileBytesVo;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.files.service.TContractFilesService;

/**
 * 附件管理Controller
 * 
 * @author zzm
 * @version 2016-03-21
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/files/tContractFiles")
public class TContractFilesController extends BaseController {

	@Autowired
	private TContractFilesService tContractFilesService;

	@ModelAttribute
	public TContractFiles get(@RequestParam(required = false) String id) {
		TContractFiles entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tContractFilesService.get(id);
		}
		if (entity == null) {
			entity = new TContractFiles();
		}
		return entity;
	}

	@RequiresPermissions("files:tContractFiles:view")
	@RequestMapping(value = { "list", "" })
	public String list(TContractFiles tContractFiles,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<TContractFiles> page = tContractFilesService.findPage(
				new Page<TContractFiles>(request, response), tContractFiles);
		model.addAttribute("page", page);
		model.addAttribute("tContractFiles", tContractFiles);

		String isClose = request.getParameter("isClose");
		if (isClose != null && !"".equals(isClose)) {
			model.addAttribute("isClose", true);
		}

		String strUrl = "modules/files/tContractFilesListEmp";
		String mark = request.getParameter("mark");// 当mark为1的时候，是企业过来的
		if (mark != null && !"".equals(mark)) {
			strUrl = "modules/files/tContractFilesList";
		}
		return strUrl;
	}

	/**
	 * 附件列表
	 * 
	 * @param BUSINESSID
	 *            附件关联的业务id
	 * @param BUSINESSTYPE
	 *            附件类型
	 * @param page
	 *            当前页
	 * @param rows
	 *            pageSize
	 * @param sidx
	 *            排序字段
	 * @param sord
	 *            排序方法（asc，desc）
	 * @return
	 */
	@RequestMapping(value = "jqgrid", method = RequestMethod.POST)
	@ResponseBody
	public Page<TContractFiles> jqgrid(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String BUSINESSID,
			@RequestParam(required = false) String BUSINESSTYPE,
			@RequestParam(required = false) String page,
			@RequestParam(required = false) String rows,
			@RequestParam(required = false) String sidx,
			@RequestParam(required = false) String sord) {
		String orderBy = "";
		if (StringUtils.isNotBlank(sidx)) {
			orderBy = sidx;
			if (StringUtils.isNotBlank(sord))
				orderBy = orderBy + " " + sord;
		}
		Page<TContractFiles> pageParam = new Page<TContractFiles>(
				Integer.valueOf(page), Integer.valueOf(rows));
		pageParam.setOrderBy(orderBy);
		TContractFiles contractFiles = new TContractFiles();
		contractFiles.setTaskId(BUSINESSID);
		if (StringUtils.isNotBlank(BUSINESSTYPE))
			contractFiles.setType(BUSINESSTYPE);
		Page<TContractFiles> data = tContractFilesService.findPage(pageParam,
				contractFiles);
		return data;
	}

	@RequestMapping(value = "form")
	public String form(TContractFiles tContractFiles, Model model) {
		model.addAttribute("tContractFiles", tContractFiles);
		return "modules/files/tContractFilesForm";
	}

	// ------------新增 start-----------
	@RequestMapping(value = "add")
	public String add(TContractFiles tContractFiles, Model model) {
		model.addAttribute("tContractFiles", tContractFiles);
		return "modules/files/tContractFilesAdd";
	}

	@RequestMapping(value = "toSave")
	public String toSave(TContractFiles tContractFiles, Model model,
			RedirectAttributes redirectAttributes) {
		model.addAttribute("tContractFiles", tContractFiles);
		addMessage(redirectAttributes, "保存客户关联成功");
		return "redirect:"
				+ Global.getAdminPath()
				+ "/files/tContractFiles/?repage&type=customer_archives&isClose=1&taskId="
				+ tContractFiles.getTaskId();
	}

	// --------------新增end-------------------

	@RequiresPermissions("files:tContractFiles:edit")
	@RequestMapping(value = "save")
	public String save(TContractFiles tContractFiles, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tContractFiles)) {
			return form(tContractFiles, model);
		}
		tContractFilesService.save(tContractFiles);
		addMessage(redirectAttributes, "保存附件成功");
		return "redirect:" + Global.getAdminPath()
				+ "/files/tContractFiles/?repage";
	}

	@RequiresPermissions("files:tContractFiles:edit")
	@RequestMapping(value = "delete")
	public String delete(TContractFiles tContractFiles,
			RedirectAttributes redirectAttributes) {
		tContractFilesService.delete(tContractFiles);
		addMessage(redirectAttributes, "删除附件成功");
		return "redirect:" + Global.getAdminPath()
				+ "/files/tContractFiles/?repage";
	}

	/**
	 * 附件列表
	 * 
	 * @param model
	 * @param BUSINESSID
	 * @return
	 */
	@RequestMapping(value = "/showfilelist/{BUSINESSID}", method = RequestMethod.GET)
	public String gofilelist(Model model, @PathVariable String BUSINESSID,
			HttpServletRequest request) {
		// String type = getCurrentRequest().getParameter("type");
		String businesstype = request.getParameter("businesstype");
		if (BUSINESSID == null || "".equals(BUSINESSID)) {
			BUSINESSID = "0";// 这样当bussinessid为空时，才不会把别的合同的附件查出
		}
		model.addAttribute("businessid", BUSINESSID);
		model.addAttribute("businesstype", businesstype);
		return "modules/files/filelist";
	}

	/**
	 * 上传附件保存到数据库
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/upload", method = RequestMethod.POST) public
	 * @ResponseBody Map<String,Object> upload(HttpServletRequest
	 * request,HttpServletResponse response,
	 * 
	 * @RequestParam("file") MultipartFile file,
	 * 
	 * @RequestParam("title") String title,
	 * 
	 * @RequestParam("taskId") String taskId,
	 * 
	 * @RequestParam("type") String type) {
	 * //post方式*****************************
	 * *************************************
	 * ***************************************** //使用MAP传递参数 Map<String,Object>
	 * map = new HashMap<String, Object>(); //理财附件校验
	 * if(StringUtils.isNotBlank(type) && type.equals(
	 * Cons.FileType.FILE_TYPE_FINANCIAL_PRODUCT )){ String fileContentType =
	 * file.getContentType(); if(StringUtils.isBlank(fileContentType) ||
	 * !("image/png".equals(fileContentType) ||
	 * "image/jpeg".equals(fileContentType))){ map.put("status", 0);
	 * map.put("message", "只支jpg和png(不透明)图片"); return map; } }
	 * map=tContractFilesService.dealUploadFile(file,title,taskId,type);
	 * if(map.get("isTrue")!=null && (Boolean)map.get("isTrue")){
	 * map.put("status", 1); }else{ map.put("status", 0); map.put("message",
	 * map.get("msg")); } return map; }
	 */

	/**
	 * 同步马赛克图片至文件服务器
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/synchroFiles", method = RequestMethod.POST)
	public  Map<String,Object> synchroFiles(String taskId, Model model, HttpServletRequest request) {
		//post方式***********************************************************************************************************
		//使用MAP传递参数
		Map<String,Object> map = new HashMap<String, Object>();
		TContractFiles tContractFiles=new TContractFiles();
		tContractFiles.setTaskId(taskId);
		List<TContractFiles> list = tContractFilesService.findList(tContractFiles); 
		for(TContractFiles tf:list){
			String root = request.getSession().getServletContext().getRealPath("");
			File file=new File(root+"/"+tf.getFilePath());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String url = Cons.Ips.IP_WFW_UPLOAND_PATH;
			RestTemplate restTemplate = new RestTemplate();
			FileBytesVo fileBytesVo=new FileBytesVo();
			fileBytesVo.setBytes(tContractFilesService.filebyte(file));
			//文件后缀名
			String suffix = tf.getFilePath().substring(tf.getFilePath().lastIndexOf(".")+1); 
			System.out.println("suffix================================="+suffix);
			fileBytesVo.setExtFile(suffix);
			//fileBytesVo.setAuthUserId("34bb841428714174b2b19106bedf726c");
			HttpEntity<FileBytesVo> entity = new HttpEntity<FileBytesVo>(fileBytesVo, headers);
			String result = restTemplate.postForObject(url, entity, String.class);
			System.out.println("result============================="+result);
			JSONObject resultJson = JSON.parseObject(result);
			if(resultJson.get("istrue")!=null && (boolean) resultJson.get("istrue")){//请求成功
				JSONObject resultJsonMap = JSON.parseObject(resultJson.getString("entity"));
				if(resultJson!=null){
					if(resultJsonMap!=null){
						map.put("filePath", resultJsonMap.getString("filePath").toString());
						map.put("fileId", resultJsonMap.getString("fileId").toString());
						String fileName = map.get("filePath").toString().substring(map.get("filePath").toString().lastIndexOf("\\")+1);//文件服务器上的名字
						tf.setFilePath(resultJsonMap.getString("filePath").toString());//更新路径为服务器路径
						tf.setFileName(fileName);
						tContractFilesService.save(tf);
						map.put("isTrue", true);
						map.put("msg", "上传成功");
					}
				}
		   }
		 if(map.get("isTrue")!=null && (Boolean)map.get("isTrue")){
			map.put("status", 1);
		 }
		 else{
			map.put("status", 0);
			map.put("message", map.get("msg"));
		 }
		
	   }
		long totle = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE task_id = '" + taskId + "'");
		long dealed = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE dealed = 2 AND task_id = '" + taskId + "'");
        map.put("totle", totle); 
        map.put("dealed", dealed); 
		return map;
	}

	/**
	 * 上传附件保存到数据库
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title,
			@RequestParam("taskId") String taskId,
			@RequestParam("type") String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		// post方式***********************************************************************************************************
		// 使用MAP传递参数
		try {
			// 判断上传文件类型 #4498
			String root = request.getSession().getServletContext().getRealPath("");
			root = root.replaceAll("\\\\", "/");
			// 理财附件校验
			if (StringUtils.isNotBlank(type) && type.equals(Cons.FileType.FILE_TYPE_FINANCIAL_PRODUCT)) {
				String fileContentType = file.getContentType();
				if (StringUtils.isBlank(fileContentType) || !("image/png".equals(fileContentType) || "image/jpeg".equals(fileContentType))) {
					map.put("status", 0); 
					map.put("message", "只支jpg和png(不透明)图片");
					return map;
				}
				tContractFilesService.uploadFile(file, title, taskId, type,request);// 如果是理财产品放在本地--供马赛克
				map.put("status", 1);
			}else {
				
				if(Cons.fdfsStatus.equals("1")){//文件服务器开
					map = tContractFilesService.dealUploadFile(file, title, taskId,type);// 上传至文件服务器
					if (map.get("isTrue") != null && (Boolean) map.get("isTrue")) {
						map.put("status", 1);
					} 
					else {
						map.put("status", 0);
						map.put("message", map.get("msg"));
					}
				}else if(Cons.fdfsStatus.equals("0")){//上传至本地
					tContractFilesService.uploadFile(file, title, taskId, type,request);
					map.put("status", 1);
				}
				return map;
			}

			// FileBytesVo vo=tContractFilesService.dealUploadFile(file);
			// System.out.println("vo.getFilePath()=================="+vo.getFilePath());

		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据Id删除附件(保存到数据库)
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "attachmentdelete", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deletefile(
			@RequestParam("ids[]") String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		tContractFilesService.delete(ids);
		map.put("status", 1);
		map.put("message", "成功删除" + ids.length + "附件");
		// 清除缓存
		return map;
	}

	// 编辑图片#4498
	@RequestMapping(value = "/toEditimg")
	public String toEditimg(TContractFiles tContractFiles, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(tContractFiles.getTaskId())) {
			tContractFiles.setTaskId("00");
		}

		String root = request.getSession().getServletContext().getRealPath("");
		root = root.replaceAll("\\\\", "/");
		List<TContractFiles> list = tContractFilesService
				.findList(tContractFiles);
		List<ContractFilesVo> listVo = new ArrayList<ContractFilesVo>();
		int childSize = 1;
		for (TContractFiles TContractFiles : list) {
			ContractFilesVo tmp = new ContractFilesVo(TContractFiles);
			tmp.setChildSize(childSize);
			tmp.setRowCount(list.size());

			String path = root + TContractFiles.getFilePath();
			String childPath = path.substring(0, path.lastIndexOf("."));
			tmp.setChildPath(childPath);

			childSize++;
			listVo.add(tmp);
		}
		// list转json
		ObjectMapper mapper = new ObjectMapper();
		String images = mapper.writeValueAsString(listVo);
		model.addAttribute("images", images);

		return "modules/files/editimage";
	}

	// 对图片进行编辑
	@RequestMapping(value = "/editimg")
	public void editimg(String xy, String path, String id, String childPath,
			Integer index, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String root = request.getSession().getServletContext().getRealPath("");
		root = root.replaceAll("\\\\", "/");
		path = root + path;
		// String img64 =
		// ImageDeal.mosaic(xy,childPath+"/"+index+".jpg",index);//马赛克处理
		// String img64 = ImageDeal.mosaic(xy,childPath+".jpg",index);//jpg OK
		String img64 = ImageDeal.mosaic(xy, path, index);
		ImageDeal.mosaicUseThread(xy, path);
		// 更新图片处理状态
		Db.update("update t_contract_files set dealed = 2 where id= '" + id
				+ "'");

		this.responseData_html(img64, response);

	}
}