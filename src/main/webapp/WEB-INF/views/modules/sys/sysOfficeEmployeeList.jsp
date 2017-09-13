w<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>从业人员管理</title>
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
		<c:if test="${user.id == '1' }">
			<li><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/sys/sysOfficeEmployee/">从业人员列表</a></li>
		<c:if test="${user.id != '1' }">
		<shiro:hasPermission name="sys:sysOfficeEmployee:edit"><li><a href="${ctx}/sys/sysOfficeEmployee/form">从业人员添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="sysOfficeEmployee" action="${ctx}/sys/sysOfficeEmployee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<%-- <li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>状态:</label>
				<form:input path="status" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th><th>职务</th><th class="sort-column name">姓名</th><th class="">学历</th><th class="">电话</th><th>状态</th>
				<shiro:hasPermission name="sys:sysOfficeEmployee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOfficeEmployee" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td>
					${sysOfficeEmployee.position}
				</td>
				<td><a href="${ctx}/sys/sysOfficeEmployee/form?id=${sysOfficeEmployee.id}">
					${sysOfficeEmployee.name}
				</a></td>
				<td>
					${fns:getDictLabel(sysOfficeEmployee.xueLi, 'education', '无')}
				</td>
				<td>
					${sysOfficeEmployee.phone}
				</td>
				<td>
					${sysOfficeEmployee.status}
				</td>
				<shiro:hasPermission name="sys:sysOfficeEmployee:edit"><td>
    				<a href="${ctx}/sys/sysOfficeEmployee/form?id=${sysOfficeEmployee.id}">修改</a>
					<a href="${ctx}/sys/sysOfficeEmployee/delete?id=${sysOfficeEmployee.id}" onclick="return confirmx('确认要删除该从业人员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>