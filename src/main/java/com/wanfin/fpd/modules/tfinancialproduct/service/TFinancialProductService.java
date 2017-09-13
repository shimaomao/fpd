/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tfinancialproduct.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.account.entity.BsMarginTrade;
import com.wanfin.fpd.modules.account.service.BsMarginTradeService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.product.entity.ProProduct;
import com.wanfin.fpd.modules.product.entity.ProProductRel;
import com.wanfin.fpd.modules.product.service.ProProductRelService;
import com.wanfin.fpd.modules.product.service.ProProductService;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorRelDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUserRel;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.tfinancialproduct.dao.TFinancialProductDao;
import com.wanfin.fpd.modules.tfinancialproduct.entity.TFinancialProduct;

/**
 * 理财产品Service
 * @author lx
 * @version 2016-11-14
 */
@Service
@Transactional(readOnly = true)
public class TFinancialProductService extends CrudService<TFinancialProductDao, TFinancialProduct> {
	@Autowired
	private SystemService systemService;
	@Autowired
	private AuthenticatorRelDao authenticatorRelDao;
	@Autowired
	private ProProductService proProductService;
	@Autowired
	private ProProductRelService proProductRelService;
	@Autowired
	private BsMarginTradeService bsMarginTradeService;
	@Autowired
	private TContractFilesService contractFilesService;//附件表
	
	public TFinancialProduct get(String id) {
		return super.get(id);
	}
	
	public List<TFinancialProduct> findList(TFinancialProduct tFinancialProduct) {
		return super.findList(tFinancialProduct);
	}
	
	
	public List<String> findLoanContractIds(TFinancialProduct tFinancialProduct) {
		return dao.findLoanContractIds(tFinancialProduct);
	}
	
	public List<String> getLoanContractIds() {
		return dao.getLoanContractIds();
	}
	
