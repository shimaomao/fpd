]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<li class="active"><a href="${ctx}/sys/role/adminlist">Admin角色</a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>归属机构</th><th>角色名称</th><th>英文名称</th><th>数据范围</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${adminList}" var="role">
		<!-- 按照陈浩要求添加 Bug #3124-->
			<tr>
				<td>${role.office.parent.name}</td>
				<td>  
				     <c:choose>
		   				<c:when test="${role.name !='租户管理员'  || fns:getUser().admin}" >
		   					<a href="form?id=${role.id}">${role.name}</a>
		   				</c:when>
		   				<c:otherwise>
		   					${role.name}
		   				</c:otherwise>
					</c:choose>
				</td>
				<td>${role.enname}</td>
				<%-- <td>${role.office.id}</td> --%>
				<td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
				<td>${role.remarks}</td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<%-- 
						Update By chenh 2016-08-24 Start
						Bug #2781
					 --%>
					<c:set var="curUser" value="${fns:getUser()}"></c:set>
					<c:if test="${curUser.admin}">
						<c:if test="${(role.organId eq '0') || (role.id eq 'c2d0a3ed353f4be9ac912761b3b1e252') || (role.id eq 'e16028c047024f6ea62a7afac952b899')}">
							<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
							<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
						</c:if>
						<c:if test="${(role.organId eq '7b506be60942497db0b02b97e70dd21c') && (role.id ne 'c2d0a3ed353f4be9ac912761b3b1e252') && (role.id ne 'e16028c047024f6ea62a7afac952b899')}">
							<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
							<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
							<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
						</c:if>
					</c:if>
					<c:if test="${!curUser.admin}">
						<c:if test="${role.organId eq '0' }">数据异常!租户ID异常(0)</c:if>
						<c:if test="${(role.id eq 'c2d0a3ed353f4be9ac912761b3b1e252') || (role.id eq 'e16028c047024f6ea62a7afac952b899') }">系统角色</c:if>
						<c:if test="${(role.id ne 'c2d0a3ed353f4be9ac912761b3b1e252') && (role.id ne 'e16028c047024f6ea62a7afac952b899') && (role.organId eq '7b506be60942497db0b02b97e70dd21c') }">数据异常!超级管理员禁止给租户添加角色(${role.createBy.name})</c:if>
						<c:if test="${(role.organId ne '0') && (role.organId ne '7b506be60942497db0b02b97e70dd21c') && (role.id ne 'c2d0a3ed353f4be9ac912761b3b1e252') && (role.id ne 'e16028c047024f6ea62a7afac952b899')}">
							<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
							<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
							<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
						</c:if>
					</c:if>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
</body>
</html>