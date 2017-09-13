/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.excel.ExportExcel;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.statistics.service.LoanContractService;

/**
 * 业务信息Controller
 * @author srf
 * @version 2017-03-07
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/statistics/loanContract")
public class LoanContractController extends BaseController {

	@Autowired
	private LoanContractService loanContractService;
	@Autowired
	private DfFormTplService dfFormTplService;
	
//	@ModelAttribute
//	public LoanContractVo get(@RequestParam(required=false) String id) {
//		LoanContractVo entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = loanContractService.get(id);
//		}
//		if (entity == null){
//			entity = new LoanContractVo();
//		}
//		return entity;
//	}
	/**
	 * 列表
	 * @param loanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequiresPermissions("statistics:loanContract:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(LoanContractVo loanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<LoanContractVo> list = loanContractService.findList(loanContract); 
		model.addAttribute("list", list);
		model.addAttribute("loanContract", loanContract);
		return "modules/statistics/loanContractList";
	}
	
	
	//贷款明细信息查询  下载用
	/*@RequiresPermissions("statistics:loanContract:view")*/
	@RequestMapping(value = "loanDatail")
	public String formLoanDatail(LoanContractVo loanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			List<LoanContractVo> teamList = loanContractService.findList(loanContract); 
			String fileName ="贷款明细-"+DateUtils.getDate("yyyyMMddHHmmssS") + ".xlsx";
			new ExportExcel("贷款明细", LoanContractVo.class).setDataList(teamList).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param loanContract
	 * @param model
	 * @return
	 */
	/*@RequiresPermissions("statistics:loanContract:view")*/
	@RequestMapping(value = "form")
	public String form(LoanContractVo loanContract, Model model) {
		model.addAttribute("loanContract", loanContract);
		return "modules/statistics/loanContractForm";
	}

	
	/**
	 * @description   还款计划预览
	 * @param tLoanContract
	 * @param model
	 * @param request
	 * @return
	 * @author lxh
	 */
	/*@RequiresPermissions("statistics:loanContract:view")*/
	@RequestMapping(value = "previewForm")
	public String previewForm(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());//考虑是否需要添加校验    是否存在模板
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);   
		return "modules/statistics/tLoanContractForm_bei";
		/*model.addAttribute("loanContract", loanContract);
		return "modules/statistics/tLoanContractForm";*/
	}
	
	
	
}