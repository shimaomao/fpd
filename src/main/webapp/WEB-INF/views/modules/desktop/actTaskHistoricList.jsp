<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
        	location = '${ctx}/act/task/historic/?pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
			<li><a href="${ctx}/index/todo/todoList">待办任务</a></li>
		    <li  class="active"><a href="${ctx}/index/todo/historic/">已办任务</a></li>
			<shiro:hasPermission name="lending:tLending:view">
				<li ><a href="${ctx}/index/todo/financeTodoList">财务</a></li>
			</shiro:hasPermission>
			<li><a href="${ctx}/index/todo/warnList">提醒</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/historic/" method="get" class="breadcrumb form-search">
		<div>
<!-- 			<label>流程类型：&nbsp;</label> -->
<%-- 			<form:select path="procDefKey" class="input-medium"> --%>
<%-- 				<form:option value="" label="全部流程"/> --%>
<%-- 				<form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 			</form:select> --%>
			<label>完成时间：</label>
			<input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>客户名称</th>
				<th>产品名称</th>
				<!-- <th>当前环节</th> -->
				<th>流程名称</th>
				<th>流程版本</th>
				<th>完成时间</th>
				<th>正在执行环节</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="act">
				<c:set var="task" value="${act.histTask}" />
			    <c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" />
				<c:set var="status" value="${act.status}" />
				<tr>
					<td>${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</td>
					<td>
						${act.customerName}
					</td>
					<td>
						${act.productname}
					</td>
					<%-- <td>
					     <a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.name}</a>
					</td> --%>
					<td>${procDef.name}</td>
					<td><b title='流程版本号'>V: ${procDef.version}</b></td>
					<td><fmt:formatDate value="${task.endTime}" type="both"/></td>
					<td>
					     <a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${act.assignee}</a>
					</td>
				<%-- 	<td>
					    <c:choose>
					        <c:when test="${act.businessTable=='t_employee'}">
					            <a href="${ctx}/act/task/employeeview?id=${act.businessId}">详情</a>
					            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
					        </c:when>
					        <c:when test="${act.businessTable=='t_company'}">
					            <a href="${ctx}/act/task/companyview?id=${act.businessId}">详情</a>
					            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
					        </c:when>
					        <c:when test="${act.businessTable=='t_loan_contract'}">
					            <a href="${ctx}/contract/tLoanContract/detail?id=${act.businessId}">详情</a>
					            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
					        </c:when>
					         <c:when test="${act.businessTable=='t_five_level'}">
					            <a href="${ctx}/postlending/fivelevel/fiveLevel/detail?id=${act.businessId}">详情</a>
					            <a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">节点图</a>
					            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
					        </c:when>
					        <c:otherwise>
								<a href="${ctx}/act/task/form?procDefId=${task.processDefinitionId}&procInsId=${task.processInstanceId}&businessId=${act.businessId}&status=${status}">详情</a>	
								 <a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">节点图</a>	
								  <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>			        
					        </c:otherwise>
					    </c:choose>
					</td> --%>
					<td>
						<%-- <c:if test="${!empty syscode}"> --%>
						    <c:choose>
						        <c:when test="${act.businessTable=='t_employee'}">
						       <%--  <c:if test="${!empty syscode}">
						            <a href="${ctx}/act/task/employeeview?id=${act.businessId}">详情</a>
						        </c:if> --%>
						        	<a href="${ctx}/act/task/employeeview?id=${act.businessId}">详情</a>
						        	<%-- <c:set var="reurl" value="/act/task/form%3FtaskId=${task.id}%26taskName=${fns:urlEncode(task.name)}%26taskDefKey=${task.taskDefinitionKey}%26procInsId=${task.processInstanceId}%26procDefId=${task.processDefinitionId}%26status=${status}"></c:set>
									<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">详情</a> --%>
						            
						            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
						        </c:when>
						        <c:when test="${act.businessTable=='t_company'}">
						            <a href="${ctx}/act/task/companyview?id=${act.businessId}">详情</a>
						            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
						        </c:when>
						        <c:when test="${act.businessTable=='t_loan_contract'}">
						            <%-- <a href="${ctx}/contract/tLoanContract/detail?id=${act.businessId}">详情</a> --%>
						            <c:set var="reurl" value="/contract/tLoanContract/detail%3Fid=${act.businessId}"></c:set>
									<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">详情</a>
						            
						            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
						        </c:when>
						         <c:when test="${act.businessTable=='t_five_level'}">
						            <%-- <a href="${ctx}/postlending/fivelevel/fiveLevel/detail?id=${act.businessId}">详情</a> --%>
						            <c:set var="reurl" value="/postlending/fivelevel/fiveLevel/detail%3Fid=${act.businessId}"></c:set>
									<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">详情</a>
						            
						            <%-- <a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">节点图</a> --%>
						            <a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>
						        </c:when>
						        <c:otherwise>
									<%-- <a href="${ctx}/act/task/form?procDefId=${task.processDefinitionId}&procInsId=${task.processInstanceId}&businessId=${act.businessId}&status=${status}">详情</a> --%>	
									<c:set var="reurl" value="/act/task/form%3FprocDefId=${task.processDefinitionId}%26procInsId=${task.processInstanceId}%26businessId=${act.businessId}%26status=${status}"></c:set>
									<a href="${ctx}/syscode/${act.productId}.html?istrue=1&reurl=${reurl}" target="_top">详情</a>
									<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a>			        
						        </c:otherwise>
						    </c:choose>
						<%-- </c:if> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
