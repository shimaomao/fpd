<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押物-商铺写字楼管理</title>
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
		<li class="active"><a href="${ctx}/collateral/building/">抵押物-商铺写字楼列表</a></li>
		<shiro:hasPermission name="collateral:building:edit"><li><a href="${ctx}/collateral/building/form">抵押物-商铺写字楼添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="building" action="${ctx}/collateral/building/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>房地产地点：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>按揭余额(元) ：</label>
				<form:input path="balance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>产权人：</label>
				<form:input path="chanMan" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>产权性质：</label>
				<form:input path="chanQuality" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>建成年份：</label>
				<input name="jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${building.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>剩余年限：</label>
				<form:input path="yearLimit" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>房地产地点</th>
				<th>按揭余额(元) </th>
				<th>产权人</th>
				<th>建筑结构</th>
				<th>权证编号</th>
				<th>建筑样式</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:building:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="building">
			<tr>
				<td><a href="${ctx}/collateral/building/form?id=${building.id}">
					${building.address}
				</a></td>
				<td>
					${building.balance}
				</td>
				<td>
					${building.chanMan}
				</td>
				<td>
					${building.framework}
				</td>
				<td>
					${building.quanNum}
				</td>
				<td>
					${building.yangshi}
				</td>
				<td>
					<fmt:formatDate value="${building.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${building.remarks}
				</td>
				<shiro:hasPermission name="collateral:building:edit"><td>
    				<a href="${ctx}/collateral/building/form?id=${building.id}">修改</a>
					<a href="${ctx}/collateral/building/delete?id=${building.id}" onclick="return confirmx('确认要删除该抵押物-商铺写字楼吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>