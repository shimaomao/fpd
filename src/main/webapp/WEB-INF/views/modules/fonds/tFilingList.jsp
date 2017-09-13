<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料归档管理</title>
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
		<li class="active"><a href="${ctx}/fonds/tFiling/?loancontractId=${loancontractId}">资料归档列表</a></li>
		<shiro:hasPermission name="fonds:tFiling:edit"><li><a href="${ctx}/fonds/tFiling/form?loancontractId=${loancontractId}">资料归档添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tFiling" action="${ctx}/fonds/tFiling/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>卷宗名称：</label>
				<form:input path="fondsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>案卷目录号：</label>
				<form:input path="fileNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>案卷目录名称：</label>
				<form:input path="fileName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>卷宗名称</th>
				<th>卷宗编号</th>
				<th>案卷目录号</th>
				<th>案卷目录名称</th>
				<th>案卷张数</th>
				<th>存放位置</th>
				<th>归档时间</th>
				<shiro:hasPermission name="fonds:tFiling:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tFiling">
			<tr>
				<td><a href="${ctx}/fonds/tFiling/form?id=${tFiling.id}">
					${tFiling.fondsName}
				</a></td>
				<td>
					${tFiling.fondsNumber}
				</td>
				<td>
					${tFiling.fileNumber}
				</td>
				<td>
					${tFiling.fileName}
				</td>
				<td>
					${tFiling.fileSheets}
				</td>
				<td>
					${tFiling.position}
				</td>
				<td>
					<fmt:formatDate value="${tFiling.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fonds:tFiling:edit"><td>
    				<a href="${ctx}/fonds/tFiling/form?loancontractId=${loancontractId}&id=${tFiling.id}">修改</a>
					<a href="${ctx}/fonds/tFiling/delete?id=${tFiling.id}&loancontractId=${loancontractId}" onclick="return confirmx('确认要删除该资料归档吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>