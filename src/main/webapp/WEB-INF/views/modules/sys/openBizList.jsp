<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务开通管理</title>
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
		<li class="${openBiz.status == 'new' ? 'active' : '' }"><a href="${ctx}/sys/openBiz/">待办业务开通申请</a></li>
		<li class="${openBiz.status == 'new' ? '' : 'active' }"><a href="${ctx}/sys/openBiz/listF">已办业务开通申请</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="openBiz" action="${ctx}/sys/openBiz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<!-- <ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<c:set var="newStatus" value="<%=Cons.NEW %>"></c:set>
		<c:set var="validStatus" value="<%=Cons.VALID %>"></c:set>
		<c:set var="invalidStatus" value="<%=Cons.INVALID %>"></c:set>
		<thead>
			<tr>
				<th>业务类型</th>
				<th>业务名称</th>
				<th>数量</th>
				<th>金额</th>
				<th>更新时间</th>
				<th>状态</th>
				<shiro:hasPermission name="sys:openBiz:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="openBiz">
			<tr>
				<td><a href="${ctx}/sys/openBiz/form?id=${openBiz.id}">
					${fns:getDictLabel(openBiz.feeBiz.category,'feebiz_category','') }
				</a></td>
				<td>
					${openBiz.feeBiz.bizName}
				</td>
				<td>
					${openBiz.count}
				</td>
				<td>
					<fmt:formatNumber value="${openBiz.amount}" pattern="#,#00.00#"/>
				</td>
				<td>
					<fmt:formatDate value="${openBiz.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${openBiz.status == newStatus ? '待处理' : (openBiz.status == validStatus ? '已激活' : '不激活')}
				</td>
				<td>
				<shiro:hasPermission name="sys:openBiz:audit">
				<c:if test="${openBiz.status == newStatus}">
					<a href="${ctx}/sys/openBiz/activite?id=${openBiz.id}" onclick="return confirmx('确认给予激活？', this.href)">开通</a>
					<a href="${ctx}/sys/openBiz/deactivite?id=${openBiz.id}">不开通</a>
				</c:if>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:openBiz:edit">
				<c:if test="${openBiz.status == newStatus}">
					<a href="${ctx}/sys/openBiz/delete?id=${openBiz.id}" onclick="return confirmx('确认要删除该申请吗？', this.href)">删除</a>
				</c:if>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>