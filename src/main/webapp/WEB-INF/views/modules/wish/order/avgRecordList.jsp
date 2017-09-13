<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>历史平均数据管理</title>
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
		<li class="active"><a href="${ctx}/wish.order/avgRecord/">历史平均数据列表</a></li>
		<shiro:hasPermission name="wish.order:avgRecord:edit"><li><a href="${ctx}/wish.order/avgRecord/form">历史平均数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="avgRecord" action="${ctx}/wish.order/avgRecord/" method="post" class="breadcrumb form-search">
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
				<th>remarks</th>
				<shiro:hasPermission name="wish.order:avgRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="avgRecord">
			<tr>
				<td><a href="${ctx}/wish.order/avgRecord/form?id=${avgRecord.id}">
					<fmt:formatDate value="${avgRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${avgRecord.remarks}
				</td>
				<shiro:hasPermission name="wish.order:avgRecord:edit"><td>
    				<a href="${ctx}/wish.order/avgRecord/form?id=${avgRecord.id}">修改</a>
					<a href="${ctx}/wish.order/avgRecord/delete?id=${avgRecord.id}" onclick="return confirmx('确认要删除该历史平均数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>