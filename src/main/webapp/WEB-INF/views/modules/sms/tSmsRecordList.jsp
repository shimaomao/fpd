<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>sms管理</title>
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
		<li class="active"><a href="${ctx}/sms/tSmsRecord/">sms列表</a></li>
<%-- 		<shiro:hasPermission name="sms:tSmsRecord:edit"><li><a href="${ctx}/sms/tSmsRecord/form">sms添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tSmsRecord" action="${ctx}/sms/tSmsRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送号码：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>短信类别：</label>
			   <form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SmsType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>短信内容</th>
				<th>发送时间</th>
				<th>发送号码</th>
				<th>短信分类</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tSmsRecord">
			<tr>
				<td>
					${tSmsRecord.id}
				</td>
				<td>
					${tSmsRecord.content}
				</td>
				<td>
					<fmt:formatDate value="${tSmsRecord.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tSmsRecord.mobile}
				</td>
				<td>
					${fns:getDictLabels(tSmsRecord.type, 'SmsType', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>