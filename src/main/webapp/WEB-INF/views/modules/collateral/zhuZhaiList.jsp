<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押住宅信息管理</title>
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
		<li class="active"><a href="${ctx}/collateral/zhuZhai/">抵押住宅信息列表</a></li>
		<shiro:hasPermission name="collateral:zhuZhai:edit"><li><a href="${ctx}/collateral/zhuZhai/form">抵押住宅信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="zhuZhai" action="${ctx}/collateral/zhuZhai/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>面积(㎡)：</label>
				<form:input path="area" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>土地抵押贷款余额：</label>
				<form:input path="daiBalance" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>产权人：</label>
				<form:input path="man" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>权证编号：</label>
				<form:input path="quanNum" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>土地抵押贷款余额</th>
				<th>产权人</th>
				<th>模型估价</th>
				<th>模型估价2</th>
				<th>权证编号</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="collateral:zhuZhai:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zhuZhai">
			<tr>
				<td><a href="${ctx}/collateral/zhuZhai/form?id=${zhuZhai.id}">
					${zhuZhai.address}
				</a></td>
				<td>
					${zhuZhai.area}
				</td>
				<td>
					${zhuZhai.daiBalance}
				</td>
				<td>
					${zhuZhai.man}
				</td>
				<td>
					${zhuZhai.moMoney1}
				</td>
				<td>
					${zhuZhai.moMoney2}
				</td>
				<td>
					${zhuZhai.quanNum}
				</td>
				<td>
					<fmt:formatDate value="${zhuZhai.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${zhuZhai.remarks}
				</td>
				<shiro:hasPermission name="collateral:zhuZhai:edit"><td>
    				<a href="${ctx}/collateral/zhuZhai/form?id=${zhuZhai.id}">修改</a>
					<a href="${ctx}/collateral/zhuZhai/delete?id=${zhuZhai.id}" onclick="return confirmx('确认要删除该抵押住宅信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>