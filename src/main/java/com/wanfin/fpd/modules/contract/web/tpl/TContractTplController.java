/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web.tpl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import org.springframework.web.util.HtmlUtils;

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.BaseEntity;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.FreeMarkers;
import com.wanfin.fpd.common.utils.MoneyToCn;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.IContract.IContractUkey;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.tpl.TContractTpl;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.contract.service.tpl.TContractTplService;
import com.wanfin.fpd.modules.contract.vo.TLoanContractDvo;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.gen.entity.GenTemplate;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.guarantee.vo.TGuaranteeContractDvo;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 合同模板Controller
 * @author chenh
 * @version 2016-03-30
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/tpl/tContractTpl")
public class TContractTplController extends BaseController {

	@Autowired
	private TContractTplService tContractTplService;

	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TGuaranteeContractService tGuaranteeContractService;
	
	@Autowired
	private CustomerCreditService customerCreditService;
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public TContractTpl get(@RequestParam(required=false) String id) {
		TContractTpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tContractTplService.get(id);
		}
		if (entity == null){
			entity = new TContractTpl();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:tpl:tContractTpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(TContractTpl tContractTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!UserUtils.getUser().getLoginName().equals("admin")){
			tContractTpl.setOrganId(UserUtils.getUser().getCompany().getId());
		}		
		Page<TContractTpl> page = tContractTplService.findPage(new Page<TContractTpl>(request, response), tContractTpl); 
		model.addAttribute("page", page);
		model.addAttribute("tContractTpl", tContractTpl);
		return "modules/contract/tpl/tContractTplList";
	}

	@RequiresPermissions("contract:tpl:tContractTpl:view")
	@RequestMapping(value = "form")
	public String form(TContractTpl tContractTpl, Model model) {
		model.addAttribute("tContractTpl", tContractTpl);
		return "modules/contract/tpl/tContractTplForm";
	}

