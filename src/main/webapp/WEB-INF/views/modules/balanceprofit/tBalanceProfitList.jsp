<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润分析管理</title>
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
		<li class="active"><a href="${ctx}/balanceprofit/tBalanceProfit/">利润分析列表</a></li>
		<shiro:hasPermission name="balanceprofit:tBalanceProfit:edit"><li><a href="${ctx}/balanceprofit/tBalanceProfit/form">利润分析添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tBalanceProfit" action="${ctx}/balanceprofit/tBalanceProfit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>报表名称：</label>
				<form:input path="reportName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createTime" id="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tBalanceProfit.createTime}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
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
				<shiro:hasPermission name="balanceprofit:tBalanceProfit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tBalanceProfit">
			<tr>
				<td><a href="${ctx}/balanceprofit/tBalanceProfit/form?id=${tBalanceProfit.id}">
					${tBalanceProfit.id}
				</a></td>
				<td>
					${tBalanceProfit.reportName}
				</td>
				<td>
					<fmt:formatDate value="${tBalanceProfit.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tBalanceProfit.remarks}
				</td>
				<shiro:hasPermission name="balanceprofit:tBalanceProfit:edit"><td>
    				<a href="${ctx}/balanceprofit/tBalanceProfit/form?id=${tBalanceProfit.id}">修改</a>
					<a href="${ctx}/balanceprofit/tBalanceProfit/delete?id=${tBalanceProfit.id}" onclick="return confirmx('确认要删除该利润分析吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>