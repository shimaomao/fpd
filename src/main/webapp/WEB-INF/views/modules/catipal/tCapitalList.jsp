<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资本信息管理</title>
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
		<li class="active"><a href="${ctx}/catipal/tCapital/">资本信息列表</a></li>
		<c:if test="${count==0 }"><shiro:hasPermission name="catipal:tCapital:edit"><li><a href="${ctx}/catipal/tCapital/form">资本信息添加</a></li></shiro:hasPermission></c:if>
	</ul>
	   <br>
   <br>
   <br>
   <br>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    
				<th>贷款上限金额</th>
				<th>放贷注入资本</th>
				<th>未分配利润</th>
				<th>融资金额</th>
				<th>注册资本</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="catipal:tCapital:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCapital">
			<tr>			  
				<td>
				<a href="${ctx}/catipal/tCapital/form?id=${tCapital.id}">
					${tCapital.limitamount}
				</a>
				</td>
				<td>
					${tCapital.loanamount}
				</td>
				<td>
					${tCapital.profitamount}
				</td>
				<td>
					${tCapital.rongziamount}
				</td>
				<td>
					${tCapital.zhuceamount}
				</td>
				<td>
					<fmt:formatDate value="${tCapital.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${tCapital.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="catipal:tCapital:edit"><td>
    				<a href="${ctx}/catipal/tCapital/form?id=${tCapital.id}">修改</a>
					<a href="${ctx}/catipal/tCapital/delete?id=${tCapital.id}" onclick="return confirmx('确认要删除该资本信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>