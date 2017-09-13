<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>质押机器设备管理</title>
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
		<li class="active"><a href="${ctx}/collateral/machine/">质押机器设备列表</a></li>
		<shiro:hasPermission name="collateral:machine:edit"><li><a href="${ctx}/collateral/machine/form">质押机器设备添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="machine" action="${ctx}/collateral/machine/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>控制劝人：</label>
				<form:input path="control" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>控制权方式：</label>
				<form:input path="method" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>设备名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>使用年限：</label>
				<form:input path="useDate" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>变现能力</th>
				<th>控制权人</th>
				<th>登记情况</th>
				<th>控制权方式</th>
				<th>设备名称</th>
				<th>新货价格</th>
				<th>二手价值1(元)</th>
				<th>二手价值2(元)</th>
				<th>使用年限</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:machine:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="machine">
			<tr>
				<td><a href="${ctx}/collateral/machine/form?id=${machine.id}">
					${machine.bianXian}
				</a></td>
				<td>
					${machine.control}
				</td>
				<td>
					${machine.dengji}
				</td>
				<td>
					${machine.method}
				</td>
				<td>
					${machine.name}
				</td>
				<td>
					${machine.newMoney}
				</td>
				<td>
					${machine.second1}
				</td>
				<td>
					${machine.second2}
				</td>
				<td>
					${machine.useDate}
				</td>
				<td>
					<fmt:formatDate value="${machine.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${machine.remarks}
				</td>
				<shiro:hasPermission name="collateral:machine:edit"><td>
    				<a href="${ctx}/collateral/machine/form?id=${machine.id}">修改</a>
					<a href="${ctx}/collateral/machine/delete?id=${machine.id}" onclick="return confirmx('确认要删除该质押机器设备吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>