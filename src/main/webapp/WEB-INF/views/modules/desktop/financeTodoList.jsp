<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.LoanContractStatus" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>财务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/index/todo/todoList">待办任务</a></li>
		<li><a href="${ctx}/index/todo/historic/">已办任务</a></li>
		<li class="active"><a href="${ctx}/index/todo/financeTodoList">财务</a></li>
		<li><a href="${ctx}/index/todo/warnList">提醒</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>当前环节</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${planList}" var="plan">
				<c:set var="contract" value="${plan.loanContract}"/>
				<tr>
					<td width="40%">
						<a href="${ctx}/receivables/receivables/receView?contractId=${contract.id}&message=">
						${contract.contractNumber}（金额：${contract.loanAmount}元，利率：${contract.loanRate}%）</a>
					</td>
					<td width="30%">待还款</td>
					<td width="30%"><a href="${ctx}/receivables/receivables/receView?contractId=${contract.id}&message=">还款</a></td>
				</tr>
			</c:forEach>
			<c:forEach items="${loanList}" var="contract">
				<tr>
					<td width="40%">
						<a href="${ctx}/lending/tLending/toLoan?contract.id=${contract.id}">
							${contract.contractNumber}（金额：${contract.loanAmount}元，利率：${contract.loanRate}%）</a>
					</td>
					<td width="30%">待放款</td>
					<%-- <td><fmt:formatDate value="${contract.createDate}" type="both"/></td> --%>
					<td width="30%"> 
<%-- 					      <a href="${ctx}/lending/tLending/toLoan?contract.id=${contract.id}">放款</a> --%>
					          <a href="${ctx}/lending/tLending/listIng">放款</a>
					      </td>
				</tr>
			</c:forEach>
			<c:forEach items="${db_to_chargeList}" var="contract">
				<tr>
					<td width="40%">
						<a href="${ctx}/receivables/receivables?contract.id=${contract.id}">
							${contract.contractNumber}（担保金额：${contract.loanAmount}元，担保费率：${contract.loanRate}%）</a>
					</td>
					<td  width="30%">待收担保费</td>
					<td  width="30%"> 
					      <a href="${ctx}/receivables/receivables">收费</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
