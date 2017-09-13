<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>展期申请管理</title>
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
		<li class="active"><a href="${ctx}/contract/extendContract/">展期申请列表</a></li>
		<shiro:hasPermission name="contract:extendContract:edit"><li><a href="${ctx}/contract/extendContract/form">展期申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="extendContract" action="${ctx}/contract/extendContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>展期编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>关联合同主键id：</label>
				<form:input path="loanContract.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>申请日期</th>
				<th>展期编号</th>
				<th>展期金额</th>
				<th>通过日期</th>
				<th>还款方式</th>
				<th>合同状态</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="contract:extendContract:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="extendContract">
			<tr>
				<td><a href="${ctx}/contract/extendContract/form?id=${extendContract.id}">
					<fmt:formatDate value="${extendContract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${extendContract.contractNumber}
				</td>
				<td>
					${extendContract.amount}
				</td>
				<td>
					<fmt:formatDate value="${extendContract.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${extendContract.payType}
				</td>
				<td>
					${extendContract.status}
				</td>
				<td>
					<fmt:formatDate value="${extendContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${extendContract.remarks}
				</td>
				<shiro:hasPermission name="contract:extendContract:edit"><td>
    				<a href="${ctx}/contract/extendContract/form?id=${extendContract.id}">修改</a>
					<a href="${ctx}/contract/extendContract/delete?id=${extendContract.id}" onclick="return confirmx('确认要删除该展期申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>