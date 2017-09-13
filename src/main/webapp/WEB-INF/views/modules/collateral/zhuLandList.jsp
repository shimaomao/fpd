<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押商宅用地管理</title>
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
		<li class="active"><a href="${ctx}/collateral/zhuLand/">抵押商宅用地列表</a></li>
		<shiro:hasPermission name="collateral:zhuLand:edit"><li><a href="${ctx}/collateral/zhuLand/form">抵押商宅用地添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="zhuLand" action="${ctx}/collateral/zhuLand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>面积(㎡)：</label>
				<form:input path="area" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>土地抵质押贷款余额(元)：</label>
				<form:input path="daiBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>产权人：</label>
				<form:input path="man" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>模型估值(元)：</label>
				<form:input path="modelValuation" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>地址</th>
				<th>面积(㎡)</th>
				<th>土地抵质押贷款余额(元)</th>
				<th>登记情况</th>
				<th>产权人</th>
				<th>权证编号</th>
				<th>模型估值(元)</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:zhuLand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zhuLand">
			<tr>
				<td><a href="${ctx}/collateral/zhuLand/form?id=${zhuLand.id}">
					${zhuLand.address}
				</a></td>
				<td>
					${zhuLand.area}
				</td>
				<td>
					${zhuLand.daiBalance}
				</td>
				<td>
					${zhuLand.dengji}
				</td>
				<td>
					${zhuLand.man}
				</td>
				<td>
					${zhuLand.quanNum}
				</td>
				<td>
					${zhuLand.modelValuation}
				</td>
				<td>
					<fmt:formatDate value="${zhuLand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${zhuLand.remarks}
				</td>
				<shiro:hasPermission name="collateral:zhuLand:edit"><td>
    				<a href="${ctx}/collateral/zhuLand/form?id=${zhuLand.id}">修改</a>
					<a href="${ctx}/collateral/zhuLand/delete?id=${zhuLand.id}" onclick="return confirmx('确认要删除该抵押商宅用地吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>