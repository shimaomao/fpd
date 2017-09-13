<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产负债管理</title>
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
		<li class="active"><a href="${ctx}/balancesheep/tBalanceSheep/">资产负债列表</a></li>
		<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><li><a href="${ctx}/balancesheep/tBalanceSheep/form">资产负债添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tBalanceSheep" action="${ctx}/balancesheep/tBalanceSheep/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>报表名称：</label>
				<form:input path="reportName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			    <li><label>时间(年月)：</label>
				<input name="createTime" id="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tBalanceSheep.createTime}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>报表名称</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tBalanceSheep">
			<tr>
				<td width="20%"><a href="${ctx}/balancesheep/tBalanceSheep/form?id=${tBalanceSheep.id}">
					${tBalanceSheep.id}
				</a></td>
				<td>
					${tBalanceSheep.reportName}
				</td>
				<td>
					<fmt:formatDate value="${tBalanceSheep.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tBalanceSheep.remarks}
				</td>
				<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><td>
    				<a href="${ctx}/balancesheep/tBalanceSheep/form?id=${tBalanceSheep.id}">修改</a>
					<a href="${ctx}/balancesheep/tBalanceSheep/delete?id=${tBalanceSheep.id}" onclick="return confirmx('确认要删除该资产负债吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>