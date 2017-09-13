<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单价管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contentTable").treeTable({expandLevel : 5}).show();
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
		<li class="active"><a href="${ctx}/billing/price/biPrice/">收费单价列表</a></li>
		<shiro:hasPermission name="billing:price:biPrice:edit"><li><a href="${ctx}/billing/price/biPrice/form">收费单价添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>收费项标识</th>
				<th>价格(元)/值-单位</th>
				<th>计价类型</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="billing:price:biPrice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${lists}" var="biPrice">
			<tr id="${biPrice.element.id}" pId="${(biPrice.element.element.id ne '1')?biPrice.element.element.id:'0'}" >
				<td><a href="${ctx}/billing/price/biPrice/form?id=${biPrice.id}">
					${biPrice.element.name}
				</a></td>
				<td>
					${biPrice.price}元/${fns:getDictLabel(biPrice.unit, 'biling_price_unit', '')}
				</td> 
				<td>
					${fns:getDictLabel(biPrice.type, 'biling_price_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${biPrice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${biPrice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${biPrice.remarks}
				</td>
				<shiro:hasPermission name="billing:price:biPrice:edit"><td>
    				<a href="${ctx}/billing/price/biPrice/form?id=${biPrice.id}">修改</a>
					<a href="${ctx}/billing/price/biPrice/delete?id=${biPrice.id}" onclick="return confirmx('确认要删除该收费单价吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot><tr><td colspan="7">共${fn:length(lists)}条记录</td></tr>
		</tfoot>
	</table>
</body>
</html>