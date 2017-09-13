/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.vo.CsPersonVo;
import com.wanfin.fpd.modules.credit.dao.CreditApplyDao;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 授信申请Service
 * 
 * @author zzm
 * @version 2016-07-13
 */
@Service
@Transactional(readOnly = true)
public class CreditApplyService extends CrudService<CreditApplyDao, CreditApply> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private CustomerCreditService customerCreditService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TContractFilesService tContractFilesService;
	@Autowired
	private CreditCoborrowerService creditCoborrowerService;//共同借款人

	public CreditApply get(String id) {
		return super.get(id);
	}
	
	public CreditApply getNewestCredit(String customerId){
		if(StringUtils.isBlank(customerId)){
			return null;
		}
		return dao.getNewestCredit(customerId);
	}

	public List<CreditApply> findList(CreditApply creditApply) {

		creditApply.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));

		creditApply.setCreateBy(UserUtils.getUser());// 过滤只有创建人才能签订合同

		return super.findList(creditApply);
	}

	public Page<CreditApply> findPage(Page<CreditApply> page, CreditApply creditApply) {
		creditApply.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, creditApply);
	}

	@Transactional(readOnly = false)
	public void save(CreditApply creditApply) throws ServiceException {
		//super.save(creditApply);
		creditApply.setExpirationDate(DateUtils.calculateDate(new Date(), 0, creditApply.getValidMonth(), 0));
		super.save(creditApply);
		if (StringUtils.equals(Cons.CreditApplyStatus.CREDIT_SUCCESS, creditApply.getStatus())) {
			CustomerCredit credit = customerCreditService.getByCustomerId(creditApply.getCustomerId());
			
			/*d9d675824aa44719a2e2b786f6d3d23d  null*/
			/*if (credit == null || !StringUtils.equals(credit.getRelId(), creditApply.getId())) {//没有授信的客户才会往客户信息授信记录加数据(单次授信    直接跟新  不是累加)
				credit = new CustomerCredit();
				credit.setCredit(creditApply.getCredit());//授信额度
				//balance 剩余可用额度
				credit.setCustomerId(creditApply.getCustomerId());
				*//**
				 * @Description 客户授信
				 * @param customerId	客户id
				 * @param credit	授信额度	
				 * @param score		评分
				 * @param overDate	有效截止时间
				 * @param relId		此次授信关联id
				 * @author zzm
				 * @date 2016-7-13 下午6:34:10  
				 *//*																			//剩余可用额度
				customerCreditService.addCustomerCredit(creditApply.getCustomerId(), creditApply.getCredit(), null,
						creditApply.getExpirationDate(), creditApply.getId());
			}*/
			
			/**
			 * new xlh
			 */
			
			if (credit == null) {//没有授信的客户才会往客户信息授信记录加数据(单次授信    直接跟新  不是累加)
				/*credit = new CustomerCredit();
				credit.setCredit(creditApply.getCredit());//授信额度
				
				credit.setCustomerId(creditApply.getCustomerId());*/
			
				/**
				 * @Description 客户授信
				 * @param customerId	客户id
				 * @param credit	授信额度	
				 * @param balance	剩余可用余额
				 * @param score		评分
				 * @param overDate	有效截止时间
				 * @param relId		此次授信关联id
				 * @author zzm
				 * @date 2016-7-13 下午6:34:10  
				 */
				customerCreditService.addCustomerCredit(creditApply.getCustomerId(), creditApply.getCredit(), creditApply.getCredit(), null,
						creditApply.getExpirationDate(), creditApply.getId());
				
			}
			
			if (credit != null) {//没有授信的客户才会往客户信息授信记录加数据(单次授信    直接跟新  不是累加)
				credit.setCredit(creditApply.getCredit().add(credit.getCredit()).setScale(2));//授信额度
				credit.setBalance(creditApply.getCredit().add(credit.getBalance()).setScale(2));//balance 剩余可用额度
				/**
				 * @Description 客户授信
				 * @param customerId	客户id
				 * @param credit	授信额度	
				 * @param balance	剩余可用余额
				 * @param score		评分
				 * @param overDate	有效截止时间
				 * @param relId		此次授信关联id
				 * @author zzm
				 * @date 2016-7-13 下午6:34:10  
				 */																		
				customerCreditService.addCustomerCredit(creditApply.getCustomerId(), credit.getCredit(), credit.getBalance(), null,
						creditApply.getExpirationDate(), creditApply.getId());
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		CreditApply delCreditApply = new CreditApply();
		delCreditApply.setId(id);
		super.delete(delCreditApply);
	}


	@Transactional(readOnly = false)
	public void saveAudit(CreditApply creditApply) throws ServiceException {
		try {
			String tmpID = creditApply.getId();
			boolean needUpdate = false;//是否需要更新关联库表
			if(StringUtils.isNotBlank(tmpID) && tmpID.startsWith("temp_")){
				creditApply.setId(null);
				needUpdate = true;
				this.delete(tmpID);//删除旧的数据
			}
			boolean isNew = creditApply.getIsNewRecord();		
			if (isNew || needUpdate) {
				if(StringUtils.isBlank(creditApply.getApplyNum())){
					creditApply.setApplyNum(UserUtils.getSqeNo(Cons.CountCategory.CREDIT_APPLY, "授信"));
				}
				creditApply.setStatus(Cons.CreditApplyStatus.NEW);
			}
			this.save(creditApply);
			//更新ID
			if(needUpdate && StringUtils.isNotBlank(creditApply.getProductId())){
				//if("6320307f3c724c86a61d5f73512af954".equals(creditApply.getProductId())){//种植贷
					Db.update("update t_report_planted set credit_apply_id = '"+creditApply.getId()+"' where credit_apply_id='"+tmpID+"'");
				/*}else if("84054c94119440588719741f1c42d6e1".equals(creditApply.getProductId())){//养殖贷
					Db.update("update t_report_culture set credit_apply_id = '"+creditApply.getId()+"' where credit_apply_id='"+tmpID+"'");
				}else if("61bc8ee4b0014c5983058c610c8991c8".equals(creditApply.getProductId())){//员工贷
					Db.update("update t_report_employee set credit_apply_id = '"+creditApply.getId()+"' where credit_apply_id='"+tmpID+"'");
				}else if("3b36391cb60a47a0895e555d0ca53e94".equals(creditApply.getProductId())){//企业贷
					Db.update("update t_report_company set credit_apply_id = '"+creditApply.getId()+"' where credit_apply_id='"+tmpID+"'");
				}*/
				
				//更新附件对应的creditApply的ID
				//更新共同借款人表
				//creditCoborrowerService.updateCoborrowerId(tmpID, creditApply.getId());
				//调整关联附件表
				tContractFilesService.updateFileTaskId(tmpID, creditApply.getId());
			}
			if (StringUtils.isBlank(creditApply.getProcInsId()) ||creditApply.getStatus().equals(Cons.CreditApplyStatus.CREDIT_FAIL)) {
				if (StringUtils.isNotBlank(creditApply.getAct().getProcDefId())) {
					creditApply.getAct()
							.setProcDef(actTaskService.getProcessByProcDefId(creditApply.getAct().getProcDefId()));
				}

				if (StringUtils.isBlank(creditApply.getAct().getProcDefKey())) {
					throw new ServiceException("没有指定流程");
				}
				
				//当前节点任务处理人
				//creditApply.getAct().getVars().getVariableMap().put("next", UserUtils.getUser().getLoginName());
				// 启动并提交流程
				
				String procInsId = actTaskService.startProcess(creditApply.getAct().getProcDefKey(), "t_credit_apply",
						creditApply.getId(), creditApply.getApplyNum(), creditApply.getAct().getVars().getVariableMap());
				
				//下一节点任务处理人
				//creditApply.getAct().getVars().getVariableMap().put("next", creditApply.getAct().getAssigneeName());
				actTaskService.completeFirstTask(procInsId, creditApply.getAct().getComment(),
						creditApply.getApplyNum(), creditApply.getAct().getVars().getVariableMap());
				creditApply.setProcInsId(procInsId);
				creditApply.setStatus(Cons.CreditApplyStatus.TO_APPROVE);

			} else {
				if(StringUtils.isNotBlank(creditApply.getAct().getFlag())){
					if(StringUtils.isNotBlank(creditApply.getAct().getFlag())){
						if(!creditApply.getAct().getFlag().equals("-1")){//同意或驳回
						     // 设置意见
						     creditApply.getAct().setComment(("1".equals(creditApply.getAct().getFlag()) ? "[同意] " : "[驳回] ")
								+ creditApply.getAct().getComment());
						        //下一节点任务处理人
								//creditApply.getAct().getVars().getVariableMap().put("next", creditApply.getAct().getAssigneeName());
						       // 执行任务
						        actTaskService.complete(creditApply.getAct().getTaskId(), creditApply.getAct().getProcInsId(),
								creditApply.getAct().getComment(), creditApply.getAct().getVars().getVariableMap());
						  }else{//终止任务
							  /*actTaskService.rollBackWorkFlow(creditApply.getAct().getTaskId());*/
							  actTaskService.overTask(creditApply.getAct().getTaskId(),creditApply.getAct().getProcInsId(),creditApply.getAct().getComment());
						      creditApply.setStatus(Cons.CreditApplyStatus.CREDIT_FAIL);//授信失败 
						  }
					}
				}
			}
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(creditApply.getAct().getProcInsId()).singleResult();
			/*if (pi != null || creditApply.getAct().getFlag().equals("-1")) {*/
			if (pi != null) {
				super.save(creditApply);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败！");
		}
	}
	
	@Transactional(readOnly = false)
	public void insert(CreditApply creditApply) throws ServiceException{
		dao.insert(creditApply);
	}
	
	@Transactional(readOnly = false)
	public void update(CreditApply creditApply) {
		dao.update(creditApply);
	}

	@Transactional(readOnly = false)
	public void delete(CreditApply creditApply) {
		super.delete(creditApply);
	}

	/**
	 * @Description （调用风控）个人用户评分授信
	 * @author zzm
	 * @date 2016-7-11 下午4:34:28
	 */
	public CsPersonVo calculateGrade(CsPersonVo param) throws ServiceException {
		logger.error("----------------------------------------------------------------------------------");
		try {
			param.setToken(Cons.Ips.IP_KIE_TOKEN);
			JSONObject json = new JSONObject(param);
			logger.error("调用个人用户评分授信接口请求：" + Cons.Ips.IP_KIE_CALCULATEPERSONALCREDIT + "? data = " + json.toString());
			String result = InterfaceUtil.sendPostJson(Cons.Ips.IP_KIE_CALCULATEPERSONALCREDIT,
					URLEncoder.encode(json.toString(), "utf-8"));
			logger.error("调用个人用户评分授信接口响应：result=" + result);
			JSONObject resultJson = new JSONObject(result);
			String code = resultJson.getString("code");
			if (code.equals("error")) {
				throw new ServiceException(resultJson.getString("message"));
			}
			param.setAudit(resultJson.optInt("audit", 0));
			param.setScore(resultJson.optDouble("score", 0.0));
			param.setAmount(new BigDecimal(resultJson.optString("credit", "0")));
		} catch (JSONException e) {
			e.printStackTrace();
			logger.error("个人用户评分授信接口：数据转换异常");
			// throw new ServiceException("数据转换异常");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("个人用户评分授信接口：数据编码异常");
			// throw new ServiceException("数据编码异常");
		} finally {
			logger.error("个人用户评分授信  结束 <<");
		}
		logger.error("----------------------------------------------------------------------------------");
		return param;
	}

	public int getCreditApplyByStatus(String customerId, String status) {
		return dao.getCreditApplyByStatus(customerId, status);
	}

	public void deleteByCondition(CreditApply creditApply) {
		//获取id后再删除
		CreditApply tmp = dao.getByCondition(creditApply);
		if(tmp != null && StringUtils.isNotBlank(tmp.getId())){
			CustomerCredit customerCredit = new CustomerCredit();
			customerCredit.setCustomerId(tmp.getCustomerId());
			customerCredit.setCredit(tmp.getCredit());
			customerCredit.setBalance(tmp.getCredit());
			customerCredit.setOverDate(tmp.getExpirationDate());
			customerCredit.setRelId(tmp.getId());
			
			customerCreditService.deleteByCondition(customerCredit);
		}
		
		dao.deleteByCondition(creditApply);
	}
}