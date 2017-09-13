<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控配置管理</title>
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
		<li class="active"><a href="${ctx}/windcfg/tWindControlCfg/">风控配置列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tWindControlCfg" action="${ctx}/windcfg/tWindControlCfg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="product.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>风控：</label>
				<form:input path="wind.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品</th>
				<th>风控</th>
				<th>更新时间</th>
				<th>备注</th>
				<%-- <shiro:hasPermission name="windcfg:tWindControlCfg:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tWindControlCfg">
			<tr>
				<td>${tWindControlCfg.product.name}</td>
				<td>${tWindControlCfg.wind.name}</td>
				<td><a href="${ctx}/windcfg/tWindControlCfg/form?id=${tWindControlCfg.id}">
					<fmt:formatDate value="${tWindControlCfg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${tWindControlCfg.remarks}
				</td>
				<%-- <shiro:hasPermission name="windcfg:tWindControlCfg:edit"><td>
    				<a href="${ctx}/windcfg/tWindControlCfg/form?id=${tWindControlCfg.id}">修改</a>
					<a href="${ctx}/windcfg/tWindControlCfg/delete?id=${tWindControlCfg.id}" onclick="return confirmx('确认要删除该风控配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>