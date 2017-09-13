<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>函件模板管理</title>
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
		<li class="active"><a href="${ctx}/lettertpl/letterTpl/">函件模板列表</a></li>
		<shiro:hasPermission name="lettertpl:letterTpl:edit"><li><a href="${ctx}/lettertpl/letterTpl/form">函件模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="letterTpl" action="${ctx}/lettertpl/letterTpl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>函件类型：</label>
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('letter_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('contract_ftlStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板名称</th>
				<th>函件类型</th>
				<th>状态</th>
				<th>版本</th>
				<th>上传word布局模板的URL</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="lettertpl:letterTpl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="letterTpl">
			<tr>
				<td><a href="${ctx}/lettertpl/letterTpl/form?id=${letterTpl.id}">
					${letterTpl.name}
				</a></td>
				<td>
					${letterTpl.type}
				</td>
				<td>
					${letterTpl.status}
				</td>
				<td>
					${letterTpl.version}
				</td>
				<td>
					${letterTpl.wordUrl}
				</td>
				<td>
					<fmt:formatDate value="${letterTpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${letterTpl.remarks}
				</td>
				<shiro:hasPermission name="lettertpl:letterTpl:edit"><td>
    				<a href="${ctx}/lettertpl/letterTpl/form?id=${letterTpl.id}">修改</a>
					<a href="${ctx}/lettertpl/letterTpl/delete?id=${letterTpl.id}" onclick="return confirmx('确认要删除该函件模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>