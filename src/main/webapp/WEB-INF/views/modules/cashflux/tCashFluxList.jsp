<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金流量分析管理</title>
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
		<li class="active"><a href="${ctx}/cashflux/tCashFlux/">现金流量分析列表</a></li>
		<shiro:hasPermission name="cashflux:tCashFlux:edit"><li><a href="${ctx}/cashflux/tCashFlux/form">现金流量分析添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCashFlux" action="${ctx}/cashflux/tCashFlux/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>报表名称：</label>
				<form:input path="reportName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createTime" id="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tCashFlux.createTime}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
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
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cashflux:tCashFlux:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCashFlux">
			<tr>
				<td><a href="${ctx}/cashflux/tCashFlux/form?id=${tCashFlux.id}">
					${tCashFlux.id}
				</a></td>
				<td>
					${tCashFlux.reportName}
				</td>
				<td>
					<fmt:formatDate value="${tCashFlux.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${tCashFlux.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tCashFlux.remarks}
				</td>
				<shiro:hasPermission name="cashflux:tCashFlux:edit"><td>
    				<a href="${ctx}/cashflux/tCashFlux/form?id=${tCashFlux.id}">修改</a>
					<a href="${ctx}/cashflux/tCashFlux/delete?id=${tCashFlux.id}" onclick="return confirmx('确认要删除该现金流量分析吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>