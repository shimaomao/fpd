<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自动业务管理</title>
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
		<li class="active"><a href="${ctx}/wauto/wAuto/">自动业务列表</a></li>
		<shiro:hasPermission name="wauto:wAuto:edit"><li><a href="${ctx}/wauto/wAuto/form">自动业务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wAuto" action="${ctx}/wauto/wAuto/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表单名：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>表单名</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="wauto:wAuto:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wAuto">
			<tr>
				<td><a href="${ctx}/wauto/wAuto/form?id=${wAuto.id}">
					${wAuto.name}
				</a></td>
				<td>
					<fmt:formatDate value="${wAuto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wAuto.remarks}
				</td>
				<shiro:hasPermission name="wauto:wAuto:edit"><td>
    				<a href="${ctx}/wauto/wAuto/form?id=${wAuto.id}">修改</a>
					<a href="${ctx}/wauto/wAuto/delete?id=${wAuto.id}" onclick="return confirmx('确认要删除该自动业务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>