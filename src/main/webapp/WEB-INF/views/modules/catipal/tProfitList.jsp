<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润列表管理</title>
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
		<li class="active"><a href="${ctx}/catipal/tProfit/">利润集合列表</a></li>
		<shiro:hasPermission name="catipal:tProfit:edit"><li><a href="${ctx}/catipal/tProfit/form">利润集合添加</a></li></shiro:hasPermission>
	</ul>
   <br>
   <br>
   <br>
   <br>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>利润金额</th>
				<th>利润时间</th>
				<th>状态</th>
				<th>利润途径</th>
				<th>创建时间</th>
				<shiro:hasPermission name="catipal:tProfit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tProfit">
			<tr>
				<td>
					${tProfit.profitmoney}
				</td>
				<td>
					<fmt:formatDate value="${tProfit.profittime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(tProfit.profittype, 'profit_type', '')}
				</td>
				<td>
					${tProfit.profitway}
				</td>
				<td>
					<fmt:formatDate value="${tProfit.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="catipal:tProfit:edit"><td>
					<a href="${ctx}/catipal/tProfit/delete?id=${tProfit.id}" onclick="return confirmx('确认要删除该利润列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>