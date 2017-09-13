/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.guarantee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.catipal.dao.TCaiwuDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.guarantee.dao.TGuaranteeContractDao;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 担保合同Service
 * @author zzm
 * @version 2016-03-24
 */
@Service("tGuaranteeContractService")
@Transactional(readOnly = true)
public class TGuaranteeContractService extends CrudService<TGuaranteeContractDao, TGuaranteeContract> {
	
	@Autowired
	TGuaranteeContractDao tGuaranteeContractDao;
	@Autowired
	private TContractFilesService tContractFilesService;

	public TGuaranteeContract get(String id) {
		return super.get(id);
	}
	
	public List<TGuaranteeContract> findList(TGuaranteeContract tGuaranteeContract) {
		return super.findList(tGuaranteeContract);
	}
	
	public Page<TGuaranteeContract> findPage(Page<TGuaranteeContract> page, TGuaranteeContract tGuaranteeContract) {
		tGuaranteeContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tGuaranteeContract);
	}
	
	@Transactional(readOnly = false)
	public void save(TGuaranteeContract tGuaranteeContract) {
		//处理临时ID
		String tmpID = tGuaranteeContract.getId();
		boolean needUpdate = false;//是否需要更新关联库表
		if(StringUtils.isNotBlank(tmpID) && tmpID.startsWith("temp_")){
			tGuaranteeContract.setId(null);
			needUpdate = true;
		}
		
		if(tGuaranteeContract.getIsNewRecord())
			tGuaranteeContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		super.save(tGuaranteeContract);
		
		if(needUpdate){
			//调整关联附件表
			tContractFilesService.updateFileTaskId(tmpID, tGuaranteeContract.getId());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TGuaranteeContract tGuaranteeContract) {
		super.delete(tGuaranteeContract);
	}

	/**
	 * 担保关联合同
	 * @param ids
	 * @param businessTable
	 * @param businessId
	 * @return	成功关联的担保数
	 */
	@Transactional(readOnly = false)
	public int relateContract(String[] ids, String businessTable, String businessId) {
		int count = 0;
		if(StringUtils.isBlank(businessTable) && StringUtils.isBlank(businessId) 
				|| ids == null || ids.length == 0){
			throw new ServiceException("缺少必要数据");
		}
		for(String id : ids){
			TGuaranteeContract guarantee = super.get(id);
			if(StringUtils.isNotBlank(guarantee.getBusinessId())){
				throw new ServiceException("担保合同【"+guarantee.getGuaranteeNumber()+"】已经挂了业务，请刷新后操作");
			}

			guarantee.setBusinessTable(businessTable);
			guarantee.setBusinessId(businessId);
			super.save(guarantee);
			count++;
		}
		return count;
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {	
		TGuaranteeContract query = new TGuaranteeContract();
		query.setScanFlag("0");
	
		List<TGuaranteeContract> proceedsList = tGuaranteeContractDao.findListByscanFlag(query);
		List<TGuaranteeContract> successList = new ArrayList<TGuaranteeContract>();
		StringBuffer data = new StringBuffer();
		if (proceedsList != null && proceedsList.size() > 0) {			
			for (TGuaranteeContract temp : proceedsList) {
				data.append(temp.toSend());
				data.append("\r\n");
				successList.add(temp);
			}

			for (TGuaranteeContract temp : successList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				this.saveOrUpdate(temp);
			}

		}		
		return data;
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdate(TGuaranteeContract temp) {
		//super.save(tCaiwu);
		if (StringUtils.isBlank(temp.getId())){
			temp.preInsert();
			dao.insert(temp);
		}else{
			// 更新数据
			temp.preUpdate();
			dao.update(temp);
		}
	}
	
	@Transactional(readOnly = false)
	public void updateListByscanFlagData(List list) {
		TGuaranteeContract response = new TGuaranteeContract();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					tGuaranteeContractDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					tGuaranteeContractDao.updateByscanFlag(response);
				}					
			}				
		}				
	}

	public void update(TGuaranteeContract g) {
		dao.update(g);
	}
	
}