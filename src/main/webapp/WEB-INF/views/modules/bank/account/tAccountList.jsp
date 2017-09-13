<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>银行账户管理</title>
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
		<li class="active"><a href="${ctx}/bank/account/tAccount/">银行账户列表</a></li>
		<shiro:hasPermission name="bank:account:tAccount:edit"><li><a href="${ctx}/bank/account/tAccount/form">银行账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tAccount" action="${ctx}/bank/account/tAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户id：</label>
				<form:input path="customerId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>客户类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>开户行：</label>
				<form:input path="gatheringBank" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户名：</label>
				<form:input path="gatheringName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户账号：</label>
				<form:input path="gatheringNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="bank:account:tAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tAccount">
			<tr>
				<td><a href="${ctx}/bank/account/tAccount/form?id=${tAccount.id}">
					<fmt:formatDate value="${tAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${tAccount.remarks}
				</td>
				<shiro:hasPermission name="bank:account:tAccount:edit"><td>
    				<a href="${ctx}/bank/account/tAccount/form?id=${tAccount.id}">修改</a>
					<a href="${ctx}/bank/account/tAccount/delete?id=${tAccount.id}" onclick="return confirmx('确认要删除该银行账户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>