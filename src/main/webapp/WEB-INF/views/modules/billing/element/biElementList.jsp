<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费项管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").treeTable({expandLevel : 5}).show();
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
		<li class="active"><a href="${ctx}/billing/element/biElement/">收费项列表</a></li>
		<shiro:hasPermission name="billing:element:biElement:edit"><li><a href="${ctx}/billing/element/biElement/form">收费项添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="biElement" action="${ctx}/billing/element/biElement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>收费项：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>收费项类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('biling_element_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed hide">
		<thead>
			<tr>
				<th>收费项</th>
				<th>收费项类型</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="billing:element:biElement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="biElement">
			<tr id="${biElement.id}" pId="${(biElement.element.id ne '1')?biElement.element.id:'0'}" >
				<td><a href="${ctx}/billing/element/biElement/form?id=${biElement.id}">
					${biElement.name}
				</a></td>
				<td>
					${fns:getDictLabel(biElement.type, 'biling_element_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${biElement.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${biElement.remarks}
				</td>
				<shiro:hasPermission name="billing:element:biElement:edit"><td>
    				<a href="${ctx}/billing/element/biElement/form?id=${biElement.id}">修改</a>
					<a href="${ctx}/billing/element/biElement/delete?id=${biElement.id}" onclick="return confirmx('确认要删除该收费项吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>