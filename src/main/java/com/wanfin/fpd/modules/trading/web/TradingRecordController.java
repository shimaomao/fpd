/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.trading.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.trading.entity.TradingRecord;
import com.wanfin.fpd.modules.trading.service.TradingRecordService;

/**
 * 交易记录Controller
 * @author zzm
 * @version 2016-06-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/trading/tradingRecord")
public class TradingRecordController extends BaseController {

	@Autowired
	private TradingRecordService tradingRecordService;
	
	@ModelAttribute
	public TradingRecord get(@RequestParam(required=false) String id) {
		TradingRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tradingRecordService.get(id);
		}
		if (entity == null){
			entity = new TradingRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("trading:tradingRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(TradingRecord tradingRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TradingRecord> page = tradingRecordService.findPage(new Page<TradingRecord>(request, response), tradingRecord); 
		model.addAttribute("page", page);
		model.addAttribute("tradingRecord", tradingRecord);
		return "modules/trading/tradingRecordList";
	}

	@RequiresPermissions("trading:tradingRecord:view")
	@RequestMapping(value = "form")
	public String form(TradingRecord tradingRecord, Model model) {
		model.addAttribute("tradingRecord", tradingRecord);
		return "modules/trading/tradingRecordForm";
	}

	@RequiresPermissions("trading:tradingRecord:edit")
	@RequestMapping(value = "save")
	public String save(TradingRecord tradingRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tradingRecord)){
			return form(tradingRecord, model);
		}
		tradingRecordService.save(tradingRecord);
		addMessage(redirectAttributes, "保存交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/trading/tradingRecord/?repage";
	}
	
	@RequiresPermissions("trading:tradingRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(TradingRecord tradingRecord, RedirectAttributes redirectAttributes) {
		tradingRecordService.delete(tradingRecord);
		addMessage(redirectAttributes, "删除交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/trading/tradingRecord/?repage";
	}
	
	/**
	 * 跳转到充值
	 */
	@RequestMapping(value = "toRecharge")
	public String toRecharge(TradingRecord tradingRecord, Model model) {
		model.addAttribute("tradingRecord", tradingRecord);
		return "modules/trading/rechargeForm";
	}

	/**
	 * 充值
	 */
	@RequestMapping(value = "recharge")
	public String recharge(TradingRecord tradingRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tradingRecord)){
			return form(tradingRecord, model);
		}
		tradingRecord.setTrader("银行账户");
		tradingRecord.setTradingTime(new Date());
		//tradingRecord.setTradingWay("网银转账");
		tradingRecord.setTradingType(Cons.TradingType.RECHARGE);
		tradingRecord.setLoginName(UserUtils.getUser().getLoginName());
		tradingRecord.setSeqNo(String.valueOf(IdGen.randomLong()));
		tradingRecordService.save(tradingRecord);
		addMessage(redirectAttributes, "保存交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/asset/asset/userAsset?repage";
	}
	
	/**
	 * 跳转到提现
	 */
	@RequestMapping(value = "toWithdraw")
	public String toWithdraw(TradingRecord tradingRecord, Model model) {
		model.addAttribute("tradingRecord", tradingRecord);
		return "modules/trading/withdrawForm";
	}

	/**
	 * 提现
	 */
	@RequestMapping(value = "withdraw")
	public String withdraw(TradingRecord tradingRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tradingRecord)){
			return form(tradingRecord, model);
		}
		tradingRecord.setTrader("银行账户");
		tradingRecord.setTradingTime(new Date());
		tradingRecord.setTradingWay("网银转账");
		tradingRecord.setTradingType(Cons.TradingType.WITHDRAW);
		tradingRecord.setLoginName(UserUtils.getUser().getLoginName());
		tradingRecord.setSeqNo(String.valueOf(IdGen.randomLong()));
		
		tradingRecord.setAmount(tradingRecord.getAmount().negate());//提现，交易金额是负值
		tradingRecordService.save(tradingRecord);
		addMessage(redirectAttributes, "保存交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/asset/asset/userAsset?repage";
	}
}