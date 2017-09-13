<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理管理</title>
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
		<li class="active"><a href="${ctx}/sms/tMsgSwitch/">消息管理列表</a></li>
		<shiro:hasPermission name="sms:tMsgSwitch:edit"><li><a href="${ctx}/sms/tMsgSwitch/form">消息管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tMsgSwitch" action="${ctx}/sms/tMsgSwitch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<!-- <ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th>业务名称</th>
				<th>站内信开关</th>
				<th>营销开关</th>
				<th>邮件开关</th>
				<shiro:hasPermission name="sms:tMsgSwitch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tMsgSwitch">
			<tr>
			<td><a href="${ctx}/sms/tMsgSwitch/form?id=${tMsgSwitch.id}">${tMsgSwitch.businessName}</a></td>
			<td>${tMsgSwitch.letterStatus eq '0' ? '关':'开'}</td>
			<td>${tMsgSwitch.marketStatus eq '0' ? '关':'开'}</td>
			<td>${tMsgSwitch.mailStatus eq '0' ? '关':'开'}</td>
				<shiro:hasPermission name="sms:tMsgSwitch:edit"><td>
    				<a href="${ctx}/sms/tMsgSwitch/form?id=${tMsgSwitch.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
<%-- <a href="${ctx}/sms/tMsgSwitch/delete?id=${tMsgSwitch.id}" onclick="return confirmx('确认要删除该消息管理吗？', this.href)">删除</a> --%>
</html>