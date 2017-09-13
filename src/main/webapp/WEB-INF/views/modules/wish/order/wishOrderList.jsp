<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>原始订单数据管理</title>
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
		<li class="active"><a href="${ctx}/wish.order/wishOrder/">原始订单数据列表</a></li>
		<shiro:hasPermission name="wish.order:wishOrder:edit"><li><a href="${ctx}/wish.order/wishOrder/form">原始订单数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wishOrder" action="${ctx}/wish.order/wishOrder/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="wish.order:wishOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wishOrder">
			<tr>
				<td><a href="${ctx}/wish.order/wishOrder/form?id=${wishOrder.id}">
					<fmt:formatDate value="${wishOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${wishOrder.remarks}
				</td>
				<shiro:hasPermission name="wish.order:wishOrder:edit"><td>
    				<a href="${ctx}/wish.order/wishOrder/form?id=${wishOrder.id}">修改</a>
					<a href="${ctx}/wish.order/wishOrder/delete?id=${wishOrder.id}" onclick="return confirmx('确认要删除该原始订单数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>