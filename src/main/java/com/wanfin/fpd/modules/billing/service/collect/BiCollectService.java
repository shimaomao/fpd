/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.service.collect;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.extend.billing.BiCall;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.modules.billing.dao.collect.BiCollectDao;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 计费汇总Service
 * @author chenh
 * @version 2016-07-02
 */
@Service
@Transactional(readOnly = true)
public class BiCollectService extends CrudService<BiCollectDao, BiCollect> {
    private BiCall call = new BiCall();

	public BiCollect get(String id) {
		return super.get(id);
	}

	public BiCollect getMinByOranIdAndElementId(BiCollect biCollect) {
		return dao.getMin(biCollect);
	}
	
	public BiCollect getMinByOrganIdAndElementId(String elementId) {
		return getMinByOrganIdAndElementId(UserUtils.getUser().getOffice().getId(), elementId);
	}

	public BiCollect getMinByOrganIdAndTypeId(String typeId) {
		return getMinByOrganIdAndTypeId(UserUtils.getUser().getOffice().getId(), typeId);
	}

	/**
	 * 获取最久的服务中的的购买记录
	 * @param elementId 收费项
	 * @param reduceNum 服务次数
	 * @return
	 */
	public BiCollect getMinByOrganIdAndElementId(String organId, String elementId) {
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		biCollect.setElement(new BiElement(elementId));
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return dao.getMin(biCollect);
	}
	
	/**
	 * 获取最久的服务中的的购买记录
	 * @param typeId 收费项标识
	 * @param reduceNum 服务次数
	 * @return
	 */
	public BiCollect getMinByOrganIdAndTypeId(String organId, String typeId) {
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		BiElement biElement = new BiElement();
		biElement.setTypeId(typeId);
		biCollect.setElement(biElement);
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return dao.getMin(biCollect);
	}
	
	public List<BiCollect> findList(BiCollect biCollect) {
		return super.findList(biCollect);
	}
	
	public List<BiCollect> findListByOrganId(BiCollect biCollect) {
		biCollect.setOrganId(UserUtils.getUser().getCompany().getId());
		return dao.findListByOrganId(biCollect);
	}

	public Page<BiCollect> findPage(Page<BiCollect> page, BiCollect biCollect) {
		return super.findPage(page, biCollect);
	}
	
	@Transactional(readOnly = false)
	public void savePL(List<BiCollect> biCollects) {
		dao.updatePL(biCollects);
	}
	
	@Transactional(readOnly = false)
	public void save(BiCollect biCollect) {
		Boolean isNew = biCollect.getIsNewRecord();
		if(isNew){
			if(biCollect.getTxFlag() == null){
				biCollect.setTxFlag(0);
			}
			if(biCollect.getStatus() == null){
				biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
			}
			if(biCollect.getSurplusNumber() == null){
				biCollect.setSurplusNumber(biCollect.getNumber());
			}
			if(biCollect.getSurplusTime() == null){
				biCollect.setSurplusTime(biCollect.getTotalTime());
			}
			if(StringUtils.isEmpty(biCollect.getOrganId())){
				biCollect.setOrganId(UserUtils.getUser().getCompany().getId());
			}
		}else{
			/**
			 * 如果剩余时间或剩余数量为零，将状态值为停止服务
			 */
			if((biCollect.getSurplusNumber() <= 0) || (biCollect.getSurplusTime() <= 0)){
				biCollect.setStatus(BiSval.BiCollectStatus.SERVER_END);
			}
		}
		super.save(biCollect);
		if(biCollect.getIsNewRecord() && (call != null)){
			call.updateAccount(biCollect);
		}
	}

	
	@Transactional(readOnly = false)
	public void delete(BiCollect biCollect) {
		super.delete(biCollect);
	}
}