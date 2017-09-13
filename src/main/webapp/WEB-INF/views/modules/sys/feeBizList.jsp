<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>付费业务</title>
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
		<li class="active"><a href="${ctx}/sys/openBiz/feeBizList">付费业务列表</a></li>
		<shiro:hasPermission name="sys:feeBiz:edit">
		<li><a href="${ctx}/sys/openBiz/feeBizForm">添加付费业务</a></li>
		</shiro:hasPermission>
	</ul>
	<%-- <form:form id="searchForm" modelAttribute="openBiz" action="${ctx}/sys/openBiz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form> --%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务名称</th>
				<th>业务分类</th>
				<shiro:hasPermission name="sys:openBiz:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="feeBiz">
			<tr>
				<td>
					${fns:getDictLabel(feeBiz.category, 'feebiz_category', '')}
				</td>
				<td>${feeBiz.bizName}</td>
				<td>
					<shiro:hasPermission name="sys:openBiz:edit">
					<a href="${ctx}/sys/openBiz/form?feeBiz.id=${feeBiz.id}" >申请购买</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>