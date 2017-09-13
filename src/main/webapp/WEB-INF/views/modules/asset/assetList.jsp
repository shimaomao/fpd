<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户资产管理</title>
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
		<li class="active"><a href="${ctx}/asset/asset/">账户资产列表</a></li>
		<shiro:hasPermission name="asset:asset:edit"><li><a href="${ctx}/asset/asset/form">账户资产添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="asset" action="${ctx}/asset/asset/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户登录名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户登录名</th>
				<th>总资产</th>
				<th>余额</th>
				<th>可用余额</th>
				<th>冻结资金</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="asset:asset:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="asset">
			<tr>
				<td><a href="${ctx}/asset/asset/form?id=${asset.id}">
					${asset.loginName}
				</a></td>
				<td>
					${asset.totalAsset}
				</td>
				<td>
					${asset.balance}
				</td>
				<td>
					${asset.availableBalance}
				</td>
				<td>
					${asset.freezeAmount}
				</td>
				<td>
					<fmt:formatDate value="${asset.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${asset.remarks}
				</td>
				<shiro:hasPermission name="asset:asset:edit"><td>
    				<a href="${ctx}/asset/asset/form?id=${asset.id}">修改</a>
					<a href="${ctx}/asset/asset/delete?id=${asset.id}" onclick="return confirmx('确认要删除该账户资产吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>