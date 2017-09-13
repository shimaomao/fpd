/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tfinancialproduct.web;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfinal.plugin.activerecord.Db;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FileType;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.tfinancialproduct.entity.TFinancialProduct;
import com.wanfin.fpd.modules.tfinancialproduct.service.TFinancialProductService;

/**
 * 理财产品Controller
 * @author lx
 * @version 2016-11-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/tfinancialproduct/tFinancialProduct")
public class TFinancialProductController extends BaseController {

	@Autowired
	private TFinancialProductService tFinancialProductService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TContractFilesService tContractFilesService;
	
	
	@ModelAttribute
	public TFinancialProduct get(@RequestParam(required=false) String id) {
		TFinancialProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tFinancialProductService.get(id);
		}
		if (entity == null){
			entity = new TFinancialProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(TFinancialProduct tFinancialProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Page<TFinancialProduct> page = null;
		if ((user != null) && (user.getCompany() != null) && (StringUtils.isNotEmpty(user.getCompany().getId()))) {
			tFinancialProduct.setOrganId(user.getCompany().getId());
			page = tFinancialProductService.findPage(new Page<TFinancialProduct>(request, response), tFinancialProduct); 
		} else{
			page = new Page<TFinancialProduct>(request, response); 
		}
		model.addAttribute("page", page);
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/tFinancialProductList";
	}

	@RequiresPermissions("tfinancialproduct:tFinancialProduct:view")
	@RequestMapping(value = "form")
	public String form(TFinancialProduct tFinancialProduct, Model model) {
		if(StringUtils.isBlank(tFinancialProduct.getId())){//新增时初始化部分值 ==null
			tFinancialProduct.setShiConversion(new BigDecimal("0"));
			tFinancialProduct.setProgress(0);
			tFinancialProduct.setKemoney(new BigDecimal("0"));
			tFinancialProduct.setZrmoney(new BigDecimal("0"));
			tFinancialProduct.setRzstatus("1"); //融资状态1:募集中,2：已流标,3：已满标, 4： 还款中,5：  转让中 ,6： 已转让 , 7： 已完成 ',
			tFinancialProduct.setStatus("0");// 产品状态:"0": 新增未审核,"1":未通过审核 , "2": 通过审核 , , "3": 发布上线, "4": 已下架 ,  ',
			tFinancialProduct.setIfRansfer(false);//默认不可转让
			
			//#4498 设置临时ID，为附件用 add by shirf 20170216
			tFinancialProduct.setId("tmp_"+IdGen.uuid());
			tFinancialProduct.setMaterialTotal(0);//#4498资料总数
			tFinancialProduct.setMaterialDeal(0); //#4498资料已经处理的数量
		}else{
			List<String> loancontractids = tFinancialProductService.findLoanContractIds(tFinancialProduct);
			//System.out.println(loancontractids);
			if(loancontractids!=null && loancontractids.size()>0){
				String contractIds = loancontractids.toString().substring(1,loancontractids.toString().length()-1).replaceAll(", ",",");
				tFinancialProduct.setLoancontractIds(contractIds);
			}
			long totle = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE task_id = '" + tFinancialProduct.getId() + "'");
			long dealed = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE dealed = 2 AND task_id = '" + tFinancialProduct.getId() + "'");
			tFinancialProduct.setMaterialTotal( totle );//#4498资料总数
			tFinancialProduct.setMaterialDeal( dealed ); //#4498资料已经处理的数量
		}
		
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/tFinancialProductForm";
	}
	
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "editImgList")
	public String editImgList(TFinancialProduct tFinancialProduct, Model model){
		model.addAttribute("fileUrl", Cons.Ips.IP_WFW_FDFS_PATH + Global.GROUP_BASE_URL);
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/editImgList";
	}
	
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:view")
	@RequestMapping(value = "showImgList")
	public String showImgList(TFinancialProduct tFinancialProduct, Model model){
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/showImgList";
	}
	
	//审核页面
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:view")
	@RequestMapping(value = "duditform")
	public String duditform(TFinancialProduct tFinancialProduct, Model model) {
		if(StringUtils.isNotBlank(tFinancialProduct.getId())){
			long totle = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE task_id = '" + tFinancialProduct.getId() + "'");
			long dealed = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE dealed = 2 AND task_id = '" + tFinancialProduct.getId() + "'");
			tFinancialProduct.setMaterialTotal( totle );//#4498资料总数
			tFinancialProduct.setMaterialDeal( dealed ); //#4498资料已经处理的数量
		}
		
		List<String> loancontractids = tFinancialProductService.findLoanContractIds(tFinancialProduct);
		if(loancontractids!=null && !"".equals(loancontractids)){
			String contractIds = loancontractids.toString().substring(1,loancontractids.toString().length()-1);
			List<TLoanContract> loanList = tLoanContractService.getLoanListsByIds("'"+contractIds.replace(", ","','")+"'");
			model.addAttribute("loanList", loanList);
		}
		tFinancialProduct.setYesOrNo("1");//默认通过
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/tFinancialProductAuditForm";
	}
	
	
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:view")
	@RequestMapping(value = "detail")
	public String detail(TFinancialProduct tFinancialProduct, Model model) {
		if(StringUtils.isNotBlank(tFinancialProduct.getId())){
			long totle = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE task_id = '" + tFinancialProduct.getId() + "'");
			long dealed = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE dealed = 2 AND task_id = '" + tFinancialProduct.getId() + "'");
			tFinancialProduct.setMaterialTotal( totle );//#4498资料总数
			tFinancialProduct.setMaterialDeal( dealed ); //#4498资料已经处理的数量
		}
		
		List<String> loancontractids = tFinancialProductService.findLoanContractIds(tFinancialProduct);//根据理财产品id查找自己的关联的业务包
		if(loancontractids!=null && !"".equals(loancontractids)){
			String contractIds = loancontractids.toString().substring(1,loancontractids.toString().length()-1);
			List<TLoanContract> loanList = tLoanContractService.getLoanListsByIds("'"+contractIds.replace(", ","','")+"'");
			model.addAttribute("loanList", loanList);
		}
		model.addAttribute("tFinancialProduct", tFinancialProduct);
		return "modules/tfinancialproduct/tFinancialProductDetailForm";
	}

	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "save")
	public String save(TFinancialProduct tFinancialProduct, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, tFinancialProduct)){
			return form(tFinancialProduct, model);
		}
		tFinancialProduct.setShiConversion(tFinancialProduct.getYearConversion());
		//检查releasesObje切除前后逗号，",1,2,"-->"1,2" start
		String rel = tFinancialProduct.getReleasesObje();
		if(rel.startsWith(",")){
			rel = new String(new StringBuffer(rel).deleteCharAt(0));
		}
        if(rel.endsWith(",")){
        	rel = new String(new StringBuffer(rel).deleteCharAt(rel.length()-1));
        }
        tFinancialProduct.setReleasesObje(rel);
        //end
		tFinancialProductService.save(tFinancialProduct);
		addMessage(redirectAttributes, "保存理财产品成功");
		return "redirect:"+Global.getAdminPath()+"/tfinancialproduct/tFinancialProduct/?repage";
	}
	
	//审核处理
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "auditsave")
	public String auditsave(TFinancialProduct tFinancialProduct, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String auditremark = tFinancialProduct.getRemarks();
		if(tFinancialProduct.getYesOrNo().equals("1")){//审核通过
			tFinancialProduct.setStatus("2");
		}else if(tFinancialProduct.getYesOrNo().equals("0")){//审核不通过
			tFinancialProduct.setStatus("1");
		}
		tFinancialProduct.setRemarks(auditremark);
		//tFinancialProductService.save(tFinancialProduct);
		//检查releasesObje切除前后逗号，",1,2,"-->"1,2" start
		String rel = tFinancialProduct.getReleasesObje();
		if(rel.startsWith(",")){
			rel = new String(new StringBuffer(rel).deleteCharAt(0));
		}
		if(rel.endsWith(",")){
		    rel = new String(new StringBuffer(rel).deleteCharAt(rel.length()-1));
		}
		tFinancialProduct.setReleasesObje(rel);
		String msg = tFinancialProductService.auditsave(tFinancialProduct);
		if(StringUtils.isBlank(msg)){
			addMessage(redirectAttributes, "审核产品成功");
		}else{
			addMessage(redirectAttributes, msg);
			duditform(tFinancialProduct, model);
		}
		return "redirect:"+Global.getAdminPath()+"/tfinancialproduct/tFinancialProduct/?repage";
	}
	
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(TFinancialProduct tFinancialProduct, RedirectAttributes redirectAttributes) {
		tFinancialProductService.delete(tFinancialProduct);
		addMessage(redirectAttributes, "删除理财产品成功");
		return "redirect:"+Global.getAdminPath()+"/tfinancialproduct/tFinancialProduct/?repage";
	}

	
	/*
	 * 资产包管理列表
	 */
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "getLoanRecord")
	public String getLoanRecord(TLoanContract tLoanContract,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String  contractIds = request.getParameter("loancontractIds");//理财产品已经选择的债权信息id
		List<String> loancontractids = tFinancialProductService.getLoanContractIds();//查找产品所有被关联的资产包
		
		if(contractIds!=null && !"".equals(contractIds) && !"null".equals(contractIds) && loancontractids.size()>0){
			String str2[] = contractIds.split(",");
			for (int i = 0; i < str2.length; i++) {
				int itemid = loancontractids.indexOf(str2[i]);
				if(itemid>=0){
					loancontractids.remove(itemid);//剔除自己已经选了的债权信息id，进行修改
				}
			}
		}
//		tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW+","+
//				Cons.LoanContractStatus.TO_SUSPENSION+","+
//				Cons.LoanContractStatus.TO_APPROVE+","+
//				Cons.LoanContractStatus.TO_SIGN+","+
//				Cons.LoanContractStatus.TO_LOAN_APPROVAL+","+
//				Cons.LoanContractStatus.DB_TO_CHARGE+","+
//				Cons.LoanContractStatus.DB_TO_LOAN+","+
//				Cons.LoanContractStatus.TO_LOAN+","+
//				Cons.LoanContractStatus.ENDED+","
//				);
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN+",");//Bug #4520
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), tLoanContract); 
		
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		model.addAttribute("loancontractids",loancontractids);
		model.addAttribute("contractIds",contractIds);
		return "modules/tfinancialproduct/loanList";
	}
	
	
	
	/**
	 **统计合同信息并同步理财附件
	 * */
	@ResponseBody
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "getContractAmount")
	public Float getContractAmount(String id, String financialProductId,Model model, RedirectAttributes redirectAttributes) {
		float allAmount = 0;
		if(id!=null&&!"".equals(id)){
			List<TLoanContract> list = tLoanContractService.getLoanListsByIds("'"+id.replace(",","','")+"'");
			for (int i = 0; i < list.size(); i++) {
				TLoanContract contract = list.get(i);
				allAmount += Float.parseFloat(contract.getLoanAmount());
			}
			//将资产信息的附件信息同步至理财产品
			List<TContractFiles> files = tContractFilesService.findListByTaskIds("'"+id.replace(",","','")+"'"); 
			for(TContractFiles file:files){
				TContractFiles tmp_file=tContractFilesService.checkFile(financialProductId,file.getFilePath());
				if(tmp_file==null){//没有同步过则同步到理财
					TContractFiles contractFile = new TContractFiles();
					contractFile.setFilePath(file.getFilePath());
					contractFile.setSourceName(file.getSourceName());
					contractFile.setTaskId(financialProductId);
					// 如果附件标题为空则取文件名(原名)作为标题
					contractFile.setTitle(file.getTitle());
					// 重命名之后的文件名
					contractFile.setFileName(file.getFileName());
					contractFile.setType(FileType.FILE_TYPE_FINANCIAL_PRODUCT);
					tContractFilesService.save(contractFile);
				}
				
			}
		}
		return allAmount;
	}
	
	/**
	 * 获取理财产品图片数量和马赛克数量
	 */
	@ResponseBody
	@RequiresPermissions("tfinancialproduct:tFinancialProduct:edit")
	@RequestMapping(value = "getFilesCount")
	public Map<String, Long> getFilesCount(String taskId, Model model, RedirectAttributes redirectAttributes){
		if(StringUtils.isNotBlank(taskId)){
			long totle = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE task_id = '" + taskId + "'");
			long dealed = Db.queryLong("SELECT count(dealed) as total FROM t_contract_files WHERE dealed = 2 AND task_id = '" + taskId + "'");
			Map<String, Long> map = new HashMap<String, Long>(); 
	        map.put("totle", totle); 
	        map.put("dealed", dealed); 
	        return map;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		String  str = "42fa38df7e0f4b1f924b37179c728eb3";
		
		List<String> str1 = new ArrayList();
		str1.add("0166f904bb474468a7f00a8253b5d22b");
		str1.add("2a33d2f8ad0e4f1ab0147a37b0fe515d");
		str1.add("42fa38df7e0f4b1f924b37179c728eb3");
		str1.add("910c1a81c080439bacf27f3597cb1b69");
		str1.add("9904efc5a69c45c28ad1c776b714e4da");
		str1.add("fb78806624cc48ce8df34e0099d2a246");
		
		String str2[] = str.split(",");
		for (int i = 0; i < str2.length; i++) {
			int itemid = str1.indexOf(str2[i]);
			if(itemid>=0){
				str1.remove(itemid);
			}
		}
System.out.println(str1);

	}
	
	
}