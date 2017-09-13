<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>农垦授信信息表管理</title>
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
		<li class="active"><a href="${ctx}/credit/nkCredit/">微信白名单信息列表</a></li>
		<shiro:hasPermission name="credit:nkCredit:edit">
			<li><a href="${ctx}/credit/nkCredit/form">微信白名单添加</a></li>
			<li><a href="${ctx}/credit/nkCredit/upload">EXCEL数据导入</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="nkCredit" action="${ctx}/credit/nkCredit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>证件号码：</label>
				<form:input path="identity" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>证件号码</th>
				<th>授信额度</th>
				<th>类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="credit:nkCredit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nkCredit">
			<tr>
				<td>
					${nkCredit.name}
				</td>
				<td>
					${nkCredit.identity}
				</td>
				<td>
					${nkCredit.credit}
				</td>
				<td>
					${fns:getDictLabel(nkCredit.type, 'wx_credit_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${nkCredit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="credit:nkCredit:edit"><td>
    				<a href="${ctx}/credit/nkCredit/form?id=${nkCredit.id}">修改</a>
					<a href="${ctx}/credit/nkCredit/delete?id=${nkCredit.id}" onclick="return confirmx('确认要删除该农垦授信信息表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>