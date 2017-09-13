/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.product.dao.ProductBizCfgDao;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 产品业务节点管理Service
 * @author zzm
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class ProductBizCfgService extends CrudService<ProductBizCfgDao, ProductBizCfg> {

	public ProductBizCfg get(String id) {
		return super.get(id);
	}
	
	public List<ProductBizCfg> findList(ProductBizCfg productBizCfg) {
		return super.findList(productBizCfg);
	}
	
	public Page<ProductBizCfg> findPage(Page<ProductBizCfg> page, ProductBizCfg productBizCfg) {
		return super.findPage(page, productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductBizCfg productBizCfg) {
		super.save(productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductBizCfg productBizCfg) {
		super.delete(productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void deletePLByIds(ProductBizCfg productBizCfg) {
		super.dao.deletePLByIds(productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void deletePLByBizIds(ProductBizCfg productBizCfg) {
		super.dao.deletePLByBizIds(productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void deletePLByOrgAndProduct(ProductBizCfg productBizCfg) {
		super.dao.deletePLByOrgAndProduct(productBizCfg);
	}
	
	@Transactional(readOnly = false)
	public void deletePLByOrgAndProductBizIds(ProductBizCfg productBizCfg) {
		super.dao.deletePLByOrgAndProductBizIds(productBizCfg);
	}
	
	/**
	 * @Description 根据bizCode和产品id获取业务节点配置
	 * @param bizCode
	 * @param sysCode
	 * @return
	 * @author zzm 
	 * @date 2016-5-25 下午2:22:19  
	 */
	public ProductBizCfg getByBizCode(String bizCode){
		String productId = (String) UserUtils.getCache(UserUtils.CACHE_SYSCODE);
		if(StringUtils.isBlank(productId)) return null;
		ProductBizCfg productBizCfg = new ProductBizCfg();
		TProductBiz biz = new TProductBiz();
		TProduct product = new TProduct();
		biz.setBizCode(bizCode);
		product.setId(productId);
		productBizCfg.setBiz(biz);
		productBizCfg.setProduct(product);
		//select by productId bizCode
		List<ProductBizCfg> list = findList(productBizCfg);
		if(list == null || list.isEmpty()) return null;
		return list.get(0);
	}
}