<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账目明细管理</title>
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
		<li class="active"><a href="${ctx}/accountdetail/tAccountDetail/">账目明细列表</a></li>
		<shiro:hasPermission name="accountdetail:tAccountDetail:edit"><li><a href="${ctx}/accountdetail/tAccountDetail/form">账目明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tAccountDetail" action="${ctx}/accountdetail/tAccountDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="publishTime" id="publishTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tAccountDetail.publishTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>内容</th>
				<th>收入</th>
				<th>支出</th>
				<th>余额</th>
				<th>时间</th>
				<th>备注</th>
				<shiro:hasPermission name="accountdetail:tAccountDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tAccountDetail">
			<tr>
				<td><a href="${ctx}/accountdetail/tAccountDetail/form?id=${tAccountDetail.id}">
					${tAccountDetail.content}
				</a></td>
				<td>
					${tAccountDetail.income}
				</td>
				<td>
					${tAccountDetail.defray}
				</td>
				<td>
					${tAccountDetail.balance}
				</td>
				<td>
					${tAccountDetail.publishTime}
				</td>
				<td>
					${tAccountDetail.remarks}
				</td>
				<shiro:hasPermission name="accountdetail:tAccountDetail:edit"><td>
    				<a href="${ctx}/accountdetail/tAccountDetail/form?id=${tAccountDetail.id}">修改</a>
					<a href="${ctx}/accountdetail/tAccountDetail/delete?id=${tAccountDetail.id}" onclick="return confirmx('确认要删除该账目明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>