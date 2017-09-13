/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.asset.service.AssetService;
import com.wanfin.fpd.modules.sys.dao.OpenBizDao;
import com.wanfin.fpd.modules.sys.entity.FeeBiz;
import com.wanfin.fpd.modules.sys.entity.OpenBiz;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.trading.service.TradingRecordService;

/**
 * 开通业务Service
 * @author zzm
 * @version 2016-06-06
 */
@Service
@Transactional(readOnly = true)
public class OpenBizService extends CrudService<OpenBizDao, OpenBiz> {
	
	@Autowired
	private TradingRecordService tradingRecordService; 
	
	@Autowired
	private AssetService assetService;

	public OpenBiz get(String id) {
		return super.get(id);
	}
	
	public List<OpenBiz> findList(OpenBiz openBiz) {
		return super.findList(openBiz);
	}
	
	public Page<OpenBiz> findPage(Page<OpenBiz> page, OpenBiz openBiz) {
		return super.findPage(page, openBiz);
	}
	
	@Transactional(readOnly = false)
	public void save(OpenBiz openBiz) throws ServiceException{
		super.save(openBiz);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpenBiz openBiz) {
		super.delete(openBiz);
		//申请未处理时，要对租户冻结的激活费用进行解冻
		if(StringUtils.equals(openBiz.getStatus(), Cons.NEW)){
			User organ = UserUtils.get(openBiz.getCreateBy().getId());//租户
			assetService.freezeAsset(organ.getLoginName(), openBiz.getAmount().negate());//解冻
		}
	}
	
	/**
	 * @Description 获取付费业务列表
	 * @param feeBiz
	 * @return
	 * @author zzm
	 * @date 2016-6-6 下午3:20:09  
	 */
	@Transactional(readOnly = false)
	public List<FeeBiz> findFeeBizList(FeeBiz feeBiz){
		List<FeeBiz> list = this.dao.findFeeBizList(feeBiz);
		return list;
	}

	/**
	 * @Description 提交激活付费业务的申请
	 * @param openBiz
	 * @throws ServiceException
	 * @author zzm
	 * @date 2016-6-6 下午5:00:35  
	 */
	@Transactional(readOnly = false)
	public void apply(OpenBiz openBiz) throws ServiceException{
		openBiz.setOrganId(UserUtils.getUser().getCompany().getId());
		//校验数据
		if(openBiz.getFeeBiz() == null || StringUtils.isBlank(openBiz.getFeeBiz().getId())){
			throw new ServiceException("数据错误！");
		}
		openBiz.setFeeBiz(getFeeBiz(openBiz.getFeeBiz().getId()));
		//判断是否已激活（短信除外，短信可多次购买）
		if(!StringUtils.equals(openBiz.getFeeBiz().getCategory(), Cons.FeeBizCategory.MESSAGE)){
			Map<String,String> map = getFeeBizOrganLink(openBiz.getFeeBiz().getId(),openBiz.getOrganId());
			if(map != null){
				throw new ServiceException("该业务已激活！");
			}
		}
		
		//判断是否已发送申请
		openBiz.setStatus(Cons.NEW);
		List<OpenBiz> list = findList(openBiz);
		if(list != null && !list.isEmpty()){
			throw new ServiceException("激活申请已发送！");
		}
		
		super.save(openBiz);
		//提交成功后，从租户资产中冻结激活的费用
		assetService.freezeAsset(openBiz.getCurrentUser().getLoginName(), openBiz.getAmount());
	}
	
	/**
	 * @Description 获取租户对应的一个付费服务的激活配置
	 * @param feeBizId	付费业务
	 * @param organId	租户
	 * @return
	 * @author zzm
	 * @date 2016-6-6 下午4:59:26  
	 */
	@Transactional(readOnly = false)
	public Map<String,String> getFeeBizOrganLink(String feeBizId, String organId){
		return this.dao.getFeeBizOrganLink(feeBizId,organId);
	}
	
	
	/**
	 * @Description 不通过租户激活付费业务的申请
	 * @param openBiz
	 * @throws ServiceException
	 * @author zzm 
	 * @date 2016-6-20 下午4:39:30  
	 */
	@Transactional(readOnly = false)
	public void deactivateOpenBiz(OpenBiz openBiz) throws ServiceException{
		if(!StringUtils.equals(openBiz.getStatus(), Cons.NEW)){
			throw new ServiceException("已处理的申请！");
		}
		openBiz.setStatus(Cons.INVALID);
		this.save(openBiz);
		//对租户冻结的激活费用进行解冻
		User organ = UserUtils.get(openBiz.getCreateBy().getId());//租户
		assetService.freezeAsset(organ.getLoginName(), openBiz.getAmount().negate());//解冻
	}
	
	/**
	 * @Description 给租户激活付费业务
	 * @param openBiz
	 * @throws ServiceException
	 * @author zzm 
	 * @date 2016-6-20 下午4:37:46  
	 */
	@Transactional(readOnly = false)
	public void activateOpenBiz(OpenBiz openBiz) throws ServiceException{
		if(!StringUtils.equals(openBiz.getStatus(), Cons.NEW)){
			throw new ServiceException("已处理的申请！");
		}
		Map<String,String> map = getFeeBizOrganLink(openBiz.getFeeBiz().getId(),openBiz.getOrganId());
		if(map == null){
			this.dao.saveFeeBizOrganLink(openBiz.getFeeBiz().getId(),openBiz.getOrganId(),openBiz.getCount());
		}else {
			this.dao.updateFeeBizOrganLink(openBiz.getFeeBiz().getId(),openBiz.getOrganId(),openBiz.getCount());
		}
		openBiz.setStatus(Cons.VALID);
		this.save(openBiz);
		
		//对租户冻结的激活费用进行解冻并扣除
		User organ = UserUtils.get(openBiz.getCreateBy().getId());//租户
		assetService.freezeAsset(organ.getLoginName(), openBiz.getAmount().negate());//解冻
		tradingRecordService.addTradingRecord("万众金融", "平台账户交易", Cons.TradingType.FEE_BIZ_PAY, organ.getLoginName(),
				openBiz.getAmount().negate(), openBiz.getId(), "付费业务激活");//扣费
		
		//增加万众的平台资产
		tradingRecordService.addTradingRecord(organ.getLoginName(), "平台账户交易", Cons.TradingType.FEE_BIZ_PAY, UserUtils.getAdmin().getLoginName(),
				openBiz.getAmount(), openBiz.getId(), "付费业务激活");
	}
	
	/**
	 * @Description 获取单个付费业务对象
	 * @param id
	 * @return
	 * @author zzm
	 * @date 2016-6-21 上午10:16:09  
	 */
	public FeeBiz getFeeBiz(String id) {
		return this.dao.getFeeBiz(id);
	}

	
	/**
	 * @Description 保存付费业务
	 * @param feeBiz
	 * @author zzm
	 * @date 2016-6-21 下午4:29:40  
	 */
	@Transactional(readOnly = false)
	public void saveFeeBiz(FeeBiz feeBiz) {
		if(StringUtils.isBlank(feeBiz.getId())){//新增
			feeBiz.setId(IdGen.uuid());
			this.dao.insertFeeBiz(feeBiz);
		}else{
			this.dao.updateFeeBiz(feeBiz);
		}
	}
}