	public Page<TFinancialProduct> findPage(Page<TFinancialProduct> page, TFinancialProduct tFinancialProduct) {
		return super.findPage(page, tFinancialProduct);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public void save(TFinancialProduct tFinancialProduct) {
		//#4498 临时ID处理，为附件用 add by shirf 20170216
		String tmpId = tFinancialProduct.getId();
		if(StringUtils.isNotBlank(tmpId) && tmpId.startsWith("tmp_")){
			tFinancialProduct.setId(null);
		}
		
		//================================
		if(UserUtils.getUser() == null || UserUtils.getUser().getCompany()==null ||StringUtils.isBlank(UserUtils.getUser().getCompany().getId())){
			return;
		}
		User tmpUser = systemService.getAdmin(UserUtils.getUser().getCompany().getId());
		
		AuthenticationUserRel authenticationUserRel = authenticatorRelDao.getBid(tmpUser.getId());
		if(authenticationUserRel == null || StringUtils.isBlank(authenticationUserRel.getAnuthenuserId())){
			return;
		}
		//================================
		
		if(tFinancialProduct.getId()!=null&&!"".equals(tFinancialProduct.getId()) 
	      && tFinancialProduct.getLoancontractIds()!=null && !"".equals(tFinancialProduct.getLoancontractIds())){
			//保存之前有主键id，证明是修改，债权信息id不为空，说明重新选择了债权信息，先把选择了的债权信息删除，后面在重新增加
			dao.deleteFinaproductLoan(tFinancialProduct);//
		}
		tFinancialProduct.setAnuthenuserId(authenticationUserRel.getAnuthenuserId());
		super.save(tFinancialProduct);
		
		//=====================================
		ProProductRel tmpProductRel = new ProProductRel();
		//tmpProductRel.setProductManagerId(tFinancialProduct.getId());
		tmpProductRel.setTfinancialProductId(tFinancialProduct.getId());
		ProProductRel productRel = proProductRelService.findProProductRel(tmpProductRel);
		
		ProProduct proProduct = new ProProduct();
		//检查主产品关联表是否存在
		if(productRel != null && StringUtils.isNotBlank(productRel.getProductId())){
			//检查主产品表是否存在
			proProduct = proProductService.get(productRel.getProductId());
		}
		
		proProduct.setAnuthenuserId(authenticationUserRel.getAnuthenuserId());
		proProduct.setType("2");
		proProduct.setName(tFinancialProduct.getProductName());
		if(StringUtils.isBlank(proProduct.getStatus())){
			proProduct.setStatus("new");
		}
		if(StringUtils.isBlank(proProduct.getPubType())){
			proProduct.setPubType("1");
		}
		proProduct.setPubEndDate(tFinancialProduct.getEndDate());
		proProductService.save(proProduct);
		
		if(productRel == null){
			productRel = new ProProductRel();
		}
		if(StringUtils.isNoneBlank(proProduct.getId(), tFinancialProduct.getId())){
			productRel.setProductId(proProduct.getId());
			//productRel.setProductManagerId(tFinancialProduct.getId());
			productRel.setTfinancialProductId(tFinancialProduct.getId());
			proProductRelService.save(productRel);
		}
		
		//====================================
		List list = new ArrayList();
		if(tFinancialProduct.getLoancontractIds()!=null && !"".equals(tFinancialProduct.getLoancontractIds())){
			String str[] = tFinancialProduct.getLoancontractIds().split(",");
			for (int i = 0; i < str.length; i++) {
				list.add(str[i]);
			}
			tFinancialProduct.setContractids(list);
			dao.insertFinaproductLoan(tFinancialProduct);
		}
		
		//#4498 临时ID处理，将对应的临时ID替换回正式的ID  add by shirf 20170216
		if(StringUtils.isNotBlank(tmpId) && tmpId.startsWith("tmp_")){
			//替换附件对应的ID
			contractFilesService.updateFileTaskId(tmpId, tFinancialProduct.getId());
		}
	}
	
	//审核保存处理,审核通过则扣除保证金
	@Transactional(readOnly = false)
	public String auditsave(TFinancialProduct tFinancialProduct) {
		if("2".equals(tFinancialProduct.getStatus())){
			String margin_ratio = Global.getApiConfig("margin_ratio");//保证金比例
			//System.out.println("系统配置的保证金比例："+margin_ratio+"%");
			if(StringUtils.isAnyBlank(margin_ratio, tFinancialProduct.getAnuthenuserId())){
				return "数据异常（不完整）！";
			}
			BigDecimal marginRatio = null;
			try{
				marginRatio = new BigDecimal(margin_ratio);
			}catch(Exception e){
				e.printStackTrace();
				return "保证金比例数据异常！";
			}
			//保证金处理
			BigDecimal marginMoney = tFinancialProduct.getAmount();
			marginMoney = marginMoney.multiply(marginRatio).divide(new BigDecimal(100)).setScale(3, BigDecimal.ROUND_HALF_UP);
			
			BsMarginTrade bsMarginTrade = new BsMarginTrade();
			bsMarginTrade.setEntityId(tFinancialProduct.getId());
			bsMarginTrade.setEntityType("1");
			bsMarginTrade.setMarginMoney(marginMoney);
			//bsMarginTrade.setFinancialProductId(tFinancialProduct.getId());
			bsMarginTrade.setAuthUserId(tFinancialProduct.getAnuthenuserId());
			
			int type = bsMarginTradeService.deductDeposit(bsMarginTrade);
			if(type == 1){
			}else if(type == 2){
				return "资金账户余额不足";
			}else if(type == 12){
				return "资金账户存在问题";
			}else if(type == 13){
				return "保证金比例异常";
			}else{
				return "处理数据异常！";
			}
		}
		this.save(tFinancialProduct);
		
		return null;
	}
	
	/**
	 * 退回保证金
	 * @param tFinancialProduct
	 */
	@Transactional(readOnly = false)
	public String returnDeposit(TFinancialProduct tFinancialProduct) {
		if(StringUtils.isAnyBlank(tFinancialProduct.getId(),tFinancialProduct.getAnuthenuserId())){
			return "数据异常！";
		}
		BsMarginTrade bsMarginTrade = new BsMarginTrade();
		bsMarginTrade.setAuthUserId(tFinancialProduct.getAnuthenuserId());
		bsMarginTrade.setEntityId(tFinancialProduct.getId());
		bsMarginTrade.setEntityType("1");
		
		int type = bsMarginTradeService.returnDeposit(bsMarginTrade);
		if(type == 1){
		}else if(type == 3){
			return "扣除记录不存在";
		}else if(type == 4){
			return "保证金未在押";
		}else if(type == 12){
			return "资金账户存在问题";
		}else{
			return "所需数据异常！";
		}
		
		return null;
	}
	
	@Transactional(readOnly = false)
	public void delete(TFinancialProduct tFinancialProduct) {
		super.delete(tFinancialProduct);
	}
	
}