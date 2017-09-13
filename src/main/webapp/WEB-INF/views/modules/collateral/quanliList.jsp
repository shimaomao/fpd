<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押无形权力管理</title>
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
		<li class="active"><a href="${ctx}/collateral/quanli/">质押无形权力列表</a></li>
		<shiro:hasPermission name="collateral:quanli:edit"><li><a href="${ctx}/collateral/quanli/form">质押无形权力添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="quanli" action="${ctx}/collateral/quanli/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模型估价(元)：</label>
				<form:input path="moMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>权利名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>享有权利剩余期限：</label>
				<form:input path="shengDate" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>已经经营权利时间</th>
				<th>享有权重比例</th>
				<th>模型估价(元)</th>
				<th>权利名称</th>
				<th>权利质量</th>
				<th>经营状况</th>
				<th>享有权利剩余期限</th>
				<th>收益</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:quanli:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="quanli">
			<tr>
				<td><a href="${ctx}/collateral/quanli/form?id=${quanli.id}">
					${quanli.already}
				</a></td>
				<td>
					${quanli.biRate}
				</td>
				<td>
					${quanli.moMoney}
				</td>
				<td>
					${quanli.name}
				</td>
				<td>
					${quanli.quality}
				</td>
				<td>
					${quanli.runStatus}
				</td>
				<td>
					${quanli.shengDate}
				</td>
				<td>
					${quanli.shouYi}
				</td>
				<td>
					<fmt:formatDate value="${quanli.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${quanli.remarks}
				</td>
				<shiro:hasPermission name="collateral:quanli:edit"><td>
    				<a href="${ctx}/collateral/quanli/form?id=${quanli.id}">修改</a>
					<a href="${ctx}/collateral/quanli/delete?id=${quanli.id}" onclick="return confirmx('确认要删除该质押无形权力吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>