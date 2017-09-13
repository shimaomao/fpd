/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.act.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.dao.ActDao;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.act.utils.ActUtils;
import com.wanfin.fpd.modules.act.utils.ProcessDefCache;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 流程定义相关Service
 * 
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService extends BaseService {

	@Autowired
	private ActDao actDao;

	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;

	/**
	 * 获取待办列表
	 * 
	 * @param procDefKey
	 *            流程定义标识
	 * @return
	 */
	public List<Act> todoList(Act act) {
		String userId = UserUtils.getUser().getLoginName();// ObjectUtils.toString(UserUtils.getUser().getId());

		List<Act> result = new ArrayList<Act>();
		// =============== 等待签收的任务 ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId).includeProcessVariables()
				.active().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null) {
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}

		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			// e.setTaskVars(task.getTaskLocalVariables());
			// System.out.println(task.getId()+" = "+task.getProcessVariables()
			// + " ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			// e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
			// e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("claim");
			//========================
			//#4413添加procInsId=${task.processInstanceId}
			if (StringUtils.isNotBlank(task.getProcessInstanceId())){//if (act.getProcInsId() != null)
				act.setProcIns(getProcIns(task.getProcessInstanceId()));
				
				String businessId = act.getBusinessId();
				String businessTable = act.getBusinessTable();
				if(StringUtils.isBlank(businessId)){
					businessId = e.getBusinessId();
				}
				if(StringUtils.isBlank(businessTable)){
					businessTable = e.getBusinessTable();
				}
				//System.out.println("BusinessId="+act.getBusinessId()+"；BusinessTable="+act.getBusinessTable());
				if(StringUtils.isNotBlank(businessId) && StringUtils.isNotBlank(businessTable)){
					Map<String, Object> mapResult = getProductNameAndCustomerName(businessId, businessTable);
					if(mapResult != null){
						e.setCustomerId(mapResult.get("customerId") == null ? "无" : (String)mapResult.get("customerId"));
						e.setCustomerName(mapResult.get("customerName") == null ? "无" : (String) mapResult.get("customerName"));
						e.setProductId(mapResult.get("productId") == null ? "无" : (String) mapResult.get("productId"));
						e.setProductname(mapResult.get("productname") == null ? "无" : (String) mapResult.get("productname"));
					}else{
						e.setCustomerId("无");
						e.setCustomerName("无");
						e.setProductId("无");
						e.setProductname("无");
					}
				}
			}
			//========================
			result.add(e);
		}
		// =============== 已经签收的任务 ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active().includeProcessVariables()
				.orderByTaskCreateTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null) {
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}

		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			// e.setTaskVars(task.getTaskLocalVariables());
			// System.out.println(task.getId()+" = "+task.getProcessVariables()
			// + " ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			// e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
			// e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("todo");
			//========================
			//#4413添加
			if (StringUtils.isNotBlank(task.getProcessInstanceId())){//if (act.getProcInsId() != null)
				act.setProcIns(getProcIns(task.getProcessInstanceId()));
				
				String businessId = act.getBusinessId();
				String businessTable = act.getBusinessTable();
				if(StringUtils.isBlank(businessId)){
					businessId = e.getBusinessId();
				}
				if(StringUtils.isBlank(businessTable)){
					businessTable = e.getBusinessTable();
				}
				//System.out.println("BusinessId="+act.getBusinessId()+"；BusinessTable="+act.getBusinessTable());
				if(StringUtils.isNotBlank(businessId) && StringUtils.isNotBlank(businessTable)){
					Map<String, Object> mapResult = getProductNameAndCustomerName(businessId, businessTable);
					if(mapResult != null){
						e.setCustomerId(mapResult.get("customerId") == null ? "无" : (String)mapResult.get("customerId"));
						e.setCustomerName(mapResult.get("customerName") == null ? "无" : (String) mapResult.get("customerName"));
						e.setProductId(mapResult.get("productId") == null ? "无" : (String) mapResult.get("productId"));
						e.setProductname(mapResult.get("productname") == null ? "无" : (String) mapResult.get("productname"));
					}else{
						e.setCustomerId("无");
						e.setCustomerName("无");
						e.setProductId("无");
						e.setProductname("无");
					}
				}
			}
			//========================
			result.add(e);
		}

		return result;
	}

	
	
	
	
	/**
	 * 获取已办任务
	 * 
	 * @param page
	 * @param procDefKey
	 *            流程定义标识
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act) {
		String userId = UserUtils.getUser().getLoginName();// ObjectUtils.toString(UserUtils.getUser().getId());

		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
				.finished().includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null) {
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}

		// 查询总数
	    int firstData = (page.getPageNo()-1)*page.getMaxResults(); 
	    int lastData = page.getPageNo()*page.getMaxResults();
		// 查询列表
		//List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
	    List<HistoricTaskInstance> histList = histTaskQuery.listPage(firstData, lastData);
		for (HistoricTaskInstance histTask : histList) {
			Act e = new Act();

			String businessid = Db.queryStr(
					"select BUSINESS_KEY_ from ACT_HI_PROCINST where ID_ = (select PROC_INST_ID_ from ACT_HI_TASKINST where ID_ = ?)",
					histTask.getId());
			if (businessid != null) {
				String str[] = businessid.split(":");
				e.setBusinessTable(str[0]);
				e.setBusinessId(str[1]);
			}
			e.setHistTask(histTask);
			e.setVars(histTask.getProcessVariables());
			e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
			e.setStatus("finish");
			
			e.setProcDefId(histTask.getProcessDefinitionId());
			e.setProcInsId(histTask.getProcessInstanceId());
			//查询出  流程当前的执行人
			List<Act> histoicFlowList = histoicFlowList(e.getProcInsId(), null, null);
			if(histoicFlowList.get(histoicFlowList.size()-1).getAssigneeName() != null){
				e.setAssignee(histoicFlowList.get(histoicFlowList.size()-1).getAssigneeName());
			}else {
				e.setAssignee(histoicFlowList.get(histoicFlowList.size()-2).getAssigneeName());
			}
				
			
				
			//========================
			//#4413添加  增加产品名称和客户名称
			if (StringUtils.isNotBlank(histTask.getProcessInstanceId())){//if (act.getProcInsId() != null)
				act.setProcIns(getProcIns(histTask.getProcessInstanceId()));
				
				//System.out.println("1 BusinessId="+e.getBusinessId()+"；BusinessTable="+e.getBusinessTable());
				//System.out.println("BusinessId="+act.getBusinessId()+"；BusinessTable="+act.getBusinessTable());
				
				String businessId = e.getBusinessId();//act.getBusinessId();
				String businessTable = e.getBusinessTable();//act.getBusinessTable();
				if(StringUtils.isBlank(businessId)){
					businessId = e.getBusinessId();
				}
				if(StringUtils.isBlank(businessTable)){
					businessTable = e.getBusinessTable();
				}
				
				if(StringUtils.isNotBlank(businessId) && StringUtils.isNotBlank(businessTable)){
					Map<String, Object> mapResult = getProductNameAndCustomerName(businessId, businessTable);
					if(mapResult != null){
						e.setCustomerId(mapResult.get("customerId") == null ? "无" : (String)mapResult.get("customerId"));
						e.setCustomerName(mapResult.get("customerName") == null ? "无" : (String) mapResult.get("customerName"));
						e.setProductId(mapResult.get("productId") == null ? "无" : (String) mapResult.get("productId"));
						e.setProductname(mapResult.get("productname") == null ? "无" : (String) mapResult.get("productname"));
					}else{
						e.setCustomerId("无");
						e.setCustomerName("无");
						e.setProductId("无");
						e.setProductname("无");
					}
				}
			}
			//========================
			
			page.getList().add(e);
		}
		page.setCount(histTaskQuery.count());
		page.setList(page.getList());
		return page;
	}

	/**
	 * 获取流转历史列表
	 * 
	 * @param procInsId
	 *            流程实例
	 * @param startAct
	 *            开始活动节点名称
	 * @param endAct
	 *            结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct) {
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(procInsId).orderByHistoricActivityInstanceStartTime().asc()
				.orderByHistoricActivityInstanceEndTime().asc().list();

		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();

		for (int i = 0; i < list.size(); i++) {

			HistoricActivityInstance histIns = list.get(i);

			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())) {
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start) {
				continue;
			}

			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee()) || "startEvent".equals(histIns.getActivityType())
					|| "endEvent".equals(histIns.getActivityType())) {

				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null) {
					actMap.put(histIns.getActivityId(), actMap.size());
				}

				Act e = new Act();
				e.setHistIns(histIns);
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())) {
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery()
							.processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
					// List<HistoricIdentityLink> il =
					// historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0) {
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())) {
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null) {
								e.setAssignee(histIns.getAssignee());
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())) {
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null) {
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())) {
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size() > 0) {
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}

			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())) {
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j = i + 1; j < list.size(); j++) {
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum)
							|| StringUtils.equals(hi.getActivityId(), histIns.getActivityId())) {
						bl = true;
					}
				}
				if (!bl) {
					break;
				}
			}
		}
		return actList;
	}

	/**
	 * 获取流程列表
	 * 
	 * @param category
	 *            流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion()
				.active().orderByProcessDefinitionKey().asc();

		if (StringUtils.isNotEmpty(category)) {
			processDefinitionQuery.processDefinitionCategory(category);
		}

		page.setCount(processDefinitionQuery.count());

		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(),
				page.getMaxResults());
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			page.getList().add(new Object[] { processDefinition, deployment });
		}
		return page;
	}

	/**
	 * @Description 获取流程
	 * @param procDefKey
	 *            流程定义标识
	 * @return
	 * @author zzm
	 * @date 2016-3-31 下午2:52:06
	 */
	public ProcessDefinition getProcessByProcDefKey(String procDefKey) {
		if (StringUtils.isEmpty(procDefKey)) {
			return null;
		}
		return repositoryService.createProcessDefinitionQuery().latestVersion().active()
				.processDefinitionKey(procDefKey).singleResult();

	}

	/**
	 * @Description 获取流程
	 * @param procDefId
	 *            流程定义Id
	 * @return
	 * @author zzm
	 * @date 2016-5-25 下午3:37:00
	 */
	public ProcessDefinition getProcessByProcDefId(String procDefId) {
		if (StringUtils.isEmpty(procDefId)) {
			return null;
		}
		return repositoryService.createProcessDefinitionQuery().active().processDefinitionId(procDefId).singleResult();
	}

	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * 
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey) {
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)) {
			if (StringUtils.isNotBlank(taskDefKey)) {
				try {
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				} catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)) {
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)) {
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}

	/**
	 * 获取流程实例对象
	 * 
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}

	/**
	 * 启动流程
	 * 
	 * @param procDefKey
	 *            流程定义KEY
	 * @param businessTable
	 *            业务表表名
	 * @param businessId
	 *            业务表编号
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId) {
		return startProcess(procDefKey, businessTable, businessId, "");
	}

	/**
	 * 启动流程
	 * 
	 * @param procDefKey
	 *            流程定义KEY
	 * @param businessTable
	 *            业务表表名
	 * @param businessId
	 *            业务表编号
	 * @param title
	 *            流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcess(procDefKey, businessTable, businessId, title, vars);
	}

	/**
	 * 启动流程
	 * 
	 * @param procDefKey
	 *            流程定义KEY
	 * @param businessTable
	 *            业务表表名
	 * @param businessId
	 *            业务表编号
	 * @param title
	 *            流程标题，显示在待办任务标题
	 * @param vars
	 *            流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title,
			Map<String, Object> vars) {
		String userId = UserUtils.getUser().getLoginName();// ObjectUtils.toString(UserUtils.getUser().getId())

		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);

		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
		}

		// 设置流程标题
		if (StringUtils.isNotBlank(title)) {
			vars.put("title", title);
		}

		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable + ":" + businessId,
				vars);

		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId); // 业务表ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		return act.getProcInsId();
	}

	/**
	 * 获取任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	public Task getTask(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * @Description 以流程实例获取任务
	 * @param procInsId
	 *            流程实例ID
	 * @return
	 * @author zzm
	 * @date 2016-3-31 下午2:24:30
	 */
	public Task getTaskByProcInsId(String procInsId) {
		String userId = UserUtils.getUser().getLoginName();
		return taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active().singleResult();
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param deleteReason
	 *            删除原因
	 */
	public void deleteTask(String taskId, String deleteReason) {
		taskService.deleteTask(taskId, deleteReason);
	}

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            签收用户ID（用户登录名）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);

	}

	/**
	 * 提交任务, 并保存意见
	 * 
	 * @param taskId
	 *            任务ID
	 * @param procInsId
	 *            流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment
	 *            任务提交意见的内容
	 * @param vars
	 *            任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars) {
		complete(taskId, procInsId, comment, "", vars);
	}

	/**
	 * 提交任务, 并保存意见
	 * 
	 * @param taskId
	 *            任务ID
	 * @param procInsId
	 *            流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment
	 *            任务提交意见的内容
	 * @param title
	 *            流程标题，显示在待办任务标题
	 * @param vars
	 *            任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars) {
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)) {
			taskService.addComment(taskId, procInsId, comment);
		}

		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
		}

		// 设置流程标题
		if (StringUtils.isNotBlank(title)) {
			vars.put("title", title);
		}
		Task task = getTask(taskId);

		if (task.getDelegationState() == null) {
			// 提交任务
			taskService.complete(taskId, vars);
		} else {

			// 被委派人完成任务
			taskService.resolveTask(taskId, vars);
			taskService.complete(taskId);

		}

	}

	/**
	 * 完成第一个任务
	 * 
	 * @param procInsId
	 */
	public void completeFirstTask(String procInsId) {
		completeFirstTask(procInsId, null, null, null);
	}

	/**
	 * 完成第一个任务
	 * 
	 * @param procInsId
	 * @param comment
	 * @param title
	 * @param vars
	 */
	@Transactional(readOnly = false)
	public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars) {
		String userId = UserUtils.getUser().getLoginName();
		Task task = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active()
				.singleResult();
		if (task != null) {// 基于用户配置----任务提交方式
			complete(task.getId(), procInsId, comment, title, vars);
		} else {// 基于角色配置----任务提交方式
				// =============== 等待签收的任务 ===============

			TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
					.processInstanceId(procInsId).active().orderByTaskCreateTime().desc();
			// 查询列表
			List<Task> toClaimList = toClaimQuery.list();
			for (Task tk : toClaimList) {
				if (tk != null) {
					taskService.claim(tk.getId(), userId);// 第一个任务直接默认执行签收操作
					complete(tk.getId(), procInsId, comment, title, vars);// 提交任务
				}

			}
		}
	}

	// 退回上一个节点
	public String rollBackWorkFlow(String taskId) {// 可以驳回但是流程图有点问题
		try {
			Map<String, Object> variables;
			// 取得当前任务.当前任务节点
			HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
					.singleResult();
			// 取得流程实例，流程实例
			ProcessInstance instance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(currTask.getProcessInstanceId()).singleResult();
			if (instance == null) {
				// log.info("流程结束");
				// log.error("出错啦！流程已经结束");
				return "ERROR";
			}
			variables = instance.getProcessVariables();
			// 取得流程定义
			ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(currTask.getProcessDefinitionId());
			if (definition == null) {
				// log.info("流程定义未找到");
				// log.error("出错啦！流程定义未找到");
				return "ERROR";
			}
			// 取得上一步活动
			// ProcessDefinitionEntity
			ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
					.findActivity(currTask.getTaskDefinitionKey());

			// 也就是节点间的连线
			List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
			// 清除当前活动的出口
			List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
			// 新建一个节点连线关系集合

			List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
			//
			for (PvmTransition pvmTransition : pvmTransitionList) {
				oriPvmTransitionList.add(pvmTransition);
			}
			pvmTransitionList.clear();

			// 建立新出口
			List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
			for (PvmTransition nextTransition : nextTransitionList) {
				PvmActivity nextActivity = nextTransition.getSource();
				ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition).findActivity(nextActivity.getId());
				TransitionImpl newTransition = currActivity.createOutgoingTransition();
				newTransition.setDestination(nextActivityImpl);
				newTransitions.add(newTransition);
			}
			// 完成任务
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId())
					.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
			for (Task task : tasks) {
				taskService.complete(task.getId(), variables);
				historyService.deleteHistoricTaskInstance(task.getId());
			}
			// 恢复方向
			for (TransitionImpl transitionImpl : newTransitions) {
				currActivity.getOutgoingTransitions().remove(transitionImpl);
			}
			for (PvmTransition pvmTransition : oriPvmTransitionList) {
				pvmTransitionList.add(pvmTransition);
			}
			// log.info("OK");
			// log.info("流程结束");

			return "SUCCESS";
		} catch (Exception e) {
			// log.error("失败");
			// log.error(e.getMessage());
			return "ERROR";
		}
	}

	/**
	 * 获取下一个任务节点
	 * 
	 * @param procInsId
	 * @param comment
	 * @param title
	 * @param vars
	 */
	@Transactional(readOnly = false)
	public void getNextTask(Task task) {
		// 1、首先是根据流程ID获取当前任务：
		// List<Task> tasks =
		// taskService.createTaskQuery().processInstanceId(procInstanceId).list();
		// 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) runtimeService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
		// 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
				.singleResult();
		String activitiId = execution.getActivityId();
		// 4、然后循环activitiList
		// 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					System.out.println("下一步任务任务：" + ac.getProperty("name"));

				}
				break;
			}
		}
	}

	/**
	 * 委派任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            被委派人
	 */
	@Transactional(readOnly = false)
	public void delegateTask(String taskId, String userId) {
		taskService.delegateTask(taskId, userId);
	}

	/**
	 * 被委派人完成任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	public void resolveTask(String taskId) {
		taskService.resolveTask(taskId);
	}

	/**
	 * 结束任务
	 * 
	 * @param taskId
	 */
	public void overTask(String taskId, String processInstanceId, String comment) {
		// 删除流程：
	       taskService.addComment(taskId, processInstanceId, comment);// 备注保留
		   runtimeService.deleteProcessInstance(processInstanceId, "");
		 //historyService.deleteHistoricProcessInstance(processInstanceId);// (顺序不能换)	
	}

	/**
	 * 二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
	 */
	public List<String> findLineListByTaskId(String taskId) {
		// 返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		// 1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				String name = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				} else {
					list.add("默认提交");
				}
			}
		}
		return list;
	}
	////////////////////////////////////////////////////////////////////

	/**
	 * 读取带跟踪的图片
	 * 
	 * @param executionId
	 *            环节ID
	 * @return 封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
		// ProcessInstance processInstance =
		// runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0) {
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}

		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine =
		// (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());

		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
	}

	/**
	 * 流程跟踪图信息
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return 封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance,
					currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * 
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("节点名称", properties.get("name"));
		vars.put("任务类型", ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// 当前节点的task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// 当前任务的分配角色
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * 设置任务组
	 * 
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * 
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee)
					.singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * 
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId())
					.taskDefinitionKey(activitiId).singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * 
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * 
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}

	/**
	 * 判断是否为最后一个节点
	 * @param act
	 * @return
	 */
	public Boolean checkIsEnd(Act act) {
		if(act!=null && StringUtils.isNotBlank(act.getTaskId())){
			// 1.获取流程定义
			Task task = this.taskService.createTaskQuery().taskId(act.getTaskId()).singleResult();
			ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
			// 2.获取流程实例
			ProcessInstance pi =runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult(); 
			// 3.通过流程实例查找当前活动的ID
			String activitiId = pi.getActivityId();
			// 4.通过活动的ID在流程定义中找到对应的活动对象
			ActivityImpl activity = pd.findActivity(activitiId);
			// 5.通过活动对象找当前活动的所有出口
			 List<PvmTransition> transitions =  activity.getOutgoingTransitions();
			// 6.提取所有出口的名称，封装成集合
			 for (PvmTransition trans : transitions) {
			    // String transName = (String) trans.getProperty("name");//连线name
				 PvmActivity ac = trans.getDestination(); //获取线路的终点节点
				 if( ac.getProperty("type").equals("endEvent")){
					 return true;
				 }
				
			 }
		} 
		return false;
	}
	
	
	/**
	 * 获取对应内容的产品ID、名称和用户ID、名称
	 * @param BusinessId 业务ID
	 * @param BusinessTable 业务对应表单
	 * @return
	 */
	private Map<String, Object> getProductNameAndCustomerName(String businessId, String businessTable){
		//xd_dqbg_adminnk 	贷前变更 t_loan_contract
		//xd_fksq_adminnk 	放款申请 t_loan_contract
		//xd_hklc_adminnk 	还款流程 t_repay_check（还款确认不需要对应产品）
		//xd_hzhx_adminnk 	坏账核销 t_loan_contract
		//xd_jchmd_adminnk 	解除黑名单 （农垦不需要）
		//xd_jrhmd_adminnk 	加入黑名单 （农垦不需要）
		//xd_lxdc_adminnk 	立项调查 t_loan_contract
		//xd_qdht 			签订合同 t_contract_audit
		//xd_sxhssq 		授信会审 t_credit_apply
		//xd_sxsq_adminnk 	授信申请 （无）
		//xd_tfsq_adminnk 	退费申请 t_loan_contract
		//xd_tqhk_adminnk 	提前还款 t_loan_contract
		//xd_wjfl_adminnk 	五级分类 t_loan_contract
		//xd_ypjc_adminnk 	押品借出 t_loan_contract
		//xd_ytgz_adminnk 	用途跟踪 t_loan_contract
		//xd_ywsq_adminnk 	业务申请 t_loan_contract
		//xd_zqsq_adminnk 	展期申请 t_loan_contract
		String sql = null;
		if("t_loan_contract".equals(businessTable)){
			sql = "select a.customer_id as customerId,a.customer_name as customerName,a.product_id as productId,p.name as productname from t_loan_contract a LEFT JOIN t_product p ON p.id = a.product_id where a.id='"+businessId+"' limit 1";
		}else if("t_repay_check".equals(businessTable)){
			Map<String, Object> map = Maps.newHashMap();
			map.put("customerId", "无");
			map.put("customerName", "无");
			map.put("productId", "无");
			map.put("productname", "无");
			return map;
		}else if("t_credit_apply".equals(businessTable)){//授信会审
			sql = "SELECT customer_id AS customerId,customer_name AS customerName,product_id AS productId,product_name AS productname FROM t_credit_apply WHERE id='"+businessId+"' LIMIT 1";
		}else if("t_contract_audit".equals(businessTable)){//签订合同
			sql = "SELECT c.customer_id AS customerId, c.customer_name AS customerName, c.product_id AS productId, p. NAME AS productname FROM t_contract_audit a LEFT JOIN t_loan_contract c ON c.id = a.loan_contract_id LEFT JOIN t_product p ON p.id = c.product_id WHERE a.id = '"+businessId+"' LIMIT 1";
		}else if("t_bad_debt_regist".equals(businessTable)){//签订合同
			sql = "SELECT c.customer_id AS customerId, c.customer_name AS customerName, c.product_id AS productId, p. NAME AS productname FROM t_bad_debt_regist a LEFT JOIN t_loan_contract c ON c.id = a.loan_contract_id LEFT JOIN t_product p ON p.id = c.product_id WHERE a.id = '"+businessId+"' LIMIT 1";
		}else if("t_advance".equals(businessTable)){//签订合同
			sql = "SELECT c.customer_id AS customerId, c.customer_name AS customerName, c.product_id AS productId, p. NAME AS productname FROM t_advance a LEFT JOIN t_loan_contract c ON c.id = a.loan_contract_id LEFT JOIN t_product p ON p.id = c.product_id WHERE a.id = '"+businessId+"' LIMIT 1";
		}else if("t_mortgage_contract".equals(businessTable)){//抵押合同
			sql = "SELECT c.customer_id AS customerId, c.customer_name AS customerName, c.product_id AS productId, p. NAME AS productname FROM t_mortgage_contract a LEFT JOIN t_loan_contract c ON c.id = a.business_id LEFT JOIN t_product p ON p.id = c.product_id WHERE a.id = '"+businessId+"' LIMIT 1";
		}else if("t_pledge_contract".equals(businessTable)){//质押合同
			sql = "SELECT c.customer_id AS customerId, c.customer_name AS customerName, c.product_id AS productId, p. NAME AS productname FROM t_pledge_contract a LEFT JOIN t_loan_contract c ON c.id = a.business_id LEFT JOIN t_product p ON p.id = c.product_id WHERE a.id = '"+businessId+"' LIMIT 1";
		}
		if(sql != null){
			Record record = Db.findFirst(sql);
			if(record != null){
				Map<String, Object> map = Maps.newHashMap();
				map.put("customerId", record.getStr("customerId"));
				map.put("customerName", record.getStr("customerName"));
				map.put("productId", record.getStr("productId"));
				map.put("productname", record.getStr("productname"));
				return map;
			}
		}
		return null;
	}
}


