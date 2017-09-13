<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>押品借出审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mortgage/tMortgageApplay/">押品借出审批列表</a></li>
		<shiro:hasPermission name="mortgage:tMortgageApplay:edit"><li><a href="${ctx}/mortgage/tMortgageApplay/form">押品借出审批添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tMortgageApplay" action="${ctx}/mortgage/tMortgageApplay/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>抵押合同ID：</label>
				<form:input path="mortgageContractId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>抵押合同ID</th>
				<th>申请类型(抵押，质押)</th>
				<th>押出开始时间</th>
				<th>押出结束时间</th>
				<th>审核状态</th>
				<th>备注</th>
				<shiro:hasPermission name="mortgage:tMortgageApplay:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tMortgageApplay">
			<tr>
				<td><a href="${ctx}/mortgage/tMortgageApplay/form?id=${tMortgageApplay.id}">
					${tMortgageApplay.mortgageContractId}
				</a></td>
				<td>
					${tMortgageApplay.applayType}
				</td>
				<td>
					${tMortgageApplay.startTime}
				</td>
				<td>
					${tMortgageApplay.endTime}
				</td>
				<td>
					${tMortgageApplay.status}
				</td>
				<td>
					${tMortgageApplay.remarks}
				</td>
				<shiro:hasPermission name="mortgage:tMortgageApplay:edit"><td>
    				<a href="${ctx}/mortgage/tMortgageApplay/form?id=${tMortgageApplay.id}">修改</a>
					<a href="${ctx}/mortgage/tMortgageApplay/delete?id=${tMortgageApplay.id}" onclick="return confirmx('确认要删除该押品借出审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>