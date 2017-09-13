<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授信记录</title>
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
		<li><a href="${ctx}/employee/tEmployee/form?id=${customerCredit.customerId}">客户基本信息</a></li>
		<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?employeeId=${customerCredit.customerId}">客户关联</a></li>
		<li><a href="${ctx}/customerintent/tCustomerIntent/list?employeeId=${customerCredit.customerId}">意向调查</a></li>
		<li><a href="${ctx}/files/tContractFiles/list?taskId=${customerCredit.customerId}&type=customer_archives">档案资料</a></li>
		<li  class="active"><a href="${ctx}/credit/customerCredit/list?customerId=${customerCredit.customerId}&type=1">授信记录</a></li>
		<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${customerCredit.customerId}&type=1">业务记录</a></li>
		<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${customerCredit.customerId}&type=2&status=9">不良记录</a></li>
	</ul>
	
	
	<form:form id="searchForm" modelAttribute="customerCredit" action="${ctx}/credit/customerCredit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="1"/>
		<form:input path="customerId" htmlEscape="false" maxlength="64" type="hidden" class="input-medium"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>授信额度</th>
				<th>剩余可用额度 </th>
				<th>评分</th>
				<th>授信方式</th>
				<th>授信时间</th>
				<th>授信失效时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerCredit">
			<tr>
			    <td>
					${customerCredit.credit}
				</td>
				<td>
					${customerCredit.balance}
				</td>
				<td>
					${customerCredit.score}
				</td>
				<td>
					${customerCredit.creditWay}
				</td>
				<td>
					<fmt:formatDate value="${customerCredit.creditDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${customerCredit.overDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>