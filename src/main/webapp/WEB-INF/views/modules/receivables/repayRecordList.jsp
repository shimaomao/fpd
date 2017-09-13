<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>真实还款记录管理</title>
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
		<li class="active"><a href="${ctx}/receivables/repayRecord/">真实还款记录列表</a></li>
		<shiro:hasPermission name="receivables:repayRecord:edit"><li><a href="${ctx}/receivables/repayRecord/form">真实还款记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="repayRecord" action="${ctx}/receivables/repayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>还款金额：</label>
				<form:input path="money" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>还款日期：</label>
				<form:input path="repayDate" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>是否逾期</th>
				<th>还款金额</th>
				<th>还款日期</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="receivables:repayRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="repayRecord">
			<tr>
				<td><a href="${ctx}/receivables/repayRecord/form?id=${repayRecord.id}">
					${repayRecord.isYuQi}
				</a></td>
				<td>
					${repayRecord.money}
				</td>
				<td>
					<fmt:formatDate value="${repayRecord.repayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${repayRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${repayRecord.remarks}
				</td>
				<shiro:hasPermission name="receivables:repayRecord:edit"><td>
    				<a href="${ctx}/receivables/repayRecord/form?id=${repayRecord.id}">修改</a>
					<a href="${ctx}/receivables/repayRecord/delete?id=${repayRecord.id}" onclick="return confirmx('确认要删除该真实还款记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>