<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小贷财务报表管理</title>
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
		<li class="active"><a href="${ctx}/catipal/tCaiwu/listXj">小贷现金流量列表</a></li>
		<li><a href="${ctx}/catipal/tCaiwu/form?informFilingType=3">小贷现金流量添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tCaiwu" action="${ctx}/catipal/tCaiwu/listLr" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>报备日期：</label>
				<input name="baoDate" id="baoDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tCaiwu.baoDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报备日期</th>
				<th>创建时间</th>			
				<shiro:hasPermission name="catipal:tCaiwu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCaiwu">
			<tr>
				<td><a href="${ctx}/catipal/tCaiwu/form?id=${tCaiwu.id}">
					${tCaiwu.baoDate}
						</a></td>
				<td>
					<fmt:formatDate value="${tCaiwu.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			
				</td>
				<shiro:hasPermission name="catipal:tCaiwu:edit"></shiro:hasPermission><td>
    				<a href="${ctx}/catipal/tCaiwu/form?informFilingType=3&id=${tCaiwu.id}">修改</a>
					<a href="${ctx}/catipal/tCaiwu/delete?id=${tCaiwu.id}&baoDate=${tCaiwu.baoDate}" onclick="return confirmx('确认要删除该小贷现金流量吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>