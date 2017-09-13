<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保现金流量管理</title>
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
		<li class="active"><a href="${ctx}/cash/jrjCashFlow/">现金流量列表</a></li>
		<li><a href="${ctx}/cash/jrjCashFlow/form">现金流量添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="jrjCashFlow" action="${ctx}/cash/jrjCashFlow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">			
			<li><label>时间(年月)：</label>
				<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjCashFlow.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报表名</th>
				<th>报出时间</th>
				<th>填表时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cash">
			<tr>
				<td width="20%"><a href="${ctx}/cash/jrjCashFlow/form?id=${cash.id}">
					${cash.reportName}
				</a></td>
				<td>
					${cash.submitDate}
				</td>
				<td>
					<fmt:formatDate value="${cash.createDate}" pattern="yyyy-MM-dd"/>
				</td>				
				<td>
    				<a href="${ctx}/cash/jrjCashFlow/form?id=${cash.id}">修改</a>
					<a href="${ctx}/cash/jrjCashFlow/delete?id=${cash.id}" onclick="return confirmx('确认要删除该现金流量吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>