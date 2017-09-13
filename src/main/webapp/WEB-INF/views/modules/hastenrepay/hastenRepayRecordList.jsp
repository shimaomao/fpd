<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>催收记录管理</title>
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
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hastenrepay/hastenRepayRecord/">催收记录列表</a></li>
		<shiro:hasPermission name="hastenrepay:hastenRepayRecord:edit"><li><a href="${ctx}/hastenrepay/hastenRepayRecord/form">催收记录添加</a></li></shiro:hasPermission>
	</ul> 
	<form:form id="searchForm" modelAttribute="hastenRepayRecord" action="${ctx}/hastenrepay/hastenRepayRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>借款人：</label>
				<form:input path="borrower" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>合同id：</label>
				<form:input path="contractId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>--%>
	<br/>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>借款人</th>
				<th>联系方式</th>
				<th>催收时间</th>
				<th>催收说明</th>
				<%-- <shiro:hasPermission name="hastenrepay:hastenRepayRecord:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hastenRepayRecord">
			<tr>
				<td><a href="${ctx}/hastenrepay/hastenRepayRecord/form?id=${hastenRepayRecord.id}">
					${hastenRepayRecord.borrower}
				</a></td>
				<td>
					${hastenRepayRecord.contact}
				</td>
				<td>
					<fmt:formatDate value="${hastenRepayRecord.hastenDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hastenRepayRecord.description}
				</td>
				<%-- <shiro:hasPermission name="hastenrepay:hastenRepayRecord:edit"><td>
    				<a href="${ctx}/hastenrepay/hastenRepayRecord/form?id=${hastenRepayRecord.id}">修改</a>
					<a href="${ctx}/hastenrepay/hastenRepayRecord/delete?id=${hastenRepayRecord.id}" onclick="return confirmx('确认要删除该催收记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>