	@RequiresPermissions("contract:tpl:tContractTpl:edit")
	@RequestMapping(value = "save")
	public String save(TContractTpl tContractTpl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tContractTpl)){
			return form(tContractTpl, model);
		}
		tContractTpl.setOrganId(UserUtils.getUser().getCompany().getId());
		tContractTplService.save(tContractTpl);
		addMessage(redirectAttributes, "保存合同模板成功");
		return "redirect:"+Global.getAdminPath()+"/contract/tpl/tContractTpl/?repage";
	}
	
	//获取某个特定的合同模板内容
		@ResponseBody
		@RequestMapping(value = "getTContractTpl")
		public Map<String, Object> getTContractTpl(@RequestParam("id")String id, @RequestParam("loanContractId") String loanContractId){
			Map<String, Object> result = Maps.newHashMap();
			TContractTpl tContractTpl = null;
			if(StringUtils.isNotBlank(id)){
				tContractTpl = tContractTplService.get(id);
			}
			//===============================================
			//------------------参数替换开始------------------
			if(tContractTpl != null){
				boolean toClean = false;//是否要清除所有参数内容
				String contractContent = tContractTpl.getFtlContent();
				//乙方参数
				Office office  = officeService.getOffiParam(UserUtils.getUser().getCompany().getName());
				//填充参数
				if(StringUtils.isNotBlank(contractContent) && StringUtils.isNotBlank(loanContractId)){
 					TLoanContract loanContract = tLoanContractService.get(loanContractId);
					if(loanContract != null){
						//客户信息
						//var_borrower  甲方（借款人）
						contractContent = contractContent.replace("var_borrower", loanContract.getCustomerName());
						if(StringUtils.isNotBlank(loanContract.getCustomerId())){
							if("1".equals(loanContract.getCustomerType())){//公司
								TCompany company = tCompanyService.get(loanContract.getCustomerId());
								//var_idcode  社会统一信用代码
								contractContent = contractContent.replace("var_idcode", company.getCardNum());
								//var_address  住所
								contractContent = contractContent.replace("var_address", company.getAddress());
								//var_agent   法人或委托代理人
								contractContent = contractContent.replace("var_agent", company.getSurety());
								//var_contact   联系方式
								contractContent = contractContent.replace("var_contact", company.getSuretyTelephone());
							}if("2".equals(loanContract.getCustomerType())){//个人
								TEmployee employee = tEmployeeService.get(loanContract.getCustomerId());
								//var_idcode  身份证号码或社会统一信用代码 
								contractContent = contractContent.replace("var_idcode", employee.getCardNum());
								//var_address  住所
								contractContent = contractContent.replace("var_address", employee.getCurrentLiveAddress());
								//var_agent   法人或委托代理人
								contractContent = contractContent.replace("var_agent", "/");
								//var_contact   联系方式
								contractContent = contractContent.replace("var_contact", employee.getTelephone());
							}
							
							
							
							
							//获取授信额度
							//获取产品id
							String productId = (String)UserUtils.getCache(UserUtils.CACHE_SYSCODE);
							if(StringUtils.isNotBlank(productId)){
								CustomerCredit credit = customerCreditService.getByCustomerProductId(loanContract.getCustomerId(), productId);
								try {
									if(credit != null && (DateUtils.compareTo(DateUtils.formatDate(loanContract.getApplyDate()), DateUtils.formatDate(credit.getOverDate())) < 1)){
										BigDecimal creditNumb = credit.getCredit();//授信额度
										String creditCn = MoneyToCn.number2CNMontrayUnit(creditNumb);//授信额度中文大写
										//var_creditNumb 授信额度
										contractContent = contractContent.replace("var_creditNumb", String.valueOf(creditNumb));
										//授信额度中文大写
										contractContent = contractContent.replace("var_creditCn", creditCn);
										//授信期限
										int creditMonth = DateUtils.monthBetween(credit.getCreditDate(), credit.getOverDate(), false);
										contractContent = contractContent.replace("var_creditMonth", String.valueOf(creditMonth));
									}else{
										toClean = true;
									}
								} catch (ParseException e) {
									e.printStackTrace();
									toClean = true;
								}
								
								//处理保证合同
								TGuaranteeContract guaranteeContract = new TGuaranteeContract();
								guaranteeContract.setProduct(new TProduct(productId));
								guaranteeContract.setBusinessTable("t_loan_contract");
								guaranteeContract.setBusinessId(loanContractId);
								List<TGuaranteeContract> listGuaranteeContract = tGuaranteeContractService.findList(guaranteeContract);
								int i=1;
								for(TGuaranteeContract gc:listGuaranteeContract){
									//var_gcName保证人
									contractContent = contractContent.replace("var_gcName_"+i, gc.getGuarantorName());
									//var_gcCardId身份证
									contractContent = contractContent.replace("var_gcCardId_"+i, gc.getCardNum());
									//var_gcAddress住所
									contractContent = contractContent.replace("var_gcAddress_"+i, gc.getAddress());
									//var_gcSurety法定代表人
									if(StringUtils.isNotBlank(gc.getSurety())){
										contractContent = contractContent.replace("var_gcSurety_"+i, gc.getSurety());
									}else{
										contractContent = contractContent.replace("var_gcSurety_"+i, "   /   ");
									}
									//var_gcTele联系方式
									contractContent = contractContent.replace("var_gcTele_"+i, gc.getTelephone());
									
									i++;
								}
								if(i<5){
									toClean = true;
								}
							}else{
								toClean = true;
							}
						}else{
							//var_idcode  身份证号码或社会统一信用代码 
							contractContent = contractContent.replace("var_idcode", "");
							//var_address  住所
							contractContent = contractContent.replace("var_address", "");
							//var_agent   法人或委托代理人
							contractContent = contractContent.replace("var_agent", "   /   ");
							//var_contact   联系方式
							contractContent = contractContent.replace("var_contact", "");
							//var_creditNumb 授信额度
							contractContent = contractContent.replace("var_creditNumb", "");
							//var_creditCn 授信额度中文大写
							contractContent = contractContent.replace("var_creditCn", "");
						}
						
						//替换乙方信息
						if(StringUtils.isBlank(office.getName())){
							contractContent = contractContent.replace("company", "");
						}else{
							contractContent = contractContent.replace("company", office.getName());
						}
						//地址
						if(StringUtils.isBlank(office.getAddress())){
							contractContent = contractContent.replace("idcodess", "");
						}else{
							contractContent = contractContent.replace("idcodess", office.getAddress());
						}
						
						//法人
						if(StringUtils.isBlank(office.getMaster())){
							contractContent = contractContent.replace("legalss", "");
						}else{
							contractContent = contractContent.replace("legalss", office.getMaster());
						}
						//联系方式
						if(StringUtils.isBlank(office.getPhone())){
							contractContent = contractContent.replace("phoness", "");
						}else {
							contractContent = contractContent.replace("phoness", office.getPhone());
						}
						
						//开户行
						if(StringUtils.isBlank(office.getBank())){
							contractContent = contractContent.replace("bankss", "");
						}else {
							contractContent = contractContent.replace("bankss", office.getBank());
						}
						//开户名
						if(StringUtils.isBlank(office.getAccoName())){
							contractContent = contractContent.replace("accoNamess", "");
						}else {
							contractContent = contractContent.replace("accoNamess", office.getAccoName());
						}
						//开户账户
						if(StringUtils.isBlank(office.getAccount())){
							contractContent = contractContent.replace("accountss", "");
						}else {
							contractContent = contractContent.replace("accountss", office.getAccount());
						}
						
						//var_loanAmount    贷款金额
						contractContent = contractContent.replace("var_loanAmount", loanContract.getLoanAmount());
						String loanAmoCn = "";
						if(StringUtils.isNotBlank(loanContract.getLoanAmount())){
							loanAmoCn = MoneyToCn.number2CNMontrayUnit(loanContract.getLoanAmount());
						}
						contractContent = contractContent.replace("var_loanAmoCn", loanAmoCn);
						//var_loanPeriod    贷款期限
						contractContent = contractContent.replace("var_loanPeriod", loanContract.getLoanPeriod());
						//var_loanRate    贷款利率(利率类型：年月日)
						String loanRateType = loanContract.getLoanRateType();
						if(StringUtils.isNotBlank(loanRateType) && "年".equals(loanRateType)){
							contractContent = contractContent.replace("var_loanRate", loanContract.getLoanRate());
							BigDecimal rateRate = new BigDecimal(loanContract.getLoanRate());
							rateRate = rateRate.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
							contractContent = contractContent.replace("var_loanMRate", rateRate.toString());
						}else if(StringUtils.isNotBlank(loanRateType) && "月".equals(loanRateType)){
							BigDecimal rateRate = new BigDecimal(loanContract.getLoanRate());
							rateRate = rateRate.multiply(new BigDecimal(12)).setScale(2, BigDecimal.ROUND_HALF_UP);
							contractContent = contractContent.replace("var_loanRate", rateRate.toString());
							contractContent = contractContent.replace("var_loanMRate", loanContract.getLoanRate());
						}else if(StringUtils.isNotBlank(loanRateType) && "日".equals(loanRateType)){
							BigDecimal rateRate = new BigDecimal(loanContract.getLoanRate());
							contractContent = contractContent.replace("var_loanRate", rateRate.multiply(new BigDecimal(360)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
							contractContent = contractContent.replace("var_loanMRate", rateRate.multiply(new BigDecimal(30)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						}
						
						//借款日期var_startDate
						contractContent = contractContent.replace("var_startDate", DateUtils.formatDate(loanContract.getLoanDate()));
						//到期日期var_endDate
						contractContent = contractContent.replace("var_endDate", DateUtils.formatDate(loanContract.getPayPrincipalDate()));
						
						//var_accoName    户名
						contractContent = contractContent.replace("var_accoName", loanContract.getGatheringName());
						//var_account    银行账户
						contractContent = contractContent.replace("var_account", loanContract.getGatheringNumber());
						//var_bank    开户行
						contractContent = contractContent.replace("var_bank", loanContract.getGatheringBank());
						//var_serverFee    服务费
						contractContent = contractContent.replace("var_serverFee", loanContract.getServerFee());
						
						//乙方  户名  银行账户   开户行
						
						//处理xls内容
						if("1".equals(tContractTpl.getCrosswise()))
						{
							int p=1;
							char[] charList = loanContract.getLoanAmount().toCharArray();
							for(int i=charList.length-1;i>=0; i--){
								if('.'!=charList[i]){
									//System.out.println(charList[i]);
									contractContent = contractContent.replace("v_m"+p, String.valueOf(charList[i]));
									p++;
								}
							}
							if(charList.length<9){
								toClean = true;
							}
						}
					}else{
						toClean = true;
					}
				}
				
				if(toClean){
					//清除所有的参数
					contractContent = contractContent.replace("var_borrower", "");
					//var_idcode  身份证号码或社会统一信用代码 
					contractContent = contractContent.replace("var_idcode", "");
					//var_address  住所
					contractContent = contractContent.replace("var_address", "");
					//var_agent   法人或委托代理人
					contractContent = contractContent.replace("var_agent", "   /   ");
					//var_contact   联系方式
					contractContent = contractContent.replace("var_contact", "");
					
					//var_loanAmount    贷款金额
					contractContent = contractContent.replace("var_loanAmount", "");
					contractContent = contractContent.replace("var_loanAmoCn", "");
					//var_loanPeriod    贷款期限
					contractContent = contractContent.replace("var_loanPeriod", "");
					//var_loanRate    贷款利率(利率类型：年月日)
					contractContent = contractContent.replace("var_loanRate", "");
					
					//借款日期var_startDate
					contractContent = contractContent.replace("var_startDate", "");
					//到期日期var_endDate
					contractContent = contractContent.replace("var_endDate", "");
					
					//var_accoName    户名
					contractContent = contractContent.replace("var_accoName", "");
					//var_account    银行账户
					contractContent = contractContent.replace("var_account", "");
					//var_bank    开户行
					contractContent = contractContent.replace("var_bank", "");
					//var_serverFee    服务费
					contractContent = contractContent.replace("var_serverFee", "");
					
					//var_creditNumb 授信额度
					contractContent = contractContent.replace("var_creditNumb", "");
					//var_creditCn 授信额度中文大写
					contractContent = contractContent.replace("var_creditCn", "");
					//授信期限
					contractContent = contractContent.replace("var_creditMonth", "");
					
					
					//清除 乙方参数
					
					contractContent = contractContent.replace("company", "");
					//地址
					contractContent = contractContent.replace("idcodess","");
					//法人
					contractContent = contractContent.replace("legalss", "");
					//联系方式
					contractContent = contractContent.replace("phoness", "");
					//开户行
					contractContent = contractContent.replace("bankss", "");
					//开户名
					contractContent = contractContent.replace("accoNamess","");
					//开户账户
					contractContent = contractContent.replace("accountss", "");
					
					for(int i=1; i<5 ;i++){
						//var_gcName保证人
						contractContent = contractContent.replace("var_gcName_"+i, "");
						//var_gcCardId身份证
						contractContent = contractContent.replace("var_gcCardId_"+i, "");
						//var_gcAddress住所
						contractContent = contractContent.replace("var_gcAddress_"+i, "");
						//var_gcSurety法定代表人
						contractContent = contractContent.replace("var_gcSurety_"+i, "   /   ");
						//var_gcTele联系方式
						contractContent = contractContent.replace("var_gcTele_"+i, "");
					}
					
					for(int p=0; p<10 ; p++){
						contractContent = contractContent.replace("v_m"+p, "0");
					}
				}
				
				tContractTpl.setFtlContent(contractContent);
			}
			//------------------参数替换结束------------------
			//===============================================
			if(tContractTpl != null){
				result.put("status", 1);
				result.put("message", "成功");
				result.put("contractTpl", tContractTpl);
			}else{
				result.put("status", 2);
				result.put("message", "没有对应的合同模板");
			}
			
			return result;
		}
	
	@RequiresPermissions("contract:tpl:tContractTpl:edit")
	@RequestMapping(value = "delete")
	public String delete(TContractTpl tContractTpl, RedirectAttributes redirectAttributes) {
		tContractTplService.delete(tContractTpl);
		addMessage(redirectAttributes, "删除合同模板成功");
		return "redirect:"+Global.getAdminPath()+"/contract/tpl/tContractTpl/?repage";
	}

	/**
	 * @Description 跳转查看合同
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:10
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "toView")
	public String toView(HttpServletRequest request, HttpServletResponse response, Model model, String id, String ukey) {
		TContractTpl tContractTpl = new TContractTpl();
		tContractTpl.setType(ukey);
		tContractTpl.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		List<TContractTpl> tContractTpls = tContractTplService.findList(tContractTpl);
		model.addAttribute("contractTpls", tContractTpls);
		model.addAttribute("id", id);
		model.addAttribute("ukey", ukey);
		return "/modules/contract/tpl/tContractTplListView";
	}
	
	/**
	 * @Description 跳转预览合同页面
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 下午4:13:14
	 */
	@RequiresPermissions("contract:tpl:tContractTpl:view")
	@RequestMapping(value = "view")
	public String view(HttpServletResponse response, RedirectAttributes redirectAttributes, Model model, String id, String rid, String rkey) {
		String content = "";
		StringBuffer msg = new StringBuffer();
		Map<String,Object> result = new HashMap<String, Object>();


		TContractTpl contractTpl = null;
		if(StringUtils.isNotEmpty(id)){
			contractTpl = tContractTplService.get(id);
		}else{
			msg.append("合同获取异常!");
		}
		
		GenTemplate tpl = new GenTemplate();
		tpl.setContent(contractTpl.getFtlContent());
		
		if(StringUtils.isNotEmpty(rid) && StringUtils.isNotEmpty(rkey)){
			Map<String,Object> params = null;
			if((rkey).equals(IContractUkey.LOAN)){
				params = new TLoanContractDvo().getParams(tLoanContractService.get(rid));
				content = FreeMarkers.renderString(HtmlUtils.htmlUnescape(StringUtils.trimToEmpty(tpl.getContent())), params);
			}else if((rkey).equals(IContractUkey.GUARANTEE)){
				params = new TGuaranteeContractDvo().getParams(tGuaranteeContractService.get(rid));
				content = FreeMarkers.renderString(HtmlUtils.htmlUnescape(StringUtils.trimToEmpty(tpl.getContent())), params);
			}else{
				msg.append("合同类型标识未知!");
			}
			result.put(SvalBase.JsonKey.KEY_PARAMS, params);
		}else{
			msg.append("合同数据获取异常!");
		}
		
		result.put(SvalBase.JsonKey.KEY_CONTENT, content);
		model.addAttribute(SvalBase.JsonKey.KEY_RESULT, result);
		addMessage(redirectAttributes, msg.toString());
		return "/modules/contract/tpl/tContractTplView";
	}
}