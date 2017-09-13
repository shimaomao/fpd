<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>征信次数统计管理</title>
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
		<li class="active"><a href="${ctx}/credit/creditTotal/">征信次数统计列表</a></li>
		<shiro:hasPermission name="credit:creditTotal:edit"><li><a href="${ctx}/credit/creditTotal/form">征信次数统计添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="creditTotal" action="${ctx}/credit/creditTotal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<th>备注</th>
				<shiro:hasPermission name="credit:creditTotal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="creditTotal">
			<tr>
				<td><a href="${ctx}/credit/creditTotal/form?id=${creditTotal.id}">
					<fmt:formatDate value="${creditTotal.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${creditTotal.remarks}
				</td>
				<shiro:hasPermission name="credit:creditTotal:edit"><td>
    				<a href="${ctx}/credit/creditTotal/form?id=${creditTotal.id}">修改</a>
					<a href="${ctx}/credit/creditTotal/delete?id=${creditTotal.id}" onclick="return confirmx('确认要删除该征信次数统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>