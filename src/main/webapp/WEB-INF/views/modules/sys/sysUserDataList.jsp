<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>初始化数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysUserData/">初始化数据列表</a></li>
		<shiro:hasPermission name="sys:sysUserData:edit"><li><a href="${ctx}/sys/sysUserData/form">初始化数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysUserData" action="${ctx}/sys/sysUserData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>数据项：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${sysUserData.user.id}" labelName="user.name" labelValue="${sysUserData.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>数据项</th>
				<th>用户</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sys:sysUserData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUserData">
			<tr>
				<td><a href="${ctx}/sys/sysUserData/form?id=${sysUserData.id}">
					${sysUserData.name}
				</a></td>
				<td>
					${sysUserData.user.name}
				</td>
				<td>
					<fmt:formatDate value="${sysUserData.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:sysUserData:edit"><td>
    				<a href="${ctx}/sys/sysUserData/form?id=${sysUserData.id}">修改</a>
					<a href="${ctx}/sys/sysUserData/delete?id=${sysUserData.id}" onclick="return confirmx('确认要删除该初始化数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>