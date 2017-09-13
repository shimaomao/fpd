/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.service.tpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.SerialUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.dao.tpl.DfFormTplDao;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.init.vo.SysInitFormtplVo;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 自定义表单Service
 * @author chenh
 * @version 2016-05-04
 */
@Service
@Transactional(readOnly = true)
public class DfFormTplService extends CrudService<DfFormTplDao, DfFormTpl> {
	
	@Autowired
	private ProductBizCfgService productBizCfgService;

	public DfFormTpl get(String id) {
		return super.get(id);
	}
	
	public List<DfFormTpl> findList(DfFormTpl dfFormTpl) {
		return super.findList(dfFormTpl);
	}
	public List<DfFormTpl> findTplList(DfFormTpl dfFormTpl) {
		return this.dao.findTplList(dfFormTpl);
	}
	public List<DfFormTpl> findRelTplList(DfFormTpl dfFormTpl) {
		return this.dao.findRelTplList(dfFormTpl);
	}
	public List<DfFormTpl> findSysTplList(DfFormTpl dfFormTpl) {
		dfFormTpl.setModsub(Cons.FModel.M_TPL.getKey());
		dfFormTpl.setOrganId(Cons.SysDF.DF_SUPER_OFFICE);
		return findList(dfFormTpl);
	}
	
	public Page<DfFormTpl> findPage(Page<DfFormTpl> page, DfFormTpl dfFormTpl) {
		return super.findPage(page, dfFormTpl);
	}
	
	/**
	 * @Description 获取表单模板
	 * @param page
	 * @param dfFormTpl
	 * @return
	 */
	public Page<DfFormTpl> findTplPage(Page<DfFormTpl> page, DfFormTpl dfFormTpl) {
		dfFormTpl.setPage(page);
		dfFormTpl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		page.setList(findTplList(dfFormTpl));
		return page;
	}
	
	/**
	 * @Description 获取关联表单模板
	 * @param page
	 * @param dfFormTpl
	 * @return
	 */
	public Page<DfFormTpl> findRelTplPage(Page<DfFormTpl> page, DfFormTpl dfFormTpl) {
		dfFormTpl.setPage(page);
		dfFormTpl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		page.setList(findRelTplList(dfFormTpl));
		return page;
	}
	
