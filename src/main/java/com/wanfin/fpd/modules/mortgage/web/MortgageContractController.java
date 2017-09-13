/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.web;

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
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 抵押物品Controller
 * @author zzm
 * @version 2016-03-27
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/mortgage/mortgageContract")
public class MortgageContractController extends BaseController {
	@Autowired
	private MortgageContractService mortgageContractService;
//	@Autowired
//	private MortgageContractMoreService mortgageContractMoreService;
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@ModelAttribute
	public MortgageContract get(@RequestParam(required=false) String id) {
		MortgageContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mortgageContractService.get(id);
		}
		if (entity == null){
			entity = new MortgageContract();
		}
		return entity;
	}
	
	@RequiresPermissions("mortgage:mortgageContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(MortgageContract mortgageContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		mortgageContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		Page<MortgageContract> page = mortgageContractService.findPage(new Page<MortgageContract>(request, response), mortgageContract); 
		model.addAttribute("page", page);
		model.addAttribute("mortgageContract", mortgageContract);
		return "modules/mortgage/mortgageContractList";
	}

	@RequiresPermissions("mortgage:mortgageContract:view")
	@RequestMapping(value = "form")
	public String form(MortgageContract mortgageContract, Model model,RedirectAttributes redirectAttributes) {
		FModel fm = FModel.M_MORTGAGE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_MORTGAGE_ID);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", mortgageContract);
		return "modules/mortgage/mortgageContractForm";
	}
	
	
	@RequiresPermissions("mortgage:mortgageContract:view")
	@RequestMapping(value = "formForLoanType")
	public String formForLoanType(MortgageContract mortgageContract, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if(StringUtils.isBlank(mortgageContract.getId())){//附件用
			mortgageContract.setId("temp_"+IdGen.uuid());
			//mortgageContract.setStruts(3);//默认已收押状态
		}
		
		
		FModel fm = FModel.M_MORTGAGE_CONTRACT;
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_MORTGAGE_ID);
		model.addAttribute("action", fm.getAction() + "ForLoanType");
		model.addAttribute("dfFormTpl", dfFormTpl);
		
		HttpSession session = request.getSession();		
		List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
	
	    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
	    	for(int i = 0; i < mortgageContractList.size(); i++){
				MortgageContract temp = mortgageContractList.get(i);
				if(mortgageContract.getId().equals(temp.getId())){		
					mortgageContract = temp;
				}
			}	
	    }				
		
		model.addAttribute("data", mortgageContract);
		
		return "modules/mortgage/mortgageContractFormForLoanType";		
	}
	
	
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@RequestMapping(value = "saveForLoanType")
	public String saveForLoanType(MortgageContract mortgageContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		if (!beanValidator(model, mortgageContract)){
			return form(mortgageContract, model,redirectAttributes);
		}
		
		boolean updateFlag = false;//标识 添加 还是 修改 操作
		
		if (mortgageContract != null && !StringUtils.isEmpty(mortgageContract.getId())) {
			String id = mortgageContract.getId();
			if(id != null && id.contains(",")){//自定义表单有时候会出现两个重复id，因此进行过滤
				String strid[] = id.split(",");
				mortgageContract.setId(strid[0]);
			}
			
			HttpSession session = request.getSession();
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");
			
			if (mortgageContractList == null) {
				mortgageContractList = new ArrayList<MortgageContract>();
			}	
			
			mortgageContract.setScanFlag("0");
		  
			List<MortgageContract> tempList = new ArrayList<MortgageContract>();
			if (mortgageContractList != null && mortgageContractList.size() > 0) {
				for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					//修改操作
					if(mortgageContract.getId().equals(temp.getId())){		
						//tempList.remove(temp);	
						tempList.add(mortgageContract);
						updateFlag = true;
					}else {
						tempList.add(temp);
					}
					
					//循环到最后一条 如果 没有修改 就是 添加 操作
					if (updateFlag == false && (mortgageContractList.size()-1) == i) {
						tempList.add(mortgageContract);
					}
				}	
			} else {
				//第一次 添加 信息				
				tempList.add(mortgageContract);
			}
			
			mortgageContractList = new ArrayList<MortgageContract>();
			mortgageContractList = tempList;	
			
			session.setAttribute("mortgageContractList", mortgageContractList);			
			//mortgageContractService.save(mortgageContract);		
			redirectAttributes.addFlashAttribute("isClose", Global.YES);	
			addMessage(redirectAttributes, "保存抵押信息成功");
		} else {
			addMessage(redirectAttributes, "保存抵押信息失败");
		}
		
		
		model.addAttribute("mortgageContract", mortgageContract);
	
		return "redirect:"+Global.getAdminPath()+"/mortgage/mortgageContract/formForLoanType" + (updateFlag ? ("?id="+ mortgageContract.getId()) : "") ;
	
	}
	
	
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@ResponseBody
	@RequestMapping(value = "listForLoanType")
	public Map<String, Object> listForLoanType(MortgageContract mortgageContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();
		
		HttpSession session = request.getSession();		
		List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		
		if (mortgageContractList == null) {
			mortgageContractList = new ArrayList<MortgageContract>();
		}			
		
		if(mortgageContract != null && !StringUtils.isEmpty(mortgageContract.getBusinessId()) && !mortgageContract.getBusinessId().startsWith("temp_")){
			//获取数据库内容				
			List<MortgageContract> tempList = mortgageContractService.findList(mortgageContract);
			if(tempList != null && tempList.size()>0){					
				mortgageContractList.addAll(tempList);
				session.setAttribute("mortgageContractList", mortgageContractList);	
			}
		}	
		
		result.put("colist", mortgageContractList);	
		return result;
	}
	
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@ResponseBody
	@RequestMapping(value = "deleteForLoanType")
	public Map<String, Object> deleteForLoanType(MortgageContract mortgageContract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ){
		Map<String, Object> result = Maps.newHashMap();
		
		HttpSession session = request.getSession();		
		List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		
		if(mortgageContract != null && !StringUtils.isEmpty(mortgageContract.getId()) && mortgageContractList != null &&  mortgageContractList.size() > 0){
			
			for(int i = 0; i < mortgageContractList.size(); i++){
				MortgageContract temp = mortgageContractList.get(i);
				//删除操作
				if(mortgageContract.getId().equals(temp.getId())){		
					mortgageContractList.remove(temp);
					//如果 已经存在 在数据库 则删除 
					if(!mortgageContract.getId().startsWith("temp_")){
						mortgageContract.setScanFlag("0");
						mortgageContractService.delete(mortgageContract);
					}
				}				
			}	
			
			session.setAttribute("mortgageContractList", mortgageContractList);			
			
			result.put("status", 1);
			result.put("message", "删除成功");			
		}else{			
			result.put("status", 0);
			result.put("message", "删除失败");			
		}
		result.put("colist", mortgageContractList);	
		return result;
	}
	
	@RequiresPermissions("mortgage:mortgageContract:view")
	@RequestMapping(value = "view")
	public String view(MortgageContract mortgageContract, Model model,RedirectAttributes redirectAttributes) {
		TLoanContract contract = tLoanContractService.get(mortgageContract.getBusinessId());
		if(contract!=null){
				FModel fm = FModel.M_MORTGAGE_CONTRACT;
				DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_MORTGAGE_ID);
				model.addAttribute("action", fm.getAction());
				model.addAttribute("dfFormTpl", dfFormTpl);
				model.addAttribute("data", mortgageContract);
		}else{
			FModel fm = FModel.M_MORTGAGE_CONTRACT;
			DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_MORTGAGE_ID);
			model.addAttribute("action", fm.getAction());
			model.addAttribute("dfFormTpl", dfFormTpl);
			model.addAttribute("data", mortgageContract);
		}
		return "modules/mortgage/mortgageContractView";
	}
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@RequestMapping(value = "save")
	public String save(MortgageContract mortgageContract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, mortgageContract)){
			return form(mortgageContract, model,redirectAttributes);
		}
		mortgageContract.setScanFlag("0");
		if(mortgageContract.getStruts()==null){
			mortgageContract.setStruts(1);//1	新增	2	已收押	3	未借出	4	审批中	5	已借出	6	已归还	7	已解除	mortgage_status
		}
		mortgageContractService.save(mortgageContract);
		
		model.addAttribute("isClose", "1");
		addMessage(redirectAttributes, "保存抵押信息成功");
		model.addAttribute("tGuaranteeContract", mortgageContract);
		return "redirect:"+Global.getAdminPath()+"/mortgage/mortgageContract/?repage";
	}
		
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@RequestMapping(value = "saveContr")
	@ResponseBody
	public Map<String, Object> saveContr(MortgageContract mortgageContract, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (!beanValidator(model, mortgageContract)){
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramErr");;
			result.put(SvalBase.JsonKey.KEY_MSG, "请正确填写内容！");
			return result;
		}
		//会自动获取错误的ID(有冲突)，去除多余添加的ID
		mortgageContract.setId(null);
		
		mortgageContractService.save(mortgageContract);

		result.put(SvalBase.JsonKey.KEY_MSG, "保存抵押物品信息成功！");
		return result;
	}
	
	@RequiresPermissions("mortgage:mortgageContract:edit")
	@RequestMapping(value = "delete")
	public String delete(MortgageContract mortgageContract, RedirectAttributes redirectAttributes) {
		TLoanContract contract = tLoanContractService.get(mortgageContract.getBusinessId());
		if(contract == null){//如果没有关联合同就直接删除
			mortgageContract.setScanFlag("0");
			mortgageContractService.delete(mortgageContract);
			addMessage(redirectAttributes, "删除抵押物品信息成功");
		}
		else if(contract.getStatus().equals(Cons.LoanContractStatus.TO_REVIEW)){//新增才可删除
			mortgageContract.setScanFlag("0");
			mortgageContractService.delete(mortgageContract);
			addMessage(redirectAttributes, "删除抵押物品信息成功");
		}else{
			addMessage(redirectAttributes, "删除失败，关联的业务合同为新增状态才可删除押品");
		}
		return "redirect:"+Global.getAdminPath()+"/mortgage/mortgageContract/?repage";
	}
	
	

	/**
	 * 业务加载处理
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showmortgagelist/{page}", method = RequestMethod.GET)
	public String goshowlist(Model model, @PathVariable String page, HttpServletRequest request) {
		String businessTable = request.getParameter("businessTable");
		String businessId = request.getParameter("businessId");//业务的ID
		model.addAttribute("businessTable", businessTable);
		model.addAttribute("businessId", businessId);
		
		if(page != null && "mortgageContractAdd".equals(page))
		{
			MortgageContract mortgageContract = new MortgageContract();
			if(StringUtils.isNotBlank(businessId)){
				mortgageContract.setBusinessId(businessId);
			}
			if(StringUtils.isNotBlank(businessTable)){
				mortgageContract.setBusinessTable(businessTable);
			}
			
			model.addAttribute("mortgageContract", mortgageContract);
		}
		
		return "modules/mortgage/"+page;
	}
	
	
	/**
	 * grid表格查询抵押信息
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
	public Page<MortgageContract> jqgrid(
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
		
		Page<MortgageContract> pageParam = new Page<MortgageContract>(Integer.valueOf(page),Integer.valueOf(rows));
		pageParam.setOrderBy(orderBy);
		MortgageContract mortgageContract = new MortgageContract();
		mortgageContract.setBusinessTable(businessTable);
		mortgageContract.setBusinessId(businessId);
		Page<MortgageContract> data = mortgageContractService.findPage(pageParam, mortgageContract); 
		return data;
	}
	
	/**
	 * 抵押挂到合同（授信、贷款）
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
			int count = mortgageContractService.relateContract(ids,businessTable,businessId);
			map.put("status", 1);
			map.put("message", "成功关联 "+count+" 条抵押");
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
	@RequestMapping(value = "getMortEntityStatus")
	public Map<String,String> getMortEntityStatus(String id, Model model, RedirectAttributes redirectAttributes) {
		Map<String,String> map=new HashMap<String,String>();
		MortgageContract mortgageContract=get(id);
		TLoanContract contract = tLoanContractService.get(mortgageContract.getBusinessId());
		if(contract==null){
			map.put("status","no");
		}else{
			map.put("status","yes");
			map.put("mcStatus", mortgageContract.getStruts().toString());
			map.put("conStatus", contract.getStatus().toString());
		}
		
		return map;
	}
  
	/**
	 * 解除
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeMortStatus")
	public Map<String,Object> changeMortStatus(String id,String status, Model model, RedirectAttributes redirectAttributes) {
		
        Map<String,Object> map = new HashMap<String, Object>();
		
        MortgageContract mortcontract = mortgageContractService.get(id);
		map.put("status", 0);
		map.put("message", "合同异常，请联系管理员");
	
		if(mortcontract!=null&&mortcontract.getBusinessTable().equals("t_loan_contract")){
			TLoanContract contract = tLoanContractService.get(mortcontract.getBusinessId());
			if(contract!=null){
				if(contract.getStatus().equals(Cons.LoanContractStatus.CLEARED)){//已结清才可解除
					mortgageContractService.updata(id,status);
					map.put("status", 1);
					map.put("message", "成功解除");
				}else{//未结清状态下，如果是担保的话，就没有未结清状态，再次判断
                    if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)&&contract.getStatus().equals(Cons.LoanContractStatus.DB_TO_CHECK)){
                    	mortgageContractService.updata(id,status);
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
	@RequestMapping(value = "changeTakeInStatus")
	public Map<String,Object> changeTakeInStatus(String id,String status, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> map = new HashMap<String, Object>();
        MortgageContract mortcontract = mortgageContractService.get(id);
		map.put("status", 0);
		map.put("message", "合同异常，请联系管理员");
		if(mortcontract!=null&&mortcontract.getBusinessTable().equals("t_loan_contract")){
			TLoanContract contract = tLoanContractService.get(mortcontract.getBusinessId());
			if(contract!=null){
					mortgageContractService.updata(id,status);
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