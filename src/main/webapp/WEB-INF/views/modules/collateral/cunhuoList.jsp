<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押存货信息管理</title>
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
		<li class="active"><a href="${ctx}/collateral/cunhuo/">质押存货信息列表</a></li>
		<shiro:hasPermission name="collateral:cunhuo:edit"><li><a href="${ctx}/collateral/cunhuo/form">质押存货信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cunhuo" action="${ctx}/collateral/cunhuo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>品牌：</label>
				<form:input path="brand" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>控制权人：</label>
				<form:input path="control" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>模型估价(元) ：</label>
				<form:input path="moMoney" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>存货名称：</label>
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
				<th>品牌</th>
				<th>控制权人</th>
				<th>控制器方式</th>
				<th>模型估价(元) </th>
				<th>存货名称</th>
				<th>数量</th>
				<th>型号</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:cunhuo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cunhuo">
			<tr>
				<td><a href="${ctx}/collateral/cunhuo/form?id=${cunhuo.id}">
					${cunhuo.brand}
				</a></td>
				<td>
					${cunhuo.control}
				</td>
				<td>
					${cunhuo.method}
				</td>
				<td>
					${cunhuo.moMoney}
				</td>
				<td>
					${cunhuo.name}
				</td>
				<td>
					${cunhuo.num}
				</td>
				<td>
					${cunhuo.type}
				</td>
				<td>
					<fmt:formatDate value="${cunhuo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cunhuo.remarks}
				</td>
				<shiro:hasPermission name="collateral:cunhuo:edit"><td>
    				<a href="${ctx}/collateral/cunhuo/form?id=${cunhuo.id}">修改</a>
					<a href="${ctx}/collateral/cunhuo/delete?id=${cunhuo.id}" onclick="return confirmx('确认要删除该质押存货信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>