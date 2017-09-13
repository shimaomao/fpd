<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存自动生成成功管理</title>
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
		<li class="active"><a href="${ctx}/testgen/testGen/">保存自动生成成功列表</a></li>
		<shiro:hasPermission name="testgen:testGen:edit"><li><a href="${ctx}/testgen/testGen/form">保存自动生成成功添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="testGen" action="${ctx}/testgen/testGen/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>area：</label>
				<form:input path="area" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>name</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="testgen:testGen:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="testGen">
			<tr>
				<td><a href="${ctx}/testgen/testGen/form?id=${testGen.id}">
					${testGen.name}
				</a></td>
				<td>
					<fmt:formatDate value="${testGen.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${testGen.remarks}
				</td>
				<shiro:hasPermission name="testgen:testGen:edit"><td>
    				<a href="${ctx}/testgen/testGen/form?id=${testGen.id}">修改</a>
					<a href="${ctx}/testgen/testGen/delete?id=${testGen.id}" onclick="return confirmx('确认要删除该保存自动生成成功吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>