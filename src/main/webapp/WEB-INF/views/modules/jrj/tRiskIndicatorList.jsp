<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风险指标管理</title>
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
		<li class="active"><a href="${ctx}/risk/jrjRiskIndicator/">风险指标列表</a></li>
		<li><a href="${ctx}/risk/jrjRiskIndicator/form">风险指标添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="jrjRiskIndicator" action="${ctx}/risk/jrjRiskIndicator/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">			
			 <li><label>时间(年月)：</label>
				<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjRiskIndicator.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
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
		<c:forEach items="${page.list}" var="indicator">
			<tr>					
				<td>
				<a href="${ctx}/risk/jrjRiskIndicator/form?id=${indicator.id}">
				    ${indicator.reportName}
				</a>    
				</td>
				<td>
				    ${indicator.submitDate}
				</td>			
				<td>
					<fmt:formatDate value="${indicator.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				
				<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><td>
	   				<a href="${ctx}/risk/jrjRiskIndicator/form?id=${indicator.id}">修改</a>
					<a href="${ctx}/risk/jrjRiskIndicator/delete?id=${indicator.id}" onclick="return confirmx('确认要删除该风险指标情况吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>