<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<html>
<head>
	<title>业务记录</title>
	<meta name="decorator" content="default"/>
  <script type="text/javascript" src="${ctxStatic}/util.js"></script>
  <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=1"></script>
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
		<li><a href="${ctx}/employee/tEmployee/form?id=${tLoanContract.customerId}">客户基本信息</a></li>
		<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?employeeId=${tLoanContract.customerId}">客户关联</a></li>
		<li><a href="${ctx}/customerintent/tCustomerIntent/list?employeeId=${tLoanContract.customerId}">意向调查</a></li>
		<li><a href="${ctx}/files/tContractFiles/list?taskId=${tLoanContract.customerId}&type=customer_archives">档案资料</a></li>
		<li><a href="${ctx}/credit/customerCredit/list?customerId=${tLoanContract.customerId}&type=1">授信记录</a></li>
		<li class="active"><a href="${ctx}/contract/tLoanContract/clist?customerId=${tLoanContract.customerId}&type=1">业务记录</a></li>
		<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tLoanContract.customerId}&type=2&status=9">不良记录</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="1"/>
		<form:input path="customerId" htmlEscape="false" maxlength="64" type="hidden" class="input-medium"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>合同编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>贷款金额：</label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>产品名称</th>
				<th>贷款金额（元）</th>
				<th>贷款期限</th>
				<th>贷款利率(%)</th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td>
					${tLoanContract.contractNumber}
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					${tLoanContract.productname}
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					${tLoanContract.loanPeriod}
					${(tLoanContract.loanPeriod == 1) ? '年' : ((tLoanContract.loanPeriod == 3) ? '日' : '个月')}
				</td>
				<td>
					${tLoanContract.loanRate}%
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>