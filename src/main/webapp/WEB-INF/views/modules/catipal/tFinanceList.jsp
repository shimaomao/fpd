<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>融资列表管理</title>
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
		<li class="active"><a href="${ctx}/catipal/tFinance/">融资集合列表</a></li>
		<shiro:hasPermission name="catipal:tFinance:edit"><li><a href="${ctx}/catipal/tFinance/form">融资添加</a></li></shiro:hasPermission>
	</ul>
	<br>
   <br>
   <br>
   <br>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>融资金额</th>
				<th>融资时间</th>
				<th>状态</th>
				<th>融资途径</th>
				<th>创建时间</th>
				<shiro:hasPermission name="catipal:tFinance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tFinance">
			<tr>
				<td>
					${tFinance.rongzimoney}
				</td>
				<td>
					<fmt:formatDate value="${tFinance.rongzitime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(tFinance.rongzitype, 'zibe_type', '')}
				</td>
				<td>
					${tFinance.rongziway}
				</td>
				<td>
					<fmt:formatDate value="${tFinance.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="catipal:tFinance:edit"><td>
					<a href="${ctx}/catipal/tFinance/delete?id=${tFinance.id}" onclick="return confirmx('确认要删除该融资列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>