	/**
	 * @Description 获取全局系统表单模板
	 * @param page
	 * @param dfFormTpl
	 * @return
	 */
	public Page<DfFormTpl> findSysTplPage(Page<DfFormTpl> page, DfFormTpl dfFormTpl) {
		dfFormTpl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		page.setList(findSysTplList(dfFormTpl));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(DfFormTpl dfFormTpl) {
		if(StringUtils.isEmpty(dfFormTpl.getModsub())){
			dfFormTpl.setModsub(Cons.FModel.M_TPL.getKey());
		}

		if(StringUtils.isEmpty(dfFormTpl.getOrganId())){
			dfFormTpl.setOrganId(UserUtils.getUser().getCompany().getId());
		}
		
		if(StringUtils.isEmpty(dfFormTpl.getName())){
			if(StringUtils.isNotEmpty(dfFormTpl.getModel())){
				dfFormTpl.setName(SerialUtils.getSerialNo(StringUtils.upperCase(dfFormTpl.getModel()), SerialUtils.FORMAT_YMDH));
			}
		}
		super.save(dfFormTpl);
	}
	
	@Transactional(readOnly = false)
	public void delete(DfFormTpl dfFormTpl) {
		super.delete(dfFormTpl);
	}
	@Transactional(readOnly = false)
	public void deleteByRelId(String relId) {
		this.dao.deleteByRelId(relId);
	}

	/**
	 * @Description 根据bizCode和产品id获取表单
	 * @param bizCode
	 * @return
	 * @author zzm 
	 * @date 2016-5-19 下午2:05:20  
	 */
	public DfFormTpl getByBizCode(String bizCode){
		ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(bizCode);
		if(productBizCfg == null) return null;
		DfFormTpl dfFormTpl = new DfFormTpl();
		dfFormTpl.setModel(productBizCfg.getBiz().getCategory());
		dfFormTpl.setRelId(productBizCfg.getId());
		return this.getByModelRelId(dfFormTpl);
	}
	
	/**
	 * @Description 查询产品所有业务节点配置的表单
	 * @param productId
	 * @return
	 * @author zzm
	 * @date 2016-5-26 下午12:01:48  
	 */
	public List<DfFormTpl> getByProductHasRelId(String productId) {
		DfFormTpl tempDfFormTpl = new DfFormTpl();
		tempDfFormTpl.setProduct(new TProduct(productId));
		return this.dao.getByProductHasRelId(tempDfFormTpl);
	}
	
	/**
	 * @Description 根据Mode和relId查询DfFormTpl
	 * @param tempDfFormTpl
	 * @return
	 * @author Chenh 
	 * @date 2016年5月6日 下午3:04:40  
	 */
	public DfFormTpl getByModelRelId(DfFormTpl tempDfFormTpl) {
		return this.dao.getByModelRelId(tempDfFormTpl);
	}
	/**
	 * @Description 根据Mode且relId不为""查询DfFormTpl
	 * @param tempDfFormTpl
	 * @return
	 * @author Chenh 
	 * @date 2016年5月6日 下午3:04:40  
	 */
	public List<DfFormTpl> getByModelHasRel(DfFormTpl tempDfFormTpl) {
		return this.dao.getByModelHasRel(tempDfFormTpl);
	}

	/**
	 * @Description TODO
	 * @param dfFormTpl
	 * @author Chenh 
	 * @date 2016年5月11日 上午9:55:41  
	 */
	@Transactional(readOnly = false)
	public void deleteWL(DfFormTpl dfFormTpl) {
		this.dao.deleteWL(dfFormTpl);
	}

	/**
	 * @Description TODO
	 * @param relId
	 * @author Chenh 
	 * @date 2016年5月17日 下午3:34:24  
	 */
	@Transactional(readOnly = false)
	public void deleteWLByRelId(String relId) {
		this.dao.deleteWLByRelId(relId);
	}
	
	/**
	 *初始化更新模板 
	 * @param dfFormTpl
	 * @param officeIds
	 */
	public void updateByInit(DfFormTpl dfFormTpl,String[] officeIds){
		this.dao.updateByInit(dfFormTpl, officeIds);
	}
	
	/**
	 * 获取更新标准模板的ID,关联更新关联模板用
	 * @param model
	 * @param officeId
	 * @return
	 */
	public DfFormTpl getFormTplId(String model,String officeId){
		return getFormTplId(model, officeId, Global.NO);
	}
	public DfFormTpl getFormTplId(String model,String officeId, String delFalg){
		return this.dao.getFormTplId(model, officeId, delFalg);
	}
	
	/**
	 * 通过parentId获取关联模板 
	 * @param relateId
	 * @return
	 */
	public List<DfFormTpl> getRelateDfFormTpl(String relateId){
		return this.dao.getRelateDfFormTpl(relateId);
	}
	
	/**
	 * 通过标准模板更新关联模板
	 * @param dfFormTpl
	 * @param relateIds
	 */
	public void updateRelate(DfFormTpl dfFormTpl,String[] relateIds){
		this.dao.updateRelateDfFormTpl(dfFormTpl, relateIds);
	}

	public List<DfFormTpl> findInitTplList(SysInitFormtplVo entity){
		if((entity != null) && (entity.getOfficeIds() != null) && ((entity.getOfficeIds().size() > 0))){
			return dao.findInitTplList(entity);
		}
		return null;
	}
	
	public List<DfFormTpl> findInitTplListByOrgan(SysInitFormtplVo entity) {
		if((entity != null) && (entity.getOfficeIds() != null) && ((entity.getOfficeIds().size() > 0))){
			return dao.findInitTplListByOrgan(entity);
		}
		return null;
	}
}