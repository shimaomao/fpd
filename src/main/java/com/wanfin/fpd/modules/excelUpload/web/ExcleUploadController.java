package com.wanfin.fpd.modules.excelUpload.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.excel.ImportExcel;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;
import com.wanfin.fpd.modules.excelUpload.service.ReadExcelService;
/**
 * 文件上传controller
 * 
 * */

@Controller
@RequestMapping(value = "${adminPath}/excelUpload/ExcelUpload")
public class ExcleUploadController extends BaseController {
	
	//注入service层
	@Autowired
	private ReadExcelService readExcel;
	
	/**
	 * 跳转到上传文件页面
	 * */
	@RequestMapping(value="/")
	public String excelUpload(){
		return"modules/excleupload/excleupload";
	}
	
	/**
	 * 接收用户的上传文件。
	 * @throws Exception 
	 * */
	@RequestMapping(value="/upload")
	//@ResponseBody
	public  String upload(@RequestParam("file") CommonsMultipartFile file, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException, Exception{
//		ModelAndView modelAndView = new ModelAndView("modules/test/ExcleUploadController");
//		Map<String, String> map = new HashMap<String, String>();
		List<Map> list1 = new ArrayList<Map>();
		List<Map> list2 = new ArrayList<Map>();
		//打印文件名
        System.out.println("fileName："+file.getOriginalFilename());
			 //调用excel解析工具解析,解析个人用户
			 ImportExcel  te = new ImportExcel(file, 1,0);
			 List<TEmployeeAndContract> employee = te.getDataList(TEmployeeAndContract.class);
		 System.out.println("个人客户数量"+employee.size());
			 //将解析的个人用户数据放入业务处理类中
			 list1 = readExcel.excelByTEmployService(employee);
			 //解析企业用户
			 ImportExcel cy = new ImportExcel(file, 1,1);
			 List<CompanyAndContract> companys = cy.getDataList(CompanyAndContract.class);
		System.out.println("企业客户数量"+companys.size());	 
			 //将解析的企业用户数据放入业务处理类中
			 list2=readExcel.excelByCompany(companys);
			 
			 System.out.println("执行完毕  前端显示"+list1);
			 System.out.println("执行完毕  前端显示"+list2);
			 
			 model.addAttribute("list1", list1);
			 model.addAttribute("list2", list2);
//			 
			 System.out.println("**********************表现层执行完毕***********************************");
			 //return modelAndView;
			 return"modules/excleupload/excleupload";
	}
}
