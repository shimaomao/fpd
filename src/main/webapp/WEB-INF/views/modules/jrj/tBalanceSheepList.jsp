<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产负债管理</title>
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
		<li class="active"><a href="${ctx}/balancesheep/jrjBalanceSheep/">资产负债列表</a></li>
		<li><a href="${ctx}/balancesheep/jrjBalanceSheep/form">资产负债添加</a></li>
	</ul>
	
	
	<button id="import"></button>
	<form:form id="searchForm" modelAttribute="tBalanceSheep" action="${ctx}/balancesheep/jrjBalanceSheep/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">			
			<li><label>时间(年月)：</label>
				<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tBalanceSheep.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报表名</th>
				<th>报出时间</th>
				<th>填表时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tBalanceSheep">
			<tr>
				<td width="20%"><a href="${ctx}/balancesheep/jrjBalanceSheep/form?id=${tBalanceSheep.id}">
					${tBalanceSheep.reportName}
				</a></td>
				<td>
					${tBalanceSheep.submitDate}
				</td>
				<td>
					<fmt:formatDate value="${tBalanceSheep.createDate}" pattern="yyyy-MM-dd"/>
				</td>		
				<td>		
    				<a href="${ctx}/balancesheep/jrjBalanceSheep/form?id=${tBalanceSheep.id}">修改</a>
					<a href="${ctx}/balancesheep/jrjBalanceSheep/delete?id=${tBalanceSheep.id}" onclick="return confirmx('确认要删除该资产负债吗？', this.href)">删除</a>
			    </td>		
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>