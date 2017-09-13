<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板初始化管理</title>
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
		<li class="active"><a href="${ctx}/init/sysInitFormtpl/">模板初始化列表</a></li>
		<shiro:hasPermission name="init:sysInitFormtpl:edit"><li><a href="${ctx}/init/sysInitFormtpl/form">模板初始化添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysInitFormtpl" action="${ctx}/init/sysInitFormtpl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>租户：</label>
				<form:input path="officeName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>记录名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>模板：</label>
				<form:input path="formName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>租户管理员：</label>
				<form:input path="organId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>租户</th>
				<th>记录名</th>
				<th>模板</th>
				<th>租户管理员ID</th>
				<th>更新时间</th>
				<shiro:hasPermission name="init:sysInitFormtpl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysInitFormtpl">
			<tr>
				<td>${sysInitFormtpl.officeName}</td>
				<td>${sysInitFormtpl.name}</td>
				<td>${sysInitFormtpl.formName}</td>
				<td>${sysInitFormtpl.organId}</td>							
				<td>
					<fmt:formatDate value="${sysInitFormtpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="init:sysInitFormtpl:edit"><td>
    				<a href="${ctx}/init/sysInitFormtpl/relate?id=${sysInitFormtpl.id}">初始化关联模板</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>