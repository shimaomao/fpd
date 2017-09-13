<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>放款记录管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
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
		<li class="active"><a href="${ctx}/lending/tLending/listIng">放款待审列表1</a></li>
		<li><a href="${ctx}/lending/tLending/listEd">已放款列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tLending" action="${ctx}/lending/tLending/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="contract.applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLending.contract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>合同编号：</label>
				<form:input path="contract.contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>贷款金额：</label>
				<form:input path="contract.loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>贷款期限：</label>
				<form:input path="contract.loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>贷款方式：</label>
				<form:radiobuttons path="contract.loanType" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table class="table table-bordered">
		<tr><td>
			<input class="btn btn-primary" type="button" onclick="loan();" value="放款"/>
		</td></tr>
    </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>贷款金额</th>
				<th>贷款期限</th>
				<th>贷款利率(%)</th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<th>状态</th>
				<shiro:hasPermission name="contract:tLoanContract:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contract">
			<tr>
				<td>
					${contract.contractNumber}
				</td>
				<td>
					${contract.customerName}
				</td>
				<td>
					${contract.loanAmount}
				</td>
				<td>
					${contract.loanPeriod}
				</td>
				<td>
					${contract.loanRate}(${contract.loanRateType})
				</td>
				<td>
					<fmt:formatDate value="${contract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(contract.loanType, 'loan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(contract.status, 'product_status', '')}
				</td>
				<shiro:hasPermission name="contract:tLoanContract:edit"><td>
    				<%-- <a href="${ctx}/contract/tLoanContract/form?id=${tLending.contract.id}">修改</a>
					<a href="${ctx}/contract/tLoanContract/delete?id=${tLending.contract.id}" onclick="return confirmx('确认要删除该业务办理吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>