<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目余额管理</title>
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
		<li class="active"><a href="${ctx}/accountbalance/tAccountBalance/">科目余额列表</a></li>
		<shiro:hasPermission name="accountbalance:tAccountBalance:edit"><li><a href="${ctx}/accountbalance/tAccountBalance/form">科目余额添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tAccountBalance" action="${ctx}/accountbalance/tAccountBalance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户编号：</label>
				<form:input path="subjectNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>科目名称：</label>
				<form:input path="subjectName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
		    <li><label>时间(年月)：</label>
				<input name="createTime" id="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tAccountBalance.createTime}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr align="center">
				<th rowspan="2">科目编号</th>
				<th rowspan="2">科目名称</th>
				<th rowspan="2">级数</th>
				<th rowspan="2">明细</th>
				<th colspan="2">期初金额</th>
				<th  colspan="2">本期发生</th>
				<th  colspan="2">当前金额</th>
				<shiro:hasPermission name="accountbalance:tAccountBalance:edit"><th rowspan="2">操作</th></shiro:hasPermission>
			</tr>
			 <tr>
				<th>方向</th>
				<th>金额</th>
				<th>借方</th>
				<th>贷方</th>
				<th>方向</th>
				<th>金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tAccountBalance">
			<tr>
				<td> <a href="${ctx}/accountbalance/tAccountBalance/form?id=${tAccountBalance.id}">
					${tAccountBalance.subjectNumber}
					</a>
				</td>
				<td>
					${tAccountBalance.subjectName}
				</td>
				<td>
					${tAccountBalance.level}
				</td>
				<td>
					${tAccountBalance.detail}
				</td>
				<td>
					${tAccountBalance.beginBalance}
				</td>
				<td>
					${tAccountBalance.beginDirec}
				</td>
				<td>
					${tAccountBalance.currentDebit}
				</td>
				<td>
					${tAccountBalance.currentLender}
				</td>
				<td>
					${tAccountBalance.currentDirec}
				</td>
				<td>
					${tAccountBalance.currentBalance}
				</td>
				<shiro:hasPermission name="accountbalance:tAccountBalance:edit"><td>
    				<a href="${ctx}/accountbalance/tAccountBalance/form?id=${tAccountBalance.id}">修改</a>
					<a href="${ctx}/accountbalance/tAccountBalance/delete?id=${tAccountBalance.id}" onclick="return confirmx('确认要删除该科目余额吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>