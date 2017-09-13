<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/initData?id=${result.user.id}">初始化数据</a></li>
	</ul>
	<sys:message content="${message}"/>
	<c:if test="${result.istrue}">
		<div>
			${result.user}
		</div>
		<div>
			${result.roles}
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead><tr><th>数据项</th><th>初始化状态</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
			<tbody>
				<tr>
					<td>初始化</td>
					<td>未初始化</td>
					<shiro:hasPermission name="sys:user:edit"><td>
	    				<a href="${ctx}/sys/user/ajaxInit?id=${user.id}">初始化</a>
					</td></shiro:hasPermission>
				</tr>
				<tr>
					<td>流程</td>
					<td>未初始化</td>
					<shiro:hasPermission name="sys:user:edit"><td>
	    				<a href="${ctx}/sys/user/ajaxInit?id=${user.id}">初始化</a>
					</td></shiro:hasPermission>
				</tr>
				<tr>
					<td>表单</td>
					<td>已初始化</td>
					<shiro:hasPermission name="sys:user:edit"><td>
	    				<a href="${ctx}/sys/user/ajaxInit?id=${user.id}">重置</a>
					</td></shiro:hasPermission>
				</tr>
				<tr>
					<td>表单2</td>
					<td>使用中</td>
					<shiro:hasPermission name="sys:user:edit"><td>-</td></shiro:hasPermission>
				</tr>
			</tbody>
		</table>
	</c:if>
</body>
</html>