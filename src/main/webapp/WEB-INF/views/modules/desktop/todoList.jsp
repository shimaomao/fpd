<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.LoanContractStatus" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		/**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
		/**
		 * 委派任务
		 */
		function delegateTask(taskId) {
			$.get('${ctx}/act/task/delegateTask' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('委派完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('委派失败');
				}
		    });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		   	<li class="active"><a href="${ctx}/index/todo/todoList">待办任务</a></li>
		    <li><a href="${ctx}/index/todo/historic/">已办任务</a></li>
			<shiro:hasPermission name="lending:tLending:view">
				<li ><a href="${ctx}/index/todo/financeTodoList">财务</a></li>
			</shiro:hasPermission>
			<li><a href="${ctx}/index/todo/warnList">提醒</a></li>
	</ul>
	<sys:message content="${message}"/>
		<form:form id="searchForm" modelAttribute="act" action="${ctx}/index/todo/todoList" method="get" class="breadcrumb form-search">
		<div>
			<label>创建时间：</label>
			<input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;" value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			  　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;" value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>客户名称</th>
				<th>产品名称</th>
				<th>当前环节</th>
				<th>流程名称</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${actList}" var="act">
				<c:set var="task" value="${act.task}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" />
				<c:set var="status" value="${act.status}" />
				<tr>
					<td>
						<%-- <c:if test="${empty task.assignee}">
						    <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>
						</c:if>
						<c:if test="${not empty task.assignee}">
							<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>
						</c:if> --%>
						<c:set var="reurl" value="/act/task/form%3FtaskId=${task.id}%26taskName=${fns:urlEncode(task.name)}%26taskDefKey=${task.taskDefinitionKey}%26procInsId=${task.processInstanceId}%26procDefId=${task.processDefinitionId}%26status=${status}"></c:set>
						<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>
						
					</td>
					<td>
						${act.customerName}
					</td>
					<td>
						${act.productname}
					</td>
					<td>
						<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.name}</a>
					</td>
					<td>${procDef.name}</td>
					<td><fmt:formatDate value="${task.createTime}" type="both"/></td>
					
					
					
					
					
					<td>
						<c:if test="${empty task.assignee}">
							<%-- <a href="javascript:claim('${task.id}');">签收任务</a> --%>
							<%-- <a href="${ctx}/act/task/claimForm?taskId=${task.id}">任务办理</a> --%>
							<c:set var="reurl" value="/act/task/claimForm%3FtaskId=${task.id}"></c:set>
							<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">任务办理</a>	
						</c:if>
						<c:if test="${not empty task.assignee}">
							<%-- <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">任务办理</a> --%>
							<c:set var="reurl" value="/act/task/form%3FtaskId=${task.id}%26taskName=${fns:urlEncode(task.name)}%26taskDefKey=${task.taskDefinitionKey}%26procInsId=${task.processInstanceId}%26procDefId=${task.processDefinitionId}%26status=${status}"></c:set>
							<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">任务办理</a>	
						</c:if>
						<shiro:hasPermission name="act:process:edit">
							<c:if test="${empty task.executionId}">
								<%-- <a href="${ctx}/act/task/deleteTask?taskId=${task.id}&reason=" onclick="return promptx('删除任务','删除原因',this.href);">删除任务</a> --%>
								<c:set var="reurl" value="/act/task/deleteTask%3FtaskId=${task.id}%26reason="></c:set>
								<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" onclick="return promptx('删除任务','删除原因',this.href);" target="_top">删除任务</a>	
							</c:if>
						</shiro:hasPermission>
						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
					</td>
				</tr>
			</c:forEach>
			
			
			
			
			<c:forEach items="${signList}" var="contract">
				<c:set var="toSign" value="<%=LoanContractStatus.TO_SIGN %>" />
				<c:set var="etToSign" value="<%=LoanContractStatus.ET_TO_SIGN %>" />
				<tr>
					<%-- 贷款合同待签订 --%>
					<c:if test="${toSign == contract.status }">
					<td>
						<c:set var="reurl" value="/contract/contractAudit/toSign%3FloanContract.id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${contract.productId}?istrue=1&reurl=${reurl}" target="_top">${contract.contractNumber}</a>
					</td>
					<td>
						${contract.customerName}
					</td>
					<td>
						${contract.productname}
					</td>
					<td><c:set var="reurl" value="/contract/contractAudit/toSign%3FloanContract.id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${contract.productId}?istrue=1&reurl=${reurl}" target="_top">待签订贷款合同</a></td>
					<td>（无）</td>
					<td><fmt:formatDate value="${contract.createDate}" type="both"/></td>
					<td><%-- <a href="${ctx}/contract/tLoanContract/toSign?id=${contract.id}">签订合同</a> --%>
						<c:set var="reurl" value="/contract/contractAudit/toSign%3FloanContract.id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${contract.productId}?istrue=1&reurl=${reurl}" target="_top">签订合同</a>	
					</td>
					</c:if>
					
					<%-- 展期合同待签订 --%>
					<c:if test="${etToSign == contract.status }">
					<td>
						<c:set var="reurl" value="/contract/tLoanContract/extendForm%3Foption=sign%26id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">${contract.contractNumber}</a>
					</td>
					<td>
						${act.customerName}
					</td>
					<td>
						${act.productname}
					</td>
					<td><c:set var="reurl" value="/contract/tLoanContract/extendForm%3Foption=sign%26id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">待签订展期合同</a></td>
					<td>（无）</td>
					<td><fmt:formatDate value="${contract.createDate}" type="both"/></td>
					<td><%-- <a href="${ctx}/contract/tLoanContract/extendForm?option=sign&id=${contract.id}">签订合同</a> --%>
						<c:set var="reurl" value="/contract/tLoanContract/extendForm%3Foption=sign%26id=${contract.id}"></c:set>		
						<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">签订合同</a>	
					</td>
					</c:if>
				</tr>
			</c:forEach>
			
			
			
			
			<!-- 授信申请待签订 -->
			<c:forEach items="${creditSignList}" var="creditApply">
				<tr>
					<td>
						<c:set var="reurl" value="/credit/creditApply/toSign%3Fid=${creditApply.id}"></c:set>
						<a href="${ctx}/syscode/${creditApply.productId}.html?istrue=1&reurl=${reurl}" target="_top">${creditApply.applyNum}</a>
					</td>
					<td>
						${creditApply.customerName}
					</td>
					<td>
						${creditApply.productname}
					</td>
					<td><c:set var="reurl" value="/credit/creditApply/toSign%3Fid=${creditApply.id}"></c:set>
						<a href="${ctx}/syscode/${creditApply.productId}.html?istrue=1&reurl=${reurl}" target="_top">待签订授信申请</a></td>
					<td>（无）</td>
					<td><fmt:formatDate value="${creditApply.createDate}" type="both"/></td>
					
					
					<%-- <a href="${ctx}/act/task/form?procDefId=${task.processDefinitionId}&procInsId=${task.processInstanceId}&businessId=${act.businessId}&status=${status}">详情</a> --%>	
					<td>									
						<%-- <a href="${ctx}/credit/creditApply/toSign?id=${creditApply.id}">签订合同</a> --%>
						<c:set var="reurl" value="/credit/creditApply/toSign%3Fid=${creditApply.id}"></c:set>
						<a href="${ctx}/syscode/${creditApply.productId}.html?istrue=1&reurl=${reurl}" target="_top">签订合同</a>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
