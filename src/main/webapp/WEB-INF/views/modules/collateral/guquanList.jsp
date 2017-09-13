<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押股权管理</title>
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
		<li class="active"><a href="${ctx}/collateral/guquan/">质押股权列表</a></li>
		<shiro:hasPermission name="collateral:guquan:edit"><li><a href="${ctx}/collateral/guquan/form">质押股权添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="guquan" action="${ctx}/collateral/guquan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>注册资本(元)：</label>
				<form:input path="capital" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>控制权人：</label>
				<form:input path="control" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>行业类别：</label>
				<form:input path="hangType" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>营业执照号：</label>
				<form:input path="license" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>目标公司名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>注册资本(元)</th>
				<th>控制权人</th>
				<th>行业类别</th>
				<th>营业执照号</th>
				<th>目标公司名称</th>
				<th>净资产</th>
				<th>经营期限</th>
				<th>经营状况</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:guquan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="guquan">
			<tr>
				<td><a href="${ctx}/collateral/guquan/form?id=${guquan.id}">
					${guquan.capital}
				</a></td>
				<td>
					${guquan.control}
				</td>
				<td>
					${guquan.hangType}
				</td>
				<td>
					${guquan.license}
				</td>
				<td>
					${guquan.name}
				</td>
				<td>
					${guquan.netAssets}
				</td>
				<td>
					${guquan.runDate}
				</td>
				<td>
					${guquan.runStatus}
				</td>
				<td>
					<fmt:formatDate value="${guquan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${guquan.remarks}
				</td>
				<shiro:hasPermission name="collateral:guquan:edit"><td>
    				<a href="${ctx}/collateral/guquan/form?id=${guquan.id}">修改</a>
					<a href="${ctx}/collateral/guquan/delete?id=${guquan.id}" onclick="return confirmx('确认要删除该质押股权吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>