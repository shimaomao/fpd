/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.act.web;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.act.utils.ActUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wanfin.fpd.modules.spouse.service.TSpouseService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 流程个人任务相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/act/task")
public class ActTaskController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;
	
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired 
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private TSpouseService tSpouseService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = {"todo", ""})
	public String todoList(Act act, HttpServletResponse response, Model model) throws Exception {
		List<Act> list = actTaskService.todoList(act);
		model.addAttribute("list", list);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		//============================财务专用     还款方面      ================================
		//============================待签订合同数量    ================================
		//============================退回合同数量     ================================
		//============================逾期合同数量     ================================
		//============================业务变更提醒     ================================
		return "modules/act/actTaskTodoList";
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "historic")
	public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<Act> page = new Page<Act>(request, response);
		page = actTaskService.historicList(page, act);
		model.addAttribute("page", page);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/act/actTaskHistoricList";
	}

	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "histoicFlow")
	public String histoicFlow(Act act, String startAct, String endAct, Model model){
		if (StringUtils.isNotBlank(act.getProcInsId())){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
		return "modules/act/actTaskHistoricFlow";
	}
	
	
	/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	@RequestMapping(value = "lineNameList")
	public String findLineNameList(String taskId, Model model){
		if (StringUtils.isNotBlank(taskId)){
			List<String> lineList = actTaskService.findLineListByTaskId(taskId);
			model.addAttribute("lineList", lineList);
		}
		return "modules/act/lineNameList";
	}
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	@RequestMapping(value = "process")
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<Object[]> page = new Page<Object[]>(request, response);
	    page = actTaskService.processList(page, category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/act/actTaskProcessList";
	}
	
	/**
	 * 获取流程表单
	 * @param taskId	任务ID
	 * @param taskName	任务名称
	 * @param taskDefKey 任务环节标识
	 * @param procInsId 流程实例ID
	 * @param procDefId 流程定义ID
	 */
	@RequestMapping(value = "form")
	public String form(Act act, HttpServletRequest request, Model model){
		
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
		// 获取流程实例对象
		if (act.getProcInsId() != null){
			act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
		}
		
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
		
//		// 传递参数到视图
//		model.addAttribute("act", act);
//		model.addAttribute("formUrl", formUrl);
//		return "modules/act/actTaskForm";
	}
	
	/**
	 * 直接由签收转执行
	 * add 2017-04-12
	 * http://localhost:8080/guangken/a/act/task/form?
		taskId=6c3ea99e4beb44ecafcc5dae3f50e7ac
		taskName=%E5%AE%A2%E6%88%B7%E6%80%BB%E7%9B%91
		taskDefKey=sid-13150025-2DD1-45EA-B28D-2A5E80EDB0E6
		procInsId=87c1322233484d8bbf3b31545dd09d3c
		procDefId=xd_sxhssq:2:3f76434fc3ad4623988d11d121743f1f
		status=todo
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "claimForm")
	public String claimForm(Act act, HttpServletRequest request, Model model) {
		String userId = UserUtils.getUser().getLoginName();
		actTaskService.claim(act.getTaskId(), userId);//执行签收任务
		Task task = actTaskService.getTask(act.getTaskId());
//		System.out.println("TaskId:"+act.getTaskId());
//		System.out.println("Task Name:"+task.getName());
//		System.out.println("TaskDefinitionKey:"+task.getTaskDefinitionKey());//=taskDefKey
//		System.out.println("ProcessInstanceId:"+task.getProcessInstanceId());//=procInsId
//		System.out.println("ProcessDefinitionId:"+task.getProcessDefinitionId());//=procDefId
		//System.out.println("Variables:"+task.getTaskLocalVariables().toString());
		if(task != null){
			act.setTaskName(task.getName());
			act.setTaskDefKey(task.getTaskDefinitionKey());
			act.setProcInsId(task.getProcessInstanceId());
			act.setProcDefId(task.getProcessDefinitionId());
			act.setStatus("todo");
			// 获取流程实例对象
			if (act.getProcInsId() != null){
				act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
			}
			// 获取流程XML上的表单KEY
			String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
			System.out.println("--------formKey:"+formKey);
			
			if(StringUtils.isNotBlank(formKey)){
				return "redirect:" + ActUtils.getFormUrl(formKey, act);
			}
		}
		
		return "redirect:" + adminPath + "/act/task";
	}
	
	/**
	 * @Description 获取流程表单
	 * @param businessId	业务ID
	 * @param businessTable	业务表名
	 * @param procDefKey 流程定义标识
	 * @param procInsId 流程实例ID
	 * @author zzm 
	 * @date 2016-3-31 下午3:10:49  
	 */
	@RequestMapping(value = "dform")
	public String dform(Act act, HttpServletRequest request, Model model){
		
		//补充上流程定义和任务的信息
		if (act.getProcInsId() != null){
			//执行中的流程
			//获取任务task
			act.setTask(actTaskService.getTaskByProcInsId(act.getProcInsId()));
		} else if(StringUtils.isNotBlank(act.getProcDefKey())){
			//未启动流程
			//获取流程定义
			ProcessDefinition procDef = actTaskService.getProcessByProcDefKey(act.getProcDefKey());
			if(procDef != null){
				act.setProcDef(procDef);
				act.setProcDefId(procDef.getId());
				act.setProcDefKey(procDef.getKey());
			}
		}
		
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());

		
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
		
	}
	
	/**
	 * 获取流程表单
	 * @param taskId	任务ID
	 * @param taskName	任务名称
	 * @param taskDefKey 任务环节标识
	 * @param procInsId 流程实例ID
	 * @param procDefId 流程定义ID
	 */
	@RequestMapping(value = "auditForm")
	public String auditForm(Act act, HttpServletRequest request, Model model){
		model.addAttribute("act", act);
		return "modules/act/actAuditForm";
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(Act act, String table, String id, Model model) throws Exception {
		actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
		return "true";//adminPath + "/act/task";
	}
	

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	/**
	 * 委派任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "delegateTask")
	@ResponseBody
	public String delegateTask(Act act,String userId) {
		userId="sszjl2";
		actTaskService.delegateTask(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务流程变量，如下
	 * 		vars.keys=flag,pass
	 * 		vars.values=1,true
	 * 		vars.types=S,B  @see com.thinkgem.jeesite.modules.act.utils.PropertyType
	 */
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		if(StringUtils.isBlank(act.getTaskId())){
			//@zzm 20160331  如果流程未启动，则启动流程，并执行第一个任务
			String procInsId = actTaskService.startProcess(act.getProcDefKey(),act.getBusinessTable(), act.getBusinessId(), act.getTitle());
			actTaskService.completeFirstTask(procInsId, act.getComment(), act.getTitle(), act.getVars().getVariableMap());
		}else{
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
		}
		return "true";//adminPath + "/act/task";
	}
	/*@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		if(StringUtils.isBlank(act.getTaskId())){
			//@zzm 20160331  如果流程未启动，则启动流程，并执行第一个任务
			String procInsId = actTaskService.startProcess(act.getProcDefKey(),act.getBusinessTable(), act.getBusinessId(), act.getTitle());
			actTaskService.completeFirstTask(procInsId, act.getComment(), act.getTitle(), act.getVars().getVariableMap());
		}else{
			if(act.getVars().getVariableMap().get("pass").equals("1")){
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
			}else if(act.getVars().getVariableMap().get("pass").equals("0")){
				actTaskService.overTask(act.getTaskId(),act.getProcInsId(),act.getComment());
			}
			
		}
		return "true";//adminPath + "/act/task";
	}*/
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
		
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 输出跟踪流程信息
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "trace/info/{proInsId}")
	public List<Map<String, Object>> traceInfo(@PathVariable("proInsId") String proInsId) throws Exception {
		List<Map<String, Object>> activityInfos = actTaskService.traceProcess(proInsId);
		return activityInfos;
	}

	/**
	 * 显示流程图
	 
	@RequestMapping(value = "processPic")
	public void processPic(String procDefId, HttpServletResponse response) throws Exception {
		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}*/
	
	/**
	 * 获取跟踪信息
	 
	@RequestMapping(value = "processMap")
	public String processMap(String procDefId, String proInstId, Model model)
			throws Exception {
		List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
		for (String activeId : activeActivityIds) {
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activityImpl.isScope()) {
					if (activityImpl.getActivities().size() > 1) {
						List<ActivityImpl> subAcList = activityImpl
								.getActivities();
						for (ActivityImpl subActImpl : subAcList) {
							String subid = subActImpl.getId();
							System.out.println("subImpl:" + subid);
							if (activeId.equals(subid)) {// 获得执行到那个节点
								actImpls.add(subActImpl);
								break;
							}
						}
					}
				}
				if (activeId.equals(id)) {// 获得执行到那个节点
					actImpls.add(activityImpl);
					System.out.println(id);
				}
			}
		}
		model.addAttribute("procDefId", procDefId);
		model.addAttribute("proInstId", proInstId);
		model.addAttribute("actImpls", actImpls);
		return "modules/act/actTaskMap";
	}*/
	
	/**
	 * 删除任务
	 * @param taskId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteTask")
	public String deleteTask(String taskId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			actTaskService.deleteTask(taskId, reason);
			addMessage(redirectAttributes, "删除任务成功，任务ID=" + taskId);
		}
		return "redirect:" + adminPath + "/act/task";
	}
	
	
	@RequestMapping(value = "companyview")
	public String companyview(TCompany tCompany, Model model) {
		if (StringUtils.isBlank(tCompany.getId())){
			//新增是设置一个临时id, 以"new_"开头表示
			tCompany.setId("new_"+IdGen.uuid());
		}
		FModel fm = FModel.M_CUSTOMER_COMPANY;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tCompany);
		return "modules/act/tCompanyView";
	}
	
	
	@RequestMapping(value = "employeeview")
	public String employeeview(TEmployee tEmployee, Model model) {
		if (StringUtils.isBlank(tEmployee.getId())){
			//新增是设置一个临时id, 以"new_"开头表示
			tEmployee.setId("new_"+IdGen.uuid());
		}
		FModel fm = FModel.M_CUSTOMER_EMPLOYEE;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tEmployee);
		
		TSpouse   spouse = new TSpouse();
		if(!tEmployee.getId().startsWith("new_")){//不以new开头，说明是修改，根据客户信息查出其配偶信息主键id
			Record id  = tEmployeeService.getSpouseId(tEmployee.getId());
			if(id!=null&&!"".equals(id)){
				 spouse= tSpouseService.get(id.getStr("id"));
			}
		}
		
		model.addAttribute("spouse", spouse);
		return "modules/act/tEmployeeView";
	}

}
