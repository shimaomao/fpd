/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.guarantee.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 担保合同Controller
 * @author zzm
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/guarantee/tGuaranteeContract")
public class TGuaranteeContractController extends BaseController {
	@Autowired
	private TGuaranteeContractService tGuaranteeContractService;
	
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	@ModelAttribute  //此controller每个方法执行前被执行
	public TGuaranteeContract get(@RequestParam(required=false) String id) {
		TGuaranteeContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tGuaranteeContractService.get(id);
		}
		if (entity == null){
			entity = new TGuaranteeContract();
		}
		return entity;
	}
	
	@RequiresPermissions("guarantee:tGuaranteeContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TGuaranteeContract tGuaranteeContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tGuaranteeContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		Page<TGuaranteeContract> page = tGuaranteeContractService.findPage(new Page<TGuaranteeContract>(request, response), tGuaranteeContract); 
		model.addAttribute("page", page);
		model.addAttribute("tGuaranteeContract", tGuaranteeContract);
		return "modules/guarantee/tGuaranteeContractList";
	}

	/*@RequiresPermissions("guarantee:tGuaranteeContract:view")
	@RequestMapping(value = "form")
	public String form(TGuaranteeContract tGuaranteeContract, Model model) {
		TLoanContract contract = tLoanContractService.get(tGuaranteeContract.getBusinessId());
		if(contract!=null){//修改操作
			if(contract.getStatus().equals(Cons.LoanContractStatus.TO_REVIEW)){//合同新增状态才可进入修改页面
		            
		           
			}
		}
		 DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_GUARANTEE_ID);
		 model.addAttribute("dfFormTpl", dfFormTpl);
		 FModel fm = FModel.M_GUARANTEE_CONTRACT;
         model.addAttribute("action", fm.getAction());
         model.addAttribute("data", tGuaranteeContract);
		return "modules/guarantee/tGuaranteeContractForm";
	}*/
	@RequiresPermissions("guarantee:tGuaranteeContract:view")
	@RequestMapping(value = "form")
	public String form(TGuaranteeContract tGuaranteeContract, Model model) {
		if(StringUtils.isBlank(tGuaranteeContract.getId())){//附件用
			tGuaranteeContract.setId("temp_"+IdGen.uuid());
		}
		
		FModel fm = FModel.M_GUARANTEE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_GUARANTEE_ID);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tGuaranteeContract);
		return "modules/guarantee/tGuaranteeContractForm";
	}
	
	
	@RequiresPermissions("guarantee:tGuaranteeContract:view")
	@RequestMapping(value = "formForLoanType")
	public String formForLoanType(TGuaranteeContract tGuaranteeContract, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if(StringUtils.isBlank(tGuaranteeContract.getId())){//附件用
			tGuaranteeContract.setId("temp_"+IdGen.uuid());			
		}
		
		
		FModel fm = FModel.M_GUARANTEE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_GUARANTEE_ID);
		model.addAttribute("action", fm.getAction() + "ForLoanType");
		model.addAttribute("dfFormTpl", dfFormTpl);
		
		HttpSession session = request.getSession();		
		List<TGuaranteeContract> tGuaranteeContractList = (List<TGuaranteeContract>)session.getAttribute("guaranteeContractList");	
	
	    if (tGuaranteeContractList != null &&  tGuaranteeContractList.size() > 0) {
	    	for(int i = 0; i < tGuaranteeContractList.size(); i++){
				TGuaranteeContract temp = tGuaranteeContractList.get(i);
				if(tGuaranteeContract.getId().equals(temp.getId())){		
					tGuaranteeContract = temp;
				}
			}	
	    }				
		
		model.addAttribute("data", tGuaranteeContract);
		
		return "modules/guarantee/tGuaranteeContractFormForLoanType";		
	}
	
	
	
	@RequiresPermissions("guarantee:tGuaranteeContract:edit")
	@RequestMapping(value = "saveForLoanType")
	public String saveForLoanType(TGuaranteeContract tGuaranteeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if (!beanValidator(model, tGuaranteeContract)){
			return form(tGuaranteeContract, model);
		}
		
	    boolean updateFlag = false;//标识 添加 还是 修改 操作 true 为修改操作
		  
		if (tGuaranteeContract != null && !StringUtils.isEmpty(tGuaranteeContract.getId())) {
			String id = tGuaranteeContract.getId();
			if(id != null && id.contains(",")){//自定义表单有时候会出现两个重复id，因此进行过滤
				String strid[] = id.split(",");
				tGuaranteeContract.setId(strid[0]);
			}
			
			HttpSession session = request.getSession();
			List<TGuaranteeContract> tGuaranteeContractList = (List<TGuaranteeContract>)session.getAttribute("guaranteeContractList");
			
			if (tGuaranteeContractList == null) {
				tGuaranteeContractList = new ArrayList<TGuaranteeContract>();
			}	
		  
			List<TGuaranteeContract> tempList = new ArrayList<TGuaranteeContract>();
			if (tGuaranteeContractList != null && tGuaranteeContractList.size() > 0) {
				for(int i = 0; i < tGuaranteeContractList.size(); i++){
					TGuaranteeContract temp = tGuaranteeContractList.get(i);
					//修改操作
					if(tGuaranteeContract.getId().equals(temp.getId())){		
						//tempList.remove(temp);		
						tempList.add(tGuaranteeContract);					
						updateFlag = true;
					}else {
						tempList.add(temp);
					}
					
					//循环到最后一条 如果 没有修改 就是 添加 操作
					if (updateFlag == false && (tGuaranteeContractList.size()-1) == i) {
						tempList.add(tGuaranteeContract);
					}
				}	
			} else {
				//第一次 添加 信息				
				tempList.add(tGuaranteeContract);
			}
			
			tGuaranteeContractList = new ArrayList<TGuaranteeContract>();
			tGuaranteeContractList = tempList;	
			
			session.setAttribute("guaranteeContractList", tGuaranteeContractList);			
			//tGuaranteeContractService.save(tGuaranteeContract);			
			redirectAttributes.addFlashAttribute("isClose", Global.YES);	
			addMessage(redirectAttributes, "保存保证信息成功");
		} else {
			addMessage(redirectAttributes, "保存保证信息失败");
		}
		
		
		model.addAttribute("tGuaranteeContract", tGuaranteeContract);
	
		return "redirect:"+Global.getAdminPath()+"/guarantee/tGuaranteeContract/formForLoanType" + (updateFlag ? ("?id="+ tGuaranteeContract.getId()) : "") ;
	 
	}
	
	
	@RequiresPermissions("guarantee:tGuaranteeContract:edit")
	@ResponseBody
	@RequestMapping(value = "listForLoanType")
	public Map<String, Object> listForLoanType(TGuaranteeContract tGuaranteeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();
		
		HttpSession session = request.getSession();		
		List<TGuaranteeContract> tGuaranteeContractList = (List<TGuaranteeContract>)session.getAttribute("guaranteeContractList");	
		
		if (tGuaranteeContractList == null) {
			tGuaranteeContractList = new ArrayList<TGuaranteeContract>();
		}			
		
		if(tGuaranteeContract != null && !StringUtils.isEmpty(tGuaranteeContract.getBusinessId()) && !tGuaranteeContract.getBusinessId().startsWith("temp_")){
			//获取数据库内容				
			List<TGuaranteeContract> tempList = tGuaranteeContractService.findList(tGuaranteeContract);
			if(tempList != null && tempList.size()>0){					
				tGuaranteeContractList.addAll(tempList);
				session.setAttribute("guaranteeContractList", tGuaranteeContractList);	
			}
		}	
		
		result.put("colist", tGuaranteeContractList);	
		return result;
	}
	
	@RequiresPermissions("guarantee:tGuaranteeContract:edit")
	@ResponseBody
	@RequestMapping(value = "deleteForLoanType")
	public Map<String, Object> deleteForLoanType(TGuaranteeContract tGuaranteeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();
		
		HttpSession session = request.getSession();		
		List<TGuaranteeContract> tGuaranteeContractList = (List<TGuaranteeContract>)session.getAttribute("guaranteeContractList");	
		
		if(tGuaranteeContract != null && !StringUtils.isEmpty(tGuaranteeContract.getId()) && tGuaranteeContractList != null &&  tGuaranteeContractList.size() > 0){
			
			for(int i = 0; i < tGuaranteeContractList.size(); i++){
				TGuaranteeContract temp = tGuaranteeContractList.get(i);
				//删除操作
				if(tGuaranteeContract.getId().equals(temp.getId())){		
					tGuaranteeContractList.remove(temp);
					
					//如果 已经存在 在数据库 则删除 
					if(!tGuaranteeContract.getId().startsWith("temp_")){						
						tGuaranteeContractService.delete(tGuaranteeContract);
					}
				}				
			}	
			
			session.setAttribute("guaranteeContractList", tGuaranteeContractList);			
			
			result.put("status", 1);
			result.put("message", "删除成功");			
		}else{			
			result.put("status", 0);
			result.put("message", "删除失败");			
		}
		result.put("colist", tGuaranteeContractList);	
		return result;
	}
	
	
	
	@RequiresPermissions("guarantee:tGuaranteeContract:view")
	@RequestMapping(value = "view")
	public String view(TGuaranteeContract tGuaranteeContract, Model model) {
		FModel fm = FModel.M_GUARANTEE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_GUARANTEE_ID);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tGuaranteeContract);
		return "modules/guarantee/tGuaranteeContractView";
	}
	
	@RequiresPermissions("guarantee:tGuaranteeContract:edit")
	@RequestMapping(value = "save")
	public String save(TGuaranteeContract tGuaranteeContract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tGuaranteeContract)){
			return form(tGuaranteeContract, model);
		}
		tGuaranteeContractService.save(tGuaranteeContract);
		model.addAttribute("isClose", "1");
		addMessage(redirectAttributes, "保存担保信息成功");
		model.addAttribute("tGuaranteeContract", tGuaranteeContract);
		return "redirect:"+Global.getAdminPath()+"/guarantee/tGuaranteeContract/?repage";
	}
	
	@RequiresPermissions("guarantee:tGuaranteeContract:edit")
	@RequestMapping(value = "delete")
	public String delete(TGuaranteeContract tGuaranteeContract, RedirectAttributes redirectAttributes) {
		tGuaranteeContractService.delete(tGuaranteeContract);
		addMessage(redirectAttributes, "删除担保信息成功");
		return "redirect:"+Global.getAdminPath()+"/guarantee/tGuaranteeContract/?repage";
	}
	
	/**
	 * 根据id获取状态
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGuaranteeContractStatus")
	public Map<String,String> getGuaranteeContract(String id, Model model, RedirectAttributes redirectAttributes) {
		Map<String,String> map=new HashMap<String,String>();
		TGuaranteeContract guaranteeContract=get(id);
		TLoanContract contract = tLoanContractService.get(guaranteeContract.getBusinessId());
		if(contract==null){
			map.put("status","no");
		}else{
			map.put("status","yes");
			map.put("conStatus", contract.getStatus().toString());
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showguaranteelist/{page}", method = RequestMethod.GET)
	public String goshowlist(Model model, @PathVariable String page, HttpServletRequest request) {
		String businessTable = request.getParameter("businessTable");
		String businessId = request.getParameter("businessId");
		model.addAttribute("businessTable", businessTable);
		model.addAttribute("businessId", businessId);
		return "modules/guarantee/"+page;
	}
	
	
	/**
	 * 
	 * @param businessTable	业务表名称
	 * @param businessId	业务id
	 * @param page			当前页
	 * @param rows			pageSize
	 * @param sidx			排序字段
	 * @param sord			排序方法（asc，desc）
	 * @return
	 */
	@RequestMapping(value = "jqgrid",method=RequestMethod.POST)
	@ResponseBody
	public Page<TGuaranteeContract> jqgrid(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=false) String businessTable,
			@RequestParam(required=false) String businessId,
			@RequestParam(required=false) String page,
	        @RequestParam(required=false) String rows,
	        @RequestParam(required=false) String sidx,
	        @RequestParam(required=false) String sord) {
		String orderBy = "";
		if(StringUtils.isNotBlank(sidx)){
			orderBy = sidx ;
			if( StringUtils.isNotBlank(sord) )
				orderBy = " "+sord;
		}
		Page<TGuaranteeContract> pageParam = new Page<TGuaranteeContract>(Integer.valueOf(page),Integer.valueOf(rows));
		pageParam.setOrderBy(orderBy);
		TGuaranteeContract tGuaranteeContract = new TGuaranteeContract();
		tGuaranteeContract.setBusinessTable(businessTable);
		tGuaranteeContract.setBusinessId(businessId);
		Page<TGuaranteeContract> data = tGuaranteeContractService.findPage(pageParam, tGuaranteeContract); 
		return data;
	}
	
	/**
	 * 保单挂到合同（授信、贷款）
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "relateContract", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> relateContract(HttpServletRequest request,
			@RequestParam("ids[]") String[] ids,
			@RequestParam("businessTable") String businessTable,
			@RequestParam("businessId") String businessId
			
			) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			int count = tGuaranteeContractService.relateContract(ids,businessTable,businessId);
			map.put("status", 1);
			map.put("message", "成功关联 "+count+" 条担保");
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		     
		return map;
	}

}