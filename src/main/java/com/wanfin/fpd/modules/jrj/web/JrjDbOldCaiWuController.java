/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.web;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.service.TBalanceSheepService;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjInterestsChang;
import com.wanfin.fpd.modules.jrj.entity.JrjOldBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCost;
import com.wanfin.fpd.modules.jrj.entity.JrjOldProfit;
import com.wanfin.fpd.modules.jrj.entity.JrjOldReport;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjInterestsChangService;
import com.wanfin.fpd.modules.jrj.service.JrjOldBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjOldCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjOldCostService;
import com.wanfin.fpd.modules.jrj.service.JrjOldProfitService;
import com.wanfin.fpd.modules.jrj.service.JrjOldReportService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.jrj.util.DbCwUtil;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 担保财务报表表Controller
 * @author xzt
 * @version 2016-05-17
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/db/oldcaiWu")
public class JrjDbOldCaiWuController extends BaseController {

	@Autowired
	private JrjOldBalanceSheepService jrjBalanceSheepService;
	
	@Autowired
	private JrjOldProfitService jrjProfitService;
	
	@Autowired
	private JrjOldCashFlowService jrjCashFlowService;
	
	@Autowired
	private JrjOldCostService jrjOldCostService;	

	@Autowired
	private JrjOldReportService jrjOldReportService;
	
	@Autowired
	private JrjInterestsChangService jrjInterestsChangService;
	
	
	@RequestMapping(value = "form")
	public String form(Model model) {	
		String  date = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
		Calendar c = Calendar.getInstance();    
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    date= format.format(c.getTime());
	    model.addAttribute("date",date);
		return "modules/jrj/tOldDbCaiwuForm";
	}	
	
