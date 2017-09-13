/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.pledge.web;

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
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 质押信息Controller
 * @author zzm
 * @version 2016-03-27
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/pledge/pledgeContract")
public class PledgeContractController extends BaseController {
	@Autowired
	private PledgeContractService pledgeContractService;
	
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@ModelAttribute
	public PledgeContract get(@RequestParam(required=false) String id) {
		PledgeContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pledgeContractService.get(id);
		}
		if (entity == null){
			entity = new PledgeContract();
		}
		return entity;
	}
	
	@RequiresPermissions("pledge:pledgeContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(PledgeContract pledgeContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		pledgeContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		Page<PledgeContract> page = pledgeContractService.findPage(new Page<PledgeContract>(request, response), pledgeContract); 
		model.addAttribute("page", page);
		model.addAttribute("pledgeContract", pledgeContract);
		return "modules/pledge/pledgeContractList";
	}

	/*@RequiresPermissions("pledge:pledgeContract:edit")
	@RequestMapping(value = "form")
	public String form(PledgeContract pledgeContract, Model model,RedirectAttributes redirectAttributes) {
		FModel fm = FModel.M_PLEDGE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", pledgeContract);
		return "modules/pledge/pledgeContractForm";

	}*/
	
	@RequiresPermissions("pledge:pledgeContract:view")
	@RequestMapping(value = "form")
	public String form(PledgeContract pledgeContract, Model model,RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(pledgeContract.getId())){//附件用
			pledgeContract.setId("temp_"+IdGen.uuid());
		}
		
		TLoanContract contract = tLoanContractService.get(pledgeContract.getBusinessId());
		if(contract!=null){ //修改操作
			if(contract.getStatus().equals(Cons.LoanContractStatus.TO_REVIEW)){//新增才可修改
				FModel fm = FModel.M_PLEDGE_CONTRACT;
				DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
				model.addAttribute("action", fm.getAction());
				model.addAttribute("dfFormTpl", dfFormTpl);
				model.addAttribute("data", pledgeContract);
				return "modules/pledge/pledgeContractForm";
			}else{
				addMessage(redirectAttributes, "关联的业务合同为新增状态才可修改押品信息");
			}
		}else{//添加操作
			FModel fm = FModel.M_PLEDGE_CONTRACT;
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("data", pledgeContract);
			return "modules/pledge/pledgeContractForm";
		}
		return "redirect:"+Global.getAdminPath()+"/pledge/pledgeContract/?repage";
		
	}
	
	
	@RequiresPermissions("pledge:pledgeContract:view")
	@RequestMapping(value = "formForLoanType")
	public String formForLoanType(PledgeContract pledgeContract, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if(StringUtils.isBlank(pledgeContract.getId())){//附件用
			pledgeContract.setId("temp_"+IdGen.uuid());
			//pledgeContract.setStruts(3);//默认已收押状态
		}
		
		
		FModel fm = FModel.M_PLEDGE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
		model.addAttribute("action", fm.getAction() + "ForLoanType");
		model.addAttribute("dfFormTpl", dfFormTpl);
		
		HttpSession session = request.getSession();		
		List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
	
	    if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
	    	for(int i = 0; i < pledgeContractList.size(); i++){
				PledgeContract temp = pledgeContractList.get(i);
				if(pledgeContract.getId().equals(temp.getId())){		
					pledgeContract = temp;
				}
			}	
	    }				
		
		model.addAttribute("data", pledgeContract);
		
		return "modules/pledge/pledgeContractFormForLoanType";		
	}
	
	
	@RequiresPermissions("pledge:pledgeContract:edit")
	@RequestMapping(value = "saveForLoanType")
	public String saveForLoanType(PledgeContract pledgeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if (!beanValidator(model, pledgeContract)){
			return form(pledgeContract, model,redirectAttributes);
		}
		
		boolean updateFlag = false;//标识 添加 还是 修改 操作
		   
		if (pledgeContract != null && !StringUtils.isEmpty(pledgeContract.getId())) {
			String id = pledgeContract.getId();
			if(id != null && id.contains(",")){//自定义表单有时候会出现两个重复id，因此进行过滤
				String strid[] = id.split(",");
				pledgeContract.setId(strid[0]);
			}
			
			HttpSession session = request.getSession();
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");
			
			if (pledgeContractList == null) {
				pledgeContractList = new ArrayList<PledgeContract>();
			}	
			
			pledgeContract.setScanFlag("0");
		 
			List<PledgeContract> tempList = new ArrayList<PledgeContract>();
			if (pledgeContractList != null && pledgeContractList.size() > 0) {
				for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					//修改操作
					if(pledgeContract.getId().equals(temp.getId())){		
						//tempList.remove(temp);	
						tempList.add(pledgeContract);
						updateFlag = true;
					}else {
						tempList.add(temp);
					}
					
					//循环到最后一条 如果 没有修改 就是 添加 操作
					if (updateFlag == false && (pledgeContractList.size()-1) == i) {
						tempList.add(pledgeContract);
					}
				}	
			} else {
				//第一次 添加 信息				
				tempList.add(pledgeContract);
			}
			
			pledgeContractList = new ArrayList<PledgeContract>();
			pledgeContractList = tempList;	
			
			session.setAttribute("pledgeContractList", pledgeContractList);			
		
			redirectAttributes.addFlashAttribute("isClose", Global.YES);			
			addMessage(redirectAttributes, "保存质押信息成功");
			
		} else {
			addMessage(redirectAttributes, "保存质押信息失败");
		}
		
		model.addAttribute("pledgeContract", pledgeContract);

		return "redirect:"+Global.getAdminPath()+"/pledge/pledgeContract/formForLoanType" + (updateFlag ? ("?id="+ pledgeContract.getId()) : "") ;

	}
	
	
	@RequiresPermissions("pledge:pledgeContract:edit")
	@ResponseBody
	@RequestMapping(value = "listForLoanType")
	public Map<String, Object> listForLoanType(PledgeContract pledgeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();		
		HttpSession session = request.getSession();		
		
		List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");			
		if (pledgeContractList == null) {
			pledgeContractList = new ArrayList<PledgeContract>();
		}	
		
		if(pledgeContract != null && !StringUtils.isEmpty(pledgeContract.getBusinessId()) && !pledgeContract.getBusinessId().startsWith("temp_")){
			//获取数据库质押内容				
			List<PledgeContract> pledgeList = pledgeContractService.findList(pledgeContract);
			if(pledgeList != null && pledgeList.size()>0){					
				pledgeContractList.addAll(pledgeList);
				session.setAttribute("pledgeContractList", pledgeContractList);	
			}
		}		
		result.put("colist", pledgeContractList);	
		return result;
	}
	
	@RequiresPermissions("pledge:pledgeContract:edit")
	@ResponseBody
	@RequestMapping(value = "deleteForLoanType")
	public Map<String, Object> deleteForLoanType(PledgeContract pledgeContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();
		
		HttpSession session = request.getSession();		
		List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
		
		if(pledgeContract != null && !StringUtils.isEmpty(pledgeContract.getId()) && pledgeContractList != null &&  pledgeContractList.size() > 0){
			
			for(int i = 0; i < pledgeContractList.size(); i++){
				PledgeContract temp = pledgeContractList.get(i);
				//删除操作
				if(pledgeContract.getId().equals(temp.getId())){
					pledgeContractList.remove(temp);
					//如果 已经存在 在数据库 则删除 
					if(!pledgeContract.getId().startsWith("temp_")){
						pledgeContract.setScanFlag("0");
						pledgeContractService.delete(pledgeContract);
					}					
				}				
			}	
			
			session.setAttribute("pledgeContractList", pledgeContractList);			
			
			result.put("status", 1);
			result.put("message", "删除成功");			
		}else{			
			result.put("status", 0);
			result.put("message", "删除失败");			
		}
		result.put("colist", pledgeContractList);	
		return result;
	}
	
	
	
	@RequiresPermissions("pledge:pledgeContract:view")
	@RequestMapping(value = "detail")
	public String detail(PledgeContract pledgeContract, Model model,RedirectAttributes redirectAttributes) {
		FModel fm = FModel.M_PLEDGE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", pledgeContract);
		return "modules/pledge/pledgeContractDetail";
	}
	
	

	@RequiresPermissions("pledge:pledgeContract:edit")
	@RequestMapping(value = "save")
	public String save(PledgeContract pledgeContract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pledgeContract)){
			return form(pledgeContract,model,redirectAttributes);
		}
		pledgeContract.setScanFlag("0");
		pledgeContractService.save(pledgeContract);
		
		model.addAttribute("isClose", "1");
		addMessage(redirectAttributes, "保存质押信息成功");
		model.addAttribute("pledgeContract", pledgeContract);
		return "redirect:"+Global.getAdminPath()+"/pledge/pledgeContract/?repage";
	}
	
	@RequiresPermissions("pledge:pledgeContract:edit")
	@RequestMapping(value = "saveContr")
	@ResponseBody
	public Map<String, Object> saveContr(PledgeContract pledgeContract, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (!beanValidator(model, pledgeContract)){
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramErr");;
			result.put(SvalBase.JsonKey.KEY_MSG, "请正确填写内容！");
			return result;
		}
		//会自动获取错误的ID(有冲突)，去除多余添加的ID
		pledgeContract.setId(null);
		
		pledgeContractService.save(pledgeContract);
		
		result.put(SvalBase.JsonKey.KEY_MSG, "保存质押信息成功！");
		return result;
	}
	
	@RequiresPermissions("pledge:pledgeContract:edit")
	@RequestMapping(value = "delete")
	public String delete(PledgeContract pledgeContract, RedirectAttributes redirectAttributes) {
		TLoanContract contract = tLoanContractService.get(pledgeContract.getBusinessId());
		if(contract == null){//如果没有关联合同就直接删除
			pledgeContract.setScanFlag("0");
			pledgeContractService.delete(pledgeContract);
			addMessage(redirectAttributes, "删除质押信息成功");
		}
		else if(contract.getStatus().equals(Cons.LoanContractStatus.TO_REVIEW)){//新增才可删除
			pledgeContract.setScanFlag("0");
			pledgeContractService.delete(pledgeContract);
			addMessage(redirectAttributes, "删除质押信息成功");
		}else{
			addMessage(redirectAttributes, "删除失败，关联的业务合同为新增状态才可删除押品");
		}
		return "redirect:"+Global.getAdminPath()+"/pledge/pledgeContract/?repage";
	}

	
	/**
	 * 根据id获取状态
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPledgeContractStatus")
	public Map<String,String> getPledgeContract(String id, Model model, RedirectAttributes redirectAttributes) {
		Map<String,String> map=new HashMap<String,String>();
		PledgeContract pledgeContract=get(id);
		TLoanContract contract = tLoanContractService.get(pledgeContract.getBusinessId());
		if(contract==null){
			map.put("status","no");
		}else{
			map.put("status","yes");
			map.put("conStatus", contract.getStatus().toString());
			map.put("pleStatus", pledgeContract.getStruts().toString());
		}
		
		return map;
	}
	

	/**
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showpledgelist/{page}", method = RequestMethod.GET)
	public String goshowlist(Model model, @PathVariable String page, HttpServletRequest request) {
		String businessTable = request.getParameter("businessTable");
		String businessId = request.getParameter("businessId");
		model.addAttribute("businessTable", businessTable);
		model.addAttribute("businessId", businessId);
		
		if(page != null && "pledgeContractAdd".equals(page)){
			PledgeContract pledgeContract = new PledgeContract();
			if(StringUtils.isNotBlank(businessId)){
				pledgeContract.setBusinessId(businessId);
			}
			if(StringUtils.isNotBlank(businessTable)){
				pledgeContract.setBusinessTable(businessTable);
			}
			model.addAttribute("pledgeContract", pledgeContract);
		}
		
		return "modules/pledge/"+page;
	}
	
	
	/**
	 * grid表格查询质押信息
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
	public Page<PledgeContract> jqgrid(
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
		Page<PledgeContract> pageParam = new Page<PledgeContract>(Integer.valueOf(page),Integer.valueOf(rows));
		pageParam.setOrderBy(orderBy);
		PledgeContract pledgeContract = new PledgeContract();
		pledgeContract.setBusinessTable(businessTable);
		pledgeContract.setBusinessId(businessId);
		Page<PledgeContract> data = pledgeContractService.findPage(pageParam, pledgeContract); 
		return data;
	}
	
	/**
	 * 质押挂到合同（授信、贷款）
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
			int count = pledgeContractService.relateContract(ids,businessTable,businessId);
			map.put("status", 1);
			map.put("message", "成功关联 "+count+" 条质押");
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		     
		return map;
	}
	
	
	/**
	 * 根据id获取抵押物的状态
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPledgeEntityStatus")
	public Integer getPledgeEntityStatus(String id, Model model, RedirectAttributes redirectAttributes) {
		return get(id).getStruts();
	}
  
	/**
	 * 解除
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changePledgeStatus")
	public Map<String,Object> changePledgeStatus(String id,String status, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		PledgeContract plegecontract = pledgeContractService.get(id);
		map.put("status", 0);
		map.put("message", "合同异常，请联系管理员");
		
		if(plegecontract!=null&&plegecontract.getBusinessTable().equals("t_loan_contract")){
			TLoanContract contract = tLoanContractService.get(plegecontract.getBusinessId());
			if(contract!=null){
				//解除操作
				if(contract.getStatus().equals(Cons.LoanContractStatus.CLEARED)){//已结清才可解除
					pledgeContractService.updata(id,status);
					map.put("status", 1);
					map.put("message", "成功解除");
				}else{
					
					 if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)&&contract.getStatus().equals(Cons.LoanContractStatus.DB_TO_CHECK)){
						    pledgeContractService.updata(id,status);
	    					map.put("status", 1);
	    					map.put("message", "成功解除");
						}else{
							map.put("status", 2);
							if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
								map.put("message", "只有已收费的担保合同才可以解除押品");
							}else{
								map.put("message", "只有已结清的合同才可以解除押品");
							}
						}
				}
			}
		}
		return map;
	}
	/**
	 * 收押
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "takeInto")
	public Map<String,Object> takeInto(String id,String status, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String, Object>();
		PledgeContract plegecontract = pledgeContractService.get(id);
		map.put("status", 0);
		map.put("message", "合同异常，请联系管理员");
		if(plegecontract!=null){
			TLoanContract contract = tLoanContractService.get(plegecontract.getBusinessId());
			if(contract!=null){
			   pledgeContractService.updata(id,status);
			   map.put("status", 1);
			   map.put("message", "成功收押");
			}else{
				map.put("status", 2);
				map.put("message", "收押失败，关联贷款合同不存在");
			}
		}
		return map;
	}
}