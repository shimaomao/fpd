/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.service.creditchecking;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.WindVo;
import com.wanfin.fpd.modules.wind.adapter.vo.IaTypeVo;
import com.wanfin.fpd.modules.wind.dao.creditchecking.TCreditCheckingDao;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;

/**
 * 征信Service
 * @author chenh
 * @version 2016-05-30
 */
@Service
@Transactional(readOnly = true)
public class TCreditCheckingService extends CrudService<TCreditCheckingDao, TCreditChecking> {
//	@Autowired
//	private AdapterService adapterService;

	/**
     * @Description 初始化Cons.CreditChecking枚举类，定时更新系统征信信息
	 * @author Chenh 
	 * @date 2016年6月3日 下午4:10:05
	 */
//	@Transactional(readOnly = false)
//	public Boolean init(){
//		return init(adapterService.getIadapterDbs());
//	}
	/**
     * @Description 初始化Cons.CreditChecking枚举类，定时更新系统征信信息
	 * @author Chenh 
	 * @date 2016年6月3日 下午4:10:05
	 */
//	@Transactional(readOnly = false)
//	public Boolean init(List<IAdapterDb> iAdapterDbs){
//		List<IaVo> iaVos = adapterService.initDataPL(iAdapterDbs);
//		List<TCreditChecking> entitys = IaVo.dealLaVo(iaVos);
//		if ((entitys != null) && (entitys.size() > 0)) {
//			savePL(entitys);
//	    	return true;
//		}
//    	return false;
//	}

	public TCreditChecking get(String id) {
		return super.get(id);
	}
	
	public List<TCreditChecking> findList(TCreditChecking tCreditChecking) {
		return super.findList(tCreditChecking);
	}
	
	public Page<TCreditChecking> findPage(Page<TCreditChecking> page, TCreditChecking tCreditChecking) {
		return super.findPage(page, tCreditChecking);
	}

	/**
	 * @Description 
	 * @param page
	 * @param tCreditChecking
	 * @return
	 * @author Chenh 
	 * @date 2016年6月1日 下午4:13:48  
	 */
	public List<TCreditChecking> findListByRelId(TCreditChecking entity) {
		return dao.findListByRelId(entity);
	}

	/**
	 * @Description 
	 * @param page
	 * @param tCreditChecking
	 * @return
	 * @author Chenh 
	 * @date 2016年6月1日 下午4:13:48  
	 */
	public Page<TCreditChecking> findPageByRelId(Page<TCreditChecking> page, TCreditChecking entity) {
		entity.setPage(page);
		page.setList(dao.findListByRelId(entity));
		return page;
	}
	
	public List<TCreditChecking> findListByTypeAndTypeIds(List<String> dbs, List<String> types, List<String> typeIds, List<String> typeSubs) {
		return super.dao.findListByTypeAndTypeIds(dbs, types, typeIds, typeSubs);
	}
	
	@Transactional(readOnly = false)
	public void save(TCreditChecking tCreditChecking) {
		super.save(tCreditChecking);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCreditChecking tCreditChecking) {
		super.delete(tCreditChecking);
	}

	@Transactional(readOnly = false)
	public Boolean savePL(List<TCreditChecking> entitys) {
		if((entitys != null) && (entitys.size() > 0)){
			super.dao.savePL(entitys);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public Boolean deletePLWL(List<TCreditChecking> entitys) {
		if((entitys != null) && (entitys.size() > 0)){
			super.dao.deletePLWL(entitys);
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public Boolean deletePLWLByIds(List<TCreditChecking> entitys) {
		if((entitys != null) && (entitys.size() > 0)){
			super.dao.deletePLWLByIds(entitys);
			return true;
		}
		return false;
	}
	

	/********************************************************************************************************
	 * 保存征信信息
	 * @param cardNum
	 * @param adapter
	 * @param vo
	 */
	public void saveCreaditChecking(String cardNum, IeAdapter adapter, IaTypeVo vo) {
		if((vo != null) && (vo.getVo() != null)){
			WindVo windVo = vo.getVo();
			if((windVo.getReturnCode()).equals(WindHttp.WHCode.SUCCESS)){
				TCreditChecking tCreditChecking = new TCreditChecking();
				tCreditChecking.setDb(adapter.getType().getDb().getKey());
				tCreditChecking.setType(adapter.getType().getKey());
				tCreditChecking.setTypeId(cardNum);
				tCreditChecking.setTypeSub(adapter.getSub());
				tCreditChecking.setData(windVo.getResult().optString(adapter.getSub()));
				save(tCreditChecking);
			}
		}
	}
}