	/**
	 * 导入担保财务报表excel数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody 
    @RequestMapping(value = "save", method=RequestMethod.POST)
    public Map<String,Object> importFile(MultipartFile file,String date, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		Office company = user.getCompany();	
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return null;
		}
		try {
	    	String fileName =file.getOriginalFilename();
	    	//解析旧报表文件
			Map<String,Object> entityMap = DbCwUtil.readOldExcel(file.getInputStream(),fileName);
			if(entityMap != null && entityMap.size() > 0){
    			for (String key : entityMap.keySet()) {
    				if("sheep".equals(key)){
    					//先查找报出的月份是否已经报备
    					JrjOldBalanceSheep findEntity = new JrjOldBalanceSheep();
    					findEntity.setSubmitDate(date);
    					findEntity.setCompanyId(company.getId());
    					List<JrjOldBalanceSheep> list = jrjBalanceSheepService.findListBySubmitDate(findEntity);
    					if(list != null && list.size() > 0){
    						for(JrjOldBalanceSheep sheep: list){
    							jrjBalanceSheepService.delete(sheep);
    						}
    					}    					
    					JrjOldBalanceSheep temp = (JrjOldBalanceSheep) entityMap.get(key);
    					temp.setCompanyId(company.getId());
    					temp.setScanFlag("0");
    					temp.setSubmitDate(date);
    					temp.setFitOut(user.getName());
    					temp.setReportName(user.getCompany().getName());
    					temp.setCompanyName(company.getName());
    					temp.setCreateBy(user);	
    					jrjBalanceSheepService.save(temp);    					
    				}else if("profit".equals(key)){    					
    					//先查找报出的月份是否已经报备
    					JrjOldProfit findEntity = new JrjOldProfit();
    					findEntity.setSubmitDate(date);
    					findEntity.setCompanyId(company.getId());
    					List<JrjOldProfit> list = jrjProfitService.findListBySubmitDate(findEntity);
    					if(list != null && list.size() > 0){
    						for(JrjOldProfit sheep: list){
    							jrjProfitService.delete(sheep);
    						}
    					}    					
    					
    					JrjOldProfit temp = (JrjOldProfit) entityMap.get(key);
    					temp.setCompanyId(company.getId());
    					temp.setCompanyName(company.getName());
    					temp.setCompanyId(company.getId());
    					temp.setScanFlag("0");
    					temp.setSubmitDate(date);
    					temp.setFitOut(user.getName());
    					temp.setReportName(user.getCompany().getName());
    					temp.setCompanyName(company.getName());
    					temp.setCreateBy(user);	
    					jrjProfitService.save(temp);
    					
    				}else if("flow".equals(key)){
    					JrjOldCashFlow findEntity = new JrjOldCashFlow();
    					findEntity.setSubmitDate(date);
    					findEntity.setCompanyId(company.getId());
    					List<JrjOldCashFlow> list = jrjCashFlowService.findListBySubmitDate(findEntity);
    					if(list != null && list.size() > 0){
    						for(JrjOldCashFlow sheep: list){
    							jrjCashFlowService.delete(sheep);
    						}
    					}
    					
    					JrjOldCashFlow temp = (JrjOldCashFlow) entityMap.get(key);
    					temp.setCompanyId(company.getId());
    					temp.setCompanyName(company.getName());
    					temp.setCompanyId(company.getId());
    					temp.setScanFlag("0");
    					temp.setSubmitDate(date);
    					temp.setFitOut(user.getName());
    					temp.setReportName(user.getCompany().getName());
    					temp.setCompanyName(company.getName());
    					temp.setCreateBy(user);	
    					jrjCashFlowService.save(temp);    					
    				}else if("cost".equals(key)){
    					JrjOldCost findEntity = new JrjOldCost();
    					findEntity.setSubmitDate(date);
    					findEntity.setCompanyId(company.getId());
    					List<JrjOldCost> list = jrjOldCostService.findListBySubmitDate(findEntity);
    					if(list != null && list.size() > 0){
    						for(JrjOldCost sheep: list){
    							jrjOldCostService.delete(sheep);
    						}
    					}
    					JrjOldCost temp = (JrjOldCost) entityMap.get(key);
    					temp.setCompanyId(company.getId());
    					temp.setCompanyName(company.getName());
    					temp.setCompanyId(company.getId());
    					temp.setScanFlag("0");
    					temp.setFitOut(user.getName());
    					temp.setSubmitDate(date);
    					temp.setReportName(user.getCompany().getName());
    					temp.setCompanyName(company.getName());
    					temp.setCreateBy(user);	
    					jrjOldCostService.save(temp);    					
    				}else if("report".equals(key)){
    					JrjOldReport findEntity = new JrjOldReport();
    					findEntity.setSubmitDate(date);
    					findEntity.setCompanyId(company.getId());
    					List<JrjOldReport> list = jrjOldReportService.findListBySubmitDate(findEntity);
    					if(list != null && list.size() > 0){
    						for(JrjOldReport sheep: list){
    							jrjOldReportService.delete(sheep);
    						}
    					}
    					JrjOldReport temp = (JrjOldReport) entityMap.get(key);
    					temp.setCompanyId(company.getId());
    					temp.setCompanyName(company.getName());
    					temp.setCompanyId(company.getId());
    					temp.setScanFlag("0");
    					temp.setFitOut(user.getName());
    					temp.setSubmitDate(date);
    					temp.setReportName(user.getCompany().getName());
    					temp.setCompanyName(company.getName());
    					temp.setCreateBy(user);	
    					jrjOldReportService.save(temp);    					
    				}    	    				
	    		 }	 
    			
    			map.put("status","1");			
    			map.put("message","导入旧担保报表成功！");				
			}		
		} catch (Exception e) {
			//addMessage(redirectAttributes, "导入在园企业失败！失败信息："+e.getMessage());
			map.put("status","0");			
			map.put("message","导入旧担保报表失败！");
		}
		return map;
    }	
	
	
	
	/**
	 * 查看旧的资产负债表
	 * @param tBalanceSheep
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "lookSheep")
	public String lookSheep(JrjOldBalanceSheep tBalanceSheep, Model model){		
		if(tBalanceSheep != null && tBalanceSheep.getId() != null){
			tBalanceSheep = jrjBalanceSheepService.get(tBalanceSheep.getId());
		}		
		model.addAttribute("tBalanceSheep", tBalanceSheep);
		return "modules/jrj/tOldBalanceSheepForm";
	}
	
	

}