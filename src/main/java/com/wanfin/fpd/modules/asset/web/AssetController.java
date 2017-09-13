/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.asset.web;

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
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.asset.entity.Asset;
import com.wanfin.fpd.modules.asset.service.AssetService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.trading.entity.TradingRecord;
import com.wanfin.fpd.modules.trading.service.TradingRecordService;

/**
 * 账户资产Controller
 * @author zzm
 * @version 2016-06-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/asset/asset")
public class AssetController extends BaseController {

	@Autowired
	private AssetService assetService;
	
	@Autowired
	private TradingRecordService tradingRecordService;
	
	@ModelAttribute
	public Asset get(@RequestParam(required=false) String id) {
		Asset entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = assetService.get(id);
		}
		if (entity == null){
			entity = new Asset();
		}
		return entity;
	}
	
	@RequiresPermissions("asset:asset:view")
	@RequestMapping(value = {"list", ""})
	public String list(Asset asset, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Asset> page = assetService.findPage(new Page<Asset>(request, response), asset); 
		model.addAttribute("page", page);
		model.addAttribute("asset", asset);
		return "modules/asset/assetList";
	}
	
	@RequiresPermissions("asset:asset:view")
	@RequestMapping(value = "userAsset")
	public String userAsset(TradingRecord tradingRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		//账户资产
		User user = UserUtils.getUser();				
		Asset userAsset = assetService.getByLoginName(user.getLoginName());
		model.addAttribute("asset", userAsset);
		
		//资产交易记录
		tradingRecord.setLoginName(UserUtils.getUser().getLoginName());
		Page<TradingRecord> page = tradingRecordService.findPage(new Page<TradingRecord>(request, response), tradingRecord); 
		model.addAttribute("page", page);
		model.addAttribute("tradingRecord", tradingRecord);
		return "modules/asset/userAsset";
	}

	@RequiresPermissions("asset:asset:view")
	@RequestMapping(value = "form")
	public String form(Asset asset, Model model) {
		model.addAttribute("asset", asset);
		return "modules/asset/assetForm";
	}

	@RequiresPermissions("asset:asset:edit")
	@RequestMapping(value = "save")
	public String save(Asset asset, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, asset)){
			return form(asset, model);
		}
		assetService.save(asset);
		addMessage(redirectAttributes, "保存账户资产成功");
		return "redirect:"+Global.getAdminPath()+"/asset/asset/?repage";
	}
	
	@RequiresPermissions("asset:asset:edit")
	@RequestMapping(value = "delete")
	public String delete(Asset asset, RedirectAttributes redirectAttributes) {
		assetService.delete(asset);
		addMessage(redirectAttributes, "删除账户资产成功");
		return "redirect:"+Global.getAdminPath()+"/asset/asset/?repage";
	}

}