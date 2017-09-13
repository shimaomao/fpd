/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.asset.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.modules.asset.entity.Asset;
import com.wanfin.fpd.modules.asset.dao.AssetDao;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 账户资产Service
 * @author zzm
 * @version 2016-06-14
 */
@Service
@Transactional(readOnly = true)
public class AssetService extends CrudService<AssetDao, Asset> {

	public Asset get(String id) {
		return super.get(id);
	}
	
	public List<Asset> findList(Asset asset) {
		return super.findList(asset);
	}
	
	public Page<Asset> findPage(Page<Asset> page, Asset asset) {
		asset.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, asset);
	}
	
	@Transactional(readOnly = false)
	public void save(Asset asset) {
		super.save(asset);
	}
	
	@Transactional(readOnly = false)
	public void delete(Asset asset) {
		super.delete(asset);
	}

	/**
	 * @Description 获取用户资产信息
	 * @param loginName 用户登录名
	 * @return
	 * @author zzm 
	 * @date 2016-6-14 下午3:52:24  
	 */
	public Asset getByLoginName(String loginName) {
		return this.dao.getByLoginName(loginName);
	}
	
	/**
	 * @Description 增加资产
	 * @param amount
	 * @param loginName 账户
	 * @author zzm
	 * @date 2016-6-14 下午5:01:17  
	 */
	@Transactional(readOnly = false)
	public void addAsset(String loginName, BigDecimal amount){
		this.dao.addAsset(loginName, amount);
	}
	
	/**
	 * @Description 冻结部分资产
	 * @param amount	冻结的额度（amount>0 冻结，amount<0 解冻）
	 * @param loginName 账户
	 * @author zzm
	 * @date 2016-6-14 下午5:01:17  
	 */
	@Transactional(readOnly = false)
	public void freezeAsset(String loginName, BigDecimal amount) throws ServiceException{
		Asset asset = getByLoginName(loginName);
		if(asset.getAvailableBalance().compareTo(amount) < 0){
			throw new ServiceException("操作的金额超出了账户的可用余额！！");
		}
		this.dao.freezeAsset(loginName, amount);
	}
	
}