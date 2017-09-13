package com.wanfin.fpd.modules.billing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;
import com.wanfin.fpd.modules.billing.service.collect.BiCollectService;
import com.wanfin.fpd.modules.billing.service.element.BiElementService;
import com.wanfin.fpd.modules.billing.service.price.BiPriceService;
import com.wanfin.fpd.modules.billing.service.rule.BiRuleService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class BiService {
	@Autowired
	private BiRuleService rule;
	@Autowired
	private BiPriceService price;
	@Autowired
	private BiElementService element;
	@Autowired
	private BiCollectService collect;
	
	/**
	 * 批量处理服务中业务
	 * @return
	 */
	public List<BiCollect> plqtServering(){
		BiCollect biCollect = new BiCollect();
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return collect.findList(biCollect);
	}
	
	/**
	 * 获取所有购买的业务包（服务中）
	 * @return
	 */
	public List<BiCollect> getServering(){
		BiCollect biCollect = new BiCollect();
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return collect.findList(biCollect);
	}
	
	/**
	 * 获取所有购买的业务包（已停止）
	 * @return
	 */
	public List<BiCollect> getServerend(){
		BiCollect biCollect = new BiCollect();
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_END);
		return collect.findList(biCollect);
	}
	
	/**
	 * 获取租户购买的所有业务包（服务中、已停止）
	 * @param organId
	 * @return
	 */
	public List<BiCollect> getAllByOranId(String organId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		return collect.findListByOrganId(biCollect);
	}

	/**
	 * 获取租户购买的所有业务包（服务中）
	 * @param organId
	 * @return
	 */
	public List<BiCollect> getServeringByOranId(String organId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return collect.findListByOrganId(biCollect);
	}

	/**
	 * 获取租户购买的所有业务包（已停止）
	 * @param organId
	 * @return
	 */
	public List<BiCollect> getServerendByOranId(String organId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_END);
		return collect.findListByOrganId(biCollect);
	}

	/**
	 * 获取租户购买的某一收费项所有业务包（服务中、已停止）
	 * @param organId 租户
	 * @param elementId 收费项
	 * @return
	 */
	public List<BiCollect> getAllByOranIdAndElement(String organId, String typeId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		BiElement biElement = new BiElement();
		biElement.setTypeId(typeId);
		biCollect.setElement(biElement);
		return collect.findListByOrganId(biCollect);
	}
	
	/**
	 * 获取租户购买的某一收费项业务包（服务中）
	 * @param organId 租户
	 * @param elementId 收费项
	 * @return
	 */
	public List<BiCollect> getServeringByOranIdAndElement(String organId, String typeId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		BiElement biElement = new BiElement();
		biElement.setTypeId(typeId);
		biCollect.setElement(biElement);
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		return collect.findListByOrganId(biCollect);
	}
	
	/**
	 * 获取租户购买的某一收费项业务包（已停止）
	 * @param organId 租户
	 * @param elementId 收费项
	 * @return
	 */
	public List<BiCollect> getServerendByOranIdAndElement(String organId, String typeId){
		BiCollect biCollect = new BiCollect();
		biCollect.setOrganId(organId);
		BiElement biElement = new BiElement();
		biElement.setTypeId(typeId);
		biCollect.setElement(biElement);
		biCollect.setStatus(BiSval.BiCollectStatus.SERVER_END);
		return collect.findListByOrganId(biCollect);
	}
	
	/**
	 * 获取最久的服务中的的购买记录
	 * @param typeId 收费项
	 * @param reduceNum 服务次数
	 * @return
	 */
	public BiCollect getMinByTypeId(String organId, String typeId){
		return collect.getMinByOrganIdAndTypeId(organId, typeId);
	}
	/**
	 * 获取最久的服务中的的购买记录
	 * @param typeId 收费项
	 * @param reduceNum 服务次数
	 * @return
	 */
	public BiCollect getMin(String organId, String typeId){
		return collect.getMinByOrganIdAndElementId(organId, typeId);
	}
	
	/**
	 * 减少服务次数-1次
	 * @param typeId 收费项
	 * @return
	 */
	@Transactional(readOnly = false)
	public Boolean reduceServerNumber(String typeId){
		return reduceServerNumber(typeId, 1);
	}
	
	/**
	 * 减少服务次数
	 * @param typeId 收费项
	 * @return
	 */
	@Transactional(readOnly = false)
	public Boolean reduceServerNumber(String typeId, Integer reduceNum){
		return reduceServerNumber(UserUtils.getUser().getOffice().getId(), typeId, reduceNum);
	}
	
	/**
	 * 减少服务次数
	 * @param organId 租户
	 * @param elementId 收费项
	 * @param reduceNum 服务次数
	 * @return
	 */

	@Transactional(readOnly = false)
	public Boolean reduceServerNumber(String organId, String typeId, Integer reduceNum){
		BiCollect entity = getMinByTypeId(organId, typeId);
		if(entity != null){
			entity.setSurplusNumber(entity.getSurplusNumber() - reduceNum);
			collect.save(entity);
			return true;
		}
		return false;
	}
	
	/**
	 * 获取服务状态
	 * @param organId 租户
	 * @param elementId 收费项
	 * @return
	 */
	public Boolean isServering(String organId, String typeId){
		Boolean isTrue = false;
		List<BiCollect> list = getServeringByOranIdAndElement(organId, typeId);
		for (BiCollect entity : list) {
			if((entity.getStatus()).equals(BiSval.BiCollectStatus.SERVER_ING)){
				isTrue = true;
				break;
			}
		}
		return isTrue;
	}
	
	public List<BiCollect> filterList(List<BiCollect> collects) {
		List<BiCollect> newCollects = new ArrayList<BiCollect>();
		newCollects.addAll(collects);
		for (BiCollect biCollect : collects) {
			BiRule rule = biCollect.getRule();
			if((rule.getType()).equals(BiSval.BiType.TIME) && (biCollect.getSurplusTime() <= 0)){
				newCollects.remove(biCollect);
			}
			if((rule.getType()).equals(BiSval.BiType.NUM) && (biCollect.getSurplusNumber() <= 0)){
				newCollects.remove(biCollect);
			}
		}
		return newCollects;
	}
}
